package com.vedeng.system.service;

import java.util.List;
import java.util.Map;

import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.system.model.Ad;
import com.vedeng.system.model.vo.AdVo;

public interface AdService {
	
	/**
	 * <b>Description:</b><br> 获取广告列表分页
	 * @param ad
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年6月19日 下午2:53:07
	 */
	Map<String, Object> getAdListPage(AdVo ad, Page page);
	
	/**
	 * <b>Description:</b><br> 获取广告详情
	 * @param ad
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年6月19日 下午2:54:50
	 */
	Ad getAdDetail(Ad ad);
	
	/**
	 * <b>Description:</b><br> 新增或修改广告
	 * @param ad
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年6月19日 下午3:05:46
	 */
	ResultInfo<?> saveOrUpdateAd(AdVo ad);
	
	/**
	 * <b>Description:</b><br> 查询销售首页广告图片
	 * @param ad
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年6月21日 上午9:37:08
	 */
	List<AdVo> getAdVoList(AdVo ad);
	
	

}
