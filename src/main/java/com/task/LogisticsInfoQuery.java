package com.task;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.common.constants.Contant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class LogisticsInfoQuery extends BaseTaskController {

	// 授权密匙key
	private String KEY = "tCMitFqr4179";
	// 公司编号customer
	private String CUSTOMER = "9F63CF85ECF41C2A49571BE750BBD328";
	// 请求url
	private String ReqURL = "http://api.kdniao.cc/Ebusiness/EbusinessOrderHandle.aspx";
	WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
	private ExpressService expressService = (ExpressService) context.getBean("expressService");
	private VedengSoapService vedengSoapService = (VedengSoapService) context.getBean("vedengSoapService");
	private SaleorderService saleorderService = (SaleorderService) context.getBean("saleorderService");
	private HcSaleorderService hcSaleorderService = (HcSaleorderService) context.getBean("hcSaleorderService");
	private ExpressMapper expressMapper = (ExpressMapper) context.getBean("expressMapper");
	public static Logger logger = LoggerFactory.getLogger(LogisticsInfoQuery.class);

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
	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	public ResultInfo synchronizationInfo() throws Exception {
		// 获取已发货订单物流号
		Express express = new Express();
		List<Express> list = new ArrayList<Express>();
		Long time = DateUtil.sysTimeMillis();
		InputStream in = null;

		express.setEndTime(
				DateUtil.convertLong(DateUtil.convertString(time, DateUtil.DATE_FORMAT), DateUtil.DATE_FORMAT));
		final TypeReference<ResultInfo<List<Express>>> TypeRef = new TypeReference<ResultInfo<List<Express>>>() {
		};
		
		List<Express> EpList = new ArrayList<Express>();
		/**************************快递改为直接在erp查询end*******************************/
		EpList = expressMapper.getExpressInfoList(express);

		List<LogisticsDetail> ldList = new ArrayList<LogisticsDetail>();
		List<String> str = new ArrayList<String>();
		for (int i = 0; i < EpList.size(); i++) {
			LogisticsDetail ld = new LogisticsDetail();
			String LNO = EpList.get(i).getLogisticsNo();// 物流单号

			// 获取快递公司编号
			if (!"".equals(LNO) && (LNO.matches("^[0-9]*$"))) {
				String logisticsInfo = queryInfo(EpList.get(i).getCode(), LNO);
				ld.setModTime(time);
				ld.setLogisticsNo(LNO);
				ld.setContent(logisticsInfo);
				ldList.add(ld);
				str.add(logisticsInfo);
			}
		}
		if (ldList.size() > 0) {
			// 批量更新物流信息
			ResultInfo<?> rt = new ResultInfo<>();
			rt = expressService.editLogisticsDetail(ldList);
			int rsNum = 0;
			ResultInfo resultInfo = new ResultInfo<>();

			if (rt.getCode() == 0) {
				List<Express> exList = new ArrayList<Express>();
				for (String s : str) {// 0:暂无信息2：在途中：3：已签收4：问题单
					if (s != null && !"".equals(s)) {
						JSONObject rd = JSONObject.fromObject(s);
						if ("ok".equals(rd.getString("message"))) {
							Express e = new Express();
							e.setLogisticsNo(rd.getString("nu"));
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
					resultInfo = expressService.editExpres(exList);
					// 快递状态推送
					if (resultInfo.getCode().equals(0)) {
						List<SaleorderVo> saleorderList = (List<SaleorderVo>) resultInfo.getData();
						// 批量签收推送
						vedengSoapService.messageBtachSignSyncWeb(saleorderList);
					}
				}
			}
		}
		return new ResultInfo(0, "操作成功");
	}

	@SuppressWarnings("static-access")
	public String queryInfo(String com, String logisticsNo) {
		String param = "{\'com\':\'" + com + "\',\'num\':\'" + logisticsNo + "\'}";
		String customer = "9F63CF85ECF41C2A49571BE750BBD328";
		String key = "tCMitFqr4179";
		String sign = MD5.encode(param + key + customer);
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("param", param);
		params.put("sign", sign);
		params.put("customer", customer);
		String resp = "";
		try {
			resp = new HttpRequestExpress().postData("http://poll.kuaidi100.com/poll/query.do", params, "utf-8")
					.toString();
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return resp;
	}
}
