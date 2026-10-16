package com.java.modern;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public final class CompletableFuturePipelineDemo {
    private CompletableFuturePipelineDemo() {
    }

    public static void main(String[] args) throws Exception {
        ExecutorService ioPool = Executors.newFixedThreadPool(2);
        try {
            CompletableFuture<String> customer =
                    CompletableFuture.supplyAsync(() -> "customer-42", ioPool);
            CompletableFuture<Integer> inventory =
                    CompletableFuture.supplyAsync(() -> 7, ioPool);

            CompletableFuture<String> order =
                    customer.thenApplyAsync(id -> "order for " + id, ioPool);
            CompletableFuture<String> summary =
                    order.thenCombineAsync(inventory,
                            (orderDescription, available) ->
                                    orderDescription + ", inventory=" + available,
                            ioPool);

            System.out.println(summary.get(2, TimeUnit.SECONDS));
        } finally {
            ioPool.shutdown();
            if (!ioPool.awaitTermination(2, TimeUnit.SECONDS)) {
                ioPool.shutdownNow();
            }
        }
    }
}
