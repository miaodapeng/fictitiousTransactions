package com.vedeng.trader.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.vedeng.system.model.Tag;
public class TraderCustomer implements Serializable{
    /**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private Integer traderCustomerId;

    private Integer traderId;

    private Integer traderCustomerCategoryId;

    private Integer isEnable;

    private Integer isTop;

    private BigDecimal amount;

    private BigDecimal periodAmount;

    private Integer periodDay;

    private Long disableTime;

    private Integer customerFrom;

    private Integer firstEnquiryType;

    private Integer customerLevel;//客户等级

    private Integer userEvaluate;

    private Integer customerScore;//客户得分

    private Integer basicMedicalDealer;

    private Integer isVip;

    private Integer ownership;

    private Integer medicalType;

    private Integer hospitalLevel;

    private Integer employees;

    private Integer annualSales;

    private Integer salesModel;

    private String registeredCapital;

    private Date registeredDate;

    private BigDecimal directSelling;

    private BigDecimal wholesale;

    private String disableReason;

    private String comments;

    private String financeComments;

    private String logisticsComments;

    private String brief;

    private String history;

    private String businessCondition;

    private String businessPlan;

    private String advantage;

    private String primalProblem;

    private Integer isCollect;//是否为集中开票客户0否1是

    private Integer isProfit;  //是否为盈利机构，0为不是，1为是
    private Long addTime,collectTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;
    
    private String registeredDateStr;
    
    private List<TraderCustomerAgentBrand> traderCustomerAgentBrands;
    
    private List<TraderCustomerAttribute> traderCustomerAttributes;
    
    private List<TraderCustomerBussinessArea> traderCustomerBussinessAreas;
    
    private List<TraderCustomerBussinessBrand> traderCustomerBussinessBrands;
    
    private Trader trader;
    
    private String customerFromStr,firstEnquiryTypeStr,ownershipStr,medicalTypeStr,hospitalLevelStr,employeesStr,annualSalesStr,salesModelStr;
    
    private TraderCustomerCategory traderCustomerCategory;
    
    private TreeMap<String, List<TraderCustomerAttribute>> attributeMap;
    
    private List<Tag> tag;
    
    private List<TraderOrderGoods> orderGoods;
    
    private String customerLevelStr,userEvaluateStr;
    
    private String ownerSale;

    private List<TraderCustomerCategory> customerCategories;
    
    private TreeMap<Integer, List<TraderCustomerCategory>> customerCategoriesMap;
    
    private List<String> tagName;
    
    private BigDecimal accountPeriodLeft;//剩余账期
	
	private Integer usedTimes;//账期使用次数

    private Integer overdueTimes;//账期逾期次数
    
    private BigDecimal overPeriodAmount;//账期逾期金额
    
    private Integer customerType; // 客户类型
    
    private Integer customerNature;//客户性质//1分销 2终端 0其它
    
    private String orgNumber ;//组织机构代码
    
    private String creditCode ;//统一社会信用代码
    
    private String businessScope ;//经营范围
    
    private String historyNames ;//历史名称
    
    private Integer buyCount;//交易次数//分配客户查询时用到

    // add by franlin.wu for[耗材商城的客户同步，新增客户来源类型] begin
 	/**
      * 来源0ERP 1耗材商城
      */
     private Integer source;
     // add by franlin.wu for[耗材商城的客户同步，新增客户来源类型] end

    /**
     * 方法实现说明(在数据库对应的字段是MN_CODE)
     * @auther Bert
     * @date 2018/11/9 8:56
     */
    private String meiNianCode;
    
    private Integer traderLevel;

    /**
     * 客户分类
     */
    private Integer customerCategory;

    public Integer getIsProfit() {
        return isProfit;
    }

    public void setIsProfit(Integer isProfit) {
        this.isProfit = isProfit;
    }

    private Integer belongPlatform;//客户归属平台

    private Integer isVedengMember;//是否是会员

    public Integer getIsVedengMember() {
        return isVedengMember;
    }

    public void setIsVedengMember(Integer isVedengMember) {
        this.isVedengMember = isVedengMember;
    }

    public Integer getCustomerCategory() {
        return customerCategory;
    }

    public void setCustomerCategory(Integer customerCategory) {
        this.customerCategory = customerCategory;
    }

    public Long getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(Long collectTime) {
        this.collectTime = collectTime;
    }

    public Integer getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(Integer isCollect) {
        this.isCollect = isCollect;
    }

    public Integer getTraderLevel() {
        return traderLevel;
    }

    public void setTraderLevel(Integer traderLevel) {
        this.traderLevel = traderLevel;
    }

    public Integer getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
	}
    
    public String getOrgNumber() {
		return orgNumber;
	}

	public void setOrgNumber(String orgNumber) {
		this.orgNumber = orgNumber;
	}

	public String getCreditCode() {
		return creditCode;
	}

	public void setCreditCode(String creditCode) {
		this.creditCode = creditCode;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	public String getHistoryNames() {
		return historyNames;
	}

	public void setHistoryNames(String historyNames) {
		this.historyNames = historyNames == null ? null : historyNames.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public TraderCustomer() {
		super();
	}
    
    public Integer getTraderCustomerId() {
        return traderCustomerId;
    }

    public void setTraderCustomerId(Integer traderCustomerId) {
        this.traderCustomerId = traderCustomerId;
    }

    public Integer getTraderId() {
        return traderId;
    }

    public void setTraderId(Integer traderId) {
        this.traderId = traderId;
    }

    public Integer getTraderCustomerCategoryId() {
        return traderCustomerCategoryId;
    }

    public void setTraderCustomerCategoryId(Integer traderCustomerCategoryId) {
        this.traderCustomerCategoryId = traderCustomerCategoryId;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPeriodAmount() {
        return periodAmount;
    }

    public void setPeriodAmount(BigDecimal periodAmount) {
        this.periodAmount = periodAmount;
    }

    public Integer getPeriodDay() {
        return periodDay;
    }

    public void setPeriodDay(Integer periodDay) {
        this.periodDay = periodDay;
    }

    public Long getDisableTime() {
        return disableTime;
    }

    public void setDisableTime(Long disableTime) {
        this.disableTime = disableTime;
    }

    public Integer getCustomerFrom() {
        return customerFrom;
    }

    public void setCustomerFrom(Integer customerFrom) {
        this.customerFrom = customerFrom;
    }

    public Integer getFirstEnquiryType() {
        return firstEnquiryType;
    }

    public void setFirstEnquiryType(Integer firstEnquiryType) {
        this.firstEnquiryType = firstEnquiryType;
    }

    public Integer getCustomerLevel() {
        return customerLevel;
    }

    public void setCustomerLevel(Integer customerLevel) {
        this.customerLevel = customerLevel;
    }

    public Integer getUserEvaluate() {
        return userEvaluate;
    }

    public void setUserEvaluate(Integer userEvaluate) {
        this.userEvaluate = userEvaluate;
    }

    public Integer getCustomerScore() {
        return customerScore;
    }

    public void setCustomerScore(Integer customerScore) {
        this.customerScore = customerScore;
    }

    public Integer getBasicMedicalDealer() {
        return basicMedicalDealer;
    }

    public void setBasicMedicalDealer(Integer basicMedicalDealer) {
        this.basicMedicalDealer = basicMedicalDealer;
    }

    public Integer getIsVip() {
        return isVip;
    }

    public void setIsVip(Integer isVip) {
        this.isVip = isVip;
    }

    public Integer getOwnership() {
        return ownership;
    }

    public void setOwnership(Integer ownership) {
        this.ownership = ownership;
    }

    public Integer getMedicalType() {
        return medicalType;
    }

    public void setMedicalType(Integer medicalType) {
        this.medicalType = medicalType;
    }

    public Integer getHospitalLevel() {
        return hospitalLevel;
    }

    public void setHospitalLevel(Integer hospitalLevel) {
        this.hospitalLevel = hospitalLevel;
    }

    public Integer getEmployees() {
        return employees;
    }

    public void setEmployees(Integer employees) {
        this.employees = employees;
    }

    public Integer getAnnualSales() {
        return annualSales;
    }

    public void setAnnualSales(Integer annualSales) {
        this.annualSales = annualSales;
    }

    public Integer getSalesModel() {
        return salesModel;
    }

    public void setSalesModel(Integer salesModel) {
        this.salesModel = salesModel;
    }

    public String getRegisteredCapital() {
		return registeredCapital;
	}

	public void setRegisteredCapital(String registeredCapital) {
		this.registeredCapital = registeredCapital;
	}

	public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }

    public BigDecimal getDirectSelling() {
        return directSelling;
    }

    public void setDirectSelling(BigDecimal directSelling) {
        this.directSelling = directSelling;
    }

    public BigDecimal getWholesale() {
        return wholesale;
    }

    public void setWholesale(BigDecimal wholesale) {
        this.wholesale = wholesale;
    }

    public String getDisableReason() {
        return disableReason;
    }

    public void setDisableReason(String disableReason) {
        this.disableReason = disableReason == null ? null : disableReason.trim();
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
    }

    public String getFinanceComments() {
        return financeComments;
    }

    public void setFinanceComments(String financeComments) {
        this.financeComments = financeComments == null ? null : financeComments.trim();
    }

    public String getLogisticsComments() {
        return logisticsComments;
    }

    public void setLogisticsComments(String logisticsComments) {
        this.logisticsComments = logisticsComments == null ? null : logisticsComments.trim();
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief == null ? null : brief.trim();
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history == null ? null : history.trim();
    }

    public String getBusinessCondition() {
        return businessCondition;
    }

    public void setBusinessCondition(String businessCondition) {
        this.businessCondition = businessCondition == null ? null : businessCondition.trim();
    }

    public String getBusinessPlan() {
        return businessPlan;
    }

    public void setBusinessPlan(String businessPlan) {
        this.businessPlan = businessPlan == null ? null : businessPlan.trim();
    }

    public String getAdvantage() {
        return advantage;
    }

    public void setAdvantage(String advantage) {
        this.advantage = advantage == null ? null : advantage.trim();
    }

    public String getPrimalProblem() {
        return primalProblem;
    }

    public void setPrimalProblem(String primalProblem) {
        this.primalProblem = primalProblem == null ? null : primalProblem.trim();
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

	public String getRegisteredDateStr() {
		return registeredDateStr;
	}

	public void setRegisteredDateStr(String registeredDateStr) {
		this.registeredDateStr = registeredDateStr;
	}

	public List<TraderCustomerAgentBrand> getTraderCustomerAgentBrands() {
		return traderCustomerAgentBrands;
	}

	public void setTraderCustomerAgentBrands(List<TraderCustomerAgentBrand> traderCustomerAgentBrands) {
		this.traderCustomerAgentBrands = traderCustomerAgentBrands;
	}

	public List<TraderCustomerAttribute> getTraderCustomerAttributes() {
		return traderCustomerAttributes;
	}

	public void setTraderCustomerAttributes(List<TraderCustomerAttribute> traderCustomerAttributes) {
		this.traderCustomerAttributes = traderCustomerAttributes;
	}

	public List<TraderCustomerBussinessArea> getTraderCustomerBussinessAreas() {
		return traderCustomerBussinessAreas;
	}

	public void setTraderCustomerBussinessAreas(List<TraderCustomerBussinessArea> traderCustomerBussinessAreas) {
		this.traderCustomerBussinessAreas = traderCustomerBussinessAreas;
	}

	public List<TraderCustomerBussinessBrand> getTraderCustomerBussinessBrands() {
		return traderCustomerBussinessBrands;
	}

	public void setTraderCustomerBussinessBrands(List<TraderCustomerBussinessBrand> traderCustomerBussinessBrands) {
		this.traderCustomerBussinessBrands = traderCustomerBussinessBrands;
	}

	public Trader getTrader() {
		return trader;
	}

	public void setTrader(Trader trader) {
		this.trader = trader;
	}

	public String getCustomerFromStr() {
		return customerFromStr;
	}

	public void setCustomerFromStr(String customerFromStr) {
		this.customerFromStr = customerFromStr;
	}

	public String getFirstEnquiryTypeStr() {
		return firstEnquiryTypeStr;
	}

	public void setFirstEnquiryTypeStr(String firstEnquiryTypeStr) {
		this.firstEnquiryTypeStr = firstEnquiryTypeStr;
	}

	public String getOwnershipStr() {
		return ownershipStr;
	}

	public void setOwnershipStr(String ownershipStr) {
		this.ownershipStr = ownershipStr;
	}

	public String getMedicalTypeStr() {
		return medicalTypeStr;
	}

	public void setMedicalTypeStr(String medicalTypeStr) {
		this.medicalTypeStr = medicalTypeStr;
	}

	public String getHospitalLevelStr() {
		return hospitalLevelStr;
	}

	public void setHospitalLevelStr(String hospitalLevelStr) {
		this.hospitalLevelStr = hospitalLevelStr;
	}

	public String getEmployeesStr() {
		return employeesStr;
	}

	public void setEmployeesStr(String employeesStr) {
		this.employeesStr = employeesStr;
	}

	public String getAnnualSalesStr() {
		return annualSalesStr;
	}

	public void setAnnualSalesStr(String annualSalesStr) {
		this.annualSalesStr = annualSalesStr;
	}

	public String getSalesModelStr() {
		return salesModelStr;
	}

	public void setSalesModelStr(String salesModelStr) {
		this.salesModelStr = salesModelStr;
	}

	public TraderCustomerCategory getTraderCustomerCategory() {
		return traderCustomerCategory;
	}

	public void setTraderCustomerCategory(TraderCustomerCategory traderCustomerCategory) {
		this.traderCustomerCategory = traderCustomerCategory;
	}

	public Map<String, List<TraderCustomerAttribute>> getAttributeMap() {
		return attributeMap;
	}

	public void setAttributeMap(TreeMap<String, List<TraderCustomerAttribute>> attributeMap) {
		this.attributeMap = attributeMap;
	}

	public List<Tag> getTag() {
		return tag;
	}

	public void setTag(List<Tag> tag) {
		this.tag = tag;
	}

	public List<TraderOrderGoods> getOrderGoods() {
		return orderGoods;
	}

	public void setOrderGoods(List<TraderOrderGoods> orderGoods) {
		this.orderGoods = orderGoods;
	}

	public String getCustomerLevelStr() {
		return customerLevelStr;
	}

	public void setCustomerLevelStr(String customerLevelStr) {
		this.customerLevelStr = customerLevelStr;
	}

	public String getUserEvaluateStr() {
		return userEvaluateStr;
	}

	public void setUserEvaluateStr(String userEvaluateStr) {
		this.userEvaluateStr = userEvaluateStr;
	}

	public String getOwnerSale() {
		return ownerSale;
	}

	public void setOwnerSale(String ownerSale) {
		this.ownerSale = ownerSale;
	}

	public List<TraderCustomerCategory> getCustomerCategories() {
		return customerCategories;
	}

	public void setCustomerCategories(List<TraderCustomerCategory> customerCategories) {
		this.customerCategories = customerCategories;
	}

	public Map<Integer, List<TraderCustomerCategory>> getCustomerCategoriesMap() {
		return customerCategoriesMap;
	}

	public void setCustomerCategoriesMap(TreeMap<Integer, List<TraderCustomerCategory>> customerCategoriesMap) {
		this.customerCategoriesMap = customerCategoriesMap;
	}

	public List<String> getTagName() {
		return tagName;
	}

	public void setTagName(List<String> tagName) {
		this.tagName = tagName;
	}

	public BigDecimal getAccountPeriodLeft() {
		return accountPeriodLeft;
	}

	public void setAccountPeriodLeft(BigDecimal accountPeriodLeft) {
		this.accountPeriodLeft = accountPeriodLeft;
	}

	public Integer getUsedTimes() {
		return usedTimes;
	}

	public void setUsedTimes(Integer usedTimes) {
		this.usedTimes = usedTimes;
	}

	public Integer getOverdueTimes() {
		return overdueTimes;
	}

	public void setOverdueTimes(Integer overdueTimes) {
		this.overdueTimes = overdueTimes;
	}

	public Integer getCustomerType() {
		return customerType;
	}

	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
	}

	public Integer getCustomerNature() {
		return customerNature;
	}

	public void setCustomerNature(Integer customerNature) {
		this.customerNature = customerNature;
	}

	public BigDecimal getOverPeriodAmount() {
		return overPeriodAmount;
	}

	public void setOverPeriodAmount(BigDecimal overPeriodAmount) {
		this.overPeriodAmount = overPeriodAmount;
	}

    public String getMeiNianCode() {
        return meiNianCode;
    }

    public void setMeiNianCode(String meiNianCode) {
        this.meiNianCode = meiNianCode;
    }
    
    public Integer getSource()
	{
		return source;
	}

	public void setSource(Integer source)
	{
		this.source = source;
	}

    public void setBelongPlatform(Integer belongPlatform) {
        this.belongPlatform = belongPlatform;
    }

    public Integer getBelongPlatform() {
        return belongPlatform;
    }
}