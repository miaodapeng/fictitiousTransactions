package com.vedeng.trader.model;

public class MjxAccountAddress {
    /** 自动编号  ADDRESS_ID **/
    private Integer addressId;

    /** 客户ID  TRADER_ID **/
    private Integer traderId;

    /** 收件人姓名  DELIVERY_NAME **/
    private String deliveryName;

    /** 联系电话  TELPHONE **/
    private String telphone;

    /** 所在地区  AREA **/
    private String area;

    /** 地区id  AREA_IDS **/
    private String areaIds;

    /** 是否默认收货地址 0否 1是  IS_DELIVERY_DEFAULT **/
    private Integer isDeliveryDefault;

    /** 是否默认收票地址 0否 1是  IS_INVOICE_DEFAULT **/
    private Integer isInvoiceDefault;
    /** 详细地址  ADDRESS **/
    private String address;

    /** 标签名称  TITLE_NAME **/
    private String titleName;

    /** 创建时间  ADD_TIME **/
    private Long addTime;

    /** 更新时间  MOD_TIME **/
    private Long modTime;

    /** 添加人手机号  CREATOR **/
    private String creator;
    
    private Integer mjxContactAdderssId;//MJX主键
    
    private Integer isEnable;//是否可用 0否 1是
    
    private String defaultAddress;//默认地址显示

	public String getDefaultAddress() {
		return defaultAddress;
	}

	public void setDefaultAddress(String defaultAddress) {
		this.defaultAddress = defaultAddress;
	}

	public Integer getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}

	public Integer getMjxContactAdderssId() {
		return mjxContactAdderssId;
	}

	public void setMjxContactAdderssId(Integer mjxContactAdderssId) {
		this.mjxContactAdderssId = mjxContactAdderssId;
	}

	/**   自动编号  ADDRESS_ID   **/
    public Integer getAddressId() {
        return addressId;
    }

    /**   自动编号  ADDRESS_ID   **/
    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    /**   客户ID  TRADER_ID   **/
    public Integer getTraderId() {
        return traderId;
    }

    /**   客户ID  TRADER_ID   **/
    public void setTraderId(Integer traderId) {
        this.traderId = traderId;
    }

    /**   收件人姓名  DELIVERY_NAME   **/
    public String getDeliveryName() {
        return deliveryName;
    }

    /**   收件人姓名  DELIVERY_NAME   **/
    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName == null ? null : deliveryName.trim();
    }

    /**   联系电话  TELPHONE   **/
    public String getTelphone() {
        return telphone;
    }

    /**   联系电话  TELPHONE   **/
    public void setTelphone(String telphone) {
        this.telphone = telphone == null ? null : telphone.trim();
    }

    /**   所在地区  AREA   **/
    public String getArea() {
        return area;
    }

    /**   所在地区  AREA   **/
    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    /**   地区id  AREA_IDS   **/
    public String getAreaIds() {
        return areaIds;
    }

    /**   地区id  AREA_IDS   **/
    public void setAreaIds(String areaIds) {
        this.areaIds = areaIds == null ? null : areaIds.trim();
    }


    public Integer getIsDeliveryDefault() {
		return isDeliveryDefault;
	}

	public void setIsDeliveryDefault(Integer isDeliveryDefault) {
		this.isDeliveryDefault = isDeliveryDefault;
	}

	public Integer getIsInvoiceDefault() {
		return isInvoiceDefault;
	}

	public void setIsInvoiceDefault(Integer isInvoiceDefault) {
		this.isInvoiceDefault = isInvoiceDefault;
	}

	/**   详细地址  ADDRESS   **/
    public String getAddress() {
        return address;
    }

    /**   详细地址  ADDRESS   **/
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**   标签名称  TITLE_NAME   **/
    public String getTitleName() {
        return titleName;
    }

    /**   标签名称  TITLE_NAME   **/
    public void setTitleName(String titleName) {
        this.titleName = titleName == null ? null : titleName.trim();
    }

    /**   创建时间  ADD_TIME   **/
    public Long getAddTime() {
        return addTime;
    }

    /**   创建时间  ADD_TIME   **/
    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    /**   更新时间  MOD_TIME   **/
    public Long getModTime() {
        return modTime;
    }

    /**   更新时间  MOD_TIME   **/
    public void setModTime(Long modTime) {
        this.modTime = modTime;
    }

    /**   添加人手机号  CREATOR   **/
    public String getCreator() {
        return creator;
    }

    /**   添加人手机号  CREATOR   **/
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }
}