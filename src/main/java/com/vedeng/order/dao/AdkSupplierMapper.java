package com.vedeng.order.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.vedeng.order.model.adk.TAdkSupplier;
import com.vedeng.order.model.adk.TAdkSupplierExample;

public interface AdkSupplierMapper {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table T_ADK_SUPPLIER
	 *
	 * @mbg.generated Thu Apr 11 09:50:49 CST 2019
	 */
	long countByExample(TAdkSupplierExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table T_ADK_SUPPLIER
	 *
	 * @mbg.generated Thu Apr 11 09:50:49 CST 2019
	 */
	int deleteByExample(TAdkSupplierExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table T_ADK_SUPPLIER
	 *
	 * @mbg.generated Thu Apr 11 09:50:49 CST 2019
	 */
	int deleteByPrimaryKey(Integer adkSupplierId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table T_ADK_SUPPLIER
	 *
	 * @mbg.generated Thu Apr 11 09:50:49 CST 2019
	 */
	int insert(TAdkSupplier record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table T_ADK_SUPPLIER
	 *
	 * @mbg.generated Thu Apr 11 09:50:49 CST 2019
	 */
	int insertSelective(TAdkSupplier record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table T_ADK_SUPPLIER
	 *
	 * @mbg.generated Thu Apr 11 09:50:49 CST 2019
	 */
	List<TAdkSupplier> selectByExample(TAdkSupplierExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table T_ADK_SUPPLIER
	 *
	 * @mbg.generated Thu Apr 11 09:50:49 CST 2019
	 */
	TAdkSupplier selectByPrimaryKey(Integer adkSupplierId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table T_ADK_SUPPLIER
	 *
	 * @mbg.generated Thu Apr 11 09:50:49 CST 2019
	 */
	int updateByExampleSelective(@Param("record") TAdkSupplier record, @Param("example") TAdkSupplierExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table T_ADK_SUPPLIER
	 *
	 * @mbg.generated Thu Apr 11 09:50:49 CST 2019
	 */
	int updateByExample(@Param("record") TAdkSupplier record, @Param("example") TAdkSupplierExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table T_ADK_SUPPLIER
	 *
	 * @mbg.generated Thu Apr 11 09:50:49 CST 2019
	 */
	int updateByPrimaryKeySelective(TAdkSupplier record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table T_ADK_SUPPLIER
	 *
	 * @mbg.generated Thu Apr 11 09:50:49 CST 2019
	 */
	int updateByPrimaryKey(TAdkSupplier record);
}