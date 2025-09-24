package com.java.multithreading.interview.questions;

public class ThreadLifecycleDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(1000); // TIMED_WAITING
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("State before start: " + t.getState()); // NEW
        t.start();
        Thread.sleep(100); // Let it start
        System.out.println("State after start: " + t.getState()); // RUNNABLE / TIMED_WAITING
        t.join(); // Waits for termination
        System.out.println("State after termination: " + t.getState()); // TERMINATED
    }
}