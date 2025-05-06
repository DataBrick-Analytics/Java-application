package com.databrick.entity;

public class Security {

    private Integer id;
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
    private Integer lastYearCollected;

    public Security() {
    }

    public Security(String policeStation, String theftsByRegion, String cargoRobbery, String robberies, String vehicleRobbery, String vehicleTheft, String violentThefts, String intentionalHomicideTraffic, String unintentionalHomicideTraffic, String unintentionalHomicide, String lastYearCollected) {
        this.policeStation = policeStation;
        this.theftsByRegion = parseInteger(theftsByRegion);
        this.cargoRobbery = parseInteger(cargoRobbery);
        this.robberies = parseInteger(robberies);
        this.vehicleRobbery = parseInteger(vehicleRobbery);
        this.vehicleTheft = parseInteger(vehicleTheft);
        this.violentThefts = parseInteger(violentThefts);
        this.intentionalHomicideTraffic = parseInteger(intentionalHomicideTraffic);
        this.unintentionalHomicideTraffic = parseInteger(unintentionalHomicideTraffic);
        this.unintentionalHomicide = parseInteger(unintentionalHomicide);
        this.lastYearCollected = parseInteger(lastYearCollected);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getLastYearCollected() {
        return lastYearCollected;
    }

    public void setLastYearCollected(Integer lastYearCollected) {
        this.lastYearCollected = lastYearCollected;
    }

    private Integer parseInteger(String value) {
        if (value == null || value.isBlank()) return null;
        try {
            return Integer.valueOf(value.replace(",", ""));
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
