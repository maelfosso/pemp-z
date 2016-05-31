package com.pempz.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mael FOSSO on 5/10/2016.
 */
public class Contact implements Serializable {
    private String id;
    private String name;
    private String phone;
    private String photo;

    @SerializedName("pempz")
    private List<Pempz> pempz;

    public Contact() {
    }

    public Contact(String id, String name, String phone, String photo) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.photo = photo;
    }

    public Contact(String name, String phone, String photo) {
        this.name = name;
        this.phone = phone;
        this.photo = photo;
    }

    public Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<Pempz> getPempz() {
        return pempz;
    }

    public void setPempz(List<Pempz> pempz) {
        this.pempz = pempz;
    }
}
