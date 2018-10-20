package org.xzh.springboot.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用java.nio实现Socket网络通信
 * 这种非阻塞：需要考虑“读半包”、“写半包”的问题，本例子没有考虑这些问题
 *
 */
public class MultiplexerTimerServer implements Runnable {
	
	private static final Logger logger = LoggerFactory.getLogger(MultiplexerTimerServer.class);
	
	/**
	 * 多路复用器
	 */
	private Selector selector;
	
	/**
	 * ServerSocket的Chanel类型
	 */
	private ServerSocketChannel serverSocketChannel;
	
	private volatile boolean stop;

	public MultiplexerTimerServer(int port) {
		try{
			//打开selector
			selector = Selector.open();
			//打开ServerSocketChannel
			serverSocketChannel = ServerSocketChannel.open();
			//设置为非阻塞模式
			serverSocketChannel.configureBlocking(false);
			//绑定端口
			serverSocketChannel.socket().bind(new InetSocketAddress(port), 1024);
			//ServerSocketChannel注册到selector中
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		}catch(Exception e){
			logger.error("start serverSocketChannel is error, ",e);
			System.exit(0);
		}
	}
	
	public void stop(){
		this.stop = true;
	}

	@Override
	public void run() {
		while(!stop){
			try{
				//设置休眠时间为1s
				selector.select(1000);
				//获取就绪状态的channel
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> it = selectionKeys.iterator();
				SelectionKey key = null;
				while(it.hasNext()){
					key = it.next();
					it.remove();
					try{
						handleInput(key);
					}catch(Exception e){
						key.cancel();
						if(key.channel() != null){
							key.channel().close();
						}
					}
				}
			}catch(Exception e){
				
			}
		}
	}
	
	private void handleInput(SelectionKey key) throws IOException{
		if(key.isValid()){
			if(key.isAcceptable()){	//判断网络类型为接入
				ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
				//获取接入的SocketChannle
				SocketChannel sc = ssc.accept();
				//设置为非阻塞
				sc.configureBlocking(false);
				sc.register(selector, SelectionKey.OP_READ);
			}
			if(key.isReadable()){	//判断网络类型为读写
				SocketChannel sc = (SocketChannel) key.channel();
				ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
				int readbyte = sc.read(byteBuffer);
				if(readbyte > 0){
					byteBuffer.flip();
					byte[] bytes = new byte[byteBuffer.remaining()];
					byteBuffer.get(bytes);
					String body = new String(bytes, "UTF-8");
					logger.info("the timer server reciver order:{}", body);
					doWrite(sc, new Date().toString());
				}else if(readbyte < 0){
					//关闭链路
					key.cancel();
					sc.close();
				}
			}
		}
	}
	
	private void doWrite(SocketChannel sc, String response) throws IOException {
		byte[] bytes = response.getBytes("UTF-8");
		ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
		byteBuffer.put(bytes);
		byteBuffer.flip();
		sc.write(byteBuffer);
	}

}
