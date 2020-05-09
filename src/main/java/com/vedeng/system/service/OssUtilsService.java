package com.vedeng.system.service;

import com.vedeng.common.model.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
/**
 * @author Calvin
 * @date created in 2020/3/5 11:49
 */
public interface OssUtilsService {
    /**
     * <b>Description:</b>上传文件到Oss<br>
     * @param
     * @return
     * @Note
     * <b>Author:calvin</b>
     * <br><b>Date:</b> 2020/3/5
     */
    FileInfo upload2Oss(HttpServletRequest request, MultipartFile upfile);
    /**
     * <b>Description:</b>上传流到Oss<br>
     * @param
     * @return
     * @Note
     * <b>Author:calvin</b>
     * <br><b>Date:</b> 2020/3/5
     */
    FileInfo upload2OssForInputStream(String suffix, InputStream inputStream);
}
