package socketDemo.com.web.redis.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import socketDemo.com.base.BaseController;
import socketDemo.com.web.redis.dto.RedisDTO;
import socketDemo.jedis.JedisUtil;

@RestController
@RequestMapping("/sysTest")
public class TestController extends BaseController {

	
	private static final String KEY = "post";
	
	
	
	@PostMapping(path = "/add")
	public JSONObject list(@RequestBody RedisDTO redisDTO) {
		if(null!=redisDTO
				&& null!=redisDTO.getxPost() && !"".equals(redisDTO.getxPost())
				&& null!=redisDTO.getyPost() && !"".equals(redisDTO.getyPost())) {
			
			String ip = getIp();
			String value = ip + "+" + redisDTO.getxPost() + "+" + redisDTO.getyPost();
			
			List<String> list = JedisUtil.getJedisApi().getList(KEY);
			boolean hasLog = false;
			for (int i = 0, len = list.size(); i < len; i++) {
				if(list.get(i).indexOf(ip) > -1) {
					JedisUtil.getJedis().lset(KEY, i, value);
					hasLog = true;
					break;
				}
			}
			if(!hasLog) {
				JedisUtil.getJedis().lpush(KEY, value);
			}
		}
		return new JSONObject();
	}
	
	
	
	@PostMapping(path = "/getAll")
	public JSONArray getAll() {
		JSONArray arr = new JSONArray();
		JSONObject json = null;
		String[] temp = null;
		
		List<String> list = JedisUtil.getJedisApi().getList(KEY);
		for (String string : list) {
			temp = string.split("\\+");
			json = new JSONObject();
			json.put("ip", temp[0]);
			json.put("lo", temp[1]);
			json.put("sh", temp[2]);
			arr.add(json);
		}
		return arr;
	}
	
	
//	public static void main(String[] args) {
//
//		String[] temp = null;
//		List<String> list = JedisUtil.getJedisApi().getList(KEY);
//		for (String string : list) {
//			temp = string.split("\\+");
//			System.out.println(temp[0] + " - " + temp[1] + " - " + temp[2]);
//		}
//		
//	}
	
	
	
}
