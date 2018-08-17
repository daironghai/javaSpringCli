package socketDemo.dSocket.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CmdAction {

	
	/**
	 * 动作名称
	 * @return
	 */
	public String value() default "";
	
	
	/**
	 * 是否单例执行
	 * @return
	 */
	public boolean single() default false;
	
	
	
	
	
}
