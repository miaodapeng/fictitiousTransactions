package com.vedeng.finance.model;

public class NjBankInterfaceInfoBody {
    //主机流水号
    private String host_serial_no;
    //流水状态(0 等待处理1 交易失败9 交易成功)
    private String stat;

    private String crual_time;

    private String crual_type;

    private String fee_flag;

    public String getHost_serial_no() {
	return host_serial_no;
    }

    public void setHost_serial_no(String host_serial_no) {
	this.host_serial_no = host_serial_no;
    }

    public String getStat() {
	return stat;
    }

    public void setStat(String stat) {
	this.stat = stat;
    }

    public String getCrual_time() {
	return crual_time;
    }

    public void setCrual_time(String crual_time) {
	this.crual_time = crual_time;
    }

    public String getCrual_type() {
	return crual_type;
    }

    public void setCrual_type(String crual_type) {
	this.crual_type = crual_type;
    }

    public String getFee_flag() {
	return fee_flag;
    }

    public void setFee_flag(String fee_flag) {
	this.fee_flag = fee_flag;
    }
    
}