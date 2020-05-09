package com.vedeng.order.model;

import java.io.Serializable;
import java.util.Date;

public class SaleorderGoodsWarranty implements Serializable{
    private Integer saleorderGoodsWarrantyId;

    private Integer saleorderGoodsId;
    
    private Integer warehouseGoodsOperateLogId;

    private String serviceNo;

    private String goodsSn;

    private String importantPartsSn;
    
    private String distributorName;

    private String terminalName;

    private Integer areaId;

    private String address;

    private String zipCode;

    private String usedDepartment;

    private String personLiable;

    private String phone;

    private String personLiableEquipment;

    private String equipmentPhone;

    private String installAgency;

    private String installPerson;

    private Date checkTime;

    private String checkPerson;

    private String dailyVolume;

    private Integer isDelete;

    private String comments;

    private Long addTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;

    public Integer getSaleorderGoodsWarrantyId() {
        return saleorderGoodsWarrantyId;
    }

    public void setSaleorderGoodsWarrantyId(Integer saleorderGoodsWarrantyId) {
        this.saleorderGoodsWarrantyId = saleorderGoodsWarrantyId;
    }

    public Integer getSaleorderGoodsId() {
        return saleorderGoodsId;
    }

    public void setSaleorderGoodsId(Integer saleorderGoodsId) {
        this.saleorderGoodsId = saleorderGoodsId;
    }

    public String getServiceNo() {
        return serviceNo;
    }

    public void setServiceNo(String serviceNo) {
        this.serviceNo = serviceNo == null ? null : serviceNo.trim();
    }

    public String getGoodsSn() {
        return goodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn == null ? null : goodsSn.trim();
    }

    public String getImportantPartsSn() {
        return importantPartsSn;
    }

    public void setImportantPartsSn(String importantPartsSn) {
        this.importantPartsSn = importantPartsSn == null ? null : importantPartsSn.trim();
    }

    public String getTerminalName() {
        return terminalName;
    }

    public void setTerminalName(String terminalName) {
        this.terminalName = terminalName == null ? null : terminalName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode == null ? null : zipCode.trim();
    }

    public String getUsedDepartment() {
        return usedDepartment;
    }

    public void setUsedDepartment(String usedDepartment) {
        this.usedDepartment = usedDepartment == null ? null : usedDepartment.trim();
    }

    public String getPersonLiable() {
        return personLiable;
    }

    public void setPersonLiable(String personLiable) {
        this.personLiable = personLiable == null ? null : personLiable.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getPersonLiableEquipment() {
        return personLiableEquipment;
    }

    public void setPersonLiableEquipment(String personLiableEquipment) {
        this.personLiableEquipment = personLiableEquipment == null ? null : personLiableEquipment.trim();
    }

    public String getEquipmentPhone() {
        return equipmentPhone;
    }

    public void setEquipmentPhone(String equipmentPhone) {
        this.equipmentPhone = equipmentPhone == null ? null : equipmentPhone.trim();
    }

    public String getInstallAgency() {
        return installAgency;
    }

    public void setInstallAgency(String installAgency) {
        this.installAgency = installAgency == null ? null : installAgency.trim();
    }

    public String getInstallPerson() {
        return installPerson;
    }

    public void setInstallPerson(String installPerson) {
        this.installPerson = installPerson == null ? null : installPerson.trim();
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getCheckPerson() {
        return checkPerson;
    }

    public void setCheckPerson(String checkPerson) {
        this.checkPerson = checkPerson == null ? null : checkPerson.trim();
    }

    public String getDailyVolume() {
        return dailyVolume;
    }

    public void setDailyVolume(String dailyVolume) {
        this.dailyVolume = dailyVolume == null ? null : dailyVolume.trim();
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
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

	public Integer getWarehouseGoodsOperateLogId() {
		return warehouseGoodsOperateLogId;
	}

	public void setWarehouseGoodsOperateLogId(Integer warehouseGoodsOperateLogId) {
		this.warehouseGoodsOperateLogId = warehouseGoodsOperateLogId;
	}

	public String getDistributorName() {
		return distributorName;
	}

	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName == null ? null : distributorName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}
}