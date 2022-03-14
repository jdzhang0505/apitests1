package com.crm.server;

import java.util.HashMap;
import java.util.Map;

import com.api.http.CrazyHttpClient;
import com.api.util.PropertyUtil;

/**
* @author 迨
* @version Nov 24, 2021 2:15:07 PM
*/
public class DeleteCustomerServer {
	
	static String url="/CrmCustomer/deleteByIds";
	static String parampath="src/main/resources/crmparam/deleteCustomer.properties";

	public static String deleteCustomer(String uri,String token) throws Exception {
		Map<String, String> header=new HashMap<String, String>();
		header.put("Admin-Token", token);
		
		Map<String, String> params = PropertyUtil.getAllKeyValue(parampath);
		String reString = CrazyHttpClient.sendHttpPost(uri+url, header, params);
		return reString;
	}
	
	public static String deleteCustomer(String uri,String token,String key,String value) throws Exception {
		Map<String, String> header=new HashMap<String, String>();
		header.put("Admin-Token", token);
		
		Map<String, String> params = PropertyUtil.getAllKeyValue(parampath);
		params.put(key, value);
		String reString = CrazyHttpClient.sendHttpPost(uri+url, header, params);
		return reString;
	}
}
