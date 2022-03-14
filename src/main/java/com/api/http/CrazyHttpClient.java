package com.api.http;
/**
* @author 迨
* @version Oct 22, 2021 3:33:00 PM
*/

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.bson.json.JsonReader;
import org.testng.Reporter;

import com.alibaba.fastjson.JSONObject;



public class CrazyHttpClient {
    private static Logger logger=Logger.getLogger(CrazyHttpClient.class);
	static CloseableHttpClient httpClient;
	static CloseableHttpResponse response;
	static HttpGet httpGet;
	static HttpPost httpPost;
	static {
		httpClient=HttpClients.createDefault();
	}
	
	public static String sendHttpGet(String uri,String param,Map<String, String> headers) throws Exception, IOException {
		logger.info("uri:"+uri);
		logger.info("param:"+param);
		Reporter.log("uri:"+uri);
		Reporter.log("param:"+param);
		
		httpGet =new HttpGet();
	    httpGet.setURI(new URI(uri+param));
	    
		if(!headers.isEmpty()) {
			Set<Entry<String, String>> entrySet = headers.entrySet();
			for (Entry<String, String> entry : entrySet) {
				logger.info("headers:"+entry.getKey()+"="+entry.getValue());
				Reporter.log("headers:"+entry.getKey()+"="+entry.getValue());
				httpGet.setHeader(entry.getKey(),entry.getValue());				
			}
		}
	
		response = httpClient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		String reString = EntityUtils.toString(entity,"utf-8");
		logger.info(reString);
		Reporter.log(reString);
	    return reString;
	}
	
	public static String sendHttpPost(String uri,Map<String, String> header, Map<String, String> params) throws Exception {
		logger.info("uri:"+uri);
		Reporter.log("uri:"+uri);
		httpPost=new HttpPost();
		httpPost.setURI(new URI(uri));
		
		if(!header.isEmpty()) {
			Set<Entry<String, String>> entrySet = header.entrySet();
			for (Entry<String, String> entry : entrySet) {
				logger.info("header:"+entry.getKey()+"="+entry.getValue());
				Reporter.log("header:"+entry.getKey()+"="+entry.getValue());
				httpPost.setHeader(entry.getKey(),entry.getValue());
			}
		}
		
		
		List<NameValuePair> list=new ArrayList<NameValuePair>();
		if(!params.isEmpty()) {
			Set<Entry<String, String>> entrySet2 = params.entrySet();
			for (Entry<String, String> entry : entrySet2) {
				logger.info("params:"+entry.getKey()+"="+entry.getValue());
				Reporter.log("params:"+entry.getKey()+"="+entry.getValue());
				NameValuePair nameValuePair =new BasicNameValuePair(entry.getKey(), entry.getValue());
				list.add(nameValuePair);				
			}
		}
		
		HttpEntity entity =new UrlEncodedFormEntity(list);
		httpPost.setEntity(entity);
	    response = httpClient.execute(httpPost);
	    HttpEntity entity2 = response.getEntity();
		String reString = EntityUtils.toString(entity2);
		logger.info("response:"+reString);
		Reporter.log("response:"+reString);
		return reString;
	}
	 
	public static String sendHttpPostJson(String uri,Map<String, String> header,JSONObject params) throws Exception {
		logger.info("uri:"+uri);
		Reporter.log("uri:"+uri);
		httpPost=new HttpPost();
		httpPost.setURI(new URI(uri));
		
		if(!header.isEmpty()) {
			Set<Entry<String, String>> entrySet = header.entrySet();
			for (Entry<String, String> entry : entrySet) {
				logger.info("header:"+entry.getKey()+"="+entry.getValue());
				Reporter.log("header:"+entry.getKey()+"="+entry.getValue());
				httpPost.setHeader(entry.getKey(), entry.getValue());				
			}
		}
		
		String paramString= params.toString();
		logger.info("param:"+paramString);
		Reporter.log("param:"+paramString);
		HttpEntity entity=new StringEntity(paramString,"utf-8");
		httpPost.setEntity(entity);
		
		CloseableHttpResponse response = httpClient.execute(httpPost);
		String reString = EntityUtils.toString(response.getEntity(),"utf-8");
		logger.info("response:"+reString);
		Reporter.log("response:"+reString);
		return reString;
		
	}
	
	public static void main(String[] args) throws Exception, Exception {
		String reString = CrazyHttpClient.sendHttpGet("http://www.baidu.com/s", "?wd=selenium", new HashMap<String, String>());
        System.out.println(reString);
	}
}
