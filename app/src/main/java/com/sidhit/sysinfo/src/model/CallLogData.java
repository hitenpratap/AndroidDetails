package com.sidhit.sysinfo.src.model;

import com.sidhit.sysinfo.src.Enums;

import java.util.Date;

/**
 * Created by hitenpratap on 26/01/17.
 */

public class CallLogData {

    private String id;
    private String number;
    private Date dateTime;
    private String duration;
    private Enums.CallType callType;

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

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Enums.CallType getCallType() {
        return callType;
    }

    public void setCallType(Enums.CallType callType) {
        this.callType = callType;
    }
}
