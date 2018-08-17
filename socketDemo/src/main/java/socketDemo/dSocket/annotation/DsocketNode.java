package socketDemo.dSocket.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DsocketNode {

	/**
	 * 节点名称
	 * @return
	 */
	public String name();
	
	
	/**
	 * 拦截器类
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Class[] interceptor() default {};
	
	/**
	 * 白名单
	 * @return
	 */
	public String[] allow() default {};
	
}
