package com.report.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.vedeng.authorization.model.Organization;
import com.vedeng.authorization.model.User;
import com.vedeng.common.service.BaseService;

public interface CommonReportService extends BaseService {
	/**
	 * <b>Description:</b><br> 
	 * @param session  
	 * @param positionType  需要检索的职位类型集合 null为全部 ； 如果当前用户在职位类型集合内，则查子集
	 * 		positionType.add(SysOptionConstant.ID_310);//销售
			positionType.add(SysOptionConstant.ID_311);//采购
			positionType.add(SysOptionConstant.ID_312);//售后
			positionType.add(SysOptionConstant.ID_313);//物流
			positionType.add(SysOptionConstant.ID_314);//财务
	 * @param haveDisabeldUser 是否包含禁用用户 true包含
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年8月2日 下午1:38:07
	 */
	public List<User> getMyUserList(HttpSession session,List<Integer> positionType,boolean haveDisabeldUser);

	/**
	 * <b>Description:</b><br> 根据日期范围查询沟通记录
	 * @param beginDate
	 * @param endDate
	 * @param communicateType 沟通记录
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年12月5日 下午5:12:02
	 */
	public List<Integer> getCommunicateRecordByDate(Long beginDate, Long endDate, String communicateType);

	/**
	 * <b>Description:</b><br> 根据职位类型获取销售部门
	 * @param orgType
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年12月5日 下午5:19:45
	 */
	public List<Organization> getSalesOrgList(Integer orgType);

	/**
	 * <b>Description:</b><br> 销售人员所属(客户或供应商)ID
	 * @param userList
	 * @param traderType
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年12月5日 下午5:30:27
	 */
	public List<Integer> getTraderIdListByUserList(List<Integer> userIdList, Integer traderType);
	
	/**
	 * <b>Description:</b><br> 获取地区层级
	 * @param regionId 地区ID
	 * @param returnType 1:数组,2:字符串
	 * @return Object
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 上午11:30:30
	 */
	Object getRegion(Integer regionId,Integer returnType);

	/**
	 * <b>Description:</b><br> 根据公司的id以及部门id查询所属员工
	 * @param proOrgtId
	 * @param request
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年1月19日 下午4:51:55
	 */
	public List<Integer> getDeptUserIdList(Integer parentId, HttpServletRequest request);

	/**
	 * <b>Description:</b><br> 根据产品归属查找对应的分类
	 * @param goodsUserId
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年2月2日 下午1:24:34
	 */
	public List<Integer> getCategoryIdListByUserId(Integer goodsUserId);
}
