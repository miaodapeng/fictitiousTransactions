package com.vedeng.saleperformance.model;

import java.math.BigDecimal;
import java.util.Date;

public class SalesPerformanceOrders {
    private Integer salesPerformanceOrdersId;

    private Integer companyId;

    private Date yearMonth;

    private Integer userId;

    private Integer saleorderId;

    private String saleorderNo;

    private Integer traderId;

    private String traderName;

    private Long validTime;

    private BigDecimal totalAmount;

    private BigDecimal costAmount;

    private Long addTime;
    
    
    
    private String addTimeStr;
    
    private String statusStr;
    
    private String validTimeStr;
    
    private String saleUsername;
    
    private String departmentTwo;
    
    private String departmentThree;
    
    private String paymentStatusStr;
    
    private String deliveryStatusStr;
    
    private String arrivalStatusStr;
    
    private BigDecimal realReceiveAmount;
    
    private BigDecimal realOrderAmount;
    
    private String sku;
    
    private String goodsName;
    
    private String brandName;
    
    private String model;
    
    private BigDecimal price;
    
    private Integer num;
    
    private BigDecimal referenceCostPrice;
    
    private String goodsDeliveryStatusStr;
    
    private String goodsArrivalStatusStr;
    
    private String salesPerformanceModTimeStr;
    

    public String getAddTimeStr() {
		return addTimeStr;
	}

	public void setAddTimeStr(String addTimeStr) {
		this.addTimeStr = addTimeStr;
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public String getValidTimeStr() {
		return validTimeStr;
	}

	public void setValidTimeStr(String validTimeStr) {
		this.validTimeStr = validTimeStr;
	}

	public String getSaleUsername() {
		return saleUsername;
	}

	public void setSaleUsername(String saleUsername) {
		this.saleUsername = saleUsername;
	}

	public String getDepartmentTwo() {
		return departmentTwo;
	}

	public void setDepartmentTwo(String departmentTwo) {
		this.departmentTwo = departmentTwo;
	}

	public String getDepartmentThree() {
		return departmentThree;
	}

	public void setDepartmentThree(String departmentThree) {
		this.departmentThree = departmentThree;
	}

	public String getPaymentStatusStr() {
		return paymentStatusStr;
	}

	public void setPaymentStatusStr(String paymentStatusStr) {
		this.paymentStatusStr = paymentStatusStr;
	}

	public String getDeliveryStatusStr() {
		return deliveryStatusStr;
	}

	public void setDeliveryStatusStr(String deliveryStatusStr) {
		this.deliveryStatusStr = deliveryStatusStr;
	}

	public String getArrivalStatusStr() {
		return arrivalStatusStr;
	}

	public void setArrivalStatusStr(String arrivalStatusStr) {
		this.arrivalStatusStr = arrivalStatusStr;
	}

	public BigDecimal getRealReceiveAmount() {
		return realReceiveAmount;
	}

	public void setRealReceiveAmount(BigDecimal realReceiveAmount) {
		this.realReceiveAmount = realReceiveAmount;
	}

	public BigDecimal getRealOrderAmount() {
		return realOrderAmount;
	}

	public void setRealOrderAmount(BigDecimal realOrderAmount) {
		this.realOrderAmount = realOrderAmount;
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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public BigDecimal getReferenceCostPrice() {
		return referenceCostPrice;
	}

	public void setReferenceCostPrice(BigDecimal referenceCostPrice) {
		this.referenceCostPrice = referenceCostPrice;
	}

	public String getGoodsDeliveryStatusStr() {
		return goodsDeliveryStatusStr;
	}

	public void setGoodsDeliveryStatusStr(String goodsDeliveryStatusStr) {
		this.goodsDeliveryStatusStr = goodsDeliveryStatusStr;
	}

	public String getGoodsArrivalStatusStr() {
		return goodsArrivalStatusStr;
	}

	public void setGoodsArrivalStatusStr(String goodsArrivalStatusStr) {
		this.goodsArrivalStatusStr = goodsArrivalStatusStr;
	}

	public String getSalesPerformanceModTimeStr() {
		return salesPerformanceModTimeStr;
	}

	public void setSalesPerformanceModTimeStr(String salesPerformanceModTimeStr) {
		this.salesPerformanceModTimeStr = salesPerformanceModTimeStr;
	}

	public Integer getSalesPerformanceOrdersId() {
        return salesPerformanceOrdersId;
    }

    public void setSalesPerformanceOrdersId(Integer salesPerformanceOrdersId) {
        this.salesPerformanceOrdersId = salesPerformanceOrdersId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Date getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(Date yearMonth) {
        this.yearMonth = yearMonth;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSaleorderId() {
        return saleorderId;
    }

    public void setSaleorderId(Integer saleorderId) {
        this.saleorderId = saleorderId;
    }

    public String getSaleorderNo() {
        return saleorderNo;
    }

    public void setSaleorderNo(String saleorderNo) {
        this.saleorderNo = saleorderNo == null ? null : saleorderNo.trim();
    }

    public Integer getTraderId() {
        return traderId;
    }

    public void setTraderId(Integer traderId) {
        this.traderId = traderId;
    }

    public String getTraderName() {
        return traderName;
    }

    public void setTraderName(String traderName) {
        this.traderName = traderName==null ? null :traderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
    }

    public Long getValidTime() {
        return validTime;
    }

    public void setValidTime(Long validTime) {
        this.validTime = validTime;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getCostAmount() {
        return costAmount;
    }

    public void setCostAmount(BigDecimal costAmount) {
        this.costAmount = costAmount;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }
}