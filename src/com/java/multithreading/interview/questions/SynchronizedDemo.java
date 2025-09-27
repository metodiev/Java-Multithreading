package com.java.multithreading.interview.questions;

public class SynchronizedDemo {

    private int counter = 0;

    // Synchronized instance method
    public synchronized void increment() {
        counter++;
        System.out.println(Thread.currentThread().getName() + " incremented counter to " + counter);
    }

    // Synchronized block with custom lock
    private final Object lock = new Object();
    public void decrement() {
        synchronized (lock) {
            counter--;
            System.out.println(Thread.currentThread().getName() + " decremented counter to " + counter);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SynchronizedDemo demo = new SynchronizedDemo();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) demo.increment();
        }, "T1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) demo.decrement();
        }, "T2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Final counter value: " + demo.counter);
    }
}

