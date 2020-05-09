package com.vedeng.trader.model;

import java.io.Serializable;

/**
 * T_WEB_ACCOUNT_CERTIFICATE
 * @author calvin
 */
public class WebAccountCertificate implements Serializable {
    /**
     * 主键
     */
    private Integer webAccountCertificateId;

    /**
     * 注册用户id
     */
    private Integer webAccountId;

    /**
     * 资质类型，1营业执照，2二类医疗资质，3三类医疗资质
     */
    private Integer type;

    /**
     * 证书域名
     */
    private String domain;

    /**
     * 证书文件地址
     */
    private String uri;

    /**
     * OSS的唯一id
     */
    private String ossId;

    /**
     * 添加时间
     */
    private Long addTime;

    /**
     * 更新时间
     */
    private Long updateTime;

    /**
     * 迁移状态，0待迁移，1已迁移
     */
    private Integer status;

    private static final long serialVersionUID = 1L;

    public Integer getWebAccountCertificateId() {
        return webAccountCertificateId;
    }

    public void setWebAccountCertificateId(Integer webAccountCertificateId) {
        this.webAccountCertificateId = webAccountCertificateId;
    }

    public Integer getWebAccountId() {
        return webAccountId;
    }

    public void setWebAccountId(Integer webAccountId) {
        this.webAccountId = webAccountId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getOssId() {
        return ossId;
    }

    public void setOssId(String ossId) {
        this.ossId = ossId;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


}