package com.vedeng.order.model.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * 前台通过MQ推送的商机消息体
 * @author Daniel
 * @date created in 2020/3/27 10:31
 */
public class BusinessChanceMqDto {

    /**
     * 商机来源
     */
    private Integer source;

    /**
     * 咨询入口：咨询入口：首页（1）、商品详情页（2），搜索结果页（3）、产品导航页（4）、品牌中心（5）、页头（6）、侧边功能栏（7）、专题模板（8）、器械数据库商品详情页（9）
     */
    private Integer entrances;

    /**
     * 功能，立即询价（1）、立即订购（2）、商品咨询（3）、为您找货（4）
     */
    private Integer functions;

    /**
     * 附件，oss地址
     */
    private String attachment;

    /**
     * 咨询商品名称
     */
    private String goodsName;

    /**
     * 咨询内容
     */
    private String content;

    private String traderName;

    /**
     * 联系人
     */
    private String checkTraderContactName;

    /**
     * 联系方式
     */
    private String checkTraderContactMobile;

    /**
     * 商机创建时间
     */
    private Long addTime;

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getEntrances() {
        return entrances;
    }

    public void setEntrances(Integer entrances) {
        this.entrances = entrances;
    }

    public Integer getFunctions() {
        return functions;
    }

    public void setFunctions(Integer functions) {
        this.functions = functions;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTraderName() {
        return traderName;
    }

    public void setTraderName(String traderName) {
        this.traderName = traderName;
    }

    public String getCheckTraderContactName() {
        return checkTraderContactName;
    }

    public void setCheckTraderContactName(String checkTraderContactName) {
        this.checkTraderContactName = checkTraderContactName;
    }

    public String getCheckTraderContactMobile() {
        return checkTraderContactMobile;
    }

    public void setCheckTraderContactMobile(String checkTraderContactMobile) {
        this.checkTraderContactMobile = checkTraderContactMobile;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }
}
