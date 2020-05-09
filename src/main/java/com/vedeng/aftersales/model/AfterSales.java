package com.vedeng.aftersales.model;

import java.io.Serializable;
import java.util.List;

public class AfterSales implements Serializable{
    /**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private Integer afterSalesId;

    private String afterSalesNo;
    
    private Integer companyId;

    private Integer subjectType;

    private Integer type;

    private Integer orderId;

    private String orderNo;

    private Integer serviceUserId;

    private Integer validStatus;

    private Long validTime;

    private Integer status;

    private Integer atferSalesStatus;

    private Long addTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;
    
    private Integer flag;//标识
    
    private String pickNums;// 订单的拣货数+每个批次拣货数
    
    private String idCnt;// 扫码出库值
    
    private Integer businessType;//业务类型
    
    private AfterSalesDetail afterSalesDetail;
    
    private String verifyUsername;//当前审核人
    
    private Integer verifyStatus;//审核状态
    
    private String area,address;
    
    // begin add by franlin at 2018-05-09 for 用于区分页面场景：通用页面场景为0
    private Integer scenesPageType = 0; 
    // end add by franlin at 2018-05-09 for 用于区分页面场景：通用页面场景为0
    
    /***2018-7-31***/
    private	Integer afterSalesStatusReson;//售后订单关闭原因Id

    private List<AfterSalesTrader> afterSalesTraderList;

    private	String afterSalesStatusResonName;//售后订单关闭原因
    
    private	Integer afterSalesStatusUser;//售后订单关闭人
    
    private	String afterSalesStatusUserName;//售后订单关闭人姓名
    
    private	String afterSalesStatusComments;//售后订单关闭备注
    /***2018-07-31 end ***/
    
    /*** 2018-9-5 新增开票/收票状态***/
    private Integer invoiceStatus;//开票状态
    
    private Long invoiceTime;//开票时间
    
    private Integer receiveInvoiceStatus;//收票状态
    
    private Long receiveInvoiceTime;//收票时间
    /*** END ***/

	private Integer firstValidStatus; //首次审核状态0待审核1审核通过2审核不通过

	private Long firstValidTime; //首次审核时间

	private Integer firstValidUser; //首次审核人ID

	private String firstValidComments; //首次审核原因

	private Integer source; //售后来源 0:ERP 1:耗材
	/**活动id*/
	private Integer actionId;

	/**
	 * 所有的产品afterSalesGoodsIds的集合
	 */
	private String afterSalesGoodsIds;

	private Integer isOutAfter;//是否可以出库(1、可以  2 不可以)

	public Integer getActionId() {
		return actionId;
	}

	public void setActionId(Integer actionId) {
		this.actionId = actionId;
	}

	public Integer getFirstValidStatus() {
		return firstValidStatus;
	}

	public void setFirstValidStatus(Integer firstValidStatus) {
		this.firstValidStatus = firstValidStatus;
	}

	public Long getFirstValidTime() {
		return firstValidTime;
	}

	public void setFirstValidTime(Long firstValidTime) {
		this.firstValidTime = firstValidTime;
	}

	public Integer getFirstValidUser() {
		return firstValidUser;
	}

	public void setFirstValidUser(Integer firstValidUser) {
		this.firstValidUser = firstValidUser;
	}

	public String getFirstValidComments() {
		return firstValidComments;
	}

	public void setFirstValidComments(String firstValidComments) {
		this.firstValidComments = firstValidComments;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public String getArea() {
		return area;
	}

	public Integer getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(Integer invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public Long getInvoiceTime() {
		return invoiceTime;
	}

	public void setInvoiceTime(Long invoiceTime) {
		this.invoiceTime = invoiceTime;
	}

	public Integer getReceiveInvoiceStatus() {
		return receiveInvoiceStatus;
	}

	public void setReceiveInvoiceStatus(Integer receiveInvoiceStatus) {
		this.receiveInvoiceStatus = receiveInvoiceStatus;
	}

	public Long getReceiveInvoiceTime() {
		return receiveInvoiceTime;
	}

	public void setReceiveInvoiceTime(Long receiveInvoiceTime) {
		this.receiveInvoiceTime = receiveInvoiceTime;
	}

	public Integer getAfterSalesStatusReson() {
		return afterSalesStatusReson;
	}

	public void setAfterSalesStatusReson(Integer afterSalesStatusReson) {
		this.afterSalesStatusReson = afterSalesStatusReson;
	}

	public List<AfterSalesTrader> getAfterSalesTraderList() {
		return afterSalesTraderList;
	}

	public void setAfterSalesTraderList(List<AfterSalesTrader> afterSalesTraderList) {
		this.afterSalesTraderList = afterSalesTraderList;
	}

	public String getAfterSalesStatusResonName() {
		return afterSalesStatusResonName;
	}

	public void setAfterSalesStatusResonName(String afterSalesStatusResonName) {
		this.afterSalesStatusResonName = afterSalesStatusResonName;
	}

	public Integer getAfterSalesStatusUser() {
		return afterSalesStatusUser;
	}

	public void setAfterSalesStatusUser(Integer afterSalesStatusUser) {
		this.afterSalesStatusUser = afterSalesStatusUser;
	}

	public String getAfterSalesStatusUserName() {
		return afterSalesStatusUserName;
	}

	public void setAfterSalesStatusUserName(String afterSalesStatusUserName) {
		this.afterSalesStatusUserName = afterSalesStatusUserName;
	}

	public String getAfterSalesStatusComments() {
		return afterSalesStatusComments;
	}

	public void setAfterSalesStatusComments(String afterSalesStatusComments) {
		this.afterSalesStatusComments = afterSalesStatusComments;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public AfterSalesDetail getAfterSalesDetail() {
		return afterSalesDetail;
	}

	public void setAfterSalesDetail(AfterSalesDetail afterSalesDetail) {
		this.afterSalesDetail = afterSalesDetail;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}
    
    public String getIdCnt() {
		return idCnt;
	}

	public void setIdCnt(String idCnt) {
		this.idCnt = idCnt;
	}

	public String getPickNums() {
		return pickNums;
	}

	public void setPickNums(String pickNums) {
		this.pickNums = pickNums;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Integer getAfterSalesId() {
        return afterSalesId;
    }

    public void setAfterSalesId(Integer afterSalesId) {
        this.afterSalesId = afterSalesId;
    }

    public String getAfterSalesNo() {
        return afterSalesNo;
    }

    public void setAfterSalesNo(String afterSalesNo) {
        this.afterSalesNo = afterSalesNo == null ? null : afterSalesNo.trim();
    }

    public Integer getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(Integer subjectType) {
        this.subjectType = subjectType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Integer getServiceUserId() {
        return serviceUserId;
    }

    public void setServiceUserId(Integer serviceUserId) {
        this.serviceUserId = serviceUserId;
    }

    public Integer getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Integer validStatus) {
        this.validStatus = validStatus;
    }

    public Long getValidTime() {
        return validTime;
    }

    public void setValidTime(Long validTime) {
        this.validTime = validTime;
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

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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

	public Integer getScenesPageType()
	{
		return scenesPageType;
	}

	public void setScenesPageType(Integer scenesPageType)
	{
		this.scenesPageType = scenesPageType;
	}

	public String getAfterSalesGoodsIds() {
		return afterSalesGoodsIds;
	}

	public void setAfterSalesGoodsIds(String afterSalesGoodsIds) {
		this.afterSalesGoodsIds = afterSalesGoodsIds;
	}

	public Integer getIsOutAfter() {
		return isOutAfter;
	}

	public void setIsOutAfter(Integer isOutAfter) {
		this.isOutAfter = isOutAfter;
	}
}
