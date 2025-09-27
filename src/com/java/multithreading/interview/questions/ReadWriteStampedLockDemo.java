package com.java.multithreading.interview.questions;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

public class ReadWriteStampedLockDemo {
    private static int sharedData = 0;
    private static final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private static final StampedLock stampedLock = new StampedLock();

    public static void main(String[] args) throws InterruptedException {
        // Example with ReadWriteLock
        Thread reader1 = new Thread(() -> {
            rwLock.readLock().lock();
            try {
                System.out.println("Reader1 (ReadWriteLock) reads: " + sharedData);
                Thread.sleep(500);
            } catch (InterruptedException ignored) {}
            finally { rwLock.readLock().unlock(); }
        });

        Thread writer1 = new Thread(() -> {
            rwLock.writeLock().lock();
            try {
                sharedData++;
                System.out.println("Writer1 (ReadWriteLock) writes: " + sharedData);
                Thread.sleep(500);
            } catch (InterruptedException ignored) {}
            finally { rwLock.writeLock().unlock(); }
        });

        reader1.start();
        writer1.start();
        reader1.join();
        writer1.join();

        // Example with StampedLock
        Thread reader2 = new Thread(() -> {
            long stamp = stampedLock.tryOptimisticRead();
            int data = sharedData;
            try { Thread.sleep(200); } catch (InterruptedException ignored) {}
            if (!stampedLock.validate(stamp)) {
                stamp = stampedLock.readLock();
                try { data = sharedData; } finally { stampedLock.unlockRead(stamp); }
            }
            System.out.println("Reader2 (StampedLock) reads: " + data);
        });

        Thread writer2 = new Thread(() -> {
            long stamp = stampedLock.writeLock();
            try {
                sharedData++;
                System.out.println("Writer2 (StampedLock) writes: " + sharedData);
            } finally { stampedLock.unlockWrite(stamp); }
        });

        reader2.start();
        writer2.start();
        reader2.join();
        writer2.join();
    }
}