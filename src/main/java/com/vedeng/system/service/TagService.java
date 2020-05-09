package com.vedeng.system.service;

import java.util.Map;

import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.system.model.Tag;

/**
 * <b>Description:</b><br> 标签
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.system.service
 * <br><b>ClassName:</b> TagService
 * <br><b>Date:</b> 2017年5月18日 下午4:27:44
 */
public interface TagService extends BaseService {
	/**
	 * <b>Description:</b><br> 查询标签数据列表(分页)
	 * @param tag
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年5月18日 下午4:28:10
	 */
	Map<String,Object> getTagListPage(Tag tag,Page page);
}
