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

public class RejectOverAfterSalesVerifyExecutionListener implements ExecutionListener {
    // 运行时注入service
    WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
    private AfterSalesService afterSalesOrderService = (AfterSalesService) context.getBean("afterSalesOrderService");
    
    @Resource
    private WebServiceContext webServiceContext;
    //修改售后单结束触发器
    //根据穿参通用回写主表中状态
    public void notify(DelegateExecution execution) throws Exception {
		ServletRequestAttributes ra= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request =  ra.getRequest();
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		AfterSalesVo afterSalesInfo = (AfterSalesVo) execution.getVariable("afterSalesVo");
		if(afterSalesInfo != null){
		    AfterSalesVo afterSalesVo = new AfterSalesVo();
		    afterSalesVo.setAfterSalesId(afterSalesInfo.getAfterSalesId());
		    afterSalesVo.setAfterSalesStatusReson(0);
		    afterSalesVo.setAfterSalesStatusUser(0);
		    afterSalesVo.setAfterSalesStatusComments("");
		    afterSalesOrderService.updateAfterSalesById(afterSalesVo);
		}
		
    }
}
/**
 * 
 * @author John
 *
 */
