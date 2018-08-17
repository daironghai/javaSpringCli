package socketDemo.dSocket.base;

import java.io.IOException;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.alibaba.fastjson.JSONObject;


/**
 * 基本处理命令类
 * CmdHandleConfig的扫描目标
 */
public class BaseCmd {

	
	
	private JSONObject json;
	
	private WebSocketSession session;

	
	
	
	public WebSocketSession getSession() {
		return session;
	}

	public void setSession(WebSocketSession session) {
		this.session = session;
	}

	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}
	
	
	/**
	 * 发送数据
	 * @param json
	 * @throws Exception
	 */
	public void sendData(Object data){
		try {
			if(session.isOpen()){
				JSONObject ret = new JSONObject();
				ret.put("cmd", json.get("cmd"));
				ret.put("data", data);
				if(null!=session){
					session.sendMessage(new TextMessage(ret.toString()));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
