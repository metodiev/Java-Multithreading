package com.java.javamemorymodel;

public class SeparateObjects {
    public static void main(String[] args) {
        int myLocalVar = 0 ;
        String myLocalString = "Text";

        Runnable runnable1 = new MyRunnable();
        Runnable runnable2 = new MyRunnable();

        Thread thread1 = new Thread(runnable1, "Thread1");
        Thread thread2 = new Thread(runnable2, "Thread 2");

        thread1.start();
        thread2.start();
    }
}
