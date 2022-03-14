package com.api.http;
/**
* @author 迨
* @version Oct 12, 2021 4:48:26 PM
*/


import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class httpGet {

	@Test
	
	public void httpRequest() throws Exception {
		CloseableHttpClient httpClient=HttpClients.createDefault();
	    HttpGet httpGet=new HttpGet();
	    httpGet.setURI(new URI("http://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd=selenium"));
	    CloseableHttpResponse response = httpClient.execute(httpGet);
	    HttpEntity entity = response.getEntity();
	    String reString = EntityUtils.toString(entity,"utf-8");
	    Assert.assertTrue(reString.contains("selenium"));
	    
	    
	}

}
