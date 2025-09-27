package com.java.multithreading.interview.questions;

public class DaemonExample {
    public static void main(String[] args) throws InterruptedException {

        Thread userThread = new Thread(() -> {
            try {
                System.out.println("User thread started");
                Thread.sleep(2000); // Simulate work
                System.out.println("User thread finished");
            } catch (InterruptedException ignored) {}
        }, "UserThread");

        Thread daemonThread = new Thread(() -> {
            try {
                while (true) {
                    System.out.println("Daemon thread running...");
                    Thread.sleep(500);
                }
            } catch (InterruptedException ignored) {}
        }, "DaemonThread");

        // Set daemon before starting
        daemonThread.setDaemon(true);

        userThread.start();
        daemonThread.start();
        Thread.sleep(4000);
        System.out.println("Daemon thread after sleep " + daemonThread.getState());
        userThread.join();
        System.out.println("Main thread exits; daemon will stop if running");
        printExampleUsage();
    }

    public static void printExampleUsage(){
        while(true){
            System.out.println("The other part of the program will be executed");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

