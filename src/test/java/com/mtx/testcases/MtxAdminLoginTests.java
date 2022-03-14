package com.mtx.testcases;
/**
* @author 迨
* @version Nov 18, 2021 2:29:54 PM
*/

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.api.http.CrazyHttpClient;
import com.api.util.JsonPathUtil;
import com.api.util.PropertyUtil;

public class MtxAdminLoginTests {
	String url;
	
	@BeforeClass
	public void init() {
		
	    Properties readProperties2 = PropertyUtil.readProperties("src/main/resources/http.properties");
	    url = readProperties2.getProperty("http.mtx.url");
	    
	}
	@Test
	public void test001_login() throws Exception {
	    Map<String, String> header=new HashMap<String, String>();
	    header.put("X-Requested-With", "XMLHttpRequest");
	    Map<String, String> params = PropertyUtil.getAllKeyValue("src/main/resources/mtxparam/adminLogin.properties");				
	    String sendHttpPost = CrazyHttpClient.sendHttpPost(url+"/mtx/admin.php?s=/admin/login.html", header, params);
	    Object extract = JsonPathUtil.extract(sendHttpPost, "$.code");
	    Assert.assertEquals(extract, "0");
	}

}
