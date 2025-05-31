package com.databrick.entity;


import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Pricing {
    private Date registeredDate;
    private Double price;
    private Double area;
    private String address;

    public Pricing(String registeredDate, String price, String area, String address) {
        this.registeredDate = parseDate(registeredDate);
        this.price = parseDouble(price);
        this.area = parseDouble(area);
        this.address = addressToRegion(address);
    }

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
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

    public Date parseDate(String data) {
        if (data == null || data.isBlank()) return null;

        List<String> patterns = List.of(
                "YYYY-MM-DDTHH:MM:SSZ\n"
        );

        for (String pattern : patterns) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
                LocalDateTime dateTime = LocalDateTime.parse(data, formatter);
                return Date.valueOf(dateTime.toLocalDate());
            } catch (Exception ignored) {}
        }
        return null;
    }
}


