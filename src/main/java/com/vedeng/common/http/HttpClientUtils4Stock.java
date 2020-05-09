package com.vedeng.common.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.common.key.CryptBase64Tool;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.util.JsonUtils;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

/**
 * @Author: Hugo
 * @Data: 2019 12/11
 */
public class HttpClientUtils4Stock {
    private static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    public static Object get(String url, Object paraMap, String clientId, String clientKey, TypeReference<?> type)
            throws IOException {
        // 序列化成 json
        String data = JSONObject.toJSONString(paraMap);
        // 准备发送的参数
        Map<String, String> paramMap = new HashMap<String, String>();

        paramMap.put("timestamp", System.currentTimeMillis() + "");
        paramMap.put("data", data);

        paramMap.put("clientId", clientId);
        paramMap.put("clientKey", clientKey);

        // http post 参数
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        Iterator<Map.Entry<String, String>> $it = paramMap.entrySet().iterator();
        while ($it.hasNext()) {
            Map.Entry<String, String> entry = $it.next();
            // params.add(new BasicNameValuePair(entry.getKey(),
            // entry.getValue()));
            params.add(new BasicNameValuePair(entry.getKey(),
                    new String(entry.getValue().getBytes("ISO-8859-1"), "UTF-8")));
        }

        // http post 请求
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = httpclient.execute(httpGet);

        int status = response.getStatusLine().getStatusCode();

        if (status != 200) {
            logger.warn("Http Post Status:" +url+ status+ EntityUtils.toString(response.getEntity(), Consts.UTF_8));
            return null;
        }

        try {
            // 返回结果 格式转换
            String result = EntityUtils.toString(response.getEntity(), Consts.UTF_8);

            JSONObject jsStr = JSONObject.parseObject(result);
            if (jsStr.getString("code") == "success") {

                JSONObject param = JSONObject.parseObject(jsStr.getString("param"));

                String sysdate = param.getString("systime");
                String skey = param.getString("skey");
                String result_data = (CryptBase64Tool.desDecrypt(jsStr.getString("data"), skey + sysdate)).toString();
                jsStr.put("data", JSON.parse(result_data));
                result = jsStr.toJSONString();
            }

            return JsonUtils.readValueByType(result, type);
        } catch (Exception e) {
            logger.error("Http Post Status:解析数据失败！",e);
            return new ResultInfo<>(-1, "客户端解析数据失败");
        } finally {
            response.close();
        }
    }

}
