package com.vedeng.common.constant.goods;

public enum GoodsCheckStatusEnum {

	NEW(0, "待完善"), PRE(1, "审核中"), APPROVE(3, "审核通过"), REJECT(2, "审核不通过")
	, DELETE(4, "删除");
	private Integer status;
	private String name;

	private GoodsCheckStatusEnum(int status, String name) {
		this.status = status;
		this.name = name;
	}

	public static boolean isApprove(Integer status) {
		return status == null ? false : status.intValue() == APPROVE.status;
	}

	public static boolean isPre(Integer status) {
		return status == null ? false : status.intValue() == PRE.status;
	}
	public static boolean isNew(Integer status) {
		return status == null ? false : status.intValue() == NEW.status;
	}


	public static boolean isReject(Integer status) {
		return status == null ? false : status.intValue() == REJECT.status;
	}
	//0审核中1审核通过2审核不通过
	static int [] t={0,0,2,1,0};
    public static Integer transToOld(Integer checkStatus) {
		if(checkStatus!=null&&checkStatus<5){
			return t[checkStatus];
		}
		return 0;
    }

    public String getName() {
		return name;
	}

	public int getStatus() {
		return status;
	}

	public static String statusName(Integer status) {
		for (GoodsCheckStatusEnum e : values()) {
			if (e.status.equals(status)) {
				return e.name;
			}
		}
		return "";
	}

}
