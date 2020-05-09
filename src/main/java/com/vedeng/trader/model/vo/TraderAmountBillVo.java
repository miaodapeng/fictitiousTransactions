package com.vedeng.trader.model.vo;

import com.vedeng.trader.model.TraderAmountBill;

/**
 * <b>Description:</b><br> 账期vo
 * @author east
 * @Note
 * <b>ProjectName:</b> dbcenter
 * <br><b>PackageName:</b> com.vedeng.model.trader.vo
 * <br><b>ClassName:</b> TraderAmountBillVo
 * <br><b>Date:</b> 2017年5月31日 上午10:53:18
 */
public class TraderAmountBillVo extends TraderAmountBill {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	
	private String opEvent;//操作事项
	private String creatorUser;//操作人

	public String getOpEvent() {
		return opEvent;
	}

	public void setOpEvent(String opEvent) {
		this.opEvent = opEvent;
	}

	public String getCreatorUser() {
		return creatorUser;
	}

	public void setCreatorUser(String creatorUser) {
		this.creatorUser = creatorUser;
	}
	

}
