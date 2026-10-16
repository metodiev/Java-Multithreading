package com.java.threadpools;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

final class Processor implements Runnable {
    private final int id;

    Processor(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Starting " + id);
        try {
            Thread.sleep(100);
            System.out.println("Complete: " + id);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Interrupted: " + id);
        }
    }
}

public final class App {
    private App() {
    }

    public static void main(String[] args) {
        ThreadFactory threadFactory = new ThreadFactory() {
            private int nextId;

            @Override
            public synchronized Thread newThread(Runnable task) {
                return new Thread(task, "worker-" + nextId++);
            }
        };
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2,
                2,
                0L,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(3),
                threadFactory,
                new ThreadPoolExecutor.AbortPolicy());

        try {
            for (int i = 0; i < 5; i++) {
                executor.execute(new Processor(i));
            }
        } finally {
            executor.shutdown();
        }

        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
                if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                    throw new IllegalStateException("Executor did not terminate");
                }
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        System.out.println("All submitted tasks completed");
    }
}
