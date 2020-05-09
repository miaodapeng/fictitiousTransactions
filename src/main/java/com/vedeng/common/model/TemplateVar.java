package com.vedeng.common.model;

import java.io.Serializable;

/**
 * @Description: 模板值/颜色
 * @Author: Franlin.wu
 * @Version: V1.0.0
 * @Since: 1.0
 * @Date: 2019/6/18
 */
public class TemplateVar implements Serializable {

    /**
     * 值
     */
    private String value;

    /**
     * 颜色
     */
    private String color;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
