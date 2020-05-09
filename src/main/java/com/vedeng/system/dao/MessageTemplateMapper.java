package com.vedeng.system.dao;

import java.util.List;
import java.util.Map;
import javax.inject.Named;
import com.vedeng.system.model.MessageTemplate;

@Named("messageTemplateMapper")
public interface MessageTemplateMapper {
	
    int deleteByPrimaryKey(Integer messageTemplateId);

    int insert(MessageTemplate record);

    int insertSelective(MessageTemplate record);

    MessageTemplate selectByPrimaryKey(Integer messageTemplateId);

    int updateByPrimaryKeySelective(MessageTemplate record);

    int updateByPrimaryKey(MessageTemplate record);
    /**
     * 
     * <b>Description:</b><br> 消息模板
     * @param map
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年11月23日 上午11:13:22
     */
	List<MessageTemplate> querylistPage(Map<String, Object> map);


}