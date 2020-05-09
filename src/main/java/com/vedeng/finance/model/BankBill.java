package com.vedeng.finance.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class BankBill implements Serializable{
    /**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private Integer bankBillId;

    private Integer bankTag;
	
    private Integer companyId;

    private String tranFlow;

    private String trandate;

    private String trantime;

    private String realTrandate;

    private String realTrandatetime;

    private String creTyp;

    private String creNo;

    private String message;

    private BigDecimal amt;

    private String amt1;

    private Integer flag1;

    private String accno2;

    private String accName1;
    
    private String accBankNo;

    private Integer flag2;

    private String bflow;

    private String detNo;

    private String det;

    private String rltvAccno;

    private String cadbankNm;

    private Integer status;

    private BigDecimal matchedAmount;
    
    //开始时间
    private String searchBegintime;//搜索开始时间
    //结束时间
    private String searchEndtime;//搜索结束时间

    //关联的资金流水详情
    private List<CapitalBillDetail> capitalBillDetailList;
    
    private String comments;//备注
    
    private CapitalBillDetail capitalBillDetail;
    
    private Integer capitalBillId;
    
    private String saleorderNo;//订单号
    
    private String traderId;//客户id
    
    private String traderName;//客户名称
    
    private String userId;//销售id
    
    private Integer userIdNow;
    
    private String userName;//销售名称
    
    private String financeVoucherNo;//金蝶凭证号
    
    private Integer financeVoucherNoId;
    
    private Integer sendResult;//发送金蝶结果 -1 全部  1是， 2否

    private String amountStatus;//记账标识
    
    private String isSend;
    
    private String amtChinese;//中文大写金额
    
    private Integer matchedObject;//匹配项目(字典库)
    
    private String matchedObjectName;//匹配项目名称
    
    private String buyorderNo;//采购订单号
    
    public Integer getSendResult() {
		return sendResult;
	}

	public void setSendResult(Integer sendResult) {
		this.sendResult = sendResult;
	}

	public Integer getUserIdNow() {
		return userIdNow;
	}

	public void setUserIdNow(Integer userIdNow) {
		this.userIdNow = userIdNow;
	}

	public String getBuyorderNo() {
		return buyorderNo;
	}

	public void setBuyorderNo(String buyorderNo) {
		this.buyorderNo = buyorderNo;
	}

	public String getAmountStatus() {
		return amountStatus;
	}

	public void setAmountStatus(String amountStatus) {
		this.amountStatus = amountStatus;
	}

	public String getSaleorderNo() {
		return saleorderNo;
	}

	public void setSaleorderNo(String saleorderNo) {
		this.saleorderNo = saleorderNo;
	}

	public String getTraderId() {
		return traderId;
	}

	public void setTraderId(String traderId) {
		this.traderId = traderId;
	}

	public String getTraderName() {
		return traderName;
	}

	public void setTraderName(String traderName) {
		this.traderName = traderName==null ? null :traderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFinanceVoucherNo() {
		return financeVoucherNo;
	}

	public void setFinanceVoucherNo(String financeVoucherNo) {
		this.financeVoucherNo = financeVoucherNo;
	}

	public Integer getCapitalBillId() {
		return capitalBillId;
	}

	public void setCapitalBillId(Integer capitalBillId) {
		this.capitalBillId = capitalBillId;
	}
    
    public CapitalBillDetail getCapitalBillDetail() {
		return capitalBillDetail;
	}

	public void setCapitalBillDetail(CapitalBillDetail capitalBillDetail) {
		this.capitalBillDetail = capitalBillDetail;
	}
    
    public String getTrandate() {
        return trandate;
    }

    public void setTrandate(String trandate) {
        this.trandate = trandate;
    }

    public String getTrantime() {
        return trantime;
    }

    public void setTrantime(String trantime) {
        this.trantime = trantime;
    }

    public String getRealTrandate() {
        return realTrandate;
    }

    public void setRealTrandate(String realTrandate) {
        this.realTrandate = realTrandate;
    }

    public String getRealTrandatetime() {
        return realTrandatetime;
    }

    public void setRealTrandatetime(String realTrandatetime) {
        this.realTrandatetime = realTrandatetime;
    }

    public String getSearchBegintime() {
        return searchBegintime;
    }

    public void setSearchBegintime(String searchBegintime) {
        this.searchBegintime = searchBegintime;
    }

    public String getSearchEndtime() {
        return searchEndtime;
    }

    public void setSearchEndtime(String searchEndtime) {
        this.searchEndtime = searchEndtime;
    }

    public Integer getBankBillId() {
        return bankBillId;
    }

    public void setBankBillId(Integer bankBillId) {
        this.bankBillId = bankBillId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getTranFlow() {
        return tranFlow;
    }

    public void setTranFlow(String tranFlow) {
        this.tranFlow = tranFlow == null ? null : tranFlow.trim();
    }

    public String getCreTyp() {
        return creTyp;
    }

    public void setCreTyp(String creTyp) {
        this.creTyp = creTyp == null ? null : creTyp.trim();
    }

    public String getCreNo() {
        return creNo;
    }

    public void setCreNo(String creNo) {
        this.creNo = creNo == null ? null : creNo.trim();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public String getAmt1() {
        return amt1;
    }

    public void setAmt1(String amt1) {
        this.amt1 = amt1 == null ? null : amt1.trim();
    }

    public Integer getFlag1() {
        return flag1;
    }

    public void setFlag1(Integer flag1) {
        this.flag1 = flag1;
    }

    public String getAccno2() {
        return accno2;
    }

    public void setAccno2(String accno2) {
        this.accno2 = accno2 == null ? null : accno2.trim();
    }

    public String getAccName1() {
        return accName1;
    }

    public void setAccName1(String accName1) {
        this.accName1 = accName1 == null ? null : accName1.replaceAll("\\(","（").replaceAll("\\)","）").trim();
    }

    public Integer getFlag2() {
        return flag2;
    }

    public void setFlag2(Integer flag2) {
        this.flag2 = flag2;
    }

    public String getBflow() {
        return bflow;
    }

    public void setBflow(String bflow) {
        this.bflow = bflow == null ? null : bflow.trim();
    }

    public String getDetNo() {
        return detNo;
    }

    public void setDetNo(String detNo) {
        this.detNo = detNo == null ? null : detNo.trim();
    }

    public String getDet() {
        return det;
    }

    public void setDet(String det) {
        this.det = det == null ? null : det.trim();
    }

    public String getRltvAccno() {
        return rltvAccno;
    }

    public void setRltvAccno(String rltvAccno) {
        this.rltvAccno = rltvAccno == null ? null : rltvAccno.trim();
    }

    public String getCadbankNm() {
        return cadbankNm;
    }

    public void setCadbankNm(String cadbankNm) {
        this.cadbankNm = cadbankNm == null ? null : cadbankNm.replaceAll("\\(","（").replaceAll("\\)","）").trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getMatchedAmount() {
        return matchedAmount;
    }

    public void setMatchedAmount(BigDecimal matchedAmount) {
        this.matchedAmount = matchedAmount;
    }

    public List<CapitalBillDetail> getCapitalBillDetailList() {
	return capitalBillDetailList;
    }

    public void setCapitalBillDetailList(List<CapitalBillDetail> capitalBillDetailList) {
	this.capitalBillDetailList = capitalBillDetailList;
    }

    public String getComments() {
	return comments;
    }

    public void setComments(String comments) {
	this.comments = comments;
    }

    public Integer getBankTag() {
	return bankTag;
    }

    public void setBankTag(Integer bankTag) {
	this.bankTag = bankTag;
    }

	public String getIsSend() {
		return isSend;
	}

	public void setIsSend(String isSend) {
		this.isSend = isSend;
	}

	public Integer getFinanceVoucherNoId() {
		return financeVoucherNoId;
	}

	public void setFinanceVoucherNoId(Integer financeVoucherNoId) {
		this.financeVoucherNoId = financeVoucherNoId;
	}

	public String getAccBankNo() {
	    return accBankNo;
	}

	public void setAccBankNo(String accBankNo) {
	    this.accBankNo = accBankNo;
	}

	public String getAmtChinese() {
	    return amtChinese;
	}

	public void setAmtChinese(String amtChinese) {
	    this.amtChinese = amtChinese;
	}

	public Integer getMatchedObject() {
	    return matchedObject;
	}

	public void setMatchedObject(Integer matchedObject) {
	    this.matchedObject = matchedObject;
	}

	public String getMatchedObjectName() {
	    return matchedObjectName;
	}

	public void setMatchedObjectName(String matchedObjectName) {
	    this.matchedObjectName = matchedObjectName;
	}
	
}