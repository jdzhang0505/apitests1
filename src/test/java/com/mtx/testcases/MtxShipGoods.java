package com.mtx.testcases;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.api.http.CrazyHttpClient;
import com.api.util.DBUtil;
import com.api.util.JsonPathUtil;
import com.api.util.PropertyUtil;

/**
* @author 迨
* @version Nov 18, 2021 2:57:51 PM
*/
public class MtxShipGoods {
    String url;
	
	@BeforeClass
	public void init() throws Exception {
		
	    Properties readProperties2 = PropertyUtil.readProperties("src/main/resources/http.properties");
	    url = readProperties2.getProperty("http.mtx.url");
	    
	    Properties readProperties = PropertyUtil.readProperties("src/main/resources/db.properties");
	    String dburl = readProperties.getProperty("DB.mtx.url");
	    String dbuser = readProperties.getProperty("DB.mtx.user");
	    String dbpwd = readProperties.getProperty("DB.mtx.password");
	    DBUtil.getConnect(dburl, dbuser, dbpwd);
	    
	    Map<String, String> header=new HashMap<String, String>();
	    header.put("X-Requested-With", "XMLHttpRequest");
	    Map<String, String> params = PropertyUtil.getAllKeyValue("src/main/resources/mtxparam/adminLogin.properties");				
	    String sendHttpPost = CrazyHttpClient.sendHttpPost(url+"/mtx/admin.php?s=/admin/login.html", header, params);
	    Object extract = JsonPathUtil.extract(sendHttpPost, "$.code");
	    Assert.assertEquals(extract, "0");    
	}
	
//	@Test(description = "已付款订单发货")
	public void test001_ShipGoods() throws Exception {
		Map<String, String> header=new HashMap<String, String>();
	    header.put("X-Requested-With", "XMLHttpRequest");
	    Map<String, String> params = PropertyUtil.getAllKeyValue("src/main/resources/mtxparam/shipGoods.properties");
	    String sendHttpPost = CrazyHttpClient.sendHttpPost(url+"/mtx/admin.php?s=/order/delivery.html", header, params);
	    Object extract = JsonPathUtil.extract(sendHttpPost, "$.msg");
	    Assert.assertEquals(extract, "发货成功");
	    //数据库断言
	    String expectOrderid = params.get("id");
	    String expectExpressnum = params.get("express_number");
	    List<Map> data = DBUtil.getData("select express_number from s_order where id="+expectOrderid);
	    String actualExpressnum = data.get(0).get("express_number").toString();
	    Assert.assertEquals(actualExpressnum, expectExpressnum);
	}
	@Test(description = "已发货订单再次发货")
	public void test002_shipGoods() throws Exception {
		Map<String, String> header=new HashMap<String, String>();
	    header.put("X-Requested-With", "XMLHttpRequest");
	    Map<String, String> params = PropertyUtil.getAllKeyValue("src/main/resources/mtxparam/shipGoods.properties");
	    params.put("id", "357");
	    String sendHttpPost = CrazyHttpClient.sendHttpPost(url+"/mtx/admin.php?s=/order/delivery.html", header, params);
	    Object extract = JsonPathUtil.extract(sendHttpPost, "$.msg");
	    Assert.assertEquals(extract, "状态不可操作[待收货]");	
	}
	
	@Test(description = "express_number字段缺失")
	public void test003_shipGoods() throws Exception {
		Map<String, String> header=new HashMap<String, String>();
		header.put("X-Requested-With", "XMLHttpRequest");
		Map<String, String> params = PropertyUtil.getAllKeyValue("src/main/resources/mtxparam/shipGoods.properties");
		params.remove("express_number");   //快递单号缺失
	//	params.put("express_number", ""); //快递单号为空
		String sendHttpPost = CrazyHttpClient.sendHttpPost(url+"/mtx/admin.php?s=/order/delivery.html", header, params);
		Object extract = JsonPathUtil.extract(sendHttpPost, "$.msg");
		Assert.assertEquals(extract, "快递单号有误");
		
	}
	
	@AfterClass
	public void afterClass() {
		DBUtil.close();
	}
}


