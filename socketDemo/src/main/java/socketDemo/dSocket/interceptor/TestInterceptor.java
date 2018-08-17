package socketDemo.dSocket.interceptor;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

public class TestInterceptor implements HandshakeInterceptor {

	public void afterHandshake(ServerHttpRequest arg0, ServerHttpResponse arg1, WebSocketHandler arg2, Exception arg3) {
		// TODO Auto-generated method stub
		System.out.println("��������");
	}

	public boolean beforeHandshake(ServerHttpRequest arg0, ServerHttpResponse arg1, WebSocketHandler arg2,
			Map<String, Object> arg3) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("������ǰ");
		return true;
	}

}
