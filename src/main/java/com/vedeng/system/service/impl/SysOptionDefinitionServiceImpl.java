package com.vedeng.system.service.impl;

import com.common.constants.Contant;
import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.system.dao.SysOptionDefinitionMapper;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.service.SysOptionDefinitionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service("sysOptionDefinitionService")
public class SysOptionDefinitionServiceImpl extends BaseServiceimpl implements SysOptionDefinitionService {
	public static Logger logger = LoggerFactory.getLogger(SysOptionDefinitionServiceImpl.class);

	
	@Autowired
	@Qualifier("sysOptionDefinitionMapper")
	private SysOptionDefinitionMapper sysOptionDefinitionMapper;
	
	
	@Override
	public Map<Integer, List<SysOptionDefinition>> getOptionByScopeList(List<Integer> scopeList) {
		String url = httpUrl + "sysoptiondefinition/getoptionbyscopelist.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Map<Integer, List<SysOptionDefinition>>>> TypeRef2 = new TypeReference<ResultInfo<Map<Integer, List<SysOptionDefinition>>>>() {};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, scopeList,clientId,clientKey, TypeRef2);
			Map<Integer, List<SysOptionDefinition>> optionList = (Map<Integer, List<SysOptionDefinition>>) result2.getData();
			return optionList;
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public List<SysOptionDefinition> getOptionByScope(Integer scope) {
		String url = httpUrl + "sysoptiondefinition/getoptionbyscope.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<SysOptionDefinition>>> TypeRef2 = new TypeReference<ResultInfo<List<SysOptionDefinition>>>() {};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, scope,clientId,clientKey, TypeRef2);
			List<SysOptionDefinition> optionList = (List<SysOptionDefinition>) result2.getData();
			return optionList;
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public Map<String,Object>  querylistPage(SysOptionDefinition sysOptionDefinition, Page page) {
		String url = httpUrl + "sysoptiondefinition/getoptionlistpage.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<SysOptionDefinition>>> TypeRef2 = new TypeReference<ResultInfo<List<SysOptionDefinition>>>() {};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, sysOptionDefinition,clientId,clientKey, TypeRef2, page);
			List<SysOptionDefinition> optionList = (List<SysOptionDefinition>) result.getData();
			page = result.getPage();
			Map<String, Object> map = new HashMap<>();
			map.put("list", optionList);
			map.put("page", page);
			return map;
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public SysOptionDefinition getOptionById(SysOptionDefinition sysOptionDefinition) {
		String url = httpUrl + "sysoptiondefinition/getoptionbyid.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<SysOptionDefinition>> TypeRef2 = new TypeReference<ResultInfo<SysOptionDefinition>>() {};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, sysOptionDefinition,clientId,clientKey, TypeRef2);
			SysOptionDefinition option = (SysOptionDefinition) result.getData();
			return option;
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public ResultInfo saveAdd(SysOptionDefinition sysOptionDefinition) {
		String url = httpUrl + "sysoptiondefinition/saveadd.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, sysOptionDefinition,clientId,clientKey, TypeRef2);
			return result;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return new ResultInfo(-1,"操作失败");
		}
	}

	@Override
	public ResultInfo saveEdit(SysOptionDefinition sysOptionDefinition) {
		String url = httpUrl + "sysoptiondefinition/saveedit.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, sysOptionDefinition,clientId,clientKey, TypeRef2);
			return result;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return new ResultInfo(-1,"操作失败");
		}
	}

	@Override
	public List<SysOptionDefinition> getChilds(SysOptionDefinition sysOptionDefinition) {
		String url = httpUrl + "sysoptiondefinition/getchilds.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<SysOptionDefinition>>> TypeRef2 = new TypeReference<ResultInfo<List<SysOptionDefinition>>>() {};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, sysOptionDefinition,clientId,clientKey, TypeRef2);
			List<SysOptionDefinition> optionList = (List<SysOptionDefinition>) result2.getData();
			return optionList;
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public ResultInfo changeStatus(SysOptionDefinition sysOptionDefinition) {
		String url = httpUrl + "sysoptiondefinition/changestatus.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, sysOptionDefinition,clientId,clientKey, TypeRef2);
			return result;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return new ResultInfo(-1,"操作失败");
		}
	}

	/**
	 * 根据父级参数获取当前作用域字典库信息
	 */
	@Override
	public Map<String,List<SysOptionDefinition>> getSysOptionDefinitionByParam(List<Integer> scopeList) {
		
		// 所有的字典信息
		List<SysOptionDefinition> systemList = sysOptionDefinitionMapper.getSysOptionDefinitionByParam(scopeList);
		
		Map<String, List<SysOptionDefinition>> map = new HashMap<String, List<SysOptionDefinition>>();
        for (Integer s : scopeList) {
            List<SysOptionDefinition> list = new ArrayList<SysOptionDefinition>();
            for (SysOptionDefinition o : systemList) {
                if (s.equals(o.getpScope())) {
                    list.add(o);
                }
            }

            map.put(s.toString(), list);
        }
        return map;
	}
	public SysOptionDefinition selectByPrimaryKey(Integer id){
		return sysOptionDefinitionMapper.selectByPrimaryKey(id);
	}


}
