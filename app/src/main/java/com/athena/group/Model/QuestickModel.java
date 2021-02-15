package com.athena.group.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuestickModel {

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

        @SerializedName("mb_id")
        @Expose
        private String mbId;
        @SerializedName("mb_qs_label")
        @Expose
        private String mbQsLabel;
        @SerializedName("mb_req_pressure")
        @Expose
        private String mbReqPressure;
        @SerializedName("status")
        @Expose
        private String status;

        @SerializedName("flag")
        @Expose
        private String flag;

        public String getMbId() {
            return mbId;
        }

        public void setMbId(String mbId) {
            this.mbId = mbId;
        }

        public String getMbQsLabel() {
            return mbQsLabel;
        }

        public void setMbQsLabel(String mbQsLabel) {
            this.mbQsLabel = mbQsLabel;
        }

        public String getMbReqPressure() {
            return mbReqPressure;
        }

        public void setMbReqPressure(String mbReqPressure) {
            this.mbReqPressure = mbReqPressure;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }
    }
}
