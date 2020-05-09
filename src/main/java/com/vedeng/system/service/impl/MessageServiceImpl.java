package com.vedeng.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.shiro.JedisUtils;
import com.vedeng.common.util.DateUtil;
import com.vedeng.system.dao.MessageMapper;
import com.vedeng.system.dao.MessageUserMapper;
import com.vedeng.system.model.Message;
import com.vedeng.system.model.MessageUser;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.service.MessageService;

import net.sf.json.JSONObject;

@Service("messageService")
public class MessageServiceImpl extends BaseServiceimpl implements MessageService {
	@Autowired
	@Qualifier("messageMapper")
	private MessageMapper messageMapper;
	
	@Autowired
	@Qualifier("messageUserMapper")
	private MessageUserMapper messageUserMapper;

	@Override
	public List<Message> getMessageByUserlistpage(Message message, Page page, Integer userId) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", message);
		map.put("page", page);
		map.put("userId", userId);
		List<Message> messageByUserlistpage = messageMapper.getMessageByUserlistpage(map);
		for(Message m : messageByUserlistpage){
//			if(JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_OBJECT+m.getCategory())){
//				JSONObject jsonObject=JSONObject.fromObject(JedisUtils.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_OBJECT+m.getCategory()));
//				SysOptionDefinition sod=(SysOptionDefinition) JSONObject.toBean(jsonObject, SysOptionDefinition.class);
//				m.setCategoryName(sod.getTitle());
//			}
			m.setCategoryName(getSysOptionDefinitionById(m.getCategory()).getTitle());
		}
		return messageByUserlistpage;
	}

	@Override
	public Integer getMessageCount(Message message, Integer userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", message);
		map.put("userId", userId);
		return messageMapper.getMessageCount(map);
	}

	@Override
	public ResultInfo editMessageUserIsView(Integer userId, Integer messageUserId) {
		Map<String,Object> map = new HashMap<String, Object>();
		MessageUser messageUser = new MessageUser();
		Long currTime = DateUtil.sysTimeMillis();
		messageUser.setIsView(1);
		messageUser.setViewTime(currTime);
		map.put("messageUser", messageUser);
		if(messageUserId == 0){
			map.put("userId", userId);
		}else{
			map.put("messageUserId", messageUserId);
		}
		Integer res = messageUserMapper.editMessageUser(map);
		ResultInfo resultInfo = new ResultInfo<>();
		if(res>0){
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
		}
		return resultInfo;
	}

	@Override
	public Integer saveMessage(Message message) {
		messageMapper.insertSelective(message);
		return message.getMessageId();
	}

	@Override
	public void saveMessageList(List<MessageUser> messageUserList) {
		messageUserMapper.saveBatch(messageUserList);	
	}

	@Override
	public ResultInfo<?> modifyMessageViewStatus(MessageUser record)
	{
		ResultInfo<?> result = new ResultInfo<>();
		if(null == record || null == record.getIsView() || null == record.getUserId())
			return result;
		record.setViewTime(System.currentTimeMillis());
		messageUserMapper.updateMessageViewStatus(record);
		
		result.setCode(0);
		result.setMessage("操作成功");
		
		return result;
	}

	@Override
	public List<Message> getMessageList(Integer userId) {

		return messageMapper.getMessageSjList(userId);
	}

	@Override
	public Integer queryNoReadMsgNum(Integer userId) {
		return messageMapper.queryNoReadMsgNum(userId);
	}


}
