package com.report.service;

import java.util.List;
import java.util.Map;

import com.report.model.export.AfterExportDetail;
import com.report.model.export.BussinessChanceExport;
import com.report.model.export.BussinessExportDetail;
import com.report.model.export.GoodsCodeExportDetail;
import com.vedeng.aftersales.model.vo.AfterSalesVo;
import com.vedeng.aftersales.model.vo.EngineerVo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.order.model.vo.BussinessChanceVo;
import com.vedeng.order.model.vo.SaleorderGoodsWarrantyVo;

/**
 * <b>Description:</b><br> 客服数据Service
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.report.service.impl
 * <br><b>ClassName:</b> ServiceReprotService
 * <br><b>Date:</b> 2017年11月30日 上午10:44:00
 */
public interface ServiceReprotService extends BaseService {

	/**
	 * <b>Description:</b><br> 导出工程师列表
	 * @param engineerVo
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月4日 上午9:34:01
	 */
	List<EngineerVo> exportEngineerList(EngineerVo engineerVo, Page page);

	/**
	 * <b>Description:</b><br> 获取工程师列表
	 * @param engineerVo
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月4日 上午9:36:05
	 */
	Map<String, Object> getEngineerList(EngineerVo engineerVo, Page page);

	/**
	 * <b>Description:</b><br> 导出录保卡列表
	 * @param saleorderGoodsWarrantyVo
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月4日 下午4:03:51
	 */
	List<SaleorderGoodsWarrantyVo> exportGoodsWarrantyList(SaleorderGoodsWarrantyVo saleorderGoodsWarrantyVo,
			Page page);

	/**
	 * <b>Description:</b><br> 获取录保卡列表 
	 * @param saleorderGoodsWarrantyVo
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月4日 下午4:04:22
	 */
	Map<String, Object> getGoodsWarrantyList(SaleorderGoodsWarrantyVo saleorderGoodsWarrantyVo, Page page);

	/**
	 * <b>Description:</b><br> 导出商机
	 * @param bussinessChanceVo
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月6日 下午3:16:56
	 */
	List<BussinessChanceExport> exportBussinessChanceList(BussinessChanceVo bussinessChanceVo, Page page);

	/**
	 * <b>Description:</b><br> 售后列表
	 * @param afterSalesVo
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月7日 下午5:39:11
	 */
	List<AfterSalesVo> exportAfterSalesList(AfterSalesVo afterSalesVo, Page page);
	
	/**
	 * <b>Description:</b><br> 获取售后列表
	 * @param afterSalesVo
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月7日 下午5:39:49
	 */
	Map<String, Object> getAfterSalesList(AfterSalesVo afterSalesVo, Page page);

	/**
	 * <b>Description:</b><br> 导出售后售后商品明细表
	 * @param afterSalesVo
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年1月22日 上午9:34:49
	 */
	List<AfterExportDetail> exportAfterGoodsDetailList(AfterExportDetail afterExportDetail, Page page);

	/**
	 * <b>Description:</b><br> 导出商机明细
	 * @param bussinessExportDetail
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年1月22日 下午1:59:28
	 */
	List<BussinessExportDetail> exportBussinessDetailList(BussinessExportDetail bussinessExportDetail, Page page);

	/**
	 * <b>Description:</b><br> 导出商品识别码维度明细表
	 * @param gced
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年2月1日 上午10:38:13
	 */
	List<GoodsCodeExportDetail> exportGoodsCodeDetailList(GoodsCodeExportDetail gced, Page page);
}
