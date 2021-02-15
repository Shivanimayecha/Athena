package com.athena.group.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PastHsModel {

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

        @SerializedName("ans_id")
        @Expose
        private String ansId;
        @SerializedName("ans_user_id")
        @Expose
        private String ansUserId;
        @SerializedName("ans_username")
        @Expose
        private String ansUsername;
        @SerializedName("user_comment")
        @Expose
        private String userComment;
        @SerializedName("ans_project")
        @Expose
        private String ansProject;
        @SerializedName("ans_worklocation")
        @Expose
        private String ansWorklocation;
        @SerializedName("ans_site_id")
        @Expose
        private String ansSiteId;
        @SerializedName("ans_date_time_off_ins")
        @Expose
        private String ansDateTimeOffIns;
        @SerializedName("ans_name_contact_manager")
        @Expose
        private String ansNameContactManager;
        @SerializedName("ans_inspected_by")
        @Expose
        private String ansInspectedBy;
        @SerializedName("ans_nameofsup")
        @Expose
        private String ansNameofsup;
        @SerializedName("ans_report_reference")
        @Expose
        private String ansReportReference;
        @SerializedName("ans_label")
        @Expose
        private String ansLabel;
        @SerializedName("ans_failed")
        @Expose
        private String ansFailed;
        @SerializedName("ans_score")
        @Expose
        private String ansScore;
        @SerializedName("ans_ques")
        @Expose
        private String ansQues;
        @SerializedName("ans_ques_status")
        @Expose
        private String ansQuesStatus;
        @SerializedName("ans_comment")
        @Expose
        private String ansComment;
        @SerializedName("ans_sumofmang")
        @Expose
        private String ansSumofmang;
        @SerializedName("ans_ins_sign_off")
        @Expose
        private String ansInsSignOff;
        @SerializedName("ans_supervisor_sign")
        @Expose
        private String ansSupervisorSign;
        @SerializedName("ans_pdf")
        @Expose
        private String ansPdf;
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
        @SerializedName("createdDate")
        @Expose
        private String createdDate;
        @SerializedName("createdDtm")
        @Expose
        private String createdDtm;
        @SerializedName("updatedDtm")
        @Expose
        private String updatedDtm;
        @SerializedName("last_step")
        @Expose
        private String lastStep;
        @SerializedName("last_ins_name")
        @Expose
        private String lastInsName;
        @SerializedName("last_ins_date")
        @Expose
        private String lastInsDate;
        @SerializedName("last_site_manager_name")
        @Expose
        private String lastSiteManagerName;
        @SerializedName("site_name")
        @Expose
        private String siteName;
        @SerializedName("ans_needdate")
        @Expose
        private String ans_needdate;
        @SerializedName("ans_date_of_exam")
        @Expose
        private String ans_date_of_exam;
        @SerializedName("jb_site_name")
        @Expose
        private String jb_site_name;
        @SerializedName("jb_laber_name")
        @Expose
        private String jb_laber_name;
        @SerializedName("jb_plant_name")
        @Expose
        private String jb_plant_name;
        @SerializedName("jb_quantity")
        @Expose
        private String jb_quantity;
        @SerializedName("jb_work_on_site_notes")
        @Expose
        private String jb_work_on_site_notes;

        @SerializedName("jb_date")
        @Expose
        private String jb_date;

        @SerializedName("jb_new_pdf")
        @Expose
        private String jb_new_pdf;

        @SerializedName("ans_completion")
        @Expose
        private String ans_completion;

        @SerializedName("ans_palnt_model")
        @Expose
        private String ans_palnt_model;
        @SerializedName("jb_id")
        @Expose
        private String jb_id;

        public String getJb_id() {
            return jb_id;
        }

        public void setJb_id(String jb_id) {
            this.jb_id = jb_id;
        }

        public String getAns_palnt_model() {
            return ans_palnt_model;
        }

        public void setAns_palnt_model(String ans_palnt_model) {
            this.ans_palnt_model = ans_palnt_model;
        }

        public String getAns_completion() {
            return ans_completion;
        }

        public void setAns_completion(String ans_completion) {
            this.ans_completion = ans_completion;
        }

        public String getJb_date() {
            return jb_date;
        }

        public void setJb_date(String jb_date) {
            this.jb_date = jb_date;
        }

        public String getJb_new_pdf() {
            return jb_new_pdf;
        }

        public void setJb_new_pdf(String jb_new_pdf) {
            this.jb_new_pdf = jb_new_pdf;
        }

        public String getAns_needdate() {
            return ans_needdate;
        }

        public void setAns_needdate(String ans_needdate) {
            this.ans_needdate = ans_needdate;
        }

        public String getAnsId() {
            return ansId;
        }

        public void setAnsId(String ansId) {
            this.ansId = ansId;
        }

        public String getAnsUserId() {
            return ansUserId;
        }

        public void setAnsUserId(String ansUserId) {
            this.ansUserId = ansUserId;
        }

        public String getAnsUsername() {
            return ansUsername;
        }

        public void setAnsUsername(String ansUsername) {
            this.ansUsername = ansUsername;
        }

        public String getUserComment() {
            return userComment;
        }

        public void setUserComment(String userComment) {
            this.userComment = userComment;
        }

        public String getAnsProject() {
            return ansProject;
        }

        public void setAnsProject(String ansProject) {
            this.ansProject = ansProject;
        }

        public String getAnsWorklocation() {
            return ansWorklocation;
        }

        public void setAnsWorklocation(String ansWorklocation) {
            this.ansWorklocation = ansWorklocation;
        }

        public String getAnsSiteId() {
            return ansSiteId;
        }

        public void setAnsSiteId(String ansSiteId) {
            this.ansSiteId = ansSiteId;
        }

        public String getAnsDateTimeOffIns() {
            return ansDateTimeOffIns;
        }

        public void setAnsDateTimeOffIns(String ansDateTimeOffIns) {
            this.ansDateTimeOffIns = ansDateTimeOffIns;
        }

        public String getAnsNameContactManager() {
            return ansNameContactManager;
        }

        public void setAnsNameContactManager(String ansNameContactManager) {
            this.ansNameContactManager = ansNameContactManager;
        }

        public String getAnsInspectedBy() {
            return ansInspectedBy;
        }

        public void setAnsInspectedBy(String ansInspectedBy) {
            this.ansInspectedBy = ansInspectedBy;
        }

        public String getAnsNameofsup() {
            return ansNameofsup;
        }

        public void setAnsNameofsup(String ansNameofsup) {
            this.ansNameofsup = ansNameofsup;
        }

        public String getAnsReportReference() {
            return ansReportReference;
        }

        public void setAnsReportReference(String ansReportReference) {
            this.ansReportReference = ansReportReference;
        }

        public String getAnsLabel() {
            return ansLabel;
        }

        public void setAnsLabel(String ansLabel) {
            this.ansLabel = ansLabel;
        }

        public String getAnsFailed() {
            return ansFailed;
        }

        public void setAnsFailed(String ansFailed) {
            this.ansFailed = ansFailed;
        }

        public String getAnsScore() {
            return ansScore;
        }

        public void setAnsScore(String ansScore) {
            this.ansScore = ansScore;
        }

        public String getAnsQues() {
            return ansQues;
        }

        public void setAnsQues(String ansQues) {
            this.ansQues = ansQues;
        }

        public String getAnsQuesStatus() {
            return ansQuesStatus;
        }

        public void setAnsQuesStatus(String ansQuesStatus) {
            this.ansQuesStatus = ansQuesStatus;
        }

        public String getAnsComment() {
            return ansComment;
        }

        public void setAnsComment(String ansComment) {
            this.ansComment = ansComment;
        }

        public String getAnsSumofmang() {
            return ansSumofmang;
        }

        public void setAnsSumofmang(String ansSumofmang) {
            this.ansSumofmang = ansSumofmang;
        }

        public String getAnsInsSignOff() {
            return ansInsSignOff;
        }

        public void setAnsInsSignOff(String ansInsSignOff) {
            this.ansInsSignOff = ansInsSignOff;
        }

        public String getAnsSupervisorSign() {
            return ansSupervisorSign;
        }

        public void setAnsSupervisorSign(String ansSupervisorSign) {
            this.ansSupervisorSign = ansSupervisorSign;
        }

        public String getAnsPdf() {
            return ansPdf;
        }

        public void setAnsPdf(String ansPdf) {
            this.ansPdf = ansPdf;
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

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
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

        public String getLastStep() {
            return lastStep;
        }

        public void setLastStep(String lastStep) {
            this.lastStep = lastStep;
        }

        public String getLastInsName() {
            return lastInsName;
        }

        public void setLastInsName(String lastInsName) {
            this.lastInsName = lastInsName;
        }

        public String getLastInsDate() {
            return lastInsDate;
        }

        public void setLastInsDate(String lastInsDate) {
            this.lastInsDate = lastInsDate;
        }

        public String getLastSiteManagerName() {
            return lastSiteManagerName;
        }

        public void setLastSiteManagerName(String lastSiteManagerName) {
            this.lastSiteManagerName = lastSiteManagerName;
        }

        public String getSiteName() {
            return siteName;
        }

        public void setSiteName(String siteName) {
            this.siteName = siteName;
        }

        public String getAns_date_of_exam() {
            return ans_date_of_exam;
        }

        public void setAns_date_of_exam(String ans_date_of_exam) {
            this.ans_date_of_exam = ans_date_of_exam;
        }

        public String getJb_site_name() {
            return jb_site_name;
        }

        public void setJb_site_name(String jb_site_name) {
            this.jb_site_name = jb_site_name;
        }

        public String getJb_laber_name() {
            return jb_laber_name;
        }

        public void setJb_laber_name(String jb_laber_name) {
            this.jb_laber_name = jb_laber_name;
        }

        public String getJb_plant_name() {
            return jb_plant_name;
        }

        public void setJb_plant_name(String jb_plant_name) {
            this.jb_plant_name = jb_plant_name;
        }

        public String getJb_quantity() {
            return jb_quantity;
        }

        public void setJb_quantity(String jb_quantity) {
            this.jb_quantity = jb_quantity;
        }

        public String getJb_work_on_site_notes() {
            return jb_work_on_site_notes;
        }

        public void setJb_work_on_site_notes(String jb_work_on_site_notes) {
            this.jb_work_on_site_notes = jb_work_on_site_notes;
        }
    }
}
