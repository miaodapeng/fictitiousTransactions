package com.vedeng.rabbitmqlogs.dao;

import com.vedeng.rabbitmqlogs.model.RabbitmqLogs;

public interface RabbitmqLogsMapper {
    int deleteByPrimaryKey(Integer rabbitmqLogsId);

    int insert(RabbitmqLogs record);

    int insertSelective(RabbitmqLogs record);

    RabbitmqLogs selectByPrimaryKey(String orderNo);

    int updateByPrimaryKeySelective(RabbitmqLogs record);

    int updateByPrimaryKeyWithBLOBs(RabbitmqLogs record);

    int updateByPrimaryKey(RabbitmqLogs record);

}