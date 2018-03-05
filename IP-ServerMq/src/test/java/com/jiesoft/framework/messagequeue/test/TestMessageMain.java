package com.jiesoft.framework.messagequeue.test;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * 
 *
 * @date 2017年5月25日 下午5:51:19
 * @author jie
 * @Description: 测试消息队列的压力和稳定性
 *
 */
public class TestMessageMain {
	
	static Logger logger = LoggerFactory.getLogger(TestMessageMain.class);
	
	static ClassPathXmlApplicationContext context = new  ClassPathXmlApplicationContext(
			"config/spring/application-busiservice-context.xml");
	
//	public static void main(String[] args) {
//        new Thread(new Runnable() {
// 
//            @Override
//            public void run() {
//                while (true) {
//                    System.out.println("数据");
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
//    }
	
	
    public static void main(String[] args) {  
    	
    	
    	JmsTemplate jmsTemplate = (JmsTemplate) context.getBean("activeMqJmsTemplate1");
    	Destination queueDestination = (Destination) context.getBean("queueDestination");
		jmsTemplate.send(queueDestination,new MessageCreator() {
			public Message createMessage(javax.jms.Session session) throws JMSException {
				return session.createTextMessage("发消息了");
			}
		});
		TextMessage tm = (TextMessage) jmsTemplate.receive("ip.notice.sendnotice.queue");
		try {
			System.out.println(tm.getText().toString());
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }  
}
