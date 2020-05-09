package com.vedeng.system.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.common.constants.Contant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.system.model.Ad;
import com.vedeng.system.model.vo.AdVo;
import com.vedeng.system.service.AdService;
import com.vedeng.system.service.ReadService;
@Service("adService")
public class AdServiceImpl extends BaseServiceimpl implements AdService {
	public static Logger logger = LoggerFactory.getLogger(AdServiceImpl.class);

	@Resource
	private ReadService readService;

	@Override
	public Map<String, Object> getAdListPage(AdVo ad, Page page) {
		String url = httpUrl + "system/ad/getadlistpage.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<AdVo>>> TypeRef2 = new TypeReference<ResultInfo<List<AdVo>>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, ad, clientId, clientKey, TypeRef2,page);
			if(result == null || result.getCode() ==-1){
				return null;
			}
			@SuppressWarnings("unchecked")
			List<AdVo> asvList = (List<AdVo>) result.getData();
			page = result.getPage();
			Map<String, Object> map = new HashMap<>();
			map.put("list", asvList);
			map.put("page", page);
			return map;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}

	@Override
	public Ad getAdDetail(Ad ad) {
		String url = httpUrl + "system/ad/getaddetail.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Ad>> TypeRef2 = new TypeReference<ResultInfo<Ad>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, ad, clientId, clientKey, TypeRef2);
			return (Ad) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}

	@Override
	public ResultInfo<?> saveOrUpdateAd(AdVo ad) {
		String url = httpUrl + "system/ad/saveorupdatead.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, ad, clientId, clientKey, TypeRef2);
			if(result != null && result.getCode() == 0 && ad.getIsShow() == 1){
				readService.delAllReadByCompanyId(ad.getCompanyId());
			}
			return result;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return new ResultInfo<>();
		}
	}

	@Override
	public List<AdVo> getAdVoList(AdVo ad) {
		String url = httpUrl + "system/ad/getadvolist.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<AdVo>>> TypeRef2 = new TypeReference<ResultInfo<List<AdVo>>>() {
		};
		try {
			@SuppressWarnings("unchecked")
			ResultInfo<List<AdVo>> result = (ResultInfo<List<AdVo>>) HttpClientUtils.post(url, ad, clientId, clientKey, TypeRef2);
			return (List<AdVo>) result.getData();
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}

	

}
