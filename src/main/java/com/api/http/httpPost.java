package com.api.http;
/**
* @author 迨
* @version Oct 13, 2021 11:13:58 AM
*/

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.LineListener;

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


public class httpPost {
	@Test(description = "用户名密码都正确")
	public void httpPostRequest() throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost=new HttpPost();
		httpPost.setURI(new URI("http://192.168.96.157/mtx/index.php?s=/index/user/login.html"));
		httpPost.setHeader("X-Requested-With", "XMLHttpRequest");
		
		NameValuePair account=new BasicNameValuePair("accounts", "zjdtest");
		NameValuePair password=new BasicNameValuePair("pwd", "123456");
		List<NameValuePair> list=new ArrayList<NameValuePair>();
		list.add(account);
		list.add(password);
		
		HttpEntity entity=new UrlEncodedFormEntity(list);
		httpPost.setEntity(entity);
		
		CloseableHttpResponse response = httpClient.execute(httpPost);
		HttpEntity entity2 = response.getEntity();
		String reString = EntityUtils.toString(entity2,"utf-8");
		Assert.assertTrue(reString.contains("登录成功"));				
	}
	
	@Test(description = "用户名正确，密码不正确")
	public void test001_loginPost() throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost=new HttpPost();
		
		httpPost.setURI(new URI("http://192.168.96.157/mtx/index.php?s=/index/user/login.html"));
		httpPost.setHeader("X-Requested-With", "XMLHttpRequest");
		
		NameValuePair account=new BasicNameValuePair("accounts", "zjdtest");
		NameValuePair password=new BasicNameValuePair("pwd", "12345678");
		
		List<NameValuePair> list=new ArrayList<NameValuePair>();
		list.add(account);
		list.add(password);
		
		HttpEntity entity=new UrlEncodedFormEntity(list);
		httpPost.setEntity(entity);
		
		CloseableHttpResponse response = httpClient.execute(httpPost);
		HttpEntity entity2 = response.getEntity();
		String reString = EntityUtils.toString(entity2,"utf-8");
		Assert.assertTrue(reString.contains("密码错误"));
				
	}
	
	@Test(description = "用户名为空")
	public void test002_loginPost() throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost  httpPost=new HttpPost("http://192.168.96.157/mtx/index.php?s=/index/user/logininfo.html");
		httpPost.setHeader("X-Requested-With", "XMLHttpRequest");
		
		NameValuePair account=new BasicNameValuePair("accounts", "");
		NameValuePair password=new BasicNameValuePair("pwd", "12345678");
		
		List<NameValuePair> list=new ArrayList<NameValuePair>();
		list.add(account);
		list.add(password);
		HttpEntity entity=new UrlEncodedFormEntity(list);
		
		httpPost.setEntity(entity);
		CloseableHttpResponse response = httpClient.execute(httpPost);
		HttpEntity entity2 = response.getEntity();
		String reString = EntityUtils.toString(entity2,"utf-8");
		Assert.assertTrue(reString.contains("请填写登录账号"));					
	}

}
