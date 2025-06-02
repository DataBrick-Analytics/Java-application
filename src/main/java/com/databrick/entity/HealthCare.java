package com.databrick.entity;

public class HealthCare {
    private Long districtCode;
    private String districtName;
    private String unitName;
    private String neighborhood;
    private String unitType;

    public HealthCare(String districtCode, String districtName, String unitName, String unitType, String neighborhood) {
        this.districtCode = parseLong(districtCode);
        this.districtName = districtName;
        this.unitName = unitName;
        this.unitType = unitType;
        this.neighborhood = neighborhood;
    }

    private Long parseLong(String value) {
        if (value == null || value.isBlank()) return null;
        try {
            return Long.valueOf(value.replace(",", "").replace(".", ""));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Long getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(Long districtCode) {
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
