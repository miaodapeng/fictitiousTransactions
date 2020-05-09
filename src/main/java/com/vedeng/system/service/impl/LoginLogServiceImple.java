package com.vedeng.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.vedeng.authorization.dao.UserLoginLogMapper;
import com.vedeng.authorization.model.UserLoginLog;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.system.service.LoginLogService;

@Service("loginLogService")
public class LoginLogServiceImple extends BaseServiceimpl implements LoginLogService {
	
	@Autowired
	@Qualifier("userLoginLogMapper")
	private UserLoginLogMapper userLoginLogMapper;
	
	@Override
	public List<UserLoginLog> querylistPage(UserLoginLog userLoginLog, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("userLoginLog", userLoginLog);
		return userLoginLogMapper.querylistPage(map); 
	}
	/**
	 * <b>Description:</b><br> 保存或更新
	 * @param userLoginLog
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年5月22日 上午10:26:04
	 */
	@Override
	public int saveOrUpdate(UserLoginLog userLoginLog) {
		if(null!=userLoginLog&&null!=userLoginLog.getUserLoginLogId()){
			return userLoginLogMapper.updateByPrimaryKey(userLoginLog);
		}else {
			return userLoginLogMapper.insert(userLoginLog);
		}
	}

}
