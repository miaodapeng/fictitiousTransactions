package com.vedeng.goods.service;

import java.util.List;
import java.util.Map;

import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.goods.model.Unit;
import com.vedeng.goods.model.UnitGroup;

/**
 * <b>Description:</b><br> 产品管理接口
 * @author leo.yang
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.goods.service
 * <br><b>ClassName:</b> UnitService
 * <br><b>Date:</b> 2017年5月12日 下午4:48:54
 */
public interface UnitService {

	/**
	 * <b>Description:</b><br> 获取单位信息（根据主键）
	 * @param unit
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年7月3日 下午3:10:22
	 */
	Unit getUnitById(Unit unit);

	/**
	 * <b>Description:</b><br> 获取单位分组列表
	 * @param unitGroup
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年7月3日 下午3:11:26
	 */
	List<UnitGroup> getUnitGroupList(UnitGroup unitGroup);

	/**
	 * <b>Description:</b><br> 保存添加的单位
	 * @param unit
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年7月3日 下午3:11:56
	 */
	ResultInfo<?> addUnit(Unit unit);

	/**
	 * <b>Description:</b><br> 获取单位列表（分页）
	 * @param unit
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年7月3日 下午3:12:14
	 */
	Map<String, Object> getUnitListPage(Unit unit, Page page);

	/**
	 * <b>Description:</b><br> 保存编辑的单位
	 * @param unit
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年7月3日 下午3:13:23
	 */
	ResultInfo<?> editUnit(Unit unit);

	/**
	 * <b>Description:</b><br> 删除单位（根据主键）
	 * @param unit
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年7月3日 下午3:15:54
	 */
	ResultInfo<?> delUnitById(Unit unit);

	/**
	 * <b>Description:</b><br> 获取单位列表（不分页）
	 * @param unit
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年7月3日 下午3:19:56
	 */
	List<Unit> getAllUnitList(Unit unit);
}
