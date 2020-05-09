package com.vedeng.activiti.dao;

import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.apache.ibatis.annotations.Param;

import com.vedeng.activiti.model.ActionProcdef;

@Named("actionProcdefMapper")
public interface ActionProcdefMapper {

	List<ActionProcdef> getList(ActionProcdef actionProcdef);

	List<ActionProcdef> getListPage(Map<String, Object> map);

	int insert(ActionProcdef actionProcdef);

	int replace(ActionProcdef actionProcdef);
	
	int updateInfo(@Param("tableName")String tableName,@Param("id") String id,@Param("idValue") Integer idValue, @Param("key")String key, @Param("value")Integer value);
}