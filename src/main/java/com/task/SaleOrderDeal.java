/**
 * 
 */
package com.task;

import com.common.constants.Contant;
import com.vedeng.activiti.service.ActionProcdefService;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.service.SaleorderService;
import com.vedeng.system.service.VerifiesRecordService;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 *
 */
public class SaleOrderDeal extends BaseTaskController{
	public static Logger logger = LoggerFactory.getLogger(SaleOrderDeal.class);

	WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();

	private SaleorderService saleorderService = (SaleorderService) context.getBean("saleorderService");
	
	private ActionProcdefService actionProcdefService = (ActionProcdefService) context.getBean("actionProcdefService");
	
	protected VerifiesRecordService verifiesRecordService = (VerifiesRecordService) context.getBean("verifiesRecordService");
	
	@Autowired // 自动装载
	protected ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	private ProcessEngine getProcessEngine() {
		return ProcessEngines.getDefaultProcessEngine();// 流程引擎
	}
	
	@Test
	public void dealContractStatus() {
		
		// 获取订单id集合；
		//	条件：
		// 	1.订单生效状态：已生效订单
		// 	2.合同回传状态：已回传
		//  3.合同审核状态：待审核
		
		// 查询参数
		Map<String, Object> paraMap = new HashMap<>();
		// 销售订单合同回传
		paraMap.put("attachmentFunction", 492);
		paraMap.put("companyId", 1);
		List<Integer> saleOrderIdList = saleorderService.getSaleOrderIdListByParam(paraMap);
		// 订单id saleOrderIdList
		paraMap.put("list", saleOrderIdList);
		// 根据订单id查询订单集合信息
		List<Saleorder> orderList = saleorderService.getOrderListInfoById(paraMap);
		for(Saleorder so:orderList){
			RuntimeService runtimeService = processEngine.getRuntimeService();// 任务相关service
			ProcessInstanceQuery instanceQuery = runtimeService.createProcessInstanceQuery();// 创建当前实例查询
			HistoricProcessInstanceQuery historyQuery = processEngine.getHistoryService().createHistoricProcessInstanceQuery();// 创建历史实例查询
			HistoryService historyService = processEngine.getHistoryService();
			ProcessInstance processInstance = instanceQuery.processInstanceBusinessKey("contractReturnVerify_"+so.getSaleorderId()).singleResult();
			if(processInstance != null && processInstance.getId() != null){
				runtimeService.deleteProcessInstance(processInstance.getId(), "流程取消");
				List<HistoricProcessInstance> resultList = historyQuery.processInstanceBusinessKey("contractReturnVerify_"+so.getSaleorderId()).list();
				if(resultList != null ){
					for(HistoricProcessInstance h:resultList){
						historyService.deleteHistoricProcessInstance(h.getId());
					}
				}
			}
		}


		// 如果不为空，则批量审核回传合同
		if(CollectionUtils.isNotEmpty(orderList)){
			int size = orderList.size();
			for (int i = 0; i < size; i++) {
				Saleorder saleorder = orderList.get(i);
				Saleorder saleorderInfo = new Saleorder();
				saleorder.setOptType("orderDetail"); 
				saleorderInfo = saleorderService.getBaseSaleorderInfoNew(saleorder);
				
				Map<String, Object> variableMap = new HashMap<String, Object>();
				
				variableMap.put("saleorderInfo", saleorderInfo);
				variableMap.put("currentAssinee", saleorder.getVerifyUsername());
				variableMap.put("processDefinitionKey", "contractReturnVerify");
				variableMap.put("businessKey", "contractReturnVerify_" + saleorder.getSaleorderId());
				variableMap.put("relateTableKey", saleorder.getSaleorderId());
				variableMap.put("relateTable", "T_SALEORDER");
				
				
				/**
			     * 流程名称saleorder+variables+businessKey
			     */
				TaskService taskService = this.getProcessEngine().getTaskService(); // 任务相关service
				
				Task taskInfo = taskService.createTaskQuery().processInstanceBusinessKey("contractReturnVerify_"+saleorder.getSaleorderId())
						.singleResult();
				if(taskInfo != null){
//					throw new Exception("流程已发起请勿重复提交");
				}else{

					IdentityService identityService = this.getProcessEngine().getIdentityService();
					identityService.setAuthenticatedUserId(saleorder.getVerifyUsername());
					// 设置当前审核人(订单归属人)
					RuntimeService runtimeService = this.getProcessEngine().getRuntimeService();// 运行时Service
					ProcessInstance processInstance = runtimeService.startProcessInstanceByKeyAndTenantId("contractReturnVerify",
							"contractReturnVerify_"+saleorder.getSaleorderId(), variableMap, "erp:company:" + 1);
				}
				
				
				// 默认申请人通过
				// 根据BusinessKey获取生成的审核实例
				Map<String, Object> historicInfo = actionProcdefService.getHistoric(processEngine,
						"contractReturnVerify_" + saleorder.getSaleorderId());
				
				if (historicInfo.get("endStatus") != "审核完成") {
					Task taskInfo1 = (Task) historicInfo.get("taskInfo");
					String taskId = taskInfo1.getId();
					Authentication.setAuthenticatedUserId(saleorder.getVerifyUsername());
					Map<String, Object> variables = new HashMap<String, Object>();
					
//					// 默认审批通过
//					ResultInfo<?> complementStatus = actionProcdefService.complementTask(request, taskId, "",
//							saleorderInfo.getVerifyUsername(), variables);

			    	HistoryService historyService = this.getProcessEngine().getHistoryService(); // 任务相关service
					TaskService taskService1 = this.getProcessEngine().getTaskService();
					// 使用任务id,获取任务对象，获取流程实例id
					Task task = taskService1.createTaskQuery().taskId(taskId).singleResult();
					// 利用任务对象，获取流程实例id
					String processInstancesId = task.getProcessInstanceId();
					
					if("" != null){
					    taskService1.addComment(taskId, processInstancesId, "");
					}
					if(saleorder.getVerifyUsername() != null){
					    taskService1.setAssignee(taskId, saleorder.getVerifyUsername());
					}
					String endStatus = "";
					try {
						taskService1.complete(taskId, variables);
						List<HistoricActivityInstance> hia = historyService.createHistoricActivityInstanceQuery()
								.processInstanceId(processInstancesId).orderByProcessInstanceId().desc().list();
						endStatus = hia.get(hia.size() - 1).getActivityType();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						logger.error(Contant.ERROR_MSG, e);
					}
			
					ResultInfo<?> complementStatus = new ResultInfo(0, "操作成功",endStatus);
					// 如果未结束添加审核对应主表的审核状态
					if (!complementStatus.getData().equals("endEvent")) {
						verifiesRecordService.saveVerifiesInfo(taskId, 0);
					}
				}
				
			}
		}
	}
}
