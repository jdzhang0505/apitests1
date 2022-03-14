package com.api.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.bson.json.JsonReader;

/**
* @author 迨
* @version Oct 29, 2021 3:44:55 PM
*/
public class PropertyUtil {
	private static Logger logger=Logger.getLogger(PropertyUtil.class);

	public static Properties readProperties(String file ) {
		Properties  properties =new Properties();
		InputStream inputStream ;
		try {
			inputStream =new FileInputStream(file);
			BufferedReader bf=new BufferedReader(new InputStreamReader(inputStream ));
			properties.load(bf);
			inputStream.close();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
		    throw new RuntimeException(e.getMessage());
		}
		return properties;
	}
	
	public static Map<String, String> getAllKeyValue(String file){
		Properties readProperties = PropertyUtil.readProperties(file);
		Map<String, String> params=new HashMap<String, String>();
		Set<Entry<Object, Object>> entrySet = readProperties.entrySet();
		for (Entry<Object, Object> entry : entrySet) {
			 String key = entry.getKey().toString();
			 String value = entry.getValue().toString();
			 params.put(key, value);
		}
		return params;
	}
	
	public static void main(String[] args) {
		Map<String, String> allKeyValue = PropertyUtil.getAllKeyValue("src/main/resources/mtxparam/order.properties");
		
		/*
		 * Set<Entry<String, String>> entrySet = allKeyValue.entrySet(); for
		 * (Entry<String, String> entry : entrySet) {
		 * System.out.println(entry.getKey()+":"+entry.getValue()); }
		 */
		System.out.println(allKeyValue);
	
	}
}
