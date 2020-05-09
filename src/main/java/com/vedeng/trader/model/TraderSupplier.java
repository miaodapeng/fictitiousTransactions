package com.vedeng.trader.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.model.Tag;
public class TraderSupplier implements Serializable {
    /**
	 * @Fields serialVersionUID : TODO
	 */
    private static final long serialVersionUID = 1L;

    private Integer traderSupplierId;

    private Integer traderId;

    private BigDecimal amount;

    private BigDecimal periodAmount;

    private Integer periodDay;

    private Integer isEnable;

    private Integer isTop;

    private String supplyBrand;

    private String supplyProduct;

    private Integer grade;

    private Long disableTime;

    private String disableReason;

    private String comments;

    private String brief;

    private Long addTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;

    private List<TraderSupplierSupplyBrand> traderSupplierSupplyBrands;
    
    private Trader trader;
    
    private String ownerSale;
    
    private List<Tag> tag;
    
    private List<TraderOrderGoods> orderGoods;
    
    private SysOptionDefinition sysOptionDefinition;
    
    private List<String> tagName;
    
    private Integer orderTimes;
    
    private Integer companyId;
    
    private String hotTelephone;
    
    private String serviceTelephone;
    
    private String logisticsName;
    
    private String website;
    
    private String[] companyUris;
    
    public Integer getTraderSupplierId() {
        return traderSupplierId;
    }

    public void setTraderSupplierId(Integer traderSupplierId) {
        this.traderSupplierId = traderSupplierId;
    }

    public Integer getTraderId() {
        return traderId;
    }

    public void setTraderId(Integer traderId) {
        this.traderId = traderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPeriodAmount() {
        return periodAmount;
    }

    public void setPeriodAmount(BigDecimal periodAmount) {
        this.periodAmount = periodAmount;
    }

    public Integer getPeriodDay() {
        return periodDay;
    }

    public void setPeriodDay(Integer periodDay) {
        this.periodDay = periodDay;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }

    public String getSupplyBrand() {
        return supplyBrand;
    }

    public void setSupplyBrand(String supplyBrand) {
        this.supplyBrand = supplyBrand == null ? null : supplyBrand.trim();
    }

    public String getSupplyProduct() {
        return supplyProduct;
    }

    public void setSupplyProduct(String supplyProduct) {
        this.supplyProduct = supplyProduct == null ? null : supplyProduct.trim();
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Long getDisableTime() {
        return disableTime;
    }

    public void setDisableTime(Long disableTime) {
        this.disableTime = disableTime;
    }

    public String getDisableReason() {
        return disableReason;
    }

    public void setDisableReason(String disableReason) {
        this.disableReason = disableReason == null ? null : disableReason.trim();
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief == null ? null : brief.trim();
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

	public List<TraderSupplierSupplyBrand> getTraderSupplierSupplyBrands() {
		return traderSupplierSupplyBrands;
	}

	public void setTraderSupplierSupplyBrands(List<TraderSupplierSupplyBrand> traderSupplierSupplyBrands) {
		this.traderSupplierSupplyBrands = traderSupplierSupplyBrands;
	}

	public Trader getTrader() {
		return trader;
	}

	public void setTrader(Trader trader) {
		this.trader = trader;
	}

	public String getOwnerSale() {
		return ownerSale;
	}

	public void setOwnerSale(String ownerSale) {
		this.ownerSale = ownerSale;
	}

	public List<Tag> getTag() {
		return tag;
	}

	public void setTag(List<Tag> tag) {
		this.tag = tag;
	}

	public List<TraderOrderGoods> getOrderGoods() {
		return orderGoods;
	}

	public void setOrderGoods(List<TraderOrderGoods> orderGoods) {
		this.orderGoods = orderGoods;
	}

	public SysOptionDefinition getSysOptionDefinition() {
		return sysOptionDefinition;
	}

	public void setSysOptionDefinition(SysOptionDefinition sysOptionDefinition) {
		this.sysOptionDefinition = sysOptionDefinition;
	}

	public List<String> getTagName() {
		return tagName;
	}

	public void setTagName(List<String> tagName) {
		this.tagName = tagName;
	}

	public Integer getOrderTimes() {
		return orderTimes;
	}

	public void setOrderTimes(Integer orderTimes) {
		this.orderTimes = orderTimes;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getHotTelephone() {
		return hotTelephone;
	}

	public void setHotTelephone(String hotTelephone) {
		this.hotTelephone = hotTelephone;
	}

	public String getServiceTelephone() {
		return serviceTelephone;
	}

	public void setServiceTelephone(String serviceTelephone) {
		this.serviceTelephone = serviceTelephone;
	}

	public String getLogisticsName() {
		return logisticsName;
	}

	public void setLogisticsName(String logisticsName) {
		this.logisticsName = logisticsName;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String[] getCompanyUris() {
		return companyUris;
	}

	public void setCompanyUris(String[] companyUris) {
		this.companyUris = companyUris;
	}

}