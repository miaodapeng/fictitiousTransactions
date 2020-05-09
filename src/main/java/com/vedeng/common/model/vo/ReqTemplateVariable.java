package com.vedeng.common.model.vo;


import com.vedeng.common.model.TemplateVariable;

import java.io.Serializable;

/**
 * @Description: 请求
 * @Author: Franlin.wu
 * @Version: V1.0.0
 * @Since: 1.0
 * @Date: 2019/6/18
 */
public class ReqTemplateVariable extends TemplateVariable implements Serializable {

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 数字字典中模板的主键ID
     */
    private Integer templateId;


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }
}
