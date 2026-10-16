package com.java.systemdesign;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class BoundedPipelineDemo {
    private BoundedPipelineDemo() {
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor workers = new ThreadPoolExecutor(
                2,
                2,
                0L,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(4),
                new ThreadPoolExecutor.CallerRunsPolicy());
        try {
            for (int i = 1; i <= 12; i++) {
                final int item = i;
                workers.execute(() -> process(item));
            }
        } finally {
            workers.shutdown();
            if (!workers.awaitTermination(5, TimeUnit.SECONDS)) {
                workers.shutdownNow();
                if (!workers.awaitTermination(5, TimeUnit.SECONDS)) {
                    System.err.println("Pipeline workers did not terminate");
                }
            }
        }
    }

    private static void process(int item) {
        System.out.println(Thread.currentThread().getName() + " processed item " + item);
    }
}
