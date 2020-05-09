package com.vedeng.wechat.service;

import com.vedeng.wechat.model.WeChatArr;

/**
 * @Description:
 * @Author: Franlin.wu
 * @Version: V1.0.0
 * @Since: 1.0
 * @Date: 2019/6/26
 */
public interface WeChatArrService {

    /**
     * 发送微信服务消息 订单签收
     * @param arr
     */
    public void sendTemplateMsgHcForOrderOk(WeChatArr arr);

    void sendTemplateMsgVedeng(WeChatArr arr);




}
