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
import com.vedeng.logistics.model.LendOut;
import com.vedeng.logistics.model.StorageArea;
import com.vedeng.logistics.model.StorageRoom;
import com.vedeng.logistics.model.Warehouse;
import com.vedeng.logistics.model.WarehousePicking;
import com.vedeng.order.model.Quoteorder;
import com.vedeng.order.model.Saleorder;
/**
 * 
 * <b>Description:</b><br> 拣货清单
 * @author scott
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.logistics.service
 * <br><b>ClassName:</b> WarehousePickService
 * <br><b>Date:</b> 2017年8月11日 上午11:15:15
 */
public interface WarehousePickService extends BaseService {
    /**
     * 
     * <b>Description:</b><br> 拣货清单列表
     * @param saleorder
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年8月11日 上午11:18:00
     */
	List<WarehousePicking> getPickDetil(Saleorder saleorder);
    /**
     * 
     * <b>Description:</b><br> 保存拣货记录
     * @param sd
     * @param session 
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年8月23日 下午5:55:36
     */
	ResultInfo<?> savePickRecord(Saleorder sd, HttpSession session);
	/**
     * 
     * <b>Description:</b><br> 撤销拣货记录
     * @param wpList 
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年8月23日 下午5:55:36
     */
	ResultInfo<?> editIsEnablePick(List<WarehousePicking> wpList);
	/**
     * 
     * <b>Description:</b><br> 根据id查询当前拣货记录
     * @param wpList 
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年8月23日 下午5:55:36
     */
	List<WarehousePicking> printPickOrder(WarehousePicking wp);
	/**
	* @Title: getPickLendOutDetil
	* @Description: TODO(获取外借单拣货记录)
	* @param @return    参数
	* @return List<WarehousePicking>    返回类型
	* @author strange
	* @throws
	* @date 2019年8月29日
	*/
	List<WarehousePicking> getPickLendOutDetil(Saleorder saleorder);
	/**
	* @Title: getLendOutPickCnt
	* @Description: TODO  获取外接单商品拣货数
	* @param @param lendout
	* @return Integer    返回类型
	* @author strange
	* @throws
	* @date 2019年9月2日
	*/
	Integer getLendOutPickCnt(LendOut lendout);

}
