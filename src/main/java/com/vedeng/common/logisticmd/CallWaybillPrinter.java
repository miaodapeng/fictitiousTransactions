package com.vedeng.common.logisticmd;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sf.dto.CargoInfoDto;
import com.sf.dto.WaybillDto;
import com.sf.util.Base64ImageTools;
import com.sf.util.MyJsonUtil;
import com.vedeng.aftersales.model.AfterSalesDetail;
import com.vedeng.authorization.model.Region;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.logistics.model.Express;
import com.vedeng.logistics.model.FileDelivery;
import com.vedeng.order.model.Buyorder;
import com.vedeng.order.model.Saleorder;
import com.vedeng.system.model.RegionCode;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.model.vo.AddressVo;
import com.vedeng.system.model.vo.ParamsConfigVo;
import com.vedeng.system.service.AddressService;
import com.vedeng.system.service.RegionService;
import com.vedeng.trader.model.TraderAddress;
import com.vedeng.trader.service.TraderCustomerService;



public class CallWaybillPrinter {
	
	WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
    private AddressService addressService = (AddressService) context.getBean("addressService");
    private TraderCustomerService traderCustomerService = (TraderCustomerService) context.getBean("traderCustomerService");
    private RegionService regionService = (RegionService) context.getBean("regionService");
    //private static String CARDNUMBER="0253523464"; 
	
	public  ResultInfo<?> WayBillPrinterTools(Object obj,Express express, Integer type, Integer companyId, Integer btype, String ip, List<SysOptionDefinition> apiList, AfterSalesDetail av, Integer shType) throws Exception{
        
		String gk = "";
		String yj = "";
		String jy = "";
		if (apiList != null && apiList.size() > 0) {
			for (SysOptionDefinition s : apiList) {
				if (s.getSysOptionDefinitionId() == 690) {
					gk = s.getComments();
				}
				if (s.getSysOptionDefinitionId() == 691) {
					jy = s.getComments();
				}
				if (s.getSysOptionDefinitionId() == 692) {
					yj = s.getComments();
				}
			}
		}
		/*********2联单**************/
		/**
		 * 调用打印机 不弹出窗口 适用于批量打印【二联单】
		 */
		String url1 = "http://localhost:4040/sf/waybill/print?type=V2.0_poster_100mm150mm&output=noAlertPrint";
		/**
		 * 调用打印机 弹出窗口 可选择份数 适用于单张打印【二联单】
		 */
		String url2 = "http://localhost:4040/sf/waybill/print?type=V2.0_poster_100mm150mm&output=print";

		/**
		 * 直接输出图片的BASE64编码字符串 可以使用html标签直接转换成图片【二联单】
		 */
		String url3 = "http://localhost:4040/sf/waybill/print?type=V2.0_poster_100mm150mm&output=image";
        
		/*********3联单**************/
		/**
		 * 调用打印机 不弹出窗口 适用于批量打印【三联单】
		 */
		String url4 = "http://localhost:4040/sf/waybill/print?type=V3.0_poster_100mm210mm&output=noAlertPrint";
		/**
		 * 调用打印机 弹出窗口 可选择份数 适用于单张打印【三联单】
		 */
		String url5 = "http://"+ip+":4040/sf/waybill/print?type=V3.0_poster_100mm210mm&output=print";

		/**
		 * 直接输出图片的BASE64编码字符串 可以使用html标签直接转换成图片【三联单】
		 */
		String url6 = "http://localhost:4040/sf/waybill/print?type=V3.0_poster_100mm210mm&output=image";
		
		/*********2联150 丰密运单**************/
		/**
		 * 调用打印机 不弹出窗口 适用于批量打印【二联单】
		 */
		String url7 = "http://localhost:4040/sf/waybill/print?type=V2.0.FM_poster_100mm150mm&output=noAlertPrint";
		/**
		 * 调用打印机 弹出窗口 可选择份数 适用于单张打印【二联单】
		 */
		String url8 = "http://localhost:4040/sf/waybill/print?type=V2.0.FM_poster_100mm150mm&output=print";

		/**
		 * 直接输出图片的BASE64编码字符串 可以使用html标签直接转换成图片【二联单】
		 */
		String url9 = "http://localhost:4040/sf/waybill/print?type=V2.0.FM_poster_100mm150mm&output=image";
		
		/*********3联210 丰密运单**************/
		/**
		 * 调用打印机 不弹出窗口 适用于批量打印【三联单】
		 */
		String url10 = "http://localhost:4040/sf/waybill/print?type=V3.0.FM_poster_100mm210mm&output=noAlertPrint";
		/**
		 * 调用打印机 弹出窗口 可选择份数 适用于单张打印【三联单】
		 */
		String url11 = "http://localhost:4040/sf/waybill/print?type=V3.0.FM_poster_100mm210mm&output=print";

		/**
		 * 直接输出图片的BASE64编码字符串 可以使用html标签直接转换成图片【三联单】
		 */
		String url12 = "http://localhost:4040/sf/waybill/print?type=V3.0.FM_poster_100mm210mm&output=image";
		
		//根据业务需求确定请求地址
		String reqURL=url5;
		
		//电子面单顶部是否需要logo 
		boolean topLogo=false;//true 需要logo  false 不需要logo
		if(reqURL.contains("V2.0")&&topLogo){
			reqURL=reqURL.replace("V2.0", "V2.1");
		}
		
		if(reqURL.contains("V3.0")&&topLogo){
			reqURL=reqURL.replace("V3.0", "V3.1");
		}
		
        /**注意 需要使用对应业务场景的url  **/
		URL myURL = new URL(reqURL);
	     
		 //其中127.0.0.1:4040为打印服务部署的地址（端口如未指定，默认为4040），
		 //type为模板类型（支持两联、三联，尺寸为100mm*150mm和100mm*210mm，type为poster_100mm150mm和poster_100mm210mm）
		 //A5 poster_100mm150mm   A5 poster_100mm210mm
		 //output为输出类型,值为print或image，如不传，
		 //默认为print（print 表示直接打印，image表示获取图片的BASE64编码字符串）
		 //V2.0/V3.0模板顶部是带logo的  V2.1/V3.1顶部不带logo
		
		HttpURLConnection httpConn = (HttpURLConnection) myURL.openConnection();
		httpConn.setDoOutput(true);
		httpConn.setDoInput(true);
		httpConn.setUseCaches(false);
		httpConn.setRequestMethod("POST");
		httpConn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
		httpConn.setConnectTimeout(5000);
		httpConn.setReadTimeout(2 * 5000);
		
		List<WaybillDto> waybillDtoList = new ArrayList<>();
		WaybillDto dto = new WaybillDto();
		TraderAddress traderAddress = new TraderAddress();
		String areas = "";
		
	    //这个必填 
		dto.setAppId(gk);//对应clientCode
        dto.setAppKey(jy);//对应checkWord
        if("".equals(express.getLogisticsNo())){
        	return new ResultInfo(-1,"快递单号为空");
        }
        dto.setMailNo(express.getLogisticsNo());
        //dto.setMailNo("755123456788,001000000002");//子母单方式
        dto.setInsureValue(express.getProtectPrice()+"");
        //回签单
        if(express.getIsReceipt()==1){
        	dto.setReturnTrackingNo(express.getLogisticsNo());
        }
        RegionCode r = new RegionCode();
        //销售单信息
        if(btype==1){
        	Saleorder saleorder = (Saleorder)obj;
        	///收件人信息 
            //财务
            if(type==3){
            	String[] invoiceAreaArr = saleorder.getInvoiceTraderArea().split("\\s+");
            	if(invoiceAreaArr.length<2){
            		//查询地区信息
					traderAddress.setTraderAddressId(saleorder.getInvoiceTraderAddressId());
					traderAddress=traderCustomerService.getTraderAddress(traderAddress);
					List<Region> araes = (List<Region>) regionService.getRegion(traderAddress.getAreaId(),1);
					if(araes==null || araes.size()<3){
						return new ResultInfo(-1,"收票方地区信息不全");
					}else{
						invoiceAreaArr = new String[2];
						invoiceAreaArr[0] = araes.get(1).getRegionName();
						invoiceAreaArr[1] = araes.get(2).getRegionName();
					}
    			}
            	dto.setConsignerProvince(invoiceAreaArr[0]+"省");
            	if(invoiceAreaArr.length>1){
            		dto.setConsignerCity(invoiceAreaArr[1]+"市");
    			}else{
    				dto.setConsignerCity("");
    			}
        		dto.setConsignerCounty("");
        		dto.setConsignerAddress(saleorder.getInvoiceTraderAddress()); //详细地址建议最多30个字  字段过长影响打印效果
        		dto.setConsignerCompany(saleorder.getInvoiceTraderName());
        		dto.setConsignerMobile(saleorder.getInvoiceTraderContactMobile());
         		dto.setConsignerShipperCode("");
        		dto.setConsignerTel(saleorder.getInvoiceTraderContactTelephone());
        		dto.setConsignerName(saleorder.getInvoiceTraderContactName());
        		//获取地区编号
        		r.setProvinceName(invoiceAreaArr[0]);
        		r.setCityName(invoiceAreaArr[1]);
        		RegionCode regionCode = addressService.getRegionCode(r);
        		if(regionCode==null){
        			return new ResultInfo(-1,"地区编号为空！");
        		}
        		dto.setDestCode(regionCode.getCode());//目的地代码 参考顺丰地区编号
            } 
            //物流
            else if(type==1 || type == 2){
            	String[] areaArr = saleorder.getTakeTraderArea().split("\\s+");
            	if(areaArr.length<2){
            		//查询地区信息
					traderAddress.setTraderAddressId(saleorder.getTakeTraderAddressId());
					traderAddress=traderCustomerService.getTraderAddress(traderAddress);
					List<Region> araes = (List<Region>) regionService.getRegion(traderAddress.getAreaId(),1);
					if(araes==null || araes.size()<3){
						return new ResultInfo(-1,"发货方地区信息不全");
					}else{
						areaArr =  new String[2]; 
						areaArr[0] = araes.get(1).getRegionName();
						areaArr[1] = araes.get(2).getRegionName();
					}
    			}
            	dto.setConsignerProvince(areaArr[0]);
    			if(areaArr.length>1){
    				dto.setConsignerCity(areaArr[1]);
    			}else{
    				dto.setConsignerCity("");
    			}
    			dto.setConsignerCounty("");
    			dto.setConsignerAddress(saleorder.getTakeTraderAddress());
    			dto.setConsignerCompany(saleorder.getTakeTraderName());
    			dto.setConsignerMobile(saleorder.getTakeTraderContactTelephone());
    			dto.setConsignerName(saleorder.getTakeTraderContactName());
    			dto.setConsignerShipperCode("");
    			r.setProvinceName(areaArr[0]);
        		r.setCityName(areaArr[1]);
        		RegionCode regionCode = addressService.getRegionCode(r);
        		if(regionCode==null){
        			return new ResultInfo(-1,"地区编号为空！");
        		}
        		dto.setDestCode(regionCode.getCode());//目的地代码 参考顺丰地区编号
            }
    		
    		//寄件人信息
    		//财务
    		if(type==3){
    			dto.setDeliverProvince("江苏省");
    			dto.setDeliverCity("南京市");
    			dto.setDeliverCounty("秦淮区");
    			dto.setDeliverCompany("南京贝登医疗股份有限公司 ");
    			dto.setDeliverAddress("白下高新技术产业园永丰大道紫霞路斯坦德电子商务大厦北楼三层 ");//详细地址建议最多30个字  字段过长影响打印效果
    			dto.setDeliverName("财务部");
    			dto.setDeliverShipperCode("210001");
    			dto.setDeliverTel("025-68538253");
    			r.setProvinceName("江苏");
        		r.setCityName("南京");
        		RegionCode regionCode = addressService.getRegionCode(r);
        		if(regionCode==null){
        			return new ResultInfo(-1,"地区编号为空！");
        		}
        		dto.setZipCode(regionCode.getCode());//原寄地代码 参考顺丰地区编号
    		}
    		 //物流普发
            else if(type==1){
            	//获取公司发货信息
    			ParamsConfigVo paramsConfigVo = new ParamsConfigVo();
    			paramsConfigVo.setCompanyId(companyId);
    			paramsConfigVo.setParamsKey(100);
    			AddressVo delivery = addressService.getDeliveryAddress(paramsConfigVo);
    			String[] area = delivery.getAreas().split("\\s+");
    			if(area.length<2){
    				return new ResultInfo(-1,"发货方地区信息不全");
    			}
    			dto.setDeliverProvince(area[0]);
    			dto.setDeliverCity(area[1]);
    			dto.setDeliverCompany(delivery.getCompanyName());
    			dto.setDeliverAddress(delivery.getAddress());
    			dto.setDeliverName("物流部");
    			dto.setDeliverShipperCode("210001");
    			dto.setDeliverTel("025-68538253");
    			r.setProvinceName(area[0]);
        		r.setCityName(area[1]);
        		RegionCode regionCode = addressService.getRegionCode(r);
        		if(regionCode==null){
        			return new ResultInfo(-1,"地区编号为空！");
        		}
        		dto.setZipCode(regionCode.getCode());//原寄地代码 参考顺丰地区编号
            }
            //物流直发
            else if(type==2){
            	String[] area = saleorder.getTraderArea().split("\\s+");
            	if(area.length<2){
            		//查询地区信息
					traderAddress.setTraderAddressId(saleorder.getTraderAddressId());
					traderAddress=traderCustomerService.getTraderAddress(traderAddress);
					List<Region> araes = (List<Region>) regionService.getRegion(traderAddress.getAreaId(),1);
					if(araes==null || araes.size()<3){
						return new ResultInfo(-1,"发货方地区信息不全");
					}else{
						area =  new String[2]; 
						area[0] = araes.get(1).getRegionName();
						area[1] = araes.get(2).getRegionName();
					}
    			}
            	dto.setDeliverProvince(area[0]);
    			if(area.length>1){
    				dto.setDeliverCity(area[1]);
    			}else{
    				dto.setDeliverCity("");
    			}
    			dto.setDeliverCompany(saleorder.getTraderName());
    			dto.setDeliverAddress(saleorder.getTraderAddress());
    			dto.setDeliverName(saleorder.getTraderContactName());
    			dto.setDeliverTel(saleorder.getTraderContactTelephone());
    			r.setProvinceName(area[0]);
        		r.setCityName(area[1]);
        		RegionCode regionCode = addressService.getRegionCode(r);
        		if(regionCode==null){
        			return new ResultInfo(-1,"地区编号为空！");
        		}
        		dto.setZipCode(regionCode.getCode());//原寄地代码 参考顺丰地区编号
            }
        }
      
		//文件寄送信息
        else if(btype ==-1){
        	FileDelivery fileDelivery = (FileDelivery)obj;
        	//获取公司发货信息
			ParamsConfigVo paramsConfigVo = new ParamsConfigVo();
			paramsConfigVo.setCompanyId(companyId);
			paramsConfigVo.setParamsKey(100);
			AddressVo delivery = addressService.getDeliveryAddress(paramsConfigVo);
			String[] area = delivery.getAreas().split("\\s+");
			if(area.length<2){
				return new ResultInfo(-1,"发货方地区信息不全");
			}
			dto.setDeliverProvince(area[0]);
			dto.setDeliverCity(area[1]);
			dto.setDeliverCompany(delivery.getCompanyName());
			dto.setDeliverAddress(delivery.getAddress());
			dto.setDeliverName("物流部");
			dto.setDeliverShipperCode("210001");
			dto.setDeliverTel("025-68538253");
			r.setProvinceName(area[0]);
    		r.setCityName(area[1]);
    		RegionCode regionCode = addressService.getRegionCode(r);
    		if(regionCode==null){
    			return new ResultInfo(-1,"地区编号为空！");
    		}
    		dto.setZipCode(regionCode.getCode());//原寄地代码 参考顺丰地区编号
    		
    		String[] areaArr = fileDelivery.getArea().split("\\s+");
        	if(areaArr.length<2){
        		//查询地区信息
				traderAddress.setTraderAddressId(fileDelivery.getTraderAddressId());
				traderAddress=traderCustomerService.getTraderAddress(traderAddress);
				List<Region> araes = (List<Region>) regionService.getRegion(traderAddress.getAreaId(),1);
				if(araes==null || araes.size()<3){
					return new ResultInfo(-1,"发货方地区信息不全");
				}else{
					areaArr =  new String[2]; 
					areaArr[0] = araes.get(1).getRegionName();
					areaArr[1] = araes.get(2).getRegionName();
				}
			}
        	dto.setConsignerProvince(areaArr[0]);
			if(areaArr.length>1){
				dto.setConsignerCity(areaArr[1]);
			}else{
				dto.setConsignerCity("");
			}
			dto.setConsignerCounty("");
			dto.setConsignerAddress(fileDelivery.getAddress());
			dto.setConsignerCompany(fileDelivery.getTraderName());
			dto.setConsignerMobile(fileDelivery.getMobile());
			dto.setConsignerName(fileDelivery.getTraderContactName());
			dto.setConsignerShipperCode("");
			r.setProvinceName(areaArr[0]);
    		r.setCityName(areaArr[1]);
    		regionCode = addressService.getRegionCode(r);
    		if(regionCode==null){
    			return new ResultInfo(-1,"地区编号为空！");
    		}
    		dto.setDestCode(regionCode.getCode());//目的地代码 参考顺丰地区编号
        }
        //财务信息
        else if(btype==3 || btype==4){
        	RegionCode regionCode = new RegionCode();
        	if(shType==0){
        		if(type == 3){//财务售后
        			dto.setDeliverProvince("江苏省");
        			dto.setDeliverCity("南京市");
        			dto.setDeliverCompany("南京贝登医疗股份有限公司");
        			dto.setDeliverAddress("白下高新技术产业园永丰大道紫霞路斯坦德电子商务大厦北楼三层");
        			dto.setDeliverName("财务部");
        			dto.setDeliverShipperCode("210001");
        			dto.setDeliverTel("025-68538253");
        			r.setProvinceName("江苏省");
            		r.setCityName("南京市");
            		regionCode = addressService.getRegionCode(r);
            		if(regionCode==null){
            			return new ResultInfo(-1,"地区编号为空！");
            		}
            		dto.setZipCode(regionCode.getCode());//原寄地代码 参考顺丰地区编号
            		
        			String[] areaArr = av.getInvoiceTraderArea().split("\\s+");
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
                	dto.setConsignerProvince(areaArr[0]);
        			if(areaArr.length>1){
        				dto.setConsignerCity(areaArr[1]);
        			}else{
        				dto.setConsignerCity("");
        			}
        			dto.setConsignerCounty("");
        			dto.setConsignerAddress(av.getInvoiceTraderAddress());
        			dto.setConsignerCompany(av.getInvoiceTraderName());
        			dto.setConsignerMobile(av.getInvoiceTraderContactMobile());
        			dto.setConsignerName(av.getInvoiceTraderContactName());
        			dto.setConsignerShipperCode("");
        			r.setProvinceName(areaArr[0]);
            		r.setCityName(areaArr[1]);
            		regionCode = addressService.getRegionCode(r);
            		if(regionCode==null){
            			return new ResultInfo(-1,"地区编号为空！");
            		}
            		dto.setDestCode(regionCode.getCode());//目的地代码 参考顺丰地区编号
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
		    			String[] area = delivery.getAreas().split("\\s+");
		    			if(area.length<2){
		    				return new ResultInfo(-1,"发货方地区信息不全");
		    			}
		    			dto.setDeliverProvince(area[0]);
		    			dto.setDeliverCity(area[1]);
		    			dto.setDeliverCompany(delivery.getCompanyName());
		    			dto.setDeliverAddress(delivery.getAddress());
		    			dto.setDeliverName("物流部");
		    			dto.setDeliverShipperCode("210001");
		    			dto.setDeliverTel("025-68538253");
		    			r.setProvinceName(area[0]);
		        		r.setCityName(area[1]);
		        		RegionCode rCode = addressService.getRegionCode(r);
		        		if(rCode==null){
		        			return new ResultInfo(-1,"地区编号为空！");
		        		}
		        		dto.setZipCode(rCode.getCode());//原寄地代码 参考顺丰地区编号
					}
					//直发
					else if(type==2){
						String[] area = saleorder.getTraderArea().split("\\s+");
		            	if(area.length<2){
		            		//查询地区信息
	    					traderAddress.setTraderAddressId(saleorder.getTraderAddressId());
	    					traderAddress=traderCustomerService.getTraderAddress(traderAddress);
	    					List<Region> araes = (List<Region>) regionService.getRegion(traderAddress.getAreaId(),1);
	    					if(araes==null || araes.size()<3){
	    						return new ResultInfo(-1,"发货方地区信息不全");
	    					}else{
	    						area =  new String[2]; 
	    						area[0] = araes.get(1).getRegionName();
	    						area[1] = araes.get(2).getRegionName();
	    					}
		    			}
		            	dto.setDeliverProvince(area[0]);
		    			if(area.length>1){
		    				dto.setDeliverCity(area[1]);
		    			}else{
		    				dto.setDeliverCity("");
		    			}
		    			dto.setDeliverCompany(saleorder.getTraderName());
		    			dto.setDeliverAddress(saleorder.getTraderAddress());
		    			dto.setDeliverName(saleorder.getTraderContactName());
		    			dto.setDeliverTel(saleorder.getTraderContactTelephone());
		    			r.setProvinceName(area[0]);
		        		r.setCityName(area[1]);
		        		RegionCode reCode = addressService.getRegionCode(r);
		        		if(reCode==null){
		        			return new ResultInfo(-1,"地区编号为空！");
		        		}
		        		dto.setZipCode(reCode.getCode());//原寄地代码 参考顺丰地区编号
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
					String[] area = delivery.getAreas().split("\\s+");
					if(area.length<2){
						return new ResultInfo(-1,"发货方地区信息不全");
					}
					dto.setDeliverProvince(area[0]);
					dto.setDeliverCity(area[1]);
					dto.setDeliverCompany(delivery.getCompanyName());
					dto.setDeliverAddress(delivery.getAddress());
					dto.setDeliverName("物流部");
					dto.setDeliverShipperCode("210001");
					dto.setDeliverTel("025-68538253");
					r.setProvinceName(area[0]);
		    		r.setCityName(area[1]);
		    		RegionCode rCode = addressService.getRegionCode(r);
		    		if(rCode==null){
		    			return new ResultInfo(-1,"地区编号为空！");
		    		}
		    		dto.setZipCode(rCode.getCode());//原寄地代码 参考顺丰地区编号
				}
	        	String[] areaArr = av.getArea().split("\\s+");
	        	if(areaArr.length<2){
	        		//查询地区信息
					traderAddress.setTraderAddressId(av.getAddressId());
					traderAddress=traderCustomerService.getTraderAddress(traderAddress);
					List<Region> araes = (List<Region>) regionService.getRegion(traderAddress.getAreaId(),1);
					if(araes==null || araes.size()<3){
						return new ResultInfo(-1,"发货方地区信息不全");
					}else{
						areaArr =  new String[2]; 
						areaArr[0] = araes.get(1).getRegionName();
						areaArr[1] = araes.get(2).getRegionName();
					}
				}
	        	dto.setConsignerProvince(areaArr[0]);
				if(areaArr.length>1){
					dto.setConsignerCity(areaArr[1]);
				}else{
					dto.setConsignerCity("");
				}
				dto.setConsignerCounty("");
				dto.setConsignerAddress(av.getAddress());
				dto.setConsignerCompany(av.getTraderName());
				dto.setConsignerMobile(av.getTraderContactMobile());
				dto.setConsignerName(av.getTraderContactName());
				dto.setConsignerShipperCode("");
				r.setProvinceName(areaArr[0]);
	    		r.setCityName(areaArr[1]);
	    		regionCode = addressService.getRegionCode(r);
	    		if(regionCode==null){
	    			return new ResultInfo(-1,"地区编号为空！");
	    		}
	    		dto.setDestCode(regionCode.getCode());//目的地代码 参考顺丰地区编号
	        }
        }
        
		CargoInfoDto cargo = new CargoInfoDto();
		if(type==3){
			dto.setElectric("E");
			dto.setExpressType(1);
			dto.setMonthAccount(yj);//月结卡号  
			dto.setPayMethod(1);// 1-寄付 2-到付 3-第三方支付
			cargo.setCargo("文件");
			cargo.setCargoCount(1);
			cargo.setCargoUnit("件");
			cargo.setSku("");
			cargo.setRemark("节假日正常派送，提前联系");
		}else if(type==1 || type==2){
			dto.setElectric("E");
			dto.setExpressType(express.getBusiness_Type());
			dto.setMonthAccount(express.getCardnumber());
			dto.setPayMethod(express.getPaymentType());
			cargo.setCargo("货物");
			cargo.setCargoCount(express.getJ_num());
			cargo.setCargoUnit("件");
			cargo.setCargoWeight(express.getRealWeight());
			cargo.setCargoTotalWeight(express.getAmountWeight());
			cargo.setRemark(express.getMailCommtents());
		}
		//签回单号  签单返回服务 会打印两份快单 其中第二份作为返寄的单
		//如客户使用签单返还业务则需打印“POD”字段，用以提醒收派员此件为签单返还快件。	
		//dto.setReturnTrackingNo("755123456789");
		
		//陆运E标示
		//业务类型为“电商特惠、顺丰特惠、电商专配、陆运件”则必须打印E标识，用以提示中转场分拣为陆运	
		//dto.setElectric("E");
		
		//快递类型	
		//1 ：标准快递   2.顺丰特惠   3： 电商特惠   5：顺丰次晨  6：顺丰即日  7.电商速配   15：生鲜速配		
		//dto.setExpressType(1);
				
		//COD代收货款金额,只需填金额, 单位元- 此项和月结卡号绑定的增值服务相关
		//dto.setCodValue("999.9");
		
		//dto.setInsureValue("501");//声明货物价值的保价金额,只需填金额,单位元
		//dto.setMonthAccount("7550385912");//月结卡号  
		//dto.setPayMethod(1);// 1-寄付 2-到付 3-第三方支付
		
		List<CargoInfoDto> cargoInfoList = new ArrayList<>();
		cargoInfoList.add(cargo);
		
		dto.setCargoInfoDtoList(cargoInfoList);
		waybillDtoList.add(dto);
		
		ObjectMapper objectMapper = new ObjectMapper();
		StringWriter stringWriter = new StringWriter();
		objectMapper.writeValue(stringWriter,waybillDtoList);
		httpConn.getOutputStream().write(stringWriter.toString().getBytes("UTF-8"));
		httpConn.getOutputStream().flush();
		httpConn.getOutputStream().close();
		InputStream in = httpConn.getInputStream();
		
		BufferedReader in2=new BufferedReader(new InputStreamReader(in));

		String y="";
	    
		String strImg="";
		while((y=in2.readLine())!=null){
			 
			strImg=y.substring(y.indexOf("[")+1,y.length()-"]".length()-1);
			if(strImg.startsWith("\"")){
				strImg=strImg.substring(1,strImg.length());
			}
			if(strImg.endsWith("\"")){
				strImg=strImg.substring(0,strImg.length()-1);
			}
		  
	   }
	    //将换行全部替换成空    
		strImg=strImg.replace("\\n", ""); 	  

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-HHmmss");
		String dateStr = format.format(new Date());
		
		return new ResultInfo(0, "成功");
	}
}
