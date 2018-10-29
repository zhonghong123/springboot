package org.xzh.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xzh.springboot.io.netty.channel.NettyChannel;

@Controller
@RequestMapping("/netty")
public class NettyController {

	@RequestMapping("/hello")
	@ResponseBody
	public String hello(String name){
		try {
			new NettyChannel().send(name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "hello";
	}
}