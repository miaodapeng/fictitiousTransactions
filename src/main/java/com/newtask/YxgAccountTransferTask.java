package com.newtask;

import com.newtask.model.YxgAccount;
import com.vedeng.trader.dao.AccountMapper;
import com.vedeng.trader.dao.TraderMapper;
import com.vedeng.trader.dao.WebAccountMapper;
import com.vedeng.trader.model.Trader;
import com.vedeng.trader.model.WebAccount;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author daniel
 * @date 2020/03/04
 **/
@JobHandler(value = "yxgAccountTransfer")
@Component
public class YxgAccountTransferTask extends IJobHandler {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private WebAccountMapper webAccountMapper;

    @Autowired
    private TraderMapper traderMapper;


    @Override
    public ReturnT<String> execute(String s) throws Exception {
        int start = 0;
        if (StringUtils.isNotBlank(s)){
            start = Integer.parseInt(s);
        }


        while (true){
            YxgAccount yxgAccount = accountMapper.getAccount(start);
            if (yxgAccount == null) {
                break;
            }

            //根据医械购账号的companyName到erp查找trader信息，如果存在客户信息，则迁移相应的账号信息
            Trader trader = traderMapper.getTraderWrapByTraderId(yxgAccount.getTraderId());

            if (trader != null) {
                //查找医械购账号的手机号码在erp webAccount中是否存在
                if (webAccountMapper.getWebAccountListByMobile(yxgAccount.getMobile()).size() > 0) {
                    XxlJobLogger.log("医械购账号：{}在ERP中已存在",yxgAccount.getMobile());
                } else {
                    //存储医械购账号
                    WebAccount webAccount = new WebAccount();
                    webAccount.setWebAccountId(yxgAccount.getAccountId());
                    webAccount.setSsoAccountId(yxgAccount.getSsoAccountId());
                    webAccount.setIsEnable(yxgAccount.getStatus());
                    webAccount.setFrom(yxgAccount.getFrom());
                    webAccount.setCompanyStatus(yxgAccount.getCompanyStatus());
                    webAccount.setIndentityStatus(yxgAccount.getIndentityStatus());
                    webAccount.setIsOpenStore(yxgAccount.getIsOpenStore());
                    webAccount.setAccount(yxgAccount.getAccount());
                    webAccount.setEmail(yxgAccount.getEmail());
                    webAccount.setMobile(yxgAccount.getMobile());
                    webAccount.setCompanyName(yxgAccount.getCompanyName());
                    webAccount.setWeixinOpenid(yxgAccount.getWeixinOpenid());
                    webAccount.setUuid(yxgAccount.getUuid());
                    webAccount.setIsVedengJx(0);
                    webAccount.setIsVedengJoin(0);
                    webAccount.setAddTime(yxgAccount.getAddTime());
                    webAccount.setTraderId(trader.getTraderId());
                    //根据trader获取其归属销售
                    webAccount.setUserId(trader.getCreator());
                    //注册平台为医械购
                    webAccount.setRegisterPlatform(2);
                    webAccount.setIsEnable(1);
                    //注册账号的归属平台为关联企业的归属平台
                    webAccount.setBelongPlatform(trader.getBelongPlatform());
                    webAccountMapper.insert(webAccount);
                }
            }
            start = yxgAccount.getAccountId();
        }
        XxlJobLogger.log("迁移医械购账号信息结束!");
        return null;
    }
}
