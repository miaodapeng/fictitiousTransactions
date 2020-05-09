package com.vedeng.system.service;

import com.vedeng.authorization.model.*;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.model.SelectModel;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;

import java.util.List;

/**
 * @Author wayne.liu
 * @Description 平台类
 * @Date 2019/6/6 9:32
 */
public interface PlatformService extends BaseService {

	/**
	 *  查询应用平台列表
	  * @Author wayne.liu
	  * @Date  2019/6/6 9:40
	  * @Param 
	  * @return list<Platform>
	  */
	List<Platform> queryList();

	/**
	 *  查询特定应用平台列表
	 * @Author wayne.liu
	 * @Date  2019/6/6 9:40
	 * @Param
	 * @return list<Platform>
	 */
	Platform queryById(Integer id);
}
