package com.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.vedeng.common.constant.SysOptionConstant;

public class TestExport {
	public static void main(String[] args){
		/*
		 * // 创建workbook HSSFWorkbook workbook = new HSSFWorkbook(); // 创建sheet页
		 * HSSFSheet sheet = workbook.createSheet("学生表"); // 创建单元格 HSSFRow row =
		 * sheet.createRow(0); HSSFCell c0 = row.createCell(0);
		 * c0.setCellValue(new HSSFRichTextString("学号")); HSSFCell c1 =
		 * row.createCell(1); c1.setCellValue(new HSSFRichTextString("姓名"));
		 * HSSFCell c2 = row.createCell(2); c2.setCellValue(new
		 * HSSFRichTextString("性别")); HSSFCell c3 = row.createCell(3);
		 * c3.setCellValue(new HSSFRichTextString("年龄")); HSSFCell c4 =
		 * row.createCell(4); c4.setCellValue(new
		 * HSSFRichTextString("2015年分数")); HSSFCell c5 = row.createCell(7);
		 * c5.setCellValue(new HSSFRichTextString("2014年分数"));
		 * 
		 * HSSFRow row1 = sheet.createRow(1); HSSFCell c6 = row1.createCell(4);
		 * c6.setCellValue(new HSSFRichTextString("语文")); HSSFCell c7 =
		 * row1.createCell(5); c7.setCellValue(new HSSFRichTextString("数学"));
		 * HSSFCell c8 = row1.createCell(6); c8.setCellValue(new
		 * HSSFRichTextString("外语")); HSSFCell c9 = row1.createCell(7);
		 * c9.setCellValue(new HSSFRichTextString("语文")); HSSFCell c10 =
		 * row1.createCell(8); c10.setCellValue(new HSSFRichTextString("数学"));
		 * HSSFCell c11 = row1.createCell(9); c11.setCellValue(new
		 * HSSFRichTextString("外语"));
		 * 
		 * Region region1 = new Region(0, (short) 0, 1, (short) 0); Region
		 * region2 = new Region(0, (short) 1, 1, (short) 1); Region region3 =
		 * new Region(0, (short) 2, 1, (short) 2); Region region4 = new
		 * Region(0, (short) 3, 1, (short) 3); Region region5 = new Region(0,
		 * (short) 4, 0, (short) 6); Region region6 = new Region(0, (short) 7,
		 * 0, (short) 9); sheet.addMergedRegion(region1);
		 * sheet.addMergedRegion(region2); sheet.addMergedRegion(region3);
		 * sheet.addMergedRegion(region4); sheet.addMergedRegion(region5);
		 * sheet.addMergedRegion(region6);
		 * 
		 * FileOutputStream stream = new FileOutputStream("e:/student.xls");
		 * workbook.write(stream);
		 */

//		int year = 2012;
//		int maxDay = getMaxDaysOfYear(year);
//		System.out.println("Year " + year + " has " + maxDay + " days!");
		System.out.println("------------------");
		System.out.println("中国 安徽 阜阳 阜南".replaceAll("中国 ","").split(" ")[0]);
		long beginTime = System.currentTimeMillis();//1、开始时间  
		for(int i=0;i<1000000;i++){
			System.out.println("i:"+i);
		}
		long endTime = System.currentTimeMillis();//1、开始时间  
		System.out.println("------开始计时: {"+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSSS").format(beginTime)+"}");
		System.out.println("------开始计时: {"+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSSS").format(endTime)+"}");
	}

	private static int getMaxDaysOfYear(int year) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		return cal.getActualMaximum(Calendar.DAY_OF_YEAR);
	}
}
