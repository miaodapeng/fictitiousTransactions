package com.smallhospital.dto;

/**
 * 小医院退货单审批传输类
 */
public class ElAfterSaleApprovalDTO {

    /**
     * 小医院退货单id
     */
    private String returnId;

    /**
     * 审批结果，0：关闭订单，1：同意
     */
    private Integer status;

    /**
     * 拒接理由
     */
    private String message;

    public String getReturnId() {
        return returnId;
    }

    public void setReturnId(String returnId) {
        this.returnId = returnId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ElAfterSaleApprovalDTO(String returnId, Integer status) {
        this.returnId = returnId;
        this.status = status;
    }

    public ElAfterSaleApprovalDTO() {
    }

    @Override
    public String toString() {
        return "ElAfterSaleApprovalDTO{" +
                "returnId='" + returnId + '\'' +
                ", status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
