package com.vedeng.common.constant;

/**
 * @Description: api接口url
 * @Author: Franlin.wu
 * @Version: V1.0.0
 * @Since: 1.0
 * @Date: 2019/6/19
 */
public interface ApiUrlConstant {

    /**
     * 发送微信模板消息接口
     */
    String API_WECHAT_SEND_TEMPLATE_MSG = "/weChat/sendTemplateMsg";

    /**
     * 推送客户归属销售至耗材商城
     */
    String API_TRADER_SALER_PUT_TO_HC = "/accountInfo/updateTraderSaler";
}
