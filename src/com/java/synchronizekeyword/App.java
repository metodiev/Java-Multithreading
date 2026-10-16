package com.java.synchronizekeyword;

public class App {
//	What does intrinsic lock actually mean for a Java class
//http://stackoverflow.com/questions/38213467/what-does-intrinsic-lock-actually-mean-for-a-java-class
	private int count = 0;
	
	public synchronized void increment() {
		count++;
	}
	
	public static void main(String[] args) throws InterruptedException {
		App app = new App();
		app.doWork();
	}
	
	public void doWork() throws InterruptedException {
		Thread t1 = new Thread(new Runnable(){
			@Override
			public void run() {
				for (int i = 0; i < 10000; i++) {
					increment();
				}
			}
		});
		
		Thread t2 = new Thread(new Runnable(){
			@Override
			public void run() {
				for (int i = 0; i < 10000; i++) {
					increment();
				}
			}
		});
		
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		
		System.out.println("Count is:" + count);
		if (count != 20000) {
			throw new AssertionError("Synchronized counter lost updates: " + count);
		}
	}

}
