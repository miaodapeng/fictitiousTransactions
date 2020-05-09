package com.vedeng.aftersales.model.vo;

import java.math.BigDecimal;
import java.util.List;

import com.vedeng.aftersales.model.AfterSalesDetail;
import com.vedeng.trader.model.TraderAddress;
import com.vedeng.trader.model.TraderContact;
import com.vedeng.trader.model.vo.TraderAddressVo;


public class AfterSalesDetailVo extends AfterSalesDetail {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal payAmount;//已退（付）款金额
	
	private Integer orderId;//销售订单id
	
	private String orderNo;//订单单号
	
	private Integer subjectType;//售后主体类型（字典库:535销售、536采购、537第三方）
	
	private Integer afterType;//售后类型
	
	private BigDecimal serviceAmount;//服务费
	
	private String afterSalesNo;//售后订单号
	
	private BigDecimal receivedReturnFee;//已收到手续费
	
	private Integer payApplyId;//付款申请ID
	
	private Integer companyId;
	
	private String traderName;
	
	private BigDecimal capitalTotalAmount;//售后流水：销售使用余额已经支付金额
	
	private BigDecimal orderPrepaidAmount;//订单预付金额		
	
	private BigDecimal orderTotalAmount;//订单总金额
	
	private List<TraderContact> tcList;//收票联系人列表
	
	private List<TraderAddress> taList;//收票地址列表
	
	private List<TraderAddressVo> tavList;//收票地址列表
	
	public String getTraderName() {
		return traderName;
	}

	public void setTraderName(String traderName) {
		this.traderName = traderName==null ? null :traderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public BigDecimal getServiceAmount() {
		return serviceAmount;
	}

	public void setServiceAmount(BigDecimal serviceAmount) {
		this.serviceAmount = serviceAmount;
	}

	public BigDecimal getOrderPrepaidAmount() {
		return orderPrepaidAmount;
	}

	public void setOrderPrepaidAmount(BigDecimal orderPrepaidAmount) {
		this.orderPrepaidAmount = orderPrepaidAmount;
	}

	public BigDecimal getOrderTotalAmount() {
		return orderTotalAmount;
	}

	public void setOrderTotalAmount(BigDecimal orderTotalAmount) {
		this.orderTotalAmount = orderTotalAmount;
	}

	public BigDecimal getCapitalTotalAmount() {
		return capitalTotalAmount;
	}

	public void setCapitalTotalAmount(BigDecimal capitalTotalAmount) {
		this.capitalTotalAmount = capitalTotalAmount;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getPayApplyId() {
		return payApplyId;
	}

	public void setPayApplyId(Integer payApplyId) {
		this.payApplyId = payApplyId;
	}
	
	public Integer getAfterType() {
		return afterType;
	}

	public void setAfterType(Integer afterType) {
		this.afterType = afterType;
	}

	public BigDecimal getReceivedReturnFee() {
		return receivedReturnFee;
	}

	public void setReceivedReturnFee(BigDecimal receivedReturnFee) {
		this.receivedReturnFee = receivedReturnFee;
	}

	public String getAfterSalesNo() {
		return afterSalesNo;
	}

	public void setAfterSalesNo(String afterSalesNo) {
		this.afterSalesNo = afterSalesNo;
	}

	public Integer getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(Integer subjectType) {
		this.subjectType = subjectType;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public List<TraderContact> getTcList() {
		return tcList;
	}

	public void setTcList(List<TraderContact> tcList) {
		this.tcList = tcList;
	}

	public List<TraderAddress> getTaList() {
		return taList;
	}

	public void setTaList(List<TraderAddress> taList) {
		this.taList = taList;
	}

	public List<TraderAddressVo> getTavList() {
		return tavList;
	}

	public void setTavList(List<TraderAddressVo> tavList) {
		this.tavList = tavList;
	}

}
