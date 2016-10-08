package com.java.synchronization;

import java.util.Scanner;

class Processor extends Thread{
	
	private boolean running = true;
	
	public void run(){
		while(running){
			System.out.println("Hello");
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void shutdown(){
		running = false;
	}
}

public class App {

	public static void main(String[] args){
		Processor process = new Processor();
		
		process.start();
		
		System.out.println("Press return to stop ... ");
		Scanner input = new Scanner(System.in);
		input.nextLine();
		
		process.shutdown();
		
	}
	
}
