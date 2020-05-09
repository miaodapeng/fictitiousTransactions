package com.newtask.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.newtask.model.ReportNotSellSku;
import com.newtask.service.ReportNotSellSkuService;
import com.vedeng.authorization.model.Organization;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.MD5;
import com.vedeng.report.dao.ReportNotSellSkuMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("reportNotSellSkuService")
public class ReportNotSellSkuServiceImpl implements ReportNotSellSkuService {

    @Autowired
    private ReportNotSellSkuMapper reportNotSellSkuMapper;
    @Override
    public Integer trunCateNotSellSku() {
        return reportNotSellSkuMapper.trunCateNotSellSku();
    }
    @Override
    public Integer getNotSellSkuTotalCount(Map<String, Object> map) {
        return reportNotSellSkuMapper.getNotSellSkuTotalCount(map);
    }

    @Override
    public List<ReportNotSellSku> getNotSellSkuList(Map<String, Object> map) throws Exception{
        List<ReportNotSellSku> reportNotSellSkuList = reportNotSellSkuMapper.getNotSellSkuList(map);
        if (CollectionUtils.isNotEmpty(reportNotSellSkuList)){
            Map<String, Object> purchaseSkuMap = Maps.newHashMap();
            //查询订单退货的数量
            List<ReportNotSellSku> purchaseSkuList = reportNotSellSkuMapper.getPurchaseSkuList(reportNotSellSkuList);
            if (CollectionUtils.isNotEmpty(purchaseSkuList)){
                purchaseSkuList.forEach(purchaseSku -> purchaseSkuMap.put(purchaseSku.getSkuNo() + "_" + purchaseSku.getOrgId() + "_" +purchaseSku.getSellorderAddTime(), purchaseSku));
            }
            reportNotSellSkuList.forEach(reportNotSellSku -> {
                reportNotSellSku.setNotsellSkuId(MD5.encode(UUID.randomUUID().toString()));
                String associationFiled = reportNotSellSku.getSkuNo() + "_" + reportNotSellSku.getOrgId() + "_" +reportNotSellSku.getSellorderAddTime();
                if (purchaseSkuMap.get(associationFiled) != null){
                    ReportNotSellSku purchaseSku = (ReportNotSellSku) purchaseSkuMap.get(associationFiled);
                    reportNotSellSku.setNotSellNum(reportNotSellSku.getNotSellNum() - purchaseSku.getNotSellNum());
                }
                reportNotSellSku.setAssociationFiled(associationFiled);
                reportNotSellSku.setAddTime(DateUtil.DateToString(new Date(),DateUtil.DATE_FORMAT));
                reportNotSellSku.setModTime(DateUtil.DateToString(new Date(),DateUtil.DATE_FORMAT));
            });
        }
        return reportNotSellSkuList;
    }

    @Override
    public Integer insertBatch(List<ReportNotSellSku> list) throws Exception {
        return reportNotSellSkuMapper.insertBatch(list);
    }

    @Override
    public Integer updateNotSellNum(List<ReportNotSellSku> list) {
        return reportNotSellSkuMapper.updateNotSellNum(list);
    }

    @Override
    public Integer getLendOutSkuCount(Map<String, Object> map) {
        return reportNotSellSkuMapper.getLendOutSkuCount(map);
    }

    @Override
    public List<ReportNotSellSku> getLendOutSkuList(Map<String, Object> map) throws Exception {
        List<ReportNotSellSku> reportNotInNotSellSkus = Lists.newArrayList();
        List<ReportNotSellSku> reportLendOutSkuList = reportNotSellSkuMapper.getLendOutSkuList(map);
        if (CollectionUtils.isNotEmpty(reportLendOutSkuList)){
            //将数据统计到非销售出库的表中,先更新存在同skuNo,orgId,orderAddTime,再插入不存在的一些数据
            reportNotSellSkuMapper.updateNotSellNum(reportLendOutSkuList);
            reportLendOutSkuList.forEach(reportNotSellSku -> {
                reportNotSellSku.setAssociationFiled(reportNotSellSku.getSkuNo() + "_" + reportNotSellSku.getOrgId() + "_" +reportNotSellSku.getSellorderAddTime());
            });
            //查询在非销售出库的表中的外借订单下的商品数据
            List<ReportNotSellSku> reportInNotSellSkus = reportNotSellSkuMapper.getInSellLendOutSkuList(reportLendOutSkuList);
            Map<String, Object> reportInNotSellSkuMap = Maps.newHashMap();
            if (CollectionUtils.isNotEmpty(reportInNotSellSkus)){
                reportInNotSellSkus.forEach(reportNotSellSku -> reportInNotSellSkuMap.put(reportNotSellSku.getSkuNo() + "_" + reportNotSellSku.getOrgId() + "_" +reportNotSellSku.getSellorderAddTime(),reportNotSellSku));
            }
            //将不在非销售商品销量表中的数据重新封装成列表
            reportLendOutSkuList.forEach(reportNotSellSku -> {
                reportNotSellSku.setNotsellSkuId(MD5.encode(UUID.randomUUID().toString()));
                String associationFiled = reportNotSellSku.getSkuNo() + "_" + reportNotSellSku.getOrgId() + "_" +reportNotSellSku.getSellorderAddTime();
                if (reportInNotSellSkuMap.get(associationFiled) == null){
                    reportNotSellSku.setAssociationFiled(associationFiled);
                    reportNotInNotSellSkus.add(reportNotSellSku);
                }
            });
        }
        return reportNotInNotSellSkus;
    }
}
