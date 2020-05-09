package com.vedeng.common.util;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.Appender;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

/**
 * logback日志动态配置实现
 *
 * @author eric
 * @date 2019/12/11
 */
public class LogbackAutoConfig {

    private static final String LOG_LEVEL_APOLLO_KEY = "log.level";

    private static final String LOG_PRINT_CONSOLE_FLAG = "log.console.flag";
    //自定义logger,逗号隔开
    private static final String LOG_CUSTOM_LOGGER = "log.custom.logger";



    private static final String FLAG_TRUE = "true";
    private static final String FLAG_FALSE = "false";

    private static final String LOGBACK_CONFIGURATION_ROOT = "root";
    private static final String DEFAULT_CONSOLE_APPENDER_NAME = "consoleLog";
    private static final String ALL_LOGGER_NAME = "";

    private Appender consoleAppend;

    private String consoleAppenderName = DEFAULT_CONSOLE_APPENDER_NAME;
    public void setConsoleAppenderName(String consoleAppenderName) {
        this.consoleAppenderName = consoleAppenderName;
    }

    @ApolloConfig
    private Config config;

    public LogbackAutoConfig() {
    }

    public LogbackAutoConfig(Appender consoleAppend) {
        this.consoleAppend = consoleAppend;
    }

    public LogbackAutoConfig(String consoleAppendName) {
        this.consoleAppenderName = consoleAppendName;
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

        this.consoleAppend = loggerContext.getLogger(LOGBACK_CONFIGURATION_ROOT).getAppender(consoleAppenderName);
    }

    public static Appender getRootConsoleAppend() {
        // 获取consoleLog 日志append对象 待返回
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        return loggerContext.getLogger(LOGBACK_CONFIGURATION_ROOT).getAppender(DEFAULT_CONSOLE_APPENDER_NAME);
    }

    @PostConstruct
    public void initRootConsole() {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

        // 获取Apollo配置项：log控制台是否输出
        String flag = config.getProperty(LOG_PRINT_CONSOLE_FLAG, FLAG_TRUE);
        for(Logger log:loggerContext.getLoggerList()) {
            if(consoleAppend==null){
                consoleAppend= loggerContext.getLogger(LOGBACK_CONFIGURATION_ROOT).getAppender(consoleAppenderName);
                break;
            }
        }
            // false 不输出则关闭console
        // false 不输出则关闭console
        Logger root=loggerContext.getLogger("root");
        root.detachAppender(consoleAppenderName);
        if (FLAG_TRUE.equals(flag)  && consoleAppend != null) {
            root.info("logName append console1=" + root.getName());
            root.addAppender(consoleAppend);
            root.info("logName append console2=" + root.getName());

        }

        String custom=config.getProperty(LOG_CUSTOM_LOGGER,"");
        custom(custom);
    }
    public void custom(String custom){
        try {
            LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
            if (StringUtils.isNotBlank(custom)) {
                String[] loggers = StringUtils.split(custom, ",");
                if (ArrayUtils.isNotEmpty(loggers)) {
                    for (String log : loggers) {
                        String[] currentLogs = StringUtils.split(log, "#");
                        if (ArrayUtils.isNotEmpty(loggers) && currentLogs.length == 3) {
                            loggerContext.getLogger(currentLogs[0]).detachAppender(consoleAppenderName);
                            if (FLAG_TRUE.equals(currentLogs[2])) {
                                loggerContext.getLogger(currentLogs[0]).addAppender(consoleAppend);
                            }
                            loggerContext.getLogger(currentLogs[0]).setLevel(Level.valueOf(currentLogs[1].toUpperCase()));
                        }
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @ApolloConfigChangeListener
    public void apolloChange(ConfigChangeEvent changeEvent) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        // 日志级别动态切换
        boolean levelChanged = changeEvent.isChanged(LOG_LEVEL_APOLLO_KEY);
        if (levelChanged) {
            for(Logger log:loggerContext.getLoggerList()) {
                log.setLevel(Level.valueOf(changeEvent.getChange(LOG_LEVEL_APOLLO_KEY).getNewValue().toUpperCase()));
            }
        }

        // log日志控制台输出动态切换
        boolean consoleOutChanged = changeEvent.isChanged(LOG_PRINT_CONSOLE_FLAG);
        if (consoleOutChanged) {
            String flag = changeEvent.getChange(LOG_PRINT_CONSOLE_FLAG).getNewValue();
            // false 不输出则关闭console
            Logger root=loggerContext.getLogger("root");
            root.detachAppender(consoleAppenderName);
            if (FLAG_TRUE.equals(flag)  && consoleAppend != null) {
                root.info("logName append console1=" + root.getName());
                root.addAppender(consoleAppend);
                root.info("logName append console2=" + root.getName());

            }
        }

        boolean customChanged = changeEvent.isChanged(LOG_CUSTOM_LOGGER);
        if (customChanged) {
            String custom=  changeEvent.getChange(LOG_CUSTOM_LOGGER).getNewValue();
            custom(custom);
        }

    }
}
