package com.newtask;

import com.vedeng.common.model.ResultInfo;
import com.vedeng.logistics.service.WarehouseStockService;
import com.vedeng.order.dao.SaleorderMapper;
import com.vedeng.order.model.Saleorder;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Map;

/**
 *刷新库存服务占用数量
 * @author strange
 * @date $
 */
@Component
@JobHandler(value="StockOccupyServiceTask")
public class StockOccupyServiceTask extends IJobHandler {
    private Logger logger = LoggerFactory.getLogger(StockServiceTask.class);
    @Autowired
    private WarehouseStockService warehouseStockService;

    @Resource
    private SaleorderMapper saleorderMapper;
    @Override
    public ReturnT<String> execute(String param) throws Exception {
        XxlJobLogger.log("XXL-JOB, Hello World.");
        updateStockInfo();
        return SUCCESS;
    }

    public ResultInfo updateStockInfo(){
        XxlJobLogger.log("StockOccupyServiceTask.updateSaleorderOccupyNum | begin ...............");
        logger.info("StockOccupyServiceTask.updateSaleorderOccupyNum | begin ...............");
        ResultInfo resultInfo = new ResultInfo();
        Integer start = 0;
        try {
            Saleorder saleorder = new Saleorder();
            if (start == null){
                start = 0;
            }
            saleorder.setOrgId(start);
            int allCount = saleorderMapper.getSaleorderidByStatus();
            for (int i = start; i < allCount/1000+1; i++) {
                saleorder.setOrgId(i*1000);
                Map<String,Integer> map = warehouseStockService.updateSaleorderOccupyNum(saleorder,1);
                Map<String,Integer> actionOccupyNum = warehouseStockService.updateSaleorderActionOccupyNum(saleorder,1);
                XxlJobLogger.log("updateSaleorderOccupyNum 当前更新到行页数 :{}",i);
                logger.info("updateSaleorderOccupyNum 当前更新到行页数 :{}",i);
                if(!CollectionUtils.isEmpty(map)) {
                    resultInfo.setMessage("更新sku为:"+ map.keySet()+"值为:"+ map.values() + "数据");
                    resultInfo.setCode(0);
                }
            }
            return resultInfo;
        }catch (Exception e){
            XxlJobLogger.log("StockOccupyServiceTask.updateSaleorderOccupyNum error:{}",e);
            logger.error("StockOccupyServiceTask.updateSaleorderOccupyNum error:{}",e);
        }
        return resultInfo;
    }
}
