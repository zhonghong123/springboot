package org.xzh.springboot.ehcache;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainTest {
	
	private static final Logger logger = LoggerFactory.getLogger(MainTest.class);

	public static void main(String[] args) {
		//创建cacheManager，在创建cacheManager的同时创建了cache
		CacheManager cacheManager = CacheManagerBuilder
				.newCacheManagerBuilder()
				.withCache(
						"preConfigured",
						CacheConfigurationBuilder.newCacheConfigurationBuilder(
								Long.class, String.class,
								ResourcePoolsBuilder.heap(100)).build())
				.build(true);
		cacheManager.init();

		//通过alias获取cache
		Cache<Long, String> preConfigured = cacheManager.getCache(
				"preConfigured", Long.class, String.class);

		//通过cacheManager创建新的cache
		Cache<Long, String> myCache = cacheManager.createCache(
				"myCache",
				CacheConfigurationBuilder.newCacheConfigurationBuilder(
						Long.class, String.class,
						ResourcePoolsBuilder.heap(100)).build());

		//在cache加入缓存内容
		myCache.put(1L, "da one!");
		//获取缓存内容
		String value = myCache.get(1L);
		
		logger.info(value);
		
		//通过alias删除cache
		cacheManager.removeCache("preConfigured");

		cacheManager.close();
	}
}
