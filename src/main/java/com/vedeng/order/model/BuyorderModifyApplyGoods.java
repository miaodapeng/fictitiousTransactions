package com.vedeng.order.model;

import java.io.Serializable;

public class BuyorderModifyApplyGoods implements Serializable{
    /**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private Integer buyorderModifyApplyGoodsId;

    private Integer buyorderModifyApplyId;

    private Integer buyorderGoodsId;

    private String insideComments;

    private String oldInsideComments;

    public Integer getBuyorderModifyApplyGoodsId() {
        return buyorderModifyApplyGoodsId;
    }

    public void setBuyorderModifyApplyGoodsId(Integer buyorderModifyApplyGoodsId) {
        this.buyorderModifyApplyGoodsId = buyorderModifyApplyGoodsId;
    }

    public Integer getBuyorderModifyApplyId() {
        return buyorderModifyApplyId;
    }

    public void setBuyorderModifyApplyId(Integer buyorderModifyApplyId) {
        this.buyorderModifyApplyId = buyorderModifyApplyId;
    }

    public Integer getBuyorderGoodsId() {
        return buyorderGoodsId;
    }

    public void setBuyorderGoodsId(Integer buyorderGoodsId) {
        this.buyorderGoodsId = buyorderGoodsId;
    }

    public String getInsideComments() {
        return insideComments;
    }

    public void setInsideComments(String insideComments) {
        this.insideComments = insideComments == null ? null : insideComments.trim();
    }

    public String getOldInsideComments() {
        return oldInsideComments;
    }

    public void setOldInsideComments(String oldInsideComments) {
        this.oldInsideComments = oldInsideComments == null ? null : oldInsideComments.trim();
    }
}