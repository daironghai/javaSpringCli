package socketDemo.com.utils;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

@Component
public class ServletContextUtil implements ServletContextAware {

	
	private static ServletContext servletContext;
	
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	
	public static ServletContext getServletContext() {
		return servletContext;
	}
	
	
	
	
}
