package com.athena.group.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MobilePlantModel {

    @SerializedName("ans_user_id")
    @Expose
    private String ans_user_id;
    @SerializedName("last_step")
    @Expose
    private String last_step;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("ans_day")
    @Expose
    private String ans_day;

    @SerializedName("questiondata")
    @Expose
    private List<questiondata_mbl> questiondata_mbl = null;

    public String getAns_user_id() {
        return ans_user_id;
    }

    public void setAns_user_id(String ans_user_id) {
        this.ans_user_id = ans_user_id;
    }

    public String getLast_step() {
        return last_step;
    }

    public void setLast_step(String last_step) {
        this.last_step = last_step;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getAns_day() {
        return ans_day;
    }

    public void setAns_day(String ans_day) {
        this.ans_day = ans_day;
    }

    public List<questiondata_mbl> getQuestiondata_mbl() {
        return questiondata_mbl;
    }

    public void setQuestiondata_mbl(List<questiondata_mbl> questiondata_mbl) {
        this.questiondata_mbl = questiondata_mbl;
    }

    public static class questiondata_mbl {

        @SerializedName("ans_label")
        @Expose
        private String ans_label;
        @SerializedName("ans_ques_status")
        @Expose
        private String ans_ques_status;
        @SerializedName("ans_recommanded_presure")
        @Expose
        private String ans_recommanded_presure;
        @SerializedName("ans_actual_presure")
        @Expose
        private String ans_actual_presure;

        public String getAns_label() {
            return ans_label;
        }

        public void setAns_label(String ans_label) {
            this.ans_label = ans_label;
        }

        public String getAns_ques_status() {
            return ans_ques_status;
        }

        public void setAns_ques_status(String ans_ques_status) {
            this.ans_ques_status = ans_ques_status;
        }

        public String getAns_recommanded_presure() {
            return ans_recommanded_presure;
        }

        public void setAns_recommanded_presure(String ans_recommanded_presure) {
            this.ans_recommanded_presure = ans_recommanded_presure;
        }

        public String getAns_actual_presure() {
            return ans_actual_presure;
        }

        public void setAns_actual_presure(String ans_actual_presure) {
            this.ans_actual_presure = ans_actual_presure;
        }
    }
}
