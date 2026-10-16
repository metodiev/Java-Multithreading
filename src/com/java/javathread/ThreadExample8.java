package com.java.javathread;

public class ThreadExample8 {
    public static class StoppableRunnable implements Runnable {
        private boolean stopRequested = false;

        public synchronized void requestStop() {
            this.stopRequested = true;
        }

        public synchronized boolean isStopRequested() {
            return this.stopRequested;
        }

        @Override
        public void run() {
            System.out.println("StoppableRunnable Running");
            while (!isStopRequested()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
                System.out.println("...");
            }
            System.out.println("StoppableRunnable Stopped");
        }
    }

    public static void main(String[] args) {
        StoppableRunnable stoppableRunnable = new StoppableRunnable();
        Thread thread = new Thread(stoppableRunnable, "The Thread");
        thread.start();

        boolean interrupted = false;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            interrupted = true;
        } finally {
            stoppableRunnable.requestStop();
            thread.interrupt();
        }

        System.out.println("requesting stop");
        while (thread.isAlive()) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                interrupted = true;
                stoppableRunnable.requestStop();
                thread.interrupt();
            }
        }
        System.out.println("stop requested");
        if (interrupted) {
            Thread.currentThread().interrupt();
        }
    }
}
