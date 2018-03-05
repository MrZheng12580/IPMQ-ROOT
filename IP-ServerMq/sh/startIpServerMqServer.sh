if [ ` ps -ef |grep IpServerMqServer | grep -v grep | wc -l ` -eq 0 ]
then
        INFLIB=./lib:.:/software/jdk/jdk1.8.0_144
				tag=IpServerMqServer;
				exe_option="-Xms256m -Xmx256m -Xmn64m -XX:PermSize=64m -XX:MaxPermSize=64m -Djava.awt.headless=true -XX:+HeapDumpOnOutOfMemoryError  -Djava.ext.dirs=$INFLIB -Dfile.encoding=GBK  -Duser.language=zh -Duser.region=CN -Dcom.sun.management.jmxremote.port=20287 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false -Dlog4j.configuration=file:./classes/config/properties/log4j.properties"
				
				nohup java -cp :$tag:./classes $exe_option com.jiesoft.framework.server.JettyServerMain config/spring/application-jettyserver-context.xml >./logs/IpServerMqSerSTD.log 2>./logs/IpServerMqSerERR.log &

else    
	echo "\nWarning: can not startup IpServerMqServer program,it is running.........!\n" 
fi
