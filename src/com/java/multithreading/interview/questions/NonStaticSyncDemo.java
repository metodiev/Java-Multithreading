package com.java.multithreading.interview.questions;

class Counter {
    private int count = 0;

    public synchronized void increment() {
        count++;
        System.out.println(Thread.currentThread().getName() + " incremented to " + count);
    }

    public synchronized void decrement() {
        count--;
        System.out.println(Thread.currentThread().getName() + " decremented to " + count);
    }
}

public class NonStaticSyncDemo {
    public static void main(String[] args) throws InterruptedException {
        Counter c1 = new Counter();
        Counter c2 = new Counter();

        Thread t1 = new Thread(() -> c1.increment(), "T1");
        Thread t2 = new Thread(() -> c1.decrement(), "T2");
        Thread t3 = new Thread(() -> c2.increment(), "T3");

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();
    }
}

