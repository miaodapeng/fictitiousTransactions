package com.newtask.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.newtask.ReportSupplyChainBHTask;
import com.newtask.model.ReportOrderSku;
import com.newtask.service.ReportOrderSkuService;
import com.vedeng.common.constant.CommonConstants;
import com.vedeng.common.http.HttpRestClientUtil;
import com.vedeng.common.http.HttpURLConstant;
import com.vedeng.common.util.DateUtil;
import com.vedeng.goods.api.RequestParameter;
import com.vedeng.goods.api.constants.GoodsApiConstants;
import com.vedeng.goods.api.vo.SkuVO;
import com.vedeng.goods.model.Unit;
import com.vedeng.report.dao.ReportOrderSkuMapper;
import com.vedeng.stock.api.stock.dto.*;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description: 报表数据拉取及清洗---供应链备货报表---订单商品方法接口实现类
 * @Author Cooper.xu
 * @PackageName: com.task.service.impl
 * @ClassName: ReportOrderSkuServiceImpl
 * @Date: 2020年01月14日
 */
@Service("reportOrderSkuService")
public class ReportOrderSkuServiceImpl implements ReportOrderSkuService {

    public static Logger logger = LoggerFactory.getLogger(ReportOrderSkuServiceImpl.class);

    @Autowired
    private ReportOrderSkuMapper reportOrderSkuMapper;

    @Value("${goods_Api_Url}")
    public String goodsApiUrl;

    @Value("${stock_url}")
    protected String stockUrl;

    @Override
    public Integer trunCateOrderSku() {
        return reportOrderSkuMapper.trunCateOrderSku();
    }

    @Override
    public Integer getDistinctSkuTotalCount(Map<String, Object> map) {
        return reportOrderSkuMapper.getDistinctSkuTotalCount(map);
    }

    @Override
    public List<ReportOrderSku> getReportOrderSkuList(Map<String, Object> map) {
        //查询商品SKU订货号
        List<ReportOrderSku> reportOrderSkuList = reportOrderSkuMapper.getReportOrderSkuList(map);
        if (CollectionUtils.isNotEmpty(reportOrderSkuList)){
            //去掉库中已经存在的商品数据

            //查询商品其他信息
            reportOrderSkuList = reportOrderSkuMapper.getReportOrderSkuOtherInfoList(reportOrderSkuList);
            //查询单位信息
            List<Unit> unitList =  reportOrderSkuMapper.getUnitList();
            Map<Integer, String> unitMap = unitList.stream().collect(Collectors.toMap(Unit :: getUnitId, Unit::getUnitName));
            //查询分类信息并组装
            List<ReportOrderSku> categoryOrderSKuList = reportOrderSkuMapper.getReportOrderSkuCategoryInfoList(reportOrderSkuList);
            Map<Integer, Object> categoryOrderSKuMap = categoryOrderSKuList.stream().collect(Collectors.toMap(ReportOrderSku :: getThirdCatehgoryId, reportOrderSku -> reportOrderSku));
            List<Integer> skuIdList = Lists.newArrayList();
            List<String> skuNoList = Lists.newArrayList();
            reportOrderSkuList.forEach(reportOrderSku -> {
                reportOrderSku.setOrderSkuId(reportOrderSku.getSkuNo().substring(CommonConstants.ONE));
                skuNoList.add(reportOrderSku.getSkuNo());
                skuIdList.add(Integer.valueOf(reportOrderSku.getSkuNo().substring(CommonConstants.ONE)));
                if (categoryOrderSKuMap.get(reportOrderSku.getThirdCatehgoryId()) != null){
                    ReportOrderSku orderSku = (ReportOrderSku) categoryOrderSKuMap.get(reportOrderSku.getThirdCatehgoryId());
                    reportOrderSku.setThirdCatehgoryName(orderSku.getThirdCatehgoryName());
                    reportOrderSku.setSecondCategoryId(orderSku.getSecondCategoryId());
                    reportOrderSku.setSecondCategoryName(orderSku.getSecondCategoryName());
                    reportOrderSku.setTopCategoryId(orderSku.getTopCategoryId());
                    reportOrderSku.setTopCategoryName(orderSku.getTopCategoryName());
                }
                if (unitMap.get(reportOrderSku.getUnitId()) != null && unitMap.get(reportOrderSku.getBaseUnitId()) != null){
                    reportOrderSku.setUnitName(unitMap.get(reportOrderSku.getUnitId()));
                    reportOrderSku.setBaseUnitName(unitMap.get(reportOrderSku.getBaseUnitId()));
                }
            });
            Map<String, Object> skuVoMap = Maps.newHashMap();
            Map<String, Object> stockInfoMap = Maps.newHashMap();
            Map<String, Object> onOrderNumMap = Maps.newHashMap();
            //查询成本价，贝登没有成本价，只有推送到医械购的商品有
            TypeReference<List<SkuVO>> goodsType = new TypeReference<List<SkuVO>>(){};
            RequestParameter requestParameter = new RequestParameter();
            //医械购平台
            requestParameter.setPlatfromId(GoodsApiConstants.GO_PLATFORM);
            requestParameter.setSkuIdList(skuIdList);
            requestParameter.setSortType(CommonConstants.ZERO);//不排序
            logger.info("requestParameter:{}", JSONObject.toJSONString(requestParameter));
            List<SkuVO> skuVOList = HttpRestClientUtil.restPost(goodsApiUrl + HttpURLConstant.GOODS_QUERY_SKU_LIST_SORT_URL, goodsType, new HashMap<>(), requestParameter);
            logger.info("skuVOList:{}", JSONObject.toJSONString(skuVOList));
            if (CollectionUtils.isNotEmpty(skuVOList)){
                skuVOList.forEach(skuVO -> {
                    if (skuVO.getSkuNature() != null){
                        skuVoMap.put(skuVO.getSkuNature().getSkuNo(), skuVO);
                    }
                });
            }
            logger.info("skuVoMap:{}", JSONObject.toJSONString(skuVoMap));
            //查询库存的信息
            TypeReference<StockInfoDto> stockType = new TypeReference<StockInfoDto>(){};
            StockInfoDto stockInfoDto = HttpRestClientUtil.restPost(stockUrl + HttpURLConstant.STOCK_GET_STOCK_INFO_URL, stockType, null, skuNoList);
            if (stockInfoDto != null && stockInfoDto.getWarehouseStockList() != null){
                stockInfoDto.getWarehouseStockList().forEach(warehouseDto -> stockInfoMap.put(warehouseDto.getSku(), warehouseDto));
            }
            //计算采购在途数量
            List<ReportOrderSku> onOrderNumList = reportOrderSkuMapper.getReportOrderSkuOnOrderNumList(reportOrderSkuList);
            if (CollectionUtils.isNotEmpty(onOrderNumList)){
                onOrderNumList.forEach(orderSku -> onOrderNumMap.put(orderSku.getSkuNo(), orderSku));
            }
            reportOrderSkuList.forEach(reportOrderSku -> {
                reportOrderSku.setIsOnSale(CommonConstants.ZERO);
                reportOrderSku.setPurchaseOnOrderNum(CommonConstants.ZERO);
                if (skuVoMap.get(reportOrderSku.getSkuNo()) != null){
                    SkuVO skuVO = (SkuVO) skuVoMap.get(reportOrderSku.getSkuNo());
                    reportOrderSku.setCostPrice(skuVO.getSkuNature() == null ? "" : skuVO.getSkuNature().getCostPrice().toString());
                    reportOrderSku.setIsOnSale(skuVO.getSkuNature() == null ? CommonConstants.ZERO : skuVO.getSkuNature().getIsOnSale() == null ? CommonConstants.ZERO : skuVO.getSkuNature().getIsOnSale());
                }
                if (stockInfoMap.get(reportOrderSku.getSkuNo()) != null){
                    WarehouseDto warehouseDto = (WarehouseDto) stockInfoMap.get(reportOrderSku.getSkuNo());
                    reportOrderSku.setStockNum(warehouseDto.getStockNum());
                    reportOrderSku.setOccupyNum(warehouseDto.getOccupyNum());
                    reportOrderSku.setActionLockCount(warehouseDto.getActionLockNum());
                    reportOrderSku.setActionOccupyCount(warehouseDto.getActionOccupyNum());
                }
                if (onOrderNumMap.get(reportOrderSku.getSkuNo()) != null){
                    ReportOrderSku orderSku = (ReportOrderSku) onOrderNumMap.get(reportOrderSku.getSkuNo());
                    reportOrderSku.setPurchaseOnOrderNum(orderSku.getPurchaseOnOrderNum());
                }
                reportOrderSku.setAddTime(DateUtil.DateToString(new Date(),DateUtil.DATE_FORMAT));
                reportOrderSku.setModTime(DateUtil.DateToString(new Date(),DateUtil.DATE_FORMAT));
            });
        }
        logger.info("reportOrderSkuList:{}",JSONObject.toJSONString(reportOrderSkuList));
        return reportOrderSkuList;
    }

    @Override
    public Integer insertBatch(List<ReportOrderSku> list) {
        return reportOrderSkuMapper.insertBatch(list);
    }
}
