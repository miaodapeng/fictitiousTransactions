package com.vedeng.reportSaleorder.service.impl;

import com.vedeng.finance.model.vo.CapitalBillDetailVo;
import com.vedeng.order.model.SaleorderGoods;
import com.vedeng.reportSaleorder.dao.ReportCapitalMapper;
import com.vedeng.reportSaleorder.dao.ReportSaleorderMapper;
import com.vedeng.reportSaleorder.model.ReportCapital;
import com.vedeng.reportSaleorder.model.vo.ReportSaleorderVo;
import com.vedeng.reportSaleorder.service.ReportSaleorderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("reportSaleorderService")
public class ReportSaleorderServiceImpl implements ReportSaleorderService {

    @Autowired
    private ReportSaleorderMapper reportSaleorderMapper;

    @Autowired
    private ReportCapitalMapper reportCapitalMapper;

    @Override
    public Integer getOrderNum() {

        return reportSaleorderMapper.getOrderNum();
    }

    @Override
    public List<ReportSaleorderVo> getSaleorderList(Map<String,Object> map) {

        return reportSaleorderMapper.getSaleorderList(map);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Integer deleteReportSaleorderListThisMon(String saleorderYM) {

        return reportSaleorderMapper.deleteReportSaleorderListThisMon(saleorderYM);
    }

    @Override
    public List<ReportSaleorderVo> getSaleorderCreatorOrgList(List<ReportSaleorderVo> saleorderList) {

        return reportSaleorderMapper.getSaleorderCreatorOrgList(saleorderList);
    }

    @Override
    public List<ReportSaleorderVo> getSaleorderValidOrgList(List<ReportSaleorderVo> saleorderList) {

        return reportSaleorderMapper.getSaleorderValidOrgList(saleorderList);
    }

    @Override
    public List<ReportSaleorderVo> getSaleorderCustomerInfo(List<ReportSaleorderVo> saleorderList) {

        return reportSaleorderMapper.getSaleorderCustomerInfo(saleorderList);
    }

    @Override
    public List<ReportSaleorderVo> getSaleorderSaleAreaInfo(List<ReportSaleorderVo> saleorderList) {

        return reportSaleorderMapper.getSaleorderSaleAreaInfo(saleorderList);
    }

    @Override
    public List<ReportSaleorderVo> getSaleorderCustomerNatureInfo(List<ReportSaleorderVo> saleorderList) {

        return reportSaleorderMapper.getSaleorderCustomerNatureInfo(saleorderList);
    }

    @Override
    public List<SaleorderGoods> getSaleorderBuyAmount(List<ReportSaleorderVo> saleorderList) {

        return reportSaleorderMapper.getSaleorderBuyAmount(saleorderList);
    }

    @Override
    public List<CapitalBillDetailVo> getSaleorderRefundAmount(List<ReportSaleorderVo> saleorderList) {

        return reportSaleorderMapper.getSaleorderRefundAmount(saleorderList);
    }

    @Override
    public List<ReportSaleorderVo> getSaleorderFirstPayTimeList(List<ReportSaleorderVo> saleorderList) {

        return reportSaleorderMapper.getSaleorderFirstPayTimeList(saleorderList);
    }

    @Override
    public List<ReportCapital> getReportCapitalList(Map<String, Object> map) {

        return reportCapitalMapper.getReportCapitalList(map);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Integer deleteReportCapitalListThisMon(String saleorderYM) {

        return reportCapitalMapper.deleteReportCapitalListThisMon(saleorderYM);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Integer insertReportCapitalBatch(List<ReportCapital> reportCapitalList) {

        return reportCapitalMapper.insertReportCapitalBatch(reportCapitalList);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Integer insertBatch(List<ReportSaleorderVo> saleorderList) {

        return reportSaleorderMapper.insertBatch(saleorderList);
    }
}
