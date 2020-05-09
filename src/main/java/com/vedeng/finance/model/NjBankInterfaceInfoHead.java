package com.vedeng.finance.model;

public class NjBankInterfaceInfoHead {
    //交易码
    private String tr_code;
    //现金管理客户号(企业不需要赋值，由银企前置机进行赋值)
    private String cms_corp_no;
    //请求号(企业erp的流水号)
    private String req_no;
    //交易流水号(由现金管理系统产生的流水号)
    private String serial_no;
    //交易日期(格式yyyymmdd)
    private String tr_acdt;
    //交易时间(格式hhmmss)
    private String tr_time;
    //返回码(0000表示成功，正常返回)
    private String ret_code;
    //返回信息
    private String ret_info;
    //成功标识(0-表示成功 8-表示结果未知 其它－交易失败)
    private String succ_flag;
    //返回附加信息
    private String ext_info;
    //文件标识(传送0-报文1-文件,或为空)
    private String file_flag;
    //保留字段
    private String reserved;
    //用户号(企业不需要赋值，由银企前置机进行赋值)
    private String user_no;
    //机构号(企业不需要赋值，由银企前置机进行赋值)
    private String org_code;
    
    private String jnl_stat;
    
    public String getTr_code() {
	return tr_code;
    }
    public void setTr_code(String tr_code) {
	this.tr_code = tr_code;
    }
    public String getCms_corp_no() {
	return cms_corp_no;
    }
    public void setCms_corp_no(String cms_corp_no) {
	this.cms_corp_no = cms_corp_no;
    }
    public String getReq_no() {
	return req_no;
    }
    public void setReq_no(String req_no) {
	this.req_no = req_no;
    }
    public String getSerial_no() {
	return serial_no;
    }
    public void setSerial_no(String serial_no) {
	this.serial_no = serial_no;
    }
    public String getTr_acdt() {
	return tr_acdt;
    }
    public void setTr_acdt(String tr_acdt) {
	this.tr_acdt = tr_acdt;
    }
    public String getTr_time() {
	return tr_time;
    }
    public void setTr_time(String tr_time) {
	this.tr_time = tr_time;
    }
    public String getRet_code() {
	return ret_code;
    }
    public void setRet_code(String ret_code) {
	this.ret_code = ret_code;
    }
    public String getRet_info() {
	return ret_info;
    }
    public void setRet_info(String ret_info) {
	this.ret_info = ret_info;
    }
    public String getSucc_flag() {
	return succ_flag;
    }
    public void setSucc_flag(String succ_flag) {
	this.succ_flag = succ_flag;
    }
    public String getExt_info() {
	return ext_info;
    }
    public void setExt_info(String ext_info) {
	this.ext_info = ext_info;
    }
    public String getFile_flag() {
	return file_flag;
    }
    public void setFile_flag(String file_flag) {
	this.file_flag = file_flag;
    }
    public String getReserved() {
	return reserved;
    }
    public void setReserved(String reserved) {
	this.reserved = reserved;
    }
    public String getUser_no() {
	return user_no;
    }
    public void setUser_no(String user_no) {
	this.user_no = user_no;
    }
    public String getOrg_code() {
	return org_code;
    }
    public void setOrg_code(String org_code) {
	this.org_code = org_code;
    }
    public String getJnl_stat() {
	return jnl_stat;
    }
    public void setJnl_stat(String jnl_stat) {
	this.jnl_stat = jnl_stat;
    }
    
    
}