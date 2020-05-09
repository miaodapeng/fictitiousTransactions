package com.newtask;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.trader.model.TraderCustomer;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * <b>Description:</b>每日刷新客户 订单覆盖品类，订单覆盖品牌，订单覆盖区域
 * 每日刷新供应商 订单覆盖品类，订单覆盖品牌，订单覆盖区域<br>
 * @Note
 * <b>Author:calvin</b>
 * <br><b>Date:</b> 2019/12/30
 */
@JobHandler(value = "customerBussinessInfoHandler")
@Component
public class CustomerBussinessInfoHandler extends BaseHandler{

    Logger logger= LoggerFactory.getLogger(CustomerBussinessInfoHandler.class);
    @Override
    public ReturnT<String> execute(String param) throws Exception {
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
        };
        String url = httpUrl + "tradercustomer/edittraderbusinessinfo.htm";
        try {
            TraderCustomer traderCustomer = new TraderCustomer();
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, traderCustomer, clientId, clientKey,
                    TypeRef);
            Integer times = 0;
            if (result.getCode() == -1 && times <= 10) {// 失败后再次调用

                Thread.sleep(10000);// 休眠10s后再次执行
                HttpClientUtils.post(url, traderCustomer, clientId, clientKey, TypeRef);

                times++;
            }
        } catch (Exception e) {
            logger.error("同步客户订单覆盖品类，订单覆盖品牌，订单覆盖区域失败",e);
            return ReturnT.FAIL;
        }

        return ReturnT.SUCCESS;
    }
}
