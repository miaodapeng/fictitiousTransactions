package com.vedeng.order.model.vo;

import java.math.BigDecimal;

import com.vedeng.order.model.Quoteorder;

public class QuoteorderVo extends Quoteorder {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer quoteNum;//报价数量
	
	private BigDecimal quoteMoney;//报价金额
	
	private Integer buyCount;//交易次数
	
	private BigDecimal buyAmount;//交易金额
	
	private Long lastBuyTime;//上次交易时间
	
	private String belongSales;//归属销售
	
	private String nextContactDate;//下次联系日期
	
    private String name;//接收人姓名
    
    private String mobile;//手机号
    
    private String telephone;//电话
    
    private String qq;//qq
    
    private String email;//邮件
    
    private Integer sex;//性别
    
    private String taxNum;//税号
	
	private String bank;//开户行

	private String bankAccount;//银行账号
	
	private String regaddress;//注册地址
	
    private String regtel;//注册电话
    
    private Integer nowUserId;
    
	public Integer getNowUserId() {
		return nowUserId;
	}

	public void setNowUserId(Integer nowUserId) {
		this.nowUserId = nowUserId;
	}

	public String getRegaddress() {
		return regaddress;
	}

	public void setRegaddress(String regaddress) {
		this.regaddress = regaddress;
	}

	public String getRegtel() {
		return regtel;
	}

	public void setRegtel(String regtel) {
		this.regtel = regtel;
	}

	public String getTaxNum() {
		return taxNum;
	}

	public void setTaxNum(String taxNum) {
		this.taxNum = taxNum;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank == null ? null : bank.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
    
	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getQuoteNum() {
		return quoteNum;
	}

	public void setQuoteNum(Integer quoteNum) {
		this.quoteNum = quoteNum;
	}

	public BigDecimal getQuoteMoney() {
		return quoteMoney;
	}

	public void setQuoteMoney(BigDecimal quoteMoney) {
		this.quoteMoney = quoteMoney;
	}

	public Integer getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
	}

	public BigDecimal getBuyAmount() {
		return buyAmount;
	}

	public void setBuyAmount(BigDecimal buyAmount) {
		this.buyAmount = buyAmount;
	}

	public Long getLastBuyTime() {
		return lastBuyTime;
	}

	public void setLastBuyTime(Long lastBuyTime) {
		this.lastBuyTime = lastBuyTime;
	}

	public String getBelongSales() {
		return belongSales;
	}

	public void setBelongSales(String belongSales) {
		this.belongSales = belongSales;
	}

	public String getNextContactDate() {
		return nextContactDate;
	}

	public void setNextContactDate(String nextContactDate) {
		this.nextContactDate = nextContactDate;
	}
	
	

}
