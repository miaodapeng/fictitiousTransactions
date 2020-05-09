package com.smallhospital.dto;

/**
 * 小医院售后申请审批结果
 * @author Daniel
 * @date created in 2020/1/19 10:17
 */
public class ElAfterSaleApprovalResultDto {

    /**
     * 医流网订单清单id
     */
    private Integer orderListId;

    /**
     * 审批结果，1确认，0驳回
     */
    private Integer status;

    public Integer getOrderListId() {
        return orderListId;
    }

    public void setOrderListId(Integer orderListId) {
        this.orderListId = orderListId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ElAfterSaleApprovalResultDto(Integer orderListId, Integer status) {
        this.orderListId = orderListId;
        this.status = status;
    }

    public ElAfterSaleApprovalResultDto() {
    }

    @Override
    public String toString() {
        return "ElAfterSaleApprovalResultDto{" +
                "orderListId=" + orderListId +
                ", status=" + status +
                '}';
    }
}
