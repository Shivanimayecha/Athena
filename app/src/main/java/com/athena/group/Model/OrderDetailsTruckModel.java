package com.athena.group.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetailsTruckModel {

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
        @SerializedName("ao_cust_name")
        @Expose
        private String ao_cust_name;
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
        private String vehicle_no;
        @SerializedName("ao_driver_name")
        @Expose
        private String ao_driver_name;
        @SerializedName("order_status_name")
        @Expose
        private String orderStatusName;
        @SerializedName("total_qty")
        @Expose
        private String total_qty;
        @SerializedName("total_order")
        @Expose
        private String total_order;
        @SerializedName("driver_order_pdf_link")
        @Expose
        private String driver_order_pdf_link;

        @SerializedName("order_details")
        @Expose
        private List<OrderDetail> orderDetails = null;

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

        public String getAo_cust_name() {
            return ao_cust_name;
        }

        public void setAo_cust_name(String ao_cust_name) {
            this.ao_cust_name = ao_cust_name;
        }

        public String getAo_driver_name() {
            return ao_driver_name;
        }

        public void setAo_driver_name(String ao_driver_name) {
            this.ao_driver_name = ao_driver_name;
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

        public String getVehicle_no() {
            return vehicle_no;
        }

        public void setVehicle_no(String vehicle_no) {
            this.vehicle_no = vehicle_no;
        }

        public String getOrderStatusName() {
            return orderStatusName;
        }

        public void setOrderStatusName(String orderStatusName) {
            this.orderStatusName = orderStatusName;
        }

        public String getTotal_qty() {
            return total_qty;
        }

        public void setTotal_qty(String total_qty) {
            this.total_qty = total_qty;
        }

        public String getTotal_order() {
            return total_order;
        }

        public void setTotal_order(String total_order) {
            this.total_order = total_order;
        }

        public String getDriver_order_pdf_link() {
            return driver_order_pdf_link;
        }

        public void setDriver_order_pdf_link(String driver_order_pdf_link) {
            this.driver_order_pdf_link = driver_order_pdf_link;
        }

        public List<OrderDetail> getOrderDetails() {
            return orderDetails;
        }

        public void setOrderDetails(List<OrderDetail> orderDetails) {
            this.orderDetails = orderDetails;
        }

    }

    public class OrdAttributes {
        @SerializedName("Quant (tonnes)")
        @Expose
        private String quantTonnes;
        @SerializedName("Type")
        @Expose
        private String type;
        @SerializedName("mix")
        @Expose
        private String mix;
        @SerializedName("aggregate_size")
        @Expose
        private String aggregateSize;
        @SerializedName("slump")
        @Expose
        private String slump;
        @SerializedName("ord_palabel")
        @Expose
        private String ordPalabel;
        @SerializedName("ord_label")
        @Expose
        private String ordLabel;
        @SerializedName("ord_product_label")
        @Expose
        private String ordProductLabel;
        @SerializedName("ord_concrete")
        @Expose
        private String ordConcrete;
        @SerializedName("ord_plastic_drainage_polypipe")
        @Expose
        private String ordPlasticDrainagePolypipe;
        @SerializedName("ord_polysewer")
        @Expose
        private String ordPolysewer;
        @SerializedName("ord_plastic_drainage_wavin")
        @Expose
        private String ordPlasticDrainageWavin;
        @SerializedName("ord_clay_drainage")
        @Expose
        private String ordClayDrainage;
        @SerializedName("ord_kerbs")
        @Expose
        private String ordKerbs;
        @SerializedName("ord_blockpaves")
        @Expose
        private String ordBlockpaves;
        @SerializedName("generalbulding_material")
        @Expose
        private String generalbuldingMaterial;
        @SerializedName("ord_ppe")
        @Expose
        private String ordPpe;
        @SerializedName("ord_smalltools")
        @Expose
        private String ordSmalltools;
        @SerializedName("ord_mesh")
        @Expose
        private String ordMesh;
        @SerializedName("ord_flags")
        @Expose
        private String ordFlags;

        public String getQuantTonnes() {
            return quantTonnes;
        }

        public void setQuantTonnes(String quantTonnes) {
            this.quantTonnes = quantTonnes;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getMix() {
            return mix;
        }

        public void setMix(String mix) {
            this.mix = mix;
        }

        public String getAggregateSize() {
            return aggregateSize;
        }

        public void setAggregateSize(String aggregateSize) {
            this.aggregateSize = aggregateSize;
        }

        public String getSlump() {
            return slump;
        }

        public void setSlump(String slump) {
            this.slump = slump;
        }

        public String getOrdPalabel() {
            return ordPalabel;
        }

        public void setOrdPalabel(String ordPalabel) {
            this.ordPalabel = ordPalabel;
        }

        public String getOrdLabel() {
            return ordLabel;
        }

        public void setOrdLabel(String ordLabel) {
            this.ordLabel = ordLabel;
        }

        public String getOrdProductLabel() {
            return ordProductLabel;
        }

        public void setOrdProductLabel(String ordProductLabel) {
            this.ordProductLabel = ordProductLabel;
        }

        public String getOrdConcrete() {
            return ordConcrete;
        }

        public void setOrdConcrete(String ordConcrete) {
            this.ordConcrete = ordConcrete;
        }

        public String getOrdPlasticDrainagePolypipe() {
            return ordPlasticDrainagePolypipe;
        }

        public void setOrdPlasticDrainagePolypipe(String ordPlasticDrainagePolypipe) {
            this.ordPlasticDrainagePolypipe = ordPlasticDrainagePolypipe;
        }

        public String getOrdPolysewer() {
            return ordPolysewer;
        }

        public void setOrdPolysewer(String ordPolysewer) {
            this.ordPolysewer = ordPolysewer;
        }

        public String getOrdPlasticDrainageWavin() {
            return ordPlasticDrainageWavin;
        }

        public void setOrdPlasticDrainageWavin(String ordPlasticDrainageWavin) {
            this.ordPlasticDrainageWavin = ordPlasticDrainageWavin;
        }

        public String getOrdClayDrainage() {
            return ordClayDrainage;
        }

        public void setOrdClayDrainage(String ordClayDrainage) {
            this.ordClayDrainage = ordClayDrainage;
        }

        public String getOrdKerbs() {
            return ordKerbs;
        }

        public void setOrdKerbs(String ordKerbs) {
            this.ordKerbs = ordKerbs;
        }

        public String getOrdBlockpaves() {
            return ordBlockpaves;
        }

        public void setOrdBlockpaves(String ordBlockpaves) {
            this.ordBlockpaves = ordBlockpaves;
        }

        public String getGeneralbuldingMaterial() {
            return generalbuldingMaterial;
        }

        public void setGeneralbuldingMaterial(String generalbuldingMaterial) {
            this.generalbuldingMaterial = generalbuldingMaterial;
        }

        public String getOrdPpe() {
            return ordPpe;
        }

        public void setOrdPpe(String ordPpe) {
            this.ordPpe = ordPpe;
        }

        public String getOrdSmalltools() {
            return ordSmalltools;
        }

        public void setOrdSmalltools(String ordSmalltools) {
            this.ordSmalltools = ordSmalltools;
        }

        public String getOrdMesh() {
            return ordMesh;
        }

        public void setOrdMesh(String ordMesh) {
            this.ordMesh = ordMesh;
        }

        public String getOrdFlags() {
            return ordFlags;
        }

        public void setOrdFlags(String ordFlags) {
            this.ordFlags = ordFlags;
        }

    }

    public class OrderDetail {

        @SerializedName("ord_id")
        @Expose
        private String ordId;

        @SerializedName("ms_ord_id")
        @Expose
        private String ms_ord_id;
        @SerializedName("ord_user_id")
        @Expose
        private String ordUserId;
        @SerializedName("ord_site_id")
        @Expose
        private String ordSiteId;
        @SerializedName("ord_prd_id")
        @Expose
        private String ordPrdId;
        @SerializedName("ord_attributes")
        @Expose
        private OrdAttributes ordAttributes;
        @SerializedName("ord_qty")
        @Expose
        private String ordQty;
        @SerializedName("ord_total")
        @Expose
        private String ordTotal;
        @SerializedName("ord_date")
        @Expose
        private String ordDate;
        @SerializedName("ord_time")
        @Expose
        private String ordTime;
        @SerializedName("ord_status")
        @Expose
        private String ordStatus;
        @SerializedName("ord_notes")
        @Expose
        private String ordNotes;
        @SerializedName("ord_delivery_date")
        @Expose
        private String ordDeliveryDate;
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
        @SerializedName("ord_pdf_link")
        @Expose
        private String ordPdfLink;
        @SerializedName("driver_order_pdf_link")
        @Expose
        private String driver_order_pdf_link;
        @SerializedName("app_type")
        @Expose
        private String appType;
        @SerializedName("product_name")
        @Expose
        private String productName;
        @SerializedName("order_status_name")
        @Expose
        private String orderStatusName;

        public String getDriver_order_pdf_link() {
            return driver_order_pdf_link;
        }

        public void setDriver_order_pdf_link(String driver_order_pdf_link) {
            this.driver_order_pdf_link = driver_order_pdf_link;
        }

        public String getOrdId() {
            return ordId;
        }

        public String getMs_ord_id() {
            return ms_ord_id;
        }

        public void setMs_ord_id(String ms_ord_id) {
            this.ms_ord_id = ms_ord_id;
        }

        public void setOrdId(String ordId) {
            this.ordId = ordId;
        }

        public String getOrdUserId() {
            return ordUserId;
        }

        public void setOrdUserId(String ordUserId) {
            this.ordUserId = ordUserId;
        }

        public String getOrdSiteId() {
            return ordSiteId;
        }

        public void setOrdSiteId(String ordSiteId) {
            this.ordSiteId = ordSiteId;
        }

        public String getOrdPrdId() {
            return ordPrdId;
        }

        public void setOrdPrdId(String ordPrdId) {
            this.ordPrdId = ordPrdId;
        }

        public OrdAttributes getOrdAttributes() {
            return ordAttributes;
        }

        public void setOrdAttributes(OrdAttributes ordAttributes) {
            this.ordAttributes = ordAttributes;
        }

        public String getOrdQty() {
            return ordQty;
        }

        public void setOrdQty(String ordQty) {
            this.ordQty = ordQty;
        }

        public String getOrdTotal() {
            return ordTotal;
        }

        public void setOrdTotal(String ordTotal) {
            this.ordTotal = ordTotal;
        }

        public String getOrdDate() {
            return ordDate;
        }

        public void setOrdDate(String ordDate) {
            this.ordDate = ordDate;
        }

        public String getOrdTime() {
            return ordTime;
        }

        public void setOrdTime(String ordTime) {
            this.ordTime = ordTime;
        }

        public String getOrdStatus() {
            return ordStatus;
        }

        public void setOrdStatus(String ordStatus) {
            this.ordStatus = ordStatus;
        }

        public String getOrdNotes() {
            return ordNotes;
        }

        public void setOrdNotes(String ordNotes) {
            this.ordNotes = ordNotes;
        }

        public String getOrdDeliveryDate() {
            return ordDeliveryDate;
        }

        public void setOrdDeliveryDate(String ordDeliveryDate) {
            this.ordDeliveryDate = ordDeliveryDate;
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

        public String getOrdPdfLink() {
            return ordPdfLink;
        }

        public void setOrdPdfLink(String ordPdfLink) {
            this.ordPdfLink = ordPdfLink;
        }

        public String getAppType() {
            return appType;
        }

        public void setAppType(String appType) {
            this.appType = appType;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getOrderStatusName() {
            return orderStatusName;
        }

        public void setOrderStatusName(String orderStatusName) {
            this.orderStatusName = orderStatusName;
        }

    }
}
