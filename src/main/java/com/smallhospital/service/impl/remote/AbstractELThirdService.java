package com.smallhospital.service.impl.remote;

import com.alibaba.fastjson.JSONObject;
import com.meinian.util.SignOutput;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.service.impl.BaseServiceimpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public abstract class AbstractELThirdService<T> extends BaseServiceimpl implements ELThirdService<T> {

    private static Logger LOGGER = LoggerFactory.getLogger(AbstractELThirdService.class);

    public static final String TOKEN_UIR = "/api/token.action";

    //接口平台对接时申请的appKey，一个appKey对应一个 secret
    @Value("${el_appkey}")
    private String el_appkey;
    //随机密码平台获取
    @Value("${el_secret}")
    private String el_secret;
    //接口地址
    @Value("${el_url}")
    private String el_url;

    public String getTokenFromEL() {
        String token = "";
        try {
            String timestamp = String.valueOf(System.currentTimeMillis());
            //获取系统的签名（将appkey、secret、timestamp参数进行字典排序，拼接成一个字符串进行sha1加密生成sign）
            String sign = SignOutput.getSign(el_appkey, el_secret, timestamp);

            //设置表单提交，也支持中文
            MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
            params.add("appKey", el_appkey);
            params.add("sign", sign);
            params.add("timestamp", timestamp);

            //通过restTample请求美年的url
            RestTemplate restTemplate = new RestTemplate();

            LOGGER.info("token请求URL:"+(el_url + TOKEN_UIR)+",请求参数:" + params);

            JSONObject json = restTemplate.postForEntity(el_url + TOKEN_UIR, params, JSONObject.class).getBody();

            //获取请求u状态吗判断是否成功
            if ("S".equals(json.getString("status"))) {
                token = json.getJSONObject("data").get("token").toString();
                LOGGER.info("token请求响应:" + token);
            }

        } catch (Exception e) {
            LOGGER.error("医流获取token接口异常", e);
        }

        return token;
    }

    @Override
    public boolean syncData(T param) {
        String token = getTokenFromEL();
        //获取token失败直接返回
        if(StringUtils.isEmpty(token)){
            return false;
        }

        //封账请求参数
        MultiValueMap<String, String> requestMap = encapsulaeRequestParam(param,token);
        //采用spring自带的远程你接口调用方法
        RestTemplate restTemplate = new RestTemplate();
        JSONObject resultJson = null;
        try {

            LOGGER.info("请求URL:"+(el_url + getRequestUIR())+",请求参数:" + requestMap);
            long start = System.currentTimeMillis();
            resultJson = restTemplate.postForEntity(el_url + getRequestUIR(), requestMap, JSONObject.class).getBody();
            long end = System.currentTimeMillis();

            LOGGER.info("小医院接口请求耗时（ms）:" + (end - start));

            LOGGER.info("响应结果:" + resultJson.toJSONString());

            if(ErpConst.SEND_DATA_SUCCESS.equalsIgnoreCase(resultJson.getString("status"))){
                LOGGER.info("医流网同步客户接口成功!");
                return true;
            }
        } catch (Exception e) {
            LOGGER.error("医流网同步客户接口异常", e);
        }
        return false;
    }

    protected MultiValueMap<String,String> encapsulaeRequestParam(T param, String token){
        MultiValueMap<String,String> params = new LinkedMultiValueMap<String, String>();
        params.add("token", token);
        params.add("appKey", el_appkey);
        String dataJson = encapsulaeRequestBodyParam(param);
        params.add("data", dataJson);
        return params;
    }

    protected abstract String encapsulaeRequestBodyParam(T param);

    protected abstract String getRequestUIR();

}
