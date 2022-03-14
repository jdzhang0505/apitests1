package com.crm.fenceng.testcases;

import java.util.Properties;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.alibaba.fastjson.JSONObject;
import com.api.util.DBUtil;
import com.api.util.PropertyUtil;
import com.crm.server.CrmLoginServer;

/**
* @author 迨
* @version Nov 23, 2021 3:37:35 PM
*/
public class TestBase {
	String token;
	String uri;
	
	@BeforeClass
	public void init() throws Exception {
		Properties readProperties = PropertyUtil.readProperties("src/main/resources/http.properties");
	    uri = readProperties.getProperty("http.crm.url");
		String login = CrmLoginServer.login(uri);
		JSONObject parseObject = JSONObject.parseObject(login);
	    token = parseObject.getString("Admin-Token");
	    
	    //数据库连接
	    Properties readProperties2 = PropertyUtil.readProperties("src/main/resources/db.properties");
	    String dburl = readProperties2.getProperty("DB.crm.url");
	    String dbuser = readProperties2.getProperty("DB.crm.user");
	    String dbpwd = readProperties2.getProperty("DB.crm.password");
	    DBUtil.getConnect(dburl, dbuser, dbpwd);
	}
	
	@AfterClass
	public void afterClass() {
		DBUtil.close();
	}
}
