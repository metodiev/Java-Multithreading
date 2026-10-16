package com.java.modern;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public final class VirtualThreadsDemo {
    private VirtualThreadsDemo() {
    }

    public static void main(String[] args) throws Exception {
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<String>> tasks = new ArrayList<Future<String>>();
            for (int i = 0; i < 1000; i++) {
                final int requestId = i;
                tasks.add(executor.submit(() -> {
                    Thread.sleep(10);
                    return "request-" + requestId + " on " + Thread.currentThread();
                }));
            }
            System.out.println(tasks.get(0).get(1, TimeUnit.SECONDS));
            System.out.println("Submitted requests: " + tasks.size());
        }
    }
}
