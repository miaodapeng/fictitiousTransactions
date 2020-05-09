package com.vedeng.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.common.constant.CommonConstants;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.model.vo.ReqTemplateVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;

/**
 * @Description: 微信推送模板消息工具类
 * @Author: Franlin.wu
 * @Version: V1.0.0
 * @Since: 1.0
 * @Date: 2019/6/19
 */
public class WeChatSendTemplateUtil {

    /**
     * 日志
     */
    private static Logger log = LoggerFactory.getLogger(WeChatSendTemplateUtil.class);

    /**
     * 发货提醒 模板
     * 数据字典ID值
     */
    public static final Integer TEMPLATE_SHIPING_REMINDER = 363;
    /**
     * 订单签收通知 模板
     * 数据字典ID值
     */
    public static final Integer TEMPLATE_ORDER_SIGNING_NOTICE = 364;

    /**
     * 发票寄出提醒 模板
     * 数据字典ID值
     */
    public static final Integer TEMPLATE_INVOICE_REMINDER = 365;

    /**
     * 资质认证结果通知 模板
     * 数据字典ID值
     */
    public static final Integer TEMPLATE_QUALIFICATION_RESULT_NOTIFICATION = 366;


    /**
     * <b>Description: 调用api的模板消息接口</b>
     * @param apiUrl   请求url
     * @param reqTemp  模板参数
     * <b>Author：</b> franlin.wu
     * <b>Date:</b> 2019年6月19日
     * @return
     */
    public static void sendTemplateMsg(String apiUrl, ReqTemplateVariable reqTemp) {
        log.info("sendTemplateMsg | 调用模板消息接口 | begin ........");
        log.info("sendTemplateMsg | 调用模板消息接口 | apiUrl:{}, reqTemp:{}", apiUrl, reqTemp);
        // 接口返回
        TypeReference<ResultInfo> type = new TypeReference<ResultInfo>() {};
        // 接口请求头
        HashMap<String, String> heads = new HashMap<String, String>();
        // 接口版本
        heads.put(CommonConstants.INTER_VERSION, CommonConstants.INTER_VERSION_VALUE);
        // 调用接口
        ResultInfo<?> resultInfo =  HttpRestClientUtil.post(apiUrl, type, heads, reqTemp);

        log.info("sendTemplateMsg | 调用模板消息接口 | 结果:{}", resultInfo);

        log.info("sendTemplateMsg | 调用模板消息接口 | end ........");
    }


    /**
     * <b>Description: 异步调用api的模板消息接口</b>
     * @param apiUrl   请求url
     * @param reqTemp  模板参数
     * <b>Author：</b> franlin.wu
     * <b>Date:</b> 2019年6月19日
     * @return
     */
    public static void syncSendTemplateMsg(String apiUrl, ReqTemplateVariable reqTemp) {
        new Thread() {
            @Override
            public void run() {
                log.info("syncSendTemplateMsg | 异步调用模板消息接口 | begin ........");

                sendTemplateMsg(apiUrl, reqTemp);

                log.info("syncSendTemplateMsg | 异步调用模板消息接口 | end ........");
            }
        }.start();
    }
}
