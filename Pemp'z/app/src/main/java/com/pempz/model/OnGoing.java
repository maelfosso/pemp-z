package com.pempz.model;

import java.io.Serializable;

/**
 * Created by Mael FOSSO on 6/17/2016.
 */
public class OnGoing implements Serializable {

    Contact contact;
    Pempz pempz;

    public OnGoing() {

    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Pempz getPempz() {
        return pempz;
    }

    public void setPempz(Pempz pempz) {
        this.pempz = pempz;
    }
}
