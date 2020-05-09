package com.vedeng.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.vedeng.system.model.MessageUser;

public interface MessageUserMapper {
    int deleteByPrimaryKey(Integer messageUserId);

    int insert(MessageUser record);

    int insertSelective(MessageUser record);

    MessageUser selectByPrimaryKey(Integer messageUserId);

    int updateByPrimaryKeySelective(MessageUser record);

    int updateByPrimaryKey(MessageUser record);
    /**
     * 
     * <b>Description:</b><br> 修改用户消息已读状态 
     * @param map
     * @return
     * @Note
     * <b>Author:</b> Micheal
     * <br><b>Date:</b> 2017年7月10日 上午9:10:18
     */
	Integer editMessageUser(Map<String, Object> map);
    /**
     * 
     * <b>Description:</b><br> 批量保存用户消息
     * @param messageUserList
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年11月29日 下午4:13:51
     */
	Integer saveBatch(@Param("messageUserList")List<MessageUser> messageUserList);
	
	/**
	 * 
	 * <b>Description: 修改用户消息已读状态 </b><br> 
	 * @param record 
	 * @return
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年8月14日 上午9:30:26 </b>
	 */
	int updateMessageViewStatus(MessageUser record);

}