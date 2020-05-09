package com.vedeng.order.model.vo;

import java.math.BigDecimal;
import java.util.List;

import com.vedeng.authorization.model.User;
import com.vedeng.finance.model.vo.InvoiceDetailVo;
import com.vedeng.order.model.BuyorderGoods;

public class BuyorderGoodsVo extends BuyorderGoods {
	
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	
	private List<SaleorderGoodsVo> saleorderGoodsVoList ;
	
	private String goodsLevelName;//产品级别
	
	private Integer goodsStock;//库存数量
	
	private Integer canUseGoodsStock;//可用库存数量
	
	private String materialCode;//物料编码
	
	private Integer buySum;//新增采购订单默认采购数量
	
	private BigDecimal oneBuyorderGoodsAmount;//单个采购商品的总额
	
    private String registrationNumber;//注册证号
    
    private String manageCategoryName;//管理类别名称
    
    private String purchaseRemind;//采购提醒
    
    private String packingList;//包装清单
    
    private String tos;//服务条款
    
    private Integer orderOccupy;//订单占用
    
    private Integer adjustableNum;//可调剂
    
    private BigDecimal applyPaymentNum;//付款申请已申请数量
    
    private BigDecimal applyPaymentAmount;//付款申请已申请总额
    
    private Integer afterSaleUpLimitNum;//售后数量上限
    
    private Integer receiveNum;//已收货数量
	
	private Integer invoiceNum;//已开票数量
	
	private String invoiceNoStr;//发票号组合
	
	private Integer deliveryNum;//已发货数量
	
	private Integer manageCategory;
	
	private Integer companyId;
	
	private Integer categoryId;
	
	private List<User> userList;

	private Long arrivalTime; //到货时间
	
	private Long time; //时间
	
	private List<InvoiceDetailVo> invoiceList;//收票列表
	
	private BigDecimal settlementPrice;//结算价
	
	private Integer traderId;
	
	private String starttime;// 开始时间

	private String endtime;// 结束时间
	
	private Long starttimeLong;//

	private Long endtimeLong;//
	
	private String buyorderNo;
	
	private Long validTime;
	
	private Integer saleorderGoodsId;
	
	private Integer buynum;
	
	private Integer afterReturnNum;//售后退货数量
	
	private String newInsideComments;//采购修改后采购备注
	
    private String allPrice;//商品总价
    
    private String prices;//单价
    
	public String getAllPrice() {
        return allPrice;
    }

    public void setAllPrice(String allPrice) {
        this.allPrice = allPrice;
    }

    public String getPrices() {
        return prices;
    }

    public void setPrices(String prices) {
        this.prices = prices;
    }

    public String getNewInsideComments() {
		return newInsideComments;
	}

	public void setNewInsideComments(String newInsideComments) {
		this.newInsideComments = newInsideComments;
	}
	
	public String getInvoiceNoStr() {
		return invoiceNoStr;
	}

	public void setInvoiceNoStr(String invoiceNoStr) {
		this.invoiceNoStr = invoiceNoStr;
	}

	public Integer getAfterReturnNum() {
		return afterReturnNum;
	}

	public void setAfterReturnNum(Integer afterReturnNum) {
		this.afterReturnNum = afterReturnNum;
	}
	
	public List<SaleorderGoodsVo> getSaleorderGoodsVoList() {
		return saleorderGoodsVoList;
	}

	public void setSaleorderGoodsVoList(List<SaleorderGoodsVo> saleorderGoodsVoList) {
		this.saleorderGoodsVoList = saleorderGoodsVoList;
	}

	public String getGoodsLevelName() {
		return goodsLevelName;
	}

	public void setGoodsLevelName(String goodsLevelName) {
		this.goodsLevelName = goodsLevelName;
	}

	public Integer getGoodsStock() {
		return goodsStock;
	}

	public void setGoodsStock(Integer goodsStock) {
		this.goodsStock = goodsStock;
	}

	public Integer getCanUseGoodsStock() {
		return canUseGoodsStock;
	}

	public void setCanUseGoodsStock(Integer canUseGoodsStock) {
		this.canUseGoodsStock = canUseGoodsStock;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public Integer getBuySum() {
		return buySum;
	}

	public void setBuySum(Integer buySum) {
		this.buySum = buySum;
	}

	public BigDecimal getOneBuyorderGoodsAmount() {
		return oneBuyorderGoodsAmount;
	}

	public void setOneBuyorderGoodsAmount(BigDecimal oneBuyorderGoodsAmount) {
		this.oneBuyorderGoodsAmount = oneBuyorderGoodsAmount;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public String getManageCategoryName() {
		return manageCategoryName;
	}

	public void setManageCategoryName(String manageCategoryName) {
		this.manageCategoryName = manageCategoryName;
	}

	public String getPurchaseRemind() {
		return purchaseRemind;
	}

	public void setPurchaseRemind(String purchaseRemind) {
		this.purchaseRemind = purchaseRemind;
	}

	public String getPackingList() {
		return packingList;
	}

	public void setPackingList(String packingList) {
		this.packingList = packingList;
	}

	public String getTos() {
		return tos;
	}

	public void setTos(String tos) {
		this.tos = tos;
	}

	public Integer getOrderOccupy() {
		return orderOccupy;
	}

	public void setOrderOccupy(Integer orderOccupy) {
		this.orderOccupy = orderOccupy;
	}

	public Integer getAdjustableNum() {
		return adjustableNum;
	}

	public void setAdjustableNum(Integer adjustableNum) {
		this.adjustableNum = adjustableNum;
	}

	public BigDecimal getApplyPaymentNum() {
		return applyPaymentNum;
	}

	public void setApplyPaymentNum(BigDecimal applyPaymentNum) {
		this.applyPaymentNum = applyPaymentNum;
	}

	public BigDecimal getApplyPaymentAmount() {
		return applyPaymentAmount;
	}

	public void setApplyPaymentAmount(BigDecimal applyPaymentAmount) {
		this.applyPaymentAmount = applyPaymentAmount;
	}

	public Integer getAfterSaleUpLimitNum() {
		return afterSaleUpLimitNum;
	}

	public void setAfterSaleUpLimitNum(Integer afterSaleUpLimitNum) {
		this.afterSaleUpLimitNum = afterSaleUpLimitNum;
	}

	public Integer getReceiveNum() {
		return receiveNum;
	}

	public void setReceiveNum(Integer receiveNum) {
		this.receiveNum = receiveNum;
	}

	public Integer getInvoiceNum() {
		return invoiceNum;
	}

	public void setInvoiceNum(Integer invoiceNum) {
		this.invoiceNum = invoiceNum;
	}

	public Integer getDeliveryNum() {
		return deliveryNum;
	}

	public void setDeliveryNum(Integer deliveryNum) {
		this.deliveryNum = deliveryNum;
	}

	public Integer getManageCategory() {
		return manageCategory;
	}

	public void setManageCategory(Integer manageCategory) {
		this.manageCategory = manageCategory;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Long getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Long arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public List<InvoiceDetailVo> getInvoiceList() {
		return invoiceList;
	}

	public void setInvoiceList(List<InvoiceDetailVo> invoiceList) {
		this.invoiceList = invoiceList;
	}

	public BigDecimal getSettlementPrice() {
	    return settlementPrice;
	}

	public void setSettlementPrice(BigDecimal settlementPrice) {
	    this.settlementPrice = settlementPrice;
	}

	public Integer getTraderId() {
		return traderId;
	}

	public void setTraderId(Integer traderId) {
		this.traderId = traderId;
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

	public String getBuyorderNo() {
		return buyorderNo;
	}

	public void setBuyorderNo(String buyorderNo) {
		this.buyorderNo = buyorderNo;
	}

	public Long getValidTime() {
		return validTime;
	}

	public void setValidTime(Long validTime) {
		this.validTime = validTime;
	}

	public Integer getSaleorderGoodsId() {
		return saleorderGoodsId;
	}

	public void setSaleorderGoodsId(Integer saleorderGoodsId) {
		this.saleorderGoodsId = saleorderGoodsId;
	}

	public Integer getBuynum() {
		return buynum;
	}

	public void setBuynum(Integer buynum) {
		this.buynum = buynum;
	}

}
