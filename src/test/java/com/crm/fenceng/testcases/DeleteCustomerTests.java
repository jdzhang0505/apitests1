package com.crm.fenceng.testcases;
/**
* @author 迨
* @version Nov 24, 2021 3:30:03 PM
*/

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import com.api.util.DBUtil;
import com.api.util.JsonPathUtil;
import com.crm.server.AddContactServer;
import com.crm.server.AddCustomerServer;
import com.crm.server.DeleteContactsServer;
import com.crm.server.DeleteCustomerServer;

public class DeleteCustomerTests extends TestBase{

	String customerid;
	
	@Test(description = "直接删除关联了联系人的客户")
	public void test001_deleteCustomer() throws Exception {
		//首先添加一个客户
		String addcustomer = AddCustomerServer.addcustomer(uri, token);
		Object extract = JsonPathUtil.extract(addcustomer, "$.code");
	    customerid = JsonPathUtil.extract(addcustomer, "$.data.customerId").toString();
		Assert.assertEquals(extract, "0");
		
		//添加一个关联该客户的联系人
		String addContact = AddContactServer.addContact(uri, token, "$.entity.customer_id", customerid);
		Object extract2 = JsonPathUtil.extract(addContact, "$.code");
		Assert.assertEquals(extract2, "0");
		
		//删除有关联联系人的该客户
		String deleteCustomer = DeleteCustomerServer.deleteCustomer(uri, token, "customerIds", customerid);
		Object extract3 = JsonPathUtil.extract(deleteCustomer, "$.msg");
		Assert.assertEquals(extract3, "该条数据与其他数据有必要关联，请勿删除");		
	}
	
	@Test(description = "删除关联联系人的客户时，先删除联系人再删除该客户")
	public void test002_deleteCustomer() throws Exception {
		String addcustomer = AddCustomerServer.addcustomer(uri, token);
		Object extract = JsonPathUtil.extract(addcustomer, "$.code");
		Assert.assertEquals(extract, "0");
		customerid = JsonPathUtil.extract(addcustomer, ".data.customerId").toString();
		
		//添加关联该客户的联系人
		String addContact = AddContactServer.addContact(uri, token, "$.entity.customer_id", customerid);
		Object extract2 = JsonPathUtil.extract(addContact, "$.code");
		Assert.assertEquals(extract2, "0");
		
		//删除关联客户的联系人
		List<Map> data = DBUtil.getData("select contacts_id,name FROM 72crm_crm_contacts where customer_id="+customerid);
		String contactsid = data.get(0).get("contacts_id").toString();
		
		String deleteContacts = DeleteContactsServer.deleteContacts(uri, token, "contactsIds", contactsid);
		Object extract3 = JsonPathUtil.extract(deleteContacts , "$.code");
		Assert.assertEquals(extract3, "0");
		
		//最后删除该客户
		String deleteCustomer = DeleteCustomerServer.deleteCustomer(uri, token, "customerIds", customerid);
		Object extract4 = JsonPathUtil.extract(deleteCustomer, "$.code");
		Assert.assertEquals(extract4, "0");
		
	}
}
