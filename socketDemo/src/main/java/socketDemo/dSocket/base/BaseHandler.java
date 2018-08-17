package socketDemo.dSocket.base;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * 基本链接声明类
 */
public abstract class BaseHandler extends TextWebSocketHandler {
	
	
	/**
	 * 链接成功事件
	 * @param session
	 */
	public abstract void connectionEstablished(WebSocketSession session);
	
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    	connectionEstablished(session);
    }
	
    
    /**
     * 收到文本消息时方法
     * @param session
     * @param message
     * @throws Exception
     */
    public abstract void textMessage(WebSocketSession session, TextMessage message);
    
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    	textMessage(session, message);
    }
    
    
    /**
     * 异常时触发
     * @param session
     * @param exception
     */
    public abstract void transportError(WebSocketSession session, Throwable exception);
    
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
    	transportError(session, exception);
    }
    
    
    /**
     * 链接关闭触发
     * @param session
     * @param closeStatus
     */
    public abstract void connectionClosed(WebSocketSession session, CloseStatus closeStatus);
    
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
    	connectionClosed(session, closeStatus);
    }
	
	
	
	
}
