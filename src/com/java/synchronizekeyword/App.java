package com.java.synchronizekeyword;

public class App {
//	What does intrinsic lock actually mean for a Java class
//http://stackoverflow.com/questions/38213467/what-does-intrinsic-lock-actually-mean-for-a-java-class
	private int count = 0;
	
	public synchronized void increment() {
		count++;
	}
	
	public static void main(String[] args) {
		App app = new App();
		app.doWork();
	}
	
	public void doWork(){
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
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Count is:" + count);
	}

}
