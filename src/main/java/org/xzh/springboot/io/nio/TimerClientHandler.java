package org.xzh.springboot.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimerClientHandler implements Runnable {
	
	private static final Logger logger = LoggerFactory.getLogger(TimerClientHandler.class);
	
	private String host;
	private int port;
	
	private Selector selector;
	private SocketChannel socketChannel;
	private volatile boolean stop;

	public TimerClientHandler(String host, int port) {
		super();
		this.host = host;
		this.port = port;
		try{
			selector = Selector.open();
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);
		}catch(Exception e){
			logger.error("start timerClientHandler is error, ", e);
		}
	}

	@Override
	public void run() {
		try{
			doConnect();
		}catch(IOException e){
			logger.error("error-->", e);
			System.exit(0);
		}

		while(!stop){
			try{
				selector.select(1000);
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> it = selectionKeys.iterator();
				SelectionKey key = null;
				while(it.hasNext()){
					key = it.next();
					it.remove();
					try{
						doHandler(key);
					}catch(Exception e){
						key.cancel();
						if(key.channel() != null){
							key.channel().close();
						}
					}
				}
			}catch(Exception e){
				logger.error("error-->", e);
				System.exit(1);
			}
		}
		
		if(selector != null){
			try {
				selector.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void doHandler(SelectionKey key) throws IOException {
		if(key.isValid()){
			SocketChannel sc = (SocketChannel) key.channel();
			if(key.isConnectable()){
				sc.register(selector, SelectionKey.OP_READ);
				doWrit(sc);
			}else{
				System.exit(1);
			}
			
			if(key.isReadable()){
				ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
				int readBytes = sc.read(byteBuffer);
				if(readBytes > 0){
					byte[] bytes = new byte[readBytes];
					byteBuffer.get(bytes);
					byteBuffer.flip();
					logger.info("Now is {}", new String(bytes, "UTF-8"));
					this.stop = true;
				}else if(readBytes < 0){
					key.cancel();
					sc.close();
				}
			}
		}
	}
	
	private void doConnect() throws IOException {
		if(socketChannel.connect(new InetSocketAddress(host, port))){
			socketChannel.register(selector, SelectionKey.OP_READ);
			doWrit(socketChannel);
		}else{
			socketChannel.register(selector, SelectionKey.OP_CONNECT);
		}
	}
	
	private void doWrit(SocketChannel sc) throws IOException {
		byte[] bytes = "QUERY TIME ORDER".getBytes("UTF-8");
		ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
		byteBuffer.put(bytes);
		byteBuffer.flip();
		sc.write(byteBuffer);
		if(!byteBuffer.hasRemaining()){
			logger.info("send order to server success");
		}
	}

}
