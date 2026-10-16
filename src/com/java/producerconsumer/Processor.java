package com.java.producerconsumer;

public final class Processor {
    private int value;
    private boolean available;

    public synchronized void produce(int nextValue) throws InterruptedException {
        while (available) {
            wait();
        }
        value = nextValue;
        available = true;
        notifyAll();
    }

    public synchronized int consume() throws InterruptedException {
        while (!available) {
            wait();
        }
        available = false;
        notifyAll();
        return value;
    }
}
