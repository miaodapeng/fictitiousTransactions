package com.vedeng.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.common.constants.Contant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.pdf.BaseFont;
import com.vedeng.common.model.FileInfo;
import com.vedeng.system.service.FtpUtilService;


public class ItextFileToPdf {
	public static Logger logger = LoggerFactory.getLogger(ItextFileToPdf.class);

    // 运行时注入service
    WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
    private FtpUtilService ftpUtilService = (FtpUtilService) context.getBean("ftpUtilService");
	
	public String htmlToPdf(String htmlFile, String pdfFile) throws IOException {
		OutputStream os = null;
		try {
			String url = new File(htmlFile).toURI().toURL().toString();
			os = new FileOutputStream(pdfFile);
			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocument(url);

			// 解决中文支持
			ITextFontResolver fontResolver = renderer.getFontResolver();
			if ("linux".equals(getCurrentOperatingSystem())) {
				fontResolver.addFont("/usr/share/fonts/chinese/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			} else {
				fontResolver.addFont("c:/Windows/Fonts/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			}

			renderer.layout();
			renderer.createPDF(os);
			renderer.finishPDF();
			FileInfo fileInfo = ftpUtilService.exeUploadFileToFtp(pdfFile,"sale/contract",null);
			return fileInfo.getHttpUrl()+"/"+fileInfo.getFilePath()+"/"+fileInfo.getFileName();
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			
			return "-1";
		}finally{
			if(os != null){	
				os.close();
			}
			//删除文件
			File file = new File(pdfFile);
			file.delete();
		}
	}

	public static String getCurrentOperatingSystem() {
		String os = System.getProperty("os.name").toLowerCase();
		System.out.println("---------当前操作系统是-----------" + os);
		return os;
	}

}
