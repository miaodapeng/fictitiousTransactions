
package com.vedeng.order.service.impl;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import com.vedeng.common.constant.OrderDataUpdateConstant;
import com.vedeng.common.constant.stock.StockOperateTypeConst;
import com.vedeng.common.util.StringUtil;
import com.vedeng.goods.dao.GoodsMapper;
import com.vedeng.goods.model.Goods;
import com.vedeng.logistics.service.WarehouseStockService;
import com.vedeng.order.dao.SaleorderGoodsMapper;
import com.vedeng.order.dao.SaleorderMapper;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.SaleorderCoupon;
import com.vedeng.trader.dao.TraderCustomerMapper;
import com.vedeng.trader.model.vo.TraderCustomerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.common.constants.Contant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.authorization.dao.UserMapper;
import com.vedeng.authorization.model.User;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.order.model.vo.OrderConnectVo;
import com.vedeng.order.service.OrderAndProductConnectService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <b>Description:</b><br> 
 * <b>Author: Franlin</b> 
 * @fileName OrderAndProductConnectServiceImpl.java
 * <br><b>Date: 2018年8月23日 下午3:31:38 </b> 
 *
 */
@Service("orderAndProductConnectService")
public class OrderAndProductConnectServiceImpl extends BaseServiceimpl implements OrderAndProductConnectService
{
	public static Logger logger = LoggerFactory.getLogger(OrderAndProductConnectServiceImpl.class);

	@Resource
	private UserMapper userMapper;

	@Autowired
	@Qualifier("saleorderMapper")
	private SaleorderMapper saleorderMapper;

	@Autowired
	@Qualifier("goodsMapper")
	private GoodsMapper goodsMapper;

	@Autowired
	@Qualifier("saleorderGoodsMapper")
	private SaleorderGoodsMapper saleorderGoodsMapper;

	@Autowired
	@Qualifier("traderCustomerMapper")
	private TraderCustomerMapper traderCustomerMapper;

	@Autowired
	private WarehouseStockService warehouseStockService;

	@SuppressWarnings("unchecked")
	@Override
	public ResultInfo<List<OrderConnectVo>> queryOrderAndProductConnectOrderNo(OrderConnectVo reqVo)
	{
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<OrderConnectVo>>> TypeRef = new TypeReference<ResultInfo<List<OrderConnectVo>>>(){};
		String url = httpUrl + "order/productConnect/queryOrderAndProductConnectOrderNo.htm";
		ResultInfo<List<OrderConnectVo>> result = null;
		
		try
		{
			result = (ResultInfo<List<OrderConnectVo>>) HttpClientUtils.post(url, reqVo, clientId, clientKey, TypeRef);
		}
		catch (IOException e)
		{
			logger.error(Contant.ERROR_MSG, e);
		}
		if(null == result)
			return new ResultInfo<List<OrderConnectVo>>();
		
		List<OrderConnectVo> resultList = new LinkedList<OrderConnectVo>();
		List<OrderConnectVo> orderList = (List<OrderConnectVo>) result.getData();
		if(null != orderList)
		{
			User user = new User();
			for(OrderConnectVo vo : orderList)
			{
				if(null == vo || null == vo.getOrderConnectNo())
					continue;
				if(null != vo.getCreatorId())
				{
					user.setUserId(vo.getCreatorId());
					User userInfo = userMapper.getUser(user);
					if(null != userInfo)
					{
						vo.setCreatorName(userInfo.getUsername());
					}
				}
				resultList.add(vo);
			}
		}
		result.setData(resultList);
		// 设置数据
	
		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	public int saveHCOrder(Saleorder order, List<SaleorderCoupon> couponList) throws Exception {
		// 根据traderId查询所属客户
		TraderCustomerVo traderCustomer = traderCustomerMapper.getCustomerInfo(order.getTraderId());
		if (traderCustomer == null) {
			logger.info("保存hc订单失败,ERP中无订单所属客户,orderNo:{},traderId:{}",order.getSaleorderNo(),order.getTraderId());
			return 0;
		}
		order.setCustomerType(traderCustomer.getCustomerType());// 客户类型
		order.setCustomerNature(traderCustomer.getCustomerNature());// 客户性质
		// order.setTraderName(traderCustomer.getTraderName());

        // 设置订单归属人默认为客户对应的归属销售
        User user = saleorderMapper.getUserInfoByTraderId(order.getTraderId());// 1客户，2供应商

        if(user == null){
            user = userMapper.getUserByName("haocai.vedeng");
        }

        if (user != null) {
            order.setUserId(user.getUserId());// 订单归属人
            order.setOwnerUserId(user.getUserId());// 订单归属人
            order.setOrgName(user.getOrgName());// 订单归属人部门
            order.setOrgId(114);//医械购诊所业务部线上开发组 TODO 根据人来修改
        }

		//如果发票邮箱为空，默认获取归属销售的邮箱
		if(StringUtil.isEmpty(order.getInvoiceEmail()) && order.getOwnerUserId() != null){
			User salesman = saleorderMapper.getUserDetailInfoByUserId(order.getOwnerUserId());
			if(salesman != null){
				order.setInvoiceEmail(salesman.getEmail());
			}
		}

		// 插入订单
		int i = saleorderMapper.insertSelective(order);
		if (i == 1) {
			for (int x = 0; x < order.getGoodsList().size(); x++) {
				order.getGoodsList().get(x).setSaleorderId(order.getSaleorderId());
				Goods goods = goodsMapper.selectGoodsExtendInfo(order.getGoodsList().get(x).getSku());
				if (null != goods) {
					// 单位名称
					order.getGoodsList().get(x).setUnitName(goods.getUnitName());
					order.getGoodsList().get(x).setGoodsId(goods.getGoodsId());
				}
				order.getGoodsList().get(x).setDeliveryCycle("14天");
				// 插入商品
				int j = saleorderGoodsMapper.insertSelective(order.getGoodsList().get(x));
				if (j <= 0) {
				    logger.info("保存订单商品信息发生错误,订单号:{}",order.getSaleorderNo());
					throw new Exception("保存订单商品信息发生错误");
				}
			}
			if (couponList != null && couponList.size() > 0) {
				// 优惠券
				for (int x = 0; x < couponList.size(); x++) {
					couponList.get(x).setSaleorderId(order.getSaleorderId());
				}
				int j = saleorderGoodsMapper.insertOrderCouponBatch(couponList);
				if (j <= 0) {
                    logger.info("保存订单优惠券信息发生错误,订单号:{}",order.getSaleorderNo());
					throw new Exception("保存订单优惠券信息发生错误");
				}
			}
			order.setOperateType(StockOperateTypeConst.START_ORDER);
			int i1 = warehouseStockService.updateOccupyStockService(order,0);
			//VDERP-2263   订单售后采购改动通知
			updateSaleOrderDataUpdateTime(order.getSaleorderId(),null, OrderDataUpdateConstant.SALE_ORDER_GENERATE);
			return i1;
		}
		logger.info("保存hc订单失败,插入数据库失败,订单号:{}",order.getSaleorderNo());
		return 0;
	}
}

