package com.vedeng.ordergoods.dao;

import java.util.List;

import javax.inject.Named;

import org.apache.ibatis.annotations.Param;

import com.vedeng.ordergoods.model.ROrdergoodsGoodsJCategory;

@Named("rOrdergoodsGoodsJCategoryMapper")
public interface ROrdergoodsGoodsJCategoryMapper {
    int delete(ROrdergoodsGoodsJCategory rOrdergoodsGoodsJCategory);

    int insert(ROrdergoodsGoodsJCategory rOrdergoodsGoodsJCategory);

    int update(ROrdergoodsGoodsJCategory rOrdergoodsGoodsJCategory);
    
    ROrdergoodsGoodsJCategory getByROrdergoodsGoodsJCategory(ROrdergoodsGoodsJCategory rOrdergoodsGoodsJCategory);

	/**
	 * <b>Description:</b><br> 获取产品ID
	 * @param ordergoodsCategoryIds
	 * @param ordergoodsStoreId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月28日 下午4:58:09
	 */
	List<Integer> getGoodsIdsByCategoryIds(@Param("ordergoodsCategoryIds")List<Integer> ordergoodsCategoryIds, @Param("ordergoodsStoreId")Integer ordergoodsStoreId);
	
	ROrdergoodsGoodsJCategory getById(ROrdergoodsGoodsJCategory rOrdergoodsGoodsJCategory);
}