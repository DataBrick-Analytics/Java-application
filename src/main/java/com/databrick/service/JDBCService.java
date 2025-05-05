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

    public List<Security> getPD(String district) {
        String sqlScript = "SELECT * FROM seguranca WHERE delegacia LIKE ?";

        return template.query(sqlScript, new BeanPropertyRowMapper<>(Security.class), "%" + district + "%");
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
                preparedStatement.setString(2, property.getAddress().getCep());
                preparedStatement.setString(3, property.getAddress().getAddressName());
                preparedStatement.setString(4, property.getAddress().getAddressType());
                preparedStatement.setString(5, property.getAddress().getFullAddress());
                preparedStatement.setString(6, property.getAddress().getState());
                preparedStatement.setString(7, property.getAddress().getDistrict());
                preparedStatement.setString(8, property.getAddress().getZone());
                preparedStatement.setString(9, property.getAddress().getLatitude());
                preparedStatement.setString(10, property.getAddress().getLongitude());
                preparedStatement.setString(11, property.getAddress().getCity());
                preparedStatement.setString(12, property.getCityIBGE());
                preparedStatement.setString(13, property.getDdd());
                preparedStatement.setDouble(14, property.getLandAream2());
                preparedStatement.setDouble(15, property.getBuiltAream2());
                preparedStatement.setString(16, property.getPropertyRegistration());
                preparedStatement.setString(17, property.getRegistryOffice());
                preparedStatement.setDouble(18, property.getValue().getReferenceMarketValue());
                preparedStatement.setDouble(19, property.getValue().getProportionalReferenceMarketValue());
                preparedStatement.setDouble(20, property.getValue().getTransactionValueDeclared());

                Integer fkRegiao = null;
                if (property.getAddress().getDistrict() != null) fkRegiao = getPD(property.getAddress().getDistrict()).getFirst().getId();
                preparedStatement.setInt(21, fkRegiao);

                return preparedStatement;
            });
        } catch (Exception e) {
            log.registerLog(Level.ERROR, "Parece que ocorreu um erro ao tentar salvar os dados. Message: " + e.getMessage());
        }

        log.registerLog(Level.INFO, "Dados de propriedades salvos no banco com sucesso");
    }

    public void saveSecurity(Security security) {
        System.out.println("Ta começando a salvar ein");
        try {
            if (getPD(security.getPoliceStation()) != null) {
                System.out.println("verificou que nao existe ainda");
                String sqlScript = "INSERT INTO seguranca (id_regiao, delegacia, " +
                        "furtos_regiao, roubos_cargas, roubos, " +
                        "roubos_veiculos, furtos_veiculos, latrocinios, " +
                        "homicidio_doloso_acidente_transito, homicidio_culposo_acidente_transito, homicidio_culposo) " +
                        "VALUES (default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                template.update(connection -> {
                    PreparedStatement preparedStatement = connection.prepareStatement(sqlScript);
                    preparedStatement.setString(1, security.getPoliceStation());
                    preparedStatement.setInt(2, security.getTheftsByRegion());
                    preparedStatement.setInt(3, security.getCargoRobbery());
                    preparedStatement.setInt(4, security.getRobberies());
                    preparedStatement.setInt(5, security.getVehicleRobbery());
                    preparedStatement.setInt(6, security.getVehicleTheft());
                    preparedStatement.setInt(7, security.getViolentThefts());
                    preparedStatement.setInt(8, security.getIntentionalHomicideTraffic());
                    preparedStatement.setInt(9, security.getUnintentionalHomicideTraffic());
                    preparedStatement.setInt(10, security.getUnintentionalHomicide());
                    return preparedStatement;
                });
                System.out.println("salvou");
            } else {
                System.out.println("ja existe ein");
                String sqlScript = "UPDATE seguranca SET " +
                        "furtos_regiao = furtos_regiao + ?, " +
                        "roubos_cargas = roubos_cargas + ?, " +
                        "roubos = roubos + ?, " +
                        "roubos_veiculos = roubos_veiculos + ?, " +
                        "furtos_veiculos = furtos_veiculos + ?, " +
                        "latrocinios = latrocinios + ?, " +
                        "homicidio_doloso_acidente_transito = homicidio_doloso_acidente_transito + ?, " +
                        "homicidio_culposo_acidente_transito = homicidio_culposo_acidente_transito + ?, " +
                        "homicidio_culposo = homicidio_culposo + ? " +
                        "WHERE delegacia = ?";

                template.update(connection -> {
                    PreparedStatement preparedStatement = connection.prepareStatement(sqlScript);
                    preparedStatement.setInt(1, security.getTheftsByRegion());
                    preparedStatement.setInt(2, security.getCargoRobbery());
                    preparedStatement.setInt(3, security.getRobberies());
                    preparedStatement.setInt(4, security.getVehicleRobbery());
                    preparedStatement.setInt(5, security.getVehicleTheft());
                    preparedStatement.setInt(6, security.getViolentThefts());
                    preparedStatement.setInt(7, security.getIntentionalHomicideTraffic());
                    preparedStatement.setInt(8, security.getUnintentionalHomicideTraffic());
                    preparedStatement.setInt(9, security.getUnintentionalHomicide());
                    preparedStatement.setString(10, security.getPoliceStation());
                    return preparedStatement;
                });
                System.out.println("atualizou");
            }
        } catch (Exception e) {
            log.registerLog(Level.ERROR, "Parece que ocorreu um erro ao tentar salvar os dados. Message: " + e.getMessage());
        }

        log.registerLog(Level.INFO, "Dados de segurança salvos no banco com sucesso");
    }

    @Override
    public void saveLog(List<String> values) {
        String sqlScript = "INSERT INTO logs (data_hora, tipo_processo, status, mensagem, usuario) VALUES (?, ?, ?, ?, ?)";

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
