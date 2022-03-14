package com.crm.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.bson.json.JsonReader;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.alibaba.fastjson.JSONReader;
import com.api.http.CrazyHttpClient;
import com.api.util.RandomUtil;

/**
* @author 迨
* @version Nov 30, 2021 4:37:32 PM
*/
public class AddContractServer{
    
	static String url="/CrmContract/saveAndUpdate";
	static String paramPath="src/main/resources/crmparam/addContract.json";
	
	public static void addContract(String uri,String token) throws Exception {
		Map<String, String> header=new HashMap<String, String>();
		header.put("Admin-Token", token);
		
		JSONReader jsonReader=new JSONReader(new FileReader(paramPath));
		String params = jsonReader.readString();
		JSONObject paramJsonObject = JSONObject.parseObject(params);
		CrazyHttpClient.sendHttpPostJson(uri+url, header, paramJsonObject);		
	}
	
	public static void addContract(String uri,String token,String path,String value) throws Exception {
		Map<String, String> header=new HashMap<String, String>();
		header.put("Admin-Token", token);
		
		JSONReader jsonReader=new JSONReader(new FileReader(paramPath));
		String params = jsonReader.readString();
		JSONObject paramJsonObject = JSONObject.parseObject(params);
		JSONPath.set(paramJsonObject, path, value);
		JSONPath.set(paramJsonObject,"$.entity.num",RandomUtil.getRndNumByLen(7));
		
		CrazyHttpClient.sendHttpPostJson(uri+url, header, paramJsonObject);
	}
	
	public static void addContract(String uri,String token,String path) throws Exception {
		Map<String, String> header=new HashMap<String, String>();
		header.put("Admin-Token",token);
		
		JSONReader jsonReader=new JSONReader(new FileReader(paramPath));
		String params = jsonReader.toString();
		JSONObject paramJsonObject = JSONObject.parseObject(params);
		JSONPath.remove(paramJsonObject, path);
		CrazyHttpClient.sendHttpPostJson(uri+url, header, paramJsonObject);	
	}
}
