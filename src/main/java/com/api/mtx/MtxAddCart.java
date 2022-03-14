package com.api.mtx;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.LineListener;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
* @author 迨
* @version Oct 15, 2021 3:32:49 PM
*/
public class MtxAddCart {

	public String addToCart(String goodid,String goodstock,CloseableHttpClient httpClient) throws Exception {
		//CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost=new HttpPost();
		httpPost.setURI(new URI("http://192.168.96.157/mtx/index.php?s=/index/cart/save.html"));
		httpPost.setHeader("X-Requested-With","XMLHttpRequest");
		
		NameValuePair goodsid =new BasicNameValuePair("goods_id", goodid);
		NameValuePair stock =new BasicNameValuePair("stock", goodstock);
		List<NameValuePair> list=new ArrayList<NameValuePair>();
		list.add(goodsid);
		list.add(stock);
		
		HttpEntity entity=new UrlEncodedFormEntity(list);
		httpPost.setEntity(entity);
		CloseableHttpResponse response = httpClient.execute(httpPost);
		HttpEntity entity2 = response.getEntity();
		String reString = EntityUtils.toString(entity2,"utf-8");
		return reString;		
	}
}
