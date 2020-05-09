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
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.service.SaleorderService;
import com.vedeng.system.service.UserService;
import com.vedeng.system.service.VerifiesRecordService;

public class TaskParentUserExecutionListener implements ExecutionListener {
    // 运行时注入service
    WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
    private SaleorderService saleorderService = (SaleorderService) context.getBean("saleorderService");
    
    private VerifiesRecordService verifiesRecordService = (VerifiesRecordService)context.getBean("verifiesRecordService");
    @Resource
    private WebServiceContext webServiceContext;
    
    public void notify(DelegateExecution execution) throws Exception {
	      //审核完成后修改订单生效状态
	      String bussinesKey= execution.getProcessBusinessKey();
	      String id[] = bussinesKey.split("_");
	      String saleorderId = id[1];
	      //MessageContext mc = webServiceContext.getMessageContext();
	      //HttpServletRequest request = (HttpServletRequest) (mc.get(MessageContext.SERVLET_REQUEST));
	      //saleorderService.saveEditSaleorderInfo(saleorder, request, request.getSession());
	      Saleorder saleorder = new Saleorder();
	      saleorder.setValidStatus(1);
	      saleorder.setValidTime(DateUtil.sysTimeMillis());
	      saleorder.setSaleorderId(Integer.parseInt(saleorderId));
	      ResultInfo<?> a = new ResultInfo<>();
	      saleorderService.validSaleorder(saleorder);
	      //添加审核对应主表的审核状态
	      verifiesRecordService.saveVerifiesInfo(execution.getId(),1);
    }
}
/**
 * 
 * @author John
 *
 */
