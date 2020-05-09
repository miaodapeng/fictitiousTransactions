package com.vedeng.aftersales.model.vo;

import java.math.BigDecimal;
import java.util.List;

import com.vedeng.aftersales.model.AfterSalesInstallstion;
import com.vedeng.aftersales.model.RInstallstionJGoods;

public class AfterSalesInstallstionVo extends AfterSalesInstallstion {

    /**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private String afterSalesNo;//售后订单单号
    
    private Integer validStatus;//审核状态
    
    private Integer status;//订单状态：0待确认（默认）、1进行中、2已完结、3已关闭

    private Integer atferSalesStatus;//售后订单状态：0待确认（默认）、1进行中、2已完结、3已关闭
    
    private String name;
    
    private String mobile;
    
    private String [] afterSalesNums;
    
    private List<AfterSalesGoodsVo> afterSalesGoodsVoList;//售后安调关联的产品列表
    
    private List<RInstallstionJGoods> riInstallstionJGoodList;//安调中产品列表
    
    private Integer areaId;
    
    private Integer subjectType;
    
    private String bank;//

    private String bankCode;//

    private String bankAccount;//
    
    private String company;//公司名称
    
    private Integer serviceTimes;//服务次数
    
    private BigDecimal serviceScoreAverage;//服务评分平均分
    
    private BigDecimal skillScoreAverage;//技能评分平均分
    
    private String traderContactName;

    private String traderContactMobile;
    
    private String terminal;//终端
    
    private String equipment;//设备
    
    private String comments;//备注
    
    private Integer type;//售后类型
    
    private String typeName;
    
    private BigDecimal payApplyTotalAmount;//申请付款的已申请总额
    
    private BigDecimal payApplySum;//申请付款的已申请数量

	public String getAfterSalesNo() {
		return afterSalesNo;
	}

	public void setAfterSalesNo(String afterSalesNo) {
		this.afterSalesNo = afterSalesNo;
	}

	public Integer getValidStatus() {
		return validStatus;
	}

	public void setValidStatus(Integer validStatus) {
		this.validStatus = validStatus;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getAtferSalesStatus() {
		return atferSalesStatus;
	}

	public void setAtferSalesStatus(Integer atferSalesStatus) {
		this.atferSalesStatus = atferSalesStatus;
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

	public List<AfterSalesGoodsVo> getAfterSalesGoodsVoList() {
		return afterSalesGoodsVoList;
	}

	public void setAfterSalesGoodsVoList(List<AfterSalesGoodsVo> afterSalesGoodsVoList) {
		this.afterSalesGoodsVoList = afterSalesGoodsVoList;
	}

	public String[] getAfterSalesNums() {
		return afterSalesNums;
	}

	public void setAfterSalesNums(String[] afterSalesNums) {
		this.afterSalesNums = afterSalesNums;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public List<RInstallstionJGoods> getRiInstallstionJGoodList() {
		return riInstallstionJGoodList;
	}

	public void setRiInstallstionJGoodList(List<RInstallstionJGoods> riInstallstionJGoodList) {
		this.riInstallstionJGoodList = riInstallstionJGoodList;
	}

	public Integer getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(Integer subjectType) {
		this.subjectType = subjectType;
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
		this.bankCode = bankCode;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company == null ? null : company.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public Integer getServiceTimes() {
		return serviceTimes;
	}

	public void setServiceTimes(Integer serviceTimes) {
		this.serviceTimes = serviceTimes;
	}

	public BigDecimal getServiceScoreAverage() {
		return serviceScoreAverage;
	}

	public void setServiceScoreAverage(BigDecimal serviceScoreAverage) {
		this.serviceScoreAverage = serviceScoreAverage;
	}

	public BigDecimal getSkillScoreAverage() {
		return skillScoreAverage;
	}

	public void setSkillScoreAverage(BigDecimal skillScoreAverage) {
		this.skillScoreAverage = skillScoreAverage;
	}

	public String getTraderContactName() {
		return traderContactName;
	}

	public void setTraderContactName(String traderContactName) {
		this.traderContactName = traderContactName;
	}

	public String getTraderContactMobile() {
		return traderContactMobile;
	}

	public void setTraderContactMobile(String traderContactMobile) {
		this.traderContactMobile = traderContactMobile;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public BigDecimal getPayApplyTotalAmount() {
		return payApplyTotalAmount;
	}

	public void setPayApplyTotalAmount(BigDecimal payApplyTotalAmount) {
		this.payApplyTotalAmount = payApplyTotalAmount;
	}

	public BigDecimal getPayApplySum() {
		return payApplySum;
	}

	public void setPayApplySum(BigDecimal payApplySum) {
		this.payApplySum = payApplySum;
	}  
    
}
