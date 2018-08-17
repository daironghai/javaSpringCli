package socketDemo.dSocket.quartz;

import java.io.IOException;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.alibaba.fastjson.JSONObject;

import socketDemo.dSocket.container.UserSocketContainer;

/**
 * 定时任务
 */
@Component
public class QuartzJob {
	
	public QuartzJob(){
        
    }
	
	public void test(){
		System.out.println("test");
	}
	
    //@Scheduled(cron = "0/5 * *  * * ? ")
    public void run(){
    	System.out.println("QuartzJob执行任务");
        JSONObject obj = new JSONObject();
        
        long max = Runtime.getRuntime().maxMemory() / 1024 / 1024;
        obj.put("max", max);
        
        long total = Runtime.getRuntime().totalMemory() / 1024 / 1024;
        obj.put("total", total);
        
		long free = Runtime.getRuntime().freeMemory() / 1024 / 1024;
		obj.put("free", free);
        
		long use = total - free;
		obj.put("use", use);

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
