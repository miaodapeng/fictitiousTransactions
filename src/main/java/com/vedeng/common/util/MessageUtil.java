package com.vedeng.common.util;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.base.Joiner;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.websocket.EchoSpcket;
import com.vedeng.system.dao.MessageMapper;
import com.vedeng.system.service.SendMessageService;
import com.vedeng.system.service.impl.SendMessageServiceImpl;

import net.sf.json.JSONObject;

/**
 * 
 * <b>Description:</b><br> 消息推送工具
 * @author scott
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.common.util
 * <br><b>ClassName:</b> MessageUtil
 * <br><b>Date:</b> 2017年12月9日 上午9:04:28
 */
public class MessageUtil extends BaseServiceimpl{
	private static Logger log = LoggerFactory.getLogger(MessageUtil.class);

	@Value("${websocket_url}")
	protected static String websocketUrl;

	/**
	 * <b>Description:</b><br> 
	 * @param messageTemplateId 站内信模板ID
	 * @param userIds 站内信接收用户集合
	 * @param params 站内信标题，内容参数
	 * @param url 站内信链接地址
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月9日 下午1:41:02
	 */
	public static Boolean sendMessage(Integer messageTemplateId,List<Integer> userIds,Map<String, String> params,String url,String... str ){
		if(null == userIds 
				||userIds.size() == 0){
			return false;
		}
		
		String userIdsStr = Joiner.on("_").join(userIds);
		String paramsStr = "";
		if(null !=params&&params.size() > 0){
			JSONObject json = JSONObject.fromObject(params);
			paramsStr = json.toString();
		}
		SendMessageService sendMessageService = new SendMessageServiceImpl(websocketUrl);
		String sendMsg = "{'toUserId':"+userIdsStr+",'templateId':"+messageTemplateId+",'parameters':'"+paramsStr+"','url':'"+url+"'}";
		try {
			if(str==null || str.length == 0){
				sendMessageService.receiveMsg(null, sendMsg);
			}else{
				sendMessageService.receiveMsg(null, sendMsg,str);
			}
			
		} catch (java.lang.Exception e) {
			log.error("sendMessage===",e);
			return false;
		}
		return true;
	}
	public static Boolean sendMessage2(Integer messageTemplateId,List<Integer> userIds,Map<String, String> params,String url,String... str ){
		if(null == userIds
				||userIds.size() == 0){
			return false;
		}
		String userIdsStr = Joiner.on("_").join(userIds);
		String paramsStr = "";
		if(null !=params&&params.size() > 0){
			JSONObject json = JSONObject.fromObject(params);
			paramsStr = json.toString();
		}
		SendMessageService sendMessageService = new SendMessageServiceImpl(websocketUrl);
		String sendMsg = "{'toUserId':"+userIdsStr+",'templateId':"+messageTemplateId+",'parameters':'"+paramsStr+"','url':'"+url+"'}";
		try {
			if(str==null || str.length == 0){
				sendMessageService.receiveMsg2(null, sendMsg);
			}else{
				sendMessageService.receiveMsg2(null, sendMsg,str);
			}

		} catch (java.lang.Exception e) {
			log.error("sendMessage===",e);
			return false;
		}
		return true;
	}


	public static String getWebsocketUrl() {
		return websocketUrl;
	}

	public static void setWebsocketUrl(String websocketUrl) {
		MessageUtil.websocketUrl = websocketUrl;
	}
}
