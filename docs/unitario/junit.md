---
layout: default
title: Teste Unitário
parent: Teste de desenvolvimento
has_children: true
nav_order: 9
---

# Teste Unitário

<center>
    <iframe src="https://vvs.rpmhub.dev/unitario/slides/index.html#/"
    title="Teste Unitário"
    width="90%" height="500" style="border:none;">
    </iframe>
</center>

Os slides acima apresentam uma visão geral do JUnit 5. Nas seções a seguir,
você aprofunda cada tópico com exemplos, configurações e detalhes que
complementam o material apresentado.
{: .fs-3 }

## O que é teste unitário?

Imagine que você precisa verificar se uma peça de um motor funciona bem
antes de montar o carro inteiro. O teste unitário faz algo parecido com o
código: ele avalia pequenos componentes de um programa, como métodos ou
classes, de forma isolada.
{: .fs-3 }

Na prática, um teste unitário chama rotinas com diferentes parâmetros de
entrada para exercitar os comportamentos esperados daquele trecho de código.
Assim, quando algo quebra, fica mais fácil localizar o problema.
{: .fs-3 }

## Por que usar o JUnit?

O [JUnit](https://junit.org/junit5/) é uma das principais ferramentas para
testes unitários em Java. Como visto nos slides, ele oferece anotações para
identificar métodos de teste, de configuração e de limpeza, além de
assertivas para verificar resultados.
{: .fs-3 }

Usar o JUnit ajuda a:

* automatizar a verificação da integridade do código;
* detectar bugs e erros mais cedo;
* repetir a execução dos testes de forma rápida e consistente.
{: .fs-3 }

## Primeiro teste

O formato básico de um teste no JUnit 5 pode ser observado no Exemplo 1
abaixo (o mesmo exemplo apresentado nos slides):
{: .fs-3 }

```java
import static org.junit.jupiter.api.Assertions.assertEquals;
import example.util.Calculator;
import org.junit.jupiter.api.Test;

class MyFirstJUnitJupiterTests {

    private final Calculator calculator = new Calculator();

    @Test
    void addition() {
        assertEquals(2, calculator.add(1, 1));
    }
}
```

<center>
Exemplo 1: teste simples com JUnit
</center>
{: .fs-3 }

No Exemplo 1, a anotação `@Test` indica que `addition` é um método de teste.
Por sua vez, a assertiva `assertEquals` verifica se a soma de 1 + 1, feita
pelo método `add` da classe `Calculator`, retorna o valor 2.
{: .fs-3 }

Como ilustração, o Vídeo 1 mostra como implementar testes unitários para a
classe `Calculator` no VS Code, usando a extensão
[Java Test Runner](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-test).
{: .fs-3 }

<center>
    <iframe
    width="560" height="315"
    src="https://www.youtube.com/embed/N_FWR1MJ37o"
    frameborder="0"
    allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
    allowfullscreen>
    </iframe>
    <br/>
    Vídeo 1: introdução ao JUnit com o VS Code
</center>
{: .fs-3 }

A configuração do JUnit em um projeto Java com Maven não aparece no vídeo.
Se você seguir os mesmos passos, perceberá dependências do JUnit no arquivo
`pom.xml`, como no trecho abaixo:
{: .fs-3 }

```xml
 <dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-api</artifactId>
    <version>${junit.version}</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-engine</artifactId>
    <version>${junit.version}</version>
    <scope>test</scope>
</dependency>
```

## Anotações

Os slides listam as principais anotações de ciclo de vida do JUnit. Esta
seção aprofunda o tema com exemplos. Entre as anotações mais usadas estão
`@BeforeAll`, `@AfterAll`, `@BeforeEach` e `@AfterEach`
([documentação](https://junit.org/junit5/docs/current/user-guide/#writing-tests-annotations)):
{: .fs-3 }

* `@BeforeAll`: método estático executado **antes** de todos os outros métodos de teste.
* `@AfterAll`: método estático executado **depois** de todos os outros métodos de teste.
* `@BeforeEach`: método executado **antes** de cada método anotado com `@Test`, `@RepeatedTest`, `@ParameterizedTest` ou `@TestFactory`.
* `@AfterEach`: método executado **depois** de cada método anotado com `@Test`, `@RepeatedTest`, `@ParameterizedTest` ou `@TestFactory`.
{: .fs-3 }

O Exemplo 2 demonstra `@BeforeAll` e `@BeforeEach`. O método estático `init`
roda uma única vez antes de qualquer teste. Já o método `add`, anotado com
`@BeforeEach`, roda antes de cada `@Test`. Com dois testes na classe, `add`
será executado duas vezes. O exemplo também usa o *Logger* do JUnit para
registrar mensagens.
{: .fs-3 }

```java
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * AnnotationsTest.
 */
public class AnnotationsTest {

    /** Logger. **/
    private static Logger logger = Logger.getLogger("AnnotationsTest");

    private static List<String> cars;

    @BeforeAll
    public static void init() {
        logger.info("init");
        cars = new ArrayList<String>();
        cars.add("Volvo");
    }

    @BeforeEach
    public void add() {
        logger.info("add");
        cars.add("Bmw");
    }

    @Test
    @DisplayName("Length test")
    public void length() {
        logger.info("length");
        assertEquals(2, cars.size());
    }

    @Test
    @DisplayName("Remove car test")
    public void remove() {
        logger.log(Level.INFO, "remove");
        cars.remove(0);
        assertEquals(2, cars.size());
    }
}
```

<center>
Exemplo 2: uso das anotações BeforeAll e BeforeEach
</center>
{: .fs-3 }

Observe que os dois casos de teste usam `@DisplayName`. Essa anotação permite
dar um nome mais significativo aos testes, o que facilita a leitura dos
relatórios de execução.
{: .fs-3 }

Outra situação comum é definir a ordem de execução dos casos de teste. Com a
anotação `@Order`, você estabelece uma sequência pré-definida. No Exemplo 3,
por causa de `@Order`, o método `second` (ordem 1) é executado antes de
`first` (ordem 2).
{: .fs-3 }

```java
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.logging.Logger;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

/**
 * TagOrderTest.
 */
class TagOrderTest {

    private static Logger logger = Logger.getLogger("TagOrderTest");

    @Test
    @Order(2)
    void first() {
        logger.info("first");
        assertEquals(1, 1);
    }

    @Test
    @Order(1)
    void second() {
        logger.info("second");
        assertEquals(1, 1);
    }

}
```

<center>
Exemplo 3: ordem de execução dos casos de teste
</center>
{: .fs-3 }

## Assertivas

Como mostrado nos slides, o JUnit 5 oferece várias assertivas. Entre as mais
comuns estão `assertEquals`, `assertTrue`, `assertNull` e `assertTimeout`.
Vale destacar `assertThrows`, usada para verificar exceções, com uma forma
de escrita um pouco diferente das demais.
{: .fs-3 }

```java
@Test
void exception() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
        Integer.parseInt("One");
    });
}
```

<center>
Exemplo 4: assertiva assertThrows
</center>
{: .fs-3 }

No Exemplo 4, `assertThrows` verifica se o trecho
`Integer.parseInt("One")`, escrito como expressão
[lambda](https://www.w3schools.com/java/java_lambda.asp), lança a exceção
`IllegalArgumentException`.
{: .fs-3 }

## Boas práticas

Os slides de JUnit 5 também destacam boas práticas que valem a pena fixar
desde o início:
{: .fs-3 }

* **Independência:** cada teste deve ser independente e não depender do
  estado deixado por outros testes.
* **Nomeação:** use nomes descritivos nos métodos de teste, indicando o
  comportamento que está sendo verificado.
* **Organização:** organize os testes em classes separadas, correspondendo
  às classes de produção que estão sendo testadas.
{: .fs-3 }

Seguir essas práticas torna os testes mais fáceis de manter e de interpretar
quando algo falha.
{: .fs-3 }

## JUnit com Maven

Além do conteúdo dos slides, é útil entender como o JUnit se encaixa no
ciclo Maven. O plugin
[Surefire](https://maven.apache.org/surefire/maven-surefire-plugin/index.html)
executa os testes durante a construção do projeto. Para isso, as classes de
teste precisam seguir o padrão de nomes do plugin, em geral o sufixo `Test`.
No Exemplo 2, a classe `AnnotationsTest` respeita essa convenção. Os padrões
de inclusão e exclusão estão na
[documentação](https://maven.apache.org/surefire/maven-surefire-plugin/examples/inclusion-exclusion.html)
do Surefire.
{: .fs-3 }

Com o plugin configurado, os testes rodam no ciclo de testes com:
{: .fs-3 }

    mvn test

Muitas vezes é necessário agrupar testes para executá-los de forma separada
(por requisito, componente ou funcionalidade). A anotação `@Tag` rotula
testes em categorias. Veja o trecho abaixo:
{: .fs-3 }

```java
@Test
@Order(1)
@Tag("VVS")
void first() {
    logger.info("first");
    assertEquals(1, 1);
}
```

<center>
Exemplo 5: método do Exemplo 3 com a anotação @Tag("VVS")
</center>
{: .fs-3 }

Assim, se você marcar um dos métodos do Exemplo 3 com `@Tag("VVS")` e
ajustar a configuração do Surefire no `pom.xml`, poderá executar apenas o
grupo rotulado. O trecho abaixo faz com que só os testes com a tag `VVS`
sejam executados por `mvn test`:
{: .fs-3 }

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>${maven-surefire-plugin.version}</version>
    <configuration>
        <groups>VVS</groups>
    </configuration>
</plugin>
```

## Teste seus conhecimentos

Revise o texto e os slides e responda às questões teóricas abaixo.
{: .fs-3 }

<center>
    <iframe src="https://vvs.rpmhub.dev/unitario/slides/questions.html"
        title="Questões sobre JUnit"
        width="90%" height="500"
        style="border:none;">
    </iframe>
</center>
{: .fs-3 }

## Referências

SOMMERVILLE, Ian. [Engenharia de software](https://biblioteca.ifrs.edu.br/pergamum_ifrs/biblioteca_s/acesso_login.php?cod_acervo_acessibilidade=5030950&acesso=aHR0cHM6Ly9taWRkbGV3YXJlLWJ2LmFtNC5jb20uYnIvU1NPL2lmcnMvOTc4ODU0MzAyNDk3NA==&label=acesso%20restrito), 10ª ed. Editora Pearson 768 ISBN 9788543024974.
{: .fs-3 }

JUnit 5. Disponível em: [https://junit.org/junit5/](https://junit.org/junit5/).
{: .fs-3 }

<center>
    <a href="rpmhub.dev" target="blanck"><img src="../imgs/logo.png" alt="Rodrigo Prestes Machado" width="3%" height="3%" border=0 style="border:0; text-decoration:none; outline:none"></a><br/>

    <a rel="license" href="http://creativecommons.org/licenses/by/4.0/">CC BY 4.0 DEED</a>
</center>
