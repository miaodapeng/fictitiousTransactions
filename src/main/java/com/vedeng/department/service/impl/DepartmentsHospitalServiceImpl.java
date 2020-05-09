package com.vedeng.department.service.impl;

import com.github.pagehelper.PageHelper;
import com.vedeng.common.constant.CommonConstants;
import com.vedeng.department.dao.DepartmentFeeItemsMappingMapper;
import com.vedeng.department.dao.DepartmentsHospitalGenerateMapper;
import com.vedeng.department.model.DepartmentsHospital;
import com.vedeng.department.model.DepartmentsHospitalGenerate;
import com.vedeng.department.model.DepartmentsHospitalGenerateExample;
import com.vedeng.department.service.DepartmentsHospitalService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 收费项目
 * <p>
 * Title: DepartmentsHospitalServiceImpl
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author Bill
 * @date 2019年4月12日
 */
@Service
public class DepartmentsHospitalServiceImpl implements DepartmentsHospitalService {

	@Autowired
	private DepartmentFeeItemsMappingMapper departmentFeeItemsMappingMapper;
	@Autowired
	DepartmentsHospitalGenerateMapper departmentsHospitalGenerateMapper;

	/**
	 * 查询收费项目
	 * 
	 */
	@Override
	public List<DepartmentsHospital> getDepartmentsHospitalByParam(Map<String, Object> paramMap) {

		return departmentFeeItemsMappingMapper.getDepartmentsHospitalByParam(paramMap);
	}

	@Override
	public List<DepartmentsHospitalGenerate> getAllDepartmentsHospital(String name,int size) {
		PageHelper.startPage(0,size);
		DepartmentsHospitalGenerateExample example = new DepartmentsHospitalGenerateExample();
		DepartmentsHospitalGenerateExample.Criteria c=example.createCriteria();
		c.andIsDeleteEqualTo(CommonConstants.IS_DELETE_0);
		if(StringUtils.isNotBlank(name)){
			c.andDepartmentNameLike("%"+name+"%");
		}
		return departmentsHospitalGenerateMapper.selectByExample(example);
	}

}
