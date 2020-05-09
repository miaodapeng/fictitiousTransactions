package com.vedeng.system.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.constants.Contant;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.activiti.service.ActionProcdefService;
import com.vedeng.authorization.dao.UserMapper;
import com.vedeng.authorization.model.User;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.DateUtil;
import com.vedeng.system.dao.VerifiesInfoMapper;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.model.VerifiesInfo;
import com.vedeng.system.model.VerifiesRecord;
import com.vedeng.system.service.VerifiesRecordService;

@Service("verifiesRecordService")
public class VerifiesRecordServiceImpl extends BaseServiceimpl implements VerifiesRecordService {
	public static Logger logger = LoggerFactory.getLogger(VerifiesRecordServiceImpl.class);

	@Autowired
	@Qualifier("userMapper")
	private UserMapper userMapper;
	
	@Autowired
	@Qualifier("actionProcdefService")
	private ActionProcdefService actionProcdefService;
	
	@Autowired
	@Qualifier("verifiesInfoMapper")
	private VerifiesInfoMapper verifiesInfoMapper;
	
	private ProcessEngine getProcessEngine() {
		return ProcessEngines.getDefaultProcessEngine();// 流程引擎
	}


	@Override
	public List<VerifiesRecord> getVerifiesRecord(VerifiesRecord verifiesRecord) {
		String url = httpUrl + "verifiesrecord/getverifiesrecord.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<VerifiesRecord>>> TypeRef2 = new TypeReference<ResultInfo<List<VerifiesRecord>>>() {};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, verifiesRecord,clientId,clientKey, TypeRef2);
			List<VerifiesRecord> optionList = (List<VerifiesRecord>) result2.getData();
			
			if(optionList != null && optionList.size() > 0){
				List<Integer> userId = new ArrayList<>();
				for(VerifiesRecord record : optionList){
					if(record.getCreator() > 0){
						userId.add(record.getCreator());
					}
				}
				
				if(userId.size() > 0){
					List<User> userList = userMapper.getUserByUserIds(userId);
					if(userList.size() > 0){
						for(VerifiesRecord record : optionList){
							for(User user : userList){
								if(record.getCreator().equals(user.getUserId())){
									record.setUsername(user.getUsername());
								}
							}
						}
					}
				}
				
				return optionList;
			}
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public Map<String, Object> getVerifiesRecordListPage(VerifiesRecord verifiesRecord, Page page) {
		List<VerifiesRecord> list = null;
		Map<String,Object> map = new HashMap<>();
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<VerifiesRecord>>> TypeRef = new TypeReference<ResultInfo<List<VerifiesRecord>>>() {};
			String url=httpUrl + "verifiesrecord/getverifiesrecordlistpage.htm";
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, verifiesRecord,clientId,clientKey, TypeRef,page);
			list = (List<VerifiesRecord>) result.getData();
			page = result.getPage();
			
			map.put("list", list);
			map.put("page", page);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return map;
	}
	/**
	 * 添加审核对应主表的审核状态
	 * 申请审核 审核通过
	 */
	@Override
	public ResultInfo<?> saveVerifiesInfo(String taskId,Integer status) {
		TaskService taskService = this.getProcessEngine().getTaskService();
		HistoryService historyService = this.getProcessEngine().getHistoryService(); // 任务相关service
		String processInstanceId = null;
		if(status == 1 ){
			 processInstanceId = taskId;
		}else{
			HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
			 processInstanceId = historicTaskInstance.getProcessInstanceId();
		}
		logger.info("================开始添加审核对应主表的审核状态===taskId:"+taskId+"===status:"+status+"===processInstanceId:"+processInstanceId+"================");

		HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		String businessKey = historicProcessInstance.getBusinessKey();
		List<HistoricVariableInstance> historicVariableInstanceList= historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).list();
		//把list转为Map
		Map<String, Object> variables = new HashMap<String, Object>();
		for(HistoricVariableInstance hvi:historicVariableInstanceList){
			variables.put(hvi.getVariableName(), hvi.getValue());
		}
		Map<String, Object> historicInfo=actionProcdefService.getHistoric(this.getProcessEngine(), businessKey);
		Task taskInfo = (Task) historicInfo.get("taskInfo");

		VerifiesInfo verifiesInfo = new VerifiesInfo();
		verifiesInfo.setRelateTable((String) variables.get("relateTable"));
		verifiesInfo.setRelateTableKey((Integer) variables.get("relateTableKey"));
		//审核中
		verifiesInfo.setStatus(status);
		Map<String, Object> candidateUserMap = (Map<String, Object>) historicInfo.get("candidateUserMap");
			//获取审核人候选组
			List<IdentityLink> candidateUserList = new ArrayList<>();
        		try {
        		    if(null != taskInfo){
        			candidateUserList = (List<IdentityLink>) candidateUserMap.get(taskInfo.getId());
        		    }
        		} catch (Exception e) {
        		  
        		} 
		//如果审核人候选组不为空的时候
		if(!candidateUserList.isEmpty()){
		    List<String> varifyUserList = new ArrayList<>();
		    String verifyUsername = null;
		    for(IdentityLink il:candidateUserList){
			varifyUserList.add(il.getUserId());
		    }
		    //都好拼接审核人候选组
		    if(!varifyUserList.isEmpty()){
			verifyUsername = String.join(",",varifyUserList);
		    }
		    verifiesInfo.setVerifyUsername(verifyUsername);
		}else{
		    if(null!= taskInfo && null!= taskInfo.getAssignee() && taskInfo.getAssignee()!=""){
			verifiesInfo.setVerifyUsername(taskInfo.getAssignee());
		    }else{
			verifiesInfo.setVerifyUsername("");
		    }
		}
		// 获取当前活动节点
		Task newTaskInfo = taskService.createTaskQuery().processInstanceBusinessKey(businessKey)
		 		.singleResult();
		logger.info("================添加审核对应主表的审核状态===taskId:"+taskId+"===businessKey:"+businessKey+"===newTaskInfo:"+newTaskInfo+"===newTaskId:"+(newTaskInfo==null?"":newTaskInfo.getId())+"=============");
		//
		taskService.setVariable(newTaskInfo.getId(), "verifyUsers", verifiesInfo.getVerifyUsername());
		//获取字典表中审核流程类型
		String processDefinitionKey= historicProcessInstance.getProcessDefinitionKey();
		List<SysOptionDefinition> sysOptionDefinitionList= getSysOptionDefinitionList(610);
		if(null !=variables.get("verifiesType")){
		    verifiesInfo.setVerifiesType((Integer) variables.get("verifiesType"));
		}else{
		    for(SysOptionDefinition s:sysOptionDefinitionList){
			if(s.getComments().equals(processDefinitionKey)){
			    verifiesInfo.setVerifiesType(s.getSysOptionDefinitionId());
			}
		    }
		}
		verifiesInfo.setAddTime(DateUtil.sysTimeMillis());
		verifiesInfo.setModTime(DateUtil.sysTimeMillis());
		
		String url = httpUrl + "verifiesrecord/saveverifiesinfo.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<VerifiesInfo>>> TypeRef2 = new TypeReference<ResultInfo<List<VerifiesInfo>>>() {};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, verifiesInfo,clientId,clientKey, TypeRef2);
			logger.info("================结束添加审核对应主表的审核状态===taskId:"+taskId+"===status:"+status+"===processInstanceId:"+processInstanceId+"================");
			return result;
		} catch (IOException e) {
			return new ResultInfo<>();
		}
	}
	/**
	 * 根据关联ID，关联表，审核类型获取审核关联记录
	 */
	@Override
	public List<VerifiesInfo> getVerifiesList(VerifiesInfo verifiesInfo) {
	    	List<VerifiesInfo> list = new ArrayList<>();
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<VerifiesInfo>>> TypeRef = new TypeReference<ResultInfo<List<VerifiesInfo>>>() {};
			String url=httpUrl + "verifiesrecord/getverifieslist.htm";
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, verifiesInfo,clientId,clientKey, TypeRef);
			list = (List<VerifiesInfo>) result.getData();
			
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
	}

	@Override
	public Integer updateBDStatus(Integer saleorderId) {
		return verifiesInfoMapper.deleteVerifiesInfo(saleorderId, "T_SALEORDER");
	}

	@Override
	public ResultInfo<?> saveVerifiesInfoForTrader(String taskId, int status) {
		TaskService taskService = this.getProcessEngine().getTaskService();
		HistoryService historyService = this.getProcessEngine().getHistoryService(); // 任务相关service
		String processInstanceId = null;
//		if(status == 1 || status==2){
//			processInstanceId = taskId;
//		}else{
			HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
			processInstanceId = historicTaskInstance.getProcessInstanceId();
		//}
		logger.info("================开始添加审核对应主表的审核状态===taskId:"+taskId+"===status:"+status+"===processInstanceId:"+processInstanceId+"================");

		HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		String businessKey = historicProcessInstance.getBusinessKey();
		List<HistoricVariableInstance> historicVariableInstanceList= historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).list();
		//把list转为Map
		Map<String, Object> variables = new HashMap<String, Object>();
		for(HistoricVariableInstance hvi:historicVariableInstanceList){
			variables.put(hvi.getVariableName(), hvi.getValue());
		}
		Map<String, Object> historicInfo=actionProcdefService.getHistoric(this.getProcessEngine(), businessKey);
		Task taskInfo = (Task) historicInfo.get("taskInfo");

		VerifiesInfo verifiesInfo = new VerifiesInfo();
		verifiesInfo.setRelateTable((String) variables.get("relateTable"));
		verifiesInfo.setRelateTableKey((Integer) variables.get("relateTableKey"));
		//审核中
		verifiesInfo.setStatus(status);
		Map<String, Object> candidateUserMap = (Map<String, Object>) historicInfo.get("candidateUserMap");
		//获取审核人候选组
		List<IdentityLink> candidateUserList = new ArrayList<>();
		try {
			if(null != taskInfo){
				candidateUserList = (List<IdentityLink>) candidateUserMap.get(taskInfo.getId());
			}
		} catch (Exception e) {

		}
		//如果审核人候选组不为空的时候
		if(!candidateUserList.isEmpty()){
			List<String> varifyUserList = new ArrayList<>();
			String verifyUsername = null;
			for(IdentityLink il:candidateUserList){
				varifyUserList.add(il.getUserId());
			}
			//都好拼接审核人候选组
			if(!varifyUserList.isEmpty()){
				verifyUsername = String.join(",",varifyUserList);
			}
			verifiesInfo.setVerifyUsername(verifyUsername);
		}else{
			if(null!= taskInfo && null!= taskInfo.getAssignee() && taskInfo.getAssignee()!=""){
				verifiesInfo.setVerifyUsername(taskInfo.getAssignee());
			}else{
				verifiesInfo.setVerifyUsername("");
			}
		}
		// 获取当前活动节点
		Task newTaskInfo = taskService.createTaskQuery().processInstanceBusinessKey(businessKey)
				.singleResult();
		logger.info("================添加审核对应主表的审核状态===taskId:"+taskId+"===businessKey:"+businessKey+"===newTaskInfo:"+newTaskInfo+"===newTaskId:"+(newTaskInfo==null?"":newTaskInfo.getId())+"=============");
		//
		taskService.setVariable(newTaskInfo.getId(), "verifyUsers", verifiesInfo.getVerifyUsername());
		//获取字典表中审核流程类型
		String processDefinitionKey= historicProcessInstance.getProcessDefinitionKey();
		List<SysOptionDefinition> sysOptionDefinitionList= getSysOptionDefinitionList(610);
		if(null !=variables.get("verifiesType")){
			verifiesInfo.setVerifiesType((Integer) variables.get("verifiesType"));
		}else{
			for(SysOptionDefinition s:sysOptionDefinitionList){
				if(s.getComments().equals(processDefinitionKey)){
					verifiesInfo.setVerifiesType(s.getSysOptionDefinitionId());
				}
			}
		}
		verifiesInfo.setAddTime(DateUtil.sysTimeMillis());
		verifiesInfo.setModTime(DateUtil.sysTimeMillis());

		String url = httpUrl + "verifiesrecord/saveverifiesinfo.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<VerifiesInfo>>> TypeRef2 = new TypeReference<ResultInfo<List<VerifiesInfo>>>() {};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, verifiesInfo,clientId,clientKey, TypeRef2);
			logger.info("================结束添加审核对应主表的审核状态===taskId:"+taskId+"===status:"+status+"===processInstanceId:"+processInstanceId+"================");
			return result;
		} catch (IOException e) {
			return new ResultInfo<>();
		}
	}

	@Override
	public ResultInfo saveVerifiesInfoDirect(VerifiesInfo verifiesInfo) {
		if(verifiesInfo==null){
			return new ResultInfo(-1,"操作失败");
		}
		if(verifiesInfo.getVerifiesInfoId()==null){
			verifiesInfoMapper.insertSelective(verifiesInfo);
		}else{
			verifiesInfoMapper.updateByPrimaryKeySelective(verifiesInfo);
		}
		return new ResultInfo(0,"操作成功");
	}

	@Override
	public int deleteVerifiesInfoByRelateKey(Integer relateTableKey, String relateTable) {
		return verifiesInfoMapper.deleteVerifiesInfo(relateTableKey,relateTable);
	}
}
