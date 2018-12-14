package org.xzh.springboot.sort;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HannuotaMain {
	
	private static final Logger logger = LoggerFactory.getLogger(HannuotaMain.class);

	public static void move(int disk, String from, String temp, String to){
		if(disk == 1){
			logger.info("将盘子{}从塔座{}移到塔座{}", disk, from, to);
		}else{
			move(disk-1, from, to, temp);
			logger.info("将盘子{}从塔座{}移到塔座{}", disk, from, to);
			move(disk-1, temp, from, to);
		}
	};
	
	public static void main(String[] args) {
		move(4, "A", "B", "c");
	}
}
