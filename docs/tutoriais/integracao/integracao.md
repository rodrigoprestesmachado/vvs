# Teste de integração com o Failsafe

Como visto anteriormente, o teste de integração/componente, sucedem os testes unitário, ou seja, trata-se de uma etapa onde classes são combinadas a fim de formar módulos de testes a serem testados. O plugin do Maven Failsafe foi projetado para executar testes de integração. A principal diferença entre o Failsafe e o Surefire é que no primeiro, se um teste falhar o seu processo de build não será comprometido.

O plugin Failsafe tem apenas dois objetivos:

* failsafe:integration-test: executa os testes de integração de um aplicativo
* verifica se os testes de integração de um aplicativo foram aprovados

## Teste de integração no projeto Tpack

O projeto [Tpack](https://github.com/rodrigoprestesmachado/tpack) implementa um questionário que avalia o conhecimento de conteúdo, pedagógico e tecnológico de um professor. Esse sistema possui dois componentes principais, um *front-end* escrito em [Vue](https://vuejs.org)/[Vuetify](https://vuetifyjs.com/en/) e um *back-end* que implementa um serviço Web Rest em Java.
O sistema Tpack (*front* e *back-end*) são instalados e executados dentro de um *container* [Open Liberty](https://hub.docker.com/_/open-liberty). Para que o sistema possa funcionar adequadamente, se faz necessário um banco de dados, nesse sentido, o projeto utiliza um segundo *container* que roda um banco [MySQL](https://hub.docker.com/_/mysql). Resumindo, para rodar o projeto Tpack são necessários dois containers, um com o sistema e outro com o banco de dados.

O projeto Tpack é um bom exemplo de como podemos utilizar um teste de integração/componente para verificar e validar um serviço Web, pois, ele possui uma complexidade que está cada vez mais presente nos sistemas atuais. Assim, as próximas seções desse documento pretendem explicar detalhes de como podemos construir um ambiente de teste de integração de um sistema baseado em *containers* Docker.

## Failsave

Se observarmos o [arquivo](https://github.com/rodrigoprestesmachado/tpack/blob/master/pom.xml) `pom.xml` iremos observar a presença do plugin Failsave no projeto por meio do seguinte trecho de código:

```xml
<plugin>
<groupId>org.apache.maven.plugins</groupId>
<artifactId>maven-failsafe-plugin</artifactId>
<version>3.0.0-M5</version>
<executions>
    <execution>
    <goals>
        <goal>integration-test</goal>
        <goal>verify</goal>
    </goals>
    </execution>
</executions>
</plugin>
```

Os testes de integração com o Fail save são divididor em dois arquivos java [TpackCompose.java](https://github.com/rodrigoprestesmachado/tpack/blob/master/src/test/java/edu/ifrs/tpack/integration/TpackApiIT.java) e [TpackApiIT.java](https://github.com/rodrigoprestesmachado/tpack/blob/master/src/test/java/edu/ifrs/tpack/integration/TpackCompose.java). Enquanto o TpackCompose se encarrega de rodar os dois *containers* do sistema (tpack e banco) por meio dos frameworks de teste [testcontainers](https://www.testcontainers.org) e [microshed](https://microshed.org/microshed-testing/), o arquivo TpackApiIT implementa os testes propriamente ditos.

## TpackCompose

A principal função desse [arquivo](https://github.com/rodrigoprestesmachado/tpack/blob/master/src/test/java/edu/ifrs/tpack/integration/TpackApiIT.java) é colocar o sistema para rodar para que os testes possam ser realizados. Assim, a classe TpackCompose implementa duas interfaces do Junit BeforeAllCallback e AfterAllCallback que garantem que os container serão executados antes dos testes e parados no final.

```java
public class TpackCompose implements BeforeAllCallback, AfterAllCallback {

}
```

Agora, repare na implementação do método `beforeAll`  abaixo:

```java
@Override
public void beforeAll(ExtensionContext context) throws Exception {

    // Create a private network for the containers
    Network network = Network.newNetwork();

    // Create a MySQL container to work with tpack service
    mysql = new MySQLContainer<>("mysql:latest");
    // Configures the  data base
    mysql.withDatabaseName("tpack");
    mysql.withUsername("tpack");
    mysql.withPassword("tpack");
    mysql.withNetworkAliases("db");
    mysql.withExposedPorts(3306);
    mysql.waitingFor(Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(60)));
    // Puts the Mysql in the network
    mysql.withNetwork(network);

    tpack.withExposedPorts(9080);
    // Sets network for tpack service
    tpack.withNetworkAliases("tpack");
    tpack.withNetwork(network);

    // Start the containers
    mysql.start();
    tpack.start();
}
```

De um ponto de vista geral, o método `beforeAll` é responsável por: (1) criar uma rede interna para que os containers possam se comunicar, (2) instanciar e configurar um container para o MySql e (3) iniciar ambos os container.

Outro trecho interessante desse código está na seguinte linha abaixo:

```java 
 @Container
public static ApplicationContainer tpack = new ApplicationContainer().withAppContextRoot("/tpack").waitingFor(Wait.forHttp("/tpack"));
```

A anotação `@Container` do *framework* [testcontainers](https://www.testcontainers.org)) faz com que o *container* que implementa o sistema Tpack seja construído respeitando a configuração indicada no arquivo [Dockerfile](https://github.com/rodrigoprestesmachado/tpack/blob/master/Dockerfile) localizado na raíz do projeto.

Contudo, o código da classe `TpackCompose` garante que os *containers* serão executados antes dos testes de integração e, ao final, o método `afterAll` garantirá que os containers de teste possam ser parados de uma forma apropriada.

## TpackApiIT

O primeiro detalhe importante para executarmos um testes de integração com o Failsave é nomearmos a classe dentro dos [parâmetros](https://maven.apache.org/surefire/maven-failsafe-plugin/examples/inclusion-exclusion.html) de inclusão e exclusão dos testes. No caso do exemplo, o nome da classe termina com o prefixo **IT**.

O primeiro detalhe que chama a atenção na classe [`TpackApiIt`](https://github.com/rodrigoprestesmachado/tpack/blob/master/src/test/java/edu/ifrs/tpack/integration/TpackApiIT.java) é a anotação `@ExtendWith({ TpackCompose.class })`. A anotação [`@ExtendWith`](https://junit.org/junit5/docs/current/user-guide/#extensions-registration-declarative) permite com que declaremos uma classe que representa uma extensão do código de teste, nesse caso, garantindo que os *containers* sejam executados antes e terminados depois da execução dos testes. O trecho código abaixo ilustra a utilização da anotação `@ExtendWith`.

```java
@ExtendWith({ TpackCompose.class })
public class TpackApiIT {
```

Outro detalhe importante classe `TpackApiIT` é o seu construtor, observe o trecho de código abaixo:

```java
public TpackApiIT() {
    this.client = HttpClients.createDefault();
    host = TpackCompose.tpack.getContainerIpAddress();
    port = TpackCompose.tpack.getFirstMappedPort();
}
```

Como estamos tentando rodar um teste em um Web service (*back-end*) se faz necessário obter o endereço ip e porta do serviço. Assim, devemos consultar os *containers* iniciados pela classe `TpackCompose` sobre as configurações correntes, como ilustra o trecho de código contido no construtor.

Finalmente, o único método deste exemplo, `getSessions`, implementa um teste capaz de realizar uma chamada para o Web Service do sistema Tpack e obter dados em JSON sobre as questões para os professores. Um detalhe importante, esse teste utiliza o cliente HTTP da [Apache](https://hc.apache.org) para poder realizar as chamadas para o Web Service do sistema. O trecho de código abaixo, mostra parte do método  `getSessions` que realiza a chamada de HTTP para o serviço do sistema Tpack, os passos são nessa ordem: (1) monta a url para fazer uma chamada, (2) instancia um objeto HttpGet, (3) realiza a chamada de Get e recebe uma resposta do serviço e (4)  verifica se a chamada foi bem sucedida por meio do código 200 que, no HTTP, representa sucesso. 

```java
// mounts the URL
String url = "http://" + host + ":" + port + API + "getSessions";
// creates a http get
HttpGet get = new HttpGet(url);
// executes and getting the response
HttpResponse response = this.client.execute(get);
```
