package org.xzh.springboot.dubbo.demo.provider;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Provider {

	public static void main(String[] args) throws IOException {
		//ClassPathXmlApplicationContext context = 
		//new ClassPathXmlApplicationContext(new String[] {"http://10.20.160.198/wiki/display/dubbo/provider.xml"});
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:dubbo/provider.xml");
		context.start();
		System.in.read();
	}
}
