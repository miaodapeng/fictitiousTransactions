package com.vedeng.common.model;

import java.io.Serializable;

/**
 * @Description: 模板变量
 * @Author: Franlin.wu
 * @Version: V1.0.0
 * @Since: 1.0
 * @Date: 2019/6/17
 */
public class TemplateVariable implements Serializable {

    /**
     * 头
     */
    private TemplateVar first;

    /**
     * 变量1
     */
    private TemplateVar keyword1;

    /**
     * 变量2
     */
    private TemplateVar keyword2;

    /**
     * 变量3
     */
    private TemplateVar keyword3;

    /**
     * 变量4
     */
    private TemplateVar keyword4;


    /**
     * 变量5
     */
    private TemplateVar keyword5;


    /**
     * 变量6
     */
    private TemplateVar keyword6;

    /**
     * 尾部
     */
    private TemplateVar remark;


    public TemplateVar getFirst() {
        return first;
    }

    public void setFirst(TemplateVar first) {
        this.first = first;
    }

    public TemplateVar getKeyword1() {
        return keyword1;
    }

    public void setKeyword1(TemplateVar keyword1) {
        this.keyword1 = keyword1;
    }

    public TemplateVar getKeyword2() {
        return keyword2;
    }

    public void setKeyword2(TemplateVar keyword2) {
        this.keyword2 = keyword2;
    }

    public TemplateVar getKeyword3() {
        return keyword3;
    }

    public void setKeyword3(TemplateVar keyword3) {
        this.keyword3 = keyword3;
    }

    public TemplateVar getKeyword4() {
        return keyword4;
    }

    public void setKeyword4(TemplateVar keyword4) {
        this.keyword4 = keyword4;
    }

    public TemplateVar getKeyword5() {
        return keyword5;
    }

    public void setKeyword5(TemplateVar keyword5) {
        this.keyword5 = keyword5;
    }

    public TemplateVar getKeyword6() {
        return keyword6;
    }

    public void setKeyword6(TemplateVar keyword6) {
        this.keyword6 = keyword6;
    }

    public TemplateVar getRemark() {
        return remark;
    }

    public void setRemark(TemplateVar remark) {
        this.remark = remark;
    }
}
