package com.java.multiplelocks.synchronizedcodeblocks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Worker {
	private final Object lock1 = new Object();
	private final Object lock2 = new Object();
	private final List<Integer> list1 = new ArrayList<Integer>();
	private final List<Integer> list2 = new ArrayList<Integer>();

	public void stageOne() {
		synchronized (lock1) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				return;
			}
			list1.add(ThreadLocalRandom.current().nextInt(10));
		}
	}

	public void stageTwo() {
		synchronized (lock2) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				return;
			}
			list2.add(ThreadLocalRandom.current().nextInt(10));
		}
	}

	public void process() {
		for (int i = 0; i < 1000 && !Thread.currentThread().isInterrupted(); i++) {
			stageOne();
			if (Thread.currentThread().isInterrupted()) {
				return;
			}
			stageTwo();
		}
	}

	public void main() {
		System.out.println("Starting...");

		long start = System.nanoTime();
		Thread t1 = new Thread(this::process, "worker-1");
		Thread t2 = new Thread(this::process, "worker-2");

		t1.start();
		t2.start();
		boolean interrupted = false;
		while (t1.isAlive() || t2.isAlive()) {
			try {
				t1.join();
				t2.join();
			} catch (InterruptedException e) {
				interrupted = true;
				t1.interrupt();
				t2.interrupt();
			}
		}

		long elapsedMillis = (System.nanoTime() - start) / 1_000_000;
		System.out.println("Time taken: " + elapsedMillis + " ms");
		System.out.println("List1: " + list1.size() + " List2: " + list2.size());
		if (interrupted) {
			Thread.currentThread().interrupt();
		}
	}
}
