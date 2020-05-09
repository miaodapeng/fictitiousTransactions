package com.vedeng.trader.model.vo;

import com.vedeng.trader.model.TraderCertificate;

public class TraderCertificateVo extends TraderCertificate implements Cloneable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private Integer threeInOne;// 三证合一

	private Integer medicalQualification;// 医疗资质合一

	private Integer traderType;
	
	private Integer customerType;//客户所属类型：1-终端；2-分销
	
	private String[] uris;//用来存放多个图片的地址
    //用来存放多个图片名称
	private String[] names;

	public String[] getNames() {
		return names;
	}

	public void setNames(String[] names) {
		this.names = names;
	}

	public String[] getUris() {
        return uris;
    }

    public void setUris(String[] uris) {
        this.uris = uris;
    }

    public Integer getThreeInOne() {
		return threeInOne;
	}

	public void setThreeInOne(Integer threeInOne) {
		this.threeInOne = threeInOne;
	}

	public Integer getMedicalQualification() {
		return medicalQualification;
	}

	public void setMedicalQualification(Integer medicalQualification) {
		this.medicalQualification = medicalQualification;
	}

	public Integer getTraderType() {
		return traderType;
	}

	public void setTraderType(Integer traderType) {
		this.traderType = traderType;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public Integer getCustomerType() {
		return customerType;
	}

	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
	}

}
