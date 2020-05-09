package com.vedeng.logistics.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.vedeng.authorization.model.Company;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.goods.model.Goods;
import com.vedeng.goods.model.GoodsAttachment;
import com.vedeng.goods.model.GoodsAttribute;
import com.vedeng.goods.model.GoodsOpt;
import com.vedeng.goods.model.GoodsPackage;
import com.vedeng.goods.model.GoodsRecommend;
import com.vedeng.goods.model.GoodsSysOptionAttribute;
import com.vedeng.logistics.model.*;
import com.vedeng.order.model.Quoteorder;
/**
 * 
 * <b>Description:</b><br> 仓库管理
 * @author scott
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.warehouse.service
 * <br><b>ClassName:</b> WarehouseService
 * <br><b>Date:</b> 2017年7月13日 上午11:33:41
 */
public interface WarehousesService extends BaseService {

	/**
	 * 
	 * <b>Description:</b><br> 仓库列表
	 * @param page 
	 * @param warehouses 
	 * @param goods
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月13日 上午11:35:03
	 */
	Map<String, Object> getWarehouseList(Warehouse warehouses, Page page);
	/**
	 * 
	 * <b>Description:</b><br> 根据公司id获取公司名称
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月13日 下午4:46:29
	 */
	Company getCompanyName(int companyId);
	
    /**
     * 
     * <b>Description:</b><br> 根据仓库名称获取仓库
     * @param warehouses
     * @param session
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年7月17日 上午10:35:35
     */
	Warehouse getWarehouseByName(Warehouse warehouses, HttpSession session);
    /**
     * 
     * <b>Description:</b><br> 保存仓库信息
     * @param warehouses
     * @param request
     * @param session
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年7月17日 下午2:19:13
     */
	ResultInfo saveWarehouse(Warehouse warehouses, HttpServletRequest request, HttpSession session);
    /**
     * 
     * <b>Description:</b><br> 仓库禁用
     * @param warehouses
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年7月18日 下午6:24:26
     */
	ResultInfo<?> disableWarehouse(Warehouse warehouses);
	/**
     * 
     * <b>Description:</b><br> 根据仓库id查询仓库信息
     * @param warehouses
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年7月18日 下午6:24:26
     */
	Warehouse getWarehouseById(Warehouse warehouses);
	/**
     * 
     * <b>Description:</b><br> 编辑仓库信息
     * @param warehouses
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年7月18日 下午6:24:26
     */
	ResultInfo<?> editWarehouse(Warehouse warehouses, HttpServletRequest request, HttpSession session);
    /**
     * 
     * <b>Description:</b><br> 得到所有的仓库
     * @param warehouses
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年8月4日 上午11:28:10
     */
	List<Warehouse> getAllWarehouse(Warehouse warehouses);
	/**
	 * 
	 * <b>Description:</b><br> 查询仓库下的商品数量
	 * @param warehouses
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年8月18日 下午2:47:11
	 */
	Warehouse getGoodsList(Warehouse warehouses);
	/**
	 * 
	 * <b>Description:</b><br> 查询临时库是否存在
	 * @param warehouses
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年9月5日 上午10:06:54
	 */
	List<Warehouse> getLsWarehouse(Warehouse warehouses);
	/**
	*获取条码信息
	* @Author:strange
	* @Date:13:29 2019-11-25
	*/
    Barcode getBarcodeInfoById(Integer barcodeId);
	/**
	*获取原错误,修复后库存数据
	* @Author:strange
	* @Date:11:38 2020-03-10
	*/
    List<WarehouseGoodsStatus> getErrorStockGoodsList();
	/**
	*更新库存状态表库存数据
	* @Author:strange
	* @Date:21:58 2020-03-10
	*/
	Integer updateStockNumById(List<WarehouseGoodsStatus> StockGoodsList);
}
