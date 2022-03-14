package com.crm.fenceng.testcases;
/**
* @author 迨
* @version Nov 29, 2021 3:08:19 PM
*/

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import com.api.util.ExcelUtil;
import com.crm.server.AddProductServer;

public class DataDriverTests extends TestBase {
	String excelPath="src/main/resources/crmparam/crmdata.xlsx";

	@DataProvider
	public Object[][] getAddProductsData() throws Exception{
		ExcelUtil excelUtil=new ExcelUtil(excelPath);
		Object[][] sheetData = excelUtil.getSheetData("新建产品");
		excelUtil.close();
		return sheetData;
	}
    
	@Test(dataProvider = "getAddProductsData")
	public void test001_addProducts(String caseName,String param,String assertValue) throws Exception {
		JSONObject paramObject = JSONObject.parseObject(param);
		String addProduct = AddProductServer.addProduct(uri, token, paramObject);
		Assert.assertTrue(addProduct.contains(assertValue));
	}
}
