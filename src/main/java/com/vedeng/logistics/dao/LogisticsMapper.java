package com.vedeng.logistics.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.vedeng.logistics.model.Logistics;
import org.apache.ibatis.annotations.Param;

/**
 * 快递公司信息
 * <p>Title: LogisticsMapper</p>  
 * <p>Description: </p>  
 * @author Bill  
 * @date 2019年3月4日
 */
public interface LogisticsMapper {

	/**
	 * 根据参数查询快递信息
	 * <p>Title: getLogisticsListByParam</p>  
	 * <p>Description: </p>  
	 * @param logisticsParam
	 * @return  
	 * @author Bill
	 * @date 2019年3月4日
	 */
	List<Logistics> getLogisticsListByParam(Map<String, Object> logisticsParam);

	/**
	 * 根据快递公司id和省份id查询快递费用
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
	 * @param :[logistics]
	 * @return :com.vedeng.logistics.model.Logistics
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2019/5/20 7:13 PM
	 */
	Logistics getLogisticsById(@Param("logisticsId")Integer logisticsId);


    /**
    * @Description: 根据快递名称查询物流编码
    * @Param:
    * @return:
    * @Author: addis
    * @Date: 2019/11/8
    */
	String getLogisticsCode(String name);

}
