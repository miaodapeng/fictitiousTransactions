package com.vedeng.order.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.vedeng.authorization.model.User;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.order.model.BussinessChance;
import com.vedeng.order.model.vo.BussinessChanceVo;
import com.vedeng.system.model.Attachment;
import com.vedeng.trader.model.TraderContact;
import com.vedeng.trader.model.TraderCustomer;
import com.vedeng.trader.model.vo.TraderCustomerVo;

public interface BussinessChanceService extends BaseService {
	
	/**
	 * <b>Description:</b><br> 售后商机列表
	 * @param bussinessChance
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年6月22日 上午10:26:32
	 */
	Map<String,Object> getServiceBussinessChanceListPage(BussinessChanceVo bussinessChance,Page page);
	
	/**
	 * <b>Description:</b><br> 销售商机列表
	 * @param bussinessChance
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年6月22日 上午10:27:03
	 */
	Map<String,Object> getSaleBussinessChanceListPage(BussinessChanceVo bussinessChance,Page page);
	
	/**
	 * <b>Description:</b><br> 批量分配商机
	 * @param request
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年6月22日 下午4:15:41
	 */
	ResultInfo assignBussinessChance(BussinessChanceVo bussinessChanceVo,HttpSession session);
	
	/**
	 * <b>Description:</b><br> 保存商机
	 * @param bussinessChance
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年6月26日 下午2:17:38
	 */
	ResultInfo saveBussinessChance(BussinessChance bussinessChance,User user,Attachment attachment);
	
	/**
	 * <b>Description:</b><br> 查询售后商机详情
	 * @param bussinessChance
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年6月27日 下午6:54:40
	 */
	Map<String, Object> getAfterSalesDetail(BussinessChance bussinessChance,Page page);
	
	/**
	 * <b>Description:</b><br> 跳转编辑售后商机页面
	 * @param bussinessChance
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年6月29日 下午1:08:47
	 */
	BussinessChanceVo toAfterSalesEditPage(BussinessChance bussinessChance);
	
	/**
	 * <b>Description:</b><br> 保存确认的客户信息
	 * @param bussinessChance
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年6月30日 下午5:42:28
	 */
	TraderCustomerVo saveConfirmCustomer(BussinessChance bussinessChance,User user,TraderContact traderContact);
	
	/**
	 * <b>Description:</b><br> 获取上传文件的域名
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年7月17日 下午3:47:39
	 */
	String getUploadDomain();
	/**
	 * 
	 * <b>Description:</b><br> 修改商机信息
	 * @param bussinessChance
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2018年3月26日 上午10:45:36
	 */
	ResultInfo editBussinessChance(BussinessChance bussinessChance);
    /**
     * 
     * <b>Description:</b>根据商机id查询商机信息
     * @param bussinessChance
     * @return BussinessChance
     * @Note
     * <b>Author：</b> scott.zhu
     * <b>Date:</b> 2019年3月4日 下午1:06:30
     */
    BussinessChance getBussinessChanceInfo(BussinessChance bussinessChance);
    /**
     * 
     * <b>Description:</b>更新商机信息
     * @param bussinessChance
     * @return Boolean
     * @Note
     * <b>Author：</b> scott.zhu
     * <b>Date:</b> 2019年3月4日 下午2:10:15
     */
    Boolean saveAddBussinessStatus(BussinessChance bussinessChance);
}
