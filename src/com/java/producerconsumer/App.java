package com.java.producerconsumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public final class App {
    private static final int ITEM_COUNT = 20;
    private static final int STOP = -1;

    private App() {
    }

    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(4);
        ExecutorService workers = Executors.newFixedThreadPool(2);
        workers.execute(() -> produce(queue));
        workers.execute(() -> consume(queue));
        workers.shutdown();

        try {
            if (!workers.awaitTermination(10, TimeUnit.SECONDS)) {
                workers.shutdownNow();
                throw new IllegalStateException("Producer-consumer demo did not terminate");
            }
            runMonitorHandoff();
        } catch (InterruptedException e) {
            workers.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    private static void runMonitorHandoff() throws InterruptedException {
        Processor handoff = new Processor();
        Thread producer = new Thread(() -> {
            try {
                handoff.produce(42);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "monitor-producer");
        Thread consumer = new Thread(() -> {
            try {
                System.out.println("Monitor consumer received: " + handoff.consume());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "monitor-consumer");

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
    }

    private static void produce(BlockingQueue<Integer> queue) {
        try {
            for (int value = 0; value < ITEM_COUNT; value++) {
                queue.put(value);
                System.out.println("Produced: " + value);
            }
            queue.put(STOP);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void consume(BlockingQueue<Integer> queue) {
        try {
            while (true) {
                int value = queue.take();
                if (value == STOP) {
                    System.out.println("Consumer received stop signal");
                    return;
                }
                System.out.println("Consumed: " + value);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
