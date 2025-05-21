package com.databrick.entity;


public class Pricing {

    private Double price;
    private Double area;
    private String address;

    public Pricing(String price, String area, String address) {
        this.price = parseDouble(price);
        this.area = parseDouble(area);
        this.address = addressToRegion(address);
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double parseDouble(String valor) {
        if (valor == null || valor.isBlank()) return 0.0;
        try {
            Double raw = Double.parseDouble(valor.replace(",", "."));
            return Math.floor(raw * 100) / 100.0;
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public String addressToRegion(String address){
        int commaIndex = address.indexOf(',');
        if (commaIndex != -1) {
            address = address.substring(commaIndex + 1);
        }

        address = address.stripLeading();

        int hyphenIndex = address.indexOf('-');
        if (hyphenIndex != -1) {
            address = address.substring(0, hyphenIndex);
        }

        address = address.strip();
        return address;
    }
}


