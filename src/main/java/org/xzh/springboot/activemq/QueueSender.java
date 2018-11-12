package org.xzh.springboot.activemq;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueueSender {

	private static final Logger logger = LoggerFactory.getLogger(QueueSender.class);
	
	private static final String BROKER_URL = "tcp://localhost:61616";
	
	private static final String DESTATION = "queue.mq.msg";
	
	private void sender(QueueSession session, javax.jms.QueueSender sender) throws JMSException{
		for(int i=0; i<5; i++){
			String text = "hhhh"+i;
			MapMessage mapMessage = session.createMapMessage();
			mapMessage.setString("text", text);
			mapMessage.setLong("time", System.currentTimeMillis());
			sender.send(mapMessage);
			logger.info(text);
		}
	}
	
	public void run() throws JMSException{
		QueueConnection conn = null;
		QueueSession session = null;
		
		try{
			QueueConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, 
					ActiveMQConnection.DEFAULT_PASSWORD, BROKER_URL);
			conn = factory.createQueueConnection();
			conn.start();
			
			session = conn.createQueueSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			
			Queue queue = session.createQueue(DESTATION);
			
			javax.jms.QueueSender sender = session.createSender(queue);
			sender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			
			sender(session, sender);
			
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
		QueueSender sender = new QueueSender();
		sender.run();
	}
}
