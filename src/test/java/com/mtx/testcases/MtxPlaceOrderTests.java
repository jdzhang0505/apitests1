package com.mtx.testcases;
/**
* @author 迨
* @version Oct 27, 2021 4:28:50 PM
*/

import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import static org.testng.Assert.ARRAY_MISMATCH_TEMPLATE;
import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import javax.naming.InitialContext;

import org.apache.http.impl.client.HttpClients;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.api.http.CrazyHttpClient;
import com.api.http.httpPost;
import com.api.util.DBUtil;
import com.api.util.JsonPathUtil;
import com.api.util.PropertyUtil;

public class MtxPlaceOrderTests {
	public String url;
	public String dburl;
	public String dbuser;
	public String dbpwd;
	
	@BeforeClass
	public void init() throws Exception {
		Properties readProperties = PropertyUtil.readProperties("src/main/resources/http.properties");
		url = readProperties.getProperty("http.mtx.url");
		
		
		Properties readProperties2 = PropertyUtil.readProperties("src/main/resources/db.properties");
		dburl = readProperties2.getProperty("DB.mtx.url");
		dbuser= readProperties2.getProperty("DB.mtx.user");
		dbpwd = readProperties2.getProperty("DB.mtx.password");
		DBUtil.getConnect(dburl, dbuser, dbpwd);
		
		Map<String, String> header=new HashMap<String, String>();
		header.put("X-Requested-With", "XMLHttpRequest");
//		Map<String, String> params=new HashMap<String, String>();
//		params.put("accounts", "zjdtest");
//		params.put("pwd","123456");
		Map<String, String> params = PropertyUtil.getAllKeyValue("src/main/resources/mtxparam/login.properties");
		String reString = CrazyHttpClient.sendHttpPost(url+"/mtx/index.php?s=/index/user/login.html", header, params);
		JSONObject jsonObject=JSONObject.parseObject(reString);		
		String actualString = jsonObject.getString("msg");
		AssertJUnit.assertEquals(actualString, "登录成功");
				
	}
 
//	@Test(description = "下单必填字段")
	public void test001_placeOrder() throws Exception {
		
		Map<String, String> header=new HashMap<String, String>();
		header.put("X-Requested-With", "XMLHttpRequest");
		
		/*
		 * Map<String, String> params=new HashMap<String, String>();
		 * params.put("goods_id", "8"); params.put("stock", "15");
		 * params.put("buy_type", "goods"); params.put("address_id", "2");
		 * params.put("payment_id", "1"); params.put("spec", "[]");
		 */
		
		Map<String, String> params = PropertyUtil.getAllKeyValue("src/main/resources/mtxparam/order.properties");
		params.remove("user_note");
		String reString = CrazyHttpClient.sendHttpPost(url+"/mtx/index.php?s=/index/buy/add.html", header, params);
		JSONObject jsonObject=JSONObject.parseObject(reString);
		String actualResult = jsonObject.getString("msg");
		AssertJUnit.assertEquals(actualResult, "提交成功");		
	}
	
//	@Test(description = "下单必填字段goods_id缺失")
	public void test002_placeOrder() throws Exception {
		
		Map<String, String> header=new HashMap<String, String>();
		header.put("X-Requested-With", "XMLHttpRequest");
		Map<String, String> params = PropertyUtil.getAllKeyValue("src/main/resources/mtxparam/order.properties");
		params.remove("goods_id");
		String sendHttpPost = CrazyHttpClient.sendHttpPost(url+"/mtx/index.php?s=/index/buy/add.html", header, params);
		System.out.println(sendHttpPost);
		JSONObject jsonObject=JSONObject.parseObject(sendHttpPost);
		AssertJUnit.assertEquals(jsonObject.getString("msg"), "商品id有误");		
	}
	
//	@Test(description = "下单必填字段buy_type为空")
	public void test003_placeOrder() throws Exception {
		Map<String, String> param=PropertyUtil.getAllKeyValue("src/main/resources/mtxparam/order.properties");
		param.put("buy_type", "");
		
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("X-Requested-With", "XMLHttpRequest");
		String sendHttpPost = CrazyHttpClient.sendHttpPost(url+"/mtx/index.php?s=/index/buy/add.html", headers , param);
		Object extract = JsonPathUtil.extract(sendHttpPost,"$.msg");
		Assert.assertEquals(extract.toString(), "参数有误");
	}
	
	@Test(description = "下单全部字段且通过数据库查询断言")
	public void test004_placeOrder() throws Exception {
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("X-Requested-With", "XMLHttpRequest");
		Map<String, String> param = PropertyUtil.getAllKeyValue("src/main/resources/mtxparam/order.properties");
		
		String sendHttpPost = CrazyHttpClient.sendHttpPost(url+"/mtx/index.php?s=/index/buy/add.html", headers, param);
	//	System.out.println(sendHttpPost);
		 String orderid = JsonPathUtil.extract(sendHttpPost, "$.data.order.id").toString();
		 
		List<Map> data2 = DBUtil.getData("SELECT title from s_goods where id="+param.get("goods_id"));
		String expectTitle = data2.get(0).get("title").toString(); 
		
		 List<Map> data = DBUtil.getData("SELECT user_id,goods_id,title from s_order_detail where order_id="+orderid);
		 String acUserid = data.get(0).get("user_id").toString();
		 String acTitle = data.get(0).get("title").toString();
		 String acGoodsid = data.get(0).get("goods_id").toString();
		 
		 Assert.assertEquals(acUserid, "28");
		 Assert.assertEquals(acGoodsid, "8");
		 Assert.assertEquals(acTitle, expectTitle);
		 
		 
	}
	
	@AfterClass
	public void afterClass() {
		DBUtil.close();
	}
}
