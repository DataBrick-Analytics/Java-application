package com.databrick.entity;



public class InfoRegion {

    private String  idCode;
    private String  nameUdh;
    private Integer municipalityCode;
    private String  minicipalityName;
    private Integer regionCode;
    private String  regionName;
    private Double  fifthPoorestHouseholdIncome;
    private Double  secondFifthPoorestHouseholdIncome;
    private Double  thirdFifthPoorestHouseholdIncome;
    private Double  fourthFifthPoorestHousedholdIncome;
    private Double  fifthRichestHouseholdIncome;
    private Integer totalPopulation;
    private Integer destrictCode;
    private String  destrictName;
    private Integer regionalDivision;
    private String  nameOfRegionalCityHall;
    private String  regionalCityHall;

    public InfoRegion(String idCode, String nameUdh, Integer municipalityCode, String minicipalityName, Integer regionCode, String regionName, Double fifthPoorestHouseholdIncome, Double secondFifthPoorestHouseholdIncome, Double thirdFifthPoorestHouseholdIncome, Double fourthFifthPoorestHousedholdIncome, Double fifthRichestHouseholdIncome, Integer totalPopulation, Integer destrictCode, String destrictName, Integer regionalDivision, String nameOfRegionalCityHall, String regionalCityHall) {
        this.idCode = idCode;
        this.nameUdh = nameUdh;
        this.municipalityCode = municipalityCode;
        this.minicipalityName = minicipalityName;
        this.regionCode = regionCode;
        this.regionName = regionName;
        this.fifthPoorestHouseholdIncome = fifthPoorestHouseholdIncome;
        this.secondFifthPoorestHouseholdIncome = secondFifthPoorestHouseholdIncome;
        this.thirdFifthPoorestHouseholdIncome = thirdFifthPoorestHouseholdIncome;
        this.fourthFifthPoorestHousedholdIncome = fourthFifthPoorestHousedholdIncome;
        this.fifthRichestHouseholdIncome = fifthRichestHouseholdIncome;
        this.totalPopulation = totalPopulation;
        this.destrictCode = destrictCode;
        this.destrictName = destrictName;
        this.regionalDivision = regionalDivision;
        this.nameOfRegionalCityHall = nameOfRegionalCityHall;
        this.regionalCityHall = regionalCityHall;
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

    public Integer getMunicipalityCode() {
        return municipalityCode;
    }

    public void setMunicipalityCode(Integer municipalityCode) {
        this.municipalityCode = municipalityCode;
    }

    public String getMinicipalityName() {
        return minicipalityName;
    }

    public void setMinicipalityName(String minicipalityName) {
        this.minicipalityName = minicipalityName;
    }

    public Integer getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(Integer regionCode) {
        this.regionCode = regionCode;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Double getFifthPoorestHouseholdIncome() {
        return fifthPoorestHouseholdIncome;
    }

    public void setFifthPoorestHouseholdIncome(Double fifthPoorestHouseholdIncome) {
        this.fifthPoorestHouseholdIncome = fifthPoorestHouseholdIncome;
    }

    public Double getSecondFifthPoorestHouseholdIncome() {
        return secondFifthPoorestHouseholdIncome;
    }

    public void setSecondFifthPoorestHouseholdIncome(Double secondFifthPoorestHouseholdIncome) {
        this.secondFifthPoorestHouseholdIncome = secondFifthPoorestHouseholdIncome;
    }

    public Double getThirdFifthPoorestHouseholdIncome() {
        return thirdFifthPoorestHouseholdIncome;
    }

    public void setThirdFifthPoorestHouseholdIncome(Double thirdFifthPoorestHouseholdIncome) {
        this.thirdFifthPoorestHouseholdIncome = thirdFifthPoorestHouseholdIncome;
    }

    public Double getFourthFifthPoorestHousedholdIncome() {
        return fourthFifthPoorestHousedholdIncome;
    }

    public void setFourthFifthPoorestHousedholdIncome(Double fourthFifthPoorestHousedholdIncome) {
        this.fourthFifthPoorestHousedholdIncome = fourthFifthPoorestHousedholdIncome;
    }

    public Double getFifthRichestHouseholdIncome() {
        return fifthRichestHouseholdIncome;
    }

    public void setFifthRichestHouseholdIncome(Double fifthRichestHouseholdIncome) {
        this.fifthRichestHouseholdIncome = fifthRichestHouseholdIncome;
    }

    public Integer getTotalPopulation() {
        return totalPopulation;
    }

    public void setTotalPopulation(Integer totalPopulation) {
        this.totalPopulation = totalPopulation;
    }

    public Integer getDestrictCode() {
        return destrictCode;
    }

    public void setDestrictCode(Integer destrictCode) {
        this.destrictCode = destrictCode;
    }

    public String getDestrictName() {
        return destrictName;
    }

    public void setDestrictName(String destrictName) {
        this.destrictName = destrictName;
    }

    public Integer getRegionalDivision() {
        return regionalDivision;
    }

    public void setRegionalDivision(Integer regionalDivision) {
        this.regionalDivision = regionalDivision;
    }

    public String getNameOfRegionalCityHall() {
        return nameOfRegionalCityHall;
    }

    public void setNameOfRegionalCityHall(String nameOfRegionalCityHall) {
        this.nameOfRegionalCityHall = nameOfRegionalCityHall;
    }
}
