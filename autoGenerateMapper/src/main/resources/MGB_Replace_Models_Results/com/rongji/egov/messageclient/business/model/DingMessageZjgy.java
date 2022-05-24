package com.rongji.egov.messageclient.business.model;

import java.util.Date;

public class DingMessageZjgy {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String senderUserNo;

    /**
     * 
     */
    private String content;

    /**
     * 
     */
    private String notifyType;

    /**
     * 
     */
    private String receiversUserNo;

    /**
     * 
     */
    private String extention;

    /**
     * 
     */
    private String sendResult;

    /**
     * 
     */
    private Date updateTime;

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
     * @return SENDER_USER_NO 
     */
    public String getSenderUserNo() {
        return senderUserNo;
    }

    /**
     * 
     * @param senderUserNo 
     */
    public void setSenderUserNo(String senderUserNo) {
        this.senderUserNo = senderUserNo == null ? null : senderUserNo.trim();
    }

    /**
     * 
     * @return CONTENT 
     */
    public String getContent() {
        return content;
    }

    /**
     * 
     * @param content 
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 
     * @return NOTIFY_TYPE 
     */
    public String getNotifyType() {
        return notifyType;
    }

    /**
     * 
     * @param notifyType 
     */
    public void setNotifyType(String notifyType) {
        this.notifyType = notifyType == null ? null : notifyType.trim();
    }

    /**
     * 
     * @return RECEIVERS_USER_NO 
     */
    public String getReceiversUserNo() {
        return receiversUserNo;
    }

    /**
     * 
     * @param receiversUserNo 
     */
    public void setReceiversUserNo(String receiversUserNo) {
        this.receiversUserNo = receiversUserNo == null ? null : receiversUserNo.trim();
    }

    /**
     * 
     * @return EXTENTION 
     */
    public String getExtention() {
        return extention;
    }

    /**
     * 
     * @param extention 
     */
    public void setExtention(String extention) {
        this.extention = extention == null ? null : extention.trim();
    }

    /**
     * 
     * @return SEND_RESULT 
     */
    public String getSendResult() {
        return sendResult;
    }

    /**
     * 
     * @param sendResult 
     */
    public void setSendResult(String sendResult) {
        this.sendResult = sendResult == null ? null : sendResult.trim();
    }

    /**
     * 
     * @return UPDATE_TIME 
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * @param updateTime 
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}