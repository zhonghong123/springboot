package org.xzh.springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/index")
public class IndexController {
	
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

	@RequestMapping("/toIndex")
	public String index(){
		logger.info("index...");
		return "index";
	}
}
