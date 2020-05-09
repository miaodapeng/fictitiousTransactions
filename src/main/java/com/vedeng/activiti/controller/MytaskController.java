package com.vedeng.activiti.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.vedeng.activiti.model.TaskSearchForm;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.page.Page;

@Controller
@RequestMapping("/activiti/mytask/")
public class MytaskController extends BaseController {

	@Autowired // 自动装载
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	// @Autowired
	// @Qualifier("processDefinitionService")
	// private ProcessDefinitionService processDefinitionService;

	/**
	 * 查询流程定义，返回流程定义集合，对应ACT_RE_MODEL
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "list", method = {}, produces = { "text/html" })
	public ModelAndView list(TaskSearchForm searchForm, HttpServletRequest request,
			HttpServletResponse respons, @RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize) {

		TaskQuery taskQuery = processEngine.getTaskService() // 任务相关service
				.createTaskQuery();// 创建任务查询

		User user= (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		System.out.println(user.getUsername() + ":" + user.getUserId());
		
		// 表单筛选
		if (StringUtils.isNotBlank(searchForm.getId())) {
			taskQuery.taskId(searchForm.getId());
		}
		if (StringUtils.isNotBlank(searchForm.getName())) {
			taskQuery.taskNameLike("%" + searchForm.getName() + "%");
		}
		if (StringUtils.isNotBlank(searchForm.getAssignee())) {
			taskQuery.taskAssigneeLike("%" + searchForm.getAssignee() + "%");
		}
		//查当前用户
		taskQuery.taskAssignee(user.getUsername());//指定处理人
		//查当前分公司
		if(user.getCompanyId().toString() != null && user.getCompanyId() != 0){
			taskQuery.taskTenantId("erp:company:" + user.getCompanyId().toString());//指定分公司为租户
		}
		//排序
		taskQuery.orderByTaskCreateTime().desc();
		// 记录总数
		long dataTotal = taskQuery.count();// 结果集数

		// 分页对象
		Page page = getPageTag(request, pageNo, pageSize);
		// page.setPageSize(20);//分页设置
		page.setTotalRecord(Integer.valueOf(((Long) dataTotal).toString()));
		Integer start = (page.getPageNo() - 1) * page.getPageSize();

		List<Task> resultList = taskQuery// 用户任务
				.listPage(start, page.getPageSize());

		// 模板对象
		ModelAndView mv = new ModelAndView();
		// 模板赋值
		mv.addObject("list", resultList);
		mv.addObject("dataTotal", dataTotal);
		mv.addObject("searchForm", searchForm);

		mv.addObject("page", page);

		
		return mv;

	}

	/**
	 * 删除流程定义 string格式：(deploymentId) id="process:2:107504"
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delete", method = {
			org.springframework.web.bind.annotation.RequestMethod.POST }, produces = { "application/json" })
	public JSONObject delete(String id, HttpServletRequest request, HttpServletResponse respons) {

		TaskService taskQuery = processEngine.getTaskService();

		JSONObject result = new JSONObject();
		try {
			taskQuery.deleteTask(id,true);
			result.put("code", 0);
			result.put("message", "删除任务成功");
			result.put("type", "success");
		} catch (Exception e) {
			logger.error("delete my task error", e);
			result.put("code", -1);
			result.put("message", "删除任务失败：" + e.getMessage());
		}
		

		return result;
	}

	/**
	 * 删除流程定义 string格式：(deploymentId) id="process:2:107504"
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "complete", method = {
			org.springframework.web.bind.annotation.RequestMethod.POST }, produces = { "application/json" })
	public JSONObject complete(String id, HttpServletRequest request, HttpServletResponse respons) {

		TaskService taskQuery = processEngine.getTaskService();

		Map<String, Object> variables = new HashMap<String, Object>();
		//variables.put("days", 15);
		
		JSONObject result = new JSONObject();
		try {
			//taskQuery.deleteTask(id,true);
			taskQuery.complete(id, variables);// 完成任务时设置变量
			result.put("code", 0);
			result.put("message", "任务完成操作成功");
			result.put("type", "success");
		} catch (Exception e) {
			logger.error("complete my task error", e);
			result.put("code", -1);
			result.put("message", "任务完成操作失败：" + e.getMessage());
		}
		

		return result;
	}
}
