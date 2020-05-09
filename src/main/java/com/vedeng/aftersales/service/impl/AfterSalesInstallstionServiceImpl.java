package com.vedeng.aftersales.service.impl;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import com.common.constants.Contant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.aftersales.model.AfterSalesInstallstion;
import com.vedeng.aftersales.service.AfterSalesInstallstionService;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.DateUtil;
@Service("afterSalesInstallstionService")
public class AfterSalesInstallstionServiceImpl extends BaseServiceimpl implements AfterSalesInstallstionService{
	public static Logger logger = LoggerFactory.getLogger(AfterSalesInstallstionServiceImpl.class);

	@Override
	public AfterSalesInstallstion getAfterSalesInstallstion(AfterSalesInstallstion afterSalesInstallstion) {
		String url = httpUrl + "aftersalesinstallstion/getaftersalesinstallstion.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<AfterSalesInstallstion>> TypeRef2 = new TypeReference<ResultInfo<AfterSalesInstallstion>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, afterSalesInstallstion, clientId, clientKey, TypeRef2);
			AfterSalesInstallstion salesInstallstion = (AfterSalesInstallstion) result.getData();
			
			return salesInstallstion;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}

	@Override
	public ResultInfo saveInstallstionScore(AfterSalesInstallstion afterSalesInstallstion, HttpSession session) {
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();
		afterSalesInstallstion.setScoreUserId(user.getUserId());
		afterSalesInstallstion.setScoreTime(time);
		
		String url = httpUrl + "aftersalesinstallstion/saveinstallstionscore.htm";
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, afterSalesInstallstion, clientId, clientKey, TypeRef);
			return result;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return new ResultInfo();
		}
	}

}
