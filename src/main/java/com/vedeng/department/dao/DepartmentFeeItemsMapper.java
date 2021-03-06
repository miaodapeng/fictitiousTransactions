package com.vedeng.department.dao;

import com.vedeng.department.model.DepartmentFeeItems;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface DepartmentFeeItemsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_DEPARTMENT_FEE_ITEMS
     *
     * @mbg.generated Tue Apr 09 16:31:49 CST 2019
     */
    int deleteByPrimaryKey(Integer departmentFeeItemsId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_DEPARTMENT_FEE_ITEMS
     *
     * @mbg.generated Tue Apr 09 16:31:49 CST 2019
     */
    int insert(DepartmentFeeItems record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_DEPARTMENT_FEE_ITEMS
     *
     * @mbg.generated Tue Apr 09 16:31:49 CST 2019
     */
    int insertSelective(DepartmentFeeItems record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_DEPARTMENT_FEE_ITEMS
     *
     * @mbg.generated Tue Apr 09 16:31:49 CST 2019
     */
    DepartmentFeeItems selectByPrimaryKey(Integer departmentFeeItemsId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_DEPARTMENT_FEE_ITEMS
     *
     * @mbg.generated Tue Apr 09 16:31:49 CST 2019
     */
    int updateByPrimaryKeySelective(DepartmentFeeItems record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_DEPARTMENT_FEE_ITEMS
     *
     * @mbg.generated Tue Apr 09 16:31:49 CST 2019
     */
    int updateByPrimaryKey(DepartmentFeeItems record);

    /**
     * @description 
     * @author bill
     * @param
     * @date 2019/5/22
     */
    List<Integer> getMinFeesIds(@Param(value = "collection") Set<Integer> collection);

    /**
     * @description 查询所有收费项目
     * @author bill
     * @param
     * @date 2019/5/22
     */
    List<DepartmentFeeItems> getFeesByIds(@Param(value = "collection") Set<Integer> collection);
}