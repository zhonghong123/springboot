package org.xzh.springboot.io.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TimeClient {

	private void connect(String host, int port) throws Exception{
		//创建NIO线程组，用于网络读写操作
		EventLoopGroup worker = new NioEventLoopGroup();
		try{
			//Bootstrap启动类，降低网络编程复杂度
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(worker)
				.channel(NioSocketChannel.class)	//设置channel通道
				.option(ChannelOption.TCP_NODELAY, true)
				.handler(new ChannelInitializer<SocketChannel>() {

					@Override
					protected void initChannel(SocketChannel arg0)
							throws Exception {
						arg0.pipeline().addLast(new TimeClientHandler());
						
					}
				});
			
			ChannelFuture f = bootstrap.connect(host, port).sync();
			
			f.channel().close().sync();
		}finally{
			worker.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) throws Exception {
		new TimeClient().connect("127.0.0.1", 8080);
	}
}
