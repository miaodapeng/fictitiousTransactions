package com.vedeng.system.service;

import java.util.List;
import java.util.Map;

import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.system.model.SysOptionDefinition;

/**
 * <b>Description:</b><br> 
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.trader.service
 * <br><b>ClassName:</b> SysOptionDefinitionService
 * <br><b>Date:</b> 2017年5月12日 下午2:22:05
 */
public interface SysOptionDefinitionService extends BaseService {
	/**
	 * <b>Description:</b><br> 批量查询字典
	 * @param scopeList
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年5月12日 下午2:17:28
	 */
	Map<Integer,List<SysOptionDefinition>> getOptionByScopeList(List<Integer> scopeList);
	
	/**
	 * <b>Description:</b><br> 查询字典
	 * @param scope
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年5月12日 下午2:21:57
	 */
	List<SysOptionDefinition> getOptionByScope(Integer scope);
	
	/**
	 * <b>Description:</b><br> 分页搜索数据字典
	 * @param sysOptionDefinition
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年9月7日 上午9:19:19
	 */
	Map<String,Object>  querylistPage(SysOptionDefinition sysOptionDefinition,Page page);

	/**
	 * <b>Description:</b><br> 根据ID查询字典
	 * @param sysOptionDefinitionId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年9月7日 上午11:37:47
	 */
	SysOptionDefinition getOptionById(SysOptionDefinition sysOptionDefinition);

	/**
	 * <b>Description:</b><br> 保存新增数据字典
	 * @param sysOptionDefinition
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年9月7日 下午1:45:29
	 */
	ResultInfo saveAdd(SysOptionDefinition sysOptionDefinition);

	/**
	 * <b>Description:</b><br> 保存编辑数据字典
	 * @param sysOptionDefinition
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年9月7日 下午2:28:52
	 */
	ResultInfo saveEdit(SysOptionDefinition sysOptionDefinition);

	/**
	 * <b>Description:</b><br> 查询子集(不分页)
	 * @param sysOptionDefinition
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年9月7日 下午2:52:07
	 */
	List<SysOptionDefinition> getChilds(SysOptionDefinition sysOptionDefinition);

	/**
	 * <b>Description:</b><br> 启用\禁用数据字典
	 * @param sysOptionDefinition
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年9月7日 下午3:16:07
	 */
	ResultInfo changeStatus(SysOptionDefinition sysOptionDefinition);

	/**
	 * 获取字典库信息
	 * <p>Title: getSysOptionDefinitionByParam</p>  
	 * <p>Description: </p>  
	 * @param paramMap
	 * @return  
	 * @author Bill
	 * @date 2019年3月25日
	 */
	Map<String,List<SysOptionDefinition>> getSysOptionDefinitionByParam(List<Integer> scopeList);
} 
