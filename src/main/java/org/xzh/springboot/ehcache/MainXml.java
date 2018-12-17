package org.xzh.springboot.ehcache;

import java.net.URL;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.Configuration;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.xml.XmlConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainXml {
	
	private static final Logger logger = LoggerFactory.getLogger(MainXml.class);
	
	public void init(){
		final URL url = this.getClass().getResource("/ehcache.xml");
		Configuration xmlConfig = new XmlConfiguration(url);
		CacheManager cacheManager = CacheManagerBuilder.newCacheManager(xmlConfig);
		
		Cache<String, String> foo = cacheManager.getCache("foo", String.class, String.class);
		foo.put("key1", "value1");
		logger.info(foo.get("key1"));
	}

	public static void main(String[] args) {
		new MainXml().init();
	}
}
