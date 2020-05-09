package com.vedeng.report.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vedeng.common.controller.BaseController;
import com.vedeng.common.util.DateUtil;

@Controller
@RequestMapping("/import")
public class ImportController extends BaseController {

	@RequestMapping("excel")
	public void readExcel(HttpServletRequest request) throws Exception {
		String excelPath = "E://abc.xlsx";
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		// 获取excel路径
		Workbook workbook = WorkbookFactory.create(new FileInputStream(new File(excelPath)));
		// 获取第一面sheet
		Sheet sheet = workbook.getSheetAt(0);
		// 起始行
		int startRowNum = sheet.getFirstRowNum() + 1;
		int endRowNum = sheet.getLastRowNum();// 结束行
		List<String> list = null;// 存放每一行数据
		
		for (int rowNum = startRowNum; rowNum <= endRowNum; rowNum++) {// 循环行数
			list = new ArrayList<String>();
			Row row = sheet.getRow(rowNum);
			int startCellNum = row.getFirstCellNum();// 起始列
			int endCellNum = row.getLastCellNum() - 1;// 结束列
			for (int cellNum = startCellNum; cellNum <= endCellNum; cellNum++) {// 循环列数（下表从0开始）
				Cell cell = row.getCell(cellNum);
				/*
				 if(cellNum==0){//第一列数据(--第二列数据cellNum==1) 
				 	Double b = cell.getNumericCellValue(); 
				 	Date date = cell.getDateCellValue(); 
				 	String s = cell.getStringCellValue(); 
				 }
				 */
				// 获取excel的值
				String str = "";
				try {
					if (cell == null) {
						//读取列错误（或者默认空白）
					}
					/*int type;
					if (cell == null) {
						type = 3;// 若excel中无内容，而且没有空格，cell为空;;;;默认3，空白
					} else {
						type = cell.getCellType();
					}*/
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:// 数值、日期类型
						double d = cell.getNumericCellValue();
						if (HSSFDateUtil.isCellDateFormatted(cell)) {
							Date date = HSSFDateUtil.getJavaDate(d);
							// str = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date)).toString();
							str = DateUtil.DateToString(date, "yyyy-MM-dd HH:mm:ss");
						} else {// 数值类型
							if(cellNum==7){//要求第7列必须为日期
								throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列非日期类型，请验证！");
							}
							str = ((int) cell.getNumericCellValue()) + "";
						}
						break;
					case Cell.CELL_TYPE_BLANK:// 空白单元格
						str = "";
						break;
					case Cell.CELL_TYPE_STRING:// 字符类型
						str = cell.getStringCellValue().toString();
						break;
					case Cell.CELL_TYPE_FORMULA:// 公式 (读取的时候回执行公式，将计算的结果读取出来)
						//取值方式，以下方式：待测试
						cell.getCellFormula();
						//or
						
						// 判断公式计算的结果是否为Date
						if (HSSFDateUtil.isCellDateFormatted(cell)) {
							// 把Date转换成本地格式的字符串
							str = DateUtil.DateToString(cell.getDateCellValue(), "yyyy-MM-dd HH:mm:ss");
						} else {
							// 取得当前Cell的数值
							double num = new Double((double) cell.getNumericCellValue());
							str = String.valueOf(num);
						}
						break;
					case Cell.CELL_TYPE_BOOLEAN:// 布尔类型
						cell.getBooleanCellValue();
						break;
					/*
					 case Cell.CELL_TYPE_ERROR://错误类型 
					 	byte value = cell.getErrorCellValue(); 
					 	break;
					 */
					default:
						throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列类型未知，请验证！");
					}
				} catch (Exception e) {
					logger.error("import excel", e);
					throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列存在错误，请验证！");
				}
				list.add(str);
				map.put(rowNum + "", list);
				if(!list.isEmpty()){//空集合长度为0，0%1000=0
					if (list.size() % 1000 == 0) {// 每1000条执行一次保存操作
						list.clear();// 防止数据量过大，内存泄漏
					}
				}
			}
		}
		System.out.println(map);
	}
}
