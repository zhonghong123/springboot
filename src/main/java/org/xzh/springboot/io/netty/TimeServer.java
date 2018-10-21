package org.xzh.springboot.io.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class TimeServer {

	private void bind(int port) throws InterruptedException{
		//配置NIO服务端的线程组，专门用于网络事件处理
		//一个用于服务器接受客户端连接，另一个用于进行SocketChannel的网络读写。
		NioEventLoopGroup boss = new NioEventLoopGroup();
		NioEventLoopGroup worker = new NioEventLoopGroup();
		try{
			//ServerBootstrap是netty用于启动NIO服务端的辅助启动类，其目的是降低服务端的开发复杂度
			ServerBootstrap bootstrap = new ServerBootstrap();
			
			bootstrap.group(boss, worker)
				.channel(NioServerSocketChannel.class)	//设置channel通道
				.option(ChannelOption.SO_BACKLOG, 1024)	//配置通道的信息
				.childHandler(new ChildChannelHandler());	//设置IO事件处理类
			
			//绑定端口，调用阻塞方法，等待绑定完成
			//返回ChannelFuture对象，用于异步操作的通知回调
			ChannelFuture future = bootstrap.bind(port).sync();	
			
			//调用同步方法，等待服务器链路关闭后main函数才推出
			future.channel().closeFuture().sync();
		}finally{
			//优雅关闭
			boss.shutdownGracefully();
			worker.shutdownGracefully();
		}
	}
	
	public class ChildChannelHandler extends ChannelInitializer<SocketChannel>{

		@Override
		protected void initChannel(SocketChannel arg0) throws Exception {
			//解决粘包问题
			//arg0.pipeline().addLast(new LineBasedFrameDecoder(1024));
			//字符串解码器，在handler收到就是String形式
			//arg0.pipeline().addLast(new StringDecoder());
			//设置handler
			arg0.pipeline().addLast(new TimeServerHandler());
		}
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		new TimeServer().bind(8080);
	}
}
