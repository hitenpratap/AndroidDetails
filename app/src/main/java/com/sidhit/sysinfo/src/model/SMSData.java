package com.sidhit.sysinfo.src.model;

import com.sidhit.sysinfo.src.Enums;

import java.util.Date;

/**
 * Created by hitenpratap on 26/01/17.
 */

public class SMSData {

    private String id;
    private String number;
    private String body;
    private Date dateTime;
    private Enums.SMSType smsType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Enums.SMSType getSmsType() {
        return smsType;
    }

    public void setSmsType(Enums.SMSType smsType) {
        this.smsType = smsType;
    }
}
