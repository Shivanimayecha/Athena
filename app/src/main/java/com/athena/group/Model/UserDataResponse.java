package com.athena.group.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserDataResponse {

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
        private String lname;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("profile_image")
        @Expose
        private String profileImage;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("dob")
        @Expose
        private String dob;
        @SerializedName("address_line1")
        @Expose
        private String addressLine1;
        @SerializedName("address_line2")
        @Expose
        private String addressLine2;
        @SerializedName("login_status")
        @Expose
        private String loginStatus;
        @SerializedName("roleId")
        @Expose
        private String roleId;
        @SerializedName("position")
        @Expose
        private String position;
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
        private String updatedDtm;
        @SerializedName("app_type")
        @Expose
        private String appType;
        @SerializedName("device_id")
        @Expose
        private String deviceId;
        @SerializedName("token")
        @Expose
        private String token;
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
        @SerializedName("Account_ifsc")
        @Expose
        private Object accountIfsc;
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
        private String truckSelected;
        @SerializedName("truck_number")
        @Expose
        private List<TruckNumber> truckNumber = null;
        @SerializedName("user_type")
        @Expose
        private String userType;
        @SerializedName("user_position")
        @Expose
        private String userPosition;

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

        public String getLname() {
            return lname;
        }

        public void setLname(String lname) {
            this.lname = lname;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getAddressLine1() {
            return addressLine1;
        }

        public void setAddressLine1(String addressLine1) {
            this.addressLine1 = addressLine1;
        }

        public String getAddressLine2() {
            return addressLine2;
        }

        public void setAddressLine2(String addressLine2) {
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

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
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

        public String getUpdatedDtm() {
            return updatedDtm;
        }

        public void setUpdatedDtm(String updatedDtm) {
            this.updatedDtm = updatedDtm;
        }

        public String getAppType() {
            return appType;
        }

        public void setAppType(String appType) {
            this.appType = appType;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
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

        public Object getAccountIfsc() {
            return accountIfsc;
        }

        public void setAccountIfsc(Object accountIfsc) {
            this.accountIfsc = accountIfsc;
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

        public String getTruckSelected() {
            return truckSelected;
        }

        public void setTruckSelected(String truckSelected) {
            this.truckSelected = truckSelected;
        }

        public List<TruckNumber> getTruckNumber() {
            return truckNumber;
        }

        public void setTruckNumber(List<TruckNumber> truckNumber) {
            this.truckNumber = truckNumber;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getUserPosition() {
            return userPosition;
        }

        public void setUserPosition(String userPosition) {
            this.userPosition = userPosition;
        }

    }

    public class TruckNumber {

        @SerializedName("tr_id")
        @Expose
        private String trId;
        @SerializedName("tr_number")
        @Expose
        private String trNumber;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("isDeleted")
        @Expose
        private String isDeleted;

        public String getTrId() {
            return trId;
        }

        public void setTrId(String trId) {
            this.trId = trId;
        }

        public String getTrNumber() {
            return trNumber;
        }

        public void setTrNumber(String trNumber) {
            this.trNumber = trNumber;
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

    }
}
