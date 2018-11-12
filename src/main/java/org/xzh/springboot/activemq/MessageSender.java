package org.xzh.springboot.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageSender {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageSender.class);

	/**
	 * 发送次数
	 */
	public static final Integer SEND_NUM = 5;
	
	public static final String BROKER_URL = "tcp://localhost:61616";
	
	public static final String DESTINATION = "segde.mq.queue";
	
	public void sendMessage(Session session, MessageProducer producer) throws JMSException{
		for(int i=0; i<SEND_NUM; i++){
			String text = "发送第"+i+"条记录";
			TextMessage textMessage = session.createTextMessage(text);
			producer.send(textMessage);
			logger.info("send: {}", text);
		}
	}
	
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
			MessageProducer producer = session.createProducer(destination);
			// 设置持久化模式
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			
			//发送消息
			sendMessage(session, producer);
			
			// 提交会话
			session.commit();
		}catch(Exception e){
			throw e;
		}finally{
			if(session != null){
				session.close();
			}
			if(conn != null){
				conn.close();
			}
		}
	}
	
	public static void main(String[] args) throws JMSException {
		MessageSender sender = new MessageSender();
		sender.run();
	}
}
