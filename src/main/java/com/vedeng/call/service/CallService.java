package com.vedeng.call.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.vedeng.phoneticWriting.model.Comment;
import com.vedeng.phoneticWriting.model.ModificationRecord;
import com.vedeng.phoneticWriting.model.PhoneticWriting;

import com.vedeng.authorization.model.User;
import com.vedeng.call.model.CallFailed;
import com.vedeng.call.model.CallOut;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.order.model.BussinessChance;
import com.vedeng.system.model.QCellCore;
import com.vedeng.trader.model.CommunicateRecord;
import com.vedeng.trader.model.Trader;
import com.vedeng.trader.model.vo.TraderCustomerVo;
import com.vedeng.trader.model.vo.TraderSupplierVo;

/**
 * <b>Description:</b><br> 呼叫中心
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.call.service
 * <br><b>ClassName:</b> CallService
 * <br><b>Date:</b> 2017年7月7日 下午3:54:25
 */
public interface CallService extends BaseService {
	/**
	 * <b>Description:</b><br> 面板初始化信息
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年7月7日 下午3:54:37
	 */
	HashMap callInit();
	
	/**
     * <b>Description:</b><br> 查询号码归属信息
     * @param phone
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年7月11日 上午9:11:15
     */
    QCellCore getQCellCoreByPhone(String phone);
	
    /**
     * <b>Description:</b><br> 根据电话 查询客户、商机、报价、订单信息
     * @param phone
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年7月11日 上午10:58:55
     */
    TraderCustomerVo getCustomerInfoByPhone(CallOut callOut);
    
    /**
     * <b>Description:</b><br> 根据沟通类型查询订单
     * @param communicateRecord
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年7月13日 上午10:12:52
     */
    List<?> getOrderList(CommunicateRecord communicateRecord);
    
    /**
     * <b>Description:</b><br> 保存沟通
     * @param communicateRecord
     * @param request
     * @param session
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年7月13日 下午1:32:37
     */
    Boolean saveCommunicate(CommunicateRecord communicateRecord, HttpServletRequest request,
			HttpSession session) throws Exception;

	/**
	 * <b>Description:</b><br> 根据电话 查询供应商、订单信息
	 * @param callOut
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年7月13日 下午4:08:14
	 */
	TraderSupplierVo getSupplierInfoByPhone(CallOut callOut);
	
	/**
	 * <b>Description:</b><br> 根据traderId查询交易者主表信息
	 * @param traderId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年7月13日 下午5:26:14
	 */
	Trader getTraderByTraderId(Integer traderId);

	/**
	 * <b>Description:</b><br> 获取最近一条商机信息
	 * @param phone
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年7月17日 上午10:48:46
	 */
	BussinessChance getBussincessChance(CallOut callOut);
	
	/**
	 * <b>Description:</b><br> 根据来电查询交易者主表信息
	 * @param callOut
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年7月17日 下午1:46:09
	 */
	Trader getTraderByPhone(CallOut callOut);

	/**
	 * <b>Description:</b><br> 坐席话机状态查询
	 * @param number
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年7月17日 下午2:36:32
	 */
	String getIsInit(String number);

	/**
	 * <b>Description:</b><br> 话务综合统计
	 * @param starttime
	 * @param endtime
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年7月19日 下午2:07:40
	 */
	Map<String,Object> getStatistics(String starttime, String endtime);

	/**
	 * <b>Description:</b><br> 坐席明细
	 * @param starttime
	 * @param endtime
	 * @param numberList
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年7月19日 下午4:08:13
	 */
	List getUserDetail(String starttime, String endtime, List<String> numberList);

	/**
	 * <b>Description:</b><br> 漏接来电分页搜索
	 * @param callFailed
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年7月20日 上午11:35:40
	 */
	List<CallFailed> queryCallFailedlistPage(CallFailed callFailed, Page page);

	/**
	 * <b>Description:</b><br> 漏接来电话务人员
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年7月20日 上午11:37:51
	 */
	List<CallFailed> getCallFailedDialBackUser();
	
	/**
	 * <b>Description:</b><br> 查询区号信息
	 * @param code
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年7月20日 下午1:34:22
	 */
	QCellCore getQCellCoreByCode(String code);

	/**
	 * <b>Description:</b><br> 漏接来电回拨更新
	 * @param callFailed
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年7月20日 下午2:59:17
	 */
	Boolean editCallFailedCoid(CallFailed callFailed, HttpSession session);

	/**
	 * <b>Description:</b><br> 通话记录
	 * @param communicateRecord
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年7月20日 下午3:30:59
	 */
	List<CommunicateRecord> queryRecordlistPage(CommunicateRecord communicateRecord, Page page,HttpSession session);

	/**
	 * <b>Description:</b><br> 获取通话记录人员
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年7月20日 下午4:49:07
	 */
	List<User> getRecordUser();
	/**
	 * 
	 * <b>Description: 查询15天内某个售后的沟通记录</b><br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年08月09日 18:12
	 */
	List<CommunicateRecord> selectRecordlistPage(CommunicateRecord communicateRecord, Page page,HttpSession session);
	
	ResultInfo<?> getRecordCoidURIByCommunicateRecordId(Integer communicateRecordId);
    /** 
    * @Description: 查询录音的文本 
    * @Param: [communicateRecord]
	 * * @return: java.util.List<com.vedeng.phoneticWriting.model.PhoneticWriting>

    * @Author: scott.zhu 
    * @Date: 2019/4/24 
    */
	PhoneticWriting getPhoneticWriting(CommunicateRecord communicateRecord);
    /** 
    * @Description: 查询争议替换的数据list 
    * @Param: [communicateRecord]
	 * @return: com.vedeng.phoneticWriting.model.ModificationRecord
	 * @Author: scott.zhu
    * @Date: 2019/4/24 
    */
	List<ModificationRecord> getMrList(CommunicateRecord communicateRecord);
    /** 
    * @Description: 查询所有的点评
    * @Param: [communicateRecord] 
    * @return: java.util.List<com.vedeng.phoneticWriting.model.Comment> 
    * @Author: scott.zhu 
    * @Date: 2019/4/24 
    */
	List<Comment> getComList(CommunicateRecord communicateRecord);
    /** 
    * @Description: 根据id查询沟通记录
    * @Param: [communicateRecord] 
    * @return: com.vedeng.trader.model.CommunicateRecord 
    * @Author: scott.zhu 
    * @Date: 2019/5/6 
    */
	CommunicateRecord getCommunicateRecordById(CommunicateRecord communicateRecord);
}
