package com.databrick.service;

import com.databrick.config.ConnectionBD;
import com.databrick.entity.*;
import com.databrick.utils.LoggingUtility;
import org.apache.logging.log4j.Level;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class JDBCService implements LoggingUtility.LogSaver {

    private final LoggingUtility log;
    private final ConnectionBD conexao = new ConnectionBD();
    private final JdbcTemplate template = new JdbcTemplate(conexao.getConexao());

    public JDBCService() {
        this.log = new LoggingUtility(JDBCService.class.getName(), this);
    }


    public boolean saveProperty(List<Property> properties) {
        String sqlScript = "INSERT INTO propriedade (fk_distrito, uso_iptu, data_criacao, data_edicao) " +
                "VALUES (?, ?, NOW(), NOW())";

        try (Connection connection = conexao.getConexao().getConnection();
             PreparedStatement propertyStmt = connection.prepareStatement(sqlScript)) {

            connection.setAutoCommit(false);

            for (Property property : properties) {
                propertyStmt.setObject(1, property.getDistrictCode());
                propertyStmt.setObject(2, property.getIptuUse() != null ? property.getIptuUse().getValue() : null);
                propertyStmt.addBatch();
            }

            propertyStmt.executeBatch();
            connection.commit();
            return true;
        } catch (Exception e) {
            log.registerLog(Level.ERROR, "Erro ao tentar salvar os dados em lote. Message: " + e.getMessage());
            return false;
        }
    }

    public boolean saveInfoRegion(List<InfoRegion> infoRegion) {
        String sqlScript = "INSERT INTO info_regiao (nome_udh, nome_municipio, codigo_municipio, " +
                "nome_regiao, codigo_regiao, renda_domiciliar_quinto_mais_pobre, " +
                "renda_domiciliar_segundo_quinto_mais_pobre, renda_domiciliar_terceiro_quinto_mais_pobre, " +
                "renda_domiciliar_quarto_quinto_mais_pobre, renda_domiciliar_quinto_mais_rico, " +
                "populacao_total, nome_distrito,  fk_distrito, " +
                "divisao_regional, nome_prefeitura_regional"
                + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = conexao.getConexao().getConnection();
             PreparedStatement infoRegionStmt = connection.prepareStatement(sqlScript)) {

            connection.setAutoCommit(false);

            for (InfoRegion infoRegionItem : infoRegion) {
                if (infoRegionItem.getInfoRegion().getDistrictCode() == null) {
                    continue;
                }

                infoRegionStmt.setString(1, infoRegionItem.getNameUdh());
                infoRegionStmt.setString(2, infoRegionItem.getMunicipalityName());
                infoRegionStmt.setInt(3, infoRegionItem.getInfoRegion().getMunicipalityCode());
                infoRegionStmt.setString(4, infoRegionItem.getRegionName());
                infoRegionStmt.setObject(5, infoRegionItem.getInfoRegion().getRegionCode());
                infoRegionStmt.setObject(6, infoRegionItem.getInfoRegion().getFifthPoorestHouseholdIncome());
                infoRegionStmt.setObject(7, infoRegionItem.getInfoRegion().getSecondFifthPoorestHouseholdIncome());
                infoRegionStmt.setObject(8, infoRegionItem.getInfoRegion().getThirdFifthPoorestHouseholdIncome());
                infoRegionStmt.setObject(9, infoRegionItem.getInfoRegion().getFourthFifthPoorestHouseholdIncome());
                infoRegionStmt.setObject(10, infoRegionItem.getInfoRegion().getFifthRichestHouseholdIncome());
                infoRegionStmt.setObject(11, infoRegionItem.getInfoRegion().getTotalPopulation());
                infoRegionStmt.setString(12, infoRegionItem.getDistrictName());
                infoRegionStmt.setLong(13, infoRegionItem.getInfoRegion().getDistrictCode());
                infoRegionStmt.setInt(14, infoRegionItem.getInfoRegion().getRegionalDivision() != null ? infoRegionItem.getInfoRegion().getRegionalDivision() : 0);
                infoRegionStmt.setString(15, infoRegionItem.getNameOfRegionalCityHall());
                infoRegionStmt.addBatch();
            }

            infoRegionStmt.executeBatch();
            connection.commit();
            return true;
        } catch (Exception e) {
            log.registerLog(Level.ERROR, "Erro ao tentar salvar os dados em lote. Message: " + e.getMessage());
            return false;
        }
    }

    public boolean savePricing(List<Pricing> pricings) {
        String sqlScript = "INSERT INTO precificacao (data_precificacao, preco, area, fk_distrito, data_criacao, data_edicao) " +
                "VALUES (?, ?, ?, ?, NOW(), NOW())";

        try (Connection connection = conexao.getConexao().getConnection();
             PreparedStatement pricingStmt = connection.prepareStatement(sqlScript)) {

            connection.setAutoCommit(false);

            for (Pricing pricing : pricings) {
                pricingStmt.setObject(1, pricing.getRegisteredDate());
                pricingStmt.setObject(2, pricing.getPrice());
                pricingStmt.setObject(3, pricing.getArea());
                pricingStmt.setObject(4, pricing.getDistrictCode());
                pricingStmt.addBatch();
            }

            pricingStmt.executeBatch();
            connection.commit();
            return true;
        } catch (Exception e) {
            log.registerLog(Level.ERROR, "Erro ao tentar salvar os dados em lote. Message: " + e.getMessage());
            return false;
        }
    }

    public boolean saveParks(List<Parks> parksList) {
        String sqlScript = "INSERT INTO parque (id_parques, nome_parque, nome_distrito, fk_distrito, data_criacao, data_edicao) " +
                "VALUES (default, ?, ?, ?, NOW(), NOW())";

        try (Connection connection = conexao.getConexao().getConnection();
             PreparedStatement parksStmt = connection.prepareStatement(sqlScript)) {

            connection.setAutoCommit(false);

            for (Parks parks : parksList) {
                parksStmt.setString(1, parks.getName());
                parksStmt.setString(2, parks.getDistrictName());
                parksStmt.setObject(3, parks.getDistrictCode());
                parksStmt.addBatch();
            }

            parksStmt.executeBatch();
            connection.commit();
            return true;
        } catch (Exception e) {
            log.registerLog(Level.ERROR, "Erro ao tentar salvar os dados em lote. Message: " + e.getMessage());
            return false;
        }
    }


    public boolean saveDistrict(List<District> districts) {
        String sqlScript = "INSERT INTO distrito (nome_distrito, id_distrito, area, zona, populacao) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = conexao.getConexao().getConnection();
             PreparedStatement districtStmt = connection.prepareStatement(sqlScript)) {

            connection.setAutoCommit(false);

            for (District district : districts) {
                districtStmt.setString(1, district.getDistrictName());
                districtStmt.setObject(2, district.getDistrictCode());
                districtStmt.setObject(3, district.getTotalArea());
                districtStmt.setString(4, district.getZone());
                districtStmt.setObject(5, district.getPopulationTotal());
                districtStmt.addBatch();
            }

            districtStmt.executeBatch();
            connection.commit();
            return true;
        } catch (Exception e) {
            log.registerLog(Level.ERROR, "Erro ao tentar salvar os distritos em lote. Message: " + e.getMessage());
            return false;
        }
    }

    public boolean saveTransportation(List<Transportation> transportations) {
        String sqlScript = "INSERT INTO mobilidade (nome_distrito, qtd_pontos_onibus, qtd_estacoes_trem_metro, fk_distrito, data_criacao, data_edicao) " +
                "VALUES (?, ?, ?, ?, NOW(), NOW())";

        try (Connection connection = conexao.getConexao().getConnection();
             PreparedStatement transportStmt = connection.prepareStatement(sqlScript)) {

            connection.setAutoCommit(false);

            for (Transportation transportation : transportations) {
                transportStmt.setString(1, transportation.getDistrictName());
                transportStmt.setObject(2, transportation.getBusStops());
                transportStmt.setObject(3, transportation.getTrainOrSubwayStations());
                transportStmt.setObject(4, transportation.getDistrictCode());
                transportStmt.addBatch();
            }

            transportStmt.executeBatch();
            connection.commit();
            return true;
        } catch (Exception e) {
            log.registerLog(Level.ERROR, "Erro ao tentar salvar os dados de transporte em lote. Message: " + e.getMessage());
            return false;
        }
    }

    public boolean saveHealthCare(List<HealthCare> healthCares) {
        String sqlScript = "INSERT INTO saude (fk_distrito, nome_distrito, nome_unidade, tipo_unidade) VALUES (?, ?, ?, ?)";

        try (Connection connection = conexao.getConexao().getConnection();
             PreparedStatement healthCareStmt = connection.prepareStatement(sqlScript)) {

            connection.setAutoCommit(false);

            for (HealthCare healthCare : healthCares) {
                healthCareStmt.setObject(1, healthCare.getDistrictCode());
                healthCareStmt.setObject(2, healthCare.getDistrictName());
                healthCareStmt.setObject(3, healthCare.getUnitName());
                healthCareStmt.setObject(4, healthCare.getUnitType());
                healthCareStmt.addBatch();
            }

            healthCareStmt.executeBatch();
            connection.commit();
            return true;
        } catch (Exception e) {
            log.registerLog(Level.ERROR, "Erro ao tentar salvar os dados de saúde em lote. Message: " + e.getMessage());
            return false;
        }
    }

    public boolean saveEducationSystem(List<EducationSystem> educationSystems) {
        String sqlScript = "INSERT INTO educacao (nome_escola, nome_distrito, fk_distrito, data_criacao, data_edicao) " +
                "VALUES (?, ?, ?, NOW(), NOW())";

        try (Connection connection = conexao.getConexao().getConnection();
             PreparedStatement educationStmt = connection.prepareStatement(sqlScript)) {

            connection.setAutoCommit(false);

            for (EducationSystem educationSystem : educationSystems) {
                educationStmt.setString(1, educationSystem.getSchoolName());
                educationStmt.setString(2, educationSystem.getDistrictName());
                educationStmt.setObject(3, educationSystem.getDistrictCode());
                educationStmt.addBatch();
            }

            educationStmt.executeBatch();
            connection.commit();
            return true;
        } catch (Exception e) {
            log.registerLog(Level.ERROR, "Erro ao tentar salvar os dados de educação em lote. Message: " + e.getMessage());
            return false;
        }
    }

    public boolean saveSecurity(List<Security> securities) {
        String sqlScript = "INSERT INTO seguranca (id_delegacia, delegacia, " +
                "furtos_regiao, roubos_cargas, roubos, " +
                "roubos_veiculos, furtos_veiculos, latrocinios, " +
                "homicidio_doloso_acidente_transito, homicidio_culposo_acidente_transito, homicidio_culposo, ano_ultima_coleta, fk_distrito) " +
                "VALUES (default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        ConnectionBD conexao = new ConnectionBD();
        try (Connection connection = conexao.getConexao().getConnection();
             PreparedStatement securityStmt = connection.prepareStatement(sqlScript)) {

            connection.setAutoCommit(false);

            for (Security security : securities) {
                securityStmt.setObject(1, security.getPoliceStation());
                securityStmt.setObject(2, security.getTheftsByRegion());
                securityStmt.setObject(3, security.getCargoRobbery());
                securityStmt.setObject(4, security.getRobberies());
                securityStmt.setObject(5, security.getVehicleRobbery());
                securityStmt.setObject(6, security.getVehicleTheft());
                securityStmt.setObject(7, security.getViolentThefts());
                securityStmt.setObject(8, security.getIntentionalHomicideTraffic());
                securityStmt.setObject(9, security.getUnintentionalHomicideTraffic());
                securityStmt.setObject(10, security.getUnintentionalHomicide());
                securityStmt.setObject(11, security.getLastYearCollected());
                securityStmt.setObject(12, security.getDistrictCode());
                securityStmt.addBatch();
            }

            securityStmt.executeBatch();
            connection.commit();
            return true;
        } catch (Exception e) {
            log.registerLog(Level.ERROR, "Erro ao tentar salvar os dados de segurança em lote. Message: " + e.getMessage());
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
