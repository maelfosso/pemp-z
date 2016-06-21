package com.pempz.model;

import java.io.Serializable;

/**
 * Created by Mael FOSSO on 6/21/2016.
 */
public class Call implements Serializable {

    private String time;

    private Contact contact;

    public Call() {
    }

    public Call(String time, Contact contact) {
        this.time = time;
        this.contact = contact;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
