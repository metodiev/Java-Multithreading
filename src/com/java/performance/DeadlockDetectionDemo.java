package com.java.performance;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public final class DeadlockDetectionDemo {
    private DeadlockDetectionDemo() {
    }

    public static void main(String[] args) throws InterruptedException {
        final ReentrantLock first = new ReentrantLock();
        final ReentrantLock second = new ReentrantLock();
        final CountDownLatch bothHoldFirst = new CountDownLatch(2);

        Thread a = deadlockingWorker("deadlock-demo-A", first, second, bothHoldFirst);
        Thread b = deadlockingWorker("deadlock-demo-B", second, first, bothHoldFirst);
        a.setDaemon(true);
        b.setDaemon(true);
        a.start();
        b.start();

        if (!bothHoldFirst.await(2, TimeUnit.SECONDS)) {
            throw new IllegalStateException("Workers did not reach the coordinated state");
        }

        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        long deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(3);
        long[] deadlockedThreads;
        do {
            deadlockedThreads = threadMXBean.findDeadlockedThreads();
            if (deadlockedThreads != null) {
                break;
            }
            Thread.sleep(10);
        } while (System.nanoTime() < deadline);

        if (deadlockedThreads == null) {
            throw new AssertionError("Expected deadlock was not detected");
        }
        ThreadInfo[] details = threadMXBean.getThreadInfo(deadlockedThreads, true, true);
        for (ThreadInfo detail : details) {
            System.out.println("Detected: " + detail.getThreadName()
                    + " waiting on " + detail.getLockInfo());
        }
        System.out.println("Daemon workers keep this demonstration process terminable.");
    }

    private static Thread deadlockingWorker(
            String name,
            ReentrantLock first,
            ReentrantLock second,
            CountDownLatch bothHoldFirst) {
        return new Thread(() -> {
            first.lock();
            try {
                bothHoldFirst.countDown();
                try {
                    bothHoldFirst.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
                second.lock();
                try {
                    System.out.println("Unreachable while deadlocked");
                } finally {
                    second.unlock();
                }
            } finally {
                first.unlock();
            }
        }, name);
    }
}
