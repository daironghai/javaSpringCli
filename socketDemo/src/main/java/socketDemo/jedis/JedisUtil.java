package socketDemo.jedis;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import socketDemo.dSocket.utils.ConfigPropertiesUtils;


/**
 * 连接对象工具
 * @author daironghai
 *
 */
public class JedisUtil {

	
	private static Jedis jedis;
	
	private static JedisPool pool;
	
	private static JedisApi jedisApi;
	
	
	/**
	 * 获取连接池
	 * @return
	 */
	public static JedisPool getJedisPool() {
		if(null==pool) {
			//获取服务器IP地址
	         String ipStr = ConfigPropertiesUtils.getProperty("redis.ip");
	         //获取服务器端口
	         int portStr = Integer.valueOf(ConfigPropertiesUtils.getProperty("redis.port"));
	         //密码
	         String auth = ConfigPropertiesUtils.getProperty("redis.auth");
	         //初始化连接池
	         if(null!=auth && !"".equals(auth)) {
		         pool = new JedisPool(JedisPoolConfigUtil.getJedisPoolConfig(), ipStr, portStr);
	         }else {
		         pool = new JedisPool(JedisPoolConfigUtil.getJedisPoolConfig(), ipStr, portStr, 1000 * 60, auth);
	         }
		}
		return pool;
	}
	
	
	/**
	 * 获取操作对象
	 * @return
	 */
	public static Jedis getJedis() {
		if(null==jedis) {
			jedis = getJedisPool().getResource();
			//设置使用分区
			String zone = ConfigPropertiesUtils.getProperty("redis.zone");
			if(null!=zone && !"".equals(zone)) {
				jedis.select(Integer.valueOf(zone));
			}
		}
		return jedis;
	}

	
	/**
	 * 获取自定义api工具
	 * @return
	 */
	public static JedisApi getJedisApi() {
		if(null==jedisApi) {
			jedisApi = new JedisApi(getJedis());
		}
		return jedisApi;
	}
		
	
	
	
	
	public static void main(String[] args) {

		String key = "post";
		Jedis jedis = JedisUtil.getJedis();
		JedisApi jedisApi = JedisUtil.getJedisApi();
//		
//		jedis.flushDB();
//		
//		jedisApi.setList(key, new String[] {"1", "2", "3"});
//		List<String> list = jedisApi.getList(key);
//		System.out.println(list.size());
//		for (String string : list) {
//			System.out.println(string);
//		}
		
		
		List<String> list = jedisApi.getList(key);
		System.out.println(list.size());
		for (String string : list) {
			System.out.println(string);
		}
		
		System.out.println("end");
	}
	
	
	
	
	
}
