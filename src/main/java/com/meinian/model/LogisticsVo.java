package com.meinian.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 功能描述 封装美年接口所需要的一些数据
 * className
 *
 * @author Bert
 * @date 2018/11/10 17:11
 * @Description //TODO
 * @version: 1.0
 */
@Data
public class LogisticsVo implements Serializable {

    private String  expressNumber;

    @JsonFormat(pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expectedArrivalTime;

    private List<DeliveryOrderDetails> details;

    public String getExpressNumber() {
        return expressNumber;
    }

    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber;
    }

    public Date getExpectedArrivalTime() {
        return expectedArrivalTime;
    }

    public void setExpectedArrivalTime(Date expectedArrivalTime) {
        this.expectedArrivalTime = expectedArrivalTime;
    }

    public List<DeliveryOrderDetails> getDetails() {
        return details;
    }

    public void setDetails(List<DeliveryOrderDetails> details) {
        this.details = details;
    }
}