package com.vedeng.firstengage.service;

import com.task.model.ReadFirst;
import com.vedeng.authorization.model.User;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.firstengage.model.FirstEngage;
import com.vedeng.firstengage.model.FirstEngageSearchInfo;
import com.vedeng.firstengage.model.RegistrationNumber;
import com.vedeng.goods.model.LogCheckGenerate;
import com.vedeng.goods.model.StandardCategory;

import java.util.List;
import java.util.Map;

/**
 * 首营信息
 * <p>Title: FirstEngageService</p>  
 * <p>Description: </p>  
 * @author Bill  
 * @date 2019年3月20日
 */
public interface FirstEngageService {

	/**
	 * 获取商品首营列表
	 * <p>Title: getAfterSalesPage</p>  
	 * <p>Description: </p>  
	 * @param paramMap
	 * @return  
	 * @author Bill
	 * @date 2019年3月20日
	 */
	Map<String, Object> getFirstEngageInfoListPage(Map<String, Object> paramMap, Page page, FirstEngage firstEngage);

	/**
	 * 获取搜索内容
	 * <p>Title: getFirstSearchInfo</p>  
	 * <p>Description: </p>  
	 * @param paramMap
	 * @return  
	 * @author Bill
	 * @date 2019年3月21日
	 */
	List<FirstEngageSearchInfo> getFirstSearchInfo(Map<String, Object> paramMap);

	/**
	 * 删除商品首映信息
	 * <p>Title: deleteFirstEngage</p>  
	 * <p>Description: </p>  
	 * @param paramMap
	 * @return  
	 * @author Bill
	 * @date 2019年3月21日
	 */
	Integer deleteFirstEngage(Map<String, Object> paramMap);

	/**
	 * 获取注册证信息
	 * <p>Title: getRegistrationInfoByStr</p>  
	 * <p>Description: </p>  
	 * @param paramMap
	 * @return  
	 * @author Bill
	 * @date 2019年3月21日
	 */
	List<RegistrationNumber> getRegistrationInfoByStr(Map<String, Object> paramMap);

	/**
	 * 添加注册证信息
	 * <p>Title: addFirstEngageInfo</p>  
	 * <p>Description: </p>  
	 * @param paramMap
	 * @return  
	 * @author Bill
	 * @param sessUser 
	 * @date 2019年3月21日
	 */
	Integer addFirstEngageInfo(FirstEngage firstEngage, User sessUser);

	/**
	 * 查询注册证信息
	 * <p>Title: getRegistrationNumberInfoById</p>  
	 * <p>Description: </p>  
	 * @param paramMap
	 * @return  
	 * @author Bill
	 * @date 2019年3月27日
	 */
	RegistrationNumber getRegistrationNumberInfoById(Map<String, Object> paramMap);

	/**
	 * 获取新国标分类
	 * <p>Title: getNewStandardCategory</p>  
	 * <p>Description: </p>  
	 * @param paramMap
	 * @return  
	 * @author Bill
	 * @date 2019年3月28日
	 */
	List<Map<String, Object>> getNewStandardCategory(Map<String, Object> paramMap);
	public FirstEngage getFirstSearchBaseInfo( Integer firstEngageId);
	/**
	 * 首营信息
	 * <p>Title: getFirstSearchDetail</p>  
	 * <p>Description: </p>  
	 * @param paramMap
	 * @return  
	 * @author Bill
	 * @param firstEngageId 
	 * @date 2019年4月1日
	 */
	FirstEngage getFirstSearchDetail(Map<String, Object> paramMap, Integer firstEngageId);

	/**
	 * @description 查询新国标分类
	 * @author bill
	 * @param
	 * @date 2019/4/24
	 */
    List<Map<String, Object>> getNewStandardCategoryByName(Map<String, Object> paramMap);

    /**
     * @description 新国标
     * @author bill
     * @param
     * @date 2019/5/21
     */
	List<StandardCategory> getallStandard();

	/**
	 * @description 所有公司
	 * @author bill
	 * @param
	 * @date 2019/5/21
	 */
	List<Map<String,Object>>  getallcompany(String productCompanyName) ;

	/**
	 * @description 获取要刷新的临效期的首营信息
	 * @author bill
	 * @param
	 * @date 2019/5/28
	 */
    List<RegistrationNumber> getRefreshFirstList(Map<String, Object> paramMap);

	void refreshFirstList(List<RegistrationNumber> list);

    void initFirstEngageInfo(FirstEngage firstEngage);

	ResultInfo dealstatus(Integer registrationNumberId);

    void checkFirstengage(FirstEngage firstEngage, User sessUser);

	List<LogCheckGenerate> listSkuCheckLog(Integer firstEngageId);

    Integer getOldStandardId(String cellval);


    Integer getNewStandardId(Map<String, Object> param);

    void addProductCompany(ReadFirst readFirst);

	void addRegisterNumber(ReadFirst readFirst);

	void addFirstInfo(ReadFirst readFirst);

	List<FirstEngage> test(Map<String, Object> paramMap);

    FirstEngage getFirstSearchDetailOnlyZczAttachment(Map<String, Object> paramMap,Integer firstEngageId);
}
