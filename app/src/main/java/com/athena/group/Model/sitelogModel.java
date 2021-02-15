package com.athena.group.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class sitelogModel {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("data")
    @Expose
    private List<Data> data = null;
    @SerializedName("site_total_hrs")
    @Expose
    private String siteTotalHrs;

    @SerializedName("total_days")
    @Expose
    private String total_days;

    @SerializedName("total_work_hrs")
    @Expose
    private String total_work_hrs;

    @SerializedName("total_payment")
    @Expose
    private String total_payment;

    @SerializedName("contractor_charges")
    @Expose
    private String contractor_charges;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("time_log_detailed_csv")
    @Expose
    private String time_log_detailed_csv;

    @SerializedName("time_log_history_summary_csv")
    @Expose
    private String time_log_history_summary_csv;

    public String getTime_log_history_summary_csv() {
        return time_log_history_summary_csv;
    }

    public void setTime_log_history_summary_csv(String time_log_history_summary_csv) {
        this.time_log_history_summary_csv = time_log_history_summary_csv;
    }

    public String getTotal_payment() {
        return total_payment;
    }

    public void setTotal_payment(String total_payment) {
        this.total_payment = total_payment;
    }

    public String getContractor_charges() {
        return contractor_charges;
    }

    public void setContractor_charges(String contractor_charges) {
        this.contractor_charges = contractor_charges;
    }

    public String getTotal_days() {
        return total_days;
    }

    public void setTotal_days(String total_days) {
        this.total_days = total_days;
    }

    public String getTotal_work_hrs() {
        return total_work_hrs;
    }

    public void setTotal_work_hrs(String total_work_hrs) {
        this.total_work_hrs = total_work_hrs;
    }


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

    public String getSiteTotalHrs() {
        return siteTotalHrs;
    }

    public void setSiteTotalHrs(String siteTotalHrs) {
        this.siteTotalHrs = siteTotalHrs;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime_log_detailed_csv() {
        return time_log_detailed_csv;
    }

    public void setTime_log_detailed_csv(String time_log_detailed_csv) {
        this.time_log_detailed_csv = time_log_detailed_csv;
    }

    public class Data {

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
        private Object weekWork;
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
        private Object updatedBy;
        @SerializedName("createdDtm")
        @Expose
        private String createdDtm;
        @SerializedName("updatedDtm")
        @Expose
        private String updatedDtm;
        @SerializedName("site_name")
        @Expose
        private String siteName;
        @SerializedName("supervisor_name")
        @Expose
        private String supervisorName;
        @SerializedName("contractor_name")
        @Expose
        private String contractorName;
        @SerializedName("contractor_hrs")
        @Expose
        private String contractorHrs;

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

        public Object getWeekWork() {
            return weekWork;
        }

        public void setWeekWork(Object weekWork) {
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

        public Object getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(Object updatedBy) {
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

        public String getContractorHrs() {
            return contractorHrs;
        }

        public void setContractorHrs(String contractorHrs) {
            this.contractorHrs = contractorHrs;
        }

    }

}
