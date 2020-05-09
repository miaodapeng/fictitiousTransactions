package com.vedeng.department.service;

import com.vedeng.authorization.model.User;
import com.vedeng.common.page.Page;
import com.vedeng.department.model.DepartmentsHospital;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 科室信息
 * <p>Title: HostipalDepartmentService</p>  
 * <p>Description: </p>  
 * @author Bill  
 * @date 2019年4月10日
 */
public interface HostipalDepartmentService {

	/**
	 * 科室列表
	 * <p>Title: getHostipalDepartmentInfo</p>  
	 * <p>Description: </p>  
	 * @param paramMap
	 * @return  
	 * @author Bill
	 * @date 2019年4月10日
	 */
	Map<String, Object> getHostipalDepartmentInfo(Map<String, Object> paramMap, Page page);

	/**
	 * 获取收费项目
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> getDepartmentFeeItemsInfo(Map<String, Object> paramMap);

    /**
     * @description 添加科室信息
     * @author bill
     * @param
     * @param departmentsHospital
	 * @param sessUser
     * @date 2019/4/16
     */
    Integer addDepartmentServiceInfo(DepartmentsHospital departmentsHospital, User sessUser);

    /**
     * @description 套餐详情
     * @author bill
     * @param
     * @date 2019/4/16
     */
	DepartmentsHospital getDepartmentsHospitalInfo(Map<String, Object> paramMap, Integer departmentsHospitalId, Integer pageMark);

	/**
	 * @description 删除科室
	 * @author bill
	 * @param
	 * @param departmentsHospital
     * @date 2019/5/5
	 */
	Integer deleteDeptHospital(Map<String, Object> paramMap, DepartmentsHospital departmentsHospital);

	/**
	 * @description 获取所有科室
	 * @author bill
	 * @param
	 * @date 2019/5/14
	 */
	List<DepartmentsHospital> getAllDepartmentsHospital(Map<String, Object> paramMap);

}
