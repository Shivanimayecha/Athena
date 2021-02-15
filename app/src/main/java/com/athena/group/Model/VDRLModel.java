package com.athena.group.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VDRLModel {

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

        @SerializedName("dd_id")
        @Expose
        private String ddId;
        @SerializedName("dd_site_id")
        @Expose
        private Object ddSiteId;
        @SerializedName("dd_ord_id")
        @Expose
        private Object ddOrdId;
        @SerializedName("dd_trusr_id")
        @Expose
        private String ddTrusrId;
        @SerializedName("dd_truck_id")
        @Expose
        private String ddTruckId;
        @SerializedName("dd_assign_date")
        @Expose
        private String ddAssignDate;
        @SerializedName("dd_report_no")
        @Expose
        private String ddReportNo;
        @SerializedName("dd_drivers_name")
        @Expose
        private String ddDriversName;
        @SerializedName("dd_date")
        @Expose
        private Object ddDate;
        @SerializedName("dd_vehicle_no")
        @Expose
        private String ddVehicleNo;
        @SerializedName("dd_trailer_feet_sr_no")
        @Expose
        private Object ddTrailerFeetSrNo;
        @SerializedName("dd_odometer_reading")
        @Expose
        private String ddOdometerReading;
        @SerializedName("dd_time")
        @Expose
        private String ddTime;
        @SerializedName("dd_fual_oil_leaks")
        @Expose
        private String ddFualOilLeaks;
        @SerializedName("dd_lights")
        @Expose
        private String ddLights;
        @SerializedName("dd_brake_lines")
        @Expose
        private String ddBrakeLines;
        @SerializedName("dd_battery_security")
        @Expose
        private String ddBatterySecurity;
        @SerializedName("dd_reflectors_markers")
        @Expose
        private String ddReflectorsMarkers;
        @SerializedName("dd_coupling_security")
        @Expose
        private String ddCouplingSecurity;
        @SerializedName("dd_tyres_wheel_wheel_fixing")
        @Expose
        private String ddTyresWheelWheelFixing;
        @SerializedName("dd_indicators_side_repeaters")
        @Expose
        private String ddIndicatorsSideRepeaters;
        @SerializedName("dd_electrical_connections")
        @Expose
        private String ddElectricalConnections;
        @SerializedName("dd_spray_suppression")
        @Expose
        private String ddSpraySuppression;
        @SerializedName("dd_wipers")
        @Expose
        private String ddWipers;
        @SerializedName("dd_brakes_inc_abs_ebs")
        @Expose
        private String ddBrakesIncAbsEbs;
        @SerializedName("dd_steering")
        @Expose
        private String ddSteering;
        @SerializedName("dd_washers")
        @Expose
        private String ddWashers;
        @SerializedName("dd_security_condition_of_body_wings")
        @Expose
        private String ddSecurityConditionOfBodyWings;
        @SerializedName("dd_security_of_load_vehicle_height")
        @Expose
        private String ddSecurityOfLoadVehicleHeight;
        @SerializedName("dd_horn")
        @Expose
        private String ddHorn;
        @SerializedName("dd_registration_plates")
        @Expose
        private String ddRegistrationPlates;
        @SerializedName("dd_mirrors_glass_visibility")
        @Expose
        private String ddMirrorsGlassVisibility;
        @SerializedName("dd_excessive_engine_exhaust_smoke")
        @Expose
        private String ddExcessiveEngineExhaustSmoke;
        @SerializedName("dd_cab_interior_seat_belts")
        @Expose
        private String ddCabInteriorSeatBelts;
        @SerializedName("dd_air_build_up_leaks")
        @Expose
        private String ddAirBuildUpLeaks;
        @SerializedName("dd_adblue_if_required")
        @Expose
        private String ddAdblueIfRequired;
        @SerializedName("dd_warning_lamps_mil")
        @Expose
        private String ddWarningLampsMil;
        @SerializedName("dd_report_defects_here")
        @Expose
        private String ddReportDefectsHere;
        @SerializedName("dd_defect_assessment_and_rectification")
        @Expose
        private String ddDefectAssessmentAndRectification;
        @SerializedName("dd_defect_reported_to")
        @Expose
        private String ddDefectReportedTo;
        @SerializedName("dd_write_nill_here")
        @Expose
        private String ddWriteNillHere;
        @SerializedName("dd_drivers_signature")
        @Expose
        private String ddDriversSignature;
        @SerializedName("dd_drivers_signature_date")
        @Expose
        private Object ddDriversSignatureDate;
        @SerializedName("dd_defects_rectified_by")
        @Expose
        private String ddDefectsRectifiedBy;
        @SerializedName("dd_signature")
        @Expose
        private String ddSignature;
        @SerializedName("dd_signature_date")
        @Expose
        private Object ddSignatureDate;
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
        @SerializedName("dd_pdf")
        @Expose
        private String ddPdf;
        @SerializedName("site_name")
        @Expose
        private String siteName;

        public String getDdId() {
            return ddId;
        }

        public void setDdId(String ddId) {
            this.ddId = ddId;
        }

        public Object getDdSiteId() {
            return ddSiteId;
        }

        public void setDdSiteId(Object ddSiteId) {
            this.ddSiteId = ddSiteId;
        }

        public Object getDdOrdId() {
            return ddOrdId;
        }

        public void setDdOrdId(Object ddOrdId) {
            this.ddOrdId = ddOrdId;
        }

        public String getDdTrusrId() {
            return ddTrusrId;
        }

        public void setDdTrusrId(String ddTrusrId) {
            this.ddTrusrId = ddTrusrId;
        }

        public String getDdTruckId() {
            return ddTruckId;
        }

        public void setDdTruckId(String ddTruckId) {
            this.ddTruckId = ddTruckId;
        }

        public String getDdAssignDate() {
            return ddAssignDate;
        }

        public void setDdAssignDate(String ddAssignDate) {
            this.ddAssignDate = ddAssignDate;
        }

        public String getDdReportNo() {
            return ddReportNo;
        }

        public void setDdReportNo(String ddReportNo) {
            this.ddReportNo = ddReportNo;
        }

        public String getDdDriversName() {
            return ddDriversName;
        }

        public void setDdDriversName(String ddDriversName) {
            this.ddDriversName = ddDriversName;
        }

        public Object getDdDate() {
            return ddDate;
        }

        public void setDdDate(Object ddDate) {
            this.ddDate = ddDate;
        }

        public String getDdVehicleNo() {
            return ddVehicleNo;
        }

        public void setDdVehicleNo(String ddVehicleNo) {
            this.ddVehicleNo = ddVehicleNo;
        }

        public Object getDdTrailerFeetSrNo() {
            return ddTrailerFeetSrNo;
        }

        public void setDdTrailerFeetSrNo(Object ddTrailerFeetSrNo) {
            this.ddTrailerFeetSrNo = ddTrailerFeetSrNo;
        }

        public String getDdOdometerReading() {
            return ddOdometerReading;
        }

        public void setDdOdometerReading(String ddOdometerReading) {
            this.ddOdometerReading = ddOdometerReading;
        }

        public String getDdTime() {
            return ddTime;
        }

        public void setDdTime(String ddTime) {
            this.ddTime = ddTime;
        }

        public String getDdFualOilLeaks() {
            return ddFualOilLeaks;
        }

        public void setDdFualOilLeaks(String ddFualOilLeaks) {
            this.ddFualOilLeaks = ddFualOilLeaks;
        }

        public String getDdLights() {
            return ddLights;
        }

        public void setDdLights(String ddLights) {
            this.ddLights = ddLights;
        }

        public String getDdBrakeLines() {
            return ddBrakeLines;
        }

        public void setDdBrakeLines(String ddBrakeLines) {
            this.ddBrakeLines = ddBrakeLines;
        }

        public String getDdBatterySecurity() {
            return ddBatterySecurity;
        }

        public void setDdBatterySecurity(String ddBatterySecurity) {
            this.ddBatterySecurity = ddBatterySecurity;
        }

        public String getDdReflectorsMarkers() {
            return ddReflectorsMarkers;
        }

        public void setDdReflectorsMarkers(String ddReflectorsMarkers) {
            this.ddReflectorsMarkers = ddReflectorsMarkers;
        }

        public String getDdCouplingSecurity() {
            return ddCouplingSecurity;
        }

        public void setDdCouplingSecurity(String ddCouplingSecurity) {
            this.ddCouplingSecurity = ddCouplingSecurity;
        }

        public String getDdTyresWheelWheelFixing() {
            return ddTyresWheelWheelFixing;
        }

        public void setDdTyresWheelWheelFixing(String ddTyresWheelWheelFixing) {
            this.ddTyresWheelWheelFixing = ddTyresWheelWheelFixing;
        }

        public String getDdIndicatorsSideRepeaters() {
            return ddIndicatorsSideRepeaters;
        }

        public void setDdIndicatorsSideRepeaters(String ddIndicatorsSideRepeaters) {
            this.ddIndicatorsSideRepeaters = ddIndicatorsSideRepeaters;
        }

        public String getDdElectricalConnections() {
            return ddElectricalConnections;
        }

        public void setDdElectricalConnections(String ddElectricalConnections) {
            this.ddElectricalConnections = ddElectricalConnections;
        }

        public String getDdSpraySuppression() {
            return ddSpraySuppression;
        }

        public void setDdSpraySuppression(String ddSpraySuppression) {
            this.ddSpraySuppression = ddSpraySuppression;
        }

        public String getDdWipers() {
            return ddWipers;
        }

        public void setDdWipers(String ddWipers) {
            this.ddWipers = ddWipers;
        }

        public String getDdBrakesIncAbsEbs() {
            return ddBrakesIncAbsEbs;
        }

        public void setDdBrakesIncAbsEbs(String ddBrakesIncAbsEbs) {
            this.ddBrakesIncAbsEbs = ddBrakesIncAbsEbs;
        }

        public String getDdSteering() {
            return ddSteering;
        }

        public void setDdSteering(String ddSteering) {
            this.ddSteering = ddSteering;
        }

        public String getDdWashers() {
            return ddWashers;
        }

        public void setDdWashers(String ddWashers) {
            this.ddWashers = ddWashers;
        }

        public String getDdSecurityConditionOfBodyWings() {
            return ddSecurityConditionOfBodyWings;
        }

        public void setDdSecurityConditionOfBodyWings(String ddSecurityConditionOfBodyWings) {
            this.ddSecurityConditionOfBodyWings = ddSecurityConditionOfBodyWings;
        }

        public String getDdSecurityOfLoadVehicleHeight() {
            return ddSecurityOfLoadVehicleHeight;
        }

        public void setDdSecurityOfLoadVehicleHeight(String ddSecurityOfLoadVehicleHeight) {
            this.ddSecurityOfLoadVehicleHeight = ddSecurityOfLoadVehicleHeight;
        }

        public String getDdHorn() {
            return ddHorn;
        }

        public void setDdHorn(String ddHorn) {
            this.ddHorn = ddHorn;
        }

        public String getDdRegistrationPlates() {
            return ddRegistrationPlates;
        }

        public void setDdRegistrationPlates(String ddRegistrationPlates) {
            this.ddRegistrationPlates = ddRegistrationPlates;
        }

        public String getDdMirrorsGlassVisibility() {
            return ddMirrorsGlassVisibility;
        }

        public void setDdMirrorsGlassVisibility(String ddMirrorsGlassVisibility) {
            this.ddMirrorsGlassVisibility = ddMirrorsGlassVisibility;
        }

        public String getDdExcessiveEngineExhaustSmoke() {
            return ddExcessiveEngineExhaustSmoke;
        }

        public void setDdExcessiveEngineExhaustSmoke(String ddExcessiveEngineExhaustSmoke) {
            this.ddExcessiveEngineExhaustSmoke = ddExcessiveEngineExhaustSmoke;
        }

        public String getDdCabInteriorSeatBelts() {
            return ddCabInteriorSeatBelts;
        }

        public void setDdCabInteriorSeatBelts(String ddCabInteriorSeatBelts) {
            this.ddCabInteriorSeatBelts = ddCabInteriorSeatBelts;
        }

        public String getDdAirBuildUpLeaks() {
            return ddAirBuildUpLeaks;
        }

        public void setDdAirBuildUpLeaks(String ddAirBuildUpLeaks) {
            this.ddAirBuildUpLeaks = ddAirBuildUpLeaks;
        }

        public String getDdAdblueIfRequired() {
            return ddAdblueIfRequired;
        }

        public void setDdAdblueIfRequired(String ddAdblueIfRequired) {
            this.ddAdblueIfRequired = ddAdblueIfRequired;
        }

        public String getDdWarningLampsMil() {
            return ddWarningLampsMil;
        }

        public void setDdWarningLampsMil(String ddWarningLampsMil) {
            this.ddWarningLampsMil = ddWarningLampsMil;
        }

        public String getDdReportDefectsHere() {
            return ddReportDefectsHere;
        }

        public void setDdReportDefectsHere(String ddReportDefectsHere) {
            this.ddReportDefectsHere = ddReportDefectsHere;
        }

        public String getDdDefectAssessmentAndRectification() {
            return ddDefectAssessmentAndRectification;
        }

        public void setDdDefectAssessmentAndRectification(String ddDefectAssessmentAndRectification) {
            this.ddDefectAssessmentAndRectification = ddDefectAssessmentAndRectification;
        }

        public String getDdDefectReportedTo() {
            return ddDefectReportedTo;
        }

        public void setDdDefectReportedTo(String ddDefectReportedTo) {
            this.ddDefectReportedTo = ddDefectReportedTo;
        }

        public String getDdWriteNillHere() {
            return ddWriteNillHere;
        }

        public void setDdWriteNillHere(String ddWriteNillHere) {
            this.ddWriteNillHere = ddWriteNillHere;
        }

        public String getDdDriversSignature() {
            return ddDriversSignature;
        }

        public void setDdDriversSignature(String ddDriversSignature) {
            this.ddDriversSignature = ddDriversSignature;
        }

        public Object getDdDriversSignatureDate() {
            return ddDriversSignatureDate;
        }

        public void setDdDriversSignatureDate(Object ddDriversSignatureDate) {
            this.ddDriversSignatureDate = ddDriversSignatureDate;
        }

        public String getDdDefectsRectifiedBy() {
            return ddDefectsRectifiedBy;
        }

        public void setDdDefectsRectifiedBy(String ddDefectsRectifiedBy) {
            this.ddDefectsRectifiedBy = ddDefectsRectifiedBy;
        }

        public String getDdSignature() {
            return ddSignature;
        }

        public void setDdSignature(String ddSignature) {
            this.ddSignature = ddSignature;
        }

        public Object getDdSignatureDate() {
            return ddSignatureDate;
        }

        public void setDdSignatureDate(Object ddSignatureDate) {
            this.ddSignatureDate = ddSignatureDate;
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

        public String getDdPdf() {
            return ddPdf;
        }

        public void setDdPdf(String ddPdf) {
            this.ddPdf = ddPdf;
        }

        public String getSiteName() {
            return siteName;
        }

        public void setSiteName(String siteName) {
            this.siteName = siteName;
        }

    }
}
