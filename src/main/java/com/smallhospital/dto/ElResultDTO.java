package com.smallhospital.dto;

/**
 * @Author: Daniel
 * @Description: 小医院系统接口交互结果类
 * @Date created in 2019/11/20 7:11 下午
 */
public class ElResultDTO<T> {

    private String status;

    private String code;

    private String message;

    private Integer total;

    private T data;

    public static ElResultDTO ok(){
        ElResultDTO dto = new ElResultDTO();
        dto.setCode("200");
        dto.setStatus(ElRequestStatus.SUCCESS);
        return dto;
    }

    public static <T> ElResultDTO ok(T data){
        ElResultDTO dto = new ElResultDTO();
        dto.setCode("200");
        dto.setStatus(ElRequestStatus.SUCCESS);
        dto.setData(data);
        return dto;
    }

    public static ElResultDTO error(String message){
        ElResultDTO dto = new ElResultDTO();
        dto.setStatus(ElRequestStatus.ERROR);
        dto.setCode("400");
        dto.setMessage(message);
        return dto;
    }

    public static ElResultDTO errorInternal(String message){
        ElResultDTO dto = new ElResultDTO();
        dto.setStatus(ElRequestStatus.ERROR);
        dto.setCode("500");
        dto.setMessage(message);
        return dto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "ElResultDTO{" +
                "status='" + status + '\'' +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
