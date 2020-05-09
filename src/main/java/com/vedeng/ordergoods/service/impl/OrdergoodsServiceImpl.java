package com.vedeng.ordergoods.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.common.constants.Contant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.authorization.dao.UserMapper;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.DateUtil;
import com.vedeng.goods.model.Goods;
import com.vedeng.goods.model.vo.GoodsVo;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.SaleorderGoods;
import com.vedeng.order.model.vo.SaleorderVo;
import com.vedeng.ordergoods.dao.OrdergoodsCategoryMapper;
import com.vedeng.ordergoods.dao.OrdergoodsGoodsAccountMapper;
import com.vedeng.ordergoods.dao.OrdergoodsGoodsMapper;
import com.vedeng.ordergoods.dao.OrdergoodsLaunchGoalMapper;
import com.vedeng.ordergoods.dao.OrdergoodsLaunchMapper;
import com.vedeng.ordergoods.dao.OrdergoodsStoreAccountMapper;
import com.vedeng.ordergoods.dao.OrdergoodsStoreMapper;
import com.vedeng.ordergoods.dao.ROrdergoodsGoodsJCategoryMapper;
import com.vedeng.ordergoods.dao.ROrdergoodsLaunchGoodsJCategoryMapper;
import com.vedeng.ordergoods.model.OrdergoodsCategory;
import com.vedeng.ordergoods.model.OrdergoodsGoods;
import com.vedeng.ordergoods.model.OrdergoodsLaunch;
import com.vedeng.ordergoods.model.OrdergoodsLaunchGoal;
import com.vedeng.ordergoods.model.OrdergoodsStore;
import com.vedeng.ordergoods.model.OrdergoodsStoreAccount;
import com.vedeng.ordergoods.model.ROrdergoodsGoodsJCategory;
import com.vedeng.ordergoods.model.ROrdergoodsLaunchGoodsJCategory;
import com.vedeng.ordergoods.model.vo.OrdergoodsGoodsAccountVo;
import com.vedeng.ordergoods.model.vo.OrdergoodsGoodsVo;
import com.vedeng.ordergoods.model.vo.OrdergoodsLaunchVo;
import com.vedeng.ordergoods.model.vo.OrdergoodsStoreAccountVo;
import com.vedeng.ordergoods.model.vo.ROrdergoodsLaunchGoodsJCategoryVo;
import com.vedeng.ordergoods.service.OrdergoodsService;
import com.vedeng.system.service.RegionService;
import com.vedeng.trader.model.Trader;
import com.vedeng.trader.model.TraderAddress;
import com.vedeng.trader.model.TraderContact;
import com.vedeng.trader.model.TraderFinance;
import com.vedeng.trader.model.vo.TraderAddressVo;
import com.vedeng.trader.model.vo.TraderContactVo;
import com.vedeng.trader.model.vo.TraderCustomerVo;
import com.vedeng.trader.model.vo.TraderFinanceVo;
import com.vedeng.trader.model.vo.TraderVo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("ordergoodsService")
public class OrdergoodsServiceImpl extends BaseServiceimpl implements OrdergoodsService {

	public static Logger logger = LoggerFactory.getLogger(OrdergoodsServiceImpl.class);

	@Autowired
	@Qualifier("ordergoodsStoreMapper")
	private OrdergoodsStoreMapper ordergoodsStoreMapper;
	
	@Autowired
	@Qualifier("ordergoodsCategoryMapper")
	private OrdergoodsCategoryMapper ordergoodsCategoryMapper;
	
	@Autowired
	@Qualifier("ordergoodsGoodsMapper")
	private OrdergoodsGoodsMapper ordergoodsGoodsMapper;
	
	@Autowired
	@Qualifier("rOrdergoodsGoodsJCategoryMapper")
	private ROrdergoodsGoodsJCategoryMapper rOrdergoodsGoodsJCategoryMapper;
	
	@Autowired
	@Qualifier("rOrdergoodsLaunchGoodsJCategoryMapper")
	private ROrdergoodsLaunchGoodsJCategoryMapper rOrdergoodsLaunchGoodsJCategoryMapper;
	
	@Autowired
	@Qualifier("ordergoodsLaunchMapper")
	private OrdergoodsLaunchMapper ordergoodsLaunchMapper;
	
	@Autowired
	@Qualifier("userMapper")
	private UserMapper userMapper;
	
	@Autowired
	@Qualifier("ordergoodsLaunchGoalMapper")
	private OrdergoodsLaunchGoalMapper ordergoodsLaunchGoalMapper;
	
	@Autowired
	@Qualifier("ordergoodsStoreAccountMapper")
	private OrdergoodsStoreAccountMapper ordergoodsStoreAccountMapper;
	
	@Autowired
	@Qualifier("regionService")
	private RegionService regionService;
	
	@Autowired
	@Qualifier("ordergoodsGoodsAccountMapper")
	private OrdergoodsGoodsAccountMapper ordergoodsGoodsAccountMapper;

	@Override
	public List<OrdergoodsStore> getStore(Integer companyId) {
		return ordergoodsStoreMapper.getStore(companyId);
	}

	@Override
	public OrdergoodsStore getStoreByName(OrdergoodsStore ordergoodsStore) {
		return ordergoodsStoreMapper.getStoreByName(ordergoodsStore);
	}

	@Override
	public Integer saveAddStore(OrdergoodsStore ordergoodsStore, HttpSession session) {
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();
		
		ordergoodsStore.setIsEnable(ErpConst.ONE);;
		ordergoodsStore.setAddTime(time);
		ordergoodsStore.setCreator(user.getUserId());
		ordergoodsStore.setModTime(time);
		ordergoodsStore.setUpdater(user.getUserId());
		
		return ordergoodsStoreMapper.insert(ordergoodsStore);
	}

	@Override
	public OrdergoodsStore getStoreById(OrdergoodsStore ordergoodsStore) {
		return ordergoodsStoreMapper.selectByPrimaryKey(ordergoodsStore.getOrdergoodsStoreId());
	}

	@Override
	public Integer saveeditStore(OrdergoodsStore ordergoodsStore, HttpSession session) {
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();
		
		ordergoodsStore.setIsEnable(ErpConst.ONE);;
		ordergoodsStore.setModTime(time);
		ordergoodsStore.setUpdater(user.getUserId());
		
		return ordergoodsStoreMapper.updateByPrimaryKey(ordergoodsStore);
	}

	@Override
	public Boolean changeEnable(OrdergoodsStore ordergoodsStore, HttpSession session) {
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();
		OrdergoodsStore store = ordergoodsStoreMapper.selectByPrimaryKey(ordergoodsStore.getOrdergoodsStoreId());
		
		if(null == store){
			return false;
		}
		store.setModTime(time);
		store.setUpdater(user.getUserId());
		Integer isEnable = 0;
		if(store.getIsEnable().equals(0)){
			isEnable = 1;
		}
		if(store.getIsEnable().equals(1)){
			isEnable = 0;
		}
		store.setIsEnable(isEnable);
		
		int suc = ordergoodsStoreMapper.updateByPrimaryKey(store);
		if(suc > 0){
			return true;
		}
		return false;
	}

	@Override
	public List<OrdergoodsCategory> getOrdergoodsCategory(Integer parentId, Integer ordergoodsStoreId,Boolean joinChar) {
		List<OrdergoodsCategory> ordergoodsCategoryList = ordergoodsCategoryMapper.getOrdergoodsCategory(0,ordergoodsStoreId);
		JSONArray jsonArray = (JSONArray) JSONArray.fromObject(ordergoodsCategoryList);
		
		List<OrdergoodsCategory> sellist = new ArrayList<OrdergoodsCategory>();
		JSONArray jsonList = treeMenuList(jsonArray,parentId,"");
		List<OrdergoodsCategory> list = resetList(jsonList,sellist,0,joinChar);
		return list;
	}
	
	/**
	 * <b>Description:</b><br> 递归组装树形结构
	 * @param menuList
	 * @param parentId
	 * @param parentName
	 * @return JSONArray
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月28日 下午1:28:35
	 */
	private JSONArray treeMenuList(JSONArray menuList, int parentId,String parentName) {
        JSONArray childMenu = new JSONArray();
        for (Object object : menuList) {
            JSONObject jsonMenu = (JSONObject) JSONObject.fromObject(object);
            int menuId = jsonMenu.getInt("ordergoodsCategoryId");
            int pid = jsonMenu.getInt("parentId");
            if(parentName != ""){
            	jsonMenu.element("nameArr", parentName + "--" + jsonMenu.getString("categoryName"));
            }else{
            	jsonMenu.element("nameArr", jsonMenu.getString("categoryName"));
            }
            if (parentId == pid) {
                JSONArray c_node = treeMenuList(menuList, menuId,jsonMenu.getString("nameArr"));
                jsonMenu.put("childNode", c_node);
                childMenu.add(jsonMenu);
            }
        }
        return childMenu;
    }
	
	/**
	 * <b>Description:</b><br> 递归分析树状结构
	 * @param tasklist
	 * @param sellist
	 * @param num
	 * @param joinChar
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月28日 下午1:29:32
	 */
	public List<OrdergoodsCategory> resetList(JSONArray tasklist,List<OrdergoodsCategory> sellist,int num,Boolean joinChar){
        String str = "";
        if(joinChar){
        	for(int i=0;i<(num*2);i++){
        		if(i % 2 == 0){
        			str += "|";
        		}
        		str += "-";
        	}
        }
        for(Object obj:tasklist){
            JSONObject jsonMenu = (JSONObject) JSONObject.fromObject(obj);
            OrdergoodsCategory sm = new OrdergoodsCategory();
            sm.setOrdergoodsCategoryId(Integer.valueOf(jsonMenu.getInt("ordergoodsCategoryId")));
            String categoryName = "";
            if(joinChar){
            	categoryName = "|--"+str+jsonMenu.getString("categoryName");
            }else{
            	categoryName = jsonMenu.getString("categoryName");
            }
            sm.setCategoryName(categoryName);
            sm.setLevel(jsonMenu.getInt("level"));
            sm.setParentId(jsonMenu.getInt("parentId"));
            sm.setMinAmount(new BigDecimal(jsonMenu.getString("minAmount")));
            sm.setStatus(jsonMenu.getInt("status"));
            sm.setSort(jsonMenu.getInt("sort"));
            sellist.add(sm);
            if(jsonMenu.get("childNode")!=null){
                if(JSONArray.fromObject(jsonMenu.get("childNode")).size()>0){
                    num++;
                    resetList(JSONArray.fromObject(jsonMenu.get("childNode")),sellist,num,joinChar);
                    num--;
                }
            }
        }
        return sellist;
    }

	@Override
	public Integer saveAddCategory(OrdergoodsCategory ordergoodsCategory) {
		int insert = ordergoodsCategoryMapper.insert(ordergoodsCategory);
		if(insert > 0){
			return ordergoodsCategory.getOrdergoodsCategoryId();
		}
		return 0;
	}

	@Override
	public OrdergoodsCategory getOrdergoodsCategoryById(OrdergoodsCategory ordergoodsCategory) {
		return ordergoodsCategoryMapper.selectByPrimaryKey(ordergoodsCategory.getOrdergoodsCategoryId());
	}

	@Override
	public OrdergoodsCategory getOrdergoodsCategoryByCate(OrdergoodsCategory ordergoodsCategory) {
		return ordergoodsCategoryMapper.getOrdergoodsCategoryByCate(ordergoodsCategory);
	}

	@Override
	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	public Integer saveEditCategory(OrdergoodsCategory ordergoodsCategory,Boolean update,Integer level) {
		if(update){
			List<OrdergoodsCategory> ordergoodsCategoryList = getOrdergoodsCategory(ordergoodsCategory.getOrdergoodsCategoryId(),ordergoodsCategory.getOrdergoodsStoreId(),false);
			if(null != ordergoodsCategoryList && ordergoodsCategoryList.size() > 0){
				for(OrdergoodsCategory cate : ordergoodsCategoryList){
					cate.setLevel(ordergoodsCategory.getLevel() + (cate.getLevel() - level));
					ordergoodsCategoryMapper.updateByPrimaryKey(cate);
				}
			}
		}
		return ordergoodsCategoryMapper.updateByPrimaryKey(ordergoodsCategory);
	}

	@Override
	public Map<String, Object> getGoodsListPage(OrdergoodsGoodsVo ordergoodsGoodsVo, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		//搜索条件
		if((null != ordergoodsGoodsVo.getGoodsName() && !ordergoodsGoodsVo.getGoodsName().equals(""))
				|| (null != ordergoodsGoodsVo.getSku() && !ordergoodsGoodsVo.getSku().equals(""))
				|| (null != ordergoodsGoodsVo.getBrandName() && !ordergoodsGoodsVo.getBrandName().equals(""))
				|| (null != ordergoodsGoodsVo.getModel() && !ordergoodsGoodsVo.getModel().equals(""))
				|| (null != ordergoodsGoodsVo.getMaterialCode() && !ordergoodsGoodsVo.getMaterialCode().equals(""))
				|| (null != ordergoodsGoodsVo.getSearchContent() && !ordergoodsGoodsVo.getSearchContent().equals(""))){
			try{
				GoodsVo goodsVo = new GoodsVo();
				if(null != ordergoodsGoodsVo.getGoodsName() && !ordergoodsGoodsVo.getGoodsName().equals("")){
					goodsVo.setGoodsName(ordergoodsGoodsVo.getGoodsName());
				}
				if(null != ordergoodsGoodsVo.getSku() && !ordergoodsGoodsVo.getSku().equals("")){
					goodsVo.setSku(ordergoodsGoodsVo.getSku());
				}
				if(null != ordergoodsGoodsVo.getBrandName() && !ordergoodsGoodsVo.getBrandName().equals("")){
					goodsVo.setBrandName(ordergoodsGoodsVo.getBrandName());
				}
				if(null != ordergoodsGoodsVo.getModel() && !ordergoodsGoodsVo.getModel().equals("")){
					goodsVo.setModel(ordergoodsGoodsVo.getModel());
				}
				if(null != ordergoodsGoodsVo.getMaterialCode() && !ordergoodsGoodsVo.getMaterialCode().equals("")){
					goodsVo.setMaterialCode(ordergoodsGoodsVo.getMaterialCode());
				}
				if(null != ordergoodsGoodsVo.getSearchContent() && !ordergoodsGoodsVo.getSearchContent().equals("")){
					goodsVo.setSearchContent(ordergoodsGoodsVo.getSearchContent());
				}
				
				//只要审核通过的产品
				if(null != ordergoodsGoodsVo.getIsValid() && ordergoodsGoodsVo.getIsValid().equals(1)){
					goodsVo.setIsValid(1);
				}
				
				// 定义反序列化 数据格式
				final TypeReference<ResultInfo<List<Integer>>> TypeRef = new TypeReference<ResultInfo<List<Integer>>>() {};
				String url=httpUrl + "goods/getgoodsbyparams.htm";
				ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, goodsVo,clientId,clientKey, TypeRef);
				List<Integer> ids = (List<Integer>) result.getData();
				if(null == ids || ids.size() == 0){
					ids.add(-1);
				}
				ordergoodsGoodsVo.setGoodsIds(ids);
			}catch (Exception e) {
				logger.error(Contant.ERROR_MSG, e);
			}
		}
		
		map.put("ordergoodsGoodsVo", ordergoodsGoodsVo);
		map.put("page", page);
		List<OrdergoodsGoodsVo> list= ordergoodsGoodsMapper.getGoodsListPage(map);
		
		if(null != list && list.size() > 0){
			List<Integer> goodsIds = new ArrayList<>();
			for(OrdergoodsGoodsVo goodsVo : list){
				goodsIds.add(goodsVo.getGoodsId());
			}
				
			//产品信息补充
			try{
				// 定义反序列化 数据格式
				final TypeReference<ResultInfo<List<GoodsVo>>> TypeRef = new TypeReference<ResultInfo<List<GoodsVo>>>() {};
				String url=httpUrl + "goods/getgoodsbyidsNew.htm";
				ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, goodsIds,clientId,clientKey, TypeRef);
				List<GoodsVo> goodsVos = (List<GoodsVo>) result.getData();
				if(null != goodsVos && goodsVos.size() > 0){
					for(GoodsVo vo : goodsVos){
						for(OrdergoodsGoodsVo goodsVo : list){
							if(vo.getGoodsId().equals(goodsVo.getGoodsId())){
								goodsVo.setSku(vo.getSku());
								goodsVo.setGoodsName(vo.getGoodsName());
								goodsVo.setBrandName(vo.getBrandName());
								goodsVo.setModel(vo.getModel());
								goodsVo.setMaterialCode(vo.getMaterialCode());
								goodsVo.setIsOnSale(vo.getIsOnSale());
								goodsVo.setIsDiscard(vo.getIsDiscard());
								/*新商品流*/
								goodsVo.setSpec(vo.getSpec());
								goodsVo.setUnit(vo.getUnitName());
							}
						}
					}
				}
			}catch (Exception e) {
				logger.error(Contant.ERROR_MSG, e);
			}
		}
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("list", list);
		returnMap.put("page", page);
		return returnMap;
	}

	@Override
	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	public ResultInfo saveUplodeBatchGoods(List<OrdergoodsGoodsVo> list, HttpSession session) {
		ResultInfo resultInfo = new ResultInfo<>();
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();
		List<OrdergoodsGoodsVo> goodsList = new ArrayList<>();
		if(null != list && list.size() > 0){
			for(OrdergoodsGoodsVo goodsVo : list){
				try {
					//判断产品是否存在
					Goods goods = new Goods();
					goods.setSku(goodsVo.getSku());
					goods.setCompanyId(goodsVo.getCompanyId());
					// 定义反序列化 数据格式
					final TypeReference<ResultInfo<Goods>> TypeRef = new TypeReference<ResultInfo<Goods>>() {};
					String url=httpUrl + "goods/getgoodsbysku.htm";
					ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, goods,clientId,clientKey, TypeRef);
					Goods goodsInfo = (Goods) result.getData();
					
					if(null == goodsInfo){
						resultInfo.setMessage("订货号："+goodsVo.getSku() + "不存在！");
						throw new Exception("订货号："+goodsVo.getSku() + "不存在！");
					}
					
					goodsVo.setGoodsId(goodsInfo.getGoodsId());
					
					//判断是否存在产品
					OrdergoodsGoodsVo ordergoodsGoods = ordergoodsGoodsMapper.getOrdergoodsGoods(goodsVo);
					if(null != ordergoodsGoods){
						goodsVo.setOrdergoodsGoodsId(ordergoodsGoods.getOrdergoodsGoodsId());
						ordergoodsGoodsMapper.update(goodsVo);
					}else{//新增
						goodsVo.setAddTime(time);
						goodsVo.setCreator(user.getUserId());
						ordergoodsGoodsMapper.insert(goodsVo);
					}
					goodsList.add(goodsVo);
				} catch (Exception e) {
					logger.error(Contant.ERROR_MSG, e);
					return resultInfo;
				}
			}
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
			resultInfo.setListData(goodsList);
		}
		return resultInfo;
	}

	@Override
	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	public Boolean deleteOrdergoodsGoods(OrdergoodsGoodsVo ordergoodsGoodsVo) {
		//删除订货产品
		int res = ordergoodsGoodsMapper.deleteByPrimaryKey(ordergoodsGoodsVo.getOrdergoodsGoodsId());
		if(res>0){
			//删除订货产品分类关系
			ROrdergoodsGoodsJCategory rOrdergoodsGoodsJCategory = new ROrdergoodsGoodsJCategory();
			rOrdergoodsGoodsJCategory.setOrdergoodsGoodsId(ordergoodsGoodsVo.getOrdergoodsGoodsId());
			rOrdergoodsGoodsJCategoryMapper.delete(rOrdergoodsGoodsJCategory);
			return true;
		}
		return false;
	}

	@Override
	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	public Boolean deleteOrdergoodsCategoryGoods(OrdergoodsGoodsVo ordergoodsGoodsVo) {
		//删除订货产品分类关系
		ROrdergoodsGoodsJCategory rOrdergoodsGoodsJCategory = new ROrdergoodsGoodsJCategory();
		rOrdergoodsGoodsJCategory.setrOrdergoodsGoodsJCategoryId(ordergoodsGoodsVo.getrOrdergoodsGoodsJCategoryId());
		int res = rOrdergoodsGoodsJCategoryMapper.delete(rOrdergoodsGoodsJCategory);
		if(res > 0){
			//删除订货产品
			ordergoodsGoodsMapper.deleteByPrimaryKey(ordergoodsGoodsVo.getOrdergoodsGoodsId());
			return true;
		}
		return false;
	}

	@Override
	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	public Boolean saveBindCategoryGoods(Integer ordergoodsCategoryId, String ids) {
		if(null == ids || null == ordergoodsCategoryId || ids.equals("")){
			return false;
		}
		String[] idList = ids.split(",");
		for(String id : idList){
			//判断是否存在
			ROrdergoodsGoodsJCategory rOrdergoodsGoodsJCategory = new ROrdergoodsGoodsJCategory();
			rOrdergoodsGoodsJCategory.setOrdergoodsCategoryId(ordergoodsCategoryId);
			rOrdergoodsGoodsJCategory.setOrdergoodsGoodsId(Integer.parseInt(id));
			ROrdergoodsGoodsJCategory goodsJCategory = rOrdergoodsGoodsJCategoryMapper.getByROrdergoodsGoodsJCategory(rOrdergoodsGoodsJCategory);
			if(null == goodsJCategory){
				rOrdergoodsGoodsJCategory.setSort(100);
				rOrdergoodsGoodsJCategoryMapper.insert(rOrdergoodsGoodsJCategory);
			}
		}
		return true;
	}

	@Override
	public Boolean changSort(ROrdergoodsGoodsJCategory rOrdergoodsGoodsJCategory) {
		int update = rOrdergoodsGoodsJCategoryMapper.update(rOrdergoodsGoodsJCategory);
		if(update > 0){
			return true;
		}
		return false;
	}

	@Override
	public Map<String, Object> getGoodsCategoriesListPage(ROrdergoodsLaunchGoodsJCategoryVo rOrdergoodsLaunchGoodsJCategoryVo, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		//搜索条件
		if((null != rOrdergoodsLaunchGoodsJCategoryVo.getGoodsName() && !rOrdergoodsLaunchGoodsJCategoryVo.getGoodsName().equals(""))
				|| (null != rOrdergoodsLaunchGoodsJCategoryVo.getSku() && !rOrdergoodsLaunchGoodsJCategoryVo.getSku().equals(""))
				|| (null != rOrdergoodsLaunchGoodsJCategoryVo.getBrandName() && !rOrdergoodsLaunchGoodsJCategoryVo.getBrandName().equals(""))
				|| (null != rOrdergoodsLaunchGoodsJCategoryVo.getModel() && !rOrdergoodsLaunchGoodsJCategoryVo.getModel().equals(""))
				|| (null != rOrdergoodsLaunchGoodsJCategoryVo.getSearchContent() && !rOrdergoodsLaunchGoodsJCategoryVo.getSearchContent().equals(""))){
			try{
				GoodsVo goodsVo = new GoodsVo();
				if(null != rOrdergoodsLaunchGoodsJCategoryVo.getGoodsName() && !rOrdergoodsLaunchGoodsJCategoryVo.getGoodsName().equals("")){
					goodsVo.setGoodsName(rOrdergoodsLaunchGoodsJCategoryVo.getGoodsName());
				}
				if(null != rOrdergoodsLaunchGoodsJCategoryVo.getSku() && !rOrdergoodsLaunchGoodsJCategoryVo.getSku().equals("")){
					goodsVo.setSku(rOrdergoodsLaunchGoodsJCategoryVo.getSku());
				}
				if(null != rOrdergoodsLaunchGoodsJCategoryVo.getBrandName() && !rOrdergoodsLaunchGoodsJCategoryVo.getBrandName().equals("")){
					goodsVo.setBrandName(rOrdergoodsLaunchGoodsJCategoryVo.getBrandName());
				}
				if(null != rOrdergoodsLaunchGoodsJCategoryVo.getModel() && !rOrdergoodsLaunchGoodsJCategoryVo.getModel().equals("")){
					goodsVo.setModel(rOrdergoodsLaunchGoodsJCategoryVo.getModel());
				}
				if(null != rOrdergoodsLaunchGoodsJCategoryVo.getSearchContent() && !rOrdergoodsLaunchGoodsJCategoryVo.getSearchContent().equals("")){
					goodsVo.setSearchContent(rOrdergoodsLaunchGoodsJCategoryVo.getSearchContent());
				}
				// 定义反序列化 数据格式
				final TypeReference<ResultInfo<List<Integer>>> TypeRef = new TypeReference<ResultInfo<List<Integer>>>() {};
				String url=httpUrl + "goods/getgoodsbyparams.htm";
				ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, goodsVo,clientId,clientKey, TypeRef);
				List<Integer> ids = (List<Integer>) result.getData();
				if(null == ids || ids.size() == 0){
					ids.add(-1);
				}
				rOrdergoodsLaunchGoodsJCategoryVo.setGoodsIds(ids);
			}catch (Exception e) {
				logger.error(Contant.ERROR_MSG, e);
			}
		}
		
		map.put("rOrdergoodsLaunchGoodsJCategoryVo", rOrdergoodsLaunchGoodsJCategoryVo);
		map.put("page", page);
		List<ROrdergoodsLaunchGoodsJCategoryVo> list= rOrdergoodsLaunchGoodsJCategoryMapper.getGoodsCategoriesListPage(map);
		
		if(null != list && list.size() > 0){
			List<Integer> goodsIds = new ArrayList<>();
			for(ROrdergoodsLaunchGoodsJCategoryVo goodsVo : list){
				goodsIds.add(goodsVo.getGoodsId());
				String ordergoodsCategoryIds = goodsVo.getOrdergoodsCategoryIds();
				String[] idList = ordergoodsCategoryIds.split(",");
				List<Integer> categoryIds = new ArrayList<>();
				for(String id : idList){
					categoryIds.add(Integer.parseInt(id));
				}
				if(categoryIds.size() > 0){
					String categoryNames = ordergoodsCategoryMapper.getCategoryNamesByIds(categoryIds);
					goodsVo.setCategoryNames(categoryNames);
				}
			}
				
			//产品信息补充
			try{
				// 定义反序列化 数据格式
				final TypeReference<ResultInfo<List<GoodsVo>>> TypeRef = new TypeReference<ResultInfo<List<GoodsVo>>>() {};
				String url=httpUrl + "goods/getgoodsbyids.htm";
				ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, goodsIds,clientId,clientKey, TypeRef);
				List<GoodsVo> goodsVos = (List<GoodsVo>) result.getData();
				if(null != goodsVos && goodsVos.size() > 0){
					for(GoodsVo vo : goodsVos){
						for(ROrdergoodsLaunchGoodsJCategoryVo goodsVo : list){
							if(vo.getGoodsId().equals(goodsVo.getGoodsId())){
								goodsVo.setSku(vo.getSku());
								goodsVo.setGoodsName(vo.getGoodsName());
								goodsVo.setBrandName(vo.getBrandName());
								goodsVo.setModel(vo.getModel());
							}
						}
					}
				}
			}catch (Exception e) {
				logger.error(Contant.ERROR_MSG, e);
			}
		}
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("list", list);
		returnMap.put("page", page);
		return returnMap;
	}

	@Override
	public List<OrdergoodsCategory> getOrdergoodsCategry(Integer ordergoodsStoreId) {
		return ordergoodsCategoryMapper.getRootOrdergoodsCategory(ordergoodsStoreId);
	}

	@Override
	public ROrdergoodsLaunchGoodsJCategoryVo getSotreGoods(
			ROrdergoodsLaunchGoodsJCategoryVo rOrdergoodsLaunchGoodsJCategoryVo) {
		return rOrdergoodsLaunchGoodsJCategoryMapper.getSotreGoods(rOrdergoodsLaunchGoodsJCategoryVo);
	}

	@Override
	public Integer saveAddEquipmentCategory(ROrdergoodsLaunchGoodsJCategory rOrdergoodsLaunchGoodsJCategory) {
		return rOrdergoodsLaunchGoodsJCategoryMapper.insert(rOrdergoodsLaunchGoodsJCategory);
	}

	@Override
	public ROrdergoodsLaunchGoodsJCategoryVo getSotreGoodsInfo(
			ROrdergoodsLaunchGoodsJCategoryVo rOrdergoodsLaunchGoodsJCategoryVo) {
		ROrdergoodsLaunchGoodsJCategoryVo sotreGoods = rOrdergoodsLaunchGoodsJCategoryMapper.getSotreGoods(rOrdergoodsLaunchGoodsJCategoryVo);
		if(null != sotreGoods){
			List<Integer> goodsIds = new ArrayList<>();
			goodsIds.add(sotreGoods.getGoodsId());
			
			String[] idList = sotreGoods.getOrdergoodsCategoryIds().split(",");
			List<Integer> categoryIds = new ArrayList<>();
			for(String id : idList){
				if(!id.equals("")){
					categoryIds.add(Integer.parseInt(id));
				}
			}
			sotreGoods.setCategoryIds(categoryIds);
			
			//产品信息补充
			try{
				// 定义反序列化 数据格式
				final TypeReference<ResultInfo<List<GoodsVo>>> TypeRef = new TypeReference<ResultInfo<List<GoodsVo>>>() {};
				String url=httpUrl + "goods/getgoodsbyids.htm";
				ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, goodsIds,clientId,clientKey, TypeRef);
				List<GoodsVo> goodsVos = (List<GoodsVo>) result.getData();
				if(null != goodsVos && goodsVos.size() > 0){
					for(GoodsVo vo : goodsVos){
						if(vo.getGoodsId().equals(sotreGoods.getGoodsId())){
							sotreGoods.setSku(vo.getSku());
							sotreGoods.setGoodsName(vo.getGoodsName());
							sotreGoods.setBrandName(vo.getBrandName());
							sotreGoods.setModel(vo.getModel());
						}
					}
				}
			}catch (Exception e) {
				logger.error(Contant.ERROR_MSG, e);
			}
		}
		return sotreGoods;
	}

	@Override
	public Integer saveEditEquipmentCategory(ROrdergoodsLaunchGoodsJCategory rOrdergoodsLaunchGoodsJCategory) {
		return rOrdergoodsLaunchGoodsJCategoryMapper.update(rOrdergoodsLaunchGoodsJCategory);
	}

	@Override
	public Map<String, Object> getOrdergoodsLaunchListPage(OrdergoodsLaunchVo ordergoodsLaunchVo, Page page, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		
		//搜索条件
		if(null != ordergoodsLaunchVo.getTraderName() && !ordergoodsLaunchVo.getTraderName().equals("")){
			//客户
			try{
				Trader trader = new Trader();
				trader.setCompanyId(user.getCompanyId());
				trader.setTraderName(ordergoodsLaunchVo.getTraderName());
				
				// 定义反序列化 数据格式
				final TypeReference<ResultInfo<List<Integer>>> TypeRef = new TypeReference<ResultInfo<List<Integer>>>() {};
				String url=httpUrl + "tradercustomer/gettradercustomeridsbytradername.htm";
				ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, trader,clientId,clientKey, TypeRef);
				List<Integer> customerIds = (List<Integer>) result.getData();
				if(null == customerIds || customerIds.size() == 0){
					customerIds.add(-1);
				}
				ordergoodsLaunchVo.setTraderCustomerIds(customerIds);
			}catch (Exception e) {
				logger.error(Contant.ERROR_MSG, e);
			}
		}
		
		if((null != ordergoodsLaunchVo.getGoodsName() && !ordergoodsLaunchVo.getGoodsName().equals(""))
				|| (null != ordergoodsLaunchVo.getSku() && !ordergoodsLaunchVo.getSku().equals(""))
				|| (null != ordergoodsLaunchVo.getBrandName() && !ordergoodsLaunchVo.getBrandName().equals(""))
				|| (null != ordergoodsLaunchVo.getModel() && !ordergoodsLaunchVo.getModel().equals(""))){
			//产品
			try{
				GoodsVo goodsVo = new GoodsVo();
				if(null != ordergoodsLaunchVo.getGoodsName() && !ordergoodsLaunchVo.getGoodsName().equals("")){
					goodsVo.setGoodsName(ordergoodsLaunchVo.getGoodsName());
				}
				if(null != ordergoodsLaunchVo.getSku() && !ordergoodsLaunchVo.getSku().equals("")){
					goodsVo.setSku(ordergoodsLaunchVo.getSku());
				}
				if(null != ordergoodsLaunchVo.getBrandName() && !ordergoodsLaunchVo.getBrandName().equals("")){
					goodsVo.setBrandName(ordergoodsLaunchVo.getBrandName());
				}
				if(null != ordergoodsLaunchVo.getModel() && !ordergoodsLaunchVo.getModel().equals("")){
					goodsVo.setModel(ordergoodsLaunchVo.getModel());
				}
				// 定义反序列化 数据格式
				final TypeReference<ResultInfo<List<Integer>>> TypeRef = new TypeReference<ResultInfo<List<Integer>>>() {};
				String url=httpUrl + "goods/getgoodsbyparams.htm";
				ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, goodsVo,clientId,clientKey, TypeRef);
				List<Integer> ids = (List<Integer>) result.getData();
				if(null == ids || ids.size() == 0){
					ids.add(-1);
				}
				ordergoodsLaunchVo.setGoodsIds(ids);
			}catch (Exception e) {
				logger.error(Contant.ERROR_MSG, e);
			}
		}
		
		map.put("ordergoodsLaunchVo", ordergoodsLaunchVo);
		map.put("page", page);
		
		List<OrdergoodsLaunchVo> list = ordergoodsLaunchMapper.getOrdergoodsLaunchListPage(map);
		
		if(null != list && list.size() > 0){
			List<Integer> goodsIds = new ArrayList<>();
			List<Integer> traderCustomerIds = new ArrayList<>();
			for(OrdergoodsLaunchVo goodsVo : list){
				goodsIds.add(goodsVo.getGoodsId());
				traderCustomerIds.add(goodsVo.getTraderCustomerId());
				
				//完成额
				List<Integer> launchGoodsIds = this.getLaunchGoodsIds(goodsVo, session);
				
				List<Long> searchBegintimeList = new ArrayList<>();
				List<Long> searchEndtimeList = new ArrayList<>();
				searchBegintimeList.add(goodsVo.getStartTime());
				searchEndtimeList.add(goodsVo.getEndTime());
				
				SaleorderVo saleorderVo = new SaleorderVo();
				saleorderVo.setCompanyId(user.getCompanyId());
				saleorderVo.setTraderCustomerId(goodsVo.getTraderCustomerId());//客户
				saleorderVo.setGoodsIds(launchGoodsIds);//产品集合
				saleorderVo.setSearchBegintimeList(searchBegintimeList);
				saleorderVo.setSearchEndtimeList(searchEndtimeList);
				
				//订货订单搜索
				try{
					// 定义反序列化 数据格式
					final TypeReference<ResultInfo<SaleorderVo>> TypeRef = new TypeReference<ResultInfo<SaleorderVo>>() {};
					String url=httpUrl + "order/saleorder/getdhorderamount.htm";
					ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorderVo,clientId,clientKey, TypeRef);
					SaleorderVo saleorder = (SaleorderVo) result.getData();
					List<BigDecimal> monthAmountList = saleorder.getMonthAmountList();
					BigDecimal amount = new BigDecimal(0);
					if(null != monthAmountList && monthAmountList.size() > 0){
						for(BigDecimal a:monthAmountList){
							amount = amount.add(a);
						}
					}
					
					BigDecimal goalAmount = goodsVo.getGoalAmount();
					DecimalFormat df2 =new DecimalFormat("0.00"); 
					BigDecimal haveAmountPre = new BigDecimal(df2.format( amount.divide(goalAmount,2, BigDecimal.ROUND_HALF_UP)));
					goodsVo.setHaveAmountPre(haveAmountPre);
					
					
				}catch (Exception e) {
					logger.error(Contant.ERROR_MSG, e);
				}
			}
				
			//产品信息补充
			try{
				// 定义反序列化 数据格式
				final TypeReference<ResultInfo<List<GoodsVo>>> TypeRef = new TypeReference<ResultInfo<List<GoodsVo>>>() {};
				String url=httpUrl + "goods/getgoodsbyids.htm";
				ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, goodsIds,clientId,clientKey, TypeRef);
				List<GoodsVo> goodsVos = (List<GoodsVo>) result.getData();
				if(null != goodsVos && goodsVos.size() > 0){
					for(GoodsVo vo : goodsVos){
						for(OrdergoodsLaunchVo goodsVo : list){
							if(vo.getGoodsId().equals(goodsVo.getGoodsId())){
								goodsVo.setSku(vo.getSku());
								goodsVo.setGoodsName(vo.getGoodsName());
								goodsVo.setBrandName(vo.getBrandName());
								goodsVo.setModel(vo.getModel());
								goodsVo.setMaterialCode(vo.getMaterialCode());
							}
						}
					}
				}
				
				
				TraderVo traderVo = new TraderVo();
				traderVo.setCompanyId(user.getCompanyId());
				traderVo.setTraderCustomerIds(traderCustomerIds);
				traderVo.setTraderType(ErpConst.ONE);
				
				
				// 定义反序列化 数据格式
				final TypeReference<ResultInfo<List<TraderVo>>> TypeRef2 = new TypeReference<ResultInfo<List<TraderVo>>>() {
				};
				String url2=httpUrl + "trader/gettraderbaseinfoforlaunch.htm";
				ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url2,traderVo, clientId, clientKey, TypeRef2);
				List<TraderVo> list2 = (List<TraderVo>) result2.getData();
				if(null != list2 && list2.size() > 0){
					for(TraderVo tvo : list2){
						for(OrdergoodsLaunchVo goodsVo : list){
							if(tvo.getTraderCustomerId().equals(goodsVo.getTraderCustomerId())){
								goodsVo.setTraderName(tvo.getTraderName());
							}
						}
					}
				}
			}catch (Exception e) {
				logger.error(Contant.ERROR_MSG, e);
			}
			
			
		}
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("list", list);
		returnMap.put("page", page);
		return returnMap;
	}

	@Override
	public Map<String, Object> getTraderCustomerListPage(TraderVo traderVo, Page page) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<TraderVo>>> TypeRef = new TypeReference<ResultInfo<List<TraderVo>>>() {
		};
		try {
			String url=httpUrl + "trader/gettraderbaseinfolistpage.htm";
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url,traderVo, clientId, clientKey, TypeRef, page);
			if (result == null || result.getCode() == -1) {
				return null;
			}
			List<TraderVo> list = (List<TraderVo>) result.getData();
			if(null != list && list.size() > 0){
				List<Integer> traderIdList = new ArrayList<>();
				for (TraderVo tcv : list) {
					traderIdList.add(tcv.getTraderId());
					
					if(null != tcv.getAreaId() && tcv.getAreaId() > 0){
						tcv.setArea(getAddressByAreaId(tcv.getAreaId()));
					}
				}
				if(traderIdList.size() > 0){
					List<User> userList = userMapper.getUserByTraderIdList(traderIdList,ErpConst.ONE);
					if(null != userList && userList.size() > 0){
						for(User user : userList){
							for(TraderVo tcv : list){
								if(user.getTraderId().equals(tcv.getTraderId())){
									tcv.setOwner(user.getUsername());
								}
							}
						}
					}
				}
			}
			page = result.getPage();
			Map<String, Object> map = new HashMap<>();
			map.put("list", list);
			map.put("page", page);
			return map;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	public Integer saveAddOrdergoodsLaunch(OrdergoodsLaunch ordergoodsLaunch, String[] startdate, String[] enddate,
			String[] goal, HttpSession session) {
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();
		
		ordergoodsLaunch.setCreator(user.getUserId());
		ordergoodsLaunch.setAddTime(time);
		ordergoodsLaunch.setModTime(time);
		ordergoodsLaunch.setUpdater(user.getUserId());
		//投放主表
		Integer res = ordergoodsLaunchMapper.insert(ordergoodsLaunch);
		if(res > 0){
			Integer ordergoodsLaunchId = ordergoodsLaunch.getOrdergoodsLaunchId();
			//投放时间
			for(int i= 0;i<startdate.length;i++){
				OrdergoodsLaunchGoal ordergoodsLaunchGoal = new OrdergoodsLaunchGoal();
				ordergoodsLaunchGoal.setOrdergoodsLaunchId(ordergoodsLaunchId);
				ordergoodsLaunchGoal.setStartTime(DateUtil.convertLong(startdate[i], "yyyy-MM-dd"));
				ordergoodsLaunchGoal.setEndTime(DateUtil.convertLong(enddate[i]+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));
				ordergoodsLaunchGoal.setGoalAmount(new BigDecimal(goal[i]));
				
				ordergoodsLaunchGoalMapper.insert(ordergoodsLaunchGoal);
			}
			return ordergoodsLaunchId;
		}
		return 0;
	}

	@Override
	public OrdergoodsLaunchVo getOrdergoodsLaunch(OrdergoodsLaunch ordergoodsLaunch,HttpSession session) {
		OrdergoodsLaunchVo launch = ordergoodsLaunchMapper.selectByPrimaryKey(ordergoodsLaunch.getOrdergoodsLaunchId());
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		if(null != launch){
			//月度额度
			BigDecimal goalAmount = launch.getGoalAmount();
			DecimalFormat df2 =new DecimalFormat("0.00"); 
			BigDecimal monthAmount = new BigDecimal(df2.format( goalAmount.divide(new BigDecimal(12),2, BigDecimal.ROUND_HALF_UP)));
			launch.setMonthAmount(monthAmount);
			
			List<Integer> traderCustomerIds = new ArrayList<>();
			traderCustomerIds.add(launch.getTraderCustomerId());
			TraderVo traderVo = new TraderVo();
			traderVo.setCompanyId(user.getCompanyId());
			traderVo.setTraderCustomerIds(traderCustomerIds);
			traderVo.setTraderType(ErpConst.ONE);
			try{
				// 定义反序列化 数据格式
				final TypeReference<ResultInfo<List<TraderVo>>> TypeRef2 = new TypeReference<ResultInfo<List<TraderVo>>>() {
				};
				String url2=httpUrl + "trader/gettraderbaseinfoforlaunch.htm";
				ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url2,traderVo, clientId, clientKey, TypeRef2);
				List<TraderVo> list2 = (List<TraderVo>) result2.getData();
				if(null != list2 && list2.size() > 0){
					for(TraderVo tvo : list2){
						if(tvo.getTraderCustomerId().equals(launch.getTraderCustomerId())){
							launch.setTraderName(tvo.getTraderName());
						}
					}
				}
				
				//产品信息
				
				List<Integer> goodsIds = new ArrayList<>();
				goodsIds.add(launch.getGoodsId());
					
				//产品信息补充
				// 定义反序列化 数据格式
				final TypeReference<ResultInfo<List<GoodsVo>>> TypeRef = new TypeReference<ResultInfo<List<GoodsVo>>>() {};
				String url=httpUrl + "goods/getgoodsbyids.htm";
				ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, goodsIds,clientId,clientKey, TypeRef);
				List<GoodsVo> goodsVos = (List<GoodsVo>) result.getData();
				if(null != goodsVos && goodsVos.size() > 0){
					for(GoodsVo vo : goodsVos){
						if(vo.getGoodsId().equals(launch.getGoodsId())){
							launch.setSku(vo.getSku());
							launch.setGoodsName(vo.getGoodsName());
							launch.setBrandName(vo.getBrandName());
							launch.setModel(vo.getModel());
							launch.setMaterialCode(vo.getMaterialCode());
						}
					}
				}
				
				String ordergoodsCategoryIds = launch.getOrdergoodsCategoryIds();
				String[] idList = ordergoodsCategoryIds.split(",");
				List<Integer> categoryIds = new ArrayList<>();
				for(String id : idList){
					categoryIds.add(Integer.parseInt(id));
				}
				if(categoryIds.size() > 0){
					String categoryNames = ordergoodsCategoryMapper.getCategoryNamesByIds(categoryIds);
					launch.setCategoryNames(categoryNames);
				}
			}catch (Exception e) {
				logger.error(Contant.ERROR_MSG, e);
			}
		}
		return launch;
	}

	@Override
	public List<OrdergoodsLaunchGoal> getOrdergoodsLaunchGoal(OrdergoodsLaunch ordergoodsLaunch) {
		return ordergoodsLaunchGoalMapper.getOrdergoodsLaunchGoal(ordergoodsLaunch);
	}

	@Override
	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	public Boolean saveEditOrdergoodsLaunch(OrdergoodsLaunch ordergoodsLaunch, String[] startdate, String[] enddate,
			String[] goal, HttpSession session) {
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();
		
		ordergoodsLaunch.setModTime(time);
		ordergoodsLaunch.setUpdater(user.getUserId());
		//投放主表
		Integer res = ordergoodsLaunchMapper.updateByPrimaryKeySelective(ordergoodsLaunch);
		if(res > 0){
			//删除投放目标
			ordergoodsLaunchGoalMapper.deleteByOrdergoodsLaunchId(ordergoodsLaunch.getOrdergoodsLaunchId());
			for(int i= 0;i<startdate.length;i++){
				OrdergoodsLaunchGoal ordergoodsLaunchGoal = new OrdergoodsLaunchGoal();
				ordergoodsLaunchGoal.setOrdergoodsLaunchId(ordergoodsLaunch.getOrdergoodsLaunchId());
				ordergoodsLaunchGoal.setStartTime(DateUtil.convertLong(startdate[i], "yyyy-MM-dd"));
				ordergoodsLaunchGoal.setEndTime(DateUtil.convertLong(enddate[i]+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));
				ordergoodsLaunchGoal.setGoalAmount(new BigDecimal(goal[i]));
				
				ordergoodsLaunchGoalMapper.insert(ordergoodsLaunchGoal);
			}
			return true;
		}
		return false;
	}

	@Override
	public List<Saleorder> getLaunchSaleorderListPage(OrdergoodsLaunchVo ordergoodsLaunchVo, Page page,HttpSession session) {
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		List<Integer> goodsIds = this.getLaunchGoodsIds(ordergoodsLaunchVo, session);
		if(goodsIds != null && goodsIds.size() > 0){
			SaleorderVo saleorderVo = new SaleorderVo();
			saleorderVo.setCompanyId(user.getCompanyId());
			saleorderVo.setTraderCustomerId(ordergoodsLaunchVo.getTraderCustomerId());//客户
			saleorderVo.setGoodsIds(goodsIds);//产品集合
			saleorderVo.setSearchBegintime(ordergoodsLaunchVo.getStartTime());//开始时间
			saleorderVo.setSearchEndtime(ordergoodsLaunchVo.getEndTime());//结束时间
			
			//订货订单搜索
			try{
				// 定义反序列化 数据格式
				final TypeReference<ResultInfo<List<Saleorder>>> TypeRef = new TypeReference<ResultInfo<List<Saleorder>>>() {};
				String url=httpUrl + "order/saleorder/getdhorderbaseinfolistpage.htm";
				ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorderVo,clientId,clientKey, TypeRef,page);
				List<Saleorder> saleorderList = (List<Saleorder>) result.getData();
				return saleorderList;
			}catch (Exception e) {
				logger.error(Contant.ERROR_MSG, e);
			}
		}
		
		return null;
	}

	@Override
	public OrdergoodsLaunchVo getLaunchMonthGoal(OrdergoodsLaunchVo ordergoodsLaunchVo, HttpSession session) {
		List<Integer> goodsIds = this.getLaunchGoodsIds(ordergoodsLaunchVo, session);
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		if(goodsIds != null && goodsIds.size() > 0){
			String startTime = DateUtil.convertString(ordergoodsLaunchVo.getStartTime(),"yyyy-MM-dd");
			String endTime = DateUtil.convertString(ordergoodsLaunchVo.getEndTime(),"yyyy-MM-dd HH:mm:ss");
			List<Long> searchBegintimeList = new  ArrayList<>();
			List<Long> searchEndtimeList = new  ArrayList<>();
			for(Integer i=0;i<60;i++){
				Calendar cal_1 = Calendar.getInstance();// 获取当前日期
				cal_1.setTime(DateUtil.StringToDate(startTime));
				cal_1.add(Calendar.MONTH, i);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				String format2 = format.format(cal_1.getTime());
				searchBegintimeList.add(DateUtil.convertLong(format2, "yyyy-MM-dd"));
//				System.out.println(format2);
			}
			
			for(Integer i=59;i>=0;i--){
				try {
					Date res;
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					res = simpleDateFormat.parse(endTime);
					Calendar cal_1 = Calendar.getInstance();// 获取当前日期
					cal_1.setTime(res);
					cal_1.add(Calendar.MONTH, -i);
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String format2 = format.format(cal_1.getTime());
					searchEndtimeList.add(DateUtil.convertLong(format2, "yyyy-MM-dd HH:mm:ss"));
//					System.out.println(format2);
				} catch (ParseException e) {
					logger.error(Contant.ERROR_MSG, e);
				}
			}
			SaleorderVo saleorderVo = new SaleorderVo();
			saleorderVo.setCompanyId(user.getCompanyId());
			saleorderVo.setTraderCustomerId(ordergoodsLaunchVo.getTraderCustomerId());//客户
			saleorderVo.setGoodsIds(goodsIds);//产品集合
			saleorderVo.setSearchBegintimeList(searchBegintimeList);
			saleorderVo.setSearchEndtimeList(searchEndtimeList);
			
			//订货订单搜索
			try{
				// 定义反序列化 数据格式
				final TypeReference<ResultInfo<SaleorderVo>> TypeRef = new TypeReference<ResultInfo<SaleorderVo>>() {};
				String url=httpUrl + "order/saleorder/getdhorderamount.htm";
				ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorderVo,clientId,clientKey, TypeRef);
				SaleorderVo saleorder = (SaleorderVo) result.getData();
				ordergoodsLaunchVo.setMonthAmountList(saleorder.getMonthAmountList());
				
				return ordergoodsLaunchVo;
			}catch (Exception e) {
				logger.error(Contant.ERROR_MSG, e);
			}
		}
		
		return new OrdergoodsLaunchVo();
	}

	private List<Integer> getLaunchGoodsIds(OrdergoodsLaunchVo ordergoodsLaunchVo, HttpSession session){
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		//设备相关产品ID集合
		String ordergoodsCategoryIds = ordergoodsLaunchVo.getOrdergoodsCategoryIds();
		String[] idList = ordergoodsCategoryIds.split(",");
		List<Integer> categoryIds = new ArrayList<>();
		for(String id : idList){
			categoryIds.add(Integer.parseInt(id));
			//查询子集
			List<OrdergoodsCategory> ordergoodsCategory = ordergoodsCategoryMapper.getOrdergoodsCategory(Integer.parseInt(id), ordergoodsLaunchVo.getOrdergoodsStoreId());
			if(null != ordergoodsCategory && ordergoodsCategory.size() > 0){
				for(OrdergoodsCategory cate : ordergoodsCategory){
					categoryIds.add(cate.getOrdergoodsCategoryId());
				}
			}
		}
		List<Integer> goodsIds = rOrdergoodsGoodsJCategoryMapper.getGoodsIdsByCategoryIds(categoryIds,ordergoodsLaunchVo.getOrdergoodsStoreId());
		
		return goodsIds;
	}

	@Override
	public Map<String, Object> getOrdergoodsAccountListPage(OrdergoodsStoreAccountVo ordergoodsStoreAccountVo,
			Page page, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		//搜索
		if((ordergoodsStoreAccountVo.getTraderName() != null && !ordergoodsStoreAccountVo.getTraderName().equals(""))
				||(ordergoodsStoreAccountVo.getMobile() != null && !ordergoodsStoreAccountVo.getMobile().equals(""))
				||(ordergoodsStoreAccountVo.getAreaId() != null && ordergoodsStoreAccountVo.getAreaId() > 0)
				){
			TraderCustomerVo customerVo = new TraderCustomerVo();
			if((ordergoodsStoreAccountVo.getTraderName() != null && !ordergoodsStoreAccountVo.getTraderName().equals(""))){
				customerVo.setTraderName(ordergoodsStoreAccountVo.getTraderName());
			}
			if(ordergoodsStoreAccountVo.getMobile() != null && !ordergoodsStoreAccountVo.getMobile().equals("")){
				customerVo.setPhone(ordergoodsStoreAccountVo.getMobile());
			}
			if(ordergoodsStoreAccountVo.getAreaId() != null && ordergoodsStoreAccountVo.getAreaId() > 0){
				customerVo.setAreaId(ordergoodsStoreAccountVo.getAreaId());
			}
			List<Integer> traderContactIds = new ArrayList<>();
			List<Integer> contactIds = ordergoodsStoreAccountMapper.getTraderCotactIds(ordergoodsStoreAccountVo.getOrdergoodsStoreId());
			if(contactIds != null && contactIds.size()>0){
				customerVo.setTraderList(contactIds);
				try{
					// 定义反序列化 数据格式
					final TypeReference<ResultInfo<List<Integer>>> TypeRef = new TypeReference<ResultInfo<List<Integer>>>() {};
					String url=httpUrl + "tradercustomer/getdhtraderContactids.htm";
					ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, customerVo,clientId,clientKey, TypeRef);
					traderContactIds = (List<Integer>) result.getData();
					if(null == traderContactIds || traderContactIds.size() == 0){
						traderContactIds.add(-1);
					}
				}catch (Exception e) {
					logger.error(Contant.ERROR_MSG, e);
				}
			}else{
				traderContactIds.add(-1);
			}
			ordergoodsStoreAccountVo.setTraderContactIds(traderContactIds);
		}
		
		
		map.put("ordergoodsStoreAccountVo", ordergoodsStoreAccountVo);
		map.put("page", page);
		
		List<OrdergoodsStoreAccountVo> list = ordergoodsStoreAccountMapper.getOrdergoodsAccountListPage(map);
		
		//信息补充
		if(null != list && list.size() > 0){
			List<Integer> ids = new ArrayList<>();
			for(OrdergoodsStoreAccountVo vo :list){
				ids.add(vo.getTraderContactId());
			}
			try{
				// 定义反序列化 数据格式
				final TypeReference<ResultInfo<List<TraderCustomerVo>>> TypeRef = new TypeReference<ResultInfo<List<TraderCustomerVo>>>() {};
				String url=httpUrl + "tradercustomer/getdhtraderinfo.htm";
				ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, ids,clientId,clientKey, TypeRef);
				List<TraderCustomerVo> customerList = (List<TraderCustomerVo>) result.getData();
				if(null != customerList && customerList.size() > 0){
					for(TraderCustomerVo customer : customerList){
						for(OrdergoodsStoreAccountVo vo :list){
							if(customer.getTraderContactId().equals(vo.getTraderContactId())){
								vo.setTraderName(customer.getTraderName());
								vo.setArea((String)regionService.getRegion(customer.getAreaId(), 2));
								vo.setMobile(customer.getPhone());
								User user = userMapper.getUserByTraderId(customer.getTraderId(), ErpConst.ONE);
								if(null != user){
									vo.setOwner(user.getUsername());
								}
							}
						}
					}
				}
			}catch (Exception e) {
				logger.error(Contant.ERROR_MSG, e);
			}
		}
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("list", list);
		returnMap.put("page", page);
		return returnMap;
	}

	@Override
	public Integer saveAddOrdergoodsAccount(OrdergoodsStoreAccount ordergoodsStoreAccount, HttpSession session) {
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();
		
		ordergoodsStoreAccount.setIsEnable(ErpConst.ONE);
		ordergoodsStoreAccount.setCreator(user.getUserId());
		ordergoodsStoreAccount.setAddTime(time);
		ordergoodsStoreAccount.setModTime(time);
		ordergoodsStoreAccount.setUpdater(user.getUserId());
		Integer suc = ordergoodsStoreAccountMapper.insert(ordergoodsStoreAccount);
		if(suc > 0){
			return ordergoodsStoreAccount.getOrdergoodsStoreAccountId();
		}
		return 0;
	}

	@Override
	public Map<String, Object> getOrdergoodsAccount(OrdergoodsStoreAccount ordergoodsStoreAccount) {
		Map<String, Object> returnMap = new HashMap<>();
		OrdergoodsStoreAccountVo accountVo = ordergoodsStoreAccountMapper.selectByPrimaryKey(ordergoodsStoreAccount.getOrdergoodsStoreAccountId());
		
		TraderCustomerVo customerVo = null;
		TraderAddressVo tav = null;
		TraderFinanceVo financeVo = null;
		if(null != accountVo){
			try{
				TraderCustomerVo traderCustomerVo = new TraderCustomerVo();
				traderCustomerVo.setTraderAddressId(accountVo.getTraderAddressId());
				traderCustomerVo.setTraderContactId(accountVo.getTraderContactId());
				// 定义反序列化 数据格式
				final TypeReference<ResultInfo<Map<String, Object>>> TypeRef = new TypeReference<ResultInfo<Map<String, Object>>>() {};
				String url=httpUrl + "tradercustomer/getordergoodsstoreaccountinfo.htm";
				ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, traderCustomerVo,clientId,clientKey, TypeRef);
				Map<String, Object> map = (Map<String, Object>) result.getData();
				
				if(map.containsKey("customer")){
					net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(map.get("customer"));
					customerVo = (TraderCustomerVo) json.toBean(json, TraderCustomerVo.class);
					if(null != customerVo){
						accountVo.setMobile(customerVo.getPhone());
						User user = userMapper.getUserByTraderId(customerVo.getTraderId(), ErpConst.ONE);
						if(null != user){
							accountVo.setOwner(user.getUsername());
						}
						accountVo.setContact(customerVo.getName());
					}
					customerVo.setArea((String)regionService.getRegion(customerVo.getAreaId(), 2));
				}
				
				if(map.containsKey("finance")){
					net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(map.get("finance"));
					financeVo = (TraderFinanceVo) json.toBean(json, TraderFinanceVo.class);
				}
								
				if(map.containsKey("address")){
					net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(map.get("address"));
					TraderAddress traderAddress = (TraderAddress) json.toBean(json, TraderAddress.class);
					tav = new TraderAddressVo();
					tav.setTraderAddress(traderAddress);
					tav.setArea(getAddressByAreaId(traderAddress.getAreaId()));
				}
			}catch (Exception e) {
				logger.error(Contant.ERROR_MSG, e);
			}
		}
		returnMap.put("account", accountVo);
		returnMap.put("customer", customerVo);
		returnMap.put("finance", financeVo);
		returnMap.put("address", tav);
		
		return returnMap;
	}

	@Override
	public Map<String, Object> getEditOrdergoodsAccount(OrdergoodsStoreAccount ordergoodsStoreAccount) {
		Map<String, Object> returnMap = new HashMap<>();
		OrdergoodsStoreAccountVo accountVo = ordergoodsStoreAccountMapper.selectByPrimaryKey(ordergoodsStoreAccount.getOrdergoodsStoreAccountId());
		TraderCustomerVo customerVo = null;
		List<TraderAddressVo> addressList = new ArrayList<>();
		List<TraderContact> contactlist = new ArrayList<>();
		if(null != accountVo){
			try{
				TraderCustomerVo traderCustomerVo = new TraderCustomerVo();
				traderCustomerVo.setTraderAddressId(accountVo.getTraderAddressId());
				traderCustomerVo.setTraderContactId(accountVo.getTraderContactId());
				// 定义反序列化 数据格式
				final TypeReference<ResultInfo<Map<String, Object>>> TypeRef = new TypeReference<ResultInfo<Map<String, Object>>>() {};
				String url=httpUrl + "tradercustomer/geteditordergoodsstoreaccountinfo.htm";
				ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, traderCustomerVo,clientId,clientKey, TypeRef);
				Map<String, Object> map = (Map<String, Object>) result.getData();
				
				if(map.containsKey("customer")){
					net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(map.get("customer"));
					customerVo = (TraderCustomerVo) json.toBean(json, TraderCustomerVo.class);
					if(null != customerVo){
						accountVo.setMobile(customerVo.getPhone());
						User user = userMapper.getUserByTraderId(customerVo.getTraderId(), ErpConst.ONE);
						if(null != user){
							accountVo.setOwner(user.getUsername());
						}
						accountVo.setContact(customerVo.getName());
					}
					customerVo.setArea((String)regionService.getRegion(customerVo.getAreaId(), 2));
				}
				
				if(map.containsKey("contact")){
					net.sf.json.JSONArray json = net.sf.json.JSONArray.fromObject(map.get("contact"));
					contactlist = (List<TraderContact>) json.toCollection(json, TraderContact.class);
				}
								
				if(map.containsKey("address")){
					net.sf.json.JSONArray json = net.sf.json.JSONArray.fromObject(map.get("address"));
					List<TraderAddress> traderAddressList = (List<TraderAddress>) json.toCollection(json, TraderAddress.class);
					if(null != traderAddressList && traderAddressList.size() > 0){
						for(TraderAddress address : traderAddressList){
							TraderAddressVo tav = new TraderAddressVo();
							tav.setTraderAddress(address);
							tav.setArea(getAddressByAreaId(address.getAreaId()));
							addressList.add(tav);
						}
					}
				}
			}catch (Exception e) {
				logger.error(Contant.ERROR_MSG, e);
			}
		}
		returnMap.put("account", accountVo);
		returnMap.put("customer", customerVo);
		returnMap.put("contact", contactlist);
		returnMap.put("address", addressList);
		
		return returnMap;
	}

	@Override
	public Integer saveEditOrdergoodsAccount(OrdergoodsStoreAccount ordergoodsStoreAccount, HttpSession session) {
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();
		
		ordergoodsStoreAccount.setModTime(time);
		ordergoodsStoreAccount.setUpdater(user.getUserId());
		return ordergoodsStoreAccountMapper.updateByPrimaryKey(ordergoodsStoreAccount);
	}

	@Override
	public Boolean changeAccountEnable(OrdergoodsStoreAccount ordergoodsStoreAccount, HttpSession session) {
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();
		OrdergoodsStoreAccountVo accountVo = ordergoodsStoreAccountMapper.selectByPrimaryKey(ordergoodsStoreAccount.getOrdergoodsStoreAccountId());
		
		if(null == accountVo){
			return false;
		}
		accountVo.setModTime(time);
		accountVo.setUpdater(user.getUserId());
		Integer isEnable = 0;
		if(accountVo.getIsEnable().equals(0)){
			isEnable = 1;
		}
		if(accountVo.getIsEnable().equals(1)){
			isEnable = 0;
		}
		accountVo.setIsEnable(isEnable);
		
		int suc = ordergoodsStoreAccountMapper.updateByPrimaryKey(accountVo);
		if(suc > 0){
			return true;
		}
		return false;
	}

	@Override
	public ROrdergoodsGoodsJCategory getOrdergoodsCategoryGoods(OrdergoodsGoodsVo ordergoodsGoodsVo) {
		ROrdergoodsGoodsJCategory rOrdergoodsGoodsJCategory = new ROrdergoodsGoodsJCategory();
		rOrdergoodsGoodsJCategory.setrOrdergoodsGoodsJCategoryId(ordergoodsGoodsVo.getrOrdergoodsGoodsJCategoryId());
		return rOrdergoodsGoodsJCategoryMapper.getById(rOrdergoodsGoodsJCategory);
	}

	@Override
	public OrdergoodsStoreAccount getOrdergoodsStoreAccount(OrdergoodsStoreAccount ordergoodsStoreAccount) {
		return ordergoodsStoreAccountMapper.getOrdergoodsStoreAccount(ordergoodsStoreAccount);
	}

	@Override
	public OrdergoodsStoreAccount getOrdergoodsStoreAccountByPrimaryKey(OrdergoodsStoreAccount ordergoodsStoreAccount) {
		return ordergoodsStoreAccountMapper.selectByPrimaryKey(ordergoodsStoreAccount.getOrdergoodsStoreAccountId());
	}

	@Override
	public Map<String, Object> getAccountGoodsListPage(OrdergoodsGoodsAccountVo ordergoodsGoodsAccountVo, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		//搜索条件
		if((null != ordergoodsGoodsAccountVo.getGoodsName() && !ordergoodsGoodsAccountVo.getGoodsName().equals(""))
				|| (null != ordergoodsGoodsAccountVo.getSku() && !ordergoodsGoodsAccountVo.getSku().equals(""))
				|| (null != ordergoodsGoodsAccountVo.getBrandName() && !ordergoodsGoodsAccountVo.getBrandName().equals(""))
				|| (null != ordergoodsGoodsAccountVo.getModel() && !ordergoodsGoodsAccountVo.getModel().equals(""))){
			try{
				GoodsVo goodsVo = new GoodsVo();
				if(null != ordergoodsGoodsAccountVo.getGoodsName() && !ordergoodsGoodsAccountVo.getGoodsName().equals("")){
					goodsVo.setGoodsName(ordergoodsGoodsAccountVo.getGoodsName());
				}
				if(null != ordergoodsGoodsAccountVo.getSku() && !ordergoodsGoodsAccountVo.getSku().equals("")){
					goodsVo.setSku(ordergoodsGoodsAccountVo.getSku());
				}
				if(null != ordergoodsGoodsAccountVo.getBrandName() && !ordergoodsGoodsAccountVo.getBrandName().equals("")){
					goodsVo.setBrandName(ordergoodsGoodsAccountVo.getBrandName());
				}
				if(null != ordergoodsGoodsAccountVo.getModel() && !ordergoodsGoodsAccountVo.getModel().equals("")){
					goodsVo.setModel(ordergoodsGoodsAccountVo.getModel());
				}
				// 定义反序列化 数据格式
				final TypeReference<ResultInfo<List<Integer>>> TypeRef = new TypeReference<ResultInfo<List<Integer>>>() {};
				String url=httpUrl + "goods/getgoodsbyparams.htm";
				ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, goodsVo,clientId,clientKey, TypeRef);
				List<Integer> ids = (List<Integer>) result.getData();
				if(null == ids || ids.size() == 0){
					ids.add(-1);
				}
				ordergoodsGoodsAccountVo.setGoodsIds(ids);
			}catch (Exception e) {
				logger.error(Contant.ERROR_MSG, e);
			}
		}
		
		map.put("ordergoodsGoodsAccountVo", ordergoodsGoodsAccountVo);
		map.put("page", page);
		List<OrdergoodsGoodsAccountVo> list= ordergoodsGoodsAccountMapper.getAccountGoodsListPage(map);
		
		if(null != list && list.size() > 0){
			List<Integer> goodsIds = new ArrayList<>();
			for(OrdergoodsGoodsAccountVo goodsVo : list){
				goodsIds.add(goodsVo.getGoodsId());
			}
				
			//产品信息补充
			try{
				// 定义反序列化 数据格式
				final TypeReference<ResultInfo<List<GoodsVo>>> TypeRef = new TypeReference<ResultInfo<List<GoodsVo>>>() {};
				String url=httpUrl + "goods/getgoodsbyids.htm";
				ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, goodsIds,clientId,clientKey, TypeRef);
				List<GoodsVo> goodsVos = (List<GoodsVo>) result.getData();
				if(null != goodsVos && goodsVos.size() > 0){
					for(GoodsVo vo : goodsVos){
						for(OrdergoodsGoodsAccountVo goodsVo : list){
							if(vo.getGoodsId().equals(goodsVo.getGoodsId())){
								goodsVo.setSku(vo.getSku());
								goodsVo.setGoodsName(vo.getGoodsName());
								goodsVo.setBrandName(vo.getBrandName());
								goodsVo.setModel(vo.getModel());
							}
						}
					}
				}
			}catch (Exception e) {
				logger.error(Contant.ERROR_MSG, e);
			}
		}
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("list", list);
		returnMap.put("page", page);
		return returnMap;
	}

	@Override
	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	public ResultInfo<?> saveUplodeBatchAccountGoods(List<OrdergoodsGoodsAccountVo> list, HttpSession session) {
		ResultInfo resultInfo = new ResultInfo<>();
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();
		List<OrdergoodsGoodsAccountVo> goodsList = new ArrayList<>();
		if(null != list && list.size() > 0){
			for(OrdergoodsGoodsAccountVo goodsVo : list){
				try {
					//判断产品是否存在
					Goods goods = new Goods();
					goods.setSku(goodsVo.getSku());
					goods.setCompanyId(goodsVo.getCompanyId());
					// 定义反序列化 数据格式
					final TypeReference<ResultInfo<Goods>> TypeRef = new TypeReference<ResultInfo<Goods>>() {};
					String url=httpUrl + "goods/getgoodsbysku.htm";
					ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, goods,clientId,clientKey, TypeRef);
					Goods goodsInfo = (Goods) result.getData();
					
					if(null == goodsInfo){
						resultInfo.setMessage("订货号："+goodsVo.getSku() + "不存在！");
						throw new Exception("订货号："+goodsVo.getSku() + "不存在！");
					}
					
					goodsVo.setGoodsId(goodsInfo.getGoodsId());
					
					OrdergoodsGoodsVo ordergoodsGoodsVo = new OrdergoodsGoodsVo(); 
					ordergoodsGoodsVo.setGoodsId(goodsVo.getGoodsId());
					ordergoodsGoodsVo.setOrdergoodsStoreId(goodsVo.getOrdergoodsStoreId());
					
					OrdergoodsGoodsVo vo = ordergoodsGoodsMapper.getOrdergoodsGoods(ordergoodsGoodsVo);
					
					if(null == vo){
						resultInfo.setMessage("请先添加订货号："+goodsVo.getSku() + "产品！");
						throw new Exception("请先添加订货号："+goodsVo.getSku() + "产品！");
					}
					
					//判断是否存在产品
					OrdergoodsGoodsAccountVo ordergoodsAccountGoods = ordergoodsGoodsAccountMapper.getOrdergoodsAccountGoods(goodsVo);
					if(null != ordergoodsAccountGoods){
						goodsVo.setOrdergoodsGoodsAccountId(ordergoodsAccountGoods.getOrdergoodsGoodsAccountId());
						ordergoodsGoodsAccountMapper.update(goodsVo);
					}else{//新增
						goodsVo.setAddTime(time);
						goodsVo.setCreator(user.getUserId());
						ordergoodsGoodsAccountMapper.insert(goodsVo);
					}
					goodsList.add(goodsVo);
				} catch (Exception e) {
					logger.error(Contant.ERROR_MSG, e);
					return resultInfo;
				}
			}
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
			resultInfo.setListData(goodsList);
		}
		return resultInfo;
	}

	@Override
	public Boolean deleteOrdergoodsAccountGoods(OrdergoodsGoodsAccountVo ordergoodsGoodsAccountVo) {
		//删除订货产品
		int res = ordergoodsGoodsAccountMapper.deleteByPrimaryKey(ordergoodsGoodsAccountVo.getOrdergoodsGoodsAccountId());
		if(res>0){
			return true;
		}
		return false;
	}

}
