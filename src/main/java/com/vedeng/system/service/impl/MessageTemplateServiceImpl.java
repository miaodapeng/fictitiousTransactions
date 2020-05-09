package com.vedeng.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.shiro.JedisUtils;
import com.vedeng.common.util.DateUtil;
import com.vedeng.system.dao.MessageMapper;
import com.vedeng.system.dao.MessageTemplateMapper;
import com.vedeng.system.dao.MessageUserMapper;
import com.vedeng.system.dao.NoticeMapper;
import com.vedeng.system.model.Message;
import com.vedeng.system.model.MessageTemplate;
import com.vedeng.system.model.MessageUser;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.service.MessageService;
import com.vedeng.system.service.MessageTemplateService;

import net.sf.json.JSONObject;

@Service("messageTemplateService")
public class MessageTemplateServiceImpl extends BaseServiceimpl implements MessageTemplateService {
    
	@Autowired
	@Qualifier("messageTemplateMapper")
	private MessageTemplateMapper messageTemplateMapper;
	
	@Override
	public List<MessageTemplate> querylistPage(MessageTemplate messageTemplate, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("messageTemplate", messageTemplate);
		map.put("page", page);
		return messageTemplateMapper.querylistPage(map);
	}

	@Override
	public Integer saveAdd(MessageTemplate messageTemplate, HttpSession session) {
		User user =(User)session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();
		
		messageTemplate.setIsEnable(1);
		messageTemplate.setAddTime(time);
		messageTemplate.setCreator(user.getUserId());
		messageTemplate.setModTime(time);
		messageTemplate.setUpdater(user.getUserId());
		
		messageTemplateMapper.insertSelective(messageTemplate);
		Integer messageTemplateId = messageTemplate.getMessageTemplateId();
		
		return messageTemplateId;
	}

	@Override
	public MessageTemplate getMessageTemplate(MessageTemplate messageTemplate) {
		return messageTemplateMapper.selectByPrimaryKey(messageTemplate.getMessageTemplateId());
	}

	@Override
	public Integer saveEdit(MessageTemplate messageTemplate, HttpSession session) {
		User user =(User)session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();
		
		messageTemplate.setModTime(time);
		messageTemplate.setUpdater(user.getUserId());
		
		int update = messageTemplateMapper.updateByPrimaryKeySelective(messageTemplate);
		return update;
	}

}
