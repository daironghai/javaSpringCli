package socketDemo.dSocket.data;


/**
 * 执行方法缓存类
 * @author daironghai
 *
 */
public class ExecuteTemp {

	//类名
	private Class clas;
	//方法名
	private String methodName;
	//单例对象
	private Object singleObj;
	
	
	public Class getClas() {
		return clas;
	}
	public void setClas(Class clas) {
		this.clas = clas;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public Object getSingleObj() {
		return singleObj;
	}
	public void setSingleObj(Object singleObj) {
		this.singleObj = singleObj;
	}
	
	
	@Override
	public String toString() {
		return "ExecuteTemp [clas=" + clas + ", methodName="
				+ methodName + "]";
	}
	
	
}
