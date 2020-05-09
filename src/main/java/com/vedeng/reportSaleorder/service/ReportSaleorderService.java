package com.vedeng.reportSaleorder.service;

import com.vedeng.finance.model.vo.CapitalBillDetailVo;
import com.vedeng.order.model.SaleorderGoods;
import com.vedeng.reportSaleorder.model.ReportCapital;
import com.vedeng.reportSaleorder.model.vo.ReportSaleorderVo;

import java.util.List;
import java.util.Map;

public interface ReportSaleorderService {

    //获取表中数据数量
    Integer getOrderNum();

    //获取订单数据
    List<ReportSaleorderVo> getSaleorderList(Map<String, Object> map);

    //删除当月基础数据
    Integer deleteReportSaleorderListThisMon(String saleorderYM);

    //批量插入订单数据
    Integer insertBatch(List<ReportSaleorderVo> saleorderList);

    //查询订单创建人所属部门列表
    List<ReportSaleorderVo> getSaleorderCreatorOrgList(List<ReportSaleorderVo> saleorderList);

    //查询订单所属人所属部门列表
    List<ReportSaleorderVo> getSaleorderValidOrgList(List<ReportSaleorderVo> saleorderList);

    //查询订单客户名称以及客户注册地信息
    List<ReportSaleorderVo> getSaleorderCustomerInfo(List<ReportSaleorderVo> saleorderList);

    //查询订单销售区域等信息
    List<ReportSaleorderVo> getSaleorderSaleAreaInfo(List<ReportSaleorderVo> saleorderList);

    //查询订单客户性质等信息
    List<ReportSaleorderVo> getSaleorderCustomerNatureInfo(List<ReportSaleorderVo> saleorderList);

    //获取订单的有效采购商品金额
    List<SaleorderGoods> getSaleorderBuyAmount(List<ReportSaleorderVo> saleorderList);

    //获取获取订单的有效退款金额
    List<CapitalBillDetailVo> getSaleorderRefundAmount(List<ReportSaleorderVo> saleorderList);

    //查询订单的付款时间
    List<ReportSaleorderVo> getSaleorderFirstPayTimeList(List<ReportSaleorderVo> saleorderList);

    //查询到款额列表
    List<ReportCapital> getReportCapitalList(Map<String, Object> map);

    //删除当月到款额基础数据
    Integer deleteReportCapitalListThisMon(String saleorderYM);

    //批量插入订单数据
    Integer insertReportCapitalBatch(List<ReportCapital> reportCapitalList);
}
