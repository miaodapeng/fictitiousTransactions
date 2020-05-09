package com.newtask.util;

import com.vedeng.file.api.constants.TerminationType;
import com.vedeng.file.api.util.SignUtil;
import com.vedeng.trader.dao.TraderCertificateMapper;
import com.vedeng.trader.model.TraderCertificate;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * @author Daniel
 * @date created in 2020/2/28 11:49
 */
@Service("traderCertificate2OssUtil")
public class TraderCertificate2OssUtil {

    public static Logger LOGGER = LoggerFactory.getLogger(TraderCertificate2OssUtil.class);

    @Autowired
    private RestTemplate restTemplate;

    /**
     * OSS地址
     */
    @Value("${oss_url}")
    private String ossUrl;
    /**
     * oss秘钥
     */
    @Value("${oss_key}")
    private String ossKey;
    /**
     * oss应用码
     */
    @Value("${oss_app_code}")
    private String ossAppCode;
    /**
     * oss文档路径
     */
    @Value("${oss_file_path}")
    private String ossFilePath;

    @Autowired
    private TraderCertificateMapper traderCertificateMapper;

    public void downloadFileByStream(TraderCertificate certificate){
        String url = "http://" + certificate.getDomain() + certificate.getUri();

        try {
            RequestCallback requestCallback = request -> request.getHeaders().setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM, MediaType.ALL));
            ResponseExtractor<Void> responseExtractor = response -> {
                //发送到oss
                sendFile2Oss(certificate.getTraderCertificateId(),url,"http://"+ossUrl+ossFilePath,response.getBody());
                return null;
            };
            restTemplate.execute(url, HttpMethod.GET,requestCallback,responseExtractor);
        } catch (Exception e){
            LOGGER.error("迁移文件:{}到OSS失败：",url,e);
        }
    }

    public void sendFile2Oss(Integer traderCertificateId, String fileSourceUrl, String ossTargetUrl, InputStream inputStream){
        Instant start = Instant.now();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        String[] urlArray = fileSourceUrl.split("\\.");
        String suffix = urlArray[urlArray.length-1];
        long timestamp = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        queryParams.add("appCode",ossAppCode);
        String authorization = SignUtil.sign(ossKey,(ossAppCode+timestamp).getBytes());
        queryParams.add("Authorization",authorization);
        queryParams.add("timestamp",String.valueOf(timestamp));
        queryParams.add("deviceInfo","pc");
        queryParams.add("sourceId","erp");
        queryParams.add("suffix",suffix);
        queryParams.add("previlege","0");
        queryParams.add("termination", TerminationType.PC.getCode());
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(ossTargetUrl)
                .queryParams(queryParams);

        MultiValueMap<String,Object> bodyMap = new LinkedMultiValueMap<>();
        Resource resource = new InputStreamResource(inputStream){

            @Override
            public String getFilename(){
                return "tmp";
            }

            @Override
            public long contentLength() throws IOException {
                return inputStream.available();
            }
        };
        bodyMap.add("file",resource);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(bodyMap,headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(uriComponentsBuilder.build().encode().toUri(),httpEntity,String.class);

        LOGGER.info("迁移文件到OSS，响应结果：{}",responseEntity.getBody());

        //解析返回的结果，获取ossUrl和resourceId
        JSONObject jsonObject = JSONObject.fromObject(responseEntity.getBody());
        boolean result = Boolean.parseBoolean(jsonObject.getString("success"));

        if (result){
            LOGGER.info("成功迁移文件{}到OSS，耗时:{}，响应结果：{}",fileSourceUrl,ChronoUnit.MILLIS.between(start, Instant.now()),responseEntity.getBody());
            JSONObject data = JSONObject.fromObject(jsonObject.get("data"));
            String ossFileUrl = data.getString("url");
            String resourceId = data.getString("resourceId");
            updateTraderCertificate(traderCertificateId,fileSourceUrl,ossFileUrl,resourceId);
        }

    }


    private void updateTraderCertificate(Integer traderCertificateId,String fileSourceUrl, String ossFileUrl, String ossResourceId){
        if (!ossFileUrl.startsWith("http://")){
            LOGGER.error("迁移文件：{}，OSS响应的结果有误：{}",fileSourceUrl,ossFileUrl);
        }
        String domainAndUri = ossFileUrl.substring(8);
        int domainIndex = domainAndUri.indexOf("/");
        String domain = domainAndUri.substring(0,domainIndex);
        String uri = domainAndUri.substring(domainIndex);

        //更新uri、domain、resourceId
        TraderCertificate toUpdateCertificate = new TraderCertificate();
        toUpdateCertificate.setTraderCertificateId(traderCertificateId);
        toUpdateCertificate.setDomain(domain);
        toUpdateCertificate.setUri(uri);
        toUpdateCertificate.setOssResourceId(ossResourceId);
        traderCertificateMapper.updateTraderCertificate(toUpdateCertificate);

    }
}
