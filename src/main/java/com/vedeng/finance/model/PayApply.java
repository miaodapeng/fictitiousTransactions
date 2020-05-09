package com.vedeng.finance.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.Task;

public class PayApply implements Serializable{
    /**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private Integer payApplyId;

    private Integer companyId;

    private Integer payType;

    private Integer relatedId;

    private Integer traderSubject;

    private String traderName;

    private BigDecimal amount;

    private Integer currencyUnitId;

    private String bank;

    private String bankAccount;
    
    private String bankCode;

    private String comments;

    private Integer validStatus;

    private Long validTime;

    private String validComments;
    
    private Integer validUserId;

    private Long addTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;
    
    private String creatorName,validUserName;//创建者人员--最后一次审核人员
    
    private String addTimeStr,validTimeStr;//申请时间--审核时间
    
    private String updaterName;//更新人员
    
    private Long searchBegintime;//搜索开始时间
    
    private Long searchEndtime;//搜索结束时间
    
    private Long searchPayBegintime;//搜索开始时间
    
    private Long searchPayEndtime;//搜索结束时间
    
    private BigDecimal searchBeginAmount;//开始金额
    
    private BigDecimal searchEndAmount;//结束金额
    
    private Integer traderMode;//交易方式（付款）
    
    // 是否余额支付
    private Integer is_528;
    
    private String buyorderNo;//采购订单
    
    private String orderNo;//关联单号
    
    private String buyorderTraderName;//供应商名称
    
    private Integer traderId;//交易人ID
    
    private Map<String , Map<String,Object>> detailMap;
    
    private List<PayApplyDetail> detailList = new ArrayList<>();
    
    private String afterSalesNo;//售后订单

    private Integer verifiesType;//审核类型
    
    private String verifyUsername;//当前审核人
    
    private Integer verifyStatus;//审核状态
    
    private List<String> verifyUsernameList;//当前审核人
    
    private BigDecimal payApplyTotalAmount;//付款申请总额
    
    private BigDecimal payApplyPayTotalAmount;//付款申请付款总额
    
    private Integer isBill;//是否制单 0否1是
    
    private List<BankBill> bankBillList;//对应流水
    
    private String taskInfoPayId;//付款申请审核对象
    
    private Integer traderSupplierId;//供应商主键
    
    private Integer isUseBalance;//使用账户余额 --1:使用；2：不适用
    
    private String search;// 单个搜索框搜索关键词

	private Integer payStatus;//申请付款状态
    public Integer getIs_528() {
		return is_528;
	}

	public void setIs_528(Integer is_528) {
		this.is_528 = is_528;
	}

	public Integer getIsUseBalance() {
		return isUseBalance;
	}

	public void setIsUseBalance(Integer isUseBalance) {
		this.isUseBalance = isUseBalance;
	}

	public Integer getTraderSupplierId() {
		return traderSupplierId;
	}

	public void setTraderSupplierId(Integer traderSupplierId) {
		this.traderSupplierId = traderSupplierId;
	}

	public String getValidTimeStr() {
		return validTimeStr;
	}

	public void setValidTimeStr(String validTimeStr) {
		this.validTimeStr = validTimeStr;
	}

	public String getValidUserName() {
		return validUserName;
	}

	public void setValidUserName(String validUserName) {
		this.validUserName = validUserName;
	}

	public Integer getValidUserId() {
		return validUserId;
	}

	public void setValidUserId(Integer validUserId) {
		this.validUserId = validUserId;
	}

	public String getAddTimeStr() {
		return addTimeStr;
	}

	public void setAddTimeStr(String addTimeStr) {
		this.addTimeStr = addTimeStr;
	}

	public Integer getPayApplyId() {
        return payApplyId;
    }

    public void setPayApplyId(Integer payApplyId) {
        this.payApplyId = payApplyId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(Integer relatedId) {
        this.relatedId = relatedId;
    }

    public Integer getTraderSubject() {
        return traderSubject;
    }

    public void setTraderSubject(Integer traderSubject) {
        this.traderSubject = traderSubject;
    }

    public String getTraderName() {
        return traderName;
    }

    public void setTraderName(String traderName) {
        this.traderName = traderName==null ? null :traderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getCurrencyUnitId() {
        return currencyUnitId;
    }

    public void setCurrencyUnitId(Integer currencyUnitId) {
        this.currencyUnitId = currencyUnitId;
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
        this.bankAccount = bankAccount == null ? null : bankAccount.trim();
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
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

    public String getValidComments() {
        return validComments;
    }

    public void setValidComments(String validComments) {
        this.validComments = validComments == null ? null : validComments.trim();
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

	public Map<String , Map<String,Object>> getDetailMap() {
		return detailMap;
	}

	public void setDetailMap(Map<String , Map<String,Object>> detailMap) {
		this.detailMap = detailMap;
	}

	public List<PayApplyDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<PayApplyDetail> detailList) {
		this.detailList = detailList;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getUpdaterName() {
		return updaterName;
	}

	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}

	public Long getSearchBegintime() {
		return searchBegintime;
	}

	public void setSearchBegintime(Long searchBegintime) {
		this.searchBegintime = searchBegintime;
	}

	public Long getSearchEndtime() {
		return searchEndtime;
	}

	public void setSearchEndtime(Long searchEndtime) {
		this.searchEndtime = searchEndtime;
	}

	public BigDecimal getSearchBeginAmount() {
		return searchBeginAmount;
	}

	public void setSearchBeginAmount(BigDecimal searchBeginAmount) {
		this.searchBeginAmount = searchBeginAmount;
	}

	public BigDecimal getSearchEndAmount() {
		return searchEndAmount;
	}

	public void setSearchEndAmount(BigDecimal searchEndAmount) {
		this.searchEndAmount = searchEndAmount;
	}

	public Integer getTraderMode() {
		return traderMode;
	}

	public void setTraderMode(Integer traderMode) {
		this.traderMode = traderMode;
	}

	public String getBuyorderNo() {
		return buyorderNo;
	}

	public void setBuyorderNo(String buyorderNo) {
		this.buyorderNo = buyorderNo;
	}

	public String getBuyorderTraderName() {
		return buyorderTraderName;
	}

	public void setBuyorderTraderName(String buyorderTraderName) {
		this.buyorderTraderName = buyorderTraderName == null ? null : buyorderTraderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getAfterSalesNo() {
		return afterSalesNo;
	}

	public void setAfterSalesNo(String afterSalesNo) {
		this.afterSalesNo = afterSalesNo;
	}

	public String getOrderNo() {
	    return orderNo;
	}

	public void setOrderNo(String orderNo) {
	    this.orderNo = orderNo;
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

	public List<String> getVerifyUsernameList() {
		return verifyUsernameList;
	}

	public void setVerifyUsernameList(List<String> verifyUsernameList) {
		this.verifyUsernameList = verifyUsernameList;
	}

	public BigDecimal getPayApplyTotalAmount() {
		return payApplyTotalAmount;
	}

	public void setPayApplyTotalAmount(BigDecimal payApplyTotalAmount) {
		this.payApplyTotalAmount = payApplyTotalAmount;
	}

	public BigDecimal getPayApplyPayTotalAmount() {
		return payApplyPayTotalAmount;
	}

	public void setPayApplyPayTotalAmount(BigDecimal payApplyPayTotalAmount) {
		this.payApplyPayTotalAmount = payApplyPayTotalAmount;
	}

	public Integer getIsBill() {
	    return isBill;
	}

	public void setIsBill(Integer isBill) {
	    this.isBill = isBill;
	}

	public Long getSearchPayBegintime() {
	    return searchPayBegintime;
	}

	public void setSearchPayBegintime(Long searchPayBegintime) {
	    this.searchPayBegintime = searchPayBegintime;
	}

	public Long getSearchPayEndtime() {
	    return searchPayEndtime;
	}

	public void setSearchPayEndtime(Long searchPayEndtime) {
	    this.searchPayEndtime = searchPayEndtime;
	}

	public List<BankBill> getBankBillList() {
	    return bankBillList;
	}

	public void setBankBillList(List<BankBill> bankBillList) {
	    this.bankBillList = bankBillList;
	}

	public String getTaskInfoPayId() {
	    return taskInfoPayId;
	}

	public void setTaskInfoPayId(String taskInfoPayId) {
	    this.taskInfoPayId = taskInfoPayId;
	}

	public String getSearch() {
	    return search;
	}

	public void setSearch(String search) {
	    this.search = search;
	}

	public Integer getTraderId() {
	    return traderId;
	}

	public void setTraderId(Integer traderId) {
	    this.traderId = traderId;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
}