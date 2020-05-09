package com.vedeng.authorization.model;

import java.io.Serializable;

/**
 * @Author wayne.liu
 * @Description 平台model
 * @Date 2019/6/6 9:36
 */
public class Platform implements Serializable{

    private static final long serialVersionUID = 5082426291689701397L;

    private Integer platformId;

    private String platformName;

    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    @Override
    public String toString() {
        return "Platform{" +
                "platformId=" + platformId +
                ", platformName='" + platformName + '\'' +
                '}';
    }
}