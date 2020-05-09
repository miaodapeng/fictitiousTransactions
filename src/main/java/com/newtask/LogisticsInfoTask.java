package com.newtask;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Sets;
import com.xxl.job.core.log.XxlJobLogger;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.data.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.putHCutil.service.HcSaleorderService;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.HttpRequestExpress;
import com.vedeng.common.util.MD5;
import com.vedeng.logistics.dao.ExpressMapper;
import com.vedeng.logistics.model.Express;
import com.vedeng.logistics.model.LogisticsDetail;
import com.vedeng.logistics.service.ExpressService;
import com.vedeng.order.model.vo.SaleorderVo;
import com.vedeng.order.service.SaleorderService;
import com.vedeng.soap.service.VedengSoapService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@JobHandler(value = "logisticsInfoTask")
@Component
public class LogisticsInfoTask extends IJobHandler {

	// 授权密匙key
	private String KEY = "vIStKARH975";
	// 公司编号customer
	private String CUSTOMER = "32EA2614AD1441636ADFA1F014060190";
	// 请求url
	private String ReqURL = "http://api.kdniao.cc/Ebusiness/EbusinessOrderHandle.aspx";
	WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
	@Autowired
	private ExpressService expressService;
	@Autowired
	private VedengSoapService vedengSoapService;
	@Autowired
	private SaleorderService saleorderService;
	@Autowired
	private HcSaleorderService hcSaleorderService;
	@Autowired
	private ExpressMapper expressMapper;

	@Value("${http_url}")
	protected String httpUrl;

	@Value("${client_id}")
	protected String clientId;

	@Value("${client_key}")
	protected String clientKey;

	@Value("${file_url}")
	protected String picUrl;

	/**
	 *
	 * <b>Description:</b><br>
	 * 同步快递信息
	 *
	 * @param cId
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2017年8月30日 下午4:37:48
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	public ReturnT<String> execute(String param) throws Exception {
		if(StringUtils.isBlank(param)&&StringUtils.contains(httpUrl,"qa.godbcenter.ivedeng.com")){
			XxlJobLogger.log("测试环境不允许批量执行");
			return ReturnT.FAIL;
		}
		// 获取已发货订单物流号
		Express express = new Express();
		List<Express> list = new ArrayList<Express>();
		Long time = DateUtil.sysTimeMillis();
		InputStream in = null;

		express.setEndTime(
				DateUtil.convertLong(DateUtil.convertString(time, DateUtil.DATE_FORMAT), DateUtil.DATE_FORMAT));
		List<Express> epList = new ArrayList<Express>();
		/************************** 快递改为直接在erp查询end *******************************/
		epList = expressMapper.getExpressInfoList(express);// 查询未到货的订单的物流单号
		//for(int i=0;i<4;i++){
			//XxlJobLogger.log("第"+i+"次执行");
			if(CollectionUtils.isNotEmpty(epList)){
				execute(  param,  epList);
			}
			//epList = expressMapper.getExpressInfoList(express);
		//}
		return SUCCESS;
	}

	private void execute(String param,List<Express> EpList){
		Long time = DateUtil.sysTimeMillis();
		List<LogisticsDetail> ldList = new ArrayList<LogisticsDetail>();
		List<String> str = new ArrayList<String>();
		for (int i = 0; i < EpList.size(); i++) {
			String LNO = EpList.get(i).getLogisticsNo();// 物流单号
			if(StringUtils.isNotBlank(param)&&!StringUtils.equals(LNO,param)){
				continue;
			}
			LogisticsDetail ld = new LogisticsDetail();

			if(StringUtils.isNotBlank(param)&&!StringUtils.equals(LNO,param) ){
				XxlJobLogger.log("指定快递单同步："+param);
				continue;
			}

			Set<String> lnoList= Sets.newHashSet();
			// 获取快递公司编号
			if (!"".equals(LNO) && (LNO.matches("^[0-9]*$")||LNO.startsWith("SF"))&&!lnoList.contains(LNO)) {
				String logisticsInfo = queryInfo(EpList.get(i).getCode(), LNO);
				JSONObject rd = JSONObject.fromObject(logisticsInfo);
				rd.put("expressId",EpList.get(i).getExpressId());
				ld.setModTime(time);
				ld.setLogisticsNo(LNO);
				ld.setContent(logisticsInfo);
				ldList.add(ld);
				str.add(JSON.toJSONString(rd));
				lnoList.add(LNO);
			}
		}
		if (ldList.size() > 0) {
			XxlJobLogger.log("size >0 "+ ldList);
			// 批量更新物流信息
			ResultInfo<?> rt = new ResultInfo<>();
			int i= expressMapper.batchInsert(ldList) ;
            XxlJobLogger.log("editLogisticsDetail success "+ ldList.size());
			int rsNum = 0;
			ResultInfo resultInfo = new ResultInfo<>();


				List<Express> exList = new ArrayList<Express>();
				for (String s : str) {// 0:暂无信息2：在途中：3：已签收4：问题单
					if (s != null && !"".equals(s)) {
						JSONObject rd = JSONObject.fromObject(s);
						XxlJobLogger.log("rd.getString(\"message\")==="+rd.getString("message"));
						if ("ok".equals(rd.getString("message"))) {
							Express e = new Express();
							e.setLogisticsNo(rd.getString("nu"));
							e.setExpressId(Integer.parseInt(rd.getString("expressId")));
							JSONArray ja = rd.getJSONArray("data");
							JSONObject jl = ja.getJSONObject(0);
							Long arrTime = 0L;
							if (jl.getString("time") != null) {
								arrTime = DateUtil.convertLong(jl.getString("time"), "yyyy-MM-dd HH:mm:ss");
							} else {
								arrTime = DateUtil.sysTimeMillis();
							}
							if ("1".equals(rd.getString("ischeck"))) {
								e.setArrivalStatus(2);
								e.setArrivalTime(arrTime);
							} else {
								e.setArrivalStatus(0);
								e.setArrivalTime(Long.valueOf("0"));
							}
							exList.add(e);
						}
					}
				}
				// 更新快递单
				if (exList.size() > 0) {
					XxlJobLogger.log("开始更新"+exList.size());
					try{
					resultInfo = expressService.editExpres(exList);
					XxlJobLogger.log("更新成功"+exList.size());
					// 快递状态推送
					if (resultInfo.getCode().equals(0)&&!StringUtils.contains(httpUrl,"qa.godbcenter.ivedeng.com")) {
						List<SaleorderVo> saleorderList = (List<SaleorderVo>) resultInfo.getData();
						// 批量签收推送
						vedengSoapService.messageBtachSignSyncWeb(saleorderList);
					}
					}catch(Exception e){
						XxlJobLogger.log("更新错误"+StringUtils.join(exList,","),e);
					}
				}

		}
	}

	@SuppressWarnings("static-access")
	public String queryInfo(String com, String logisticsNo) {
		String param = "{\'com\':\'" + com + "\',\'num\':\'" + logisticsNo + "\'}";
		String customer = "32EA2614AD1441636ADFA1F014060190";
		String key = "vIStKARH975";
		String sign = MD5.encode(param + key + customer);
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("param", param);
		params.put("sign", sign);
		params.put("customer", customer);
		String resp = "";
		try {
			resp = new HttpRequestExpress().postData("http://poll.kuaidi100.com/poll/query.do", params, "utf-8")
					.toString();
			XxlJobLogger.log(resp);
		} catch (Exception e) {
			XxlJobLogger.log(e.getMessage());
		}
		return resp;
	}

}
