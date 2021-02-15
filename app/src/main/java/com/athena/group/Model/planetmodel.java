package com.athena.group.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class planetmodel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("plant")
    @Expose
    private String plant;
    @SerializedName("quntity")
    @Expose
    private String quntity;

    public planetmodel() {

    }
    /*public planetmodel(String id, String p, String q) {
        this.plant = p;
        this.id = id;
        this.quntity = q;

    }*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public String getQuntity() {
        return quntity;
    }

    public void setQuntity(String quntity) {
        this.quntity = quntity;
    }
}
