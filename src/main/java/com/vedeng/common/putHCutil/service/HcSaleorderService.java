/**
 * 
 */
package com.vedeng.common.putHCutil.service;

import java.util.Map;

import com.vedeng.common.model.ResultInfo;

/**
 * <b>Description:推送数据到耗材接口</b><br>
 * 
 * @author cooper.xu
 * @Note <b>ProjectName:</b> erp <br>
 *       <b>PackageName:</b> com.vedeng.common.putHCutil.service <br>
 *       <b>ClassName:</b> HcSaleorderService <br>
 *       <b>Date:</b> 2018年11月26日 下午6:59:06
 */
public interface HcSaleorderService {
    /**
     * 
     * <b>Description:</b>
     * 
     * @param map
     * @return ResultInfo<?>
     * @Note <b>Author：</b> cooper.xu <b>Date:</b> 2018年11月26日 下午6:59:30
     */
    ResultInfo<?> putExpressToHC(Map<String, Object> map);

    /**
     * 
     * <b>Description:</b>
     * 
     * @param map
     * @return ResultInfo<?>
     * @Note <b>Author：</b> cooper.xu <b>Date:</b> 2018年11月26日 下午6:59:33
     */
    ResultInfo<?> putOrderStatustoHC(Map<String, Object> map);

    /**
     * 
     * <b>Description:</b>
     * 
     * @param map
     * @return ResultInfo<?>
     * @Note <b>Author：</b> cooper.xu <b>Date:</b> 2018年11月28日 下午6:44:07
     */
    ResultInfo<?> putInvoicetoHC(Map<String, Object> map);

    /**
     *
     * <b>Description: 向医械购前台推送订单修改价格信息</b>
     *
     * @param map
     * @return ResultInfo<?>
     * @Note <b>Author：</b> Hugo <b>Date:</b> 2020年3月23日 下午6:44:07
     */
    ResultInfo<?> putOrderPricetoHC(Map<String, Object> map);
}
