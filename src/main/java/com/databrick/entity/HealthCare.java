package com.databrick.entity;

public class HealthCare {
    private Integer districtCode;
    private String districtName;
    private String unitName;
    private String neighborhood;
    private String unitType;

    public HealthCare(String districtCode, String districtName, String unitName, String unitType, String neighborhood) {
        this.districtCode = parseInteger(districtCode);
        this.districtName = districtName;
        this.unitName = unitName;
        this.unitType = unitType;
        this.neighborhood = neighborhood;
    }

    private Integer parseInteger(String value) {
        if (value == null || value.isBlank()) return null;
        try {
            return Integer.valueOf(value.replace(",", "").replace(".", ""));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Integer getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(Integer districtCode) {
        this.districtCode = districtCode;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }
}
