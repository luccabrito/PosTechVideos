# #####| PosTechVideos |############################

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