package com.newtask;

import com.vedeng.common.http.NewHttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.logistics.service.WarehouseStockService;
import com.vedeng.order.dao.SaleorderMapper;
import com.vedeng.order.model.Saleorder;
import com.vedeng.stock.api.stock.dto.ActionStockDto;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 *推送活动订单占用,销量数据
 * @author strange
 * @date $
 */
@Component
@JobHandler(value="StockActionTask")
public class StockActionTask extends IJobHandler {
    private Logger logger = LoggerFactory.getLogger(StockActionTask.class);

    @Resource
    private WarehouseStockService warehouseStockService;

    @Value("${stock_url}")
    private String stockUrl;

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        XxlJobLogger.log("XXL-JOB, Hello World.");
        updateStockActionInfo();
        return SUCCESS;
    }

    public void updateStockActionInfo() {
        XxlJobLogger.log("StockActionTask.updateStockActionInfo | begin ...............");
        logger.info("StockActionTask.updateStockActionInfo | begin ...............");
        try {
            List<Integer> actionIdlist = warehouseStockService.getAllActionId();
            for (Integer actionId : actionIdlist) {
                ActionStockDto actionGoodsInfo =  warehouseStockService.getActionGoodsInfo(actionId);
                if(CollectionUtils.isNotEmpty(actionGoodsInfo.getWarehouseDtoList())) {
                    String json = JsonUtils.translateToJson(actionGoodsInfo);
                    String url = stockUrl + "/promotion/action/checkActionInfo";
                    JSONObject result2 = NewHttpClientUtils.doPut(url, json);
                    logger.info("updateStockActionInfo 调用库存服务校验活动库存:{}", json);
                    XxlJobLogger.log("updateStockActionInfo 调用库存服务校验活动库存:{}", json);
                }
            }
        }catch (Exception e){
            XxlJobLogger.log("StockActionTask.updateStockActionInfo error:{}",e);
            logger.error("StockActionTask.updateStockActionInfo error:{}",e);
        }
    }
}
