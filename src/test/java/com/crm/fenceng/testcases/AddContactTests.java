package com.crm.fenceng.testcases;
/**
* @author 迨
* @version Nov 24, 2021 2:42:18 PM
*/

import org.testng.Assert;
import org.testng.annotations.Test;

import com.api.util.JsonPathUtil;
import com.crm.server.AddContactServer;

public class AddContactTests extends TestBase {

	@Test(description = "正常添加联系人")
	public void test001_addcontact() throws Exception {
		String addContact = AddContactServer.addContact(uri, token);
		Object extract = JsonPathUtil.extract(addContact, "$.code");
		Assert.assertEquals(extract, "0");		
	}
	@Test(description = "联系人名字为空")
	public void test002_addcontact() throws Exception {
		String addContact = AddContactServer.addContact(uri, token,"$.entity.name","");
		Object extract = JsonPathUtil.extract(addContact, "$.code");
		Assert.assertEquals(extract, "0");
	}
	
	@Test(description = "缺失字段customer_id")
	public void test003_addcontact() throws Exception {
		String addContact = AddContactServer.addContact(uri, token,"$.entity.name","");
		Object extract = JsonPathUtil.extract(addContact, "$.code");
		Assert.assertEquals(extract, "0");
	}
}
