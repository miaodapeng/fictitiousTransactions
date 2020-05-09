package com.vedeng.goods.command;

import com.vedeng.common.controller.BaseCommand;

public class SpuOperateCommand  extends BaseCommand {


    private Integer optType;//1spu  2sku


    public Integer getOptType() {
        return optType;
    }

    public void setOptType(Integer optType) {
        this.optType = optType;
    }
}
