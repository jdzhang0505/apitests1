package com.api.mtx;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import com.api.http.httpPost;

/**
* @author 迨
* @version Oct 20, 2021 3:58:28 PM
*/
public class CrmAddContact {
 
	public static String addContact(CloseableHttpClient httpClient,String token) throws Exception {
		
		String param="{\r\n" + 
				"	\"entity\": {\r\n" + 
				"		\"customer_name\": \"客户002\",\r\n" + 
				"		\"mobile\": \"18991112345\",\r\n" + 
				"		\"telephone\": \"01028375678\",\r\n" + 
				"		\"website\": \"http://testfan.cn\",\r\n" + 
				"		\"next_time\": \"2020-04-02 00:00:00\",\r\n" + 
				"		\"remark\": \"这是备注\",\r\n" + 
				"		\"address\": \"北京市,北京城区,昌平区\",\r\n" + 
				"		\"detailAddress\": \"霍营地铁\",\r\n" + 
				"		\"location\": \"\",\r\n" + 
				"		\"lng\": \"\",\r\n" + 
				"		\"lat\": \"\"\r\n" + 
				"	}\r\n" + 
				"}\r\n" + 
				"";
		
		HttpPost  httpPost=new HttpPost();
		httpPost.setURI(new URI("http://192.168.96.157:8090/CrmCustomer/addOrUpdate"));
		httpPost.setHeader("Admin-Token", token);
		HttpEntity entity=new StringEntity(param, "utf-8");
		httpPost.setEntity(entity);
		CloseableHttpResponse response = httpClient.execute(httpPost);
		HttpEntity entity2 = response.getEntity();
		String reString = EntityUtils.toString(entity2);
		return reString;
	}
}
