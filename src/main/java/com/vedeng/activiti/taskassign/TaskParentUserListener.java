package com.vedeng.activiti.taskassign;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.vedeng.authorization.model.User;
import com.vedeng.order.model.Quoteorder;
import com.vedeng.system.service.UserService;

/**
 * 
 * @author John
 *
 */
public class TaskParentUserListener implements TaskListener{
	private static final long serialVersionUID = 1L;

	/*private FixedValue myName; // 监听注入属性
	private FixedValue dynamicName; // 监听注入属性
	private FixedValue dynamicName2; // 监听注入属性
*/	
	// 运行时注入service
	WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
	private UserService userService = (UserService) context.getBean("userService");
	
	/*@Autowired // 自动装载
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();*/
	

	public void notify(DelegateTask delegateTask) {
		// 获取从上一步流程的办理人，用作查找他的上级作为本流程的办理人
		String preAssignee = delegateTask.getVariable("currentAssinee").toString();
		Quoteorder quoteorder = (Quoteorder) delegateTask.getVariable("quoteorder");
		// delegateTask.setAssignee(preAssignee); // 指定办理人
		User assigneeObj = userService.getUserParentInfo(preAssignee, quoteorder.getCompanyId());
		if (assigneeObj != null && assigneeObj.getpUsername() != null) {
			String assignee = assigneeObj.getpUsername();
			delegateTask.setAssignee(assignee); // 指定办理人
//	 			System.out.println("John TaskId:" + assignee);
		}
//	 		String myName = (String) this.myName.getValue(delegateTask);
//	 		String dynamicName = (String) this.dynamicName.getValue(delegateTask);
//	 		String dynamicName2 = (String) this.dynamicName2.getValue(delegateTask);
//	 		System.out.println("John TaskId:" + myName + ":" + dynamicName + ":" + dynamicName2);
		
	}

}