package com.java.multithreading.interview.questions;

public class WaitVsSleepDemo {
    private static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        // Example of wait()
        Thread waiter = new Thread(() -> {
            synchronized (lock) {
                try {
                    System.out.println("Waiter: calling wait(), releases lock");
                    lock.wait(); // releases lock
                    System.out.println("Waiter: resumed after notify");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        // Example of sleep()
        Thread sleeper = new Thread(() -> {
            synchronized (lock) {
                try {
                    System.out.println("Sleeper: calling sleep(), lock held");
                    Thread.sleep(2000); // lock is NOT released
                    System.out.println("Sleeper: finished sleeping");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        waiter.start();
        Thread.sleep(500); // ensure waiter starts first
        sleeper.start();

        Thread.sleep(1000);
        synchronized (lock) {
            System.out.println("Main thread: calling notify()");
            lock.notify(); // wakes waiter
        }

        waiter.join();
        sleeper.join();
    }
}
