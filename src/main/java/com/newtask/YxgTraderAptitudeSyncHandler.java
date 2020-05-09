package com.newtask;

import com.newtask.model.YXGTraderAptitude;
import com.vedeng.common.page.Page;
import com.vedeng.trader.dao.YxgTraderAptitudeMapper;
import com.vedeng.trader.service.TraderCustomerService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JobHandler(value = "yxgTraderAptitudeSyncHandler")
@Component
public class YxgTraderAptitudeSyncHandler extends IJobHandler {


    @Autowired
    private TraderCustomerService traderCustomerService;
    @Autowired
    private YxgTraderAptitudeMapper yxgTraderAptitudeMapper;
    @Override
    public ReturnT<String> execute(String param) throws Exception {
        Page page = new Page(1, 100);
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        int currentPage=1;
        do{
            List<YXGTraderAptitude> traderAptitudeList=yxgTraderAptitudeMapper.getAptitudeListPage(params);
            for(YXGTraderAptitude a:traderAptitudeList){
                if(a!=null) {
                    XxlJobLogger.log("正在处理："+a.getTraderId());
                    traderCustomerService.syncYxgTraderStatus(a);
                }
            }
            currentPage++;
            page.setPageNo(currentPage);
        }while (currentPage<=page.getTotalPage());
        return ReturnT.SUCCESS;
    }


}
