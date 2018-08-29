package socketDemo.dSocket.configurator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.context.ApplicationContext;
import org.springframework.web.socket.WebSocketSession;

import com.alibaba.fastjson.JSONObject;

import socketDemo.dSocket.annotation.CmdAction;
import socketDemo.dSocket.annotation.CmdClass;
import socketDemo.dSocket.container.CmdClassContainer;
import socketDemo.dSocket.container.CmdContainer;
import socketDemo.dSocket.data.CmdExecuteClassTemp;
import socketDemo.dSocket.data.CmdExecuteMethodTemp;
import socketDemo.dSocket.data.ExecuteTemp;
import socketDemo.dSocket.utils.ConfigPropertiesUtils;
import socketDemo.dSocket.utils.DsocketSpringContextUtil;

public class CmdHandleConfig {

	//动作和执行间隔标记
	private final static String CMD_SUBSTRING_KEY = "socketMvc.CMD_SUBSTRING_KEY"; 
	
	/**
	 * cmd执行记录缓存
	 */
	private static final Map<String, ExecuteTemp> executeMap = new HashMap<String, ExecuteTemp>();
	public static Map<String, ExecuteTemp> getExecuteMap(){
		return executeMap;
	}
	
	private static final CmdHandleConfig config = new CmdHandleConfig();
	
	
	
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
		/**
		 *  临时变量
		 */
		String cmdName = null;	//命令名
		String limitTarget = ConfigPropertiesUtils.getProperty(CmdHandleConfig.CMD_SUBSTRING_KEY); //命令方法之间的分隔符
		if(null==limitTarget || "".equals(limitTarget)) {
			limitTarget = "/"; //默认设置
		}
		CmdExecuteClassTemp cmdClassTemp = null; //方法对象
		CmdExecuteMethodTemp cmdMethodTemp = null; //方法对象
		Map<String, CmdExecuteMethodTemp> cmdMethodMap = null; //方法对象集
		Class cla = null;
		Method m = null;
		Method[] methodList = null;
		CmdClass cmdClass = null;
		CmdAction cmdAction = null;
		
		
		//获取spring正文
		ApplicationContext context = DsocketSpringContextUtil.getApplicationContext();
		//获取指定注解的所有类
		Map<String, Object> map = context.getBeansWithAnnotation(CmdClass.class);
		//变量缓存到内存
		Iterator<Entry<String, Object>> scanIt = map.entrySet().iterator();
		Entry<String, Object> entry = null;
		while(scanIt.hasNext()){
			entry = scanIt.next();
			
			//类映射
			cla = entry.getValue().getClass();
			//类注解
			cmdClass = (CmdClass) cla.getAnnotation(CmdClass.class);
			if(cmdClass.value().equals("")){
				continue;
			}else{
				cmdName = cmdClass.value();
			}
			//方法集
			cmdMethodMap = new HashMap<>();
			//遍历方法
			methodList = cla.getMethods();
			for (int i = 0; i < methodList.length; i++) {
				m = methodList[i];
				if((cmdAction = m.getAnnotation(CmdAction.class))==null){
					continue;
				}
				if(cmdAction.value().trim().equals("")) {
					continue;
				}
				cmdMethodTemp = new CmdExecuteMethodTemp();
				cmdMethodTemp.setMethodName(m.getName());
				cmdMethodTemp.setSingle(cmdAction.single());
				
				cmdMethodMap.put(cmdAction.value(), cmdMethodTemp);
			}
			//命令
			cmdClassTemp = new CmdExecuteClassTemp();
			cmdClassTemp.setClasTarget(cla);
			cmdClassTemp.setCmdMethodMap(cmdMethodMap);
			//
			CmdContainer.getCmdMap().put(cmdName, cmdClassTemp);
		}
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void execute(String cmdText, WebSocketSession session, JSONObject json) throws Exception{
		if(null==cmdText || "".equals(cmdText) || null==session) {
			return;
		}
		/**
		 * 
		 */
		CmdExecuteClassTemp classTemp = null;
		CmdExecuteMethodTemp methodTemp = null;
		Class father = null;
		
		String limitTarget = ConfigPropertiesUtils.getProperty(CmdHandleConfig.CMD_SUBSTRING_KEY); //命令方法之间的分隔符
		int index = cmdText.indexOf(limitTarget);
		if(index == -1) {
			return;
		}
		String cls = cmdText.substring(0, index);
		String met = null;
		try {
			met = cmdText.substring(index + 1, cmdText.length());
		} catch (Exception e) {
			return;
		}
		if(CmdContainer.getCmdMap().containsKey(cls)) {
			classTemp = CmdContainer.getCmdMap().get(cls);
			father = classTemp.getClasTarget().getSuperclass();
			if(father.getTypeName().equals("java.lang.Object")){
				throw new Exception("cmd执行类："+classTemp.getClass().getName()+"，未继承BaseCmd");
			}
			if(classTemp.getCmdMethodMap().containsKey(met)) {
				methodTemp = classTemp.getCmdMethodMap().get(met);
				
				//类
				Class executeClass = classTemp.getClasTarget();
				//实例
				Object executeClassObj = null;
				//单例容器
				if(methodTemp.isSingle()) {
					executeClassObj = executeClass.newInstance();
				}else {
					if(CmdClassContainer.getClassMap().containsKey(executeClass)) {
						executeClassObj = CmdClassContainer.getClassMap().get(executeClass);
					}else {
						executeClassObj = executeClass.newInstance();
						CmdClassContainer.getClassMap().put(executeClass, executeClassObj);
					}
				}
				try {
					//父类方法注入
					Method setSession = father.getDeclaredMethod("setSession", new Class[]{WebSocketSession.class});
					setSession.invoke(executeClassObj, session);
					Method setJson = father.getDeclaredMethod("setJson", new Class[]{JSONObject.class});
					setJson.invoke(executeClassObj, json);
					//执行方法查找
					Method execute = executeClass.getDeclaredMethod(methodTemp.getMethodName());
					//执行
					execute.invoke(executeClassObj);
				} catch (InvocationTargetException e) {
					System.out.println("反射方法异常");
					Throwable t = e.getTargetException();// 获取反射目标中的异常，在接收点抛出而不是在反射方法内
					t.printStackTrace();
				}
			}
		}
	}
	



	
	
	

	
	
	
	
}
