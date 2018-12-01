package org.xzh.springboot.dubbo.demo.provider;

import org.xzh.springboot.dubbo.demo.DemoService;

public class DemoServiceImpl implements DemoService {

	@Override
	public String sayHello(String name) {
		return "hello "+name;
	}

}
