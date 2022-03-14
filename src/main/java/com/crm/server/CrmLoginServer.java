package com.crm.server;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.api.http.CrazyHttpClient;
import com.api.util.JsonPathUtil;
import com.api.util.PropertyUtil;

/**
* @author 迨
* @version Nov 23, 2021 1:43:09 PM
*/
public class CrmLoginServer {
	
	
    static String paramPath="src/main/resources/crmparam/login.properties";
    static String url="/login";
    
	public static String login(String uri) throws Exception {	
		Map<String, String> params = PropertyUtil.getAllKeyValue(paramPath);		
		Map<String, String> header=new HashMap<String, String>();
		
		String reString = CrazyHttpClient.sendHttpPost(uri+url, header, params);
	    return reString;
	}
}
