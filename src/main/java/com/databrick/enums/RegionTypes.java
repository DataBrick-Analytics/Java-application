package com.databrick.enums;

public enum RegionTypes {
    ACLIMACAO(1),
    AEROPORTO(2),
    AGUA_BRANCA(3),
    AGUA_FRIA(4),
    AGUA_FUNDA(5),
    ALTO_DE_PINHEIROS(6),
    ANHANGABAU(7),
    ARICANDUVA(8),
    ARTUR_ALVIM(9),
    BARRA_FUNDA(10),
    BELA_VISTA(11),
    BELENZINHO(12),
    BOM_RETIRO(13),
    BROOKLIN(14),
    BUTANTA(15),
    CAMBUCI(16),
    CAMPO_BELO(17),
    CAMPO_LIMPO(18),
    CASA_VERDE(19),
    CIDADE_ADEMIR(20),
    CIDADE_JARDIM(21),
    CONSOLACAO(22),
    FREGUESIA_DO_O(23),
    GUARAPIRANGA(24),
    IPIRANGA(25),
    ITAIM_BIBI(26),
    ITAQUERA(27),
    JABAQUARA(28),
    JARDIM_ANGELA(29),
    JARDIM_PAULISTA(30),
    LIBERDADE(31),
    LAPA(32),
    MOOCA(33),
    MORUMBI(34),
    PARI(35),
    PENHA(36),
    PERDIZES(37),
    PINHEIROS(38),
    PIRITUBA(39),
    REPUBLICA(40),
    SANTA_CECILIA(41),
    SANTO_AMARO(42),
    SAO_MATEUS(43),
    SAO_MIGUEL_PAULISTA(44),
    SAUDE(45),
    TATUAPE(46),
    TREMEMBE(47),
    VILA_MARIANA(48),
    VILA_PRUDENTE(49),
    VILA_GUILHERME(50),
    VILA_LEOPOLDINA(51),
    VILA_NOVA_CONCEICAO(52),
    VILA_SONIA(53),
    VILA_FORMOSA(54),
    VILA_MATILDE(55),
    VILA_CARRAO(56),
    VILA_BRASILANDIA(57),
    VILA_JACUI(58),
    VILA_GUARANI(59),
    VILA_MIRANDOPOLIS(60);

    private final Integer id;

    RegionTypes(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}