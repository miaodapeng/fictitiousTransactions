package com.vedeng.finance.model;
//南京银行接口返回对象
public class NjBankInterfaceInfo {
    //南京银行接口返回头
    private NjBankInterfaceInfoHead head;
    //南京银行接口返回主体
    private NjBankInterfaceInfoBody body;
    public NjBankInterfaceInfoHead getHead() {
	return head;
    }
    public void setHead(NjBankInterfaceInfoHead head) {
	this.head = head;
    }
    public NjBankInterfaceInfoBody getBody() {
	return body;
    }
    public void setBody(NjBankInterfaceInfoBody body) {
	this.body = body;
    }
}