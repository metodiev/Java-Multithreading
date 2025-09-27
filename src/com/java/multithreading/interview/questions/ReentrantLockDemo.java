package com.java.multithreading.interview.questions;


import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {

    private final ReentrantLock lock = new ReentrantLock(true); // fair lock
    private int counter = 0;

    public void increment() {
        lock.lock();
        try {
            counter++;
            System.out.println(Thread.currentThread().getName() + " incremented to " + counter);
        } finally {
            lock.unlock(); // always unlock in finally
        }
    }

    public void decrement() {
        lock.lock();
        try {
            counter--;
            System.out.println(Thread.currentThread().getName() + " decremented to " + counter);
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockDemo demo = new ReentrantLockDemo();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) demo.increment();
        }, "T1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) demo.decrement();
        }, "T2");

        //demo.lock
        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Final counter: " + demo.counter);
    }
}

