package com.athena.group.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PercFaildModel {

    @SerializedName("data")
    @Expose
    private List<Dataa> dataa = null;

    public List<Dataa> getDataa() {
        return dataa;
    }

    public void setDataa(List<Dataa> dataa) {
        this.dataa = dataa;
    }

    public static class Dataa {
        @SerializedName("position")
        @Expose
        private String poistion;

        @SerializedName("id")
        @Expose
        private String id;

        public String getPoistion() {
            return poistion;
        }

        public void setPoistion(String poistion) {
            this.poistion = poistion;
        }

        @SerializedName("ans_ques_points")
        @Expose
        private String ans_ques_points;

        @SerializedName("ans_failed")
        @Expose
        private String ans_failed;

        public void setId(String id) {
            this.id = id;
        }

        public String getAns_failed() {
            return ans_failed;
        }

        public void setAns_failed(String ans_failed) {
            this.ans_failed = ans_failed;
        }

        public String getAns_ques_points() {
            return ans_ques_points;
        }

        public void setAns_ques_points(String ans_ques_points) {
            this.ans_ques_points = ans_ques_points;
        }

        public String getId() {
            return id;
        }


    }
}

