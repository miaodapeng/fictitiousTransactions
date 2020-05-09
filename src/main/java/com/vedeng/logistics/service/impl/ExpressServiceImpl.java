package com.vedeng.logistics.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.common.constants.Contant;
import com.vedeng.aftersales.service.WebAccountService;
import com.vedeng.common.constant.CommonConstants;
import com.vedeng.common.constant.OrderDataUpdateConstant;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.util.EmptyUtils;
import com.vedeng.finance.model.InvoiceApply;
import com.vedeng.finance.service.InvoiceService;
import com.vedeng.logistics.model.*;
import com.vedeng.logistics.service.LogisticsService;
import com.vedeng.order.dao.SaleorderMapper;
import com.vedeng.order.service.SaleorderService;
import com.vedeng.passport.api.wechat.dto.req.ReqTemplateVariable;
import com.vedeng.passport.api.wechat.dto.template.TemplateVar;
import com.vedeng.trader.model.WebAccount;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.task.LogisticsInfoQuery;
import com.vedeng.authorization.dao.UserMapper;
import com.vedeng.authorization.model.User;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ReqVo;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.HttpRestClientUtil;
import com.vedeng.logistics.dao.ExpressMapper;
import com.vedeng.logistics.model.Express;
import com.vedeng.logistics.model.ExpressDetail;
import com.vedeng.logistics.model.LogisticsDetail;
import com.vedeng.logistics.service.ExpressService;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.SaleorderGoods;
import com.vedeng.order.model.vo.SaleorderVo;
import com.vedeng.soap.service.VedengSoapService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.annotation.Resource;

import static com.vedeng.common.controller.BaseSonContrller.sendTemplateMsg;

/**
 * 修改的时候注意一下定时任务：LogisticsInfoTask
 * @author vedeng
 *
 */
@Service("expressService")
public class ExpressServiceImpl extends BaseServiceimpl implements ExpressService {


    @Value("${OrderClosingNotice}")
	protected String OrderClosingNotice; //订单关闭通知

	@Value("${vx_service}")
	protected String vxService;

    @Value("${mjx_page}")
	protected String mjxPage;

	@Autowired
	@Qualifier("userMapper")
	private UserMapper userMapper;

	@Autowired
	@Qualifier("vedengSoapService")
	private VedengSoapService vedengSoapService;

	@Autowired
	@Qualifier("saleorderService")
	private SaleorderService saleorderService;

	 @Autowired
	 protected WebAccountService webAccountService;

	@Autowired
	@Qualifier("logisticsService")
	protected LogisticsService logisticsService;

	@Autowired
	@Qualifier("saleorderMapper")
	private SaleorderMapper saleorderMapper;

	@Resource
	private ExpressMapper expressMapper;

	@Autowired
	private InvoiceService invoiceService;

	/**
	 * 记录日志
	 */

    public static Logger LOG = LoggerFactory.getLogger(ExpressServiceImpl.class);

	@Override
	public List<Express> getExpressInfo(Saleorder saleorder) {
		List<Express> list = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<Express>>> TypeRef = new TypeReference<ResultInfo<List<Express>>>() {
		};
		String url = httpUrl + "logistics/express/getexpresslist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorder, clientId, clientKey, TypeRef);
			list = (List<Express>) result.getData();
		} catch (IOException e) {
			LOG.error(Contant.ERROR_MSG, e);
		}
		return list;
	}

	/**
	 * 获取快递信息
	 */
	@Override
	public List<Express> getExpressList(Express express) throws Exception {
		// TODO Auto-generated method stub
		// 调用接口补充快递单信息
		String url = httpUrl + "logistics/express/getexpressinfo.htm";
		// 定义反序列化 数据格式
		List<Express> expressList = null;
		final TypeReference<ResultInfo<List<Express>>> TypeRef = new TypeReference<ResultInfo<List<Express>>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, express, clientId, clientKey, TypeRef);
			if (result.getCode() == 0) {
				expressList = (List<Express>) result.getData();
				// 快递操作人人员查询
				List<Integer> userIds = new ArrayList<>();
				if (null != expressList) {
					for (Express e : expressList) {
						userIds.add(e.getCreator());
					}
				}
				if (userIds.size() > 0) {
					List<User> userList = userMapper.getUserByUserIds(userIds);
					// 信息补充
					if (null != expressList) {
						for (Express e : expressList) {
							for (User u : userList) {
								if (e.getCreator().equals(u.getUserId())) {
									e.setUpdaterUsername(u.getUsername());
								}
							}
						}
					}
				}
			}
			return expressList;
		} catch (IOException e) {
			LOG.error(Contant.ERROR_MSG, e);
			return null;
		}
	}
	/*新商品流*/
	@Override
	public List<Express> getExpressListNew(Express express) throws Exception {
		// TODO Auto-generated method stub
		// 调用接口补充快递单信息
		String url = httpUrl + "logistics/express/getexpressinfoNew.htm";
		// 定义反序列化 数据格式
		List<Express> expressList = null;
		final TypeReference<ResultInfo<List<Express>>> TypeRef = new TypeReference<ResultInfo<List<Express>>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, express, clientId, clientKey, TypeRef);
			if (result.getCode() == 0) {
				expressList = (List<Express>) result.getData();
				// 快递操作人人员查询
				List<Integer> userIds = new ArrayList<>();
				if (null != expressList) {
					for (Express e : expressList) {
						userIds.add(e.getCreator());
					}
				}
				if (userIds.size() > 0) {
					List<User> userList = userMapper.getUserByUserIds(userIds);
					// 信息补充
					if (null != expressList) {
						for (Express e : expressList) {
							for (User u : userList) {
								if (e.getCreator().equals(u.getUserId())) {
									e.setUpdaterUsername(u.getUsername());
								}
							}
						}
					}
				}
			}
			return expressList;
		} catch (IOException e) {
			LOG.error(Contant.ERROR_MSG, e);
			return null;
		}
	}

	/**
	 * 保存快递信息
	 */
	@Override
	public ResultInfo<?> saveExpress(Express express) {
		String url = httpUrl + "logistics/express/saveexpressinfo.htm";
		final TypeReference<ResultInfo<Express>> TypeRef = new TypeReference<ResultInfo<Express>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, express, clientId, clientKey, TypeRef);
			// 接口返回条码生成的记录
			return result;
		} catch (Exception e) {
			LOG.error("call db saveexpressinfo error:"+ JSON.toJSONString(express), e);
			return new ResultInfo();
		}
	}

	@Override
	public Map<String, Object> getExpressListPage(Express express, Page page) {
		List<Express> list = null;
		Map<String, Object> map = new HashMap<>();
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<Express>>> TypeRef = new TypeReference<ResultInfo<List<Express>>>() {
		};
		String url = httpUrl + "logistics/express/getexpresslistpage.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, express, clientId, clientKey, TypeRef,
					page);
			list = (List<Express>) result.getData();
			if (null != list && list.size() > 0) {
				List<Integer> userIds = new ArrayList<>();
				for (Express ex : list) {
					if (ex.getCreator() > 0) {
						userIds.add(ex.getCreator());
					}

					// 是否超时
					String addTime = DateUtil.convertString(ex.getAddTime(), "yyyy-MM-dd HH:mm:ss");
					String now = DateUtil.convertString(DateUtil.sysTimeMillis(), "yyyy-MM-dd HH:mm:ss");
					long days = DateUtil.getDistanceTimeDays(addTime, now);

					if ((int) days > 5) {
						ex.setIsovertime(1);
					}
				}

				if (userIds.size() > 0) {
					List<User> userList = userMapper.getUserByUserIds(userIds);
					/*
					 * for(Express ex : list){ for(User u : userList){
					 * ex.setCreatName(u.getUsername()); } }
					 */
					for (Express ex : list) {
						for (User u : userList) {
							if (ex.getCreator().equals(u.getUserId())) {
								ex.setCreatName(u.getUsername());
							}
						}
					}
				}
			}

			map.put("list", list);
			map.put("page", result.getPage());
		} catch (Exception e) {
			LOG.error(Contant.ERROR_MSG, e);
		}
		return map;
	}

	@Override
	public ResultInfo<?> editBatchExpress(List<Express> epList) {
		String url = httpUrl + "logistics/express/editbatchexpress.htm";
		final TypeReference<ResultInfo<List<SaleorderVo>>> TypeRef = new TypeReference<ResultInfo<List<SaleorderVo>>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, epList, clientId, clientKey, TypeRef);
			if (result!= null && result.getCode() == 0) {
				// 签收消息推送
				List<SaleorderVo> saleorderList = (List<SaleorderVo>) result.getData();
				// 批量签收推送
				if (CollectionUtils.isNotEmpty(saleorderList) && null != saleorderList.get(0)) {
					vedengSoapService.messageBtachSignSyncWeb(saleorderList);

					//更新订单updateData时间
					for (SaleorderVo saleorderVo : saleorderList) {
						updateSaleOrderDataUpdateTime(saleorderVo.getSaleorderId(),null, OrderDataUpdateConstant.SALE_ORDER_EXPRESS_END);
					}
				}
				return result;
			} else {
				return new ResultInfo();
			}
		} catch (IOException e) {
			LOG.error("call db editBatchExpress error:", e);
			return new ResultInfo();
		}
	}

	@Override
	public ResultInfo<?> delExpress(Express express) {
		String url = httpUrl + "logistics/express/delExpress.htm";
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, express, clientId, clientKey, TypeRef);
			// 接口返回条码生成的记录
			if (result.getCode() == 0) {
				return new ResultInfo(0, "操作成功");
			} else {
				return new ResultInfo();
			}
		} catch (IOException e) {
			LOG.error(Contant.ERROR_MSG, e);
			return new ResultInfo();
		}
	}

	@Override
	public ResultInfo<?> queryState() {
		LogisticsInfoQuery lf = new LogisticsInfoQuery();
		ResultInfo<?> result = null;
		try {
			result = lf.synchronizationInfo();
		} catch (Exception e) {
			LOG.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public Express getCntExpress(Express express) {
		// 接口调用
		String url = httpUrl + "logistics/express/getcntexpress.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Express>> TypeRef2 = new TypeReference<ResultInfo<Express>>() {
		};
		try {
			ResultInfo<Express> result2 = (ResultInfo<Express>) HttpClientUtils.post(url, express, clientId, clientKey,
					TypeRef2);
			if (null == result2) {
				return null;
			}
			Express res = (Express) result2.getData();
			return res;
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public Express getExpressInfoByNo(Express ex) {
		// 接口调用
		String url = httpUrl + "logistics/express/getexpressinfobyno.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Express>> TypeRef2 = new TypeReference<ResultInfo<Express>>() {
		};
		try {
			ResultInfo<Express> result2 = (ResultInfo<Express>) HttpClientUtils.post(url, ex, clientId, clientKey,
					TypeRef2);
			if (null == result2) {
				return null;
			}
			Express res = (Express) result2.getData();
			return res;
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public ResultInfo<?> editExpres(List<Express> exList) {
		String url = httpUrl + "warehouseout/editexpress.htm";
		final TypeReference<ResultInfo<List<SaleorderVo>>> TypeRef = new TypeReference<ResultInfo<List<SaleorderVo>>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, exList, clientId, clientKey, TypeRef);
			// 接口返回条码生成的记录
			if (result.getCode() == 0) {
				return new ResultInfo(0, "修改成功", result.getData());
			} else {
				return new ResultInfo();
			}
		} catch (

		IOException e) {
			LOG.error(Contant.ERROR_MSG, e);
			return new ResultInfo();
		}
	}

	@Override
	public ResultInfo<?> editLogisticsDetail(List<LogisticsDetail> ldList) {
		String url = httpUrl + "warehouseout/editlogisticsdetail.htm";
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, ldList, clientId, clientKey, TypeRef);
			// 接口返回条码生成的记录
			if (result.getCode() == 0) {
				return new ResultInfo(0, "修改成功");
			} else {
				return new ResultInfo();
			}
		} catch (IOException e) {
			LOG.error(Contant.ERROR_MSG, e);
			return new ResultInfo();
		}
	}

	@Override
	public SaleorderGoods getSaleorderGoodsInfoById(Integer saleorderGoodsId) {
		SaleorderGoods saleorderGoods = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<SaleorderGoods>> TypeRef = new TypeReference<ResultInfo<SaleorderGoods>>() {
		};
		String url = httpUrl + "order/saleorder/getsaleordergoodsinfobyid.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorderGoodsId, clientId, clientKey,
					TypeRef);
			saleorderGoods = (SaleorderGoods) result.getData();
		} catch (IOException e) {
			LOG.error(Contant.ERROR_MSG, e);
		}
		return saleorderGoods;
	}



	@Override
	public ResultInfo<?> batchUpdateExpress(List<Express> epList) {
		String url = httpUrl + "logistics/express/editbatchexpress.htm";
		final TypeReference<ResultInfo<List<SaleorderVo>>> TypeRef = new TypeReference<ResultInfo<List<SaleorderVo>>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, epList, clientId, clientKey, TypeRef);
			// 接口响应信息
			if (null == result || result.getCode() != 0) {
				return new ResultInfo();
			} else {
				return new ResultInfo(0, "修改成功", result.getData());
			}
		} catch (IOException e) {
			LOG.error(Contant.ERROR_MSG, e);
			return new ResultInfo();
		}
	}

	@Override
	public Express updateExpressInfoById(Express express) {
		// 接口调用
		String url = httpUrl + "logistics/express/updateexpressInfoById.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Express>> TypeRef2 = new TypeReference<ResultInfo<Express>>() {
		};
		try {
			ResultInfo<Express> result2 = (ResultInfo<Express>) HttpClientUtils.post(url, express, clientId, clientKey,
					TypeRef2);
			if (null == result2) {
				return null;
			}
			Express res = (Express) result2.getData();
			return res;
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public Express getExpressInfoByInvoiceNo(Integer invoiceId) {
		// 接口调用
		String url = httpUrl + "logistics/express/getexpressinfobyinvoiceno.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Express>> TypeRef2 = new TypeReference<ResultInfo<Express>>() {
		};
		try {
			ResultInfo<Express> result2 = (ResultInfo<Express>) HttpClientUtils.post(url, invoiceId, clientId,
					clientKey, TypeRef2);
			if (null == result2) {
				return null;
			}
			Express res = (Express) result2.getData();
			return res;
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public LogisticsDetail getLogisticsDetailInfo(Express express) {
		// 接口调用
		String url = httpUrl + "logistics/express/getlogisticsdetailinfo.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<LogisticsDetail>> TypeRef2 = new TypeReference<ResultInfo<LogisticsDetail>>() {
		};
		try {
			ResultInfo<LogisticsDetail> result2 = (ResultInfo<LogisticsDetail>) HttpClientUtils.post(url, express,
					clientId, clientKey, TypeRef2);
			if (null == result2) {
				return null;
			}
			LogisticsDetail res = (LogisticsDetail) result2.getData();
			return res;
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public List<Express> getExpressListBySaleorderNo(String saleorderNo) throws Exception {
		// TODO Auto-generated method stub
		// 调用接口补充快递单信息
		String url = httpUrl + "logistics/express/getexpresslistbysaleorderno.htm";
		// 定义反序列化 数据格式
		List<Express> expressList = null;
		final TypeReference<ResultInfo<List<Express>>> TypeRef = new TypeReference<ResultInfo<List<Express>>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorderNo, clientId, clientKey, TypeRef);
			if (result.getCode() == 0) {
				expressList = (List<Express>) result.getData();
			}
			return expressList;
		} catch (IOException e) {
			LOG.error(Contant.ERROR_MSG, e);
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vedeng.logistics.service.ExpressService#
	 * getSaleorderGoodsListByexpressId(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SaleorderGoods> getSaleorderGoodsListByexpressId(Integer expressId) {
		// TODO Auto-generated method stub
		// 调用接口补充快递单信息
		String url = restDbUrl + "rest/order/hc/v1/search/getSaleOrderGoods";
		final TypeReference<ResultInfo<List<SaleorderGoods>>> TypeRef = new TypeReference<ResultInfo<List<SaleorderGoods>>>() {
		};
		Express ex = new Express();
		ex.setExpressId(expressId);
		ReqVo<Express> reqVo = new ReqVo<Express>();
		reqVo.setReq(ex);
		List<SaleorderGoods> goodsList = null;
		ResultInfo<?> result = (ResultInfo<?>) HttpRestClientUtil.post(url, TypeRef, reqVo);
		if (result.getCode() == 0) {
			goodsList = (List<SaleorderGoods>) result.getData();
		}
		return goodsList;
	}

	@Override public ResultInfo<?> sendWxMessageForExpress(Saleorder saleOrderInfo, Express express, Map<String, String> wxDataMap){
		ResultInfo resultInfo = new ResultInfo<>();

		try {

			if(saleOrderInfo != null && null != wxDataMap) {
				//快递公司为“客户自提/其他/中铁物流”之外的数据
				if(express.getLogisticsId() !=null && !express.getLogisticsId().equals(CommonConstants.LOGISTICS_ID_EIGHT) && !express.getLogisticsId().equals(CommonConstants.LOGISTICS_ID_SEVEN) && !express.getLogisticsId().equals(CommonConstants.LOGISTICS_ID_FOUR)){
					//如果销售订单是VS订单，DH订单，JX订单则推送贝登官网发送微信推送给客户
					if(CommonConstants.SALEORDER_TYPE_ZERO.equals(saleOrderInfo.getOrderType()) || CommonConstants.SALEORDER_TYPE_THREE.equals(saleOrderInfo.getOrderType()) || CommonConstants.SALEORDER_TYPE_FOUR.equals(saleOrderInfo.getOrderType())){
						/*********************准备调用接口数据START************************/
						//获取销售订单联系人
						WebAccount webaccount = new WebAccount();
						if(saleOrderInfo.getTraderContactId() !=null ){
							webaccount.setTraderContactId(saleOrderInfo.getTraderContactId());
							//获取销售单联系人关联的注册用户信息
						 	List<WebAccount> webAccountList= webAccountService.getWebAccountByTraderContactId(webaccount);

							if(webAccountList !=null && webAccountList.size()>0){
								LOG.info("发货发送微信模版消息开始....start....saleorderId="+saleOrderInfo.getSaleorderId());
								for(WebAccount wa:webAccountList){
									//推送微信消息
									ResultInfo res= vedengSoapService.sendWxMessage(wxDataMap,CommonConstants.WX_TEMPLATE_NO_FH,wa.getUuid());
									if(res.getCode().equals(0)){
										LOG.info("发货发送微信模版消息发送成功.......saleorderId="+saleOrderInfo.getSaleorderId());
									}else{
										LOG.error("发货发送微信模版消息发送失败.......saleorderId="+saleOrderInfo.getSaleorderId());
									}
								}
								LOG.info("发货发送微信模版消息结束....end....saleorderId="+saleOrderInfo.getSaleorderId());
								return new ResultInfo<>(0, "发送成功");
							}
						}
						/*********************准备调用接口数据END************************/
					}
				}

			}
			return resultInfo;
		}catch (Exception e){
			LOG.error("发送发货微信模版消息失败 快递单号:"+express.getLogisticsNo(),e);
			return resultInfo;
		}

	}

	@Override
	public ResultInfo sendWxMessageForArrival(Integer saleorderId){
		ResultInfo resultInfo = new ResultInfo();
		Saleorder saleOrderInfo = new Saleorder();

		try {
			//查询订单信息
			Saleorder saleorder = new Saleorder();
			saleorder.setSaleorderId(saleorderId);
			saleOrderInfo = saleorderService.getBaseSaleorderInfo(saleorder);
			if(saleOrderInfo !=null ){
				/*********************准备调用接口数据START************************/
				//获取销售订单联系人
				WebAccount webaccount = new WebAccount();
				if(saleOrderInfo.getTraderContactId() !=null ){
					webaccount.setTraderContactId(saleOrderInfo.getTraderContactId());
					//获取销售单联系人关联的注册用户信息
					List<WebAccount> webAccountList= webAccountService.getWebAccountByTraderContactId(webaccount);
					//订单实际金额
					// 获取交易信息（订单实际金额，客户已付款金额）
					Map<String, BigDecimal> saleorderDataInfo = saleorderService.getSaleorderDataInfo(saleorderId);
					BigDecimal realAmount =  saleorderDataInfo.get("realAmount");
					// 获取订单产品信息(与订单详情中获取相同)
					Saleorder sale = new Saleorder();
					sale.setSaleorderId(saleOrderInfo.getSaleorderId());
					sale.setTraderId(saleOrderInfo.getTraderId());
					sale.setCompanyId(saleOrderInfo.getCompanyId());
					List<SaleorderGoods> saleorderGoodsList = saleorderService.getSaleorderGoodsById(sale);
					//商品总数量（除去售后数量）
					Integer saleorderAllNum =0;
					String saleorderFirstGoodsName = "";
					if(saleorderGoodsList != null && saleorderGoodsList.size()>0){
						for (SaleorderGoods sg : saleorderGoodsList) {
							// 运费
							if(null == sg || "V127063".equals(sg.getSku()) || "V251526".equals(sg.getSku()) || "V252843".equals(sg.getSku()) || "V256675".equals(sg.getSku())) {
								continue;
							}
							if(EmptyUtils.isBlank(saleorderFirstGoodsName)) {
								saleorderFirstGoodsName = sg.getGoodsName();
							}
							//商品数-售后数
							saleorderAllNum = saleorderAllNum+(sg.getNum()-sg.getAfterReturnNum());
						}
					}

					Map data = new HashMap<>();//消息数据
					//订单编号
					data.put("totalAmount",realAmount);
					//快递单号对应的第一个商品名称
					data.put("saleorderFirstGoodsName", saleorderFirstGoodsName);
					//商品总数量（除去售后数量）
					data.put("saleorderAllNum", saleorderAllNum);
					//快递单商品总数量
					data.put("traderContactName",saleOrderInfo.getTraderContactName());
					data.put("traderContactMobile",saleOrderInfo.getTraderContactMobile());
					data.put("traderAddress",saleOrderInfo.getTraderAddress());
					data.put("saleorderNo",saleOrderInfo.getSaleorderNo());
					data.put("takeTraderAddress",saleOrderInfo.getTakeTraderAddress());
					data.put("takeTraderArea",saleOrderInfo.getTakeTraderArea());

					resultInfo.setData(data);

//					if(webAccountList !=null && webAccountList.size()>0){
//						LOG.info("签收发送微信模版消息开始....start....saleorderId="+saleorderId);
//						for(WebAccount wa:webAccountList){
//							//推送微信消息
//							ResultInfo res = vedengSoapService.sendWxMessage(data, CommonConstants.WX_TEMPLATE_NO_QS,wa.getUuid());
//							//如果推送成功则回写T_SALEORDER中的LOGISTICS_WXSEND_SYNC字段
//							if(res.getCode().equals(0)){
//								saleorder.setLogisticsWxsendSync(1);
//								saleorderMapper.updateByPrimaryKeySelective(saleorder);
//								LOG.info("签收发送微信模版消息发送成功.......saleorderId="+saleorderId);
//							}else{
//								LOG.error("签收发送微信模版消息发送失败.......saleorderId="+saleorderId);
//							}
//						}
//						LOG.info("签收发送微信模版消息结束....end....saleorderId="+saleorderId);
//						return new ResultInfo(0, "发送成功", data);
//					}
				}
				/*********************准备调用接口数据END************************/

			}
			return resultInfo;
		}catch (Exception e){
			LOG.error("订单签收发送微信模版消息失败 订单ID:"+saleorderId,e);
			return resultInfo;
		}
	}

	@Override
	public Map<String, String> sendForExpress(Saleorder saleOrderInfo, Express express) {

		LOG.info("sendForExpress | saleOrderInfo :{}, express : {}", saleOrderInfo, express);
		if(null == saleOrderInfo || null == express) {
			return null;
		}

		// 获取订单产品信息(与订单详情中获取相同)
		Saleorder sale = new Saleorder();
		sale.setSaleorderId(saleOrderInfo.getSaleorderId());
		sale.setTraderId(saleOrderInfo.getTraderId());
		sale.setCompanyId(saleOrderInfo.getCompanyId());
		sale.setReqType(1);
		List<SaleorderGoods> saleorderGoodsList = saleorderService.getSaleorderGoodsById(sale);

		// 物流信息
		Express expressInfo = new Express();
		expressInfo.setBusinessType(SysOptionConstant.ID_496);
		expressInfo.setCompanyId(saleOrderInfo.getCompanyId());
		expressInfo.setExpressId(express.getExpressId());
		List<Integer> relatedIds = new ArrayList<Integer>();
		//商品总数量（除去售后数量）
		Integer saleorderAllNum = 0;
		for (SaleorderGoods sg : saleorderGoodsList) {
			// 运费
			if(null == sg || "V127063".equals(sg.getSku()) || "V251526".equals(sg.getSku()) || "V252843".equals(sg.getSku()) || "V256675".equals(sg.getSku())) {
				continue;
			}
			relatedIds.add(sg.getSaleorderGoodsId());
			//商品数-售后数
			saleorderAllNum = saleorderAllNum+(sg.getNum()-sg.getAfterReturnNum());
		}
		//快递单号对应的第一个商品名称
		String expressFirstGoodsName = "";
		//快递单商品总数量
		Integer expressAllNum = 0;
		if (relatedIds != null && relatedIds.size() > 0) {
			//根据ID获取快递单信息
			Express expressList = this.getExpressInfoById(expressInfo);
			if (expressList != null && expressList.getExpressDetail() != null) {
				expressFirstGoodsName = expressList.getExpressDetail().get(0).getGoodName();
				for(ExpressDetail ed:expressList.getExpressDetail()){
					expressAllNum = expressAllNum + ed.getNum();
				}
			}

		}
		//快递信息（快递公司名称+快递单号）
		Logistics logistics = logisticsService.getLogisticsById(express.getLogisticsId());

		Map<String, String> returnData = new HashMap<>();
		//订单编号
		returnData.put("saleorderNo", saleOrderInfo.getSaleorderNo());
		//快递单号对应的第一个商品名称
		returnData.put("expressFirstGoodsName", expressFirstGoodsName);
		//商品总数量（除去售后数量）
		returnData.put("saleorderAllNum", saleorderAllNum + "");
		//快递单商品总数量
		returnData.put("expressAllNum", expressAllNum + "");
		//订单生效时间
		returnData.put("validTime", DateUtil.convertString(saleOrderInfo.getValidTime(),DateUtil.TIME_FORMAT));
		//客户信息（联系人名称+联系人手机号）
		returnData.put("customerInfo", saleOrderInfo.getTraderContactName()+ " "+ saleOrderInfo.getTraderContactMobile());
		//快递信息（快递公司名称+快递单号）
		returnData.put("logisticsName", logistics.getName());
		returnData.put("logisticsNo", express.getLogisticsNo());

		// 返回结果
		return returnData;
	}

	@Override
	public List<Express> getLendOutExpressList(Express express) {
		// 快递操作人人员查询
		List<Integer> userIds = new ArrayList<>();
		List<Express>expressList = expressMapper.getLendOutExpressInfo(express);
		if (null != expressList) {
		for (Express ep : expressList) {
			userIds.add(ep.getCreator());
			ep.setBusiness_Type(express.getBusinessType());
			if (ep.getContent() != null && !"".equals(ep.getContent())) {
				String str = "";
				JSONObject rd = JSONObject.fromObject(ep.getContent());
				if (rd != null) {
					String message = rd.getString("message");
					if ("ok".equals(message)) {
						JSONArray ja = rd.getJSONArray("data");
						JSONObject jl = ja.getJSONObject(0);
						str = jl.getString("time") + jl.getString("context");
					} else {
						str = message;
					}
					ep.setContentNew(str);
				} else {
					ep.setContentNew("暂无信息");
				}

			} else {
				ep.setContentNew("暂无信息");
			}
		}
		}
		if (userIds.size() > 0) {
			List<User> userList = userMapper.getUserByUserIds(userIds);
			// 信息补充
			if (null != expressList) {
				for (Express e : expressList) {
					for (User u : userList) {
						if (e.getCreator().equals(u.getUserId())) {
							e.setUpdaterUsername(u.getUsername());
						}
					}
				}
			}
		}
		return expressList;
	}


/*    public Map<String, String>  shipmentToRemind(Saleorder saleOrderInfo, Express express) {
		LOG.info("shipmentToRemind | shipmentToRemind :{}, express : {}", saleOrderInfo, express);
		if(null == saleOrderInfo || null == express) {
			return null;
		}

		// 获取订单产品信息(与订单详情中获取相同)
		Saleorder sale = new Saleorder();
		sale.setSaleorderId(saleOrderInfo.getSaleorderId());
		sale.setTraderId(saleOrderInfo.getTraderId());
		sale.setCompanyId(saleOrderInfo.getCompanyId());
		sale.setReqType(1);
		List<SaleorderGoods> saleorderGoodsList = saleorderService.getSaleorderGoodsById(sale);

		// 物流信息
		Express expressInfo = new Express();
		expressInfo.setBusinessType(SysOptionConstant.ID_496);
		expressInfo.setCompanyId(saleOrderInfo.getCompanyId());
		expressInfo.setExpressId(express.getExpressId());
		List<Integer> relatedIds = new ArrayList<Integer>();
		//商品总数量（除去售后数量）
		Integer saleorderAllNum = 0;
		for (SaleorderGoods sg : saleorderGoodsList) {
			// 运费
	*//*		if(null == sg || "V127063".equals(sg.getSku()) || "V251526".equals(sg.getSku()) || "V252843".equals(sg.getSku()) || "V256675".equals(sg.getSku())) {
				continue;
			}
			relatedIds.add(sg.getSaleorderGoodsId());*//*
			//商品数-售后数
			saleorderAllNum = saleorderAllNum+(sg.getNum()-sg.getAfterReturnNum());
		}
		//快递单号对应的第一个商品名称
		String expressFirstGoodsName = "";
		//快递单商品总数量
		Integer expressAllNum = 0;
		if (relatedIds != null && relatedIds.size() > 0) {
			//根据ID获取快递单信息
			Express expressList = this.getExpressInfoById(expressInfo);
			if (expressList != null && expressList.getExpressDetail() != null) {
				expressFirstGoodsName = expressList.getExpressDetail().get(0).getGoodName();
				for(ExpressDetail ed:expressList.getExpressDetail()){
					expressAllNum = expressAllNum + ed.getNum();
				}
			}

		}
		//快递信息（快递公司名称+快递单号）
		Logistics logistics = logisticsService.getLogisticsById(express.getLogisticsId());

		Map<String, String> returnData = new HashMap<>();
		//订单编号
		returnData.put("saleorderNo", saleOrderInfo.getSaleorderNo());
		//快递单号对应的第一个商品名称
		returnData.put("expressFirstGoodsName", expressFirstGoodsName);
		//商品总数量（除去售后数量）
		returnData.put("saleorderAllNum", saleorderAllNum + "");
		//快递单商品总数量
		returnData.put("expressAllNum", expressAllNum + "");
		//订单生效时间
		returnData.put("validTime", DateUtil.convertString(saleOrderInfo.getValidTime(),DateUtil.TIME_FORMAT));
		//客户信息（联系人名称+联系人手机号）
		returnData.put("customerInfo", saleOrderInfo.getTraderContactName()+ " "+ saleOrderInfo.getTraderContactMobile());
		//快递信息（快递公司名称+快递单号）
		returnData.put("logisticsName", logistics.getName());
		returnData.put("logisticsNo", express.getLogisticsNo());

		// 返回结果
		return returnData;
    }*/

	@Override
	public void sendOrderConfirmedClose(Saleorder saleorders, Map sTempMap) {
		ReqTemplateVariable reqTemp = new ReqTemplateVariable();
		if (null != saleorders.getCreateMobile()) {
			// 订单客户联系人
			reqTemp.setMobile(saleorders.getCreateMobile());
			//reqTemp.setMobile("17554243894");
		}

		// reqTemp.setMobile(phone);
		//reqTemp.setTemplateId(OrderClosingNotice);
		reqTemp.setTemplateId(OrderClosingNotice);
		reqTemp.setJumpUrl(mjxPage+"?orderNo="+saleorders.getSaleorderNo());
		TemplateVar first = new TemplateVar();
		String firstStr = getConfigStringByDefault("尊敬的客户，您的订单已自动关闭", SysOptionConstant.THE_ORDER_CLOSED);
		LOG.info("获取数据配置 | firstStr：{} ", firstStr);
		first.setValue(firstStr + "\r\n");

		TemplateVar keyword1 = new TemplateVar();
		TemplateVar keyword2 = new TemplateVar();
		TemplateVar keyword3 = new TemplateVar();

		TemplateVar remark = new TemplateVar();
		String remarkStr = getConfigStringByDefault("感谢您对贝登的支持与信任，如有疑问请联系：4006-999-569", SysOptionConstant.WECHAT_TEMPLATE_BEDENG_REMARK);
		remark.setValue(remarkStr);

		if (null != sTempMap) {

			String saleorderAllNum = String.valueOf(sTempMap.get("saleorderAllNum"));
			// 商品名称
			keyword1.setValue((String) sTempMap.get("saleorderFirstGoodsName") + "等 " + saleorderAllNum + "个商品");
			// 订单编号
			keyword2.setValue(saleorders.getSaleorderNo());
			// 订单编号
			keyword3.setValue("超时未确认订单" + "\r\n");

			reqTemp.setFirst(first);
			reqTemp.setKeyword1(keyword1);
			reqTemp.setKeyword2(keyword2);
			reqTemp.setKeyword3(keyword3);
			reqTemp.setRemark(remark);
			// 发送 待用户确认消息推送
			sendTemplateMsg(vxService + "/wx/wxchat/send", reqTemp);
			//sendTemplateMsg("http://172.16.3.100:8280/wx/wxchat/send",reqTemp);
			LOG.info("订单关闭消息推送结束");
		}
	}

	/** @description: VDERP-1325 分批开票 查询已收货数量..
	 * @notes: 从dbcenter迁移过来.
	 * @author: Tomcat.Hui.
	 * @date: 2019/11/11 11:28.
	 * @return: com.vedeng.logistics.model.Express.
	 * @throws: .
	 */
	@Override
	public List<ExpressDetail> getSEGoodsNum(List<Integer> saleorderIds){
		String url = httpUrl + "logistics/express/getSEGoodsNum.htm";
		// 定义反序列化 数据格式
		List<ExpressDetail> expressList = null;
		final TypeReference<ResultInfo<List<ExpressDetail>>> TypeRef = new TypeReference<ResultInfo<List<ExpressDetail>>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorderIds, clientId, clientKey, TypeRef);
			if (result.getCode() == 0) {
				expressList = (List<ExpressDetail>) result.getData();
			}
			return expressList;
		} catch (IOException e) {
			LOG.error(Contant.ERROR_MSG, e);
			return null;
		}
	}


	@Override
	public Express getExpressInfoById(Express ex) {
		// 接口调用
		String url = httpUrl + "logistics/express/getexpressinfobyid.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Map<String, Object>>> TypeRef = new TypeReference<ResultInfo<Map<String, Object>>>() {
		};
		try {
			ResultInfo<Express> result2 = (ResultInfo<Express>) HttpClientUtils.post(url, ex, clientId, clientKey,
					TypeRef);
			if (null == result2) {
				return null;
			}
			Map<String, Object> res = (Map<String, Object>) result2.getData();
			JSONObject jsonObject = JSONObject.fromObject(res.get("express"));
			Express expressInfo = (Express) JSONObject.toBean(jsonObject, Express.class);
			JSONArray jsonArray = JSONArray.fromObject(res.get("expressDetails"));
			List<ExpressDetail> expressDetails = (List<ExpressDetail>) JSONArray.toCollection(jsonArray,
					ExpressDetail.class);
			expressInfo.setExpressDetail(expressDetails);
			return expressInfo;
		} catch (IOException e) {
			return null;
		}
	}
	@Override
	public List<ExpressDetail> getExpressDetailInfoBySaleorderId(Saleorder saleorder) {
		List<ExpressDetail> expressList = expressMapper.getExpressDetailList(saleorder);
		return expressList;
	}

	@Override
	public Map<Integer, Integer> getExpressDetailNumInfo(Saleorder saleorder) {
		List<ExpressDetail> expressDetaillist = getExpressDetailInfoBySaleorderId(saleorder);
		Map<Integer, Integer> map = new HashMap<>(16);
		if(CollectionUtils.isNotEmpty(expressDetaillist)) {
			for (ExpressDetail expressDetail : expressDetaillist) {
				Integer num = map.get(expressDetail.getRelatedId());
				if (num == null) {
					num = 0;
				}
				num += expressDetail.getNum();
				map.put(expressDetail.getRelatedId(), num);
			}
		}
		return map;
	}

	@Override
	public List<Integer> getExpressIds() {
		return expressMapper.getExpressIds();
	}
	/**
	 * 获取当前快递下某商品数量
	 * @Author:strange
	 * @Date:15:24 2020-01-06
	 */
	@Override
	public ExpressDetail getExpressDetailNumByExpressId(ExpressDetail expressDetail) {
		return expressMapper.getExpressDetailNumByExpressId(expressDetail);
	}
	/**
	 *获取当前快递单商品详情
	 * @Author:strange
	 * @Date:08:55 2020-01-07
	 */
	@Override
	public List<ExpressDetail> getExpressDetailByExpressId(Integer expressId) {
		return expressMapper.getExpressDetailByExpressId(expressId);
	}
    /**
     *判断是否需要重新生成开票申请
     * @Author:strange
     * @Date:15:20 2020-01-07
     */
    @Override
    public boolean isUpdateExpressAndInvoice(Express express) {
        //如果快递商品信息变更则重新生成新的开票申请
        List<ExpressDetail> nweExpressDetailList = express.getExpressDetail();
        List<ExpressDetail> oldExpressDetailList = getExpressDetailByExpressId(express.getExpressId());
        //
        if(CollectionUtils.isEmpty(oldExpressDetailList)){
            return true;
        }else if( CollectionUtils.isEmpty(nweExpressDetailList)){
            return true;
        }else{
            Map<Integer,ExpressDetail> map = oldExpressDetailList.stream().collect(Collectors.toMap(ExpressDetail::getRelatedId, goods -> goods));
            if(nweExpressDetailList.size() != oldExpressDetailList.size()){
                return true;
            }
            for (ExpressDetail nweExpressDetail : nweExpressDetailList) {
                ExpressDetail oldexpressDetail = map.get(nweExpressDetail.getRelatedId());
                if(oldexpressDetail == null){
                	return true;
				}
                if(!nweExpressDetail.getNum().equals(oldexpressDetail.getNum())){
                    return true;
                }
            }
        }
        //如果该快递下没有非关闭的开票申请则生成
        InvoiceApply invoiceApply = new InvoiceApply();
        invoiceApply.setExpressId(express.getExpressId());
		List<InvoiceApply> invoiceApplyInfoList = invoiceService.getInvoiceApplyInfoByExpressId(invoiceApply);
		if(CollectionUtils.isEmpty(invoiceApplyInfoList)){
			return true;
		}
		return false;
    }

	//是否为第一次物流
	@Override
	public List<Express> getFirst(Integer traId) {
		return expressMapper.getFirst(traId);
	}
	//改变是否开据发票状态
	@Override
	public int changeIsinvoicing(Integer invoiceApplyId) {
		return expressMapper.changeIsinvoicing(invoiceApplyId);
	}
	//改变是否开据发票状态
	@Override
	public int updateIsinvoicing(Integer expressId) {
		return expressMapper.updateIsinvoicing(expressId);
	}
	//改变是否开据发票状态
	@Override
	public int updateIsinvoicingNo(Integer expressId) {
		return expressMapper.updateIsinvoicingNo(expressId);
	}


}
