package com.vedeng.department.service.impl;

import com.vedeng.department.dao.DepartmentFeeItemsMapper;
import com.vedeng.department.dao.DepartmentFeeItemsMappingMapper;
import com.vedeng.department.model.DepartmentFeeItems;
import com.vedeng.department.model.DepartmentsHospital;
import com.vedeng.department.service.DepartmentFeeItemsMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service("departmentFeeItemsMappingService")
public class DepartmentFeeItemsMappingServiceImpl implements DepartmentFeeItemsMappingService {

	@Autowired
	private DepartmentFeeItemsMappingMapper departmentFeeItemsMappingMapper;

	@Autowired
	private DepartmentFeeItemsMapper departmentFeeItemsMapper;

	/**
	 * @description 收费信息
	 * @author bill
	 * @param
	 * @date 2019/4/16
	 */
	@Override
	public Integer addDepartmentFeeItemsMappingInfos(Map<String, Object> paramMap) {

		return departmentFeeItemsMappingMapper.insertDepartmentFeeItemsInfos(paramMap);
	}

	@Override
	public Integer deleteFeeItemsMappingsById(DepartmentsHospital departmentsHospital) {

		return departmentFeeItemsMappingMapper.deleteFeeItemsMappingsById(departmentsHospital);
	}

	@Override
	public Integer insertFeeItemsMappingsByParams(Map<String, Object> paramMap) {

		return departmentFeeItemsMappingMapper.insertDepartmentFeeItemsInfos(paramMap);
	}

	/**
	 * @description 获取所有最小集id
	 * @author bill
	 * @param
	 * @date 2019/5/22
	 */
	@Override
	public List<Integer> getMinFeesIds(Set<Integer> feeIds) {

		return departmentFeeItemsMapper.getMinFeesIds(feeIds);
	}

	@Override
	public List<DepartmentFeeItems> getFeesByIds(Set<Integer> feeIds) {

		return departmentFeeItemsMapper.getFeesByIds(feeIds);
	}

}
