package com.java.synchronization;

import java.util.Scanner;

class Processor extends Thread {

	private volatile boolean running = true;
	private volatile int count = 0;

	public void run() {
		while (running) {
			System.out.println("Hello" );
			++count;
			try {
				Thread.sleep(1000);
				
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(count);
		}
		
	}

	public void shutdown() {
		running = false;
	}
}

public class App {

	public static void main(String[] args) {
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
		

	}

}
