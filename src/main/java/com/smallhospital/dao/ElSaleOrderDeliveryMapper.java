package com.smallhospital.dao;

import com.smallhospital.model.ElSaleOrderDelivery;
import com.vedeng.logistics.model.ExpressDetail;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.SaleorderGoods;

import javax.inject.Named;
import java.util.List;

@Named
public interface ElSaleOrderDeliveryMapper {

    void insert(ElSaleOrderDelivery delivery);

    Integer getSaleOrderIdByExpressId(Integer expressId);

    SaleorderGoods getSaleGoodsByExpressDetailId(Integer detailId);

    List<SaleorderGoods> getSaleorderGoodsByExpressDetailId(List<Integer> detailList);

    List<SaleorderGoods> getSaleOrderGoodsBySaleOrderId(Integer supplyId);

    void updateSaleorderArrivalStatus(Saleorder saleorder);

    void updateSaleOrderGoodsArrivalStatus(SaleorderGoods goods);

    void insertBatch(List<ElSaleOrderDelivery> list);

    List<ExpressDetail> getSaleOrderGoodsIdByExpressDetailId(List<Integer> expressDetailIdList);

    SaleorderGoods getSaleOrderGoodsByExpressDetailId(Integer expressDetailId);

    List<ElSaleOrderDelivery> getElSaleorderDeliveryByExpressId(Integer expressId);
}

