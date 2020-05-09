package com.vedeng.goods.model;

import java.util.Date;

/**
 * T_PLATFROM
 */
public class Platfrom {
    /**
     * <pre>
     * 平台id
     * 表字段 : T_PLATFROM.PLATFROM_ID
     * </pre>
     *
     */
    private Integer platfromId;

    /**
     * 平台id
     * @return PLATFROM_ID 平台id
     */
    public Integer getPlatfromId() {
        return platfromId;
    }

    /**
     * 平台id
     * @param platfromId 平台id
     */
    public void setPlatfromId(Integer platfromId) {
        this.platfromId = platfromId;
    }
    /**
     * <pre>
     * 平台名称
     * 表字段 : T_PLATFROM.PLATFROM_NAME
     * </pre>
     * 
     */
    private String platfromName;

    /**
     * <pre>
     * 平台备注
     * 表字段 : T_PLATFROM.PLATFROM_REMARK
     * </pre>
     * 
     */
    private String platfromRemark;

    /**
     * <pre>
     * 创建人
     * 表字段 : T_PLATFROM.CREATOR
     * </pre>
     * 
     */
    private Integer creator;

    /**
     * <pre>
     * 修改时间
     * 表字段 : T_PLATFROM.UPDATE_TIME
     * </pre>
     * 
     */
    private Date updateTime;

    /**
     * <pre>
     * 创建时间
     * 表字段 : T_PLATFROM.CREATE_TIME
     * </pre>
     * 
     */
    private Date createTime;

    /**
     * <pre>
     * 更新人
     * 表字段 : T_PLATFROM.UPDATER
     * </pre>
     * 
     */
    private Integer updater;

    /**
     * <pre>
     * 删除标记（0否1是）
     * 表字段 : T_PLATFROM.IS_DEL
     * </pre>
     * 
     */
    private Integer isDel;

    /**
     * 平台名称
     * @return PLATFROM_NAME 平台名称
     */
    public String getPlatfromName() {
        return platfromName;
    }

    /**
     * 平台名称
     * @param platfromName 平台名称
     */
    public void setPlatfromName(String platfromName) {
        this.platfromName = platfromName == null ? null : platfromName.trim();
    }

    /**
     * 平台备注
     * @return PLATFROM_REMARK 平台备注
     */
    public String getPlatfromRemark() {
        return platfromRemark;
    }

    /**
     * 平台备注
     * @param platfromRemark 平台备注
     */
    public void setPlatfromRemark(String platfromRemark) {
        this.platfromRemark = platfromRemark == null ? null : platfromRemark.trim();
    }

    /**
     * 创建人
     * @return CREATOR 创建人
     */
    public Integer getCreator() {
        return creator;
    }

    /**
     * 创建人
     * @param creator 创建人
     */
    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    /**
     * 修改时间
     * @return UPDATE_TIME 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 修改时间
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 创建时间
     * @return CREATE_TIME 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 更新人
     * @return UPDATER 更新人
     */
    public Integer getUpdater() {
        return updater;
    }

    /**
     * 更新人
     * @param updater 更新人
     */
    public void setUpdater(Integer updater) {
        this.updater = updater;
    }

    /**
     * 删除标记（0否1是）
     * @return IS_DEL 删除标记（0否1是）
     */
    public Integer getIsDel() {
        return isDel;
    }

    /**
     * 删除标记（0否1是）
     * @param isDel 删除标记（0否1是）
     */
    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}