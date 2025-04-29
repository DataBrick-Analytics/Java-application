package com.databrick.entity;

public class Security {

    private String policeStation;
    private Integer theftsByRegion;
    private Integer cargoRobbery;
    private Integer robberies;
    private Integer vehicleRobbery;
    private Integer vehicleTheft;
    private Integer violentThefts;
    private Integer intentionalHomicideTraffic;
    private Integer unintentionalHomicideTraffic;
    private Integer unintentionalHomicide;
    private String region;

    public Security(String policeStation, String theftsByRegion, String cargoRobbery, String robberies, String vehicleRobbery, String vehicleTheft, String violentThefts, String intentionalHomicideTraffic, String unintentionalHomicideTraffic, String unintentionalHomicide, String region, String regionId) {
        this.policeStation = policeStation;
        this.theftsByRegion = Integer.valueOf(theftsByRegion);
        this.cargoRobbery = Integer.valueOf(cargoRobbery);
        this.robberies = Integer.valueOf(robberies);
        this.vehicleRobbery = Integer.valueOf(vehicleRobbery);
        this.vehicleTheft = Integer.valueOf(vehicleTheft);
        this.violentThefts = Integer.valueOf(violentThefts);
        this.intentionalHomicideTraffic = Integer.valueOf(intentionalHomicideTraffic);
        this.unintentionalHomicideTraffic = Integer.valueOf(unintentionalHomicideTraffic);
        this.unintentionalHomicide = Integer.valueOf(unintentionalHomicide);
        this.region = region;
    }

    public String getPoliceStation() {
        return policeStation;
    }

    public void setPoliceStation(String policeStation) {
        this.policeStation = policeStation;
    }

    public Integer getTheftsByRegion() {
        return theftsByRegion;
    }

    public void setTheftsByRegion(Integer theftsByRegion) {
        this.theftsByRegion = theftsByRegion;
    }

    public Integer getCargoRobbery() {
        return cargoRobbery;
    }

    public void setCargoRobbery(Integer cargoRobbery) {
        this.cargoRobbery = cargoRobbery;
    }

    public Integer getRobberies() {
        return robberies;
    }

    public void setRobberies(Integer robberies) {
        this.robberies = robberies;
    }

    public Integer getVehicleRobbery() {
        return vehicleRobbery;
    }

    public void setVehicleRobbery(Integer vehicleRobbery) {
        this.vehicleRobbery = vehicleRobbery;
    }

    public Integer getVehicleTheft() {
        return vehicleTheft;
    }

    public void setVehicleTheft(Integer vehicleTheft) {
        this.vehicleTheft = vehicleTheft;
    }

    public Integer getViolentThefts() {
        return violentThefts;
    }

    public void setViolentThefts(Integer violentThefts) {
        this.violentThefts = violentThefts;
    }

    public Integer getIntentionalHomicideTraffic() {
        return intentionalHomicideTraffic;
    }

    public void setIntentionalHomicideTraffic(Integer intentionalHomicideTraffic) {
        this.intentionalHomicideTraffic = intentionalHomicideTraffic;
    }

    public Integer getUnintentionalHomicideTraffic() {
        return unintentionalHomicideTraffic;
    }

    public void setUnintentionalHomicideTraffic(Integer unintentionalHomicideTraffic) {
        this.unintentionalHomicideTraffic = unintentionalHomicideTraffic;
    }

    public Integer getUnintentionalHomicide() {
        return unintentionalHomicide;
    }

    public void setUnintentionalHomicide(Integer unintentionalHomicide) {
        this.unintentionalHomicide = unintentionalHomicide;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }
}
