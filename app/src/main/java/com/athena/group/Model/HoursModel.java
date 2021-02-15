package com.athena.group.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HoursModel {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("csv_path")
    @Expose
    private String csv_path;
    @SerializedName("contimelog_detail")
    @Expose
    private List<ContimelogDetail> contimelogDetail = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<ContimelogDetail> getContimelogDetail() {
        return contimelogDetail;
    }

    public void setContimelogDetail(List<ContimelogDetail> contimelogDetail) {
        this.contimelogDetail = contimelogDetail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCsv_path() {
        return csv_path;
    }

    public void setCsv_path(String csv_path) {
        this.csv_path = csv_path;
    }

    public class ContimelogDetail {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("site_id")
        @Expose
        private String siteId;
        @SerializedName("supervisor")
        @Expose
        private String supervisor;
        @SerializedName("contractor_id")
        @Expose
        private String contractorId;
        @SerializedName("start_time")
        @Expose
        private String startTime;
        @SerializedName("end_time")
        @Expose
        private String endTime;
        @SerializedName("br_start_time")
        @Expose
        private String brStartTime;
        @SerializedName("br_end_time")
        @Expose
        private String brEndTime;
        @SerializedName("work_hrs")
        @Expose
        private String workHrs;
        @SerializedName("break_hrs")
        @Expose
        private String breakHrs;
        @SerializedName("total_work")
        @Expose
        private String totalWork;
        @SerializedName("week_work")
        @Expose
        private String weekWork;
        @SerializedName("site_date")
        @Expose
        private String siteDate;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("note")
        @Expose
        private String note;
        @SerializedName("isdeleted")
        @Expose
        private String isdeleted;
        @SerializedName("Photo")
        @Expose
        private String photo;
        @SerializedName("createdBy")
        @Expose
        private String createdBy;
        @SerializedName("updatedBy")
        @Expose
        private String updatedBy;
        @SerializedName("createdDtm")
        @Expose
        private String createdDtm;
        @SerializedName("updatedDtm")
        @Expose
        private String updatedDtm;
        @SerializedName("travel_time")
        @Expose
        private String travelTime;
        @SerializedName("site_name")
        @Expose
        private String siteName;
        @SerializedName("supervisor_name")
        @Expose
        private String supervisorName;
        @SerializedName("contractor_name")
        @Expose
        private String contractorName;
        @SerializedName("rate")
        @Expose
        private String rate;
        @SerializedName("paymnet")
        @Expose
        private String paymnet;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSiteId() {
            return siteId;
        }

        public void setSiteId(String siteId) {
            this.siteId = siteId;
        }

        public String getSupervisor() {
            return supervisor;
        }

        public void setSupervisor(String supervisor) {
            this.supervisor = supervisor;
        }

        public String getContractorId() {
            return contractorId;
        }

        public void setContractorId(String contractorId) {
            this.contractorId = contractorId;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getBrStartTime() {
            return brStartTime;
        }

        public void setBrStartTime(String brStartTime) {
            this.brStartTime = brStartTime;
        }

        public String getBrEndTime() {
            return brEndTime;
        }

        public void setBrEndTime(String brEndTime) {
            this.brEndTime = brEndTime;
        }

        public String getWorkHrs() {
            return workHrs;
        }

        public void setWorkHrs(String workHrs) {
            this.workHrs = workHrs;
        }

        public String getBreakHrs() {
            return breakHrs;
        }

        public void setBreakHrs(String breakHrs) {
            this.breakHrs = breakHrs;
        }

        public String getTotalWork() {
            return totalWork;
        }

        public void setTotalWork(String totalWork) {
            this.totalWork = totalWork;
        }

        public String getWeekWork() {
            return weekWork;
        }

        public void setWeekWork(String weekWork) {
            this.weekWork = weekWork;
        }

        public String getSiteDate() {
            return siteDate;
        }

        public void setSiteDate(String siteDate) {
            this.siteDate = siteDate;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getIsdeleted() {
            return isdeleted;
        }

        public void setIsdeleted(String isdeleted) {
            this.isdeleted = isdeleted;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
        }

        public String getCreatedDtm() {
            return createdDtm;
        }

        public void setCreatedDtm(String createdDtm) {
            this.createdDtm = createdDtm;
        }

        public String getUpdatedDtm() {
            return updatedDtm;
        }

        public void setUpdatedDtm(String updatedDtm) {
            this.updatedDtm = updatedDtm;
        }

        public String getTravelTime() {
            return travelTime;
        }

        public void setTravelTime(String travelTime) {
            this.travelTime = travelTime;
        }

        public String getSiteName() {
            return siteName;
        }

        public void setSiteName(String siteName) {
            this.siteName = siteName;
        }

        public String getSupervisorName() {
            return supervisorName;
        }

        public void setSupervisorName(String supervisorName) {
            this.supervisorName = supervisorName;
        }

        public String getContractorName() {
            return contractorName;
        }

        public void setContractorName(String contractorName) {
            this.contractorName = contractorName;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getPaymnet() {
            return paymnet;
        }

        public void setPaymnet(String paymnet) {
            this.paymnet = paymnet;
        }

    }

}
