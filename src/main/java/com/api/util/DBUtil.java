package com.api.util;
/**
* @author 迨
* @version Nov 5, 2021 3:52:13 PM
*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;


public class DBUtil {

	public static Connection connection;
	public static Logger logger=Logger.getLogger(DBUtil.class);
	
	public static void getConnect(String url,String user,String password) {
		
		try {
			connection=DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			e.printStackTrace();
		}	
	}
	
	public static void close() {
		if(connection!=null)
		try {
			connection .close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
   //查询数据
	public static List<Map> getData(String sql){
		List<Map> list=new ArrayList<Map>();
		try {
			PreparedStatement prepareStatement = connection.prepareStatement(sql);
			ResultSet executeQuery = prepareStatement.executeQuery();
			ResultSetMetaData metaData = executeQuery.getMetaData();
			int columnCount = metaData.getColumnCount();
			while(executeQuery.next()) {
				Map<String, Object> line=new HashMap<String, Object>();
				for (int i = 1; i <= columnCount; i++) {
					String columnLabel = metaData.getColumnLabel(i);
					Object object = executeQuery.getObject(i);
					System.out.println(columnLabel+"="+object.toString());
					line.put(columnLabel, object);
				}
				list.add(line);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	//以下方法可以执行update,delete,insert
	public static int executeUpdate(String sql) {
		Statement stmt=null;
		try {
			stmt= connection.createStatement();
			stmt.execute(sql);
			int updateCount = stmt.getUpdateCount();
			return updateCount;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(stmt!=null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return 0;
	}
	

  public static void main(String[] args){
	getConnect("jdbc:mysql://192.168.96.76:3306/mtx?tinyInt1isBit=false", "root", "123456");
	List<Map> data = getData("SELECT content,rating,user_id,is_show FROM `s_goods_comments` ORDER BY id DESC LIMIT 1;");
	for (Map map : data) {
		Set<Entry<String, Object>> entrySet = map.entrySet();
	    for(Entry entry:entrySet) {
	    	System.out.println(entry.getKey()+"="+entry.getValue());
	    }		
	}
	close();
}
}
