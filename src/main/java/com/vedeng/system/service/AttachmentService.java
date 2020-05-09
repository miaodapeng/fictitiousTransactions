package com.vedeng.system.service;

import java.util.List;
import java.util.Map;

import com.vedeng.common.model.ResultInfo;
import com.vedeng.system.model.Attachment;

public interface AttachmentService {
	/**
	 * <b>Description:</b><br> 新增或修改附件数据
	 * @param ad
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2018年7月25日 下午6:37:56
	 */
	ResultInfo<?> saveOrUpdateAttachment(Attachment attachment);

}
