package com.databrick.entity;


public class Region {

    private Integer regionId;
    private String  name;
    private String  description;

    public Region(Integer regionId, String name, String description) {
        this.regionId = regionId;
        this.name = name;
        this.description = description;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
