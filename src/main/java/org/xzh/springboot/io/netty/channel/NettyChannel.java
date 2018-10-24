package org.xzh.springboot.io.netty.channel;

import java.nio.charset.Charset;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.xzh.springboot.io.netty.trigger.TriggerClientHandler;

public class NettyChannel {

	private static ObjectPool<Channel> pool = null;
	
	public void init(){
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMaxIdle(2);
		config.setMaxWaitMillis(60000);
		config.setMinIdle(0);
		pool = new GenericObjectPool<Channel>(new NettyChannelPoolObjectFactroy(), config);
	}
	
	public void send(String s) throws Exception{
		if(pool == null){
			init();
		}
		
		Channel channel = null;
		try {
			channel = pool.borrowObject();
			channel.writeAndFlush(s);
		} finally{
			if(channel != null){
				pool.returnObject(channel);
			}
		}
	}
	
	public class NettyChannelPoolObjectFactroy implements PooledObjectFactory<Channel> {

		@Override
		public PooledObject<Channel> makeObject() throws Exception {
			EventLoopGroup worker = new NioEventLoopGroup();
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(worker).channel(NioSocketChannel.class)
				.option(ChannelOption.SO_KEEPALIVE, true)
				.option(ChannelOption.TCP_NODELAY, true)
				.handler(new ChannelInitializer<NioSocketChannel>() {

					@Override
					protected void initChannel(NioSocketChannel ch)
							throws Exception {
						ch.pipeline().addLast("encoder", new StringEncoder());
						ch.pipeline().addLast("decoder", new StringDecoder());
						ch.pipeline().addLast("idle", new IdleStateHandler(0, 0, 5, TimeUnit.SECONDS));
						ch.pipeline().addLast("handler", new TriggerClientHandler());
					}
				});
			ChannelFuture f = bootstrap.connect("127.0.0.1", 9080);
			return new DefaultPooledObject<Channel>(f.channel());
		}

		@Override
		public void destroyObject(PooledObject<Channel> p) throws Exception {
			p.getObject().close();
		}

		@Override
		public boolean validateObject(PooledObject<Channel> p) {
			if(p.getObject().isActive()){
				return true;
			}
			return false;
		}

		@Override
		public void activateObject(PooledObject<Channel> p) throws Exception {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void passivateObject(PooledObject<Channel> p) throws Exception {
			// TODO Auto-generated method stub
			
		}
		
	}
}
