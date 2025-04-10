package com.databrick.entity;

public class Property {

    private Double transactionValueDeclared;
    private Double referenceMarketValue;
    private Double proportionalReferenceMarketValue;
    private String registryOffice;
    private String propertyRegistration;
    private String sqlStatus;
    private Double landAream2;
    private Double builtAream2;
    private Address address;
    private String cityIBGE;
    private String ddd;

    public Property(Double transactionValueDeclared, Double referenceMarketValue, Double proportionalReferenceMarketValue, String registryOffice, String propertyRegistration, String sqlStatus, Double landAream2, Double builtAream2, Address address, String cityIBGE, String ddd) {
        this.transactionValueDeclared = transactionValueDeclared;
        this.referenceMarketValue = referenceMarketValue;
        this.proportionalReferenceMarketValue = proportionalReferenceMarketValue;
        this.registryOffice = registryOffice;
        this.propertyRegistration = propertyRegistration;
        this.sqlStatus = sqlStatus;
        this.landAream2 = landAream2;
        this.builtAream2 = builtAream2;
        this.address = address;
        this.cityIBGE = cityIBGE;
        this.ddd = ddd;
    }
}
