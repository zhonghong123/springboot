package org.xzh.springboot.mybatis.mapper;

public interface UserMapper {

	public User getUserById(Integer id);
	
	public Integer saveUser(User user);
}
