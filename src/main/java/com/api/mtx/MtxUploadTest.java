package com.api.mtx;
/**
* @author 迨
* @version Oct 22, 2021 9:40:30 AM
*/

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;

public class MtxUploadTest {
    CloseableHttpClient httpClient;
	
	@BeforeClass
	public void init() throws Exception {
		httpClient=HttpClients.createDefault();
		String reString = MtxLogin.login("zjdtest", "123456",httpClient);
		Assert.assertTrue(reString.contains( "登录成功"));
	}
	
	@Test
	public void test001_upload() throws Exception {
		String upload = MtxUpload.upload(httpClient);
		System.out.println("upload:"+upload);
		JSONObject jsonObject=JSONObject.parseObject(upload);
		String actualResult = jsonObject.getString("msg");
		Assert.assertEquals(actualResult, "上传成功");
		
	}
	
	
}
