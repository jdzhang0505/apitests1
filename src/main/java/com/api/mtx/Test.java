package com.api.mtx;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.annotation.processing.Filer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.alibaba.fastjson.JSONReader;



/**
* @author 迨
* @version Nov 19, 2021 2:58:35 PM
*/
public class Test {

	public static void main(String[] args) throws Exception {
		JSONReader jsonReader=new JSONReader(new FileReader(new File("src/main/resources/crmparam/addcustomer.json")));
		String readString = jsonReader.readString();		
		System.out.println(readString);
		JSONObject jsonObject=JSONObject.parseObject(readString);
	    JSONPath.set(jsonObject, "$.entity.address", "天津市河北区");
	    System.out.println(jsonObject.toString());
		
	}
}
