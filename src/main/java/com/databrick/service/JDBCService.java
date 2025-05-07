package com.databrick.service;

import com.databrick.config.ConnectionBD;
import com.databrick.entity.Property;
import com.databrick.entity.Security;
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
                "dt_ultima_coleta AS lastYearCollected FROM seguranca WHERE delegacia LIKE ?";

        List<Security> results = template.query(sqlScript, new BeanPropertyRowMapper<>(Security.class), "%" + district + "%");
        return results.isEmpty() ? null : results.getFirst();
    }


    public void saveProperty(Property property) {
        try {
            String sqlScript = "INSERT INTO propriedades (id_imovel, cep, nome_endereco, tipo_endereco, " +
                    "endereco_completo, estado, bairro, zona, latitude, longitude, cidade, " +
                    "codigo_ibge_cidade, ddd, descricao_uso_iptu, area_terreno_m2, " +
                    "area_construida_m2, registro_propriedade, cartorio_registro, " +
                    "valor_mercado_divulgado, valor_proporcional_mercado, " +
                    "valor_transacao_declarado, fk_regiao) " +
                    "VALUES (default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            template.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(sqlScript);
                preparedStatement.setObject(2, property.getAddress().getCep());
                preparedStatement.setObject(3, property.getAddress().getAddressName());
                preparedStatement.setObject(4, property.getAddress().getAddressType());
                preparedStatement.setObject(5, property.getAddress().getFullAddress());
                preparedStatement.setObject(6, property.getAddress().getState());
                preparedStatement.setObject(7, property.getAddress().getDistrict());
                preparedStatement.setObject(8, property.getAddress().getZone());
                preparedStatement.setObject(9, property.getAddress().getLatitude());
                preparedStatement.setObject(10, property.getAddress().getLongitude());
                preparedStatement.setObject(11, property.getAddress().getCity());
                preparedStatement.setObject(12, property.getCityIBGE());
                preparedStatement.setObject(13, property.getDdd());
                preparedStatement.setObject(14, property.getLandAream2());
                preparedStatement.setObject(15, property.getBuiltAream2());
                preparedStatement.setObject(16, property.getPropertyRegistration());
                preparedStatement.setObject(17, property.getRegistryOffice());
                preparedStatement.setObject(18, property.getValue().getReferenceMarketValue());
                preparedStatement.setObject(19, property.getValue().getProportionalReferenceMarketValue());
                preparedStatement.setObject(20, property.getValue().getTransactionValueDeclared());

                Integer fkRegiao = null;
                if (property.getAddress().getDistrict() != null)
                    fkRegiao = getPD(property.getAddress().getDistrict()).getId();
                preparedStatement.setInt(21, fkRegiao);

                return preparedStatement;
            });
        } catch (Exception e) {
            log.registerLog(Level.ERROR, "Parece que ocorreu um erro ao tentar salvar os dados. Message: " + e.getMessage());
        }

        log.registerLog(Level.INFO, "Dados de propriedades salvos no banco com sucesso");
    }

    public void saveSecurity(Security security) throws Exception {
        try {
            Security PDDatabase = getPD(security.getPoliceStation());
            Integer newYear = security.getLastYearCollected() != null ? security.getLastYearCollected() : 0;
            if (PDDatabase == null) {
                String sqlScript = "INSERT INTO seguranca (id_regiao, delegacia, " +
                        "furtos_regiao, roubos_cargas, roubos, " +
                        "roubos_veiculos, furtos_veiculos, latrocinios, " +
                        "homicidio_doloso_acidente_transito, homicidio_culposo_acidente_transito, homicidio_culposo, dt_ultima_coleta) " +
                        "VALUES (default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
                            "dt_ultima_coleta = ? " +
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
                    log.registerLog(Level.WARN, "O dado já esta registrado no banco e o mais recente possível!");
                }
            }
        } catch (Exception e) {
            log.registerLog(Level.ERROR, "Parece que ocorreu um erro ao tentar salvar os dados. Message: " + e.getMessage());
            // TODO remover log de salvo com sucesso caso alguem de erro
        }
    }

    @Override
    public void saveLog(List<String> values) {
        String sqlScript = "INSERT INTO logs (data_hora, tipo_processo, status, mensagem, usuarios) VALUES (?, ?, ?, ?, ?)";

        template.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlScript);
            preparedStatement.setString(1, values.get(0));
            preparedStatement.setString(2, values.get(1));
            preparedStatement.setString(3, values.get(2));
            preparedStatement.setString(4, values.get(3));
            preparedStatement.setString(5, values.get(4));
            return preparedStatement;
        });

        System.out.println("Log salvo com sucesso!");
    }
}
