---
layout: default
title: Teste de componente
parent: Teste de desenvolvimento
nav_order: 9
---

# Teste de componente

Como visto anteriormente, os teste de componente sucedem os testes unitários, ou seja, trata-se de uma etapa onde classes são combinadas a fim de formar módulos a serem testados. Um dos plugins do Maven mais famosos para executar testes de integração é o [Failsafe](https://maven.apache.org/surefire/maven-failsafe-plugin/). A principal diferença entre o [Failsafe](https://maven.apache.org/surefire/maven-failsafe-plugin/) e o [Surefire](https://maven.apache.org/surefire/maven-surefire-plugin/) é que no primeiro, se um teste falhar o processo de construção do sistema (*build*) não será comprometido.

O plugin Failsafe tem apenas dois objetivos:

* failsafe:integration-test - executa os testes de integração de um aplicativo
* failsafe:verify - verifica se os testes de integração de um aplicativo foram aprovados

Assim, se o Failsave estiver configurado em um projeto Maven, basta executar, por exemplo, o comando `verify` para que o Failsave possa ser executado:

    mvn verify

## Teste de componente no projeto Tpack

Por ser um projeto pequeno, iremos utilizar o [Tpack](https://github.com/rodrigoprestesmachado/tpack) para ilustrar a utilização do Failsave. Esse projeto implementa um questionário que avalia o conhecimento pedagógico, tecnológico e de conteúdo de um professor. O Tpack possui dois componentes principais, um *front-end* escrito em [Vue](https://vuejs.org)/[Vuetify](https://vuetifyjs.com/en/) e um *back-end* que implementa um RESTFul Web Service em Java.
O sistema Tpack (*front* e *back-end*) são instalados e executados dentro de um *container* [Open Liberty](https://hub.docker.com/_/open-liberty) Docker. Para armazenar os dados, o projeto também utiliza um segundo *container* [MySQL](https://hub.docker.com/_/mysql), ou seja, para rodar o projeto Tpack são necessários dois *containers*, um com o sistema e outro com o banco de dados.

Por possuir essas questões de *containers* e por ser baseado em uma arquitetura de serviço Web, o projeto Tpack apresenta uma boa complexidade para analisarmos o funcionamento dos testes de componentes. Assim, as próximas seções desse documento pretendem explicar alguns detalhes de como podemos construir um ambiente de teste de RESTful Web Services que rodam por meio de *containers* Docker.

---
Para saber mais: consulte o o capítulo 7 do livro [Desenvolvimento de software, v.3 programação de sistemas web orientada a objetos em Java](https://biblioteca.ifrs.edu.br/pergamum_ifrs/biblioteca_s/acesso_login.php?cod_acervo_acessibilidade=5020683&acesso=aHR0cHM6Ly9pbnRlZ3JhZGEubWluaGFiaWJsaW90ZWNhLmNvbS5ici9ib29rcy85Nzg4NTgyNjAzNzEw&label=acesso%20restrito) para compreender detalhes sobre o desenvolvimento de serviços Web (Web Services) em Java.

---

## Configuração do Failsave

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

<center>Exemplo 1 - configuração do Failsave</center>

Sobre a implementação dos testes, o código está divido em dois arquivos Java, são eles: [TpackCompose.java](https://github.com/rodrigoprestesmachado/tpack/blob/master/src/test/java/edu/ifrs/tpack/integration/TpackApiIT.java) e [TpackApiIT.java](https://github.com/rodrigoprestesmachado/tpack/blob/master/src/test/java/edu/ifrs/tpack/integration/TpackCompose.java). Enquanto o [TpackCompose.java](https://github.com/rodrigoprestesmachado/tpack/blob/master/src/test/java/edu/ifrs/tpack/integration/TpackApiIT.java) se encarrega de rodar os dois *containers* (sistema e banco) por meio dos _frameworks_ de teste [testcontainers](https://www.testcontainers.org) e [microshed](https://microshed.org/microshed-testing/), o arquivo [TpackApiIT.java](https://github.com/rodrigoprestesmachado/tpack/blob/master/src/test/java/edu/ifrs/tpack/integration/TpackApiIT.java) implementa os testes de integração propriamente ditos.

## TpackCompose

Como dito anteriormente, a principal função desse [arquivo](https://github.com/rodrigoprestesmachado/tpack/blob/master/src/test/java/edu/ifrs/tpack/integration/TpackApiIT.java) é colocar os *containers* (sistema e banco) para rodar para que os testes possam ser realizados. Assim, a classe TpackCompose implementa duas interfaces do Junit `BeforeAllCallback` e `AfterAllCallback` que garantem que os container serão executados antes dos testes e parados no final, observe o trecho de código do Exemplo 2.

```java
public class TpackCompose implements BeforeAllCallback, AfterAllCallback {

}
```
<center>Exemplo 2 - Interfaces BeforeAllCallback e AfterAllCallback</center>

Agora, repare na implementação do método `beforeAll` abaixo (Exemplo 3):

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
<center>Exemplo 3 - O método beforeAll</center>

De um ponto de vista geral, o método `beforeAll` é responsável por: (1) criar uma rede interna para que os containers possam se comunicar, (2) instanciar e configurar um container para o MySql e (3) iniciar ambos os container.

Outro trecho interessante do código do TpackCompose está na seguinte linha abaixo:

```java
 @Container
public static ApplicationContainer tpack = new ApplicationContainer().withAppContextRoot("/tpack").waitingFor(Wait.forHttp("/tpack"));
```
<center>Exemplo 4 - Instanciando o container da aplicação Tpack</center>

O Exemplo 4, mostra que a anotação `@Container` do *framework* [testcontainers](https://www.testcontainers.org)) faz com que o *container* que implementa o sistema Tpack seja construído respeitando a configuração indicada no arquivo [Dockerfile](https://github.com/rodrigoprestesmachado/tpack/blob/master/Dockerfile) localizado na raíz do projeto.

Resumindo, o código da classe `TpackCompose` garante que os *containers* (sistema e banco) sejam executados antes dos testes de integração e, ao final, o método `afterAll` fará que os containers de teste possam ser parados de uma maneira apropriada.

## TpackApiIT

O primeiro detalhe importante para executarmos um testes de integração com o Failsave é nomearmos a classe dentro dos [parâmetros](https://maven.apache.org/surefire/maven-failsafe-plugin/examples/inclusion-exclusion.html) de inclusão e exclusão dos testes desse plugin. No caso do exemplo, repare que o a classe TpackApiIT termina com o prefixo **IT**.

Outra questão que chama a atenção na classe [`TpackApiIt`](https://github.com/rodrigoprestesmachado/tpack/blob/master/src/test/java/edu/ifrs/tpack/integration/TpackApiIT.java) é a anotação `@ExtendWith({ TpackCompose.class })`. A anotação [`@ExtendWith`](https://junit.org/junit5/docs/current/user-guide/#extensions-registration-declarative) permite com que declaremos uma classe que representa uma extensão do código de teste, nesse caso, garantindo que os *containers* sejam executados antes e, finalizados depois da execução dos testes. O trecho código do Exemplo 5 ilustra a utilização da anotação `@ExtendWith`.

```java
@ExtendWith({ TpackCompose.class })
public class TpackApiIT {
```
<center>Exemplo 5 - Anotação ExtendWith</center>

Outro detalhe importante classe `TpackApiIT` é o seu construtor, observe o trecho de código abaixo:

```java
public TpackApiIT() {
    this.client = HttpClients.createDefault();
    host = TpackCompose.tpack.getContainerIpAddress();
    port = TpackCompose.tpack.getFirstMappedPort();
}
```
<center>Exemplo 5 - Obtendo o ip e a porta de um *container*</center>

Como estamos tentando rodar um teste em um Web Service (*back-end*) se faz necessário obter o endereço ip e porta do serviço. Assim, devemos consultar os *containers* iniciados pela classe `TpackCompose` sobre as configurações correntes, como ilustra o trecho de código do construtor do Exemplo 5.

Finalmente, o único método de teste deste exemplo, `getSessions`, implementa uma verificação capaz de realizar uma chamada para o Web Service do sistema Tpack e obter dados em JSON sobre as questões para os professores. Um detalhe importante, esse teste utiliza o cliente [HTTP da Apache](https://hc.apache.org) para poder realizar as chamadas para o Web Service do sistema (poderia ser utilizado qualquer cliente HTTP). O trecho de código abaixo, mostra parte do método  `getSessions` que realiza a chamada de HTTP para o serviço do sistema Tpack, os passos são: (1) monta a url para fazer uma chamada, (2) instancia um objeto HttpGet, (3) realiza a chamada de Get e recebe uma resposta do serviço e (4) verifica se a chamada foi bem sucedida por meio do código 200 que, no HTTP, representa sucesso.

```java
// mounts the URL
String url = "http://" + host + ":" + port + API + "getSessions";
// creates a http get
HttpGet get = new HttpGet(url);
// executes and getting the response
HttpResponse response = this.client.execute(get);
```
<center>Exemplo 6 - Trecho do método getSessions</center>

## Compilando o projeto TPACK

O projeto Tpack utiliza um [plugin](https://code.google.com/archive/p/maven-replacer-plugin/) do Maven para alterar os arquivos do sistema tanto para produção quanto para o desenvolvimento. Assim, para ajustar os arquivos do projeto Tpack para um modo de desenvolvimento e/ou produção, copie o arquivo [settings.xml](https://github.com/rodrigoprestesmachado/tpack/blob/master/scripts/settings.xml) para o diretório oculto `.m2` no Maven. No Linux/Unix o diretorio `.m2` se encontra no diretório home de um usuário, como por exemplo, `/home/rodrigo/.m2`. O arquivo [settings.xml](https://github.com/rodrigoprestesmachado/tpack/blob/master/scripts/settings.xml) esá configurado por padrão para um modo de **desenvolvimento**, assim, não existe a necessidade de modificar esse arquivo para realizar testes locais no Web Services do sistema. No caso de dúvidas, você conferir todos os passos da compilação do projeto Tpack (_back_ e _front end_) por meio do script [build.sh](https://github.com/rodrigoprestesmachado/tpack/blob/master/scripts/build.sh).

Caso você queria rodar os testes de Web Service (_back end_) sem utilizar o recurso do [plugin replacer](https://code.google.com/archive/p/maven-replacer-plugin/), basta alterar manualmente o arquivo [server.xml](https://github.com/rodrigoprestesmachado/tpack/blob/master/src/main/liberty/config/server.xml) na linha 30, de:

```xml
     <properties serverName="REPLACE-DB" ...
```

para:

```xml
     <properties serverName="db" ...
```

Essa alteração, que normalmente é realizada pelo [plugin replacer](https://code.google.com/archive/p/maven-replacer-plugin/), faz com que a aplicação Tpack procure na rede o servidor de banco de dados MySQL de nome `db`. Note na linha 54 do arquivo [TpackCompose.java](https://github.com/rodrigoprestesmachado/tpack/blob/master/src/test/java/edu/ifrs/tpack/integration/TpackCompose.java) que `db` é o nome de rede atribuído ao servidor do MySQL durante o teste do Web Service do sistema Tpack. Observe também que o método `beforeAll` do [TpackCompose.java](https://github.com/rodrigoprestesmachado/tpack/blob/master/src/test/java/edu/ifrs/tpack/integration/TpackCompose.java) coloca tanto o _container_ da aplicação Tpack quando o _container_ que roda o MySQL na mesma rede e, por esse motivo, a plicação Tpack consegue encontrar o servidor do MySQL.

## Rodando os teste de componentes do Tpack

Finalmente, se você compilou corretamente o projeto Tpack, utilize o comando `mvn verify` do Failsafe para rodar os testes de componente nesse projeto. Porém, atenção, trata-se de um teste que necessita baixar e criar dois _containers_, assim, é um tipo de teste que costuma ser demorado.

# Referências bibliográficas

SOMMERVILLE, Ian. [Engenharia de software](https://biblioteca.ifrs.edu.br/pergamum_ifrs/biblioteca_s/acesso_login.php?cod_acervo_acessibilidade=5030950&acesso=aHR0cHM6Ly9taWRkbGV3YXJlLWJ2LmFtNC5jb20uYnIvU1NPL2lmcnMvOTc4ODU0MzAyNDk3NA==&label=acesso%20restrito), 10ª ed. Editora Pearson 768, cap. 8 ISBN 9788543024974.

MACHADO, Rodrigo Prestes. [Desenvolvimento de software, v.3 programação de sistemas web orientada a objetos em Java](https://biblioteca.ifrs.edu.br/pergamum_ifrs/biblioteca_s/acesso_login.php?cod_acervo_acessibilidade=5020683&acesso=aHR0cHM6Ly9pbnRlZ3JhZGEubWluaGFiaWJsaW90ZWNhLmNvbS5ici9ib29rcy85Nzg4NTgyNjAzNzEw&label=acesso%20restrito). Porto Alegre Bookman 2016 (Tekne). ISBN 9788582603710.

<center>
<a href="https://github.com/rodrigoprestesmachado" target="blanck"><img src="../imgs/logo.png" alt="Rodrigo Prestes Machado" width="3%" height="3%" border=0 style="border:0; text-decoration:none; outline:none"></a><br/>
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/">CC BY 4.0 DEED</a>
</center>