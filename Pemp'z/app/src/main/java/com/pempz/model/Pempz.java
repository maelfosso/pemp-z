package com.pempz.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Mael FOSSO on 5/25/2016.
 */
public class Pempz implements Serializable {

    @SerializedName("message")
    private String message;
    @SerializedName("from")
    private Date from;
    @SerializedName("to")
    private Date to;

    public Pempz() {
    }

    public Pempz(String message) {
        this.message = message;
    }

    public Pempz(String message, Date from, Date to) {
        this.message = message;
        this.from = from;
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }
}
