/**   
 * Copyright © 2019 公司名. All rights reserved.
 * 
 * @Title: GoodsDistributeServiceImpl.java 
 * @Prject: erp.vedeng.com
 * @Package: com.vedeng.goods.service.impl 
 * @Description: TODO
 * @author: vedeng   
 * @date: 2019年6月4日 下午1:41:26 
 * @version: V1.0   
 */
package com.vedeng.goods.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.common.primitives.Ints;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.firstengage.dao.FirstEngageMapper;
import com.vedeng.firstengage.model.FirstEngage;
import com.vedeng.goods.dao.GoodsDistributeMapper;
import com.vedeng.goods.model.Goods;
import com.vedeng.goods.model.GoodsDistribute;
import com.vedeng.goods.service.GoodsDistributeService;

/** 
 * @ClassName: GoodsDistributeServiceImpl 
 * @Description: TODO
 * @author: vedeng
 * @date: 2019年6月4日 下午1:41:26  
 */       
@Service("goodsDistributeService")
public class GoodsDistributeServiceImpl extends BaseServiceimpl implements GoodsDistributeService  {


	
	@Autowired
	@Qualifier("goodsDistributeMapper")
	private GoodsDistributeMapper goodsDistributeMapper;
	/* 
	 * @Title: getGoodsListPage
	 * @Description: 获取商品归属列表
	 * @param request
	 * @param goods
	 * @param page
	 * @param session
	 * @return 
	 * @see com.vedeng.goods.service.GoodsDistributeService#getGoodsListPage(javax.servlet.http.HttpServletRequest, com.vedeng.goods.model.Goods, com.vedeng.common.page.Page, javax.servlet.http.HttpSession) 
	 */
	@Override
	public Map<String, Object> getGoodsDistributeListPage(GoodsDistribute goodsDistribute,Page page) {
		
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("page", page);
		queryMap.put("goodsDistribute", goodsDistribute);
		//产品分类数据组装
//				if(StringUtils.isNotBlank(goodsDistribute.getCategoryName()))
//				{
//					String categoryName = goodsDistribute.getCategoryName();
//					// 字符串转list<String>
//					List<String> categorylist = Arrays.asList(categoryName.split("@"));
//					queryMap.put("categorylist", categorylist);
//
//				}
		//归属产品经理复选
				if(StringUtils.isNotBlank(goodsDistribute.getManagerUsername()))
				{
					String managerUsername = goodsDistribute.getManagerUsername();
					List<String> managerlist = Arrays.asList(managerUsername.split("@"));
					queryMap.put("managerUseIDsList", managerlist);
				}
		//归属助理复选
				if(StringUtils.isNotBlank(goodsDistribute.getAssUsername()))
				{
					String assUsername = goodsDistribute.getAssUsername();
					List<String> asslist = Arrays.asList(assUsername.split("@"));
					queryMap.put("assUserIDsList", asslist);
				}
					
				
		//分页商品归属列表
		List <GoodsDistribute>  list = goodsDistributeMapper.getGoodsDistributeListPage(queryMap);
		Map<String,Object> distributemap = new HashMap<String,Object>();
		distributemap.put("list", list);
		distributemap.put("page", page);
		return distributemap;
	}
	
	

	@Override
	public void addDistribution(GoodsDistribute goodsDistribute) {
		// TODO Auto-generated method stub
		Map<String,Object> distributeAdd = new HashMap<String,Object>();
		//分配经理
		if(null != goodsDistribute.getAssignmentManagerId() )
		{
			distributeAdd.put("assignmentManagerId", goodsDistribute.getAssignmentManagerId());

		}
		//分配助理
		if(null != goodsDistribute.getAssignmentAssistantId() )
		{
			distributeAdd.put("assignmentAssistantId", goodsDistribute.getAssignmentAssistantId());

		}
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
			
		}                      

		goodsDistributeMapper.addDistribution(distributeAdd);	
	}



	/* (non Javadoc) 
	 * @Title: editDistribution
	 * @Description: TODO
	 * @param goodsDistribute 
	 * @see com.vedeng.goods.service.GoodsDistributeService#editDistribution(com.vedeng.goods.model.GoodsDistribute) 
	 */
	@Override
	public void editDistribution(GoodsDistribute goodsDistribute) {
		Map<String,Object> distributeAdd = new HashMap<String,Object>();
		
	    distributeAdd.put("goodsDistribute", goodsDistribute);
	    //判断更换前和后都为NUll的情况
	    if(null !=goodsDistribute.getAssignmentManagerIdOld() && null != goodsDistribute.getAssignmentManagerIdNew() )
	    {
	    	 //更换归属经理
			goodsDistributeMapper.editDistributionM(distributeAdd);
	    }
	    if(null !=goodsDistribute.getAssignmentAssistantIdOld() && null != goodsDistribute.getAssignmentAssistantIdNew() )
	    {
			//更换归属助理
			goodsDistributeMapper.editDistributionA(distributeAdd);
	    }


		
	}
	
	
	
	public List<GoodsDistribute> isHaveDistribution(Map<String,Object> queryMap)
	{
		return goodsDistributeMapper.isHaveDistribution(queryMap);
		
	}

	
}
