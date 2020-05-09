package com.vedeng.trader.dao;

import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.apache.ibatis.annotations.Param;

import com.vedeng.authorization.model.User;
import com.vedeng.trader.model.CommunicateRecord;

@Named("communicateRecordMapper")
public interface CommunicateRecordMapper {

    /**
     * <b>Description:</b><br> 新增沟通
     * @param communicateRecord
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年5月23日 下午4:49:22
     */
    int insert(CommunicateRecord communicateRecord);

    /**
     * <b>Description:</b><br> 编辑沟通
     * @param communicateRecord
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年5月23日 下午4:49:29
     */
    int update(CommunicateRecord communicateRecord);

    /**
     * <b>Description:</b><br> 沟通记录
     * @param map
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年5月23日 上午9:33:20
     */
    List<CommunicateRecord> getCommunicateRecordListPage(Map<String, Object> map);
    
    /**
     * <b>Description:</b><br> 沟通记录(不分页)
     * @param communicateRecord
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年8月24日 上午11:34:38
     */
    List<CommunicateRecord> getCommunicateRecordList(Map<String, Object> map);
    
    /**
     * <b>Description:</b><br> 查询沟通
     * @param communicateRecord
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年5月24日 下午1:38:07
     */
    CommunicateRecord getCommunicate(CommunicateRecord communicateRecord);
    /**
     * <b>Description:</b><br> 查询最近一次沟通记录
     * @param communicateRecord
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年5月24日 下午1:38:07
     */
    CommunicateRecord getLastCommunicate(CommunicateRecord communicateRecord);
    /**
     * <b>Description:</b><br> 批量查询最近一次沟通记录
     * @param communicateRecord
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年5月24日 下午1:38:07
     */
    List<CommunicateRecord> getLastCommunicateList(CommunicateRecord communicateRecord);
    /**
     * 根据外检ID查询最后一次的沟通记录
     * <b>Description:</b><br> 
     * @return
     * @Note
     * <b>Author:</b> Cooper
     * <br><b>Date:</b> 2018年8月8日 上午9:48:58
     */
    List<CommunicateRecord> getLastCommunicateListById(@Param("list")List<Integer> list,@Param("communicateType")Integer communicateType);
    /**
     * <b>Description:</b><br> 更新历史沟通（处理状态）
     * @param communicateRecord
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年6月23日 下午1:39:53
     */
    Integer updateCommunicateDone(CommunicateRecord communicateRecord);
    
    /**
     * <b>Description:</b><br> 外键ID查询沟通记录
     * @param list
     * @param communicateType
     * @return
     * @Note
     * <b>Author:</b> duke
     * <br><b>Date:</b> 2017年7月17日 下午5:58:22
     */
    List<CommunicateRecord> getCommunicateRecord(@Param("keyList")List<Integer> list,@Param("communicateType")String communicateType);
    /**
     * <b>Description:</b><br> 外键ID查询沟通记录条数
     * @param quoteorderId
     * @param bussinessChanceId
     * @return
     * @Note
     * <b>Author:</b> duke
     * <br><b>Date:</b> 2017年6月23日 下午4:01:52
     */
    Integer getCommunicateRecordCount(@Param("communicateRecord")CommunicateRecord communicateRecord,@Param("bussinessType")Integer bussinessType,@Param("quoteType")Integer quoteType);
    
    /**
     * <b>Description:</b><br> 根据沟通日期范围查询沟通记录
     * @param beginDate
     * @param endDate
     * @return
     * @Note
     * <b>Author:</b> duke
     * <br><b>Date:</b> 2017年6月26日 上午10:28:25
     */
    List<Integer> getCommunicateRecordByDate(@Param("beginDate") Long beginDate,@Param("endDate") Long endDate,@Param("communicateType")String communicateType);
	/**
	 * <b>Description:</b><br> 根据外键查询沟通记录（分页）
	 * @param relatedIds
	 * @param communicateType
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月5日 上午11:36:22
	 */
    List<CommunicateRecord> getQuoteCommunicateListPage(Map<String, Object> map);
    
    /**
     * <b>Description:</b><br> 获取沟通次数和上次沟通时间（已作废，最好不要使用）
     * @param communicateRecord
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年7月5日 下午2:13:27
     */
    CommunicateRecord getCustomerCommunicateCount(CommunicateRecord communicateRecord);
    
    /**
     * <b>Description:</b><br> 根据电话查询最近一次沟通记录
     * @param phone
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年7月11日 下午2:05:04
     */
    CommunicateRecord getCommunicateByPhone(@Param("phone") String phone);
    
    /**
     * <b>Description:</b><br> 根据电话查询客户最近一次沟通记录
     * @param phone
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年7月13日 下午4:12:55
     */
    CommunicateRecord getCustomerCommunicateByPhone(@Param("phone") String phone);
    
    /**
     * <b>Description:</b><br> 根据电话查询供应商最近一次沟通记录
     * @param phone
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年7月13日 下午4:12:55
     */
    CommunicateRecord getSupplierCommunicateByPhone(@Param("phone") String phone);
    
    /**
     * <b>Description:</b><br> 根据coid与 用户ID查询沟通记录
     * @param coid
     * @param userId
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年7月13日 下午1:44:34
     */
    CommunicateRecord getCommunicateByCoidAndUserId(@Param("coid") String coid,@Param("userId") Integer userId);

	/**
	 * <b>Description:</b><br> 根据coid与 用户ID编辑沟通记录
	 * @param communicateRecord
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年7月13日 下午1:55:45
	 */
	Integer updateByCoidAUserId(CommunicateRecord communicateRecord);

	/**
	 * <b>Description:</b><br> 通过电话查询沟通记录
	 * @param communicateRecord
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年7月14日 上午11:01:33
	 */
	List<CommunicateRecord> getCommunicateTraderByPhone(CommunicateRecord communicateRecord);

    /**
     * <b>Description:</b><br> 根据客户ID 客户类型 获取沟通次数和上次沟通时间
     * @param communicateRecord
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年7月5日 下午2:13:27
     */
	CommunicateRecord getTraderCommunicateCount(CommunicateRecord communicateRecord);
	
    /**
     * <b>Description:</b><br> 根据客户ID 客户类型 批量查询获取沟通次数和上次沟通时间
     * @param communicateRecord
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年7月5日 下午2:13:27
     */
	List<CommunicateRecord> getTraderCommunicateCountList(CommunicateRecord communicateRecord);
	
	/**
	 * <b>Description:</b><br> 根据沟通记录的主键id集合查询traderId集合
	 * @param comIdList
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年7月18日 下午1:03:39
	 */
	List<Integer> getTraderIdListByList(Map<String, Object> map);

	/**
	 * <b>Description:</b><br> 通话记录列表
	 * @param map
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年7月20日 下午3:33:02
	 */
	List<CommunicateRecord> queryCallRecordListPage(Map<String, Object> map);

	/**
	 * <b>Description:</b><br> 通话记录话务人员
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年7月20日 下午4:49:44
	 */
	List<User> getRecordUser();

	/**
	 * <b>Description:</b><br> 获取订单沟通次数（包含商机，报价，销售订单）
	 * @param cr
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年7月25日 下午2:40:58
	 */
	int getSaleorderCommunicateRecordCount(@Param("communicateRecord")CommunicateRecord communicateRecord);

	/**
	 * <b>Description:</b><br> 获取通话记录无关联公司的沟通记录
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年7月25日 下午1:22:18
	 */
	List<CommunicateRecord> getNoTraderCommunicateRecord(@Param("addTime") Long addTime);
	
	/**
	 * <b>Description:</b><br> 获取
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年7月25日 下午2:33:34
	 */
	List<CommunicateRecord> getUnSyncCommunicateRecord(@Param("beginTime") Long beginTime,@Param("endTime") Long endTime);
	
	/**
	 * <b>Description:</b><br> 获取时间段内的通话记录
	 * @param beginTime
	 * @param endTime
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年7月25日 下午3:52:38
	 */
	List<CommunicateRecord> getCommunicateRecordByTime(@Param("beginTime") Long beginTime,@Param("endTime") Long endTime);
	
	/**
	 * <b>Description:</b><br> 获取个人首页的沟通数（客户，报价，订单）
	 * @param communicateRecord
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年11月28日 下午4:23:06
	 */
	Integer getHomePageSum(CommunicateRecord communicateRecord);
	
	/**
	 * <b>Description:</b><br> 获取今天要沟通的id的集合（客户，商机，报价）
	 * @param communicateRecord
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年12月2日 上午9:39:07
	 */
	List<Integer> getTodayCommunicateTrdaerIdList(CommunicateRecord communicateRecord);

	/**
	 * <b>Description:</b><br> 获取订单沟通次数（包含商机，报价，销售订单）
	 * @param saleIdList
	 * @param quoteIdList
	 * @param businessIdList
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年1月15日 下午1:28:10
	 */
	List<CommunicateRecord> getCommunicateNumList(@Param("saleIdList")List<Integer> saleIdList, @Param("quoteIdList")List<Integer> quoteIdList, @Param("businessIdList")List<Integer> businessIdList);

	/**
	 * <b>Description:</b><br> 分页获取通话记录（未补充过时长，地址）
	 * @param map
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年6月8日 上午9:36:02
	 */
	List<CommunicateRecord> getUnSyncCommunicateRecordListPage(Map<String, Object> map);
	
	/**
	 * 
	 * <b>Description: 查询15天内某个售后的沟通记录</b><br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年08月09日 18:03
	 */
	List<CommunicateRecord> selectCallRecordListPage(Map<String, Object> map);
	
	/**
	 * 
	 * <b>Description: 获取COIDURI</b><br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年08月10日 10:30
	 */
	String getRecordCoidURIByCommunicateRecordId(@Param("communicateRecordId")Integer communicateRecordId);
	/**
     * 
     * <b>Description:</b>查询商机的最新沟通内容
     * @param comRe
     * @return List<CommunicateRecord>
     * @Note
     * <b>Author：</b> scott.zhu
     * <b>Date:</b> 2019年4月4日 下午1:07:14
     */
	List<CommunicateRecord> getCommunicateList(CommunicateRecord comRe);

	/**
	 * 功能描述: 获取待转语音的数据总条数
	 * @param: []
	 * @return: int
	 * @auther: Barry.Xu
	 * @date: 2019/4/24 9:23
	 */
	Integer getCountOfCommunicate();

	/**
	 * 功能描述:
	 * @param: [page]
	 * @return: java.util.List<com.vedeng.trader.model.CommunicateRecord>
	 * @auther: Barry.Xu
	 * @date: 2019/4/24 10:54
	 */
	List<CommunicateRecord> getLfasrCommunicateList(@Param("page")Integer page);

	/**
	 * 功能描述: 更新录音状态
	 * @param: [list]
	 * @return: int
	 * @auther: Barry.Xu
	 * @date: 2019/4/24 16:05
	 */
	int updateLfasrStatus(List<CommunicateRecord> list);
}