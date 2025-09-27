package com.java.multithreading.interview.questions;

public class ThreadGroupExample {
    public static void main(String[] args) {

        // Create a thread group
        ThreadGroup group = new ThreadGroup("MyThreadGroup");

        // Thread 1 in the group
        Thread t1 = new Thread(group, () -> {
            System.out.println(Thread.currentThread().getName() + " running");
            try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        }, "Thread-1");

        // Thread 2 in the group
        Thread t2 = new Thread(group, () -> {
            System.out.println(Thread.currentThread().getName() + " running");
            try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        }, "Thread-2");

        // Start threads
        t1.start();
        t2.start();

        // Print active thread count
        System.out.println("Active threads in group: " + group.activeCount());

        // Enumerate threads in the group
        Thread[] threads = new Thread[group.activeCount()];
        group.enumerate(threads);
        for (Thread t : threads) {
            System.out.println("Thread in group: " + t.getName());
        }

        // Interrupt all threads in the group
        group.interrupt();
    }
}

