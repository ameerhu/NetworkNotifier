package com.example.networknotifier.helper.adapter;

public class LogModel {
    String message;
    String ddate;
    String ttime;

    public LogModel(String message, String ddate, String ttime) {
        this.message = message;
        this.ddate = ddate;
        this.ttime = ttime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDdate() {
        return ddate;
    }

    public void setDdate(String ddate) {
        this.ddate = ddate;
    }

    public String getTtime() {
        return ttime;
    }

    public void setTtime(String ttime) {
        this.ttime = ttime;
    }
}
