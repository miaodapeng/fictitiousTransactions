package com.vedeng.activiti.taskassign;

import java.math.BigDecimal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.util.DateUtil;
import com.vedeng.finance.model.TraderAccountPeriodApply;
import com.vedeng.finance.service.TraderAccountPeriodApplyService;

public class EditAccountPeriodAuditExecutionListener implements ExecutionListener {
    // 运行时注入service
    WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
    private TraderAccountPeriodApplyService accountPeriodService = (TraderAccountPeriodApplyService) context.getBean("accountPeriodService");
    @Resource
    private WebServiceContext webServiceContext;
    //账期审核触发器
    //根据穿参通用回写主表中状态
    public void notify(DelegateExecution execution) throws Exception {
		TraderAccountPeriodApply tapa = new TraderAccountPeriodApply();
		ServletRequestAttributes ra= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request =  ra.getRequest();
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		BigDecimal accountPeriodApply = (BigDecimal) execution.getVariable("accountPeriodApply");
		Integer accountPeriodDaysApply = (Integer) execution.getVariable("accountPeriodDaysApply");
     	    	Integer traderId = (Integer) execution.getVariable("traderId");
     	    	Integer traderType = (Integer) execution.getVariable("traderType");
     	    	Integer traderAccountPeriodApplyId = (Integer) execution.getVariable("traderAccountPeriodApplyId");
     	    	if(traderAccountPeriodApplyId != null){
	    	    tapa.setTraderAccountPeriodApplyId(traderAccountPeriodApplyId);
	    	}
     	    	if(accountPeriodApply != null){
     	    	    tapa.setAccountPeriodApply(accountPeriodApply);
     	    	}
     	    	if(accountPeriodDaysApply != null){
	    	    tapa.setAccountPeriodDaysApply(accountPeriodDaysApply);
	    	}
     	    	if(traderId != null){
	    	    tapa.setTraderId(traderId);
	    	}
     	    	if(traderType != null){
	    	    tapa.setTraderType(traderType);
	    	}
        	if(user!=null){
        		tapa.setUpdater(user.getUserId());
        		tapa.setModTime(DateUtil.sysTimeMillis());
        	}
        	tapa.setStatus(1);
		accountPeriodService.editAccountPeriodAudit(tapa);
    }
}
/**
 * 
 * @author John
 *
 */
