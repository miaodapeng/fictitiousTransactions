package com.vedeng.trader.service;

import com.vedeng.trader.model.WebAccount;

import java.util.List;

public interface WebAccountService  {


    /**
     * <b>Description:</b><br>发送消息（尊敬的客户，您的贝登商城会员申请已审核通过）
     *
     *
     * @param :[webAccount]
     * @return :java.util.List<com.vedeng.trader.model.vo.WebAccountVo>
     * @Note <b>Author:</b> Michael <br>
     *       <b>Date:</b> 2019/5/20 3:24 PM
     */
    List<WebAccount> SendVipMessageMethod(WebAccount webAccount);



}
