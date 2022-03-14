package com.api.mtx;
/**
* @author 迨
* @version Oct 14, 2021 4:09:26 PM
*/

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;

import javax.naming.InitialContext;
import javax.security.auth.login.LoginContext;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MtxLoginTest {
	CloseableHttpClient httpClient;
	@BeforeClass
	public void init() {
		httpClient=HttpClients.createDefault();
	}
	
	@Test(description = "用户名密码正确")
	public void test001_login() throws Exception {
		
		String reString = MtxLogin.login("zjdtest", "123456",httpClient);
		Assert.assertTrue(reString.contains( "登录成功"));
	}
	
	@Test(description = "用户名正确，密码错误")
	public void test002_login() throws Exception {
		
		String reString = MtxLogin.login("zjdtest", "123456888",httpClient);
		Assert.assertTrue(reString.contains("密码错误"));
	}
	
	@Test
	public void test003_login() throws Exception {
		
		String reString = MtxLogin.login("", "11111",httpClient);
		Assert.assertTrue(reString.contains("请填写登录账号"));
	}
	
}
