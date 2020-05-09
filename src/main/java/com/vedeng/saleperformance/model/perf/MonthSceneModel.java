package com.vedeng.saleperformance.model.perf;




import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.vedeng.saleperformance.model.SalesPerformanceOrdersDetail;
/**
 * <b>Description: 五行剑法--本月概况数据模型</b><br>
 * <b>Author: Franlin</b>
 * 
 * @fileName MonthSceneModel.java <br>
 * 			<b>Date: 2018年6月5日 下午2:12:54 </b>
 *
 */
public class MonthSceneModel extends SalesPerformanceOrdersDetail implements Serializable
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3916399003786363327L;


	/**
	 * 本月概况中业务场景类型 
	 * 1--个人 
	 * 2--团队 
	 * 3--榜首 
	 * 4--昨天 
	 * 5--上月
	 */
	private Integer sceneType;

	/**
	 * 排名类型
	 */
	private Integer sortType;

	/**
	 * 业绩到款额
	 */
	private BigDecimal arrivalAmount;

	/**
	 * 本月目标额度
	 */
	private BigDecimal monthGoalAmount;

	/**
	 * 年度目标额度
	 */
	private BigDecimal yearGoalAmount;

	/**
	 * 订单原始总额
	 */
	private BigDecimal totalAmount;

	/**
	 * 产品手填成本之和
	 */
	private BigDecimal costAmount;

	/**
	 * 业绩额 本月有到款流水记录、到款95%及以上，且未被计算过业绩的订单，可参与计算业绩额；（这里的到款为实际到款，不包含账期）---  *
	 * 当毛利率小于4%时，该订单业绩额=毛利率/4% * 订单总额  * 当毛利率大于等于4%时，该订单业绩额=订单总额
	 *
	 * day_ 获取
	 */
	private BigDecimal performanceAmount;
	
	/**
	 * 订单金额
	 */
	private BigDecimal orderAmount;

	/**
	 * 业绩完成度 %
	 */
	private String scheduleRate;
	
	/**
	 * 团队完成度 
	 */
	private String treamRate;
	
	/**
	 * 团队业绩额
	 */
	private BigDecimal treamPerfAmount;

	/**
	 * 毛利率 %
	 */
	private String amountRate;

	/**
	 * 得分
	 */
	private BigDecimal score;

	/**
	 * 排名
	 */
	private Integer sort;

	public String getSortValue() {
		return sortValue;
	}

	public void setSortValue(String sortValue) {
		this.sortValue = sortValue;
	}

	private String sortValue;

	/**
	 * 销售员名称
	 */
	private String saleName;

	/**
	 * 销售员userId
	 */
	private Integer userId;
	
	/**
	 * 销售人员ID
	 */
	private Integer salesUserId;
	
	/**
	 * 单个品牌--榜首金额
	 */
	private BigDecimal brandFirstAmount;

	/**
	 * 已合作品牌数量
	 */
	private Integer brandNum;
	private BigDecimal brandNum1;
	private BigDecimal gooDecimal;
	
	public BigDecimal getGooDecimal() {
		return gooDecimal;
	}

	public void setGooDecimal(BigDecimal gooDecimal) {
		this.gooDecimal = gooDecimal;
	}

	public BigDecimal getBrandNum1() {
		return brandNum1;
	}

	public void setBrandNum1(BigDecimal brandNum1) {
		this.brandNum1 = brandNum1;
	}

	/**
	 * 指定品牌数量
	 */
	private Integer specBrandNum;
	
	private BigDecimal specBrandNum1;

	public BigDecimal getSpecBrandNum1() {
		return specBrandNum1;
	}

	public void setSpecBrandNum1(BigDecimal specBrandNum1) {
		this.specBrandNum1 = specBrandNum1;
	}

	/**
	 * 通话时长
	 * 
	 * day_ 获取
	 */
	private Integer callTimeVal;

	/**
	 * 新成交询价数量
	 * 
	 * day_ 获取
	 */
	private Integer newTraderPriceNun;

	/**
	 * 本月分配总机询价数量
	 * 
	 * day_ 获取
	 */
	private Integer totalThisMonthPriceNun;
	
	/**
	 * 小组ID[分配小组ID]
	 */
	private Integer orgConfigId;
	
	/**
	 * 销售3级部门的ID
	 */
	private Integer orgId3;
	
	/**
	 * 年份
	 */
	private Integer year;

	/**
	 * 月份
	 */
	private Integer month;
	
	/**
	 * YEAR_MONTH
	 * 默认每年的月份第一天
	 */
	private Date yearMonthTime;
	
	/**
	 * 历史时间展示
	 * 具体到月份
	 */
	private String yearMonthTimeStr;
	
	/**
	 * 开始时间
	 */
	private Date beginTime;
	
	/**
	 * 结束时间
	 */
	private Date endTime;
	
	/**
	 * 开始时间
	 * --用于查询ADD_TIME
	 */
	private Long beginLongTime;
	
	/**
	 * 结束时间
	 * --用于查询ADD_TIME
	 */
	private Long endLongTime;
	
	/**
	 * 销售订单ID
	 */
	private Integer saleorderId;
	
	/**
	 * 销售订单单号
	 */
	private String saleorderNo;
	
	/**
	 * 订单生效时间
	 */
	private Long orderTime;

	/**
	 * 订单生效时间/展示计入业绩时间
	 * 页面展示
	 */
	private String orderTimeStr;

	/**
	 * 交易者ID
	 */
	private Integer traderId;
	
	/**
	 * 交易者名称
	 */
	private String traderName;
		
	/**
	 * 品牌ID
	 */
	private Integer brandId;
	
	/**
	 * 品牌名称
	 */
	private String brandName;
	
	/**
	 * 品牌成交金额
	 */
	private BigDecimal brandAmount;
	
	/**
	 * 客户业绩
	 * 类型1：90天2:150天
	 */
	private Integer termType;
	
	/**
	 * 排序表中的时间
	 */
	private Date sortTime;
	
	/**
	 * 用于展示历史数据--单位月份
	 * 默认12
	 */
	private Integer historyMonthNum;
	
	/**
	 * 多个类型查询
	 */
	private String types;
	
	/**
	 * 是否生效
	 */
	private Integer isEnable;
	
	/**
	 * 公司ID
	 */
	private Integer companyId;
	
	/**
	 * 客户合作数量
	 */
	private Integer customerCopNum;

	/**
	 * 客户--团队合作数量
	 */
	private Integer customerTreamCopNum;
	
	/**
	 * 客户--团队合作数量平均数
	 */
	private BigDecimal customerTreamCopNumAvg;
	
	/**
	 * 有效订单数
	 */
	private Integer validOrderNum;
	
	/**
	 * 有效成交额
	 */
	private BigDecimal validDealAmount;
	
	/**
	 * 最近一次下单时间
	 */
	private String lastOrderTimeStr;
	
	/**
	 * 最近一次沟通时间
	 */
	private String lastCallTimeStr;
	
	/**
	 * 是否是老客户
	 * 1--是
	 */
	private String isOldCutomer;
	
	/**
	 * 平均数
	 * 客户-- 部门平均合作客户数量
	 * 品牌-- 部门平均合作数量
	 */
	private BigDecimal averageNum;
	
	/**
	 * 通话时长
	 * 单位:秒
	 */
	private Integer callTime;
	
	/**
	 * 通话时长 页面展示
	 * 00小时00分00秒
	 */
	private String callTimeStr;
	
	/**
	 * call客户数量
	 */
	private Integer callTraderNum;
	
	/**
	 * 呼入次数
	 */
	private Integer callInNum;
	
	/**
	 * 呼入时长
	 * 单位--秒
	 */
	private Integer callInTime;

	/**
	 * 呼入时长
	 */
	private String callInTimeStr;
	
	/**
	 *  呼入平均时长
	 *  单位--秒
	 */
	private Integer averageCallInTime;

	/**
	 * 呼入平均时长
	 */
	private String averageCallInTimeStr;
	
	/**
	 * 呼出时长
	 * 单位--秒
	 */
	private Integer callOutTime;

	/**
	 * 呼出时长
	 */
	private String callOutTimeStr;
	
	/**
	 *  呼出平均时长
	 *  单位--秒
	 */
	private Integer averageCallOutTime;

	/**
	 * 呼出平均时长
	 */
	private String averageCallOutTimeStr;
	
	/**
	 * 呼出次数
	 */
	private Integer callOutNum;
	
	/**
	 * 部门平均通话时长
	 */
	private Integer callTreamAvgCallTime;
	
	/**
	 * 部门平均通话时长
	 * -- 页面展示
	 */
	private String callTreamAvgCallTimeStr;
	
	/**
	 * 话务业绩
	 * 类型1：未成交客户 
	 * 2：初次成交客户
	 * 3：老客户
	 */
	private Integer statusType;
	
	/**
	 * 新成交询价数量
	 */
	private Integer newDealToPriceNum;
	
	/**
	 * 本月分配总机询价数量
	 */
	private Integer monthToPriceNum;
	
	/**
	 * 转化率
	 */
	private String rate;
	
	/**
	 * 默认0
	 * 查询五行
	 * 1--查询五行总成绩
	 */
	private Integer totalFlag = 0;
	
	/**
	 * 团队人数
	 * 平台/科研
	 */
	private Integer orgConfigUserNum;
	
	/**
	 * 商机ID
	 */
	private Integer bussinessId;

	/**
	 * 商机单号
	 */
	private String bussinessNo;
	
	/**
	 * 展示时间
	 */
	private Long showTime;
	
	/**
	 * 展示时间
	 */
	private String showTimeStr;
	
	/**
	 * 距离今天的天数
	 */
	private Integer subDays;

	public Integer getSceneType()
	{
		return sceneType;
	}

	public void setSceneType(Integer sceneType)
	{
		this.sceneType = sceneType;
	}

	public Integer getSortType()
	{
		return sortType;
	}

	public void setSortType(Integer sortType)
	{
		this.sortType = sortType;
	}

	public BigDecimal getArrivalAmount()
	{
		return arrivalAmount;
	}

	public void setArrivalAmount(BigDecimal arrivalAmount)
	{
		this.arrivalAmount = arrivalAmount;
	}

	public BigDecimal getMonthGoalAmount()
	{
		return monthGoalAmount;
	}

	public void setMonthGoalAmount(BigDecimal monthGoalAmount)
	{
		this.monthGoalAmount = monthGoalAmount;
	}

	public BigDecimal getYearGoalAmount()
	{
		return yearGoalAmount;
	}

	public void setYearGoalAmount(BigDecimal yearGoalAmount)
	{
		this.yearGoalAmount = yearGoalAmount;
	}

	public BigDecimal getTotalAmount()
	{
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount)
	{
		this.totalAmount = totalAmount;
	}

	public BigDecimal getCostAmount()
	{
		return costAmount;
	}

	public void setCostAmount(BigDecimal costAmount)
	{
		this.costAmount = costAmount;
	}

	public BigDecimal getPerformanceAmount()
	{
		return performanceAmount;
	}

	public void setPerformanceAmount(BigDecimal performanceAmount)
	{
		this.performanceAmount = performanceAmount;
	}

	public BigDecimal getOrderAmount()
	{
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount)
	{
		this.orderAmount = orderAmount;
	}

	public String getScheduleRate()
	{
		return scheduleRate;
	}

	public void setScheduleRate(String scheduleRate)
	{
		this.scheduleRate = scheduleRate;
	}

	public String getTreamRate()
	{
		return treamRate;
	}

	public void setTreamRate(String treamRate)
	{
		this.treamRate = treamRate;
	}

	public BigDecimal getTreamPerfAmount()
	{
		return treamPerfAmount;
	}

	public void setTreamPerfAmount(BigDecimal treamPerfAmount)
	{
		this.treamPerfAmount = treamPerfAmount;
	}

	public String getAmountRate()
	{
		return amountRate;
	}

	public void setAmountRate(String amountRate)
	{
		this.amountRate = amountRate;
	}

	public BigDecimal getScore()
	{
		return score;
	}

	public void setScore(BigDecimal score)
	{
		this.score = score;
	}

	public Integer getSort()
	{
		return sort;
	}

	public void setSort(Integer sort)
	{
		this.sort = sort;
	}

	public String getSaleName()
	{
		return saleName;
	}

	public void setSaleName(String saleName)
	{
		this.saleName = saleName;
	}

	public Integer getUserId()
	{
		return userId;
	}

	public void setUserId(Integer userId)
	{
		this.userId = userId;
	}

	public Integer getSalesUserId()
	{
		return salesUserId;
	}

	public void setSalesUserId(Integer salesUserId)
	{
		this.salesUserId = salesUserId;
	}

	public BigDecimal getBrandFirstAmount()
	{
		return brandFirstAmount;
	}

	public void setBrandFirstAmount(BigDecimal brandFirstAmount)
	{
		this.brandFirstAmount = brandFirstAmount;
	}

	public Integer getBrandNum()
	{
		return brandNum;
	}

	public void setBrandNum(Integer brandNum)
	{
		this.brandNum = brandNum;
	}

	public Integer getSpecBrandNum()
	{
		return specBrandNum;
	}

	public void setSpecBrandNum(Integer specBrandNum)
	{
		this.specBrandNum = specBrandNum;
	}

	public Integer getCallTimeVal()
	{
		return callTimeVal;
	}

	public void setCallTimeVal(Integer callTimeVal)
	{
		this.callTimeVal = callTimeVal;
	}

	public Integer getNewTraderPriceNun()
	{
		return newTraderPriceNun;
	}

	public void setNewTraderPriceNun(Integer newTraderPriceNun)
	{
		this.newTraderPriceNun = newTraderPriceNun;
	}

	public Integer getTotalThisMonthPriceNun()
	{
		return totalThisMonthPriceNun;
	}

	public void setTotalThisMonthPriceNun(Integer totalThisMonthPriceNun)
	{
		this.totalThisMonthPriceNun = totalThisMonthPriceNun;
	}

	public Integer getOrgConfigId()
	{
		return orgConfigId;
	}

	public void setOrgConfigId(Integer orgConfigId)
	{
		this.orgConfigId = orgConfigId;
	}

	public Integer getOrgId3()
	{
		return orgId3;
	}

	public void setOrgId3(Integer orgId3)
	{
		this.orgId3 = orgId3;
	}

	public Integer getYear()
	{
		return year;
	}

	public void setYear(Integer year)
	{
		this.year = year;
	}

	public Integer getMonth()
	{
		return month;
	}

	public void setMonth(Integer month)
	{
		this.month = month;
	}

	public Date getYearMonthTime()
	{
		return yearMonthTime;
	}

	public void setYearMonthTime(Date yearMonthTime)
	{
		this.yearMonthTime = yearMonthTime;
	}

	public String getYearMonthTimeStr()
	{
		return yearMonthTimeStr;
	}

	public void setYearMonthTimeStr(String yearMonthTimeStr)
	{
		this.yearMonthTimeStr = yearMonthTimeStr;
	}

	public Date getBeginTime()
	{
		return beginTime;
	}

	public void setBeginTime(Date beginTime)
	{
		this.beginTime = beginTime;
	}

	public Date getEndTime()
	{
		return endTime;
	}

	public void setEndTime(Date endTime)
	{
		this.endTime = endTime;
	}

	public Long getBeginLongTime()
	{
		return beginLongTime;
	}

	public void setBeginLongTime(Long beginLongTime)
	{
		this.beginLongTime = beginLongTime;
	}

	public Long getEndLongTime()
	{
		return endLongTime;
	}

	public void setEndLongTime(Long endLongTime)
	{
		this.endLongTime = endLongTime;
	}

	public Integer getSaleorderId()
	{
		return saleorderId;
	}

	public void setSaleorderId(Integer saleorderId)
	{
		this.saleorderId = saleorderId;
	}

	public String getSaleorderNo()
	{
		return saleorderNo;
	}

	public void setSaleorderNo(String saleorderNo)
	{
		this.saleorderNo = saleorderNo;
	}

	public Long getOrderTime()
	{
		return orderTime;
	}

	public void setOrderTime(Long orderTime)
	{
		this.orderTime = orderTime;
	}
	
	

	public String getOrderTimeStr()
	{
		return orderTimeStr;
	}

	public void setOrderTimeStr(String orderTimeStr)
	{
		this.orderTimeStr = orderTimeStr;
	}

	public Integer getTraderId()
	{
		return traderId;
	}

	public void setTraderId(Integer traderId)
	{
		this.traderId = traderId;
	}

	public String getTraderName()
	{
		return traderName;
	}

	public void setTraderName(String traderName)
	{
		this.traderName = traderName==null ? null :traderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public Integer getBrandId()
	{
		return brandId;
	}

	public void setBrandId(Integer brandId)
	{
		this.brandId = brandId;
	}

	public String getBrandName()
	{
		return brandName;
	}

	public void setBrandName(String brandName)
	{
		this.brandName = brandName;
	}

	public BigDecimal getBrandAmount()
	{
		return brandAmount;
	}

	public void setBrandAmount(BigDecimal brandAmount)
	{
		this.brandAmount = brandAmount;
	}

	public Integer getTermType()
	{
		return termType;
	}

	public void setTermType(Integer termType)
	{
		this.termType = termType;
	}

	public Date getSortTime()
	{
		return sortTime;
	}

	public void setSortTime(Date sortTime)
	{
		this.sortTime = sortTime;
	}

	public Integer getHistoryMonthNum()
	{
		return historyMonthNum;
	}

	public void setHistoryMonthNum(Integer historyMonthNum)
	{
		this.historyMonthNum = historyMonthNum;
	}

	public String getTypes()
	{
		return types;
	}
	
	public void setTypes(String types)
	{
		this.types = types;
	}

	public Integer getIsEnable()
	{
		return isEnable;
	}

	public void setIsEnable(Integer isEnable)
	{
		this.isEnable = isEnable;
	}

	public Integer getCompanyId()
	{
		return companyId;
	}

	public void setCompanyId(Integer companyId)
	{
		this.companyId = companyId;
	}

	public BigDecimal getCustomerTreamCopNumAvg() {
		return customerTreamCopNumAvg;
	}

	public void setCustomerTreamCopNumAvg(BigDecimal customerTreamCopNumAvg) {
		this.customerTreamCopNumAvg = customerTreamCopNumAvg;
	}

	public Integer getCustomerCopNum()
	{
		return customerCopNum;
	}

	public void setCustomerCopNum(Integer customerCopNum)
	{
		this.customerCopNum = customerCopNum;
	}

	public Integer getCustomerTreamCopNum()
	{
		return customerTreamCopNum;
	}

	public void setCustomerTreamCopNum(Integer customerTreamCopNum)
	{
		this.customerTreamCopNum = customerTreamCopNum;
	}

	public Integer getValidOrderNum()
	{
		return validOrderNum;
	}

	public void setValidOrderNum(Integer validOrderNum)
	{
		this.validOrderNum = validOrderNum;
	}

	public BigDecimal getValidDealAmount()
	{
		return validDealAmount;
	}

	public void setValidDealAmount(BigDecimal validDealAmount)
	{
		this.validDealAmount = validDealAmount;
	}

	public String getLastOrderTimeStr()
	{
		return lastOrderTimeStr;
	}

	public void setLastOrderTimeStr(String lastOrderTimeStr)
	{
		this.lastOrderTimeStr = lastOrderTimeStr;
	}

	public String getLastCallTimeStr()
	{
		return lastCallTimeStr;
	}

	public void setLastCallTimeStr(String lastCallTimeStr)
	{
		this.lastCallTimeStr = lastCallTimeStr;
	}

	public String getIsOldCutomer()
	{
		return isOldCutomer;
	}

	public void setIsOldCutomer(String isOldCutomer)
	{
		this.isOldCutomer = isOldCutomer;
	}

	public BigDecimal getAverageNum()
	{
		return averageNum;
	}

	public void setAverageNum(BigDecimal averageNum)
	{
		this.averageNum = averageNum;
	}

	public Integer getCallTime()
	{
		return callTime;
	}

	public void setCallTime(Integer callTime)
	{
		this.callTime = callTime;
	}

	public String getCallTimeStr()
	{
		return callTimeStr;
	}

	public void setCallTimeStr(String callTimeStr)
	{
		this.callTimeStr = callTimeStr;
	}

	public Integer getCallTraderNum()
	{
		return callTraderNum;
	}

	public void setCallTraderNum(Integer callTraderNum)
	{
		this.callTraderNum = callTraderNum;
	}

	public Integer getCallInNum()
	{
		return callInNum;
	}

	public void setCallInNum(Integer callInNum)
	{
		this.callInNum = callInNum;
	}

	public Integer getCallInTime()
	{
		return callInTime;
	}

	public void setCallInTime(Integer callInTime)
	{
		this.callInTime = callInTime;
	}

	public String getCallInTimeStr()
	{
		return callInTimeStr;
	}

	public void setCallInTimeStr(String callInTimeStr)
	{
		this.callInTimeStr = callInTimeStr;
	}

	public Integer getAverageCallInTime()
	{
		return averageCallInTime;
	}

	public void setAverageCallInTime(Integer averageCallInTime)
	{
		this.averageCallInTime = averageCallInTime;
	}

	public String getAverageCallInTimeStr()
	{
		return averageCallInTimeStr;
	}

	public void setAverageCallInTimeStr(String averageCallInTimeStr)
	{
		this.averageCallInTimeStr = averageCallInTimeStr;
	}

	public Integer getCallOutTime()
	{
		return callOutTime;
	}

	public void setCallOutTime(Integer callOutTime)
	{
		this.callOutTime = callOutTime;
	}

	public String getCallOutTimeStr()
	{
		return callOutTimeStr;
	}

	public void setCallOutTimeStr(String callOutTimeStr)
	{
		this.callOutTimeStr = callOutTimeStr;
	}

	public Integer getAverageCallOutTime()
	{
		return averageCallOutTime;
	}

	public void setAverageCallOutTime(Integer averageCallOutTime)
	{
		this.averageCallOutTime = averageCallOutTime;
	}

	public String getAverageCallOutTimeStr()
	{
		return averageCallOutTimeStr;
	}

	public void setAverageCallOutTimeStr(String averageCallOutTimeStr)
	{
		this.averageCallOutTimeStr = averageCallOutTimeStr;
	}

	public Integer getCallOutNum()
	{
		return callOutNum;
	}

	public void setCallOutNum(Integer callOutNum)
	{
		this.callOutNum = callOutNum;
	}

	public Integer getCallTreamAvgCallTime()
	{
		return callTreamAvgCallTime;
	}

	public void setCallTreamAvgCallTime(Integer callTreamAvgCallTime)
	{
		this.callTreamAvgCallTime = callTreamAvgCallTime;
	}

	public String getCallTreamAvgCallTimeStr()
	{
		return callTreamAvgCallTimeStr;
	}

	public void setCallTreamAvgCallTimeStr(String callTreamAvgCallTimeStr)
	{
		this.callTreamAvgCallTimeStr = callTreamAvgCallTimeStr;
	}

	public Integer getStatusType()
	{
		return statusType;
	}

	public void setStatusType(Integer statusType)
	{
		this.statusType = statusType;
	}

	public Integer getNewDealToPriceNum()
	{
		return newDealToPriceNum;
	}

	public void setNewDealToPriceNum(Integer newDealToPriceNum)
	{
		this.newDealToPriceNum = newDealToPriceNum;
	}

	public Integer getMonthToPriceNum()
	{
		return monthToPriceNum;
	}

	public void setMonthToPriceNum(Integer monthToPriceNum)
	{
		this.monthToPriceNum = monthToPriceNum;
	}

	public String getRate()
	{
		return rate;
	}

	public void setRate(String rate)
	{
		this.rate = rate;
	}

	public Integer getTotalFlag()
	{
		return totalFlag;
	}

	public void setTotalFlag(Integer totalFlag)
	{
		this.totalFlag = totalFlag;
	}

	public Integer getOrgConfigUserNum()
	{
		return orgConfigUserNum;
	}

	public void setOrgConfigUserNum(Integer orgConfigUserNum)
	{
		this.orgConfigUserNum = orgConfigUserNum;
	}

	public Integer getBussinessId()
	{
		return bussinessId;
	}

	public void setBussinessId(Integer bussinessId)
	{
		this.bussinessId = bussinessId;
	}

	public String getBussinessNo()
	{
		return bussinessNo;
	}

	public void setBussinessNo(String bussinessNo)
	{
		this.bussinessNo = bussinessNo;
	}

	public Long getShowTime()
	{
		return showTime;
	}

	public void setShowTime(Long showTime)
	{
		this.showTime = showTime;
	}

	public String getShowTimeStr()
	{
		return showTimeStr;
	}

	public void setShowTimeStr(String showTimeStr)
	{
		this.showTimeStr = showTimeStr;
	}
	public Integer getSubDays()
	{
		return subDays;
	}

	public void setSubDays(Integer subDays)
	{
		this.subDays = subDays;
	}
	
	
	
	
	
}
