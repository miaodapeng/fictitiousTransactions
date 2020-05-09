package com.vedeng.goods.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.vedeng.authorization.model.User;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.goods.model.GoodsOpt;
import com.vedeng.goods.model.GoodsPackage;
import com.vedeng.goods.model.GoodsRecommend;
import com.vedeng.goods.service.GoodsService;

@Controller
@RequestMapping("/goods/opt")
public class GoodsOptController extends BaseController{

	@Autowired
	@Qualifier("goodsService")
	private GoodsService goodsService;
	
	
	/**
	 * <b>Description:</b><br> 查询产品基本信息及品牌单位
	 * @param request
	 * @param goodsOpt
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年6月2日 下午4:12:18
	 */
	@ResponseBody
	@RequestMapping(value="index")
	public ModelAndView index(HttpServletRequest request,GoodsOpt goodsOpt,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,@RequestParam(required = false, defaultValue = "10") Integer pageSize){
		ModelAndView mv = new ModelAndView();
		User user = getSessionUser(request);
		// begin modify by Franlin for[4219 给商品添加配件，数据未分离，搜出2个同样的商品名称] at 2018-07-04 
		if(null != goodsOpt && null != user)
		{
			goodsOpt.setCompanyId(user.getCompanyId());
		}
		// end modify by Franlin for[4219 给商品添加配件，数据未分离，搜出2个同样的商品名称] at 2018-07-04 
		Page page = getPageTag(request,pageNo,pageSize);
		
		if(goodsOpt!=null){
			if(StringUtils.isNotBlank(goodsOpt.getSearchData())){
				Map<String,Object> map = goodsService.getGoodsOptListPage(goodsOpt,page);
				mv.addObject("goodsOptList",(List<GoodsOpt>)map.get("list"));
				mv.addObject("page", (Page)map.get("page"));
			}
		}
		
		mv.addObject("goodsOpt",goodsOpt);
		
		
		mv.setViewName("goods/goods/opt_goods");
		return mv;
	}
	
	
	/**
	 * <b>Description:</b><br> 销售信息新增配件保存
	 * @param request
	 * @param categoryAttribute
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年6月2日 下午4:13:00
	 */
	@ResponseBody
	@RequestMapping(value="/saveGoodsPackage")
	public ResultInfo<?> saveGoodsPackage(HttpServletRequest request,GoodsPackage goodsPackage){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			goodsPackage.setCreator(user.getUserId());
			goodsPackage.setAddTime(DateUtil.sysTimeMillis());
		}
		
		goodsPackage.setGoodsIdArr(JSON.parseArray(request.getParameter("goodsIdArr").toString(),String.class));
		
		ResultInfo<?> result = goodsService.saveGoodsPackage(goodsPackage);
		return result;
	}
	
	/**
	 * <b>Description:</b><br> 销售信息新增关联产品保存
	 * @param request
	 * @param categoryAttribute
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年6月2日 下午4:13:00
	 */
	@ResponseBody
	@RequestMapping(value="/saveGoodsRecommend")
	public ResultInfo<?> saveGoodsRecommend(HttpServletRequest request,GoodsRecommend goodsRecommend){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			goodsRecommend.setCreator(user.getUserId());
			goodsRecommend.setAddTime(DateUtil.sysTimeMillis());
		}
		
		goodsRecommend.setGoodsIdArr(JSON.parseArray(request.getParameter("goodsIdArr").toString(),String.class));
		
		ResultInfo<?> result = goodsService.saveGoodsRecommend(goodsRecommend);
		return result;
	}
	

	/**
	 * <b>Description:</b><br> 删除配件信息
	 * @param request
	 * @param category
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年6月5日 下午3:49:25
	 */
	@ResponseBody
	@RequestMapping(value="/delGoodsPackageById")
	public ResultInfo<?> delGoodsPackageById(HttpServletRequest request,GoodsPackage goodsPackage){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			goodsPackage.setCreator(user.getUserId());
			goodsPackage.setAddTime(System.currentTimeMillis());
		}
		return goodsService.delGoodsPackageById(goodsPackage);
	}
	
	/**
	 * <b>Description:</b><br> 删除关联产品管理信息
	 * @param request
	 * @param goodsRecommend
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年6月5日 下午3:56:27
	 */
	@ResponseBody
	@RequestMapping(value="/delGoodsRecommendById")
	public ResultInfo<?> delGoodsRecommendById(HttpServletRequest request,GoodsRecommend goodsRecommend){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			goodsRecommend.setCreator(user.getUserId());
			goodsRecommend.setAddTime(System.currentTimeMillis());
		}
		return goodsService.delGoodsRecommendById(goodsRecommend);
	}
}
