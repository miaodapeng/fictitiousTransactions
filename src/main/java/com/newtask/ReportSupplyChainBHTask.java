package com.newtask;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.newtask.model.ReportNotSellSku;
import com.newtask.model.ReportOrderSku;
import com.newtask.model.ReportStatistitcsSku;
import com.newtask.model.ReportViewGylSku;
import com.newtask.service.ReportNotSellSkuService;
import com.newtask.service.ReportOrderSkuService;
import com.newtask.service.ReportStatistitcsService;
import com.newtask.service.ReportViewGylSkuService;
import com.vedeng.common.constant.CommonConstants;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Description: 报表数据拉取及清洗---供应链备货报表
 * @Author Cooper.xu
 * @PackageName: com.task
 * @ClassName: ReportSupplyChainBHTask
 * @Date: 2020年01月14日
 */
@JobHandler(value = "reportSupplyChainBHTask")
@Component
public class ReportSupplyChainBHTask extends IJobHandler {

    public static Logger logger = LoggerFactory.getLogger(ReportSupplyChainBHTask.class);

    @Autowired
    private ReportOrderSkuService reportOrderSkuService;

    @Autowired
    private ReportStatistitcsService reportStatistitcsService;

    @Autowired
    private ReportNotSellSkuService reportNotSellSkuService;

    @Autowired
    private ReportViewGylSkuService reportViewGylSkuService;

    @Value("${report_sku_batch_num}")
    private Integer orderSkuBatchNum;

    @Value("${report_statistitcs_batch_num}")
    private Integer orderStatistitcsBatchNum;

    @Value("${not_sell_trader_ids}")
    private String notSellTraderIds;
    /**
     * @Description: 组装参数
     * @Author: Cooper.xu
     * @Date: 2020年01月14日
     */
    public void packageParam(Map<String, Object> map){
        //订单状态 3-已关闭
        map.put("status", CommonConstants.SALEORDER_TYPE_THREE);
        //订单流水交易类型  1-收入 | 3-转移 | 4-转入
        List<Integer> traderTypeList = Lists.newArrayList();
        traderTypeList.add(CommonConstants.ONE);
        traderTypeList.add(CommonConstants.SALEORDER_TYPE_FOUR);
        traderTypeList.add(CommonConstants.SALEORDER_TYPE_THREE);
        map.put("traderTypeList", traderTypeList);
        //订单流水交易方式  529 - 退还信用 | 530 - 退还余额
        List<Integer> traderModeList = Lists.newArrayList();
        traderModeList.add(CommonConstants.CAPITALBILL_TRADER_MODE_529);
        traderModeList.add(CommonConstants.CAPITALBILL_TRADER_MODE_530);
        map.put("traderModeList", traderModeList);
        List<String> traderIdList = Arrays.asList(notSellTraderIds.split(","));
        map.put("traderIdList",traderIdList);
    }

    /**
     * @Description: 处理订单中商品的信息
     * @Author: Cooper.xu
     * @Date: 2020年01月14日
     */
    public void doOrderSkuData(){
        try{
            //清空T_REPORT_ORDER_SKU表中数据
            reportOrderSkuService.trunCateOrderSku();
            //查询参数组装
            Map<String, Object> map = Maps.newHashMap();
            this.packageParam(map);
            logger.info("reportSupplyChainBHTask +++++++ doOrderSkuData ++++++ paramMap:{}", JSONObject.toJSONString(map));
            //查询截止到当前时间的所有订单关联的商品去重后数量
            Integer totalSkuCount = reportOrderSkuService.getDistinctSkuTotalCount(map);
            logger.info("reportSupplyChainBHTask +++++++ doOrderSkuData ++++++ totalSkuCount:{}", totalSkuCount);
            //按批次循环查询符合条件的订单列表
            Integer totalBatchCount = totalSkuCount % orderSkuBatchNum == 0 ? totalSkuCount/orderSkuBatchNum : totalSkuCount/orderSkuBatchNum + 1;
            logger.info("reportSupplyChainBHTask +++++++ doOrderSkuData ++++++ totalBatchCount:{}", totalBatchCount);
            Integer thisBatch = 0;
            do {
                //计算查询开始和结束
                Integer beginCount = orderSkuBatchNum * thisBatch;
                Integer endCount = orderSkuBatchNum;
                //查询商品
                map.put("beginCount",beginCount);
                map.put("endCount",endCount);
                List<ReportOrderSku> reportOrderSkuList = reportOrderSkuService.getReportOrderSkuList(map);
                //持久化数据
                if (CollectionUtils.isNotEmpty(reportOrderSkuList)){
                    reportOrderSkuService.insertBatch(reportOrderSkuList);
                }
                thisBatch ++;
                logger.info("reportSupplyChainBHTask +++++++ doOrderSkuData ++++++ thisBatch: {} +++++", thisBatch);
            }while (thisBatch <= totalBatchCount);
        }catch (Exception e){
            logger.error("reportSupplyChainBHTask ++++ doOrderSkuData +++ error: ", e);
        }
    }

    /**
     * @Description: 处理非销售订单中商品的统计信息
     * @Author: Cooper.xu
     * @Date: 2020年01月16日
     */
    public void doNotSellSkuData(){
        try{
            //清空T_REPORT_NOTSELL_SKU表中所有数据
            reportNotSellSkuService.trunCateNotSellSku();
            //查询参数组装
            Map<String, Object> map = Maps.newHashMap();
            map.put("orderStatus", CommonConstants.SALEORDER_TYPE_THREE);//已关闭
            map.put("payStatus",CommonConstants.ZERO);//未付款
            List<String> traderIdList = Arrays.asList(notSellTraderIds.split(","));
            map.put("traderIdList",traderIdList);
            logger.info("reportSupplyChainBHTask +++++++ doNotSellSkuData ++++++ paramMap:{}", JSONObject.toJSONString(map));
            //查询截止到当前时间的所有非销售订单关联的商品数量,不去重
            Integer totalSkuCount = reportNotSellSkuService.getNotSellSkuTotalCount(map);
            logger.info("reportSupplyChainBHTask +++++++ doNotSellSkuData ++++++ totalSkuCount:{}", totalSkuCount);
            //按批次循环查询符合条件的订单列表
            Integer totalBatchCount = totalSkuCount % orderStatistitcsBatchNum == 0 ? totalSkuCount/orderStatistitcsBatchNum : totalSkuCount/orderStatistitcsBatchNum + 1;
            logger.info("reportSupplyChainBHTask +++++++ doNotSellSkuData ++++++ totalBatchCount:{}", totalBatchCount);
            Integer thisBatch = 0;
            do{
                //计算查询开始和结束
                Integer beginCount = orderStatistitcsBatchNum * thisBatch;
                Integer endCount = orderStatistitcsBatchNum;
                //查询商品
                map.put("begin",beginCount);
                map.put("end",endCount);
                List<ReportNotSellSku> reportNotSellSkuList = reportNotSellSkuService.getNotSellSkuList(map);
                //持久化数据
                if (CollectionUtils.isNotEmpty(reportNotSellSkuList)){
                    reportNotSellSkuService.insertBatch(reportNotSellSkuList);
                }
                thisBatch ++;
                logger.info("reportSupplyChainBHTask +++++++ doNotSellSkuData ++++++ thisBatch: {} +++++", thisBatch);
            }while (thisBatch <= totalBatchCount);

        }catch (Exception e){
            logger.error("reportSupplyChainBHTask ++++ doNotSellSkuData +++ error: ", e);
        }
    }

    /**
     * @Description: 处理外借订单的商品信息
     * @Author: Cooper.xu
     * @Date: 2020年01月16日
     */
    public void doLendOutSkuData(){
        try{
            //查询参数组装
            Map<String, Object> map = Maps.newHashMap();
            map.put("lendOutStatus", CommonConstants.SALEORDER_TYPE_TWO);//已关闭
            map.put("lendOutType",CommonConstants.ONE);//样品外借类型
            logger.info("reportSupplyChainBHTask +++++++ doLendOutSkuData ++++++ paramMap:{}", JSONObject.toJSONString(map));
            //查询截止到当前时间的所有外界订单关联的商品数量,不去重
            Integer totalSkuCount = reportNotSellSkuService.getLendOutSkuCount(map);
            logger.info("reportSupplyChainBHTask +++++++ doLendOutSkuData ++++++ totalSkuCount:{}", totalSkuCount);
            //按批次循环查询符合条件的订单列表
            Integer totalBatchCount = totalSkuCount % orderStatistitcsBatchNum == 0 ? totalSkuCount/orderStatistitcsBatchNum : totalSkuCount/orderStatistitcsBatchNum + 1;
            logger.info("reportSupplyChainBHTask +++++++ doLendOutSkuData ++++++ totalBatchCount:{}", totalBatchCount);
            Integer thisBatch = 0;
            do{
                //计算查询开始和结束
                Integer beginCount = orderStatistitcsBatchNum * thisBatch;
                Integer endCount = orderStatistitcsBatchNum;
                //查询商品
                map.put("begin",beginCount);
                map.put("end",endCount);
                List<ReportNotSellSku> reportLendOutSkuList = reportNotSellSkuService.getLendOutSkuList(map);
                //持久化数据
                if (CollectionUtils.isNotEmpty(reportLendOutSkuList)){
                    reportNotSellSkuService.insertBatch(reportLendOutSkuList);
                }
                thisBatch ++;
                logger.info("reportSupplyChainBHTask +++++++ doLendOutSkuData ++++++ thisBatch: {} +++++", thisBatch);
            }while (thisBatch <= totalBatchCount);
        }catch (Exception e){
            logger.error("reportSupplyChainBHTask ++++ doLendOutSkuData +++ error: ", e);
        }
    }

    /**
     * @Description: 处理销售订单中商品的统计信息
     * @Author: Cooper.xu
     * @Date: 2020年01月16日
     */
    public void doStatistitcsSkuData(){
        try{
            //清空T_REPORT_STATISTITCS_SKU表中所有数据
            reportStatistitcsService.trunCateStatistitcsSku();
            //查询参数组装
            Map<String, Object> map = Maps.newHashMap();
            this.packageParam(map);
            logger.info("reportSupplyChainBHTask +++++++ doStatistitcsSkuData ++++++ paramMap:{}", JSONObject.toJSONString(map));
            //查询截止到当前时间的所有订单关联的商品数量,不去重
            Integer totalSkuCount = reportStatistitcsService.getStatistitcsSkuTotalCount(map);
            logger.info("reportSupplyChainBHTask +++++++ doStatistitcsSkuData ++++++ totalSkuCount:{}", totalSkuCount);
            //按批次循环查询符合条件的订单列表
            Integer totalBatchCount = totalSkuCount % orderStatistitcsBatchNum == 0 ? totalSkuCount/orderStatistitcsBatchNum : totalSkuCount/orderStatistitcsBatchNum + 1;
            logger.info("reportSupplyChainBHTask +++++++ doStatistitcsSkuData ++++++ totalBatchCount:{}", totalBatchCount);
            Integer thisBatch = 0;
            do{
                //计算查询开始和结束
                Integer beginCount = orderStatistitcsBatchNum * thisBatch;
                Integer endCount = orderStatistitcsBatchNum;
                //查询商品
                map.put("begin",beginCount);
                map.put("end",endCount);
                List<ReportStatistitcsSku> reportStatistitcsSkuList = reportStatistitcsService.getReportStatistitcsSkuList(map);
                //持久化数据
                if (CollectionUtils.isNotEmpty(reportStatistitcsSkuList)){
                    reportStatistitcsService.insertBatch(reportStatistitcsSkuList);
                }
                thisBatch ++;
                logger.info("reportSupplyChainBHTask +++++++ doStatistitcsSkuData ++++++ thisBatch: {} +++++", thisBatch);
            }while (thisBatch <= totalBatchCount);
        }catch (Exception e){
            logger.error("reportSupplyChainBHTask ++++ doStatistitcsSkuData +++ error: ", e);
        }
    }

    /**
     * @Description: 处理最终表信息
     * @Author: Cooper.xu
     * @Date: 2020年01月16日
     */
    public void doViewGylSkuData(){
        long startDoOrderSkuData = System.currentTimeMillis();
        doOrderSkuData();
        logger.info("reportSupplyChainBHTask performance doOrderSkuData cost {} ", (System.currentTimeMillis() - startDoOrderSkuData));

        long startDoNotSellSkuData = System.currentTimeMillis();
        doNotSellSkuData();
        logger.info("reportSupplyChainBHTask performance doNotSellSkuData cost {} ", (System.currentTimeMillis() - startDoNotSellSkuData));

        long startDoLendOutSkuData = System.currentTimeMillis();
        doLendOutSkuData();
        logger.info("reportSupplyChainBHTask performance doLendOutSkuData cost {} ", (System.currentTimeMillis() - startDoLendOutSkuData));

        long startDoStatistitcsSkuData = System.currentTimeMillis();
        doStatistitcsSkuData();
        logger.info("reportSupplyChainBHTask performance doStatistitcsSkuData cost {} ", (System.currentTimeMillis() - startDoStatistitcsSkuData));

        //插入数据
        long result = System.currentTimeMillis();
        reportViewGylSkuService.trunCateViewGylSku();
        reportViewGylSkuService.insertBatch();
        //插入未销售数量中不在已销售的表中的数据
        Integer num = reportViewGylSkuService.insertNotInReportStatistitcsSkuList();
        logger.info("reportSupplyChainBHTask performance last step cost {} ", (System.currentTimeMillis() - result));

        if (num >= 0){
            //清空T_REPORT_STATISTITCS_SKU表中所有数据
            reportStatistitcsService.trunCateStatistitcsSku();
            //清空T_REPORT_ORDER_SKU表中数据
            reportOrderSkuService.trunCateOrderSku();
        }
    }

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        doViewGylSkuData();
        return SUCCESS;
    }
}
