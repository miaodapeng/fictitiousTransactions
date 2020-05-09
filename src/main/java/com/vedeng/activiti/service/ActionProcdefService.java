package com.vedeng.activiti.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.task.Task;

import com.vedeng.activiti.model.ActionProcdef;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.logistics.model.FileDelivery;

/**
 * 
 * @author John
 *
 */
public interface ActionProcdefService {
	
	/**
	 * 
	 * @return
	 */
	List<ActionProcdef> getList();

	/**
	 * 
	 * @param actionProcdef
	 * @return
	 */
	List<ActionProcdef> getList(ActionProcdef actionProcdef);

	/**
	 * 
	 * @param companyId
	 * @return
	 */
	List<ActionProcdef> getList(int companyId);

	/**
	 * 
	 * @param actionId
	 * @param companyId
	 * @return
	 */
	List<ActionProcdef> getByAcitonId(int actionId, int companyId);

	/**
	 * 
	 * @param actionId
	 * @return
	 */
	List<ActionProcdef> getByAcitonId(int actionId);

	/**
	 * 
	 * @param preBusinessKey
	 * @param companyId
	 * @return
	 */
	List<ActionProcdef> getByPreBusinessKey(String preBusinessKey, int companyId);

	/**
	 * 
	 * @param preBusinessKey
	 * @return
	 */
	List<ActionProcdef> getByPreBusinessKey(String preBusinessKey);

	/**
	 * 
	 * @param procdefId
	 * @param companyId
	 * @return
	 */
	List<ActionProcdef> getByProcdefId(String procdefId, int companyId);

	/**
	 * 
	 * @param procdefId
	 * @return
	 */
	List<ActionProcdef> getByProcdefId(String procdefId);

	/**
	 * 
	 * @param actionProcdef
	 * @return
	 */
	int insert(ActionProcdef actionProcdef);

	/**
	 * 
	 * @param actionProcdef
	 * @return
	 */
	int replace(ActionProcdef actionProcdef);
	
	/**
	 * 
	 * @return
	 */
	List<ActionProcdef> getListPage(ActionProcdef actionProcdef,Page page);
	/**
	 * 
	 * @param page
	 * @return
	 */
	List<ActionProcdef> getListPage(Page page);
	/**
	 * 
	 * <b>Description:</b><br> 
	 * @param businessKey
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2017年11月15日 下午3:49:51
	 */
	Map<String, Object> getHistoric(ProcessEngine processEngine,String businessKey);


	Map<String, Object> getHistoricForNjadmin(ProcessEngine processEngine,String businessKey);


	/**
	 * 
	 * <b>Description:</b><br> 更新主表数据
	 * @param tableName
	 * @param id
	 * @param idValue
	 * @param key
	 * @param value
	 * @param db 1 erp,2db
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2017年12月4日 下午3:33:42
	 */
	int updateInfo(String tableName,String id,Integer idValue,String key,Integer value,Integer db);
	/**
	 * 
	 * <b>Description:</b><br> 更新主表数据
	 * @param tableName
	 * @param id
	 * @param idValue
	 * @param key
	 * @param value
	 * @param db 1 erp,2db
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2017年12月4日 下午3:33:42
	 */
	int updateVerifyInfo(String tableName,Integer idValue,Integer value);
	/**
	 * 
	 * <b>Description:</b><br> 审核操作
	 * @param request
	 * @param taskId 
	 * @param comment 备注信息
	 * @param assignee 审核人（非必填可以用null）
	 * @param variables 需要传的参数集合Map
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2017年11月28日 下午6:18:52
	 */
	ResultInfo<?> complementTask(HttpServletRequest request, String taskId, String comment,String assignee,Map<String, Object> variables);
	/**
	 * 
	 * <b>Description:</b><br> 创建流程实例
	 * @param request
	 * @param processDefinitionKey 流程实例定义名称
	 * @param businessKey 流程实例定义名称_业务ID
	 * @param variables 需要传的参数集合Map
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2017年11月28日 下午6:19:42
	 */
	ResultInfo<?> createProcessInstance(HttpServletRequest request,String processDefinitionKey, String businessKey, Map<String, Object> variables) throws Exception;
	/**
	 * 
	 * <b>Description:</b><br> 获取流程储存变量
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2018年1月24日 上午10:33:04
	 */
	Map<String, Object> getVariablesMap(Task taskInfo);
	/**
	 * 
	 * <b>Description:</b><br> 获取流程储存变量
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2018年1月24日 上午10:33:04
	 */
	Map<String, Object> getVariablesMap(String businessKey);


	/**
	 * <b>Description:</b>为任务添加候选人<br>
	 * @param
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:</b> 2019/12/26
	 */
	void addCandidate(String taskId,List<String> userName);

	/**
	 * <b>Description:</b>根据任务id,删除任务实例<br>
	 * @param taskId 任务id
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:</b> 2020/2/25
	 */
	ResultInfo deleteProcessInstance(String taskId);
}
