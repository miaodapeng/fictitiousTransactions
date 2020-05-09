package com.vedeng.logistics.dao;

import com.vedeng.logistics.model.WarehouseStock;

import javax.inject.Named;
import java.util.List;

@Named("warehouseStockMapper")
public interface WarehouseStockMapper {
//    int deleteByPrimaryKey(Integer warehouseStockId);
//
//    int insert(WarehouseStock record);
//
//    int insertSelective(WarehouseStock record);
//
//    WarehouseStock selectByPrimaryKey(Integer warehouseStockId);
//
//    int updateByPrimaryKeySelective(WarehouseStock record);
//
//    int updateByPrimaryKey(WarehouseStock record);
    /**
    *获取订单占用数量(不包括售后换货)
    * @Author:strange
    * @Date:13:27 2019-11-08
    */
    List<WarehouseStock> getOrderOccupyNum(Integer saleorderId);
    /**
    *获取hc订单占用数量(不包括售后换货)
    * @Author:strange
    * @Date:09:46 2019-11-11
    */
    List<WarehouseStock> getHCorderOccupyNum(Integer saleorderId);
    /**
    *换货占用数量
    * @Author:strange
    * @Date:09:56 2019-11-11
    */
    List<WarehouseStock> getAfterOccupyNum(Integer saleorderId);
    /**
    *查询sku占用数量(不包括换货单,hc单)
    * @Author:strange
    * @Date:13:40 2019-11-11
    */
    List<WarehouseStock> getOccupyNumBySku(List<String> sku);
    /**
    *此sku耗材单占用
    * @Author:strange
    * @Date:14:02 2019-11-11
    */
    List<WarehouseStock> getHcOccupyBySku(List<String> sku);
    /**
    * 获取该sku在售后换货的占用数量
    * @Author:strange
    * @Date:14:12 2019-11-11
    */
    List<WarehouseStock> getAfterOccupyNumBySku(List<String> sku);
    /**
    *获取sku占用总量
    * @Author:strange
    * @Date:23:00 2019-11-19
    */
    List<WarehouseStock> getOrderOccupyNumBySku(List<String> sku);
    /**
    *获取订单活动占用数量
    * @Author:strange
    * @Date:13:31 2019-12-05
    */
    List<WarehouseStock> getActionOccupyNum(Integer saleorderId);
}