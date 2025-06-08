package com.databrick.service;

import com.databrick.config.ConnectionBD;
import com.databrick.entity.*;
import com.databrick.utils.LoggingUtility;
import org.apache.logging.log4j.Level;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.util.List;

public class JDBCService implements LoggingUtility.LogSaver {

    private final LoggingUtility log;
    private final ConnectionBD connection = new ConnectionBD();
    private final JdbcTemplate template = new JdbcTemplate(connection.getConexao());

    public JDBCService() {
        this.log = new LoggingUtility(JDBCService.class.getName(), this);
    }

    public Security getPD(String district) {
        String sqlScript = "SELECT delegacia AS policeStation, furtos_regiao AS theftsByRegion, roubos_cargas AS cargoRobbery, " +
                "roubos AS robberies, roubos_veiculos AS vehicleRobbery, furtos_veiculos AS vehicleTheft, " +
                "latrocinios AS violentThefts, homicidio_doloso_acidente_transito AS intentionalHomicideTraffic, " +
                "homicidio_culposo_acidente_transito AS unintentionalHomicideTraffic, homicidio_culposo AS unintentionalHomicide, " +
                "ano_ultima_coleta AS lastYearCollected FROM seguranca WHERE delegacia LIKE ?";

        List<Security> results = template.query(sqlScript, new BeanPropertyRowMapper<>(Security.class), "%" + district + "%");
        return results.isEmpty() ? null : results.getFirst();
    }


    public boolean saveProperty(Property property) {
        try {
            String sqlScript = "INSERT INTO propriedade (fk_distrito, uso_iptu, data_criacao, data_edicao) " +
                    "VALUES (?, ?, NOW(), NOW())";

            template.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(sqlScript);
                preparedStatement.setObject(1, property.getDistrictCode());
                preparedStatement.setObject(2, property.getIptuUse() != null ? property.getIptuUse().getValue() : null);
                return preparedStatement;
            });
            return true;
        } catch (Exception e) {
            log.registerLog(Level.ERROR, "Parece que ocorreu um erro ao tentar salvar os dados. Message: " + e.getMessage());
        }
        return false;
    }

    public boolean saveInfoRegion(InfoRegion infoRegion) {
        try {
            String sqlScript = "INSERT INTO info_regiao (nome_udh, nome_municipio, codigo_municipio, " +
                    "nome_regiao, codigo_regiao, renda_domiciliar_quinto_mais_pobre, " +
                    "renda_domiciliar_segundo_quinto_mais_pobre, renda_domiciliar_terceiro_quinto_mais_pobre, " +
                    "renda_domiciliar_quarto_quinto_mais_pobre, renda_domiciliar_quinto_mais_rico, " +
                    "populacao_total, nome_distrito,  fk_distrito, " +
                    "divisao_regional, nome_prefeitura_regional"
                    + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            template.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(sqlScript);
                preparedStatement.setObject(1, infoRegion.getNameUdh());
                preparedStatement.setObject(2, infoRegion.getMunicipalityName());
                preparedStatement.setObject(3, infoRegion.getInfoRegion().getMunicipalityCode());
                preparedStatement.setObject(4, infoRegion.getRegionName());
                preparedStatement.setObject(5, infoRegion.getInfoRegion().getRegionCode());
                preparedStatement.setObject(6, infoRegion.getInfoRegion().getFifthPoorestHouseholdIncome());
                preparedStatement.setObject(7, infoRegion.getInfoRegion().getSecondFifthPoorestHouseholdIncome());
                preparedStatement.setObject(8, infoRegion.getInfoRegion().getThirdFifthPoorestHouseholdIncome());
                preparedStatement.setObject(9, infoRegion.getInfoRegion().getFourthFifthPoorestHouseholdIncome());
                preparedStatement.setObject(10, infoRegion.getInfoRegion().getFifthRichestHouseholdIncome());
                preparedStatement.setObject(11, infoRegion.getInfoRegion().getTotalPopulation());
                preparedStatement.setObject(12, infoRegion.getDistrictName());
                preparedStatement.setObject(13, infoRegion.getInfoRegion().getDistrictCode());
                preparedStatement.setObject(14, infoRegion.getInfoRegion().getRegionalDivision());
                preparedStatement.setObject(15, infoRegion.getNameOfRegionalCityHall());

                return preparedStatement;
            });
            return true;
        } catch (Exception e) {
            log.registerLog(Level.ERROR, "Parece que ocorreu um erro ao tentar salvar os dados. Message: " + e.getMessage());
            return false;
        }
    }

    public boolean savePricing(Pricing pricing) {
        try {
            String sqlScript = "INSERT INTO precificacao (data_precificacao, preco, area, fk_distrito, data_criacao, data_edicao) VALUES (?, ?, ?, ?, NOW(), NOW())";

            template.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(sqlScript);
                preparedStatement.setObject(1, pricing.getRegisteredDate());
                preparedStatement.setObject(2, pricing.getPrice());
                preparedStatement.setObject(3, pricing.getArea());
                preparedStatement.setObject(4, pricing.getDistrictCode());
                return preparedStatement;
            });
            return true;
        } catch (Exception e) {
            log.registerLog(Level.ERROR, "Parece que ocorreu um erro ao tentar salvar os dados. Message: " + e.getMessage());
            return false;
        }
    }
    

    public boolean saveParks(Parks parks) {
        try {
            String sqlScript = "INSERT INTO parque (id_parques, nome_parque, nome_distrito, fk_distrito, data_criacao, data_edicao) VALUES (default, ?, ?, ?, NOW(), NOW())";

            template.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(sqlScript);
                preparedStatement.setObject(1, parks.getName());
                preparedStatement.setObject(2, parks.getDistrictName());
                preparedStatement.setObject(3, parks.getDistrictCode());
                return preparedStatement;
            });
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean saveDistrict(District district) {
        try {
            String sqlScript = "INSERT INTO distrito (nome_distrito, id_distrito, area, zona) VALUES (?, ?, ?, ?)";

            template.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(sqlScript);
                preparedStatement.setObject(1, district.getDistrictName());
                preparedStatement.setObject(2, district.getDistrictCode());
                preparedStatement.setObject(3, district.getTotalArea());
                preparedStatement.setObject(4, district.getZone());
                return preparedStatement;
            });
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean saveTransportation(Transportation transportation) {
        try {
            String sqlScript = "INSERT INTO mobilidade (nome_distrito, qtd_pontos_onibus, qtd_estacoes_trem_metro, fk_distrito, data_criacao, data_edicao) VALUES (?, ?, ?, ?, NOW(), NOW())";

            template.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(sqlScript);
                preparedStatement.setObject(1, transportation.getDistrictName());
                preparedStatement.setObject(2, transportation.getBusStops());
                preparedStatement.setObject(3, transportation.getTrainOrSubwayStations());
                preparedStatement.setObject(4, transportation.getDistrictCode());

                return preparedStatement;
            });
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean saveHealthCare(HealthCare healthCare) {
        try {
            String sqlScript = "INSERT INTO saude (fk_distrito, nome_distrito, nome_unidade, tipo_unidade) VALUES (?, ?, ?, ?)";

            template.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(sqlScript);
                preparedStatement.setObject(1, healthCare.getDistrictCode());
                preparedStatement.setObject(2, healthCare.getDistrictName());
                preparedStatement.setObject(3, healthCare.getUnitName());
                preparedStatement.setObject(4, healthCare.getUnitType());
                return preparedStatement;
            });
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean saveEducationSystem(EducationSystem educationSystem) {
        try {
            String sqlScript = "INSERT INTO educacao (nome_escola, nome_distrito, fk_distrito, data_criacao, data_edicao) VALUES (?, ?, ?, NOW(), NOW())";

            template.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(sqlScript);
                preparedStatement.setObject(1, educationSystem.getSchoolName());
                preparedStatement.setObject(2, educationSystem.getDistrictName());
                preparedStatement.setObject(3, educationSystem.getDistrictCode());
                return preparedStatement;
            });
            return true;
        } catch (Exception e) {
            log.registerLog(Level.ERROR, "Parece que ocorreu um erro ao tentar salvar os dados. Message: " + e.getMessage());
            return false;
        }
    }

    public boolean saveSecurity(Security security) {
        try {
            Security PDDatabase = getPD(security.getPoliceStation());
            Integer newYear = security.getLastYearCollected() != null ? security.getLastYearCollected() : 0;
            if (PDDatabase == null) {
                String sqlScript = "INSERT INTO seguranca (id_delegacia, delegacia, " +
                        "furtos_regiao, roubos_cargas, roubos, " +
                        "roubos_veiculos, furtos_veiculos, latrocinios, " +
                        "homicidio_doloso_acidente_transito, homicidio_culposo_acidente_transito, homicidio_culposo, ano_ultima_coleta, fk_distrito) " +
                        "VALUES (default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                template.update(connection -> {
                    PreparedStatement preparedStatement = connection.prepareStatement(sqlScript);
                    preparedStatement.setObject(1, security.getPoliceStation());
                    preparedStatement.setObject(2, security.getTheftsByRegion());
                    preparedStatement.setObject(3, security.getCargoRobbery());
                    preparedStatement.setObject(4, security.getRobberies());
                    preparedStatement.setObject(5, security.getVehicleRobbery());
                    preparedStatement.setObject(6, security.getVehicleTheft());
                    preparedStatement.setObject(7, security.getViolentThefts());
                    preparedStatement.setObject(8, security.getIntentionalHomicideTraffic());
                    preparedStatement.setObject(9, security.getUnintentionalHomicideTraffic());
                    preparedStatement.setObject(10, security.getUnintentionalHomicide());
                    preparedStatement.setObject(11, security.getLastYearCollected());
                    preparedStatement.setObject(12, security.getDistrictCode());

                    return preparedStatement;
                });
            } else {
                Integer existingYear = PDDatabase.getLastYearCollected() != null ? PDDatabase.getLastYearCollected() : 0;

                if (existingYear < newYear) {
                    String sqlScript = "UPDATE seguranca SET " +
                            "furtos_regiao = furtos_regiao + ?, " +
                            "roubos_cargas = roubos_cargas + ?, " +
                            "roubos = roubos + ?, " +
                            "roubos_veiculos = roubos_veiculos + ?, " +
                            "furtos_veiculos = furtos_veiculos + ?, " +
                            "latrocinios = latrocinios + ?, " +
                            "homicidio_doloso_acidente_transito = homicidio_doloso_acidente_transito + ?, " +
                            "homicidio_culposo_acidente_transito = homicidio_culposo_acidente_transito + ?, " +
                            "homicidio_culposo = homicidio_culposo + ?, " +
                            "ano_ultima_coleta = ? " +
                            "WHERE delegacia = ?";

                    template.update(connection -> {
                        PreparedStatement preparedStatement = connection.prepareStatement(sqlScript);
                        preparedStatement.setObject(1, security.getTheftsByRegion());
                        preparedStatement.setObject(2, security.getCargoRobbery());
                        preparedStatement.setObject(3, security.getRobberies());
                        preparedStatement.setObject(4, security.getVehicleRobbery());
                        preparedStatement.setObject(5, security.getVehicleTheft());
                        preparedStatement.setObject(6, security.getViolentThefts());
                        preparedStatement.setObject(7, security.getIntentionalHomicideTraffic());
                        preparedStatement.setObject(8, security.getUnintentionalHomicideTraffic());
                        preparedStatement.setObject(9, security.getUnintentionalHomicide());
                        preparedStatement.setObject(10, security.getLastYearCollected());
                        preparedStatement.setObject(11, security.getPoliceStation());
                        return preparedStatement;
                    });
                } else {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void saveLog(List<String> values) {
        String sqlScript = "INSERT INTO log (data_criacao, tipo_processo, status, mensagem, usuario) VALUES (?, ?, ?, ?, ?)";

        template.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlScript);
            preparedStatement.setString(1, values.get(0));
            preparedStatement.setString(2, values.get(1));
            preparedStatement.setString(3, values.get(2));
            preparedStatement.setString(4, values.get(3));
            preparedStatement.setString(5, values.get(4));
            return preparedStatement;
        });
    }
}
