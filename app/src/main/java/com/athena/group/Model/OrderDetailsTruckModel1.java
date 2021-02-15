package com.athena.group.Model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetailsTruckModel1 {

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

    public class Data{

        @SerializedName("ao_id")
        @Expose
        private String aoId;
        @SerializedName("ao_ord_id")
        @Expose
        private String aoOrdId;
        @SerializedName("ao_site_id")
        @Expose
        private String aoSiteId;
        @SerializedName("ao_trusr_id")
        @Expose
        private String aoTrusrId;
        @SerializedName("ao_truck_id")
        @Expose
        private String aoTruckId;
        @SerializedName("ao_date")
        @Expose
        private String aoDate;
        @SerializedName("ao_ord_status")
        @Expose
        private String aoOrdStatus;
        @SerializedName("ao_pickup_location")
        @Expose
        private String aoPickupLocation;
        @SerializedName("ao_delivery_location")
        @Expose
        private String aoDeliveryLocation;
        @SerializedName("ao_cust_name")
        @Expose
        private String aoCustName;
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
        private String updatedBy;
        @SerializedName("updatedDtm")
        @Expose
        private String updatedDtm;
        @SerializedName("site_name")
        @Expose
        private String siteName;
        @SerializedName("pickup_location")
        @Expose
        private String pickupLocation;
        @SerializedName("drop_location")
        @Expose
        private String dropLocation;
        @SerializedName("vehicle_no")
        @Expose
        private String vehicleNo;
        @SerializedName("ao_driver_name")
        @Expose
        private String aoDriverName;
        @SerializedName("order_status_name")
        @Expose
        private String orderStatusName;
        @SerializedName("total_qty")
        @Expose
        private String totalQty;
        @SerializedName("total_order")
        @Expose
        private Integer totalOrder;
        @SerializedName("driver_order_pdf_link")
        @Expose
        private String driverOrderPdfLink;
        @SerializedName("order_details")
        @Expose
        private ArrayList<OrderDetailsTruckModel.OrderDetail> orderDetails = null;

        public String getAoId() {
            return aoId;
        }

        public void setAoId(String aoId) {
            this.aoId = aoId;
        }

        public String getAoOrdId() {
            return aoOrdId;
        }

        public void setAoOrdId(String aoOrdId) {
            this.aoOrdId = aoOrdId;
        }

        public String getAoSiteId() {
            return aoSiteId;
        }

        public void setAoSiteId(String aoSiteId) {
            this.aoSiteId = aoSiteId;
        }

        public String getAoTrusrId() {
            return aoTrusrId;
        }

        public void setAoTrusrId(String aoTrusrId) {
            this.aoTrusrId = aoTrusrId;
        }

        public String getAoTruckId() {
            return aoTruckId;
        }

        public void setAoTruckId(String aoTruckId) {
            this.aoTruckId = aoTruckId;
        }

        public String getAoDate() {
            return aoDate;
        }

        public void setAoDate(String aoDate) {
            this.aoDate = aoDate;
        }

        public String getAoOrdStatus() {
            return aoOrdStatus;
        }

        public void setAoOrdStatus(String aoOrdStatus) {
            this.aoOrdStatus = aoOrdStatus;
        }

        public String getAoPickupLocation() {
            return aoPickupLocation;
        }

        public void setAoPickupLocation(String aoPickupLocation) {
            this.aoPickupLocation = aoPickupLocation;
        }

        public String getAoDeliveryLocation() {
            return aoDeliveryLocation;
        }

        public void setAoDeliveryLocation(String aoDeliveryLocation) {
            this.aoDeliveryLocation = aoDeliveryLocation;
        }

        public String getAoCustName() {
            return aoCustName;
        }

        public void setAoCustName(String aoCustName) {
            this.aoCustName = aoCustName;
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

        public String getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
        }

        public String getUpdatedDtm() {
            return updatedDtm;
        }

        public void setUpdatedDtm(String updatedDtm) {
            this.updatedDtm = updatedDtm;
        }

        public String getSiteName() {
            return siteName;
        }

        public void setSiteName(String siteName) {
            this.siteName = siteName;
        }

        public String getPickupLocation() {
            return pickupLocation;
        }

        public void setPickupLocation(String pickupLocation) {
            this.pickupLocation = pickupLocation;
        }

        public String getDropLocation() {
            return dropLocation;
        }

        public void setDropLocation(String dropLocation) {
            this.dropLocation = dropLocation;
        }

        public String getVehicleNo() {
            return vehicleNo;
        }

        public void setVehicleNo(String vehicleNo) {
            this.vehicleNo = vehicleNo;
        }

        public String getAoDriverName() {
            return aoDriverName;
        }

        public void setAoDriverName(String aoDriverName) {
            this.aoDriverName = aoDriverName;
        }

        public String getOrderStatusName() {
            return orderStatusName;
        }

        public void setOrderStatusName(String orderStatusName) {
            this.orderStatusName = orderStatusName;
        }

        public String getTotalQty() {
            return totalQty;
        }

        public void setTotalQty(String totalQty) {
            this.totalQty = totalQty;
        }

        public Integer getTotalOrder() {
            return totalOrder;
        }

        public void setTotalOrder(Integer totalOrder) {
            this.totalOrder = totalOrder;
        }

        public String getDriverOrderPdfLink() {
            return driverOrderPdfLink;
        }

        public void setDriverOrderPdfLink(String driverOrderPdfLink) {
            this.driverOrderPdfLink = driverOrderPdfLink;
        }

        /*public String getOrderDetails() {
            return orderDetails;
        }

        public void setOrderDetails(String orderDetails) {
            this.orderDetails = orderDetails;
        }*/

        public ArrayList<OrderDetailsTruckModel.OrderDetail> getOrderDetails() {
            return orderDetails;
        }

        public void setOrderDetails(ArrayList<OrderDetailsTruckModel.OrderDetail> orderDetails) {
            this.orderDetails = orderDetails;
        }
    }


}
