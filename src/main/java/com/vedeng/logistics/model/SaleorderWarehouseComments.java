package com.vedeng.logistics.model;

public class SaleorderWarehouseComments {
    private Integer saleorderWarehouseCommentsId;

    private Integer saleorderId;

    private String comments;

    private Integer isDelete;

    private Long addTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;
    
    private String opterName;
    
    public String getOpterName() {
        return opterName;
    }

    public void setOpterName(String opterName) {
        this.opterName = opterName;
    }

    public Integer getSaleorderWarehouseCommentsId() {
        return saleorderWarehouseCommentsId;
    }

    public void setSaleorderWarehouseCommentsId(Integer saleorderWarehouseCommentsId) {
        this.saleorderWarehouseCommentsId = saleorderWarehouseCommentsId;
    }

    public Integer getSaleorderId() {
        return saleorderId;
    }

    public void setSaleorderId(Integer saleorderId) {
        this.saleorderId = saleorderId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Long getModTime() {
        return modTime;
    }

    public void setModTime(Long modTime) {
        this.modTime = modTime;
    }

    public Integer getUpdater() {
        return updater;
    }

    public void setUpdater(Integer updater) {
        this.updater = updater;
    }
}