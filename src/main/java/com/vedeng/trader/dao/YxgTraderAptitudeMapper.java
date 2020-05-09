package com.vedeng.trader.dao;

import com.newtask.model.YXGTraderAptitude;

import java.util.List;
import java.util.Map;

public interface YxgTraderAptitudeMapper {
    List<YXGTraderAptitude> getAptitudeListPage(Map<String,Object> params);
}
