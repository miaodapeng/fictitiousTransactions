package com.vedeng.report.dao;



import com.newtask.model.ReportNotSellSku;

import java.util.List;
import java.util.Map;

public interface ReportNotSellSkuMapper {
    int deleteByPrimaryKey(String notsellSkuId);

    int insert(ReportNotSellSku record);

    int insertSelective(ReportNotSellSku record);

    ReportNotSellSku selectByPrimaryKey(String notsellSkuId);

    int updateByPrimaryKeySelective(ReportNotSellSku record);

    int updateByPrimaryKey(ReportNotSellSku record);

    /**
     * @Description: 清空T_REPORT_NOTSELL_SKU表中数据，使用truncate方式，提高效率
     * @param
     * @Author: Cooper.xu
     * @Date: 2020年01月16日
     * @return
     */
    Integer trunCateNotSellSku();

    /**
     * @Description: 获取所有状态为未关闭的，部分付款或全部付款的非销售订单关联的商品的总数
     * @param map
     * @Author: Cooper.xu
     * @Date: 2020年01月16日
     * @return
     */
    Integer getNotSellSkuTotalCount(Map<String, Object> map);

    /**
     * @Description: 获取所有状态为未关闭的，部分付款或全部付款的非销售订单关联的商品的列表
     * @param map
     * @Author: Cooper.xu
     * @Date: 2020年01月16日
     * @return
     */
    List<ReportNotSellSku> getNotSellSkuList(Map<String, Object> map);

    /**
     * @Description: 获取订单的退货信息的列表
     * @param list
     * @Author: Cooper.xu
     * @Date: 2020年01月16日
     * @return
     */
    List<ReportNotSellSku> getPurchaseSkuList(List<ReportNotSellSku> list);

    /**
     * @Description: 批量插入
     * @Author: Cooper.xu
     * @Date: 2020年01月16日
     * @return
     */
    Integer insertBatch(List<ReportNotSellSku> list);

    /**
     * @Description: 获取所有状态为未关闭的，部分付款或全部付款的非销售订单关联的商品的列表
     * @param list
     * @Author: Cooper.xu
     * @Date: 2020年01月16日
     * @return
     */
    Integer updateNotSellNum(List<ReportNotSellSku> list);

    /**
     * @Description: 获取所有状态为未关闭的，部分付款或全部付款的非销售订单关联的商品的总数
     * @param map
     * @Author: Cooper.xu
     * @Date: 2020年01月16日
     * @return
     */
    Integer getLendOutSkuCount(Map<String, Object> map);

    /**
     * @Description: 获取所有外借订单关联的商品的列表
     * @param map
     * @Author: Cooper.xu
     * @Date: 2020年01月16日
     * @return
     */
    List<ReportNotSellSku> getLendOutSkuList(Map<String, Object> map);

    /**
     * @Description: 查询在非销售出库的表中的外借订单下的商品数据
     * @param list
     * @Author: Cooper.xu
     * @Date: 2020年01月16日
     * @return
     */
    List<ReportNotSellSku> getInSellLendOutSkuList(List<ReportNotSellSku> list);
}