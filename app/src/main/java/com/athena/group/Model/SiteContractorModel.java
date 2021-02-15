package com.athena.group.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SiteContractorModel {


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

        @SerializedName("fa_id")
        @Expose
        private String fa_id;
        @SerializedName("fa_label")
        @Expose
        private String fa_label;
        @SerializedName("userId")
        @Expose
        private String userId;
        @SerializedName("puserId")
        @Expose
        private String puserId;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("lname")
        @Expose
        private Object lname;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("profile_image")
        @Expose
        private Object profileImage;
        @SerializedName("status")
        @Expose
        private Object status;
        @SerializedName("country")
        @Expose
        private Object country;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("state")
        @Expose
        private Object state;
        @SerializedName("dob")
        @Expose
        private Object dob;
        @SerializedName("address_line1")
        @Expose
        private Object addressLine1;
        @SerializedName("address_line2")
        @Expose
        private Object addressLine2;
        @SerializedName("login_status")
        @Expose
        private String loginStatus;
        @SerializedName("roleId")
        @Expose
        private String roleId;
        @SerializedName("position")
        @Expose
        private Object position;
        @SerializedName("isDeleted")
        @Expose
        private String isDeleted;
        @SerializedName("createdBy")
        @Expose
        private String createdBy;
        @SerializedName("createdDtm")
        @Expose
        private String createdDtm;
        @SerializedName("updatedBy")
        @Expose
        private Object updatedBy;
        @SerializedName("updatedDtm")
        @Expose
        private Object updatedDtm;
        @SerializedName("app_type")
        @Expose
        private String appType;
        @SerializedName("device_id")
        @Expose
        private Object deviceId;
        @SerializedName("token")
        @Expose
        private Object token;
        @SerializedName("Bank_Name")
        @Expose
        private String bankName;
        @SerializedName("Branch_code")
        @Expose
        private String branchCode;
        @SerializedName("holder_name")
        @Expose
        private String holderName;
        @SerializedName("Account_Number")
        @Expose
        private String accountNumber;
        @SerializedName("Pan_Card")
        @Expose
        private String panCard;
        @SerializedName("Charge")
        @Expose
        private String charge;
        @SerializedName("Site")
        @Expose
        private String site;
        @SerializedName("truck_selected")
        @Expose
        private Object truckSelected;

        public String getFa_id() {
            return fa_id;
        }

        public void setFa_id(String fa_id) {
            this.fa_id = fa_id;
        }

        public String getFa_label() {
            return fa_label;
        }

        public void setFa_label(String fa_label) {
            this.fa_label = fa_label;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPuserId() {
            return puserId;
        }

        public void setPuserId(String puserId) {
            this.puserId = puserId;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getLname() {
            return lname;
        }

        public void setLname(Object lname) {
            this.lname = lname;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public Object getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(Object profileImage) {
            this.profileImage = profileImage;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public Object getCountry() {
            return country;
        }

        public void setCountry(Object country) {
            this.country = country;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public Object getState() {
            return state;
        }

        public void setState(Object state) {
            this.state = state;
        }

        public Object getDob() {
            return dob;
        }

        public void setDob(Object dob) {
            this.dob = dob;
        }

        public Object getAddressLine1() {
            return addressLine1;
        }

        public void setAddressLine1(Object addressLine1) {
            this.addressLine1 = addressLine1;
        }

        public Object getAddressLine2() {
            return addressLine2;
        }

        public void setAddressLine2(Object addressLine2) {
            this.addressLine2 = addressLine2;
        }

        public String getLoginStatus() {
            return loginStatus;
        }

        public void setLoginStatus(String loginStatus) {
            this.loginStatus = loginStatus;
        }

        public String getRoleId() {
            return roleId;
        }

        public void setRoleId(String roleId) {
            this.roleId = roleId;
        }

        public Object getPosition() {
            return position;
        }

        public void setPosition(Object position) {
            this.position = position;
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

        public String getCreatedDtm() {
            return createdDtm;
        }

        public void setCreatedDtm(String createdDtm) {
            this.createdDtm = createdDtm;
        }

        public Object getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(Object updatedBy) {
            this.updatedBy = updatedBy;
        }

        public Object getUpdatedDtm() {
            return updatedDtm;
        }

        public void setUpdatedDtm(Object updatedDtm) {
            this.updatedDtm = updatedDtm;
        }

        public String getAppType() {
            return appType;
        }

        public void setAppType(String appType) {
            this.appType = appType;
        }

        public Object getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(Object deviceId) {
            this.deviceId = deviceId;
        }

        public Object getToken() {
            return token;
        }

        public void setToken(Object token) {
            this.token = token;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getBranchCode() {
            return branchCode;
        }

        public void setBranchCode(String branchCode) {
            this.branchCode = branchCode;
        }

        public String getHolderName() {
            return holderName;
        }

        public void setHolderName(String holderName) {
            this.holderName = holderName;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        public String getPanCard() {
            return panCard;
        }

        public void setPanCard(String panCard) {
            this.panCard = panCard;
        }

        public String getCharge() {
            return charge;
        }

        public void setCharge(String charge) {
            this.charge = charge;
        }

        public String getSite() {
            return site;
        }

        public void setSite(String site) {
            this.site = site;
        }

        public Object getTruckSelected() {
            return truckSelected;
        }

        public void setTruckSelected(Object truckSelected) {
            this.truckSelected = truckSelected;
        }

    }
}
