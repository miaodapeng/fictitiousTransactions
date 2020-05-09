package com.meinian.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.constants.Contant;
import com.fasterxml.jackson.core.type.TypeReference;

import com.meinian.model.LogisticsVo;

import com.meinian.service.MeinianOderService;
import com.meinian.util.SignOutput;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.ObjectUtils;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.trader.model.TraderCustomer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@Service("meinianOderService")
public class MeinianOderServiceImpl extends BaseServiceimpl implements MeinianOderService {


    public static Logger LOGGER = LoggerFactory.getLogger(MeinianOderServiceImpl.class);


    /**
     * <b>Description:</b><br>
     * @param : token appkey url
     * @return :JSONObject
     * @Note <b>Author:</b> Bert <br>
     * <b>Date:</b> 2018/11/12 16:27
     */
    @Override
    public JSONObject getTokenFromMeiNian(String appkey, String secret, String url) {
        JSONObject result = new JSONObject();
        String timestamp = null;
        String sign = null;
        try {
            timestamp = String.valueOf(System.currentTimeMillis());
            //获取系统的签名（将appkey、secret、timestamp参数进行字典排序，拼接成一个字符串进行sha1加密生成sign）
            sign = SignOutput.getSign(appkey, secret, timestamp);
        } catch (Exception e) {
            LOGGER.error("类型转换异常", e);
        }
        //设置表单提交，也支持中文
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("appKey", appkey);
        params.add("sign", sign);
        params.add("timestamp", timestamp);
        //通过restTample请求美年的url
        RestTemplate restTemplate = new RestTemplate();
        JSONObject json = null;
        try {
            json = restTemplate.postForEntity(url + "/order/token", params, JSONObject.class).getBody();
        } catch (Exception e) {
            LOGGER.error("美年接口异常信息", e);
        }
        //获取请求u状态吗判断是否成功
        String status = json.getString("status");
        if (status.equals("S")) {
            result.put("status", "S");
            result.put("token", json.getJSONObject("data").get("token").toString());
            return result;
        }
        return json;

    }

    /**
     * <b>Description:</b><br>
     * 获取近一个月的所有订单*
     * @param : token appkey url
     * @return :JSONObject
     * @Note <b>Author:</b> Bert <br>
     * <b>Date:</b> 2018/11/12 15:28
     */
    @Override
    public JSONObject getOrderList(String token, String appkey, String url) {
        //设置查询条件
        JSONObject inputjson = new JSONObject();
        //设置起始时间
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        inputjson.put("startTime", c.getTimeInMillis());
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        //也支持中文
        params.add("data", inputjson.toString());
        params.add("token", token);
        params.add("appKey", appkey);
        //采用spring自带的远程你接口调用方法
        RestTemplate restTemplate = new RestTemplate();

        JSONObject json = null;
        try {
            json = restTemplate.postForEntity(url + "/order/orderList", params, JSONObject.class).getBody();
        } catch (Exception e) {
            LOGGER.error("美年接口异常信息", e);
        }
        return json;
    }

    /**
     * <b>Description:</b><br>
     * 获取最近一个月所有退货订单(此接口暂时未使用)
     * @param : token appkey url
     * @return :JSONObject
     * @Note <b>Author:</b> Bert <br>
     * <b>Date:</b> 2018/11/10 13:28
     */
    @Override
    public JSONObject getRefundList(String token, String appkey, String url) {
        JSONObject inputjson = new JSONObject();
        //设置起始时间
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        inputjson.put("startTime", c.getTimeInMillis());
        //设置表单提交（data条件， token  ， apply(通过平台获取)）
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("data", inputjson.toString());
        params.add("token", token);
        params.add("appKey", appkey);
        RestTemplate restTemplate = new RestTemplate();
        JSONObject json = null;
        try {
            json = restTemplate.postForEntity(url + "/order/refundList", params, JSONObject.class).getBody();
        } catch (RestClientException e) {
            logger.error(Contant.ERROR_MSG, e);
            LOGGER.info("美年接口异常信息"+e.getMessage());
        }
        return json;
    }

    /**
     * <b>Description:</b><br>
     *  得到美年的用户
     * @param : token appkey url
     * @return :JSONObject
     * @Note <b>Author:</b> Bert <br>
     * <b>Date:</b> 2018/11/12 10:25
     */
    @Override
    public JSONObject getCustomers(String token, String appkey, String url) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        //也支持中文
        params.add("token", token);
        params.add("appKey", appkey);
        //spring自带的远程接口调用
        RestTemplate restTemplate = new RestTemplate();
        JSONObject json = null;
        try {
            json = restTemplate.postForEntity(url + "/order/customers", params, JSONObject.class).getBody();
        } catch (RestClientException e) {
            logger.error(Contant.ERROR_MSG, e);
            LOGGER.info("美年接口异常信息"+e.getMessage());
        }
        return json;
    }
    /**
     * <b>Description:</b><br>
     * 将查询到的美年所有的信息跟更新到数据库
     * @param :jsons
     * @return :ResultInfo
     * @Note <b>Author:</b> Bert <br>
     * <b>Date:</b> 2018/11/12 11:24
     */
    @Override
    public ResultInfo sendMeinianOrders(JSONArray orders) {
        // 结果集
        ResultInfo<?> result = null;
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
        };
        String url = httpUrl + "order/saleorder/acceptallmeinianorders.htm";
        try {
            result = (ResultInfo<?>) HttpClientUtils.post(url, orders, clientId, clientKey, TypeRef);
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            LOGGER.info("dbCenter接口异常信息"+e.getMessage());
        }
        return result;
    }

    /**
     * 方法实现说明
     * 更新
     * @param traderCustomer session
     * @return ResultInfo
     * @author Bert
     */
    @Override
    public ResultInfo updateToMeiNianCode(TraderCustomer traderCustomer , HttpSession session) {
        ResultInfo<?> updateResult = null;
        //获取当前用户
        User user = (User) session.getAttribute(Consts.SESSION_USER);
        if (ObjectUtils.allNotNull(user)){
            traderCustomer.setUpdater(user.getUserId());
        }
        //返回结果集的定义
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
        };
        //拼接请求路径
        String url = httpUrl + "tradercustomer/updateToMeiNianCode.htm";
        try {
            //返回结果
            updateResult = (ResultInfo<?>) HttpClientUtils.post(url, traderCustomer, clientId, clientKey, TypeRef);
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            LOGGER.info("dbCenter接口异常信息"+e.getMessage());
        }
        return updateResult;
    }

    /**
     * 上传供货单
     * @param logistics 供货单转换的json串
     * @return
     * @auther Bert
     */

    @Override
    public String sendToMeinianSupply(LogisticsVo logistics) {

        LOGGER.info("sendToMeinianSupply json data: " + JSON.toJSONString(logistics));

        List<SysOptionDefinition> apiList = getSysOptionDefinitionList(901);
        //获取appkey,secret,url
        String appkey = apiList.get(0).getComments();
        //获取密码
        String secret = apiList.get(1).getComments();
        //获取器地址
        String url = apiList.get(2).getComments();
        //获取token
        JSONObject tokenmes = getTokenFromMeiNian(appkey, secret, url);

        //防止空指针异常的出现
        if (!ObjectUtils.allNotNull(tokenmes)){
            LOGGER.error(" get meinian token error ");
            return "获取token失败";
        }

        //将数据进行封装
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        //也支持中文
        params.add("token", tokenmes.get("token").toString());
        params.add("appKey", appkey);
        params.add("data", JSON.toJSONString(logistics));

        //使用spring自带的远程调用接口
        RestTemplate restTemplate = new RestTemplate();
        JSONObject result = null;
        try {
            result = restTemplate.postForEntity(url + "/order/uploads", params, JSONObject.class).getBody();

            LOGGER.info("sendToMeinianSupply result: " + result);
        } catch (Exception e) {
            LOGGER.error("美年接口异常信息", e);
        }
        //去状态值防止空指针异常
        if (null != result){
            Object code = result.get("status");
            if(!ErpConst.SEND_DATA_SUCCESS.equals(code)){
            	return result.get("message").toString();
            }
            return code.toString();
        }
        return ErpConst.SEND_DATA_FAIL;
    }

    /**
     * 方法实现说明
     * 更新
     * @param meiNianCode
     * @return ResultInfo
     * @author Bert
     */
    @Override
    public Integer getCountTraderCustomerbyMeinianCode(String meiNianCode) {
        //返回结果集的定义
        final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
        };
        //拼接请求路径
        String url = httpUrl + "tradercustomer/getCountTraderCustomerbyMeinianCode.htm";
        try {
            //返回结果
            ResultInfo<?> countTraderCustomerbyMeinianCode = (ResultInfo<?>) HttpClientUtils.post(url, meiNianCode, clientId, clientKey, TypeRef);
            if (null != countTraderCustomerbyMeinianCode) {
                if (countTraderCustomerbyMeinianCode.getCode() == ErpConst.ZERO){
                    return ErpConst.ZERO;
                } else  {
                    return ErpConst.ONE;
                }
            }

        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
            LOGGER.info("dbCenter接口异常信息"+e.getMessage());
        }
        return ErpConst.ONE;
    }
//    @Override
//    public Saleorder saveAddSaleorderInfo(Saleorder saleorder, HttpServletRequest request, HttpSession session) {
//        User user =(User)session.getAttribute(ErpConst.CURR_USER);
//        Long time = DateUtil.sysTimeMillis();
//
//        saleorder.setAddTime(time);
//        saleorder.setCreator(user.getUserId());
//        saleorder.setCreatorOrgId(user.getOrgId());
//        saleorder.setCreatorOrgName(user.getOrgName());
//        saleorder.setCompanyId(user.getCompanyId());
//        saleorder.setOrderType(0);
//        //saleorder.setOrgId(user.getOrgId());
//        //saleorder.setUserId(user.getUserId());
//
//        // 归属销售
////        User belongUser = new User();
////        if(saleorder.getTraderId() != null ){
////            belongUser = userService.getUserInfoByTraderId(saleorder.getTraderId(), 1);// 1客户，2供应商
////            if(belongUser != null && belongUser.getUserId() != null){
////                saleorder.setUserId(belongUser.getUserId());
////            }
////            if(belongUser != null && belongUser.getOrgId() != null){
////                saleorder.setOrgId(belongUser.getOrgId());
////            }
////            if(belongUser != null && belongUser.getOrgName() != null){
////                saleorder.setOrgName(belongUser.getOrgName());
////            }
////        }
//
//        // 定义反序列化 数据格式
//        final TypeReference<ResultInfo<Saleorder>> TypeRef2 = new TypeReference<ResultInfo<Saleorder>>() {};
//        try {
//            ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(httpUrl + "order/saleorder/saveaddsaleorderinfo.htm", saleorder,clientId,clientKey, TypeRef2);
//            Saleorder res = (Saleorder) result2.getData();
//            return res;
//        } catch (IOException e) {
//            return null;
//        }
//    }
}
