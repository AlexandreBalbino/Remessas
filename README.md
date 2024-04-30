# Remessas
Repositório com processo seletivo de remessa

## Versões
Versão escolhida do Java é 17 com framework Spring boot; Utilizado o maven como gerenciador de pacotes;

## Comandos para Execução
Para rodar entrar na pasta /remessas e utilizar comando 

> mvn spring-boot:run

## Comandos para testes

Para rodar entrar na pasta /remessas e utilizar comando 

> mvn tests

## Bibliotecas utilizadas

As principais bibliotecas utilizadas foram:
```
    - jpa
    - flywaydb
    - h2database
    - projectlombok
    - hibernate.validator
    - apache.httpcomponents
    - google.code.gson
```

## Banco de dados

O banco de dados foi pensado para ser enxuto e o menos complexo possível.
As três tabelas são:
```
    - usuario
    - carteira
    - remessa
```

Sendo que um usuario sempre irá possuir uma carteira americana e uma brasileira.
A remessa tem como objetivo manter o histórico das transações.

Não vi a necessidade de separar a tabela usuario entre PF e PJ, pois havia somente um campo diferindo nessa aplicação.
Para melhorar a apresentação foram inseridos dados via flyway.

## Tópicos de pastas

O projeto foi pensado utilizando três camadas principais. Sendo elas: 
```
    - Controller
    - Services
    - Repository
```

Além disso, existem os diretorios com os modelos de negócio
```
    - Dto
    - Entity
```

Para transitar entre as duas camadas de modelo há os mappers. 
Existem também as camadas:
```
    - Util
    - Enums
```

Optei por exceções customizadas devido ao fato de permitir maior visão das validações ocorridas no codigo.
```
    - Exception
```

## endpoints

### Para criar um usuario pf

Realizar a requisição post para
> http://localhost:8080/usuario/pf

Com corpo
```
{
    "email": "joaomail2@teste.com",
    "nome":"teste",
    "senha":"123",
    "cpfCnpj":"69622032095"
}
```

atenção ao cpf sem pontuação.

### Para criar um usuario pj

Realizar a requisição post para
> http://localhost:8080/usuario/pj

Com corpo
```
{
    "email": "joaomail2@teste.com",
    "nome":"teste",
    "senha":"123",
    "cpfCnpj":"06300917000198"
}
```

atenção ao cnpj sem pontuação


### para criar uma remessa
Realizar um post para 
> http://localhost:8080/remessa/{emailUsuario}

inserir um email de usuario válido. O corpo da requisição deverá ser:
```
{
    "emailDestinario":"joao@email.com",
    "remessa": 1000
}
```
