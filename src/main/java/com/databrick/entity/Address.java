package com.databrick.entity;

import com.databrick.enums.zoneEnum;

public class Address {

    private String cep;
    private Integer number;
    private String addressType;
    private String addressName;
    private String fullAddress;
    private String state;
    private String district;
    private zoneEnum zone;
    private String latitude;
    private String longitude;
    private String city;

    public Address(String cep, String number, String addressType, String addressName, String fullAddress, String state, String district, String zone, String latitude, String longitude, String city) {
        this.cep = cep;
        this.number = Integer.parseInt(number);
        this.addressType = addressType;
        this.addressName = addressName;
        this.fullAddress = fullAddress;
        this.state = state;
        this.district = district;
        this.zone = zoneEnum.valueOf(zoneEnum.class, zone.toUpperCase());
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public zoneEnum getZone() {
        return zone;
    }

    public void setZone(zoneEnum zone) {
        this.zone = zone;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
