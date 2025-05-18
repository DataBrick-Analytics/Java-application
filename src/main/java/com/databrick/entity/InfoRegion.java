package com.databrick.entity;



public class InfoRegion {

    private String  idCode;
    private String  nameUdh;
    private String  minicipalityName;
    private String  regionName;
    private String  destrictName;
    private String  nameOfRegionalCityHall;
    private String  regionalCityHall;
    private  InfoRegionValue infoRegion;

    public InfoRegion(String idCode, String nameUdh, String minicipalityName, String regionName, String destrictName, String nameOfRegionalCityHall, String regionalCityHall, InfoRegionValue infoRegion) {
        this.idCode = idCode;
        this.nameUdh = nameUdh;
        this.minicipalityName = minicipalityName;
        this.regionName = regionName;
        this.destrictName = destrictName;
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

    public String getMinicipalityName() {
        return minicipalityName;
    }

    public void setMinicipalityName(String minicipalityName) {
        this.minicipalityName = minicipalityName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getDestrictName() {
        return destrictName;
    }

    public void setDestrictName(String destrictName) {
        this.destrictName = destrictName;
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
