package com.crm.server;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.alibaba.fastjson.JSONReader;
import com.api.http.CrazyHttpClient;

/**
* @author 迨
* @version Nov 24, 2021 11:09:42 AM
*/
public class AddContactServer {
	
    static String url="/CrmContacts/addOrUpdate";
    static String parampath="src/main/resources/crmparam/addContact.json";
    
	public static String addContact(String uri,String token) throws Exception {
		Map<String, String> header=new HashMap<String, String>();
		header.put("Admin-Token", token);
		
		JSONReader jsonReader=new JSONReader(new FileReader(parampath));
		String params = jsonReader.readString();
		JSONObject paramJsonObject = JSONObject.parseObject(params);
		
		String reString = CrazyHttpClient.sendHttpPostJson(uri+url, header, paramJsonObject);
		return reString;
	}
	
	public static String addContact(String uri,String token,String path,String value) throws Exception {
		Map<String, String> header=new HashMap<String, String>();
		header.put("Admin-Token", token);
		
		JSONReader jsonReader=new JSONReader(new FileReader(parampath));
		String params = jsonReader.readString();
		JSONObject paramJsonObject = JSONObject.parseObject(params);
		JSONPath.set(paramJsonObject, path, value);
		
		String reString = CrazyHttpClient.sendHttpPostJson(uri+url, header, paramJsonObject);
		return reString;
	}
	
	public static String addContact(String uri,String token,String path) throws Exception {
		Map<String, String> header=new HashMap<String, String>();
		header.put("Admin-Token", token);
		
		JSONReader jsonReader=new JSONReader(new FileReader(parampath));
		String params = jsonReader.readString();
		JSONObject paramJsonObject = JSONObject.parseObject(params);
		JSONPath.remove(paramJsonObject, path);
		
		String reString = CrazyHttpClient.sendHttpPostJson(uri+url, header, paramJsonObject);
		return reString;
	}
	
}
