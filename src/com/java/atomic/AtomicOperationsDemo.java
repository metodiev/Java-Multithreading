package com.java.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.atomic.LongAdder;

public final class AtomicOperationsDemo {
    private AtomicOperationsDemo() {
    }

    public static void main(String[] args) throws InterruptedException {
        final int workers = 4;
        final int incrementsPerWorker = 100000;
        final AtomicInteger exactCount = new AtomicInteger();
        final LongAdder statistics = new LongAdder();
        final CountDownLatch done = new CountDownLatch(workers);

        for (int i = 0; i < workers; i++) {
            new Thread(() -> {
                for (int j = 0; j < incrementsPerWorker; j++) {
                    exactCount.incrementAndGet();
                    statistics.increment();
                }
                done.countDown();
            }, "counter-" + i).start();
        }

        done.await();
        int expected = workers * incrementsPerWorker;
        System.out.println("AtomicInteger exact count: " + exactCount.get());
        System.out.println("LongAdder aggregate: " + statistics.sum());
        if (exactCount.get() != expected || statistics.sum() != expected) {
            throw new AssertionError("Lost counter update");
        }

        demonstrateStampedReference();
    }

    private static void demonstrateStampedReference() {
        AtomicStampedReference<String> reference =
                new AtomicStampedReference<String>("A", 0);
        int[] observedStamp = new int[1];
        String observedValue = reference.get(observedStamp);

        reference.compareAndSet("A", "B", observedStamp[0], observedStamp[0] + 1);
        int currentStamp = reference.getStamp();
        reference.compareAndSet("B", "A", currentStamp, currentStamp + 1);

        boolean staleCasSucceeded = reference.compareAndSet(
                observedValue, "C", observedStamp[0], observedStamp[0] + 1);
        System.out.println("Stamped reference detected A -> B -> A: "
                + !staleCasSucceeded);
    }
}
