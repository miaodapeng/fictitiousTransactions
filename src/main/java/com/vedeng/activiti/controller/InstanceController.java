package com.vedeng.activiti.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.constants.Contant;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ExecutionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.vedeng.activiti.model.HistorySearchForm;
import com.vedeng.authorization.model.Company;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.page.Page;
import com.vedeng.system.service.CompanyService;

@Controller
@RequestMapping("/activiti/instance/")
public class InstanceController extends BaseController {
	public static Logger logger = LoggerFactory.getLogger(InstanceController.class);

	@Autowired // 自动装载
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	// @Autowired
	// @Qualifier("processDefinitionService")
	// private ProcessDefinitionService processDefinitionService;

	@Autowired // 自动装载
	private CompanyService companyService;

	/**
	 * 查询流程实例，返回流程实例集合
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "list", method = {}, produces = { "text/html" })
	public ModelAndView list(HistorySearchForm searchForm, HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize) {

		RuntimeService runtimeService = processEngine.getRuntimeService();// 任务相关service
		ProcessInstanceQuery instanceQuery = runtimeService.createProcessInstanceQuery();// 创建历史实例查询

		// 表单筛选
		if (StringUtils.isNotBlank(searchForm.getId())) {
			instanceQuery.processInstanceId(searchForm.getId());// 通过定义id查询
		}
		if (StringUtils.isNotBlank(searchForm.getName())) {
			instanceQuery.processInstanceNameLike("%" + searchForm.getName() + "%");
		}
		if (StringUtils.isNotBlank(searchForm.getTenantId()) && !searchForm.getTenantId().equals("0")) {
			instanceQuery.processInstanceTenantId(searchForm.getTenantId());
		}
		if (StringUtils.isNotBlank(searchForm.getBusinessKey())) {
			instanceQuery.processInstanceBusinessKey(searchForm.getBusinessKey());
		}
		
		// 业务绑定标识，比如订单审核 status.order.id.12,表示处理订单id号为12的审核状态字段为status
		// String processInstanceBusinessKey = null;
		// instanceQuery.processInstanceBusinessKey(processInstanceBusinessKey);

		// 排序
		instanceQuery.orderByProcessInstanceId().desc();
		// 记录总数
		long dataTotal = instanceQuery.count();// 结果集数

		// 分页对象
		Page page = getPageTag(request, pageNo, pageSize);
		// page.setPageSize(20);//分页设置
		page.setTotalRecord(Integer.valueOf(((Long) dataTotal).toString()));
		Integer start = (page.getPageNo() - 1) * page.getPageSize();

		List<ProcessInstance> resultList = instanceQuery// 历史流程实例
				.listPage(start, page.getPageSize());

		// 补变量信息
		List<Map<String, Object>> applyUserList = new ArrayList<>();

		for (ProcessInstance result : resultList) {
			ExecutionQuery executionQuery = runtimeService.createExecutionQuery();
			List<Execution> executionList = executionQuery.processInstanceId(result.getId()).list();

			String executionId = null;

			for (Execution execution : executionList) {
				executionId = execution.getId();
				break;
			}

			Map<String, Object> newMap = new HashMap<String, Object>();
			
			String applyUser = null;
			if (executionId != null) {
				try {
					Object applyUserObj = runtimeService.getVariable(executionId, "applyUser");
					if (applyUserObj != null) {
						applyUser = applyUserObj.toString();
					}
				} catch (Exception e) {
					logger.error(Contant.ERROR_MSG, e);
				}
			}

			if (applyUser != null) {
				newMap.put("applyUser", applyUser);
				System.out.println(applyUser);
			} else {
				newMap.put("applyUser", "");
			}
			applyUserList.add(newMap);
		}

		// 模板对象
		ModelAndView mv = new ModelAndView();
		try {
			List<Company> companyList = companyService.getAll();
			mv.addObject("companyList", companyList);
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		// 模板赋值
		mv.addObject("list", resultList);
		mv.addObject("applyUserList", applyUserList);
		mv.addObject("dataTotal", dataTotal);
		mv.addObject("searchForm", searchForm);

		mv.addObject("page", page);

		return mv;

	}

	/**
	 * 删除流程实例 string格式：(processInstanceId) id="1234"
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delete", method = {
			org.springframework.web.bind.annotation.RequestMethod.POST }, produces = { "application/json" })
	public JSONObject delete(String id, HttpServletRequest request, HttpServletResponse respons) {

		RuntimeService runtimeService = processEngine.getRuntimeService();

		JSONObject result = new JSONObject();
		try {
			runtimeService.deleteProcessInstance(id, "流程取消");
			result.put("code", 0);
			result.put("message", "删除流程实例成功");
			result.put("type", "success");
		} catch (Exception e) {
			logger.error("delete instance error", e);
			result.put("code", -1);
			result.put("message", "删除流程实例失败：" + e.getMessage());
		}

		return result;
	}

}
