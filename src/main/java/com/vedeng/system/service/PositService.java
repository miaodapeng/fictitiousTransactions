package com.vedeng.system.service;

import java.util.List;

import com.vedeng.authorization.model.Position;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.system.model.SysOptionDefinition;


/**
 * <b>Description:</b><br> 职位管理接口
 * @author duke
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.system.service
 * <br><b>ClassName:</b> PositService
 * <br><b>Date:</b> 2017年4月25日 下午6:28:00
 */
public interface PositService extends BaseService {

	/**
	 * <b>Description:</b><br> 根据部门获取职位
	 * @param orgId
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午6:28:23
	 */
	List<Position> getPositByOrgId(Integer orgId);
	/**
	 * <b>Description:</b><br> 分页搜索职位
	 * @param position
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午6:28:32
	 */
	List<Position> querylistPage(Position position,Page page);
	
	/**
	 * <b>Description:</b><br> 根据主键查询对应详细信息
	 * @param position
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午6:28:44
	 */
	public Position getPositionByKey(Position position);
	
	/**
	 * <b>Description:</b><br> 新增数据保存
	 * @param position
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午6:28:51
	 */
	public ResultInfo<?> insert(Position position);
	
	/**
	 * <b>Description:</b><br> 修改职位信息
	 * @param position
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午6:28:57
	 */
	public ResultInfo<?> update(Position position);
	
	/**
	 * <b>Description:</b><br> 查询用户职位 
	 * @param userId
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午6:29:04
	 */
	List<Position> getPositionByUserId(Integer userId);
	
	/**
	 * <b>Description:</b><br> 部门集合查询职位 
	 * @param orgIds
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午6:29:10
	 */
	List<Position> getPositByOrgIds(List<Integer> orgIds);
	
	/**
	 * <b>Description:</b><br> 删除职位
	 * @param posit 职位bean
	 * @return 成功1 失败0
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 下午1:03:48
	 */
	Integer deletePosit(Position posit);
	
	/**
	 * <b>Description:</b><br> 获取职位类型列表信息
	 * @param id
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月15日 下午7:21:15
	 */
	List<SysOptionDefinition> getPositType(Integer id);
	
}
