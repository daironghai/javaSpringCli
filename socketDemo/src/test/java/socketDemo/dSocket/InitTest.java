package socketDemo.dSocket;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import socketDemo.dSocket.configurator.CmdHandleConfig;
import socketDemo.dSocket.data.ExecuteTemp;



/**
 * @Configuration
 * socketDemo.dSocket.configurator.WebSocketConfig.java
 * @author daironghai
 *
 */
public class InitTest {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		
//		Map<String, Object> map = ctx.getBeansWithAnnotation(DsocketNode.class);
//		Iterator<Entry<String, Object>> scanIt = map.entrySet().iterator();
//		while(scanIt.hasNext()){
//			Entry<String, Object> entry = scanIt.next();
//			System.out.println(entry.getKey());
//		}
		
		Map<String, ExecuteTemp> executeMap = CmdHandleConfig.getExecuteMap();
		Iterator<Entry<String, ExecuteTemp>> eIt = executeMap.entrySet().iterator();
		while(eIt.hasNext()){
			Entry<String, ExecuteTemp> entry = eIt.next();
			
			String key = entry.getKey();
			ExecuteTemp ex = entry.getValue();
			
			System.out.println(key);
			System.out.println(ex.getClas().getName());
			System.out.println(ex.getMethodName());
			System.out.println(ex.getSingleObj());
			System.out.println("===========");
		}
		
		
	}
	
}
