package com.rest.editor;

import java.io.InputStream;
import org.activiti.engine.ActivitiException;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RestController
public class StencilsetRestResource {
	@RequestMapping(value = { "/service/editor/stencilset" }, method = {
			org.springframework.web.bind.annotation.RequestMethod.GET }, produces = {
					"application/json;charset=utf-8" })
	@ResponseBody
	public Object getStencilset() {
		System.out.println("StencilsetRestResource.getStencilset-----------");
		InputStream stencilsetStream = getClass().getClassLoader().getResourceAsStream("stencilset.json");
		try {
			// return IOUtils.toString(stencilsetStream, "utf-8");
			return JSONObject.parse(IOUtils.toString(stencilsetStream, "utf-8"));// 返回Json对象，针对当前springmvc环境做了修改
		} catch (Exception e) {
			throw new ActivitiException("Error while loading stencil set", e);
		}
	}
}