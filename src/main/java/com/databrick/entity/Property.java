package com.databrick.entity;

import com.databrick.enums.IptuUse;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.Long.parseLong;

public class Property {

    private Long districtCode;
    private IptuUse iptuUse;

    private static final Map<Integer, IptuUse> IPTU_MAP = Arrays.stream(IptuUse.values())
            .collect(Collectors.toMap(IptuUse::getValue, Function.identity()));

    public Property() {
    }

    public Property(String districtCode, String iptuUse) {
        this.districtCode = parseLong(districtCode);
        this.iptuUse = parseIptuUse(iptuUse);
    }

    public Long getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(Long districtCode) {
        this.districtCode = districtCode;
    }

    public IptuUse getIptuUse() {
        return iptuUse;
    }

    public void setIptuUse(IptuUse iptuUse) {
        this.iptuUse = iptuUse;
    }

    public IptuUse parseIptuUse(String iptuUse) {
        if (iptuUse == null || iptuUse.isBlank()) {
            return null;
        }

        try {
            int codigo = Integer.parseInt(iptuUse.replaceAll("[,.]", ""));

            return switch (codigo) {
                case 74, 64 -> IptuUse.OUTRAS_EDIFICACOES;
                default -> {
                    yield IPTU_MAP.getOrDefault(codigo, null);
                }
            };
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
