package com.vedeng.activiti.taskassign;


import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.vedeng.order.model.BussinessChance;
import com.vedeng.order.model.Quoteorder;
import com.vedeng.order.service.BussinessChanceService;
import com.vedeng.order.service.BuyorderService;
import com.vedeng.order.service.SaleorderService;
import com.vedeng.soap.service.VedengSoapService;

public class EditQuoteExecutionListener implements ExecutionListener {
    // 运行时注入service
    WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
    @Resource
    private WebServiceContext webServiceContext;
    
    private VedengSoapService vedengSoapService = (VedengSoapService) context.getBean("vedengSoapService");
    private BussinessChanceService bussinessChanceService = (BussinessChanceService) context.getBean("bussinessChanceService");
    //报价审核触发器
    //根据穿参通用回写主表中状态
    public void notify(DelegateExecution execution) throws Exception {
		Quoteorder quoteorder = (Quoteorder) execution.getVariable("quoteorder");
		//报价成功后修改商机
		BussinessChance bussinessChance= new BussinessChance();
		bussinessChance.setBussinessChanceId(quoteorder.getBussinessChanceId());
		bussinessChance.setStatus(2);
		bussinessChanceService.editBussinessChance(bussinessChance);
		
		vedengSoapService.quoteorderSync(quoteorder.getQuoteorderId());
		vedengSoapService.messageSyncWeb(1, quoteorder.getQuoteorderId(), 0);
		
    }
}
/**
 * 
 * @author John
 *
 */
