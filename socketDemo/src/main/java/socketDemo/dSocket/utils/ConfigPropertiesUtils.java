package socketDemo.dSocket.utils;

import java.io.IOException;
import java.util.Properties;

public class ConfigPropertiesUtils {

	
	private static Properties properties;
	
	static {
        properties = new Properties();
        try {
            properties.load(ConfigPropertiesUtils.class.getResourceAsStream("/config.properties"));
        } catch (IOException e) {
            System.err.println("config.properties file may not be not exists");
        }
    }
	
	
	public static String getProperty(String key) {
        return properties.getProperty(key);
    }
	
	
	public static void main(String[] args) {
		System.out.println(ConfigPropertiesUtils.getProperty("cmdSubstring"));
	}
	
	
	
}
