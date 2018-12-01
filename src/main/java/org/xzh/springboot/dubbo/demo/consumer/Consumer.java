package org.xzh.springboot.dubbo.demo.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xzh.springboot.dubbo.demo.DemoService;

public class Consumer {
	
	private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:dubbo/consumer.xml");
		context.start();
		
		// 获取远程服务代理
		DemoService service = (DemoService) context.getBean("demoService");
		// 执行远程方法
		String hello = service.sayHello("world");
		logger.info(hello);
	}
}
