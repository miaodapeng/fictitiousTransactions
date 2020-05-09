package com.vedeng.department.service;

import com.vedeng.department.model.DepartmentsHospital;
import com.vedeng.department.model.DepartmentsHospitalGenerate;

import java.util.List;
import java.util.Map;

/**
 * 收费项目
 * <p>
 * Title: DepartmentsHospitalService
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author Bill
 * @date 2019年4月12日
 */
public interface DepartmentsHospitalService {

	/**
	 * 获取收费项目
	 * <p>
	 * Title: getDepartmentsHospitalByParam
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param paramMap
	 * @return
	 * @author Bill
	 * @date 2019年4月12日
	 */
	List<DepartmentsHospital> getDepartmentsHospitalByParam(Map<String, Object> paramMap);

	/**
	 * 下拉框
	 * @param name
	 * @return
	 */
	List<DepartmentsHospitalGenerate> getAllDepartmentsHospital(String name,int size);

}
