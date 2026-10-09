---
layout: default
title: JaCoCo
parent: Teste de componente
grand_parent: Teste de desenvolvimento
nav_order: 14
---

# JaCoCo 🏠

<center>
    <iframe src="https://vvs.rpmhub.dev/jacoco/slides/index.html#/"
    title="JaCoCo"
    width="90%" height="500" style="border:none;">
    </iframe>
</center>

Os slides acima apresentam a cobertura de código com o JaCoCo num projeto
Quarkus. O exemplo é o cadastro de livros em
[`exemplos/hexagonal`](https://github.com/rodrigoprestesmachado/vvs/tree/dev/exemplos/hexagonal):
a pasta se chama hexagonal, e o sistema é a livraria (`Book`,
`BooksService`, `BookResource`). Nas seções a seguir, a mesma ideia aparece
com mais calma e, nos exercícios, você acende o relatório desse projeto.
{: .fs-3 }

## A planta da casa

Imagine o código como a planta de uma casa, à noite. Cada método é um
cômodo. Cada `if` é uma porta. Os testes são quem entra e acende a luz.
{: .fs-3 }

O [JaCoCo](https://www.jacoco.org/jacoco/) (*Java Code Coverage*) desenha
essa planta: onde alguém passou, a luz fica acesa. O relatório é esse
desenho.
{: .fs-3 }

- Cômodo **verde**: o método foi percorrido por inteiro.
- Interruptor **âmbar**: alguém entrou, mas deixou uma porta fechada.
- Cômodo **escuro**: nenhum teste chegou lá.
{: .fs-3 }

Esse é o argumento do slide **A planta da casa**. A moral fica para o
fim da página: acender todas as luzes não prova que a casa está em ordem.
Cobertura mede o que foi executado. Quem diz se o resultado está certo é
o `assert`.
{: .fs-3 }

## O que a planta mostra

O relatório HTML mostra vários contadores. Você não precisa decorar a
fórmula de cada um. Precisa saber o que está olhando. O slide **O que ele
mede** resume os cinco que mais aparecem.
{: .fs-3 }

- **Instrução.** O passo mais curto no chão: uma instrução de bytecode.
  Várias instruções cabem numa única linha de Java.
- **Linha.** O corredor. Uma linha conta como coberta quando pelo menos uma
  instrução dela rodou.
- **Ramo.** Cada porta. Um `if`, um `else` e cada saída de um `switch` são
  ramos. O losango desenhado na linha é esse contador.
- **Método.** O cômodo. Entra na conta quando o método é chamado.
- **Classe.** A ala da casa. Entra na conta quando pelo menos um método
  dela roda.
{: .fs-3 }

Há ainda a coluna de complexidade (ciclomática): um número de caminhos
independentes. Ela ajuda a perceber um método cheio de portas. Não é a
meta dos exercícios desta página.
{: .fs-3 }

### Linha e ramo não são a mesma coisa

A linha do `if` acende assim que a condição é avaliada, mesmo que só um
lado tenha sido tomado. O ramo só fica verde quando as duas portas abrem.
Por isso a cobertura de linhas pode parecer boa enquanto a de ramos ainda
está pela metade. O slide **O losango âmbar** usa a validação de título
do `Book`, no cadastro de livros:
{: .fs-3 }

```java
if (title == null || title.isBlank()) {
    throw new InvalidBookException("Title cannot be blank");
}
```

Um POST com título `""` executa a linha do `if` e o `throw`. O losango
continua âmbar: a porta `title == null` não abriu, porque `||` tem duas
entradas. O `BooksService.add` tem a mesma forma de porta em
`opt.isPresent()`. No `BookResourceTest` ela já fica verde, porque existem
o POST que cria o livro (201) e o POST repetido (409).
{: .fs-3 }
{: .fs-3 }

## Como ler as cores

Abra o `index.html` do relatório e entre na classe. O JaCoCo pinta o fonte
assim (slide **As cores do relatório**):
{: .fs-3 }

- **Verde:** tudo o que está naquela linha foi executado.
- **Amarelo:** a linha rodou, mas algum ramo dela ficou de fora. O losango
  ao lado da linha é a porta ainda fechada.
- **Vermelho:** nenhum teste passou por ali.
{: .fs-3 }

No topo da página, as barras repetem os contadores da seção anterior. Use
a de ramos quando quiser saber se as portas foram abertas, e a de linhas
quando quiser uma leitura rápida dos corredores.
{: .fs-3 }

## Por que a extensão, e não o plugin sozinho

Num projeto Maven comum, o JaCoCo entra como `jacoco-maven-plugin` e faz
dois trabalhos, cada um num goal:
{: .fs-3 }

- `prepare-agent` sobe um agente Java antes dos testes. Esse agente
  reescreve o bytecode no momento em que a classe é carregada e anota, em
  um arquivo `.exec`, cada instrução que rodou. O Surefire recebe essa
  configuração pela propriedade `argLine`.
- `report` lê o `.exec` depois dos testes e monta o HTML. Sem esse goal,
  os dados existem, mas a planta não aparece no navegador.
{: .fs-3 }

Esse arranjo funciona quando o teste e o código de produção são carregados
pelo mesmo classloader, o da JVM do Surefire. No cadastro de livros, o
`BooksServiceTest` é esse caso: ele monta o `BooksService` na mão, sem
subir a aplicação. A classe entra pela porta da frente, o agente está nessa
porta e acende a luz.
{: .fs-3 }

O `@QuarkusTest` muda a porta. O Quarkus sobe a aplicação dentro do teste e
carrega as classes já aumentadas pelo próprio classloader,
`QuarkusClassLoader`. O agente pendurado pelo `prepare-agent` continua na
porta da frente. Ele não acompanha o que entra pela porta de serviço, então
o teste pode passar e o cômodo da aplicação continuar escuro no relatório.
O slide **Por que a extensão no Quarkus?** resume esse desvio.
{: .fs-3 }

A extensão [`quarkus-jacoco`](https://quarkus.io/guides/tests-with-coverage/)
fica do lado de dentro dessa subida. Ela faz, para o `@QuarkusTest`, o que
os dois goals fariam à mão: instrumenta a classe quando o
`QuarkusClassLoader` a carrega, grava `target/jacoco-quarkus.exec` e gera
o HTML em `target/jacoco-report`. No cadastro de livros, quem passa por
essa porta é o `BookResourceTest`. O `BooksServiceTest` instancia
`BooksService` direto, sem `@QuarkusTest`, então a extensão sozinha não
acende esses testes. Por isso, nos exercícios desta página, a dependência
da extensão basta e os testes novos entram no `BookResourceTest`. Não é
preciso declarar `prepare-agent`.
{: .fs-3 }

Os dois juntos, sem a nota lá embaixo, instrumentam a mesma classe duas
vezes. O segundo passe encontra o bytecode que o primeiro já marcou e o
teste quebra com erro de classe já instrumentada. A configuração da nota
serve só ao caso misto: a extensão cobre o `@QuarkusTest`, e o plugin cobre
o JUnit que não sobe o Quarkus, ignorando o `QuarkusClassLoader` para não
repetir a marcação.
{: .fs-3 }

## Ligando as luzes no projeto

No `pom.xml`, a dependência fica no escopo de teste. A versão vem do BOM
do Quarkus, então não é preciso declará-la. O slide **Como ligar** traz o
mesmo bloco.
{: .fs-3 }

```xml
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-jacoco</artifactId>
    <scope>test</scope>
</dependency>
```

Gere o relatório com o ciclo que executa os testes:
{: .fs-3 }

```bash
./mvnw verify
```

### O relatório

Dois arquivos importam:
{: .fs-3 }

- `target/jacoco-report/index.html` é o mapa. Abra no navegador.
- `target/jacoco-quarkus.exec` são os dados brutos. O HTML é montado a
  partir dele.
{: .fs-3 }

O diretório do HTML é o valor padrão de `quarkus.jacoco.report-location`.
Se você mudar essa propriedade, o `index.html` passa a nascer no caminho
novo. O slide **Onde fica o relatório** lista os dois arquivos.
{: .fs-3 }

## Cômodos fora da planta

Um DTO, um código gerado ou uma classe só de configuração acendem pouco e
dizem pouco sobre a regra de negócio. Dá para tirá-los do mapa com
`quarkus.jacoco.excludes`, em `src/main/resources/application.properties`.
O padrão segue o do guia do Quarkus: `*` e `?` funcionam como curinga.
{: .fs-3 }

```properties
quarkus.jacoco.excludes=**/BookRequest.class
```

No cadastro de livros, `BookRequest` é o DTO da porta REST: o POST chama
`isbn()`, `title()` e os outros acessores, então o cômodo acende, mas ele
não é a regra de negócio. O padrão acima tira essa classe do relatório.
`quarkus.jacoco.includes` faz o contrário: quando está ausente, tudo entra.
O slide **Cômodos fora da planta** é esse recorte.
{: .fs-3 }

## Luz acesa não é casa em ordem

Três limites valem mais do que perseguir 100% (slide **Luz acesa não é
casa em ordem**):
{: .fs-3 }

- Cobertura alta com assert fraco só prova que o código rodou. Um POST em
  `/books` que só confere o status 201, sem olhar o ISBN devolvido, acende
  o cômodo do `add` e deixa o problema quieto.
- Cobrir cada ramo de um método enorme é sinal de que o método tem portas
  demais, não de que a meta é 100%.
- O modo nativo do Quarkus não gera esse relatório. A cobertura desta
  página é a dos testes na JVM.
{: .fs-3 }

---

**Testes que não sobem o Quarkus.** A extensão, sozinha, cobre testes
`@QuarkusTest`. Um teste JUnit puro, sem essa anotação, precisa do
`jacoco-maven-plugin` apontando para o mesmo arquivo de dados e ignorando
o classloader do Quarkus. Só use os dois juntos com essa configuração, como
no [guia de cobertura do Quarkus](https://quarkus.io/guides/tests-with-coverage/):
{: .fs-3 }

```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.12</version>
    <executions>
        <execution>
            <id>default-prepare-agent</id>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
            <configuration>
                <exclClassLoaders>*QuarkusClassLoader</exclClassLoaders>
                <destFile>${project.build.directory}/jacoco-quarkus.exec</destFile>
                <append>true</append>
            </configuration>
        </execution>
    </executions>
</plugin>
```

Os exercícios abaixo usam só `@QuarkusTest`. A dependência da extensão
basta.
{: .fs-3 }

---

## Exercícios práticos: o cadastro de livros

Os exercícios acontecem no projeto que já existe, em `exemplos/hexagonal`.
A pasta tem nome de arquitetura; o sistema é o cadastro de livros. Você não
cria outro projeto. Os exercícios vão do mais simples ao mais exigente.
Resolva na ordem. Depois de cada um, rode `./mvnw test` nessa pasta, abra
`target/jacoco-report/index.html` e só avance quando o que o enunciado
pede estiver visível na planta. O slide **Exercícios, um cômodo por vez**
é a lista curta.
{: .fs-3 }

`./mvnw test` sobe o MySQL de teste pelo Dev Services. É preciso Docker,
como no restante desse projeto.
{: .fs-3 }
{: .fs-3 }

### Preparação

Entre na pasta do cadastro de livros e abra o projeto.
{: .fs-3 }

```bash
cd exemplos/hexagonal
code .
```

O domínio está em `Book` e `BooksService`. A API está em `BookResource`,
com o DTO `BookRequest`. O teste que a extensão enxerga é o
`BookResourceTest`, anotado com `@QuarkusTest`. O `BooksServiceTest` fica
de fora desse relatório até a configuração da nota sobre testes que não
sobem o Quarkus.
{: .fs-3 }

### Exercício 1: achar o cômodo escuro
{: .fw-500 }

Adicione `quarkus-jacoco` ao `pom.xml` de `exemplos/hexagonal`, no escopo
`test`, como na seção **Ligando as luzes no projeto**. Rode `./mvnw test`
e abra `target/jacoco-report/index.html`. Em `Book.validate`, a linha
`if (title == null || title.isBlank())` fica amarela: os POSTs que já
existem mandam um título preenchido, então a porta do `throw` continua
fechada.
{: .fs-3 }

### Exercício 2: acender o corredor do título em branco
{: .fw-500 }

No `BookResourceTest`, acrescente um POST com título vazio. O
`BookExceptionMapper` traduz `InvalidBookException` em HTTP 400.
{: .fs-3 }

```java
@Test
void shouldReturn400WhenTitleIsBlank() {
    String json = """
            {
              "isbn": "020161622X",
              "title": "",
              "author": "Andrew Hunt",
              "publicationYear": 1999,
              "copiesAvailable": 3
            }
            """;

    given()
            .contentType(ContentType.JSON)
            .body(json)
            .when()
            .post("/books")
            .then()
            .statusCode(400)
            .body("message", notNullValue());
}
```

Rode `./mvnw test`. O `throw` do título em branco fica verde. O losango do
`||` continua âmbar: `title == null` ainda não foi visitado. Anote o
percentual de linhas e o de ramos de `validate`. Você usa esses dois
números no exercício 6.
{: .fs-3 }

### Exercício 3: a outra porta do `||`
{: .fw-500 }

Acrescente o POST em que o título vem nulo. As duas entradas de
`title == null || title.isBlank()` ficam acesas e o losango fica verde.
{: .fs-3 }

```java
@Test
void shouldReturn400WhenTitleIsNull() {
    String json = """
            {
              "isbn": "020161622X",
              "title": null,
              "author": "Andrew Hunt",
              "publicationYear": 1999,
              "copiesAvailable": 3
            }
            """;

    given()
            .contentType(ContentType.JSON)
            .body(json)
            .when()
            .post("/books")
            .then()
            .statusCode(400)
            .body("message", notNullValue());
}
```

{: .fs-3 }

### Exercício 4: a porta dos exemplares negativos
{: .fw-500 }

`copiesAvailable < 0` ainda está escuro: nenhum teste da API manda
exemplar negativo. Cubra essa exceção com outro POST, conferindo o 400.
{: .fs-3 }

```java
@Test
void shouldReturn400WhenCopiesAreNegative() {
    String json = """
            {
              "isbn": "020161622X",
              "title": "The Pragmatic Programmer",
              "author": "Andrew Hunt",
              "publicationYear": 1999,
              "copiesAvailable": -1
            }
            """;

    given()
            .contentType(ContentType.JSON)
            .body(json)
            .when()
            .post("/books")
            .then()
            .statusCode(400)
            .body("message", notNullValue());
}
```

O `throw` de exemplares negativos fica verde, e o teste continua dizendo
qual resposta a API devolveu.
{: .fs-3 }

### Exercício 5: tirar o DTO da planta
{: .fw-500 }

`BookRequest` aparece no relatório porque `BookResource.addBook` chama os
acessores. É a forma do JSON, não a regra do livro. Em
`src/main/resources/application.properties`, exclua a classe e rode
`./mvnw test` outra vez.
{: .fs-3 }

```properties
quarkus.jacoco.excludes=**/BookRequest.class
```

`BookRequest` sai do relatório. `Book` e `BooksService` permanecem, com a
cobertura que os testes da API acenderam.
{: .fs-3 }

### Exercício 6: uma frase sobre linha e ramo
{: .fw-500 }

Sem escrever código novo, volte aos percentuais que você anotou no
exercício 2, depois só do título vazio, antes do título nulo. Escreva uma
frase dizendo por que a cobertura de linhas de `validate` e a de ramos não
coincidem. A pista está na seção **Linha e ramo não são a mesma coisa**: a
linha do `if` já conta como executada quando uma única porta do `||` abre.
{: .fs-3 }

## Teste seus conhecimentos 🧠

Revise o texto e os slides e responda às questões teóricas abaixo.
{: .fs-3 }

<center>
    <iframe src="https://vvs.rpmhub.dev/jacoco/slides/questions.html"
        title="Questões sobre JaCoCo"
        width="90%" height="500"
        style="border:none;">
    </iframe>
</center>
{: .fs-3 }

## Referências

* JaCoCo. Documentação. Disponível em: [https://www.jacoco.org/jacoco/trunk/doc/](https://www.jacoco.org/jacoco/trunk/doc/).
* Quarkus. Measuring the coverage of your tests. Disponível em: [https://quarkus.io/guides/tests-with-coverage/](https://quarkus.io/guides/tests-with-coverage/).
{: .fs-3 }

<center>
<a href="https://rpmhub.dev" target="blanck"><img src="../imgs/logo.png" alt="Rodrigo Prestes Machado" width="3%" height="3%" border=0 style="border:0; text-decoration:none; outline:none"></a><br/>
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/">CC BY 4.0 DEED</a>
</center>
