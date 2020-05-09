package com.newtask.service;


import com.newtask.model.ReportStatistitcsSku;

import java.util.List;
import java.util.Map;

/**
 * @Description: 报表数据拉取及清洗---供应链备货报表---订单统计方法接口
 * @Author Cooper.xu
 * @PackageName: com.task.service
 * @ClassName: ReportStatistitcsService
 * @Date: 2020年01月14日
 */
public interface ReportStatistitcsService {

    /**
     * @Description: 清空T_REPORT_STATISTITCS_SKU表中数据，使用truncate方式，提高效率
     * @param
     * @Author: Cooper.xu
     * @Date: 2020年01月16日
     * @return
     */
    Integer trunCateStatistitcsSku();

    /**
     * @Description: 获取所有状态为未关闭的，部分付款或全部付款的订单关联的商品的总数
     * @param map
     * @Author: Cooper.xu
     * @Date: 2020年01月16日
     * @return
     */
    Integer getStatistitcsSkuTotalCount(Map<String, Object> map);

    /**
     * @Description: 获取所有状态为未关闭的，部分付款或全部付款的订单关联的去重后的商品列表
     * @param map
     * @Author: Cooper.xu
     * @Date: 2020年01月16日
     * @return
     */
    List<ReportStatistitcsSku> getReportStatistitcsSkuList(Map<String, Object> map) throws Exception;

    /**
     * @Description: 批量插入
     * @Author: Cooper.xu
     * @Date: 2020年01月16日
     * @return
     */
    Integer insertBatch(List<ReportStatistitcsSku> list) throws Exception;
}
