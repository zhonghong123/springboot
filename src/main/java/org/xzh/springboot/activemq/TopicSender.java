package org.xzh.springboot.activemq;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TopicSender {

	private static final Logger logger = LoggerFactory.getLogger(TopicSender.class);
	
	private static final String BROKER_URL = "tcp://localhost:61616";
	
	private static final String TOPIC = "topic.activemq";
	
	private void send(TopicSession session, TopicPublisher publisher) throws JMSException{
		for(int i = 0; i < 5; i++){
			MapMessage mapMessage = session.createMapMessage();
			String text = "text"+i;
			mapMessage.setString("text", text);
			mapMessage.setLong("time", System.currentTimeMillis());
			publisher.send(mapMessage);
			logger.info("text:{}", text);
		}
	}
	
	public void run() throws JMSException{
		TopicConnection conn = null;
		TopicSession session = null;
		
		try{
			TopicConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, 
					ActiveMQConnection.DEFAULT_PASSWORD, BROKER_URL);
			
			conn = factory.createTopicConnection();
			conn.start();
			
			session = conn.createTopicSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			
			Topic topic = session.createTopic(TOPIC);
			
			TopicPublisher publisher = session.createPublisher(topic);
			publisher.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			
			send(session, publisher);
			
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
		new TopicSender().run();
	}
}
