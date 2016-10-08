package com.java.startingthreads;

class Runner extends Thread{

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			System.out.println("Thread Number: " + i);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}

public class App {

	public static void main(String[] args){
		Runner runner = new Runner();
		runner.start();
		
		Runner runne2 = new Runner();
		runne2.start();
	}
}
