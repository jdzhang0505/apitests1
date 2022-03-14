package com.api.mtx;
/**
* @author 迨
* @version Oct 19, 2021 2:13:41 PM
*/

import javax.naming.InitialContext;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;

public class CrmTest {

	CloseableHttpClient httpClient;
	String token;
	
	@BeforeClass
	public void init() {
		httpClient=HttpClients.createDefault();
	}
	
	@Test
	public void test001_login() throws Exception {
		
		String reString = CrmLogin.login(httpClient, "admin", "123456");
		Assert.assertTrue(reString.contains("办公"));
		JSONObject jsonObject=JSONObject.parseObject(reString);
		token = jsonObject.getString("Admin-Token");
	//	token = (String) jsonObject.get("Admin-Token");
		System.out.println("token:"+token);
		
	}
	
	@Test
	public void test002_addContact() throws Exception {
		String addContact = CrmAddContact.addContact(httpClient, token);
		System.out.println("addcontact:"+addContact);
		JSONObject resJsonObject=JSONObject.parseObject(addContact);
		String customerName = resJsonObject.getJSONObject("data").getString("customerName");
		Assert.assertEquals(customerName, "客户002");
		
	}
}
