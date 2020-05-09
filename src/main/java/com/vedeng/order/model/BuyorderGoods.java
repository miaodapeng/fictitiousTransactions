package com.vedeng.order.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <b>Description:</b><br> 采购商品
 * @author east
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.order.model
 * <br><b>ClassName:</b> BuyorderGoods
 * <br><b>Date:</b> 2017年7月11日 上午9:05:48
 */
public class BuyorderGoods implements Serializable{
    /**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private Integer buyorderGoodsId;

    private Integer buyorderId;

    private Integer goodsId;

    private String sku;

    private String goodsName;

    private String brandName;

    private String model;

    private String unitName;

    private BigDecimal price;

    private Integer currencyUnitId;

    private Integer num;

    private Integer arrivalNum;

    private Long estimateDeliveryTime;

    private Long estimateArrivalTime;

    private Integer arrivalUserId;

    private Integer arrivalStatus;

    private Long arrivalTime;

    private Integer isDelete;

    private String insideComments;

    private Long addTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;
    
    private BigDecimal totalAmount;//订单总额
    
    private String manufacturer;//生产厂家
	
    private String productionLicense;//生产许可证号

    private String deliveryCycle;//DELIVERY_CYCLE 货期
    
    private String installation;//INSTALLATION 安调信息
    
    private String goodsComments;//COMMENTS 内部备注
    
    private Long searchBegintime;//搜索开始时间
    
    private Long searchEndtime;//搜索结束时间
    
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

    public String getDeliveryCycle() {
		return deliveryCycle;
	}

	public void setDeliveryCycle(String deliveryCycle) {
		this.deliveryCycle = deliveryCycle;
	}

	public String getInstallation() {
		return installation;
	}

	public void setInstallation(String installation) {
		this.installation = installation;
	}

	public String getGoodsComments() {
		return goodsComments;
	}

	public void setGoodsComments(String goodsComments) {
		this.goodsComments = goodsComments;
	}
    
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Integer getBuyorderGoodsId() {
        return buyorderGoodsId;
    }

    public void setBuyorderGoodsId(Integer buyorderGoodsId) {
        this.buyorderGoodsId = buyorderGoodsId;
    }

    public Integer getBuyorderId() {
        return buyorderId;
    }

    public void setBuyorderId(Integer buyorderId) {
        this.buyorderId = buyorderId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku == null ? null : sku.trim();
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName == null ? null : brandName.trim();
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName == null ? null : unitName.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCurrencyUnitId() {
        return currencyUnitId;
    }

    public void setCurrencyUnitId(Integer currencyUnitId) {
        this.currencyUnitId = currencyUnitId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getArrivalNum() {
        return arrivalNum;
    }

    public void setArrivalNum(Integer arrivalNum) {
        this.arrivalNum = arrivalNum;
    }

    public Long getEstimateDeliveryTime() {
        return estimateDeliveryTime;
    }

    public void setEstimateDeliveryTime(Long estimateDeliveryTime) {
        this.estimateDeliveryTime = estimateDeliveryTime;
    }

    public Long getEstimateArrivalTime() {
        return estimateArrivalTime;
    }

    public void setEstimateArrivalTime(Long estimateArrivalTime) {
        this.estimateArrivalTime = estimateArrivalTime;
    }

    public Integer getArrivalUserId() {
        return arrivalUserId;
    }

    public void setArrivalUserId(Integer arrivalUserId) {
        this.arrivalUserId = arrivalUserId;
    }

    public Integer getArrivalStatus() {
        return arrivalStatus;
    }

    public void setArrivalStatus(Integer arrivalStatus) {
        this.arrivalStatus = arrivalStatus;
    }

    public Long getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Long arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getInsideComments() {
        return insideComments;
    }

    public void setInsideComments(String insideComments) {
        this.insideComments = insideComments == null ? null : insideComments.trim();
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

    public String getManufacturer() {
	return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
	this.manufacturer = manufacturer == null ? null : manufacturer.replaceAll("\\(","（").replaceAll("\\)","）").trim();
    }

    public String getProductionLicense() {
	return productionLicense;
    }

    public void setProductionLicense(String productionLicense) {
	this.productionLicense = productionLicense;
    }
}