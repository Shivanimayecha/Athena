
package com.athena.group.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultModel {

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
        @SerializedName("user_name")
        @Expose
        private String userName;
        @SerializedName("site_name")
        @Expose
        private String siteName;
        @SerializedName("ans_user_id")
        @Expose
        private String ansUserId;
        @SerializedName("last_step")
        @Expose
        private String lastStep;
        @SerializedName("questiondata")
        @Expose
        private List<questiondata> questiondata = null;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getSiteName() {
            return siteName;
        }

        public void setSiteName(String siteName) {
            this.siteName = siteName;
        }

        public String getAnsUserId() {
            return ansUserId;
        }

        public void setAnsUserId(String ansUserId) {
            this.ansUserId = ansUserId;
        }

        public String getLastStep() {
            return lastStep;
        }

        public void setLastStep(String lastStep) {
            this.lastStep = lastStep;
        }

        public List<questiondata> getQuestiondata() {
            return questiondata;
        }

        public void setQuestiondata(List<questiondata> questiondata) {
            this.questiondata = questiondata;
        }


        public class questiondata {

            @SerializedName("ans_label")
            @Expose
            private String ansLabel;
            @SerializedName("ans_ques")
            @Expose
            private String ansQues;
            @SerializedName("ans_ques_value")
            @Expose
            private String ansQuesValue;
            @SerializedName("ans_ques_value_no")
            @Expose
            private String ansQuesValueNo;
            @SerializedName("ans_comment")
            @Expose
            private String ansComment;
            @SerializedName("ans_needdate")
            @Expose
            private String ansNeeddate;

            public String getAnsLabel() {
                return ansLabel;
            }

            public void setAnsLabel(String ansLabel) {
                this.ansLabel = ansLabel;
            }

            public String getAnsQues() {
                return ansQues;
            }

            public void setAnsQues(String ansQues) {
                this.ansQues = ansQues;
            }

            public String getAnsQuesValue() {
                return ansQuesValue;
            }

            public void setAnsQuesValue(String ansQuesValue) {
                this.ansQuesValue = ansQuesValue;
            }

            public String getAnsQuesValueNo() {
                return ansQuesValueNo;
            }

            public void setAnsQuesValueNo(String ansQuesValueNo) {
                this.ansQuesValueNo = ansQuesValueNo;
            }

            public String getAnsComment() {
                return ansComment;
            }

            public void setAnsComment(String ansComment) {
                this.ansComment = ansComment;
            }

            public String getAnsNeeddate() {
                return ansNeeddate;
            }

            public void setAnsNeeddate(String ansNeeddate) {
                this.ansNeeddate = ansNeeddate;
            }



        }


    }

}
