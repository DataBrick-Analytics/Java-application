package com.databrick.entity;

import com.databrick.enums.IptuUse;

public class Property {

    private Value value;
    private String registryOffice;
    private String propertyRegistration;
    private Double landAream2;
    private Double builtAream2;
    private IptuUse iptuUse;
    private Address address;
    private String cityIBGE;
    private String ddd;

    public Property() {
    }

    public Property(Value value, String registryOffice, String propertyRegistration, String landAream2, String builtAream2, String iptuUse, Address address, String cityIBGE, String ddd) {
        this.value = value;
        this.registryOffice = registryOffice;
        this.propertyRegistration = propertyRegistration;
        this.landAream2 = parseDouble(landAream2);
        this.builtAream2 = parseDouble(builtAream2);
        this.iptuUse = parseIptuUse(iptuUse);
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

    public IptuUse getIptuUse() {
        return iptuUse;
    }

    public void setIptuUse(IptuUse iptuUse) {
        this.iptuUse = iptuUse;
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

    public Double parseDouble(String valor) {
        if (valor == null || valor.isBlank()) return 0.0;
        try {
            double raw = Double.parseDouble(valor.replace(",", "."));
            return Math.floor(raw * 100) / 100.0; // corta para 2 casas decimais
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public IptuUse parseIptuUse(String value) {
        if (value == null || value.isBlank()) return null;

        try {
            int codigo = Integer.parseInt(value.replace(",", "").replace(".", ""));

            if (codigo == 74 || codigo == 64) {
                return IptuUse.OUTRAS_EDIFICACOES;
            }

            for (IptuUse tipo : IptuUse.values()) {
                if (tipo.getValue() == codigo) {
                    return tipo;
                }
            }

        } catch (NumberFormatException e) {
            return null;
        }

        return null;
    }

}
