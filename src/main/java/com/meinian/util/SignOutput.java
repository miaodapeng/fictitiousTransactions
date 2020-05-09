package com.meinian.util;


import java.util.ArrayList;
import java.util.Collections;

/**
 * <b>Description:</b><br>
 * Sha1加密生成sign
 * @Note <b>Author:</b> Bert <br>
 * <b>Date:</b> 2018/11/10 14:12
 */
public class SignOutput {

    public static String getSign(String appkey,String secret,String timestamp){
        String sign = null;
        //进行字典排序开始
        String str = "";
        ArrayList list = new ArrayList();
        list.add(appkey);
        list.add(secret);
        list.add(timestamp);
        Collections.sort(list);
        for(Object s:list){
            str +=  s;
        }
        //进行字典排序结束

        //进行Sha1加密
        sign = Sha1Input.getSha1(str);
        return sign;
    }
}
