package org.xzh.springboot.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyRunnable implements Runnable {
	
	private static final Logger logger = LoggerFactory.getLogger(MyRunnable.class);

	@Override
	public void run() {
		logger.info(Thread.currentThread().getName());
	}
	
	public static void main(String[] args) {
		Thread t = new Thread(new MyRunnable());
		t.start();
	}

}
