package com.newtask.service.impl;

import com.google.common.collect.Maps;
import com.newtask.model.ReportStatistitcsSku;
import com.newtask.service.ReportStatistitcsService;
import com.vedeng.authorization.model.Organization;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.MD5;
import com.vedeng.report.dao.ReportStatistitcsSkuMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("reportStatistitcsService")
public class ReportStatistitcsServiceImpl implements ReportStatistitcsService {

    @Autowired
    private ReportStatistitcsSkuMapper reportStatistitcsSkuMapper;

    @Override
    public Integer trunCateStatistitcsSku() {
        return reportStatistitcsSkuMapper.trunCateStatistitcsSku();
    }

    @Override
    public Integer getStatistitcsSkuTotalCount(Map<String, Object> map) {
        return reportStatistitcsSkuMapper.getStatistitcsSkuTotalCount(map);
    }

    @Override
    public List<ReportStatistitcsSku> getReportStatistitcsSkuList(Map<String, Object> map) throws Exception {
        //查询商品SKU订货号,除去部分客户的订单下面的商品，如：南京YP等
        List<ReportStatistitcsSku> reportStatistitcsSkuList = reportStatistitcsSkuMapper.getReportStatistitcsSkuList(map);
        if (CollectionUtils.isNotEmpty(reportStatistitcsSkuList)){
            Map<Integer, String> organizationMap = Maps.newHashMap();
            Map<String, Object> purchaseSkuMap = Maps.newHashMap();
            //查询订单归属部门名称
            List<Organization> organizationList = reportStatistitcsSkuMapper.getOrganizationByOrgIdList(reportStatistitcsSkuList);
            if (CollectionUtils.isNotEmpty(organizationList)){
                organizationList.forEach(organization -> organizationMap.put(organization.getOrgId(),organization.getOrgName()));
            }
            //查询订单退货的数量
            List<ReportStatistitcsSku> purchaseSkuList = reportStatistitcsSkuMapper.getPurchaseSkuList(reportStatistitcsSkuList);
            if (CollectionUtils.isNotEmpty(purchaseSkuList)){
                purchaseSkuList.forEach(purchaseSku -> purchaseSkuMap.put(purchaseSku.getSkuNo() + "_" + purchaseSku.getSellorderNo(), purchaseSku));
            }
            //组装参数
            reportStatistitcsSkuList.forEach(reportStatistitcsSku -> {
                if (organizationMap.get(reportStatistitcsSku.getOrgId()) != null){
                    reportStatistitcsSku.setOrgName(organizationMap.get(reportStatistitcsSku.getOrgId()));
                }
                if (purchaseSkuMap.get(reportStatistitcsSku.getSkuNo() + "_" + reportStatistitcsSku.getSellorderNo()) != null){
                    ReportStatistitcsSku purchaseSku = (ReportStatistitcsSku) purchaseSkuMap.get(reportStatistitcsSku.getSkuNo() + "_" + reportStatistitcsSku.getSellorderNo());
                    reportStatistitcsSku.setSellNum(reportStatistitcsSku.getSellNum() - purchaseSku.getSellNum());
                }
                reportStatistitcsSku.setStatistitcsSkuId(MD5.encode(UUID.randomUUID().toString()));
                reportStatistitcsSku.setAddTime(DateUtil.DateToString(new Date(),DateUtil.DATE_FORMAT));
                reportStatistitcsSku.setModTime(DateUtil.DateToString(new Date(),DateUtil.DATE_FORMAT));
            });
        }
        return reportStatistitcsSkuList;
    }

    @Override
    public Integer insertBatch(List<ReportStatistitcsSku> list) throws Exception {
        return reportStatistitcsSkuMapper.insertBatch(list);
    }


}
