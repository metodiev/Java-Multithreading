package com.java.multithreading.interview.questions;

class StaticCounter {
    private static int count = 0;

    public static synchronized void increment() {
        count++;
        System.out.println(Thread.currentThread().getName() + " incremented to " + count);
    }

    public static synchronized void decrement() {
        count--;
        System.out.println(Thread.currentThread().getName() + " decremented to " + count);
    }
}

public class StaticSyncDemo {
    public static void main(String[] args) throws InterruptedException {
        StaticCounter c1 = new StaticCounter();
        StaticCounter c2 = new StaticCounter();

        Thread t1 = new Thread(() -> c1.increment(), "T1");
        Thread t2 = new Thread(() -> c2.decrement(), "T2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}
