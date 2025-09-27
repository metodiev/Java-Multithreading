package com.java.multithreading.interview.questions;

public class DaemonThreadDemo {
    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            while (true) {
                System.out.println("Daemon running...");
                try { Thread.sleep(500); } catch (InterruptedException e) {}
            }
        });
        t.setDaemon(true);
        t.start();
        System.out.println("Main thread exiting");
    }
}


