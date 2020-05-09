package com.vedeng.common.shiro;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.vedeng.system.service.DictionaryService;

/**
 * <b>Description:</b><br> 加载字典表数据到redis
 * @author east
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.common.constant
 * <br><b>ClassName:</b> SysInitBean
 * <br><b>Date:</b> 2017年6月1日 下午2:56:55
 */
@Component
public class SysInitBean implements ServletContextAware {
	
	@Resource
	private DictionaryService dictionaryService;

	@Override
	public void setServletContext(ServletContext sc) {
		//String ctxPath=sc.getContextPath();
        //sc.setAttribute("ctxPath",ctxPath);
        //初始化数据字典到application中  
        //dictionaryService.innit();

	}

}
