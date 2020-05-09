package com.vedeng.order.model.vo;

import java.math.BigDecimal;
import java.util.List;

/**
* @ClassName: OrderData
* @Description: TODO(这里用一句话描述这个类的作用)
* @author strange
* @date 2019年8月19日
*
*/
/**
* @ClassName: OrderData
* @Description: TODO(这里用一句话描述这个类的作用)
* @author strange
* @date 2019年8月19日
*
*/
public class OrderData {

    private Integer accountId;
    private String orderNo;//订单号
    private Integer orderType;//订单类型
    private String username;//用户昵称
    private String phone;//用户手机号
    private Integer companyId;//公司id
    private String companyName;//公司名称
    private Integer deliveryAddressId;//收货地址ID
    private String deliveryUserName;//收货人名称
    private String deliveryUserArea;//收货地址：省 市 区
    private String deliveryUserAddress;//收件人地址
    private String deliveryUserTel;//收件人电话
    private String deliveryUserPhone;//收件人手机
    private Integer invoiceTraderContactId;//收票联系人id
    private String invoiceTraderContactName;//收票联系人名称
    private String invoiceTraderContactMobile;//收票联系人手机
    private String invoiceTraderContactTelephone;//收票联系人电话
    private Integer invoiceTraderAddressId;//收票地址id
    private Integer invoiceTraderAreaId;//收票地区最小级ID
    private String invoiceTraderArea;//收票地区
    private String invoiceTraderAddress;//收票地址
    private String invoiceUserPhone;//收票电话
    private String remakes;//备注
    private BigDecimal marketMomney;//总价
    private Integer productTypeNum;//种类数量
    private Integer productNum;//商品总数量
    private Integer paymentMode;//支付方式
    private Integer orderStatus;//订单状态
    private BigDecimal jxSalePrice;//贝登精选销售价
    private Integer validStatus;//生效状态
    private Integer isSendInvoice;//是否寄送发票
    private String additionalClause;//附加条款
    private Integer invoiceType;//发票类型 1、增值 2、普通
    private String closeComments;//订单关闭原因
    private String deliveryLevel1Id;//省Id
    private String deliveryLevel2Id;//市id
    private String deliveryLevel3Id;//区id
//    private String deliveryAreaIds;//省市区id
    private List<OrderGoodsData> goodsList;//订单商品
    private Integer cancelType;// 1、超时关闭 2、手动关闭

    public Integer getCancelType() {
        return cancelType;
    }

    public void setCancelType(Integer cancelType) {
        this.cancelType = cancelType;
    }


    
//    public String getDeliveryAreaIds() {
//		return deliveryAreaIds;
//	}
//
//	public void setDeliveryAreaIds(String deliveryAreaIds) {
//		this.deliveryAreaIds = deliveryAreaIds;
//	}

	public String getDeliveryLevel1Id() {
		return deliveryLevel1Id;
	}

	public void setDeliveryLevel1Id(String deliveryLevel1Id) {
		this.deliveryLevel1Id = deliveryLevel1Id;
	}

	public String getDeliveryLevel2Id() {
		return deliveryLevel2Id;
	}

	public void setDeliveryLevel2Id(String deliveryLevel2Id) {
		this.deliveryLevel2Id = deliveryLevel2Id;
	}

	public String getDeliveryLevel3Id() {
		return deliveryLevel3Id;
	}

	public void setDeliveryLevel3Id(String deliveryLevel3Id) {
		this.deliveryLevel3Id = deliveryLevel3Id;
	}

	public Integer getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(Integer invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getCloseComments() {
		return closeComments;
	}

	public void setCloseComments(String closeComments) {
		this.closeComments = closeComments;
	}

	public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getInvoiceUserPhone() {
        return invoiceUserPhone;
    }

    public void setInvoiceUserPhone(String invoiceUserPhone) {
        this.invoiceUserPhone = invoiceUserPhone;
    }

    public Integer getProductTypeNum() {
        return productTypeNum;
    }

    public void setProductTypeNum(Integer productTypeNum) {
        this.productTypeNum = productTypeNum;
    }

    public Integer getValidStatus() {
		return validStatus;
	}

	public void setValidStatus(Integer validStatus) {
		this.validStatus = validStatus;
	}

	public Integer getInvoiceTraderContactId() {
		return invoiceTraderContactId;
	}

	public void setInvoiceTraderContactId(Integer invoiceTraderContactId) {
		this.invoiceTraderContactId = invoiceTraderContactId;
	}

	public String getInvoiceTraderContactName() {
		return invoiceTraderContactName;
	}

	public void setInvoiceTraderContactName(String invoiceTraderContactName) {
		this.invoiceTraderContactName = invoiceTraderContactName;
	}

	public String getInvoiceTraderContactMobile() {
		return invoiceTraderContactMobile;
	}

	public void setInvoiceTraderContactMobile(String invoiceTraderContactMobile) {
		this.invoiceTraderContactMobile = invoiceTraderContactMobile;
	}

	public String getInvoiceTraderContactTelephone() {
		return invoiceTraderContactTelephone;
	}

	public void setInvoiceTraderContactTelephone(String invoiceTraderContactTelephone) {
		this.invoiceTraderContactTelephone = invoiceTraderContactTelephone;
	}

	public Integer getInvoiceTraderAddressId() {
		return invoiceTraderAddressId;
	}

	public void setInvoiceTraderAddressId(Integer invoiceTraderAddressId) {
		this.invoiceTraderAddressId = invoiceTraderAddressId;
	}

	public Integer getInvoiceTraderAreaId() {
		return invoiceTraderAreaId;
	}

	public void setInvoiceTraderAreaId(Integer invoiceTraderAreaId) {
		this.invoiceTraderAreaId = invoiceTraderAreaId;
	}

	public String getInvoiceTraderArea() {
		return invoiceTraderArea;
	}

	public void setInvoiceTraderArea(String invoiceTraderArea) {
		this.invoiceTraderArea = invoiceTraderArea;
	}

	public String getInvoiceTraderAddress() {
		return invoiceTraderAddress;
	}

	public void setInvoiceTraderAddress(String invoiceTraderAddress) {
		this.invoiceTraderAddress = invoiceTraderAddress;
	}

	public BigDecimal getJxSalePrice() {
		return jxSalePrice;
	}

	public void setJxSalePrice(BigDecimal jxSalePrice) {
		this.jxSalePrice = jxSalePrice;
	}

	public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getDeliveryAddressId() {
        return deliveryAddressId;
    }

    public void setDeliveryAddressId(Integer deliveryAddressId) {
        this.deliveryAddressId = deliveryAddressId;
    }

    public String getDeliveryUserName() {
        return deliveryUserName;
    }

    public void setDeliveryUserName(String deliveryUserName) {
        this.deliveryUserName = deliveryUserName;
    }

    public String getDeliveryUserArea() {
        return deliveryUserArea;
    }

    public void setDeliveryUserArea(String deliveryUserArea) {
        this.deliveryUserArea = deliveryUserArea;
    }

    public String getDeliveryUserAddress() {
        return deliveryUserAddress;
    }

    public void setDeliveryUserAddress(String deliveryUserAddress) {
        this.deliveryUserAddress = deliveryUserAddress;
    }

    public String getDeliveryUserTel() {
        return deliveryUserTel;
    }

    public void setDeliveryUserTel(String deliveryUserTel) {
        this.deliveryUserTel = deliveryUserTel;
    }

    public String getDeliveryUserPhone() {
        return deliveryUserPhone;
    }

    public void setDeliveryUserPhone(String deliveryUserPhone) {
        this.deliveryUserPhone = deliveryUserPhone;
    }

    public String getRemakes() {
        return remakes;
    }

    public void setRemakes(String remakes) {
        this.remakes = remakes;
    }

    public BigDecimal getMarketMomney() {
        return marketMomney;
    }

    public void setMarketMomney(BigDecimal marketMomney) {
        this.marketMomney = marketMomney;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    public Integer getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(Integer paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getIsSendInvoice() {
        return isSendInvoice;
    }

    public void setIsSendInvoice(Integer isSendInvoice) {
        this.isSendInvoice = isSendInvoice;
    }

    public String getAdditionalClause() {
        return additionalClause;
    }

    public void setAdditionalClause(String additionalClause) {
        this.additionalClause = additionalClause;
    }

    public List<OrderGoodsData> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<OrderGoodsData> goodsList) {
        this.goodsList = goodsList;
    }

	@Override
	public String toString() {
		return "OrderData [accountId=" + accountId + ", orderNo=" + orderNo + ", orderType=" + orderType + ", username="
				+ username + ", phone=" + phone + ", deliveryAddressId=" + deliveryAddressId + ", deliveryUserName="
				+ deliveryUserName + ", deliveryUserArea=" + deliveryUserArea + ", deliveryUserAddress="
				+ deliveryUserAddress + ", deliveryUserTel=" + deliveryUserTel + ", deliveryUserPhone="
				+ deliveryUserPhone + ", invoiceTraderContactId=" + invoiceTraderContactId
				+ ", invoiceTraderContactName=" + invoiceTraderContactName + ", invoiceTraderContactMobile="
				+ invoiceTraderContactMobile + ", invoiceTraderContactTelephone=" + invoiceTraderContactTelephone
				+ ", invoiceTraderAddressId=" + invoiceTraderAddressId + ", invoiceTraderAreaId=" + invoiceTraderAreaId
				+ ", invoiceTraderArea=" + invoiceTraderArea + ", invoiceTraderAddress=" + invoiceTraderAddress
				+ ", remakes=" + remakes + ", marketMomney=" + marketMomney + ", productNum=" + productNum
				+ ", paymentMode=" + paymentMode + ", orderStatus=" + orderStatus + ", jxSalePrice=" + jxSalePrice
				+ ", validStatus=" + validStatus + ", goodsList=" + goodsList + ", getValidStatus()=" + getValidStatus()
				+ ", getInvoiceTraderContactId()=" + getInvoiceTraderContactId() + ", getInvoiceTraderContactName()="
				+ getInvoiceTraderContactName() + ", getInvoiceTraderContactMobile()=" + getInvoiceTraderContactMobile()
				+ ", getInvoiceTraderContactTelephone()=" + getInvoiceTraderContactTelephone()
				+ ", getInvoiceTraderAddressId()=" + getInvoiceTraderAddressId() + ", getInvoiceTraderAreaId()="
				+ getInvoiceTraderAreaId() + ", getInvoiceTraderArea()=" + getInvoiceTraderArea()
				+ ", getInvoiceTraderAddress()=" + getInvoiceTraderAddress() + ", getJxSalePrice()=" + getJxSalePrice()
				+ ", getAccountId()=" + getAccountId() + ", getOrderNo()=" + getOrderNo() + ", getOrderType()="
				+ getOrderType() + ", getUsername()=" + getUsername() + ", getPhone()=" + getPhone()
				+ ", getDeliveryAddressId()=" + getDeliveryAddressId() + ", getDeliveryUserName()="
				+ getDeliveryUserName() + ", getDeliveryUserArea()=" + getDeliveryUserArea()
				+ ", getDeliveryUserAddress()=" + getDeliveryUserAddress() + ", getDeliveryUserTel()="
				+ getDeliveryUserTel() + ", getDeliveryUserPhone()=" + getDeliveryUserPhone() + ", getRemakes()="
				+ getRemakes() + ", getMarketMomney()=" + getMarketMomney() + ", getProductNum()=" + getProductNum()
				+ ", getPaymentMode()=" + getPaymentMode() + ", getOrderStatus()=" + getOrderStatus()
				+ ", getGoodsList()=" + getGoodsList() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

    
    
}
