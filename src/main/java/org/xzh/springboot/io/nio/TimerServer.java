package org.xzh.springboot.io.nio;

public class TimerServer {

	public static void main(String[] args) {
		int port = 8080;
		
		MultiplexerTimerServer mts = new MultiplexerTimerServer(port);
		new Thread(mts).start();
	}
}
