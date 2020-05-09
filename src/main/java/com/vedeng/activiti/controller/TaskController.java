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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.vedeng.activiti.model.TaskSearchForm;
import com.vedeng.authorization.model.Company;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.page.Page;
import com.vedeng.system.service.CompanyService;

@Controller
@RequestMapping("/activiti/task/")
public class TaskController extends BaseController {

	@Autowired // 自动装载
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	// @Autowired
	// @Qualifier("processDefinitionService")
	// private ProcessDefinitionService processDefinitionService;

	@Autowired // 自动装载
	private CompanyService companyService;
	
	/**
	 * 查询流程定义，返回流程定义集合，对应ACT_RE_MODEL
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "list", method = {}, produces = { "text/html" })
	public ModelAndView list(TaskSearchForm searchForm, HttpServletRequest request, HttpServletResponse respons,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize) {

		TaskQuery taskQuery = processEngine.getTaskService() // 任务相关service
				.createTaskQuery();// 创建任务查询

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
		if (StringUtils.isNotBlank(searchForm.getTenantId()) && !searchForm.getTenantId().equals("0")) {
			taskQuery.taskTenantId(searchForm.getTenantId());
		}
		// 排序
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
		try {
			List<Company> companyList = companyService.getAll();
			mv.addObject("companyList", companyList);
		} catch (Exception e) {
			logger.error("task list:", e);
		}
		// 模板赋值
		mv.addObject("list", resultList);
		mv.addObject("dataTotal", dataTotal);
		mv.addObject("searchForm", searchForm);

		mv.addObject("page", page);

		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		System.out.println(user.getUsername() + ":" + user.getUserId());
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
			taskQuery.deleteTask(id, true);
			result.put("code", 0);
			result.put("message", "删除任务成功");
			result.put("type", "success");
		} catch (Exception e) {
			logger.error("delete task error", e);
			result.put("code", -1);
			result.put("message", "删除任务失败：" + e.getMessage());
		}

		return result;
	}

	/**
	 * 完成任务 string格式：(deploymentId) id="process:2:107504",String pass=true|false
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "complete", method = {
			org.springframework.web.bind.annotation.RequestMethod.GET }, produces = { "text/html" })
	public ModelAndView complete(String tid, String piid, Boolean pass, HttpServletRequest request,
			HttpServletResponse respons) {
		// 模板对象
		ModelAndView mv = new ModelAndView();
		// 透传参数
		Map<String, Object> params = new HashMap<String, Object>();

		// 参数判断
		if (StringUtils.isBlank(tid)) {
			params.put("tid", null);
			params.put("piid", null);
			params.put("pass", pass);
			// 模板赋值
			mv.addObject("params", params);
			mv.addObject("message", "参数错误");
			return mv;
		} else {
			params.put("tid", tid);
			params.put("piid", piid);
			params.put("pass", pass);
			// 模板赋值
			mv.addObject("params", params);
		}
		return mv;
	}

	/**
	 * 完成任务 string格式：(deploymentId) id="process:2:107504",String pass=true|false
	 * ,String reason
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "complete", method = {
			org.springframework.web.bind.annotation.RequestMethod.POST }, produces = { "application/json" })
	public JSONObject complete(@RequestBody Map<String, String> paramsMap, HttpServletRequest request,
			HttpServletResponse respons) {

		TaskService taskService = processEngine.getTaskService();
		JSONObject result = new JSONObject();
		// 参数判断
		if (StringUtils.isBlank(paramsMap.get("tid")) || StringUtils.isBlank(paramsMap.get("piid"))) {
			result.put("code", -1);
			result.put("message", "参数错误。");
			return result;
		}

		if (StringUtils.isNotBlank(paramsMap.get("pass")) && paramsMap.get("pass").equals("false")
				&& StringUtils.isBlank(paramsMap.get("comment"))) {
			result.put("code", -1);
			result.put("message", "备注必填。");
			return result;
		}
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("pass", paramsMap.get("pass"));
		
		//找出当前办理人
		TaskQuery taskQuery = taskService.createTaskQuery();
		Task task = taskQuery.taskId(paramsMap.get("tid")).singleResult();
		variables.put("currentAssinee", task.getAssignee());

		try {
			//添加备注
			if (StringUtils.isNotBlank(paramsMap.get("comment"))) {
				taskService.addComment(paramsMap.get("tid"), paramsMap.get("piid"), paramsMap.get("comment"));
			}
			taskService.complete(paramsMap.get("tid"), variables);// 完成任务时设置变量
			result.put("code", 0);
			result.put("message", "任务完成操作成功");
			result.put("type", "success");
		} catch (Exception e) {
			logger.error("complete task error", e);
			result.put("code", -1);
			result.put("message", "任务完成操作失败：" + e.getMessage());
		}

		return result;
	}
}
