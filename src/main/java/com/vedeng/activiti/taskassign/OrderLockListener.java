package com.vedeng.activiti.taskassign;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.vedeng.activiti.service.ActionProcdefService;
import com.vedeng.aftersales.model.vo.AfterSalesVo;
import com.vedeng.common.util.StringUtil;

public class OrderLockListener implements TaskListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 运行时注入service
	WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
	private ActionProcdefService actionProcdefService = (ActionProcdefService) context.getBean("actionProcdefService");

	@Override
	public void notify(DelegateTask delegateTask) {
		String processKey = delegateTask.getVariable("processDefinitionKey").toString();
		if (StringUtil.isNotBlank(processKey)) {
			Integer orderId = null;
			switch (processKey) {
			case "editSaleorderVerify":// 销售订单编辑审核
				orderId = Integer.parseInt(delegateTask.getVariable("orderId").toString());
				break;
			case "afterSalesVerify":// 售后订单审核
				AfterSalesVo afterSales = (AfterSalesVo) delegateTask.getVariable("afterSalesInfo");
				if (afterSales.getType().equals(543)) {
					orderId = afterSales.getOrderId();
				}
				break;
			default:
				break;
			}
			if (orderId != null) {
				actionProcdefService.updateInfo("T_SALEORDER_GOODS", "SALEORDER_ID", orderId, "LOCKED_STATUS", 1, 2);
			}
		}
	}

}
