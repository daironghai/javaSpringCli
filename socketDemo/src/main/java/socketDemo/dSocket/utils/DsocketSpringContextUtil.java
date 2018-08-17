package socketDemo.dSocket.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 运行时获取spring上下文工具
 * @author Administrator
 *
 */
@Component
public class DsocketSpringContextUtil implements ApplicationContextAware {

	
	private static ApplicationContext applicationContext;
	
	
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		DsocketSpringContextUtil.applicationContext = applicationContext;
	}
	
	
	public static ApplicationContext getApplicationContext(){
		return DsocketSpringContextUtil.applicationContext;
	}
	
	

}
