package socketDemo.com.socket.main.cmd;

import java.util.ArrayList;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import socketDemo.com.utils.ServletContextUtil;
import socketDemo.com.utils.SpringContextUtil;
import socketDemo.dSocket.annotation.CmdAction;
import socketDemo.dSocket.annotation.CmdClass;
import socketDemo.dSocket.base.BaseCmd;
import socketDemo.dSocket.container.UserSocketContainer;

@Component
@CmdClass("test")
public class TestCmd extends BaseCmd {

	
	@CmdAction("list")
	public void test_1(){
		System.out.println(getSession().getId());
		System.out.println(getJson().toString());
	}
	
	@CmdAction("path")
	public void path(){
		ServletContext sc = ServletContextUtil.getServletContext();
		String path = sc.getRealPath("");
		System.out.println(path);
		sendData(path);
	}
	
	@CmdAction("spring")
	public void spring(){
		ApplicationContext ac = SpringContextUtil.getApplicationContext();
		String path = ac.getApplicationName();
		sendData(path);
	}
	
	
	@CmdAction(value = "chat", single = true)
	public void test_2(){
		System.out.println("12W3userChat cmd");
	}

	
	@CmdAction(value = "getUser", single = true)
	public void test_3(){
		JSONArray arr = new JSONArray(new ArrayList<Object>(UserSocketContainer.get().keySet()));
		sendData(arr);
	}
	
	
	@CmdAction(value = "getJvm", single = true)
	public void test_4() {
		JSONObject obj = new JSONObject();
        
        long max = Runtime.getRuntime().maxMemory() / 1024 / 1024;
        obj.put("max", max);
        
        long total = Runtime.getRuntime().totalMemory() / 1024 / 1024;
        obj.put("total", total);
        
		long free = Runtime.getRuntime().freeMemory() / 1024 / 1024;
		obj.put("free", free);
        
		long use = total - free;
		obj.put("use", use);
		
		sendData(obj);
	}
	
	
	
	
}
