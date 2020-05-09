package com.vedeng.common.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.common.constants.Contant;
import com.vedeng.common.shiro.JedisUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.vedeng.aftersales.model.AfterSales;
import com.vedeng.aftersales.model.AfterSalesDetail;
import com.vedeng.authorization.dao.CompanyMapper;
import com.vedeng.authorization.model.Company;
import com.vedeng.authorization.model.Region;
import com.vedeng.common.logisticmd.ZopExpressService;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.logistics.model.Express;
import com.vedeng.logistics.model.FileDelivery;
import com.vedeng.logistics.service.ExpressService;
import com.vedeng.order.model.Buyorder;
import com.vedeng.order.model.Saleorder;
import com.vedeng.system.dao.AddressMapper;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.model.vo.AddressVo;
import com.vedeng.system.model.vo.ParamsConfigVo;
import com.vedeng.system.service.AddressService;
import com.vedeng.system.service.RegionService;
import com.vedeng.trader.model.TraderAddress;
import com.vedeng.trader.model.TraderContact;
import com.vedeng.trader.service.TraderCustomerService;

import sun.misc.BASE64Encoder;
/**
 * 
 * <b>Description:</b><br> 
 * @author scott
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.common.util
 * <br><b>ClassName:</b> ExpressUtil
 * <br><b>Date:</b> 2018年5月4日 下午1:06:21
 */
public class ExpressUtil {
	public static Logger logger = LoggerFactory.getLogger(ExpressUtil.class);

	WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
    private AddressService addressService = (AddressService) context.getBean("addressService");
    private TraderCustomerService traderCustomerService = (TraderCustomerService) context.getBean("traderCustomerService");
    private RegionService regionService = (RegionService) context.getBean("regionService");


    
	/**
	 * 
	 * <b>Description:</b><br> 生成顺丰xml字符串报文
	 * @param saleorder
	 * @param type
	 * @param companyId 
	 * @param btype 
	 * @param apiList 
	 * @param ob 
	 * @param shType 
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2018年5月7日 下午5:51:11
	 */
	public  ResultInfo<?> createXml(Object obj, Integer type, Integer companyId, Integer btype, List<SysOptionDefinition> apiList, Object ob, Integer shType) {
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
		}
		String xmlString = "";
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			document.setXmlStandalone(true);
			Element request = document.createElement("Request");
			request.setAttribute("service","OrderService");
			request.setAttribute("lang", "zh-CN");
			document.appendChild(request);

			Element head = document.createElement("Head");
			head.setTextContent(gk);
			request.appendChild(head);

			Element body = document.createElement("Body");
			request.appendChild(body);
			Element order = document.createElement("Order");
			TraderAddress traderAddress = new TraderAddress();
			String areas = "";
			//销售单信息
			if(btype==1){
				Saleorder saleorder = (Saleorder)obj;
				order.setAttribute("orderid",saleorder.getSaleorderNo()+System.currentTimeMillis());
				order.setAttribute("express_type","1");
				
				//物流普发
				if(type == 1){
					
					//获取公司发货信息
					ParamsConfigVo paramsConfigVo = new ParamsConfigVo();
					paramsConfigVo.setCompanyId(companyId);
					paramsConfigVo.setParamsKey(100);
					AddressVo delivery = addressService.getDeliveryAddress(paramsConfigVo);
					String[] area = null;
					if(delivery.getAreas()==null || "".equals(delivery.getAreas())){
						return new ResultInfo(-1,"发货方地区信息为空");
					}else{
						area = delivery.getAreas().split("\\s+");
					}
					if(area.length<2){
						return new ResultInfo(-1,"发货方地区信息不全");
					}
					order.setAttribute("j_province",area[0]+"省");
					order.setAttribute("j_city",area[1]+"市");
					if(delivery.getCompanyName()==null || "".equals(delivery.getCompanyName()) ){
						return new ResultInfo(-1,"发货方公司名称为空");
					}else{
						order.setAttribute("j_company",delivery.getCompanyName());
					}
					if(delivery.getContactName()==null || "".equals(delivery.getContactName())){
						return new ResultInfo(-1,"发货方联系人为空");
					}else{
						order.setAttribute("j_contact",delivery.getContactName());
					}
					if((delivery.getMobile()==null || "".equals(delivery.getMobile())) && (delivery.getTelephone()==null || "".equals(delivery.getTelephone()))){
						return new ResultInfo(-1,"发货方联系方式为空");
					}
					if(delivery.getMobile()!=null && !"".equals(delivery.getMobile())){
						order.setAttribute("j_tel",delivery.getMobile());
					}else if(delivery.getTelephone()!=null && !"".equals(delivery.getTelephone())){
						order.setAttribute("j_tel",delivery.getTelephone());
					}
					if(delivery.getAddress()==null || "".equals(delivery.getAddress())){
						return new ResultInfo(-1,"发货方详细地址为空");
					}else{
						order.setAttribute("j_address",delivery.getAddress());
					}
				}
				//物流直发
				else if(type == 2){
					String[] area  = null;
					if(saleorder.getTraderArea()==null || "".equals(saleorder.getTraderArea())){
						return new ResultInfo(-1,"发货方地区信息为空");
					}else{
						area = saleorder.getTraderArea().split("\\s+");
					}
					if(area.length<2){
						//查询地区信息
					    if(saleorder.getOrderType()!=null && saleorder.getOrderType()== 5){
					        traderAddress.setTraderAddressId(saleorder.getTraderAreaId());
					    }else{
					        traderAddress.setTraderAddressId(saleorder.getTraderAddressId());
					    }
						traderAddress=traderCustomerService.getTraderAddress(traderAddress);
						if(traderAddress==null){
						    return new ResultInfo(-1,"发货方地区信息为空");
						}
						List<Region> araes = (List<Region>) regionService.getRegion(traderAddress.getAreaId(),1);
						if(araes==null || araes.size()<3){
							return new ResultInfo(-1,"发货方地区信息不全");
						}else{
							area =  new String[2]; 
							area[0] = araes.get(1).getRegionName();
							area[1] = araes.get(2).getRegionName();
						}
					}
					order.setAttribute("j_province",area[0]+"省");
					if(area.length>1){
						order.setAttribute("j_city",area[1]+"市");
					}else{
						order.setAttribute("j_city","");
					}
					if(saleorder.getTraderName()==null || "".equals(saleorder.getTraderName())){
						return new ResultInfo(-1,"发货方公司名称为空");
					}else{
						order.setAttribute("j_company",saleorder.getTraderName());
					}
					if(saleorder.getTraderContactName()==null || "".equals(saleorder.getTraderContactName())){
						return new ResultInfo(-1,"发货方联系人为空");
					}else{
						order.setAttribute("j_contact",saleorder.getTraderContactName());
					}
					if((saleorder.getTraderContactTelephone()==null || "".equals(saleorder.getTraderContactTelephone())) && (saleorder.getTraderContactMobile()==null || "".equals(saleorder.getTraderContactMobile()))){
						return new ResultInfo(-1,"发货方联系方式为空");
					}
					if(saleorder.getTraderContactMobile()!=null && !"".equals(saleorder.getTraderContactMobile())){
						order.setAttribute("j_tel",saleorder.getTraderContactMobile());
					}else if(saleorder.getTraderContactTelephone()!=null && !"".equals(saleorder.getTraderContactTelephone())){
						order.setAttribute("j_tel",saleorder.getTraderContactTelephone());
					}
					if(saleorder.getTraderAddress()==null || "".equals(saleorder.getTraderAddress())){
						return new ResultInfo(-1,"发货方详细地址为空");
					}else{
						order.setAttribute("j_address",saleorder.getTraderAddress());
					}
				}
				//财务
				else if(type == 3){
					order.setAttribute("j_province","江苏省");
					order.setAttribute("j_city","南京市");
					order.setAttribute("j_company","南京贝登医疗股份有限公司");
					order.setAttribute("j_contact","财务部");
					order.setAttribute("j_tel","025-68538253");
					order.setAttribute("j_address","白下高新技术产业园永丰大道紫霞路斯坦德电子商务大厦北楼三层 ");
				}
				//物流收货信息
				if(type ==2 || type==1){
					String[] areaArr = null;
					if(saleorder.getTakeTraderArea()==null || "".equals(saleorder.getTakeTraderArea())){
						return new ResultInfo(-1,"收货方地区信息为空");
					}else{
						areaArr = saleorder.getTakeTraderArea().split("\\s+");
					}
					if(areaArr.length<2){
						//查询地区信息
					    if(saleorder.getOrderType()!=null && saleorder.getOrderType()== 5){
                            traderAddress.setTraderAddressId(saleorder.getTakeTraderAreaId());
                        }else{
                            traderAddress.setTraderAddressId(saleorder.getTakeTraderAddressId());
                        }
						traderAddress=traderCustomerService.getTraderAddress(traderAddress);
					    if(traderAddress==null){
                            return new ResultInfo(-1,"收货方地区信息为空");
                        }
						List<Region> araes = (List<Region>) regionService.getRegion(traderAddress.getAreaId(),1);
						if(araes==null || araes.size()<3){
							return new ResultInfo(-1,"收货方地区信息不全");
						}else{
							areaArr =  new String[2]; 
							areaArr[0] = araes.get(1).getRegionName();
							areaArr[1] = araes.get(2).getRegionName();
						}
					}
					order.setAttribute("d_province",areaArr[0]+"省");
					if(areaArr.length>1){
						order.setAttribute("d_city",areaArr[1]+"市");
					}else{
						order.setAttribute("d_city","");
					}
					order.setAttribute("d_county","");
					if(saleorder.getTakeTraderName()==null || "".equals(saleorder.getTakeTraderName())){
						return new ResultInfo(-1,"收货方公司名称为空");
					}else{
						order.setAttribute("d_company",saleorder.getTakeTraderName());
					}
					if(saleorder.getTakeTraderContactName()==null || "".equals(saleorder.getTakeTraderContactName())){
						return new ResultInfo(-1,"收货方联系人为空");
					}else{
						order.setAttribute("d_contact",saleorder.getTakeTraderContactName());
					}
					if((saleorder.getTakeTraderContactTelephone()==null || "".equals(saleorder.getTakeTraderContactTelephone())) && (saleorder.getTraderContactMobile()==null || "".equals(saleorder.getTraderContactMobile()))){
						return new ResultInfo(-1,"收货方联系信息不全");
					}
					if(saleorder.getTakeTraderContactMobile()!=null && !"".equals(saleorder.getTakeTraderContactMobile())){
							order.setAttribute("d_tel",saleorder.getTakeTraderContactMobile());
					}else if(saleorder.getTakeTraderContactTelephone()!=null && !"".equals(saleorder.getTakeTraderContactTelephone())){
						order.setAttribute("d_tel",saleorder.getTakeTraderContactTelephone());
					}
					if(saleorder.getTakeTraderAddress()==null || "".equals(saleorder.getTakeTraderAddress())){
						return new ResultInfo(-1,"收货方详细地址为空");
					}else{
						order.setAttribute("d_address",saleorder.getTakeTraderAddress());
					}
				}
				//财务收票信息
				else if(type ==3){
					String[] areaArr =null;
					if(saleorder.getInvoiceTraderArea()==null || "".equals(saleorder.getInvoiceTraderArea())){
						return new ResultInfo(-1,"收票方地区信息为空");
					}else{
						areaArr = saleorder.getInvoiceTraderArea().split("\\s+");
					}
					if(areaArr.length<2){
						//查询地区信息
					    if(saleorder.getOrderType()!=null && saleorder.getOrderType()== 5){
                            traderAddress.setTraderAddressId(saleorder.getInvoiceTraderAreaId());
                        }else{
                            traderAddress.setTraderAddressId(saleorder.getInvoiceTraderAddressId());
                        }
						traderAddress=traderCustomerService.getTraderAddress(traderAddress);
						if(traderAddress==null){
                            return new ResultInfo(-1,"收票方地区信息为空");
                        }
						List<Region> araes = (List<Region>) regionService.getRegion(traderAddress.getAreaId(),1);
						if(araes==null || araes.size()<3){
							return new ResultInfo(-1,"收票方地区信息不全");
						}else{
							areaArr =  new String[2]; 
							areaArr[0] = araes.get(1).getRegionName();
							areaArr[1] = araes.get(2).getRegionName();
						}
					}
					order.setAttribute("d_province",areaArr[0]+"省");
					if(areaArr.length>1){
						order.setAttribute("d_city",areaArr[1]+"市");
					}else{
						order.setAttribute("d_city","");
					}
					order.setAttribute("d_county","");
					if(saleorder.getInvoiceTraderName()==null || "".equals(saleorder.getInvoiceTraderName())){
						return new ResultInfo(-1,"收票方公司名称为空");
					}else{
						order.setAttribute("d_company",saleorder.getInvoiceTraderName());
					}
					if(saleorder.getInvoiceTraderContactName()==null || "".equals(saleorder.getInvoiceTraderContactName())){
						return new ResultInfo(-1,"收票方联系人为空");
					}else{
						order.setAttribute("d_contact",saleorder.getInvoiceTraderContactName());
					}
					if((saleorder.getInvoiceTraderContactTelephone()==null || "".equals(saleorder.getInvoiceTraderContactTelephone())) && (saleorder.getInvoiceTraderContactMobile()==null || "".equals(saleorder.getInvoiceTraderContactMobile()))){
						return new ResultInfo(-1,"收票方联系信息不全");
					}
					if(saleorder.getInvoiceTraderContactMobile()!=null && !"".equals(saleorder.getInvoiceTraderContactMobile())){
						order.setAttribute("d_tel",saleorder.getInvoiceTraderContactMobile());
					}else if(saleorder.getInvoiceTraderContactTelephone()!=null && !"".equals(saleorder.getInvoiceTraderContactTelephone())){
						order.setAttribute("d_tel",saleorder.getInvoiceTraderContactTelephone());
					} 
					if(saleorder.getInvoiceTraderAddress()==null && "".equals(saleorder.getInvoiceTraderAddress())){
						return new ResultInfo(-1,"收票方详细地址为空");
					}else{
						order.setAttribute("d_address",saleorder.getInvoiceTraderAddress());
					}
				}
			}
			//售后信息
			else if(btype==3 || btype==4 ){
				AfterSalesDetail av = (AfterSalesDetail)ob;
				order.setAttribute("orderid",av.getAfterSalesDetailId()+""+System.currentTimeMillis());
				order.setAttribute("express_type","1");
				if(shType==0){
					if(type==3){//财务
						
						//获取公司发货信息
						order.setAttribute("j_province","江苏省");
						order.setAttribute("j_city","南京市");
						order.setAttribute("j_company","南京贝登医疗股份有限公司");
						order.setAttribute("j_contact","财务部");
						order.setAttribute("j_tel","025-68538253");
						order.setAttribute("j_address","白下高新技术产业园永丰大道紫霞路斯坦德电子商务大厦北楼三层 ");
						String[] areaArr = null;
						if(av.getInvoiceTraderArea()==null || "".equals(av.getInvoiceTraderArea())){
							return new ResultInfo(-1,"收票方地区信息为空");
						}else{
							areaArr = av.getInvoiceTraderArea().split("\\s+");
						}
						if(areaArr.length<2){
							//查询地区信息
							traderAddress.setTraderAddressId(av.getInvoiceTraderAddressId());
							traderAddress=traderCustomerService.getTraderAddress(traderAddress);
							List<Region> araes = (List<Region>) regionService.getRegion(traderAddress.getAreaId(),1);
							if(araes==null || araes.size()<3){
								return new ResultInfo(-1,"收票方地区信息不全");
							}else{
								areaArr =  new String[2]; 
								areaArr[0] = araes.get(1).getRegionName();
								areaArr[1] = araes.get(2).getRegionName();
							}
						}
						order.setAttribute("d_province",areaArr[0]+"省");
						if(areaArr.length>1){
							order.setAttribute("d_city",areaArr[1]+"市");
						}else{
							order.setAttribute("d_city","");
						}
						order.setAttribute("d_county","");
						if(av.getInvoiceTraderName()==null && "".equals(av.getInvoiceTraderName())){
							return new ResultInfo(-1,"收票方公司名称为空");
						}else{
							order.setAttribute("d_company",av.getInvoiceTraderName());
						}
						if(av.getInvoiceTraderContactName()==null || "".equals(av.getInvoiceTraderContactName())){
							return new ResultInfo(-1,"收票方联系人为空");
						}else{
							order.setAttribute("d_contact",av.getInvoiceTraderContactName());
						}
						if((av.getInvoiceTraderContactTelephone()==null || "".equals(av.getInvoiceTraderContactTelephone())) && (av.getInvoiceTraderContactMobile()==null || "".equals(av.getInvoiceTraderContactMobile()))){
							return new ResultInfo(-1,"收票方联系信息不全");
						}
						if(av.getInvoiceTraderContactMobile()!=null && !"".equals(av.getInvoiceTraderContactMobile())){
							order.setAttribute("d_tel",av.getInvoiceTraderContactMobile());
						} else if(av.getInvoiceTraderContactTelephone()!=null && !"".equals(av.getInvoiceTraderContactTelephone())){
							order.setAttribute("d_tel",av.getInvoiceTraderContactTelephone());
						} 
						if(av.getInvoiceTraderAddress()==null && "".equals(av.getInvoiceTraderAddress())){
							return new ResultInfo(-1,"收票方详细地址为空");
						}else{
							order.setAttribute("d_address",av.getInvoiceTraderAddress());
						}
					}
				}else{
					//物流--销售售后
					if(shType==1){
						Saleorder saleorder = (Saleorder)obj;
						//普发
						if(type==1){
							String[] area = null;
							ParamsConfigVo paramsConfigVo = new ParamsConfigVo();
							paramsConfigVo.setCompanyId(companyId);
							paramsConfigVo.setParamsKey(100);
							AddressVo delivery = addressService.getDeliveryAddress(paramsConfigVo);
							if(delivery.getAreas()==null || "".equals(delivery.getAreas())){
								return new ResultInfo(-1,"发货方地区信息为空");
							}else{
								area = delivery.getAreas().split("\\s+");
							}
							if(area.length<2){
								return new ResultInfo(-1,"发货方地区信息不全");
							}
							order.setAttribute("j_province",area[0]+"省");
							order.setAttribute("j_city",area[1]+"市");
							if(delivery.getCompanyName()==null || "".equals(delivery.getCompanyName()) ){
								return new ResultInfo(-1,"发货方公司名称为空");
							}else{
								order.setAttribute("j_company",delivery.getCompanyName());
							}
							if(delivery.getContactName()==null || "".equals(delivery.getContactName())){
								return new ResultInfo(-1,"发货方联系人为空");
							}else{
								order.setAttribute("j_contact",delivery.getContactName());
							}
							if((delivery.getMobile()==null || "".equals(delivery.getMobile())) && (delivery.getTelephone()==null || "".equals(delivery.getTelephone()))){
								return new ResultInfo(-1,"发货方联系方式为空");
							}
							if(delivery.getMobile()!=null && !"".equals(delivery.getMobile())){
								order.setAttribute("j_tel",delivery.getMobile());
							}else if(delivery.getTelephone()!=null && !"".equals(delivery.getTelephone())){
								order.setAttribute("j_tel",delivery.getTelephone());
							}
							if(delivery.getAddress()==null || "".equals(delivery.getAddress())){
								return new ResultInfo(-1,"发货方详细地址为空");
							}else{
								order.setAttribute("j_address",delivery.getAddress());
							}
						}
						//物流--销售售后直发
						else if(type==2){
							String[] area = null;
							if(saleorder.getTraderArea()==null || "".equals(saleorder.getTraderArea())){
								return new ResultInfo(-1,"发货方地区信息为空");
							}else{
								area = saleorder.getTraderArea().split("\\s+");
							}
							if(area.length<2){
								//查询地区信息
							    if(saleorder.getOrderType()!=null && saleorder.getOrderType()== 5){
		                            traderAddress.setTraderAddressId(saleorder.getTraderAreaId());
		                        }else{
		                            traderAddress.setTraderAddressId(saleorder.getTraderAddressId());
		                        }
								traderAddress=traderCustomerService.getTraderAddress(traderAddress);
								if(traderAddress==null){
		                            return new ResultInfo(-1,"发货方地区信息为空");
		                        }
								List<Region> araes = (List<Region>) regionService.getRegion(traderAddress.getAreaId(),1);
								if(araes==null || araes.size()<3){
									return new ResultInfo(-1,"发货方地区信息不全");
								}else{
									area =  new String[2]; 
									area[0] = araes.get(1).getRegionName();
									area[1] = araes.get(2).getRegionName();
								}
							}
							order.setAttribute("j_province",area[0]+"省");
							if(area.length>1){
								order.setAttribute("j_city",area[1]+"市");
							}else{
								order.setAttribute("j_city","");
							}
							if(saleorder.getTraderName()==null || "".equals(saleorder.getTraderName())){
								return new ResultInfo(-1,"发货方公司名称为空");
							}else{
								order.setAttribute("j_company",saleorder.getTraderName());
							}
							if(saleorder.getTraderContactName()==null || "".equals(saleorder.getTraderContactName())){
								return new ResultInfo(-1,"发货方联系人为空");
							}else{
								order.setAttribute("j_contact",saleorder.getTraderContactName());
							}
							if((saleorder.getTraderContactTelephone()==null || "".equals(saleorder.getTraderContactTelephone())) && (saleorder.getTraderContactMobile()==null || "".equals(saleorder.getTraderContactMobile()))){
								return new ResultInfo(-1,"发货方联系方式为空");
							}
							if(saleorder.getTraderContactMobile()!=null && !"".equals(saleorder.getTraderContactMobile())){
								order.setAttribute("j_tel",saleorder.getTraderContactMobile());
							}else if(saleorder.getTraderContactTelephone()!=null && !"".equals(saleorder.getTraderContactTelephone())){
								order.setAttribute("j_tel",saleorder.getTraderContactTelephone());
							} 
							if(saleorder.getTraderAddress()==null || "".equals(saleorder.getTraderAddress())){
								return new ResultInfo(-1,"发货方详细地址为空");
							}else{
								order.setAttribute("j_address",saleorder.getTraderAddress());
							}
						}
					}
					//采购售后
					else if(shType == 2){
						Buyorder buyorder = (Buyorder)obj;
						order.setAttribute("orderid",buyorder.getBuyorderNo()+System.currentTimeMillis());
						order.setAttribute("express_type","1");
						//获取公司发货信息
						ParamsConfigVo paramsConfigVo = new ParamsConfigVo();
						paramsConfigVo.setCompanyId(companyId);
						paramsConfigVo.setParamsKey(100);
						AddressVo delivery = addressService.getDeliveryAddress(paramsConfigVo);
						String[] area = null;
						if(delivery.getAreas()==null || "".equals(delivery.getAreas())){
							return new ResultInfo(-1,"发货方地区信息为空");
						}else{
							area = delivery.getAreas().split("\\s+");
						}
						if(area.length<2){
							return new ResultInfo(-1,"发货方地区信息不全");
						}
						order.setAttribute("j_province",area[0]+"省");
						order.setAttribute("j_city",area[1]+"市");
						if(delivery.getCompanyName()==null || "".equals(delivery.getCompanyName()) ){
							return new ResultInfo(-1,"发货方公司名称为空");
						}else{
							order.setAttribute("j_company",delivery.getCompanyName());
						}
						if(delivery.getContactName()==null || "".equals(delivery.getContactName())){
							return new ResultInfo(-1,"发货方联系人为空");
						}else{
							order.setAttribute("j_contact",delivery.getContactName());
						}
						if((delivery.getMobile()==null || "".equals(delivery.getMobile())) && (delivery.getTelephone()==null || "".equals(delivery.getTelephone()))){
							return new ResultInfo(-1,"发货方联系方式为空");
						}
						if(delivery.getMobile()!=null && !"".equals(delivery.getMobile())){
							order.setAttribute("j_tel",delivery.getMobile());
						}else if(delivery.getTelephone()!=null && !"".equals(delivery.getTelephone())){
							order.setAttribute("j_tel",delivery.getTelephone());
						}
						if(delivery.getAddress()==null || "".equals(delivery.getAddress())){
							return new ResultInfo(-1,"发货方详细地址为空");
						}else{
							order.setAttribute("j_address",delivery.getAddress());
						}
					}
					String[] areaArr = null;
					if(av.getArea()==null || "".equals(av.getArea())){
						return new ResultInfo(-1,"收货方地区信息为空");
					}else{
						areaArr = av.getArea().split("\\s+");
					}
					if(areaArr.length<2){
						//查询地区信息
						traderAddress.setTraderAddressId(av.getAddressId());
						traderAddress=traderCustomerService.getTraderAddress(traderAddress);
						List<Region> araes = (List<Region>) regionService.getRegion(traderAddress.getAreaId(),1);
						if(araes==null || araes.size()<3){
							return new ResultInfo(-1,"收货方地区信息不全");
						}else{
							areaArr =  new String[2]; 
							areaArr[0] = araes.get(1).getRegionName();
							areaArr[1] = araes.get(2).getRegionName();
						}
					}
					order.setAttribute("d_province",areaArr[0]+"省");
					if(areaArr.length>1){
						order.setAttribute("d_city",areaArr[1]+"市");
					}else{
						order.setAttribute("d_city","");
					}
					order.setAttribute("d_county","");
					if(av.getTraderName()==null || "".equals(av.getTraderName())){
						return new ResultInfo(-1,"收货方公司名称为空");
					}else{
						order.setAttribute("d_company",av.getTraderName());
					}
					if(av.getTraderContactName()==null || "".equals(av.getTraderContactName())){
						return new ResultInfo(-1,"收货方联系人为空");
					}else{
						order.setAttribute("d_contact",av.getTraderContactName());
					}
					if((av.getTraderContactTelephone()==null || "".equals(av.getTraderContactTelephone())) && (av.getTraderContactMobile()==null || "".equals(av.getTraderContactMobile()))){
						return new ResultInfo(-1,"收货方联系信息不全");
					}
					if(av.getTraderContactMobile()!=null && !"".equals(av.getTraderContactMobile())){
						order.setAttribute("d_tel",av.getTraderContactMobile());
					}else if(av.getTraderContactTelephone()!=null && !"".equals(av.getTraderContactTelephone())){
						order.setAttribute("d_tel",av.getTraderContactTelephone());
					} 
					if(av.getAddress()==null || "".equals(av.getAddress())){
						return new ResultInfo(-1,"收货方详细地址为空");
					}else{
						order.setAttribute("d_address",av.getAddress());
					}
				}
				
			}
			//文件寄送信息
			else if(btype==-1){
				FileDelivery fileDelivery = (FileDelivery)obj;
				//文件的orderno本身就是唯一的
				order.setAttribute("orderid",fileDelivery.getFileDeliveryNo());
				order.setAttribute("express_type","1");
				//获取公司发货信息
				ParamsConfigVo paramsConfigVo = new ParamsConfigVo();
				paramsConfigVo.setCompanyId(companyId);
				paramsConfigVo.setParamsKey(100);
				AddressVo delivery = addressService.getDeliveryAddress(paramsConfigVo);
				String[] area = null;
				if(delivery.getAreas()==null && "".equals(delivery.getAreas())){
					return new ResultInfo(-1,"发货方地区信息为空");
				}else{
					area = delivery.getAreas().split("\\s+");
				}
				if(area.length<2){
					return new ResultInfo(-1,"发货方地区信息不全");
				}
				order.setAttribute("j_province",area[0]+"省");
				order.setAttribute("j_city",area[1]+"市");
				if(delivery.getCompanyName()==null || "".equals(delivery.getCompanyName()) ){
					return new ResultInfo(-1,"发货方公司名称为空");
				}else{
					order.setAttribute("j_company",delivery.getCompanyName());
				}
				if(delivery.getContactName()==null || "".equals(delivery.getContactName())){
					return new ResultInfo(-1,"发货方联系人为空");
				}else{
					order.setAttribute("j_contact",delivery.getContactName());
				}
				if((delivery.getMobile()==null || "".equals(delivery.getMobile())) && (delivery.getTelephone()==null || "".equals(delivery.getTelephone()))){
					return new ResultInfo(-1,"发货方联系方式为空");
				}
				if(delivery.getMobile()!=null && !"".equals(delivery.getMobile())){
					order.setAttribute("j_tel",delivery.getMobile());
				}else if(delivery.getTelephone()!=null && !"".equals(delivery.getTelephone())){
					order.setAttribute("j_tel",delivery.getTelephone());
				}
				if(delivery.getAddress()==null || "".equals(delivery.getAddress())){
					return new ResultInfo(-1,"发货方详细地址为空");
				}else{
					order.setAttribute("j_address",delivery.getAddress());
				}
				
				String[] areaArr = null;
				if(fileDelivery.getArea()==null || "".equals(fileDelivery.getArea())){
					return new ResultInfo(-1,"收货方地区信息为空");
				}else{
					 areaArr = fileDelivery.getArea().split("\\s+");
				}
				TraderContact  traderContact = new TraderContact();
				if(areaArr.length<2){
					//查询地区信息
					/*traderAddress.setTraderAddressId(fileDelivery.getTraderAddressId());
					traderAddress=traderCustomerService.getTraderAddress(traderAddress);*/
					
					/**********************查询客户的地区start*****************************/
					//联系人默认信息
				    traderContact = traderCustomerService.getTraderContactInfo(fileDelivery);
				    List<Region> araes = null;
				    if(traderContact.getAreaId()!=null){
				    	araes = (List<Region>) regionService.getRegion(traderContact.getAreaId(),1);
				    }
					/**********************查询客户的地区end*****************************/
					if(araes==null || araes.size()<3){
						return new ResultInfo(-1,"收货方地区信息不全");
					}else{
						areaArr =  new String[2]; 
						areaArr[0] = araes.get(1).getRegionName();
						areaArr[1] = araes.get(2).getRegionName();
					}
				}
				order.setAttribute("d_province",areaArr[0]+"省");
				if(areaArr.length>1){
					order.setAttribute("d_city",areaArr[1]+"市");
				}else{
					order.setAttribute("d_city","");
				}
				order.setAttribute("d_county","");
				if(fileDelivery.getTraderName()==null || "".equals(fileDelivery.getTraderName())){
					return new ResultInfo(-1,"收货方公司名称为空");
				}else{
					order.setAttribute("d_company",fileDelivery.getTraderName());
				}
				if(fileDelivery.getTraderContactName()==null && "".equals(fileDelivery.getTraderContactName())){
					if(StringUtils.isNotBlank(traderContact.getName())){
						order.setAttribute("d_contact",traderContact.getName());
					}else{
						return new ResultInfo(-1,"收货方联系人称为空");
					}
				}else{
					order.setAttribute("d_contact",fileDelivery.getTraderContactName());
				}
				if((fileDelivery.getTelephone()==null || "".equals(fileDelivery.getTelephone())) && (fileDelivery.getMobile()==null || "".equals(fileDelivery.getMobile()))){
					if(StringUtil.isBlank(traderContact.getTelephone()) && StringUtil.isBlank(traderContact.getTelephone())){
						return new ResultInfo(-1,"收件方联系信息不全");
					}
				}
				if(fileDelivery.getMobile()!=null && !"".equals(fileDelivery.getMobile())){
					//优先取手机号    
				    order.setAttribute("d_tel",fileDelivery.getMobile());
				}else if(fileDelivery.getTelephone()!=null && !"".equals(fileDelivery.getTelephone())){
					//没手机号，取电话
					order.setAttribute("d_tel",fileDelivery.getTelephone());
				}else{
					//手机号、电话都没有，取默认联系人信息
					if(StringUtils.isNotBlank(traderContact.getMobile())){
						order.setAttribute("d_tel",traderContact.getMobile());
					}else if(StringUtils.isNotBlank(traderContact.getTelephone())){
						order.setAttribute("d_tel",traderContact.getTelephone());
					}
				}
				if(fileDelivery.getAddress()==null || "".equals(fileDelivery.getAddress())){
					if(StringUtils.isBlank(traderContact.getAddress())){
						return new ResultInfo(-1,"收件方详细地址为空");
					}else{
						order.setAttribute("d_address",traderContact.getAddress());
					}
				}else{
					order.setAttribute("d_address",fileDelivery.getAddress());
				}
			}
			order.setAttribute("parcel_quantity","1");
			order.setAttribute("pay_method","1");
			order.setAttribute("custid",yj);
			order.setAttribute("customs_batchs","");
			order.setAttribute("cargo","");
			body.appendChild(order);

			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer transformer = transFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			DOMSource domSource = new DOMSource(document);

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			transformer.transform(domSource, new StreamResult(bos));
			
			xmlString = bos.toString();

			xmlString = xmlString.substring(xmlString.indexOf(">")+1);
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return new ResultInfo(-1, "失败500", xmlString);
		}

		return new ResultInfo(0, "成功", xmlString);
	}
    /**
     * 
     * <b>Description:</b><br> 对顺丰xml字符串解析
     * @param strXML
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2018年5月9日 下午3:08:29
     */
	public static String parserXML(String strXML) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		String mailno = "";
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			StringReader sr = new StringReader(strXML);
			InputSource is = new InputSource(sr);
			Document doc = builder.parse(is);
			Element rootElement = doc.getDocumentElement();
			NodeList responseNodeList = rootElement.getChildNodes();   
			for (int i = 0; i < responseNodeList.getLength(); i++) {
				Node node = responseNodeList.item(i);
				NodeList properties = node.getChildNodes();
				for (int j = 0; j < properties.getLength(); j++) {
					Node property = properties.item(j);
					if("OrderResponse".equals(property.getNodeName())){
						
						//获取快递单号
						mailno = ((Element) property ).getAttribute("mailno");
					}
				}
			}
		} catch (ParserConfigurationException e) {
			logger.error(Contant.ERROR_MSG, e);
		} catch (SAXException e) {
			logger.error(Contant.ERROR_MSG, e);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return mailno;
	}
	/**
	 * 
	 * <b>Description:</b><br> 生成中通请求的json串
	 * @param obj
	 * @param ep 
	 * @param type
	 * @param ob
	 * @param btype 
	 * @param opType 生成json类别1获取快递单2获取大头笔3打印
	 * @param map  大头笔集散地信息
	 * @param btype 业务类型 
	 * @param zotApiList 
	 * @param pid 
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2018年6月13日 下午1:54:27
	 */
	public ResultInfo<?> createJson(Object obj, Express ep, Integer type, Integer companyId, Integer btype, Object ob,
			Integer shType, Integer opType, Map<String, String> map, List<SysOptionDefinition> zotApiList, Integer pid){
		
		JSONObject content = new JSONObject();
		JSONObject jsonObj = new JSONObject();
		TraderAddress traderAddress = new TraderAddress();
		
		Map <String, String> sender = new HashMap <String, String>();
		Map <String, String> receiver = new HashMap <String, String>();
		String jsonStr = "";
		String expressForVedengId="VD"+ JedisUtils.increaseExpressNo();
		//对应的销售单信息
		if(btype==1){
			Saleorder saleorder = (Saleorder)obj;
			if(StringUtils.isNotBlank(saleorder.getBussinessNo())){
				saleorder.setBussinessNo(expressForVedengId);
			}
			//物流普发
			if(type == 1){
				 
				//获取公司发货信息
				ParamsConfigVo paramsConfigVo = new ParamsConfigVo();
				paramsConfigVo.setCompanyId(companyId);
				paramsConfigVo.setParamsKey(100);
				AddressVo delivery = addressService.getDeliveryAddress(paramsConfigVo);
				String[] area = null;
				if(delivery.getAreas()==null || "".equals(delivery.getAreas())){
					return new ResultInfo(-1,"发货方地区信息为空");
				}else{
					area = delivery.getAreas().split("\\s+");
				}
				if(area.length<3){
					return new ResultInfo(-1,"发货方地区信息不全");
				}
				//快递单号
				if(opType==1){
					sender.put("city", area[0]+","+area[1]+","+area[2]);
					content.put("id", saleorder.getBussinessNo()+"");
				}
				//大头笔、集散地
				else if(opType == 2){
				    content.put("unionCode", saleorder.getBussinessNo()+"");
					content.put("send_province", area[0]);
					content.put("send_city", area[1]);
					content.put("send_district", area[2]);
					content.put("send_address", delivery.getAddress());
				}
				//打印面单
				else if(opType == 3){
				    content.put("partnerCode", saleorder.getBussinessNo()+"");
					sender.put("prov", area[0]);
					sender.put("city", area[1]);
					sender.put("county", area[2]);
				}
				if(opType ==1 || opType == 3){
					if(delivery.getCompanyName()==null || "".equals(delivery.getCompanyName()) ){
						return new ResultInfo(-1,"发货方公司名称为空");
					}else{
						sender.put("company", delivery.getCompanyName());
					}
					if(delivery.getContactName()==null || "".equals(delivery.getContactName())){
						return new ResultInfo(-1,"发货方联系人为空");
					}else{
						sender.put("name", delivery.getContactName());
					}
					if((delivery.getMobile()==null || "".equals(delivery.getMobile())) && (delivery.getTelephone()==null && "".equals(delivery.getTelephone()))){
						return new ResultInfo(-1,"发货方联系方式为空");
					}
					if(delivery.getMobile()!=null && !"".equals(delivery.getMobile())){
						sender.put("mobile",delivery.getMobile());
					}else if(delivery.getTelephone()!=null && !"".equals(delivery.getTelephone())){
						sender.put("phone", delivery.getTelephone());
					}
					if(delivery.getAddress()==null || "".equals(delivery.getAddress())){
						return new ResultInfo(-1,"发货方详细地址为空");
					}else{
						sender.put("address", delivery.getAddress());
					}
				}
			}
			//物流直发
			else if(type == 2){
				String[] area  = null;
				if(saleorder.getTraderArea()==null || "".equals(saleorder.getTraderArea())){
					return new ResultInfo(-1,"发货方地区信息为空");
				}else{
					area = saleorder.getTraderArea().split("\\s+");
				}
				if(area.length<3){
					//查询地区信息
				    if(saleorder.getOrderType()!=null && saleorder.getOrderType()== 5){
                        traderAddress.setTraderAddressId(saleorder.getTraderAreaId());
                    }else{
                        traderAddress.setTraderAddressId(saleorder.getTraderAddressId());
                    }
					traderAddress=traderCustomerService.getTraderAddress(traderAddress);
					if(traderAddress==null){
                        return new ResultInfo(-1,"发货方地区信息为空");
                    }
					List<Region> araes = (List<Region>) regionService.getRegion(traderAddress.getAreaId(),1);
					if(araes==null || araes.size()<4){
						return new ResultInfo(-1,"发货方地区信息不全");
					}else{
						area =  new String[3]; 
						area[0] = araes.get(1).getRegionName();
						area[1] = araes.get(2).getRegionName();
						area[2] = araes.get(3).getRegionName();
					}
				}
				//快递单号
				if(opType==1){
					sender.put("city", area[0]+","+area[1]+","+area[2]);
					content.put("id", saleorder.getBussinessNo()+"");
				}
				//大头笔、集散地
				else if(opType == 2){
					content.put("unionCode", saleorder.getBussinessNo()+"");
					content.put("send_province", area[0]);
					content.put("send_city", area[1]);
					content.put("send_district", area[2]);
					content.put("send_address", saleorder.getTraderAddress());
				}
				//打印面单
				else if(opType == 3){
					content.put("partnerCode", saleorder.getBussinessNo()+"");
					sender.put("prov", area[0]);
					sender.put("city", area[1]);
					sender.put("county", area[2]);
				}
				if(opType==1 || opType == 3){
					if(saleorder.getTraderName()==null || "".equals(saleorder.getTraderName())){
						return new ResultInfo(-1,"发货方公司名称为空");
					}else{
						sender.put("company", saleorder.getTraderName());
					}
					if(saleorder.getTraderContactName()==null || "".equals(saleorder.getTraderContactName())){
						return new ResultInfo(-1,"发货方联系人为空");
					}else{
						sender.put("name", saleorder.getTraderContactName());
					}
					if((saleorder.getTraderContactTelephone()==null || "".equals(saleorder.getTraderContactTelephone())) && (saleorder.getTraderContactMobile()==null && "".equals(saleorder.getTraderContactMobile()))){
						return new ResultInfo(-1,"发货方联系方式为空");
					}
					if(saleorder.getTraderContactMobile()!=null && !"".equals(saleorder.getTraderContactMobile())){
						sender.put("mobile",saleorder.getTraderContactMobile());
					}else if(saleorder.getTraderContactTelephone()!=null && !"".equals(saleorder.getTraderContactTelephone())){
						sender.put("phone", saleorder.getTraderContactTelephone());
					} 
					if(saleorder.getTraderAddress()==null || "".equals(saleorder.getTraderAddress())){
						return new ResultInfo(-1,"发货方详细地址为空");
					}else{
						sender.put("address", saleorder.getTraderAddress());
					}
				}
			}
			//财务
			if(type == 3){
				//快递单号json
				if(opType==1){
					if(saleorder.getBussinessNo() == null){
						content.put("id", "CW"+saleorder.getSaleorderId());
					}else{
						content.put("id", saleorder.getBussinessNo()+"");
					}
					sender.put("address", "白下高新技术产业园永丰大道紫霞路斯坦德电子商务大厦北楼三层 ");
					sender.put("city", "江苏省,南京市,秦淮区");
					sender.put("company", "南京贝登医疗股份有限公司");
					sender.put("name", "财务部");
					sender.put("phone", "025-68538253");
				}
				//大头笔、集散地
				else if(opType==2){
					if(saleorder.getBussinessNo() == null){
						content.put("unionCode", "CW"+saleorder.getSaleorderId());
					}else{
						content.put("unionCode", saleorder.getBussinessNo()+"");
					}
					content.put("send_province", "江苏省");
					content.put("send_city", "南京市");
					content.put("send_district", "秦淮区");
					content.put("send_address", "白下高新技术产业园永丰大道紫霞路斯坦德电子商务大厦北楼三层");
				}
				//打印json
				else if(opType==3){
					if(saleorder.getBussinessNo() == null){
						content.put("partnerCode", "CW"+saleorder.getSaleorderId());
					}else{
						content.put("partnerCode", saleorder.getBussinessNo()+"");
					}
					sender.put("address", "白下高新技术产业园永丰大道紫霞路斯坦德电子商务大厦北楼三层 ");
					sender.put("city", "南京市");
					sender.put("company", "南京贝登医疗股份有限公司");
					sender.put("name", "财务部");
					sender.put("county", "南京");
					sender.put("prov", "江苏");
					sender.put("phone", "025-68538253");
				}
			}
			//物流收货信息
			if(type ==2 || type==1){
				String[] areaArr = null;
				if(saleorder.getTakeTraderArea()==null || "".equals(saleorder.getTakeTraderArea())){
					return new ResultInfo(-1,"收货方地区信息为空");
				}else{
					areaArr = saleorder.getTakeTraderArea().split("\\s+");
				}
				if(areaArr.length<3){
					//查询地区信息
				    if(saleorder.getOrderType()!=null && saleorder.getOrderType()== 5){
				        traderAddress.setTraderAddressId(saleorder.getTakeTraderAreaId());
                    }else{
                        traderAddress.setTraderAddressId(saleorder.getTakeTraderAddressId());
                    }
					traderAddress=traderCustomerService.getTraderAddress(traderAddress);
					if(traderAddress==null){
                        return new ResultInfo(-1,"收货方地区信息为空");
                    }
					List<Region> araes = (List<Region>) regionService.getRegion(traderAddress.getAreaId(),1);
					if(araes==null || araes.size()<4){
						return new ResultInfo(-1,"收货方地区信息不全");
					}else{
						areaArr =  new String[3]; 
						areaArr[0] = araes.get(1).getRegionName();
						areaArr[1] = araes.get(2).getRegionName();
						areaArr[2] = araes.get(3).getRegionName();
					}
				}
				//快递单号
				if(opType==1){
					receiver.put("city", areaArr[0]+","+areaArr[1]+","+areaArr[2]);
					
				}
				//大头笔、集散地
				else if(opType == 2){
					content.put("receive_province", areaArr[0]);
					content.put("receive_city", areaArr[1]);
					content.put("receive_district", areaArr[2]);
					content.put("receive_address", saleorder.getInvoiceTraderAddress());
				}
				//打印面单
				else if(opType == 3){
					receiver.put("prov", areaArr[0]);
					receiver.put("city", areaArr[1]);
					receiver.put("county", areaArr[2]);
				}
				
				if(saleorder.getTakeTraderName()==null || "".equals(saleorder.getTakeTraderName())){
					return new ResultInfo(-1,"收货方公司名称为空");
				}else{
					receiver.put("company", saleorder.getTakeTraderName());
				}
				if(saleorder.getTakeTraderContactName()==null || "".equals(saleorder.getTakeTraderContactName())){
					return new ResultInfo(-1,"收货方联系人为空");
				}else{
					receiver.put("name", saleorder.getTakeTraderContactName());
				}
				if((saleorder.getTakeTraderContactTelephone()==null || "".equals(saleorder.getTakeTraderContactTelephone())) && (saleorder.getTraderContactMobile()==null && "".equals(saleorder.getTraderContactMobile()))){
					return new ResultInfo(-1,"收货方联系信息不全");
				}
				if(saleorder.getTakeTraderContactMobile()!=null && !"".equals(saleorder.getTakeTraderContactMobile())){
					receiver.put("mobile", saleorder.getTakeTraderContactMobile());
				}else if(saleorder.getTakeTraderContactTelephone()!=null && !"".equals(saleorder.getTakeTraderContactTelephone())){
					receiver.put("phone", saleorder.getTakeTraderContactTelephone());
				} 
				if(saleorder.getTakeTraderAddress()==null || "".equals(saleorder.getTakeTraderAddress())){
					return new ResultInfo(-1,"收货方详细地址为空");
				}else{
					receiver.put("address", saleorder.getTakeTraderAddress()+""+saleorder.getTakeTraderName());
				}
			}
			//财务收票信息
			if(type ==3){
				String[] areaArr =null;
				if(saleorder.getInvoiceTraderArea()==null || "".equals(saleorder.getInvoiceTraderArea())){
					return new ResultInfo(-1,"收票方地区信息为空");
				}else{
					areaArr = saleorder.getInvoiceTraderArea().split("\\s+");
				}
				if(areaArr.length<3){
					//查询地区信息
				    if(saleorder.getOrderType()!=null && saleorder.getOrderType()== 5){
                        traderAddress.setTraderAddressId(saleorder.getInvoiceTraderAreaId());
                    }else{
                        traderAddress.setTraderAddressId(saleorder.getInvoiceTraderAddressId());
                    }
					traderAddress=traderCustomerService.getTraderAddress(traderAddress);
					if(traderAddress==null){
                        return new ResultInfo(-1,"收票方地区信息为空");
                    }
					List<Region> araes = (List<Region>) regionService.getRegion(traderAddress.getAreaId(),1);
					if(araes==null || araes.size()<4){
						return new ResultInfo(-1,"收票方地区信息不全");
					}else{
						areaArr =  new String[3]; 
						areaArr[0] = araes.get(1).getRegionName();
						areaArr[1] = araes.get(2).getRegionName();
						areaArr[2] = araes.get(3).getRegionName();
					}
				}
				//快递单号
				if(opType==1){
					receiver.put("city", areaArr[0]+","+areaArr[1]+","+areaArr[2]);
					
				}
				//大头笔、集散地
				else if(opType == 2){
					content.put("receive_province", areaArr[0]);
					content.put("receive_city", areaArr[1]);
					content.put("receive_district", areaArr[2]);
					content.put("receive_address", saleorder.getInvoiceTraderAddress());
				}
				//打印面单
				else if(opType == 3){
					receiver.put("prov", areaArr[0]);
					receiver.put("city", areaArr[1]);
					receiver.put("county", areaArr[2]);
				}
				if(opType == 1 || opType==3){
					if(saleorder.getInvoiceTraderName()==null || "".equals(saleorder.getInvoiceTraderName())){
						return new ResultInfo(-1,"收票方公司名称为空");
					}else{
						receiver.put("company", saleorder.getInvoiceTraderName());
					}
					if(saleorder.getInvoiceTraderContactName()==null || "".equals(saleorder.getInvoiceTraderContactName())){
						return new ResultInfo(-1,"收票方联系人为空");
					}else{
						receiver.put("name", saleorder.getInvoiceTraderContactName());
					}
					if((saleorder.getInvoiceTraderContactTelephone()==null || "".equals(saleorder.getInvoiceTraderContactTelephone())) && (saleorder.getInvoiceTraderContactMobile()==null || "".equals(saleorder.getInvoiceTraderContactMobile()))){
						return new ResultInfo(-1,"收票方联系信息不全");
					}
					if(saleorder.getInvoiceTraderContactMobile()!=null && !"".equals(saleorder.getInvoiceTraderContactMobile())){
						receiver.put("mobile",saleorder.getInvoiceTraderContactMobile());
					}else if(saleorder.getInvoiceTraderContactTelephone()!=null && !"".equals(saleorder.getInvoiceTraderContactTelephone())){
						receiver.put("phone", saleorder.getInvoiceTraderContactTelephone());
					} 
					if(saleorder.getInvoiceTraderAddress()==null && "".equals(saleorder.getInvoiceTraderAddress())){
						return new ResultInfo(-1,"收票方详细地址为空");
					}else{
						receiver.put("address", saleorder.getInvoiceTraderAddress()+""+saleorder.getInvoiceTraderName());
					}
				}
			}
		}
		//售后信息
		else if(btype==3 || btype==4 ){
			AfterSalesDetail av = (AfterSalesDetail)ob;
			if(StringUtils.isNotBlank(av.getBussinessNo())){
				av.setBussinessNo(expressForVedengId);
			}
			if(shType==0){
				if(type==3){//财务
					
					//获取公司发货信息
					//快递单号json
					if(opType==1){
						if(av.getBussinessNo() == null){
							content.put("id", "CW"+av.getAfterSalesId());
						}else{
							content.put("id", av.getBussinessNo()+"");
						}
						sender.put("address", "白下高新技术产业园永丰大道紫霞路斯坦德电子商务大厦北楼三层 ");
						sender.put("city", "江苏省,南京市,秦淮区");
						sender.put("company", "南京贝登医疗股份有限公司");
						sender.put("name", "财务部");
						sender.put("phone", "025-68538253");
					}
					//大头笔、集散地
					else if(opType==2){
						if(av.getBussinessNo() == null){
							content.put("unionCode", "CW"+av.getAfterSalesId());
						}else{
							content.put("unionCode", av.getBussinessNo()+"");
						}
						content.put("send_province", "江苏省");
						content.put("send_city", "南京市");
						content.put("send_district", "秦淮区");
						content.put("send_address", "白下高新技术产业园永丰大道紫霞路斯坦德电子商务大厦北楼三层");
					}
					//打印json
					else if(opType==3){
						if(av.getBussinessNo() == null){
							content.put("partnerCode", "CW"+av.getAfterSalesId());
						}else{
							content.put("partnerCode", av.getBussinessNo()+"");
						}
						sender.put("address", "白下高新技术产业园永丰大道紫霞路斯坦德电子商务大厦北楼三层 ");
						sender.put("city", "南京市");
						sender.put("company", "南京贝登医疗股份有限公司");
						sender.put("name", "财务部");
						sender.put("county", "南京");
						sender.put("prov", "江苏");
						sender.put("phone", "025-68538253");
					}
					
					String[] areaArr = null;
					if(av.getInvoiceTraderArea()==null || "".equals(av.getInvoiceTraderArea())){
						return new ResultInfo(-1,"收票方地区信息为空");
					}else{
						areaArr = av.getInvoiceTraderArea().split("\\s+");
					}
					if(areaArr.length<3){
						//查询地区信息
						traderAddress.setTraderAddressId(av.getInvoiceTraderAddressId());
						traderAddress=traderCustomerService.getTraderAddress(traderAddress);
						List<Region> araes = (List<Region>) regionService.getRegion(traderAddress.getAreaId(),1);
						if(araes==null || araes.size()<4){
							return new ResultInfo(-1,"收票方地区信息不全");
						}else{
							areaArr =  new String[3]; 
							areaArr[0] = araes.get(1).getRegionName();
							areaArr[1] = araes.get(2).getRegionName();
							areaArr[2] = araes.get(3).getRegionName();
						}
					}
					//快递单号
					if(opType==1){
						receiver.put("city", areaArr[0]+","+areaArr[1]+","+areaArr[2]);
						
					}
					//大头笔、集散地
					else if(opType == 2){
						content.put("receive_province", areaArr[0]);
						content.put("receive_city", areaArr[1]);
						content.put("receive_district", areaArr[2]);
						content.put("receive_address", av.getInvoiceTraderAddress());
					}
					//打印面单
					else if(opType == 3){
						receiver.put("prov", areaArr[0]);
						receiver.put("city", areaArr[1]);
						receiver.put("county", areaArr[2]);
					}
					if(opType ==1 || opType==3){
						if(av.getInvoiceTraderName()==null && "".equals(av.getInvoiceTraderName())){
							return new ResultInfo(-1,"收票方公司名称为空");
						}else{
							receiver.put("company", av.getInvoiceTraderName());
						}
						if(av.getInvoiceTraderContactName()==null || "".equals(av.getInvoiceTraderContactName())){
							return new ResultInfo(-1,"收票方联系人为空");
						}else{
							receiver.put("name", av.getInvoiceTraderContactName());
						}
						if((av.getInvoiceTraderContactTelephone()==null || "".equals(av.getInvoiceTraderContactTelephone())) && (av.getInvoiceTraderContactMobile()==null || "".equals(av.getInvoiceTraderContactMobile()))){
							return new ResultInfo(-1,"收票方联系信息不全");
						}
						if(av.getInvoiceTraderContactMobile()!=null && !"".equals(av.getInvoiceTraderContactMobile())){
							receiver.put("mobile", av.getInvoiceTraderContactMobile());
						}else if(av.getInvoiceTraderContactTelephone()!=null && !"".equals(av.getInvoiceTraderContactTelephone())){
							receiver.put("phone", av.getInvoiceTraderContactTelephone());
						} 
						if(av.getInvoiceTraderAddress()==null && "".equals(av.getInvoiceTraderAddress())){
							return new ResultInfo(-1,"收票方详细地址为空");
						}else{
							receiver.put("address", av.getInvoiceTraderAddress()+av.getInvoiceTraderName());
						}
					}
				}
			}else{
				//物流--销售售后
				if(shType==1){
					Saleorder saleorder = (Saleorder)obj;
					//普发
					if(type==1){
						//获取公司发货信息
						ParamsConfigVo paramsConfigVo = new ParamsConfigVo();
						paramsConfigVo.setCompanyId(companyId);
						paramsConfigVo.setParamsKey(100);
						AddressVo delivery = addressService.getDeliveryAddress(paramsConfigVo);
						String[] area = null;
						if(delivery.getAreas()==null || "".equals(delivery.getAreas())){
							return new ResultInfo(-1,"发货方地区信息为空");
						}else{
							area = delivery.getAreas().split("\\s+");
						}
						if(area.length<3){
							return new ResultInfo(-1,"发货方地区信息不全");
						}
						//快递单号
						if(opType==1){
							sender.put("city", area[0]+","+area[1]+","+area[2]);
							content.put("id", saleorder.getBussinessNo()+"");
						}
						//大头笔、集散地
						else if(opType == 2){
							content.put("unionCode", saleorder.getBussinessNo()+"");
							content.put("send_province", area[0]);
							content.put("send_city", area[1]);
							content.put("send_district", area[2]);
							content.put("send_address", delivery.getAddress());
						}
						//打印面单
						else if(opType == 3){
							content.put("partnerCode", saleorder.getBussinessNo()+"");
							sender.put("prov", area[0]);
							sender.put("city", area[1]);
							sender.put("county", area[2]);
						}
						if(opType ==1 || opType == 3){
							if(delivery.getCompanyName()==null || "".equals(delivery.getCompanyName()) ){
								return new ResultInfo(-1,"发货方公司名称为空");
							}else{
								sender.put("company", delivery.getCompanyName());
							}
							if(delivery.getContactName()==null || "".equals(delivery.getContactName())){
								return new ResultInfo(-1,"发货方联系人为空");
							}else{
								sender.put("name", delivery.getContactName());
							}
							if((delivery.getMobile()==null || "".equals(delivery.getMobile())) && (delivery.getTelephone()==null && "".equals(delivery.getTelephone()))){
								return new ResultInfo(-1,"发货方联系方式为空");
							}
							if(delivery.getMobile()!=null && !"".equals(delivery.getMobile())){
								sender.put("mobile",delivery.getMobile());
							}else if(delivery.getTelephone()!=null && !"".equals(delivery.getTelephone())){
								sender.put("phone", delivery.getTelephone());
							}
							if(delivery.getAddress()==null || "".equals(delivery.getAddress())){
								return new ResultInfo(-1,"发货方详细地址为空");
							}else{
								sender.put("address", delivery.getAddress());
							}
						}
					}
					//物流--销售售后直发
					else if(type==2){
						String[] area  = null;
						if(saleorder.getTraderArea()==null || "".equals(saleorder.getTraderArea())){
							return new ResultInfo(-1,"发货方地区信息为空");
						}else{
							area = saleorder.getTraderArea().split("\\s+");
						}
						if(area.length<3){
							//查询地区信息
						    if(saleorder.getOrderType()!=null && saleorder.getOrderType()== 5){
	                            traderAddress.setTraderAddressId(saleorder.getTraderAreaId());
	                        }else{
	                            traderAddress.setTraderAddressId(saleorder.getTraderAddressId());
	                        }
						    if(traderAddress==null){
	                            return new ResultInfo(-1,"发货方地区信息为空");
	                        }
							List<Region> araes = (List<Region>) regionService.getRegion(traderAddress.getAreaId(),1);
							if(araes==null || araes.size()<4){
								return new ResultInfo(-1,"发货方地区信息不全");
							}else{
								area =  new String[3]; 
								area[0] = araes.get(1).getRegionName();
								area[1] = araes.get(2).getRegionName();
								area[2] = araes.get(3).getRegionName();
							}
						}
						//快递单号
						if(opType==1){
							sender.put("city", area[0]+","+area[1]+","+area[2]);
							content.put("id", saleorder.getBussinessNo()+"");
						}
						//大头笔、集散地
						else if(opType == 2){
							content.put("unionCode", saleorder.getBussinessNo()+"");
							content.put("send_province", area[0]);
							content.put("send_city", area[1]);
							content.put("send_district", area[2]);
							content.put("send_address", saleorder.getTraderAddress());
						}
						//打印面单
						else if(opType == 3){
							content.put("partnerCode", saleorder.getBussinessNo()+"");
							sender.put("prov", area[0]);
							sender.put("city", area[1]);
							sender.put("county", area[2]);
						}
						if(opType==1 || opType == 3){
							if(saleorder.getTraderName()==null || "".equals(saleorder.getTraderName())){
								return new ResultInfo(-1,"发货方公司名称为空");
							}else{
								sender.put("company", saleorder.getTraderName());
							}
							if(saleorder.getTraderContactName()==null || "".equals(saleorder.getTraderContactName())){
								return new ResultInfo(-1,"发货方联系人为空");
							}else{
								sender.put("name", saleorder.getTraderContactName());
							}
							if((saleorder.getTraderContactTelephone()==null || "".equals(saleorder.getTraderContactTelephone())) && (saleorder.getTraderContactMobile()==null && "".equals(saleorder.getTraderContactMobile()))){
								return new ResultInfo(-1,"发货方联系方式为空");
							}
							if(saleorder.getTraderContactMobile()!=null && !"".equals(saleorder.getTraderContactMobile())){
								sender.put("mobile",saleorder.getTraderContactMobile());
							}else if(saleorder.getTraderContactTelephone()!=null && !"".equals(saleorder.getTraderContactTelephone())){
								sender.put("phone", saleorder.getTraderContactTelephone());
							} 
							if(saleorder.getTraderAddress()==null || "".equals(saleorder.getTraderAddress())){
								return new ResultInfo(-1,"发货方详细地址为空");
							}else{
								sender.put("address", saleorder.getTraderAddress());
							}
						}
					}
				}
				//采购售后
				else if(shType == 2){
					Buyorder buyorder = (Buyorder)obj;
					//获取公司发货信息
					ParamsConfigVo paramsConfigVo = new ParamsConfigVo();
					paramsConfigVo.setCompanyId(companyId);
					paramsConfigVo.setParamsKey(100);
					AddressVo delivery = addressService.getDeliveryAddress(paramsConfigVo);
					String[] area = null;
					if(delivery.getAreas()==null || "".equals(delivery.getAreas())){
						return new ResultInfo(-1,"发货方地区信息为空");
					}else{
						area = delivery.getAreas().split("\\s+");
					}
					if(area.length<3){
						return new ResultInfo(-1,"发货方地区信息不全");
					}
					//快递单号
					if(opType==1){
						sender.put("city", area[0]+","+area[1]+","+area[2]);
						content.put("id",buyorder.getBussinessNo());
					}
					//大头笔、集散地
					else if(opType == 2){
						content.put("unionCode", buyorder.getBussinessNo());
						content.put("send_province", area[0]);
						content.put("send_city", area[1]);
						content.put("send_district", area[2]);
						content.put("send_address", delivery.getAddress());
					}
					//打印面单
					else if(opType == 3){
						content.put("partnerCode", buyorder.getBussinessNo());
						sender.put("prov", area[0]);
						sender.put("city", area[1]);
						sender.put("county", area[2]);
					}
					if(opType ==1 || opType == 3){
						if(delivery.getCompanyName()==null || "".equals(delivery.getCompanyName()) ){
							return new ResultInfo(-1,"发货方公司名称为空");
						}else{
							sender.put("company", delivery.getCompanyName());
						}
						if(delivery.getContactName()==null || "".equals(delivery.getContactName())){
							return new ResultInfo(-1,"发货方联系人为空");
						}else{
							sender.put("name", delivery.getContactName());
						}
						if((delivery.getMobile()==null || "".equals(delivery.getMobile())) && (delivery.getTelephone()==null && "".equals(delivery.getTelephone()))){
							return new ResultInfo(-1,"发货方联系方式为空");
						}
						if(delivery.getMobile()!=null && !"".equals(delivery.getMobile())){
							sender.put("mobile",delivery.getMobile());
						}else if(delivery.getTelephone()!=null && !"".equals(delivery.getTelephone())){
							sender.put("phone", delivery.getTelephone());
						}
						if(delivery.getAddress()==null || "".equals(delivery.getAddress())){
							return new ResultInfo(-1,"发货方详细地址为空");
						}else{
							sender.put("address", delivery.getAddress());
						}
					}
				}
				String[] areaArr = null;
				if(av.getArea()==null || "".equals(av.getArea())){
					return new ResultInfo(-1,"收货方地区信息为空");
				}else{
					areaArr = av.getArea().split("\\s+");
				}
				if(areaArr.length<3){
					//查询地区信息
					traderAddress.setTraderAddressId(av.getAddressId());
					traderAddress=traderCustomerService.getTraderAddress(traderAddress);
					List<Region> araes = (List<Region>) regionService.getRegion(traderAddress.getAreaId(),1);
					if(araes==null || araes.size()<4){
						return new ResultInfo(-1,"收货方地区信息不全");
					}else{
						areaArr =  new String[3]; 
						areaArr[0] = araes.get(1).getRegionName();
						areaArr[1] = araes.get(2).getRegionName();
						areaArr[2] = araes.get(3).getRegionName();
					}
				}
				//快递单号
				if(opType==1){
					receiver.put("city", areaArr[0]+","+areaArr[1]+","+areaArr[2]);
					
				}
				//大头笔、集散地
				else if(opType == 2){
					content.put("receive_province", areaArr[0]);
					content.put("receive_city", areaArr[1]);
					content.put("receive_district", areaArr[2]);
					content.put("receive_address", av.getInvoiceTraderAddress());
				}
				//打印面单
				else if(opType == 3){
					receiver.put("prov", areaArr[0]);
					receiver.put("city", areaArr[1]);
					receiver.put("county", areaArr[2]);
				}
				if(av.getTraderName()==null || "".equals(av.getTraderName())){
					return new ResultInfo(-1,"收货方公司名称为空");
				}else{
					receiver.put("company", av.getTraderName());
				}
				if(av.getTraderContactName()==null || "".equals(av.getTraderContactName())){
					return new ResultInfo(-1,"收货方联系人为空");
				}else{
					receiver.put("name", av.getTraderContactName());
				}
				if((av.getTraderContactTelephone()==null || "".equals(av.getTraderContactTelephone())) && (av.getTraderContactMobile()==null || "".equals(av.getTraderContactMobile()))){
					return new ResultInfo(-1,"收货方联系信息不全");
				}
				if(av.getTraderContactMobile()!=null && !"".equals(av.getTraderContactMobile())){
					receiver.put("mobile", av.getTraderContactMobile());
				}else if(av.getTraderContactTelephone()!=null && !"".equals(av.getTraderContactTelephone())){
					receiver.put("phone", av.getTraderContactTelephone());
				} 
				if(av.getAddress()==null || "".equals(av.getAddress())){
					return new ResultInfo(-1,"收货方详细地址为空");
				}else{
					receiver.put("address", av.getAddress()+ av.getTraderName());
				}
			}
		}
		//文件寄送信息
		else if(btype==-1){
			FileDelivery fileDelivery = (FileDelivery)obj;
			if(StringUtils.isNotBlank(fileDelivery.getBussinessNo())){
				fileDelivery.setBussinessNo(expressForVedengId);
			}
			//获取公司发货信息
			ParamsConfigVo paramsConfigVo = new ParamsConfigVo();
			paramsConfigVo.setCompanyId(companyId);
			paramsConfigVo.setParamsKey(100);
			AddressVo delivery = addressService.getDeliveryAddress(paramsConfigVo);
			String[] area = null;
			if(delivery.getAreas()==null || "".equals(delivery.getAreas())){
				return new ResultInfo(-1,"发货方地区信息为空");
			}else{
				area = delivery.getAreas().split("\\s+");
			}
			if(area.length<3){
				return new ResultInfo(-1,"发货方地区信息不全");
			}
			//快递单号
			if(opType==1){
				sender.put("city", area[0]+","+area[1]+","+area[2]);
				content.put("id", fileDelivery.getBussinessNo()+"");
			}
			//大头笔、集散地
			else if(opType == 2){
				content.put("unionCode", fileDelivery.getBussinessNo()+"");
				content.put("send_province", area[0]);
				content.put("send_city", area[1]);
				content.put("send_district", area[2]);
				content.put("send_address", delivery.getAddress());
			}
			//打印面单
			else if(opType == 3){
				content.put("partnerCode", fileDelivery.getBussinessNo()+"");
				sender.put("prov", area[0]);
				sender.put("city", area[1]);
				sender.put("county", area[2]);
			}
			if(opType ==1 || opType == 3){
				if(delivery.getCompanyName()==null || "".equals(delivery.getCompanyName()) ){
					return new ResultInfo(-1,"发货方公司名称为空");
				}else{
					sender.put("company", delivery.getCompanyName());
				}
				if(delivery.getContactName()==null || "".equals(delivery.getContactName())){
					return new ResultInfo(-1,"发货方联系人为空");
				}else{
					sender.put("name", delivery.getContactName());
				}
				if((delivery.getMobile()==null || "".equals(delivery.getMobile())) && (delivery.getTelephone()==null && "".equals(delivery.getTelephone()))){
					return new ResultInfo(-1,"发货方联系方式为空");
				}
				if(delivery.getMobile()!=null && !"".equals(delivery.getMobile())){
					sender.put("mobile",delivery.getMobile());
				}else if(delivery.getTelephone()!=null && !"".equals(delivery.getTelephone())){
					sender.put("phone", delivery.getTelephone());
				}
				if(delivery.getAddress()==null || "".equals(delivery.getAddress())){
					return new ResultInfo(-1,"发货方详细地址为空");
				}else{
					sender.put("address", delivery.getAddress());
				}
			}
			String[] areaArr = null;
			if(fileDelivery.getArea()==null || "".equals(fileDelivery.getArea())){
				return new ResultInfo(-1,"收货方地区信息为空");
			}else{
				 areaArr = fileDelivery.getArea().split("\\s+");
			}
			TraderContact  traderContact = new TraderContact();
			if(areaArr.length<3){
				//查询地区信息
				/**********************查询客户的地区start*****************************/
				//联系人默认信息
			    traderContact = traderCustomerService.getTraderContactInfo(fileDelivery);
			    List<Region> araes = null;
			    if(traderContact.getAreaId()!=null){
			    	araes = (List<Region>) regionService.getRegion(traderContact.getAreaId(),1);
			    }
				/**********************查询客户的地区end*****************************/
				if(araes==null || araes.size()<4){
					return new ResultInfo(-1,"收货方地区信息不全");
				}else{
					areaArr =  new String[3]; 
					areaArr[0] = araes.get(1).getRegionName();
					areaArr[1] = araes.get(2).getRegionName();
					areaArr[1] = araes.get(3).getRegionName();
				}
			}
			//快递单号
			if(opType==1){
				receiver.put("city", areaArr[0]+","+areaArr[1]+","+areaArr[2]);
				
			}
			//大头笔、集散地
			else if(opType == 2){
				content.put("receive_province", areaArr[0]);
				content.put("receive_city", areaArr[1]);
				content.put("receive_district", areaArr[2]);
				content.put("receive_address", fileDelivery.getAddress());
			}
			//打印面单
			else if(opType == 3){
				receiver.put("prov", areaArr[0]);
				receiver.put("city", areaArr[1]);
				receiver.put("county", areaArr[2]);
			}
			if(opType==1 || opType == 3){
				if(fileDelivery.getTraderName()==null || "".equals(fileDelivery.getTraderName())){
					return new ResultInfo(-1,"收货方公司名称为空");
				}else{
					receiver.put("company", fileDelivery.getTraderName());
				}
				if(fileDelivery.getTraderContactName()==null && "".equals(fileDelivery.getTraderContactName())){
					if(StringUtils.isNotBlank(traderContact.getName())){
						receiver.put("name", traderContact.getName());
					}else{
						return new ResultInfo(-1,"收货方联系人称为空");
					}
				}else{
					receiver.put("name", fileDelivery.getTraderContactName());
				}
				if((fileDelivery.getTelephone()==null || "".equals(fileDelivery.getTelephone())) && (fileDelivery.getMobile()==null || "".equals(fileDelivery.getMobile()))){
					if(StringUtil.isBlank(traderContact.getTelephone()) && StringUtil.isBlank(traderContact.getTelephone())){
						return new ResultInfo(-1,"收件方联系信息不全");
					}
				}
				if(fileDelivery.getMobile()!=null && !"".equals(fileDelivery.getMobile())){
					receiver.put("mobile", fileDelivery.getMobile());
				}else if(fileDelivery.getTelephone()!=null && !"".equals(fileDelivery.getTelephone())){
					receiver.put("phone", fileDelivery.getTelephone());
				} else{
					//手机号、电话都没有，取默认联系人信息
					if(StringUtils.isNotBlank(traderContact.getMobile())){
						receiver.put("mobile", traderContact.getMobile());
					}else if(StringUtils.isNotBlank(traderContact.getTelephone())){
						receiver.put("mobile", traderContact.getTelephone());
					}
				}
				if(fileDelivery.getAddress()==null || "".equals(fileDelivery.getAddress())){
					if(StringUtils.isBlank(traderContact.getAddress())){
						return new ResultInfo(-1,"收件方详细地址为空");
					}else{
						receiver.put("address", traderContact.getAddress()+fileDelivery.getTraderName());
					}
				}else{
					receiver.put("address", fileDelivery.getAddress()+fileDelivery.getTraderName());
				}
			}
		}
		//获取快递单
		if(opType ==1){
			content.put("typeId", "1");
			content.put("remark", "请勿摔货");
			content.put("receiver", receiver);
			content.put("sender", sender);
			
			jsonObj.put("datetime", DateUtil.getNowDate("yyyy-MM-dd HH:mm:ss"));
			for (SysOptionDefinition sysOptionDefinition : zotApiList) {
				if(sysOptionDefinition.getSysOptionDefinitionId()==710){
					jsonObj.put("partner", sysOptionDefinition.getComments());
				}
				if(sysOptionDefinition.getSysOptionDefinitionId()==711){
					jsonObj.put("verify", sysOptionDefinition.getComments());
				}
			}
			jsonObj.put("content", content);
			jsonStr = jsonObj.toString();
		}
		//获取大头笔、集散地
		else if(opType ==2){
			jsonStr = content.toString();
		}
		//打印面单
		else if(opType ==3){
			Map <String, String> printParam = new HashMap <String, String>();
			printParam.put("paramType","ELEC_MARK" );
			printParam.put("mailNo",ep.getLogisticsNo() );
			printParam.put("printMark",map.get("mark") );
			printParam.put("printBagaddr",map.get("bagAddr") );
			if(type == 3){
				for (SysOptionDefinition sysOptionDefinition : zotApiList) {
					if(sysOptionDefinition.getSysOptionDefinitionId()==702){
						content.put("deviceId", sysOptionDefinition.getComments());
					}
					if(sysOptionDefinition.getSysOptionDefinitionId()==703){
						content.put("printerId", sysOptionDefinition.getComments());
					}
				}
			}else{
				if(pid==1){
					for (SysOptionDefinition sysOptionDefinition : zotApiList) {
						if(sysOptionDefinition.getSysOptionDefinitionId()==704){
							content.put("deviceId", sysOptionDefinition.getComments());
						}
						if(sysOptionDefinition.getSysOptionDefinitionId()==705){
							content.put("printerId", sysOptionDefinition.getComments());
						}
					}
				}else if(pid==2){
					for (SysOptionDefinition sysOptionDefinition : zotApiList) {
						if(sysOptionDefinition.getSysOptionDefinitionId()==706){
							content.put("deviceId", sysOptionDefinition.getComments());
						}
						if(sysOptionDefinition.getSysOptionDefinitionId()==707){
							content.put("printerId", sysOptionDefinition.getComments());
						}
					}
				}
			}
			content.put("printChannel", "c5e016fd2e8241cfbdcf5175fbccbcab");
			content.put("printType", "REMOTE_EPRINT");
			content.put("repetition", true);
			content.put("receiver", receiver);
			content.put("sender", sender);
			content.put("printParam", printParam);
			jsonStr = content.toString();
		}
		return new ResultInfo(0, "成功", jsonStr);
	}

 	/**
	 * 
	 * <b>Description:</b><br> 解析出快递单号
	 * @param expressJson
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2018年6月14日 上午9:18:38
	 */
	public static ResultInfo<?> parserJsonExpressNo(String expressJson) {
		String billCode = "";
		JSONObject jsonObject = new JSONObject(expressJson);
		if(jsonObject.getBoolean("result")){
			JSONObject object = jsonObject.getJSONObject("data");
			billCode = object.getString("billCode");
			if(StringUtils.isBlank(billCode)){
				logger.error("无法解析出快递单号");
				return null;
			}
			return new ResultInfo(0, "成功", billCode);
		}else{
			return null;
		}
		
	}
	/**
	 * 
	 * <b>Description:</b><br> 获取大头笔信息
	 * @param bigInfoJson
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2018年6月14日 上午10:32:01
	 */
	public static ResultInfo<?> parserJsonMark(String bigInfoJson) {
		Map<String,String> map = new HashMap();
		String mark = "";
		String bagAddr="";
		JSONObject jsonObject = new JSONObject(bigInfoJson);
		if(jsonObject.getBoolean("status")){
			JSONObject object = jsonObject.getJSONObject("result");  
			if(object!=null){
				mark = object.getString("mark");  
				bagAddr = object.getString("bagAddr");
				map.put("mark", mark);
				map.put("bagAddr", bagAddr);
			}
			return new ResultInfo(0, "成功", map);
		}else{
			return null;
		}
	}
	/**
	 * 
	 * <b>Description:</b><br> 打印后返回的数据
	 * @param expressJson
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2018年7月3日 下午1:39:38
	 */
	public static ResultInfo<?> parserJsonPrint(String expressJson) {
		JSONObject jsonObject = new JSONObject(expressJson);
		if(jsonObject.getBoolean("status")){
			return new ResultInfo(0, "成功");
		}else{
			return null;
		}
	}
	
}

