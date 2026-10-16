package com.java.javathread;

public class ThreadExample10 {
    public static void main(String[] args) {
        Runnable runnable = () -> {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
                System.out.println("Runing");
            }
        };

        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        thread.start();

        try{
            thread.join();
        }catch (InterruptedException e){
            thread.interrupt();
            Thread.currentThread().interrupt();
        }
    }

}
