package socketDemo.dSocket;

import javax.servlet.ServletContext;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import socketDemo.BaseTest;
import socketDemo.dSocket.utils.DsocketServletContextUtil;

public class ServletContextTest extends BaseTest {

	
	@Test
	public void test1() {
		
		ServletContext content = DsocketServletContextUtil.getServletContext();
		String cPath = content.getRealPath("");
		System.out.println(cPath);
		
	}
	
	
	
	public static void main(String[] args) {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		
		//ServletContext content = DsocketServletContextUtil.getServletContext();
		//String cPath = content.getContextPath();
		//System.out.println(cPath);
		
		
		String path = DsocketServletContextUtil.getPath();
		System.out.println(path);
		
	}
	
	
	
}
