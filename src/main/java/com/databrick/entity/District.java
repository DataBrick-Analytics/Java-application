package com.databrick.entity;

public class District {
    private String districtName;
    private Long districtCode;
    private Double totalArea;
    private String zone;
    private Integer populationTotal;

    public District(String districtName, String districtCode, String totalArea, String zone, String populationTotal) {
        this.districtName = districtName;
        this.districtCode = parseLong(districtCode);
        this.totalArea = parseDouble(totalArea);
        this.zone = zone;
        this.populationTotal = parseInteger(populationTotal);
    }

    private Integer parseInteger(String value) {
        if (value == null || value.isBlank()) return null;
        try {
            return Integer.valueOf(value.replace(",", "").replace(".", ""));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Long parseLong(String value) {
        if (value == null || value.isBlank()) return null;
        try {
            return Long.valueOf(value.replace(",", "").replace(".", ""));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Double parseDouble(String valor) {
        if (valor == null || valor.isBlank()) return 0.0;
        try {
            Double raw = Double.parseDouble(valor.replace(",", "."));
            return Math.floor(raw * 100) / 100.0;
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public Integer getPopulationTotal() {
        return populationTotal;
    }

    public void setPopulationTotal(Integer populationTotal) {
        this.populationTotal = populationTotal;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public Double getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(Double totalArea) {
        this.totalArea = totalArea;
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
