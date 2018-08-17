package socketDemo.dSocket.container;

import java.util.HashMap;
import java.util.Map;

/**
 * cmd class容器
 * @author daironghai
 *
 */
public class CmdClassContainer {

	
	
	private static final Map<Class, Object> CLASS_MAP = new HashMap<>();

	public static Map<Class, Object> getClassMap() {
		return CLASS_MAP;
	}
	
	
	
	
}
