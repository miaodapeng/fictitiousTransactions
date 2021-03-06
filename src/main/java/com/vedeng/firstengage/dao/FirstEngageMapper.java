package com.vedeng.firstengage.dao;

import com.task.model.ReadFirst;
import com.vedeng.common.page.Page;
import com.vedeng.firstengage.model.FirstEngage;
import com.vedeng.firstengage.model.FirstEngageSearchInfo;
import com.vedeng.firstengage.model.SimpleMedicalCategory;
import com.vedeng.goods.model.GoodsCount;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface FirstEngageMapper {

	/**
	 * <b>Description:</b>获取商品的管理类别，器械，新旧医疗资质<br>
	 * @param
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:</b> 2020/3/17
	 */
	SimpleMedicalCategory getSimpleMedicalCategory(@Param("skuNo")String skuNo);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table T_FIRST_ENGAGE
	 * @mbg.generated  Wed Mar 20 11:35:21 CST 2019
	 */
	int deleteByPrimaryKey(Integer firstEngageId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table T_FIRST_ENGAGE
	 * @mbg.generated  Wed Mar 20 11:35:21 CST 2019
	 */
	int insert(FirstEngage record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table T_FIRST_ENGAGE
	 * @mbg.generated  Wed Mar 20 11:35:21 CST 2019
	 */
	int insertSelective(FirstEngage record);

	/**
	  
	 */
	FirstEngage selectByPrimaryKey(Integer firstEngageId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table T_FIRST_ENGAGE
	 * @mbg.generated  Wed Mar 20 11:35:21 CST 2019
	 */
	int updateByPrimaryKeySelective(FirstEngage record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table T_FIRST_ENGAGE
	 * @mbg.generated  Wed Mar 20 11:35:21 CST 2019
	 */
	int updateByPrimaryKey(FirstEngage record);

	
	/**
	 * 获取商品首营列表
	 * <p>Title: getAfterSalesPage</p>  
	 * <p>Description: </p>  
	 * @param paramMap
	 * @return  
	 * @author Bill
	 * @date 2019年3月20日
	 */
	List<FirstEngage> getFirstEngageInfoList(Map<String, Object> paramMap);

	/**
	 * 获取搜索列表
	 * <p>Title: getFirstSearchInfo</p>  
	 * <p>Description: </p>  
	 * @param paramMap
	 * @return  
	 * @author Bill
	 * @date 2019年3月21日
	 */
	List<FirstEngageSearchInfo> getFirstSearchInfo(Map<String, Object> paramMap);

	/**
	 * 删除首映商品信息
	 * <p>Title: deleteFirstEngage</p>  
	 * <p>Description: </p>  
	 * @param paramMap
	 * @return  
	 * @author Bill
	 * @date 2019年3月21日
	 */
	Integer deleteFirstEngage(Map<String, Object> paramMap);

	/**
	 * @description 不同状态对应的
	 * @author bill
	 * @param
	 * @date 2019/4/22
	 */
    Map<String, Integer> getStatusCountByParam(Map<String, Object> paramMap);

    Integer getOverDateCount(Map<String, Object> paramMap);

    Integer getOldStandardId(String cellval);

	Integer getNewStandardId(Map<String, Object> param);

	void addProductCompany(ReadFirst readFirst);

	void addRegisterNumber(ReadFirst readFirst);

	void updateExcelRegisterNumber(ReadFirst readFirst);

	void addFirstInfo(ReadFirst readFirst);

	void updateExcelFirstEngage(ReadFirst readFirst);

	List<GoodsCount> getCountAll();

}