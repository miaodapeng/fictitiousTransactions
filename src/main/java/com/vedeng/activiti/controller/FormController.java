package com.vedeng.activiti.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.FormService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.form.FormProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vedeng.common.controller.BaseController;

@Controller
@RequestMapping("/activiti/form/")
public class FormController extends BaseController {

	@Autowired // 自动装载
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	// @Autowired
	// @Qualifier("processDefinitionService")
	// private ProcessDefinitionService processDefinitionService;

	/**
	 * 查询任务表单，返回表单数据集合
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "task", method = {}, produces = { "text/html" })
	public ModelAndView task(String taskId, HttpServletRequest request,
			HttpServletResponse response, @RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize) {

		FormService formQuery = processEngine.getFormService(); // 表单service

        List<FormProperty> list = formQuery.getTaskFormData(taskId).getFormProperties();  
          
        if(list!=null && list.size()>0){  
            for(FormProperty formProperty:list){  
                System.out.println(formProperty.getId() + "     " + formProperty.getName() + "      " +formProperty.getValue() + "     " +formProperty.getType().getName());  
            }  
        }  
		
		// 模板对象
		ModelAndView mv = new ModelAndView();
		// 模板赋值
		mv.addObject("list", list);
		
		return mv;

	}

	/**
	 * 查询任务表单，表单数据集合
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "outform", method = {}, produces = { "text/html" })
	public ModelAndView outform(String taskId, HttpServletRequest request,
			HttpServletResponse response, @RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize) {

		FormService formService = processEngine.getFormService(); // 表单service
		
		Object renderedTaskForm = formService.getRenderedTaskForm(taskId); 
		System.out.println(renderedTaskForm.toString());
        
		List<FormProperty> list = formService.getTaskFormData(taskId).getFormProperties();  
          
        if(list!=null && list.size()>0){  
            for(FormProperty formProperty:list){  
                System.out.println(formProperty.getId() + "     " + formProperty.getName() + "      " +formProperty.getValue() + "     " +formProperty.getType().getName());  
            }  
        }  
		
		// 模板对象
		ModelAndView mv = new ModelAndView();
		// 模板赋值
		mv.addObject("list", list);
		
		return mv;

	}

}
