package com.smallhospital.dto;

import java.util.List;

/**
 * @Author: Daniel
 * @Description: 物流信息类
 * @Date created in 2019/11/21 9:37 上午
 */
public class LogisticsDTO {

    /**
     * 物流消息体
     */
    private String message;

    /**
     * 快递单当前状态，包括0在途，1揽收，2疑难，3签收，4退签，5派件，6退回，7转投等8个状态
     */
    private Integer state;

    /**
     * 通训状态，比如200
     */
    private Integer status;

    /**
     * 快递单明细状态标记，暂未实现，请忽略
     */
    private String condition;

    /**
     * 是否签收标记
     */
    private Integer isCheck;

    /**
     * 快递公司编码,一律用小写字母
     */
    private String com;

    /**
     * 快递单号
     */
    private String nu;

    private List<ProcessItem> data;

    /**
     * 物流过程
     */
    public static class ProcessItem {

        /**
         * 物流轨迹节点内容
         */
        private String context;

        /**
         * 时间
         */
        private String time;

        /**
         * 格式化时间
         */
        private String ftime;

        /**
         * 本数据元对应的签收状态
         */
        private String status;

        /**
         * 本数据元对应的行政区域的编码
         */
        private String areaCode;

        /**
         * 本数据元对应的行政区域的名称
         */
        private String areaName;


        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getFtime() {
            return ftime;
        }

        public void setFtime(String ftime) {
            this.ftime = ftime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        @Override
        public String toString() {
            return "ProcessItem{" +
                    "context='" + context + '\'' +
                    ", time='" + time + '\'' +
                    ", ftime='" + ftime + '\'' +
                    ", status='" + status + '\'' +
                    ", areaCode='" + areaCode + '\'' +
                    ", areaName='" + areaName + '\'' +
                    '}';
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Integer getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(Integer isCheck) {
        this.isCheck = isCheck;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getNu() {
        return nu;
    }

    public void setNu(String nu) {
        this.nu = nu;
    }

    public List<ProcessItem> getData() {
        return data;
    }

    public void setData(List<ProcessItem> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "LogisticsDTO{" +
                "message='" + message + '\'' +
                ", state=" + state +
                ", status=" + status +
                ", condition='" + condition + '\'' +
                ", isCheck=" + isCheck +
                ", com='" + com + '\'' +
                ", nu='" + nu + '\'' +
                ", data=" + data +
                '}';
    }
}
