package socketDemo.dSocket.container;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.web.socket.WebSocketSession;

/**
 * socket用户容器
 */
public class UserSocketContainer {

	
	private static DateFormat dateF = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	
	private static final Map<String, WebSocketSession> userList = Collections.synchronizedMap(new HashMap<String, WebSocketSession>());
	
	
	//获取容器
	public static Map<String, WebSocketSession> get(){
		return UserSocketContainer.userList;
	}
	
	
	//新增
	public static void add(String key, WebSocketSession session){
		if(null!=session){
			System.err.println(dateF.format(new Date()) + " [" + key +"] 用户进入");
			UserSocketContainer.userList.put(key, session);
		}
	}
	
	
	//根据key获取
	public static WebSocketSession get(String key){
		if(UserSocketContainer.userList.containsKey(key)){
			return UserSocketContainer.userList.get(key);
		}
		return null;
	}
	
	//根据value获取
	public static WebSocketSession get(WebSocketSession session){
		if(UserSocketContainer.userList.containsValue(session)){
			return session;
		}
		return null;
	}
	
	
	//key去除
	public static void remove(String key){
		if(UserSocketContainer.userList.containsKey(key)){
			UserSocketContainer.userList.remove(key);
		}
	}
	
	
	//value去除
	public static void remove(WebSocketSession session){
		if(UserSocketContainer.userList.containsValue(session)){
			Iterator<Entry<String, WebSocketSession>> it = UserSocketContainer.userList.entrySet().iterator();
			while(it.hasNext()){
				Entry<String, WebSocketSession> entity = it.next();
				if(entity.getValue()==session){
					UserSocketContainer.userList.remove(entity.getKey());
					break;
				}
			}
		}
	}
	
	//获取key数组
	public static List<String> getKetList(){
		if(UserSocketContainer.userList!=null&&UserSocketContainer.userList.size()>0){
			List<String> list = new ArrayList<String>();
			Iterator<Entry<String, WebSocketSession>> it = UserSocketContainer.userList.entrySet().iterator();
			while(it.hasNext()){
				Entry<String, WebSocketSession> entity = it.next();
				list.add(entity.getKey());
			}
			Collections.sort(list);
			return list;
		}else{
			return null;
		}
	}
	
}
