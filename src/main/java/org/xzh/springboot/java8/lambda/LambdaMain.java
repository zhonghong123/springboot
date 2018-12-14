package org.xzh.springboot.java8.lambda;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LambdaMain {
	
	private static final Logger logger = LoggerFactory.getLogger(LambdaMain.class);

	public static void main(String[] args) {
		AppleInfo appleInfo = new AppleInfo();
		appleInfo.printSize(12, msg -> logger.info(msg));
		appleInfo.printWeight(1, msg -> System.out.println(msg));
	}
}
