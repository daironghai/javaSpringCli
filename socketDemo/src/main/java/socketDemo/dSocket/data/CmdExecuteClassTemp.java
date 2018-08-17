package socketDemo.dSocket.data;

import java.util.HashMap;
import java.util.Map;

//cmd执行类缓存
public class CmdExecuteClassTemp {

	
	//声明名称
	private String cmdClassName;
	
	//对象
	private Class clasTarget;
	
	//声明方法集
	private Map<String, CmdExecuteMethodTemp> cmdMethodMap = new HashMap<>();
	
	

	//其他配置
	//...
	

	public String getCmdClassName() {
		return cmdClassName;
	}

	public void setCmdClassName(String cmdClassName) {
		this.cmdClassName = cmdClassName;
	}

	public Class getClasTarget() {
		return clasTarget;
	}

	public void setClasTarget(Class clasTarget) {
		this.clasTarget = clasTarget;
	}

	public Map<String, CmdExecuteMethodTemp> getCmdMethodMap() {
		return cmdMethodMap;
	}

	public void setCmdMethodMap(Map<String, CmdExecuteMethodTemp> cmdMethodMap) {
		this.cmdMethodMap = cmdMethodMap;
	}
	
	
	
	
	
	
	
	
	
}
