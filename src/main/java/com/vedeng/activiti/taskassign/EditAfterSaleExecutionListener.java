package com.vedeng.activiti.taskassign;

import com.vedeng.aftersales.model.vo.AfterSalesVo;
import com.vedeng.aftersales.service.AfterSalesService;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.OrderDataUpdateConstant;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.service.BaseService;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.DateUtil;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;

public class EditAfterSaleExecutionListener   implements ExecutionListener {
    // 运行时注入service
    WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
    private AfterSalesService afterSalesOrderService = (AfterSalesService) context.getBean("afterSalesOrderService");
    private BaseService baseService = (BaseService) context.getBean("baseService");
    @Resource
    private WebServiceContext webServiceContext;


    //售后审核触发器
    //根据穿参通用回写主表中状态
    public void notify(DelegateExecution execution) throws Exception {
		ServletRequestAttributes ra= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request =  ra.getRequest();
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		AfterSalesVo afterSales = (AfterSalesVo) execution.getVariable("afterSalesInfo");
		//afterSales.setTraderType(2);
		afterSales.setValidStatus(1);//已生效
		afterSales.setValidTime(DateUtil.sysTimeMillis());
		afterSales.setStatus(2);//审核通过
		afterSales.setAtferSalesStatus(1);//进行中
		afterSales.setCompanyId(user.getCompanyId());
		afterSales.setPayer(user.getCompanyName());
		afterSales.setModTime(DateUtil.sysTimeMillis());
		afterSales.setUpdater(user.getUserId());
		ResultInfo<?> res = afterSalesOrderService.editApplyAudit(afterSales);
		if(res != null && res.getCode().equals(0)){
			//更新售后单updateDataTime
			baseService.updateAfterOrderDataUpdateTime(afterSales.getAfterSalesId(),null, OrderDataUpdateConstant.AFTER_ORDER_VAILD);
		}



    }
}
