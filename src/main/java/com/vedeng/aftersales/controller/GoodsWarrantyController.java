package com.vedeng.aftersales.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vedeng.aftersales.service.GoodsWarrantyService;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.order.model.vo.SaleorderGoodsWarrantyVo;

/**
 * <b>Description:</b><br> 录保卡管理
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.aftersales.controller
 * <br><b>ClassName:</b> GoodsWarrantyController
 * <br><b>Date:</b> 2017年10月24日 上午10:04:52
 */
@Controller
@RequestMapping("/aftersales/goodswarranty")
public class GoodsWarrantyController extends BaseController{
	@Autowired
	@Qualifier("goodsWarrantyService")
	private GoodsWarrantyService goodsWarrantyService;
	
	/**
	 * <b>Description:</b><br> 录保卡列表
	 * @param request
	 * @param saleorderGoodsWarrantyVo
	 * @param pageNo
	 * @param pageSize
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年10月24日 上午10:08:39
	 */
	@ResponseBody
	@RequestMapping(value = "/list")
	public ModelAndView list(HttpServletRequest request, SaleorderGoodsWarrantyVo saleorderGoodsWarrantyVo,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize, HttpSession session) {
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView("aftersales/goodswarranty/list_goodswarranty");
		Page page = getPageTag(request, pageNo, pageSize);
		List<SaleorderGoodsWarrantyVo> list = null;
		saleorderGoodsWarrantyVo.setCompanyId(user.getCompanyId());
		
		if(null == saleorderGoodsWarrantyVo.getCheckTimeStartStr()){
			saleorderGoodsWarrantyVo.setCheckTimeStartStr(DateUtil.getDayOfMonth(-1));
		}
		if(null == saleorderGoodsWarrantyVo.getCheckTimeEndStr()){
			saleorderGoodsWarrantyVo.setCheckTimeEndStr(DateUtil.getNowDate(""));
		}
		
		if(null == saleorderGoodsWarrantyVo.getAddTimeStartStr()){
			saleorderGoodsWarrantyVo.setAddTimeStartStr(DateUtil.getDayOfMonth(-1));
		}
		if(null == saleorderGoodsWarrantyVo.getAddTimeEndStr()){
			saleorderGoodsWarrantyVo.setAddTimeEndStr(DateUtil.getNowDate(""));
		}
		
		if(null != saleorderGoodsWarrantyVo.getCheckTimeStartStr() && saleorderGoodsWarrantyVo.getCheckTimeStartStr() != ""){
			saleorderGoodsWarrantyVo.setCheckTimeStart(DateUtil.convertLong(saleorderGoodsWarrantyVo.getCheckTimeStartStr(), "yyyy-MM-dd"));
		}
		if(null != saleorderGoodsWarrantyVo.getCheckTimeEndStr() && saleorderGoodsWarrantyVo.getCheckTimeEndStr() != ""){
			saleorderGoodsWarrantyVo.setCheckTimeEnd(DateUtil.convertLong(saleorderGoodsWarrantyVo.getCheckTimeEndStr()+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		}
		
		if(null != saleorderGoodsWarrantyVo.getAddTimeStartStr() && saleorderGoodsWarrantyVo.getAddTimeStartStr() != ""){
			saleorderGoodsWarrantyVo.setAddTimeStart(DateUtil.convertLong(saleorderGoodsWarrantyVo.getAddTimeStartStr(), "yyyy-MM-dd"));
		}
		if(null != saleorderGoodsWarrantyVo.getAddTimeEndStr() && saleorderGoodsWarrantyVo.getAddTimeEndStr() != ""){
			saleorderGoodsWarrantyVo.setAddTimeEnd(DateUtil.convertLong(saleorderGoodsWarrantyVo.getAddTimeEndStr()+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		}
		
		Map<String, Object> map = goodsWarrantyService.querylistPage(saleorderGoodsWarrantyVo, page);
		list = (List<SaleorderGoodsWarrantyVo>) map.get("list");
		mv.addObject("saleorderGoodsWarrantyVo", saleorderGoodsWarrantyVo);
		mv.addObject("list", list);
		mv.addObject("page", (Page) map.get("page"));
		return mv;
	}
}
