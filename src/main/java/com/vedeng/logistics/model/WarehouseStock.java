package com.vedeng.logistics.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Objects;

public class WarehouseStock {
    /** 库存表主键  WAREHOUSE_STOCK_ID **/
    private Integer warehouseStockId;

    /** 商品表id  GOODS_ID **/
    private Integer goodsId;

    /** SKU  SKU **/
    private String sku;

    /** 库存量  STOCK_NUM **/
    private Integer stockNum;

    /** 占用库存  OCCUPY_NUM **/
    private Integer occupyNum;

    /** 可用库存  **/
    private Integer availableStockNum;

    /** 是否删除 0否 1是  IS_DELETE **/
    private Integer isDelete;
    /**活动占用数量*/
    private Integer actionOccupyNum;

    /**活动锁定库存*/
    private Integer actionLockNum;

    /**活动id*/
    private  Integer actionId;
    /** 添加时间  ADD_TIME **/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;

    /** 修改时间  MOD_TIME **/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modTime;

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getActionLockNum() {
        return actionLockNum;
    }

    public void setActionLockNum(Integer actionLockNum) {
        this.actionLockNum = actionLockNum;
    }

    public Integer getActionOccupyNum() {
        return actionOccupyNum;
    }

    public void setActionOccupyNum(Integer actionOccupyNum) {
        this.actionOccupyNum = actionOccupyNum;
    }

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    /**   库存表主键  WAREHOUSE_STOCK_ID   **/
    public Integer getWarehouseStockId() {
        return warehouseStockId;
    }

    /**   库存表主键  WAREHOUSE_STOCK_ID   **/
    public void setWarehouseStockId(Integer warehouseStockId) {
        this.warehouseStockId = warehouseStockId;
    }

    /**   商品表id  GOODS_ID   **/
    public Integer getGoodsId() {
        return goodsId;
    }

    /**   商品表id  GOODS_ID   **/
    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    /**   SKU  SKU   **/
    public String getSku() {
        return sku;
    }

    /**   SKU  SKU   **/
    public void setSku(String sku) {
        this.sku = sku == null ? null : sku.trim();
    }

    public Integer getAvailableStockNum() {
        return availableStockNum;
    }

    public void setAvailableStockNum(Integer availableStockNum) {
        this.availableStockNum = availableStockNum;
    }

    /**   库存量  STOCK_NUM   **/
    public Integer getStockNum() {
        return stockNum;
    }

    /**   库存量  STOCK_NUM   **/
    public void setStockNum(Integer stockNum) {
        this.stockNum = stockNum;
    }

    /**   占用库存  OCCUPY_NUM   **/
    public Integer getOccupyNum() {
        return occupyNum;
    }

    /**   占用库存  OCCUPY_NUM   **/
    public void setOccupyNum(Integer occupyNum) {
        this.occupyNum = occupyNum;
    }


    /**   添加时间  ADD_TIME   **/
    public Date getAddTime() {
        return addTime;
    }

    /**   添加时间  ADD_TIME   **/
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**   修改时间  MOD_TIME   **/
    public Date getModTime() {
        return modTime;
    }

    /**   修改时间  MOD_TIME   **/
    public void setModTime(Date modTime) {
        this.modTime = modTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WarehouseStock that = (WarehouseStock) o;

        if (warehouseStockId != null ? !warehouseStockId.equals(that.warehouseStockId) : that.warehouseStockId != null)
            return false;
        if (goodsId != null ? !goodsId.equals(that.goodsId) : that.goodsId != null) return false;
        if (sku != null ? !sku.equals(that.sku) : that.sku != null) return false;
        if (stockNum != null ? !stockNum.equals(that.stockNum) : that.stockNum != null) return false;
        if (occupyNum != null ? !occupyNum.equals(that.occupyNum) : that.occupyNum != null) return false;
        if (availableStockNum != null ? !availableStockNum.equals(that.availableStockNum) : that.availableStockNum != null)
            return false;
        if (isDelete != null ? !isDelete.equals(that.isDelete) : that.isDelete != null) return false;
        if (actionOccupyNum != null ? !actionOccupyNum.equals(that.actionOccupyNum) : that.actionOccupyNum != null)
            return false;
        if (actionLockNum != null ? !actionLockNum.equals(that.actionLockNum) : that.actionLockNum != null)
            return false;
        if (actionId != null ? !actionId.equals(that.actionId) : that.actionId != null) return false;
        if (addTime != null ? !addTime.equals(that.addTime) : that.addTime != null) return false;
        return modTime != null ? modTime.equals(that.modTime) : that.modTime == null;
    }

    @Override
    public int hashCode() {
        int result = warehouseStockId != null ? warehouseStockId.hashCode() : 0;
        result = 31 * result + (goodsId != null ? goodsId.hashCode() : 0);
        result = 31 * result + (sku != null ? sku.hashCode() : 0);
        result = 31 * result + (stockNum != null ? stockNum.hashCode() : 0);
        result = 31 * result + (occupyNum != null ? occupyNum.hashCode() : 0);
        result = 31 * result + (availableStockNum != null ? availableStockNum.hashCode() : 0);
        result = 31 * result + (isDelete != null ? isDelete.hashCode() : 0);
        result = 31 * result + (actionOccupyNum != null ? actionOccupyNum.hashCode() : 0);
        result = 31 * result + (actionLockNum != null ? actionLockNum.hashCode() : 0);
        result = 31 * result + (actionId != null ? actionId.hashCode() : 0);
        result = 31 * result + (addTime != null ? addTime.hashCode() : 0);
        result = 31 * result + (modTime != null ? modTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "WarehouseStock{" +
                "warehouseStockId=" + warehouseStockId +
                ", goodsId=" + goodsId +
                ", sku='" + sku + '\'' +
                ", stockNum=" + stockNum +
                ", occupyNum=" + occupyNum +
                ", availableStockNum=" + availableStockNum +
                ", isDelete=" + isDelete +
                ", actionOccupyNum=" + actionOccupyNum +
                ", actionLockNum=" + actionLockNum +
                ", actionId=" + actionId +
                ", addTime=" + addTime +
                ", modTime=" + modTime +
                '}';
    }
}