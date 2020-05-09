package com.vedeng.order.model;

import java.math.BigDecimal;

public class SaleorderContract extends Saleorder {
	  /**
	 * 
	 */
	private static final long serialVersionUID = -7502442793671425956L;

	 /**
     * 客户名称
     */
    private String customerName;
    
   
    
    /**
     * 初次打款时间
     */
    private String firstPaymentTime;

    /**
     * 距离今天数
     */
    private Integer nowDayBetween;
    
    /**
     * 回传类型
     */
    private String contractType;
    
    /**
     * 已付款金额
     */
    private BigDecimal paymentAmount;
    
    /**
     * taskId
     */
    private String taskId;
    
    /**
     * 合同文件数
     */
    private Integer contractFileCount;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName == null ? null : customerName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public Integer getContractFileCount() {
		return contractFileCount;
	}

	public void setContractFileCount(Integer contractFileCount) {
		this.contractFileCount = contractFileCount;
	}

	public String getFirstPaymentTime() {
		return firstPaymentTime;
	}

	public void setFirstPaymentTime(String firstPaymentTime) {
		this.firstPaymentTime = firstPaymentTime;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Integer getNowDayBetween() {
		return nowDayBetween;
	}

	public void setNowDayBetween(Integer nowDayBetween) {
		this.nowDayBetween = nowDayBetween;
	}

	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
}
