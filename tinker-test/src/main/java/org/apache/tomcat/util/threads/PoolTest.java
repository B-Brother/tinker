package org.apache.tomcat.util.threads;

import java.util.concurrent.TimeUnit;

class MyRunnable implements Runnable{
	private int id;
	
	public MyRunnable(int id){
		this.id = id;
	}
	
	@Override
	public void run() {
		 System.out.println(id);
	}
	
}

public class PoolTest {
	public static void main(String[] args) {
		TaskQueue queue = new TaskQueue();
		
		for (int i = 0; i < 30; i++) {
			queue.add(new MyRunnable(i));
		}
		
		ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 3000, TimeUnit.MILLISECONDS, queue);
		 
	}
}
