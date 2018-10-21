package org.xzh.springboot.io.netty;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeServerHandler extends ChannelHandlerAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(TimeServerHandler.class);

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		ByteBuf byteBuf = (ByteBuf) msg;
		byte[] bytes = new byte[byteBuf.readableBytes()];
		byteBuf.readBytes(bytes);
		String body = new String(bytes, "UTF-8");
		logger.info("the query order is {}", body);
		String response = new Date(System.currentTimeMillis()).toString();
		ByteBuf resp = Unpooled.copiedBuffer(response.getBytes("UTF-8"));
		ctx.write(resp);
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
	}
}
