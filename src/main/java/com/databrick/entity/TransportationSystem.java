package com.databrick.entity;

public class TransportationSystem {
    private String districtName;
    private Integer districtCode;
    private Integer busStops;
    private Integer metroStops;
    private Integer trainStops;

    public TransportationSystem(String districtName, String districtCode, String busStops, String metroStops, String trainStops) {
        this.districtName = districtName;
        this.districtCode = parseInteger(districtCode);
        this.busStops = parseInteger(busStops);
        this.metroStops = parseInteger(metroStops);
        this.trainStops = parseInteger(trainStops);
    }

    private Integer parseInteger(String value) {
        if (value == null || value.isBlank()) return null;
        try {
            return Integer.valueOf(value.replace(",", "").replace(".", ""));
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

    public Integer getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(Integer districtCode) {
        this.districtCode = districtCode;
    }

    public Integer getBusStops() {
        return busStops;
    }

    public void setBusStops(Integer busStops) {
        this.busStops = busStops;
    }

    public Integer getMetroStops() {
        return metroStops;
    }

    public void setMetroStops(Integer metroStops) {
        this.metroStops = metroStops;
    }

    public Integer getTrainStops() {
        return trainStops;
    }

    public void setTrainStops(Integer trainStops) {
        this.trainStops = trainStops;
    }
}
