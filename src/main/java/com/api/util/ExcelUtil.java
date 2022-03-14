package com.api.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
* @author 迨
* @version Nov 25, 2021 3:35:26 PM
*/



public class ExcelUtil {
  private static Logger logger=Logger.getLogger(ExcelUtil.class);
  
  public XSSFWorkbook excelWorkbook;
  public XSSFSheet excelSheet;
  public Row row;
  public Cell cell;
  
  public ExcelUtil(String excelPath) throws Exception {
	  FileInputStream fileInputStream=new FileInputStream(new File(excelPath));
	  this.excelWorkbook=new XSSFWorkbook(fileInputStream);
  }
  
  public String getCellData(String sheetName,int rownum,int colnum) {
	  String cellValue="";
	  excelSheet = excelWorkbook.getSheet(sheetName);
	  row = excelSheet.getRow(rownum);
	  cell = row.getCell(colnum);
	  try {
		  if(cell.getCellType()==XSSFCell.CELL_TYPE_BOOLEAN) {
			  cellValue=String.valueOf(cell.getBooleanCellValue()) ;
		  }else if(cell.getCellType()==XSSFCell.CELL_TYPE_STRING) {
			  cellValue=cell.getStringCellValue();
		  }else if(cell.getCellType()==XSSFCell.CELL_TYPE_NUMERIC) {
			  short format = cell.getCellStyle().getDataFormat();
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					Date d = cell.getDateCellValue();
					DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
					cellValue = formater.format(d);
				} else if (format == 14 || format == 31 || format == 57 || format == 58) {
					// 日期
					DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
					Date date = DateUtil.getJavaDate(cell.getNumericCellValue());
					cellValue = formater.format(date);
				} else if (format == 20 || format == 32) {
					// 时间
					DateFormat formater = new SimpleDateFormat("HH:mm");
					Date date = DateUtil.getJavaDate(cell.getNumericCellValue());
					cellValue = formater.format(date);
				} else {
					DecimalFormat df = new DecimalFormat("0");
					cellValue = df.format(cell.getNumericCellValue());
				}
		  }else if(cell.getCellType()==XSSFCell.CELL_TYPE_BLANK) {
			  cellValue="";
		  }
		  logger.info("读取【"+sheetName+"】第"+rownum+"行第"+colnum+"列的值是："+cellValue);
	} catch (Exception e) {
		// TODO: handle exception
		logger.error(e);
		new RuntimeException(e);
	}
	  return cellValue;
	  
  }

  public Object[][] getSheetData(String sheetName){
	  int rowCount = excelWorkbook.getSheet(sheetName).getLastRowNum();
	  // 获取的colCount值计数是从1开始的，有几列，colCount就是几；但是获取某行某列单元格的值即调用getCellData(String sheetName,int rownum,int colnum)时，行列计数都是从0开始
	  int colCount = excelWorkbook.getSheet(sheetName).getRow(0).getLastCellNum();
	  
	  System.out.println("rowCount:"+rowCount);
	  System.out.println("colCount:"+colCount);
	  
	  Object[][] data=new Object[rowCount][colCount];
	  for (int i = 1; i <= rowCount; i++) {
		for (int j = 0; j < colCount; j++) {
			String cellData = getCellData(sheetName, i, j);
			data[i-1][j]=cellData;
		}
	}
	  return data;
  }
  
  public void close() {
	  try {
		excelWorkbook.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
  
  public static void main(String[] args) throws Exception {
	String parampath="src/main/resources/crmparam/crmdata.xlsx";
	ExcelUtil excelUtil=new ExcelUtil(parampath);
//	excelUtil.getCellData("新建产品", 4, 2);
	Object[][] sheetData = excelUtil.getSheetData("新建产品");
	for (int i = 0; i < sheetData.length; i++) {
		for (int j = 0; j < sheetData[i].length; j++) {
			System.out.println(sheetData[i][j]);
		}
	}
}
}
