package com.studio.bin.kqxs.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataElement {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("values")
    @Expose
    private String values;

    @SerializedName("dateKqxs")
    @Expose
    private String dateKqxs;

    @SerializedName("location")
    @Expose
    private String location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public String getDateKqxs() {
        return dateKqxs;
    }

    public void setDateKqxs(String dateKqxs) {
        this.dateKqxs = dateKqxs;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
