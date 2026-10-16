package com.java.multithreading.interview.questions;

public class InterruptDemo {
    public static void main(String[] args) throws InterruptedException {
        // Using interrupt
        Thread worker = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("Worker running...");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("Worker exiting gracefully");
        });

        worker.start();
        Thread.sleep(600); // worker run for some time
        worker.interrupt();
        worker.join();

        System.out.println("Main thread exits");
    }
}
