package com.newtask;

import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.util.StringUtil;
import com.vedeng.trader.model.WebAccount;
import com.vedeng.trader.service.WebAccountService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: erp
 * @description: 会员开通消息提醒
 * @author: addis
 * @create: 2020-03-16 13:29
 **/
@Component
@JobHandler(value = "SendVipMessageTask")
public class SendVipMessageTask extends IJobHandler {


    @Autowired
    WebAccountService webAccountService;


    @Override
    public ReturnT<String> execute(String param) throws Exception {
        if(StringUtil.isNotBlank(param)){
            WebAccount webAccount=new WebAccount();
            webAccount.setMobile(param.trim());
            webAccount.setIsSendMessage(0);//未发送消息
            webAccount.setStatus(1);//资质审核通过
            webAccount.setCustomerNature(ErpConst.CUSTOME_RNATURE);//分销
            webAccountService.SendVipMessageMethod(webAccount);
        }else{
            WebAccount webAccount=new WebAccount();
            webAccount.setIsSendMessage(0);//未发送消息
            webAccount.setStatus(1);//资质审核通过
            webAccount.setCustomerNature(ErpConst.CUSTOME_RNATURE);//分销
            webAccountService.SendVipMessageMethod(webAccount);//调用会员提醒发送
        }

        return SUCCESS;
    }
}
