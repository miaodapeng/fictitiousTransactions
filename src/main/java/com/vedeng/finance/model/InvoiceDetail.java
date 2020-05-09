package com.vedeng.finance.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class InvoiceDetail implements Serializable{
	
    /**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private Integer invoiceDetailId;
    
    private Integer relatedId;//关联表ID

    private Integer invoiceId;//发票ID

    private Integer detailgoodsId;//订单产品表Id

    private BigDecimal price;//单价

    private BigDecimal num;//数量

    private BigDecimal totalAmount;//总额
    
    private Integer goodsId;//产品名称

    private String goodsName;//产品名称
    
    private String sku;//产品订货号
    
    private String brandName;//品牌名称
    
    private String model,specParameter;//型号
    
    private String unitName;//单位
    
    private List<orderInfo> orderList;//订单详情
    
    private BigDecimal enterNum;//已录入数量
    
    private BigDecimal enterAmount;//已录入金额
    
    private BigDecimal arrivalNum;//已到货数量
    
    private Integer invoiceType;
    
    private String orderNo;
    
    private String orderId;

	private Integer isActionGoods;//是否为活动商品   0否  1是
	// add by Tomcat.Hui 2019/11/21 13:46 .Desc: VDERP-1325 分批开票 变更后的商品名称. start

	private Integer applyNum;

	public Integer getApplyNum() {
		return applyNum;
	}

	public void setApplyNum(Integer applyNum) {
		this.applyNum = applyNum;
	}

	private String changedGoodsName;

	public String getChangedGoodsName() {
		return changedGoodsName;
	}

	public void setChangedGoodsName(String changedGoodsName) {
		this.changedGoodsName = changedGoodsName;
	}

	// add by Tomcat.Hui 2019/11/21 13:46 .Desc: VDERP-1325 分批开票 变更后的商品名称. end

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public String getSpecParameter() {
		return specParameter;
	}

	public void setSpecParameter(String specParameter) {
		this.specParameter = specParameter;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public BigDecimal getArrivalNum() {
		return arrivalNum;
	}

	public void setArrivalNum(BigDecimal arrivalNum) {
		this.arrivalNum = arrivalNum;
	}

	public Integer getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(Integer invoiceType) {
		this.invoiceType = invoiceType;
	}

	public BigDecimal getEnterNum() {
		return enterNum;
	}

	public void setEnterNum(BigDecimal enterNum) {
		this.enterNum = enterNum;
	}

	public BigDecimal getEnterAmount() {
		return enterAmount;
	}

	public void setEnterAmount(BigDecimal enterAmount) {
		this.enterAmount = enterAmount;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public List<orderInfo> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<orderInfo> orderList) {
		this.orderList = orderList;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Integer getRelatedId() {
		return relatedId;
	}

	public void setRelatedId(Integer relatedId) {
		this.relatedId = relatedId;
	}

	public Integer getInvoiceDetailId() {
        return invoiceDetailId;
    }

    public void setInvoiceDetailId(Integer invoiceDetailId) {
        this.invoiceDetailId = invoiceDetailId;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Integer getDetailgoodsId() {
        return detailgoodsId;
    }

    public void setDetailgoodsId(Integer detailgoodsId) {
        this.detailgoodsId = detailgoodsId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getNum() {
        return num;
    }

    public void setNum(BigDecimal num) {
        this.num = num;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

	public Integer getIsActionGoods() {
		return isActionGoods;
	}

	public void setIsActionGoods(Integer isActionGoods) {
		this.isActionGoods = isActionGoods;
	}
}