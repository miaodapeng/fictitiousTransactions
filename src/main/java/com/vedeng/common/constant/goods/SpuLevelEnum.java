package com.vedeng.common.constant.goods;

public enum SpuLevelEnum {
	TEMP(2, "临时商品"), CORE(1, "核心商品"), OTHER(0, "其他商品");
	private Integer spuLevel;
	private String levelName;

	private SpuLevelEnum(int spuLevel, String levelName) {
		this.spuLevel = spuLevel;
		this.levelName = levelName;
	}

	public int spuLevel() {
		return spuLevel;
	}

	public static String levelName(Integer level) {
		for (SpuLevelEnum e : values()) {
			if (e.spuLevel.equals(level)) {
				return e.levelName;
			}
		}
		return "";
	}
	public static boolean isTempSpu(Integer spuLevel){
		return TEMP.spuLevel.equals(spuLevel);
	}


}
