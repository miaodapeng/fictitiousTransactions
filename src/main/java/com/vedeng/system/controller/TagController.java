package com.vedeng.system.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vedeng.common.controller.BaseController;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.system.model.Tag;
import com.vedeng.system.service.TagService;

/**
 * <b>Description:</b><br> 标签
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.system.controller
 * <br><b>ClassName:</b> TagController
 * <br><b>Date:</b> 2017年5月18日 下午4:58:48
 */
@Controller
@RequestMapping("/system/tag")
public class TagController extends BaseController {
	@Autowired
	@Qualifier("tagService")
	private TagService tagService;
	
	/**
	 * <b>Description:</b><br> 异步分页获取标签
	 * @param request
	 * @param tag
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年5月18日 下午5:07:31
	 */
	@ResponseBody
	@RequestMapping(value="/gettag")
	public ResultInfo<?> getTag(HttpServletRequest request,Tag tag,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,  //required = false:可不传入pageNo这个参数，true必须传入，defaultValue默认值，若无默认值，使用Page类中的默认值
            @RequestParam(required = false) Integer pageSize){
		Page page = getPageTag(request,pageNo,pageSize);
		Map<String, Object> tagMap = tagService.getTagListPage(tag, page);
		
		ResultInfo<Map<String, Object>> result = new ResultInfo<Map<String, Object>>();
		
		if((List<Tag>)tagMap.get("list") != null){
			try {
				result.setCode(0);
				result.setMessage("操作成功");
				result.setParam(JsonUtils.translateToJson(tagMap));
			} catch (Exception e) {
				logger.error("tag gettag:", e);
			}
		}
		return result;
	}
}
