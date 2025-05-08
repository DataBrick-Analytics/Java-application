package com.databrick.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Value {

    private Double transactionValueDeclared;
    private Date transactionDate;
    private Double referenceMarketValue;
    private Double transmittedProportion;
    private Double proportionalReferenceMarketValue;

    public Value(String transactionValueDeclared, String transactionDate, String referenceMarketValue, String transmittedProportion, String proportionalReferenceMarketValue) {
        this.transactionValueDeclared = parseDouble(transactionValueDeclared);
        this.transactionDate = parseDate(transactionDate);
        this.referenceMarketValue = parseDouble(referenceMarketValue);
        this.transmittedProportion = parseDouble(transmittedProportion);
        this.proportionalReferenceMarketValue = parseDouble(proportionalReferenceMarketValue);
    }

    public Double getTransactionValueDeclared() {
        return transactionValueDeclared;
    }

    public void setTransactionValueDeclared(Double transactionValueDeclared) {
        this.transactionValueDeclared = transactionValueDeclared;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Double getReferenceMarketValue() {
        return referenceMarketValue;
    }

    public void setReferenceMarketValue(Double referenceMarketValue) {
        this.referenceMarketValue = referenceMarketValue;
    }

    public Double getTransmittedProportion() {
        return transmittedProportion;
    }

    public void setTransmittedProportion(Double transmittedProportion) {
        this.transmittedProportion = transmittedProportion;
    }

    public Double getProportionalReferenceMarketValue() {
        return proportionalReferenceMarketValue;
    }

    public void setProportionalReferenceMarketValue(Double proportionalReferenceMarketValue) {
        this.proportionalReferenceMarketValue = proportionalReferenceMarketValue;
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

    public Date parseDate(String data) {
        if (data == null || data.isBlank()) return null;
        List<String> patterns = List.of("MM/dd/yyyy H:mm", "MM/dd/yyyy HH:mm", "MM/dd/yyyy");
        for (String pattern : patterns) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
                LocalDate localDate = LocalDate.parse(data, formatter);
                return Date.valueOf(localDate);
            } catch (Exception ignored) {}
        }
        return null;
    }

}
