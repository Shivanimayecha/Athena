package com.athena.group.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class HSQuestionModel {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("data")
    @Expose
    private ArrayList<Data> data = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    /*public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }*/

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class Data {

        @SerializedName("HSEQ Inspections")
        @Expose
        private List<HSEQInspection> hSEQInspections = null;
        @SerializedName("Inspection and Reporting")
        @Expose
        private List<InspectionAndReporting> inspectionAndReporting = null;
        @SerializedName("Documentation")
        @Expose
        private List<Documentation> documentation = null;
        @SerializedName("Competence & Training")
        @Expose
        private List<CompetenceTraining> competenceTraining = null;
        @SerializedName("Welfare")
        @Expose
        private List<Welfare> welfare = null;
        @SerializedName("Emergency Procedures")
        @Expose
        private List<EmergencyProcedure> emergencyProcedures = null;
        @SerializedName("Risk Assessment/Method Statements(RAMS)")
        @Expose
        private List<RiskAssessmentMethodStatementsRAMS> riskAssessmentMethodStatementsRAMS = null;
        @SerializedName("Risk Assessment/Method Statements (RAMS)")
        @Expose
        private List<RiskAssessmentMethodStatements_RAMS> riskAssessmentMethodStatements_RAMS = null;
        @SerializedName("NB")
        @Expose
        private List<NB> nB = null;
        @SerializedName("Existing Services")
        @Expose
        private List<Existing_Services> Existing_Services = null;
        @SerializedName("Permits to Work")
        @Expose
        private List<PermitsToWork> permitsToWork = null;
        @SerializedName("Traffic Management")
        @Expose
        private List<TrafficManagement> trafficManagement = null;
        @SerializedName("Vehicles, Vehicle Movements, Plant and Machinery")
        @Expose
        private List<VehiclesVehicleMovementsPlantAndMachinery> vehiclesVehicleMovementsPlantAndMachinery = null;
        @SerializedName("Portable Tools")
        @Expose
        private List<PortableTool> portableTools = null;
        @SerializedName("Noise, Vibration and Dust")
        @Expose
        private List<NoiseVibrationAndDust> noiseVibrationAndDust = null;
        @SerializedName("Excavations")
        @Expose
        private List<Excavation> excavations = null;
        @SerializedName("Protecting the Public and Site Security")
        @Expose
        private List<ProtectingThePublicAndSiteSecurity> protectingThePublicAndSiteSecurity = null;
        @SerializedName("Plant")
        @Expose
        private List<Plant> plant = null;
        @SerializedName("Materials")
        @Expose
        private List<Material> materials = null;
        @SerializedName("Visual checks")
        @Expose
        private List<VisualCheck> visualChecks = null;

        @SerializedName("Communication, Co-ordination, Competence and Training")
        @Expose
        private List<CommunicationCoOrdinationCompetenceAndTraining> communicationCoOrdinationCompetenceAndTraining = null;
        @SerializedName("Emergencies and Welfare")
        @Expose
        private List<EmergenciesAndWelfare> emergenciesAndWelfare = null;

        @SerializedName("Traffic Management and Pedestrian Access / Egress")
        @Expose
        private List<TrafficManagementAndPedestrianAccessEgress> trafficManagementAndPedestrianAccessEgress = null;

        @SerializedName("Lifting Equipment and Operations")
        @Expose
        private List<LiftingEquipmentAndOperation> liftingEquipmentAndOperations = null;
        @SerializedName("Housekeeping, Access and Egress")
        @Expose
        private List<HousekeepingAccessAndEgress> housekeepingAccessAndEgress = null;

        @SerializedName("Access to Height (Scaffold,/Podiums/Ladders)")
        @Expose
        private List<AccessToHeightScaffoldPodiumsLadders> accessToHeightScaffoldPodiumsLadders = null;
        @SerializedName("Powered Access Equipment i.e. MEWP")
        @Expose
        private List<PoweredAccessEquipmentIEMEWP> poweredAccessEquipmentIEMEWP = null;
        @SerializedName("COSHH")
        @Expose
        private List<COSHH> cOSHH = null;
        @SerializedName("Manual Handling")
        @Expose
        private List<ManualHandling> manualHandling = null;
        @SerializedName("Confined Spaces")
        @Expose
        private List<ConfinedSpace> confinedSpaces = null;
        @SerializedName("Temporary Works")
        @Expose
        private List<TemporaryWork> temporaryWorks = null;
        @SerializedName("Control of Subcontractors")
        @Expose
        private List<ControlOfSubcontractor> controlOfSubcontractors = null;
        @SerializedName("PPE")
        @Expose
        private List<PPE> pPE = null;

        public List<RiskAssessmentMethodStatements_RAMS> getRiskAssessmentMethodStatements_RAMS() {
            return riskAssessmentMethodStatements_RAMS;
        }

        public void setRiskAssessmentMethodStatements_RAMS(List<RiskAssessmentMethodStatements_RAMS> riskAssessmentMethodStatements_RAMS) {
            this.riskAssessmentMethodStatements_RAMS = riskAssessmentMethodStatements_RAMS;
        }

        public List<CommunicationCoOrdinationCompetenceAndTraining> getCommunicationCoOrdinationCompetenceAndTraining() {
            return communicationCoOrdinationCompetenceAndTraining;
        }

        public void setCommunicationCoOrdinationCompetenceAndTraining(List<CommunicationCoOrdinationCompetenceAndTraining> communicationCoOrdinationCompetenceAndTraining) {
            this.communicationCoOrdinationCompetenceAndTraining = communicationCoOrdinationCompetenceAndTraining;
        }

        public List<EmergenciesAndWelfare> getEmergenciesAndWelfare() {
            return emergenciesAndWelfare;
        }

        public void setEmergenciesAndWelfare(List<EmergenciesAndWelfare> emergenciesAndWelfare) {
            this.emergenciesAndWelfare = emergenciesAndWelfare;
        }

        public List<TrafficManagementAndPedestrianAccessEgress> getTrafficManagementAndPedestrianAccessEgress() {
            return trafficManagementAndPedestrianAccessEgress;
        }

        public void setTrafficManagementAndPedestrianAccessEgress(List<TrafficManagementAndPedestrianAccessEgress> trafficManagementAndPedestrianAccessEgress) {
            this.trafficManagementAndPedestrianAccessEgress = trafficManagementAndPedestrianAccessEgress;
        }

        public List<LiftingEquipmentAndOperation> getLiftingEquipmentAndOperations() {
            return liftingEquipmentAndOperations;
        }

        public void setLiftingEquipmentAndOperations(List<LiftingEquipmentAndOperation> liftingEquipmentAndOperations) {
            this.liftingEquipmentAndOperations = liftingEquipmentAndOperations;
        }

        public List<HousekeepingAccessAndEgress> getHousekeepingAccessAndEgress() {
            return housekeepingAccessAndEgress;
        }

        public void setHousekeepingAccessAndEgress(List<HousekeepingAccessAndEgress> housekeepingAccessAndEgress) {
            this.housekeepingAccessAndEgress = housekeepingAccessAndEgress;
        }

        public List<AccessToHeightScaffoldPodiumsLadders> getAccessToHeightScaffoldPodiumsLadders() {
            return accessToHeightScaffoldPodiumsLadders;
        }

        public void setAccessToHeightScaffoldPodiumsLadders(List<AccessToHeightScaffoldPodiumsLadders> accessToHeightScaffoldPodiumsLadders) {
            this.accessToHeightScaffoldPodiumsLadders = accessToHeightScaffoldPodiumsLadders;
        }

        public List<PoweredAccessEquipmentIEMEWP> getPoweredAccessEquipmentIEMEWP() {
            return poweredAccessEquipmentIEMEWP;
        }

        public void setPoweredAccessEquipmentIEMEWP(List<PoweredAccessEquipmentIEMEWP> poweredAccessEquipmentIEMEWP) {
            this.poweredAccessEquipmentIEMEWP = poweredAccessEquipmentIEMEWP;
        }

        public List<COSHH> getcOSHH() {
            return cOSHH;
        }

        public void setcOSHH(List<COSHH> cOSHH) {
            this.cOSHH = cOSHH;
        }

        public List<ManualHandling> getManualHandling() {
            return manualHandling;
        }

        public void setManualHandling(List<ManualHandling> manualHandling) {
            this.manualHandling = manualHandling;
        }

        public List<ConfinedSpace> getConfinedSpaces() {
            return confinedSpaces;
        }

        public void setConfinedSpaces(List<ConfinedSpace> confinedSpaces) {
            this.confinedSpaces = confinedSpaces;
        }

        public List<TemporaryWork> getTemporaryWorks() {
            return temporaryWorks;
        }

        public void setTemporaryWorks(List<TemporaryWork> temporaryWorks) {
            this.temporaryWorks = temporaryWorks;
        }

        public List<ControlOfSubcontractor> getControlOfSubcontractors() {
            return controlOfSubcontractors;
        }

        public void setControlOfSubcontractors(List<ControlOfSubcontractor> controlOfSubcontractors) {
            this.controlOfSubcontractors = controlOfSubcontractors;
        }

        public List<PPE> getpPE() {
            return pPE;
        }

        public void setpPE(List<PPE> pPE) {
            this.pPE = pPE;
        }

        public List<HSEQInspection> getHSEQInspections() {
            return hSEQInspections;
        }

        public void setHSEQInspections(List<HSEQInspection> hSEQInspections) {
            this.hSEQInspections = hSEQInspections;
        }

        public List<InspectionAndReporting> getInspectionAndReporting() {
            return inspectionAndReporting;
        }

        public void setInspectionAndReporting(List<InspectionAndReporting> inspectionAndReporting) {
            this.inspectionAndReporting = inspectionAndReporting;
        }

        public List<Documentation> getDocumentation() {
            return documentation;
        }

        public void setDocumentation(List<Documentation> documentation) {
            this.documentation = documentation;
        }

        public List<CompetenceTraining> getCompetenceTraining() {
            return competenceTraining;
        }

        public void setCompetenceTraining(List<CompetenceTraining> competenceTraining) {
            this.competenceTraining = competenceTraining;
        }

        public List<Welfare> getWelfare() {
            return welfare;
        }

        public void setWelfare(List<Welfare> welfare) {
            this.welfare = welfare;
        }

        public List<EmergencyProcedure> getEmergencyProcedures() {
            return emergencyProcedures;
        }

        public void setEmergencyProcedures(List<EmergencyProcedure> emergencyProcedures) {
            this.emergencyProcedures = emergencyProcedures;
        }

        public List<RiskAssessmentMethodStatementsRAMS> getRiskAssessmentMethodStatementsRAMS() {
            return riskAssessmentMethodStatementsRAMS;
        }

        public void setRiskAssessmentMethodStatementsRAMS(List<RiskAssessmentMethodStatementsRAMS> riskAssessmentMethodStatementsRAMS) {
            this.riskAssessmentMethodStatementsRAMS = riskAssessmentMethodStatementsRAMS;
        }

        public List<NB> getNB() {
            return nB;
        }

        public void setNB(List<NB> nB) {
            this.nB = nB;
        }

        public List<Existing_Services> getExisting_Services() {
            return Existing_Services;
        }

        public void setExisting_Services(List<Existing_Services> existing_Services) {
            Existing_Services = existing_Services;
        }

        public List<PermitsToWork> getPermitsToWork() {
            return permitsToWork;
        }

        public void setPermitsToWork(List<PermitsToWork> permitsToWork) {
            this.permitsToWork = permitsToWork;
        }

        public List<TrafficManagement> getTrafficManagement() {
            return trafficManagement;
        }

        public void setTrafficManagement(List<TrafficManagement> trafficManagement) {
            this.trafficManagement = trafficManagement;
        }

        public List<VehiclesVehicleMovementsPlantAndMachinery> getVehiclesVehicleMovementsPlantAndMachinery() {
            return vehiclesVehicleMovementsPlantAndMachinery;
        }

        public void setVehiclesVehicleMovementsPlantAndMachinery(List<VehiclesVehicleMovementsPlantAndMachinery> vehiclesVehicleMovementsPlantAndMachinery) {
            this.vehiclesVehicleMovementsPlantAndMachinery = vehiclesVehicleMovementsPlantAndMachinery;
        }

        public List<PortableTool> getPortableTools() {
            return portableTools;
        }

        public void setPortableTools(List<PortableTool> portableTools) {
            this.portableTools = portableTools;
        }

        public List<NoiseVibrationAndDust> getNoiseVibrationAndDust() {
            return noiseVibrationAndDust;
        }

        public void setNoiseVibrationAndDust(List<NoiseVibrationAndDust> noiseVibrationAndDust) {
            this.noiseVibrationAndDust = noiseVibrationAndDust;
        }

        public List<Excavation> getExcavations() {
            return excavations;
        }

        public void setExcavations(List<Excavation> excavations) {
            this.excavations = excavations;
        }

        public List<ProtectingThePublicAndSiteSecurity> getProtectingThePublicAndSiteSecurity() {
            return protectingThePublicAndSiteSecurity;
        }

        public void setProtectingThePublicAndSiteSecurity(List<ProtectingThePublicAndSiteSecurity> protectingThePublicAndSiteSecurity) {
            this.protectingThePublicAndSiteSecurity = protectingThePublicAndSiteSecurity;
        }

        public List<Plant> getPlant() {
            return plant;
        }

        public void setPlant(List<Plant> plant) {
            this.plant = plant;
        }

        public List<Material> getMaterials() {
            return materials;
        }

        public void setMaterials(List<Material> materials) {
            this.materials = materials;
        }

        public List<VisualCheck> getVisualChecks() {
            return visualChecks;
        }

        public void setVisualChecks(List<VisualCheck> visualChecks) {
            this.visualChecks = visualChecks;
        }

    }

    public class ControlOfSubcontractor {

        @SerializedName("hs_id")
        @Expose
        private String hsId;
        @SerializedName("hs_label")
        @Expose
        private String hsLabel;
        @SerializedName("hs_ques")
        @Expose
        private String hsQues;
        @SerializedName("status")
        @Expose
        private String status;


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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

    public class PPE {

        @SerializedName("hs_id")
        @Expose
        private String hsId;
        @SerializedName("hs_label")
        @Expose
        private String hsLabel;
        @SerializedName("hs_ques")
        @Expose
        private String hsQues;
        @SerializedName("status")
        @Expose
        private String status;


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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

    public class CompetenceTraining {

        @SerializedName("hs_id")
        @Expose
        private String hsId;
        @SerializedName("hs_label")
        @Expose
        private String hsLabel;
        @SerializedName("hs_ques")
        @Expose
        private String hsQues;
        @SerializedName("status")
        @Expose
        private String status;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

    public class Documentation {

        @SerializedName("hs_id")
        @Expose
        private String hsId;
        @SerializedName("hs_label")
        @Expose
        private String hsLabel;
        @SerializedName("hs_ques")
        @Expose
        private String hsQues;
        @SerializedName("status")
        @Expose
        private String status;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

    public class EmergencyProcedure {

        @SerializedName("hs_id")
        @Expose
        private String hsId;
        @SerializedName("hs_label")
        @Expose
        private String hsLabel;
        @SerializedName("hs_ques")
        @Expose
        private String hsQues;
        @SerializedName("status")
        @Expose
        private String status;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }


    }

    public class Excavation {

        @SerializedName("hs_id")
        @Expose
        private String hsId;
        @SerializedName("hs_label")
        @Expose
        private String hsLabel;
        @SerializedName("hs_ques")
        @Expose
        private String hsQues;
        @SerializedName("status")
        @Expose
        private String status;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }


    }


    public class HSEQInspection {

        @SerializedName("hs_id")
        @Expose
        private String hsId;
        @SerializedName("hs_label")
        @Expose
        private String hsLabel;
        @SerializedName("hs_ques")
        @Expose
        private String hsQues;
        @SerializedName("status")
        @Expose
        private String status;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

    public class InspectionAndReporting {

        @SerializedName("hs_id")
        @Expose
        private String hsId;
        @SerializedName("hs_label")
        @Expose
        private String hsLabel;
        @SerializedName("hs_ques")
        @Expose
        private String hsQues;
        @SerializedName("status")
        @Expose
        private String status;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

    public class Material {

        @SerializedName("hs_id")
        @Expose
        private String hsId;
        @SerializedName("hs_label")
        @Expose
        private String hsLabel;
        @SerializedName("hs_ques")
        @Expose
        private String hsQues;
        @SerializedName("status")
        @Expose
        private String status;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

    public class Existing_Services {

        @SerializedName("hs_id")
        @Expose
        private String hsId;
        @SerializedName("hs_label")
        @Expose
        private String hsLabel;
        @SerializedName("hs_ques")
        @Expose
        private String hsQues;
        @SerializedName("status")
        @Expose
        private String status;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }


    public class NB {

        @SerializedName("hs_id")
        @Expose
        private String hsId;
        @SerializedName("hs_label")
        @Expose
        private String hsLabel;
        @SerializedName("hs_ques")
        @Expose
        private String hsQues;
        @SerializedName("status")
        @Expose
        private String status;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

    public class NoiseVibrationAndDust {

        @SerializedName("hs_id")
        @Expose
        private String hsId;
        @SerializedName("hs_label")
        @Expose
        private String hsLabel;
        @SerializedName("hs_ques")
        @Expose
        private String hsQues;
        @SerializedName("status")
        @Expose
        private String status;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }


    public class PermitsToWork {

        @SerializedName("hs_id")
        @Expose
        private String hsId;
        @SerializedName("hs_label")
        @Expose
        private String hsLabel;
        @SerializedName("hs_ques")
        @Expose
        private String hsQues;
        @SerializedName("status")
        @Expose
        private String status;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }


    public class Plant {

        @SerializedName("hs_id")
        @Expose
        private String hsId;
        @SerializedName("hs_label")
        @Expose
        private String hsLabel;
        @SerializedName("hs_ques")
        @Expose
        private String hsQues;
        @SerializedName("status")
        @Expose
        private String status;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public class PortableTool {

        @SerializedName("hs_id")
        @Expose
        private String hsId;
        @SerializedName("hs_label")
        @Expose
        private String hsLabel;
        @SerializedName("hs_ques")
        @Expose
        private String hsQues;
        @SerializedName("status")
        @Expose
        private String status;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

    public class ProtectingThePublicAndSiteSecurity {

        @SerializedName("hs_id")
        @Expose
        private String hsId;
        @SerializedName("hs_label")
        @Expose
        private String hsLabel;
        @SerializedName("hs_ques")
        @Expose
        private String hsQues;
        @SerializedName("status")
        @Expose
        private String status;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

    public class RiskAssessmentMethodStatements_RAMS {

        @SerializedName("hs_id")
        @Expose
        private String hsId;
        @SerializedName("hs_label")
        @Expose
        private String hsLabel;
        @SerializedName("hs_ques")
        @Expose
        private String hsQues;
        @SerializedName("status")
        @Expose
        private String status;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

    public class RiskAssessmentMethodStatementsRAMS {

        @SerializedName("hs_id")
        @Expose
        private String hsId;
        @SerializedName("hs_label")
        @Expose
        private String hsLabel;
        @SerializedName("hs_ques")
        @Expose
        private String hsQues;
        @SerializedName("status")
        @Expose
        private String status;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

    public class TrafficManagement {

        @SerializedName("hs_id")
        @Expose
        private String hsId;
        @SerializedName("hs_label")
        @Expose
        private String hsLabel;
        @SerializedName("hs_ques")
        @Expose
        private String hsQues;
        @SerializedName("status")
        @Expose
        private String status;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public class VehiclesVehicleMovementsPlantAndMachinery {

        @SerializedName("hs_id")
        @Expose
        private String hsId;
        @SerializedName("hs_label")
        @Expose
        private String hsLabel;
        @SerializedName("hs_ques")
        @Expose
        private String hsQues;
        @SerializedName("status")
        @Expose
        private String status;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public class VisualCheck {

        @SerializedName("hs_id")
        @Expose
        private String hsId;
        @SerializedName("hs_label")
        @Expose
        private String hsLabel;
        @SerializedName("hs_ques")
        @Expose
        private String hsQues;
        @SerializedName("status")
        @Expose
        private String status;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public class Welfare {

        @SerializedName("hs_id")
        @Expose
        private String hsId;
        @SerializedName("hs_label")
        @Expose
        private String hsLabel;
        @SerializedName("hs_ques")
        @Expose
        private String hsQues;
        @SerializedName("status")
        @Expose
        private String status;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

    public class TemporaryWork {
        @SerializedName("hs_id")
        @Expose
        private String hsId;
        @SerializedName("hs_label")
        @Expose
        private String hsLabel;
        @SerializedName("hs_ques")
        @Expose
        private String hsQues;
        @SerializedName("status")
        @Expose
        private String status;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public class CommunicationCoOrdinationCompetenceAndTraining {
        @SerializedName("hs_id")
        @Expose
        private String hsId;
        @SerializedName("hs_label")
        @Expose
        private String hsLabel;
        @SerializedName("hs_ques")
        @Expose
        private String hsQues;
        @SerializedName("status")
        @Expose
        private String status;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public class EmergenciesAndWelfare {
        @SerializedName("hs_id")
        @Expose
        private String hsId;
        @SerializedName("hs_label")
        @Expose
        private String hsLabel;
        @SerializedName("hs_ques")
        @Expose
        private String hsQues;
        @SerializedName("status")
        @Expose
        private String status;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public class TrafficManagementAndPedestrianAccessEgress {
        @SerializedName("hs_id")
        @Expose
        private String hsId;
        @SerializedName("hs_label")
        @Expose
        private String hsLabel;
        @SerializedName("hs_ques")
        @Expose
        private String hsQues;
        @SerializedName("status")
        @Expose
        private String status;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public class LiftingEquipmentAndOperation {
        @SerializedName("hs_id")
        @Expose
        private String hsId;
        @SerializedName("hs_label")
        @Expose
        private String hsLabel;
        @SerializedName("hs_ques")
        @Expose
        private String hsQues;
        @SerializedName("status")
        @Expose
        private String status;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public class HousekeepingAccessAndEgress {
        @SerializedName("hs_id")
        @Expose
        private String hsId;
        @SerializedName("hs_label")
        @Expose
        private String hsLabel;
        @SerializedName("hs_ques")
        @Expose
        private String hsQues;
        @SerializedName("status")
        @Expose
        private String status;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public class AccessToHeightScaffoldPodiumsLadders {
        @SerializedName("hs_id")
        @Expose
        private String hsId;
        @SerializedName("hs_label")
        @Expose
        private String hsLabel;
        @SerializedName("hs_ques")
        @Expose
        private String hsQues;
        @SerializedName("status")
        @Expose
        private String status;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public class PoweredAccessEquipmentIEMEWP {
        @SerializedName("hs_id")
        @Expose
        private String hsId;
        @SerializedName("hs_label")
        @Expose
        private String hsLabel;
        @SerializedName("hs_ques")
        @Expose
        private String hsQues;
        @SerializedName("status")
        @Expose
        private String status;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public class COSHH {
        @SerializedName("hs_id")
        @Expose
        private String hsId;
        @SerializedName("hs_label")
        @Expose
        private String hsLabel;
        @SerializedName("hs_ques")
        @Expose
        private String hsQues;
        @SerializedName("status")
        @Expose
        private String status;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public class ManualHandling {
        @SerializedName("hs_id")
        @Expose
        private String hsId;
        @SerializedName("hs_label")
        @Expose
        private String hsLabel;
        @SerializedName("hs_ques")
        @Expose
        private String hsQues;
        @SerializedName("status")
        @Expose
        private String status;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public class ConfinedSpace {
        @SerializedName("hs_id")
        @Expose
        private String hsId;
        @SerializedName("hs_label")
        @Expose
        private String hsLabel;
        @SerializedName("hs_ques")
        @Expose
        private String hsQues;
        @SerializedName("status")
        @Expose
        private String status;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
