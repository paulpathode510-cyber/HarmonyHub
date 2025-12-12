package com.soft.dto;

public class MyConnRequListDTO {
    private Integer senderId;
    private String senderName;
    private String senderCity;
    private String senderLevel;
    private String senderState;
    private String senderTalent;
    private String status;
    public Integer getSenderId() {
        return senderId;
    }
    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }
    public String getSenderName() {
        return senderName;
    }
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
    public String getSenderCity() {
        return senderCity;
    }
    public void setSenderCity(String senderCity) {
        this.senderCity = senderCity;
    }
    public String getSenderLevel() {
        return senderLevel;
    }
    public void setSenderLevel(String senderLevel) {
        this.senderLevel = senderLevel;
    }
    public String getSenderState() {
        return senderState;
    }
    public void setSenderState(String senderState) {
        this.senderState = senderState;
    }
    public String getSenderTalent() {
        return senderTalent;
    }
    public void setSenderTalent(String senderTalent) {
        this.senderTalent = senderTalent;
    }
    
    public MyConnRequListDTO() {
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    
}
