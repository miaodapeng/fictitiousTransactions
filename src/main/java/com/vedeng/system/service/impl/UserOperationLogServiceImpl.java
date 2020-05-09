package com.vedeng.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vedeng.authorization.dao.UserOperationLogMapper;
import com.vedeng.authorization.model.UserOperationLog;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.system.service.UserOperationLogService;


/**
 * <b>Description:</b><br> 用户操作日志功能
 * @author leo.yang
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.system.service.impl
 * <br><b>ClassName:</b> UserOperationLogServiceImpl
 * <br><b>Date:</b> 2017年4月25日 上午9:32:32
 */
@Service("userOperationLogService")
public class UserOperationLogServiceImpl extends BaseServiceimpl implements UserOperationLogService {
	@Autowired
	@Qualifier("userOperationLogMapper")
	private UserOperationLogMapper userOperationLogMapper;
	
	@Override
	public List<UserOperationLog> querylistPage(UserOperationLog userOperationLog, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("userOperationLog", userOperationLog);
		return userOperationLogMapper.querylistPage(map);
	}

	@Override
	public UserOperationLog getUserOperationLogByKey(Integer userOperationLogId) {
		return userOperationLogMapper.selectByPrimaryKey(userOperationLogId);
	}
	/**
	 * <b>Description:</b><br> 保存
	 * @param userOperationLog
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年8月31日 下午1:07:41
	 */
	@Transactional
	@Override
	public Integer saveUserOperationLog(UserOperationLog userOperationLog) {
		
		return userOperationLogMapper.insertSelective(userOperationLog);
	}

}
