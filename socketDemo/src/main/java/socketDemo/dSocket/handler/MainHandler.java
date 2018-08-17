package socketDemo.dSocket.handler;


import java.util.Random;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.alibaba.fastjson.JSONObject;

import socketDemo.dSocket.configurator.CmdHandleConfig;
import socketDemo.dSocket.container.UserSocketContainer;

public class MainHandler extends TextWebSocketHandler {


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    	UserSocketContainer.add("唯一标识" + new Random().nextInt(9999), session);
    	//主动关闭
    	//session.close();
    }
	
    
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    	
    	String data = message.getPayload();
    	try {
    		//命令正文
    		System.out.println("请求数据 ：" + data);
    		
        	JSONObject json = JSONObject.parseObject(data);
        	if(json.containsKey("cmd")){
        		String cmdText = json.get("cmd").toString();
        		if(null!=cmdText && !"".equals(cmdText.trim())) {
            		CmdHandleConfig.executeCmd(cmdText, session, json);
        		}
        	}else{
        		session.sendMessage(new TextMessage(json.toString()));
        	}
		} catch (Exception e) {
			e.printStackTrace();
			//非json数据
			//session.close();
		}
    }
    
    
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
    	System.out.println("error");
    	UserSocketContainer.remove(session);
    }
    

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
    	/**
    	 * 状态码见：http://blog.csdn.net/u014290233/article/details/53390103
    	 */
    	System.out.println(closeStatus.getCode() +"," + "close");
    	UserSocketContainer.remove(session);
    }

    
}
