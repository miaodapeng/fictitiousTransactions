package com.vedeng.department.service;

import com.vedeng.department.model.DepartmentFeeItems;
import com.vedeng.department.model.DepartmentsHospital;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 科室信息
 * <p>Title: DepartmentFeeItemsMappingService</p>
 * <p>Description: </p>  
 * @author Bill  
 * @date 2019年4月10日
 */
public interface DepartmentFeeItemsMappingService {



	/**
	 * @description
	 * @author bill
	 * @param
	 * @date 2019/4/16
	 */
    Integer addDepartmentFeeItemsMappingInfos(Map<String, Object> paramMap);

    /**
     * @description 删除收费项目中间表数据
     * @author bill
     * @param
     * @date 2019/4/28
     */
    Integer deleteFeeItemsMappingsById(DepartmentsHospital departmentsHospital);

    /**
     * @description 新增收费项目
     * @author bill
     * @param
     * @date 2019/4/28
     */
	Integer insertFeeItemsMappingsByParams(Map<String, Object> paramMap);

	/**
	 * @description 最小集id
	 * @author bill
	 * @param
	 * @date 2019/5/22
	 */
	List<Integer> getMinFeesIds(Set<Integer> feeIds);

	/**
	 * @description 查询收费项目
	 * @author bill
	 * @param
	 * @date 2019/5/22
	 */
	List<DepartmentFeeItems> getFeesByIds(Set<Integer> feeIds);
}
