package com.vedeng.common.util;

import java.net.URL;

public class UrlUtils {

    /**
     * <b>Description:</b>从URL中获取主机和路径<br>
     * @param
     * @return
     * @Note
     * <b>Author:calvin</b>
     * <br><b>Date:</b> 2020/3/5
     */
    public static String[] getDomainAndUriFromUrl(String pic){
        if(StringUtil.isBlank(pic)){
            return null;
        }
        String[] urlArray=new String[2];
        try {
            URL url = new URL(pic);
            String host=url.getHost();
            String path=url.getPath();
            String param=url.getQuery();
            urlArray[0]=host;
            urlArray[1]=path+"?"+param;
            return urlArray;
        }catch (Exception ex){}
        return null;
    }

    public static void main(String arg[]){

        String url="https://file.vedeng.com/file/display?resourceId=64310009ec1653ba20ee57b072588572";
        String[] array=getDomainAndUriFromUrl(url);
        System.out.println(array[0]+"||"+array[1]);
    }
}
