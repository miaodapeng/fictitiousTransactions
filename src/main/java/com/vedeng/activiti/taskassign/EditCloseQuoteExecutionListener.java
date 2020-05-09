package com.vedeng.activiti.taskassign;


import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.vedeng.common.util.DateUtil;
import com.vedeng.order.model.BussinessChance;
import com.vedeng.order.model.Quoteorder;
import com.vedeng.order.service.BussinessChanceService;
import com.vedeng.order.service.BuyorderService;
import com.vedeng.order.service.QuoteService;
import com.vedeng.order.service.SaleorderService;
import com.vedeng.soap.service.VedengSoapService;

public class EditCloseQuoteExecutionListener implements ExecutionListener {
    // 运行时注入service
    WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
    @Resource
    private WebServiceContext webServiceContext;
    
    private QuoteService quoteService = (QuoteService) context.getBean("quoteService");
    //关闭报价审核触发器
    //根据穿参通用回写主表中状态
    public void notify(DelegateExecution execution) throws Exception {
		Quoteorder quoteorder = (Quoteorder) execution.getVariable("quoteorder");
		quoteorder.setFollowOrderStatusComments("");
		quoteorder.setFollowOrderTime(DateUtil.sysTimeMillis());
		quoteService.editLoseOrderStatus(quoteorder);
    }
}

