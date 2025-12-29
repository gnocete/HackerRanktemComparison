# HackerRank Mercado Livre - API de Comparação de Produtos

Esta aplicação é uma API REST para comparação de produtos, levando em conta que para comparar produtos é necessário criá-los, tê-los disponíveis, atualizá-los ou até mesmo excluí-los.
Foi construída com Java, Spring Boot e Gradle.

## Tecnologias Utilizadas

- Java 24
- Spring Boot
- Gradle

## Como Executar
- Clone o [repositório](https://github.com/gnocete/HackerRanktemComparison)
- Execute a aplicação com o Gradle:
    ./gradlew bootRun
  - No Windows, use:
    gradlew.bat bootRun

## Endpoints Principais
- POST /products — Cria um novo produto
- POST /products/compare — Compara produtos considerando seus preços e avaliações
- GET /products/{id} — Recupera um produto pelo ID
- PATCH /products/{id} — Atualiza parcialmente um produto
- DELETE /products/{id} — Remove um produto

## Collection Insomnia
1. Baixe a collection [aqui]https://github.com/gnocete/HackerRanktemComparison/blob/master/Insomnia_2025-12-29.yaml).
2. No Insomnia, clique em **Import/Export** > **Import Data** > **From File**.
3. Selecione o arquivo baixado para importar os endpoints.

## Estrutura do Projeto
- controller — Camada de controle (REST)
- service — Regras de negócio
- repository — Persistência de dados
- dto — Objetos de transferência de dados
- mapper — Conversão entre entidades e DTOs

## Autora
- Gabriela Nocete Saes da Silva

