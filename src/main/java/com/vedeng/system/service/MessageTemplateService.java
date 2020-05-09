package com.vedeng.system.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.system.model.Message;
import com.vedeng.system.model.MessageTemplate;
import com.vedeng.system.model.MessageUser;

public interface MessageTemplateService extends BaseService{
    /**
     * 
     * <b>Description:</b><br> 消息模板列表
     * @param messageTemplate
     * @param page
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年11月23日 上午11:10:18
     */
	List<MessageTemplate> querylistPage(MessageTemplate messageTemplate, Page page);
    /**
     * 
     * <b>Description:</b><br> 保存消息模板
     * @param messageTemplate
     * @param session
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年11月23日 下午5:08:59
     */
	Integer saveAdd(MessageTemplate messageTemplate, HttpSession session);
	/**
	 * 
	 * <b>Description:</b><br> 获取信息模板
	 * @param messageTemplate
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年11月23日 下午5:30:49
	 */
	MessageTemplate getMessageTemplate(MessageTemplate messageTemplate);
	/**
	 * 
	 * <b>Description:</b><br> 保存编辑
	 * @param messageTemplate
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年11月23日 下午6:35:55
	 */
	Integer saveEdit(MessageTemplate messageTemplate, HttpSession session);
	
}
