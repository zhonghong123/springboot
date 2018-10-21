package org.xzh.springboot.io.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeClientHandler extends ChannelHandlerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(TimeClientHandler.class);
	
	private ByteBuf firstMessage = null;

	public TimeClientHandler() {
		try{
			String message = "hello world";
			byte[] bytes = message.getBytes("UTF-8");
			firstMessage = Unpooled.buffer(bytes.length);
			firstMessage.writeBytes(bytes);
		}catch(Exception e){
			logger.error("error --> ", e);
		}
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(firstMessage);
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		ByteBuf byteBuf = (ByteBuf) msg;
		byte[] bytes = new byte[byteBuf.readableBytes()];
		byteBuf.readBytes(bytes);
		String body = new String(bytes, "UTF-8");
		logger.info("the revice the time is {}", body);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
	}
	
}
