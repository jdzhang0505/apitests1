package com.crm.fenceng.testcases;
/**
* @author 迨
* @version Nov 23, 2021 2:09:48 PM
*/

import org.testng.Assert;
import org.testng.annotations.Test;

import com.api.util.JsonPathUtil;
import com.api.util.Props;
import com.crm.server.AddCustomerServer;

public class AddCustomerTests extends TestBase{
	

	@Test(description = "正常添加客户")
	public void test001_AddCustomer() throws Exception {
		String addcustomer = AddCustomerServer.addcustomer(uri, token);
		Object extract2 = JsonPathUtil.extract(addcustomer, "$.code");
		Assert.assertEquals(extract2 , "0");
		Object extract = JsonPathUtil.extract(addcustomer, "$.data.customerName");
		Assert.assertEquals(extract, "客户123");
		Object customerId = JsonPathUtil.extract(addcustomer, "$.data.customerId");
		Props.putObject("customerId", customerId);
		
	}
	
//	@Test (description = "添加客户名称为空")
	public void test002_AddCustomer() throws Exception {
		String addcustomer = AddCustomerServer.addcustomer(uri, token, "$.entity.customer_name", "");
		Object extract = JsonPathUtil.extract(addcustomer, "$.code");
		Assert.assertEquals(extract, "0");
	}
	
//	@Test (description = "添加客户名称字段缺失")
	public void test003_AddCustomer() throws Exception {
		String addcustomer = AddCustomerServer.addCustomer(uri, token, "$.entity.customer_name");
		Object extract = JsonPathUtil.extract(addcustomer, "$.code");
		Assert.assertEquals(extract, "500");
	}
}
