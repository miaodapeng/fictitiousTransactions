package com.vedeng.logistics.service.impl;

import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.common.constants.Contant;
import com.report.dao.FinanceReportMapper;
import com.vedeng.aftersales.dao.AfterSalesGoodsMapper;
import com.vedeng.aftersales.model.AfterSalesGoods;
import com.vedeng.common.constant.OrderDataUpdateConstant;
import com.vedeng.common.constant.stock.StockOperateTypeConst;
import com.vedeng.common.redis.RedisUtils;
import com.vedeng.goods.dao.GoodsMapper;
import com.vedeng.logistics.dao.BarcodeMapper;
import com.vedeng.logistics.dao.WarehouseGoodsStatusMapper;
import com.vedeng.logistics.model.*;
import com.vedeng.logistics.model.vo.WarehouseGoodsOperateLogVo;
import com.vedeng.logistics.service.WarehouseStockService;
import com.vedeng.logistics.service.WarehousesService;
import com.vedeng.order.dao.SaleorderGoodsMapper;
import com.vedeng.order.dao.SaleorderMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.aftersales.model.vo.AfterSalesGoodsVo;
import com.vedeng.authorization.dao.UserMapper;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.MessageUtil;
import com.vedeng.goods.dao.GoodsExpirationWarnMapper;
import com.vedeng.goods.dao.RCategoryJUserMapper;
import com.vedeng.goods.model.Goods;
import com.vedeng.goods.model.vo.GoodsExpirationWarnVo;
import com.vedeng.logistics.dao.LendOutMapper;
import com.vedeng.logistics.dao.WarehouseGoodsOperateLogMapper;
import com.vedeng.logistics.service.WarehouseGoodsOperateLogService;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.SaleorderGoods;

@Service("warehouseGoodsOperateLogService")
public class WarehouseGoodsOperateLogServiceImpl extends BaseServiceimpl implements WarehouseGoodsOperateLogService {
	public static Logger logger = LoggerFactory.getLogger(WarehouseGoodsOperateLogServiceImpl.class);

    @Autowired
    @Qualifier("userMapper")
    private UserMapper userMapper;
    
    @Autowired
    @Qualifier("goodsExpirationWarnMapper")
    private GoodsExpirationWarnMapper goodsExpirationWarnMapper;
    
    @Autowired
    @Qualifier("rCategoryJUserMapper")
    private RCategoryJUserMapper rCategoryJUserMapper;
    
    @Autowired
    @Qualifier("warehouseGoodsOperateLogMapper")
    private WarehouseGoodsOperateLogMapper warehouseGoodsOperateLogMapper;
    @Autowired
    private com.report.dao.FinanceReportMapper financeReportMapper;

    @Autowired
	@Qualifier("warehouseGoodsStatusMapper")
	private WarehouseGoodsStatusMapper warehouseGoodsStatusMapper;

    @Autowired
	private GoodsMapper goodsMapper;
    
    @Autowired
    @Qualifier("lendOutMapper")
    private LendOutMapper lendOutMapper;
    @Autowired
	private WarehouseStockService warehouseStockService;
    @Autowired
    private SaleorderGoodsMapper saleorderGoodsMapper;
    @Autowired
    private AfterSalesGoodsMapper afterSalesGoodsMapper;
    @Resource
	private BarcodeMapper barcodeMapper;
    @Autowired
	private RedisUtils redisUtils;
    @Autowired
	private WarehousesService warehousesService;
	@Override
	public ResultInfo<?> getCreatorId(Saleorder sd) {
		ResultInfo<WarehouseGoodsOperateLog> result = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<WarehouseGoodsOperateLog>> TypeRef = new TypeReference<ResultInfo<WarehouseGoodsOperateLog>>() {};
		String url=httpUrl + "warehousegoodsoperatelog/getwarehousegoodsoperatelog.htm";
		try {
			result = (ResultInfo<WarehouseGoodsOperateLog>) HttpClientUtils.post(url, sd,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public List<WarehouseGoodsOperateLog> getWlog(SaleorderGoods saleorderGood) {
		List<WarehouseGoodsOperateLog> list = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<WarehouseGoodsOperateLog>>> TypeRef = new TypeReference<ResultInfo<List<WarehouseGoodsOperateLog>>>() {};
		String url=httpUrl + "warehousegoodsoperatelog/getwloglist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url,saleorderGood,clientId,clientKey, TypeRef);
			list = (List<WarehouseGoodsOperateLog>) result.getData();
			//处理外借单
			 List<Integer> lendoutIdList = new ArrayList<>();
			 for (WarehouseGoodsOperateLog w : list) {
				 if(w.getYwType()==3) {
					 lendoutIdList.add(w.getBuyorderId());
				 }
			 }
			 List<LendOut> lendoutlist = new ArrayList<LendOut>();
			 if(lendoutIdList.size()>0) {
				 lendoutlist = lendOutMapper.getLendoutByLendoutIdList(lendoutIdList);
			 } 
			 for (WarehouseGoodsOperateLog w : list) {
				 for (LendOut lendOut : lendoutlist) {
					 if(w.getBuyorderId().equals(lendOut.getLendOutId())){
						 w.setBuyorderNo(lendOut.getLendOutNo());
		                 w.setOperateType(9);
					 }
				 }
			 }
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
	}

	@Override
	public ResultInfo<?> saveOutRecord(Saleorder sd, HttpSession session) {
		Long time = DateUtil.sysTimeMillis();
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		List<WarehouseGoodsOperateLog> list = new ArrayList<WarehouseGoodsOperateLog>();
		if(sd.getBussinessType()!=2){
			List<AfterSalesGoodsVo> listSv = sd.getAfterSalesGoodsList();
			for (AfterSalesGoodsVo sg : listSv) {
				List<WarehouseGoodsOperateLog> listWo = sg.getWlist();
				for (WarehouseGoodsOperateLog w : listWo) {
					WarehouseGoodsOperateLog wl = new WarehouseGoodsOperateLog();
					wl.setCompanyId(user.getCompanyId());
					wl.setOperateType(w.getOperateType());
					wl.setRelatedId(sg.getAfterSalesGoodsId());
					wl.setGoodsId(sg.getGoodsId());
					wl.setWarehouseId(w.getWarehouseId());
					wl.setStorageRoomId(w.getStorageRoomId());
					wl.setStorageAreaId(w.getStorageAreaId());
					wl.setStorageRackId(w.getStorageRackId());
					wl.setStorageLocationId(w.getStorageLocationId());
					wl.setNum(0-Integer.parseInt(w.getpCtn()));
					wl.setBatchNumber(w.getBatchNumber());
					wl.setExpirationDate(w.getExpirationDate());
					wl.setCheckStatusTime(w.getCheckStatusTime());
					wl.setCheckStatus(w.getCheckStatus());
					wl.setCheckStatusUser(w.getCheckStatusUser());
					wl.setRecheckStatus(w.getRecheckStatus());
					wl.setRecheckStatusTime(w.getRecheckStatusTime());
					wl.setRecheckStatusUser(w.getRecheckStatusUser());
					wl.setComments(w.getComments());
					wl.setAddTime(time);
					wl.setCreator(user.getUserId());
					wl.setUpdater(user.getUserId());
					wl.setModTime(time);
					wl.setIsEnable(1);
					wl.setWarehousePickingDetailId(w.getWarehousePickingDetailId());
					list.add(wl);
			}
			}
		}else if(sd.getBussinessType()==2){
			List<SaleorderGoods> listSd =  sd.getGoodsList();
			for (SaleorderGoods sg : listSd) {
				List<WarehouseGoodsOperateLog> listWo = sg.getWlist();
				for (WarehouseGoodsOperateLog w : listWo) {
					WarehouseGoodsOperateLog wl = new WarehouseGoodsOperateLog();
					wl.setCompanyId(user.getCompanyId());
					wl.setOperateType(w.getOperateType());
					wl.setRelatedId(sg.getSaleorderGoodsId());
					wl.setGoodsId(sg.getGoodsId());
					wl.setWarehouseId(w.getWarehouseId());
					wl.setStorageRoomId(w.getStorageRoomId());
					wl.setStorageAreaId(w.getStorageAreaId());
					wl.setStorageRackId(w.getStorageRackId());
					wl.setStorageLocationId(w.getStorageLocationId());
					wl.setNum(0-Integer.parseInt(w.getpCtn()));
					wl.setBatchNumber(w.getBatchNumber());
					wl.setExpirationDate(w.getExpirationDate());
					wl.setCheckStatusTime(w.getCheckStatusTime());
					wl.setCheckStatus(w.getCheckStatus());
					wl.setCheckStatusUser(w.getCheckStatusUser());
					wl.setRecheckStatus(w.getRecheckStatus());
					wl.setRecheckStatusTime(w.getRecheckStatusTime());
					wl.setRecheckStatusUser(w.getRecheckStatusUser());
					wl.setComments(w.getComments());
					wl.setAddTime(time);
					wl.setCreator(user.getUserId());
					wl.setUpdater(user.getUserId());
					wl.setModTime(time);
					wl.setIsEnable(1);
					wl.setWarehousePickingDetailId(w.getWarehousePickingDetailId());
					list.add(wl);
				}
		}
		
		}
		ResultInfo<?> result = null;
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
			String url=httpUrl + "warehousegoodsoperatelog/addwloglist.htm";
			result = (ResultInfo<?>) HttpClientUtils.post(url, list,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public List<WarehouseGoodsOperateLog> getWGOlog(WarehouseGoodsOperateLog warehouseGoodsOperateLog)throws Exception {
	    	List<WarehouseGoodsOperateLog> list = null;
	    	//操作类型必须有值
	    	if(warehouseGoodsOperateLog.getOperateType() == null || warehouseGoodsOperateLog.getOperateType() == 0){
	    	    return null;
	    	}
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<WarehouseGoodsOperateLog>>> TypeRef = new TypeReference<ResultInfo<List<WarehouseGoodsOperateLog>>>() {};
		String url=httpUrl + "warehousegoodsoperatelog/getwgolog.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url,warehouseGoodsOperateLog,clientId,clientKey, TypeRef);
			list = (List<WarehouseGoodsOperateLog>) result.getData();
			//操作人员查询
			List<Integer> userIds = new ArrayList<>();
			if (null != list) {
				for (WarehouseGoodsOperateLog wlog : list) {
					userIds.add(wlog.getCreator());
					if(wlog.getCheckStatusUser()!=null){
						userIds.add(wlog.getCheckStatusUser());
					}
					if(wlog.getRecheckStatusUser()!=null){
						userIds.add(wlog.getRecheckStatusUser());
					}
				}
			}
			if (userIds.size() > 0) {
			    List<User> userList = userMapper.getUserByUserIds(userIds);
				// 信息补充
				if (null != list) {
					for (WarehouseGoodsOperateLog wlog :list) {
						for (User u : userList) {
							if (wlog.getCreator().equals(u.getUserId())) {
							    wlog.setOperator(u.getUsername());
							}
						}
					}
				}
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
	}

	@Override
	public Map<String, Object> getWGOlogList(WarehouseGoodsOperateLog warehouseGoodsOperateLog,Page page) throws Exception{
	    	List<WarehouseGoodsOperateLog> list = null;
	    	Map<String, Object> map = new HashMap<>();
	    	//操作类型必须有值
	    	if(warehouseGoodsOperateLog.getOperateType() == null || warehouseGoodsOperateLog.getOperateType() == 0){
	    	    return null;
	    	}
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<WarehouseGoodsOperateLog>>> TypeRef = new TypeReference<ResultInfo<List<WarehouseGoodsOperateLog>>>() {};
		String url=httpUrl + "warehousegoodsoperatelog/getwgologList.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url,warehouseGoodsOperateLog,clientId,clientKey, TypeRef,page);
			list = (List<WarehouseGoodsOperateLog>) result.getData();
			map.put("list", list);
			map.put("page", result.getPage());
			//操作人员查询
			List<Integer> userIds = new ArrayList<>();
			if (null != list) {
				for (WarehouseGoodsOperateLog wlog : list) {
					userIds.add(wlog.getCreator());
					if(wlog.getCheckStatusUser()!=null){
						userIds.add(wlog.getCheckStatusUser());
					}
					if(wlog.getRecheckStatusUser()!=null){
						userIds.add(wlog.getRecheckStatusUser());
					}
				}
			}
			if (userIds.size() > 0) {
			    List<User> userList = userMapper.getUserByUserIds(userIds);
				// 信息补充
				if (null != list) {
					for (WarehouseGoodsOperateLog wlog :list) {
						for (User u : userList) {
							if (null != wlog.getCreator() && wlog.getCreator().equals(u.getUserId())) {
							    wlog.setOperator(u.getUsername());
							}
							if (null != wlog.getCheckStatusUser() && wlog.getCheckStatusUser().equals(u.getUserId())) {
							    wlog.setCheckUserName(u.getUsername());
							}
							if (null != wlog.getRecheckStatusUser() && wlog.getRecheckStatusUser().equals(u.getUserId())) {
							    wlog.setRecheckUserName(u.getUsername());
							}
						}
					}
				}
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return map;
	}

	@Override
	public ResultInfo<?> addWlogList(List<WarehouseGoodsOperateLog> warehouseGoodsOperateLogList) {
		String url = httpUrl + "warehousegoodsoperatelog/addwloglist.htm";
		final TypeReference<ResultInfo<Map<String, Object>>> TypeRef = new TypeReference<ResultInfo<Map<String, Object>>>() {};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, warehouseGoodsOperateLogList, clientId, clientKey, TypeRef);
			if(result != null){
				if(result.getCode() == 0 && result.getData() != null){
					int mes_temple = 0;
					Integer operateType = warehouseGoodsOperateLogList.get(0).getOperateType();//操作类型
					//1入库 2出库3销售换货入库4销售换货出库5销售退货入库6采购退货出库7采购换货出库8采购换货入库9外借入库 10外借出库
					if(operateType.equals(3) || operateType.equals(5)){//售后-销售换货入库;销售退货入库
						mes_temple = 38;
					}else if(operateType.equals(4)){//售后-销售换货出库
						mes_temple = 39;
					}else if(operateType.equals(6) || operateType.equals(7)){//采购退货出库;;采购换货出库
						mes_temple = 42;
					}else if(operateType.equals(8)){//采购换货入库
						mes_temple = 43;
					}
					Map<String, Object> result_map = (Map<String, Object>)result.getData();
					if(result_map.get("orderId") != null && result_map.get("orderNo") != null && result_map.get("traderId") != null && mes_temple != 0 ){
						Map<String,String> map = new HashMap<>();
						map.put("afterorderNo", result_map.get("orderNo").toString());
						//根据客户Id查询客户负责人
						List<Integer> userIdList = userMapper.getUserIdListByTraderId(Integer.valueOf(result_map.get("traderId").toString()),ErpConst.ONE);
						//售后单的归属售后人员
						List<Integer> userShIdList = new ArrayList<>();
						if(result_map.get("userId")!=null){
							userShIdList.add(Integer.parseInt(result_map.get("userId").toString()));
							MessageUtil.sendMessage(mes_temple, userShIdList, map, "./aftersales/order/viewAfterSalesDetail.do?afterSalesId="+result_map.get("orderId")+"&traderType=1");
						}
						if(userIdList != null && !userIdList.isEmpty()){
							//售后（销售-采购）退换货出入库后发送消息
							MessageUtil.sendMessage(mes_temple, userIdList, map, "./order/saleorder/viewAfterSalesDetail.do?afterSalesId="+result_map.get("orderId"));
						}
					}
				}
				//调用库存服务
				int i = warehouseStockService.updateStockNumService(warehouseGoodsOperateLogList);
				// 接口返回条码生成的记录
				//VDERP-2263   订单售后采购改动通知
				updateOutOrderDataTime(warehouseGoodsOperateLogList);
				result.setMessage("修改成功");
				return result;
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return new ResultInfo<>();
		}finally {
			releaseDistributedLock(warehouseGoodsOperateLogList);
		}
		return new ResultInfo<>();
	}

	private void updateOutOrderDataTime(List<WarehouseGoodsOperateLog> warehouseGoodsOperateLogList) {
		WarehouseGoodsOperateLog warehouseGoodsOperateLog = warehouseGoodsOperateLogList.get(0);
		Integer relatedId = warehouseGoodsOperateLog.getRelatedId();
		Integer operateType = warehouseGoodsOperateLog.getOperateType();
		//1入库 2出库3销售换货入库4销售换货出库5销售退货入库6采购退货出库7采购换货出库8采购换货入库9外借入库 10外借出库
		if(operateType.equals(2)){
			updateSaleOrderDataUpdateTime(null,relatedId, OrderDataUpdateConstant.SALE_ORDER_OUT);
		}else if(operateType.equals(1)){
			updateBuyOrderDataUpdateTime(null,relatedId,OrderDataUpdateConstant.BUY_ORDER_IN);
		}else if(operateType.equals(3)||operateType.equals(4)||operateType.equals(5)||operateType.equals(6)||operateType.equals(7)||operateType.equals(8)){
			updateAfterOrderDataUpdateTime(null,relatedId,operateType.toString());
		}
	}

	@Override
	public ResultInfo<?> editIsEnableWlog(List<WarehouseGoodsOperateLog> wlogList) {
	    String url = httpUrl + "warehousegoodsoperatelog/editisenablewlog.htm";
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		try {
		    ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, wlogList, clientId, clientKey, TypeRef);
		    // 接口返回条码生成的记录
		    if (result.getCode() == 0) {
		    	//调用库存服务
				int i = warehouseStockService.updateStockNumService(wlogList);
				return new ResultInfo(0, "修改成功");
		    }else{
			return new ResultInfo();
		    }
		} catch (IOException e) {
		    logger.error(Contant.ERROR_MSG, e);
		    return new ResultInfo();
		}
	}

	@Override
	public ResultInfo<?> editWlog(List<WarehouseGoodsOperateLog> wlogList) {
	    String url = httpUrl + "warehousegoodsoperatelog/editwlog.htm";
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		try {
		    ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, wlogList, clientId, clientKey, TypeRef);
		    // 接口返回条码生成的记录
		    if (result.getCode() == 0) {
			return new ResultInfo(0, "修改成功");
		    }else{
			return new ResultInfo();
		    }
		} catch (IOException e) {
		    logger.error(Contant.ERROR_MSG, e);
		    return new ResultInfo();
		}
	}

	@Override
	public Map<String, Object> getExpirationDateList(WarehouseGoodsOperateLog warehouseGoodsOperateLog, Page page) {
	    	List<WarehouseGoodsOperateLog> list = null;
	    	Map<String, Object> map = new HashMap<>();
	    	
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<WarehouseGoodsOperateLog>>> TypeRef = new TypeReference<ResultInfo<List<WarehouseGoodsOperateLog>>>() {};
		String url=httpUrl+ "warehousegoodsoperatelog/getexpirationdatelist.htm";
		//String url="http://192.168.2.58:6080/api/warehousegoodsoperatelog/getexpirationdatelist.htm";
		try {
			logger.info("insert into getExpirationDateList ==== urk============="+url);
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url,warehouseGoodsOperateLog,clientId,clientKey,TypeRef,page);
			list = (List<WarehouseGoodsOperateLog>) result.getData();
			logger.info("insert into getExpirationDateList ==== list=============");

//			if(null != list && list.size() > 0){
//                for(WarehouseGoodsOperateLog w:list){
//                    w.setUserList(rCategoryJUserMapper.getUserByCategory(w.getCategoryId(), warehouseGoodsOperateLog.getCompanyId()));
//                }
//            }
			map.put("list", list);
			map.put("page", result.getPage());
			
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return map;
	}

	@SuppressWarnings("unused")
	@Override
	public List<WarehouseGoodsOperateLog> getWLById(WarehouseGoodsOperateLog w) {
		// 接口调用
		String url = httpUrl + "warehousegoodsoperatelog/getwlbyid.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<WarehouseGoodsOperateLog>>> TypeRef2 = new TypeReference<ResultInfo<List<WarehouseGoodsOperateLog>>>() {};
		try {
			ResultInfo<List<WarehouseGoodsOperateLog>> result2 = (ResultInfo<List<WarehouseGoodsOperateLog>>) HttpClientUtils.post(url, w, clientId, clientKey,TypeRef2);
			if (null == result2) {
				return null;
			}
			List<WarehouseGoodsOperateLog> res = (List<WarehouseGoodsOperateLog>) result2.getData();
			return res;
		} catch (IOException e) {
			return null;
		}
	}
    /******************************注释代码为调接口循环拉取缓存中的数据start*****************************************/
	/*@Override
	public List<WarehouseGoodsOperateLog> getWglList(Goods goods) {
		List<WarehouseGoodsOperateLog> list = new ArrayList<>();
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<WarehouseGoodsOperateLog>>> TypeRef = new TypeReference<ResultInfo<List<WarehouseGoodsOperateLog>>>() {};
		String url=httpUrl + "warehousegoodsoperatelog/getwgllist.htm";
		List <WarehouseGoodsOperateLog> wlogList = new ArrayList<WarehouseGoodsOperateLog>();
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, goods, clientId, clientKey, TypeRef);
			list = (List<WarehouseGoodsOperateLog>) result.getData();
			wlogList.addAll(list);
			
			if(list != null && list.size() > 0 && result.getStatus() > 100){
				int size = (int) Math.ceil((double)result.getStatus()/(double)100);
				Integer currentCount = 2;
				for(int i = 0;i<size-1;i++){
					goods.setCurrentCount(currentCount);
					result = (ResultInfo<List<Express>>) HttpClientUtils.post(httpUrl + "warehousegoodsoperatelog/getwgllists.htm", goods, clientId, clientKey, TypeRef);
					if (result == null || result.getCode() == -1) {
						return null;
					}
					list = (List<WarehouseGoodsOperateLog>) result.getData();
					wlogList.addAll(list);
					currentCount ++;
				}
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return wlogList;
		}*/
	   /******************************注释代码为调接口循环拉取缓存中的数据end*****************************************/
		@Override
	   public List<WarehouseGoodsOperateLog> getWglList(Goods goods) {
		   if (goods.getGoodsIdsList() != null && !"".equals(goods.getGoodsIdsList())) {
	            // 要出库的产品id
	            String[] id = goods.getGoodsIdsList().split("_");
	            String ids = "";
	            for (String str : id) {
	                ids += str + ",";
	            }
	            ids += 0;
	            goods.setGoodsIdsList(ids);
	        }
		   List<WarehouseGoodsOperateLog> wList = null;
		    try {
		    	wList = financeReportMapper.getWglList(goods);
			} catch (Exception e) {
				logger.error(Contant.ERROR_MSG, e);
			}
	        if(CollectionUtils.isNotEmpty(wList)){
	        // 设置采购单和采购单价
	        for (WarehouseGoodsOperateLog w : wList) {
	            WarehouseGoodsOperateLog wg = new WarehouseGoodsOperateLog();
	            if (w.getOperateType() == 1) {
	                wg = warehouseGoodsOperateLogMapper.getWOLByC(w);
	            } else if (w.getOperateType() == 3 || w.getOperateType() == 5) {
	                wg = warehouseGoodsOperateLogMapper.getWOLByS(w);
	            } else if (w.getOperateType() == 8) {
	                wg = warehouseGoodsOperateLogMapper.getWOLByCH(w);
	            }else if(w.getOperateType() == 9) {
	            	wg = warehouseGoodsOperateLogMapper.getLendoutByL(w);
	            }
	            if (wg != null) {
	                w.setBuyorderId(wg.getBuyorderId());
	                w.setBuyorderNo(wg.getBuyorderNo());
	                w.setCgprice(wg.getPrice());
	                w.setTraderName(wg.getTraderName());
	            }
	        }}
	        return wList;
	}
	   
	@Override
	public List<WarehouseGoodsOperateLog> getKindList(Goods goods) {
		List<WarehouseGoodsOperateLog> list = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<WarehouseGoodsOperateLog>>> TypeRef = new TypeReference<ResultInfo<List<WarehouseGoodsOperateLog>>>() {};
		String url=httpUrl + "warehousegoodsoperatelog/getkindlist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url,goods,clientId,clientKey, TypeRef);
			list = (List<WarehouseGoodsOperateLog>) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
		}

	@Override
	public List<WarehouseGoodsOperateLog> getWLByBarcodeFactory(WarehouseGoodsOperateLog warehouseGoodsOperateLog) {
		List<WarehouseGoodsOperateLog> list = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<WarehouseGoodsOperateLog>>> TypeRef = new TypeReference<ResultInfo<List<WarehouseGoodsOperateLog>>>() {};
		String url=httpUrl + "warehousegoodsoperatelog/getwlbybarcodefactory.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url,warehouseGoodsOperateLog,clientId,clientKey, TypeRef);
			list = (List<WarehouseGoodsOperateLog>) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
	}

	@Override
	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	public ResultInfo<?> saveUploadExpirationDay(List<GoodsExpirationWarnVo> list) {
		ResultInfo resultInfo = new ResultInfo<>();
		if(null != list && list.size() > 0){
			for(GoodsExpirationWarnVo expirationWarnVo : list){
				try {
					Goods goods = new Goods();
					goods.setSku(expirationWarnVo.getSku());
					goods.setCompanyId(expirationWarnVo.getCompanyId());
					// 定义反序列化 数据格式
					final TypeReference<ResultInfo<Goods>> TypeRef = new TypeReference<ResultInfo<Goods>>() {};
					String url=httpUrl + "goods/getgoodsbysku.htm";
					ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, goods,clientId,clientKey, TypeRef);
					Goods goodsInfo = (Goods) result.getData();
					
					if(null == goodsInfo){
						resultInfo.setMessage("订货号："+expirationWarnVo.getSku() + "不存在！");
						throw new Exception("订货号："+expirationWarnVo.getSku() + "不存在！");
					}
					
					expirationWarnVo.setGoodsId(goodsInfo.getGoodsId());
					
					GoodsExpirationWarnVo goodsExpirationWarn = goodsExpirationWarnMapper.selectByGoodsId(goodsInfo.getGoodsId(),expirationWarnVo.getCompanyId());
					
					if(null != goodsExpirationWarn){
						goodsExpirationWarnMapper.updateByGoodsId(expirationWarnVo);
					}else{
						expirationWarnVo.setAddTime(expirationWarnVo.getModTime());
						expirationWarnVo.setCreator(expirationWarnVo.getUpdater());
						expirationWarnVo.setCompanyId(goodsInfo.getCompanyId());
						goodsExpirationWarnMapper.insert(expirationWarnVo);
					}
					
					resultInfo.setCode(0);
					resultInfo.setMessage("操作成功");
				} catch (Exception e) {
					logger.error(Contant.ERROR_MSG, e);
					return resultInfo;
				}
				
			}
		}
		return resultInfo;
	}

	@Override
	public ResultInfo<?> batchAddWarehouseinList(List<WarehouseGoodsOperateLog> warehouseGoodsOperateLogList) {
	    String url = httpUrl + "warehousegoodsoperatelog/batchaddwarehouseinlist.htm";
		final TypeReference<ResultInfo<Map<String, Object>>> TypeRef = new TypeReference<ResultInfo<Map<String, Object>>>() {};
		ResultInfo<?> result = new ResultInfo<>();
		try {
		    	result = (ResultInfo<?>) HttpClientUtils.post(url, warehouseGoodsOperateLogList, clientId, clientKey, TypeRef);
		    	if (result.getCode() == 0){
                    //VDERP-2263   订单售后采购改动通知
                    updateOutOrderDataTime(warehouseGoodsOperateLogList);
		    	    return new ResultInfo(0, "修改成功");
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return new ResultInfo<>();
		}
		return result;
	}

	@Override
	public ResultInfo<?> batchAddWarehouseOutList(List<WarehouseGoodsOperateLog> list) {
		String url = httpUrl + "warehousegoodsoperatelog/batchaddwarehouseoutlist.htm";
		final TypeReference<ResultInfo<Map<String, Object>>> TypeRef = new TypeReference<ResultInfo<Map<String, Object>>>() {};
		ResultInfo<?> result = new ResultInfo<>();
		try {
		    	result = (ResultInfo<?>) HttpClientUtils.post(url, list, clientId, clientKey, TypeRef);
		    	if (result.getCode() == 0){
                    //VDERP-2263   订单售后采购改动通知
                    updateOutOrderDataTime(list);
		    	    return new ResultInfo(0, "修改成功");
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return new ResultInfo<>();
		}
		return result;
	}

	@Override
    public List<WarehouseGoodsOperateLog> printAllOutOrder(Saleorder saleorder) {
        // 接口调用
        String url = httpUrl + "warehousegoodsoperatelog/printalloutorder.htm";
        // 定义反序列化 数据格式
        final TypeReference<ResultInfo<List<WarehouseGoodsOperateLog>>> TypeRef2 = new TypeReference<ResultInfo<List<WarehouseGoodsOperateLog>>>() {};
        try {
            ResultInfo<List<WarehouseGoodsOperateLog>> result2 = (ResultInfo<List<WarehouseGoodsOperateLog>>) HttpClientUtils.post(url, saleorder, clientId, clientKey,TypeRef2);
            if (null == result2) {
                return null;
            }
            List<WarehouseGoodsOperateLog> res = (List<WarehouseGoodsOperateLog>) result2.getData();
            return res;
        } catch (IOException e) {
            return null;
        }
    }

	@Override
	public Integer updateWarehouse(List<WarehouseGoodsOperateLogVo> wg) {
		return warehouseGoodsOperateLogMapper.updateWarehouse(wg);
	}

	@Override
	public List<WarehouseGoodsOperateLogVo> getWarehouseGoodsId(WarehouseGoodsOperateLogVo warehouseGoodsOperateLogVo) {
		return warehouseGoodsOperateLogMapper.getWarehouseGoodsId(warehouseGoodsOperateLogVo);
	}

	@Override
	public Integer updateGoodId(WarehouseGoodsStatus warehouseGoodsStatus) {
		return warehouseGoodsStatusMapper.updateGoodId(warehouseGoodsStatus);
	}

	/* (non-Javadoc)
	 * @see com.vedeng.logistics.service.WarehouseGoodsOperateLogService#getfirstRegistrationInfo(java.util.List)
	 * 获取首营信息的产品注册证号/者备案凭证编号 ,生产企业,生产企业许可证号/备案凭证编号	,储运条件  字段)
	 */
	@Override
	public List<WarehouseGoodsOperateLog> getfirstRegistrationInfo(List<WarehouseGoodsOperateLog> woList) {
		List<WarehouseGoodsOperateLog> list =warehouseGoodsOperateLogMapper.getfirstRegistrationInfo(woList);
		return list;
	}
    /**
     *判断是否为活动商品出入库记录
     * @Author:strange
     * @Date:09:30 2019-12-06
     */
	@Override
	public WarehouseGoodsOperateLog updateIsActionGoods(WarehouseGoodsOperateLog wlog) {
        Integer warehouseGoodsOperateLogId = wlog.getWarehouseGoodsOperateLogId();
        //销售
		if(wlog.getOperateType().equals(ErpConst.TWO)){
            WarehouseGoodsOperateLog warehouseInfo = warehouseGoodsOperateLogMapper.getWarehouseInfoById(warehouseGoodsOperateLogId);
            SaleorderGoods saleorderGoods = saleorderGoodsMapper.selectByPrimaryKey(warehouseInfo.getRelatedId());
            wlog.setRelatedId(warehouseInfo.getRelatedId());
            //是否是活动商品
            if (saleorderGoods.getIsActionGoods() > 0){
                wlog.setIsActionGoods(1);
            }
        }else if(wlog.getOperateType().equals(ErpConst.THREE)||wlog.getOperateType().equals(ErpConst.FOUR)||wlog.getOperateType().equals(ErpConst.FIVE)){
		    //售后
            WarehouseGoodsOperateLog warehouseInfo = warehouseGoodsOperateLogMapper.getWarehouseInfoById(warehouseGoodsOperateLogId);
            AfterSalesGoods afterSalesGoods = new AfterSalesGoods();
            afterSalesGoods.setAfterSalesGoodsId(warehouseInfo.getRelatedId());
            AfterSalesGoodsVo afterSalesGoodsInfo = afterSalesGoodsMapper.getAfterSalesGoodsInfo(afterSalesGoods);
            //是否是活动商品
			wlog.setRelatedId(warehouseInfo.getRelatedId());
            if(afterSalesGoodsInfo.getIsActionGoods() > 0){
                wlog.setIsActionGoods(1);
            }

        }
		return wlog;
	}

	@Override
	public Integer getWarehouseIsEnable(Integer wlogId) {
		WarehouseGoodsOperateLog warehouseInfo = warehouseGoodsOperateLogMapper.getWarehouseInfoById(wlogId);
		if(warehouseInfo != null && warehouseInfo.getIsEnable().equals(1)){
			return  1;
		}
		return 0;
	}
	/**
	 *获取条码是否可以   0 不可用  1 可用
	 * @Author:strange
	 * @Date:14:34 2019-12-16
	 * @param type 业务类型  1入库  2出库
	 * @param wl 获取条码信息用barcodeId查询出入库记录
	 */
	@Override
	public Integer getBarcodeIsEnable(WarehouseGoodsOperateLog wl, int type) {
		if(type == 1 || type == 2) {
			wl.setOperatorfalg(type);
		}
		//通过条码id查看出入库记录
        Barcode  barcode = new Barcode();
		if(wl.getBarcodeId() == null && wl.getWarehouseGoodsOperateLogId() != null){
		    barcode = barcodeMapper.getBarcodeByWarehouseGoodsOperateLogId(wl.getWarehouseGoodsOperateLogId());
		    if(barcode == null){
				return 0;
			}
			wl.setBarcodeId(barcode.getBarcodeId());
		}else if(wl.getBarcodeId() == null && wl.getBarcode() != null){
		    barcode =  barcodeMapper.getBarcodeByBarcode(wl.getBarcode());
		    if(barcode == null){
		        return 0;
            }
		    wl.setBarcodeId(barcode.getBarcodeId());
        }
		if(barcode.getBarcode() == null && wl.getBarcodeId() != null ){
			barcode = warehousesService.getBarcodeInfoById(wl.getBarcodeId());
		}
		if(wl.getBarcodeId() != null) {
			List<WarehouseGoodsOperateLog> list = warehouseGoodsOperateLogMapper.getBarcodeIsEnable(wl);
			if (CollectionUtils.isNotEmpty(list)) {
				return 0;
			}
		}
		Boolean lock;
		if(type ==2){
		//redis分布式锁,入库条码记录id为key
		String key = ErpConst.BARCODE + barcode.getBarcode();
		 lock = redisUtils.tryGetDistributedLock(key, UUID.randomUUID().toString(), 300000);
		}else{
			//redis分布式锁,入库码id为key
			String key = ErpConst.RETURN_BARCODE+barcode.getBarcode();
			lock = redisUtils.tryGetDistributedLock(key, UUID.randomUUID().toString(),300000);
		}
		if(!lock){
			return 0;
		}

		return 1;
	}

	@Override
	public void releaseDistributedLock(List<WarehouseGoodsOperateLog> list) {
		if(CollectionUtils.isNotEmpty(list)){
			for (WarehouseGoodsOperateLog wl : list) {
				if(wl.getBarcode() == null && wl.getBarcodeId() != null){
					Barcode barcode = warehousesService.getBarcodeInfoById(wl.getBarcodeId());
					wl.setBarcode(barcode.getBarcode());
				}
				Integer operateType = wl.getOperateType();
				//出库
				if(operateType.equals(StockOperateTypeConst.WAREHOUSE_OUT)||operateType.equals(StockOperateTypeConst.ORDER_WAREHOUSE_CHANGE_OUT)
						||operateType.equals(StockOperateTypeConst.BUYORDER_WAREHOUSE_BACK_OUT)||operateType.equals(StockOperateTypeConst.BUYORDER_WAREHOUSE_CHANGE_OUT)
				||operateType.equals(StockOperateTypeConst.LENDOUT_WAREHOUSE_OUT)){
					String key = ErpConst.BARCODE + wl.getBarcode();
					String s = redisUtils.get(key);
					redisUtils.releaseDistributedLock(key,s);
				}else{
				    //入库
					String key = ErpConst.RETURN_BARCODE + wl.getBarcode();
					String s = redisUtils.get(key);
					redisUtils.releaseDistributedLock(key,s);
				}

			}
		}
	}

	@Override
	public Boolean isEnableOutForAction(List<WarehouseGoodsOperateLog> wgList) {
		HashMap<String,Integer> skuOutNumMap = new HashMap<>();
		for (WarehouseGoodsOperateLog warehouseGoodsOperateLog : wgList) {
			if(warehouseGoodsOperateLog.getIsActionGoods().equals(0)){
				String sku = goodsMapper.getSkuByGoodsId(warehouseGoodsOperateLog.getGoodsId());
				Integer skuNum = skuOutNumMap.get(sku);
				if(skuNum == null){
					skuNum = 0;
				}
				skuOutNumMap.put(sku,skuNum - warehouseGoodsOperateLog.getNum());
			}
		}
		if(!skuOutNumMap.isEmpty()){
			Set<String> skuset = skuOutNumMap.keySet();
			List<String> skulist = new ArrayList<>();
			skulist.addAll(skuset);

			Map<String, WarehouseStock> stockInfo = warehouseStockService.getStockInfo(skulist);
			for (String sku : skuset) {
				WarehouseStock warehouseStock = stockInfo.get(sku);
				//普通商品可出库量
				Integer enableNum = warehouseStock.getStockNum()-warehouseStock.getActionLockNum();
				//出库数量大于普通商品可出库量不予许出库
				if(enableNum - skuOutNumMap.get(sku) < 0){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void updateWarehouselogInfo(WarehouseGoodsOperateLog wl) {
		Long productDate = wl.getProductDate();
		Long expirationDate = wl.getExpirationDate();
		long time = System.currentTimeMillis();
		//生产日期为空 or效期 or 当前时间不在生产日期和效期之间 则保质期产品为否
		if(productDate == null || productDate.equals(0L)){
			wl.setProductDateStr("1");
		}else if(expirationDate == null || expirationDate.equals(0L)){
			wl.setProductDateStr("1");
		}else if(expirationDate < time){
			wl.setProductDateStr("1");
		}
		//获取日志记录的库位信息
		WarehouseGoodsOperateLog storafeInfo =warehouseGoodsOperateLogMapper.getStorageInfo(wl.getWarehouseGoodsOperateLogId());
		StringBuffer sb = new StringBuffer();
		if(StringUtils.isNotBlank(storafeInfo.getStorageRoomName())){
			sb = sb.append(storafeInfo.getStorageRoomName()).append(" ");
		}
		if(StringUtils.isNotBlank(storafeInfo.getStorageAreaName())){
			sb = sb.append(storafeInfo.getStorageAreaName()).append(" ");
		}
		if(StringUtils.isNotBlank(storafeInfo.getStorageRackName())){
			sb = sb.append(storafeInfo.getStorageRackName()).append(" ");
		}
		if(StringUtils.isNotBlank(storafeInfo.getStorageLocationName())){
			sb = sb.append(storafeInfo.getStorageLocationName()).append(" ");
		}
		wl.setStorageAreaName(sb.toString());
	}

}
