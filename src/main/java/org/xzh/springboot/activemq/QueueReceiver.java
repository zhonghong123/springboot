package org.xzh.springboot.activemq;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueueReceiver {

	private static final Logger logger = LoggerFactory.getLogger(QueueReceiver.class);
	
	private static final String BROKER_URL = "tcp://localhost:61616";
	
	private static final String DESTATION = "queue.mq.msg";
	
	public void run() throws Exception{
		QueueConnection conn = null;
		QueueSession session = null;
		
		try{
			QueueConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, 
					ActiveMQConnection.DEFAULT_PASSWORD, BROKER_URL);
			conn = factory.createQueueConnection();
			conn.start();
			
			session = conn.createQueueSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			
			Queue queue = session.createQueue(DESTATION);
			javax.jms.QueueReceiver receiver = session.createReceiver(queue);
			
			receiver.setMessageListener(new MessageListener() {
				
				@Override
				public void onMessage(Message paramMessage) {
					if(paramMessage != null){
						MapMessage mapMessage = (MapMessage) paramMessage;
						try{
							String text = mapMessage.getString("text");
							Long time = mapMessage.getLong("time");
							logger.info("text:{}, time:{}", text, time);
						}catch(Exception e){
							logger.error("error", e);
						}
					}
				}
			});
			
			Thread.sleep(1000 * 100);
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
	
	public static void main(String[] args) throws Exception {
		QueueReceiver receiver = new QueueReceiver();
		receiver.run();
	}
}
