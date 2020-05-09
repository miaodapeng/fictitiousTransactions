package com.smallhospital.service;

import com.smallhospital.dto.*;
import com.vedeng.logistics.model.Express;
import com.vedeng.order.model.Saleorder;

/**
 * @Author: Daniel
 * @Description:
 * @Date created in 2019/11/20 6:41 下午
 */
public interface ElSaleOrderService {


    /**
     * 查询物流详情
     * @param logisticsNo 订单号
     * @param hospitalId 医疗机构id
     * @return 物流详情
     */
    LogisticsDTO queryLogisticsInfo(String logisticsNo, Integer hospitalId) throws Exception;

    /**
     * 给医流网发送出库信息
     * @param express 物流信息
     * @param saleorder 订单信息
     * @return 操作结果
     */
    boolean sendElWareHouseInfo(Express express, Saleorder saleorder) throws Exception;


    void confirmElSaleOrder(ConfirmElSaleOrderDTO dto) throws Exception;

    /**
     * 终止订单
     * @param terminateDTO 订单
     * @return
     */
    ElResultDTO terminateSaleOrder(ElSaleOrderTerminateDTO terminateDTO) throws Exception;

    void afterSaleOrder(ElAfterSaleOrderDTO elAfterSaleOrderDTO) throws Exception;


}
