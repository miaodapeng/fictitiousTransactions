package com.vedeng.system.dao;

import com.vedeng.system.model.MessageConfig;

public interface MessageConfigMapper {
    int deleteByPrimaryKey(Integer messageConfigId);

    int insert(MessageConfig record);

    int insertSelective(MessageConfig record);

    MessageConfig selectByPrimaryKey(Integer messageConfigId);

    int updateByPrimaryKeySelective(MessageConfig record);

    int updateByPrimaryKey(MessageConfig record);
}