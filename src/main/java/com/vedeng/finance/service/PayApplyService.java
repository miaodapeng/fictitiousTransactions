package com.vedeng.finance.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.finance.model.PayApply;
import com.vedeng.finance.model.PayApplyDetail;

public interface PayApplyService extends BaseService{

	/**
	 * <b>Description:</b><br> 获取付款申请列表
	 * @param payApply
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年9月7日 下午3:21:15
	 */
	List<PayApply> getPayApplyList(PayApply payApply);

	/**
	 * <b>Description:</b><br> 付款申请不通过
	 * @param payApply
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年9月8日 上午10:15:36
	 */
	ResultInfo<?> payApplyNoPass(PayApply payApply);

	/**
	 * <b>Description:</b><br> 付款申请通过
	 * @param payApply
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年9月8日 上午11:04:44
	 */
	ResultInfo<?> payApplyPass(PayApply payApply);

	/**
	 * <b>Description:</b><br> 付款申请列表（分页）
	 * @param request
	 * @param payApply
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年9月8日 下午3:50:11
	 */
	Map<String, Object> getPayApplyListPage(HttpServletRequest request, PayApply payApply, Page page);

	/**
	 * <b>Description:</b><br> 获取付款申请该产品（已申请数量、已申请总额）
	 * @param buyorderGoodsId
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年9月27日 下午2:46:18
	 */
	Map<String, BigDecimal> getPassedByBuyorderGoodsId(Integer buyorderGoodsId);

	/**
	 * <b>Description:</b><br> 获取付款申请主表信息（根据ID）
	 * @param payApplyId
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年9月30日 下午2:29:33
	 */
	PayApply getPayApplyInfo(Integer payApplyId);

	/**
	 * <b>Description:</b><br> 获取付款申请详情列表信息（根据ID）
	 * @param payApplyId
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年9月30日 下午2:29:59
	 */
	List<PayApplyDetail> getPayApplyDetailList(Integer payApplyId);
	/**
	 * <b>Description:</b><br> 付款申请列表（分页）不包含银行同步
	 * <b>Author:</b> hank.he
	 * <br><b>Date:</b> 2020-03-11
	 */
	public Map<String, Object> getPayApplyListPageNew(HttpServletRequest request, PayApply payApply, Page page);



	/**
	 * 获取当前最新的申请记录
	 * @param payApply
	 * @return
	 */
	PayApply getPayApplyMaxRecord(PayApply payApply);

	/**
	 * 根据条件动态修改申请表数据
	 * @param payApply
	 * @return
	 */
	int updatePayStutas(PayApply payApply);

	/**
	 * g根据申请id获得数据
	 * @param payApply
	 * @return
	 */
	PayApply getPayApplyRecord(PayApply payApply);


	/**
	 * @Description: 根据relatedId获取当前最新申请表得申请付款状态
	 * @Param:
	 * @return:
	 * @Author: addis
	 * @Date: 2020/2/26
	 */
	public int getPayStatusBill(Integer payType,Integer payApplyId);

	/**
	 * @Description: 修改申请付款状态
	 * @Param:
	 * @return:
	 * @Author: addis
	 * @Date: 2020/2/26
	 */
	public int updatePayStatusBill(Integer payType,Integer payStatus,Integer payApplyId);



}
