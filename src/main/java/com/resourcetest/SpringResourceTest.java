package com.resourcetest;

import org.springframework.beans.factory.annotation.Value;

public class SpringResourceTest {

    @Value("${http_url}")
    protected String httpUrl;

    @Value("${client_id}")
    protected String clientId;

    @Value("${client_key}")
    protected String clientKey;

    @Value("${file_url}")
    protected String picUrl;

    @Value("${redis_dbtype}")
    protected String dbType;


//        Resource resource = new ClassPathResource("api/dbcenter.properties");
//        Properties p=new Properties();
//        try {
//            p.load(new FileInputStream(resource.getFile()));
//        } catch (IOException e) {
//            logger.error(Contant.ERROR_MSG, e);
//        }
//        System.out.printf(p.getProperty("http_url"));


}
