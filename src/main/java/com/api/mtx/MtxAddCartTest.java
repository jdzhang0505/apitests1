package com.api.mtx;
/**
* @author 迨
* @version Oct 15, 2021 4:04:03 PM
*/

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class MtxAddCartTest {
	
	CloseableHttpClient httpClient;
	
	@BeforeClass
	public void init() throws Exception {
		httpClient=HttpClients.createDefault();
		MtxLogin login=new MtxLogin();
		String reString = login.login("zjdtest", "123456",httpClient);
		Assert.assertTrue(reString.contains( "登录成功"));
	}

	@Test(description = "goods_id和stock值有效")
	public void test001_addToCart() throws Exception {
		MtxAddCart mtxAddCart=new MtxAddCart();
		String addToCart = mtxAddCart.addToCart("8", "1",httpClient);
		System.out.println("响应结果："+addToCart);
		Assert.assertTrue(addToCart.contains("加入成功"));		
	}
	
}
