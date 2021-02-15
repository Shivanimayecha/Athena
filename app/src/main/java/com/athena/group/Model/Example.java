package com.athena.group.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Example {

    @SerializedName("ans_user_id")
    @Expose
    private String ans_user_id;
    @SerializedName("ans_comment")
    @Expose
    private String ans_comment;
    @SerializedName("ans_needdate")
    @Expose
    private String ans_needdate;
    @SerializedName("last_step")
    @Expose
    private String last_step;
    @SerializedName("questiondata")
    @Expose
    private List<questiondata> questions = null;

    public String getAns_user_id() {
        return ans_user_id;
    }

    public void setAns_user_id(String ans_user_id) {
        this.ans_user_id = ans_user_id;
    }

    public String getAns_comment() {
        return ans_comment;
    }

    public void setAns_comment(String ans_comment) {
        this.ans_comment = ans_comment;
    }

    public String getAns_needdate() {
        return ans_needdate;
    }

    public void setAns_needdate(String ans_needdate) {
        this.ans_needdate = ans_needdate;
    }

    public String getLast_step() {
        return last_step;
    }

    public void setLast_step(String last_step) {
        this.last_step = last_step;
    }

    public List<questiondata> getQuestions_hs() {
        return questions;
    }

    public void setQuestions_hs(List<questiondata> questions) {
        this.questions = questions;
    }



    public static class questiondata {

        @SerializedName("ans_label")
        @Expose
        private String ans_label;
        @SerializedName("ans_ques")
        @Expose
        private String ans_ques;
        @SerializedName("ans_ques_value")
        @Expose
        private String ans_ques_value;
        @SerializedName("ans_ques_value_no")
        @Expose
        private String ans_ques_value_no;

        public String getAns_label() {
            return ans_label;
        }

        public void setAns_label(String ans_label) {
            this.ans_label = ans_label;
        }

        public String getAns_ques() {
            return ans_ques;
        }

        public void setAns_ques(String ans_ques) {
            this.ans_ques = ans_ques;
        }

        public String getAns_ques_value() {
            return ans_ques_value;
        }

        public void setAns_ques_value(String ans_ques_value) {
            this.ans_ques_value = ans_ques_value;
        }

        public String getAns_ques_value_no() {
            return ans_ques_value_no;
        }

        public void setAns_ques_value_no(String ans_ques_value_no) {
            this.ans_ques_value_no = ans_ques_value_no;
        }
    }
}
