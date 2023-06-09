# AltJack Game 
[![codecov](https://codecov.io/gh/Gegeu/blackjackgame/branch/main/graph/badge.svg?token=ZFE5CTM9SW)](https://codecov.io/gh/Gegeu/blackjackgame)

## 📓Introdução
API criada utilizando Spring Boot para simulação de um jogo de blackjack (21, no Brasil).

## 👨‍💻Tecnologias Utilizadas
* JDK 17
* Spring Boot 3.1.0
* Spring Data Redis
* Spring Data JPA
* Postgresql
* JaCoCo
* Railway (Hospedagem)

## 💭 Desenvolvimento
Incialmente foi idealizado um fluxo onde os dados de cada rodada ficariam armazenados no banco de dados. Após testes iniciais, foi definido que as rodadas ficaram em um banco em memória (Redis) e ao final da partida, o jogador pode salvar seu resultado no banco de dados.
Para controlar a partida, os dados são salvos no banco em memória e relacionados ao jogador pelo ID do jogo.
Assim, os jogadores são independetes entre si, com baixo risco de conflito.

### 🧱 Diagrama da Aplicação
![](https://raw.githubusercontent.com/Gegeu/blackjackgame/main/diagrama%20altjack.jpg)

## 📝 Documentação e Uso
Você pode utilizar com o próprio swagger da aplicação:
* Ambiente hospedado: https://blackjackgame-production.up.railway.app/swagger-ui/index.html
* Ambiente local: localhost:PORTA_DA_APLICACAO/swagger-ui/index.html#/
![](https://raw.githubusercontent.com/Gegeu/blackjackgame/main/blackjackswagger.jpg)

### Configuração local
Edite as variaveis de ambiente para uma instância do Redis e Postgresql:
```
spring.data.redis.host=REDIS_HOST
spring.data.redis.port=REDIS_PORT
spring.data.redis.username=REDIS_USERNAME
spring.data.redis.password=REDIS_PASS
spring.datasource.url=POSTGRES_HOST
spring.datasource.username=POSTGRES_USERNAME
spring.datasource.password=POSTGRES_PASS
```
Dentro da pasta raiz do projeto, baixe as dependências com maven install:
```
./mvnw clean install
```
Para iniciar a aplicação, execute o comando spring run:
```
./mvnw spring-boot:run
```

### 🛡Testes
Para executar os testes com a cobertura, acesse a pasta raiz e execute: 
```
./mvnw clean install
```
Será gerado um relatório pelo JaCoCo com a cobertura, disponível no diretório:
```
target/site/jacoco/index.html
```

## 🎮 Como Jogar
1. Crie um jogo fazendo uma requisição para /games, você receberá o ID do seu jogo
2. Jogue a rodada, fazendo uma requisição para /games/{idDoJogo}, a cada requisição você receberá suas cartas e sua pontuação, assim como do dealer
3. Ao ganhar ou perder, você pode salvar o resultado, fazendo uma requisição para /games/{idDoJogo}/results, passando seu nome para registrar a pontuação
4. Você pode consultar a pontuação de outros jogadores em /games/results
5. Você pode excluir um jogo, fazendo uma requisição para /games/{idDoJogo}