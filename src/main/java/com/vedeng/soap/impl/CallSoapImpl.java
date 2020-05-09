package com.vedeng.soap.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.authorization.dao.UserMapper;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.common.util.XmlExercise;
import com.vedeng.soap.CallSoap;
import com.vedeng.trader.model.vo.TraderCustomerVo;
import com.vedeng.trader.model.vo.TraderSupplierVo;

import net.sf.json.JSONObject;

@Component("callSoap")
public class CallSoapImpl extends BaseServiceimpl implements CallSoap {

	@Value("${http_url}")
	protected String httpUrl;

	@Autowired
	@Qualifier("userMapper")
	private UserMapper userMapper;

	@Override
	public String hello(String msg) {
		return "Hello" + msg;
	}

	@Override
	public String agent(String phone) {
		Map<String,Object> map = new HashMap<String, Object>();
		XmlExercise xmlExercise = new XmlExercise();
		
		Integer code = 2;
		String message = "无归属";
		String data = "";
		
		try {
			// 接口调用（优先查客户，客户没有查供应商库）
			String url = httpUrl + "tradercustomer/getcustomerinfobyphone.htm";
			TraderCustomerVo traderCustomer = new TraderCustomerVo();
			traderCustomer.setPhone(phone);
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {
			};
			ResultInfo<?> result2;
			result2 = (ResultInfo<?>) HttpClientUtils.post(url, traderCustomer, clientId, clientKey, TypeRef2);

			JSONObject json = JSONObject.fromObject(result2.getData());
			// 对象包含另一个对象集合时用以下方法转化
			TraderCustomerVo res = JsonUtils.readValue(json.toString(), TraderCustomerVo.class);

			if (null != res) {// 有客户
				// 客户归属人
				User sale = userMapper.getUserByTraderId(res.getTraderId(), ErpConst.ONE);
				if (null != sale) {
					code = 1;
					message = "有归属";
					data = sale.getNumber();
				}
			} else {// 无客户查供应商
					// 接口调用
				String urlSupplier = httpUrl + "tradersupplier/getsupplierinfobyphone.htm";
				TraderSupplierVo traderSupplier = new TraderSupplierVo();
				traderSupplier.setPhone(phone);
				// 定义反序列化 数据格式
				final TypeReference<ResultInfo<?>> TypeRefSupplier = new TypeReference<ResultInfo<?>>() {
				};
				ResultInfo<?> resultSupplier = (ResultInfo<?>) HttpClientUtils.post(urlSupplier, traderSupplier,
						clientId, clientKey, TypeRefSupplier);
				JSONObject jsonSupplier = JSONObject.fromObject(resultSupplier.getData());
				// 对象包含另一个对象集合时用以下方法转化
				TraderSupplierVo resSupplier = JsonUtils.readValue(jsonSupplier.toString(), TraderSupplierVo.class);

				if (null != resSupplier) {
					// 客户归属人
					User sale = userMapper.getUserByTraderId(resSupplier.getTraderId(), ErpConst.TWO);
					if (null != sale) {
						code = 1;
						message = "有归属";
						data = sale.getNumber();
					}
				}
			}

		} catch (IOException e) {
			code = 0;
			message = "出错了";
		}
		
		map.put("code", code);
		map.put("data", data);
		map.put("message", message);
		return xmlExercise.mapToXml(map, "data");
	}

}
