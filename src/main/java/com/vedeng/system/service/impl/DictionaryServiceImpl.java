package com.vedeng.system.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.shiro.JedisUtils;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.service.DictionaryService;

@Service("dictionaryService")
public class DictionaryServiceImpl extends BaseServiceimpl implements DictionaryService {
	/**
	 * @Fields logger : TODO日志
	 */
	private static Logger logger = LoggerFactory.getLogger(DictionaryServiceImpl.class);
	//private ServletContext sc;
	/**
	 * @Fields zero : TODO
	 */
	private static final Integer zero = 0;
	private static final String DATA_DICTIONARY_LIST = "sysoptiondefinition/getdatadictionarylist.htm";

	/**
	 * <b>Description:</b><br>
	 * 初始化字典数据
	 * 
	 * @param sc
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年6月1日 下午3:01:39
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void innit() {
		//this.sc = sc;
		SysOptionDefinition sod = new SysOptionDefinition();
		sod.setParentId(0);
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Map<Integer, List<SysOptionDefinition>>>> TypeRef2 = new TypeReference<ResultInfo<Map<Integer, List<SysOptionDefinition>>>>() {
		};
		try {

			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(httpUrl + DATA_DICTIONARY_LIST, sod, clientId,
					clientKey, TypeRef2);
			if(result==null || result.getCode() != 0){
				return;
			}
			Map<Integer, List<SysOptionDefinition>> map = (Map<Integer, List<SysOptionDefinition>>) result.getData();
			if (null != map) {
				List<SysOptionDefinition> list = null;
				for (Integer key : map.keySet()) {
					list = map.get(key);
					if(key==0){
						if(list!=null&&list.size()>0){
							for (SysOptionDefinition sys : list) {
								if (JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_OBJECT + sys.getSysOptionDefinitionId())) {
									JedisUtils.del(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_OBJECT + sys.getSysOptionDefinitionId());
								}
								JedisUtils.set(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_OBJECT + sys.getSysOptionDefinitionId(), JsonUtils.convertObjectToJsonStr(sys), zero);
							}
						}
					}else{
						if (JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST+ key)) {
							JedisUtils.del(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST + key);
						}
						JedisUtils.set(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST + key, JsonUtils.convertConllectionToJsonStr(list), zero);
					}
					
				}
			}

		} catch (Exception e) {
			logger.error("字典数据加载失败!");
			throw new RuntimeException();
		}

	}

}
