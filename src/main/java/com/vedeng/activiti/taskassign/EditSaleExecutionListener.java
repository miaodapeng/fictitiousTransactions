package com.vedeng.activiti.taskassign;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.vedeng.activiti.service.ActionProcdefService;
import com.vedeng.aftersales.model.vo.AfterSalesVo;
import com.vedeng.aftersales.service.AfterSalesService;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.util.DateUtil;
import com.vedeng.finance.model.TraderAccountPeriodApply;
import com.vedeng.order.model.SaleorderModifyApply;
import com.vedeng.order.service.SaleorderService;

public class EditSaleExecutionListener implements ExecutionListener {
    // 运行时注入service
    WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
    private SaleorderService saleorderService = (SaleorderService) context.getBean("saleorderService");
    
    private ActionProcdefService actionProcdefService = (ActionProcdefService) context.getBean("actionProcdefService");
    @Resource
    private WebServiceContext webServiceContext;
    //修改订单审核触发器
    //根据穿参通用回写主表中状态
    public void notify(DelegateExecution execution) throws Exception {
		ServletRequestAttributes ra= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request =  ra.getRequest();
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		SaleorderModifyApply saleorderModifyApply = (SaleorderModifyApply) execution.getVariable("saleorderModifyApplyInfo");

		if(saleorderModifyApply == null){
			saleorderModifyApply = new SaleorderModifyApply();
			saleorderModifyApply.setSaleorderModifyApplyId((Integer) execution.getVariable("saleorderModifyApplyId"));
            saleorderModifyApply.setSaleorderId((Integer) execution.getVariable("orderId"));
		}

		String a = execution.getCurrentActivityName();

		if(execution.getCurrentActivityName().equals("审核完成")){
		    ResultInfo<?> res = saleorderService.saveSaleorderModifyApplyToSaleorder(saleorderModifyApply);
		}else{
		  
            	    actionProcdefService.updateInfo("T_SALEORDER", "SALEORDER_ID", saleorderModifyApply.getSaleorderId(), "LOCKED_STATUS", 0,2);
		}
    }
}
/**
 * 
 * @author John
 *
 */
