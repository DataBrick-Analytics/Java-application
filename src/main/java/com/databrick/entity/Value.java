package com.databrick.entity;

public class Value {

    private Double transactionValueDeclared;
    private Double referenceMarketValue;
    private Double proportionalReferenceMarketValue;

    public Value(String transactionValueDeclared, String referenceMarketValue, String proportionalReferenceMarketValue) {
        this.transactionValueDeclared = Double.valueOf(transactionValueDeclared);
        this.referenceMarketValue = Double.valueOf(referenceMarketValue);
        this.proportionalReferenceMarketValue = Double.valueOf(proportionalReferenceMarketValue);
    }

    public Double getTransactionValueDeclared() {
        return transactionValueDeclared;
    }

    public void setTransactionValueDeclared(Double transactionValueDeclared) {
        this.transactionValueDeclared = transactionValueDeclared;
    }

    public Double getReferenceMarketValue() {
        return referenceMarketValue;
    }

    public void setReferenceMarketValue(Double referenceMarketValue) {
        this.referenceMarketValue = referenceMarketValue;
    }

    public Double getProportionalReferenceMarketValue() {
        return proportionalReferenceMarketValue;
    }

    public void setProportionalReferenceMarketValue(Double proportionalReferenceMarketValue) {
        this.proportionalReferenceMarketValue = proportionalReferenceMarketValue;
    }
}
