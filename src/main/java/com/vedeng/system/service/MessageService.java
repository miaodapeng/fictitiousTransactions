package com.vedeng.system.service;

import java.util.List;

import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.system.model.Message;
import com.vedeng.system.model.MessageUser;

public interface MessageService extends BaseService{
	
	/**
	 * 
	 * <b>Description:</b><br>根据用户获取消息列表数据 
	 * @param message
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Micheal
	 * <br><b>Date:</b> 2017年7月6日 下午1:29:03
	 */
	List<Message> getMessageByUserlistpage(Message message, Page page, Integer userId);
    /**
     * 
     * <b>Description:</b><br>根据条件获取消息列表条数
     * @param message 条件传入message对象中
     * @param userId
     * @return
     * @Note
     * <b>Author:</b> Micheal
     * <br><b>Date:</b> 2017年7月7日 下午1:47:27
     */
	Integer getMessageCount(Message message, Integer userId);
	/**
	 * 
	 * <b>Description:</b><br> 修改消息阅读状态
	 * @return
	 * @Note
	 * <b>Author:</b> Micheal
	 * <br><b>Date:</b> 2017年7月7日 下午5:18:23
	 */
	ResultInfo editMessageUserIsView(Integer userId, Integer messageUserId);
	/**
	 * 
	 * <b>Description:</b><br>新增消息
	 * @param message
	 * @return 
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年11月29日 下午3:52:59
	 */
	Integer saveMessage(Message message);
	/**
	 * 
	 * <b>Description:</b><br> 批量保存关联用户
	 * @param messageUserList
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年11月29日 下午4:08:06
	 */
	void saveMessageList(List<MessageUser> messageUserList);
	
	/**
	 * 
	 * <b>Description: 读取站内信息后将未读状态改为已读状态</b><br> 
	 * @param userId
	 * @param messageId
	 * @return
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年8月14日 上午9:17:29 </b>
	 */
	ResultInfo<?> modifyMessageViewStatus(MessageUser messageUser);

	/**
	 *
	 * <b>Description: 查询用户所有的未读消息</b><br>
	 * @param userId
	 * @return
	 * <b>Author: scott</b>
	 * <br><b>Date: 2019年2月22日 下午2:17:29 </b>
	 */
	List<Message> getMessageList(Integer userId);
 
    /** 
    * @Description: 查询用户未读的商机数量
    * @Param: [message] 
    * @return: java.lang.Integer 
    * @Author: scott.zhu 
    * @Date: 2019/4/12 
    */
	Integer queryNoReadMsgNum(Integer userId);
}
