package com.vedeng.system.controller;

import com.alibaba.fastjson.JSON;
import com.vedeng.authorization.model.User;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.model.FileInfo;
import com.vedeng.system.service.FtpUtilService;
import com.vedeng.system.service.OssUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping("/fileUpload")
public class FtpUtilController extends BaseController{
	
	@Autowired
	@Qualifier("ftpUtilService")
	private FtpUtilService ftpUtilService;

	@Autowired
	private OssUtilsService ossUtilsService;
	/**
	 * <b>Description:</b><br> 测试上传页面
	 * @param request
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月24日 上午9:40:49
	 */
	@RequestMapping(value = "/uEditorer")
	public ModelAndView uEditorer(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("system/file/edit_uedit");
		return mv;
	}

	/**
	 * <b>Description:</b>上传文件到Oss接口<br>
	 * @param
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:</b> 2020/3/5
	 */

	@RequestMapping(value = "/uploadFile2Oss")
	public void uploadFile2Oss(HttpServletRequest request,HttpServletResponse response,@RequestParam("lwfile")MultipartFile lwfile) throws IOException {
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		FileInfo fileInfo=null;
		if(user!=null){
			  fileInfo= ossUtilsService.upload2Oss(request,lwfile);
		}else{
			fileInfo= new FileInfo(-1,"登录用户不能为空");
		}
		String result=JSON.toJSONString(fileInfo);
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Content-type", "text/html;charset=utf-8");
		response.getWriter().write(result);
		response.getWriter().flush();

	}
	/**
	 * <b>Description:</b><br> ajax异步上传附件操作
	 * @param request
	 * @param response
	 * @param upfile
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月24日 上午9:41:00
	 */
	@ResponseBody
	@RequestMapping(value = "ajaxFileUpload")
	public FileInfo ajaxFileUpload(HttpServletRequest request, HttpServletResponse response, @RequestParam("lwfile") MultipartFile lwfile) {
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			String path = "/upload/ajax";
//			System.out.println(ftpUtilService.exeUploadFileToFtp("E://jsp/test.txt", path,request,""));
			return ftpUtilService.uploadFile(lwfile, path,request,"");
		}else{
			return new FileInfo(-1,"登录用户不能为空");
		}
	}

	@ResponseBody
	@RequestMapping(value = "fileAjaxUpload")
	public FileInfo fileAjaxUpload(HttpServletRequest request, HttpServletResponse response, @RequestParam("lwfile") MultipartFile lwfile) {
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			String path = "/upload/ajax";
			return ftpUtilService.uploadFile(lwfile, path,request,"");
		}else{
			return new FileInfo(-1,"登录用户不能为空");
		}
	}
	

	/**
	 * <b>Description:</b><br> ueditor编辑器上传附件操作
	 * @param request
	 * @param response
	 * @param upfile
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月24日 上午9:41:13
	 */
	@ResponseBody
	@RequestMapping(value = "ueditFileUpload")
	public void imageUpload(HttpServletRequest request, HttpServletResponse response, @RequestParam("upfile") MultipartFile upfile) {
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		String json = "";
		if(user!=null){
			String path = "/upload/ueditor";
			
			FileInfo result = ftpUtilService.uploadFile(upfile, path,request,"");
			if(result.getCode()==0){
				json = "{ \"code\": \"0\",\"state\": \"SUCCESS\",\"message\": \"success\",\"url\": \"" + result.getHttpUrl() + result.getFilePath() + "/" +result.getFileName() + "\",\"title\": \"" + result.getFileName() + "\",\"original\": \"" + upfile.getOriginalFilename() + "\"}";
			}
		}else{
			json = "当前登录系统不能为空";
		}
		try {
			response.setHeader("Content-type", "text/html;charset=UTF-8");  
			//用UTF-8转码，而不是用默认的ISO8859  
			response.setCharacterEncoding("UTF-8");
			PrintWriter pw = response.getWriter();
			pw.write(json);
			pw.close();
//			response.getWriter().write(json);
		} catch (Exception e) {
			logger.error("ueditFileUpload:", e);
		}
	}

	/**
	 * <b>Description:</b><br> 浏览器下载
	 * @param request
	 * @param response
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月25日 上午11:02:21
	 */
	@ResponseBody
	@RequestMapping(value = "downFile")
	public FileInfo downFile(HttpServletRequest request, HttpServletResponse response) {
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			String ftp_path = request.getParameter("filePath").toString();
//		String ftp_path = "/ajax/upload/2017-05/25/image/1495679906000_4903.jpg";
			String domain = "192.168.1.52:8082";
			//String alt = "説到底asa654.png";
			//ftpUtilService.downloadFtpFile(request,ftp_path,alt);//下载到本地
			return ftpUtilService.downFile(domain,ftp_path);
		}else{
			return new FileInfo(-1,"登录用户不能为空");
		}
	}

	/**
	 * <b>Description:</b><br> 删除附件
	 * @param request
	 * @param response
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月25日 上午11:03:04
	 */
	@ResponseBody
	@RequestMapping(value = "deleteFile")
	public FileInfo deleteFile(HttpServletRequest request, HttpServletResponse response) {
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			String path = request.getParameter("filePath").toString();
//			String path = "/ajax/upload/2017-05/25/image/1495679906000_4903.jpg";
			return ftpUtilService.deleteFile(path);
		}else{
			return new FileInfo(-1,"登录用户不能为空");
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value = "ajaxFileUploadServe")
	public FileInfo ajaxFileUploadServe(HttpServletRequest request, HttpServletResponse response, @RequestParam("lwfile") MultipartFile lwfile) {
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			//临时文件存放地址
			String path = request.getSession().getServletContext().getRealPath("/upload/ajax");
			ftpUtilService.fileUploadServe(path,lwfile);
			
			return new FileInfo(0,"ok");
		}else{
			return new FileInfo(-1,"登录用户不能为空");
		}
	}
}
