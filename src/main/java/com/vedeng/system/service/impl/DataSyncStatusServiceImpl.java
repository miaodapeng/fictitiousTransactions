package com.vedeng.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.system.dao.DataSyncStatusMapper;
import com.vedeng.system.model.DataSyncStatus;
import com.vedeng.system.service.DataSyncStatusService;

@Service("dataSyncStatusService")
public class DataSyncStatusServiceImpl extends BaseServiceimpl implements DataSyncStatusService {

	@Autowired
	@Qualifier("dataSyncStatusMapper")
	private DataSyncStatusMapper dataSyncStatusMapper;
	
	@Override
	public void addDataSyncStatus(DataSyncStatus dataSyncStatus) {
		dataSyncStatusMapper.insert(dataSyncStatus);
	}	

}
