package org.xzh.springboot.activemq;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TopicReceiver {

	private static final Logger logger = LoggerFactory.getLogger(TopicReceiver.class);
	
	private static final String BROKER_URL = "tcp://localhost:61616";
	
	private static final String TOPIC = "topic.activemq";
	
	public void run() throws Exception{
		TopicConnection conn = null;
		TopicSession session = null;
		
		try{
			TopicConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, 
					ActiveMQConnection.DEFAULT_PASSWORD, BROKER_URL);
			
			conn = factory.createTopicConnection();
			conn.start();
			
			session = conn.createTopicSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			
			Topic topic = session.createTopic(TOPIC);
			
			TopicSubscriber subscriber = session.createSubscriber(topic);
			
			subscriber.setMessageListener(new MessageListener() {
				
				@Override
				public void onMessage(Message arg0) {
					if(arg0 != null){
						MapMessage mapMessage = (MapMessage) arg0;
						try{
							String text = mapMessage.getString("text");
							Long time = mapMessage.getLong("time");
							logger.info("text:{}, time:{}", text, time);
						}catch(Exception e){
							logger.error("error ", e);
						}
					}
				}
			});
			
			// 休眠100ms再关闭
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
		new TopicReceiver().run();
	}
}
