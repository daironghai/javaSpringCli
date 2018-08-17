package socketDemo.dSocket;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import socketDemo.dSocket.configurator.CmdHandleConfig;
import socketDemo.dSocket.container.CmdContainer;
import socketDemo.dSocket.data.CmdExecuteClassTemp;
import socketDemo.dSocket.data.CmdExecuteMethodTemp;



/**
 * @Configuration
 * socketDemo.dSocket.configurator.WebSocketConfig.java
 * @author daironghai
 *
 */
public class InitTest {

	public static void main(String[] args) {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/spring-mvc.xml");
		//手动激活
		CmdHandleConfig.getExecuteMap();
		
		Map<String, CmdExecuteClassTemp> executeMap = CmdContainer.getCmdMap();
		
		Iterator<Entry<String, CmdExecuteClassTemp>> eIt = executeMap.entrySet().iterator();
		while(eIt.hasNext()){
			Entry<String, CmdExecuteClassTemp> entry = eIt.next();
			
			String key = entry.getKey();
			CmdExecuteClassTemp ex = entry.getValue();
			
			System.out.println(key);
			
			Map<String, CmdExecuteMethodTemp> methodMap = ex.getCmdMethodMap();
			Iterator<Entry<String, CmdExecuteMethodTemp>> methodIt = methodMap.entrySet().iterator();
			while(methodIt.hasNext()){
				Entry<String, CmdExecuteMethodTemp> mEntry = methodIt.next();
				
				String name = mEntry.getKey();
				CmdExecuteMethodTemp mEx = mEntry.getValue();
				
				System.out.println("\t" + name);
			}
			
		}
		
		System.out.println(1);
	}
	
}
