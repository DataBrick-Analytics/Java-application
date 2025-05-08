package com.databrick.enums;

public enum IptuUse {

    TERRENO(0),
    RESIDENCIA(10),
    RESIDENCIA_COLETIVA(12),
    CORTICO(13),
    RESIDENCIA_E_OUTROS(14),
    APARTAMENTO_CONDOMINIO(20),
    PREDIO_APARTAMENTO_RESIDENCIAL(21),
    PREDIO_APARTAMENTO_MISTO(22),
    GARAGEM_COMERCIAL(23),
    GARAGEM_RESIDENCIAL(24),
    FLAT_RESIDENCIAL(25),
    DEPOSITO(26),
    ESCRITORIO_CONDOMINIO(30),
    PREDIO_ESCRITORIO(31),
    PREDIO_MISTO(32),
    LOJA(40),
    LOJA_CONDOMINIO(41),
    LOJA_RESIDENCIA(42),
    OUTRAS_EDIFICACOES(43), // 74 && 64
    INDUSTRIA(50),
    ARMAZENS(51),
    OFICINA(60),
    POSTO_SERVICO(61),
    GARAGEM_PREDIO_CONDOMINIO(62),
    GARAGEM_PREDIO(63),
    CINEMA(70),
    ESCOLA(71),
    TEMPLO(72),
    HOTEL(80),
    HOSPITAL(81),
    RADIOEMISSORA(82),
    ASILO(84),
    FLAT_COMERCIAL(85);

    private Integer value;

    IptuUse(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
