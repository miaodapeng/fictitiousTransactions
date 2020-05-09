package com.vedeng.report.controller;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vedeng.authorization.model.Action;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.util.DateUtil;
import com.vedeng.report.service.ExportService;

@Controller
@RequestMapping("/export")
public class ExportController extends BaseController {

	@Autowired
	@Qualifier("exportService")
	private ExportService exportService;

	/**
	 * <b>Description:</b><br>
	 * 测试报表导出
	 * 
	 * @param request
	 * @param response
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年5月8日 上午10:55:19
	 */
	@RequestMapping(value = "/testexport")
	public void export(HttpServletRequest request, HttpServletResponse response) {

		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setContentType("multipart/form-data");
		String fileName = System.currentTimeMillis() + ".xlsx";
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		try {
			int sheetNum = 1;// 工作薄sheet编号
			int currentRowCount = 1;// 当前的行号
			int bodyRowCount = 1;// 正文内容行号
			int perPageNum = 8000;// 每个工作薄显示8000条数据

			OutputStream out = response.getOutputStream();
			SXSSFWorkbook wb = new SXSSFWorkbook(10);//内存中保留 x 条数据，以免内存溢出，其余写入 硬盘
			Sheet sh = wb.createSheet("工作簿" + sheetNum);
			writeTitleContent(sh);

			Row row_value = null;
			Cell cel_value = null;

			// List<Action> list = reportService.selectList();
			List<Action> list = new ArrayList<>();

			// ------------------定义表头----------------------------------------
			int page_size = 4000;// 数据库中每次查询条数
			int list_count = exportService.getListCount();
			int export_times = list_count % page_size > 0 ? list_count / page_size + 1 : list_count / page_size;
			for (int i = 0; i < export_times; i++) {
				list = exportService.queryListSize(page_size * i, page_size);
				int len = list.size() < page_size ? list.size() : page_size;
				for (int j = 0; j < len; j++) {
					// Row row_value = sh.createRow(j * page_size + i + 1);
					row_value = sh.createRow(bodyRowCount);

					cel_value = row_value.createCell(0);
					if (list.get(j).getActiongroupId() instanceof Integer) {// 判断对象类型
						cel_value.setCellType(XSSFCell.CELL_TYPE_NUMERIC);// 设置文本框类型
					}
					cel_value.setCellValue(list.get(j).getActiongroupId());
					
					sh.setColumnWidth(1, 4000);// 设置第二列宽度
					cel_value = row_value.createCell(1);
					cel_value.setCellValue(list.get(j).getControllerName());
					
					sh.setColumnWidth(2, 6000);// 设置第三列宽度
					cel_value = row_value.createCell(2);
					cel_value.setCellValue(list.get(j).getActionName());
					
					cel_value = row_value.createCell(3);
					cel_value.setCellValue(list.get(j).getModuleName());
					CellRangeAddress cra = new CellRangeAddress(j+1, (short)(j+1), 3, (short)4);//合并单元格：参数：起始行号，终止行号， 起始列号，终止列号
					sh.addMergedRegion(cra);
					
					cel_value = row_value.createCell(5);
					cel_value.setCellValue(list.get(j).getActionDesc());
					
					cel_value = row_value.createCell(7);
					cel_value.setCellType(XSSFCell.CELL_TYPE_STRING);
					cel_value.setCellValue(list.get(j).getIsMenu() == 1 ? "是" : "否");
					
					cel_value = row_value.createCell(8);
					cel_value.setCellType(XSSFCell.CELL_TYPE_STRING);
					String addTime = DateUtil.convertString(list.get(j).getAddTime(), "yyyy-MM-dd HH:mm:ss");
					sh.setColumnWidth(8, addTime.length() * 300);// 设置第六列宽度
					cel_value.setCellValue(addTime);

					/*例如：每頁5条，总共10条，会生成3个工作簿，因为第二页最后一次循环时，当页是5条，下面公式成立*/
					if (currentRowCount % perPageNum == 0) {// 每个工作薄显示8000条数据
						sh = null;
						sheetNum++;// 工作薄编号递增1
						bodyRowCount = 0;// 正文内容行号置位为0
						sh = wb.createSheet("工作簿" + sheetNum);// 创建一个新的工作薄
						// setSheetColumn(sh);//设置工作薄列宽
						writeTitleContent(sh);// 写入标题
					}
					currentRowCount++;// 当前行号递增1
					bodyRowCount++;
				}
				list.clear();
			}

			wb.write(out);
			out.close();
			wb.dispose();

			/*
			 * FileOutputStream fileOut = new FileOutputStream("E://1211.xlsx");
			 * wb.write(fileOut); fileOut.close(); wb.dispose();
			 */
		} catch (Exception e) {
			logger.error("export testexport", e);
		}
	}

	public void writeTitleContent(Sheet sh) {
		Row row = sh.createRow(0);
		Cell cel = null;
		CellRangeAddress cra = null;
		// --------------------------------------------------
		cel = row.createCell(0);
		cel.setCellValue("功能分組");
		
		cel = row.createCell(1);
		cel.setCellValue("控制器名稱");
		
		cel = row.createCell(2);
		cel.setCellValue("功能名稱");
		
		cel = row.createCell(3);
		cel.setCellValue("模塊名稱");
		cra = new CellRangeAddress(0, (short)0, 3, (short)4);//合并单元格：参数：起始行号，终止行号， 起始列号，终止列号
		sh.addMergedRegion(cra);
		
		cel = row.createCell(5);
		cel.setCellValue("功能描述");
		cra = new CellRangeAddress(0, (short)0, 5, (short)6);//合并单元格：参数：起始行号，终止行号， 起始列号，终止列号
		sh.addMergedRegion(cra);
		
		cel = row.createCell(7);
		cel.setCellValue("是否菜单");
		
		cel = row.createCell(8);
		cel.setCellValue("添加时间");
	}
}
