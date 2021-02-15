package com.athena.group.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetailsModel {

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
        @SerializedName("do_order_pdf_link")
        @Expose
        private String do_order_pdf_link;
        @SerializedName("ord_id")
        @Expose
        private String ordId;
        @SerializedName("ms_ord_id")
        @Expose
        private String ms_ord_id;
        @SerializedName("cart_id")
        @Expose
        private String cartId;
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
        @SerializedName("app_type")
        @Expose
        private String appType;
        @SerializedName("product_name")
        @Expose
        private String productName;
        @SerializedName("order_status_name")
        @Expose
        private String orderStatusName;
        @SerializedName("coc_comment")
        @Expose
        private String cocComment;
        @SerializedName("coc_images")
        @Expose
        private String cocImages;


        public String getCocComment() {
            return cocComment;
        }

        public void setCocComment(String cocComment) {
            this.cocComment = cocComment;
        }

        public String getCocImages() {
            return cocImages;
        }

        public void setCocImages(String cocImages) {
            this.cocImages = cocImages;
        }

        public String getDo_order_pdf_link() {
            return do_order_pdf_link;
        }

        public void setDo_order_pdf_link(String do_order_pdf_link) {
            this.do_order_pdf_link = do_order_pdf_link;
        }

        public String getOrdId() {
            return ordId;
        }

        public void setOrdId(String ordId) {
            this.ordId = ordId;
        }

        public String getMs_ord_id() {
            return ms_ord_id;
        }

        public void setMs_ord_id(String ms_ord_id) {
            this.ms_ord_id = ms_ord_id;
        }

        public String getCartId() {
            return cartId;
        }

        public void setCartId(String cartId) {
            this.cartId = cartId;
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
}
