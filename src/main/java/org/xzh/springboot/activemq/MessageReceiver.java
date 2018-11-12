package org.xzh.springboot.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageReceiver {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageReceiver.class);

	// tcp 地址
	public static final String BROKER_URL = "tcp://localhost:61616";
	
	// 目标，在ActiveMQ管理员控制台创建 http://localhost:8161/admin/queues.jsp
	public static final String DESTINATION = "segde.mq.queue";
	
	public void run() throws JMSException{
		Connection conn = null;
		Session session = null;
		
		try{
			// 创建链接工厂
			ConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, 
					ActiveMQConnection.DEFAULT_PASSWORD, BROKER_URL);
			// 通过工厂创建一个连接
			conn = factory.createConnection();
			// 启动连接
			conn.start();
			// 创建一个session会话
			session = conn.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			// 创建一个消息队列
			Destination destination = session.createQueue(DESTINATION);
			// 创建消息制作者
			MessageConsumer consumer = session.createConsumer(destination);
			
			while(true){
				 // 接收数据的时间（等待） 100 ms
				Message message = consumer.receive(1000*100);
				
				TextMessage textMessage = (TextMessage) message;
				if(textMessage != null){
					logger.info(textMessage.getText());
				}
			}
		}catch(Exception e){
			throw e;
		}finally{
			// 关闭释放资源
			if(conn != null){
				conn.close();
			}
			
			if(session != null){
				session.close();
			}
		}
	}
	
	public static void main(String[] args) throws JMSException {
		MessageReceiver receiver = new MessageReceiver();
		receiver.run();
	}
}
