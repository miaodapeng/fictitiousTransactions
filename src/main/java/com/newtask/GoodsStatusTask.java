package com.newtask;

import com.vedeng.logistics.model.WarehouseGoodsStatus;
import com.vedeng.logistics.service.WarehousesService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 校准T_WAREHOUSE_GOODS_STATUS库存数据
 * @author strange
 * @date $
 */
@Component
@JobHandler(value="GoodsStatusTask")
public class GoodsStatusTask extends IJobHandler {
    private Logger logger = LoggerFactory.getLogger(GoodsStatusTask.class);

    @Autowired
    private WarehousesService warehousesService;

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        XxlJobLogger.log("XXL-JOB, Hello World.");
        updateGoodsStatusStockNum();
        return SUCCESS;
    }

    private void updateGoodsStatusStockNum() {
        XxlJobLogger.log("GoodsStatusTask.updateGoodsStatusStockNum | begin ...............");
        logger.info("GoodsStatusTask.updateGoodsStatusStockNum | begin ...............");
        try {
            List<WarehouseGoodsStatus> errorStockGoodsList = warehousesService.getErrorStockGoodsList();
            if(CollectionUtils.isNotEmpty(errorStockGoodsList)) {
                for (WarehouseGoodsStatus warehouseGoodsStatus : errorStockGoodsList) {
                    XxlJobLogger.log("errorStockInfo:{}",warehouseGoodsStatus.toString());
                }
            }
            Integer num = warehousesService.updateStockNumById(errorStockGoodsList);
            XxlJobLogger.log("更新数据 数量:{}",num);
        }catch (Exception e){
            XxlJobLogger.log("GoodsStatusTask.updateGoodsStatusStockNum error:{}",e);
            logger.error("GoodsStatusTask.updateGoodsStatusStockNum error:{}",e);
        }

    }
}
