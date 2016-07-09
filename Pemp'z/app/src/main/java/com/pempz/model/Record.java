package com.pempz.model;

import java.util.Date;

/**
 * Created by Mael FOSSO on 7/8/2016.
 */
public class Record {

    private String file;
    private Date created;

    public Record() {
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
