package com.athena.group.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationModel {

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

        @SerializedName("ntf_id")
        @Expose
        private String ntfId;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("user_seen")
        @Expose
        private String userSeen;
        @SerializedName("admin_seen")
        @Expose
        private String adminSeen;
        @SerializedName("noti_status")
        @Expose
        private String notiStatus;
        @SerializedName("order_id")
        @Expose
        private String orderId;
        @SerializedName("assign_ord_id")
        @Expose
        private String assignOrdId;
        @SerializedName("trusr_id")
        @Expose
        private String trusrId;
        @SerializedName("site_id")
        @Expose
        private String siteId;
        @SerializedName("timelog_id")
        @Expose
        private Object timelogId;
        @SerializedName("notitype")
        @Expose
        private String notitype;
        @SerializedName("addedby")
        @Expose
        private String addedby;
        @SerializedName("createdby")
        @Expose
        private String createdby;
        @SerializedName("redirect_status")
        @Expose
        private String redirectStatus;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("created_date")
        @Expose
        private String createdDate;

        public String getNtfId() {
            return ntfId;
        }

        public void setNtfId(String ntfId) {
            this.ntfId = ntfId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserSeen() {
            return userSeen;
        }

        public void setUserSeen(String userSeen) {
            this.userSeen = userSeen;
        }

        public String getAdminSeen() {
            return adminSeen;
        }

        public void setAdminSeen(String adminSeen) {
            this.adminSeen = adminSeen;
        }

        public String getNotiStatus() {
            return notiStatus;
        }

        public void setNotiStatus(String notiStatus) {
            this.notiStatus = notiStatus;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getAssignOrdId() {
            return assignOrdId;
        }

        public void setAssignOrdId(String assignOrdId) {
            this.assignOrdId = assignOrdId;
        }

        public String getTrusrId() {
            return trusrId;
        }

        public void setTrusrId(String trusrId) {
            this.trusrId = trusrId;
        }

        public String getSiteId() {
            return siteId;
        }

        public void setSiteId(String siteId) {
            this.siteId = siteId;
        }

        public Object getTimelogId() {
            return timelogId;
        }

        public void setTimelogId(Object timelogId) {
            this.timelogId = timelogId;
        }

        public String getNotitype() {
            return notitype;
        }

        public void setNotitype(String notitype) {
            this.notitype = notitype;
        }

        public String getAddedby() {
            return addedby;
        }

        public void setAddedby(String addedby) {
            this.addedby = addedby;
        }

        public String getCreatedby() {
            return createdby;
        }

        public void setCreatedby(String createdby) {
            this.createdby = createdby;
        }

        public String getRedirectStatus() {
            return redirectStatus;
        }

        public void setRedirectStatus(String redirectStatus) {
            this.redirectStatus = redirectStatus;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

    }
}
