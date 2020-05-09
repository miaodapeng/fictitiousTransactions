package com.vedeng.activiti.taskassign;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.pvm.delegate.ExecutionListenerExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.util.DateUtil;
import com.vedeng.order.model.Quoteorder;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.service.QuoteService;
import com.vedeng.order.service.SaleorderService;
import com.vedeng.system.service.UserService;
import com.vedeng.system.service.VerifiesRecordService;

public class TaskQuoteValidExecutionListener implements ExecutionListener {
    // 运行时注入service
    WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
    private QuoteService quoteService = (QuoteService) context.getBean("quoteService");
    
    private VerifiesRecordService verifiesRecordService = (VerifiesRecordService)context.getBean("verifiesRecordService");
    @Resource
    private WebServiceContext webServiceContext;
    
    public void notify(DelegateExecution execution) throws Exception {
	      //审核完成后修改报价单生效状态
	      String bussinesKey= execution.getProcessBusinessKey();
	      String id[] = bussinesKey.split("_");
	      String quoteorderId = id[1];
	      Quoteorder quoteorderInfo = quoteService.getQuoteInfoByKey(Integer.parseInt(quoteorderId));
	      Integer updater=  (Integer) execution.getVariable("updater");
	      Quoteorder quoteorder = new Quoteorder();
	      quoteorder.setValidStatus(1);
	      quoteorder.setValidTime(DateUtil.sysTimeMillis());
	      quoteorder.setQuoteorderId(Integer.parseInt(quoteorderId));
	      quoteorder.setUpdater(updater);
	      quoteorder.setModTime(DateUtil.sysTimeMillis());
	      quoteorder.setBussinessChanceId(quoteorderInfo.getBussinessChanceId());
	      ResultInfo<?> a = new ResultInfo<>();
	      quoteService.editQuoteValIdSave(quoteorder);
	    //添加审核对应主表的审核状态
	      verifiesRecordService.saveVerifiesInfo(execution.getId(),1);
    }
}
/**
 * 
 * @author John
 *
 */
