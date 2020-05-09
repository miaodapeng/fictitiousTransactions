package com.vedeng.common.web.listener;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;


import com.vedeng.ez.utils.Encode;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;

public class Customlister extends ContextLoaderListener {
	private static final Logger LOGGE = LoggerFactory.getLogger(Customlister.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ConvertUtils.register(new DateConverter(null), java.util.Date.class);
		LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
		JoranConfigurator jc = new JoranConfigurator();
		jc.setContext(context);
		context.reset();
		Config config = ConfigService.getAppConfig();
		try {
			ClassPathResource logxml = new ClassPathResource("log/logback.xml");
			jc.doConfigure(logxml.getFile().getAbsolutePath());
		} catch (Exception e1) {
			LOGGE.error("日志初始化异常 111  ", e1);
		}
		Encode encode=new Encode();
		encode.init("vedeng1234");
		super.contextInitialized(sce);
	}
}