package com.vedeng.system.model.vo;

/**
 * @ClassName AccountSalerToGo
 * @Description 推送数据到gomanager的类
 * @Author Cooper.xu
 * @Date 2019-07-23 19:40
 * @Version 1.0
 **/
public class AccountSalerToGo {

    /**
     * 客户ID
     */
    private Integer traderId;

    /**
     * 归属销售ID
     */
    private Integer userId;

    /**
     * 归属销售名称
     */
    private String userName;

    public Integer getTraderId() {
        return traderId;
    }

    public void setTraderId(Integer traderId) {
        this.traderId = traderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
