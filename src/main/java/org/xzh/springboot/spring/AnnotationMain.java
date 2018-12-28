package org.xzh.springboot.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.xzh.springboot.spring.annotation.SimpleServiceImpl;

@Configuration
@ComponentScan(basePackages="org.xzh.springboot.spring")
public class AnnotationMain {

	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(AnnotationMain.class);
		
		SimpleServiceImpl simpleServiceImpl = context.getBean(SimpleServiceImpl.class);
		simpleServiceImpl.printString();
	}
}
