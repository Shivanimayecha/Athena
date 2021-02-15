package com.athena.group.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SpinnerModel {

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
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("hsn_name")
        @Expose
        private String hsn_name;
        @SerializedName("ins_id")
        @Expose
        private String insId;
        @SerializedName("ins_name")
        @Expose
        private String insName;
        @SerializedName("tr_id")
        @Expose
        private String trId;
        @SerializedName("tr_number")
        @Expose
        private String trNumber;
        @SerializedName("site_id")
        @Expose
        private String siteId;
        @SerializedName("site_name")
        @Expose
        private String siteName;
        @SerializedName("site_number")
        @Expose
        private String site_number;
        @SerializedName("site_city")
        @Expose
        private String siteCity;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("completeddate")
        @Expose
        private String completeddate;
        @SerializedName("isDeleted")
        @Expose
        private String isDeleted;


        @SerializedName("lname")
        @Expose
        private String lname;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHsn_name() {
            return hsn_name;
        }

        public void setHsn_name(String hsn_name) {
            this.hsn_name = hsn_name;
        }

        public String getInsId() {
            return insId;
        }

        public void setInsId(String insId) {
            this.insId = insId;
        }

        public String getInsName() {
            return insName;
        }

        public void setInsName(String insName) {
            this.insName = insName;
        }

        public String getTrId() {
            return trId;
        }

        public void setTrId(String trId) {
            this.trId = trId;
        }

        public String getTrNumber() {
            return trNumber;
        }

        public void setTrNumber(String trNumber) {
            this.trNumber = trNumber;
        }

        public String getSiteId() {
            return siteId;
        }

        public void setSiteId(String siteId) {
            this.siteId = siteId;
        }

        public String getSiteName() {
            return siteName;
        }

        public void setSiteName(String siteName) {
            this.siteName = siteName;
        }

        public String getSite_number() {
            return site_number;
        }

        public void setSite_number(String site_number) {
            this.site_number = site_number;
        }

        public String getSiteCity() {
            return siteCity;
        }

        public void setSiteCity(String siteCity) {
            this.siteCity = siteCity;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCompleteddate() {
            return completeddate;
        }

        public void setCompleteddate(String completeddate) {
            this.completeddate = completeddate;
        }

        public String getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(String isDeleted) {
            this.isDeleted = isDeleted;
        }

        public String getLname() {
            return lname;
        }

        public void setLname(String lname) {
            this.lname = lname;
        }
    }
}
