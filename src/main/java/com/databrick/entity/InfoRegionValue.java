package com.databrick.entity;

public class InfoRegionValue {
    private Integer municipalityCode;
    private Integer regionCode;
    private Double fifthPoorestHouseholdIncome;
    private Double secondFifthPoorestHouseholdIncome;
    private Double thirdFifthPoorestHouseholdIncome;
    private Double fourthFifthPoorestHouseholdIncome;
    private Double fifthRichestHouseholdIncome;
    private Integer totalPopulation;
    private Integer destrictCode;
    private Integer regionalDivision;

    public InfoRegionValue(String municipalityCode, String regionCode, String fifthPoorestHouseholdIncome, String secondFifthPoorestHouseholdIncome, String thirdFifthPoorestHouseholdIncome, String fourthFifthPoorestHouseholdIncome, String fifthRichestHouseholdIncome, String totalPopulation, String destrictCode, String regionalDivision) {
        this.municipalityCode = parseInteger(municipalityCode);
        this.regionCode = parseInteger(regionCode);
        this.fifthPoorestHouseholdIncome = parseDouble(fifthPoorestHouseholdIncome);
        this.secondFifthPoorestHouseholdIncome = parseDouble(secondFifthPoorestHouseholdIncome);
        this.thirdFifthPoorestHouseholdIncome = parseDouble(thirdFifthPoorestHouseholdIncome);
        this.fourthFifthPoorestHouseholdIncome = parseDouble(fourthFifthPoorestHouseholdIncome);
        this.fifthRichestHouseholdIncome = parseDouble(fifthRichestHouseholdIncome);
        this.totalPopulation = parseInteger(totalPopulation);
        this.destrictCode = parseInteger(destrictCode);
        this.regionalDivision = parseInteger(regionalDivision);
    }

    public Integer getMunicipalityCode() {
        return municipalityCode;
    }

    public void setMunicipalityCode(Integer municipalityCode) {
        this.municipalityCode = municipalityCode;
    }

    public Integer getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(Integer regionCode) {
        this.regionCode = regionCode;
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

    public Double getFourthFifthPoorestHouseholdIncome() {
        return fourthFifthPoorestHouseholdIncome;
    }

    public void setFourthFifthPoorestHouseholdIncome(Double fourthFifthPoorestHouseholdIncome) {
        this.fourthFifthPoorestHouseholdIncome = fourthFifthPoorestHouseholdIncome;
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

    public Integer getRegionalDivision() {
        return regionalDivision;
    }

    public void setRegionalDivision(Integer regionalDivision) {
        this.regionalDivision = regionalDivision;
    }

    private Integer parseInteger(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }


}