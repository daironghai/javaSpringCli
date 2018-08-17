package socketDemo.jedis;

import java.util.Collections;
import java.util.List;

import redis.clients.jedis.Jedis;

/**
 * Jedis自定义拓展
 * @author daironghai
 *
 */
public class JedisApi {

	
	public JedisApi(Jedis jedis) {
		this.jedis = jedis;
	}
	
	
	private Jedis jedis;

	
	
	/********************** list *****************************/
	
	public Long setList(String key, List<String> list) {
		return this.setList(key, (String[]) list.toArray());
	}
	
	public Long setList(String key, String[] arr) {
		Long ret = new Long(0);
		if(null==key || "".equals(key)
				|| null==arr || arr.length==0) {
			return ret;
		}
		try {
			ret = jedis.lpush(key, arr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public List<String> getList(String key){
		try {
			if(null==key || "".equals(key)) {
				return Collections.EMPTY_LIST;
			}
			Long len = jedis.llen(key);
			if(null==len || len==0) {
				return Collections.EMPTY_LIST;
			}
			List<String> list = jedis.lrange(key, 0, len - 1);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.EMPTY_LIST;
		}
	}
	
	
	
	
	
	
	
}
