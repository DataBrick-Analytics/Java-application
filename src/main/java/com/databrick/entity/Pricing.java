package com.databrick.entity;


import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Pricing {
    private Date registeredDate;
    private Double price;
    private Double area;
    private String districtName;
    private Long districtCode;

    public Pricing(String registeredDate, String price, String area, String districtName, String districtCode) {
        this.registeredDate = parseDate(registeredDate);
        this.price = parseDouble(price);
        this.area = parseDouble(area);
        this.districtName = addressToRegion(districtName);
        this.districtCode = parseLong(districtCode);
    }

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }

    public Long getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(Long districtCode) {
        this.districtCode = districtCode;
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

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
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

    private Long parseLong(String value) {
        if (value == null || value.isBlank()) return null;
        try {
            return Long.valueOf(value.replace(",", "").replace(".", ""));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}


