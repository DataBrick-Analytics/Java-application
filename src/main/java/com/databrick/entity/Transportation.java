package com.databrick.entity;

public class Transportation {
    private String districtName;
    private Integer busStops;
    private Integer trainOrSubwayStations;
    private Long districtCode;

    public Transportation(String districtName, String busStops, String trainOrSubwayStations, String districtCode) {
        this.districtName = districtName;
        this.busStops = parseInteger(busStops);
        this.trainOrSubwayStations = parseInteger(trainOrSubwayStations);
        this.districtCode = parseLong(districtCode);
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

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public Integer getBusStops() {
        return busStops;
    }

    public void setBusStops(Integer busStops) {
        this.busStops = busStops;
    }

    public Integer getTrainOrSubwayStations() {
        return trainOrSubwayStations;
    }

    public void setTrainOrSubwayStations(Integer trainOrSubwayStations) {
        this.trainOrSubwayStations = trainOrSubwayStations;
    }

    public Long getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(Long districtCode) {
        this.districtCode = districtCode;
    }
}
