# 🛠️ Projeto ETL em Java

Este projeto é um pipeline **ETL (Extract, Transform, Load)** desenvolvido em **Java** com o objetivo de automatizar o processo de extração, transformação e carga de dados, possibilitando integração entre diferentes fontes e destinos de dados.

---

## 📌 Funcionalidades

* 🔍 **Extração de Dados**: Coleta informações de fontes como arquivos `.xlsx`.
* 🔧 **Transformação**: Limpeza, normalização e enriquecimento dos dados com regras definidas no código.
* 📦 **Carga**: Inserção dos dados tratados em um banco de dados destino ou exportação para novos arquivos.

---

## ⚙️ Tecnologias Utilizadas

* **Java 21**
* **Apache POI** (para leitura e escrita de planilhas Excel)
* **Log4j** (para logging)
* **JDBC** (para conexão com banco de dados)

---

## 🗂️ Estrutura do Projeto

```
src/
├── config/        # Arquivos de configuração do projeto
├── entity/        # Classes que representam os dados e entidades
├── enums/         # Enumerações usadas na transformação de dados
├── service/       # Camada principal de lógica de extração, transformação e carga
├── utils/         # Funções utilitárias para auxiliar no processo
└── Main.java      # Ponto de entrada da aplicação
```

---

## 🚀 Como Executar

1. **Clone o repositório:**

```bash
git clone https://github.com/seu-usuario/seu-projeto-etl.git
```

2. **Configure o `application.properties`:**

Preencha as credenciais de acesso ao banco de dados, paths de arquivos, etc.

3. **Compile e execute:**

```bash
mvn clean install
java -jar target/seu-projeto-etl.jar
```

---

## 📝 Exemplos de Uso

* Transformar um `.xlsx` com dados brutos em um formato limpo e carregar em um banco MySQL.
