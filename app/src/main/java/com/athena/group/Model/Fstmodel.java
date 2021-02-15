package com.athena.group.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Fstmodel {
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
        @SerializedName("ans_ques")
        @Expose
        private Object ansQues;
        @SerializedName("ans_ques_value")
        @Expose
        private Object ansQuesValue;
        @SerializedName("ans_ques_value_no")
        @Expose
        private String ansQuesValueNo;
        @SerializedName("ans_ques_no_value")
        @Expose
        private String ansQuesNoValue;
        @SerializedName("ans_comment")
        @Expose
        private Object ansComment;
        @SerializedName("ans_needdate")
        @Expose
        private String ansNeeddate;
        @SerializedName("ans_sumofmang")
        @Expose
        private Object ansSumofmang;
        @SerializedName("ans_completion")
        @Expose
        private Object ansCompletion;
        @SerializedName("ans_ins_sign_off")
        @Expose
        private Object ansInsSignOff;
        @SerializedName("ans_supervisor_sign")
        @Expose
        private Object ansSupervisorSign;
        @SerializedName("ans_pdf")
        @Expose
        private Object ansPdf;
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

        @SerializedName("supervisor_name")
        @Expose
        private String supervisor_name;
        @SerializedName("site_name")
        @Expose
        private String site_name;
        @SerializedName("contractor_name")
        @Expose
        private String contractor_name;
        @SerializedName("site_number")
        @Expose
        private String site_number;

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

        public Object getAnsQues() {
            return ansQues;
        }

        public void setAnsQues(Object ansQues) {
            this.ansQues = ansQues;
        }

        public Object getAnsQuesValue() {
            return ansQuesValue;
        }

        public void setAnsQuesValue(Object ansQuesValue) {
            this.ansQuesValue = ansQuesValue;
        }

        public String getAnsQuesValueNo() {
            return ansQuesValueNo;
        }

        public void setAnsQuesValueNo(String ansQuesValueNo) {
            this.ansQuesValueNo = ansQuesValueNo;
        }

        public String getAnsQuesNoValue() {
            return ansQuesNoValue;
        }

        public void setAnsQuesNoValue(String ansQuesNoValue) {
            this.ansQuesNoValue = ansQuesNoValue;
        }

        public Object getAnsComment() {
            return ansComment;
        }

        public void setAnsComment(Object ansComment) {
            this.ansComment = ansComment;
        }

        public String getAnsNeeddate() {
            return ansNeeddate;
        }

        public void setAnsNeeddate(String ansNeeddate) {
            this.ansNeeddate = ansNeeddate;
        }

        public Object getAnsSumofmang() {
            return ansSumofmang;
        }

        public void setAnsSumofmang(Object ansSumofmang) {
            this.ansSumofmang = ansSumofmang;
        }

        public Object getAnsCompletion() {
            return ansCompletion;
        }

        public void setAnsCompletion(Object ansCompletion) {
            this.ansCompletion = ansCompletion;
        }

        public Object getAnsInsSignOff() {
            return ansInsSignOff;
        }

        public void setAnsInsSignOff(Object ansInsSignOff) {
            this.ansInsSignOff = ansInsSignOff;
        }

        public Object getAnsSupervisorSign() {
            return ansSupervisorSign;
        }

        public void setAnsSupervisorSign(Object ansSupervisorSign) {
            this.ansSupervisorSign = ansSupervisorSign;
        }

        public Object getAnsPdf() {
            return ansPdf;
        }

        public void setAnsPdf(Object ansPdf) {
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

        public String getSupervisor_name() {
            return supervisor_name;
        }

        public void setSupervisor_name(String supervisor_name) {
            this.supervisor_name = supervisor_name;
        }

        public String getSite_name() {
            return site_name;
        }

        public void setSite_name(String site_name) {
            this.site_name = site_name;
        }

        public String getContractor_name() {
            return contractor_name;
        }

        public void setContractor_name(String contractor_name) {
            this.contractor_name = contractor_name;
        }

        public String getSite_number() {
            return site_number;
        }

        public void setSite_number(String site_number) {
            this.site_number = site_number;
        }
    }
}
