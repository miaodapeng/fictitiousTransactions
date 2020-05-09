package com.vedeng.activiti.service;

import java.io.OutputStream;
import java.util.List;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;


/**
 * 
 * @author John
 *
 */
public interface FlowchartService {
	/** 
	 * 流程跟踪图片 
	 *  
	 * @param processDefinitionId 
	 *            流程定义ID 
	 * @param executionId 
	 *            流程运行ID 
	 * @param out 
	 *            输出流 
	 * @throws Exception 
	 */  
	public void processTracking(String processDefinitionId, String executionId, OutputStream out) throws Exception;
	
	/** 
	 * 流程是否已经结束 
	 *  
	 * @param processInstanceId 流程实例ID 
	 * @return 
	 */  
	public boolean isFinished(String processInstanceId);
	
	/** 
	 * 获得高亮线 
	 *  
	 * @param processDefinitionEntity 
	 *            流程定义实体 
	 * @param historicActivityInstances 
	 *            历史活动实体 
	 * @return 线ID集合 
	 */  
	public List<String> getHighLightedFlows(ProcessDefinitionEntity processDefinitionEntity,
			List<HistoricActivityInstance> historicActivityInstances);
}
