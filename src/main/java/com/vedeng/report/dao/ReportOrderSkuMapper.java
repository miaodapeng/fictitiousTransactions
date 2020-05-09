package com.vedeng.report.dao;


import com.newtask.model.ReportOrderSku;
import com.vedeng.goods.model.Unit;

import java.util.List;
import java.util.Map;

/**
 * @Description: 报表数据拉取及清洗---供应链备货报表---订单商品方法 mapper
 * @Author Cooper.xu
 * @PackageName: com.task.dao
 * @ClassName: ReportOrderSkuMapper
 * @Date: 2020年01月14日
 */
public interface ReportOrderSkuMapper {

    int deleteByPrimaryKey(String orderSkuId);

    int insert(ReportOrderSku record);

    int insertSelective(ReportOrderSku record);

    ReportOrderSku selectByPrimaryKey(String orderSkuId);

    int updateByPrimaryKeySelective(ReportOrderSku record);

    int updateByPrimaryKey(ReportOrderSku record);

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
    List<ReportOrderSku> getReportOrderSkuList(Map<String, Object> map);

    /**
     * @Description: 获取商品的其他信息，如：品牌等
     * @param list
     * @Author: Cooper.xu
     * @Date: 2020年01月15日
     * @return
     */
    List<ReportOrderSku> getReportOrderSkuOtherInfoList(List<ReportOrderSku> list);

    /**
     * @Description: 查询单位列表
     * @Author: Cooper.xu
     * @Date: 2020年01月15日
     * @return
     */
    List<Unit> getUnitList();

    /**
     * @Description: 查询一二三级分类信息列表
     * @Author: Cooper.xu
     * @Date: 2020年01月15日
     * @return
     */
    List<ReportOrderSku> getReportOrderSkuCategoryInfoList(List<ReportOrderSku> list);

    /**
     * @Description: 获取采购在途数量信息列表
     * @Author: Cooper.xu
     * @Date: 2020年01月15日
     * @return
     */
    List<ReportOrderSku> getReportOrderSkuOnOrderNumList(List<ReportOrderSku> list);

    /**
     * @Description: 批量插入
     * @Author: Cooper.xu
     * @Date: 2020年01月15日
     * @return
     */
    Integer insertBatch(List<ReportOrderSku> list);
}