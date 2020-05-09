package com.smallhospital.service.impl;

import java.util.HashMap;
import java.util.Map;

public class CreateSaleOrderContext {

    public static ThreadLocal<Map<String,Object>> context = new ThreadLocal<>();

    public static void put(String key,Object value){
        Map<String,Object> contextMap = context.get();
        if(contextMap == null){
            synchronized (context){
                contextMap = context.get();
                if(contextMap == null){
                    contextMap = new HashMap<>();
                    context.set(contextMap);
                }
            }
        }
        contextMap.put(key,value);
    }

    public static Object get(String key){
        return context.get().get(key);
    }

}
