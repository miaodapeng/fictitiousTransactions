package com.vedeng.order.model;

import com.vedeng.system.model.Attachment;

import java.io.Serializable;
import java.math.BigDecimal;

public class BussinessChance implements Serializable{
    /**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private Integer bussinessChanceId;

    private Integer webBussinessChanceId;

    private String bussinessChanceNo;

    private Integer webAccountId;
    
    private Integer companyId;

    private Integer userId;

    private Integer traderId;
    
    private String checkTraderArea;//确认客户地区
    
	private String checkTraderName;// 确认客户名称

	private String checkTraderContactName;// 确认联系人

	private String checkMobile;// 确认联系人手机
	
	private String checkTraderContactTelephone;//确认联系人电话

    private Integer type;

    private Long receiveTime;

    private Integer source;

    /**
     * 咨询入口：1商品详情页，2搜索结果页，3产品导航页，4品牌中心，5页头，6右侧悬浮，7专题模板
     */
    private Integer entrances;

    /**
     * 功能：1立即询价，2立即订购，3商品咨询，4帮您找货，5发送采购需求
     */
    private Integer functions;

    private Integer communication;

    private String content;

    private Integer goodsCategory;

    private String goodsBrand;

    private String goodsName;

    private String traderName;

    private Integer areaId;

    private String areaIds;

    private Integer traderContactId;

    private String traderContactName;

    private String mobile;

    private String telephone;

    private String otherContact;

    private String comments;

    private Long assignTime;

    private Long firstViewTime;

    private Integer status;

    private Integer statusComments;

    private String closedComments;

    private String wenxinOpenId;

    private Long addTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;
    
    private Integer orgId;
    
    private Integer isRest;
    
    private Integer bussinessLevel;
    
    private Integer bussinessStage;
    
    private Integer enquiryType;
    
    private Integer orderRate;
    
    private BigDecimal amount;
    
    private Long orderTime;
    
    private String orderTimeStr;
    
    private Integer cancelReason;
    
    private String otherReason;

    public Integer getEntrances() {
        return entrances;
    }

    public void setEntrances(Integer entrances) {
        this.entrances = entrances;
    }

    public Integer getFunctions() {
        return functions;
    }

    public void setFunctions(Integer functions) {
        this.functions = functions;
    }

    public Integer getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(Integer cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getOtherReason() {
        return otherReason;
    }

    public void setOtherReason(String otherReason) {
        this.otherReason = otherReason;
    }

    public String getOrderTimeStr() {
        return orderTimeStr;
    }

    public void setOrderTimeStr(String orderTimeStr) {
        this.orderTimeStr = orderTimeStr;
    }

    public Integer getBussinessLevel() {
        return bussinessLevel;
    }

    public void setBussinessLevel(Integer bussinessLevel) {
        this.bussinessLevel = bussinessLevel;
    }

    public Integer getBussinessStage() {
        return bussinessStage;
    }

    public void setBussinessStage(Integer bussinessStage) {
        this.bussinessStage = bussinessStage;
    }

    public Integer getEnquiryType() {
        return enquiryType;
    }

    public void setEnquiryType(Integer enquiryType) {
        this.enquiryType = enquiryType;
    }

    public Integer getOrderRate() {
        return orderRate;
    }

    public void setOrderRate(Integer orderRate) {
        this.orderRate = orderRate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Long orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getBussinessChanceId() {
        return bussinessChanceId;
    }

    public void setBussinessChanceId(Integer bussinessChanceId) {
        this.bussinessChanceId = bussinessChanceId;
    }

    public Integer getWebBussinessChanceId() {
        return webBussinessChanceId;
    }

    public void setWebBussinessChanceId(Integer webBussinessChanceId) {
        this.webBussinessChanceId = webBussinessChanceId;
    }

    public String getBussinessChanceNo() {
        return bussinessChanceNo;
    }

    public void setBussinessChanceNo(String bussinessChanceNo) {
        this.bussinessChanceNo = bussinessChanceNo == null ? null : bussinessChanceNo.trim();
    }

    public Integer getWebAccountId() {
        return webAccountId;
    }

    public void setWebAccountId(Integer webAccountId) {
        this.webAccountId = webAccountId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTraderId() {
        return traderId;
    }

    public void setTraderId(Integer traderId) {
        this.traderId = traderId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Long receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getCommunication() {
        return communication;
    }

    public void setCommunication(Integer communication) {
        this.communication = communication;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getGoodsCategory() {
        return goodsCategory;
    }

    public void setGoodsCategory(Integer goodsCategory) {
        this.goodsCategory = goodsCategory;
    }

    public String getGoodsBrand() {
        return goodsBrand;
    }

    public void setGoodsBrand(String goodsBrand) {
        this.goodsBrand = goodsBrand == null ? null : goodsBrand.trim();
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public String getTraderName() {
        return traderName;
    }

    public void setTraderName(String traderName) {
        this.traderName = traderName==null ? null :traderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(String areaIds) {
        this.areaIds = areaIds == null ? null : areaIds.trim();
    }

    public Integer getTraderContactId() {
        return traderContactId;
    }

    public void setTraderContactId(Integer traderContactId) {
        this.traderContactId = traderContactId;
    }

    public String getTraderContactName() {
        return traderContactName;
    }

    public void setTraderContactName(String traderContactName) {
        this.traderContactName = traderContactName == null ? null : traderContactName.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getOtherContact() {
        return otherContact;
    }

    public void setOtherContact(String otherContact) {
        this.otherContact = otherContact == null ? null : otherContact.trim();
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
    }

    public Long getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(Long assignTime) {
        this.assignTime = assignTime;
    }

    public Long getFirstViewTime() {
        return firstViewTime;
    }

    public void setFirstViewTime(Long firstViewTime) {
        this.firstViewTime = firstViewTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatusComments() {
        return statusComments;
    }

    public void setStatusComments(Integer statusComments) {
        this.statusComments = statusComments;
    }

    public String getWenxinOpenId() {
        return wenxinOpenId;
    }

    public void setWenxinOpenId(String wenxinOpenId) {
        this.wenxinOpenId = wenxinOpenId == null ? null : wenxinOpenId.trim();
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

	public String getClosedComments() {
		return closedComments;
	}

	public void setClosedComments(String closedComments) {
		this.closedComments = closedComments;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getCheckTraderArea() {
		return checkTraderArea;
	}

	public void setCheckTraderArea(String checkTraderArea) {
		this.checkTraderArea = checkTraderArea;
	}

	public String getCheckTraderName() {
		return checkTraderName;
	}

	public void setCheckTraderName(String checkTraderName) {
		this.checkTraderName = checkTraderName==null ? null :checkTraderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public String getCheckTraderContactName() {
		return checkTraderContactName;
	}

	public void setCheckTraderContactName(String checkTraderContactName) {
		this.checkTraderContactName = checkTraderContactName;
	}

	public String getCheckMobile() {
		return checkMobile;
	}

	public void setCheckMobile(String checkMobile) {
		this.checkMobile = checkMobile;
	}

	public String getCheckTraderContactTelephone() {
		return checkTraderContactTelephone;
	}

	public void setCheckTraderContactTelephone(String checkTraderContactTelephone) {
		this.checkTraderContactTelephone = checkTraderContactTelephone;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getIsRest() {
		return isRest;
	}

	public void setIsRest(Integer isRest) {
		this.isRest = isRest;
	}
	
}