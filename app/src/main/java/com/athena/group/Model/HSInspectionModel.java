package com.athena.group.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HSInspectionModel {

    @SerializedName("ans_user_id")
    @Expose
    private String ans_user_id;
    @SerializedName("last_step")
    @Expose
    private String last_step;
    @SerializedName("ans_failed")
    @Expose
    private String ans_failed;
    @SerializedName("ans_score")
    @Expose
    private String ans_score;
    //    @SerializedName("ans_ques_points_percentage")
//    @Expose
//    private String ans_ques_points_percentage;
    @SerializedName("questiondata")
    @Expose
    private List<questiondata_Hs> questions_hs = null;

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

    public String getAns_failed() {
        return ans_failed;
    }

    public void setAns_failed(String ans_failed) {
        this.ans_failed = ans_failed;
    }

    public String getAns_score() {
        return ans_score;
    }

    public void setAns_score(String ans_score) {
        this.ans_score = ans_score;
    }
    /*public String getAns_ques_points_percentage() {
        return ans_ques_points_percentage;
    }

    public void setAns_ques_points_percentage(String ans_ques_points_percentage) {
        this.ans_ques_points_percentage = ans_ques_points_percentage;
    }*/

    public List<questiondata_Hs> getQuestions_hs() {
        return questions_hs;
    }

    public void setQuestions_hs(List<questiondata_Hs> questions_hs) {
        this.questions_hs = questions_hs;
    }

    public static class questiondata_Hs {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("ans_comment")
        @Expose
        private String ans_comment;
        @SerializedName("ans_label")
        @Expose
        private String ans_label;
        @SerializedName("ans_ques")
        @Expose
        private String ans_ques;
        @SerializedName("ans_ques_status")
        @Expose
        private String ans_ques_status;

        public String getAns_comment() {
            return ans_comment;
        }

        public void setAns_comment(String ans_comment) {
            this.ans_comment = ans_comment;
        }

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

        public String getAns_ques_status() {
            return ans_ques_status;
        }

        public void setAns_ques_status(String ans_ques_status) {
            this.ans_ques_status = ans_ques_status;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }


    }
}
