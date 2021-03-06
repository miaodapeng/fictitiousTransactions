package com.vedeng.system.dao;

import com.vedeng.firstengage.model.FirstEngage;
import com.vedeng.goods.model.StandardCategory;

import java.util.List;
import java.util.Map;

public interface StandardCategoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_STANDARD_CATEGORY
     *
     * @mbg.generated Thu Mar 28 14:00:11 CST 2019
     */
    int deleteByPrimaryKey(Integer standardCategoryId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_STANDARD_CATEGORY
     *
     * @mbg.generated Thu Mar 28 14:00:11 CST 2019
     */
    int insert(StandardCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_STANDARD_CATEGORY
     *
     * @mbg.generated Thu Mar 28 14:00:11 CST 2019
     */
    int insertSelective(StandardCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_STANDARD_CATEGORY
     *
     * @mbg.generated Thu Mar 28 14:00:11 CST 2019
     */
    StandardCategory selectByPrimaryKey(Integer standardCategoryId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_STANDARD_CATEGORY
     *
     * @mbg.generated Thu Mar 28 14:00:11 CST 2019
     */
    int updateByPrimaryKeySelective(StandardCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_STANDARD_CATEGORY
     *
     * @mbg.generated Thu Mar 28 14:00:11 CST 2019
     */
    int updateByPrimaryKey(StandardCategory record);

    /**
     * 获取新国标分类
     * <p>Title: getNewStandardCategory</p>  
     * <p>Description: </p>  
     * @param paramMap
     * @return  
     * @author Bill
     * @date 2019年3月28日
     */
	List<StandardCategory> getNewStandardCategory(Map<String, Object> paramMap);
	

	List<StandardCategory> getParentStandardCateList(StandardCategory standardCategory);

	List<StandardCategory> getStandardCategoryList(StandardCategory standardCategory);
	
	/**
	 * 
	 * <b>Description:获取新国标分类的所有分类的名称，显示到最小分类</b><br> 可根据categoryName模糊查询,standardCategoryId精确查询
	 * @param standardCategory
	 * @return
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年6月1日 上午10:21:39 </b>
	 */
	List<StandardCategory> getNewStandardCategoryList(StandardCategory standardCategory);
	
	/**
	 * 
	 * <b>Description: 根据parentId查询新国标其子类</b><br> 
	 * @param parentId
	 * @return
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年10月7日 下午6:20:39 </b>
	 */
	List<StandardCategory> selectByParentId(Integer parentId);

	/**
	 * @description 查询新国标分类
	 * @author bill
	 * @param
	 * @date 2019/4/24
	 */
    List<StandardCategory> getNewStandardCategoryByName(Map<String, Object> paramMap);

	/**
	 *
	 * <p>Title: getStandardCategoryStrMap</p>
	 * <p>Description: </p>
	 * @param firstEngageList
	 * @return
	 * @author Bill
	 * @date 2019年3月29日
	 */
	List<Map<String, Object>> getStandardCategoryStrMap(List<FirstEngage> firstEngageList);
}