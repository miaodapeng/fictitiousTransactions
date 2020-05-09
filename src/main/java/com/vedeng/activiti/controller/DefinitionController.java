package com.vedeng.activiti.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.constants.Contant;
import org.activiti.engine.FormService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.vedeng.activiti.model.ActionProcdef;
import com.vedeng.activiti.model.DefinitionSearchForm;
import com.vedeng.activiti.service.ActionProcdefService;
import com.vedeng.authorization.model.Action;
import com.vedeng.authorization.model.Actiongroup;
import com.vedeng.authorization.model.Company;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.page.Page;
import com.vedeng.system.service.ActionService;
import com.vedeng.system.service.ActiongroupService;
import com.vedeng.system.service.CompanyService;
import com.vedeng.system.service.UserService;

@Controller
@RequestMapping("/activiti/definition/")
public class DefinitionController extends BaseController {
	public static Logger logger = LoggerFactory.getLogger(DefinitionController.class);

	@Autowired // 自动装载
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	// @Autowired
	// @Qualifier("processDefinitionService")
	// private ProcessDefinitionService processDefinitionService;

	@Autowired // 自动装载
	private CompanyService companyService;

	@Autowired // 自动装载
	private UserService userService;

	@Autowired // 自动装载
	@Qualifier("actiongroupService")
	private ActiongroupService actiongroupService;

	@Autowired // 自动装载
	@Qualifier("actionService")
	private ActionService actionService;

	@Autowired // 自动装载
	@Qualifier("actionProcdefService")
	private ActionProcdefService actionProcdefService;

	/**
	 * 查询流程定义，返回流程定义集合，对应ACT_RE_MODEL
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "list", method = {}, produces = { "text/html" })
	public ModelAndView list(DefinitionSearchForm searchForm, HttpServletRequest request, HttpServletResponse respons,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize) {

		RepositoryService repositoryService = processEngine.getRepositoryService();
		ProcessDefinitionQuery definitionQuery = repositoryService.createProcessDefinitionQuery();

		// 表单筛选
		if (StringUtils.isNotBlank(searchForm.getId())) {
			definitionQuery.processDefinitionId(searchForm.getId());
		}
		if (StringUtils.isNotBlank(searchForm.getKey())) {
			definitionQuery.processDefinitionKey(searchForm.getKey());
		}
		if (StringUtils.isNotBlank(searchForm.getName())) {
			definitionQuery.processDefinitionNameLike("%" + searchForm.getName() + "%");
		}
		if (StringUtils.isNotBlank(searchForm.getTenantId()) && !searchForm.getTenantId().equals("0")) {
			definitionQuery.processDefinitionTenantId(searchForm.getTenantId());
		}
		// 记录总数
		long dataTotal = definitionQuery.count();// 结果集数

		// 分页对象
		Page page = getPageTag(request, pageNo, pageSize);
		// page.setPageSize(20);//分页设置
		page.setTotalRecord(Integer.valueOf(((Long) dataTotal).toString()));
		Integer start = (page.getPageNo() - 1) * page.getPageSize();

		List<ProcessDefinition> resultList = definitionQuery.orderByDeploymentId().desc().listPage(start,
				page.getPageSize());

		// 转存，补数据
		DeploymentQuery deploymentQuery = repositoryService.createDeploymentQuery();// 部署查询

		List<Map<String, Object>> pdeList = new ArrayList<>();
		// 把组合后的结果集转存到Map数组中
		for (ProcessDefinition rList : resultList) {
			Map<String, Object> pde = new HashMap<String, Object>();

			List<Deployment> deploymentList = deploymentQuery.deploymentId(rList.getDeploymentId()).list();// 找出指定部署的信息
			for (Deployment dpList : deploymentList) {// 补部署数据
				pde.put("deploymentName", dpList.getName());
				pde.put("deploymentTime", dpList.getDeploymentTime());
				// break;//只要一条
			}
			pdeList.add(pde);
		}

		// 模板对象
		ModelAndView mv = new ModelAndView();
		try {
			List<Company> companyList = companyService.getAll();
			mv.addObject("companyList", companyList);
		} catch (Exception e) {
			logger.error("definition list:", e);
		}
		// 模板赋值
		mv.addObject("list", resultList);
		mv.addObject("pdeList", pdeList);
		mv.addObject("dataTotal", dataTotal);
		mv.addObject("searchForm", searchForm);

		mv.addObject("page", page);

		return mv;

	}

	/**
	 * 查看流程png图 string格式：(deploymentId) id="process:2:107504"
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "png", method = {
			org.springframework.web.bind.annotation.RequestMethod.GET }, produces = {})
	public void png(String did, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		List<String> list = null;
		RepositoryService repositoryService = processEngine.getRepositoryService();

		list = repositoryService.getDeploymentResourceNames(did);

		String resourceName = null;// 保存资源文件名
		for (String name : list) {
			if (name.indexOf(".png") > 0) {
				resourceName = name;
				break;
			}
		}
		if (null != resourceName) {
			InputStream inputStream = repositoryService.getResourceAsStream(did,
					java.net.URLDecoder.decode(resourceName, "utf-8"));

			try {
				// 生成图片
				// String fileName =
				// resourceName.substring(resourceName.lastIndexOf('/') + 1);
				// String path = "/tmp/";
				// File file = new File(path + fileName);
				// System.out.println(file.getAbsolutePath());
				// if (file.exists()) {
				// file.delete();// 若存在同名文件，先删除后创建
				// }
				// file.createNewFile();

				// 直接向流浏览器输出图片
				response.setCharacterEncoding("utf-8");
				OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
				IOUtils.copy(inputStream, outputStream); // 代码样本的输出方式

				outputStream.flush();
				outputStream.close();

				// 关闭图片源
				inputStream.close();

			} catch (Exception e) {
				logger.error("definition png:", e);
			} finally {
				IOUtils.closeQuietly(inputStream);
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

	/**
	 * 无流程图数据源时显示信息 string格式：(message) message="no process image source data";
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "noimage", method = {
			org.springframework.web.bind.annotation.RequestMethod.GET }, produces = {})
	public ModelAndView noimage(String message, HttpServletRequest request, HttpServletResponse response) {
		// 模板对象
		ModelAndView mv = new ModelAndView("common/noimage");
		// 模板赋值
		mv.addObject("message", message);
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
	public JSONObject delete(String id, HttpServletRequest request, HttpServletResponse response) {

		RepositoryService repositoryService = processEngine.getRepositoryService();

		JSONObject result = new JSONObject();
		try {
			/* 非级联删除，若有对应的流程启动，则报错 */
			repositoryService.deleteDeployment(id);

			/* 级联删除,同时删除相关流程实例等信息 */
			// repositoryService.deleteDeployment(id,true);

			result.put("code", 0);
			result.put("message", "删除流程定义成功");
			result.put("type", "success");
		} catch (Exception e) {
			logger.error("definition delete error", e);
			result.put("code", -1);
			result.put("message", "删除流程定义失败：" + e.getMessage());
		}

		return result;
	}

	/**
	 * 用功能分组id查找功能列表 返回Json
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "action_by_actiongroup_id", method = {
			org.springframework.web.bind.annotation.RequestMethod.GET }, produces = { "application/json" })
	public JSONObject actionByActiongroupId(int groupId, HttpServletRequest request, HttpServletResponse response) {

		JSONObject result = new JSONObject();

		if (groupId <= 0) {
			result.put("code", -1);
			result.put("message", "参数错误");
		} else {
			// 功能列表
			List<Action> actionList = actionService.getActionByActiongroupId(groupId);
			if (!actionList.isEmpty()) {
				result.put("code", 0);
				result.put("actionList", actionList);
			} else {
				result.put("code", -1);
				result.put("message", "无数据");
			}

		}

		return result;
	}

	/**
	 * 启动新流程 string格式： id="process:2:107504"
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "start", method = {
			org.springframework.web.bind.annotation.RequestMethod.GET }, produces = {})
	public ModelAndView start(String pdid, String pdname, String dname, HttpServletRequest request,
			HttpServletResponse response) {

		// 模板对象
		ModelAndView mv = new ModelAndView();
		// 透传参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("pdid", pdid);
		params.put("pdname", pdname);
		params.put("dname", dname);

		// 设定流程实例名称
		String fullName = "";

		if (StringUtils.isBlank(pdname)) {
			fullName = dname;
		} else {
			fullName = pdname;
		}
		User userModel = new User();
		userModel.setCompanyId(1);
		List<User> userList = userService.getAllUser(userModel);

		// 功能分组
		List<Actiongroup> actiongroupList = actiongroupService.getActionGroupList();

		// 模板赋值
		mv.addObject("actiongroupList", actiongroupList);
		mv.addObject("userList", userList);
		mv.addObject("params", params);
		mv.addObject("fullName", fullName);

		return mv;

	}

	/**
	 * 启动新流程 string格式： id="process:2:107504"
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "start", method = {
			org.springframework.web.bind.annotation.RequestMethod.POST }, produces = { "application/json" })
	public JSONObject start(@RequestBody Map<String, String> paramsMap, HttpServletRequest request,
			HttpServletResponse response) {

		JSONObject result = new JSONObject();
		try {
			User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
			Map<String, Object> variableMap = new HashMap<String, Object>();
			variableMap.put("applyUser", user.getUsername());

			RuntimeService runtimeService = processEngine.getRuntimeService();// 运行时Service
			ProcessInstance pi = runtimeService.startProcessInstanceById(paramsMap.get("pdid"),
					paramsMap.get("businessKey"), variableMap);// 启动流程;

			// 设定流程实例名称
			String fullName = "";

			if (StringUtils.isBlank(paramsMap.get("name"))) {
				if (StringUtils.isBlank(paramsMap.get("pdname"))) {
					fullName = paramsMap.get("dname");
				} else {
					fullName = paramsMap.get("pdname");
				}

			} else {
				fullName = paramsMap.get("name");
			}

			runtimeService.setProcessInstanceName(pi.getId(), fullName);// 修改流程实例名称
			// runtimeService.updateBusinessKey(pi.getId(),
			// paramsMap.get("businessKey"));// 修改businessKey

			// 设置当前实例的第一个节点的办理人
			if (StringUtils.isNotBlank(paramsMap.get("userName"))) {
				String piId = pi.getProcessInstanceId();//
				TaskQuery taskQuery = processEngine.getTaskService() // 任务相关service
						.createTaskQuery();// 创建任务查询
				taskQuery.processInstanceId(piId);
				Task newTask = taskQuery// 用户任务
						.singleResult();

				// List<Task> newTaskList = taskQuery// 用户任务
				// .list();
				//// for(Task newTask : newTaskList){
				// System.out.println("name:" + newTask.getName());
				// }
				processEngine.getTaskService().setAssignee(newTask.getId(), paramsMap.get("userName"));
			}
			// 返回状态

			result.put("code", 0);
			result.put("message", "启动新流程成功，流程实例ID：" + pi.getId());
			result.put("type", "success");

		} catch (Exception e) {
			logger.error("definition start error.", e);
			result.put("code", -1);
			result.put("message", "启动任务失败：" + e.getMessage());
		}

		return result;
	}

	/**
	 * 启动新流程 string格式： id="process:2:107504"
	 * 
	 * @param String
	 *            processDefinitionId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "startform", method = {
			org.springframework.web.bind.annotation.RequestMethod.POST }, produces = { "application/json" })
	public JSONObject startform(String id, String name, String deploymentName, HttpServletRequest request,
			HttpServletResponse response) {

		JSONObject result = new JSONObject();
		try {
			// 定义map用于往工作流数据库中传值。
			Map<String, String> formProperties = new HashMap<String, String>();

			FormService formService = processEngine.getFormService();// 表单Service

			ProcessInstance pi = formService.submitStartFormData(id, formProperties);// 用表单启动流程;

			// 设定流程实例名称
			String fullName = "";

			if (StringUtils.isBlank(name)) {
				fullName = deploymentName;
			} else {
				fullName = name;
			}

			fullName += ":" + pi.getId();

			// formService.setProcessInstanceName(pi.getId(), fullName);//
			// 修改流程实例名称

			// 返回状态

			result.put("code", 0);
			result.put("message", "启动新流程成功，流程实例ID：" + pi.getId() + "，流程定义ID：" + pi.getProcessDefinitionId());
			result.put("type", "success");

		} catch (Exception e) {
			logger.error("startform error.", e);
			result.put("code", -1);
			result.put("message", "启动任务失败：" + e.getMessage());
		}

		return result;
	}

	/**
	 * 绑定ERP业务功能 string格式： piid="process:2:107504"
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "bind_action", method = {
			org.springframework.web.bind.annotation.RequestMethod.GET }, produces = {})
	public ModelAndView bindAction(String pdid, HttpServletRequest request, HttpServletResponse response) {

		// 模板对象
		ModelAndView mv = new ModelAndView();
		// 透传参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("pdid", pdid);

		// 功能分组
		List<Actiongroup> actiongroupList = actiongroupService.getActionGroupList();

		// 模板赋值
		mv.addObject("actiongroupList", actiongroupList);
		mv.addObject("params", params);

		return mv;

	}

	/**
	 * 绑定ERP业务功能 ActionProcdef actionProcdef
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "bind_action", method = {
			org.springframework.web.bind.annotation.RequestMethod.POST }, produces = { "application/json" })
	public JSONObject bindAction(ActionProcdef actionProcdef, HttpServletRequest request,
			HttpServletResponse response) {

		JSONObject result = new JSONObject();
		try {

			RepositoryService repositoryService = processEngine.getRepositoryService();
			ProcessDefinitionQuery definitionQuery = repositoryService.createProcessDefinitionQuery();

			// 表单筛选
			definitionQuery.processDefinitionId(actionProcdef.getProcdefId());

			ProcessDefinition processDefinition = definitionQuery.orderByDeploymentId().desc().singleResult();

			String tenantId = processDefinition.getTenantId();
			String[] tenantIdArray = tenantId.split(":");

			actionProcdef.setCompanyId(Integer.parseInt(tenantIdArray[tenantIdArray.length - 1]));

			actionProcdefService.replace(actionProcdef);

			// 返回状态

			result.put("code", 0);
			result.put("message", "绑定成功");
			result.put("type", "success");

		} catch (Exception e) {
			logger.error("definition bind_action", e);
			result.put("code", -1);
			result.put("message", "绑定失败：" + e.getMessage());
		}

		return result;
	}

	/**
	 * 查询流程定义与业务关系列表，返回集合
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "action_procdef_list", method = {}, produces = { "text/html" })
	public ModelAndView actionProcdelList(HttpServletRequest request, HttpServletResponse respons,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize) {
		// 模板对象
		ModelAndView mv = new ModelAndView();
		// 分页对象
		Page page = getPageTag(request, pageNo, pageSize);
		List<ActionProcdef> resultList = actionProcdefService.getListPage(page);
		// 模板赋值
		mv.addObject("list", resultList);
		mv.addObject("page", page);

		return mv;
	}
}
