package org.xzh.springboot.java8.lambda;

public class AppleInfo {

	public void printSize(int size, LoggerInfo logger){
		logger.info(size+"cm");
	}
	
	public void printWeight(int weight, LoggerInfo logger){
		logger.info(weight+"kg");
	}
}
