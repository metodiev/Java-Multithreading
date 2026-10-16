package com.java.concurrency;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class CoordinationUtilitiesDemo {
    private CoordinationUtilitiesDemo() {
    }

    public static void main(String[] args) throws InterruptedException {
        demonstrateCyclicBarrier();
        demonstratePhaser();
        demonstrateScheduler();
    }

    private static void demonstrateCyclicBarrier() throws InterruptedException {
        final CyclicBarrier barrier = new CyclicBarrier(3,
                () -> System.out.println("All barrier parties arrived"));
        ExecutorService workers = Executors.newFixedThreadPool(3);
        try {
            for (int i = 0; i < 3; i++) {
                final int workerId = i;
                workers.execute(() -> {
                    System.out.println("Barrier worker ready: " + workerId);
                    try {
                        barrier.await();
                    } catch (Exception e) {
                        throw new IllegalStateException("Barrier failed", e);
                    }
                });
            }
        } finally {
            workers.shutdown();
        }
        if (!workers.awaitTermination(5, TimeUnit.SECONDS)) {
            workers.shutdownNow();
            throw new IllegalStateException("Barrier workers did not terminate");
        }
    }

    private static void demonstratePhaser() throws InterruptedException {
        final Phaser phaser = new Phaser(1);
        ExecutorService workers = Executors.newFixedThreadPool(2);
        try {
            for (int i = 0; i < 2; i++) {
                phaser.register();
                final int workerId = i;
                workers.execute(() -> {
                    System.out.println("Phaser worker completed phase: " + workerId);
                    phaser.arriveAndDeregister();
                });
            }
            phaser.arriveAndAwaitAdvance();
            System.out.println("Phaser advanced; registered parties: "
                    + phaser.getRegisteredParties());
        } finally {
            workers.shutdown();
        }
        if (!workers.awaitTermination(5, TimeUnit.SECONDS)) {
            workers.shutdownNow();
            throw new IllegalStateException("Phaser workers did not terminate");
        }
    }

    private static void demonstrateScheduler() throws InterruptedException {
        ScheduledExecutorService scheduler =
                Executors.newSingleThreadScheduledExecutor();
        try {
            scheduler.schedule(
                    () -> System.out.println("Scheduled task ran"),
                    10,
                    TimeUnit.MILLISECONDS);
        } finally {
            scheduler.shutdown();
        }
        if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
            scheduler.shutdownNow();
            throw new IllegalStateException("Scheduled task did not finish");
        }
    }
}
