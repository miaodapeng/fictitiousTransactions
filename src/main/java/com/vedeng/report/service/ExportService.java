package com.vedeng.report.service;

import java.util.List;

import com.vedeng.authorization.model.Action;

public interface ExportService {

	List<Action> selectList();
	
	List<Action> queryListSize(int pageNo,int pageSize);
	
	int getListCount();
}
