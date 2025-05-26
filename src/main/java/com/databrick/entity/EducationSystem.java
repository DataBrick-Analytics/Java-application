package com.databrick.entity;

public class EducationSystem {
    private String schoolName;
    private String neighborhood;
    private String districtName;
    private Integer districtCode;

    public EducationSystem(String schoolName, String neighborhood, String districtName, String districtCode) {
        this.schoolName = schoolName;
        this.neighborhood = neighborhood;
        this.districtName = districtName;
        this.districtCode = parseInteger(districtCode);
    }

    private Integer parseInteger(String value) {
        if (value == null || value.isBlank()) return null;
        try {
            return Integer.valueOf(value.replace(",", "").replace(".", ""));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public Integer getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(Integer districtCode) {
        this.districtCode = districtCode;
    }
}
