package org.xzh.springboot.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xzh.springboot.spring.annotation.SimpleServiceImpl;

public class XmlMain {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
		
		SimpleServiceImpl simpleServiceImpl = context.getBean(SimpleServiceImpl.class);
		simpleServiceImpl.printString();
	}
}
