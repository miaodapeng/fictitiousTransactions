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
import com.vedeng.system.model.Attachment;
import com.vedeng.system.model.vo.AdVo;
import com.vedeng.system.service.AdService;
import com.vedeng.system.service.AttachmentService;
import com.vedeng.system.service.ReadService;
@Service("attachmentService")
public class AttachmentServiceImpl extends BaseServiceimpl implements AttachmentService {
	public static Logger logger = LoggerFactory.getLogger(AttachmentServiceImpl.class);

	@Resource
	private ReadService readService;

	@Override
	public ResultInfo<?> saveOrUpdateAttachment(Attachment attachment) {
		String url = httpUrl + "attachment/saveorupdateattachment.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, attachment, clientId, clientKey, TypeRef2);
			return result;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return new ResultInfo<>();
		}
	}


	

}
