package com.crm.server;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.alibaba.fastjson.JSONReader;
import com.api.http.CrazyHttpClient;

/**
* @author 迨
* @version Nov 23, 2021 10:32:17 AM
*/
public class AddCustomerServer {

    static String paramPath="src/main/resources/crmparam/addcustomer.json";	
    static String url="/CrmCustomer/addOrUpdate";
 //   static String httpPath="src/main/resources/http.properties";
    
    //正常添加客户
	public static String addcustomer(String uri,String token) throws Exception {
		JSONReader jsonReader=new JSONReader(new FileReader(paramPath));
		String readString = jsonReader.readString();
		JSONObject paramJsonObject = JSONObject.parseObject(readString);
		
		Map<String, String> header=new HashMap<String, String>();
		header.put("Admin-Token", token);
		
		String reString = CrazyHttpClient.sendHttpPostJson(uri+url, header, paramJsonObject);
		return reString;
	}
	
	//添加客户参数中修改某个字段值
	public static String addcustomer(String uri,String token,String path, String value) throws Exception {
		JSONReader jsonReader=new JSONReader(new FileReader(paramPath));
		String readString = jsonReader.readString();
		JSONObject paramJsonObject = JSONObject.parseObject(readString);
		JSONPath.set(paramJsonObject, path, value);
		
		Map<String, String> header=new HashMap<String, String>();
		header.put("Admin-Token", token);
		
		String reString = CrazyHttpClient.sendHttpPostJson(uri+url, header, paramJsonObject);		
		return reString;		
	}
	
	// 移除某个字段值
	public static String addCustomer(String uri,String token,String path) throws Exception {
		JSONReader jsonReader=new JSONReader(new FileReader(paramPath));
		String readString = jsonReader.readString();
		JSONObject paramJsonObject = JSONObject.parseObject(readString);
		JSONPath.remove(paramJsonObject, path);
		
		Map<String, String> header=new HashMap<String, String>();
		header.put("Admin-Token", token);
		
		String reString = CrazyHttpClient.sendHttpPostJson(uri+url, header, paramJsonObject);
		return reString;		
	}
}
