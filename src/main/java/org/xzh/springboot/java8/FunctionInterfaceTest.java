package org.xzh.springboot.java8;

@FunctionalInterface
public interface FunctionInterfaceTest {

	public void logger(String name);
	
	default int sum(int a, int b){
		return a+b;
	}
}
