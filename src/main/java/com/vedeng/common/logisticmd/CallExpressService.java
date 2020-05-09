package com.vedeng.common.logisticmd;
import com.sf.csim.express.service.CallExpressServiceTools;
import com.vedeng.system.model.SysOptionDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class CallExpressService {
	static Logger logger= LoggerFactory.getLogger(CallExpressService.class);
	@SuppressWarnings("static-access")
	public static String getExpressNo(String xmlStr,List<SysOptionDefinition> apiList) {
			String respXml = null;
			String reqURL = "https://bsp-oisp.sf-express.com/bsp-oisp/sfexpressService";
			String gk= "";
			String yj="";
			String jy="";
			if(apiList!=null && apiList.size()>0){
				for (SysOptionDefinition s : apiList) {
					if(s.getSysOptionDefinitionId()==690){
						gk = s.getComments();
					}
					if(s.getSysOptionDefinitionId()==691){
						jy = s.getComments();
					}
					if(s.getSysOptionDefinitionId()==692){
						yj = s.getComments();
					}
			}
			String clientCode = gk;// 此处替换为您在丰桥平台获取的顾客编码
			String checkword = jy;// 此处替换为您在丰桥平台获取的校验码
			CallExpressServiceTools client = CallExpressServiceTools.getInstance();
			String myReqXML = xmlStr.replace("SLKJ2019", clientCode);
				logger.info("请求报文：" + myReqXML);
			respXml = client.callSfExpressServiceByCSIM(reqURL, myReqXML, clientCode, checkword);
			if (respXml != null) {

				logger.info("getExpressNo 返回报文: " + respXml);

			}
		}
		
		return respXml;
	}

	public static void main(String[] args) throws Exception {
		String reqURL = "https://bsp-oisp.sf-express.com/bsp-oisp/sfexpressService";
		 String myReqXML="<Request service='RouteService' lang='zh-CN'>\n" +
				 "    <Head>BDYL</Head>\n" +
				 "    <Body>\n" +
				 "        <RouteRequest\n" +
				 "            tracking_type='1'\n" +
				 "            method_type='1'\n" +
				 "            tracking_number='SF1011671035339'\n" +
				 "             />\n" +
				 "    </Body>\n" +
				 "</Request>";
		CallExpressServiceTools client = CallExpressServiceTools.getInstance();
		String respXml = client.callSfExpressServiceByCSIM(reqURL, myReqXML, "BDYL", "5gb7xMbhwraNITKUbyrXvqTygoUBxT9e");
		System.out.println(respXml);
	}

}
	

