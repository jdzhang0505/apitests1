package com.crm.fenceng.testcases;
/**
* @author 迨
* @version Dec 22, 2021 4:07:01 PM
*/

import org.testng.Assert;
import org.testng.annotations.Test;

import com.api.util.JsonPathUtil;
import com.api.util.Props;
import com.crm.server.AddContactServer;

public class AddContractTests extends TestBase {

	@Test(description = "正常添加合同")
	public void test001_addContract() throws Exception {
		String addContact = AddContactServer.addContact(uri, token, "$.entity.customer_id", Props.getString("customerId"));
		Object extract = JsonPathUtil.extract(addContact, "$.code");
		Assert.assertEquals(extract, 0);
	}
}
