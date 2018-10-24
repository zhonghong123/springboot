package org.xzh.springboot.io.netty.trigger;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

public class TriggerServer {

	private void bind(int port) throws Exception{
		EventLoopGroup boss = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();
		try{
			ServerBootstrap server = new ServerBootstrap();
			server.group(boss, worker)
				.channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 1024)
				.childHandler(new ChannelInitializer<SocketChannel>() {

					@Override
					protected void initChannel(SocketChannel ch)
							throws Exception {
						//ch.pipeline().addLast(new IdleStateHandler(readerIdleTime, writerIdleTime, allIdleTime, TimeUnit.SECONDS));
						ch.pipeline().addLast(new StringDecoder());
						ch.pipeline().addLast(new TriggerServerHandler());
					}
				});
			
			ChannelFuture future = server.bind(port).sync();
			
			future.channel().closeFuture().sync();
		}finally{
			boss.shutdownGracefully();
			worker.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) {
		try {
			new TriggerServer().bind(9080);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
