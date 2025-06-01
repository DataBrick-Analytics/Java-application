package com.databrick.entity;



public class InfoRegion {

    private String  idCode;
    private String  nameUdh;
    private String municipalityName;
    private String  regionName;
    private String districtName;
    private String  nameOfRegionalCityHall;
    private String  regionalCityHall;
    private InfoRegionValue infoRegion;

    public InfoRegion(String idCode, String nameUdh, String municipalityName, String regionName, String districtName, String nameOfRegionalCityHall, String regionalCityHall, InfoRegionValue infoRegion) {
        this.idCode = idCode;
        this.nameUdh = nameUdh;
        this.municipalityName = municipalityName;
        this.regionName = regionName;
        this.districtName = districtName;
        this.nameOfRegionalCityHall = nameOfRegionalCityHall;
        this.regionalCityHall = regionalCityHall;
        this.infoRegion = infoRegion;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public String getNameUdh() {
        return nameUdh;
    }

    public void setNameUdh(String nameUdh) {
        this.nameUdh = nameUdh;
    }

    public String getMunicipalityName() {
        return municipalityName;
    }

    public void setMunicipalityName(String municipalityName) {
        this.municipalityName = municipalityName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getNameOfRegionalCityHall() {
        return nameOfRegionalCityHall;
    }

    public void setNameOfRegionalCityHall(String nameOfRegionalCityHall) {
        this.nameOfRegionalCityHall = nameOfRegionalCityHall;
    }

    public String getRegionalCityHall() {
        return regionalCityHall;
    }

    public void setRegionalCityHall(String regionalCityHall) {
        this.regionalCityHall = regionalCityHall;
    }

    public InfoRegionValue getInfoRegion() {
        return infoRegion;
    }

    public void setInfoRegion(InfoRegionValue infoRegion) {
        this.infoRegion = infoRegion;
    }
}
