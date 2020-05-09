package com.vedeng.logistics.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class WarehouseGoodsDayStatus implements Serializable{
    private Integer warehouseGoodsDayStatusId;

    private Date date;

    private Integer saleorderId;

    private String saleorderNo;

    private Integer orderType;

    private Integer buyorderId;

    private String buyorderNo;

    private Integer goodsId;

    private String goodsName;

    private String sku;

    private String brandName;

    private String model;

    private String materialCode;

    private BigDecimal buyPrice;

    private Integer preNum;

    private Integer inNum;

    private String unitName;

    private BigDecimal inAmount;

    private Integer outNum;

    private Integer leftNum;

    private BigDecimal leftAmount;

    private Integer occupyNum;

    private Integer onwayNum;

    private Integer warehouseId;

    private Integer storageRoomId;

    private Integer storageAreaId;

    private Integer storageLocationId;

    private Integer storageRackId;

    private Integer arrivalCycle;

    private Integer deliveryCycle;

    private Integer inWarehoueseCycle;

    public Integer getWarehouseGoodsDayStatusId() {
        return warehouseGoodsDayStatusId;
    }

    public void setWarehouseGoodsDayStatusId(Integer warehouseGoodsDayStatusId) {
        this.warehouseGoodsDayStatusId = warehouseGoodsDayStatusId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getBuyorderId() {
        return buyorderId;
    }

    public void setBuyorderId(Integer buyorderId) {
        this.buyorderId = buyorderId;
    }

    public String getBuyorderNo() {
        return buyorderNo;
    }

    public void setBuyorderNo(String buyorderNo) {
        this.buyorderNo = buyorderNo == null ? null : buyorderNo.trim();
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku == null ? null : sku.trim();
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

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode == null ? null : materialCode.trim();
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Integer getPreNum() {
        return preNum;
    }

    public void setPreNum(Integer preNum) {
        this.preNum = preNum;
    }

    public Integer getInNum() {
        return inNum;
    }

    public void setInNum(Integer inNum) {
        this.inNum = inNum;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName == null ? null : unitName.trim();
    }

    public BigDecimal getInAmount() {
        return inAmount;
    }

    public void setInAmount(BigDecimal inAmount) {
        this.inAmount = inAmount;
    }

    public Integer getOutNum() {
        return outNum;
    }

    public void setOutNum(Integer outNum) {
        this.outNum = outNum;
    }

    public Integer getLeftNum() {
        return leftNum;
    }

    public void setLeftNum(Integer leftNum) {
        this.leftNum = leftNum;
    }

    public BigDecimal getLeftAmount() {
        return leftAmount;
    }

    public void setLeftAmount(BigDecimal leftAmount) {
        this.leftAmount = leftAmount;
    }

    public Integer getOccupyNum() {
        return occupyNum;
    }

    public void setOccupyNum(Integer occupyNum) {
        this.occupyNum = occupyNum;
    }

    public Integer getOnwayNum() {
        return onwayNum;
    }

    public void setOnwayNum(Integer onwayNum) {
        this.onwayNum = onwayNum;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Integer getStorageRoomId() {
        return storageRoomId;
    }

    public void setStorageRoomId(Integer storageRoomId) {
        this.storageRoomId = storageRoomId;
    }

    public Integer getStorageAreaId() {
        return storageAreaId;
    }

    public void setStorageAreaId(Integer storageAreaId) {
        this.storageAreaId = storageAreaId;
    }

    public Integer getStorageLocationId() {
        return storageLocationId;
    }

    public void setStorageLocationId(Integer storageLocationId) {
        this.storageLocationId = storageLocationId;
    }

    public Integer getStorageRackId() {
        return storageRackId;
    }

    public void setStorageRackId(Integer storageRackId) {
        this.storageRackId = storageRackId;
    }

    public Integer getArrivalCycle() {
        return arrivalCycle;
    }

    public void setArrivalCycle(Integer arrivalCycle) {
        this.arrivalCycle = arrivalCycle;
    }

    public Integer getDeliveryCycle() {
        return deliveryCycle;
    }

    public void setDeliveryCycle(Integer deliveryCycle) {
        this.deliveryCycle = deliveryCycle;
    }

    public Integer getInWarehoueseCycle() {
        return inWarehoueseCycle;
    }

    public void setInWarehoueseCycle(Integer inWarehoueseCycle) {
        this.inWarehoueseCycle = inWarehoueseCycle;
    }
}