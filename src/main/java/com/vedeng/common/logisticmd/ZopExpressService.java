package com.vedeng.common.logisticmd;
import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.nio.Buffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.vedeng.common.util.ExpressUtil;
import com.vedeng.logistics.controller.WarehousesOutController;
import com.vedeng.system.model.SysOptionDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZopExpressService {
    public static Logger logger = LoggerFactory.getLogger(ZopExpressService.class);
	private static String URL ="";
	
	public static String getZopInfo(Integer type,String data, List<SysOptionDefinition> zotApiList) throws IOException, NoSuchAlgorithmException {
        logger.info("中通快递开始"+data);
		String companyId = "";
	    String key = "";
	    for (SysOptionDefinition sysOptionDefinition : zotApiList) {
			if(sysOptionDefinition.getSysOptionDefinitionId()==708){
				companyId = sysOptionDefinition.getComments();
			}
			if(sysOptionDefinition.getSysOptionDefinitionId()==709){
				key = sysOptionDefinition.getComments();
			}
		}
	    Map<String, String> parameters = new HashMap<>();
	    
		//获取单号
		if(type == 1){
			for (SysOptionDefinition sysOptionDefinition : zotApiList) {
				if(sysOptionDefinition.getSysOptionDefinitionId()==713){
					URL = sysOptionDefinition.getComments();
				}
			}
			parameters.put("data", data);
		}
		//获取集散地和大头笔
		else if(type == 2){
			for (SysOptionDefinition sysOptionDefinition : zotApiList) {
				if(sysOptionDefinition.getSysOptionDefinitionId()==714){
					URL = sysOptionDefinition.getComments();
				}
			}
			parameters.put("data", data);
			parameters.put("msg_type", "GETMARK");
		}
		//云打印面单
		else if(type ==3){
			for (SysOptionDefinition sysOptionDefinition : zotApiList) {
				if(sysOptionDefinition.getSysOptionDefinitionId()==715){
					URL = sysOptionDefinition.getComments();
				}
			}
			parameters.put("request", data);
		}
       
        parameters.put("company_id", companyId);

        String strToDigest = paramsToQueryString(parameters) + key;
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(strToDigest.getBytes(Charset.forName("UTF-8")));

        String dataDigest = Base64.getEncoder().encodeToString(md.digest());
        URL url = new URL(URL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        con.setDoOutput(true);
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);
        con.setRequestProperty("x-datadigest", dataDigest);
        con.setRequestProperty("x-companyid", companyId);
        DataOutputStream out = new DataOutputStream(con.getOutputStream());
        out.write(paramsToQueryStringUrlencoded(parameters).getBytes(Charset.forName("UTF-8")));
        out.flush();
        out.close();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream(),"utf-8"));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        logger.info("中通快递end"+data+content.toString());
        return content.toString();
    }

    public static String paramsToQueryString(Map<String, String> params) {
        return params.entrySet().stream().map(e -> e.getKey() + "=" + e.getValue()).collect(Collectors.joining("&"));
    }


    public static String paramsToQueryStringUrlencoded(Map<String, String> params) {
        return params.entrySet().stream().map(e -> {
            try {
                return e.getKey() + "=" + URLEncoder.encode(e.getValue(), "UTF-8");
            } catch (UnsupportedEncodingException e1) {
                return e.getValue();
            }
        }).collect(Collectors.joining("&"));
    }
}
