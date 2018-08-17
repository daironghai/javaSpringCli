package socketDemo.com.socket.main.quartz;

import java.io.IOException;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.alibaba.fastjson.JSONObject;

import socketDemo.dSocket.container.UserSocketContainer;

/**
 * 定时 广播 任务
 */
@Component
public class BroadcastJob {
	
	public BroadcastJob(){
        
    }
	
	private static final String CMD_KEY = "broadcast";
	
    @Scheduled(cron = "0/10 * *  * * ? ")
    public void run(){
    	System.out.println("BroadcastJob广播任务");
        JSONObject obj = new JSONObject();
        
        obj.put("cmd", CMD_KEY);
        obj.put("src", "sound/output.mp3");

		for (Map.Entry<String, WebSocketSession> sess : UserSocketContainer.get().entrySet()) {
			if(null!=sess.getValue()){
				WebSocketSession session = sess.getValue();
				if(session.isOpen()){
					try {
						session.sendMessage(new TextMessage(obj.toString()));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

    }
	
}
