package com.report.dao;

import java.util.List;

import javax.inject.Named;

import org.apache.ibatis.annotations.Param;

import com.vedeng.authorization.model.Action;

@Named("exportMapper")
public interface ExportMapper {
	
	List<Action> queryList();
	
	List<Action> queryListSize(@Param("pageNo")int pageNo,@Param("pageSize")int pageSize);
	
	int getListCount();

}
