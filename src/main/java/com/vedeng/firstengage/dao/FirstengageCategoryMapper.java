package com.vedeng.firstengage.dao;

import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.apache.ibatis.annotations.Param;

import com.vedeng.firstengage.model.FirstengageCategory;


@Named("firstengageCategoryMapper")
public interface FirstengageCategoryMapper {

	List<FirstengageCategory> getCategorylistpage(Map<String, Object> map);
	
	
	List<FirstengageCategory> getCategoryList(FirstengageCategory category);
	
	
	FirstengageCategory getCategoryById(FirstengageCategory category);
	
	/**
	 * 
	 * <b>Description:通过分类ID获取所有分类名称</b><br>示例: 获取格式: 1级,2级,3级
	 * @param category 
	 * @return
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年4月25日 下午1:44:20 </b>
	 * <br><b>该方法已过时，请使用getCategoryListByCategoryName接口</b>
	 */
	@Deprecated
	String getAllCategoryNameById(FirstengageCategory category);
	
	int addCategory(FirstengageCategory category);
	
	
	int editCategory(FirstengageCategory category);
	
	
	Integer getParentLevel(FirstengageCategory category);
	
	Integer vailParentLevel(FirstengageCategory category);
	
	Integer vailCategoryLevel(FirstengageCategory category);
	
	Integer vailCateBottom(FirstengageCategory category);
	
	Integer updateGoodsCategory(@Param("oldCategoryId")Integer oldCategoryId,@Param("newCategoryId")Integer newCategoryId,@Param("modTime")long modTime,@Param("updater")Integer updater);
	
	Integer vailCategoryName(FirstengageCategory category);
	
	
	Integer delCategory(FirstengageCategory category);
	
	
	Integer batchUpdateLevel(List<Map<String,Object>> list);
	
	
	Integer vailCategoryToGoods(FirstengageCategory category);
	
	
	List<FirstengageCategory> getParentCateList(FirstengageCategory category);
	
	
	int vailGoodsBindCate(@Param("list")List<Integer> list);


	int updateCategoryTreenodes(FirstengageCategory category);


	int editCategoryTreenodes(FirstengageCategory category);


	Integer batchUpdateTreenodes(@Param("tree_list")List<Map<String, Object>> tree_list);

	/**
	 * <b>Description:</b><br> 获取每个分类下产品个数
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年11月10日 上午10:57:00
	 */
	List<Map<Integer, Integer>> getCategoryGoodsNum();


	List<FirstengageCategory> getCategoryIndexList(FirstengageCategory category);

	/**
	 * 
	 * <b>Description: 根据categoryName来搜索商品运营分类[展示最小分类]</b><br> 
	 * @param category
	 * @return
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年6月4日 上午10:41:18 </b>
	 */
	List<FirstengageCategory> getCategoryListByCategoryName(FirstengageCategory category);

	/**
	 * <b>Description:</b><br> 递归查询分类
	 * @param category
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年11月23日 上午11:02:09
	 */
	List<FirstengageCategory> getRecursionCategoryList(FirstengageCategory category);


	List<FirstengageCategory> getCategoryListByIdlist(@Param("categoryIdList")List<Integer> categoryIdList);


	List<FirstengageCategory> getIndexCategoryList(FirstengageCategory category);


	List<FirstengageCategory> getCategoryIndexListLevel(FirstengageCategory category);
	/**
	 * 
	 * <b>Description:</b><br>根据treenodes和level去查询0 
	 * @param category
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2018年6月15日 上午10:04:23
	 */
	List<FirstengageCategory> getCategoryByTreenodes(FirstengageCategory category);
	
	/**
	 * <b>Description:</b><br> 销售聚合页查询分类
	 * @param category
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年6月21日 下午1:42:57
	 */
	List<FirstengageCategory> getCategoryListByParam(FirstengageCategory category);
	
	/**
	 * <b>Description:</b><br> 根据名称查询销售聚合页产品分类
	 * @param category
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年6月21日 下午1:42:57
	 */
	List<FirstengageCategory> getCategoryListByName(FirstengageCategory category);
	
	/**
	 * <b>Description:</b><br> 查询一二级产品分类
	 * @param category
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年6月22日 上午10:43:38
	 */
	List<FirstengageCategory> getOneAndTwoCategoryList(FirstengageCategory category);
	
	/**
	 * <b>Description:</b><br> 根据二类查三类
	 * @param categoryId
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年6月22日 下午4:17:01
	 */
	List<Integer> getThreeCategoryIdListByTwo(@Param("categoryId")Integer categoryId);
	
}
