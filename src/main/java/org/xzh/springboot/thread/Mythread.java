package org.xzh.springboot.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mythread extends Thread {
	
	private static final Logger logger = LoggerFactory.getLogger(Mythread.class);

	@Override
	public void run() {
		logger.info("system");
	}
	
	public static void main(String[] args) {
		Mythread th = new Mythread();
		th.start();
	}
}
