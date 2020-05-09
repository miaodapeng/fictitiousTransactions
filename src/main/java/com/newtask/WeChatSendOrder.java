package com.newtask;

import com.task.BaseTaskController;
import com.vedeng.common.constant.OrderConstant;
import com.vedeng.common.controller.BaseSonContrller;
import com.vedeng.wechat.dao.WeChatArrMapper;
import com.vedeng.wechat.model.WeChatArr;
import com.vedeng.wechat.service.WeChatArrService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

/**
 * @Description: 医械购订单 定时任务刷新时，每天10点左右要发送
 * @Author: Franlin.wu
 * @Version: V1.0.0
 * @Since: 1.0
 * @Date: 2019/6/26
 */
@JobHandler(value="weChatSendOrder")
@Component
public class WeChatSendOrder extends IJobHandler {

    private Logger logger = LoggerFactory.getLogger(WeChatSendOrder.class);

    @Autowired
    @Qualifier("weChatArrMapper")
    private WeChatArrMapper weChatArrMapper;

    @Autowired
    private WeChatArrService weChatArrService;
                public  void sendWxTemplateForOrder() {
                    XxlJobLogger.log("WeChatSendOrder.sendWxTemplateForOrder | begin ...............");
                    logger.info("WeChatSendOrder.sendWxTemplateForOrder | begin ...............");
                    // 查询
                    WeChatArr weChatArr=new WeChatArr();
                    weChatArr.setPlatfromId(1);
                    List<WeChatArr> arrList = weChatArrMapper.selectAllList(null);
                   // List<WeChatArr> arrList2 = weChatArrMapper.selectAllList(weChatArr);
                    logger.info("WeChatSendOrder.sendWxTemplateForOrder | num:{} ...............");
                    if(CollectionUtils.isNotEmpty(arrList)) {
                        for (WeChatArr arr : arrList) {
                            if (null == arr) {
                                continue;
                            }
                            if (arr.getOrderType() == OrderConstant.ORDER_TYPE_BD || arr.getOrderType() == OrderConstant.ORDER_TYPE_DH || arr.getOrderType() == OrderConstant.ORDER_TYPE_JX || arr.getOrderType() == OrderConstant.ORDER_TYPE_SALE) {
                                try {
                                    weChatArrService.sendTemplateMsgVedeng(arr);

                                } catch (Exception e) {
                                    logger.error("weChatArrService.sendTemplateMsgHcForOrderOk | error ", e);
                                }
                            }
                        }
                        Integer platfromId=1;
                        weChatArrMapper.deleteAll(platfromId);
                    }
                            if(CollectionUtils.isNotEmpty(arrList)) {
                                for (WeChatArr arr : arrList) {
                                    if (null == arr) {
                                        continue;
                                    }
                                    if (arr.getOrderType() == OrderConstant.ORDER_TYPE_HC) {
                                        try {
                                            weChatArrService.sendTemplateMsgHcForOrderOk(arr);

                                            // 清除
                                            // weChatArrMapper.deleteAll(null);
                                        } catch (Exception e) {
                                            logger.error("weChatArrService.sendTemplateMsgHcForOrderOk | error ", e);
                                        }

                                    }
                                }
                                weChatArrMapper.deleteAll(null);

                            }


        XxlJobLogger.log("WeChatSendOrder.sendWxTemplateForOrder | end ...............");
        logger.info("WeChatSendOrder.sendWxTemplateForOrder | end ...............");
    }


    @Override
    public ReturnT<String> execute(String s) throws Exception {
        XxlJobLogger.log("XXL-JOB, Hello World.");
        sendWxTemplateForOrder();
        return SUCCESS;
    }
}
