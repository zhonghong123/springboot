package org.xzh.springboot.spring.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigureTest {

	@Bean
	public InstanceTest publicInstance(){
		return new InstanceTest("public");
	}
}
