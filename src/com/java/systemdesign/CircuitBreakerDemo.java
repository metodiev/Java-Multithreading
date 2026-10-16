package com.java.systemdesign;

import java.util.concurrent.TimeUnit;

public final class CircuitBreakerDemo {
    private CircuitBreakerDemo() {
    }

    public static void main(String[] args) throws InterruptedException {
        CircuitBreaker breaker =
                new CircuitBreaker(2, TimeUnit.MILLISECONDS.toNanos(200));

        call(breaker, false);
        call(breaker, false);
        call(breaker, true);

        Thread.sleep(250);
        call(breaker, true);
        call(breaker, true);
    }

    private static void call(CircuitBreaker breaker, boolean dependencySucceeds) {
        if (!breaker.tryAcquire()) {
            System.out.println("Rejected fast; breaker is " + breaker.state());
            return;
        }

        if (dependencySucceeds) {
            breaker.recordSuccess();
            System.out.println("Dependency call succeeded; breaker is " + breaker.state());
        } else {
            breaker.recordFailure();
            System.out.println("Dependency call failed; breaker is " + breaker.state());
        }
    }

    private static final class CircuitBreaker {
        private enum State {
            CLOSED,
            OPEN,
            HALF_OPEN
        }

        private final int failureThreshold;
        private final long openNanos;
        private State state = State.CLOSED;
        private int failures;
        private long openedAt;
        private boolean probeInFlight;

        private CircuitBreaker(int failureThreshold, long openNanos) {
            if (failureThreshold <= 0 || openNanos <= 0) {
                throw new IllegalArgumentException("Threshold and open duration must be positive");
            }
            this.failureThreshold = failureThreshold;
            this.openNanos = openNanos;
        }

        private synchronized boolean tryAcquire() {
            if (state == State.OPEN) {
                if (System.nanoTime() - openedAt < openNanos) {
                    return false;
                }
                state = State.HALF_OPEN;
                probeInFlight = false;
            }
            if (state == State.HALF_OPEN) {
                if (probeInFlight) {
                    return false;
                }
                probeInFlight = true;
            }
            return true;
        }

        private synchronized void recordSuccess() {
            if (state == State.HALF_OPEN) {
                state = State.CLOSED;
                failures = 0;
                probeInFlight = false;
            } else if (state == State.CLOSED) {
                failures = 0;
            }
        }

        private synchronized void recordFailure() {
            if (state == State.HALF_OPEN) {
                open();
            } else if (state == State.CLOSED && ++failures >= failureThreshold) {
                open();
            }
        }

        private void open() {
            state = State.OPEN;
            openedAt = System.nanoTime();
            probeInFlight = false;
        }

        private synchronized State state() {
            return state;
        }
    }
}
