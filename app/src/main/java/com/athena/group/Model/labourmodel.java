package com.athena.group.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class labourmodel {




    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String names;


    public labourmodel(String id, String name)
    {
        this.id=id;
        this.names=name;
    }

    public labourmodel()
    {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }
}
