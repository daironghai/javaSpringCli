package socketDemo.dSocket.utils;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

@Component
public class DsocketServletContextUtil implements ServletContextAware {

	
	private static ServletContext servletContext;
	
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		DsocketServletContextUtil.servletContext = servletContext;
	}
	
	
	public static ServletContext getServletContext() {
		return DsocketServletContextUtil.servletContext;
	}
	
	
	public static String getPath() {
		return servletContext.getRealPath("");
	}
	
	
	
}
