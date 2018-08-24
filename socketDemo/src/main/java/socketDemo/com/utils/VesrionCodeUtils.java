package socketDemo.com.utils;

import java.util.List;

/**
 * 版本代号工具
 */
public class VesrionCodeUtils {

	
	private final static String INIT_VERSION = "1.0";
	
	
	
	
	
	/**
	 * 获取版本号编码
	 * 空或null，返回1.0
	 * @param version 版本字符，可为null
	 * @return
	 */
	public static String getVersion(String version){
		if(null!=version && !"".equals(version)){
			String[] dArr = version.split("\\.");
			if(null!=dArr && dArr.length > 0){
				Integer lastVersionCode = null;
				try {
					lastVersionCode = Integer.parseInt(dArr[dArr.length - 1]);
				} catch (Exception e) {
					e.printStackTrace();
					return VesrionCodeUtils.INIT_VERSION;
				}
				dArr[dArr.length - 1] = (lastVersionCode + 1) + "";
				return String.join(".", dArr);
			}
		}
		return VesrionCodeUtils.INIT_VERSION;
	}
	
	
	/**
	 * 获取版本号编码
	 * 自动判断最大版本
	 * @param versionList 版本字符数组，可为null
	 * @return
	 */
	public static String getVersion(List<String> versionList){
		if(null!=versionList && versionList.size()>0){
			String[] versionArr = new String[versionList.size()];
			for (int i = 0, len = versionList.size(); i < len; i++) {
				versionArr[i] = versionList.get(i);
			}
			return getVersion(versionArr);
		}
		return VesrionCodeUtils.INIT_VERSION;
	}
	
	
	/**
	 * 获取版本号编码
	 * 自动判断最大版本
	 * @param version 版本字符数组，可为null
	 * @return
	 */
	public static String getVersion(String[] versionArr){
		try {
			if(null!=versionArr && versionArr.length>0){
				String maxVersion = versionArr[0];
				for (int i = 1, len = versionArr.length; i < len; i++) {
					if(compareVersion(maxVersion, versionArr[i]) == -1){
						maxVersion = versionArr[i];
					}
				}
				return getVersion(maxVersion);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return VesrionCodeUtils.INIT_VERSION;
	}
	
	
	/**
	 * 版本比较
	 * @param version1 
	 * @param version2
	 * @return 1：version1大，-1：version1x小，0：一样
	 */
    public static int compareVersion(String version1, String version2) {
        String[] s1 = version1.split("\\."); //通过\\将.进行转义
        String[] s2 = version2.split("\\.");
        int len1 = s1.length;
        int len2 = s2.length;
        int i, j;
        for (i = 0, j = 0; i < len1 && j < len2; i++, j++) {
            if (Integer.parseInt(s1[i]) > Integer.parseInt(s2[j])) {
                return 1;
            } else if (Integer.parseInt(s1[i]) < Integer.parseInt(s2[j])) {
                return -1;
            }
        }
        while (i < len1) {
            if (Integer.parseInt(s1[i]) != 0) {
                return 1;
            }
            i++;
        }
        while (j < len2) {
            if (Integer.parseInt(s2[j]) != 0) {
                return -1;
            }
            j++;
        }
        return 0;
    }
	
	
    
    
	
	public static void main(String[] args) {
		
		String a = VesrionCodeUtils.getVersion("1.0.1");
		//System.out.println(a);
		
		String[] aArr = new String[]{
			"1.1.2",
			"1.7",
			"1.8",
			"1.9"
		};
		
		String b = VesrionCodeUtils.getVersion(aArr);
		System.out.println(b);
		

		
	}
	
	
	
	
	
	
	
	
	
}
