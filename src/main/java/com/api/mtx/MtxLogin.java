package com.api.mtx;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
* @author 迨
* @version Oct 14, 2021 3:54:30 PM
*/
public class MtxLogin {
	
	public static String login(String account,String password,CloseableHttpClient httpClient) throws Exception {
		//CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost=new HttpPost();
		httpPost.setURI(new URI("http://192.168.96.157/mtx/index.php?s=/index/user/login.html"));
		httpPost.setHeader("X-Requested-With", "XMLHttpRequest");
		
		NameValuePair username=new BasicNameValuePair("accounts", account);
		NameValuePair pwd=new BasicNameValuePair("pwd", password);
		List<NameValuePair> list=new ArrayList<NameValuePair>();
		list.add(username);
		list.add(pwd);
		
		HttpEntity entity=new UrlEncodedFormEntity(list);
		httpPost.setEntity(entity);
		
		CloseableHttpResponse response = httpClient.execute(httpPost);
		HttpEntity entity2 = response.getEntity();
		String reString = EntityUtils.toString(entity2,"utf-8");
		return reString;
						
	}	
}
