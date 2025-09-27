package com.java.multithreading.interview.questions;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadRunnableCallableDemo {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        //Thread class
        Thread thread = new Thread() {
            public void run() {
                System.out.println("Thread class running");
            }
        };
        thread.start();
        thread.join();

        //Runnable interface
        Runnable runnableTask = () -> System.out.println("Runnable running");
        Thread runnableThread = new Thread(runnableTask);
        runnableThread.start();
        runnableThread.join();

        //⃣Callable interface with ExecutorService
        Callable<String> callableTask = () -> {
            Thread.sleep(500);
            return " return the Callable result";
        };
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(callableTask);
        System.out.println(future.get()); // "Callable result"
        executor.shutdown();
    }
}

