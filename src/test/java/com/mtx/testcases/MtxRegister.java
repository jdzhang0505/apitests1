package com.mtx.testcases;
/**
* @author 迨
* @version Nov 10, 2021 11:02:09 AM
*/

import java.sql.Connection;
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
import com.api.util.EncryptionUtil;
import com.api.util.JsonPathUtil;
import com.api.util.PropertyUtil;

@Test
public class MtxRegister {
	String uri ;

	@BeforeClass
	public void init() {
		Properties readProperties = PropertyUtil.readProperties("src/main/resources/db.properties");
		String dburl = readProperties.getProperty("DB.mtx.url");
		String dbuser = readProperties.getProperty("DB.mtx.user");
		String dbpassword = readProperties.getProperty("DB.mtx.password");
		DBUtil.getConnect(dburl, dbuser, dbpassword);

	    Properties readProperties2 = PropertyUtil.readProperties("src/main/resources/http.properties");
	    uri = readProperties2.getProperty("http.mtx.url");
	}
	@Test
	public void test001_register() throws Exception {
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("X-Requested-With", "XMLHttpRequest");
		Map<String, String> params = PropertyUtil.getAllKeyValue("src/main/resources/mtxparam/register.properties");
		String sendHttpPost = CrazyHttpClient.sendHttpPost(uri+"/mtx/index.php?s=/index/user/reg.html", headers, params);
	//	System.out.println(sendHttpPost);
		Object msg = JsonPathUtil.extract(sendHttpPost, "$.msg");
		Assert.assertEquals(msg.toString(),"注册成功");
		Object userid = JsonPathUtil.extract(sendHttpPost, "$.data.data.user_id");
		List<Map> data = DBUtil.getData("select salt,pwd from s_user where id="+userid);
		String salt = data.get(0).get("salt").toString();
		String actualPwd = data.get(0).get("pwd").toString();
		String expectPwd = EncryptionUtil.md5(salt+params.get("pwd"));
		Assert.assertEquals(actualPwd, expectPwd);
		
		
	}
	@AfterClass
	public void afterClass() {
		DBUtil.close();
	}
}
