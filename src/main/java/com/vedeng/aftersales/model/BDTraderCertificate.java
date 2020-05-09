package com.vedeng.aftersales.model;

import java.util.List;

/**
 * <b>Description:</b>贝登前台传过来的资质参数类<br>
 * <b>Author:calvin</b>
 * <br><b>Date:</b> 2020/2/27
 */
public class BDTraderCertificate {

    /**
     * 贝登用户的手机号
     */
    private String vdUserTel;
    /**
     * 关联的客户名称
     */
    private String companyName;
    /**
     * 营业执照
     */
    private List<Picture> businessLicensePic;
    /**
     * 二类医疗资质证书
     */
    private List<Picture> secondMedicalQualificationPic;
    /**
     * 三类医疗资质证书
     */
    private List<Picture> thirdMedicalQualificationPic;


    public String getVdUserTel() {
        return vdUserTel;
    }

    public void setVdUserTel(String vdUserTel) {
        this.vdUserTel = vdUserTel;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<Picture> getBusinessLicensePic() {
        return businessLicensePic;
    }

    public void setBusinessLicensePic(List<Picture> businessLicensePic) {
        this.businessLicensePic = businessLicensePic;
    }

    public List<Picture> getSecondMedicalQualificationPic() {
        return secondMedicalQualificationPic;
    }

    public void setSecondMedicalQualificationPic(List<Picture> secondMedicalQualificationPic) {
        this.secondMedicalQualificationPic = secondMedicalQualificationPic;
    }

    public List<Picture> getThirdMedicalQualificationPic() {
        return thirdMedicalQualificationPic;
    }

    public void setThirdMedicalQualificationPic(List<Picture> thirdMedicalQualificationPic) {
        this.thirdMedicalQualificationPic = thirdMedicalQualificationPic;
    }

    public static class Picture{
        private String picUrl;
        private String resourceId;

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getResourceId() {
            return resourceId;
        }

        public void setResourceId(String resourceId) {
            this.resourceId = resourceId;
        }
    }
}
