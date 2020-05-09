package com.newtask;

import com.vedeng.common.model.ResultInfo;
import com.vedeng.logistics.service.WarehouseStockService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *库存服务定时任务
 * @author strange
 * @date $
 */
@Component
@JobHandler(value="StockInfoTask")
public class StockServiceTask extends IJobHandler {
    private Logger logger = LoggerFactory.getLogger(StockServiceTask.class);
    @Autowired
    private WarehouseStockService warehouseStockService;

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        XxlJobLogger.log("XXL-JOB, Hello World.");
        updateStockInfo();
        return SUCCESS;
    }

    public ResultInfo updateStockInfo(){
        XxlJobLogger.log("StockServiceTask.updateStockInfo | begin ...............");
        logger.info("StockServiceTask.updateStockInfo | begin ...............");
        ResultInfo resultInfo = new ResultInfo();
        try {
            String re = warehouseStockService.insertNewStock();
            XxlJobLogger.log("StockServiceTask.updateStockInfo | result:{}",re);
            logger.info("StockServiceTask.updateStockInfo | result:{}",re);
            resultInfo.setMessage(re);
            resultInfo.setCode(0);
            return resultInfo;
        }catch (Exception e){
            XxlJobLogger.log("StockServiceTask.updateStockInfo error:{}",e);
            logger.error("StockServiceTask.updateStockInfo error:{}",e);
        }
        return resultInfo;
    }
}
