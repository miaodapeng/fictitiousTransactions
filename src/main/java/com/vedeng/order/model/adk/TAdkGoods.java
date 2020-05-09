package com.vedeng.order.model.adk;

import java.math.BigDecimal;
import java.util.Date;

public class TAdkGoods {
	
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_ADK_GOODS.adk_goods_id
     *
     * @mbg.generated Tue Apr 09 20:42:17 CST 2019
     */
    private Integer adkGoodsId;
    private Integer changeNum=1;

    public BigDecimal getErpSkuPrice() {
        return erpSkuPrice;
    }

    public void setErpSkuPrice(BigDecimal erpSkuPrice) {
        this.erpSkuPrice = erpSkuPrice;
    }

    private BigDecimal erpSkuPrice;
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_ADK_GOODS.adk_goods_code
     *
     * @mbg.generated Tue Apr 09 20:42:17 CST 2019
     */
    private String adkGoodsCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_ADK_GOODS.adk_goods_name
     *
     * @mbg.generated Tue Apr 09 20:42:17 CST 2019
     */
    private String adkGoodsName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_ADK_GOODS.adk_goods_model
     *
     * @mbg.generated Tue Apr 09 20:42:17 CST 2019
     */
    private String adkGoodsModel;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_ADK_GOODS.adk_goods_unit
     *
     * @mbg.generated Tue Apr 09 20:42:17 CST 2019
     */
    private String adkGoodsUnit;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_ADK_GOODS.erp_goods_sku
     *
     * @mbg.generated Tue Apr 09 20:42:17 CST 2019
     */
    private String erpGoodsSku;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_ADK_GOODS.status
     *
     * @mbg.generated Tue Apr 09 20:42:17 CST 2019
     */
    private String status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_ADK_GOODS.erp_goods_id
     *
     * @mbg.generated Tue Apr 09 20:42:17 CST 2019
     */
    private Integer erpGoodsId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_ADK_GOODS.add_no
     *
     * @mbg.generated Tue Apr 09 20:42:17 CST 2019
     */
    private String addNo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_ADK_GOODS.add_name
     *
     * @mbg.generated Tue Apr 09 20:42:17 CST 2019
     */
    private String addName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_ADK_GOODS.add_time
     *
     * @mbg.generated Tue Apr 09 20:42:17 CST 2019
     */
    private Date addTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_ADK_GOODS.update_no
     *
     * @mbg.generated Tue Apr 09 20:42:17 CST 2019
     */
    private String updateNo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_ADK_GOODS.update_name
     *
     * @mbg.generated Tue Apr 09 20:42:17 CST 2019
     */
    private String updateName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_ADK_GOODS.update_time
     *
     * @mbg.generated Tue Apr 09 20:42:17 CST 2019
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_ADK_GOODS.adk_goods_id
     *
     * @return the value of T_ADK_GOODS.adk_goods_id
     *
     * @mbg.generated Tue Apr 09 20:42:17 CST 2019
     */
    public Integer getAdkGoodsId() {
        return adkGoodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_ADK_GOODS.adk_goods_id
     *
     * @param adkGoodsId the value for T_ADK_GOODS.adk_goods_id
     *
     * @mbg.generated Tue Apr 09 20:42:17 CST 2019
     */
    public void setAdkGoodsId(Integer adkGoodsId) {
        this.adkGoodsId = adkGoodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_ADK_GOODS.adk_goods_code
     *
     * @return the value of T_ADK_GOODS.adk_goods_code
     *
     * @mbg.generated Tue Apr 09 20:42:17 CST 2019
     */
    public String getAdkGoodsCode() {
        return adkGoodsCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_ADK_GOODS.adk_goods_code
     *
     * @param adkGoodsCode the value for T_ADK_GOODS.adk_goods_code
     *
     * @mbg.generated Tue Apr 09 20:42:17 CST 2019
     */
    public void setAdkGoodsCode(String adkGoodsCode) {
        this.adkGoodsCode = adkGoodsCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_ADK_GOODS.adk_goods_name
     *
     * @return the value of T_ADK_GOODS.adk_goods_name
     *
     * @mbg.generated Tue Apr 09 20:42:17 CST 2019
     */
    public String getAdkGoodsName() {
        return adkGoodsName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_ADK_GOODS.adk_goods_name
     *
     * @param adkGoodsName the value for T_ADK_GOODS.adk_goods_name
     *
     * @mbg.generated Tue Apr 09 20:42:17 CST 2019
     */
    public void setAdkGoodsName(String adkGoodsName) {
        this.adkGoodsName = adkGoodsName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_ADK_GOODS.adk_goods_model
     *
     * @return the value of T_ADK_GOODS.adk_goods_model
     *
     * @mbg.generated Tue Apr 09 20:42:17 CST 2019
     */
    public String getAdkGoodsModel() {
        return adkGoodsModel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_ADK_GOODS.adk_goods_model
     *
     * @param adkGoodsModel the value for T_ADK_GOODS.adk_goods_model
     *
     * @mbg.generated Tue Apr 09 20:42:17 CST 2019
     */
    public void setAdkGoodsModel(String adkGoodsModel) {
        this.adkGoodsModel = adkGoodsModel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_ADK_GOODS.adk_goods_unit
     *
     * @return the value of T_ADK_GOODS.adk_goods_unit
     *
     * @mbg.generated Tue Apr 09 20:42:17 CST 2019
     */
    public String getAdkGoodsUnit() {
        return adkGoodsUnit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_ADK_GOODS.adk_goods_unit
     *
     * @param adkGoodsUnit the value for T_ADK_GOODS.adk_goods_unit
     *
     * @mbg.generated Tue Apr 09 20:42:17 CST 2019
     */
    public void setAdkGoodsUnit(String adkGoodsUnit) {
        this.adkGoodsUnit = adkGoodsUnit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_ADK_GOODS.erp_goods_sku
     *
     * @return the value of T_ADK_GOODS.erp_goods_sku
     *
     * @mbg.generated Tue Apr 09 20:42:17 CST 2019
     */
    public String getErpGoodsSku() {
        return erpGoodsSku;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_ADK_GOODS.erp_goods_sku
     *
     * @param erpGoodsSku the value for T_ADK_GOODS.erp_goods_sku
     *
     * @mbg.generated Tue Apr 09 20:42:17 CST 2019
     */
    public void setErpGoodsSku(String erpGoodsSku) {
        this.erpGoodsSku = erpGoodsSku;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_ADK_GOODS.status
     *
     * @return the value of T_ADK_GOODS.status
     *
     * @mbg.generated Tue Apr 09 20:42:17 CST 2019
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_ADK_GOODS.status
     *
     * @param status the value for T_ADK_GOODS.status
     *
     * @mbg.generated Tue Apr 09 20:42:17 CST 2019
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_ADK_GOODS.erp_goods_id
     *
     * @return the value of T_ADK_GOODS.erp_goods_id
     *
     * @mbg.generated Tue Apr 09 20:42:17 CST 2019
     */
    public Integer getErpGoodsId() {
        return erpGoodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_ADK_GOODS.erp_goods_id
     *
     * @param erpGoodsId the value for T_ADK_GOODS.erp_goods_id
     *
     * @mbg.generated Tue Apr 09 20:42:17 CST 2019
     */
    public void setErpGoodsId(Integer erpGoodsId) {
        this.erpGoodsId = erpGoodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_ADK_GOODS.add_no
     *
     * @return the value of T_ADK_GOODS.add_no
     *
     * @mbg.generated Tue Apr 09 20:42:17 CST 2019
     */
    public String getAddNo() {
        return addNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_ADK_GOODS.add_no
     *
     * @param addNo the value for T_ADK_GOODS.add_no
     *
     * @mbg.generated Tue Apr 09 20:42:17 CST 2019
     */
    public void setAddNo(String addNo) {
        this.addNo = addNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_ADK_GOODS.add_name
     *
     * @return the value of T_ADK_GOODS.add_name
     *
     * @mbg.generated Tue Apr 09 20:42:17 CST 2019
     */
    public String getAddName() {
        return addName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_ADK_GOODS.add_name
     *
     * @param addName the value for T_ADK_GOODS.add_name
     *
     * @mbg.generated Tue Apr 09 20:42:17 CST 2019
     */
    public void setAddName(String addName) {
        this.addName = addName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_ADK_GOODS.add_time
     *
     * @return the value of T_ADK_GOODS.add_time
     *
     * @mbg.generated Tue Apr 09 20:42:17 CST 2019
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_ADK_GOODS.add_time
     *
     * @param addTime the value for T_ADK_GOODS.add_time
     *
     * @mbg.generated Tue Apr 09 20:42:17 CST 2019
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_ADK_GOODS.update_no
     *
     * @return the value of T_ADK_GOODS.update_no
     *
     * @mbg.generated Tue Apr 09 20:42:17 CST 2019
     */
    public String getUpdateNo() {
        return updateNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_ADK_GOODS.update_no
     *
     * @param updateNo the value for T_ADK_GOODS.update_no
     *
     * @mbg.generated Tue Apr 09 20:42:17 CST 2019
     */
    public void setUpdateNo(String updateNo) {
        this.updateNo = updateNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_ADK_GOODS.update_name
     *
     * @return the value of T_ADK_GOODS.update_name
     *
     * @mbg.generated Tue Apr 09 20:42:17 CST 2019
     */
    public String getUpdateName() {
        return updateName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_ADK_GOODS.update_name
     *
     * @param updateName the value for T_ADK_GOODS.update_name
     *
     * @mbg.generated Tue Apr 09 20:42:17 CST 2019
     */
    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_ADK_GOODS.update_time
     *
     * @return the value of T_ADK_GOODS.update_time
     *
     * @mbg.generated Tue Apr 09 20:42:17 CST 2019
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_ADK_GOODS.update_time
     *
     * @param updateTime the value for T_ADK_GOODS.update_time
     *
     * @mbg.generated Tue Apr 09 20:42:17 CST 2019
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public Integer getChangeNum() {
		return changeNum;
	}

	public void setChangeNum(Integer changeNum) {
		this.changeNum = changeNum;
	}
}