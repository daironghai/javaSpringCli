package socketDemo.jedis;

import redis.clients.jedis.JedisPoolConfig;
import socketDemo.dSocket.utils.ConfigPropertiesUtils;


/**
 * 连接池配置工具
 * @author daironghai
 *
 */
public class JedisPoolConfigUtil {

	
	
	private static JedisPoolConfig jedisPool;
	
	
	
	public static JedisPoolConfig getJedisPoolConfig() {
		if(null==jedisPool) {
			jedisPool = new JedisPoolConfig();
			//最大连接数
			jedisPool.setMaxTotal(Integer.valueOf(ConfigPropertiesUtils.getProperty("jedis.pool.maxActive")));
			//最大连接数
			jedisPool.setMaxTotal(Integer.valueOf(ConfigPropertiesUtils.getProperty("jedis.pool.maxTotal")));
			//最大空闲连接数
			jedisPool.setMaxIdle(Integer.valueOf(ConfigPropertiesUtils.getProperty("jedis.pool.maxIdle")));
			//获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
			jedisPool.setMaxWaitMillis(Long.valueOf(ConfigPropertiesUtils.getProperty("jedis.pool.maxWait")));
			//在获取连接的时候检查有效性, 默认false
			jedisPool.setTestOnBorrow(Boolean.valueOf(ConfigPropertiesUtils.getProperty("jedis.pool.testOnBorrow")));
			//在获取返回结果的时候检查有效性, 默认false
			jedisPool.setTestOnReturn(Boolean.valueOf(ConfigPropertiesUtils.getProperty("jedis.pool.testOnReturn")));
		}
		return jedisPool;
	}
	
	
	public static void main(String[] args) {
		
		String ip = ConfigPropertiesUtils.getProperty("redis.ip");
		System.out.println(ip);
		
		
	}
	
	
	
	
	
	
}
