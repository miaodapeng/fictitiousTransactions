package com.vedeng.trader.model.vo;

import java.math.BigDecimal;
import java.util.List;

import com.vedeng.order.model.Quoteorder;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.vo.BussinessChanceVo;
import com.vedeng.trader.model.CommunicateRecord;
import com.vedeng.trader.model.TraderCustomer;
import com.vedeng.trader.model.TraderOrderGoods;

/**
 * <b>Description:</b><br>
 * vo类
 * 
 * @author east
 * @Note <b>ProjectName:</b> erp <br>
 *       <b>PackageName:</b> com.vedeng.trader.model.vo <br>
 *       <b>ClassName:</b> TraderCustomerVo <br>
 *       <b>Date:</b> 2017年5月17日 下午2:45:28
 */
public class TraderCustomerVo extends TraderCustomer{
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private String name;// 名称
	private String area;// 所在地
	private String address;// 地址
	private Integer aptitudeStatus; //资质审核的状态：0为审核中，1为审核通过，2为审核不通过，3为待审核
	private Integer customerStatus;// 审核状态：状态0请求审核1审核通过2审核不通过
	private Integer customerProperty;// 客户性质
	
	private String customerPropertys;// 搜索不选客户类型，直接选客户性质
	
	private	Integer bussinessChanceCount;//商机次数
	private Integer buyCount;// 交易次数
	private BigDecimal buyMoney;// 交易金额
	private Integer quoteCount;// 报价次数
	private Integer orderCount;// 交易次数
	private BigDecimal orderTotalAmount;// 交易金额
	private Integer communcateCount;// 沟通次数
	private BigDecimal averagePrice;//均单价
	private BigDecimal transactionFrequency;//交易频次
	private Long lastCommuncateTime;//上次沟通时间
	private Integer partnerId;// 页面搜索id
	private String partners;// 战略合作伙伴
	private Long startTime;// 开始时间，用于页面搜索
	private Long endTime;// 结束时间，用于页面搜索
	private String contactWay;// 联系方式

	private Integer areaId;// 所属区域id
	
	private String areaIds;//多级区域

	private String personal;// 归属销售
	private String cooperate;// 合作

	private Integer timeType;// 页面搜索时间：1、创建时间；2、交易时间3、更新时间

	private List<Integer> traderList;// 交易者id

	private Integer wxStatus;// 微信状态

	private Integer smallScore;// 页面搜索条件，客户得分小分数
	private Integer bigScore;// 页面搜索条件，客户得分大分数

	private Integer quote;// 有报价：1、是；2、否
	
	private List<Integer> categoryList;//分类属性
	
	private Long lastBussinessTime;//上次交易时间
	private Long firstBussinessTime;//第一次交易时间
	
	private String customerTypeStr;// 客户类型
	private String customerPropertyStr;// 客户性质
	
	private String customerNatureStr;// 客户性质
	
	private Integer customerPropertyType;//1分销 2终端 0其它
	
	private String traderName;//客户名称
	
	private String searchTraderName;//搜索内容
	
	private String optType;//操作类型
	
	private List<Integer> enquiryOrderIds;//商机
    
    private List<Integer> quoteOrderIds;//报价
    
    private List<Integer> saleOrderIds;//销售
    
    private	List<Integer> buyOrderIds;//采购订单
    
    private List<Integer> serviceOrderIds;//售后
    
    private TraderSupplierVo traderSupplierVo;//供应商 
    
    private String phone;//电话
    
    private Integer lastCommunicateType;//上次沟通类型
    
    private Integer lastRelatedId;//上次沟通主表ID
    
    private String contactName;// 联系人名称
    
    private List<BussinessChanceVo> bussinessChanceList;//商机列表
    
    private List<Quoteorder> quoteorderList;//报价列表
    
	private List<Saleorder> saleorderList;//订单列表
	
	private Integer traderContactId;//联系人ID
	
	private String searchMsg;//信息搜索 ：沟通记录内容，商机的询价产品
	
	private List<CommunicateRecord> communicateRecordList;//沟通记录
	
	private Integer homePurchasing;//归属销售--页面搜索条件
	private Integer province;//省份id
	private Integer city;//市id
	private Integer zone;//区县id
	private String queryStartTime;//查询开始时间
	private String queryEndTime;//查询结束时间
	private Integer isView;//列表也是否能查看0-否；1-是
	private Integer companyId;//客户所属公司
	
	private BigDecimal grossRate;//毛利率
	
	private Integer traderGoodsTypeNum;//交易商品种类
	
	private Integer traderGoodsNum;//交易商品数量
	
	private Integer traderBrandNum;//交易品牌数量
	
	private BigDecimal saleorderRate;//有限订单率
	
	private BigDecimal refundGoodsAmount;//退货金额
	
	private Integer refundGoodsTimes;//退货次数
	
	private String firstTraderTimeStr;//初次交易时间
	
	private String lastTraderTimeStr;//上次交易时间
	
	private String lastCommuncateTimeStr;//上次沟通时间
	
	private String modTimeStr;//更新时间
	
	private String provinceName;//省
	
	private String cityName;//市
	
	private String zoneName;//区
	
	private String addTimeStr;//添加时间格式化
	
	private String orgName;//部门
	
	private String traderContactName;//默认联系人的名称
	
	private String traderContactMobile;//默认联系人的手机号
	
	private CommunicateRecord communicateRecord;//上次沟通记录
	
	private String lastVerifyUsermae;//最后审核人
	    
	private String verifyUsername;//当前审核人
	    
	private Integer verifyStatus;//审核状态
	
	private List<String> verifyUsernameList;//当前审核人
	
	private String [] threeMedicalType;//
	
	private String [] twoMedicalType;//
	
	private Integer traderAddressId;//联系地址ID
	
	private List<TraderOrderGoods> traderOrderGoodsList;
	
	private List<Integer> userIdList;//客户id集合
	
	private Integer userId;//客户归属的用户
	
	private Integer communicateRecordId;
	
	private String registeredDateStr;
	
	private List<String> categoryNameList;//订单覆盖品类
	
	private List<String> brandNameList;//订单覆盖品牌
	
	private List<String> areaList;//订单覆盖产品
	
	private String traderLevelStr;//合作等级



	private Integer customerCategory;

	private String officeStr;
    // 财务审核状态
	private Integer financeCheckStatus;

	public Integer getFinanceCheckStatus() {
		return financeCheckStatus;
	}

	public void setFinanceCheckStatus(Integer financeCheckStatus) {
		this.financeCheckStatus = financeCheckStatus;
	}

	public String getOfficeStr() {
		return officeStr;
	}

	public void setOfficeStr(String officeStr) {
		this.officeStr = officeStr;
	}

	@Override
	public Integer getCustomerCategory() {
		return customerCategory;
	}

	@Override
	public void setCustomerCategory(Integer customerCategory) {
		this.customerCategory = customerCategory;
	}



	public Integer getAptitudeStatus() {
		return aptitudeStatus;
	}

	public void setAptitudeStatus(Integer aptitudeStatus) {
		this.aptitudeStatus = aptitudeStatus;
	}

	public String getTraderLevelStr() {
        return traderLevelStr;
    }

    public void setTraderLevelStr(String traderLevelStr) {
        this.traderLevelStr = traderLevelStr;
    }

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

	public List<String> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<String> areaList) {
		this.areaList = areaList;
	}

	public String getRegisteredDateStr() {
		return registeredDateStr;
	}

	public void setRegisteredDateStr(String registeredDateStr) {
		this.registeredDateStr = registeredDateStr;
	}

	public Integer getCommunicateRecordId() {
		return communicateRecordId;
	}

	public void setCommunicateRecordId(Integer communicateRecordId) {
		this.communicateRecordId = communicateRecordId;
	}

	public String[] getThreeMedicalType() {
		return threeMedicalType;
	}

	public void setThreeMedicalType(String[] threeMedicalType) {
		this.threeMedicalType = threeMedicalType;
	}

	public String[] getTwoMedicalType() {
		return twoMedicalType;
	}

	public void setTwoMedicalType(String[] twoMedicalType) {
		this.twoMedicalType = twoMedicalType;
	}

	public CommunicateRecord getCommunicateRecord() {
		return communicateRecord;
	}

	public void setCommunicateRecord(CommunicateRecord communicateRecord) {
		this.communicateRecord = communicateRecord;
	}

	private List<Integer> communicateTraderIds;//沟通客户集合
	
	private List<Integer> searchMsgTraderIds;//信息搜索客户ID

	public String getCustomerNatureStr() {
		return customerNatureStr;
	}

	public void setCustomerNatureStr(String customerNatureStr) {
		this.customerNatureStr = customerNatureStr;
	}

	public String getOptType() {
		return optType;
	}

	public void setOptType(String optType) {
		this.optType = optType;
	}

	public String getSearchTraderName() {
		return searchTraderName;
	}

	public void setSearchTraderName(String searchTraderName) {
		this.searchTraderName = searchTraderName == null ? null : searchTraderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public String getName() {
		return name;
	}

	public String getTraderName() {
		return traderName;
	}

	public void setTraderName(String traderName) {
		this.traderName = traderName==null ? null :traderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public void setName(String name) {
		this.name = name == null ? null : name.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getCustomerStatus() {
		return customerStatus;
	}

	public void setCustomerStatus(Integer customerStatus) {
		this.customerStatus = customerStatus;
	}

	public Integer getCustomerProperty() {
		return customerProperty;
	}

	public void setCustomerProperty(Integer customerProperty) {
		this.customerProperty = customerProperty;
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

	public Integer getQuoteCount() {
		return quoteCount;
	}

	public void setQuoteCount(Integer quoteCount) {
		this.quoteCount = quoteCount;
	}

	public Integer getCommuncateCount() {
		return communcateCount;
	}

	public void setCommuncateCount(Integer communcateCount) {
		this.communcateCount = communcateCount;
	}

	public String getPartners() {
		return partners;
	}

	public void setPartners(String partners) {
		this.partners = partners;
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

	public String getPersonal() {
		return personal;
	}

	public void setPersonal(String personal) {
		this.personal = personal;
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

	public Integer getWxStatus() {
		return wxStatus;
	}

	public void setWxStatus(Integer wxStatus) {
		this.wxStatus = wxStatus;
	}

	public Integer getSmallScore() {
		return smallScore;
	}

	public void setSmallScore(Integer smallScore) {
		this.smallScore = smallScore;
	}

	public Integer getBigScore() {
		return bigScore;
	}

	public void setBigScore(Integer bigScore) {
		this.bigScore = bigScore;
	}

	public Integer getQuote() {
		return quote;
	}

	public void setQuote(Integer quote) {
		this.quote = quote;
	}

	public Integer getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}

	public List<Integer> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Integer> categoryList) {
		this.categoryList = categoryList;
	}

	public Long getLastBussinessTime() {
		return lastBussinessTime;
	}

	public void setLastBussinessTime(Long lastBussinessTime) {
		this.lastBussinessTime = lastBussinessTime;
	}

	public String getCustomerTypeStr() {
		return customerTypeStr;
	}

	public void setCustomerTypeStr(String customerTypeStr) {
		this.customerTypeStr = customerTypeStr;
	}

	public String getCustomerPropertyStr() {
		return customerPropertyStr;
	}

	public void setCustomerPropertyStr(String customerPropertyStr) {
		this.customerPropertyStr = customerPropertyStr;
	}

	public Integer getCustomerPropertyType() {
		return customerPropertyType;
	}

	public void setCustomerPropertyType(Integer customerPropertyType) {
		this.customerPropertyType = customerPropertyType;
	}

	public String getAreaIds() {
		return areaIds;
	}

	public void setAreaIds(String areaIds) {
		this.areaIds = areaIds;
	}

	public Integer getBussinessChanceCount() {
		return bussinessChanceCount;
	}

	public void setBussinessChanceCount(Integer bussinessChanceCount) {
		this.bussinessChanceCount = bussinessChanceCount;
	}

	public BigDecimal getAveragePrice() {
		return averagePrice;
	}

	public void setAveragePrice(BigDecimal averagePrice) {
		this.averagePrice = averagePrice;
	}

	public BigDecimal getTransactionFrequency() {
		return transactionFrequency;
	}

	public void setTransactionFrequency(BigDecimal transactionFrequency) {
		this.transactionFrequency = transactionFrequency;
	}

	public Long getLastCommuncateTime() {
		return lastCommuncateTime;
	}

	public void setLastCommuncateTime(Long lastCommuncateTime) {
		this.lastCommuncateTime = lastCommuncateTime;
	}

	public Long getFirstBussinessTime() {
		return firstBussinessTime;
	}

	public void setFirstBussinessTime(Long firstBussinessTime) {
		this.firstBussinessTime = firstBussinessTime;
	}

	public List<Integer> getEnquiryOrderIds() {
		return enquiryOrderIds;
	}

	public void setEnquiryOrderIds(List<Integer> enquiryOrderIds) {
		this.enquiryOrderIds = enquiryOrderIds;
	}

	public List<Integer> getQuoteOrderIds() {
		return quoteOrderIds;
	}

	public void setQuoteOrderIds(List<Integer> quoteOrderIds) {
		this.quoteOrderIds = quoteOrderIds;
	}

	public List<Integer> getSaleOrderIds() {
		return saleOrderIds;
	}

	public void setSaleOrderIds(List<Integer> saleOrderIds) {
		this.saleOrderIds = saleOrderIds;
	}

	public List<Integer> getBuyOrderIds() {
		return buyOrderIds;
	}

	public void setBuyOrderIds(List<Integer> buyOrderIds) {
		this.buyOrderIds = buyOrderIds;
	}

	public List<Integer> getServiceOrderIds() {
		return serviceOrderIds;
	}

	public void setServiceOrderIds(List<Integer> serviceOrderIds) {
		this.serviceOrderIds = serviceOrderIds;
	}

	public TraderSupplierVo getTraderSupplierVo() {
		return traderSupplierVo;
	}

	public void setTraderSupplierVo(TraderSupplierVo traderSupplierVo) {
		this.traderSupplierVo = traderSupplierVo;
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

	public List<BussinessChanceVo> getBussinessChanceList() {
		return bussinessChanceList;
	}

	public void setBussinessChanceList(List<BussinessChanceVo> bussinessChanceList) {
		this.bussinessChanceList = bussinessChanceList;
	}

	public List<Quoteorder> getQuoteorderList() {
		return quoteorderList;
	}

	public void setQuoteorderList(List<Quoteorder> quoteorderList) {
		this.quoteorderList = quoteorderList;
	}

	public List<Saleorder> getSaleorderList() {
		return saleorderList;
	}

	public void setSaleorderList(List<Saleorder> saleorderList) {
		this.saleorderList = saleorderList;
	}

	public Integer getTraderContactId() {
		return traderContactId;
	}

	public void setTraderContactId(Integer traderContactId) {
		this.traderContactId = traderContactId;
	}

	public List<CommunicateRecord> getCommunicateRecordList() {
		return communicateRecordList;
	}

	public void setCommunicateRecordList(List<CommunicateRecord> communicateRecordList) {
		this.communicateRecordList = communicateRecordList;
	}

	public String getSearchMsg() {
		return searchMsg;
	}

	public void setSearchMsg(String searchMsg) {
		this.searchMsg = searchMsg;
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

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

	public BigDecimal getOrderTotalAmount() {
		return orderTotalAmount;
	}

	public void setOrderTotalAmount(BigDecimal orderTotalAmount) {
		this.orderTotalAmount = orderTotalAmount;
	}

	public BigDecimal getGrossRate() {
		return grossRate;
	}

	public void setGrossRate(BigDecimal grossRate) {
		this.grossRate = grossRate;
	}

	public Integer getTraderGoodsNum() {
		return traderGoodsNum;
	}

	public void setTraderGoodsNum(Integer traderGoodsNum) {
		this.traderGoodsNum = traderGoodsNum;
	}

	public Integer getTraderGoodsTypeNum() {
		return traderGoodsTypeNum;
	}

	public void setTraderGoodsTypeNum(Integer traderGoodsTypeNum) {
		this.traderGoodsTypeNum = traderGoodsTypeNum;
	}

	public Integer getTraderBrandNum() {
		return traderBrandNum;
	}

	public void setTraderBrandNum(Integer traderBrandNum) {
		this.traderBrandNum = traderBrandNum;
	}

	public BigDecimal getSaleorderRate() {
		return saleorderRate;
	}

	public void setSaleorderRate(BigDecimal saleorderRate) {
		this.saleorderRate = saleorderRate;
	}

	public BigDecimal getRefundGoodsAmount() {
		return refundGoodsAmount;
	}

	public void setRefundGoodsAmount(BigDecimal refundGoodsAmount) {
		this.refundGoodsAmount = refundGoodsAmount;
	}

	public Integer getRefundGoodsTimes() {
		return refundGoodsTimes;
	}

	public void setRefundGoodsTimes(Integer refundGoodsTimes) {
		this.refundGoodsTimes = refundGoodsTimes;
	}

	public String getFirstTraderTimeStr() {
		return firstTraderTimeStr;
	}

	public void setFirstTraderTimeStr(String firstTraderTimeStr) {
		this.firstTraderTimeStr = firstTraderTimeStr;
	}

	public String getLastTraderTimeStr() {
		return lastTraderTimeStr;
	}

	public void setLastTraderTimeStr(String lastTraderTimeStr) {
		this.lastTraderTimeStr = lastTraderTimeStr;
	}

	public String getLastCommuncateTimeStr() {
		return lastCommuncateTimeStr;
	}

	public void setLastCommuncateTimeStr(String lastCommuncateTimeStr) {
		this.lastCommuncateTimeStr = lastCommuncateTimeStr;
	}

	public String getModTimeStr() {
		return modTimeStr;
	}

	public void setModTimeStr(String modTimeStr) {
		this.modTimeStr = modTimeStr;
	}


	public String getCustomerPropertys() {
		return customerPropertys;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setCustomerPropertys(String customerPropertys) {
		this.customerPropertys = customerPropertys;
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

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
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

	public List<Integer> getCommunicateTraderIds() {
		return communicateTraderIds;
	}

	public void setCommunicateTraderIds(List<Integer> communicateTraderIds) {
		this.communicateTraderIds = communicateTraderIds;
	}

	public List<Integer> getSearchMsgTraderIds() {
		return searchMsgTraderIds;
	}

	public void setSearchMsgTraderIds(List<Integer> searchMsgTraderIds) {
		this.searchMsgTraderIds = searchMsgTraderIds;
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

	public Integer getTraderAddressId() {
		return traderAddressId;
	}

	public void setTraderAddressId(Integer traderAddressId) {
		this.traderAddressId = traderAddressId;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
