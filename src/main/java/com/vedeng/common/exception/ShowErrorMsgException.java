/**
 * <b>Description: 用于错误信息展示</b><br> 
 * <b>Author: Franlin</b> 
 * @fileName ShowTipException.java
 * <br><b>Date: 2018年5月11日 上午11:04:40 </b> 
 *
 */
package com.vedeng.common.exception;

public class ShowErrorMsgException extends RuntimeException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3977656385824466258L;

	private String errorCode;
	private String errorMsg;

	public ShowErrorMsgException(String errorCode) {
		this.errorCode = errorCode;
		this.errorMsg = errorCode;
	}

	public ShowErrorMsgException(String errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public ShowErrorMsgException(Integer errorCode, String errorMsg) {
		this.errorCode = errorCode + "";
		this.errorMsg = errorMsg;
	}
	public String getMessage(){
		return errorMsg;
	}
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("errorCode = ").append(errorCode).append(", errorMsg = ").append(errorMsg);
		return buf.toString();
	}

}
