package com.vedeng.order.model;

import java.io.Serializable;
import java.util.List;

public class SaleorderModifyApply implements Serializable{
    private Integer saleorderModifyApplyId;

    private String saleorderModifyApplyNo;
    
    private Integer companyId;

    private Integer saleorderId;

    private Integer validStatus;

    private Long validTime;

    private Integer takeTraderId;

    private String takeTraderName;

    private Integer takeTraderContactId;

    private String takeTraderContactName;

    private String takeTraderContactMobile;

    private String takeTraderContactTelephone;

    private Integer takeTraderAddressId;

    private String takeTraderArea;

    private String takeTraderAddress;

    private String logisticsComments;

    private Integer invoiceTraderId;

    private String invoiceTraderName;

    private Integer invoiceTraderContactId;

    private String invoiceTraderContactName;

    private String invoiceTraderContactMobile;

    private String invoiceTraderContactTelephone;

    private Integer invoiceTraderAddressId;

    private String invoiceTraderArea;

    private String invoiceTraderAddress;

    private String invoiceComments;

    private Integer oldTakeTraderContactId;

    private String oldTakeTraderContactName;

    private String oldTakeTraderContactMobile;

    private String oldTakeTraderContactTelephone;

    private Integer oldTakeTraderAddressId;

    private String oldTakeTraderArea;

    private String oldTakeTraderAddress;

    private String oldLogisticsComments;

    private Integer oldInvoiceTraderContactId;

    private String oldInvoiceTraderContactName;

    private String oldInvoiceTraderContactMobile;

    private String oldInvoiceTraderContactTelephone;

    private Integer oldInvoiceTraderAddressId;

    private String oldInvoiceTraderArea;

    private String oldInvoiceTraderAddress;

    private String oldInvoiceComments;
    
    private Integer invoiceType;
    
    private Integer isSendInvoice;
    
    private Integer oldInvoiceType;
    
    private Integer oldIsSendInvoice;

    private Long addTime;

    private Integer creator;
    
    private List<SaleorderModifyApplyGoods> goodsList;// 订单申请修改下的商品列表
    
    private Integer verifiesType;//审核类型
    
    private String verifyUsername;//当前审核人
    
    private Integer verifyStatus;//审核状态
    
    private Integer isChangeDeliveryDirect;//是否修改直发普发状态 0未修改 1修改
    
    private Integer isChangeTakeTraderAddressId;//是否修改收货地址 0未修改 1修改
    
    private Integer isChangeLogisticsComments;//是否修改物流备注 0未修改 1修改
    
    private List<Integer> buyorderUsers;//需要审核的采购负责人
    
    private String createName;//创建者人
    
    private Integer isDelayInvoice;
    
    private Integer oldIsDelayInvoice;
    
    private Integer invoiceMethod;
    
    private Integer oldInvoiceMethod;

    //是否打印出库单  0不打印  1打印带价格出库单  2打印不带价格出库单
    private Integer isPrintout;

    public Integer getSaleorderModifyApplyId() {
        return saleorderModifyApplyId;
    }

    public void setSaleorderModifyApplyId(Integer saleorderModifyApplyId) {
        this.saleorderModifyApplyId = saleorderModifyApplyId;
    }

    public String getSaleorderModifyApplyNo() {
        return saleorderModifyApplyNo;
    }

    public void setSaleorderModifyApplyNo(String saleorderModifyApplyNo) {
        this.saleorderModifyApplyNo = saleorderModifyApplyNo == null ? null : saleorderModifyApplyNo.trim();
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getSaleorderId() {
        return saleorderId;
    }

    public void setSaleorderId(Integer saleorderId) {
        this.saleorderId = saleorderId;
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

    public Integer getTakeTraderId() {
        return takeTraderId;
    }

    public void setTakeTraderId(Integer takeTraderId) {
        this.takeTraderId = takeTraderId;
    }

    public String getTakeTraderName() {
        return takeTraderName;
    }

    public void setTakeTraderName(String takeTraderName) {
        this.takeTraderName = takeTraderName==null ? null :takeTraderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
    }

    public Integer getTakeTraderContactId() {
        return takeTraderContactId;
    }

    public void setTakeTraderContactId(Integer takeTraderContactId) {
        this.takeTraderContactId = takeTraderContactId;
    }

    public String getTakeTraderContactName() {
        return takeTraderContactName;
    }

    public void setTakeTraderContactName(String takeTraderContactName) {
        this.takeTraderContactName = takeTraderContactName == null ? null : takeTraderContactName.trim();
    }

    public String getTakeTraderContactMobile() {
        return takeTraderContactMobile;
    }

    public void setTakeTraderContactMobile(String takeTraderContactMobile) {
        this.takeTraderContactMobile = takeTraderContactMobile == null ? null : takeTraderContactMobile.trim();
    }

    public String getTakeTraderContactTelephone() {
        return takeTraderContactTelephone;
    }

    public void setTakeTraderContactTelephone(String takeTraderContactTelephone) {
        this.takeTraderContactTelephone = takeTraderContactTelephone == null ? null : takeTraderContactTelephone.trim();
    }

    public Integer getTakeTraderAddressId() {
        return takeTraderAddressId;
    }

    public void setTakeTraderAddressId(Integer takeTraderAddressId) {
        this.takeTraderAddressId = takeTraderAddressId;
    }

    public String getTakeTraderArea() {
        return takeTraderArea;
    }

    public void setTakeTraderArea(String takeTraderArea) {
        this.takeTraderArea = takeTraderArea == null ? null : takeTraderArea.trim();
    }

    public String getTakeTraderAddress() {
        return takeTraderAddress;
    }

    public void setTakeTraderAddress(String takeTraderAddress) {
        this.takeTraderAddress = takeTraderAddress == null ? null : takeTraderAddress.trim();
    }

    public String getLogisticsComments() {
        return logisticsComments;
    }

    public void setLogisticsComments(String logisticsComments) {
        this.logisticsComments = logisticsComments == null ? null : logisticsComments.trim();
    }

    public Integer getInvoiceTraderId() {
        return invoiceTraderId;
    }

    public void setInvoiceTraderId(Integer invoiceTraderId) {
        this.invoiceTraderId = invoiceTraderId;
    }

    public String getInvoiceTraderName() {
        return invoiceTraderName;
    }

    public void setInvoiceTraderName(String invoiceTraderName) {
        this.invoiceTraderName = invoiceTraderName==null ? null :invoiceTraderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
    }

    public Integer getInvoiceTraderContactId() {
        return invoiceTraderContactId;
    }

    public void setInvoiceTraderContactId(Integer invoiceTraderContactId) {
        this.invoiceTraderContactId = invoiceTraderContactId;
    }

    public String getInvoiceTraderContactName() {
        return invoiceTraderContactName;
    }

    public void setInvoiceTraderContactName(String invoiceTraderContactName) {
        this.invoiceTraderContactName = invoiceTraderContactName == null ? null : invoiceTraderContactName.trim();
    }

    public String getInvoiceTraderContactMobile() {
        return invoiceTraderContactMobile;
    }

    public void setInvoiceTraderContactMobile(String invoiceTraderContactMobile) {
        this.invoiceTraderContactMobile = invoiceTraderContactMobile == null ? null : invoiceTraderContactMobile.trim();
    }

    public String getInvoiceTraderContactTelephone() {
        return invoiceTraderContactTelephone;
    }

    public void setInvoiceTraderContactTelephone(String invoiceTraderContactTelephone) {
        this.invoiceTraderContactTelephone = invoiceTraderContactTelephone == null ? null : invoiceTraderContactTelephone.trim();
    }

    public Integer getInvoiceTraderAddressId() {
        return invoiceTraderAddressId;
    }

    public void setInvoiceTraderAddressId(Integer invoiceTraderAddressId) {
        this.invoiceTraderAddressId = invoiceTraderAddressId;
    }

    public String getInvoiceTraderArea() {
        return invoiceTraderArea;
    }

    public void setInvoiceTraderArea(String invoiceTraderArea) {
        this.invoiceTraderArea = invoiceTraderArea == null ? null : invoiceTraderArea.trim();
    }

    public String getInvoiceTraderAddress() {
        return invoiceTraderAddress;
    }

    public void setInvoiceTraderAddress(String invoiceTraderAddress) {
        this.invoiceTraderAddress = invoiceTraderAddress == null ? null : invoiceTraderAddress.trim();
    }

    public String getInvoiceComments() {
        return invoiceComments;
    }

    public void setInvoiceComments(String invoiceComments) {
        this.invoiceComments = invoiceComments == null ? null : invoiceComments.trim();
    }

    public Integer getOldTakeTraderContactId() {
        return oldTakeTraderContactId;
    }

    public void setOldTakeTraderContactId(Integer oldTakeTraderContactId) {
        this.oldTakeTraderContactId = oldTakeTraderContactId;
    }

    public String getOldTakeTraderContactName() {
        return oldTakeTraderContactName;
    }

    public void setOldTakeTraderContactName(String oldTakeTraderContactName) {
        this.oldTakeTraderContactName = oldTakeTraderContactName == null ? null : oldTakeTraderContactName.trim();
    }

    public String getOldTakeTraderContactMobile() {
        return oldTakeTraderContactMobile;
    }

    public void setOldTakeTraderContactMobile(String oldTakeTraderContactMobile) {
        this.oldTakeTraderContactMobile = oldTakeTraderContactMobile == null ? null : oldTakeTraderContactMobile.trim();
    }

    public String getOldTakeTraderContactTelephone() {
        return oldTakeTraderContactTelephone;
    }

    public void setOldTakeTraderContactTelephone(String oldTakeTraderContactTelephone) {
        this.oldTakeTraderContactTelephone = oldTakeTraderContactTelephone == null ? null : oldTakeTraderContactTelephone.trim();
    }

    public Integer getOldTakeTraderAddressId() {
        return oldTakeTraderAddressId;
    }

    public void setOldTakeTraderAddressId(Integer oldTakeTraderAddressId) {
        this.oldTakeTraderAddressId = oldTakeTraderAddressId;
    }

    public String getOldTakeTraderArea() {
        return oldTakeTraderArea;
    }

    public void setOldTakeTraderArea(String oldTakeTraderArea) {
        this.oldTakeTraderArea = oldTakeTraderArea == null ? null : oldTakeTraderArea.trim();
    }

    public String getOldTakeTraderAddress() {
        return oldTakeTraderAddress;
    }

    public void setOldTakeTraderAddress(String oldTakeTraderAddress) {
        this.oldTakeTraderAddress = oldTakeTraderAddress == null ? null : oldTakeTraderAddress.trim();
    }

    public String getOldLogisticsComments() {
        return oldLogisticsComments;
    }

    public void setOldLogisticsComments(String oldLogisticsComments) {
        this.oldLogisticsComments = oldLogisticsComments == null ? null : oldLogisticsComments.trim();
    }

    public Integer getOldInvoiceTraderContactId() {
        return oldInvoiceTraderContactId;
    }

    public void setOldInvoiceTraderContactId(Integer oldInvoiceTraderContactId) {
        this.oldInvoiceTraderContactId = oldInvoiceTraderContactId;
    }

    public String getOldInvoiceTraderContactName() {
        return oldInvoiceTraderContactName;
    }

    public void setOldInvoiceTraderContactName(String oldInvoiceTraderContactName) {
        this.oldInvoiceTraderContactName = oldInvoiceTraderContactName == null ? null : oldInvoiceTraderContactName.trim();
    }

    public String getOldInvoiceTraderContactMobile() {
        return oldInvoiceTraderContactMobile;
    }

    public void setOldInvoiceTraderContactMobile(String oldInvoiceTraderContactMobile) {
        this.oldInvoiceTraderContactMobile = oldInvoiceTraderContactMobile == null ? null : oldInvoiceTraderContactMobile.trim();
    }

    public String getOldInvoiceTraderContactTelephone() {
        return oldInvoiceTraderContactTelephone;
    }

    public void setOldInvoiceTraderContactTelephone(String oldInvoiceTraderContactTelephone) {
        this.oldInvoiceTraderContactTelephone = oldInvoiceTraderContactTelephone == null ? null : oldInvoiceTraderContactTelephone.trim();
    }

    public Integer getOldInvoiceTraderAddressId() {
        return oldInvoiceTraderAddressId;
    }

    public void setOldInvoiceTraderAddressId(Integer oldInvoiceTraderAddressId) {
        this.oldInvoiceTraderAddressId = oldInvoiceTraderAddressId;
    }

    public String getOldInvoiceTraderArea() {
        return oldInvoiceTraderArea;
    }

    public void setOldInvoiceTraderArea(String oldInvoiceTraderArea) {
        this.oldInvoiceTraderArea = oldInvoiceTraderArea == null ? null : oldInvoiceTraderArea.trim();
    }

    public String getOldInvoiceTraderAddress() {
        return oldInvoiceTraderAddress;
    }

    public void setOldInvoiceTraderAddress(String oldInvoiceTraderAddress) {
        this.oldInvoiceTraderAddress = oldInvoiceTraderAddress == null ? null : oldInvoiceTraderAddress.trim();
    }

    public String getOldInvoiceComments() {
        return oldInvoiceComments;
    }

    public void setOldInvoiceComments(String oldInvoiceComments) {
        this.oldInvoiceComments = oldInvoiceComments == null ? null : oldInvoiceComments.trim();
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
    
    public List<SaleorderModifyApplyGoods> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<SaleorderModifyApplyGoods> goodsList) {
		this.goodsList = goodsList;
	}

	public Integer getVerifiesType() {
		return verifiesType;
	}

	public void setVerifiesType(Integer verifiesType) {
		this.verifiesType = verifiesType;
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

	public Integer getIsChangeDeliveryDirect() {
	    return isChangeDeliveryDirect;
	}

	public void setIsChangeDeliveryDirect(Integer isChangeDeliveryDirect) {
	    this.isChangeDeliveryDirect = isChangeDeliveryDirect;
	}

	public Integer getIsChangeTakeTraderAddressId() {
	    return isChangeTakeTraderAddressId;
	}

	public void setIsChangeTakeTraderAddressId(Integer isChangeTakeTraderAddressId) {
	    this.isChangeTakeTraderAddressId = isChangeTakeTraderAddressId;
	}

	public List<Integer> getBuyorderUsers() {
	    return buyorderUsers;
	}

	public void setBuyorderUsers(List<Integer> buyorderUsers) {
	    this.buyorderUsers = buyorderUsers;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public Integer getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(Integer invoiceType) {
		this.invoiceType = invoiceType;
	}

	public Integer getIsSendInvoice() {
		return isSendInvoice;
	}

	public void setIsSendInvoice(Integer isSendInvoice) {
		this.isSendInvoice = isSendInvoice;
	}

	public Integer getOldInvoiceType() {
		return oldInvoiceType;
	}

	public void setOldInvoiceType(Integer oldInvoiceType) {
		this.oldInvoiceType = oldInvoiceType;
	}

	public Integer getOldIsSendInvoice() {
		return oldIsSendInvoice;
	}

	public void setOldIsSendInvoice(Integer oldIsSendInvoice) {
		this.oldIsSendInvoice = oldIsSendInvoice;
	}

	public Integer getIsDelayInvoice() {
		return isDelayInvoice;
	}

	public void setIsDelayInvoice(Integer isDelayInvoice) {
		this.isDelayInvoice = isDelayInvoice;
	}

	public Integer getOldIsDelayInvoice() {
		return oldIsDelayInvoice;
	}

	public void setOldIsDelayInvoice(Integer oldIsDelayInvoice) {
		this.oldIsDelayInvoice = oldIsDelayInvoice;
	}

	public Integer getInvoiceMethod() {
		return invoiceMethod;
	}

	public void setInvoiceMethod(Integer invoiceMethod) {
		this.invoiceMethod = invoiceMethod;
	}

	public Integer getOldInvoiceMethod() {
		return oldInvoiceMethod;
	}

	public void setOldInvoiceMethod(Integer oldInvoiceMethod) {
		this.oldInvoiceMethod = oldInvoiceMethod;
	}

	public Integer getIsChangeLogisticsComments() {
	    return isChangeLogisticsComments;
	}

	public void setIsChangeLogisticsComments(Integer isChangeLogisticsComments) {
	    this.isChangeLogisticsComments = isChangeLogisticsComments;
	}

    public Integer getIsPrintout() {
        return isPrintout;
    }

    public void setIsPrintout(Integer isPrintout) {
        this.isPrintout = isPrintout;
    }
}