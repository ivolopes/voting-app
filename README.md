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
    
Decisões:

    No momento da votação, pensando em performance, ao chamar o endpoint, estou salvado primeiro o voto
    na fila ao invés de salvar no banco diretamente. Com essa decisão, o tempo de resposta foi para a metade.
    
    Ao utilizar o webflux ao invés do web mvc, também tem um ganho em performance também.

    Foi criado um job que roda a cada um minuto para enviar o resultado da pauta para a fila. 
    O resultado é enviado com no mínimo dois minutos após o termino da sessão, pra evitar que tenha votos na
    fila que ainda precisa ser consumida.

    No resultado, estou enviando o total de votos que teve para cada opção. 
    

