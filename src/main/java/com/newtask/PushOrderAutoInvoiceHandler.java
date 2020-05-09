package com.newtask;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.util.DateUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@JobHandler(value = "pushOrderAutoInvoiceHandler")
@Component
public class PushOrderAutoInvoiceHandler extends IJobHandler {
    @Value("${http_url}")
    public String httpUrl;

    @Value("${client_id}")
    public String clientId;

    @Value("${client_key}")
    public String clientKey;


    @Override
    public ReturnT<String> execute(String param) throws Exception {
        System.out.println("AutoInvoiceApply task run"+ DateUtil.gainNowDate());

        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
        };
        String url = httpUrl + "finance/invoice/pushorderintoautoinvoiceapply.htm";
        try {
            ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, param, clientId, clientKey,
                    TypeRef);

        } catch (Exception e) {
            XxlJobLogger.log("分批开票失败");
        }

        return ReturnT.SUCCESS;
    }

}
