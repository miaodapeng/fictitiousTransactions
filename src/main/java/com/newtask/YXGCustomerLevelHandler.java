package com.newtask;

import com.vedeng.common.constant.TraderConstants;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.order.dao.SaleorderMapper;
import com.vedeng.order.model.SaleorderCountParam;
import com.vedeng.order.model.SaleorderCountResult;
import com.vedeng.trader.dao.TraderCustomerMapper;
import com.vedeng.trader.model.TraderCustomer;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.apache.commons.collections.CollectionUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JobHandler(value = "yxgCustomerLevelHandler")
@Component
public class YXGCustomerLevelHandler extends IJobHandler {

    @Autowired
    private TraderCustomerMapper traderCustomerMapper;

    @Autowired
    private SaleorderMapper saleorderMapper;

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        Page page = new Page(1, 100);
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        List<TraderCustomer> traders = traderCustomerMapper.getYXGCustomerListPage(params);
        classifyCustomerLevel(traders);
        int pageSum = page.getTotalPage();
        if (pageSum > 1) {
            for (int i = 2; i <= pageSum; i++) {
                page.setPageNo(i);
                params.put("page", page);
                traders = traderCustomerMapper.getYXGCustomerListPage(params);
                classifyCustomerLevel(traders);
            }
        }
        return new ReturnT<>().SUCCESS;
    }


    private void classifyCustomerLevel(List<TraderCustomer> traderCustomers) {
        if (CollectionUtils.isEmpty(traderCustomers)) {
            return;
        }
        Date now = new Date();
        Long beginTime = DateUtil.getDateBefore(now, TraderConstants.LEVEL_YXG_PERIOD_DAYS);
        for (TraderCustomer c : traderCustomers) {
            if (c == null || c.getTraderId() == null || c.getTraderCustomerId() == null) {
                continue;
            }
            XxlJobLogger.log("正在处理：" + c.getTraderId());
            SaleorderCountParam param = new SaleorderCountParam();
            param.setTraderId(c.getTraderId());
            param.setBeginTime(beginTime);
            SaleorderCountResult countAndAmount = saleorderMapper.getDaysCountSum(param);

            if (countAndAmount.getOrderDaysCount() >= TraderConstants.LEVEL_A_S_DAYS
                    && countAndAmount.getOrderDaysMoneySum().compareTo(new BigDecimal(TraderConstants.LEVEL_A_S_AMOUNT_STR)) >= TraderConstants.ZERO) {
                //S级别
                c.setCustomerLevel(TraderConstants.LEVEL_S);
            } else if (countAndAmount.getOrderDaysCount() >= TraderConstants.LEVEL_A_S_DAYS) {
                //A级别
                c.setCustomerLevel(TraderConstants.LEVEL_A);
            } else if (countAndAmount.getOrderDaysCount() >= TraderConstants.LEVEL_B_C_DAYS
                    && countAndAmount.getOrderDaysCount() < TraderConstants.LEVEL_A_S_DAYS
                    && countAndAmount.getOrderDaysMoneySum().compareTo(new BigDecimal(TraderConstants.LEVEL_B_C_AMOUNT_STR)) >= TraderConstants.ZERO) {
                //B级别
                c.setCustomerLevel(TraderConstants.LEVEL_B);
            } else if (countAndAmount.getOrderDaysCount() >= TraderConstants.LEVEL_B_C_DAYS
                    && countAndAmount.getOrderDaysCount() < TraderConstants.LEVEL_A_S_DAYS) {
                //C级别
                c.setCustomerLevel(TraderConstants.LEVEL_C);
            } else if (countAndAmount.getOrderDaysCount() == TraderConstants.LEVEL_D_DAYS) {
                //D级别
                c.setCustomerLevel(TraderConstants.LEVEL_D);
            } else {
                //E级别
                c.setCustomerLevel(TraderConstants.LEVEL_E);
            }
            traderCustomerMapper.updateCustomerLevelById(c);
        }
    }


}
