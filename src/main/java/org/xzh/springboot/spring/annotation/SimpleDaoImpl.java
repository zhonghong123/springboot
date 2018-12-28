package org.xzh.springboot.spring.annotation;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
public class SimpleDaoImpl implements SimpleDao {

	@Override
	public String getString() {
		return "simple say hello world!!!";
	}

	@Bean
	@Primary
	public InstanceTest publicInstance(){
		return new InstanceTest("public");
	}
	
	@Bean
	@Qualifier(value="qualifier")
	public InstanceTest qualifierInstance(){
		return new InstanceTest("qualifier");
	}
	
	@Bean
	private InstanceTest privateInstance(@Qualifier("qualifier") InstanceTest instanceTest,
			@Value("#{publicInstance.name}") String name){
		System.out.println(instanceTest.returnIntanceNam());
		System.out.println(name);
		return new InstanceTest("private");
	}
}
