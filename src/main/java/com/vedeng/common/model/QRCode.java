package com.vedeng.common.model;

import java.awt.image.BufferedImage;

public class QRCode {

	private String content;//二维码内容
	
	private String saveName;//图片名称
	
	private String logoUrl;//logo图片地址
	
	private BufferedImage bImage;//二维码文件流

	private String ftpPath;//ftp保存地址
	
	private Integer relatedId;//关联键
	
	private String ftpName;//ftp保存名称
	
	public QRCode() {
		super();
	}

	public String getFtpName() {
		return ftpName;
	}


	public void setFtpName(String ftpName) {
		this.ftpName = ftpName;
	}


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSaveName() {
		return saveName;
	}

	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}

	public String getFtpPath() {
		return ftpPath;
	}

	public void setFtpPath(String ftpPath) {
		this.ftpPath = ftpPath;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public BufferedImage getbImage() {
		return bImage;
	}

	public void setbImage(BufferedImage bImage) {
		this.bImage = bImage;
	}

	public Integer getRelatedId() {
	    return relatedId;
	}

	public void setRelatedId(Integer relatedId) {
	    this.relatedId = relatedId;
	}

}
