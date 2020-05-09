package com.smallhospital.dto;


import java.util.List;

/**
 * @Author: Daniel
 * @Description: 小医院出库类
 * @Date created in 2019/11/21 4:18 下午
 */
public class ElWarehouseOutDTO {

    /**
     * 供货单id，也就是订单id
     */
    private String supplyId;

    /**
     * 小医院用户id，T_EL_TRADER中的TRADER_ID
     */
    private String purchaserId;

    /**
     * 采购方名称
     */
    private String purchaserName;

    /**
     * 发货人
     */
    private String deliverMan;

    /**
     * 备注
     */
    private String remark;

    /**
     * 物流单号
     */
    private String logisticsNumber;

    /**
     * 物流公司
     */
    private String logisticsCompany;

    /**
     * 供货单号，也就是订单号
     */
    private String supplyNumber;

    private List<GHDMX> GHDMX;

    public String getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(String supplyId) {
        this.supplyId = supplyId;
    }

    public String getPurchaserId() {
        return purchaserId;
    }

    public void setPurchaserId(String purchaserId) {
        this.purchaserId = purchaserId;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public String getDeliverMan() {
        return deliverMan;
    }

    public void setDeliverMan(String deliverMan) {
        this.deliverMan = deliverMan;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLogisticsNumber() {
        return logisticsNumber;
    }

    public void setLogisticsNumber(String logisticsNumber) {
        this.logisticsNumber = logisticsNumber;
    }

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }

    public String getSupplyNumber() {
        return supplyNumber;
    }

    public void setSupplyNumber(String supplyNumber) {
        this.supplyNumber = supplyNumber;
    }

    public List<ElWarehouseOutDTO.GHDMX> getGHDMX() {
        return GHDMX;
    }

    public void setGHDMX(List<ElWarehouseOutDTO.GHDMX> GHDMX) {
        this.GHDMX = GHDMX;
    }

    public static class GHDMX{

        /**
         * 供货清单id，saleorder_good_id
         */
        private String supplyListId;

        /**
         * 订单明细id，saleorder_id
         */
        private Integer orderListId;

        /**
         * 合同明细id，el_contract_id
         */
        private Integer contractListId;

        /**
         * 产品id，也就是sku的id
         */
        private Integer productId;

        /**
         * 供货数量
         */
        private Integer quantitySupplied;

        /**
         * 备注
         */
        private String remark;

        /**
         * 供货单id
         */
        private String supplyId;

        /**
         * 类别，默认1：供货
         */
        private Integer type;

        /**
         * 产品批次号
         */
        private String productBatchNo;

        /**
         * 批次有效期
         */
        private String batchValidityDate;

        public String getSupplyListId() {
            return supplyListId;
        }

        public void setSupplyListId(String supplyListId) {
            this.supplyListId = supplyListId;
        }

        public Integer getOrderListId() {
            return orderListId;
        }

        public void setOrderListId(Integer orderListId) {
            this.orderListId = orderListId;
        }

        public Integer getContractListId() {
            return contractListId;
        }

        public void setContractListId(Integer contractListId) {
            this.contractListId = contractListId;
        }

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public Integer getQuantitySupplied() {
            return quantitySupplied;
        }

        public void setQuantitySupplied(Integer quantitySupplied) {
            this.quantitySupplied = quantitySupplied;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getSupplyId() {
            return supplyId;
        }

        public void setSupplyId(String supplyId) {
            this.supplyId = supplyId;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getProductBatchNo() {
            return productBatchNo;
        }

        public void setProductBatchNo(String productBatchNo) {
            this.productBatchNo = productBatchNo;
        }

        public String getBatchValidityDate() {
            return batchValidityDate;
        }

        public void setBatchValidityDate(String batchValidityDate) {
            this.batchValidityDate = batchValidityDate;
        }
    }


}
