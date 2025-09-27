package com.java.multithreading.interview.questions;

public class ThreadStatesDemo {
    public static void main(String[] args) throws InterruptedException {
        final Object lock = new Object();

        Thread holder = new Thread(() -> {
            synchronized (lock) {
                try {
                    Thread.sleep(1500); // TIMED_WAITING
                } catch (InterruptedException ignored) {}
            }
        }, "Holder");

        Thread blocked = new Thread(() -> {
            synchronized (lock) {
                System.out.println("Blocked thread acquired lock");
            }
        }, "Blocked");

        Thread sleeper = new Thread(() -> {
            try {
                Thread.sleep(2000); // TIMED_WAITING
            } catch (InterruptedException ignored) {}
        }, "Sleeper");

        // Show NEW state
        System.out.println("Initial state: " + holder.getState()); // NEW

        holder.start();
        Thread.sleep(50); // let holder acquire lock TIMED_WAITING for holder thread
        blocked.start();
        sleeper.start();

        Thread.sleep(100); // allow threads to settle
        System.out.println("Holder: " + holder.getState());   // TIMED_WAITING
        System.out.println("Blocked: " + blocked.getState()); // BLOCKED
        System.out.println("Sleeper: " + sleeper.getState()); // TIMED_WAITING

        holder.join();
        blocked.join();
        sleeper.join();

        System.out.println("Final Holder state: " + holder.getState()); // TERMINATED
    }
}

