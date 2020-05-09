package com.vedeng.reportSaleorder.dao;


import com.vedeng.finance.model.vo.CapitalBillDetailVo;
import com.vedeng.order.model.SaleorderGoods;
import com.vedeng.reportSaleorder.model.ReportSaleorder;
import com.vedeng.reportSaleorder.model.vo.ReportSaleorderVo;
import org.apache.ibatis.annotations.Param;

import javax.inject.Named;
import java.util.List;
import java.util.Map;

@Named("reportSaleorderMapper")
public interface ReportSaleorderMapper {
    int deleteByPrimaryKey(Integer saleorderId);

    int insert(ReportSaleorder record);

    int insertSelective(ReportSaleorder record);

    ReportSaleorder selectByPrimaryKey(Integer saleorderId);

    int updateByPrimaryKeySelective(ReportSaleorder record);

    int updateByPrimaryKey(ReportSaleorder record);

    //获取表中数据数量
    Integer getOrderNum();

    //获取订单数据
    List<ReportSaleorderVo> getSaleorderList(Map<String, Object> map);

    //删除当月基础数据
    Integer deleteReportSaleorderListThisMon(String saleorderYM);

    //批量插入订单数据
    Integer insertBatch(@Param("saleorderList") List<ReportSaleorderVo> saleorderList);

    //查询订单创建人所属部门列表
    List<ReportSaleorderVo> getSaleorderCreatorOrgList(@Param("saleorderList") List<ReportSaleorderVo> saleorderList);

    //查询订单所属人所属部门列表
    List<ReportSaleorderVo> getSaleorderValidOrgList(@Param("saleorderList") List<ReportSaleorderVo> saleorderList);

    //查询订单客户名称以及客户注册地信息
    List<ReportSaleorderVo> getSaleorderCustomerInfo(@Param("saleorderList") List<ReportSaleorderVo> saleorderList);

    //查询订单销售区域等信息
    List<ReportSaleorderVo> getSaleorderSaleAreaInfo(@Param("saleorderList") List<ReportSaleorderVo> saleorderList);

    //查询订单客户性质等信息
    List<ReportSaleorderVo> getSaleorderCustomerNatureInfo(@Param("saleorderList") List<ReportSaleorderVo> saleorderList);

    //获取订单的有效采购商品金额
    List<SaleorderGoods> getSaleorderBuyAmount(@Param("saleorderList") List<ReportSaleorderVo> saleorderList);

    //获取获取订单的有效退款金额
    List<CapitalBillDetailVo> getSaleorderRefundAmount(@Param("saleorderList") List<ReportSaleorderVo> saleorderList);

    //查询订单的付款时间
    List<ReportSaleorderVo> getSaleorderFirstPayTimeList(@Param("saleorderList") List<ReportSaleorderVo> saleorderList);
}