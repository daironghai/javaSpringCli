package socketDemo.api.baidu;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.regex.Pattern;

import org.json.JSONObject;

import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;

public class VoiceSynthesisApiUtils {

	
	//判断是否为数字
	private static final Pattern PATTERN_IsNumber = Pattern.compile("[0-9]*");
	//工具对象
	private static AipSpeech aipSpeech;
	
	
	public static AipSpeech getClient() {
		if(null==aipSpeech) {
			aipSpeech = new AipSpeech(BAIDU_APP_KEY.APP_ID, BAIDU_APP_KEY.API_KEY, BAIDU_APP_KEY.SECRET_KEY);
		}
		return aipSpeech;
	}
	
	
	
	
	public static File getDefualt(String txt, File file) {
		AipSpeech client = getClient();
		client.setConnectionTimeoutInMillis(1000 * 10);
		
		HashMap<String, Object> options = new HashMap<String, Object>();
		//语速，取值0-9，默认为5中语速
		options.put("spd", "3");
		//音调，取值0-9，默认为5中语调
	    options.put("pit", "5");
	    //音量，取值0-15，默认为5中音量
	    options.put("vol", "7");
	    //发音人选择, 0为女声，1为男声，3为情感合成-度逍遥，4为情感合成-度丫丫，默认为普通女
	    options.put("per", "4");
	    
	    TtsResponse res = client.synthesis(txt, "zh", 100, options);
	    byte[] data = res.getData();
	    if (null != data) {
	    	try {
	    		if(file.exists()) {
      				file.delete();
      				file.createNewFile();
      			}
      			OutputStream os = new FileOutputStream(file);
      			os.write(data);
      			os.flush();
      			os.close();
	    	} catch (Exception e) {
        	  	e.printStackTrace();
          	}
		    JSONObject result = res.getResult();
		    if(null!=result) {
		    	System.out.println(result.toString());
		    }
	    }
		return file;
	}
	
	
	
	
	public static void main(String[] args) {
		
		File file = new File("C:\\Users\\daironghai\\Desktop\\output.mp3");
		VoiceSynthesisApiUtils.getDefualt("你是猪吗？让你打开就打开吖", file);
		System.out.println("end");
		
	}
	
	
	
	
}
