package com.vedeng.goods.model.vo;

import com.vedeng.goods.model.SetMealSkuMapping;

import java.util.List;
import java.util.Map;

public class SetMealSkuMappingVo extends SetMealSkuMapping {

    /** 科室名称拼接字符串，以英文逗号隔开  DEPARTMENT_NAMES **/
    private String departmentNames;

    /** 商品名称 SKU_NAME**/
    private String skuName;

    /** 科室ID  DEPARTMENT_ID **/
    private Integer departmentId;

    /** 科室名称  DEPARTMENT_NAME **/
    private String departmentName;

    /** 自身转化为json串  OBJECT_JSON **/
    private String objectJson;

    /** SKU商品单位ID  SKU_UNIT_NAME **/
    private String skuUnitName;

    /** 商品所有绑定的科室ID及名称  DEPARTMENTS **/
    private List<Map<String,Object>> departments;

    /** 商品启用状态  STATUS **/
    private Integer status;

    /** 商品审核状态  CHECK_STATUS **/
    private Integer checkStatus;

    public String getDepartmentNames() {
        return departmentNames;
    }

    public void setDepartmentNames(String departmentNames) {
        this.departmentNames = departmentNames;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getObjectJson() {
        return objectJson;
    }

    public void setObjectJson(String objectJson) {
        this.objectJson = objectJson;
    }

    public String getSkuUnitName() {
        return skuUnitName;
    }

    public void setSkuUnitName(String skuUnitName) {
        this.skuUnitName = skuUnitName;
    }

    public List<Map<String, Object>> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Map<String, Object>> departments) {
        this.departments = departments;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }
}
