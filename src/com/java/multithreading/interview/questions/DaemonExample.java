package com.java.multithreading.interview.questions;

public class DaemonExample {
    public static void main(String[] args) throws InterruptedException {

        Thread userThread = new Thread(() -> {
            try {
                System.out.println("User thread started");
                Thread.sleep(2000); // Simulate work
                System.out.println("User thread finished");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "UserThread");

        Thread daemonThread = new Thread(() -> {
            try {
                while (true) {
                    System.out.println("Daemon thread running...");
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "DaemonThread");

        // Set daemon before starting
        daemonThread.setDaemon(true);

        userThread.start();
        daemonThread.start();
        userThread.join();
        System.out.println("Main thread exits; daemon will stop if running");
    }
}
