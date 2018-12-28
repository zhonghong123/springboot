package org.xzh.springboot.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xzh.springboot.mybatis.mapper.User;
import org.xzh.springboot.mybatis.mapper.UserMapper;

public class UserMapperTest {
	
	private static final Logger logger = LoggerFactory.getLogger(UserMapperTest.class);

	@Test
	public void getUserById1(){
		SqlSessionFactory sqlSessionFactory = MySqlSessionFactory.getSqlSessionFactory();
		SqlSession session = sqlSessionFactory.openSession();
		try{
			User user = session.selectOne("org.xzh.springboot.mybatis.mapper.UserMapper.getUserById", 1);
			logger.info("user-->{}", user);
		}finally{
			session.close();
		}
	}
	
	@Test
	public void getUserById2(){
		SqlSessionFactory sqlSessionFactory = MySqlSessionFactory.getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			User user = userMapper.getUserById(1);
			logger.info("user-->{}", user);
		}finally{
			sqlSession.close();
		}
	}
	
	@Test
	public void saveUserTest(){
		SqlSessionFactory sqlSessionFactory = MySqlSessionFactory.getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			User user = new User();
			user.setId(3);
			user.setUsername("wangwu");
			user.setAge(13);
			
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			userMapper.saveUser(user);
			sqlSession.commit();
		}catch(Exception e){
			sqlSession.rollback();
		}finally{
			sqlSession.close();
		}
	}
}
