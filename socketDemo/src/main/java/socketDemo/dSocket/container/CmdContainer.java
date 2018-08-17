package socketDemo.dSocket.container;

import java.util.HashMap;
import java.util.Map;

import socketDemo.dSocket.data.CmdExecuteClassTemp;

//cmd缓存容器
public class CmdContainer {

	
	
	/**
	 * cmd执行类map
	 * key : cmd命令
	 * value : cmd处理类
	 */
	private static final Map<String, CmdExecuteClassTemp> CMD_MAP = new HashMap<>();

	
	public static Map<String, CmdExecuteClassTemp> getCmdMap() {
		return CMD_MAP;
	}
	
	
	
	
	
	
	
	
	
	
	
}
