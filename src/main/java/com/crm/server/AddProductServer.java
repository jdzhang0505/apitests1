package com.crm.server;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.alibaba.fastjson.JSONReader;
import com.api.http.CrazyHttpClient;
import com.api.util.JsonPathUtil;
import com.api.util.RandomUtil;

/**
* @author 迨
* @version Nov 29, 2021 3:49:23 PM
*/
public class AddProductServer {

	static String paramPath="src/main/resources/crmparam/addProducts.json";
	static String url="/CrmProduct/saveAndUpdate";
	public static String addProduct(String uri,String token) throws Exception {
		Map<String, String> header=new HashMap<String, String>();
		header.put("Admin-Token", token);
		
		JSONReader jsonReader=new JSONReader(new FileReader(paramPath));
		String readString = jsonReader.readString();
		JSONObject paramJson = JSONObject.parseObject(readString);
		String reString = CrazyHttpClient.sendHttpPostJson(uri+url, header, paramJson);
		return reString;
	}
	
	public static String addProduct(String uri,String token,String jsonPath,String value) throws Exception {
		Map<String, String> header=new HashMap<String, String>();
		header.put("Admin-Token", token);
		
		JSONReader jsonReader=new JSONReader(new FileReader(paramPath));
		String readString = jsonReader.readString();
		JSONObject paramJson = JSONObject.parseObject(readString);
		JSONPath.set(paramJson, jsonPath, value);
		JSONPath.set(paramJson, "$.entity.num", RandomUtil.getRndNumByLen(8));
		String reString = CrazyHttpClient.sendHttpPostJson(uri+url, header, paramJson);
		return reString;
	}
	
	public static String addProduct(String uri,String token,String jsonPath) throws Exception {
		Map<String, String> header=new HashMap<String, String>();
		header.put("Admin-Token", token);
		
		JSONReader jsonReader=new JSONReader(new FileReader(paramPath));
		String readString = jsonReader.readString();
		JSONObject paramJson = JSONObject.parseObject(readString);
		JSONPath.remove(paramJson, jsonPath);
		String reString = CrazyHttpClient.sendHttpPostJson(uri+url, header, paramJson);
		return reString;
	} 
	
	public static String addProduct(String uri,String token,JSONObject params) throws Exception {
		Map<String, String> header=new HashMap<String, String>();
		header.put("Admin-Token", token);
		JSONPath.set(params, "$.entity.num", RandomUtil.getRndNumByLen(8));
		String reString = CrazyHttpClient.sendHttpPostJson(uri+url, header, params);
		return reString;
	}
}
