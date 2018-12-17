package org.xzh.springboot.ehcache;

import org.ehcache.UserManagedCache;
import org.ehcache.config.builders.UserManagedCacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserManagedCacheMain {
	
	private static final Logger logger = LoggerFactory.getLogger(UserManagedCacheMain.class);

	public static void main(String[] args) {
		//创建UserManagedCache
		UserManagedCache<Integer, String> userManagedCache = UserManagedCacheBuilder
				.newUserManagedCacheBuilder(Integer.class, String.class).build();
		//初始化UserManagedCache
		userManagedCache.init();
		
		//设置缓存
		userManagedCache.put(1, "kkk");
		logger.info(userManagedCache.get(1));
		
		//关闭userManagedCache
		userManagedCache.close();
	}
}
