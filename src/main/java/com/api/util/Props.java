package com.api.util;
/**
* @author 迨
* @version Dec 21, 2021 2:32:24 PM
*/

import java.util.HashMap;
import java.util.Map;

public class Props {
  private static Map<String, Object> propMap=new HashMap<String, Object>();
  
  public void clear() {
	  propMap.clear();  
  }
  
  public static Object getObject(String key) {
	  return propMap.get(key);
  }
  
  public static Map<String, Object> putObject(String key,Object value) {
	  propMap.put(key, value);
	  return propMap;
  }
  
  public static String getString(String key) {
	 return ""+propMap.get(key);
  }
  
  public static Map<String, Object> remove(String key) {
	  propMap.remove(key);
	  return propMap;
  }
}
