package com.vedeng.firstengage.service;

import java.util.List;
import java.util.Map;

import com.vedeng.common.exception.ShowErrorMsgException;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.firstengage.model.FirstengageBrand;
import org.springframework.mail.MailParseException;

import javax.servlet.http.HttpServletRequest;

/**
 * <b>Description:</b><br> 品牌管理接口
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.goods.service
 * <br><b>ClassName:</b> BrandService
 * <br><b>Date:</b> 2017年5月15日 上午10:09:21
 */
public interface FirstengageBrandService extends BaseService {
	/**
	 * 获取所有首营品牌
	 * <b>Description:</b>
	 * @param brand
	 * @return List<FirstengageBrand>
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年3月25日 下午4:28:22
	 */
	List<FirstengageBrand> getAllBrand(FirstengageBrand brand);
	
	/**
	 * 查询所有首营品牌信息（分页）
	 * <b>Description:</b>
	 * @param brand
	 * @param page
	 * @return Map<String,Object>
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年3月25日 下午4:28:36
	 */
	Map<String,Object> getBrandListPage(FirstengageBrand brand,Page page);
	
	/**
	 * 首营品牌信息添加保存
	 * <b>Description:</b>
	 * @param brand
	 * @return ResultInfo<?>
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年3月25日 下午4:28:55
	 */
	ResultInfo<?> saveBrand(FirstengageBrand brand);
	
	/**
	 * 根據主鍵查詢首营品牌信息
	 * <b>Description:</b>
	 * @param brand
	 * @return FirstengageBrand
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年3月25日 下午4:29:15
	 */
	FirstengageBrand getBrandByKey(FirstengageBrand brand);
	
	/**
	 * 編輯首营品牌信息保存
	 * <b>Description:</b>
	 * @param brand
	 * @return ResultInfo<?>
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年3月25日 下午4:29:33
	 */
	ResultInfo<?> editBrand(FirstengageBrand brand);
	
	/**
	 * 根据主键删除首营品牌信息
	 * <b>Description:</b>
	 * @param brand
	 * @return ResultInfo<?>
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年3月25日 下午4:29:52
	 */
	ResultInfo<?> delBrand(FirstengageBrand brand);
	
	/**
	 * 
	 * <b>Description:</b>查询所有厂商名称（全量提供给前端）
	 * @return List<Map<String,Object>>
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年4月11日 下午1:18:38
	 */
	public List<Map<String,Object>> getTraderSupplierAll(String[] str);

	/**
	 * @description 品牌详情
	 * @author bill
	 * @param
	 * @date 2019/5/5
	 */
	FirstengageBrand getBrandByParam(Integer brandId);

	/**
	 * @description 查询导出的品牌列表
	 * @author bill
	 * @param
	 * @date 2019/5/6
	 */
	List<FirstengageBrand> getBrandListByParam(HttpServletRequest request, Map<String, Object> paramMap);

	/**
	 * @description 根据品牌名称查询品牌信息
	 * @author bill
	 * @param
	 * @date 2019/5/14
	 */
	List<Map<String, String>> getBrandInfoByParam(Map<String, Object> paramMap);

	/**
	 * @description 校验参数
	 * @author bill
	 * @param
	 * @date 2019/5/21
	 */
    void initBrand(FirstengageBrand brand) throws ShowErrorMsgException;
}
