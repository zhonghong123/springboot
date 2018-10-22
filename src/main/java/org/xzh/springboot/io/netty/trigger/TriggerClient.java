package org.xzh.springboot.io.netty.trigger;

import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

public class TriggerClient {

	private void connect(String host, int port) throws Exception{
		EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
		try{
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(eventLoopGroup)
				.channel(NioSocketChannel.class)
				.option(ChannelOption.TCP_NODELAY, true)
				.handler(new ChannelInitializer<SocketChannel>() {

					@Override
					protected void initChannel(SocketChannel ch)
							throws Exception {
						ch.pipeline().addLast(new IdleStateHandler(0, 0, 5, TimeUnit.SECONDS));
						ch.pipeline().addLast(new StringEncoder());
						ch.pipeline().addLast(new TriggerClientHandler());
					}
				});
			
			ChannelFuture future = bootstrap.connect(host, port).sync();
			future.channel().closeFuture().sync();
		}finally{
			eventLoopGroup.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) throws Exception {
		new TriggerClient().connect("127.0.0.1", 8080);
	}
}
