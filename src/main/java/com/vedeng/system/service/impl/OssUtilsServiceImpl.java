package com.vedeng.system.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.http.NewHttpClientUtils;
import com.vedeng.common.model.FileInfo;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.StringUtil;
import com.vedeng.common.util.UrlUtils;
import com.vedeng.file.api.constants.TerminationType;
import com.vedeng.file.api.request.UploadConstantsMapKey;
import com.vedeng.file.api.util.SignUtil;
import com.vedeng.system.service.OssUtilsService;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;

/**
 * @author Calvin
 * @date created in 2020/3/5 11:49
 */
@Service
public class OssUtilsServiceImpl implements OssUtilsService {

    Logger logger= LoggerFactory.getLogger(OssUtilsServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${oss_http}")
    private String ossHttp;

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
    @Override
    public FileInfo upload2Oss(HttpServletRequest request, MultipartFile upfile) {
        String fileName = upfile.getOriginalFilename();
        // 文件后缀名称
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        try {
            return uploadFile2Oss(request,upfile,suffix);
        }catch (Exception ex){
            logger.error("图片上传OSS失败，请重新上传",ex);
            return new FileInfo(-1,"上传失败，请重新上传");
        }
    }


    private FileInfo uploadFile2Oss(HttpServletRequest request, MultipartFile file, String suffix) throws IOException {

        String time = DateUtil.gainNowDate() + "";
        Random random = new Random();
        time = time + "_" + String.format("%04d", random.nextInt(10000));// 随机数4位，不足4位，补位
        String newFileName=time + "." + suffix;

        long timestamp = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        String authorization = SignUtil.sign(ossKey,(ossAppCode+timestamp).getBytes());
        TypeReference<ResultInfo> resultType = new TypeReference<ResultInfo>() {};
        String reqUrl = ossHttp + ossUrl + ossFilePath;
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        //封装请求body
        builder.addBinaryBody("file",file.getInputStream(), ContentType.MULTIPART_FORM_DATA,file.getOriginalFilename());
        // 类似浏览器表单提交，对应input的name和value
        builder.addTextBody(UploadConstantsMapKey.appCode,ossAppCode);
        builder.addTextBody(UploadConstantsMapKey.Authorization, authorization);
        builder.addTextBody(UploadConstantsMapKey.timestamp, String.valueOf(timestamp));
        builder.addTextBody(UploadConstantsMapKey.deviceInfo,request.getHeader("User-Agent"));
        builder.addTextBody(UploadConstantsMapKey.suffix,suffix);
        builder.addTextBody(UploadConstantsMapKey.previlege, "0");
        builder.addTextBody(UploadConstantsMapKey.termination, TerminationType.PC.getCode());
        //发送请求
        org.apache.http.HttpEntity entity = builder.build();
        HttpPost httpPost = new HttpPost(reqUrl);
        httpPost.setEntity(entity);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpResponse response = httpClient.execute(httpPost);

        //解析返回的结果，获取ossUrl和resourceId
        JSONObject jsonObject = JSONObject.fromObject(EntityUtils.toString(response.getEntity()));
        logger.info("pic upload:"+jsonObject==null?"":jsonObject.toString());
        boolean result = Boolean.parseBoolean(jsonObject.getString("success"));

        if (result) {

            JSONObject data = JSONObject.fromObject(jsonObject.get("data"));
            String ossFileUrl = data.getString("url");
            String resourceId = data.getString("resourceId");
            String[] domainAndUri= UrlUtils.getDomainAndUriFromUrl(ossFileUrl);
            if(domainAndUri!=null&& StringUtil.isNotBlank(domainAndUri[0])&&StringUtil.isNotBlank(domainAndUri[1])) {
                FileInfo fileInfo = new FileInfo(0, "上传成功",newFileName, domainAndUri[1],
                        domainAndUri[0], resourceId, null);
                return fileInfo;
            }

        }
        logger.error("pic upload:"+jsonObject==null?"":jsonObject.toString());
        return new FileInfo(-1,"上传失败，请重新上传");
    }


    @Override
    public FileInfo upload2OssForInputStream(String suffix, InputStream inputStream) {
        String ossTargetUrl=ossHttp + ossUrl+ossFilePath;
        try{

             NewHttpClientUtils.doGet(ossHttp + ossUrl+"/checkpreload.html");
        }catch (Exception e){
            logger.error("",e);
        }
        String time = DateUtil.gainNowDate() + "";
        Random random = new Random();
        time = time + "_" + String.format("%04d", random.nextInt(10000));// 随机数4位，不足4位，补位
        String newFileName=time + "." + suffix;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
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
                return newFileName;
            }

            @Override
            public long contentLength() throws IOException {
                return inputStream.available();
            }
        };
        bodyMap.add("file",resource);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(bodyMap,headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(uriComponentsBuilder.build().encode().toUri(),httpEntity,String.class);



        //解析返回的结果，获取ossUrl和resourceId
        JSONObject jsonObject = JSONObject.fromObject(responseEntity.getBody());
        logger.info("pic upload:"+jsonObject==null?"":jsonObject.toString());
        boolean result = Boolean.parseBoolean(jsonObject.getString("success"));

        if (result) {

            JSONObject data = JSONObject.fromObject(jsonObject.get("data"));
            String ossFileUrl = data.getString("url");
            String resourceId = data.getString("resourceId");
            String[] domainAndUri= UrlUtils.getDomainAndUriFromUrl(ossFileUrl);
            if(domainAndUri!=null&& StringUtil.isNotBlank(domainAndUri[0])&&StringUtil.isNotBlank(domainAndUri[1])) {
                FileInfo fileInfo = new FileInfo(0, "上传成功",newFileName, domainAndUri[1],
                        domainAndUri[0], resourceId, null);
                return fileInfo;
            }

        }
        logger.error("pic upload:"+jsonObject==null?"":jsonObject.toString());
        return new FileInfo(-1,"上传失败，请重新上传");
    }
}
