package org.xzh.springboot.io.netty.trigger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TriggerServerHandler extends ChannelHandlerAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(TriggerServerHandler.class);

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		String body = (String) msg;
		logger.info("ping-->{}", body);
	}
}
