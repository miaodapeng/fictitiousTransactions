package com.vedeng.logistics.model.bo;

/**
 *
 * <b>Description:</b><br> 货架业务对象
 * @return
 * @Note
 * <b>Author:</b> Hugo
 * <br><b>Date:</b> 2020年2月15日 下午3:19:01
 */
public class StorageRackBo {

    /**
     * 仓库名
     */
    private String warehouse;

    /**
     * 库房名
     */
    private String storageRoom;

    /**
     * 货区名
     */
    private String storageArea;

    /**
     * 货架名
     */
    private String storageRack;

    /**
     * 货区ID
     */
    private Integer storageAreaId;

    /**
     * 货架备注
     */
    private String comments;

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getStorageRoom() {
        return storageRoom;
    }

    public void setStorageRoom(String storageRoom) {
        this.storageRoom = storageRoom;
    }

    public String getStorageArea() {
        return storageArea;
    }

    public void setStorageArea(String storageArea) {
        this.storageArea = storageArea;
    }

    public String getStorageRack() {
        return storageRack;
    }

    public void setStorageRack(String storageRack) {
        this.storageRack = storageRack;
    }

    public Integer getStorageAreaId() {
        return storageAreaId;
    }

    public void setStorageAreaId(Integer storageAreaId) {
        this.storageAreaId = storageAreaId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
