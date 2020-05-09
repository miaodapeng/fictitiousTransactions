package com.vedeng.system.dao;

import java.util.List;
import java.util.Map;

import javax.inject.Named;

import com.vedeng.common.thread.ext.ThreadExt;
import com.vedeng.system.model.Message;
@Named("messageMapper")
public interface MessageMapper {
    int deleteByPrimaryKey(Integer messageId);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Integer messageId);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);
    /**
     * 
     * <b>Description:</b><br>根据用户获取消息列表数据 
     * @param map
     * @return
     * @Note
     * <b>Author:</b> Micheal
     * <br><b>Date:</b> 2017年7月6日 下午1:42:37
     */
	List<Message> getMessageByUserlistpage(Map<String, Object> map);
	/**
	 * 
	 * <b>Description:</b><br> 根据条件获取消息列表条数
	 * @param map
	 * @return
	 * @Note
	 * <b>Author:</b> Micheal
	 * <br><b>Date:</b> 2017年7月7日 下午2:04:24
	 */
	Integer getMessageCount(Map<String, Object> map);
	
	/**
	 * 
	 * <b>Description: 查询商机是否读取状态</b><br> 
	 * @param req
	 * @return
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年7月31日 下午5:35:42 </b>
	 */
	Message queryBussinessStatus(ThreadExt req);

	/**
	 * 查询用户所有未读消息
	 * @param userId
	 * @return
	 */
	List<Message> getMessageList(Integer userId);

	/**
	 * 查询用户所有未读商机消息
	 * @param userId
	 * @return
	 */
	List<Message> getMessageSjList(Integer userId);

    /** 
    * @Description: 查询客户未读商机数量
    * @Param: [] 
    * @return: java.lang.Integer 
    * @Author: scott.zhu 
    * @Date: 2019/4/12 
    */
    Integer queryNoReadMsgNum(Integer userId);
}