package com.vedeng.order.model.vo;

import java.math.BigDecimal;
import java.util.List;

import com.vedeng.order.model.BussinessChance;

public class BussinessChanceVo extends BussinessChance {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private String sourceName;// 询价来源

	//咨询入口名称
	private String entranceName;

	/**
	 * 功能
	 */
	private String functionName;

	private String communicationName;// 询价方式

	private String saleUser;// 销售人员
	
	private String saleDeptName;//归属销售部门

	private Integer quoteorderId;//报价主键

	private String quoteorderNo;// 报价单号
	
	private Integer paymentType;//付款方式

	private Long quoteorderAddTime;// 报价时间

	private BigDecimal quoteorderTotalAmount;// 报价金额
	
	private Integer quoteorderStatus;//报价单状态
	
	private String saleorderId;//销售订单主键

	private String saleorderNo;// 销售订单单号

	private Long saleorderAddTime;// 销售订单时间

	private BigDecimal saleorderTotalAmount;// 销售订单金额

	private Integer saleorderstatus;// 销售订单状态

	private Integer timeType;// 时间类型

	private String starttime;// 开始时间

	private String endtime;// 结束时间

	private Integer province;// 省

	private Integer city;// 市

	private Integer zone;// 区

	private String closedComments;// 关闭原因

	private List<Integer> creatorId;// 创建人集合

	private Long starttimeLong;//

	private Long endtimeLong;//

	private String typeName;// 商机类型

	private String goodsCategoryName;// 商品类型

	private List<Integer> bussinessChanceIds;// 商机ID集合
	
	private List<String> bussinessChanceNos;// 商机编号集合

	private String attachmentName;// 附件名称

	private String attachmentUri;//

	private String attachmentDomain;//
	
	private String closeReason;//关闭原因
	
	private String areas;//所属地区
	
	private String creatorName;//创建人名称
	
	private String salerName;//分配销售人名称
	
	private List<Integer> userIds;//归属人集合
	
	private List<Integer> traderIds;//客户ID集合
	
	private Integer quoteValidStatus;//报价审核状态
	
	private String assignTimeStr;//
	
	private String quoteorderAddTimeStr;//
	
	private String saleorderAddTimeStr;//
	
	private String firstViewTimeStr;//
	
	private String addTimeStr;//
	
	private String respondTime;//
	
	private String lastVerifyUsermae;//最后审核人
	    
	private String verifyUsername;//当前审核人
	    
	private Integer verifyStatus;//审核状态
	
	private List<String> verifyUsernameList;//当前审核人
	
	private Integer currUserId;//当前登录人员的id
	
	private String nextContactDate;//下次沟通时间
	
    private String bussinessLevelStr;//商机等级
    
    private String bussinessStageStr;//商机阶段
    
    private String enquiryTypeStr;//询价类型
    
    private String orderRateStr;//成单几率
    
    private String flag;//是否用感叹号标记（1是0否）
    
    private String cancelReasonStr; //作废原因
    
    private String cdstarttime;//预计成单开始时间
    
    private String cdendtime;//预计成单结束时间
    
    private Long cdstarttimeLong;//预计成单开始时间
    
    private Long cdendtimeLong;//预计成单结束时间
    
    private String isPayment;//是否已成单未结款商机1是0否
    
    private BigDecimal totalAmount;//商机预计金额之和
    
    private Integer isRest;//是否重置筛选项

	//商机分配状态
	private Integer assignStatus;

	//客户分类，0为未交易客户，1为新客户,2为流失客户，3为留存客户
	private Integer traderCategory;

	public Integer getAssignStatus() {
		return assignStatus;
	}

	public void setAssignStatus(Integer assignStatus) {
		this.assignStatus = assignStatus;
	}

	public Integer getTraderCategory() {
		return traderCategory;
	}

	public void setTraderCategory(Integer traderCategory) {
		this.traderCategory = traderCategory;
	}

	public String getEntranceName() {
		return entranceName;
	}

	public void setEntranceName(String entranceName) {
		this.entranceName = entranceName;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public Integer getIsRest() {
        return isRest;
    }

    public void setIsRest(Integer isRest) {
        this.isRest = isRest;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getIsPayment() {
        return isPayment;
    }

    public void setIsPayment(String isPayment) {
        this.isPayment = isPayment;
    }

    public Long getCdstarttimeLong() {
        return cdstarttimeLong;
    }

    public void setCdstarttimeLong(Long cdstarttimeLong) {
        this.cdstarttimeLong = cdstarttimeLong;
    }

    public Long getCdendtimeLong() {
        return cdendtimeLong;
    }

    public void setCdendtimeLong(Long cdendtimeLong) {
        this.cdendtimeLong = cdendtimeLong;
    }

    public String getCdstarttime() {
        return cdstarttime;
    }

    public void setCdstarttime(String cdstarttime) {
        this.cdstarttime = cdstarttime;
    }

    public String getCdendtime() {
        return cdendtime;
    }

    public void setCdendtime(String cdendtime) {
        this.cdendtime = cdendtime;
    }

    public String getCancelReasonStr() {
        return cancelReasonStr;
    }

    public void setCancelReasonStr(String cancelReasonStr) {
        this.cancelReasonStr = cancelReasonStr;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getBussinessLevelStr() {
        return bussinessLevelStr;
    }

    public void setBussinessLevelStr(String bussinessLevelStr) {
        this.bussinessLevelStr = bussinessLevelStr;
    }

    public String getBussinessStageStr() {
        return bussinessStageStr;
    }

    public void setBussinessStageStr(String bussinessStageStr) {
        this.bussinessStageStr = bussinessStageStr;
    }

    public String getEnquiryTypeStr() {
        return enquiryTypeStr;
    }

    public void setEnquiryTypeStr(String enquiryTypeStr) {
        this.enquiryTypeStr = enquiryTypeStr;
    }

    public String getOrderRateStr() {
        return orderRateStr;
    }

    public void setOrderRateStr(String orderRateStr) {
        this.orderRateStr = orderRateStr;
    }

    public String getNextContactDate() {
        return nextContactDate;
    }

    public void setNextContactDate(String nextContactDate) {
        this.nextContactDate = nextContactDate;
    }

    public List<String> getBussinessChanceNos() {
		return bussinessChanceNos;
	}

	public void setBussinessChanceNos(List<String> bussinessChanceNos) {
		this.bussinessChanceNos = bussinessChanceNos;
	}

	public Integer getQuoteValidStatus() {
		return quoteValidStatus;
	}

	private Integer bussinessChance;//商机次数
	
	public void setQuoteValidStatus(Integer quoteValidStatus) {
		this.quoteValidStatus = quoteValidStatus;
	}

	public String getQuoteorderNo() {
		return quoteorderNo;
	}

	public void setQuoteorderNo(String quoteorderNo) {
		this.quoteorderNo = quoteorderNo;
	}

	public Long getQuoteorderAddTime() {
		return quoteorderAddTime;
	}

	public void setQuoteorderAddTime(Long quoteorderAddTime) {
		this.quoteorderAddTime = quoteorderAddTime;
	}

	public BigDecimal getQuoteorderTotalAmount() {
		return quoteorderTotalAmount;
	}

	public void setQuoteorderTotalAmount(BigDecimal quoteorderTotalAmount) {
		this.quoteorderTotalAmount = quoteorderTotalAmount;
	}

	public String getSaleorderNo() {
		return saleorderNo;
	}

	public void setSaleorderNo(String saleorderNo) {
		this.saleorderNo = saleorderNo;
	}

	public Long getSaleorderAddTime() {
		return saleorderAddTime;
	}

	public void setSaleorderAddTime(Long saleorderAddTime) {
		this.saleorderAddTime = saleorderAddTime;
	}

	public BigDecimal getSaleorderTotalAmount() {
		return saleorderTotalAmount;
	}

	public void setSaleorderTotalAmount(BigDecimal saleorderTotalAmount) {
		this.saleorderTotalAmount = saleorderTotalAmount;
	}

	public Integer getSaleorderstatus() {
		return saleorderstatus;
	}

	public void setSaleorderstatus(Integer saleorderstatus) {
		this.saleorderstatus = saleorderstatus;
	}

	public Integer getTimeType() {
		return timeType;
	}

	public void setTimeType(Integer timeType) {
		this.timeType = timeType;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
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

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getCommunicationName() {
		return communicationName;
	}

	public void setCommunicationName(String communicationName) {
		this.communicationName = communicationName;
	}

	public String getSaleUser() {
		return saleUser;
	}

	public void setSaleUser(String saleUser) {
		this.saleUser = saleUser;
	}

	public String getClosedComments() {
		return closedComments;
	}

	public void setClosedComments(String closedComments) {
		this.closedComments = closedComments;
	}

	public List<Integer> getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(List<Integer> creatorId) {
		this.creatorId = creatorId;
	}

	public Long getStarttimeLong() {
		return starttimeLong;
	}

	public void setStarttimeLong(Long starttimeLong) {
		this.starttimeLong = starttimeLong;
	}

	public Long getEndtimeLong() {
		return endtimeLong;
	}

	public void setEndtimeLong(Long endtimeLong) {
		this.endtimeLong = endtimeLong;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getGoodsCategoryName() {
		return goodsCategoryName;
	}

	public void setGoodsCategoryName(String goodsCategoryName) {
		this.goodsCategoryName = goodsCategoryName;
	}

	public List<Integer> getBussinessChanceIds() {
		return bussinessChanceIds;
	}

	public void setBussinessChanceIds(List<Integer> bussinessChanceIds) {
		this.bussinessChanceIds = bussinessChanceIds;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public String getAttachmentUri() {
		return attachmentUri;
	}

	public void setAttachmentUri(String attachmentUri) {
		this.attachmentUri = attachmentUri;
	}

	public String getAttachmentDomain() {
		return attachmentDomain;
	}

	public void setAttachmentDomain(String attachmentDomain) {
		this.attachmentDomain = attachmentDomain;
	}

	public String getCloseReason() {
		return closeReason;
	}

	public void setCloseReason(String closeReason) {
		this.closeReason = closeReason;
	}

	public String getAreas() {
		return areas;
	}

	public void setAreas(String areas) {
		this.areas = areas;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getSalerName() {
		return salerName;
	}

	public void setSalerName(String salerName) {
		this.salerName = salerName;
	}

	public Integer getQuoteorderId() {
		return quoteorderId;
	}

	public void setQuoteorderId(Integer quoteorderId) {
		this.quoteorderId = quoteorderId;
	}

	public String getSaleorderId() {
		return saleorderId;
	}

	public void setSaleorderId(String saleorderId) {
		this.saleorderId = saleorderId;
	}

	public Integer getQuoteorderStatus() {
		return quoteorderStatus;
	}

	public void setQuoteorderStatus(Integer quoteorderStatus) {
		this.quoteorderStatus = quoteorderStatus;
	}

	public Integer getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}

	public List<Integer> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<Integer> userIds) {
		this.userIds = userIds;
	}

	public List<Integer> getTraderIds() {
		return traderIds;
	}

	public void setTraderIds(List<Integer> traderIds) {
		this.traderIds = traderIds;
	}

	public String getSaleDeptName() {
		return saleDeptName;
	}

	public void setSaleDeptName(String saleDeptName) {
		this.saleDeptName = saleDeptName;
	}

	public Integer getBussinessChance() {
		return bussinessChance;
	}

	public void setBussinessChance(Integer bussinessChance) {
		this.bussinessChance = bussinessChance;
	}

	public String getAssignTimeStr() {
		return assignTimeStr;
	}

	public void setAssignTimeStr(String assignTimeStr) {
		this.assignTimeStr = assignTimeStr;
	}

	public String getQuoteorderAddTimeStr() {
		return quoteorderAddTimeStr;
	}

	public void setQuoteorderAddTimeStr(String quoteorderAddTimeStr) {
		this.quoteorderAddTimeStr = quoteorderAddTimeStr;
	}

	public String getSaleorderAddTimeStr() {
		return saleorderAddTimeStr;
	}

	public void setSaleorderAddTimeStr(String saleorderAddTimeStr) {
		this.saleorderAddTimeStr = saleorderAddTimeStr;
	}

	public String getFirstViewTimeStr() {
		return firstViewTimeStr;
	}

	public void setFirstViewTimeStr(String firstViewTimeStr) {
		this.firstViewTimeStr = firstViewTimeStr;
	}

	public String getAddTimeStr() {
		return addTimeStr;
	}

	public void setAddTimeStr(String addTimeStr) {
		this.addTimeStr = addTimeStr;
	}

	public String getRespondTime() {
		return respondTime;
	}

	public void setRespondTime(String respondTime) {
		this.respondTime = respondTime;
	}

	public Integer getVerifyStatus() {
	    return verifyStatus;
	}

	public void setVerifyStatus(Integer verifyStatus) {
	    this.verifyStatus = verifyStatus;
	}


	public String getVerifyUsername() {
	    return verifyUsername;
	}

	public void setVerifyUsername(String verifyUsername) {
	    this.verifyUsername = verifyUsername;
	}

	public List<String> getVerifyUsernameList() {
	    return verifyUsernameList;
	}

	public void setVerifyUsernameList(List<String> verifyUsernameList) {
	    this.verifyUsernameList = verifyUsernameList;
	}

	public String getLastVerifyUsermae() {
	    return lastVerifyUsermae;
	}

	public void setLastVerifyUsermae(String lastVerifyUsermae) {
	    this.lastVerifyUsermae = lastVerifyUsermae;
	}

	public Integer getCurrUserId() {
		return currUserId;
	}

	public void setCurrUserId(Integer currUserId) {
		this.currUserId = currUserId;
	}

}
