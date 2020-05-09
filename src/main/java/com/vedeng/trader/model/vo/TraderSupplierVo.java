package com.vedeng.trader.model.vo;

import java.math.BigDecimal;
import java.util.List;

import com.vedeng.order.model.vo.BuyorderVo;
import com.vedeng.system.model.Attachment;
import com.vedeng.trader.model.CommunicateRecord;
import com.vedeng.trader.model.TraderOrderGoods;
import com.vedeng.trader.model.TraderSupplier;


/**
 * <b>Description:</b><br>
 * 供应商vo
 * 
 * @author east
 * @Note <b>ProjectName:</b> erp <br>
 *       <b>PackageName:</b> com.vedeng.trader.model.vo <br>
 *       <b>ClassName:</b> TraderSupplierVo <br>
 *       <b>Date:</b> 2017年5月12日 上午10:13:16
 */
public class TraderSupplierVo extends TraderSupplier{
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private String traderSupplierName;// 供应商名称

	private String traderSupplierAddress;// 供应商地址

	private Integer traderSupplierStatus;// 供应商审核状态：状态0请求审核1审核通过2审核不通过

	private Integer buyCount;// 采购次数
	private BigDecimal buyMoney;// 采购金额
	private Long startTime;//开始时间，用于页面搜索
	private Long endTime;//结束时间，用于页面搜索
	private String contactWay;//联系方式
	
	private Integer areaId;//所属区域id
	
	private String personal;//归属采购
	private String cooperate;//合作
	
	private Integer timeType;//页面搜索时间：1、创建时间；2、交易时间3、更新时间
	
	private List<Integer> traderList;//交易者id
	
	private BigDecimal averagePrice;//均单价
	private BigDecimal buyFrequency;//交易频次
	private Integer communcateCount;// 沟通次数
	private Long lastCommuncateTime;//上次沟通时间
	private List<Integer> buyorderIds;//采购订单ID
	private List<Integer> serviceOrderIds;//售后订单ID
	private Long lastBussinessTime;//上次交易时间
	private Long firstBussinessTime;//第一次交易时间
	
	private TraderCustomerVo traderCustomerVo;//客户信息

	private String phone;//电话
	
	private Integer companyId;//ERP公司ID
    
    private Integer lastCommunicateType;//上次沟通类型
    
    private Integer lastRelatedId;//上次沟通主表ID
    
    private String contactName;// 联系人名称
    
    private List<BuyorderVo> buyorderList;//采购订单
    
    private Integer traderContactId;//联系人ID
    
    private String gradeStr;//供应商等级
    
    private List<CommunicateRecord> communicateRecordList;//沟通记录
    
    private BigDecimal accountPeriodLeft;//剩余账期
	
	private Integer usedTimes;//账期使用次数

    private Integer overdueTimes;//账期逾期次数
    
    private BigDecimal overPeriodAmount;//账期逾期金额
    
    private List<TraderContactVo> traderContactVoList;
    
    private List<TraderAddressVo> traderAddressVoList;
    
	private Integer homePurchasing;//归属销售--页面搜索条件
	private Integer province;//省份id
	private Integer city;//市id
	private Integer zone;//区县id
	private String queryStartTime;//查询开始时间
	private String queryEndTime;//查询结束时间
	private Integer isView;//列表也是否能查看0-否；1-是
	
	private String searchMsg;//搜索信息
	
	private BigDecimal periodBalance;//供应商账期余额
    
	private String provinceName;//省
	
	private String cityName;//市
	
	private String zoneName;//区
	
	private String addTimeStr;//添加时间格式化
	
	private List<Integer> communicateTraderIds;//沟通供应商ID
	
	private List<Integer> cooperateTraderIds;//合作过的供应商
	
	private List<Integer> contactTraderIds;//联系方式客户集合id集合
	
	private String lastVerifyUsermae;//最后审核人
	    
	private String verifyUsername;//当前审核人
	    
	private Integer verifyStatus;//审核状态
	
	private List<String> verifyUsernameList;//当前审核人
	
	private Integer orderCount;//交易次数
	
	List<TraderOrderGoods> traderOrderGoodsList;
	
	private List<Integer> userIdList;//当前所有采购人员
	
	private String requestType;//采购搜索供应商
	
	private List<Attachment> companyUriList;//企业宣传片地址列表
	
	private List<String> categoryNameList;//订单覆盖品类
	
	private List<String> brandNameList;//订单覆盖品牌
	
	private List<String> goodNameList;//订单覆盖产品
	
	public List<String> getCategoryNameList() {
		return categoryNameList;
	}

	public void setCategoryNameList(List<String> categoryNameList) {
		this.categoryNameList = categoryNameList;
	}

	public List<String> getBrandNameList() {
		return brandNameList;
	}

	public void setBrandNameList(List<String> brandNameList) {
		this.brandNameList = brandNameList;
	}

	public List<String> getGoodNameList() {
		return goodNameList;
	}

	public void setGoodNameList(List<String> goodNameList) {
		this.goodNameList = goodNameList;
	}

	public String getTraderSupplierName() {
		return traderSupplierName;
	}

	public void setTraderSupplierName(String traderSupplierName) {
		this.traderSupplierName = traderSupplierName == null ? null : traderSupplierName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public String getTraderSupplierAddress() {
		return traderSupplierAddress;
	}

	public void setTraderSupplierAddress(String traderSupplierAddress) {
		this.traderSupplierAddress = traderSupplierAddress;
	}

	public Integer getTraderSupplierStatus() {
		return traderSupplierStatus;
	}

	public void setTraderSupplierStatus(Integer traderSupplierStatus) {
		this.traderSupplierStatus = traderSupplierStatus;
	}

	public Integer getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
	}

	public BigDecimal getBuyMoney() {
		return buyMoney;
	}

	public void setBuyMoney(BigDecimal buyMoney) {
		this.buyMoney = buyMoney;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public String getContactWay() {
		return contactWay;
	}

	public void setContactWay(String contactWay) {
		this.contactWay = contactWay;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getCooperate() {
		return cooperate;
	}

	public void setCooperate(String cooperate) {
		this.cooperate = cooperate;
	}

	public Integer getTimeType() {
		return timeType;
	}

	public void setTimeType(Integer timeType) {
		this.timeType = timeType;
	}

	public List<Integer> getTraderList() {
		return traderList;
	}

	public void setTraderList(List<Integer> traderList) {
		this.traderList = traderList;
	}

	public String getPersonal() {
		return personal;
	}

	public void setPersonal(String personal) {
		this.personal = personal;
	}

	public BigDecimal getAveragePrice() {
		return averagePrice;
	}

	public void setAveragePrice(BigDecimal averagePrice) {
		this.averagePrice = averagePrice;
	}

	public BigDecimal getBuyFrequency() {
		return buyFrequency;
	}

	public void setBuyFrequency(BigDecimal buyFrequency) {
		this.buyFrequency = buyFrequency;
	}

	public Integer getCommuncateCount() {
		return communcateCount;
	}

	public void setCommuncateCount(Integer communcateCount) {
		this.communcateCount = communcateCount;
	}

	public Long getLastCommuncateTime() {
		return lastCommuncateTime;
	}

	public void setLastCommuncateTime(Long lastCommuncateTime) {
		this.lastCommuncateTime = lastCommuncateTime;
	}

	public List<Integer> getBuyorderIds() {
		return buyorderIds;
	}

	public void setBuyorderIds(List<Integer> buyorderIds) {
		this.buyorderIds = buyorderIds;
	}

	public List<Integer> getServiceOrderIds() {
		return serviceOrderIds;
	}

	public void setServiceOrderIds(List<Integer> serviceOrderIds) {
		this.serviceOrderIds = serviceOrderIds;
	}

	public Long getLastBussinessTime() {
		return lastBussinessTime;
	}

	public void setLastBussinessTime(Long lastBussinessTime) {
		this.lastBussinessTime = lastBussinessTime;
	}

	public Long getFirstBussinessTime() {
		return firstBussinessTime;
	}

	public void setFirstBussinessTime(Long firstBussinessTime) {
		this.firstBussinessTime = firstBussinessTime;
	}

	public TraderCustomerVo getTraderCustomerVo() {
		return traderCustomerVo;
	}

	public void setTraderCustomerVo(TraderCustomerVo traderCustomerVo) {
		this.traderCustomerVo = traderCustomerVo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getLastCommunicateType() {
		return lastCommunicateType;
	}

	public void setLastCommunicateType(Integer lastCommunicateType) {
		this.lastCommunicateType = lastCommunicateType;
	}

	public Integer getLastRelatedId() {
		return lastRelatedId;
	}

	public void setLastRelatedId(Integer lastRelatedId) {
		this.lastRelatedId = lastRelatedId;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public List<BuyorderVo> getBuyorderList() {
		return buyorderList;
	}

	public void setBuyorderList(List<BuyorderVo> buyorderList) {
		this.buyorderList = buyorderList;
	}

	public Integer getTraderContactId() {
		return traderContactId;
	}

	public void setTraderContactId(Integer traderContactId) {
		this.traderContactId = traderContactId;
	}

	public String getGradeStr() {
		return gradeStr;
	}

	public void setGradeStr(String gradeStr) {
		this.gradeStr = gradeStr;
	}

	public List<CommunicateRecord> getCommunicateRecordList() {
		return communicateRecordList;
	}

	public void setCommunicateRecordList(List<CommunicateRecord> communicateRecordList) {
		this.communicateRecordList = communicateRecordList;
	}

	public Integer getCompanyId() {
	    return companyId;
	}

	public void setCompanyId(Integer companyId) {
	    this.companyId = companyId;
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

	public List<TraderContactVo> getTraderContactVoList() {
		return traderContactVoList;
	}

	public void setTraderContactVoList(List<TraderContactVo> traderContactVoList) {
		this.traderContactVoList = traderContactVoList;
	}

	public List<TraderAddressVo> getTraderAddressVoList() {
		return traderAddressVoList;
	}

	public void setTraderAddressVoList(List<TraderAddressVo> traderAddressVoList) {
		this.traderAddressVoList = traderAddressVoList;
	}

	public Integer getHomePurchasing() {
		return homePurchasing;
	}

	public void setHomePurchasing(Integer homePurchasing) {
		this.homePurchasing = homePurchasing;
	}

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public Integer getZone() {
		return zone;
	}

	public void setZone(Integer zone) {
		this.zone = zone;
	}

	public String getQueryStartTime() {
		return queryStartTime;
	}

	public void setQueryStartTime(String queryStartTime) {
		this.queryStartTime = queryStartTime;
	}

	public String getQueryEndTime() {
		return queryEndTime;
	}

	public void setQueryEndTime(String queryEndTime) {
		this.queryEndTime = queryEndTime;
	}

	public Integer getIsView() {
		return isView;
	}

	public void setIsView(Integer isView) {
		this.isView = isView;
	}

	public String getSearchMsg() {
		return searchMsg;
	}

	public void setSearchMsg(String searchMsg) {
		this.searchMsg = searchMsg;
	}

	public BigDecimal getPeriodBalance() {
		return periodBalance;
	}

	public void setPeriodBalance(BigDecimal periodBalance) {
		this.periodBalance = periodBalance;
	}

	public BigDecimal getOverPeriodAmount() {
		return overPeriodAmount;
	}

	public void setOverPeriodAmount(BigDecimal overPeriodAmount) {
		this.overPeriodAmount = overPeriodAmount;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public String getAddTimeStr() {
		return addTimeStr;
	}

	public void setAddTimeStr(String addTimeStr) {
		this.addTimeStr = addTimeStr;
	}

	public List<Integer> getCommunicateTraderIds() {
		return communicateTraderIds;
	}

	public void setCommunicateTraderIds(List<Integer> communicateTraderIds) {
		this.communicateTraderIds = communicateTraderIds;
	}

	public List<Integer> getCooperateTraderIds() {
		return cooperateTraderIds;
	}

	public void setCooperateTraderIds(List<Integer> cooperateTraderIds) {
		this.cooperateTraderIds = cooperateTraderIds;
	}

	public List<Integer> getContactTraderIds() {
		return contactTraderIds;
	}

	public void setContactTraderIds(List<Integer> contactTraderIds) {
		this.contactTraderIds = contactTraderIds;
	}

	public String getLastVerifyUsermae() {
	    return lastVerifyUsermae;
	}

	public void setLastVerifyUsermae(String lastVerifyUsermae) {
	    this.lastVerifyUsermae = lastVerifyUsermae;
	}

	public String getVerifyUsername() {
	    return verifyUsername;
	}

	public void setVerifyUsername(String verifyUsername) {
	    this.verifyUsername = verifyUsername;
	}

	public Integer getVerifyStatus() {
	    return verifyStatus;
	}

	public void setVerifyStatus(Integer verifyStatus) {
	    this.verifyStatus = verifyStatus;
	}

	public List<String> getVerifyUsernameList() {
	    return verifyUsernameList;
	}

	public void setVerifyUsernameList(List<String> verifyUsernameList) {
	    this.verifyUsernameList = verifyUsernameList;
	}

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

	public List<TraderOrderGoods> getTraderOrderGoodsList() {
		return traderOrderGoodsList;
	}

	public void setTraderOrderGoodsList(List<TraderOrderGoods> traderOrderGoodsList) {
		this.traderOrderGoodsList = traderOrderGoodsList;
	}

	public List<Integer> getUserIdList() {
		return userIdList;
	}

	public void setUserIdList(List<Integer> userIdList) {
		this.userIdList = userIdList;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public List<Attachment> getCompanyUriList() {
		return companyUriList;
	}

	public void setCompanyUriList(List<Attachment> companyUriList) {
		this.companyUriList = companyUriList;
	}

}
