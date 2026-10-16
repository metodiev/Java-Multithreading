package com.java.synchronization;

import java.util.Scanner;

class Processor extends Thread {

	private volatile boolean running = true;
	private int count = 0;

	public void run() {
		while (running) {
			System.out.println("Hello" );
			++count;
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			}
			System.out.println(count);
		}
		
	}

	public void shutdown() {
		running = false;
		interrupt();
	}
}

public class App {

	public static void main(String[] args) throws InterruptedException {
		Processor process = new Processor();
		process.start();

		Processor process1 = new Processor();
		process1.start();
		
		Processor process2 = new Processor();
		process2.start();

		System.out.println("Press return to stop ... ");
		Scanner input = new Scanner(System.in);
		input.nextLine();

		process.shutdown();
		process1.shutdown();
		process2.shutdown();
		process.join();
		process1.join();
		process2.join();

	}

}
