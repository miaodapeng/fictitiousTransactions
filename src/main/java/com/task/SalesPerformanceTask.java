package com.task;

import com.common.constants.Contant;
import com.fasterxml.jackson.core.type.TypeReference;
import com.task.service.SalesPerformanceTaskService;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.model.SearchModel;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.system.service.UserService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.*;

public class SalesPerformanceTask extends BaseTaskController{
	WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
	private SalesPerformanceTaskService salesPerformanceTaskService = (SalesPerformanceTaskService) context.getBean("salesPerformanceTaskService");
	private UserService userService = (UserService) context.getBean("userService");
	public static Logger logger = LoggerFactory.getLogger(OldDataRepair.class);

	
	public void dataTask(){
		this.saleorders();
		this.saleBrand();
		System.out.println("task run"+DateUtil.gainNowDate());
	}
	
	public void dataNextTask(){
        this.traderCustomer();
        this.saleComm();
        this.saleRate();
        this.personalSort();
        this.generateSaleorderDsata();
        System.out.println("task next run"+DateUtil.gainNowDate());
    }

	public void callTask(){
		this.callRecordInfoSync();
	}
	
	public void sort(){
		System.out.println("task run "+DateUtil.gainNowDate());
		/*SearchModel searchModel = new SearchModel();
		searchModel.setCompanyId(1);
		
		this.saleorderSort();
		this.brandSort();
		this.SalesPerformanceTraderSort(searchModel);
		this.SalesPerformanceCommSort(searchModel);
		this.SalesPerformanceRateSort(searchModel);
		this.personalSort();*/
	}
	
	/**
	 * <b>Description:</b><br> 销售业绩客户明细
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年6月4日 下午2:30:44
	 */
	@Test
	public void traderCustomer(){
		try {
			//基本客户明细
			Map<Integer,Integer> typeMap = new TreeMap<>();//90天、150天
			typeMap.put(1, 91);//90天销售业绩客户明细
			typeMap.put(2, 181);//180天销售业绩客户明细
			
			for (Map.Entry<Integer, Integer> entry : typeMap.entrySet()) { 
				Integer type = entry.getKey();//类型1：90天2:180天
				Integer day = entry.getValue();//天数
				SearchModel searchModel = new SearchModel();
				long startDateLong = DateUtil.getDateBefore(new Date(), day);//获取X天前的时间戳
				long endDateLong = DateUtil.gainNowDate();//当前时间戳
				if(type == 2){
					endDateLong = DateUtil.getDateBefore(new Date(), 91);//获取X天前的时间戳
				}
				
				searchModel.setStartDateLong(startDateLong);
				searchModel.setEndDateLong(endDateLong);
				
				searchModel.setCompanyId(1);
				searchModel.setRelateData(type);//类型1：90天2:180天
				
				Page page = Page.newBuilder(1, ErpConst.EXPORT_DATA_LIMIT_5,"");
				Map<String, Object> map = this.getSalesPerformanceTraderListPage(searchModel, page);
				if(map != null && map.get("page") != null){
					Page pageInfo = (Page) map.get("page");
					Integer total = pageInfo.getTotalPage();
					for (int i = 2; i <= total; i++) {//循环调取
						pageInfo.setPageNo(i);
						map = this.getSalesPerformanceTraderListPage(searchModel, page);
					}
				}
			}
			
			//客户得分排名刷新
			SearchModel searchSortModel = new SearchModel();
			searchSortModel.setCompanyId(1);
			this.SalesPerformanceTraderSort(searchSortModel);
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
	}
	
	/**
	 * <b>Description:</b><br> 分页处理销售业绩客户明细
	 * @param searchModel
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年6月6日 上午11:27:47
	 */
	private Map<String, Object> getSalesPerformanceTraderListPage(SearchModel searchModel, Page page) {
		
		
		String url = httpUrl + "sales/salesperformancetask/getsalesperformancetraderlistpage.htm";
		final TypeReference<ResultInfo> TypeRef = new TypeReference<ResultInfo>() {};
		try {
		    ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, searchModel, clientId, clientKey, TypeRef,page);
		    if(null != result && 0 == result.getCode()){
			    page = result.getPage();
				Map<String, Object> map = new HashMap<>();
				map.put("page", page);
				return map;
		    }else{
		    	return null;
		    }
		} catch (IOException e) {
		    logger.error(Contant.ERROR_MSG, e);
		    throw new RuntimeException();
		}
	}
	
	/**
	 * <b>Description:</b><br> 销售客户得分排名刷新
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年6月6日 下午1:43:56
	 */
	private void SalesPerformanceTraderSort(SearchModel searchModel){
		
		List<User> userList = userService.getSaleUserOrgList(1);
		searchModel.setUserList(userList);
		
		String url = httpUrl + "sales/salesperformancetask/salesperformancetradersort.htm";
		final TypeReference<ResultInfo> TypeRef = new TypeReference<ResultInfo>() {};
		try {
		    ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, searchModel, clientId, clientKey, TypeRef);
		} catch (IOException e) {
		    logger.error(Contant.ERROR_MSG, e);
		    throw new RuntimeException();
		}
	}
	
	/**
	 * <b>Description:</b><br> 通话时长
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年6月4日 下午2:30:50
	 */
	@Test
	public void saleComm(){
		try {
			//本月通话记录信息补充
			this.callRecordInfoSync();
			
			//类型1：未成交客户2：初次成交客户3：老客户
			List<Integer> typeList = new ArrayList<>();
			typeList.add(1);
			typeList.add(2);
			typeList.add(3);
			typeList.add(4);//不是客户
			
			for (Integer type : typeList) { 
				SearchModel searchModel = new SearchModel();
				Page page = Page.newBuilder(1, ErpConst.EXPORT_DATA_LIMIT_5,"");
				
				//获取前一天的月份第一天
				//获取前一天的日
				String dateString = DateUtil.convertString(DateUtil.getDateBefore(new Date(), 1), "yyyy-MM-dd");
				//获取前一天的所在月的第一日
				String yearMonth = DateUtil.getFirstDayOfGivenMonth(dateString, "yyyy-MM-dd");
//				String dateString = DateUtil.convertString(DateUtil.getDateBefore(new Date(), 1), "yyyy-MM-dd");
				long startTime = DateUtil.convertLong(yearMonth, "yyyy-MM-dd");
				long endTime = DateUtil.sysTimeMillis();
				
				searchModel.setStartDateLong(startTime);
				searchModel.setEndDateLong(endTime);
				searchModel.setRelateData(type);
				searchModel.setCompanyId(1);
				
				
				Map<String, Object> map = this.getSalesPerformanceCommListPage(searchModel, page);
				if(map != null && map.get("page") != null){
					Page pageInfo = (Page) map.get("page");
					Integer total = pageInfo.getTotalPage();
					for (int i = 2; i <= total; i++) {//循环调取
						pageInfo.setPageNo(i);
						map = this.getSalesPerformanceCommListPage(searchModel, page);
					}
				}
			}
			
			//得分排名刷新
			SearchModel searchSortModel = new SearchModel();
			searchSortModel.setCompanyId(1);
			this.SalesPerformanceCommSort(searchSortModel);
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
	}
	
	/**
	 * <b>Description:</b><br> 分页处理销售业绩通话明细
	 * @param searchModel
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年6月8日 上午10:20:39
	 */
	private Map<String, Object> getSalesPerformanceCommListPage(SearchModel searchModel, Page page) {
		
		
		String url = httpUrl + "sales/salesperformancetask/getsalesperformancecommlistpage.htm";
		final TypeReference<ResultInfo> TypeRef = new TypeReference<ResultInfo>() {};
		try {
		    ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, searchModel, clientId, clientKey, TypeRef,page);
		    if(null != result && 0 == result.getCode()){
			    page = result.getPage();
				Map<String, Object> map = new HashMap<>();
				map.put("page", page);
				return map;
		    }else{
		    	return null;
		    }
		} catch (IOException e) {
		    logger.error(Contant.ERROR_MSG, e);
		    throw new RuntimeException();
		}
	}
	
	/**
	 * <b>Description:</b><br> 呼叫中心本月通话信息补充（通话时长、录音地址)
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年6月8日 上午9:08:13
	 */
	private void callRecordInfoSync(){
		SearchModel searchModel = new SearchModel();
		Page page = new Page(1, ErpConst.EXPORT_DATA_LIMIT);
		//获取前一天的月份第一天
		//获取前一天的日
		String dateString = DateUtil.convertString(DateUtil.getDateBefore(new Date(), 1), "yyyy-MM-dd");
		//获取前一天的所在月的第一日
		String yearMonth = DateUtil.getFirstDayOfGivenMonth(dateString, "yyyy-MM-dd");
		long startTime = DateUtil.convertLong(yearMonth, "yyyy-MM-dd");
		long endTime = DateUtil.sysTimeMillis();
		
		searchModel.setStartDateLong(startTime);
//		searchModel.setStartDateLong(new Long("1518167093852"));
		searchModel.setEndDateLong(endTime);
		try {
			salesPerformanceTaskService.callRecordInfoSync(searchModel,page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(Contant.ERROR_MSG, e);
		}
	}
	
	/**
	 * <b>Description:</b><br> 通话排名 
	 * @param searchModel
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年6月12日 上午9:26:09
	 */
	private void SalesPerformanceCommSort(SearchModel searchModel){
		
		List<User> userList = userService.getSaleUserOrgList(1);
		searchModel.setUserList(userList);
		
		String url = httpUrl + "sales/salesperformancetask/salesperformancecommsort.htm";
		final TypeReference<ResultInfo> TypeRef = new TypeReference<ResultInfo>() {};
		try {
		    ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, searchModel, clientId, clientKey, TypeRef);
		} catch (IOException e) {
		    logger.error(Contant.ERROR_MSG, e);
		    throw new RuntimeException();
		}
	}
	
	/**
	 * <b>Description:</b><br> 转化率
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年6月4日 下午2:30:56
	 */
	@Test
	public void saleRate(){
		try {
			SearchModel searchModel = new SearchModel();
			long startDateLong = DateUtil.getDateBefore(new Date(), 181);//获取X天前的时间戳
			long endDateLong = DateUtil.gainNowDate();//当前时间戳
			
			searchModel.setStartDateLong(startDateLong);
			searchModel.setEndDateLong(endDateLong);
			
			searchModel.setCompanyId(1);
			
			Page page = Page.newBuilder(1, ErpConst.EXPORT_DATA_LIMIT_5,"");
			Map<String, Object> map = this.getSalesPerformanceRateListPage(searchModel, page);
			if(map != null && map.get("page") != null){
				Page pageInfo = (Page) map.get("page");
				Integer total = pageInfo.getTotalPage();
				for (int i = 2; i <= total; i++) {//循环调取
					pageInfo.setPageNo(i);
					map = this.getSalesPerformanceRateListPage(searchModel, page);
				}
			}
			
			//得分排名刷新
			SearchModel searchSortModel = new SearchModel();
			searchSortModel.setCompanyId(1);
			this.SalesPerformanceRateSort(searchSortModel);
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
	}
	
	/**
	 * <b>Description:</b><br> 分页处理销售转化率
	 * @param searchModel
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年6月7日 下午5:09:33
	 */
	private Map<String, Object> getSalesPerformanceRateListPage(SearchModel searchModel, Page page) {
		
		
		String url = httpUrl + "sales/salesperformancetask/getsalesperformanceratelistpage.htm";
		final TypeReference<ResultInfo> TypeRef = new TypeReference<ResultInfo>() {};
		try {
		    ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, searchModel, clientId, clientKey, TypeRef,page);
		    if(null != result && 0 == result.getCode()){
			    page = result.getPage();
				Map<String, Object> map = new HashMap<>();
				map.put("page", page);
				return map;
		    }else{
		    	return null;
		    }
		} catch (IOException e) {
		    logger.error(Contant.ERROR_MSG, e);
		    throw new RuntimeException();
		}
	}
	
	/**
	 * <b>Description:</b><br> 转化率排名
	 * @param searchModel
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年6月12日 上午9:26:56
	 */
	private void SalesPerformanceRateSort(SearchModel searchModel){
		
		List<User> userList = userService.getSaleUserOrgList(1);
		searchModel.setUserList(userList);
		
		String url = httpUrl + "sales/salesperformancetask/salesperformanceratesort.htm";
		final TypeReference<ResultInfo> TypeRef = new TypeReference<ResultInfo>() {};
		try {
		    ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, searchModel, clientId, clientKey, TypeRef);
		} catch (IOException e) {
		    logger.error(Contant.ERROR_MSG, e);
		    throw new RuntimeException();
		}
	}
	
	/**
	 * <b>Description:</b><br> 业绩明细
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2018年6月4日 下午5:43:41
	 */
	@Test
	public void saleorders(){
		
		SearchModel searchModel = new SearchModel();
		searchModel.setCompanyId(1);
		Page page = new Page(1, ErpConst.EXPORT_DATA_LIMIT_5);
		String url = httpUrl + "sales/salesperformancetask/getsaleorderslistpage.htm";
		final TypeReference<ResultInfo> TypeRef = new TypeReference<ResultInfo>() {};
		try {
		    ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, searchModel, clientId, clientKey, TypeRef);
		    /*page = result.getPage();
			Map<String, Object> map = new HashMap<>();
			map.put("page", page);
			return map;*/
		} catch (IOException e) {
		    logger.error(Contant.ERROR_MSG, e);
		    throw new RuntimeException();
		}
		
		//业绩得分排名刷新
		this.saleorderSort();
		
		/*
		try {
			SearchModel searchModel = new SearchModel();
			Page page = new Page(1, ErpConst.EXPORT_DATA_LIMIT_5);
			ResultInfo resultInfo = salesPerformanceTaskService.updateSaleorders(searchModel,page);
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}*/
	}
	
	/**
	 * <b>Description:</b><br> 品牌
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2018年6月4日 下午5:44:36
	 */
	@Test
	public void saleBrand(){
//		String http_url = resource.getString("http_url");
//		String client_id = resource.getString("client_id");
//		String client_key = resource.getString("client_key");
//		
//		SearchModel searchModel = new SearchModel();
//		searchModel.setCompanyId(1);
//		Page page = new Page(1, ErpConst.EXPORT_DATA_LIMIT_5);
//		String url = http_url + "sales/salesperformancetask/getsalebrandlistpage.htm";
//		final TypeReference<ResultInfo> TypeRef = new TypeReference<ResultInfo>() {};
//		try {
//		    ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, searchModel, client_id, client_key, TypeRef);
//		    /*page = result.getPage();
//			Map<String, Object> map = new HashMap<>();
//			map.put("page", page);
//			return map;*/
//		} catch (IOException e) {
//		    logger.error(Contant.ERROR_MSG, e);
//		    throw new RuntimeException();
//		}
		
		//品牌得分排名刷新
		this.brandSort();
	}
	
	/**
	 * <b>Description:</b><br> 更新业绩额排名
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2018年6月15日 上午10:43:29
	 */
	@Test
	public void saleorderSort(){
		
		SearchModel searchModel = new SearchModel();
		searchModel.setCompanyId(1);
		
		
		String url = httpUrl + "sales/salesperformancetask/updatesaleordersort.htm";
		final TypeReference<ResultInfo> TypeRef = new TypeReference<ResultInfo>() {};
		try {
		    ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, searchModel, clientId, clientKey, TypeRef);
		    /*page = result.getPage();
			Map<String, Object> map = new HashMap<>();
			map.put("page", page);
			return map;*/
		} catch (IOException e) {
		    logger.error(Contant.ERROR_MSG, e);
		    throw new RuntimeException();
		}
	}
	
	/**
	 * <b>Description:</b><br> 更新品牌排名
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2018年6月15日 上午10:43:49
	 */
	@Test
	public void brandSort(){
		
		SearchModel searchModel = new SearchModel();
		searchModel.setCompanyId(1);
		String url = httpUrl + "sales/salesperformancetask/updatebrandsort.htm";
		final TypeReference<ResultInfo> TypeRef = new TypeReference<ResultInfo>() {};
		try {
		    ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, searchModel, clientId, clientKey, TypeRef);
		    /*page = result.getPage();
			Map<String, Object> map = new HashMap<>();
			map.put("page", page);
			return map;*/
		} catch (IOException e) {
		    logger.error(Contant.ERROR_MSG, e);
		    throw new RuntimeException();
		}
	}
	
	/**
	 * <b>Description:</b><br> 综合排名
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年6月20日 上午11:26:44
	 */
	public void personalSort(){
		//List<User> userList = userService.getSaleUserOrgList(1);
		SearchModel searchModel = new SearchModel();
		searchModel.setCompanyId(1);
		//searchModel.setUserList(userList);
		
		String url = httpUrl + "sales/salesperformancetask/salesperformancepersonalsort.htm";
		final TypeReference<ResultInfo> TypeRef = new TypeReference<ResultInfo>() {};
		try {
		    ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, searchModel, clientId, clientKey, TypeRef);
		} catch (IOException e) {
		    logger.error(Contant.ERROR_MSG, e);
		    throw new RuntimeException();
		}
	}
	
	@Test
	public void generateSaleorderDsata(){
		SearchModel searchModel = new SearchModel();
		searchModel.setCompanyId(1);
		try {
			//HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			//HttpServletResponse response = ((ServletWebRequest)RequestContextHolder.getRequestAttributes()).getResponse();
			salesPerformanceTaskService.generateSaleorderData(searchModel);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(Contant.ERROR_MSG, e);
		}
	}

}
