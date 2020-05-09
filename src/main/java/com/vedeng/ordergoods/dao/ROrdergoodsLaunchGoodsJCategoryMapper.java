package com.vedeng.ordergoods.dao;

import java.util.List;
import java.util.Map;

import javax.inject.Named;

import com.vedeng.ordergoods.model.ROrdergoodsLaunchGoodsJCategory;
import com.vedeng.ordergoods.model.vo.ROrdergoodsLaunchGoodsJCategoryVo;

@Named("rOrdergoodsLaunchGoodsJCategoryMapper")
public interface ROrdergoodsLaunchGoodsJCategoryMapper {

    int insert(ROrdergoodsLaunchGoodsJCategory rOrdergoodsLaunchGoodsJCategory);

    ROrdergoodsLaunchGoodsJCategory selectByPrimaryKey(Integer rOrdergoodsLaunchGoodsJCategoryId);

    int update(ROrdergoodsLaunchGoodsJCategory rOrdergoodsLaunchGoodsJCategory);

	/**
	 * <b>Description:</b><br> 分页获取设备与试剂分类列表
	 * @param map
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月26日 下午2:46:13
	 */
	List<ROrdergoodsLaunchGoodsJCategoryVo> getGoodsCategoriesListPage(Map<String, Object> map);
	
	/**
	 * <b>Description:</b><br> 获取产品
	 * @param rOrdergoodsLaunchGoodsJCategory
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月27日 下午2:23:26
	 */
	ROrdergoodsLaunchGoodsJCategoryVo getSotreGoods(ROrdergoodsLaunchGoodsJCategoryVo rOrdergoodsLaunchGoodsJCategoryVo);
}