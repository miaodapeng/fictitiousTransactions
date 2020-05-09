
package com.vedeng.common.thread.ext;


/**
 * <b>Description: 业务处理线程数据模型</b><br> 
 * <b>Author: Franlin</b> 
 * @fileName ThreadExt.java
 * <br><b>Date: 2018年7月31日 上午9:03:46 </b> 
 *
 */
public class ThreadExt 
{
	/**
	 * 业务类型
	 */
	private Integer serviceType;
	
	/**
	 *  短信接收人的userId
	 */
	private Integer acceptUserId;
	
	/**
	 * 商机ID
	 */
	private Integer bussinessChanceId;

	/**
	 * 商机No
	 */
	private String bussinessChanceNo;
	
	/**
	 * 短信接收电话号码
	 */
	private String acceptTelPhone;
	
	/**
	 * 联系人名称
	 * @1@
	 */
	private String traderContactName;
	
	/**
	 * 联系人电话/手机号
	 * @2@
	 */
	private String traderContactTelephone;

	/**
	 * 产品名称  
	 * @3@
	 */
	private String productName;
	
	/**
	 * 扩展字段1
	 */
	private String extendString1;

	/**
	 * 扩展字段2
	 */
	private String extendString2;

	
	public Integer getServiceType()
	{
		return serviceType;
	}

	public void setServiceType(Integer serviceType)
	{
		this.serviceType = serviceType;
	}

	public Integer getAcceptUserId()
	{
		return acceptUserId;
	}

	public void setAcceptUserId(Integer acceptUserId)
	{
		this.acceptUserId = acceptUserId;
	}

	public String getAcceptTelPhone()
	{
		return acceptTelPhone;
	}

	public void setAcceptTelPhone(String acceptTelPhone)
	{
		this.acceptTelPhone = acceptTelPhone;
	}

	public String getTraderContactName()
	{
		return traderContactName;
	}

	public void setTraderContactName(String traderContactName)
	{
		this.traderContactName = traderContactName;
	}

	public String getTraderContactTelephone()
	{
		return traderContactTelephone;
	}

	public void setTraderContactTelephone(String traderContactTelephone)
	{
		this.traderContactTelephone = traderContactTelephone;
	}

	public Integer getBussinessChanceId()
	{
		return bussinessChanceId;
	}

	public void setBussinessChanceId(Integer bussinessChanceId)
	{
		this.bussinessChanceId = bussinessChanceId;
	}

	public String getBussinessChanceNo()
	{
		return bussinessChanceNo;
	}

	public void setBussinessChanceNo(String bussinessChanceNo)
	{
		this.bussinessChanceNo = bussinessChanceNo;
	}

	public String getProductName()
	{
		return productName;
	}

	public void setProductName(String productName)
	{
		this.productName = productName;
	}

	public String getExtendString1()
	{
		return extendString1;
	}

	public void setExtendString1(String extendString1)
	{
		this.extendString1 = extendString1;
	}

	public String getExtendString2()
	{
		return extendString2;
	}

	public void setExtendString2(String extendString2)
	{
		this.extendString2 = extendString2;
	}
	
	
}

