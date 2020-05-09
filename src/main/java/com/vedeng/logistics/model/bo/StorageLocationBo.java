package com.vedeng.logistics.model.bo;


/**
 *
 * <b>Description:</b><br> 库位业务对象
 * @return
 * @Note
 * <b>Author:</b> Hugo
 * <br><b>Date:</b> 2020年2月15日 下午3:19:01
 */
public class StorageLocationBo {

    /**
     * 库位名
     */

    private String storageLocation;
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
     * 货架ID
     */
    private Integer storageRackId;

    /**
     * 库位备注
     */
    private String comments;

    public StorageLocationBo() {
    }

    public StorageLocationBo(String storageLocation, String warehouse, String storageRoom, String storageArea, String storageRack, Integer storageRackId, String comments) {
        this.storageLocation = storageLocation;
        this.warehouse = warehouse;
        this.storageRoom = storageRoom;
        this.storageArea = storageArea;
        this.storageRack = storageRack;
        this.storageRackId = storageRackId;
        this.comments = comments;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }

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

    public Integer getStorageRackId() {
        return storageRackId;
    }

    public void setStorageRackId(Integer storageRackId) {
        this.storageRackId = storageRackId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;

    }
}
