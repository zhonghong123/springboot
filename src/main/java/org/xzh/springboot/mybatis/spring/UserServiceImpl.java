package org.xzh.springboot.mybatis.spring;

import org.springframework.transaction.annotation.Transactional;
import org.xzh.springboot.mybatis.mapper.User;
import org.xzh.springboot.mybatis.mapper.UserMapper;

public class UserServiceImpl {

	private UserMapper userMapper;

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}
	
	public User getUser(Integer id){
		return userMapper.getUserById(id);
	}
	
	@Transactional(rollbackFor=Exception.class, readOnly=false)
	public void saveUser(User user){
		userMapper.saveUser(user);
		throw new RuntimeException("事物回滚");
	}
}
