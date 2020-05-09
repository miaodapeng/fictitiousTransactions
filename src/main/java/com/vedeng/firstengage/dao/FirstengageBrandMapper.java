package com.vedeng.firstengage.dao;

import java.util.List;
import java.util.Map;

import com.vedeng.firstengage.model.FirstengageBrand;
import org.apache.ibatis.annotations.Param;

public interface FirstengageBrandMapper {
	
   /**
    * 首营品牌信息添加保存
    * <b>Description:</b>
    * @param record
    * @return Integer
    * @Note
    * <b>Author：</b> chuck
    * <b>Date:</b> 2019年3月25日 下午4:30:13
    */
    Integer addBrand(FirstengageBrand record);

   /**
    * 根據主鍵查詢首营品牌信息
    * <b>Description:</b>
    * @param brandId
    * @return FirstengageBrand
    * @Note
    * <b>Author：</b> chuck
    * <b>Date:</b> 2019年3月25日 下午4:30:30
    */
    FirstengageBrand getBrandByKey(Integer brandId);

   /**
    * 編輯首营品牌信息保存
    * <b>Description:</b>
    * @param record
    * @return Integer
    * @Note
    * <b>Author：</b> chuck
    * <b>Date:</b> 2019年3月25日 下午4:30:50
    */
    Integer editBrand(FirstengageBrand record);
    
    /**
     * 获取首营品牌
     * <b>Description:</b>
     * @param brand
     * @return List<FirstengageBrand>
     * @Note
     * <b>Author：</b> chuck
     * <b>Date:</b> 2019年3月25日 下午4:31:07
     */
	List<FirstengageBrand> getAllBrand(FirstengageBrand brand);
	

	/**
	 * 查询首营品牌信息（分页）
	 * <b>Description:</b>
	 * @param map
	 * @return List<FirstengageBrand>
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年3月25日 下午4:31:23
	 */
	List<FirstengageBrand> getBrandlistpage(Map<String, Object> map);
	
	/**
	 * 驗證首营品牌名稱是否重複
	 * <b>Description:</b>
	 * @param brand
	 * @return Integer
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年3月25日 下午4:31:49
	 */
	Integer vailBrandName(FirstengageBrand brand);
	
	/**
	 * 删除首营品牌信息
	 * <b>Description:</b>
	 * @param brand
	 * @return Integer
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年3月25日 下午4:32:05
	 */
	Integer delBrand(FirstengageBrand brand);
	
	Integer vailBrandGoods(FirstengageBrand brand);
	
	/**
	 * 
	 * <b>Description:</b>
	 * @return List<Map<String,Object>>
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年4月11日 下午1:20:35
	 */
	List<Map<String,Object>> getTraderSupplierAll(@Param(value = "array") String[] array);

	/**
	 * @description 查询品牌列表
	 * @author bill
	 * @param
	 * @date 2019/5/6
	 */
    List<FirstengageBrand> getBrandListByParam(Map<String, Object> paramMap);

    /**
     * @description 查询品牌对应商品数
     * @author bill
     * @param
     * @date 2019/5/6
     */
	List<Map<String, Integer>> getBrandGoodsCountByParam(Map<String, Object> paramMap);

	/**
	 * @description 获取品牌信息
	 * @author bill
	 * @param
	 * @date 2019/5/14
	 */
    List<Map<String, String>> getBrandInfoByParam(Map<String, Object> paramMap);

    /**
     * @description 生产企业
     * @author bill
     * @param
     * @date 2019/6/4
     */
    void addBrandAndManufacturer(Map<String, Object> attachmentMap);

    // 删除生产厂商
	void deleteManufacturer(@Param(value = "brandId") Integer brandId);
}