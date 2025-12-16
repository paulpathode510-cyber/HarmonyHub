/*
This DTO is for Showing the outgoing request from currently loggend-In user
*/

//ResponseDTO
package com.soft.dto;

public class MySendReqListDTO {
    private Integer sentToid;
    private String sentToname;
    private String sentTostatus;
    private String sentTotalent;
    private String sentTolevel;
    private String sentTocity;
    private String sentTostate;

    public Integer getSentToid() {
        return sentToid;
    }
    public void setSentToid(Integer sentToid) {
        this.sentToid = sentToid;
    }
    public String getSentToname() {
        return sentToname;
    }
    public void setSentToname(String sentToname) {
        this.sentToname = sentToname;
    }
    public String getSentTostatus() {
        return sentTostatus;
    }
    public void setSentTostatus(String sentTostatus) {
        this.sentTostatus = sentTostatus;
    }
    public String getSentTotalent() {
        return sentTotalent;
    }
    public void setSentTotalent(String sentTotalent) {
        this.sentTotalent = sentTotalent;
    }
    public String getSentTolevel() {
        return sentTolevel;
    }
    public void setSentTolevel(String sentTolevel) {
        this.sentTolevel = sentTolevel;
    }
    public String getSentTocity() {
        return sentTocity;
    }
    public void setSentTocity(String sentTocity) {
        this.sentTocity = sentTocity;
    }
    public String getSentTostate() {
        return sentTostate;
    }
    public void setSentTostate(String sentTostate) {
        this.sentTostate = sentTostate;
    }
    public MySendReqListDTO() {
    }

}
