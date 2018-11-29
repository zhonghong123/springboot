package org.xzh.springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/index")
public class IndexController {
	
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

	@RequestMapping("/toIndex")
	public String index(Model model){
		logger.info("index...");
		model.addAttribute("text", "hello world");
		return "index";
	}
	
	@RequestMapping("/admin/{path}")
	public String admin(@PathVariable String path){
		logger.info("path --> {}", path);
		return "admin/"+path;
	}
	
	@RequestMapping("/front/{path}")
	public String front(@PathVariable String path){
		logger.info("front path --> {}", path);
		return "front/"+path;
	}
}
