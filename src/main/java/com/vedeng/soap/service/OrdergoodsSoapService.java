package com.vedeng.soap.service;

import java.util.List;

import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.service.BaseService;
import com.vedeng.ordergoods.model.OrdergoodsGoodsAccount;
import com.vedeng.ordergoods.model.OrdergoodsStoreAccount;
import com.vedeng.ordergoods.model.vo.OrdergoodsGoodsAccountVo;
import com.vedeng.ordergoods.model.vo.OrdergoodsGoodsVo;

/**
 * <b>Description:</b><br> 订货系统同步
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.soap.service
 * <br><b>ClassName:</b> OrdergoodsSoapService
 * <br><b>Date:</b> 2018年1月5日 下午2:05:13
 */
public interface OrdergoodsSoapService extends BaseService {

	/**
	 * <b>Description:</b><br> 订货产品分类同步
	 * @param ordergoodsCategoryId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月5日 下午1:56:06
	 */
	public ResultInfo ordergoodsCategorySync(Integer ordergoodsCategoryId);
	
	/**
	 * <b>Description:</b><br> 订货分类产品同步
	 * @param ordergoodsCategoryId
	 * @param orderGoodsIds
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月5日 下午1:57:08
	 */
	public ResultInfo ordergoodsCategoryGoodsSync(Integer ordergoodsCategoryId, String orderGoodsIds);
	
	/**
	 * <b>Description:</b><br> 订货分类产品删除
	 * @param goodsId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月5日 下午4:43:44
	 */
	public ResultInfo delOrdergoodsCategoryGoodsSync(Integer goodsId,Integer ordergoodsGoodsId,Integer ordergoodsStoreId);
	
	/**
	 * <b>Description:</b><br> 订货产品批量同步
	 * @param goodsList
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月5日 下午1:58:13
	 */
	public ResultInfo ordergoodsGoodsBatchSync(List<OrdergoodsGoodsVo> goodsList);
	
	/**
	 * <b>Description:</b><br> 订货经销商账号同步
	 * @param ordergoodsStoreAccount
	 * @param type 1新增 2编辑
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月5日 下午2:00:21
	 */
	public ResultInfo ordergoodsStoreAccountSync(OrdergoodsStoreAccount ordergoodsStoreAccount,Integer type);
	
	/**
	 * <b>Description:</b><br> 经销商账号重置密码
	 * @param ordergoodsStoreAccount
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月8日 下午8:24:43
	 */
	public ResultInfo ordergoodsStoreAccountRestPasswordSync(OrdergoodsStoreAccount ordergoodsStoreAccount);
	
	/**
	 * <b>Description:</b><br> 批量同步经销商产品价格
	 * @param goodsList
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年5月18日 下午1:33:34
	 */
	public ResultInfo ordergoodsGoodsAccountBatchSync(List<OrdergoodsGoodsAccountVo> goodsList);

	/**
	 * <b>Description:</b><br> 删除经销商产品价格
	 * @param goodsId
	 * @param ordergoodsStoreId
	 * @param webAccountId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年5月18日 下午1:33:45
	 */
	public ResultInfo delOrdergoodsAccountGoodsSync(Integer goodsId, Integer ordergoodsStoreId, Integer webAccountId,Integer ordergoodsGoodsAccountId);
}
