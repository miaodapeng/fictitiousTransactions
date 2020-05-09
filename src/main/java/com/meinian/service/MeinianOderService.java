package com.meinian.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.meinian.model.LogisticsVo;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.service.BaseService;
import com.vedeng.trader.model.TraderCustomer;

import javax.servlet.http.HttpSession;

/**
 * @Auther Bert
 * 向美年推送接口所需要的相关数据
 */
public interface MeinianOderService extends BaseService {

    /**
     * <b>Description:</b><br>
     * @param : token appkey url
     * @return :JSONObject
     * @Note <b>Author:</b> Bert <br>
     * <b>Date:</b> 2018/11/12 16:27
     */
    JSONObject getTokenFromMeiNian(String appkey, String secret, String url);


    /**
     * <b>Description:</b><br>
     * 获取近一个月的所有订单
     * @param : token appkey url
     * @return :JSONObject
     * @Note <b>Author:</b> Bert <br>
     * <b>Date:</b> 2018/11/12 15:28
     */
    JSONObject getOrderList(String token, String appkey, String url);


    /**
     * <b>Description:</b><br>
     * 获取最近一个月所有退货订单
     * @param : token appkey url
     * @return :JSONObject
     * @Note <b>Author:</b> Bert <br>
     * <b>Date:</b> 2018/11/10 13:28
     */
    JSONObject getRefundList(String token, String appkey, String url);

    /**
     * <b>Description:</b><br>
     *  得到美年的所有用户
     * @param : token appkey url
     * @return :JSONObject
     * @Note <b>Author:</b> Bert <br>
     * <b>Date:</b> 2018/11/12 10:25
     */
    JSONObject getCustomers(String token, String appkey, String url);

    /**
     * <b>Description:</b><br>
     * 将查询到的美年所有的信息跟更新到数据库
     * @param :jsons
     * @return :ResultInfo
     * @Note <b>Author:</b> Bert <br>
     * <b>Date:</b> 2018/11/12 11:24
     */
    ResultInfo sendMeinianOrders(JSONArray jsons);

    /**
     * <b>Description:</b><br>
     *
     * @param :traderCustomer session
     * @return :ResultInfo
     * @Note <b>Author:</b> Bert <br>
     * <b>Date:</b> 2018/11/12 20:22
     */
    ResultInfo updateToMeiNianCode(TraderCustomer traderCustomer , HttpSession session);


    /**
     * <b>Description:</b><br>
     * 上传供货单
     * @param :logistics
     * @return :String
     * @Note <b>Author:</b> Bert <br>
     * <b>Date:</b> 2018/11/12 21:22
     */
    String sendToMeinianSupply(LogisticsVo logistics);

    /**
     * <b>Description:</b><br>
     * 检查客户编码有没有绑定
     * @param :traderCustomer
     * @return : Integer
     * @Note <b>Author:</b> Bert <br>
     * <b>Date:</b> 2018/11/16 16:47
     */
    Integer getCountTraderCustomerbyMeinianCode(String meiNianCode);
}
