package com.newtask;

import com.xxl.job.core.handler.IJobHandler;
import org.springframework.beans.factory.annotation.Value;

public abstract class BaseHandler extends IJobHandler {
    @Value("${http_url}")
    protected String httpUrl;

    @Value("${client_id}")
    protected String clientId;

    @Value("${client_key}")
    protected String clientKey;
}
