package com.task;

import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Value;

public class BaseTaskController {
	
	//ResourceBundle resource = ResourceBundle.getBundle("api/dbcenter"); 

	@Value("${http_url}")
	public String httpUrl;
	
	@Value("${client_id}")
	public String clientId;
	
	@Value("${client_key}")
	public String clientKey;
	
	@Value("${email_protocol}")
	public String emailProtocol;
	
	@Value("${email_host}")
	public String emailHost;
	
	@Value("${email_username}")
	public String emailUsername;
	
	@Value("${email_password}")
	public String emailPassword;
	
	@Value("${sms_account}")
	public String account;
	
	@Value("${sms_password}")
	public String password;
	
	@Value("${sms_veryCode}")
	public String veryCode;
	
	@Value("${sms_http_url}")
	public String http_url;
	
	
}
