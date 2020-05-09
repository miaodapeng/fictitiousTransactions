package com.vedeng.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.vedeng.system.model.SysOptionDefinition;

import javax.inject.Named;

@Named("sysOptionDefinitionMapper")
public interface SysOptionDefinitionMapper {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table T_SYS_OPTION_DEFINITION
	 *
	 * @mbg.generated Mon Mar 25 13:59:53 CST 2019
	 */
	int deleteByPrimaryKey(Integer sysOptionDefinitionId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table T_SYS_OPTION_DEFINITION
	 *
	 * @mbg.generated Mon Mar 25 13:59:53 CST 2019
	 */
	int insert(SysOptionDefinition record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table T_SYS_OPTION_DEFINITION
	 *
	 * @mbg.generated Mon Mar 25 13:59:53 CST 2019
	 */
	int insertSelective(SysOptionDefinition record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table T_SYS_OPTION_DEFINITION
	 *
	 * @mbg.generated Mon Mar 25 13:59:53 CST 2019
	 */
	SysOptionDefinition selectByPrimaryKey(Integer sysOptionDefinitionId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table T_SYS_OPTION_DEFINITION
	 *
	 * @mbg.generated Mon Mar 25 13:59:53 CST 2019
	 */
	int updateByPrimaryKeySelective(SysOptionDefinition record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table T_SYS_OPTION_DEFINITION
	 *
	 * @mbg.generated Mon Mar 25 13:59:53 CST 2019
	 */
	int updateByPrimaryKey(SysOptionDefinition record);

	/**
	 * 根据parentId查询字典库信息
	 * <p>
	 * Title: getSysOptionDefinitionByParam
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param paramMap
	 * @return
	 * @author Bill
	 * @date 2019年3月25日
	 */
	List<SysOptionDefinition> getSysOptionDefinitionByParam(List<Integer> scopeList);

	List<SysOptionDefinition> getSysOptionDefinitionByOptionType(@Param("optionType") String optionType);

	Integer getSysOptionDefinitionIdByTitleAndParentTitle(@Param("title") String title, @Param("parentTitle") String parentTitle);

	List<SysOptionDefinition> getSysOptionDefinitionByParentTitle(String title);
}