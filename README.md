###### Voting Application ######

Tecnologias utilizadas:

    - Java 17
    - Spring boot com Webflux
    - MongoDB
    - RabbitMQ

Antes de rodar:

    Eu criei um docker compose para subir uma instância do RabbitMQ e do MongoDB.
    Antes de rodar a aplicação, é necessário rodar o comando "docker compose up"

Organização do código:

    Foi utilizado a arquitetura hexagonal para organizar toda a estrutura de pacotes e classes.
    O projeto está separado em três principais pacotes:
        - application: Entrada de dados para a aplicação.
        - domain: Toda a lógica de negócio do projeto está nessa classe
        - infrastructure: Integrações para a busca de dados.
    
    
    

