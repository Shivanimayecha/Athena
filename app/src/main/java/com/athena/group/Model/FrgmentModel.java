package com.athena.group.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FrgmentModel {
    @SerializedName("lable")
    @Expose
    private String label;

    @SerializedName("insert")
    @Expose
    private String insert;


    @SerializedName("update")
    @Expose
    private String update;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getInsert() {
        return insert;
    }

    public void setInsert(String insert) {
        this.insert = insert;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }
}
