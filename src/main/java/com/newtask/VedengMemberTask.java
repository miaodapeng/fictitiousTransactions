package com.newtask;

import com.vedeng.common.model.ResultInfo;
import com.vedeng.trader.service.TraderCustomerService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 刷新贝登会员定时任务
 * @author strange
 * @date $
 */
@Component
@JobHandler(value="vedengMemberTask")
public class VedengMemberTask extends IJobHandler {
    private Logger logger = LoggerFactory.getLogger(VedengMemberTask.class);

    @Autowired
    private TraderCustomerService traderCustomerService;
    @Override
    public ReturnT<String> execute(String param) throws Exception {
        XxlJobLogger.log("XXL-JOB, Hello World.");
        updateVedengMember();
        return SUCCESS;
    }

    private ResultInfo updateVedengMember() {
        ResultInfo resultInfo = new ResultInfo();
        try {
          List<Integer> webAccountIdList =  traderCustomerService.updateVedengMember();
            XxlJobLogger.log("VedengMemberTask.updateVedengMember | result:{}",webAccountIdList);
            logger.info("VedengMemberTask.updateVedengMember | result:{}",webAccountIdList);
            resultInfo.setMessage(webAccountIdList.toString());
            resultInfo.setCode(0);
            return resultInfo;
        }catch (Exception e){
            XxlJobLogger.log("VedengMemberTask.updateVedengMember error:{}",e);
            logger.error("VedengMemberTask.updateVedengMember error:{}",e);
        }
        return resultInfo;
    }
}
