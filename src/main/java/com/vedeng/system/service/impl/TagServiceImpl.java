package com.vedeng.system.service.impl;

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
import com.vedeng.system.model.Tag;
import com.vedeng.system.service.TagService;

@Service("tagService")
public class TagServiceImpl extends BaseServiceimpl implements TagService {
	public static Logger logger = LoggerFactory.getLogger(TagServiceImpl.class);

	@Override
	public Map<String, Object> getTagListPage(Tag tag, Page page) {
		List<Tag> tagList= null;
		Map<String,Object> map = new HashMap<>();
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<Tag>>> TypeRef = new TypeReference<ResultInfo<List<Tag>>>() {};
			String url=httpUrl + "tag/gettaglistpage.htm";
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, tag,clientId,clientKey, TypeRef,page);
			tagList = (List<Tag>) result.getData();
			page = result.getPage();
			
			map.put("list", tagList);map.put("page", page);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return map;
	}

}
