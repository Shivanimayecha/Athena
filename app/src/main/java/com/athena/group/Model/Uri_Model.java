package com.athena.group.Model;

import android.net.Uri;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Uri_Model {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("uri")
    @Expose
    private Uri uri;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
