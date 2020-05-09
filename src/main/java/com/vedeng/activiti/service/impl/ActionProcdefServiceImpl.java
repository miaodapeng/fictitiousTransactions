package com.vedeng.activiti.service.impl;

import com.common.constants.Contant;
import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.activiti.dao.ActionProcdefMapper;
import com.vedeng.activiti.model.ActionProcdef;
import com.vedeng.activiti.service.ActionProcdefService;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.StringUtil;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("actionProcdefService")
public class ActionProcdefServiceImpl implements ActionProcdefService {
	public static Logger logger = LoggerFactory.getLogger(ActionProcdefServiceImpl.class);

    	@Value("${http_url}")
	protected String httpUrl;
    	
    	@Value("${client_id}")
	protected String clientId;
	
	@Value("${client_key}")
	protected String clientKey;
    
	@Autowired
	@Qualifier("actionProcdefMapper")
	private ActionProcdefMapper actionProcdefMapper;


	private ProcessEngine getProcessEngine() {
		return ProcessEngines.getDefaultProcessEngine();// 流程引擎
	}

	@Override
	public List<ActionProcdef> getList() {
		ActionProcdef actionProcdef = new ActionProcdef();
		return actionProcdefMapper.getList(actionProcdef);
	}

	@Override
	public List<ActionProcdef> getList(ActionProcdef actionProcdef) {
		return actionProcdefMapper.getList(actionProcdef);
	}

	@Override
	public List<ActionProcdef> getList(int companyId) {
		ActionProcdef actionProcdef = new ActionProcdef();
		actionProcdef.setCompanyId(companyId);
		return actionProcdefMapper.getList(actionProcdef);
	}

	@Override
	public List<ActionProcdef> getByAcitonId(int actionId, int companyId) {
		ActionProcdef actionProcdef = new ActionProcdef();
		actionProcdef.setCompanyId(companyId);
		actionProcdef.setActionId(actionId);
		return actionProcdefMapper.getList(actionProcdef);
	}

	@Override
	public List<ActionProcdef> getByAcitonId(int actionId) {
		ActionProcdef actionProcdef = new ActionProcdef();
		actionProcdef.setActionId(actionId);
		return actionProcdefMapper.getList(actionProcdef);
	}

	@Override
	public List<ActionProcdef> getByPreBusinessKey(String preBusinessKey, int companyId) {
		ActionProcdef actionProcdef = new ActionProcdef();
		actionProcdef.setCompanyId(companyId);
		actionProcdef.setPreBusinessKey(preBusinessKey);
		return actionProcdefMapper.getList(actionProcdef);
	}

	@Override
	public List<ActionProcdef> getByPreBusinessKey(String preBusinessKey) {
		ActionProcdef actionProcdef = new ActionProcdef();
		actionProcdef.setPreBusinessKey(preBusinessKey);
		return actionProcdefMapper.getList(actionProcdef);
	}

	@Override
	public List<ActionProcdef> getByProcdefId(String procdefId, int companyId) {
		ActionProcdef actionProcdef = new ActionProcdef();
		actionProcdef.setCompanyId(companyId);
		actionProcdef.setProcdefId(procdefId);
		return actionProcdefMapper.getList(actionProcdef);
	}

	@Override
	public List<ActionProcdef> getByProcdefId(String procdefId) {
		ActionProcdef actionProcdef = new ActionProcdef();
		actionProcdef.setProcdefId(procdefId);
		return actionProcdefMapper.getList(actionProcdef);
	}

	@Override
	public int insert(ActionProcdef actionProcdef) {
		return actionProcdefMapper.insert(actionProcdef);
	}

	@Override
	public int replace(ActionProcdef actionProcdef) {
		return actionProcdefMapper.replace(actionProcdef);
	}

	@Override
	public List<ActionProcdef> getListPage(ActionProcdef actionProcdef, Page page) {
		Map<String, Object> map = new HashMap<>();
		map.put("actionProcdef", actionProcdef);
		map.put("page", page);
		return actionProcdefMapper.getListPage(map);
	}

	@Override
	public List<ActionProcdef> getListPage(Page page) {
		Map<String, Object> map = new HashMap<>();
		map.put("page", page);
		return actionProcdefMapper.getListPage(map);
	}

	@Override
	public Map<String, Object> getHistoric(ProcessEngine processEngine,String businessKey) {
		 ServletRequestAttributes ra= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request=null;
		 if(ra!=null) {
			 request = ra.getRequest();
		 }
		 User user;

		 if(request!=null){
		    user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);}
		 else {
		 	user=new User();
		 	user.setCompanyId(1);
		 	user.setUserId(2);
		 	user.setUsername("njadmin");
		 }
		 return getHistoricByUser(processEngine,businessKey,user);
	}

	@Override
	public Map<String, Object> getHistoricForNjadmin(ProcessEngine processEngine,String businessKey) {
		User user =new User();
		user.setUserId(2);
		user.setUsername("njadmin");
		return getHistoricByUser(processEngine,businessKey,user);
	}

	private Map<String, Object> getHistoricByUser(ProcessEngine processEngine,String businessKey,User user) {
		Map<String, Object> map = new HashMap<>();
		// 获取订单审核信息
		TaskService taskService = processEngine.getTaskService(); // 任务相关service
		// 获取当前活动节点
		Task taskInfo = taskService.createTaskQuery().processInstanceBusinessKey(businessKey)
				.singleResult();
		map.put("taskInfo", taskInfo);
		List<HistoricProcessInstance> historicProcessInstance = new ArrayList<>();
		List<HistoricActivityInstance> historicActivityInstance = new ArrayList<>();
		Map<String, Object> commentMap = new HashMap<String, Object>();
		Map<String, Object> candidateUserMap = new HashMap<String, Object>();
		candidateUserMap.put("belong", false);
		String startUser = null;
		String processInstanceId = null;
		String endStatus = null;
		// 获取该订单所有审核流程历史纪录
		HistoryService historyService = processEngine.getHistoryService(); // 任务相关service
		historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
				.processInstanceBusinessKey(businessKey).orderByProcessInstanceStartTime().asc().list();
		// 如果不为空，循环获取每个审核流程中的记录
		if (null != historicProcessInstance && !historicProcessInstance.isEmpty()) {
			for (HistoricProcessInstance hi : historicProcessInstance) {
				List<HistoricActivityInstance> hia = historyService.createHistoricActivityInstanceQuery()
						.processInstanceId(hi.getId()).orderByHistoricActivityInstanceStartTime().asc().list();
				processInstanceId = hi.getId();

				//根据流程ID去查询对应的节点备注信息
				List<Comment> commentList = taskService.getProcessInstanceComments(processInstanceId);
				//获取候选人员信息表
				for (Comment c : commentList) {
					// 备注信息塞入
					commentMap.put(c.getTaskId(), c.getFullMessage());
				}

				historicActivityInstance.addAll(hia);
			}
			endStatus = historicActivityInstance.get(historicActivityInstance.size() - 1).getActivityName();

		}
		if (null != processInstanceId) {
			startUser = processEngine.getHistoryService().createHistoricProcessInstanceQuery()
					.processInstanceId(processInstanceId).singleResult().getStartUserId();

		}
		try
		{
			//获取当前审核候选人
			List<IdentityLink> candidateUserList = taskService.getIdentityLinksForTask(taskInfo.getId());
			candidateUserMap.put(taskInfo.getId(),candidateUserList);
			if(candidateUserList!=null && candidateUserList.size()>0){
				for(IdentityLink identityLink:candidateUserList){
					if(identityLink.getUserId().equals(user.getUsername())){
						candidateUserMap.put("belong",true);
					}

				}
			}


		}catch (Exception e) {
			//ignor
			//logger.error("getHistoric exception",e);
		}
		map.put("candidateUserMap", candidateUserMap);
		map.put("startUser", startUser);
		// 最后审核状态
		map.put("endStatus", endStatus);
		map.put("historicActivityInstance", historicActivityInstance);
		map.put("commentMap", commentMap);
		return map;
	}

	@Override
	public int updateInfo(String tableName, String id, Integer idValue, String key, Integer value,Integer db) {
	    if(db == 1){
		return actionProcdefMapper.updateInfo(tableName,id,idValue,key,value);
	    }else{
		// 接口调用
		String url = httpUrl + "verifiesrecord/updateinfo.htm";
		Map<String, Object> variableMap = new HashMap<String, Object>();
		variableMap.put("tableName", tableName);
		variableMap.put("id", id);
		variableMap.put("idValue", idValue);
		variableMap.put("key", key);
		variableMap.put("value", value);
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Integer>> TypeRef2 = new TypeReference<ResultInfo<Integer>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, variableMap, clientId, clientKey,
							TypeRef2);
			if(result.getCode() == 0){
			    return 1;
			}else{
			    return -1;
			}
		} catch (IOException e) {
			return -1;
		}
	    }
	}
	
	public int updateVerifyInfo(String tableName,Integer idValue,Integer value) {
		// 接口调用
		String url = httpUrl + "verifiesrecord/updateverifyinfo.htm";
		Map<String, Object> variableMap = new HashMap<String, Object>();
		variableMap.put("tableName", tableName);
		variableMap.put("idValue", idValue);
		variableMap.put("value", value);
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Integer>> TypeRef2 = new TypeReference<ResultInfo<Integer>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, variableMap, clientId, clientKey,
							TypeRef2);
			if(result.getCode() == 0){
			    return 1;
			}else{
			    return -1;
			}
		} catch (IOException e) {
			return -1;
		}
	}

	@Override
	public void addCandidate(String taskId, List<String> userName) {
		if(CollectionUtils.isEmpty(userName)){
			return;
		}
//		HistoryService historyService = this.getProcessEngine().getHistoryService(); // 任务相关service
		TaskService taskService = this.getProcessEngine().getTaskService();
		for(String n:userName) {
			taskService.addCandidateUser(taskId, n);
		}
	}

	@Override
	public ResultInfo<?> complementTask(HttpServletRequest request, String taskId, String comment,String assignee,
		Map<String, Object> variables) {
			logger.info("================开始完成审核节点===taskId:"+taskId+"===comment:"+comment+"===assignee:"+assignee+"================");
		User user = null;
		if(request!=null) {
			user=(User) request.getSession().getAttribute(ErpConst.CURR_USER);
		}else{
			user=new User();
			user.setUserId(2);
			user.setUsername("njadmin");
			user.setCompanyId(1);
		}
		HistoryService historyService = this.getProcessEngine().getHistoryService(); // 任务相关service
		TaskService taskService = this.getProcessEngine().getTaskService();
		// 使用任务id,获取任务对象，获取流程实例id
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		if(task == null){
			return new ResultInfo<>(-1, "流程此节点已操作完成，请确认");
		}
		// 利用任务对象，获取流程实例id
		String processInstancesId = task.getProcessInstanceId();
		if(comment != null){
		    taskService.addComment(taskId, processInstancesId, comment);
		}
		if(assignee != null){
		    taskService.setAssignee(taskId, assignee);
		}
		try {
			taskService.complete(taskId, variables);
			//logger.info("================完成审核节点===taskId:"+taskId+"===comment:"+comment+"===assignee:"+assignee+"================");
			List<HistoricActivityInstance> hia = historyService.createHistoricActivityInstanceQuery()
					.processInstanceId(processInstancesId).orderByHistoricActivityInstanceStartTime().asc().list();
			String endStatus = hia.get(hia.size() - 1).getActivityType();
//			//打印历史节点日志
//			for(HistoricActivityInstance hiai:hia){
//				logger.info("================历史审核节点对应信息===taskId:"+taskId+"=================ActivityType:"+(hiai!=null ? hiai.getActivityType() : ""));
//			}
//            logger.info("================结束完成审核节点===taskId:"+taskId+"===comment:"+comment+"===assignee:"+assignee+"=====endStatus:"+endStatus+"===========");
			return new ResultInfo(0, "操作成功",endStatus);
		} catch (Exception e) {
			return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
		}
	}

	@Override
	public ResultInfo<?> createProcessInstance(HttpServletRequest request, String processDefinitionKey,
		String businessKey, Map<String, Object> variables)throws Exception {
	    /**
	     * 流程名称saleorder+variables+businessKey
	     */
		TaskService taskService = this.getProcessEngine().getTaskService(); // 任务相关service
		Task taskInfo = taskService.createTaskQuery().processInstanceBusinessKey(businessKey)
				.singleResult();
		if(taskInfo != null){
			throw new Exception("流程已发起请勿重复提交");
		}else{

			User user = null;
			if(request!=null) {
				user=(User) request.getSession().getAttribute(ErpConst.CURR_USER);
			}else{
				user=new User();
				user.setUserId(2);
				user.setUsername("njadmin");
				user.setCompanyId(1);
			}
			IdentityService identityService = this.getProcessEngine().getIdentityService();
			identityService.setAuthenticatedUserId(user.getUsername());
			// 设置当前审核人(订单归属人)
			RuntimeService runtimeService = this.getProcessEngine().getRuntimeService();// 运行时Service
			ProcessInstance processInstance;
			try {
				processInstance = runtimeService.startProcessInstanceByKeyAndTenantId(processDefinitionKey,
						businessKey, variables, "erp:company:" + user.getCompanyId());
				return new ResultInfo(0, "操作成功");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
			}
		}

	}

	@Override
	public Map<String, Object> getVariablesMap(Task taskInfo) {
	    if(null!=taskInfo){
		    //把list转为Map
		    Map<String, Object> variablesMap = new HashMap<String, Object>();
		    try {
			HistoryService historyService = this.getProcessEngine().getHistoryService(); // 任务相关service
			HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskInfo.getId()).singleResult();
			String processInstanceId = historicTaskInstance.getProcessInstanceId();
			List<HistoricVariableInstance> historicVariableInstanceList= historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).list();
			
			for(HistoricVariableInstance hvi:historicVariableInstanceList){
				variablesMap.put(hvi.getVariableName(), hvi.getValue());
			}
			return variablesMap;
		    } catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(Contant.ERROR_MSG, e);
			return variablesMap;
		    }
	    	    
	    }else{
		return null;
	    }
	}
	
	@Override
	public Map<String, Object> getVariablesMap(String businessKey) {
	    if(null!=businessKey){
		    //把list转为Map
		    Map<String, Object> variablesMap = new HashMap<String, Object>();
		    try {
			HistoryService historyService = this.getProcessEngine().getHistoryService(); // 任务相关service
			List<HistoricProcessInstance> historicProcessInstance = new ArrayList<>();
			String processInstanceId = null;
			historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
	 			.processInstanceBusinessKey(businessKey).orderByProcessInstanceStartTime().asc().list();
                        	 // 如果不为空，循环获取每个审核流程中的记录
                        	 if (null != historicProcessInstance && !historicProcessInstance.isEmpty()) {
                        	 	for (HistoricProcessInstance hi : historicProcessInstance) {
                        	 	List<HistoricActivityInstance> hia = historyService.createHistoricActivityInstanceQuery()
                        	 					.processInstanceId(hi.getId()).orderByHistoricActivityInstanceStartTime().asc().list();
                        	 		processInstanceId = hi.getId();
                        	 	}
                        	 	
                        	 }
                        if(processInstanceId != null){
                            List<HistoricVariableInstance> historicVariableInstanceList= historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).list();
                            for(HistoricVariableInstance hvi:historicVariableInstanceList){
                        	variablesMap.put(hvi.getVariableName(), hvi.getValue());
                            }
                        }
			return variablesMap;
		    } catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(Contant.ERROR_MSG, e);
			return variablesMap;
		    }
	    	    
	    }else{
		return null;
	    }
	}

	@Override
	public ResultInfo deleteProcessInstance(String taskId){
		if(StringUtil.isNotBlank(taskId)){
			try {
				HistoryService historyService = this.getProcessEngine().getHistoryService(); // 任务相关service
				HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
				String processInstanceId = historicTaskInstance.getProcessInstanceId();
				RuntimeService runtimeService = this.getProcessEngine().getRuntimeService();
				runtimeService.suspendProcessInstanceById(processInstanceId);
				runtimeService.deleteProcessInstance(processInstanceId,"销售撤回");
				return new ResultInfo(0,"操作成功");
			}catch (Exception ex){
				logger.error(Contant.ERROR_MSG,ex);
				return new ResultInfo(-1,"操作失败");
			}

		}
		return new ResultInfo(-1,"操作失败,任务id为空");
	}
}
