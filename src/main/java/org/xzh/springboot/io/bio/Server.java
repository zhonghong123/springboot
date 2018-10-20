package org.xzh.springboot.io.bio;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {
	
	private static final Logger logger = LoggerFactory.getLogger(Server.class);

	private void run() {
		int port = 9900;
		ServerSocket server = null;
		try{
			server = new ServerSocket(port);
			while(true){
				Socket socket = server.accept();
				new Thread(new SocketHandler(socket)).start();
			}
		}catch(Exception e){
			
		}
	}
	
	public class SocketHandler implements Runnable{
		
		private Socket socket = null;
		
		public SocketHandler(Socket socket){
			this.socket = socket;
		}

		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName()+" : "+socket.getInetAddress().getHostAddress());
		}
		
	}
	
	public static void main(String[] args) {
		Server server = new Server();
		server.run();
	}
}
