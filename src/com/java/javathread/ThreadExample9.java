package com.java.javathread;

public class ThreadExample9 {
    public static void main(String[] args) {
        Runnable runnable = () -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                if (!Thread.currentThread().isInterrupted()) {
                    System.out.println("Running");
                }
            }
        };

        Thread  thread = new Thread(runnable);
        thread.setDaemon(true);
        thread.start();
        try {
            Thread.sleep(3100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
