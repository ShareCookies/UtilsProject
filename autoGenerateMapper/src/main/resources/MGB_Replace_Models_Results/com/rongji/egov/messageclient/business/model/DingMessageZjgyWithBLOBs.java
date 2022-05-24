package com.rongji.egov.messageclient.business.model;

public class DingMessageZjgyWithBLOBs extends DingMessageZjgy {
    /**
     * 
     */
    private String content;

    /**
     * 
     */
    private String receivers;

    /**
     * 
     */
    private String extention;

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
     * @return RECEIVERS 
     */
    public String getReceivers() {
        return receivers;
    }

    /**
     * 
     * @param receivers 
     */
    public void setReceivers(String receivers) {
        this.receivers = receivers == null ? null : receivers.trim();
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
}