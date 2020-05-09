package com.vedeng.department.dao;

import com.vedeng.department.model.DepartmentFeeItems;
import com.vedeng.department.model.DepartmentsHospital;

import java.util.List;
import java.util.Map;

public interface DepartmentsHospitalMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table T_DEPARTMENTS_HOSPITAL
	 * @mbg.generated  Tue Apr 09 19:02:26 CST 2019
	 */
	int deleteByPrimaryKey(Integer departmentId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table T_DEPARTMENTS_HOSPITAL
	 * @mbg.generated  Tue Apr 09 19:02:26 CST 2019
	 */
	int insert(DepartmentsHospital record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table T_DEPARTMENTS_HOSPITAL
	 * @mbg.generated  Tue Apr 09 19:02:26 CST 2019
	 */
	int insertSelective(DepartmentsHospital record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table T_DEPARTMENTS_HOSPITAL
	 * @mbg.generated  Tue Apr 09 19:02:26 CST 2019
	 */
	DepartmentsHospital selectByPrimaryKey(Integer departmentId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table T_DEPARTMENTS_HOSPITAL
	 * @mbg.generated  Tue Apr 09 19:02:26 CST 2019
	 */
	int updateByPrimaryKeySelective(DepartmentsHospital record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table T_DEPARTMENTS_HOSPITAL
	 * @mbg.generated  Tue Apr 09 19:02:26 CST 2019
	 */
	int updateByPrimaryKey(DepartmentsHospital record);

	/**
	 * 获取列表
	 * <p>Title: getHostipalDepartmentInfoListPage</p>  
	 * <p>Description: </p>  
	 * @param paramMap
	 * @return  
	 * @author Bill
	 * @date 2019年4月10日
	 */
	List<DepartmentsHospital> getHostipalDepartmentInfoListPage(Map<String, Object> paramMap);

	/**
	 * @description 获取收费项目
	 * @author bill
	 * @param
	 * @date 2019/4/15
	 */
    List<DepartmentFeeItems> getDepartmentFeeItemsInfo(Map<String, Object> paramMap);

    /**
     * @description 科室详情
     * @author bill
     * @param
     * @date 2019/4/16
     */
    DepartmentsHospital getDepartmentsHospitalInfoById(Map<String, Object> paramMap);

    Integer deleteByParam(Map<String, Object> paramMap);

    /**
     * @description 编辑页
     * @author bill
     * @param
     * @date 2019/5/23
     */
	DepartmentsHospital getDepartmentsHospitalInfoByIdEdit(Map<String, Object> paramMap);
}