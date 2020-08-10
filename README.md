# Spring Boot Challenge

Desafio
---
Este código trata-se da implementação do desafio da primeira etapa do processo seletivo.

Nesta estapa esperamos que você construa o código que contemple as seguintes operações expostas como 
endpoints REST para:

    Cadastrar cidade
    Cadastrar cliente
    Consultar cidade pelo nome
    Consultar cidade pelo estado
    Consultar cliente pelo nome
    Consultar cliente pelo Id
    Remover cliente
    Alterar o nome do cliente
    
Considere o cadastro com dados básicos:

    Cidades: nome e estado
    Cliente: nome completo, sexo, data de nascimento, idade e cidade onde mora.


### Executando localmente
SpringBootChallenge é uma aplicação [Spring Boot](https://spring.io/guides/gs/spring-boot) contruida utilizando
 [Maven](https://spring.io/guides/gs/maven/). Você pode contruir o arquivo jar e executá-lo utilizando os 
  comandos abaixo:


```
git clone https://github.com/leonardmoraes/SpringBootChallenge.git
cd SpringBootChallenge
./mvnw package -DskipTests
java -jar target/SpringBootChallenge-0.0.1-SNAPSHOT.jar
```

Para acessar a aplicação basta utilizar a url:

    http://localhost:8080/
    
Ou utilizar alguma ferramenta de requisições APIs Rest. 
Como por exemplo [Postman](https://www.postman.com/)
ou [Insomnia](https://insomnia.rest/download/).

***Note:*** Este projeto foi contruido utilizando a JDK 8.
