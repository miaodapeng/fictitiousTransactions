package com.vedeng.activiti.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.task.Comment;
import org.apache.commons.lang.StringUtils;
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
@RequestMapping("/activiti/history/")
public class HistoryController extends BaseController {

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

		HistoricProcessInstanceQuery historyQuery = processEngine.getHistoryService() // 任务相关service
				.createHistoricProcessInstanceQuery();// 创建历史实例查询

		// 表单筛选
		if (StringUtils.isNotBlank(searchForm.getId())) {
			historyQuery.processInstanceId(searchForm.getId());// 通过定义id查询
		}
		if (StringUtils.isNotBlank(searchForm.getName())) {
			historyQuery.processInstanceNameLike("%" + searchForm.getName() + "%");
		}
		if (StringUtils.isNotBlank(searchForm.getTenantId()) && !searchForm.getTenantId().equals("0")) {
			historyQuery.processInstanceTenantId(searchForm.getTenantId());
		}
		if (StringUtils.isNotBlank(searchForm.getBusinessKey())) {
		    	historyQuery.processInstanceBusinessKey(searchForm.getBusinessKey());
		}
		if (searchForm.getComplete() > 0) {
			if (searchForm.getComplete() == 1) {
				historyQuery.unfinished();
			}
			if (searchForm.getComplete() == 2) {
				historyQuery.finished();
			}

		}
		// 查找被直截删除的记录
		// historyQuery.deleted();
		// 排序
		historyQuery.orderByProcessInstanceStartTime().desc();
		// 记录总数
		long dataTotal = historyQuery.count();// 结果集数

		// 分页对象
		Page page = getPageTag(request, pageNo, pageSize);
		// page.setPageSize(20);//分页设置
		page.setTotalRecord(Integer.valueOf(((Long) dataTotal).toString()));
		Integer start = (page.getPageNo() - 1) * page.getPageSize();

		List<HistoricProcessInstance> resultList = historyQuery// 历史流程实例
				.listPage(start, page.getPageSize());

		// 模板对象
		ModelAndView mv = new ModelAndView();
		try {
			List<Company> companyList = companyService.getAll();
			mv.addObject("companyList", companyList);
		} catch (Exception e) {
			logger.error("history list:", e);
		}
		// 模板赋值
		mv.addObject("list", resultList);
		mv.addObject("dataTotal", dataTotal);
		mv.addObject("searchForm", searchForm);

		mv.addObject("page", page);

		return mv;

	}

	/**
	 * 根据流程实例ID查询流程历史
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "byinstance", method = {}, produces = { "text/html" })
	public ModelAndView byinstance(String piid, HistorySearchForm searchForm, HttpServletRequest request,
			HttpServletResponse response, @RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize) {

		HistoricActivityInstanceQuery historyAIQuery = processEngine.getHistoryService() // 任务相关service
				.createHistoricActivityInstanceQuery();// 创建历史实例查询

		// 表单筛选
		if (StringUtils.isNotBlank(searchForm.getId())) {
			historyAIQuery.processInstanceId(searchForm.getId());// 通过定义id查询
		}

		if (searchForm.getComplete() > 0) {
			if (searchForm.getComplete() == 1) {
				historyAIQuery.unfinished();
			}
			if (searchForm.getComplete() == 2) {
				historyAIQuery.finished();
			}

		}

		historyAIQuery.processInstanceId(piid);
		historyAIQuery.orderByHistoricActivityInstanceStartTime().asc();
		// 记录总数
		long dataTotal = historyAIQuery.count();// 结果集数

		// 分页对象
		Page page = getPageTag(request, pageNo, pageSize);
		// page.setPageSize(20);//分页设置
		page.setTotalRecord(Integer.valueOf(((Long) dataTotal).toString()));
		Integer start = (page.getPageNo() - 1) * page.getPageSize();

		List<HistoricActivityInstance> resultList = historyAIQuery// 历史流程实例
				.listPage(start, page.getPageSize());

		List<Map<String, Object>> mapList = new ArrayList<>();

		TaskService taskService = processEngine.getTaskService();
		try {
			//补备注信息
			for (HistoricActivityInstance result : resultList) {
				Map<String, Object> map = new HashMap<String, Object>();

				if (result.getTaskId() != null) {
					List<Comment> commentList = taskService.getTaskComments(result.getTaskId());
					for (Comment comment : commentList) {
						map.put("comment", comment.getFullMessage());
						map.put("commentType", comment.getType());
					}
				} else {
					map.put("comment", "");
				}

				mapList.add(map);
			}
		} catch (Exception e) {
			logger.error("byinstance:", e);
		}
		// 模板对象
		ModelAndView mv = new ModelAndView();
		// 模板赋值
		mv.addObject("list", resultList);
		mv.addObject("mapList", mapList);
		mv.addObject("dataTotal", dataTotal);
		mv.addObject("searchForm", searchForm);

		mv.addObject("page", page);

		return mv;

	}


	/**
	 * 删除流程定义 string格式：(processInstanceId) id="1234"
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delete", method = {
			org.springframework.web.bind.annotation.RequestMethod.POST }, produces = { "application/json" })
	public JSONObject delete(String id, HttpServletRequest request, HttpServletResponse respons) {

		HistoryService historyService = processEngine.getHistoryService();

		JSONObject result = new JSONObject();
		try {
			historyService.deleteHistoricProcessInstance(id);
			result.put("code", 0);
			result.put("message", "删除流程实例成功");
			result.put("type", "success");
		} catch (Exception e) {
			logger.error("history delete:", e);
			result.put("code", -1);
			result.put("message", "删除流程实例失败：" + e.getMessage());
		}

		return result;
	}

}
