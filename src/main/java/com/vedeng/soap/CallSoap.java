package com.vedeng.soap;

import com.vedeng.soap.model.SoapResult;

/**
 * <b>Description:</b><br> 呼叫中心接口：http://localhost:8080/erp/services/callapi?wsdl
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.soap
 * <br><b>ClassName:</b> CallSoap
 * <br><b>Date:</b> 2017年7月26日 下午1:13:49
 */
public interface CallSoap {
	/**
	 * <b>Description:</b><br> 测试
	 * @param msg
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年7月25日 下午5:31:28
	 */
	public String hello(String msg);
	
	/**
	 * <b>Description:</b><br> 主动呼入查询归属客服工号
	 * @param phone
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年7月25日 下午6:30:10
	 */
	public String agent(String phone);
}
