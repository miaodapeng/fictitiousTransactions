package com.smallhospital.service.impl.remote;

import com.alibaba.fastjson.JSONObject;

public interface ELThirdService<T> {

    boolean syncData(T t);
    
}
