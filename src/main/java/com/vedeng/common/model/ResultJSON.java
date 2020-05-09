package com.vedeng.common.model;

import com.vedeng.common.exception.ShowErrorMsgException;
import com.vedeng.common.page.Page;

import java.util.HashMap;
import java.util.Map;

public class ResultJSON   {

    private int code = 0;//0成功，
    private boolean success=true;

    private String message;
    private Object data;

    public static ResultJSON success(){
        return new ResultJSON();
    }
    public static ResultJSON failed(){
        ResultJSON json= new ResultJSON();
        json.setSuccess(false);
        json.setCode(-1);
        return json;
    }

    public static ResultJSON failed(Exception e) {
        if(e instanceof ShowErrorMsgException){
          return  failed().message(((ShowErrorMsgException) e).getErrorMsg());
        }else{
            return failed().message(e.getMessage());
        }
    }

    public   ResultJSON code( int code){
        setCode(code);
        return this;
    }
    public   ResultJSON message( String msg){
        setMessage(msg);
        return this;
    }
    public   ResultJSON data( Object data){
        setData(data);
        return this;
    }
    public   ResultJSON dataWithPage(Object data, Page page){
        Map<String,Object> map=new HashMap<>();
        map.put("list",data);
        map.put("page",page);
        setData(map);
        return this;
    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
