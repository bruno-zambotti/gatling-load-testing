## Testes de desempenho com Gatling Load Testing

Este projeto tem como objetivo agrupar os testes de desempenho de uma aplicação fictícia chamada ```customer-service```

Será utilizado a ferramenta Gatling em sua versão open-source, para mais informações, acesse o [link](https://gatling.io/open-source/start-testing/)

Será necessário para executar os testes aqui demonstrados, será:
- Realizar o download do repositório [customer-service](https://github.com/bruno-zambotti/microservices/tree/main/customer-service) e executá-lo seguindo as instruções em sua documentação.
- A JDK do Java 8 ou superior
- Scala 2.12, devido a limitações de compatibilidade com a versão atual do gatling 3.4.1.

### Executar as simulações

Para executar todas simulações, é necessário que o nome das classes terminem em `*Simulation.scala` 
Se estiver utilizando Windows, informe o seguinte comando:
```
gradlew gatlingRun
```
Caso esteja utilizando outro sistema operacional,informe o seguinte comando:
```
./gradlew gatlingRun
```

Caso deseje executar apenas uma simulação específica, informe o seguinte comando de exemplo no terminal em caso de Windows:
```
gradlew gatlingRun-br.com.zambotti.customer.CustomerApiSimulation
```
Para outros sistemas operacionais:
```
./gradlew gatlingRun-br.com.zambotti.customer.CustomerApiSimulation
```

### Criando uma simulação
Os testes são executados utilizando a ferramenta Gatling.
Consulte a documentação para criar as suas simulações:
- https://gatling.io/docs/current
- https://gatling.io/docs/current/cheat-sheet/ 

### Debugando os valores passados na sessão
É possível visualizar os valores que estiverem na sessão, isso pode ser realizado tanto durante a execução dos cenários ou nos passos das simulações. Sendo necessário informar o seguinte comando a instrução que estiver executando, alterando o valor do texto 'value' pelo valor desejado.
```
    .exec( session => {
      println( "value:      " + session("value").as[String] )
      session
    })
```

### Exemplo de Funcionamento 
![](assets/example.gif)


### Para saber mais:
- [Gravando simulações de forma simples](https://gatling.io/open-source/start-testing/)
    ![](assets/tutorial_example.gif)
    Durante o teste foi utilizado o seguinte comando:
    ```
    curl --location --request GET http://computer-database.gatling.io/computers --proxy localhost:8000
    ```


- [Exemplo oficial com gradle](https://github.com/gatling/gatling-gradle-plugin-demo)
    ![](assets/official_example.gif)

