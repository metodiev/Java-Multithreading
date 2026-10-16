package com.java.systemdesign;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

public final class ResiliencePatternsDemo {
    private ResiliencePatternsDemo() {
    }

    public static void main(String[] args) throws InterruptedException {
        demonstrateBoundedRetries();
        demonstrateTimeoutCancellation();
        demonstrateTokenBucket();
    }

    private static void demonstrateBoundedRetries() {
        AtomicInteger attempts = new AtomicInteger();
        String idempotencyKey = "order-42";
        String result = retry(3, () -> {
            int attempt = attempts.incrementAndGet();
            System.out.println("Attempt " + attempt + " for " + idempotencyKey);
            if (attempt < 3) {
                throw new TransientFailure();
            }
            return "accepted";
        });
        System.out.println("Retry result: " + result);
    }

    private static <T> T retry(int maxAttempts, Retryable<T> operation) {
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                return operation.run();
            } catch (TransientFailure failure) {
                if (attempt == maxAttempts) {
                    throw failure;
                }
                long backoffMillis = 5L << (attempt - 1);
                try {
                    Thread.sleep(backoffMillis);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new IllegalStateException("Retry interrupted", e);
                }
            }
        }
        throw new AssertionError("Unreachable");
    }

    private static void demonstrateTimeoutCancellation() throws InterruptedException {
        ExecutorService worker = Executors.newSingleThreadExecutor();
        CountDownLatch started = new CountDownLatch(1);
        try {
            java.util.concurrent.Future<?> task = worker.submit(() -> {
                started.countDown();
                try {
                    new CountDownLatch(1).await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Timed-out task observed cancellation");
                }
            });
            if (!started.await(1, TimeUnit.SECONDS)) {
                throw new IllegalStateException("Timed-out task did not start");
            }
            try {
                task.get(10, TimeUnit.MILLISECONDS);
            } catch (TimeoutException expected) {
                task.cancel(true);
                System.out.println("Deadline expired; cancellation requested");
            } catch (java.util.concurrent.ExecutionException e) {
                throw new IllegalStateException("Task failed unexpectedly", e.getCause());
            }
        } finally {
            worker.shutdownNow();
            if (!worker.awaitTermination(2, TimeUnit.SECONDS)) {
                throw new IllegalStateException("Timed-out worker did not terminate");
            }
        }
    }

    private static void demonstrateTokenBucket() {
        TokenBucket limiter = new TokenBucket(2, TimeUnit.MILLISECONDS.toNanos(5));
        boolean first = limiter.tryAcquire();
        boolean second = limiter.tryAcquire();
        boolean third = limiter.tryAcquire();
        System.out.println("Token bucket admitted first two: "
                + (first && second) + "; rejected third: " + !third);
        if (!first || !second || third) {
            throw new AssertionError("Token bucket capacity was not enforced");
        }
        try {
            Thread.sleep(15);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Token refill demonstration interrupted", e);
        }
        if (!limiter.tryAcquire()) {
            throw new AssertionError("Token bucket did not refill");
        }
        System.out.println("Token bucket refilled after elapsed time");
    }

    private interface Retryable<T> {
        T run();
    }

    private static final class TransientFailure extends RuntimeException {
        private static final long serialVersionUID = 1L;
    }

    private static final class TokenBucket {
        private final int capacity;
        private final long nanosPerToken;
        private double tokens;
        private long lastRefillNanos;

        private TokenBucket(int capacity, long nanosPerToken) {
            if (capacity <= 0 || nanosPerToken <= 0) {
                throw new IllegalArgumentException("Capacity and refill interval must be positive");
            }
            this.capacity = capacity;
            this.tokens = capacity;
            this.nanosPerToken = nanosPerToken;
            this.lastRefillNanos = System.nanoTime();
        }

        private synchronized boolean tryAcquire() {
            long now = System.nanoTime();
            long elapsed = now - lastRefillNanos;
            tokens = Math.min(capacity, tokens + (double) elapsed / nanosPerToken);
            lastRefillNanos = now;
            if (tokens < 1.0) {
                return false;
            }
            tokens -= 1.0;
            return true;
        }
    }
}
