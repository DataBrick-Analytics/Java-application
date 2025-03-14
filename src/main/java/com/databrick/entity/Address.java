package com.databrick.entity;

import java.time.LocalDateTime;

public class Address {

    private LocalDateTime registerDate;
    private String road;
    private Integer number;
    private String neighborhood;
    private String zipCode;

    public Address(LocalDateTime registerDate, String road, Integer number, String neighborhood, String zipCode) {
        this.registerDate = registerDate;
        this.road = road;
        this.number = number;
        this.neighborhood = neighborhood;
        this.zipCode = zipCode;
    }

    public Address() {

    }

    public LocalDateTime getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDateTime registerDate) {
        this.registerDate = registerDate;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
