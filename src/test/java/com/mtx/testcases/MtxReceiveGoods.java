package com.mtx.testcases;
/**
* @author 迨
* @version Nov 19, 2021 11:17:05 AM
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

public class MtxReceiveGoods {
  String url;
  Map<String, String> header;
	
  @BeforeClass
  public void init() throws Exception {
	  Properties readProperties = PropertyUtil.readProperties("src/main/resources/http.properties");
	  url = readProperties.getProperty("http.mtx.url");
	  
	  header=new HashMap<String, String>();
	  header.put("X-Requested-With", "XMLHttpRequest");
	  Map<String, String> params = PropertyUtil.getAllKeyValue("src/main/resources/mtxparam/login.properties");
	  String sendHttpPost = CrazyHttpClient.sendHttpPost(url+"/mtx/index.php?s=/index/user/login.html", header, params);
	  Object extract = JsonPathUtil.extract(sendHttpPost, "$.msg");
	  Assert.assertEquals(extract, "登录成功");
	  
  }
  
  @Test(description = "已发货订单正常收货")
  public void test001_ReceivedGoods() throws Exception {
	  Map<String, String> params = PropertyUtil.getAllKeyValue("src/main/resources/mtxparam/receiveGoods.properties");
	  String sendHttpPost = CrazyHttpClient.sendHttpPost(url+"/mtx/index.php?s=/index/order/collect.html", header, params);
	  Object extract = JsonPathUtil.extract(sendHttpPost, "$.msg");
	  Assert.assertEquals(extract, "收货成功");
  }
  
  @Test(description = "已收货订单再次收货")
  public void test002_ReceivedGoods() throws Exception {
	  Map<String, String> params = PropertyUtil.getAllKeyValue("src/main/resources/mtxparam/receiveGoods.properties");
	  params.put("id", "359");
	  String sendHttpPost = CrazyHttpClient.sendHttpPost(url+"/mtx/index.php?s=/index/order/collect.html", header, params);
	  Object extract = JsonPathUtil.extract(sendHttpPost, "$.msg");
	  Assert.assertEquals(extract, "状态不可操作[已完成]");
  }
  
  @Test(description = "订单号字段id为空")
  public void test003_ReceivedGoods() throws Exception {
	  Map<String, String> params = PropertyUtil.getAllKeyValue("src/main/resources/mtxparam/receiveGoods.properties");
	  params.put("id", "");
	  String sendHttpPost = CrazyHttpClient.sendHttpPost(url+"/mtx/index.php?s=/index/order/collect.html", header, params);
	  Object extract = JsonPathUtil.extract(sendHttpPost, "$.msg");
	  Assert.assertEquals(extract, "订单id有误");
  }
  
}
