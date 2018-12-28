package org.xzh.springboot.mybatis;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xzh.springboot.mybatis.mapper.User;
import org.xzh.springboot.mybatis.spring.UserServiceImpl;

public class UserServiceImplTest {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImplTest.class);
	
	private static final String SPRING_MYBATIS_CONFIG = "classpath*:mybatis-spring.xml";

	@Test
	public void getUserTest(){
		ApplicationContext context = new ClassPathXmlApplicationContext(SPRING_MYBATIS_CONFIG);
		UserServiceImpl userServiceImpl = (UserServiceImpl) context.getBean("userServiceImpl");
		User user = userServiceImpl.getUser(1);
		logger.info("user-->{}", user);
	}
	
	@Test
	public void saveUserTest(){
		ApplicationContext context = new ClassPathXmlApplicationContext(SPRING_MYBATIS_CONFIG);
		UserServiceImpl userServiceImpl = (UserServiceImpl) context.getBean("userServiceImpl");
		
		User user = new User();
		user.setId(6);
		user.setUsername("zhaoliu3");
		user.setAge(15);
		userServiceImpl.saveUser(user);
	}
}
