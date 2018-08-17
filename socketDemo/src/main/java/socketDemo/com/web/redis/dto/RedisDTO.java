package socketDemo.com.web.redis.dto;

import java.io.Serializable;

public class RedisDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7902748830203213099L;

	
	private String data;

	
	//定位信息
	private String xPost;
	private String yPost;
	
	
	

	public String getxPost() {
		return xPost;
	}


	public void setxPost(String xPost) {
		this.xPost = xPost;
	}


	public String getyPost() {
		return yPost;
	}


	public void setyPost(String yPost) {
		this.yPost = yPost;
	}


	public String getData() {
		return data;
	}


	public void setData(String data) {
		this.data = data;
	}
	
	
	
	
	
}
