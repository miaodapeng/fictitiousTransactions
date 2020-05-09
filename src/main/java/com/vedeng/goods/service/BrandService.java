package com.vedeng.goods.service;

import java.util.List;
import java.util.Map;

import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.goods.model.Brand;

/**
 * <b>Description:</b><br> 品牌管理接口
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.goods.service
 * <br><b>ClassName:</b> BrandService
 * <br><b>Date:</b> 2017年5月15日 上午10:09:21
 */
public interface BrandService extends BaseService {
	/**
	 * <b>Description:</b><br> 获取所有品牌
	 * @param brand
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年5月15日 上午10:09:18
	 */
	List<Brand> getAllBrand(Brand brand);
	
	/**
	 * <b>Description:</b><br> 查询所有品牌信息（分页）
	 * @param brand
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月16日 下午4:47:04
	 */
	Map<String,Object> getBrandListPage(Brand brand,Page page);
	
	/**
	 * <b>Description:</b><br> 品牌信息添加保存
	 * @param brand
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月16日 下午6:03:14
	 */
	ResultInfo<?> saveBrand(Brand brand);
	
	/**
	 * <b>Description:</b><br> 根據主鍵查詢品牌信息
	 * @param brand
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月16日 下午6:03:14
	 */
	Brand getBrandByKey(Brand brand);
	
	/**
	 * <b>Description:</b><br> 編輯品牌信息保存
	 * @param brand
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月17日 上午8:47:36
	 */
	ResultInfo<?> editBrand(Brand brand);
	
	/**
	 * <b>Description:</b><br> 根据主键删除品牌信息
	 * @param brand
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月17日 上午10:59:30
	 */
	ResultInfo<?> delBrand(Brand brand);
}
