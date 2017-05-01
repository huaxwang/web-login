package com.soga.code.core.util;

import com.soga.code.common.util.PropertiesLoader;

import java.util.Properties;

public class PropertiesUtil {
	
	private static Properties properties=null;
	
	static{
		if(properties==null){
			PropertiesLoader loader = new PropertiesLoader("config.properties");
			properties = loader.getProperties();
		}
	}
	
	/**
	 * 取出Property，但以System的Property优先,取不到返回空字符串.
	 */
	public static String getValue(String key) {
		String systemProperty = System.getProperty(key);
		if (systemProperty != null) {
			return systemProperty;
		}
		if (properties.containsKey(key)) {
	        return properties.getProperty(key);
	    }
	    return "";
	}
	
}
