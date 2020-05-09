package com.vedeng.goods.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.constants.Contant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.ObjectUtils;
import com.vedeng.goods.model.Brand;
import com.vedeng.goods.service.BrandService;

@Service("brandService")
public class BrandServiceImpl extends BaseServiceimpl implements BrandService {
	public static Logger logger = LoggerFactory.getLogger(BrandServiceImpl.class);

	@Override
	public List<Brand> getAllBrand(Brand brand) {
		String url = httpUrl + "goods/brand/getallbrand.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<Brand>>> TypeRef2 = new TypeReference<ResultInfo<List<Brand>>>() {};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, brand,clientId,clientKey, TypeRef2);
			List<Brand> brandList = (List<Brand>) result2.getData();
			return brandList;
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public Map<String, Object> getBrandListPage(Brand brand, Page page) {
		List<Brand> list = null;Map<String,Object> map = new HashMap<>();
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<Brand>>> TypeRef = new TypeReference<ResultInfo<List<Brand>>>() {};
			String url=httpUrl + "goods/brand/getbrandlistpage.htm";
			
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, brand,clientId,clientKey, TypeRef,page);
			
			list = (List<Brand>) result.getData();
			page = result.getPage();
			if(list != null && list.size() > 0){
				for (Brand b : list) {
					if(ObjectUtils.notEmpty(b.getLogoUri())){
						b.setLogoUriName(b.getLogoUri().substring(b.getLogoUri().lastIndexOf("/")+1));
					}
				}
			}
			map.put("list", list);map.put("page", page);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return map;
	}

	
	@Override
	public ResultInfo<?> saveBrand(Brand brand) {
		ResultInfo<?> result = null;
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Brand>> TypeRef = new TypeReference<ResultInfo<Brand>>() {};
		String url=httpUrl + "goods/brand/addbrand.htm";
		
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, brand,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public Brand getBrandByKey(Brand brand) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Brand>> TypeRef = new TypeReference<ResultInfo<Brand>>() {};
		String url=httpUrl + "goods/brand/getbrandbykey.htm";
		
		ResultInfo<?> result;
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, brand,clientId,clientKey, TypeRef);
			brand = (Brand)result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return brand;
	}

	@Override
	public ResultInfo<?> editBrand(Brand brand) {
		ResultInfo<?> result = null;
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Brand>> TypeRef = new TypeReference<ResultInfo<Brand>>() {};
		String url=httpUrl + "goods/brand/editbrand.htm";
		
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, brand,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public ResultInfo<?> delBrand(Brand brand) {
		ResultInfo<?> result = null;
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Brand>> TypeRef = new TypeReference<ResultInfo<Brand>>() {};
		String url=httpUrl + "goods/brand/delbrand.htm";
		
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, brand,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(Contant.ERROR_MSG, e);
			return new ResultInfo<>();
		}
		return result;
	}

}
