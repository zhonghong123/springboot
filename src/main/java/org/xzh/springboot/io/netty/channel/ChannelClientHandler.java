package org.xzh.springboot.io.netty.channel;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ChannelClientHandler extends ChannelHandlerAdapter {

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {
		String ping = "000000";
		ctx.writeAndFlush(ping);
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// TODO Auto-generated method stub
		super.channelRead(ctx, msg);
	}
}
