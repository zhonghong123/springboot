package org.xzh.springboot.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.xzh.springboot.mybatis.mapper.User;
import org.xzh.springboot.mybatis.mapper.UserMapper;

public class MainTest {

	public static void main(String[] args) {
		SqlSessionFactory sqlSessionFactory = MySqlSessionFactory.getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			User user = sqlSession.selectOne("org.xzh.springboot.mybatis.mapper.UserMapper.getUserById", 1);
			System.out.println(user);
			
			UserMapper mapper = sqlSession.getMapper(UserMapper.class);
			User user2 = mapper.getUserById(1);
			System.out.println(user2);
		}finally{
			sqlSession.close();
		}
	}
}
