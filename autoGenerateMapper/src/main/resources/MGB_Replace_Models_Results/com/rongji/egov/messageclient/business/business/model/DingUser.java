package com.rongji.egov.messageclient.business.business.model;

public class DingUser {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String userNo;

    /**
     * 
     */
    private String mobile;

    /**
     * 
     */
    private String ding2AccountId;

    /**
     * 
     * @return ID 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return USER_NO 
     */
    public String getUserNo() {
        return userNo;
    }

    /**
     * 
     * @param userNo 
     */
    public void setUserNo(String userNo) {
        this.userNo = userNo == null ? null : userNo.trim();
    }

    /**
     * 
     * @return MOBILE 
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 
     * @param mobile 
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * 
     * @return DING2_ACCOUNT_ID 
     */
    public String getDing2AccountId() {
        return ding2AccountId;
    }

    /**
     * 
     * @param ding2AccountId 
     */
    public void setDing2AccountId(String ding2AccountId) {
        this.ding2AccountId = ding2AccountId == null ? null : ding2AccountId.trim();
    }
}