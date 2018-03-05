package com.jiesoft.framework.server;

//import org.eclipse.jetty.server.Server;
//import org.eclipse.jetty.util.resource.Resource;
//import org.eclipse.jetty.xml.XmlConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * jettyServer
 *
 * @date 2016年9月8日 下午3:40:53
 * @author jie
 *
 */
public class JettyServerMain {

	private static final Logger logger = LoggerFactory.getLogger(JettyServerMain.class);

	private static final String CONFIG_FILE_PATH = "config/spring/application-jettyserver-context.xml";

	/**
	 * 启动服务
	 * @date 2015年12月11日 下午3:01:20
	 * @author jie
	 * @Description: 启动服务
	 * @param args
	 * @throws Exception
	 *
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		
		String configFilePath = null;
		if(args.length>0 && args[0] != null && !"".equals(args[0])){
			configFilePath = args[0]; 
		}else{
			configFilePath = CONFIG_FILE_PATH;
		}
		
		logger.info("configFilePath:{}",configFilePath);
		
		logger.info("JettyServer init start......");
		
		new ClassPathXmlApplicationContext(configFilePath);  
		
//		Resource serverXml = Resource.newSystemResource(configFilePath);
//		XmlConfiguration configuration = new XmlConfiguration(serverXml.getInputStream());
//		Server server = (Server) configuration.configure();
//		server.start();
//		server.join();

		logger.info("JettyServer init success......");
	}
}
