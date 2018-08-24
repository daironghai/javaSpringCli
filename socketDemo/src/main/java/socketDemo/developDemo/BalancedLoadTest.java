package socketDemo.developDemo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.zip.CRC32;

/**
 * 均衡负载test
 */
public class BalancedLoadTest {

	
	//服务器临时
	class ServerTemp {
		public int index; //索引序号
		public Long hash; //hash值
		public String ip; //实际ip
		@Override
		public String toString() {
			return "ServerTemp [index=" + index + ", hash=" + hash + ", ip=" + ip + "]";
		}
	}
	
	
	//服务器列表
	public List<ServerTemp> serverList = new ArrayList<ServerTemp>();
	//链接分布统计
	public int[] countArr = null;
	//总链接统计
	public int arrCount = 0;
	//使用率调整次数
	private int perCount = 0;
	//权重分离
	public int weight = 0;
	//使用率平衡模式
	public boolean useLimitMode = false;
	//使用率差值-超过当前差值优化
	public double limit = 0.4;
	//默认使用率
	private double limitDefault = 0.4;
	
	
	/**
	 * hash化
	 * @param txt
	 * @return
	 */
	public long getHash(String txt) {
        CRC32 crc32 = new CRC32();
        crc32.update(txt.getBytes());
        return crc32.getValue();
    }
	
	
	/**
	 * 挂载服务器
	 * @param server
	 * @throws Exception
	 */
	public List<ServerTemp> setServer(String[] server) throws Exception{
		if(null==server || server.length<1){
			throw new Exception("服务器起始列表为空");
		}
		boolean weightMode = false;
		if(weight!=0){
			weightMode = true;
		}
		//
		ServerTemp temp = null;
		for (int i = 0, len = server.length; i < len; i++) {
			String string = server[i];
			if(weightMode){
				for (int j = 0; j < weight; j++) {
					temp = new ServerTemp();
					temp.ip = string;
					temp.hash = getHash(string + "@1#" + (j + 1));
					serverList.add(temp);
				}
			}else{
				temp = new ServerTemp();
				temp.ip = string;
				temp.hash = getHash(string);
				serverList.add(temp);
			}
		}
		//
		countArr = new int[serverList.size()];
		arrCount = 0;
		perCount = 0;
		if(limit >= 1 || limit <= 0){
			limit = limitDefault;
		}
		//
		Collections.sort(serverList, new Comparator<ServerTemp>() {
			public int compare(ServerTemp o1, ServerTemp o2) {
				return (o1.hash < o2.hash ? -1 : 1);
			}
		});
		//
		int index = 0;
		for (ServerTemp serverTemp : serverList) {
			serverTemp.index = index;
			index++;
			//System.out.println(serverTemp.toString());
		}
		//
		return serverList;
	}
	
	
	/**
	 * 获取可连接服务器
	 * @param ip
	 */
	public void connServer(String ip){
		String targetIp = null;
		int index = 0;
		Long hashIn = getHash(ip);
		for (ServerTemp temp : serverList) {
			if(temp.hash > hashIn){
				targetIp = temp.ip;
				index = temp.index;
				break;
			}
		}
		ServerTemp temp = null;
		if(null==targetIp){
			temp = serverList.get(0);
			targetIp = temp.ip;
			index = temp.index;
		}else{
			temp = serverList.get(index);
			targetIp = temp.ip;
			index = temp.index;
			
			if(useLimitMode){
				//权重不平均，当使用率差距较大时，引导系统判断的ip到压力较清的服务器
				Double[] perArr = new Double[serverList.size()];
				double minPer = 1;
				int minIndex = 0;
				for(int i = 0, len = perArr.length; i < len; i++){
					perArr[i] = (double) countArr[i] / (double) arrCount;
					if(perArr[i] < minPer){
						minPer = perArr[i];
						minIndex = i;
					}
				}
				//当前使用率
				double thisPer = (null==perArr[index] ? 0 : perArr[index]);
				if(thisPer > minPer && (thisPer - minPer) > limit){
					temp = serverList.get(minIndex);
					targetIp = temp.ip;
					index = temp.index;
					//统计
					perCount++;
				}
			}
		}
		//返回、统计
		//System.out.println("访问ip：" + ip + "，服务器ip：" + targetIp);
		countArr[index] += 1; 
		arrCount++;
	}
	
	
	public String getRadomIp(){
		String ret = null;
		Random r1 = new Random();
		int one = r1.nextInt(9900) + 1;
		ret = "127.0.0.1:" + one;
		return ret;
	}
	
	
	public void showStatistics(){
		System.out.println("*******************************");
		System.out.println("虚拟服务器:" + (weight > 0) + (weight > 0 ? ",单位虚拟:" + weight : ""));
		System.out.println("使用率平衡:" + useLimitMode + (useLimitMode ? ",差限:" + limit : ""));
		System.out.println("访问统计:" + Arrays.toString(countArr) + ", 总计:" + arrCount);
		//百分比显示
		DecimalFormat df = new DecimalFormat("0.00");
		DecimalFormat df_int = new DecimalFormat("#");
		String[] perArrStr = new String[serverList.size()];
		double[] perArrDouble = new double[serverList.size()];
		for(int i = 0, len = perArrStr.length; i < len; i++){
			perArrStr[i] = df.format((double) countArr[i] / (double) arrCount * 100) + "%";
			perArrDouble[i] = (double) countArr[i] / (double) arrCount;
		}
		System.out.println("使用率分布:" + Arrays.toString(perArrStr));
		
		Arrays.sort(perArrDouble);
		System.out.println("最高:" + df.format(perArrDouble[perArrDouble.length - 1] * 100) + "%, 最低:" + df.format(perArrDouble[0] * 100) + "%");
		System.out.println("最高:" + df_int.format(perArrDouble[perArrDouble.length - 1] * arrCount) + ",最低:" + df_int.format(perArrDouble[0] * arrCount));
		
		System.out.println("per使用率调整次数:" + perCount);
		System.out.println("*******************************");
	}
	
	
	
	
	
	public static void main(String[] args) throws Exception{
		long a = System.currentTimeMillis();
		
		BalancedLoadTest t = new BalancedLoadTest();
		//权重
		t.weight = 0;
		
		//使用率管理
		t.useLimitMode = true;
		t.limit = 0.1;
		
		//服务器
		String[] server = {
				"192.168.0.0:111", "192.168.0.1:111", "192.168.0.2:111",
                "192.168.0.3:111", "192.168.0.4:111"
		};
		//挂载服务器
		List<ServerTemp> list = t.setServer(server);
		System.out.println("服务器群初始化:" + list.size());
//		for (ServerTemp serverTemp : list) {
//			System.out.println(serverTemp.toString());
//		}
		
		//链接次数 - 1万
		int count = 10000;
		String[] ipC = new String[count];
		for (int i = 0; i < count; i++) {
			ipC[i] = t.getRadomIp();
		}
		System.out.println("虚拟ip:" + ipC.length);
		
		//执行模拟访问
		for (int i = 0; i < ipC.length; i++) {
			t.connServer(ipC[i]);
		}
		//打印统计
		t.showStatistics();
		

		System.out.println();
		System.out.println("-------------");
		long b = System.currentTimeMillis();
		System.out.println((double) (b - a) / 1000 + "秒");
	}
	
	
	
}
