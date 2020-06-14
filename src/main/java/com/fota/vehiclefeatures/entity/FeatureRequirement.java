package com.fota.vehiclefeatures.entity;

//FEATURE_CODE varchar(10),
//SOFT_HARD_CODE varchar(50),
//SOFT_HARD_FLAG varchar(10),
//PRESENT_NOTPRESENT_FLAG varchar(10),

public class FeatureRequirement {
    private String featureCode;
    private String softHardCode;
    private String softHardFlag;
    private String presentNotPresentFlag;
    public FeatureRequirement() {
    }

    public FeatureRequirement(String featureCode, String softHardCode, String softHardFlag, String presentNotPresentFlag) {
        this.featureCode = featureCode;
        this.softHardCode = softHardCode;
        this.softHardFlag = softHardFlag;
        this.presentNotPresentFlag = presentNotPresentFlag;
    }
    public FeatureRequirement(String featureCode, String softHardCode, String softHardFlag) {
        this.featureCode = featureCode;
        this.softHardCode = softHardCode;
        this.softHardFlag = softHardFlag;
        this.presentNotPresentFlag = null;
    }

    public String getFeatureCode() {
        return featureCode;
    }

    public void setFeatureCode(String featureCode) {
        this.featureCode = featureCode;
    }

    public String getSoftHardCode() {
        return softHardCode;
    }

    public void setSoftHardCode(String softHardCode) {
        this.softHardCode = softHardCode;
    }

    public String getSoftHardFlag() {
        return softHardFlag;
    }

    public void setSoftHardFlag(String softHardFlag) {
        this.softHardFlag = softHardFlag;
    }

    public String getPresentNotPresentFlag() {
        return presentNotPresentFlag;
    }

    public void setPresentNotPresentFlag(String presentNotPresentFlag) {
        this.presentNotPresentFlag = presentNotPresentFlag;
    }
}
