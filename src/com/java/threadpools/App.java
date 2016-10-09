package com.java.threadpools;
//http://stackoverflow.com/questions/26938210/executorservice-vs-casual-thread-spawner
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Processor implements Runnable{

	private int id;
	public Processor(int id){
		this.id = id;
	}
	
	@Override
	public void run() {
		
		System.out.println("Starting " + id);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Complete: " + id);
	}
	
}

public class App {

	public static void main(String[] args){
		
		//first option is to create with Thread claas
		//Thread t1 = new Thread(new Processor(1));
		//Thread t2 = new Thread(new Processor(1));
		
		//Instead we use threadpool executorService
		ExecutorService executor = Executors.newFixedThreadPool(2);
		
		for (int i = 0; i < 5; i++) {
			executor.submit(new Processor(i));
		}
		
		executor.shutdown();
		System.out.println("All task submited.");
		
		try {
			executor.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("All task completed");
		
	}
	
}
