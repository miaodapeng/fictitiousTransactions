package com.vedeng.department.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.CommonConstants;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.department.dao.DepartmentsHospitalMapper;
import com.vedeng.department.model.DepartmentFeeItems;
import com.vedeng.department.model.DepartmentFeeItemsMapping;
import com.vedeng.department.model.DepartmentsHospital;
import com.vedeng.department.service.DepartmentFeeItemsMappingService;
import com.vedeng.department.service.DepartmentsHospitalService;
import com.vedeng.department.service.HostipalDepartmentService;

/**
 * 科室
 * <p>
 * Title: HostipalDepartmentServiceImpl
 * </p>
 * <p>
 * Description:
 * </p>
 *
 * @author Bill
 * @date 2019年4月10日
 */
@Service("hostipalDepartmentService")
public class HostipalDepartmentServiceImpl implements HostipalDepartmentService {

	@Autowired
	@Qualifier("departmentsHospitalMapper")
	private DepartmentsHospitalMapper departmentsHospitalMapper;

	@Autowired
	private DepartmentsHospitalService departmentsHospitalService;

	@Autowired
	@Qualifier("departmentFeeItemsMappingService")
	private DepartmentFeeItemsMappingService departmentFeeItemsMappingService;

	// 重复错误
	private final static Integer ERROR_REPEAT_NUM_1 = -1;

	/**
	 * 进入详情页的标识
	 */
	private final static Integer VIEW_1 = 1;

	@Override
	public Map<String, Object> getHostipalDepartmentInfo(Map<String, Object> paramMap, Page page) {
		paramMap.put("page", page);
		// 结果集
		Map<String, Object> resMap = new HashMap<>();
		// 列表页
		List<DepartmentsHospital> hospitals = departmentsHospitalMapper.getHostipalDepartmentInfoListPage(paramMap);
		// 如果为空，直接返回
		if (CollectionUtils.isEmpty(hospitals)) {
			return null;
		}
		paramMap.put("hospitalList", hospitals);
		List<DepartmentsHospital> hospitalsFeeList = departmentsHospitalService.getDepartmentsHospitalByParam(paramMap);
		// 判断费用项目是否为空
		if (CollectionUtils.isNotEmpty(hospitalsFeeList)) {
			// 科室集合
			int deptSize = hospitals.size();
			// 费用集合
			int feeSize = hospitalsFeeList.size();
			for (int i = 0; i < deptSize; i++) {
				List<DepartmentFeeItemsMapping> departmentFeeItemsMappings = new ArrayList<>();
				DepartmentsHospital d = hospitals.get(i);
				// 如果日期大于0
				if (null != d.getModTime() && d.getModTime() > 0) {

					/* 判断是否是今天 */
					if(DateUtil.isToday(DateUtil.convertString(d.getModTime(), DateUtil.DATE_FORMAT))) {
						// 今天返回时间
						d.setModTimeStr(DateUtil.convertString(d.getModTime(), DateUtil.TIME_FORMAT_T));
					}
					// 不是返回日期
					else{
						d.setModTimeStr(DateUtil.convertString(d.getModTime(), DateUtil.DATE_FORMAT));
					}
				}

				for (int j = 0; j < feeSize; j++) {
					DepartmentsHospital departmentsHospital2 = hospitalsFeeList.get(j);
					// 如果科室id对应，将收费项目更新到科室信息中
					if (d.getDepartmentId().equals(departmentsHospital2.getDepartmentId())) {
						DepartmentFeeItemsMapping departmentFeeItemsMapping = new DepartmentFeeItemsMapping();
						// 收费项目
						departmentFeeItemsMapping.setFeePro(departmentsHospital2.getFeePro());
						// 科室id
						departmentFeeItemsMapping.setDepartmentId(d.getDepartmentId());
						departmentFeeItemsMappings.add(departmentFeeItemsMapping);
					}
				}
				// 如果收费项目不为空
				if (CollectionUtils.isNotEmpty(departmentFeeItemsMappings)) {
					d.setDepartmentFeeItemsMappings(departmentFeeItemsMappings);
				}
			}
		}
		resMap.put("hospitals", hospitals);
		resMap.put("page", page);

		return resMap;
	}

	/**
	 * @param paramMap
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getDepartmentFeeItemsInfo(Map<String, Object> paramMap) {
		// 所有收费项目 （一级二级三级）
		List<DepartmentFeeItems> departmentFeeItems = departmentsHospitalMapper.getDepartmentFeeItemsInfo(paramMap);
		// 非空收费项目(一级)
		if (CollectionUtils.isNotEmpty(departmentFeeItems)) {
			List<Map<String, Object>> mapList = new ArrayList<>();
			int size = departmentFeeItems.size();
			for (int i = 0; i < size; i++) {
				DepartmentFeeItems departmentFeeItem = departmentFeeItems.get(i);
				Map<String, Object> dataMap = new HashMap<>();
				// 收费项目
				dataMap.put("label", departmentFeeItem.getFeeItemsName());
				// id
				dataMap.put("value", departmentFeeItem.getDepartmentFeeItemsId());

				// 二级
				if (CollectionUtils.isNotEmpty(departmentFeeItem.getDepartmentFeeItemsList())) {
					List<Map<String, Object>> mapList2 = new ArrayList<>();
					int size2 = departmentFeeItem.getDepartmentFeeItemsList().size();
					for (int j = 0; j < size2; j++) {
						DepartmentFeeItems departmentFeeItem2 = departmentFeeItem.getDepartmentFeeItemsList().get(j);
						Map<String, Object> dataMap2 = new HashMap<>();
						// 收费项目
						dataMap2.put("label", departmentFeeItem2.getFeeItemsName());
						// id
						dataMap2.put("value", departmentFeeItem2.getDepartmentFeeItemsId());

						// 三级
						if (CollectionUtils.isNotEmpty(departmentFeeItem2.getDepartmentFeeItemsList())) {
							List<Map<String, Object>> mapList3 = new ArrayList<>();
							int size3 = departmentFeeItem2.getDepartmentFeeItemsList().size();
							for (int k = 0; k < size3; k++) {
								// 三级收费项目
								DepartmentFeeItems departmentFeeItem3 = departmentFeeItem2.getDepartmentFeeItemsList()
										.get(k);
								Map<String, Object> dataMap3 = new HashMap<>();
								// 收费项目
								dataMap3.put("label", departmentFeeItem3.getFeeItemsName());
								// id
								dataMap3.put("value", departmentFeeItem3.getDepartmentFeeItemsId());
								mapList3.add(dataMap3);
							}
							dataMap2.put("list", mapList3);
						}

						mapList2.add(dataMap2);
					}
					dataMap.put("list", mapList2);
				}
				mapList.add(dataMap);
			}
			return mapList;
		}
		return null;
	}

	/**
	 * @description 添加科室信息
	 * @author bill
	 * @param
	 * @param departmentsHospital
	 * @param sessUser
	 * @date 2019/4/16
	 */
	@Override
	public Integer addDepartmentServiceInfo(DepartmentsHospital departmentsHospital, User sessUser) {

		// 查重
		Map<String, Object> paraMap = new HashedMap();
		paraMap.put("isDelete", CommonConstants.IS_DELETE_0);
		paraMap.put("deptName", departmentsHospital.getDepartmentName());
		// 如果id不为空
		if(null != departmentsHospital.getDepartmentId()){
			paraMap.put("notEqId", departmentsHospital.getDepartmentId());
		}
		paraMap.put("deptName1", departmentsHospital.getDepartmentName());
		// 根据科室名称查重
		List<DepartmentsHospital> deptRes = departmentsHospitalMapper.getHostipalDepartmentInfoListPage(paraMap);
		if(CollectionUtils.isNotEmpty(deptRes)){
			return ERROR_REPEAT_NUM_1;
		}

		// 如果科室主键id存在，是修改操作
		if (null != departmentsHospital.getDepartmentId()) {
			// 1.修改主表
			Integer deptInfoId = departmentsHospitalMapper.updateByPrimaryKeySelective(departmentsHospital);
			if (null == deptInfoId || deptInfoId <= 0) {
				return 0;
			}
		}
		// 新增科室信息操作并且返回主键
		else {
			// 1.添加科室信息，返回主键
			Integer deptInfoId = departmentsHospitalMapper.insertSelective(departmentsHospital);
			// 添加失败，返回0
			if (null == deptInfoId || deptInfoId <= 0) {
				return 0;
			}
		}
		departmentsHospital.setUpdater(sessUser.getUserId());
		// 2.删除中间表数据
		departmentFeeItemsMappingService.deleteFeeItemsMappingsById(departmentsHospital);
		// 3.添加中间表数据
		Map<String, Object> paramMap = new HashMap<>();
		// 收费项目id
		paramMap.put("feePros", departmentsHospital.getFeePros());
		// 科室id
		paramMap.put("departmentId", departmentsHospital.getDepartmentId());
		// 操作人
		paramMap.put("userId", sessUser.getUserId());
		departmentFeeItemsMappingService.insertFeeItemsMappingsByParams(paramMap);
		return departmentsHospital.getDepartmentId();
	}

	/**
	 * @description 科室详情
	 * @author bill
	 * @param
	 * @date 2019/4/16
	 */
	@Override
	public DepartmentsHospital getDepartmentsHospitalInfo(Map<String, Object> paramMap, Integer departmentsHospitalId, Integer pageMark) {
		// 科室id
		paramMap.put("departmentsHospitalId", departmentsHospitalId);
		// 未删除状态
		paramMap.put("isDelete", CommonConstants.IS_DELETE_0);
		// 走详情页查询
		if(VIEW_1.equals(pageMark)){
			return departmentsHospitalMapper.getDepartmentsHospitalInfoById(paramMap);
		}

		// 走编辑页查询
		else{
			return departmentsHospitalMapper.getDepartmentsHospitalInfoByIdEdit(paramMap);
		}

	}

	/**
	 * @description 删除科室
	 * @author bill
	 * @param
	 * @param departmentsHospital
	 * @date 2019/5/5
	 */
	@Override
	public Integer deleteDeptHospital(Map<String, Object> paramMap, DepartmentsHospital departmentsHospital) {

		// 先判断删除的是否存在
		DepartmentsHospital deptHos = departmentsHospitalMapper.selectByPrimaryKey(departmentsHospital.getDepartmentId());
		if(null == deptHos){
			return ERROR_REPEAT_NUM_1;
		}

		// 1.先删除科室信息
		Integer deleteHospitalRes = departmentsHospitalMapper.deleteByParam(paramMap);
		if (null != deleteHospitalRes) {
			// 2.删除科室关联信息
			return departmentFeeItemsMappingService.deleteFeeItemsMappingsById(departmentsHospital);
		}
		return 0;
	}

	/**
	 * @description 查询所有科室
	 * @author bill
	 * @param
	 * @date 2019/5/14
	 */
	@Override
	public List<DepartmentsHospital> getAllDepartmentsHospital(Map<String, Object> paramMap) {

		return null;
	}

}
