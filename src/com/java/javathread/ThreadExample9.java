package com.java.javathread;

public class ThreadExample9 {
    public static void main(String[] args) {
        Runnable runnable = () -> {
          while(true) {
              sleep(1000);
              System.out.println("Running");
          }
        };

        Thread  thread = new Thread(runnable);
        thread.start();
        thread.setDaemon(true);
        sleep(3100);
    }

    public static void sleep(long millis){
        try{
            Thread.sleep(millis);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
