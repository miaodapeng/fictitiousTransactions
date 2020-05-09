package com.vedeng.activiti.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vedeng.activiti.model.ModelSearchForm;
import com.vedeng.activiti.model.MyProcessDefinitionInfo;
import com.vedeng.authorization.model.Company;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.key.ShortUUID;
import com.vedeng.common.page.Page;
import com.vedeng.system.service.CompanyService;

@Controller
@RequestMapping("/activiti/model/")
public class ModelController extends BaseController {

	@Autowired // 自动装载
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	@Autowired // 自动装载
	private CompanyService companyService;
	/**
	 * 查询流程定义，返回流程定义集合，对应ACT_RE_MODEL
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "list", method = {}, produces = { "text/html" })
	public ModelAndView list(ModelSearchForm searchForm, HttpServletRequest request, HttpServletResponse respons,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize) {

		RepositoryService repositoryService = processEngine.getRepositoryService();
		ModelQuery modelQuery = repositoryService.createModelQuery();

		// 表单筛选
		if (StringUtils.isNotBlank(searchForm.getId())) {
			modelQuery.modelId(searchForm.getId());
		}
		if (StringUtils.isNotBlank(searchForm.getKey())) {
			modelQuery.modelKey(searchForm.getKey());
		}
		if (StringUtils.isNotBlank(searchForm.getName())) {
			modelQuery.modelNameLike("%" + searchForm.getName() + "%");
		}
		if (StringUtils.isNotBlank(searchForm.getTenantId()) && !searchForm.getTenantId().equals("0")) {
			modelQuery.modelTenantId(searchForm.getTenantId());
		}

		// 记录总数
		long dataTotal = modelQuery.count();// 结果集数

		// 分页对象
		Page page = getPageTag(request, pageNo, pageSize);
		// page.setPageSize(20);//分页设置
		page.setTotalRecord(Integer.valueOf(((Long) dataTotal).toString()));
		Integer start = (page.getPageNo() - 1) * page.getPageSize();

		List<Model> resultList = modelQuery.orderByCreateTime().desc().listPage(start, page.getPageSize());

		// 模板对象
		ModelAndView mv = new ModelAndView();
		try {
			List<Company> companyList = companyService.getAll();
			mv.addObject("companyList", companyList);
		} catch (Exception e) {
			logger.error("model list:", e);
		}
		// 模板赋值
		mv.addObject("list", resultList);
		mv.addObject("dataTotal", dataTotal);
		mv.addObject("searchForm", searchForm);

		mv.addObject("page", page);

		return mv;

	}

	/**
	 * 创建模型
	 * 
	 * @return
	 * 
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public ModelAndView create(HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		try {
			List<Company> companyList = companyService.getAll();
			mv.addObject("companyList", companyList);
		} catch (Exception e) {
			logger.error("model create:", e);
		}
		return mv;
	}

	/**
	 * 创建模型
	 * 
	 * @return
	 * 
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public JSONObject create(MyProcessDefinitionInfo myInfo, String tenantId, HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");

		// 流程定义参数
		myInfo.setVersion(1);

		if (StringUtils.isBlank(myInfo.getName())) {
			myInfo.setName("erp.vedeng.com");
		}
		if (StringUtils.isBlank(myInfo.getDescription())) {
			myInfo.setDescription("默认的流程定义");
		}

		String key = ShortUUID.generateShortUuid();
		myInfo.setKey(key);// 使用UUID作key;

		JSONObject result = new JSONObject();
		// 保存定义
		try {
			RepositoryService repositoryService = processEngine.getRepositoryService();

			ObjectMapper objectMapper = new ObjectMapper();
			ObjectNode editorNode = objectMapper.createObjectNode();
			editorNode.put("id", "canvas");
			editorNode.put("resourceId", "canvas");
			ObjectNode stencilSetNode = objectMapper.createObjectNode();
			stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
			editorNode.put("stencilset", stencilSetNode);

			Model modelData = repositoryService.newModel();

			ObjectNode modelObjectNode = objectMapper.createObjectNode();
			modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, myInfo.getName());
			modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, myInfo.getVersion());
			modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, myInfo.getDescription());
			modelData.setMetaInfo(modelObjectNode.toString());
			modelData.setName(myInfo.getName());
			modelData.setKey(myInfo.getKey());
			modelData.setTenantId(tenantId);

			// 保存模型
			repositoryService.saveModel(modelData);
			repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
			// response.sendRedirect(request.getContextPath() +
			// "/modeler.html?modelId=" + modelData.getId());

			result.put("message", "创建模型成功");
			result.put("type", "success");
			result.put("code", 0);
			result.put("modelId", modelData.getId());

		} catch (Exception e) {
			logger.error("创建模型失败,code:01", e);
			result.put("code", -1);
			result.put("message", "创建模型失败：" + e.getMessage());
		}

		return result;
	}

	/**
	 * 删除流程定义 string数组格式： ids=1,2,3
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delete", method = {
			org.springframework.web.bind.annotation.RequestMethod.POST }, produces = { "application/json" })
	public JSONObject delete(String[] ids, HttpServletRequest request, HttpServletResponse respons) {

		RepositoryService repositoryService = processEngine.getRepositoryService();

		JSONObject result = new JSONObject();

		for (String id : ids) {
			repositoryService.deleteModel(id);
		}
		result.put("code", 0);
		result.put("message", "删除成功");
		result.put("type", "success");

		return result;
	}

	/**
	 * 导出model的xml文件
	 */
	@RequestMapping(value = "export/{modelId}")
	public void export(@PathVariable("modelId") String modelId, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/xml; charset=utf-8");
		try {
			RepositoryService repositoryService = processEngine.getRepositoryService();
			Model modelData = repositoryService.getModel(modelId);
			BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
			// 获取节点信息
			byte[] arg0 = repositoryService.getModelEditorSource(modelData.getId());
			JsonNode editorNode = new ObjectMapper().readTree(arg0);
			// 将节点信息转换为xml
			BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
			BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
			byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);

			ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
			IOUtils.copy(in, response.getOutputStream());
			String filename = bpmnModel.getMainProcess().getId() + "-" + modelData.getId() + ".bpmn20.xml";
			// String filename = modelData.getName() + ".bpmn20.xml";
			response.setHeader("Content-Disposition",
					"attachment; filename=" + java.net.URLEncoder.encode(filename, "UTF-8"));
			response.flushBuffer();
		} catch (Exception e) {
			logger.error("export error:", e);
			PrintWriter out = null;
			try {
				out = response.getWriter();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			out.write("未找到对应数据");
		}
	}

	/**
	 * 
	 * @param modelId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "deploy", method = RequestMethod.POST, produces = { "application/json" })
	@ResponseBody
	public JSONObject deploy(@RequestParam("modelId") String modelId, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try {
			RepositoryService repositoryService = processEngine.getRepositoryService();
			Model modelData = repositoryService.getModel(modelId);
			ObjectNode modelNode = (ObjectNode) new ObjectMapper()
					.readTree(repositoryService.getModelEditorSource(modelData.getId()));
			byte[] bpmnBytes = null;
			BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
			bpmnBytes = new BpmnXMLConverter().convertToXML(model);
			String processName = modelData.getName() + ".bpmn20.xml";
			DeploymentBuilder deployment = repositoryService.createDeployment();// 创建部署
			deployment.addString(processName, new String(bpmnBytes, "utf-8"));// 流程源
			deployment.name(modelData.getName());// 设置名称
			deployment.category(modelData.getCategory());// 分类
			deployment.tenantId(modelData.getTenantId());//tenantId:租户，可以用做身份，系统，库等区分
			deployment.deploy();
			result.put("code", 0);
			result.put("message", "发布流程成功");
			result.put("type", "success");
		} catch (Exception e) {
			result.put("code", -1);
			result.put("message", "发布流程失败");
			result.put("type", "error");
			logger.error("model deploy", e);
		}
		return result;
	}
}
