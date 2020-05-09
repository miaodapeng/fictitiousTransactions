package com.vedeng.logistics.controller;

import java.util.*;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.vedeng.aftersales.dao.AfterSalesMapper;
import com.vedeng.common.annotation.MethodLock;
import com.vedeng.order.dao.SaleorderMapper;
import org.apache.commons.collections.CollectionUtils;
import com.common.constants.Contant;
import com.vedeng.logistics.model.*;
import com.vedeng.logistics.service.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vedeng.aftersales.model.AfterSales;
import com.vedeng.aftersales.model.vo.AfterSalesGoodsVo;
import com.vedeng.aftersales.model.vo.AfterSalesVo;
import com.vedeng.aftersales.service.AfterSalesService;
import com.vedeng.authorization.model.User;
import com.vedeng.common.annotation.SystemControllerLog;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.StringUtil;
import com.vedeng.common.validator.FormToken;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.SaleorderGoods;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.service.UserService;
/**
 * 
 * <b>Description:</b><br> 业务出库
 * @author scott
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.logistics.controller
 * <br><b>ClassName:</b> BusinessWarehousesOutController
 * <br><b>Date:</b> 2017年10月31日 下午3:59:06
 */
@Controller
@RequestMapping("warehouse/businessWarehouseOut")
public class BusinessWarehousesOutController extends BaseController {
	public static Logger logger = LoggerFactory.getLogger(BusinessWarehousesOutController.class);

	@Autowired
	@Qualifier("businessWarehouseOutService")
	private BusinessWarehouseOutService businessWarehouseOutService;
	
	@Autowired
    @Qualifier("afterSalesOrderService")
    private AfterSalesService afterSalesOrderService;
	
	@Autowired
    @Qualifier("userService")
	private UserService userService;
	
	@Autowired
	@Qualifier("warehousePickService")
	private WarehousePickService warehousePickService;
	
	@Autowired
	@Qualifier("warehouseOutService")
	private WarehouseOutService warehouseOutService;
	
	@Autowired
	@Qualifier("expressService")
	private ExpressService expressService;
	
	@Autowired
	@Qualifier("warehouseGoodsOperateLogService")
	private WarehouseGoodsOperateLogService warehouseGoodsOperateLogService;
	@Autowired
	private SaleorderMapper saleorderMapper;

	@Autowired
	private WarehouseGoodsSetService warehouseGoodsSetService;

	@Autowired
	private WarehouseStockService warehouseStockService;

	@Autowired
    private AfterSalesMapper afterSalesMapper;
	/**
	 * 
	 * <b>Description:</b><br> 销售业务出库列表
	 * @param request
	 * @param saleorder
	 * @param pageNo
	 * @param pageSize
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年10月31日 下午4:24:46
	 */
	@ResponseBody
	@RequestMapping(value="/index")
	public ModelAndView index(HttpServletRequest request,AfterSalesVo afterSalesVo,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo, 
			@RequestParam(required = false) Integer pageSize,HttpSession session) {
		User session_user =(User)session.getAttribute(ErpConst.CURR_USER);
		Page page = getPageTag(request,pageNo,pageSize);
		ModelAndView mv = new ModelAndView();
		afterSalesVo.setCompanyId(session_user.getCompanyId());
		afterSalesVo.setBusinessType(1);
		Map<String, Object> map = afterSalesOrderService.getStorageAftersalesPage(afterSalesVo, page);
		List<AfterSalesVo> list = (List<AfterSalesVo>)map.get("list");
		//售后订单下的产品信息
		String Ids = "";
		for (AfterSalesVo asv : list) {
			/*asv.setBusinessType(1);
			List<AfterSalesGoodsVo> asvList = afterSalesOrderService.getAfterSalesGoodsVoList(asv,session);
			asv.setAfterSalesGoodsList(asvList);
			User u = userService.getUserById(asv.getCreator());
			asv.setCreatorName(u.getUsername());*/
			Ids += asv.getAfterSalesId() + "_";
			int count = 0;
			asv.setBusinessType(1);
			List<AfterSalesGoodsVo> asvList = afterSalesOrderService.getAfterSalesGoodsVoList(asv,session);
			for (AfterSalesGoodsVo afterSalesGoodsVo : asvList) {
				//当前可出库数量
				Integer afterNum = afterSalesGoodsVo.getArrivalNum() - afterSalesGoodsVo.getDeliveryNum();
				if (afterNum > afterSalesGoodsVo.getGoodsStock()) {
					count++;
				}
			}
			if(count>=0){
				asv.setIsOutAfter(2);
			}else{
				asv.setIsOutAfter(1);
			}
			AfterSales afterSales=new AfterSales();
			afterSales.setAfterSalesId(asv.getAfterSalesId());
			afterSales.setIsOutAfter(asv.getIsOutAfter());
			afterSalesMapper.updateByPrimaryKeySelective(afterSales);

		}
		mv.addObject("Ids", Ids);
		mv.addObject("afterSalesVo",afterSalesVo);
		mv.addObject("afterSalesList",list);
		mv.addObject("page", (Page)map.get("page"));
		mv.setViewName("logistics/businessWarehouseOut/index_warehouseOut");
		return mv;
	}
	/**
	 * 
	 * <b>Description:</b><br> 售后单下的产品
	 * @param request
	 * @param afterSalesVo
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2018年4月28日 下午2:32:26
	 */
	@ResponseBody
	@RequestMapping(value = "/queryShGoodsList")
	public ResultInfo<List<AfterSalesGoodsVo>> queryShGoodsList(HttpServletRequest request, AfterSalesVo afterSalesVo,
			HttpSession session) {
		User curr_user = (User) session.getAttribute(ErpConst.CURR_USER);
		afterSalesVo.setBusinessType(1);
		afterSalesVo.setCompanyId(curr_user.getCompanyId());
		ResultInfo<List<AfterSalesGoodsVo>> resultInfo = new ResultInfo<List<AfterSalesGoodsVo>>();
		List<AfterSalesGoodsVo> asvList = afterSalesOrderService.getAfterSalesGoodsVoList(afterSalesVo,session);
		if (asvList.size() > 0) {
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
			resultInfo.setData(asvList);
			resultInfo.setParam(afterSalesVo.getAfterSalesId());
		} else {
			resultInfo.setCode(-1);
			resultInfo.setMessage("操作失败");
			resultInfo.setData(null);
		}
		return resultInfo;
	}
	
	/**
	 * 
	 * <b>Description:</b><br> 采购退换货出库列表
	 * @param request
	 * @param afterSalesVo
	 * @param pageNo
	 * @param pageSize
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年11月16日 上午8:59:31
	 */
	@ResponseBody
	@RequestMapping(value="/changeIndex")
	public ModelAndView changeIndex(HttpServletRequest request,AfterSalesVo afterSalesVo,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo, 
			@RequestParam(required = false) Integer pageSize,HttpSession session) {
		User session_user =(User)session.getAttribute(ErpConst.CURR_USER);
		Page page = getPageTag(request,pageNo,pageSize);
		ModelAndView mv = new ModelAndView();
		afterSalesVo.setCompanyId(session_user.getCompanyId());
		afterSalesVo.setBusinessType(2);
		Map<String, Object> map = afterSalesOrderService.getStorageAftersalesPage(afterSalesVo, page);
		@SuppressWarnings("unchecked")
		List<AfterSalesVo> list = (List<AfterSalesVo>)map.get("list");
		//售后订单下的产品信息
		for (AfterSalesVo asv : list) {
            asv.setBusinessType(2);
            List<AfterSalesGoodsVo> asvList = afterSalesOrderService.getAfterSalesGoodsVoList(asv, session);
            asv.setAfterSalesGoodsList(asvList);
            User u = userService.getUserById(asv.getCreator());
            asv.setCreatorName(u.getUsername());
            int count = 0;

            for (AfterSalesGoodsVo afterSalesGoodsVo : asvList) {
                //当前可出库数量
                Integer afterNum = afterSalesGoodsVo.getArrivalNum() - afterSalesGoodsVo.getDeliveryNum();
                if (afterNum > afterSalesGoodsVo.getGoodsStock()) {
                    count++;
                }
            }
            if(count>=0){
                asv.setIsOutAfter(2);
            }else{
                asv.setIsOutAfter(1);
            }
            AfterSales afterSales=new AfterSales();
            afterSales.setAfterSalesId(asv.getAfterSalesId());
            afterSales.setIsOutAfter(asv.getIsOutAfter());
            afterSalesMapper.updateByPrimaryKeySelective(afterSales);
        }
		mv.addObject("afterSalesVo",afterSalesVo);
		mv.addObject("afterSalesList",list);
		mv.addObject("page", (Page)map.get("page"));
		mv.setViewName("logistics/businessWarehouseOut/index_warehouseCgOut");
		return mv;
	}
	/**
	 * 
	 * <b>Description:</b><br> 退换货出库详情
	 * @param session
	 * @param afterSalesVo
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年11月1日 上午11:32:16
	 */
	@ResponseBody
    @FormToken(save = true)
	@RequestMapping(value = "/viewBusinessWdetail")
	public ModelAndView viewBusinessWdetail(HttpSession session,AfterSales afterSales) {
		if(afterSales.getBusinessType() !=null ){
			if(afterSales.getBusinessType()==540){
				afterSales.setBusinessType(1);
			}else if(afterSales.getBusinessType()==546 || afterSales.getBusinessType()==547){
				afterSales.setBusinessType(2);
			}else{
				afterSales.setBusinessType(afterSales.getBusinessType());
			}
		}
		User curr_user = (User)session.getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView();
		AfterSalesVo asv  = afterSalesOrderService.getAfterSalesVoListById(afterSales);
		//售后订单下的产品信息
		asv.setCompanyId(curr_user.getCompanyId());
		if(afterSales.getBusinessType() !=null ){
			if(afterSales.getBusinessType()==540){
				asv.setBusinessType(1);
			}else if(afterSales.getBusinessType()==546 || afterSales.getBusinessType()==547){
				asv.setBusinessType(2);
				afterSales.setAddress(asv.getAddress());
				afterSales.setArea(asv.getArea());
			}else{
				asv.setBusinessType(afterSales.getBusinessType());
			}
		}
		List<AfterSalesGoodsVo> asvList = afterSalesOrderService.getAfterSalesGoodsVoList(asv,session);
		asv.setAfterSalesGoodsList(asvList);
		User user = userService.getUserById(asv.getCreator());
		asv.setCreatorName(user==null?"":user.getUsername());
		int goodsNum = 0;
		//判断是否0<已拣货数量||已发货数量<商品总数
		int allPickCnt = 0;
		int allOutCnt = 0;
		for (AfterSalesGoodsVo afterSalesGoodsVo : asvList) {
			goodsNum += afterSalesGoodsVo.getNum();
			allPickCnt+= afterSalesGoodsVo.getPickCnt();
			allOutCnt+= afterSalesGoodsVo.getDeliveryNum();
		}
		
		List<Logistics> logisticsList = getLogisticsList(curr_user.getCompanyId());
		mv.addObject("logisticsList",logisticsList);
		
		List<SysOptionDefinition> freightDescriptions=getSysOptionDefinitionList(469);
		mv.addObject("freightDescriptions", freightDescriptions);
		//------------VDERP-1553出库详情页产品信息模块增加库存相关信息---------
		List<String> skuList = asvList.stream().map(item ->item.getSku()).collect(Collectors.toList());
		//获取库存信息
		Map<String, WarehouseStock> stockInfo = warehouseStockService.getStockInfo(skuList);
		//获取库位信息
		Map<String, WarehouseGoodsSet> storageLocationNameMap = warehouseGoodsSetService.getStorageLocationNameBuySku(skuList);
		mv.addObject("stockInfo",stockInfo);
		mv.addObject("storageLocation",storageLocationNameMap);
		//------------VDERP-1553出库详情页产品信息模块增加库存相关信息---------
		//拣货记录
		Saleorder saleorder = new Saleorder();
		saleorder.setBussinessId(asv.getAfterSalesId());
		if(asv.getType()==540){
			saleorder.setBussinessType(4);
		}else if(asv.getType()==546){
			saleorder.setBussinessType(6);
		}else if(asv.getType()==547){
			saleorder.setBussinessType(7);
		}
		List<WarehousePicking> warehousePickList = warehousePickService.getPickDetil(saleorder);
		List<String> timeArray = new ArrayList<>();
		String pickFlag = "0";
		if (null != warehousePickList){
			for (WarehousePicking wp : warehousePickList) {
				if(wp.getCnt()==0){
				  timeArray.add(DateUtil.convertString(wp.getAddTime(), "YYYY-MM-dd HH:mm:ss"));
				  pickFlag = "1";
				}
			}
			HashSet<String> tArray = new HashSet<String>(timeArray);
			mv.addObject("timeArray", tArray);
	    }
		//出库记录清单
	    List<WarehouseGoodsOperateLog> warehouseOutList = warehouseOutService.getOutDetil(saleorder);
		List<String> timeArrayWl = new ArrayList<>();
		if (null != warehouseOutList){
			for (WarehouseGoodsOperateLog wl : warehouseOutList) {
				User u = userService.getUserById(wl.getCreator());
				wl.setOpName(u.getUsername());
				timeArrayWl.add(DateUtil.convertString(wl.getAddTime(), "YYYY-MM-dd HH:mm:ss"));
			}
			HashSet<String> wArray = new HashSet<String>(timeArrayWl);
			TreeSet ts = new TreeSet(wArray);
		    ts.comparator();
			mv.addObject("timeArrayWl", ts);
	    }
		// 物流信息
		Express express = new Express();
		express.setBusinessType(SysOptionConstant.ID_582);
		List<Integer> relatedIds = new ArrayList<Integer>();
		for (AfterSalesGoodsVo afterSalesGoodsVo : asvList) {
		    relatedIds.add(afterSalesGoodsVo.getAfterSalesGoodsId());
		}
		if (relatedIds != null && relatedIds.size() > 0) {
			express.setRelatedIds(relatedIds);
			try {
			    List<Express> expressList = expressService.getExpressList(express);
			    mv.addObject("expressList", expressList);
			} catch (Exception e) {
			    logger.error(Contant.ERROR_MSG, e);
			}
		}
		mv.addObject("pickFlag",pickFlag);
		mv.addObject("goodsNum",goodsNum);
		mv.addObject("allPickCnt",allPickCnt);
		mv.addObject("allOutCnt",allOutCnt);
		mv.addObject("afterSales",asv);
		mv.addObject("goodsNum",goodsNum);
		mv.addObject("warehouseOutList",warehouseOutList);
		mv.addObject("warehousePickList",warehousePickList);
		mv.addObject("asvList",asvList);
		mv.addObject("businessType",afterSales.getBusinessType());
		
		mv.setViewName("logistics/businessWarehouseOut/view_BusinessWOutDetail");
		return mv;
	}
	/**
	 * 
	 * <b>Description:</b><br> 确定换货的拣货数量
	 * @param afterSales
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年11月2日 下午4:38:00
	 */
	@ResponseBody
	@RequestMapping(value = "/viewBusinessPicking")
	public ModelAndView viewBusinessPicking(HttpSession session,AfterSales afterSales) {
		User curr_user = (User)session.getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView();
		AfterSalesVo asv  = afterSalesOrderService.getAfterSalesVoListById(afterSales);
		User user = userService.getUserById(asv.getCreator());
		asv.setCreatorName(user==null?"":user.getUsername());
		//售后订单下的产品信息
		asv.setCompanyId(curr_user.getCompanyId());
		asv.setBusinessType(afterSales.getBusinessType());
		asv.setIsNormal(1);
		List<AfterSalesGoodsVo> asvList = afterSalesOrderService.getAfterSalesGoodsVoList(asv,session);
		List<AfterSalesGoodsVo> aList = new ArrayList<AfterSalesGoodsVo>();
		int goodsNum = 0;
		for (AfterSalesGoodsVo s : asvList) {
			if((s.getNum()-s.getPickCnt())>0&&(s.getTotalNum()-(s.getPickCnt()-s.getDeliveryNum()))>0){
				aList.add(s);
			}
			goodsNum += s.getNum();
		}
		for (AfterSalesGoodsVo afterSalesGoodsVo : aList) {
			SaleorderGoods saleorderGood = new SaleorderGoods();
			saleorderGood.setIsOut("0");
			saleorderGood.setBussinessId(afterSalesGoodsVo.getAfterSalesGoodsId());
			saleorderGood.setGoodsId(afterSalesGoodsVo.getGoodsId());
			saleorderGood.setCompanyId(curr_user.getCompanyId());
		    //saleorderGood.setBussinessId(-1);
			saleorderGood.setBussinessType(asv.getType());
			saleorderGood.setSaleorderGoodsId(afterSalesGoodsVo.getAfterSalesGoodsId());
			List<WarehouseGoodsOperateLog> list = warehouseGoodsOperateLogService.getWlog(saleorderGood);
			afterSalesGoodsVo.setWlist(list);
		}
		asv.setAfterSalesGoodsList(aList);
		List<AfterSalesGoodsVo> goodsList = asv.getAfterSalesGoodsList();
		for(int i=0;i<goodsList.size();i++){
			int needCnt = goodsList.get(i).getNum()- goodsList.get(i).getPickCnt();
			List<WarehouseGoodsOperateLog> w = goodsList.get(i).getWlist();
			int allCnt = 0;
			for(int j=0;j<w.size();j++){
				allCnt+=w.get(j).getCnt();
				if(needCnt>0){
					if(needCnt>w.get(j).getCnt()){
						//w.get(j).setpCount(needCnt-w.get(j).getCnt());
						w.get(j).setpCount(w.get(j).getCnt());
					}else if(needCnt<=w.get(j).getCnt()){
						w.get(j).setpCount(needCnt);
					}
					needCnt-=w.get(j).getCnt();
				}	
			}
			if(allCnt>needCnt){
				goodsList.get(i).setpCountAll(goodsList.get(i).getNum()- goodsList.get(i).getPickCnt());
			}else{
				goodsList.get(i).setpCountAll(allCnt);
			}
		}
		mv.addObject("goodsNum",goodsNum);
		mv.addObject("afterSales",asv);
		mv.addObject("businessType",afterSales.getBusinessType());
		
		mv.setViewName("logistics/businessWarehouseOut/view_businessPicking");
		return mv;
	}
	/**
	 * 
	 * <b>Description:</b><br> 拣货详情页面
	 * @param afterSales
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年11月3日 上午10:42:44
	 */
	@SuppressWarnings("rawtypes")
	@FormToken(save = true)
	@ResponseBody
	@RequestMapping(value = "/viewBsPickingDetail")
	@SystemControllerLog(operationType = "add",desc = "保存售后拣货数量")
	public ModelAndView viewBsPickingDetail(AfterSales afterSales,HttpSession session) {
		User curr_user = (User)session.getAttribute(ErpConst.CURR_USER);

		//获取售后订单的afterSalesGoodsId信息
		String[] afterSalesGoodsIds = StringUtils.split(afterSales.getAfterSalesGoodsIds(), ",");

		ModelAndView mv = new ModelAndView();
		AfterSalesVo asv  = afterSalesOrderService.getAfterSalesVoListById(afterSales);
		User user = userService.getUserById(asv.getCreator());
		if(null != user) {
			String companyName = user.getCompanyName();
			mv.addObject("companyName", companyName);
		}
		asv.setCreatorName(user==null?"":user.getUsername());
		Long time = DateUtil.sysTimeMillis();
		String pickNums = afterSales.getPickNums();
		mv.addObject("pickNums",pickNums);
		
		//售后订单下的产品信息
		asv.setCompanyId(curr_user.getCompanyId());
		asv.setBusinessType(afterSales.getBusinessType());
		asv.setIsNormal(1);
		List<AfterSalesGoodsVo> asvList = afterSalesOrderService.getAfterSalesGoodsVoList(asv,session);
		List<AfterSalesGoodsVo> aList = new ArrayList<AfterSalesGoodsVo>();
		int goodsNum = 0;
		for (AfterSalesGoodsVo s : asvList) {
			if((s.getNum()-s.getPickCnt())>0&&(s.getTotalNum()-(s.getPickCnt()-s.getDeliveryNum()))>0){
				aList.add(s);
			}
			goodsNum += s.getNum();
		}
		for (AfterSalesGoodsVo afterSalesGoodsVo : aList) {
			List<String> pickList = new ArrayList<>();
			List<String> batchNumberList = new ArrayList<>();
			List<String> expirationDateList = new ArrayList<>();
			List<String> relatedIdList = new ArrayList<>();
			List<String> relatedTypeList = new ArrayList<>();
			String picks[] = null;
			String values[] = null;
			String bpvalues[] = null;
			String ervalues[] = null;
			String rtvalues[] = null;
			String goodsList[] = pickNums.split("#");

			int count = 0;
			for (String st : goodsList) {
				String goodsValue[] = st.split("@");
				String gId = goodsValue[0];
				if (gId.equals(afterSalesGoodsVo.getGoodsId() + ""  ) && afterSalesGoodsVo.getAfterSalesGoodsId()  == Integer.parseInt(afterSalesGoodsIds[count])) {
					String nums[] = goodsValue[1].split("_");
					String pickNum = nums[0];
					afterSalesGoodsVo.setNowNum(Integer.parseInt(pickNum));
					if (nums.length > 1) {
						
						picks = nums[1].split(",");
						for (String str : picks) {
							values = str.split("!");
							pickList.add(values[0]);
							bpvalues = values[1].split("%");
						    batchNumberList.add(bpvalues[0]);
						    ervalues = bpvalues[1].split("\\+");
							expirationDateList.add(ervalues[0]);
							rtvalues = ervalues[1].split("\\=");
							relatedIdList.add(rtvalues[0]);
							relatedTypeList.add(rtvalues[1]);
						}
					}
					break;
				}
				count ++;
			}
			SaleorderGoods saleorderGood = new SaleorderGoods();
			saleorderGood.setIsOut("0");
			saleorderGood.setBussinessId(afterSalesGoodsVo.getAfterSalesGoodsId());
			saleorderGood.setGoodsId(afterSalesGoodsVo.getGoodsId());
			saleorderGood.setCompanyId(curr_user.getCompanyId());
			//saleorderGood.setBussinessId(-1);
			saleorderGood.setBussinessType(asv.getType());
			saleorderGood.setSaleorderGoodsId(afterSalesGoodsVo.getAfterSalesGoodsId());
			List<WarehouseGoodsOperateLog> list = warehouseGoodsOperateLogService.getWlog(saleorderGood);
			for(int i=0;i<list.size();i++){
				list.get(i).setpCtn(pickList.get(i));
				if(!"-1".equals(batchNumberList.get(i))){
					list.get(i).setBatchNumber(batchNumberList.get(i));
				}
				list.get(i).setExpirationDate(Long.valueOf(expirationDateList.get(i)));
				list.get(i).setOrderId(Integer.parseInt(relatedIdList.get(i)));
				if(!"1".equals(relatedTypeList.get(i))){
					list.get(i).setYwType(2);
				}else{
					list.get(i).setYwType(1);
				}
			}
			List<WarehouseGoodsOperateLog> wlist = new ArrayList<WarehouseGoodsOperateLog>();
			for (WarehouseGoodsOperateLog wl : list) {
				if(Integer.parseInt(wl.getpCtn())>0){
					wlist.add(wl);
				}
			}
			afterSalesGoodsVo.setWlist(wlist);
		}
		asv.setAfterSalesGoodsList(aList);
		
		Saleorder sd = new Saleorder();
		sd.setBussinessId(asv.getAfterSalesId());
		sd.setBussinessType(1);
		sd.setAfterSalesGoodsList(aList);
		ResultInfo<?> result = warehousePickService.savePickRecord(sd,session);
		
		//拣货记录
		Saleorder saleorder = new Saleorder();
		saleorder.setBussinessId(asv.getAfterSalesId());
		if(asv.getType()==540){
			saleorder.setBussinessType(4);
		}else if(asv.getType()==546){
			saleorder.setBussinessType(6);
		}else if(asv.getType()==547){
			saleorder.setBussinessType(7);
		}
		List<WarehousePicking> warehousePickList = warehousePickService.getPickDetil(saleorder);
		for (WarehousePicking warehousePicking : warehousePickList) {
			User u = userService.getUserById(warehousePicking.getCreator());
			warehousePicking.setOperator(u==null?"":u.getUsername());
		}
		List<String> timeArray = new ArrayList<>();
		if (null != warehousePickList){
			for (WarehousePicking wp : warehousePickList) {
				if(wp.getCnt()==0)
				timeArray.add(DateUtil.convertString(wp.getAddTime(), "YYYY-MM-dd HH:mm:ss"));
			}
			HashSet<String> tArray = new HashSet<String>(timeArray);
			mv.addObject("timeArray", tArray);
	    }
		List<AfterSalesGoodsVo> asList = new ArrayList<AfterSalesGoodsVo>();
		for(AfterSalesGoodsVo av :asv.getAfterSalesGoodsList()){
			if(av.getNowNum()>0){
				asList.add(av);
			}
		}
		asv.setAfterSalesGoodsList(asList);
		mv.addObject("warehousePickList",warehousePickList);
		mv.addObject("afterSales",asv);
		mv.addObject("time",time);
		mv.addObject("goodsNum",goodsNum);
		mv.addObject("userName",curr_user.getUsername());
		mv.addObject("businessType",afterSales.getBusinessType());
		
		mv.setViewName("logistics/businessWarehouseOut/view_buspicking_detail");
		return mv;
	}
	/**
	 * 
	 * <b>Description:</b><br> 换货出库
	 * @param afterSales
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年11月3日 下午1:03:12
	 */
	@ResponseBody
	@RequestMapping(value = "/viewBsOutDetail")
	public ModelAndView viewBsOutDetail(AfterSales afterSales,HttpSession session) {
		User curr_user = (User)session.getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView();
		AfterSalesVo asv  = afterSalesOrderService.getAfterSalesVoListById(afterSales);
		User user = userService.getUserById(asv.getCreator());
		asv.setCreatorName(user==null?"":user.getUsername());
		asv.setIsOut("1");
		asv.setBusinessType(afterSales.getBusinessType());
		asv.setIsNormal(1);
		List<AfterSalesGoodsVo> asvList = afterSalesOrderService.getAfterSalesGoodsVoList(asv,session);
		List<AfterSalesGoodsVo> asList = new ArrayList<AfterSalesGoodsVo>();
		int goodsNum = 0;
		for(AfterSalesGoodsVo s : asvList){
			if((s.getPickCnt()-s.getDeliveryNum())>0){
				asList.add(s);
			}
			goodsNum += s.getNum();
		}
		for (AfterSalesGoodsVo av : asList) {
			SaleorderGoods sd = new SaleorderGoods();
			sd.setIsOut("1");
			sd.setBussinessId(av.getAfterSalesId());
			sd.setGoodsId(av.getGoodsId());
			sd.setCompanyId(curr_user.getCompanyId());
			List<WarehouseGoodsOperateLog> list = warehouseGoodsOperateLogService.getWlog(sd);
			av.setWlist(list);
		}
		asv.setAfterSalesGoodsList(asList);
		//拣货记录
		Saleorder saleorder = new Saleorder();
		saleorder.setBussinessId(asv.getAfterSalesId());
		if(asv.getType()==540){
			saleorder.setBussinessType(4);
		}else if(asv.getType()==546){
			saleorder.setBussinessType(6);
		}else if(asv.getType()==547){
			saleorder.setBussinessType(7);
		}
		List<WarehousePicking> warehousePickList = warehousePickService.getPickDetil(saleorder);
		mv.addObject("warehousePickList",warehousePickList);
		mv.addObject("afterSales",asv);
		mv.addObject("businessType",afterSales.getBusinessType());
		mv.addObject("goodsNum",goodsNum);
		
		mv.setViewName("logistics/businessWarehouseOut/view_bsWarehouseout");
		return mv;
	}
	/**
	 * 
	 * <b>Description:</b><br> 换货出库完成
	 * @param afterSales
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年11月6日 下午3:07:41
	 */
	@ResponseBody
	@RequestMapping(value = "/warehouseBsEnd")
	@SystemControllerLog(operationType = "add",desc = "保存售后出库数量")
	public ModelAndView warehouseBsEnd(AfterSales afterSales,HttpSession session) {
		User curr_user = (User)session.getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView();
		AfterSalesVo asv  = afterSalesOrderService.getAfterSalesVoListById(afterSales);
		User user = userService.getUserById(asv.getCreator());
		asv.setCreatorName(user==null?"":user.getUsername());
		Long time = DateUtil.sysTimeMillis();
		String pickNums = afterSales.getPickNums();
		mv.addObject("pickNums",pickNums);
		asv.setIsOut("1");
		asv.setBusinessType(afterSales.getBusinessType());
		asv.setIsNormal(1);
		List<AfterSalesGoodsVo> asvList = afterSalesOrderService.getAfterSalesGoodsVoList(asv,session);
		List<AfterSalesGoodsVo> aList = new ArrayList<AfterSalesGoodsVo>();
		int goodsNum = 0;
		for (AfterSalesGoodsVo s : asvList) {
			if((s.getPickCnt()-s.getDeliveryNum())>0){
				aList.add(s);
			}
			goodsNum += s.getNum();
		}
		for (AfterSalesGoodsVo afterSalesGoodsVo : aList) {
			String pick[] = null;
			String goodsList [] = pickNums.split("#");
			for (String st : goodsList) {
				String goodsValue[] = st.split("@");
				String gId = goodsValue[0];
				if(gId.equals(afterSalesGoodsVo.getGoodsId()+"")){
					String nums[] = goodsValue[1].split("_");
					String pickNum = nums[0];
					afterSalesGoodsVo.setNowNum(Integer.parseInt(pickNum));
					pick = nums[1].split(",");
					break;
				}
			}
			asv.setAfterSalesGoodsList(aList);
			SaleorderGoods saleorderGood = new SaleorderGoods();
			saleorderGood.setIsOut("1");
			saleorderGood.setBussinessId(afterSalesGoodsVo.getAfterSalesId());
			saleorderGood.setGoodsId(afterSalesGoodsVo.getGoodsId());
			saleorderGood.setCompanyId(curr_user.getCompanyId());
			saleorderGood.setBussinessType(-1);
			List<WarehouseGoodsOperateLog> list = warehouseGoodsOperateLogService.getWlog(saleorderGood);
			for(int i=0;i<list.size();i++){
				list.get(i).setpCtn(pick[i]);
			}
			List<WarehouseGoodsOperateLog> wlist = new ArrayList<WarehouseGoodsOperateLog>();
			for (WarehouseGoodsOperateLog wl : list) {
				if(Integer.parseInt(wl.getpCtn())>0){
					wlist.add(wl);
				}
				if(asv.getType()==540){
					wl.setOperateType(4);
				}else if(asv.getType()==546){
					wl.setOperateType(6);
				}else if(asv.getType()==547){
					wl.setOperateType(7);
				}
				
			}
			afterSalesGoodsVo.setWlist(wlist);
		}
		//保存产品出库记录
		Saleorder sd = new Saleorder();
		sd.setBussinessId(asv.getAfterSalesId());
		sd.setBussinessType(1);
		sd.setAfterSalesGoodsList(aList);
		ResultInfo<?> result = warehouseGoodsOperateLogService.saveOutRecord(sd,session);
		//出库记录清单
		Saleorder saleorder = new Saleorder();
		saleorder.setBussinessId(asv.getAfterSalesId());
		if(asv.getType()==540){
			saleorder.setBussinessType(4);
		}else if(asv.getType()==546){
			saleorder.setBussinessType(6);
		}else if(asv.getType()==547){
			saleorder.setBussinessType(7);
		}
	    List<WarehouseGoodsOperateLog> warehouseOutList = warehouseOutService.getOutDetil(saleorder);
		List<String> timeArrayWl = new ArrayList<>();
		if (null != warehouseOutList){
			for (WarehouseGoodsOperateLog wl : warehouseOutList) {
				User u = userService.getUserById(wl.getCreator());
				wl.setOpName(u.getUsername());
				timeArrayWl.add(DateUtil.convertString(wl.getAddTime(), "YYYY-MM-dd HH:mm:ss"));
			}
			HashSet<String> wArray = new HashSet<String>(timeArrayWl);
			mv.addObject("timeArrayWl", wArray);
	    }
		List<AfterSalesGoodsVo> asList = new ArrayList<AfterSalesGoodsVo>();
		for(AfterSalesGoodsVo av :asv.getAfterSalesGoodsList()){
			if(av.getNowNum()>0){
				asList.add(av);
			}
		}
		asv.setAfterSalesGoodsList(asList);
		mv.addObject("warehouseOutList",warehouseOutList);
		mv.addObject("afterSales",asv);
		mv.addObject("time",time);
		mv.addObject("goodsNum",goodsNum);
		mv.addObject("userName",curr_user.getUsername());
		mv.addObject("businessType",afterSales.getBusinessType());
		mv.setViewName("logistics/businessWarehouseOut/view_out_end");
		return mv;
	}
	/**
	 * 
	 * <b>Description:</b><br> 扫码出库
	 * @param afterSales
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年11月7日 上午10:28:20
	 */
	@FormToken(save = true)
	@ResponseBody
	@RequestMapping(value = "/warehouseSmOut")
	public ModelAndView warehouseSmOut(AfterSales afterSales,HttpSession session) {
		User curr_user = (User)session.getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView();
		AfterSalesVo asv  = afterSalesOrderService.getAfterSalesVoListById(afterSales);
		User user = userService.getUserById(asv.getCreator());
		asv.setCreatorName(user==null?"":user.getUsername());
		asv.setIsOut("1");
		asv.setBusinessType(afterSales.getBusinessType());
		asv.setIsNormal(1);
		List<AfterSalesGoodsVo> asvList = afterSalesOrderService.getAfterSalesGoodsVoList(asv,session);
		int goodsNum = 0;
		for (AfterSalesGoodsVo s : asvList) {
			goodsNum += s.getNum();
		}
		asv.setAfterSalesGoodsList(asvList);
		//拣货记录
		Saleorder saleorder = new Saleorder();
		saleorder.setBussinessId(asv.getAfterSalesId());
		if(asv.getType()==540){
			saleorder.setBussinessType(4);
		}else if(asv.getType()==546){
			saleorder.setBussinessType(6);
		}else if(asv.getType()==547){
			saleorder.setBussinessType(7);
		}
		List<WarehousePicking> warehousePickList = warehousePickService.getPickDetil(saleorder);
		for (WarehousePicking warehousePicking : warehousePickList) {
			User u = userService.getUserById(warehousePicking.getCreator());
			warehousePicking.setOperator(u==null?"":u.getUsername());
		}
		List<String> timeArray = new ArrayList<>();
		if (null != warehousePickList){
			for (WarehousePicking wp : warehousePickList) {
				if(wp.getCnt()==0)
				timeArray.add(DateUtil.convertString(wp.getAddTime(), "YYYY-MM-dd HH:mm:ss"));
			}
			HashSet<String> tArray = new HashSet<String>(timeArray);
			mv.addObject("timeArray", tArray);
	    }
		// 物流信息
		Express express = new Express();
		express.setBusinessType(SysOptionConstant.ID_582);
		List<Integer> relatedIds = new ArrayList<Integer>();
		for (AfterSalesGoodsVo as : asvList) {
			relatedIds.add(as.getAfterSalesGoodsId());
		}
		express.setRelatedIds(relatedIds);
		try {
		    List<Express> expressList = expressService.getExpressList(express);
		    mv.addObject("expressList", expressList);
		} catch (Exception e) {
		    logger.error(Contant.ERROR_MSG, e);
		}
		mv.addObject("goodsNum",goodsNum);
		mv.addObject("warehouseBarcodeOutList",warehousePickList);
		mv.addObject("afterSales",asv);
		mv.addObject("businessType",asv.getType());
		mv.setViewName("logistics/businessWarehouseOut/view_sm_warehouseout");
		return mv;
	}
	/**
	 * 
	 * <b>Description:</b><br> 保存条码出库记录
	 * @param afterSales
	 * @param session
	 * @return
	 * @NoteH
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年11月7日 上午10:54:01
	 */
	@MethodLock(field = "afterSalesId",className = AfterSales.class)
//	@FormToken(remove = true)
	@ResponseBody
	@RequestMapping(value = "/warehouseSMEnd")
	@SystemControllerLog(operationType = "add",desc = "保存售后扫码出库数量")
	public ModelAndView warehouseSMEnd(AfterSales afterSales,HttpSession session,@RequestParam(value = "batchType",defaultValue = "0")Integer batchType) {
		User curr_user = (User)session.getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView();
		AfterSalesVo asv  = afterSalesOrderService.getAfterSalesVoListById(afterSales);
		User user = userService.getUserById(asv.getCreator());
		asv.setCreatorName(user==null?"":user.getUsername());
		Long time = DateUtil.sysTimeMillis();
		String pickNums = afterSales.getPickNums();
		mv.addObject("pickNums",pickNums);
		asv.setIsOut("1");
		if(afterSales.getBusinessType()==540){
			asv.setBusinessType(1);
		}else{
			asv.setBusinessType(2);
		}
		asv.setIsNormal(1);
		List<AfterSalesGoodsVo> asvList = afterSalesOrderService.getAfterSalesGoodsVoList(asv,session);
		int goodsNum = 0;
		for (AfterSalesGoodsVo s : asvList) {
			goodsNum += s.getNum();
		}
		asv.setAfterSalesGoodsList(asvList);
		String idCnt = afterSales.getIdCnt();
		String values[]= idCnt.split("#");
		List<WarehouseGoodsOperateLog> wgList = new ArrayList<WarehouseGoodsOperateLog>();
		//---------------限购------------------
		//是否为活动订单
		boolean	isActionOrderflag = false;
		//订单商品内未发货数量
		HashMap<Integer,Integer> afterNumMap = new HashMap<>();
		if (asv.getType().equals(ErpConst.AFTER_SALEORDERRETURN_TYPE)){
			Integer orderId = asv.getOrderId();
			Saleorder sd = saleorderMapper.getSaleOrderById(orderId);
			if(sd.getActionId()!=0){
				//为活动订单
				isActionOrderflag = true;
			}
		}
		//---------------限购------------------
		for(int i=0;i<values.length;i++){
			String nc[] = values[i].split(",");
			String warehouseGoodsOperateLogId = nc[0];
			WarehouseGoodsOperateLog w = new WarehouseGoodsOperateLog();
			w.setWarehouseGoodsOperateLogId(Integer.parseInt(warehouseGoodsOperateLogId));
			//根据id获取入库信息，设置为出库信息
			WarehouseGoodsOperateLog wl  = warehouseOutService.getSMGoods(w);
			if (asv.getType() == 540) {
				wl.setOperateType(4);
			} else if (asv.getType() == 546) {
				wl.setOperateType(6);
			} else if (asv.getType() == 547) {
				wl.setOperateType(7);
			}
			wl.setAddTime(time);
			wl.setCreator(curr_user.getUserId());
			wl.setUpdater(curr_user.getUserId());
			wl.setModTime(time);
			wl.setIsEnable(1);
			//判断条码是否出库
			Integer barcodeIsEnable=0;
			if(batchType.equals(1)){
				barcodeIsEnable=1;
			}else {
				barcodeIsEnable = warehouseGoodsOperateLogService.getBarcodeIsEnable(wl, 2);
			}
			if(barcodeIsEnable > 0) {
				//设置售后产品id
				AfterSalesVo av = new AfterSalesVo();
				av.setCompanyId(curr_user.getCompanyId());
				av.setGoodsId(wl.getGoodsId());
				List<AfterSalesGoodsVo> aList = afterSalesOrderService.getAfterSalesGoodsVoList(asv, session);
				//---------------限购------------------
					for (AfterSalesGoodsVo afterSalesGoodsVo : aList) {
						//当前可出库数量
						Integer afterNum = afterNumMap.get(afterSalesGoodsVo.getOrderDetailId());
						if(afterSales.getBusinessType().equals(546)){//采购退货
							afterNumMap.put(afterSalesGoodsVo.getOrderDetailId(), afterSalesGoodsVo.getNum());
						}else{
							if (afterNum == null) {
								afterNum = afterSalesGoodsVo.getArrivalNum() - afterSalesGoodsVo.getDeliveryNum();
								afterNumMap.put(afterSalesGoodsVo.getOrderDetailId(), afterNum);
							}
						}
					}
				if (isActionOrderflag) {
					//排序将活动商品排在前面
					aList = aList.stream().sorted((o1, o2) -> {
						if (o1.getIsActionGoods() > o2.getIsActionGoods()) {
							return -1;
						} else {
							return 1;
						}
					}).collect(Collectors.toList());
					for (AfterSalesGoodsVo afterSalesGoodsVo : aList) {
						if (wl.getGoodsId().equals(afterSalesGoodsVo.getGoodsId())) {
							Integer afterNum = afterNumMap.get(afterSalesGoodsVo.getOrderDetailId());
							if(afterNum == null){
								afterNum = 0;
							}
							//先出库活动商品
							if (afterSalesGoodsVo.getIsActionGoods() > 0 && afterNum > 0) {
								wl.setRelatedId(afterSalesGoodsVo.getAfterSalesGoodsId());
								afterNumMap.put(afterSalesGoodsVo.getOrderDetailId(), afterNum - Integer.parseInt(nc[1]));
								wl.setIsActionGoods(1);
								wl.setNum(0 - Integer.parseInt(nc[1]));
								wgList.add(wl);
								break;
							} else if (afterSalesGoodsVo.getIsActionGoods().equals(0) && afterNum > 0) {
								wl.setRelatedId(afterSalesGoodsVo.getAfterSalesGoodsId());
								afterNumMap.put(afterSalesGoodsVo.getOrderDetailId(), afterNum - Integer.parseInt(nc[1]));
								wl.setNum(0 - Integer.parseInt(nc[1]));
								wgList.add(wl);
								break;
							}
						}
					}
				} else {
					for (AfterSalesGoodsVo afterSalesGoodsVo : aList) {
						if (wl.getGoodsId().equals(afterSalesGoodsVo.getGoodsId())) {
							Integer afterNum = afterNumMap.get(afterSalesGoodsVo.getOrderDetailId());
							if(afterNum == null){
								afterNum = 0;
							}
							if(afterNum - Integer.parseInt(nc[1]) >= 0 && afterNum > 0) {
								afterNumMap.put(afterSalesGoodsVo.getOrderDetailId(), afterNum - Integer.parseInt(nc[1]));
								wl.setRelatedId(afterSalesGoodsVo.getAfterSalesGoodsId());
								wl.setNum(0 - Integer.parseInt(nc[1]));
								wgList.add(wl);
							}
						}
					}
				}
				//---------------限购------------------
			}
		}
		if(CollectionUtils.isEmpty(wgList)){
			return fail(mv);
		}
		//批量插入条码出库信息
		ResultInfo<?> result = warehouseGoodsOperateLogService.addWlogList(wgList);
		//出库记录清单
		Saleorder saleorder = new Saleorder();
		saleorder.setBussinessId(asv.getAfterSalesId());
		if(asv.getType()==540){
			saleorder.setBussinessType(4);
		}else if(asv.getType()==546){
			saleorder.setBussinessType(6);
		}else if(asv.getType()==547){
			saleorder.setBussinessType(7);
		}
	    List<WarehouseGoodsOperateLog> warehouseOutList = warehouseOutService.getOutDetil(saleorder);
		List<String> timeArrayWl = new ArrayList<>();
		if (null != warehouseOutList){
			for (WarehouseGoodsOperateLog wl : warehouseOutList) {
				User u = userService.getUserById(wl.getCreator());
				wl.setOpName(u.getUsername());
				timeArrayWl.add(DateUtil.convertString(wl.getAddTime(), "YYYY-MM-dd HH:mm:ss"));
			}
			HashSet<String> wArray = new HashSet<String>(timeArrayWl);
			mv.addObject("timeArrayWl", wArray);
	    }
		List<AfterSalesGoodsVo> asList = new ArrayList<AfterSalesGoodsVo>();
		asv.setAfterSalesGoodsList(asList);
		mv.addObject("warehouseOutList",warehouseOutList);
		mv.addObject("afterSales",asv);
		mv.addObject("time",time);
		mv.addObject("goodsNum",goodsNum);
		mv.addObject("userName",curr_user.getUsername());
		mv.addObject("businessType",afterSales.getBusinessType());
		mv.setViewName("logistics/businessWarehouseOut/view_out_end");
		return mv;
	}
}
