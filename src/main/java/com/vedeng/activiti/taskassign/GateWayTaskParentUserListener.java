package com.vedeng.activiti.taskassign;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.el.FixedValue;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.vedeng.system.service.UserService;

/**
 * 
 * @author John
 *
 */
public class GateWayTaskParentUserListener implements TaskListener {
	private static final long serialVersionUID = 1L;

	private FixedValue myName; // 监听注入属性
	private FixedValue dynamicName; // 监听注入属性

	// 运行时注入service
	WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
	private UserService userService = (UserService) context.getBean("userService");

	public void notify(DelegateTask delegateTask) {
		// TODO Auto-generated method stub
		String myName = (String) this.myName.getValue(delegateTask);
		String dynamicName = (String) this.dynamicName.getValue(delegateTask);

	}

}