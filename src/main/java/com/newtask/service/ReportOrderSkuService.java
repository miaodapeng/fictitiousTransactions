package com.newtask.service;


import com.newtask.model.ReportOrderSku;

import java.util.List;
import java.util.Map;

/**
 * @Description: 报表数据拉取及清洗---供应链备货报表---订单商品方法接口
 * @Author Cooper.xu
 * @PackageName: com.task.service
 * @ClassName: ReportOrderSkuService
 * @Date: 2020年01月14日
 */
public interface ReportOrderSkuService {

    /**
     * @Description: 清空T_REPORT_ORDER_SKU表中数据，使用truncate方式，提高效率
     * @param
     * @Author: Cooper.xu
     * @Date: 2020年01月14日
     * @return
     */
    Integer trunCateOrderSku();

    /**
     * @Description: 获取所有状态为未关闭的，部分付款或全部付款的订单关联的商品去重后的总数
     * @param map
     * @Author: Cooper.xu
     * @Date: 2020年01月14日
     * @return
     */
    Integer getDistinctSkuTotalCount(Map<String, Object> map);

    /**
     * @Description: 获取所有状态为未关闭的，部分付款或全部付款的订单关联的去重后的商品列表
     * @param map
     * @Author: Cooper.xu
     * @Date: 2020年01月14日
     * @return
     */
    List<ReportOrderSku> getReportOrderSkuList(Map<String, Object> map) throws Exception;

    /**
     * @Description: 批量插入
     * @Author: Cooper.xu
     * @Date: 2020年01月15日
     * @return
     */
    Integer insertBatch(List<ReportOrderSku> list) throws Exception;
}
