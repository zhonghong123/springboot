package org.xzh.springboot.io.nio;

public class TimerClient {

	public static void main(String[] args) {
		String host = "127.0.0.1";
		int port = 8080;
		new Thread(new TimerClientHandler(host, port)).start();
	}
}
