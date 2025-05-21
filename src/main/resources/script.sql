CREATE DATABASE databrick;
USE databrick;

-- Tabela: empresas
CREATE TABLE IF NOT EXISTS `empresas` (
  `id_empresa` INT PRIMARY KEY AUTO_INCREMENT,
  `nome` VARCHAR(45),
  `endereco` VARCHAR(80),
  `telefone` VARCHAR(11),
  `email` VARCHAR(45),
  `data_cadastro` DATE
);

-- Tabela: funcoes
CREATE TABLE IF NOT EXISTS `funcoes` (
  `id_funcao` INT PRIMARY KEY AUTO_INCREMENT,
  `nome` VARCHAR(45),
  `descricao` VARCHAR(45)
);

-- Tabela: usuarios
CREATE TABLE IF NOT EXISTS `usuarios` (
  `id_usuario` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45),
  `email` VARCHAR(85),
  `senha` VARCHAR(45),
  `data_cadastro` DATE,
  `fk_empresa` INT NOT NULL,
  `fk_funcao` INT NOT NULL,
  PRIMARY KEY (`id_usuario`, `fk_empresa`),
  FOREIGN KEY (`fk_empresa`) REFERENCES `empresas` (`id_empresa`),
  FOREIGN KEY (`fk_funcao`) REFERENCES `funcoes` (`id_funcao`)
);

-- Tabela: seguranca
CREATE TABLE IF NOT EXISTS `seguranca` (
  `id_delegacia` INT PRIMARY KEY AUTO_INCREMENT,
  `delegacia` VARCHAR(100),
  `furtos_regiao` INT,
  `roubos_cargas` INT,
  `roubos` INT,
  `roubos_veiculos` INT,
  `furtos_veiculos` INT,
  `latrocinios` INT,
  `homicidio_doloso_acidente_transito` INT,
  `homicidio_culposo_acidente_transito` INT,
  `homicidio_culposo` INT,
  `dt_ultima_coleta` YEAR
);

-- Tabela: propriedades
CREATE TABLE IF NOT EXISTS `propriedades` (
  `id_imovel` INT PRIMARY KEY AUTO_INCREMENT,
  `valor_transacao_declarado` DECIMAL(15,2),
  `data_transacao` DATE,
  `valor_transacao_referencial` DECIMAL(15,2),
  `percentual_transmitido` DECIMAL(5,2),
  `valor_proporcional_referencia_mercado` DECIMAL (15,2),
  `registro_cartorio` VARCHAR(100),
  `registro_propriedade` INT,
  `area_terreno_m2` DECIMAL(10,2),
  `area_construida_m2` DECIMAL(10,2),
  `uso_iptu` VARCHAR(50),
  `cep` VARCHAR(8),
  `nome_endereco` VARCHAR(150),
  `tipo_endereco` VARCHAR(50),
  `endereco_completo` VARCHAR(255),
  `estado` VARCHAR(2),
  `bairro` VARCHAR(100),
  `zona` VARCHAR(50),
  `latitude` VARCHAR(20),
  `longitude` VARCHAR(20),
  `cidade` VARCHAR(100),
  `codigo_ibge_cidade` INT,
  `ddd` VARCHAR(2)
);

-- Tabela: favoritos
CREATE TABLE IF NOT EXISTS `favoritos` (
  `fk_usuario` INT NOT NULL,
  `fk_empresa` INT NOT NULL,
  `fk_propriedade` INT NOT NULL,
  `id_favorito` VARCHAR(45) NOT NULL,
  `data_favorito` DATETIME,
  PRIMARY KEY (`fk_usuario`, `fk_empresa`, `fk_propriedade`, `id_favorito`),
  FOREIGN KEY (`fk_usuario`, `fk_empresa`) REFERENCES `usuarios` (`id_usuario`, `fk_empresa`),
  FOREIGN KEY (`fk_propriedade`) REFERENCES `propriedades` (`id_imovel`)
);

-- Tabela: logs
CREATE TABLE IF NOT EXISTS `logs` (
  `id_logs` INT PRIMARY KEY AUTO_INCREMENT,
  `data_hora` DATETIME,
  `tipo_processo` VARCHAR(80),
  `status` VARCHAR(50),
  `mensagem` VARCHAR(255),
  `usuarios` VARCHAR(90)
);

CREATE TABLE IF NOT EXISTS `acoes` (
  `id_acao` INT PRIMARY KEY,
  `tipo` VARCHAR(45),
  `descricao` VARCHAR(120)
);

CREATE TABLE IF NOT EXISTS `acoesDeUsuario` (
  `id_usuario` INT NOT NULL,
  `fk_empresa` INT NOT NULL,
  `id_acao` INT NOT NULL,
  `dtAcao` DATETIME,
  PRIMARY KEY (`id_usuario`, `fk_empresa`, `id_acao`),
  FOREIGN KEY (`id_usuario`, `fk_empresa`) REFERENCES `usuarios` (`id_usuario`, `fk_empresa`),
  FOREIGN KEY (`id_acao`) REFERENCES `acoes` (`id_acao`)
);

CREATE TABLE IF NOT EXISTS `precificacao` (
  `id_usuario` INT NOT NULL,
  `fk_empresa` INT NOT NULL,
  `id_acao` INT NOT NULL,
  `dtAcao` DATETIME,
  PRIMARY KEY (`id_usuario`, `fk_empresa`, `id_acao`),
  FOREIGN KEY (`id_usuario`, `fk_empresa`) REFERENCES `usuarios` (`id_usuario`, `fk_empresa`),
  FOREIGN KEY (`id_acao`) REFERENCES `acoes` (`id_acao`)
)