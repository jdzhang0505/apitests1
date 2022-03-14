package com.crm.server;

import java.util.HashMap;
import java.util.Map;

import com.api.http.CrazyHttpClient;
import com.api.util.PropertyUtil;

/**
* @author 迨
* @version Nov 25, 2021 2:35:57 PM
*/
public class DeleteContactsServer {

	static String url="/CrmContacts/deleteByIds";
	static String parampath="src/main/resources/crmparam/deleteContacts.properties";
	public static String deleteContacts(String uri,String token) throws Exception {
		Map<String, String> header=new HashMap<String, String>();
		header.put("Admin-Token", token);
		
		Map<String, String> params = PropertyUtil.getAllKeyValue(parampath);
		String reString = CrazyHttpClient.sendHttpPost(uri+url, header, params);
		return reString;
	}
	
	public static String deleteContacts(String uri,String token,String key,String value) throws Exception {
		Map<String, String> header=new HashMap<String, String>();
		header.put("Admin-Token", token);
		
		Map<String, String> params = PropertyUtil.getAllKeyValue(parampath);
		params.put(key, value);
		
		String reString = CrazyHttpClient.sendHttpPost(uri+url, header, params);
		return reString;
		
	}
}
