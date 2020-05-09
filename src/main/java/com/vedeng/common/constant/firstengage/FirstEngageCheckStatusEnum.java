package com.vedeng.common.constant.firstengage;

public enum FirstEngageCheckStatusEnum {
	NEW(0, "待完善"), PRE(1, "审核中"), APPROVE(3, "审核通过"), REJECT(2, "审核不通过")
	, DELETE(4, "删除");
	private Integer status;
	private String name;

	private FirstEngageCheckStatusEnum(int status, String name) {
		this.status = status;
		this.name = name;
	}

	public static boolean isApprove(Integer status) {
		return status == null ? false : status == APPROVE.status;
	}

	public String getName() {
		return name;
	}

	public int getStatus() {
		return status;
	}

	public static String statusName(Integer status) {
		for (FirstEngageCheckStatusEnum e : values()) {
			if (e.status.equals(status)) {
				return e.name;
			}
		}
		return "";
	}

}
