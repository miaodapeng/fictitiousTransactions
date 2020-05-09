package com.vedeng.finance.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.vedeng.aftersales.model.AfterSales;
import com.vedeng.order.model.Buyorder;
import com.vedeng.order.model.Saleorder;


public class CapitalBillDetail implements Serializable{
    private Integer capitalBillDetailId;

    private Integer capitalBillId;

    private Integer bussinessType;

    private Integer orderType;
    
    private String orderNo;

    private Integer relatedId;

    private BigDecimal amount;
    
    private Integer userId;//所属销售
    
    //销售订单
    private Saleorder saleorder;
    //付款方
    private String payer;
    //收款方
    private String payee;
    //备注
    private String comments;
    
    private Integer traderId;//交易者的id
    
    private Integer traderType;//交易者的类型
    
    private List<CapitalBillDetailGoods> capitalBillDetailGoodss;

    private String bussinessTypeStr;//业务类型
    
    private String orgName;//当前资金流水关联用户部门
    
    private Integer orgId;//当前资金流水关联用户部门ID
    
    private Integer afterSalesInstallstionId;//安调维修工程师主键
    
    //采购订单
    private Buyorder buyorder;  
    //售后订单
    private AfterSales afterSales;
    
    private PayApply payApply;//付款申请
    
    public String getBussinessTypeStr() {
		return bussinessTypeStr;
	}

	public void setBussinessTypeStr(String bussinessTypeStr) {
		this.bussinessTypeStr = bussinessTypeStr;
	}
    
    public String getPayer() {
        return payer;
    }

    public Integer getTraderId() {
		return traderId;
	}

	public void setTraderId(Integer traderId) {
		this.traderId = traderId;
	}

	public Integer getTraderType() {
		return traderType;
	}

	public void setTraderType(Integer traderType) {
		this.traderType = traderType;
	}

	public void setPayer(String payer) {
        this.payer = payer == null ? null : payer.replaceAll("\\(","（").replaceAll("\\)","）").trim();
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee == null ? null : payee.replaceAll("\\(","（").replaceAll("\\)","）").trim();
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getCapitalBillDetailId() {
        return capitalBillDetailId;
    }

    public void setCapitalBillDetailId(Integer capitalBillDetailId) {
        this.capitalBillDetailId = capitalBillDetailId;
    }

    public Integer getCapitalBillId() {
        return capitalBillId;
    }

    public void setCapitalBillId(Integer capitalBillId) {
        this.capitalBillId = capitalBillId;
    }

    public Integer getBussinessType() {
        return bussinessType;
    }

    public void setBussinessType(Integer bussinessType) {
        this.bussinessType = bussinessType;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(Integer relatedId) {
        this.relatedId = relatedId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Saleorder getSaleorder() {
	return saleorder;
    }

    public void setSaleorder(Saleorder saleorder) {
	this.saleorder = saleorder;
    }

	public List<CapitalBillDetailGoods> getCapitalBillDetailGoodss() {
		return capitalBillDetailGoodss;
	}

	public void setCapitalBillDetailGoodss(List<CapitalBillDetailGoods> capitalBillDetailGoodss) {
		this.capitalBillDetailGoodss = capitalBillDetailGoodss;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getUserId() {
	    return userId;
	}

	public void setUserId(Integer userId) {
	    this.userId = userId;
	}

	public String getOrgName() {
	    return orgName;
	}

	public void setOrgName(String orgName) {
	    this.orgName = orgName;
	}

	public Integer getOrgId() {
	    return orgId;
	}

	public void setOrgId(Integer orgId) {
	    this.orgId = orgId;
	}

	public Integer getAfterSalesInstallstionId() {
	    return afterSalesInstallstionId;
	}

	public void setAfterSalesInstallstionId(Integer afterSalesInstallstionId) {
	    this.afterSalesInstallstionId = afterSalesInstallstionId;
	}

	public Buyorder getBuyorder() {
	    return buyorder;
	}

	public void setBuyorder(Buyorder buyorder) {
	    this.buyorder = buyorder;
	}

	public AfterSales getAfterSales() {
	    return afterSales;
	}

	public void setAfterSales(AfterSales afterSales) {
	    this.afterSales = afterSales;
	}

	public PayApply getPayApply() {
	    return payApply;
	}

	public void setPayApply(PayApply payApply) {
	    this.payApply = payApply;
	}
}