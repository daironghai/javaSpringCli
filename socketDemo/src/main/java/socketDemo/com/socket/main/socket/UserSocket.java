package socketDemo.com.socket.main.socket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import socketDemo.dSocket.annotation.DsocketNode;
import socketDemo.dSocket.base.BaseHandler;

@Component
@DsocketNode(name = "user")
public class UserSocket extends BaseHandler {

	@Override
	public void connectionEstablished(WebSocketSession session) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void textMessage(WebSocketSession session, TextMessage message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void transportError(WebSocketSession session, Throwable exception) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connectionClosed(WebSocketSession session, CloseStatus closeStatus) {
		// TODO Auto-generated method stub
		
	}

}
