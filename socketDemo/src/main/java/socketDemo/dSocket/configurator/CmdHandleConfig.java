package socketDemo.dSocket.configurator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.context.ApplicationContext;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.alibaba.fastjson.JSONObject;

import socketDemo.dSocket.annotation.CmdAction;
import socketDemo.dSocket.annotation.CmdClass;
import socketDemo.dSocket.data.ExecuteTemp;
import socketDemo.dSocket.utils.ConfigPropertiesUtils;
import socketDemo.dSocket.utils.DsocketSpringContextUtil;

public class CmdHandleConfig {

	/**
	 * cmd执行记录缓存
	 */
	private static final Map<String, ExecuteTemp> executeMap = new HashMap<String, ExecuteTemp>();
	public static Map<String, ExecuteTemp> getExecuteMap(){
		return executeMap;
	}
	
	public static final CmdHandleConfig config = new CmdHandleConfig();
	
	
	public CmdHandleConfig(){
		
	}
	
	static{
		try {
			System.out.println("CmdHandleConfig init start");
			init();
			System.out.println("CmdHandleConfig init end");
		} catch (Exception e) {
			System.out.println("CmdHandleConfig失败");
			e.printStackTrace();
		}
	}
	
	public static void executeCmd(String cmdText, WebSocketSession session, JSONObject json) throws Exception{
		config.execute(cmdText, session, json);
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void init(){
		ApplicationContext context = DsocketSpringContextUtil.getApplicationContext();
		//获取指定注解的所有类
		Map<String, Object> map = context.getBeansWithAnnotation(CmdClass.class);
		
		
		Iterator<Entry<String, Object>> scanIt = map.entrySet().iterator();
		while(scanIt.hasNext()){
			String cmdText = "";
			Entry<String, Object> entry = scanIt.next();
			
			//
			Class cla = entry.getValue().getClass();
			
			CmdClass cmdClass = (CmdClass) cla.getAnnotation(CmdClass.class);
			if(cmdClass.value().equals("")){
				continue;
			}else{
				cmdText += cmdClass.value();
			}
			
			//命令动作间隔符号
			//网址/端口/【@CmdClass】【limitTarget】【@CmdAction】
			String limitTarget = ConfigPropertiesUtils.getProperty("cmdSubstring");
			//
			Method[] methodList = cla.getMethods();
			for (int i = 0; i < methodList.length; i++) {
				ExecuteTemp et = new ExecuteTemp();
				et.setClas(cla);
				
				Method m = methodList[i];
				CmdAction cmdAction = null;
				//
				if((cmdAction = m.getAnnotation(CmdAction.class))!=null){
					String cmdUrl = cmdText;
					if(cmdAction.value().equals("")){
						continue;
					}else{
						cmdUrl = cmdUrl + limitTarget + cmdAction.value();
						
						if(cmdAction.single()){
							try {
								et.setSingleObj(et.getClas().newInstance());
							} catch (Exception e) {
								System.err.println(et.getClas().getClass() + "单例化失败");
								et.setSingleObj(null);
							}
						}
					}
					et.setMethodName(m.getName());
					CmdHandleConfig.executeMap.put(cmdUrl, et);
				}else{
					continue;
				}
			}
		}
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void execute(String cmdText, WebSocketSession session, JSONObject json) throws Exception{
		ExecuteTemp executeTemp = executeMap.get(cmdText);
		if(null==executeTemp) {
			//无效命令，返回信息...
			System.out.println("无效命令，返回信息...");
			return;
		}
		Class classTemp = executeTemp.getClas();
		
		Class father = classTemp.getSuperclass();
		if(father.getTypeName().equals("java.lang.Object")){
			throw new Exception("cmd执行类："+classTemp.getName()+"，未继承BaseCmd");
		}
		
		Object executeClass = executeTemp.getSingleObj();
		if(null==executeClass){
			executeClass = classTemp.newInstance();
		}
		
		try {
			Method setSession = father.getDeclaredMethod("setSession", new Class[]{WebSocketSession.class});
			setSession.invoke(executeClass, session);
			Method setJson = father.getDeclaredMethod("setJson", new Class[]{JSONObject.class});
			setJson.invoke(executeClass, json);
			//执行
			Method execute = classTemp.getDeclaredMethod(executeTemp.getMethodName());
			execute.invoke(executeClass);
		} catch (InvocationTargetException e) {
			System.out.println("反射方法异常");
			Throwable t = e.getTargetException();// 获取反射目标中的异常，在接收点抛出而不是在反射方法内
			t.printStackTrace();
		}
	}
	
	
	


	
	
	

	
	
	
	
}
