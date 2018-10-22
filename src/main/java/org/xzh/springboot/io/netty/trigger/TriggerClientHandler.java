package org.xzh.springboot.io.netty.trigger;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TriggerClientHandler extends ChannelHandlerAdapter {

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {
		String ping = "000000";
		ctx.writeAndFlush(ping);
	}
}
