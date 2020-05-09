package com.vedeng.order.model;

import java.math.BigDecimal;

public class SaleorderCoupon {
	private Integer saleorderCouponId;

    private Integer saleorderId;

    private Integer couponType;
    
    private BigDecimal denomination;

	public Integer getSaleorderCouponId() {
		return saleorderCouponId;
	}

	public void setSaleorderCouponId(Integer saleorderCouponId) {
		this.saleorderCouponId = saleorderCouponId;
	}

	public Integer getSaleorderId() {
		return saleorderId;
	}

	public void setSaleorderId(Integer saleorderId) {
		this.saleorderId = saleorderId;
	}

	public Integer getCouponType() {
		return couponType;
	}

	public void setCouponType(Integer couponType) {
		this.couponType = couponType;
	}

	public BigDecimal getDenomination() {
		return denomination;
	}

	public void setDenomination(BigDecimal denomination) {
		this.denomination = denomination;
	}
    
}
