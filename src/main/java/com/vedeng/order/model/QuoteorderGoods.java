package com.vedeng.order.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.vedeng.goods.model.Goods;


public class QuoteorderGoods implements Serializable{
    private Integer quoteorderGoodsId;

    private Integer quoteorderId;

    private Integer isTemp;

    private Integer goodsId;

    private String sku;

    private String goodsName;

    private String brandName;

    private String model;

    private String unitName;

    private BigDecimal price;

    private Integer currencyUnitId;

    private Integer num;

    private String deliveryCycle;

    private Integer deliveryDirect;

    private String deliveryDirectComments;

    private String registrationNumber;

    private String supplierName;

    private BigDecimal referenceCostPrice;

    private BigDecimal referencePrice;

    private String referenceDeliveryCycle;

    private Integer reportStatus;

    private String reportComments;

    private Integer haveInstallation;

    private String goodsComments;

    private String insideComments;

    private Integer isDelete;

    private Long addTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;
    
    private String uri;//产品图片
    
    private String domain;//域名
    
    //-----------------------------------------------------------------------------------------
    
    private Goods goods;//产品信息
    
    private String materialCode;//物料编码
    
    private BigDecimal goodsTotalAmount;//产品总额
    
    private Integer manageCategory;//管理类别
    
    private String manageCategoryName;//管理类别名称
    
    private String goodsLevelName;//产品级别名称

    private Integer lastReferenceUser;
    
    private String goodsUserNm,goodsUserIdStr;//产品负责人

    private BigDecimal LastOrderPrice;//上次销售订单价格
    
    private String technicalParameter;//产品技术参数
    
    private Integer categoryId;//分类ID
    
    private Integer areaControl = 0;//是否区域控制0：否；；1：是
    
    private BigDecimal channelPrice;//产品核价价格
    
    private BigDecimal settlePrice;//产品结算价格
    
    private Integer terminalTraderId,terminalTraderType;//终端客户ID和类型
    
    private String terminalTraderName;
    
    private Integer salesAreaId;//报价区域Id
    
    private String salesArea;//报价区域
    
    private Integer customerNature;//报价客户性质
    
    private BigDecimal costPrice;//产品核算成本价
    
    private String channelDeliveryCycle;//期货交货期
    
    private String delivery;//现货交货期
    
    private BigDecimal avgPrice;//近12个月平均报价
    

    
	public String getDelivery() {
		return delivery;
	}

	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}

	public Integer getTerminalTraderId() {
		return terminalTraderId;
	}

	public void setTerminalTraderId(Integer terminalTraderId) {
		this.terminalTraderId = terminalTraderId;
	}

	public Integer getTerminalTraderType() {
		return terminalTraderType;
	}

	public void setTerminalTraderType(Integer terminalTraderType) {
		this.terminalTraderType = terminalTraderType;
	}

	public BigDecimal getAvgPrice() {
		return avgPrice;
	}

	public void setAvgPrice(BigDecimal avgPrice) {
		this.avgPrice = avgPrice;
	}

	public String getTerminalTraderName() {
		return terminalTraderName;
	}

	public void setTerminalTraderName(String terminalTraderName) {
		this.terminalTraderName = terminalTraderName == null ? null : terminalTraderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public String getSalesArea() {
		return salesArea;
	}

	public void setSalesArea(String salesArea) {
		this.salesArea = salesArea;
	}

	public Integer getSalesAreaId() {
		return salesAreaId;
	}

	public void setSalesAreaId(Integer salesAreaId) {
		this.salesAreaId = salesAreaId;
	}

	public Integer getCustomerNature() {
		return customerNature;
	}

	public void setCustomerNature(Integer customerNature) {
		this.customerNature = customerNature;
	}

	public BigDecimal getSettlePrice() {
		return settlePrice;
	}

	public void setSettlePrice(BigDecimal settlePrice) {
		this.settlePrice = settlePrice;
	}

	public Integer getAreaControl() {
		return areaControl;
	}

	public void setAreaControl(Integer areaControl) {
		this.areaControl = areaControl;
	}

	public BigDecimal getChannelPrice() {
		return channelPrice;
	}

	public void setChannelPrice(BigDecimal channelPrice) {
		this.channelPrice = channelPrice;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getTechnicalParameter() {
   		return technicalParameter;
   	}

   	public void setTechnicalParameter(String technicalParameter) {
   		this.technicalParameter = technicalParameter;
   	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public BigDecimal getGoodsTotalAmount() {
		return goodsTotalAmount;
	}

	public void setGoodsTotalAmount(BigDecimal goodsTotalAmount) {
		this.goodsTotalAmount = goodsTotalAmount;
	}

	public Integer getManageCategory() {
		return manageCategory;
	}

	public void setManageCategory(Integer manageCategory) {
		this.manageCategory = manageCategory;
	}

	public BigDecimal getLastOrderPrice() {
		return LastOrderPrice;
	}

	public void setLastOrderPrice(BigDecimal lastOrderPrice) {
		LastOrderPrice = lastOrderPrice;
	}

	public String getGoodsUserIdStr() {
		return goodsUserIdStr;
	}

	public void setGoodsUserIdStr(String goodsUserIdStr) {
		this.goodsUserIdStr = goodsUserIdStr;
	}

	public String getGoodsUserNm() {
		return goodsUserNm;
	}

	public void setGoodsUserNm(String goodsUserNm) {
		this.goodsUserNm = goodsUserNm;
	}

	public Integer getLastReferenceUser() {
		return lastReferenceUser;
	}

	public void setLastReferenceUser(Integer lastReferenceUser) {
		this.lastReferenceUser = lastReferenceUser;
	}

	public String getManageCategoryName() {
		return manageCategoryName;
	}

	public void setManageCategoryName(String manageCategoryName) {
		this.manageCategoryName = manageCategoryName;
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public String getGoodsLevelName() {
		return goodsLevelName;
	}

	public void setGoodsLevelName(String goodsLevelName) {
		this.goodsLevelName = goodsLevelName;
	}

	public Integer getQuoteorderGoodsId() {
        return quoteorderGoodsId;
    }

    public void setQuoteorderGoodsId(Integer quoteorderGoodsId) {
        this.quoteorderGoodsId = quoteorderGoodsId;
    }

    public Integer getQuoteorderId() {
        return quoteorderId;
    }

    public void setQuoteorderId(Integer quoteorderId) {
        this.quoteorderId = quoteorderId;
    }

    public Integer getIsTemp() {
        return isTemp;
    }

    public void setIsTemp(Integer isTemp) {
        this.isTemp = isTemp;
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

    public String getDeliveryCycle() {
        return deliveryCycle;
    }

    public void setDeliveryCycle(String deliveryCycle) {
        this.deliveryCycle = deliveryCycle == null ? null : deliveryCycle.trim();
    }

    public Integer getDeliveryDirect() {
        return deliveryDirect;
    }

    public void setDeliveryDirect(Integer deliveryDirect) {
        this.deliveryDirect = deliveryDirect;
    }

    public String getDeliveryDirectComments() {
        return deliveryDirectComments;
    }

    public void setDeliveryDirectComments(String deliveryDirectComments) {
        this.deliveryDirectComments = deliveryDirectComments == null ? null : deliveryDirectComments.trim();
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber == null ? null : registrationNumber.trim();
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName == null ? null : supplierName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
    }

    public BigDecimal getReferenceCostPrice() {
        return referenceCostPrice;
    }

    public void setReferenceCostPrice(BigDecimal referenceCostPrice) {
        this.referenceCostPrice = referenceCostPrice;
    }

    public BigDecimal getReferencePrice() {
        return referencePrice;
    }

    public void setReferencePrice(BigDecimal referencePrice) {
        this.referencePrice = referencePrice;
    }

    public String getReferenceDeliveryCycle() {
        return referenceDeliveryCycle;
    }

    public void setReferenceDeliveryCycle(String referenceDeliveryCycle) {
        this.referenceDeliveryCycle = referenceDeliveryCycle == null ? null : referenceDeliveryCycle.trim();
    }

    public Integer getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(Integer reportStatus) {
        this.reportStatus = reportStatus;
    }

    public String getReportComments() {
        return reportComments;
    }

    public void setReportComments(String reportComments) {
        this.reportComments = reportComments == null ? null : reportComments.trim();
    }

    public Integer getHaveInstallation() {
        return haveInstallation;
    }

    public void setHaveInstallation(Integer haveInstallation) {
        this.haveInstallation = haveInstallation;
    }

    public String getGoodsComments() {
        return goodsComments;
    }

    public void setGoodsComments(String goodsComments) {
        this.goodsComments = goodsComments == null ? null : goodsComments.trim();
    }

    public String getInsideComments() {
        return insideComments;
    }

    public void setInsideComments(String insideComments) {
        this.insideComments = insideComments == null ? null : insideComments.trim();
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
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

    public Integer getCategoryId() {
	return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
	this.categoryId = categoryId;
    }

    public BigDecimal getCostPrice() {
	return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
	this.costPrice = costPrice;
    }

    public String getChannelDeliveryCycle() {
	return channelDeliveryCycle;
    }

    public void setChannelDeliveryCycle(String channelDeliveryCycle) {
	this.channelDeliveryCycle = channelDeliveryCycle;
    }
}