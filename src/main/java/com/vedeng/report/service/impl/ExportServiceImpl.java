package com.vedeng.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.report.dao.ExportMapper;
import com.vedeng.authorization.model.Action;
import com.vedeng.report.service.ExportService;

@Service("exportService")
public class ExportServiceImpl implements ExportService{

    @Autowired
    @Qualifier("exportMapper")
    private ExportMapper exportMapper;
	
	@Override
	public List<Action> selectList() {
		return exportMapper.queryList();
	}

	@Override
	public int getListCount() {
		return exportMapper.getListCount();
	}

	@Override
	public List<Action> queryListSize(int pageNo,int pageSize) {
		return exportMapper.queryListSize(pageNo,pageSize);
	}

}
