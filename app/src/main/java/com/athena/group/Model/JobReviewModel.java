package com.athena.group.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JobReviewModel {

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

        @SerializedName("jb_id")
        @Expose
        private String jbId;
        @SerializedName("jb_user_id")
        @Expose
        private String jbUserId;
        @SerializedName("jb_site")
        @Expose
        private String jbSite;
        @SerializedName("jb_laber")
        @Expose
        private String jbLaber;
        @SerializedName("jb_plant")
        @Expose
        private String jbPlant;
        @SerializedName("jb_quantity")
        @Expose
        private String jbQuantity;
        @SerializedName("jb_work_on_site_notes")
        @Expose
        private String jbWorkOnSiteNotes;
        @SerializedName("jb_work_on_site_images")
        @Expose
        private String jbWorkOnSiteImages;
        @SerializedName("jb_programs")
        @Expose
        private String jbPrograms;
        @SerializedName("jb_day_work_ans")
        @Expose
        private String jbDayWorkAns;
        @SerializedName("jb_day_work_notes")
        @Expose
        private String jbDayWorkNotes;
        @SerializedName("jb_issue_site_ans")
        @Expose
        private String jbIssueSiteAns;
        @SerializedName("jb_issue_site_notes")
        @Expose
        private String jbIssueSiteNotes;
        @SerializedName("jb_outstanding_ans")
        @Expose
        private String jbOutstandingAns;
        @SerializedName("jb_outstanding_notes")
        @Expose
        private String jbOutstandingNotes;
        @SerializedName("jb_pdf")
        @Expose
        private Object jbPdf;
        @SerializedName("jb_new_pdf")
        @Expose
        private String jbNewPdf;
        @SerializedName("jb_date")
        @Expose
        private String jbDate;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("isDeleted")
        @Expose
        private String isDeleted;
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
        @SerializedName("creatdate")
        @Expose
        private String creatdate;
        @SerializedName("user_name")
        @Expose
        private String userName;
        @SerializedName("jb_site_name")
        @Expose
        private String jbSiteName;
        @SerializedName("jb_laber_name")
        @Expose
        private String jbLaberName;
        @SerializedName("jb_plant_name")
        @Expose
        private String jbPlantName;
        @SerializedName("jb_site_number")
        @Expose
        private String jb_site_number;

        public String getJb_site_number() {
            return jb_site_number;
        }

        public void setJb_site_number(String jb_site_number) {
            this.jb_site_number = jb_site_number;
        }

        public String getJbId() {
            return jbId;
        }

        public void setJbId(String jbId) {
            this.jbId = jbId;
        }

        public String getJbUserId() {
            return jbUserId;
        }

        public void setJbUserId(String jbUserId) {
            this.jbUserId = jbUserId;
        }

        public String getJbSite() {
            return jbSite;
        }

        public void setJbSite(String jbSite) {
            this.jbSite = jbSite;
        }

        public String getJbLaber() {
            return jbLaber;
        }

        public void setJbLaber(String jbLaber) {
            this.jbLaber = jbLaber;
        }

        public String getJbPlant() {
            return jbPlant;
        }

        public void setJbPlant(String jbPlant) {
            this.jbPlant = jbPlant;
        }

        public String getJbQuantity() {
            return jbQuantity;
        }

        public void setJbQuantity(String jbQuantity) {
            this.jbQuantity = jbQuantity;
        }

        public String getJbWorkOnSiteNotes() {
            return jbWorkOnSiteNotes;
        }

        public void setJbWorkOnSiteNotes(String jbWorkOnSiteNotes) {
            this.jbWorkOnSiteNotes = jbWorkOnSiteNotes;
        }

        public String getJbWorkOnSiteImages() {
            return jbWorkOnSiteImages;
        }

        public void setJbWorkOnSiteImages(String jbWorkOnSiteImages) {
            this.jbWorkOnSiteImages = jbWorkOnSiteImages;
        }

        public String getJbPrograms() {
            return jbPrograms;
        }

        public void setJbPrograms(String jbPrograms) {
            this.jbPrograms = jbPrograms;
        }

        public String getJbDayWorkAns() {
            return jbDayWorkAns;
        }

        public void setJbDayWorkAns(String jbDayWorkAns) {
            this.jbDayWorkAns = jbDayWorkAns;
        }

        public String getJbDayWorkNotes() {
            return jbDayWorkNotes;
        }

        public void setJbDayWorkNotes(String jbDayWorkNotes) {
            this.jbDayWorkNotes = jbDayWorkNotes;
        }

        public String getJbIssueSiteAns() {
            return jbIssueSiteAns;
        }

        public void setJbIssueSiteAns(String jbIssueSiteAns) {
            this.jbIssueSiteAns = jbIssueSiteAns;
        }

        public String getJbIssueSiteNotes() {
            return jbIssueSiteNotes;
        }

        public void setJbIssueSiteNotes(String jbIssueSiteNotes) {
            this.jbIssueSiteNotes = jbIssueSiteNotes;
        }

        public String getJbOutstandingAns() {
            return jbOutstandingAns;
        }

        public void setJbOutstandingAns(String jbOutstandingAns) {
            this.jbOutstandingAns = jbOutstandingAns;
        }

        public String getJbOutstandingNotes() {
            return jbOutstandingNotes;
        }

        public void setJbOutstandingNotes(String jbOutstandingNotes) {
            this.jbOutstandingNotes = jbOutstandingNotes;
        }

        public Object getJbPdf() {
            return jbPdf;
        }

        public void setJbPdf(Object jbPdf) {
            this.jbPdf = jbPdf;
        }

        public String getJbNewPdf() {
            return jbNewPdf;
        }

        public void setJbNewPdf(String jbNewPdf) {
            this.jbNewPdf = jbNewPdf;
        }

        public String getJbDate() {
            return jbDate;
        }

        public void setJbDate(String jbDate) {
            this.jbDate = jbDate;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(String isDeleted) {
            this.isDeleted = isDeleted;
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

        public String getCreatdate() {
            return creatdate;
        }

        public void setCreatdate(String creatdate) {
            this.creatdate = creatdate;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getJbSiteName() {
            return jbSiteName;
        }

        public void setJbSiteName(String jbSiteName) {
            this.jbSiteName = jbSiteName;
        }

        public String getJbLaberName() {
            return jbLaberName;
        }

        public void setJbLaberName(String jbLaberName) {
            this.jbLaberName = jbLaberName;
        }

        public String getJbPlantName() {
            return jbPlantName;
        }

        public void setJbPlantName(String jbPlantName) {
            this.jbPlantName = jbPlantName;
        }

    }
}
