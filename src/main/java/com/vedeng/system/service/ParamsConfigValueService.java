package com.vedeng.system.service;

import java.util.List;

import com.vedeng.authorization.model.User;
import com.vedeng.common.service.BaseService;
import com.vedeng.system.model.ParamsConfigValue;

public interface ParamsConfigValueService extends BaseService {
	
	/**
	 * <b>Description:</b><br> 查询ParamsValue的集合
	 * @param paramsConfigValue
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年8月22日 下午4:57:12
	 */
	List<Integer> getParamsValueList(ParamsConfigValue paramsConfigValue);

	/**
	 * <b>Description:</b><br> 默认负责人
	 * @param companyId
	 * @param quoteType
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年7月23日 下午3:16:47
	 */
	User getQuoteConsultant(Integer companyId, int paramsKey);

}
