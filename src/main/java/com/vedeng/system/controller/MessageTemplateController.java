package com.vedeng.system.controller;


import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vedeng.authorization.model.User;
import com.vedeng.common.annotation.SystemControllerLog;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.logistics.model.Express;
import com.vedeng.system.model.MessageTemplate;
import com.vedeng.system.model.Notice;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.service.MessageService;
import com.vedeng.system.service.MessageTemplateService;
import com.vedeng.system.service.UserService;
/**
 * 
 * <b>Description:</b><br> 消息模板
 * @author scott
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.system.controller
 * <br><b>ClassName:</b> MessageTemplateController
 * <br><b>Date:</b> 2017年11月23日 上午10:59:33
 */

@Controller
@RequestMapping("/system/messagetemplate")
public class MessageTemplateController extends BaseController {
	
	@Autowired
	@Qualifier("messageTemplateService")
	private MessageTemplateService messageTemplateService;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	/**
	 * 
	 * <b>Description:</b><br> 消息模板列表
	 * @param request
	 * @param messageTemplate
	 * @param pageNo
	 * @param pageSize
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年11月23日 上午10:59:54
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
    @RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request, MessageTemplate messageTemplate,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,  
            @RequestParam(required = false) Integer pageSize,HttpSession session) {
		
		ModelAndView mv = new ModelAndView("system/meaaagetemplate/template_index");  
		Page page = getPageTag(request,pageNo,pageSize);
		User session_user =(User)session.getAttribute(ErpConst.CURR_USER);
		List<MessageTemplate> list = messageTemplateService.querylistPage(messageTemplate, page);
		for (MessageTemplate mg : list) {
			mg.setCreatorName(userService.getUserById(mg.getCreator()).getUsername());
		}
		
		mv.addObject("messageTemplate", messageTemplate);
		mv.addObject("page", page);
		mv.addObject("messageTemplateList", list);
		return mv;
	}
	/**
	 * 
	 * <b>Description:</b><br> 新增消息模板
	 * @param notice
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年11月23日 下午2:25:20
	 */
	@ResponseBody
    @RequestMapping(value = "/add")
	public ModelAndView add(MessageTemplate messageTemplate){
		ModelAndView mv = new ModelAndView("system/meaaagetemplate/modify_Template");  
		return mv;
	}
	/**
	 * 
	 * <b>Description:</b><br> 保存新增消息模板
	 * @param messageTemplate
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年11月23日 下午4:53:08
	 */
	@ResponseBody
    @RequestMapping(value = "/saveadd")
	@SystemControllerLog(operationType = "add",desc = "保存新增消息模板")
	public ModelAndView saveAdd(MessageTemplate messageTemplate,HttpSession session){
		ModelAndView mv = new ModelAndView();
		try {
			String title = messageTemplate.getTitle();
			String content = messageTemplate.getContent();
			String params = getParam(title) + getParam(content) ;
			messageTemplate.setParams(params);
			
			Integer messageTemplateId = messageTemplateService.saveAdd(messageTemplate,session);
			if(messageTemplateId > 0){
				mv.addObject("refresh", "true_false_true");
				return success(mv);
			}else{
				return fail(mv);
			}
		} catch (Exception e) {
			logger.error("message template saveadd:", e);
			return fail(mv);
		}
	}
	/**
	 * 
	 * <b>Description:</b><br> 
	 * @param messageTemplate
	 * @return
	 * @throws IOException
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年11月23日 下午5:27:33
	 */
	@ResponseBody
    @RequestMapping(value = "/edit")
	public ModelAndView edit(MessageTemplate messageTemplate) throws IOException{
		ModelAndView mv = new ModelAndView("system/meaaagetemplate/modify_Template");  
		
		MessageTemplate info = messageTemplateService.getMessageTemplate(messageTemplate);
		
		mv.addObject("messageTemplate", info);
		return mv;
	}
	/**
	 * 
	 * <b>Description:</b><br> 保存编辑消息模板
	 * @param messageTemplate
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年11月23日 下午6:32:07
	 */
	@ResponseBody
    @RequestMapping(value = "/saveedit")
	@SystemControllerLog(operationType = "edit",desc = "保存编辑")
	public ModelAndView saveEdit(MessageTemplate messageTemplate,HttpSession session){
		ModelAndView mv = new ModelAndView();
		if(null == messageTemplate.getMessageTemplateId() || messageTemplate.getMessageTemplateId() < 0){
			return fail(mv);
		}
		try {
			String title = messageTemplate.getTitle();
			String content = messageTemplate.getContent();
			String params = getParam(title) + getParam(content) ;
			messageTemplate.setParams(params);
			Integer succ = messageTemplateService.saveEdit(messageTemplate,session);
			if(succ > 0){
				mv.addObject("refresh", "true_false_true");
				return success(mv);
			}else{
				return fail(mv);
			}
		} catch (Exception e) {
			logger.error("message template saveedit:", e);
			return fail(mv);
		}
	}
	/**
	 * 
	 * <b>Description:</b><br> 删除消息模板
	 * @param messageTemplate
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年11月24日 上午9:39:03
	 */
	@ResponseBody
	@RequestMapping(value="/delMessageTemplate")
	public ResultInfo<?> delMessageTemplate(MessageTemplate messageTemplate,HttpSession session){
		if(messageTemplate==null){
			return new ResultInfo<>(-1, "参数为空");
		}
		messageTemplate.setIsEnable(0);
		Integer succ = messageTemplateService.saveEdit(messageTemplate,session);
		ResultInfo<?> result = null;
		if(succ>0){
			result = new ResultInfo(0, "操作成功");
		}else{
			result = new ResultInfo(0, "操作失败");
		}
		return result;
	}
	
	public String getParam(String str) {
		String params = "";
		String [] s = str.split("}");
			for (String st : s) {
				if(st.indexOf("{$")!=-1){
					st+="}";
					params += st.substring(st.indexOf("{"),st.indexOf("}")+1)+",";
				}
			}
		return params;
	}
}
