package com.vedeng.trader.model;

import java.io.Serializable;

public class TraderFinance implements Serializable{
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private Integer traderFinanceId;

	private Integer traderId;

	private Integer traderType;

	private Integer creditRating;

	private String regAddress;

	private String regTel;

	private String taxNum;

	private String averageTaxpayerDomain;

	private String averageTaxpayerUri;

	private String bank;

	private String bankCode;

	private String bankAccount;

	private String comments;//备注

	private Long addTime;

	private Integer creator;

	private Long modTime;

	private Integer updater;
    //财务审核状态
	private Integer checkStatus;

	private String preTaxNum;

	public String getPreTaxNum() {
		return preTaxNum;
	}

	public void setPreTaxNum(String preTaxNum) {
		this.preTaxNum = preTaxNum;
	}

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public Integer getTraderFinanceId() {
		return traderFinanceId;
	}

	public void setTraderFinanceId(Integer traderFinanceId) {
		this.traderFinanceId = traderFinanceId;
	}

	public Integer getTraderId() {
		return traderId;
	}

	public void setTraderId(Integer traderId) {
		this.traderId = traderId;
	}

	public Integer getTraderType() {
		return traderType;
	}

	public void setTraderType(Integer traderType) {
		this.traderType = traderType;
	}

	public Integer getCreditRating() {
		return creditRating;
	}

	public void setCreditRating(Integer creditRating) {
		this.creditRating = creditRating;
	}

	public String getRegAddress() {
		return regAddress;
	}

	public void setRegAddress(String regAddress) {
		this.regAddress = regAddress == null ? null : regAddress.trim();
	}

	public String getRegTel() {
		return regTel;
	}

	public void setRegTel(String regTel) {
		this.regTel = regTel == null ? null : regTel.trim();
	}

	public String getTaxNum() {
		return taxNum;
	}

	public void setTaxNum(String taxNum) {
		this.taxNum = taxNum == null ? null : taxNum.trim();
	}

	public String getAverageTaxpayerDomain() {
		return averageTaxpayerDomain;
	}

	public void setAverageTaxpayerDomain(String averageTaxpayerDomain) {
		this.averageTaxpayerDomain = averageTaxpayerDomain == null ? null : averageTaxpayerDomain.trim();
	}

	public String getAverageTaxpayerUri() {
		return averageTaxpayerUri;
	}

	public void setAverageTaxpayerUri(String averageTaxpayerUri) {
		this.averageTaxpayerUri = averageTaxpayerUri == null ? null : averageTaxpayerUri.trim();
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank == null ? null : bank.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode == null ? null : bankCode.trim();
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount == null ? null : bankAccount.trim();
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

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}