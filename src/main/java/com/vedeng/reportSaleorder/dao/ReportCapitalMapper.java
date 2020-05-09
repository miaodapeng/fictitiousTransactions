package com.vedeng.reportSaleorder.dao;

import com.vedeng.reportSaleorder.model.ReportCapital;
import org.apache.ibatis.annotations.Param;

import javax.inject.Named;
import java.util.List;
import java.util.Map;

@Named("reportCapitalMapper")
public interface ReportCapitalMapper {
    int deleteByPrimaryKey(Integer capitalId);

    int insert(ReportCapital record);

    int insertSelective(ReportCapital record);

    ReportCapital selectByPrimaryKey(Integer capitalId);

    int updateByPrimaryKeySelective(ReportCapital record);

    int updateByPrimaryKey(ReportCapital record);

    //查询到款额列表
    List<ReportCapital> getReportCapitalList(Map<String, Object> map);

    //删除当月到款额基础数据
    Integer deleteReportCapitalListThisMon(String saleorderYM);

    //批量插入订单数据
    Integer insertReportCapitalBatch(@Param("reportCapitalList") List<ReportCapital> reportCapitalList);
}