package com.smallhospital.util;

import com.smallhospital.dto.LogisticsDTO;
import com.vedeng.common.util.JacksonHelper;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Daniel
 * @Description: 物流信息工具类
 * @Date created in 2019/11/21 9:36 上午
 */
public class LogisticsUtil {


    /**
     * 快递公司编码
     */
    private static String com = "zhongtong";
    /**
     * 快递单号
     */
    private static String num = "75306436322629";
    /**
     * 手机号码后四位
     */
    private static String phone = "";
    /**
     * 出发地
     */
    private static String from = "";
    /**
     * 目的地
     */
    private static String to = "";
    /**
     * 开启行政规划解析
     */
    private static int resultv2 = 0;

    /**
     * 实时查询请求地址
     */
    private static final String SYNQUERY_URL = "http://poll.kuaidi100.com/poll/query.do";



    /**
     * 实时查询快递单号
     * @param com			快递公司编码
     * @param num			快递单号
     * @param resultv2		开通区域解析功能：0-关闭；1-开通
     * @return 查询结果
     */
    public static LogisticsDTO queryLogisticsInfo(String com, String num, int resultv2) {

        //贵司的授权key
        String key = "vIStKARH975";
        //贵司的查询公司编号
        String customer = "32EA2614AD1441636ADFA1F014060190";

        StringBuilder param = new StringBuilder("{");
        param.append("\"com\":\"").append(com).append("\"");
        param.append(",\"num\":\"").append(num).append("\"");
        param.append(",\"phone\":\"").append("").append("\"");
        param.append(",\"from\":\"").append("").append("\"");
        param.append(",\"to\":\"").append("").append("\"");
        if(1 == resultv2) {
            param.append(",\"resultv2\":1");
        } else {
            param.append(",\"resultv2\":0");
        }
        param.append("}");

        Map<String, String> params = new HashMap<String, String>(3);
        params.put("customer", customer);
        String sign = DigestUtils.md5Hex(param + key + customer).toUpperCase();
        params.put("sign", sign);
        params.put("param", param.toString());
        return post(params);
    }

    /**
     * 发送post请求
     */
    public static LogisticsDTO post(Map<String, String> params) {
        StringBuilder response = new StringBuilder("");

        BufferedReader reader = null;
        try {
            StringBuilder builder = new StringBuilder();
            for (Map.Entry<String, String> param : params.entrySet()) {
                if (builder.length() > 0) {
                    builder.append('&');
                }
                builder.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                builder.append('=');
                builder.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] bytes = builder.toString().getBytes(StandardCharsets.UTF_8);

            URL url = new URL(SYNQUERY_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(bytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(bytes);

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));

            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != reader) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return JacksonHelper.fromJSON(response.toString(), LogisticsDTO.class);
    }


    public static void main(String[] args){
        LogisticsDTO dto = queryLogisticsInfo(com,num,1);
        System.out.println(dto.toString());
    }
}
