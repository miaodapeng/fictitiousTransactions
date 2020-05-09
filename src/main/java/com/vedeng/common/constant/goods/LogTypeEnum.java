package com.vedeng.common.constant.goods;

public enum LogTypeEnum {
    SPU(1),SKU(2),FIRSTENGAGE(3);

    private int j;

    private LogTypeEnum(int i){
        j=i;
    }
    public int getLogType(){
        return j;
    }

}
