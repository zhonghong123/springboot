package org.xzh.springboot.ehcache;

import java.io.File;

import org.ehcache.Cache;
import org.ehcache.PersistentCacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersistentCacheManagerMain {
	
	private static final Logger logger = LoggerFactory.getLogger(PersistentCacheManagerMain.class);

	public static void main(String[] args) {
		PersistentCacheManager persistentCacheManager = CacheManagerBuilder
				.newCacheManagerBuilder()
				.with(CacheManagerBuilder.persistence("E:" + File.separator + "myData")) 
				.withCache("threeTieredCache", 
						CacheConfigurationBuilder.newCacheConfigurationBuilder(Integer.class, String.class, 
								ResourcePoolsBuilder.newResourcePoolsBuilder()
								.heap(10, EntryUnit.ENTRIES)	//堆
								.offheap(1, MemoryUnit.MB)	//堆外
								.disk(20, MemoryUnit.GB)	//磁盘
							)
					).build(true);
		
		Cache<Integer, String> threeTieredCache = persistentCacheManager
				.getCache("threeTieredCache", Integer.class, String.class);
		
		//写
		for(int i=0; i<20000; i++){
			threeTieredCache.put(i, "$"+i);
		}
		
		//读
		for(int i=0; i<20000; i++){
			logger.info(threeTieredCache.get(i));
		}
		
		persistentCacheManager.close();
	}
}
