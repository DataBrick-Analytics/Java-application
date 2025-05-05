package com.databrick.entity;

public class Property {

    private Value value;
    private String registryOffice;
    private String propertyRegistration;
    private String sqlStatus;
    private Double landAream2;
    private Double builtAream2;
    private Address address;
    private String cityIBGE;
    private String ddd;

    public Property() {
    }

    public Property(Value value, String registryOffice, String propertyRegistration, String sqlStatus, String landAream2, String builtAream2, Address address, String cityIBGE, String ddd) {
        this.value = value;
        this.registryOffice = registryOffice;
        this.propertyRegistration = propertyRegistration;
        this.sqlStatus = sqlStatus;
        this.landAream2 = Double.valueOf(landAream2);
        this.builtAream2 = Double.valueOf(builtAream2);
        this.address = address;
        this.cityIBGE = cityIBGE;
        this.ddd = ddd;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public String getRegistryOffice() {
        return registryOffice;
    }

    public void setRegistryOffice(String registryOffice) {
        this.registryOffice = registryOffice;
    }

    public String getPropertyRegistration() {
        return propertyRegistration;
    }

    public void setPropertyRegistration(String propertyRegistration) {
        this.propertyRegistration = propertyRegistration;
    }

    public String getSqlStatus() {
        return sqlStatus;
    }

    public void setSqlStatus(String sqlStatus) {
        this.sqlStatus = sqlStatus;
    }

    public Double getLandAream2() {
        return landAream2;
    }

    public void setLandAream2(Double landAream2) {
        this.landAream2 = landAream2;
    }

    public Double getBuiltAream2() {
        return builtAream2;
    }

    public void setBuiltAream2(Double builtAream2) {
        this.builtAream2 = builtAream2;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getCityIBGE() {
        return cityIBGE;
    }

    public void setCityIBGE(String cityIBGE) {
        this.cityIBGE = cityIBGE;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }
}
