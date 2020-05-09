package com.vedeng.activiti.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.constants.Contant;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vedeng.activiti.service.FlowchartService;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.controller.BaseController;

@Controller
@RequestMapping("/activiti/flowchart/")
public class FlowchartController extends BaseController {
	public static Logger logger = LoggerFactory.getLogger(FlowchartController.class);

	@Autowired // 自动装载
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	@Autowired
	@Qualifier("flowchartService")
	private FlowchartService flowchartService;

	/**
	 * 流程是否已经结束
	 * 
	 * @param processInstanceId
	 *            流程实例ID
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "tracking", method = {}, produces = {})
	public void tracking(String piid, String tid, HttpServletRequest request, HttpServletResponse response) {
		// 当前用户
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		// System.out.println(user.getUsername() + ":" + user.getUserId());

		TaskQuery taskQuery = processEngine.getTaskService() // 任务相关service
				.createTaskQuery();// 创建历史实例查询
		if (StringUtils.isNotBlank(tid)) {
			taskQuery.taskId(tid);
		} else if (StringUtils.isNotBlank(piid)) {
			taskQuery.processInstanceId(piid);
		} else {
			taskQuery.taskAssignee(user.getUsername());
		}

		// 结果
		Task task = taskQuery.singleResult();
		if (task != null) {
			try {
				// FlowchartService flowchartService = new
				// FlowchartServiceImpl();
				// 直接向流浏览器输出图片
				OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
				flowchartService.processTracking(task.getProcessDefinitionId(), task.getExecutionId(), outputStream);
			} catch (Exception e) {
				logger.error("tracking 1:", e);
			}
		} else {
			try {
				response.sendRedirect(request.getContextPath() + "/activiti/definition/noimage.do?message="
						+ URLEncoder.encode("设计图数据已被删除", "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				logger.error(Contant.ERROR_MSG, e);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error(Contant.ERROR_MSG, e);
			}
		}
	}

}
