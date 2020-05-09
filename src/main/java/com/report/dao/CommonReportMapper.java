package com.report.dao;

import java.util.List;

import javax.inject.Named;

import org.apache.ibatis.annotations.Param;

import com.report.model.export.ExportAssist;
import com.vedeng.authorization.model.Organization;
import com.vedeng.authorization.model.Region;
import com.vedeng.authorization.model.User;
import com.vedeng.homepage.model.vo.SaleEngineerDataVo;
import com.vedeng.order.model.vo.QuoteorderVo;
import com.vedeng.system.model.ActProcinst;
import com.vedeng.trader.model.CommunicateRecord;
import com.vedeng.trader.model.vo.TraderCustomerVo;

@Named("commonReportMapper")
public interface CommonReportMapper {
	/**
	 * <b>Description:</b><br> 查询职位类型集合下所有的员工
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年8月2日 下午1:42:07
	 */
	List<User> getUserByPositTypes(User user);
	
	/**
	 * <b>Description:</b><br> 批量查询用户
	 * @param userId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年5月27日 上午10:33:21
	 */
	List<User> getUserByUserIds(@Param("userIdList")List<Integer> userIdList);

	/**
	 * <b>Description:</b><br> 根据日期范围查询沟通记录
	 * @param beginDate
	 * @param endDate
	 * @param communicateType 沟通记录
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年12月5日 下午5:14:37
	 */
	List<Integer> getCommunicateRecordByDate(@Param("beginDate")Long beginDate, @Param("endDate")Long endDate, @Param("communicateType")String communicateType);

	/**
	 * <b>Description:</b><br> 根据职位类型获取销售部门
	 * @param orgType
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年12月5日 下午5:21:38
	 */
	List<Organization> getSalesOrgList(@Param("orgType")Integer orgType);

	/**
	 * <b>Description:</b><br> 销售人员所属(客户或供应商)Id
	 * @param userList
	 * @param traderType
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年12月5日 下午5:32:20
	 */
	List<Integer> getTraderIdListByUserList(@Param("userIdList")List<Integer> userIdList, @Param("traderType")Integer traderType);

	/**
	 * <b>Description:</b><br> 根据客户ID查询客户负责人信息
	 * @param traderIdList
	 * @param one
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年12月5日 下午6:39:07
	 */
	List<User> getUserByTraderIdList(@Param("traderIdList")List<Integer> traderIdList, @Param("traderType")Integer traderType);

	/**
	 * <b>Description:</b><br> 根据部门ID_list查询部门信息
	 * @param orgIdList
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年12月5日 下午6:46:42
	 */
	List<Organization> getOrgNameByOrgIdList(@Param("orgIdList")List<Integer> orgIdList);

	/**
	 * <b>Description:</b><br> 根据订单ID、商机ID等查询沟通记录数
	 * @param communicateRecordList
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年12月5日 下午6:54:14
	 */
	List<ExportAssist> getCommunicateRecordCountList(@Param("communicateRecordList")List<CommunicateRecord> communicateRecordList,@Param("bussinessType")Integer bussinessType,@Param("quoteType")Integer quoteType);

	/**
	 * <b>Description:</b><br> 获取订单沟通次数（包含商机，报价，销售订单）
	 * @param saleIdList
	 * @param quoteIdList
	 * @param businessIdList
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年1月15日 下午1:28:10
	 */
	List<CommunicateRecord> getCommunicateNumList(@Param("saleIdList")List<Integer> saleIdList, @Param("quoteIdList")List<Integer> quoteIdList, @Param("businessIdList")List<Integer> businessIdList);

	/**
	 * <b>Description:</b><br> 查询地区 
	 * @param regionId 地区ID
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年1月17日 上午10:53:57
	 */
	Region getRegionById(Integer regionId);

	/**
	 * <b>Description:</b><br> 根据部门id集合查询所有员工
	 * @param orgIds
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年1月19日 下午5:00:41
	 */
	List<User> getUserListByOrgIdList(@Param("orgIdList")List<Integer> orgIdList, @Param("companyId")Integer companyId);

	/**
	 * <b>Description:</b><br> 根据产品分类查询产品负责人、及负责人所属部门
	 * @param userIdList
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年2月1日 下午1:13:02
	 */
	List<User> getCategoryUserAndOrgList(@Param("categoryIdList")List<Integer> categoryIdList);

	/**
	 * <b>Description:</b><br> 根据客户ID查询客户负责人、及负责人所属部门
	 * @param traderIdList
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年2月1日 下午1:53:24
	 */
	List<User> getTraderUserAndOrgList(@Param(value="traderIdList")List<Integer> traderIdList);

	/**
	 * <b>Description:</b><br> 根据产品归属查找对应的分类
	 * @param goodsUserId
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年2月2日 下午1:35:56
	 */
	List<Integer> getCategoryIdListByUserId(@Param(value="goodsUserId")Integer goodsUserId);

	/**
	 * <b>Description:</b><br> 查询分类对应人员（产品归属人员）
	 * @param categoryIdList
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年2月2日 下午4:43:22
	 */
	List<User> getCategoryUserList(@Param(value="categoryIdList")List<Integer> categoryIdList);

	/**
	 * <b>Description:</b><br> 查询审核流程记录
	 * @param buyOrderKeyList
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年2月6日 下午1:21:52
	 */
	List<ActProcinst> getActTaskList(@Param(value="businessKeyList")List<String> businessKeyList);

	/**
	 * <b>Description:</b><br> 根据用户ID获取用户部门信息
	 * @param businessUserIdList
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年3月15日 下午2:25:33
	 */
	List<User> getUserOrgByUserIdList(@Param(value="userIdList")List<Integer> userIdList);
	
	/**
	 * <b>Description:</b><br> 查询当前销售的待沟通信息
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年5月21日 上午9:41:57
	 */
	List<TraderCustomerVo> getTraderCustomerVoFromCommunicate(SaleEngineerDataVo saleEngineerDataVo);
	
	/**
	 * <b>Description:</b><br> 查询当前销售的待沟通报价信息
	 * @param saleEngineerDataVo
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年5月21日 下午1:04:28
	 */
	List<QuoteorderVo> getQuoteorderVoFromCommunicate(SaleEngineerDataVo saleEngineerDataVo);
}
