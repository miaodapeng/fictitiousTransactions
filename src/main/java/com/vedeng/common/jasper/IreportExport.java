package com.vedeng.common.jasper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.constants.Contant;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import org.apache.ibatis.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.vedeng.common.model.FileInfo;
import com.vedeng.common.util.DateUtil;
import com.vedeng.order.service.SaleorderService;
import com.vedeng.saleperformance.model.SalesPerformanceOrders;
import com.vedeng.system.model.Attachment;
import com.vedeng.system.service.AttachmentService;
import com.vedeng.system.service.FtpUtilService;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class IreportExport {
	public static Logger logger = LoggerFactory.getLogger(IreportExport.class);

	// 运行时注入service
    WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
    private FtpUtilService ftpUtilService = (FtpUtilService) context.getBean("ftpUtilService");
    private AttachmentService attachmentService = (AttachmentService) context.getBean("attachmentService");
	
	public static boolean exportWrite(HttpServletRequest request,HttpServletResponse response,String jrxmlPath,List<?> list,String fileName){
		ServletOutputStream ouputStream = null;
		try {
			ouputStream = response.getOutputStream();
			JasperDesign jasperDesign = JRXmlLoader.load(request.getSession().getServletContext().getRealPath(jrxmlPath));
			
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			
			if(list != null && list.size() > 0){

				JRDataSource dataSource = new JRBeanCollectionDataSource(list);
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);

				// response.setContentType("application/vnd.ms-excel");
				// response.setHeader("Content-disposition", "attachment; filename="+ new String("export.xls".getBytes("gbk"), "utf-8"));

				JRAbstractExporter exporter = null;
				if(list.size() >= 65535){
					exporter = new JRXlsxExporter();
				}else{
					exporter = new JRXlsExporter();
				}

				exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);// 写出内容
				exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, response.getOutputStream());// 输出流
				exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);// 可分多页显示
				exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);// 白色底色
				exporter.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
				exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);//导出Excel列表中（BigDecimal、Number、Integer、double等）以数字格式显示
				response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO-8859-1"));
				response.setContentType("application/vnd_ms-excel");
				exporter.exportReport();
				
			}else{
				response.setHeader("Content-type", "text/html;charset=UTF-8");
				response.setContentType("multipart/form-data");
				response.setHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes("GBK"), "ISO-8859-1"));
			}
			if(list != null){
				list.clear();
			}
			ouputStream.flush();
			ouputStream.close();
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return false;
		} finally {
			if (ouputStream != null) {
				try {
					ouputStream.close();
				} catch (IOException e) {
					logger.error(Contant.ERROR_MSG, e);
					return false;
				}
			}
		}
		return true;
	}
	
	public static boolean exportWrite(HttpServletRequest request,HttpServletResponse response,String jrxmlPath,JRDataSource dataSource,String fileName){
		ServletOutputStream ouputStream = null;
		try {
			ouputStream = response.getOutputStream();
			JasperDesign jasperDesign = JRXmlLoader.load(request.getSession().getServletContext().getRealPath(jrxmlPath));
			
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			
			/*HashMap<String,Object> jasperParams = new HashMap<>();
			User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
			jasperParams.put("exportUserName", user.getUsername());
			jasperParams.put("exportDate", DateUtil.getNowDate(null));
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, jasperParams, dataSource);*/
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
			
			// response.setContentType("application/vnd.ms-excel");
			// response.setHeader("Content-disposition", "attachment; filename="+ new String("export.xls".getBytes("gbk"), "utf-8"));
			
			JRXlsExporter exporter = new JRXlsExporter();
			exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);// 写出内容
			exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, response.getOutputStream());// 输出流
			exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);// 可分多页显示
			exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);// 白色底色
			exporter.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO-8859-1"));
			response.setContentType("application/vnd_ms-excel");
			exporter.exportReport();
			ouputStream.flush();
			ouputStream.close();
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return false;
		} finally {
			if (ouputStream != null) {
				try {
					ouputStream.close();
				} catch (IOException e) {
					logger.error(Contant.ERROR_MSG, e);
					return false;
				}
			}
		}
		return true;
	}
	
	public static Model exportOut(Model model,String jasperPath,JRDataSource jrDataSource,String format){
		if(model == null){
			return null;
		}
		// 动态指定报表模板url  
		model.addAttribute("url", jasperPath);///WEB-INF/ireport/jasper/accountPeriod.jasper
		model.addAttribute("format", format); // 报表格式  pdf，xls，rtf，html，csv
		model.addAttribute("jrDataSource", jrDataSource);
		return model;
	}
	
	
	public static boolean exportWrite2(HttpServletRequest request,HttpServletResponse response,String jrxmlPath,List<?> list,String fileName){
		ServletOutputStream ouputStream = null;
		try {
			ouputStream = response.getOutputStream();
			JasperDesign jasperDesign = JRXmlLoader.load(request.getSession().getServletContext().getRealPath(jrxmlPath));
			
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			
			if(list != null && list.size() > 0){
				
				JRDataSource dataSource = new JRBeanCollectionDataSource(list);
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
				
				// response.setContentType("application/vnd.ms-excel");
				// response.setHeader("Content-disposition", "attachment; filename="+ new String("export.xls".getBytes("gbk"), "utf-8"));
				
				JRXlsExporter exporter = new JRXlsExporter();
				exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);// 写出内容
//				exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, response.getOutputStream());// 输出流
//				exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);// 可分多页显示
//				exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);// 白色底色
//				exporter.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
				exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);//导出Excel列表中（BigDecimal、Number、Integer、double等）以数字格式显示
				//response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO-8859-1"));
				//response.setContentType("application/vnd_ms-excel");
				exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME,"d:/filenameByte.xlsx");
				exporter.exportReport();
			}else{
				response.setHeader("Content-type", "text/html;charset=UTF-8");
				response.setContentType("multipart/form-data");
				response.setHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes("GBK"), "ISO-8859-1"));
			}
			if(list != null){
				list.clear();
			}
			
			try {

				 

	            String content = "This is the content to write into file";

	 

	            File file = new File("d:/filenameByte.txt");

	 

	            // if file doesnt exists, then create it

	            if (!file.exists()) {

	                file.createNewFile();

	            }

	            FileOutputStream fop = new FileOutputStream(file, true);

	            fop.write(ouputStream.toString().getBytes());

	            fop.flush();

	            fop.close();

	            System.out.println("Done");

	 

	        } catch (IOException e) {

	            logger.error(Contant.ERROR_MSG, e);

	        }

			

			ouputStream.flush();
			ouputStream.close();
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return false;
		} finally {
			if (ouputStream != null) {
				try {
					ouputStream.close();
				} catch (IOException e) {
					logger.error(Contant.ERROR_MSG, e);
					return false;
				}
			}
		}
		return true;
	}

	public static void main(String[] args) throws IOException {
		System.out.println( Resources.getResourceAsFile("ireport/jrxml/").getAbsolutePath());
	}
	public boolean generateSaleorderData(String jrxmlPath, List<SalesPerformanceOrders> list, String fileName) throws IOException, JRException {
		
		
//		String path = this.getClass().getClassLoader().getResource("").getPath().substring(1);
//		path = path.substring(0, path.length()-17);
		
		String classpath = this.getClass().getResource("/").getPath().replaceFirst("/", "");
		String path = classpath.replaceAll("/WEB-INF/classes/", "");
		
		System.out.println(path);

		try {
			File  path1= Resources.getResourceAsFile("ireport/jrxml/salesPerformanceOrders.jrxml");
			JasperDesign jasperDesign = JRXmlLoader.load(path1);
			
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

		File file = new File(Resources.getResourceAsFile("ireport/jrxml/").getAbsolutePath() + "/salesperformanceorder.xls");

            if (!file.exists()) {
                file.createNewFile();
            }
            
			if(list != null && list.size() > 0){
				
				
				JRDataSource dataSource = new JRBeanCollectionDataSource(list);
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
				
				// response.setContentType("application/vnd.ms-excel");
				// response.setHeader("Content-disposition", "attachment; filename="+ new String("export.xls".getBytes("gbk"), "utf-8"));
				
				JRXlsExporter exporter = new JRXlsExporter();
				exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);// 写出内容
//				exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, response.getOutputStream());// 输出流
//				exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);// 可分多页显示
//				exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);// 白色底色
//				exporter.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
				exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);//导出Excel列表中（BigDecimal、Number、Integer、double等）以数字格式显示
				//response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO-8859-1"));
				//response.setContentType("application/vnd_ms-excel");
				exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, Resources.getResourceAsFile("ireport/jrxml/").getAbsolutePath() + "/salesperformanceorder.xls");
				exporter.exportReport();
				
				
			}else{
				//response.setHeader("Content-type", "text/html;charset=UTF-8");
				//response.setContentType("multipart/form-data");
				//response.setHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes("GBK"), "ISO-8859-1"));
			}
			
			String dateString = DateUtil.convertString(DateUtil.getDateBefore(new Date(), 1), "yyyy-MM-dd");
			FileInfo fileInfo = ftpUtilService.exeUploadFileToFtp(Resources.getResourceAsFile("ireport/jrxml/").getAbsolutePath() + "/salesperformanceorder.xls", "/salesperformance", "salesperformanceorder"+dateString+".xls");
			
			if (fileInfo.getCode() == 0) {
				Attachment attachment = new Attachment();
				attachment.setAttachmentFunction(753);
				attachment.setAttachmentType(461);
				attachment.setName(fileInfo.getFileName());
				attachment.setDomain(fileInfo.getHttpUrl());
				attachment.setUri(fileInfo.getFilePath()+"/"+fileInfo.getFileName());
				attachment.setAddTime(DateUtil.sysTimeMillis());
				attachmentService.saveOrUpdateAttachment(attachment);
			}
			
			if(list != null){
				list.clear();
			}
			
		} catch (Exception e) {
			throw e;
		} finally {
		}
		return true;
	}
	
	public boolean generateSaleorderData22(HttpServletRequest request, HttpServletResponse response, String jrxmlPath,
			List<SalesPerformanceOrders> list, String fileName) {
		ServletOutputStream ouputStream = null;
		try {
			ouputStream = response.getOutputStream();
			JasperDesign jasperDesign = JRXmlLoader.load(request.getSession().getServletContext().getRealPath(jrxmlPath));
			
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			
			File file = new File(request.getSession().getServletContext().getRealPath("/WEB-INF/ireport/jrxml/salesperformanceorder.xls"));

            if (!file.exists()) {
                file.createNewFile();
            }
            
			if(list != null && list.size() > 0){
				
				
				JRDataSource dataSource = new JRBeanCollectionDataSource(list);
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
				
				// response.setContentType("application/vnd.ms-excel");
				// response.setHeader("Content-disposition", "attachment; filename="+ new String("export.xls".getBytes("gbk"), "utf-8"));
				
				JRXlsExporter exporter = new JRXlsExporter();
				exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);// 写出内容
//				exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, response.getOutputStream());// 输出流
//				exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);// 可分多页显示
//				exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);// 白色底色
//				exporter.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
				exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);//导出Excel列表中（BigDecimal、Number、Integer、double等）以数字格式显示
				//response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO-8859-1"));
				//response.setContentType("application/vnd_ms-excel");
				exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, request.getSession().getServletContext().getRealPath("/WEB-INF/ireport/jrxml/salesperformanceorder.xls"));
				exporter.exportReport();
			}else{
				//response.setHeader("Content-type", "text/html;charset=UTF-8");
				//response.setContentType("multipart/form-data");
				//response.setHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes("GBK"), "ISO-8859-1"));
			}
			
			String dateString = DateUtil.convertString(DateUtil.getDateBefore(new Date(), 1), "yyyy-MM-dd");
			FileInfo fileInfo = ftpUtilService.exeUploadFileToFtp(request.getSession().getServletContext().getRealPath("/WEB-INF/ireport/jrxml/salesperformanceorder.xls"), "/salesperformance", "salesperformanceorder"+dateString+".xls");
			
			if (fileInfo.getCode() == 0) {
				Attachment attachment = new Attachment();
				attachment.setAttachmentFunction(753);
				attachment.setAttachmentType(461);
				attachment.setName(fileInfo.getFileName());
				attachment.setDomain(fileInfo.getHttpUrl());
				attachment.setUri(fileInfo.getFilePath()+"/"+fileInfo.getFileName());
				attachment.setAddTime(DateUtil.sysTimeMillis());
				attachmentService.saveOrUpdateAttachment(attachment);
			}
			
			if(list != null){
				list.clear();
			}
			
			ouputStream.flush();
			ouputStream.close();
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return false;
		} finally {
			if (ouputStream != null) {
				try {
					ouputStream.close();
				} catch (IOException e) {
					logger.error(Contant.ERROR_MSG, e);
					return false;
				}
			}
		}
		return true;
	}
	
    /*List<TraderAccountPeriodApply> accountPeriodApplyList = accountPeriodService.exportAccountPeriodApplyList(tapa);
    JRDataSource dataSource = new JRBeanCollectionDataSource(accountPeriodApplyList);  
    // 实际中编译报表很耗时,采用Ireport编译好的报表
    JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile("E:\\账期申请.jasper");
    // 填充报表
    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
    // JasperExportManager.exportReportToHtmlFile(jasperPrint,
    // "test.html");
    JasperViewer jasperViewer = new JasperViewer(jasperPrint);
    jasperViewer.setVisible(true);*/
}
