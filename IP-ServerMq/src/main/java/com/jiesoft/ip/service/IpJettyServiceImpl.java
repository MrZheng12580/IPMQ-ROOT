package com.jiesoft.ip.service;

import java.io.IOException;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.jiesoft.framework.server.JettyServiceInterface;

/**
 * 
 *
 * @date 2017年11月20日 下午2:48:55
 * @author jie
 * @Description: jetty实现类
 *
 */
//@Service
public class IpJettyServiceImpl implements JettyServiceInterface{

	
	@Resource(name="activeMqJmsTemplate1")
	private JmsTemplate jmsTemplate;
//	@Resource(name="queueDestination")
//	private Destination destination;
	
	@Override
	public void invoke(Request baseRequest, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String result = null;
//		String method = request.getParameter("method");
//		
//		if(logger.isDebugEnabled()){
//			logger.debug("invoke method:{}", method);
//		}
		
//		jmsTemplate.send(destination,new MessageCreator() {
//			public Message createMessage(javax.jms.Session session) throws JMSException {
//				return session.createTextMessage("发消息了");
//			}
//		});
		jmsTemplate.send("ip.notice.sendnotice.queue",new MessageCreator() {
			public Message createMessage(javax.jms.Session session) throws JMSException {
				return session.createTextMessage("获取一个外网ip给我！");
			}
		});
		
		//拿队列中的消息，也就是有可能得到的是以前的消息
		TextMessage tm = (TextMessage) jmsTemplate.receive("ip.showIp.sendnotice.queue");
		try {
			System.out.println(tm.getText().toString());
			result = tm.getText().toString();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "获取失败！";
		}
		
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(result);
		response.getWriter().close();
	}
	
}
