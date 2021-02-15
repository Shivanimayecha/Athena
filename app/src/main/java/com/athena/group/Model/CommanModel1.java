package com.athena.group.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CommanModel1 {

    @SerializedName("position")
    @Expose
    private String position;

    @SerializedName("data")
    @Expose
    private List<CommanModel1.Data> data = null;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public static class Data{
        @SerializedName("hs_id")
        @Expose
        private String hsId;
        @SerializedName("hs_label")
        @Expose
        private String hsLabel;
        @SerializedName("hs_ques")
        @Expose
        private String hsQues;

        public String getHsId() {
            return hsId;
        }

        public void setHsId(String hsId) {
            this.hsId = hsId;
        }

        public String getHsLabel() {
            return hsLabel;
        }

        public void setHsLabel(String hsLabel) {
            this.hsLabel = hsLabel;
        }

        public String getHsQues() {
            return hsQues;
        }

        public void setHsQues(String hsQues) {
            this.hsQues = hsQues;
        }
    }
}
