package org.xzh.springboot.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MySqlSessionFactory {

	private static SqlSessionFactory sqlSessionFactory;
	private static AtomicInteger init = new AtomicInteger(0);
	
	public static SqlSessionFactory getSqlSessionFactory(){
		if(sqlSessionFactory == null){
			synchronized (init) {
				if(sqlSessionFactory == null){
					String resource = "mybatis-config.xml";
					InputStream inputStream;
					try {
						inputStream = Resources.getResourceAsStream(resource);
						sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
					} catch (IOException e) {
					}
				}
			}
		}
		return sqlSessionFactory;
	}
}
