package com.api.mtx;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.api.http.httpPost;

/**
* @author 迨
* @version Oct 19, 2021 1:20:53 PM
*/
public class CrmLogin {

	public static String login(CloseableHttpClient httpClient,String name,String pwd) throws Exception {
		
		HttpPost httpPost=new HttpPost();
		httpPost.setURI(new URI("http://192.168.96.157:8090/login"));
		
		NameValuePair userName=new BasicNameValuePair("username", name);
		NameValuePair password=new BasicNameValuePair("password", pwd);
		List<NameValuePair> list=new ArrayList<NameValuePair>();
		list.add(userName);
		list.add(password);
		
		HttpEntity entity=new UrlEncodedFormEntity(list);
		httpPost.setEntity(entity);
		
		CloseableHttpResponse response = httpClient.execute(httpPost);
		HttpEntity entity2 = response.getEntity();
		String reString = EntityUtils.toString(entity2);
		return reString;		
	}
}
