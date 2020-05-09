package com.vedeng.system.service;

import java.util.List;

import javax.websocket.Session;

import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.system.model.Message;
import com.vedeng.system.model.MessageUser;

public interface SendMessageService extends BaseService{
    /**
     * 
     * <b>Description:</b>发送消息
     * @param object
     * @param sendMsg void
     * @Note
     * <b>Author：</b> scott.zhu
     * <b>Date:</b> 2019年3月21日 上午8:46:35
     */
	void receiveMsg(Session session, String msg, String... str);
    void receiveMsg2(Session session, String msg, String... str);




}
