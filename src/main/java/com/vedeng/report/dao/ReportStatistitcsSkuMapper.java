package com.vedeng.report.dao;


import com.newtask.model.ReportStatistitcsSku;
import com.vedeng.authorization.model.Organization;

import java.util.List;
import java.util.Map;

public interface ReportStatistitcsSkuMapper {

    int deleteByPrimaryKey(String statistitcsSkuId);

    int insert(ReportStatistitcsSku record);

    int insertSelective(ReportStatistitcsSku record);

    ReportStatistitcsSku selectByPrimaryKey(String statistitcsSkuId);

    int updateByPrimaryKeySelective(ReportStatistitcsSku record);

    int updateByPrimaryKey(ReportStatistitcsSku record);

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
     * @Description: 获取所有状态为未关闭的，部分付款或全部付款的订单关联的商品列表
     * @param map
     * @Author: Cooper.xu
     * @Date: 2020年01月16日
     * @return
     */
    List<ReportStatistitcsSku> getReportStatistitcsSkuList(Map<String, Object> map);

    /**
     * @Description: 获取部门名称
     * @param list
     * @Author: Cooper.xu
     * @Date: 2020年01月16日
     * @return
     */
    List<Organization> getOrganizationByOrgIdList(List<ReportStatistitcsSku> list);

    /**
     * @Description: 获取订单的退货信息的列表
     * @param list
     * @Author: Cooper.xu
     * @Date: 2020年01月16日
     * @return
     */
    List<ReportStatistitcsSku> getPurchaseSkuList(List<ReportStatistitcsSku> list);

    /**
     * @Description: 批量插入
     * @Author: Cooper.xu
     * @Date: 2020年01月16日
     * @return
     */
    Integer insertBatch(List<ReportStatistitcsSku> list);
}