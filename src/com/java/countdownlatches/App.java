package com.java.countdownlatches;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//http://stackoverflow.com/questions/17827022/how-is-countdownlatch-used-in-java-multithreading
class Processor implements Runnable {

	private CountDownLatch latch;

	public Processor(CountDownLatch latch) {
		this.latch = latch;
	}

	@Override
	public void run() {
		System.out.println("Started..");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} finally {
			latch.countDown();
		}
	}

}

public class App {

	public static void main(String[] args) {
		CountDownLatch latch = new CountDownLatch(3);

		ExecutorService executor = Executors.newFixedThreadPool(3);

		for (int i = 0; i < 3; i++) {
			executor.submit(new Processor(latch));
		}
		executor.shutdown();
		try {
			latch.await();
			if (!executor.awaitTermination(5, java.util.concurrent.TimeUnit.SECONDS)) {
				executor.shutdownNow();
				throw new IllegalStateException("Worker pool did not terminate");
			}
		} catch (InterruptedException e) {
			executor.shutdownNow();
			Thread.currentThread().interrupt();
		}

		System.out.println("Completed...");
	}

}
