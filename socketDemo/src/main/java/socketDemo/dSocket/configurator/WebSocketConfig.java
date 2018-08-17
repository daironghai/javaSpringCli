package socketDemo.dSocket.configurator;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import socketDemo.dSocket.handler.MainHandler;


//@Configuration 测试模式下请注释
@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {
	
	
	//跨域访问需要拦截，不得为“*”
	private final static String[] allow = new String[]{
		"http://lcoalhost:8020",
		"http://lcoalhost",
		"http://lcoalhost:8082",
		"http://lcoalhost:8082/socketDemo",
		"http://127.0.0.1:8020"
	};
	
	
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		System.out.println("WebSocketConfig init");
		
		//默认端口
		registry.addHandler(new MainHandler(), "/main").setAllowedOrigins(allow);
		
		//此处申明端口
		
	}
	
	

}
