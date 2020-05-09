package com.vedeng.system.service;

import java.util.List;

import com.vedeng.authorization.model.Region;
import com.vedeng.authorization.model.vo.RegionVo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;

/**
 * <b>Description:</b><br>
 * 地区service
 * 
 * @author Jerry
 * @Note <b>ProjectName:</b> erp <br>
 *       <b>PackageName:</b> com.vedeng.system.service <br>
 *       <b>ClassName:</b> RegionService <br>
 *       <b>Date:</b> 2017年4月25日 上午11:30:02
 */
public interface RegionService extends BaseService {
	/**
	 * <b>Description:</b><br>
	 * 根据上级ID查询地区
	 * 
	 * @param parentId
	 *            上级ID
	 * @return List<Region>
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年4月25日 上午11:30:13
	 */
	List<Region> getRegionByParentId(Integer parentId);

	/**
	 * <b>Description:</b><br>
	 * 获取地区层级
	 * 
	 * @param regionId
	 *            地区ID
	 * @param returnType
	 *            1:数组,2:字符串
	 * @return Object
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年4月25日 上午11:30:30
	 */
	Object getRegion(Integer regionId, Integer returnType);

	/**
	 * <b>Description:</b><br>
	 * 根据主键查询
	 * 
	 * @param RegionId
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年10月13日 上午11:14:20
	 */
	Region getRegionByRegionId(Integer RegionId);

	/**
	 * <b>Description:</b><br>
	 * 获取地区信息(名称+上级ID)
	 * 
	 * @param region
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年11月28日 下午2:23:49
	 */
	Region getRegion(Region region);

	/**
	 * <b>Description:</b><br>
	 * 查询分页
	 * 
	 * @param region
	 * @param page
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2018年3月16日 上午10:57:06
	 */
	List<Region> getRegionListPage(Region region, Page page);

	/**
	 * <b>Description:</b><br>
	 * 新增保存
	 * 
	 * @param regionVo
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2018年3月16日 下午1:39:06
	 */
	int saveRegin(RegionVo regionVo);

	/**
	 * <b>Description:</b><br>
	 * 编辑保存
	 * 
	 * @param regionVo
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2018年3月16日 下午1:39:06
	 */
	int saveEditRegin(Region region);

	/**
	 * <b>Description:</b><br>
	 * 根据区域获取地区信息
	 * 
	 * @param area
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2018年8月20日 上午10:42:26
	 */
	RegionVo getRegionByArea(String area);

	/**
	 * 
	 * <b>Description: 根据最小地区Id获取省市区ID以,分割</b><br>
	 * 
	 * @param minRegionId
	 * @return <b>Author: Franlin.wu</b> <br>
	 *         <b>Date: 2018年11月28日 下午6:07:24 </b>
	 */
	String getRegionIdStringByMinRegionId(Integer minRegionId);

	String getRegionNameStringByMinRegionIds(String regionIds);

	/**
	 * <b>Description:</b><br>
	 * 获取所有的城市或者县级市或者区
	 * 
	 * @param :a
	 * @return :a
	 * @Note <b>Author:</b> Bert <br>
	 *       <b>Date:</b> 2019/1/21 10:08
	 */
	List<RegionVo> getCityList(RegionVo regionVo);
}
