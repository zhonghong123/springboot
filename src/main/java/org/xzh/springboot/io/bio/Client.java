package org.xzh.springboot.io.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client {
	
	private static final Logger logger = LoggerFactory.getLogger(Client.class);

	public void run(){
		Socket socket = null;
		try {
			socket = new Socket("127.0.0.1", 9900);
			//InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			os.write(Thread.currentThread().getName().getBytes("UTF-8"));
			
			/*byte[] bytes = new byte[1024];
			is.read(bytes, 0, 1024);
			logger.info(new String(bytes, "UTF-8"));*/
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(socket != null){
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
