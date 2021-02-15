package com.athena.group.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AttributeValueModel {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("data")
    @Expose
    private List<Data> data = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class Data {

        @SerializedName("ca_id")
        @Expose
        private String ca_id;

        @SerializedName("ca_label")
        @Expose
        private String ca_label;

        @SerializedName("ca_value")
        @Expose
        private String ca_value;


        public String getCa_id() {
            return ca_id;
        }

        public void setCa_id(String ca_id) {
            this.ca_id = ca_id;
        }

        public String getCa_label() {
            return ca_label;
        }

        public void setCa_label(String ca_label) {
            this.ca_label = ca_label;
        }

        public String getCa_value() {
            return ca_value;
        }

        public void setCa_value(String ca_value) {
            this.ca_value = ca_value;
        }
    }
}
