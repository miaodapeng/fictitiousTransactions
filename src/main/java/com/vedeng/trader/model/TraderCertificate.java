package com.vedeng.trader.model;

import java.io.Serializable;

public class TraderCertificate implements Serializable {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private Integer traderCertificateId;

	private Integer traderId;

	private Integer sysOptionDefinitionId;

	private Long begintime;

	private Long endtime;

	private String name;

	private String sn;

	private String domain;

	private String uri;

	private Integer isMedical;// 是否含有医疗器械

	private String extra;

	private Long addTime;

	private Integer creator;

	private Long modTime;

	private Integer updater;
	// begin by franlin for[3865 供应商资质中，增加销售人授权书，销售人信息]  at 2018-06-21 
	
	/**
	 * --供应商资质[授权销售人-职位]
	 */
	private String authPost;
	
	/**
	 * --供应商资质[授权销售人-姓名]
	 */
	private String authUserName;
	
	/**
	 * --供应商资质[授权销售人-联系方式]
	 */
	private String authContactInfo;

	/**
	 * oss资源标识
	 */
	private String ossResourceId;
	// end by franlin for[3865 供应商资质中，增加销售人授权书，销售人信息]  at 2018-06-21 



	public Integer getTraderCertificateId() {
		return traderCertificateId;
	}

	public void setTraderCertificateId(Integer traderCertificateId) {
		this.traderCertificateId = traderCertificateId;
	}

	public Integer getTraderId() {
		return traderId;
	}

	public void setTraderId(Integer traderId) {
		this.traderId = traderId;
	}

	public Integer getSysOptionDefinitionId() {
		return sysOptionDefinitionId;
	}

	public void setSysOptionDefinitionId(Integer sysOptionDefinitionId) {
		this.sysOptionDefinitionId = sysOptionDefinitionId;
	}

	public String getOssResourceId() {
		return ossResourceId;
	}

	public void setOssResourceId(String ossResourceId) {
		this.ossResourceId = ossResourceId;
	}

	public Long getBegintime() {
		return begintime;
	}

	public void setBegintime(Long begintime) {
		this.begintime = begintime;
	}

	public Long getEndtime() {
		return endtime;
	}

	public void setEndtime(Long endtime) {
		this.endtime = endtime;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn == null ? null : sn.trim();
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain == null ? null : domain.trim();
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri == null ? null : uri.trim();
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra == null ? null : extra.trim();
	}

	public Long getAddTime() {
		return addTime;
	}

	public void setAddTime(Long addTime) {
		this.addTime = addTime;
	}

	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	public Long getModTime() {
		return modTime;
	}

	public void setModTime(Long modTime) {
		this.modTime = modTime;
	}

	public Integer getUpdater() {
		return updater;
	}

	public void setUpdater(Integer updater) {
		this.updater = updater;
	}

	public Integer getIsMedical() {
		return isMedical;
	}

	public void setIsMedical(Integer isMedical) {
		this.isMedical = isMedical;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthPost()
	{
		return authPost;
	}

	public void setAuthPost(String authPost)
	{
		this.authPost = authPost;
	}

	public String getAuthUserName()
	{
		return authUserName;
	}

	public void setAuthUserName(String authUserName)
	{
		this.authUserName = authUserName;
	}

	public String getAuthContactInfo()
	{
		return authContactInfo;
	}

	public void setAuthContactInfo(String authContactInfo)
	{
		this.authContactInfo = authContactInfo;
	}


	
}