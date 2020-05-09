package com.vedeng.system.dao;

import java.util.List;
import java.util.Map;

public interface ProcinstMapper {

    List<String> getBuyOrderIdsByCurrentOperateUser(Map<String, Object> map);

    List<String> getBuyOrderIdsByProcessIds(Map<String, Object> processIds);
}
