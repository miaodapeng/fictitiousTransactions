package com.vedeng.goods.model;

import java.io.Serializable;

public class GoodsAttachment implements Serializable{
    private Integer goodsAttachmentId;

    private Integer goodsId;

    private Integer attachmentType;

    private String domain;

    private String uri;

    private String alt;

    private Integer sort;

    private Integer isDefault;

    private Integer status;

    public Integer getGoodsAttachmentId() {
        return goodsAttachmentId;
    }

    public void setGoodsAttachmentId(Integer goodsAttachmentId) {
        this.goodsAttachmentId = goodsAttachmentId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(Integer attachmentType) {
        this.attachmentType = attachmentType;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain == null ? null : domain.trim();
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri == null ? null : uri.trim();
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt == null ? null : alt.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}