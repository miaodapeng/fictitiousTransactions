package com.vedeng.authorization.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.vedeng.authorization.model.Region;
import com.vedeng.authorization.model.vo.RegionVo;

/**
 * <b>Description:</b><br> 地区Mapper
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.authorization.dao
 * <br><b>ClassName:</b> RegionMapper
 * <br><b>Date:</b> 2017年4月25日 上午9:55:22
 */
public interface RegionMapper {
	
	/**
	 * <b>Description:</b><br> 根据父级地区ID查询子集地区
	 * @param parentId 父级地区ID
	 * @return List<Region>
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 上午9:55:44
	 */
	List<Region> getRegionByParentId(Integer parentId);
	
	/**
	 * <b>Description:</b><br> 查询城市
	 * @param parentId 父级地区ID
	 * @return List<Region>
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 上午9:55:44
	 */
	List<Region> getRegionByParam(Region region);
	
	/**
	 * <b>Description:</b><br> 查询地区 
	 * @param regionId 地区ID
	 * @return Region
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 上午9:56:33
	 */
	Region getRegionById(Integer regionId);

	/**
	 * <b>Description:</b><br> 获取地区信息(名称+上级ID)
	 * @param region
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月28日 下午2:24:26
	 */
	Region getRegion(Region region);
	
	/**
	 * <b>Description:</b><br> 查询分页
	 * @param map
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年3月16日 上午11:03:04
	 */
	List<Region> getRegionListPage(Map<String, Object> map);
	
	/**
	 * <b>Description:</b><br> 新增
	 * @param region
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年3月16日 下午1:52:50
	 */
	int insert(Region region);
	
	/**
	 * <b>Description:</b><br> 修改
	 * @param region
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年3月16日 下午1:53:31
	 */
	int update(Region region);

	/**
	 * <b>Description:</b><br> 根据区域获取地区信息
	 * @param regionVo
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年8月20日 上午10:50:38
	 */
	RegionVo getRegionByArea(RegionVo regionVo);
	
	/**
	 * 
	 * <b>Description: 根据当前地区ID查询省市区的ID以,分割</b><br> 
	 * @param minRegionId
	 * @return
	 * <b>Author: Franlin.wu</b>  
	 * <br><b>Date: 2018年11月28日 下午6:21:14 </b>
	 */
	String getRegionIdStringByMinRegionId(@Param("minRegionId")Integer minRegionId);
	
	/**
	 * <b>Description:</b><br>
	 * 获取所有的城市或者县级市或者区
	 * @param :a
	 *@return :a
	 *@Note <b>Author:</b> Bert <br>
	 * <b>Date:</b> 2019/1/21 10:08
	 */
	List<RegionVo> getCityList(RegionVo regionVo);
}