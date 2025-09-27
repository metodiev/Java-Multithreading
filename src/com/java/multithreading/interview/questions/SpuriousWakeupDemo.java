package com.java.multithreading.interview.questions;

import java.util.concurrent.locks.*;

public class SpuriousWakeupDemo {
    private static final Lock lock = new ReentrantLock();
    private static final Condition condition = lock.newCondition();
    private static boolean ready = false;

    public static void main(String[] args) throws InterruptedException {
        Thread waitingThread = new Thread(() -> {
            lock.lock();
            try {
                while (!ready) { // loop protects against spurious wakeups
                    System.out.println("WaitingThread: waiting...");
                    condition.await();
                }
                System.out.println("WaitingThread: condition met, proceeding");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        });

        Thread signalingThread = new Thread(() -> {
            try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
            lock.lock();
            try {
                ready = true;
                System.out.println("SignalingThread: signaling condition");
                condition.signal();
            } finally {
                lock.unlock();
            }
        });

        waitingThread.start();
        signalingThread.start();

        waitingThread.join();
        signalingThread.join();
    }
}

