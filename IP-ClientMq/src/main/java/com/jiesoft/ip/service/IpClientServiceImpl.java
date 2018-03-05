package com.jiesoft.ip.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;


/**
 * 
 *
 * @date 2017年11月20日 下午2:48:55
 * @author jie
 * @Description: jetty实现类
 *
 */
@Service
public class IpClientServiceImpl implements MessageListener{

	
	@Resource(name="activeMqJmsTemplate1")
	private JmsTemplate jmsTemplate;
	
	public void receive() {

		TextMessage tm = (TextMessage) jmsTemplate.receive("ip.notice.sendnotice.queue");
		try {
			System.out.println(tm.getText().toString());
		} catch (JMSException e) {
			e.printStackTrace();
		}
		final String ip = getExtranetIp();
		jmsTemplate.send("ip.showIp.sendnotice.queue",new MessageCreator() {
			public Message createMessage(javax.jms.Session session) throws JMSException {
				return session.createTextMessage("外网ip："+ip);
			}
		});
	}
	
	private String getExtranetIp() {
		String ip = "";
		String url = "http://ip.chinaz.com";
		String urlParam = "";
		String regex = "\\<dd class\\=\"fz24\">(.*?)\\<\\/dd>";
		try {
			String html = request(url, urlParam);
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(html);
			if(m.find()){
				String ipstr = m.group(1);
				ip = ipstr;
			}
		} catch (Exception e) {
			//logger.error("getExtranetIp error url:{}, urlParam:{}, e:{}", url, urlParam, e);
		}
		return ip;
	}

	private static String request(String url, String urlParam) throws Exception {
		// 创建HttpClient实例
		String getURL = url + urlParam;
		//String result = null;
		StringBuilder result = new StringBuilder();
		URL getUrl = null;
		HttpURLConnection connection = null;
		BufferedReader reader = null;
		try {
			getUrl = new URL(getURL);
			connection = (HttpURLConnection) getUrl.openConnection();
			connection.connect();
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String lines;

			while ((lines = reader.readLine()) != null) {
				result.append(lines+"\r\n");
			}
		} catch (IOException e) {
			//logger.error("connect error serverUrl:{}, urlParam:{}", url, urlParam, e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					//logger.error("close reader error serverUrl:{}, urlParam:{}", url, urlParam, e);
				}
			}

			if (connection != null) {
				connection.disconnect();
			}
		}
		return result.toString();
	}

	/* (non-Javadoc)
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	@Override
	public void onMessage(Message message) {
		TextMessage tm = (TextMessage) message;
		try {
			System.out.println(tm.getText().toString());
		} catch (JMSException e) {
			e.printStackTrace();
		}
		final String ip = getExtranetIp();
		jmsTemplate.send("ip.showIp.sendnotice.queue",new MessageCreator() {
			public Message createMessage(javax.jms.Session session) throws JMSException {
				return session.createTextMessage("外网ip："+ip);
			}
		});
		
	}
	
}
