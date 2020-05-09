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
import com.vedeng.goods.model.CategoryAttribute;
import com.vedeng.goods.model.CategoryAttributeValue;
import com.vedeng.goods.service.CategoryAttributeService;

@Service("categoryAttributeService")
public class CategoryAttributeServiceImpl extends BaseServiceimpl implements CategoryAttributeService{
	public static Logger logger = LoggerFactory.getLogger(CategoryAttributeServiceImpl.class);

	@Override
	public Map<String, Object> getCateAttributeListPage(CategoryAttribute categoryAttribute, Page page) {
		List<CategoryAttribute> list = null;Map<String,Object> map = new HashMap<>();
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<CategoryAttribute>>> TypeRef = new TypeReference<ResultInfo<List<CategoryAttribute>>>() {};
			String url=httpUrl + "goods/categoryattribute/getcateattributelistpage.htm";
			
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, categoryAttribute,clientId,clientKey, TypeRef,page);
			if(result != null){
				list = (List<CategoryAttribute>) result.getData();
				page = result.getPage();
			}
			
			map.put("list", list);map.put("page", page);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return map;
	}

	@Override
	public CategoryAttribute getCateAttributeByKey(CategoryAttribute categoryAttribute) {
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<CategoryAttribute>> TypeRef = new TypeReference<ResultInfo<CategoryAttribute>>() {};
		String url=httpUrl + "goods/categoryattribute/getcateattributebykey.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, categoryAttribute,clientId,clientKey, TypeRef);
			categoryAttribute = (CategoryAttribute) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);return null;
		}
		return categoryAttribute;
	}

	@Override
	public ResultInfo<?> saveCateAttribute(CategoryAttribute categoryAttribute) {
		ResultInfo<?> result = null;
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<CategoryAttribute>> TypeRef = new TypeReference<ResultInfo<CategoryAttribute>>() {};
		String url=httpUrl + "goods/categoryattribute/addcateattribute.htm";
		
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, categoryAttribute,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public ResultInfo<?> editCateAttribute(CategoryAttribute categoryAttribute) {
		ResultInfo<?> result = null;
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<CategoryAttribute>> TypeRef = new TypeReference<ResultInfo<CategoryAttribute>>() {};
		String url=httpUrl + "goods/categoryattribute/editcateattribute.htm";
		
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, categoryAttribute,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public ResultInfo<?> delCateAttribute(CategoryAttribute categoryAttribute) {
		ResultInfo<?> result = new ResultInfo<>();
		if(categoryAttribute!=null && categoryAttribute.getCategoryAttributeId()!=null){
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<CategoryAttribute>> TypeRef = new TypeReference<ResultInfo<CategoryAttribute>>() {};
			String url=httpUrl + "goods/categoryattribute/delcateattribute.htm";
			try {
				result = (ResultInfo<?>) HttpClientUtils.post(url, categoryAttribute,clientId,clientKey, TypeRef);
				categoryAttribute = (CategoryAttribute) result.getData();
			} catch (IOException e) {
				logger.error(Contant.ERROR_MSG, e);return null;
			}
		}
		return result;
	}

	@Override
	public ResultInfo<?> updateCateAttributeValue(CategoryAttributeValue categoryAttributeValue) {
		ResultInfo<?> result = new ResultInfo<>();
		if(categoryAttributeValue!=null && categoryAttributeValue.getCategoryAttributeId()!=null){
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<CategoryAttribute>> TypeRef = new TypeReference<ResultInfo<CategoryAttribute>>() {};
			String url=httpUrl + "goods/categoryattribute/editcateattributevalue.htm";
			try {
				result = (ResultInfo<?>) HttpClientUtils.post(url, categoryAttributeValue,clientId,clientKey, TypeRef);
				categoryAttributeValue = (CategoryAttributeValue) result.getData();
			} catch (IOException e) {
				logger.error(Contant.ERROR_MSG, e);return null;
			}
		}
		return result;
	}

	@Override
	public List<CategoryAttributeValue> getCateAttrValueByKey(CategoryAttributeValue categoryAttributeValue) {
		List<CategoryAttributeValue> list = null;
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<CategoryAttributeValue>>> TypeRef = new TypeReference<ResultInfo<List<CategoryAttributeValue>>>() {};
			String url=httpUrl + "goods/categoryattribute/getcateattrvaluebykey.htm";
			
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, categoryAttributeValue,clientId,clientKey, TypeRef);
			
			list = (List<CategoryAttributeValue>) result.getData();
			
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
	}

	@Override
	public List<CategoryAttribute> getAttributeByCategoryId(CategoryAttribute categoryAttribute) {
		List<CategoryAttribute> list = null;
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<CategoryAttribute>>> TypeRef = new TypeReference<ResultInfo<List<CategoryAttribute>>>() {};
			String url=httpUrl + "goods/categoryattribute/getattributebycategoryid.htm";
			
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, categoryAttribute,clientId,clientKey, TypeRef);
			
			list = (List<CategoryAttribute>) result.getData();
			
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
	}

}
