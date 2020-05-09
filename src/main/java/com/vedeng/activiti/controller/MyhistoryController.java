package com.vedeng.activiti.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vedeng.activiti.model.HistorySearchForm;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.page.Page;

@Controller
@RequestMapping("/activiti/myhistory/")
public class MyhistoryController extends BaseController {

	@Autowired // 自动装载
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	/**
	 * 查询流程实例，返回流程实例集合
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "runlist", method = {}, produces = { "text/html" })
	public ModelAndView runlist(HistorySearchForm searchForm, HttpServletRequest request, HttpServletResponse response,
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

		// 排序
		historyQuery.orderByProcessInstanceStartTime().desc();

		// 找出当前用户所在任务对应的实例ID号
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);// 当前用户
		// System.out.println(user.getUsername() + ":" + user.getUserId());

		HistoricTaskInstanceQuery historyTaskInstanceQuery = processEngine.getHistoryService() // 历史任务相关service
				.createHistoricTaskInstanceQuery();// 创建任务查询
		historyTaskInstanceQuery.taskAssignee(user.getUsername());
		historyTaskInstanceQuery.unfinished();

		// 查当前分公司
		if (user.getCompanyId().toString() != null && user.getCompanyId() != 0) {
			historyTaskInstanceQuery.taskTenantId("erp:company:" + user.getCompanyId().toString());// 指定分公司为租户
		}

		List<HistoricTaskInstance> historicTaskInstanceList = historyTaskInstanceQuery.list();// 用户任务
		Set<String> processInstanceIds = new HashSet<>();
		for (HistoricTaskInstance historicTaskInstance : historicTaskInstanceList) {
			processInstanceIds.add(historicTaskInstance.getProcessInstanceId());
		}

		// 分页对象
		Page page = getPageTag(request, pageNo, pageSize);
		long dataTotal = 0;
		// 列表对象初始化
		List<HistoricProcessInstance> resultList = null;
		// 根据批量实例ID查找
		try {
			historyQuery.processInstanceIds(processInstanceIds);
			historyQuery.orderByProcessInstanceStartTime().desc();
			// 记录总数
			dataTotal = historyQuery.count();// 结果集数
			Integer start = (page.getPageNo() - 1) * page.getPageSize();
			resultList = historyQuery// 历史流程实例
					.listPage(start, page.getPageSize());
		} catch (Exception aiae) {
			// aialogger.error(Contant.ERROR_MSG, e);
			logger.error("history runlist:", aiae);
		}
		// page.setPageSize(20);//分页设置
		page.setTotalRecord(Integer.valueOf(((Long) dataTotal).toString()));

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

		// 排序
		historyQuery.orderByProcessInstanceStartTime().desc();

		// 找出当前用户所在任务对应的实例ID号
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);// 当前用户
		// System.out.println(user.getUsername() + ":" + user.getUserId());

		HistoricTaskInstanceQuery historyTaskInstanceQuery = processEngine.getHistoryService() // 历史任务相关service
				.createHistoricTaskInstanceQuery();// 创建任务查询
		historyTaskInstanceQuery.taskAssignee(user.getUsername());
		historyTaskInstanceQuery.finished();

		// 查当前分公司
		if (user.getCompanyId().toString() != null && user.getCompanyId() != 0) {
			historyTaskInstanceQuery.taskTenantId("erp:company:" + user.getCompanyId().toString());// 指定分公司为租户
		}

		List<HistoricTaskInstance> historicTaskInstanceList = historyTaskInstanceQuery.list();// 用户任务
		Set<String> processInstanceIds = new HashSet<>();
		for (HistoricTaskInstance historicTaskInstance : historicTaskInstanceList) {
			processInstanceIds.add(historicTaskInstance.getProcessInstanceId());
		}

		// 分页对象
		Page page = getPageTag(request, pageNo, pageSize);
		long dataTotal = 0;
		// 列表对象初始化
		List<HistoricProcessInstance> resultList = null;
		// 根据批量实例ID查找
		try {
			historyQuery.processInstanceIds(processInstanceIds);
			historyQuery.orderByProcessInstanceStartTime().desc();
			// 记录总数
			dataTotal = historyQuery.count();// 结果集数
			Integer start = (page.getPageNo() - 1) * page.getPageSize();
			resultList = historyQuery// 历史流程实例
					.listPage(start, page.getPageSize());
		} catch (Exception aiae) {
			logger.error("history list:", aiae);
		}
		// page.setPageSize(20);//分页设置
		page.setTotalRecord(Integer.valueOf(((Long) dataTotal).toString()));

		// 模板对象
		ModelAndView mv = new ModelAndView();
		// 模板赋值
		mv.addObject("list", resultList);
		mv.addObject("dataTotal", dataTotal);
		mv.addObject("searchForm", searchForm);

		mv.addObject("page", page);

		return mv;

	}

}
