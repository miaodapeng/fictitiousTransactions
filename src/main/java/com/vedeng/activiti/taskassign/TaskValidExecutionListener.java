package com.vedeng.activiti.taskassign;

import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.vedeng.activiti.service.ActionProcdefService;
import com.vedeng.system.service.VerifiesRecordService;

public class TaskValidExecutionListener implements ExecutionListener {
    // 运行时注入service
    WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
    private ActionProcdefService actionProcdefService = (ActionProcdefService) context.getBean("actionProcdefService");
    
    private VerifiesRecordService verifiesRecordService = (VerifiesRecordService)context.getBean("verifiesRecordService");
    @Resource
    private WebServiceContext webServiceContext;
    //根据穿参通用回写主表中状态
    public void notify(DelegateExecution execution) throws Exception {
	      //审核完成后修主表参数状态
        	if((String) execution.getVariable("tableName") != null){
        	    String tableName = (String) execution.getVariable("tableName");
        	    String id = (String) execution.getVariable("id");
        	    Integer idValue = (Integer) execution.getVariable("idValue");
        	    String key = (String) execution.getVariable("key");
        	    Integer value = (Integer) execution.getVariable("value");
        	    Integer db = (Integer) execution.getVariable("db");
        	    actionProcdefService.updateInfo(tableName, id, idValue, key, value,db);
        	    if(null != execution.getVariable("key1")){
        		String key1 = (String) execution.getVariable("key1");
            	    Integer value1 = (Integer) execution.getVariable("value1");
            	    actionProcdefService.updateInfo(tableName, id, idValue, key1, value1,db);
        	    }
        	}
	      //添加审核对应主表的审核状态
              if((Integer) execution.getVariable("verifyStatus") != null){
        	  verifiesRecordService.saveVerifiesInfo(execution.getId(),(Integer) execution.getVariable("verifyStatus"));
              }else{
        	  verifiesRecordService.saveVerifiesInfo(execution.getId(),1);
              }
    }
}
/**
 * 
 * @author John
 *
 */
