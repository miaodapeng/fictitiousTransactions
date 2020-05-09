package com.vedeng.goods.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class GoodsChannelPrice implements Serializable{
    private Integer goodsChannelPriceId;

    private Integer goodsId;

    private Integer provinceId;

    private String provinceName;

    private Integer cityId;
    
    private Integer type=0;

    private String cityName;
    
    private BigDecimal distributionPrice;
    
    private BigDecimal distributionPriceCg;//采购经销价

    private BigDecimal publicPrice;

    private BigDecimal privatePrice;
    
    private BigDecimal privatePriceCg;//采购非公终端价
    
    private BigDecimal publicPriceCg;//采购公立终端价

    private String deliveryCycle;

    private Date periodDate;
    
    private Date periodDateCg;//采购核价有效期
    
    private Date periodDateXs;//销售核价有效期

    private Integer isReportTerminal;
    
    private Integer isReportTerminalCg;//采购终端报备

    private Integer isManufacturerAuthorization;
    
    private Integer isManufacturerAuthorizationCg;//采购厂家授权书
    
    private Long addTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;
    
    private String username;
    
    private String customerTypeComments;
    
    private Integer minNum;
    
    private Integer minNumCg;//采购最小起订量
    
    private BigDecimal minAmount;
    
    private BigDecimal minAmountCg;//采购最小起订价
    
    private String batchPolicy;
    
    private String batchPolicyCg;//采购批量政策
    
    private BigDecimal vipPrice;
    
    private BigDecimal marketPrice;
    
    
    //--------------------------------------------------------------------------------------------
    
    private String sku,goodsName,model;
    
    private Integer brandId,unitId;
    
    private Long periodTime;
    
    private BigDecimal channelPrice;//产品核价价格
    
    private List<GoodsChannelPriceExtend> goodsChannelPriceExtendList;//产品核价附属信息
    
    private List<GoodsChannelPriceExtend> goodsChannelPriceExtendListCg;//采购产品核价附属信息
    
    private BigDecimal costPrice;
    
    private Long costPriceStartTime;//成本价开始时间
    
    private Long costPriceEndTime;//成本价结束时间
    
    private BigDecimal  batchPrice;//批量价
    
    private BigDecimal  batchPriceCg;//采购批量价
    
    private Integer batchPriceMinNum;//销售批量价最小数量
    
    private Integer batchPriceMaxNum;//销售批量价最大数量
    
    private Integer batchPriceMinNumCg;//采购批量价最小数量
    
    private Integer batchPriceMaxNumCg;//采购批量价最大数量
    
    public Date getPeriodDateCg() {
		return periodDateCg;
	}

	public void setPeriodDateCg(Date periodDateCg) {
		this.periodDateCg = periodDateCg;
	}

	public Date getPeriodDateXs() {
		return periodDateXs;
	}

	public void setPeriodDateXs(Date periodDateXs) {
		this.periodDateXs = periodDateXs;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public BigDecimal getBatchPriceCg() {
		return batchPriceCg;
	}

	public void setBatchPriceCg(BigDecimal batchPriceCg) {
		this.batchPriceCg = batchPriceCg;
	}

	public Integer getBatchPriceMinNumCg() {
		return batchPriceMinNumCg;
	}

	public void setBatchPriceMinNumCg(Integer batchPriceMinNumCg) {
		this.batchPriceMinNumCg = batchPriceMinNumCg;
	}

	public Integer getBatchPriceMaxNumCg() {
		return batchPriceMaxNumCg;
	}

	public void setBatchPriceMaxNumCg(Integer batchPriceMaxNumCg) {
		this.batchPriceMaxNumCg = batchPriceMaxNumCg;
	}

	public Long getCostPriceStartTime() {
		return costPriceStartTime;
	}

	public void setCostPriceStartTime(Long costPriceStartTime) {
		this.costPriceStartTime = costPriceStartTime;
	}

	public Long getCostPriceEndTime() {
		return costPriceEndTime;
	}

	public void setCostPriceEndTime(Long costPriceEndTime) {
		this.costPriceEndTime = costPriceEndTime;
	}

	public BigDecimal getBatchPrice() {
		return batchPrice;
	}

	public void setBatchPrice(BigDecimal batchPrice) {
		this.batchPrice = batchPrice;
	}

	public Integer getBatchPriceMinNum() {
		return batchPriceMinNum;
	}

	public void setBatchPriceMinNum(Integer batchPriceMinNum) {
		this.batchPriceMinNum = batchPriceMinNum;
	}

	public Integer getBatchPriceMaxNum() {
		return batchPriceMaxNum;
	}

	public void setBatchPriceMaxNum(Integer batchPriceMaxNum) {
		this.batchPriceMaxNum = batchPriceMaxNum;
	}

	public BigDecimal getDistributionPriceCg() {
		return distributionPriceCg;
	}

	public void setDistributionPriceCg(BigDecimal distributionPriceCg) {
		this.distributionPriceCg = distributionPriceCg;
	}

	public BigDecimal getPrivatePriceCg() {
		return privatePriceCg;
	}

	public void setPrivatePriceCg(BigDecimal privatePriceCg) {
		this.privatePriceCg = privatePriceCg;
	}

	public BigDecimal getPublicPriceCg() {
		return publicPriceCg;
	}

	public void setPublicPriceCg(BigDecimal publicPriceCg) {
		this.publicPriceCg = publicPriceCg;
	}

	public Integer getIsReportTerminalCg() {
		return isReportTerminalCg;
	}

	public void setIsReportTerminalCg(Integer isReportTerminalCg) {
		this.isReportTerminalCg = isReportTerminalCg;
	}

	public Integer getIsManufacturerAuthorizationCg() {
		return isManufacturerAuthorizationCg;
	}

	public void setIsManufacturerAuthorizationCg(Integer isManufacturerAuthorizationCg) {
		this.isManufacturerAuthorizationCg = isManufacturerAuthorizationCg;
	}

	public Integer getMinNumCg() {
		return minNumCg;
	}

	public void setMinNumCg(Integer minNumCg) {
		this.minNumCg = minNumCg;
	}

	public BigDecimal getMinAmountCg() {
		return minAmountCg;
	}

	public void setMinAmountCg(BigDecimal minAmountCg) {
		this.minAmountCg = minAmountCg;
	}

	public String getBatchPolicyCg() {
		return batchPolicyCg;
	}

	public void setBatchPolicyCg(String batchPolicyCg) {
		this.batchPolicyCg = batchPolicyCg;
	}

	public List<GoodsChannelPriceExtend> getGoodsChannelPriceExtendListCg() {
		return goodsChannelPriceExtendListCg;
	}

	public void setGoodsChannelPriceExtendListCg(List<GoodsChannelPriceExtend> goodsChannelPriceExtendListCg) {
		this.goodsChannelPriceExtendListCg = goodsChannelPriceExtendListCg;
	}

	public String getCustomerTypeComments() {
		return customerTypeComments;
	}

	public void setCustomerTypeComments(String customerTypeComments) {
		this.customerTypeComments = customerTypeComments;
	}

	public Integer getMinNum() {
		return minNum;
	}

	public void setMinNum(Integer minNum) {
		this.minNum = minNum;
	}

	public BigDecimal getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(BigDecimal minAmount) {
		this.minAmount = minAmount;
	}

	public String getBatchPolicy() {
		return batchPolicy;
	}

	public void setBatchPolicy(String batchPolicy) {
		this.batchPolicy = batchPolicy;
	}

	public BigDecimal getVipPrice() {
		return vipPrice;
	}

	public void setVipPrice(BigDecimal vipPrice) {
		this.vipPrice = vipPrice;
	}

	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}

	public List<GoodsChannelPriceExtend> getGoodsChannelPriceExtendList() {
		return goodsChannelPriceExtendList;
	}

	public void setGoodsChannelPriceExtendList(List<GoodsChannelPriceExtend> goodsChannelPriceExtendList) {
		this.goodsChannelPriceExtendList = goodsChannelPriceExtendList;
	}

	public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}

	public BigDecimal getChannelPrice() {
		return channelPrice;
	}

	public void setChannelPrice(BigDecimal channelPrice) {
		this.channelPrice = channelPrice;
	}

	public Long getPeriodTime() {
		return periodTime;
	}

	public void setPeriodTime(Long periodTime) {
		this.periodTime = periodTime;
	}

	public String getSku() {
    	return sku;
    }
    
    public void setSku(String sku) {
    	this.sku = sku;
    }
    
    public String getGoodsName() {
    	return goodsName;
    }
    
    public void setGoodsName(String goodsName) {
    	this.goodsName = goodsName;
    }
    
    public String getModel() {
    	return model;
    }
    
    public void setModel(String model) {
    	this.model = model;
    }
    
    public Integer getBrandId() {
    	return brandId;
    }
    
    public void setBrandId(Integer brandId) {
    	this.brandId = brandId;
    }
    
    public Integer getUnitId() {
    	return unitId;
    }
    
    public void setUnitId(Integer unitId) {
    	this.unitId = unitId;
    }
    
    //--------------------------------------------------------------------------------------------

	public Integer getGoodsChannelPriceId() {
        return goodsChannelPriceId;
    }

    public void setGoodsChannelPriceId(Integer goodsChannelPriceId) {
        this.goodsChannelPriceId = goodsChannelPriceId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName == null ? null : provinceName.trim();
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public BigDecimal getDistributionPrice() {
		return distributionPrice;
	}

	public void setDistributionPrice(BigDecimal distributionPrice) {
		this.distributionPrice = distributionPrice;
	}

	public BigDecimal getPublicPrice() {
		return publicPrice;
	}

	public void setPublicPrice(BigDecimal publicPrice) {
		this.publicPrice = publicPrice;
	}

	public BigDecimal getPrivatePrice() {
        return privatePrice;
    }

    public void setPrivatePrice(BigDecimal privatePrice) {
        this.privatePrice = privatePrice;
    }

    public String getDeliveryCycle() {
		return deliveryCycle;
	}

	public void setDeliveryCycle(String deliveryCycle) {
		this.deliveryCycle = deliveryCycle;
	}

	public Date getPeriodDate() {
        return periodDate;
    }

    public void setPeriodDate(Date periodDate) {
        this.periodDate = periodDate;
    }

    public Integer getIsReportTerminal() {
        return isReportTerminal;
    }

    public void setIsReportTerminal(Integer isReportTerminal) {
        this.isReportTerminal = isReportTerminal;
    }

    public Integer getIsManufacturerAuthorization() {
        return isManufacturerAuthorization;
    }

    public void setIsManufacturerAuthorization(Integer isManufacturerAuthorization) {
        this.isManufacturerAuthorization = isManufacturerAuthorization;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}