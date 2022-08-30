package com.java.javathread;

public class ThreadExample3 {

  public static class MyRunable implements  Runnable {
      @Override
      public void run() {
          System.out.println("MyRunnable running");
          System.out.println("MyRunnable finished");
      }
  }

    public static void main(String[] args) {
        Thread  thread = new Thread(new MyRunable());
        thread.start();
    }
}
