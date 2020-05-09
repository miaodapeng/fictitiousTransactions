package com.report.service;

import java.util.List;

import com.report.model.export.BuyExportDetail;
import com.report.model.export.GoodsExport;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.goods.model.Brand;
import com.vedeng.goods.model.Category;
import com.vedeng.order.model.vo.BuyorderVo;

/**
 * <b>Description:</b><br> 产品数据导出server
 * @author duke
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.report.service
 * <br><b>ClassName:</b> GoodsReportService
 * <br><b>Date:</b> 2017年12月4日 上午9:37:38
 */
public interface SupplyReportService extends BaseService{

	/**
	 * <b>Description:</b><br> 产品分类导出
	 * @param category
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年12月4日 上午9:41:26
	 */
	List<Category> exportCategoryList(Category category);

	/**
	 * <b>Description:</b><br> 导出产品品牌列表
	 * @param brand
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年12月4日 下午4:20:16
	 */
	List<Brand> exportBrandList(Brand brand, Page page);

	/**
	 * <b>Description:</b><br> 导出产品列表
	 * @param goods
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年2月2日 下午1:43:29
	 */
	List<GoodsExport> exportGoodsList(GoodsExport goods, Page page);

	/**
	 * <b>Description:</b><br> 导出供应链-采购订单列表
	 * @param buyorderVo
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年2月4日 上午10:51:31
	 */
	List<BuyExportDetail> exportBuyOrderList(BuyorderVo buyorderVo, Page page);

	/**
	 * <b>Description:</b><br> 导出采购订单明细
	 * @param buyorderVo
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年2月5日 下午10:16:09
	 */
	List<BuyExportDetail> exportBuyOrderDetailList(BuyorderVo buyorderVo, Page page);

}
