package com.vedeng.common.model;

import org.apache.commons.lang.StringUtils;

/**
 * <b>Description:</b><br> 附件 json 数据模型
 * @author duke
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.common.model
 * <br><b>ClassName:</b> FileInfo
 * <br><b>Date:</b> 2017年5月24日 上午9:55:39
 */
public class FileInfo {

	private Integer code = -1;//0成功，-1失败
	
	private String message = "操作失败";
	
	private String fileName;//示例149559546256_6653.jpg
	
	private String filePath;//示例upload/2017-05/25/image/
	
	private String httpUrl;//示例192.168.1.52:8082
	
	private String prefix;//文件后缀

	private boolean pdfFlag;

	/**
	 * oss资源ID
	 */
	private String ossResourceId;

	public String getOssResourceId() {
		return ossResourceId;
	}

	public void setOssResourceId(String ossResourceId) {
		this.ossResourceId = ossResourceId;
	}

	public String getFullPath() {
		return httpUrl+filePath+fileName;
	}
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}
	private String fullPath;





	public FileInfo(Integer code, String message, String fileName, String filePath, String httpUrl, String prefix) {
		super();
		this.code = code;
		this.message = message;
		this.fileName = fileName;
		this.filePath = filePath.replaceAll("\\\\", "/");;
		this.httpUrl = httpUrl;
		this.prefix = prefix;
	}

	public FileInfo(Integer code, String message, String fileName, String filePath, String httpUrl, String ossResourceId,String prefix) {
		super();
		this.code = code;
		this.message = message;
		this.fileName = fileName;
		this.filePath = filePath.replaceAll("\\\\", "/");
		this.httpUrl = httpUrl;
		this.ossResourceId=ossResourceId;
		this.prefix=prefix;
	}

	public FileInfo(Integer code, String message, String fileName, String filePath) {
		super();
		this.code = code;
		this.message = message;
		this.fileName = fileName;
		this.filePath = filePath.replaceAll("\\\\", "/");;
	}

	public FileInfo(Integer code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public FileInfo(Integer code, String message, String fileName, String filePath, String prefix) {
		super();
		this.code = code;
		this.message = message;
		this.fileName = fileName;
		this.filePath = filePath.replaceAll("\\\\", "/");;
		this.prefix = prefix;
	}
	
	public String getHttpUrl() {
		return httpUrl;
	}

	public void setHttpUrl(String httpUrl) {
		this.httpUrl = httpUrl;
	}

	public FileInfo() {
		super();
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public String toString() {
		return "FileInfo [code=" + code + ", message=" + message + ", fileName=" + fileName + ", filePath=" + filePath
				+ ", prefix=" + prefix + "]";
	}

	public boolean isPdfFlag() {
		  return StringUtils.endsWith(StringUtils.lowerCase(fileName),"pdf");
	}

	public void setPdfFlag(boolean pdfFlag) {
		this.pdfFlag = pdfFlag;
	}
}
