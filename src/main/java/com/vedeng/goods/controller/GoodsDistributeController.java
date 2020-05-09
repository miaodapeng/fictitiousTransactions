package com.vedeng.goods.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.vedeng.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.google.common.primitives.Ints;
import com.vedeng.authorization.model.User;
import com.vedeng.common.annotation.SystemControllerLog;
import com.vedeng.common.constant.CommonConstants;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.model.ResultJSON;
import com.vedeng.common.page.Page;
import com.vedeng.firstengage.model.FirstengageBrand;
import com.vedeng.goods.model.Category;
import com.vedeng.goods.model.Goods;
import com.vedeng.goods.model.GoodsChannelPrice;
import com.vedeng.goods.model.GoodsDistribute;
import com.vedeng.goods.model.StandardCategory;
import com.vedeng.goods.service.CategoryService;
import com.vedeng.goods.service.GoodsDistributeService;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.service.UserService;

/**
 * <b>Description:</b><br> 商品归属集合
 * @author porter
 * @Note 
 * <b>ProjectName:</b> dbcenter
 * <br><b>PackageName:</b> com.vedeng.controller.goods
 * <br><b>ClassName:</b> GoodsDistributeController
 * <br><b>Date:</b> 2017年6月4日 上午9:32:21
 */
@Controller
@RequestMapping("/goods/goodsdistribute")
public class GoodsDistributeController extends BaseController {
	
	@Autowired
	@Qualifier("goodsDistributeService")
	private GoodsDistributeService goodsDistributeService;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	
	/**
	 * 
	 * @Title: index 
	 * @Description: 当前商品归属信息
	 * @param request
	 * @param goodsDistribute POJO
	 * @param pageNo
	 * @param pageSize
	 * @param session
	 * @return
	 * @return: ModelAndView
	 */
	@ResponseBody
	@RequestMapping(value = "/index" )
	public ModelAndView index(HttpServletRequest request, GoodsDistribute goodsDistribute,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize, HttpSession session) {
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
	    
		ModelAndView mv = new ModelAndView();
		pageSize=100;
		Page page = getPageTag(request, pageNo, pageSize);
		// 参数集
		Map<String, Object> paramMap = new HashMap<>();
		//拼接分页函数
		paramMap.put("page", page);
		//V_SOCRE_SPU商品归属列表(分页)
		if(goodsDistribute!=null){
			if(StringUtil.isNotBlank(goodsDistribute.getSpuNo())) {
				goodsDistribute.setSpuNo(goodsDistribute.getSpuNo().trim());
				if(StringUtil.isNumeric(goodsDistribute.getSpuNo())){
					goodsDistribute.setSpuId(Integer.valueOf(goodsDistribute.getSpuNo()));
				}else{
					goodsDistribute.setSpuId(null);
				}
			}
			if(StringUtil.isNotBlank(goodsDistribute.getSpuName())){
				goodsDistribute.setSpuName(goodsDistribute.getSpuName().trim());
			}
			if(StringUtil.isNotBlank(goodsDistribute.getBrandName())){
				goodsDistribute.setBrandName(goodsDistribute.getBrandName().trim());
			}
			if(StringUtil.isNotBlank(goodsDistribute.getSpuName())){
				goodsDistribute.setSpuName(goodsDistribute.getSpuName().trim());
			}
			if(goodsDistribute.getCategoryLv3Name()!=null){
			    goodsDistribute.setCategoryLevel(3);
            }else if(goodsDistribute.getCategoryLv2Name()!=null){
			    goodsDistribute.setCategoryLevel(2);
            }else if(goodsDistribute.getCategoryLv1Name()!=null){
			    goodsDistribute.setCategoryLevel(1);
            }
		}

		Map<String, Object> distributemap = goodsDistributeService
				  .getGoodsDistributeListPage(goodsDistribute, page);

		  //所有的分配人
        List<User> assUser = userService.selectAllAssignUser();
		//所属产品经理
		mv.addObject("managerUser", assUser);
		//所属产品助理
		mv.addObject("assUser", assUser);
        //pojo
		mv.addObject("goodsDistribute", goodsDistribute);

		mv.addObject("distributelist", distributemap.get("list"));
		mv.addObject("page", (Page)distributemap.get("page"));
		mv.setViewName("goodsdribute/goodsdribute");
		return mv ;
	}
	
	/**
	 * 
	 * @Title: editBrand 
	 * @Description: 商品分配经理和助理
	 * @param request
	 * @param beforeParams
	 * @param goodsDistribute
	 * @return
	 * @return: ResultJSON
	 */
	@ResponseBody
	@RequestMapping(value = "/addDistribution")
	@SystemControllerLog(operationType = "edit", desc = "分配商品归属")
	public ResultJSON addDistribution(HttpServletRequest request, String beforeParams, GoodsDistribute goodsDistribute)
	{
		try {
			goodsDistributeService.addDistribution(goodsDistribute);

		} catch (Exception e) {
			
			return ResultJSON.failed().message(e.getMessage());
		}
		return ResultJSON.success().message("分配归属人成功");
		
	}
	
	/**
	 * 
	 * @Title: editDistribution 
	 * @Description: 归属人更换
	 * @param request
	 * @param beforeParams
	 * @param goodsDistribute
	 * @return
	 * @return: ResultJSON
	 */
	@ResponseBody
	@RequestMapping(value = "/editDistribution" )
	public ResultJSON editDistribution(HttpServletRequest request, String beforeParams, GoodsDistribute goodsDistribute)
	{
		//更换前归属助理和更换后归属助理关系错误
		if(null !=goodsDistribute.getAssignmentAssistantIdOld() && 
				 null ==goodsDistribute.getAssignmentAssistantIdNew() || null ==goodsDistribute.getAssignmentAssistantIdOld() 
				                            && null !=goodsDistribute.getAssignmentAssistantIdNew()  )
		{
			return ResultJSON.failed().message("已选内容对应关系错误，请重新选择");
		}
		//更换前归属经理和更换后归属经理关系错误

		else
			if(null !=goodsDistribute.getAssignmentManagerIdOld() && 
					 null ==goodsDistribute.getAssignmentManagerIdNew() || null ==goodsDistribute.getAssignmentManagerIdOld() 
                     && null !=goodsDistribute.getAssignmentManagerIdNew())
		{
				return ResultJSON.failed().message("已选内容对应关系错误，请重新选择");

		}
		else
		{
			//更换归属经理和助理
			goodsDistributeService.editDistribution(goodsDistribute);
			
		}
		return ResultJSON.success().message("归属人更换成功");
		
	}
	
	
	/**
	 * 
	 * @Title: isHaveDistribution 
	 * @Description: 是否包含已经分配过归属人的商品
	 * @param request
	 * @param beforeParams
	 * @param goodsDistribute
	 * @return
	 * @return: ResultJSON
	 */
	@ResponseBody
	@RequestMapping(value = "/isHaveDistribution" )
	public ResultJSON isHaveDistribution(HttpServletRequest request, GoodsDistribute goodsDistribute)
	{
		Map<String,Object> distributeAdd = new HashMap<String,Object>();

		try {

			//分割SPUID
			if(null != goodsDistribute.getSpuIds())
			{
				String spuIds = goodsDistribute.getSpuIds();
				String[] strArr = spuIds.split("@");
				int[] intArr = new int[strArr.length];
				for(int a=0;a<strArr.length;a++){
					intArr[a] = Integer.valueOf(strArr[a]);  //然后遍历字符串数组，使用包装类Integer的valueOf方法将字符串转为整型
					}
				List<Integer> spuIdList = Ints.asList(intArr);
				
				
				distributeAdd.put("spuIdList", spuIdList);
				List<GoodsDistribute> goodsDistributeList = goodsDistributeService.isHaveDistribution(distributeAdd);
				//判断是否已经分配过经理和助理
				if(!goodsDistributeList.isEmpty())
				{
					for (int i = 0; i < goodsDistributeList.size(); i++) 
					{ 
					    if(null != goodsDistributeList.get(i).getAssignmentAssistantId() || 
						 null!= goodsDistributeList.get(i).getAssignmentManagerId())
					    {
					    	//return ResultJSON.success().message("所选数据中部分商品已有归属人，提交后将覆盖");

					    	//JSONObject jsStr = JSONObject.parseObject(requestBody);
					    	//return ResultJSON.success().message(requestBody);
				            return ResultJSON.success().data(1).message(CommonConstants.SUCCESS_MSG);

					    	
					    }
					}
				}

				
			}  
			
		
			

		} catch (Exception e) {
			
			return ResultJSON.failed().message(e.getMessage());
		}
		return ResultJSON.success().data(2).message("操作成功");
		
	}

}
  
