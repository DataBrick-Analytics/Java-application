package com.databrick.entity;

public class EducationSystem {
    private String schoolName;
    private String neighborhood;
    private String districtName;
    private Long districtCode;

    public EducationSystem(String schoolName, String neighborhood, String districtName, String districtCode) {
        this.schoolName = schoolName;
        this.neighborhood = neighborhood;
        this.districtName = districtName;
        this.districtCode = parseLong(districtCode);
    }

    private Long parseLong(String value) {
        if (value == null || value.isBlank()) return null;
        try {
            return Long.valueOf(value.replace(",", "").replace(".", ""));
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

    public Long getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(Long districtCode) {
        this.districtCode = districtCode;
    }
}
