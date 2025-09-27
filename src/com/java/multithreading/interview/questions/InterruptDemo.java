package com.java.multithreading.interview.questions;

public class InterruptDemo {
    public static void main(String[] args) throws InterruptedException {
        // Using interrupt
        Thread worker = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("Worker running...");
                try {
                    //Thread.sleep(500); // may throw InterruptedException
                    System.out.println("Worker ");
                }catch (Exception ex){
                    ex.printStackTrace();
                }
//                catch (InterruptedException e) {
//                    System.out.println("Worker interrupted during sleep");
//                    Thread.currentThread().interrupt(); // preserve interrupted status
//                }
            }
            System.out.println("Worker exiting gracefully");
        });

        worker.start();
        Thread.sleep(1600); // worker run for some time
        worker.interrupt();
        worker.join();

        System.out.println("Main thread exits");
    }
}
