# #####| PosTechVideos |#####

* **Grupo:** 60
* **Turma:** 1ADJT - 2023
* **Integrantes**
  ```
    Guilherme Franco: oguilhermefranco@gmail.com
    Vinicius M. de Menezes: paravinicius@gmail.com
    Lucca Brito Gesteira: luccabritogt@gmail.com
  ```

# Sobre o projeto

Este projeto foi elaborado como o 4º Tech Challenge da Pós-Tech da FIAP + Alura de Arquitetura e Desenvolvimento com Java. Foi desenvolvido o backend de uma aplicação web de streaming de vídeos, utilizando os conceitos de programação reativa.

A respeito da arquitetura do projeto, aplicamos a Clean Architecture da melhor forma que nos foi possível. Esta arquitetura propõe uma clara separação de responsabilidades em camadas. Aqui no projeto, utilizamos Controllers, UseCases, Entities e Repositories, visando manter as regras de negócio independentes de detalhes de implementação externos, facilitando desta forma a manutenção do código.

Tendo em vista as decisões técnicas, uma das discussões mais interessantes que ocorreram foi a respeito das Entidades que teríamos em nosso projeto. Originalmente, havíamos pensado em apenas duas: Video e User. A entidade User haveria um atributo do tipo List < Video >, para representar os vídeos favoritos daquele usuário.

Entretanto, tendo em vista que não utilizamos um banco de dados relacional para fazer o relacionamento Many To One e após encontrar dificuldades em recuperar os dados do User sem esta lista de favoritos, o que faria sentido para alguns dos UseCases, decidimos criar uma terceira entidade: Favorito.

A entidade Favorito, neste cenário, possui dois atributos: Username, para indicar o usuário que favoritou aquele vídeo; e o videoId, representando o ID do vídeo favoritado. Desta forma, foi criado uma nova Collection para o Favorito. Isso ajudou a manter o código mais limpo e organizado, tendo em vista que fizemos uma refatoração para ter o FavoritoController e demais fluxos, separando ainda mais as responsabilidades de cada classe.

O guia de uso de aplicação será através da Collection do Postman, que inclui todos os endpoints desenvolvidos, assim como do vídeo gravado que segue em anexo com a entrega deste projeto.
Vide anexo: *FIAP - Tech challenge_4.postman_collection.json*

# Funcionalidades Principais
O sistema possui as seguintes principais APIs e quesrão mais detalhadas na seção de endpoints:

1. Registro de usuários (UserController)
2. Registro de favoritos do usuário (FavoritoController)
3. Registro de videos (VideoController)
4. Relatório de estatisticas (EstatisticaController)

# Estrutura do projeto
Como a projeto possui 4 APIs principais e outas de suporte. Localizado em
```
src/main/java/com/fiap/postech/videos/controllers  
- ./EstatisticaController.java
- ./FavoritoController.java
- ./VideoController.java
- ./EstatisticaController.java
```

Se tratando um laboratório com foco na aprendizagem de Qualidade, Arquitetura e Segurança este projeto ainda não contem a camada de view do projeto.
O trabalho foi feito nas camadas responsáveis de controller, conforme os conceitos da aquitetura hexagonal e clean code, e persistência, conforme a seguir:
```
src/main/java/com/fiap/postech/videos
- ./controllers
- ./dto
- ./entities
- ./repositories
- ./usecases
```

O projeto também conta com uma busta arquitetura de teste. Onde foi aplicada tecnologias de JUnit, Mokito e Assertj:
src/test/java/com/fiap/postech/videos
```
- ./controllers
- ./usercases
```

# Como montar o ambiente
O JPA deste projeto foi configurado para criara automáticamente todas as tabelas necessárias. Basta configurar corretamente no
**application.properties**
as configurações do banco de dados na máquina local, num schema vazio e inicar o projeto.

## Tecnologias empregadas

### Spring WebFlux
O Spring WebFlux é um framework de programação reativa para aplicações web que oferece suporte a entrada e saída não bloqueante. Ele é totalmente compatível com o Spring Web MVC e pode ser usado com Reactive Streams, Netty, Undertow e contêineres Servlet.

### Repositório
Este projeto utiliza Reactive MongoRepository para poder acessar o banco de dados Mongo de forma assincrona em conjunto
com a arquitetura spring webflux.

O acesso ao banco de dados é todo encapsulado na camada `com/tc/tech_challange/repositories`, extendendo a classe do
framwork Reactive Mongo (ReactiveMongoRepository).

### Lombok
Foi utilizado o framework Lombok, com objetivo de diminuir a verbosidade das classes de mapeamento JPA, DTOs, Beans entre outros.

Durante o desenvolvimento deste projeto foi possível identificar que a sua vantagem é evitar a repetição comuns de códigos, como a criação de gets e sets para todos os atributos, métodos equals e hashCode, toString, Construtores entre outros. Dessa forma, o código fica mais limpo e claro.

### Validadores de campos
Para a validação de campos foi utilizado o pacote da bliblioteca do jakarta.validation.constraints.*. Com esta solução foi possível fácilmene mapear regras de validação um simples anotation, como: obrigatóridade de campos, valores máximos e mínimos e até regexp, conforme será destacando na seção das APIs.

### JUnit
O JUnit é um framework de testes unitários em Java que permite verificar e validar o funcionamento de classes e métodos
de forma automatizada e repetível. O JUnit é baseado na arquitetura xUnit, que é um padrão para frameworks de testes em diversas linguagens de programação. O JUnit facilita a escrita de testes, a execução dos testes e a apresentação dos resultados.

### Mokito
O Mokito é um framework de testes unitários em Java que permite criar e controlar objetos simulados (mocks) para isolar as dependências e o comportamento dos métodos. Ele é usado para desenvolver aplicações testáveis e com código limpo e legível.


# Como empacotar e rodar o projeto

```
./mvnw clean package

java -jar target/PosTechVideos-semestre4-0.0.1-SNAPSHOT.jar
```
