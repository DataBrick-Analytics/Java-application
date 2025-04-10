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

    public Address(String cep, Integer number, String addressType, String addressName, String fullAddress, String state, String district, zoneEnum zone, String latitude, String longitude, String city) {
        this.cep = cep;
        this.number = number;
        this.addressType = addressType;
        this.addressName = addressName;
        this.fullAddress = fullAddress;
        this.state = state;
        this.district = district;
        this.zone = zone;
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
    }
}
