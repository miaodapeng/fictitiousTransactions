package com.vedeng.logistics.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.vedeng.authorization.model.User;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.service.BaseService;
import com.vedeng.logistics.model.Logistics;

public interface LogisticsService extends BaseService{
    /**
     * 
     * <b>Description:</b><br> 获取所有快递公司名称
     * @return
     * @Note
     * <b>Author:</b> Micheal
     * <br><b>Date:</b> 2017年7月17日 下午2:48:09
     */
    List<Logistics> getLogisticsList(Integer companyId);
    
    /**
     * 
     * <b>Description:</b><br> 获取所有快递公司名称
     * @return
     * @Note
     * <b>Author:</b> Micheal
     * <br><b>Date:</b> 2017年7月17日 下午2:48:09
     */
    List<Logistics> getAllLogisticsList(Integer companyId);
    
    /**
     * <b>Description:</b><br> 保存新增或编辑的快递公司
     * @param user
     * @param logistics
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年12月5日 上午10:55:02
     */
    ResultInfo<?> saveOrUpdateLogistice(User user,Logistics logistics);
    
    /**
     * <b>Description:</b><br> 保存默认的快递公司
     * @param user
     * @param logistics
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年12月5日 上午10:55:02
     */
    ResultInfo<?> saveSetDefaultLogistics(User user,Logistics logistics);

    /**
     * 根据参数查询快递公司信息
     * @param logisticsParam
     * @return
     */
	List<Logistics> getLogisticsListByParam(Map<String, Object> logisticsParam);

	/**
	 * 根据快递id和省份id查询快递费用
	 * <p>Title: getFreeByParam</p>  
	 * <p>Description: </p>  
	 * @param regionParamMap
	 * @return  
	 * @author Bill
	 * @date 2019年3月5日
	 */
	BigDecimal getFreeByParam(Map<String, Object> regionParamMap);

	/**
	 * <b>Description:根据快递公司主键获取快递公司信息</b><br>
	 *
	 *
	 * @param :[logisticsId]
	 * @return :com.vedeng.logistics.model.Logistics
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2019/5/20 7:15 PM
	 */
	Logistics getLogisticsById(Integer logisticsId);


	/**
	 * @Description: 根据快递名称查询物流编码
	 * @Param:
	 * @return:
	 * @Author: addis
	 * @Date: 2019/11/8
	 */
	String getLogisticsCode(String name);
}
