# Introdução ao Junit

Os testes unitários estabelecem um processo de testar pequenos componentes de um programa, como por exemplo, os métodos e/ou classes. Assim, esse tipo de teste consiste em realizar chamadas para as rotinas com diferentes parâmetros de entrada a fim de exercitar todos os comportamentos de um trecho de código.

O [Junit](https://junit.org/junit5/) talvez seja a principal ferramenta para testes unitários na linguagem Java. O formato de um teste unitário no Junit pode ser observado no [Exemplo 1](https://junit.org/junit5/docs/current/user-guide/#writing-tests) abaixo:

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
Exemplo 1 - Exemplo simples de Junit
</center

No Exemplo 1, a anotação `@Test` indica que `addition` é um método de teste. Por suz vez, a assertiva `assertEquals` verifica se o resultado da soma de 1+1 por meio do método `add` da classe `Calculator` retorna no valor 2.

Como ilustração, o Vídeo 1 mostra como podemos implementar testes unitários para a classe `Calculator` no VScode. Para isso, o vídeo utiliza uma extensão chamada [Java Test Runner](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-test).

<center>
    <iframe
    width="560" height="315"
    src="https://www.youtube.com/embed/N_FWR1MJ37o"
    frameborder="0"
    allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
    allowfullscreen>
    </iframe>
    <br/>
    Vídeo 1 - Introdução ao Junit com o Vscode
</center>

A configuração do Junit em um projeto Java com Maven é um detalhe que não foi retratado no vídeo. Porém, se voc6e seguir os mesmos passos do vídeo, perceberá a presença de dependências no Junit no arquivo `pom.xml`, como por exemplo, o trecho abaixo:

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

O Junit possui um conjunto de [anotações](https://junit.org/junit5/docs/current/user-guide/#writing-tests-annotations) que auxiliam na configuração dos testes, entre as principais estão: `@BeforeAll`, `@AfterAll`, `@BeforeEach` e `@AfterEach`.

* `@BeforeAll`: Indica que o método estático que será executado **antes** dos outros métodos.
* `@AfterAll`: Indica que o método estático que será executado **depois** dos outros métodos.
* `@BeforeEach`: Indica que o método que será executado **antes** de cada método anotado com: `@Test`, `@RepeatedTest`, `@ParameterizedTest` ou `@TestFactory`.
* `@AfterEach`: Indica que o método que será executado **depois** de cada método anotado com: `@Test`, `@RepeatedTest`, `@ParameterizedTest` ou `@TestFactory`.

O código abaixo demonstra um exemplo de como se pode utilizar a anotação `@BeforeAll`. No exemplo, o método estático `init` será executado apenas uma única vez antes da execução de qualquer teste. Por outro lado, o método `add` possui a anotação `@BeforeEach` e será executado antes de cada método anotado com `@Test`, ou seja, o exemplo abaixo fará que `add` seja executado duas vezes. Cabe ainda destacar que o exemplo utiliza o *Logger* do Junit para criar um registro das mensagens do teste.

```java
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * AnnotationsTest.
 */
public class AnnotationsTest {

    /** Logger. **/
    private static Logger logger = LoggerFactory.getLogger(AnnotationsTest.class);

    private static List<String> cars;

    @BeforeAll
    public static void init() {
        logger.info(() -> "Init test");
        cars = new ArrayList<String>();
        cars.add("Volvo");
    }

    @BeforeEach
    public void add() {
        logger.info(() -> "Add a car");
        cars.add("Bmw");
    }

    @Test
    @DisplayName("Length")
    public void length() {
        logger.info(() -> "length test");
        assertEquals(2, cars.size());
    }

    @Test
    @DisplayName("Remove")
    public void remove() {
        logger.info(() -> "remove car");
        cars.remove(0);
        assertEquals(2, cars.size());
    }

}
```

<center>
Exemplo 2 - Uso das anotações @BeforeAll e @BeforeEach.
</center>

## Assertivas

---

## Agora é a sua vez

Utilizando o vídeo como uma referência, implemente a classe do Exemplo 2 em um projeto Java com o Vscode.

---

## Junit com Maven

O Junit pode ser incorporado dentro do ciclo de construção e instalação de um sistema por meio de um plugin chamado [Surefire](https://maven.apache.org/surefire/maven-surefire-plugin/index.html). Para que testes com o Junit possam ser executados por meio do Surefire, se faz necessário respeitar o padrão de nomes estabelecido pelo plugin, como por exemplo, nomear todos as classes Java que implementam testes com o sufixo `Test`. Como ilustração, no nome da classe do Exemplo 2 chama-se `AnnotationsTest`, ou seja, respeita o padrão de nomes do Surefire. O padrão de nomes, ou seja, testes que podem ser incluídos ou excluídos no Surefire pode ser obtido na [documentação](https://maven.apache.org/surefire/maven-surefire-plugin/examples/inclusion-exclusion.html) específica) da ferramenta. Assim, uma vez incorporado em um projeto Maven, os testes poderão ser executador dentro do ciclo de testes por meio do commando:

    mvn test

