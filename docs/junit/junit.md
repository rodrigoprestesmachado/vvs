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
</center>

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

A configuração do Junit em um projeto Java com Maven é um detalhe que não foi retratado no vídeo. Porém, se você seguir os mesmos passos do vídeo, perceberá a presença de dependências no Junit no arquivo `pom.xml`, como por exemplo, o trecho abaixo:

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
Exemplo 2 - Uso das anotações BeforeAll e BeforeEach.
</center>

Note que no Exemplo 2 que os dois casos de teste estão anotados com `@DisplayName`, ou seja, essa anotação permite que coloquemos um nome mais significativo para os testes.

Outra situação comum é necessitarmos estabelecer uma ordem para a execução dos casos de teste, nesse caso, podemos estabelecer uma sequencia pré-definida por meio da anotação `@Order`. O Exemplo 3 mostra uma situação onde, devido a presença da anotação `@Order`, o segundo método de teste (`second`) será executado antes do primeiro.

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
Exemplo 3 - Ordem da execução dos casos de teste
</center>

## Assertivas

O Junit 5 possui um conjunto grande de assertivas (afirmações categóricas), entre as mais comuns estão `assertEquals`, `assertTrue`, `assertTimeout`, entre outras. Porém, se faz necessário destacar a assertiva `assertThrows` que, para verificar exceções, possui uma forma de escrita um pouco diferente das demais.

```java
@Test
void exception() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
        Integer.parseInt("One");
    });
}
```

<center>
Exemplo 4 - Assertiva assertThrows
</center>

No Exemplo 4, a assertiva `assertThrows` verifica se o trecho de código `Integer.parseInt("One")` (escrito como uma expressão [lambda](https://www.w3schools.com/java/java_lambda.asp)) irá resultar a exceção `IllegalArgumentException`.

## Junit com Maven

O Junit pode ser incorporado dentro do ciclo de construção e instalação de um sistema por meio de um plugin chamado [Surefire](https://maven.apache.org/surefire/maven-surefire-plugin/index.html). Para que testes com o Junit possam ser executados por meio do Surefire, se faz necessário respeitar o padrão de nomes estabelecido pelo plugin, como por exemplo, nomear todos as classes Java que implementam testes com o sufixo `Test`. Como ilustração, no nome da classe do Exemplo 2 chama-se `AnnotationsTest`, ou seja, respeita o padrão de nomes do Surefire. O padrão de nomes, ou seja, testes que podem ser incluídos ou excluídos no Surefire pode ser obtido na [documentação](https://maven.apache.org/surefire/maven-surefire-plugin/examples/inclusion-exclusion.html) específica) da ferramenta. Assim, uma vez incorporado em um projeto Maven, os testes poderão ser executador dentro do ciclo de testes por meio do commando:

    mvn test

Muitas vezes se faz necessário agrupar testes para que possam ser executados de maneira separada (por requisito, componentes, funcionalidades, entre outros). Nesse sentido, a anotação `@Tag`auxilia a rotular testes dentro de categorias. Veja o exemplo do trecho abaixo:

```Java
@Test
@Order(1)
@Tag("VVS")
void first() {
    logger.info("first");
    assertEquals(1, 1);
}
```

<center>
Exemplo 4 - Modificação do primeiro método do Exemplo 3 com a anotação `@Tag("VVS")`
</center>

Assim, se alterarmos un dos métodos do Exemplo 3 e a configuração do plugin Surefire no Maven (dentro do `pom.xml`), podemos executar apenas um grupo de testes previamente rotulado. O trecho de código abaixo mostra um exemplo onde apenas os testes marcado com a `@Tag(VVS)` irão ser executados por meio do comando `mvn test`.

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

---

# Mock

A ideia por trás dos objetos mock está na possibilidade de simular o comportamento de uma ou mais dependências (acoplamentos) que por ventura possa existir em um método. Uma vez que conseguimos simular e, consequentemente, controlar o comportamento das dependências, podemos então testar de forma segura um trecho de código do nosso interesse.

A grande maioria de linguagens de programação possui _frameworks_ para construir objetos mock. Em Java, por exemplo, existe uma série de ferramentas capazes de realizar essa tarefa, entre elas: [Mockito](https://site.mockito.org), [EasyMock](https://easymock.org), [JMock](https://jmock.org).

Possivelmente, o [Mockito](https://site.mockito.org) seja o _framework_ em Java mais utilizado na construção de objetos _mock_. Nesse sentido, observe trecho de código do exemplo abaixo que ilustra a utilização de objetos _mock_ em um teste unitário: 😃

```java
// 1 - Estende o Junit para suportar, por exemplo, injeção de dependência de objetos Mock
@ExtendWith(MockitoExtension.class)
public class AppTest {

    // 2 - Cria um objeto mock da interface (ou classe) DataBase
    @Mock
    DataBase base;

    @Test
    public void create() {
        // 3 - define o comportamento do método createUser (stub)
        when(base.createUser("Rodrigo")).thenReturn("Rodrigo");

        // TODO ... código do método de teste

        assertEquals("Rodrigo", base.createUser("Rodrigo"));
    }

    @Test
    public void delete() {
        when(base.deleteUser(5L)).thenReturn(false);

        // TODO ... código do método de teste

        assertEquals(false, base.deleteUser(5L));
    }

    @Test
    public void deleteProblem() {
        // 4 - define que o método deleteUser irá lançar uma exceção se receber um valor negativo
        when(base.deleteUser(-1L)).thenThrow(new IllegalArgumentException());

        // TODO ... código do método de teste

        // 5 - verifica se a exceção lançada é igual a esperada
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            base.deleteUser(-1L);
        });
    }
}

```

Como pode ser visto no item (3) do exemplo acima, utilizamos o comando `when` para criar um _stub_. Um stub faz com que uma chamada de método sempre retorne o mesmo valor, ou seja, com essa técnica podemos prever o comportamento das dependências e testar de forma segura um trecho de código.
## Principais anotações do Mockito

O Mockito possui algumas anotações úteis que nos auxiliam no momento de construir objetos mock, não elas: `@Mock`, `@Spy`, `@InjectMocks` e `@Captor`.

A anotação mais usada no Mockito é [`@Mock`](https://frontbackend.com/java/mockito-mock-annotation), pois, por meio dela podemos criar e injetar instâncias simuladas. Trata-se de uma implementação fictícia para uma interface ou uma classe na qual você pode definir os valores de retorno para as chamadas dos métodos. O exemplo acima demostra a utilização da anotação `@Mock`.

Já a anotação [`@Spy`](https://frontbackend.com/java/mockito-spy-annotation) é usada para adicionar um mecanismo de rastreamento das chamadas de método, vejamos um exemplo:

```java
@RunWith(MockitoExtension.class)
public class MockitoSpyTest {

    @Spy
    private final List<String> list = new ArrayList<>();

    @Test
    public void shouldAddItemsToListSuccessfully() {
        // 1 - estamos fazendo algumas operações no objeto que estamos espionando
        // onde cada chamada é rastreada pelo Mockito.
        list.add("one");
        list.add("two");

        // 2- o método verify analisa se algumas das condições especificadas
        // foram atendidas
        verify(list, times(2)).add(anyString());

        // 3 - verificando se o método add foi chamado com o valor esperado
        verify(list).add("one");
        verify(list).add("two");

        // 4 - a assertiva prova que o método add foi chamado na instância real
        Assert.assertEquals(2, list.size());
    }
}
```

Podemos configurar os objetos que estamos espionando de forma que os métodos selecionados retornem um valor específico (_stub_), veja o exemplo abaixo:

```java
@ExtendWith(MockitoExtension.class)
public class MockitoSpyStubTest {

    @Spy
    private final List<String> list = new ArrayList<>();

    @Test
    public void shouldReturnDifferentSizeWhenStubbed() {

        // 1 - Estamos sobrescrevendo o comportamento original do método size()
        // (stub)
        when(list.size()).thenReturn(100);

        list.add("one");
        list.add("two");

        verify(list, times(2)).add(anyString());

        verify(list).add("one");
        verify(list).add("two");

        // 2- Nesse caso, não podemos mais esperar que o método size retorne 2
        Assertions.assertEquals(100, list.size());
    }

}
```

A anotação [`@InjectMocks`](https://frontbackend.com/java/mockito-injectmocks-annotation) é usada para injetar objetos Mock em um objeto real. Vejamos um exemplo, imagine uma interface chama `Network` e uma classe `Communication` que utiliza essa interface:

```java
public interface Network {

    public boolean send(String message);

}
```

```java
public class Communication {

    private Network network;

    public boolean send(String message) {
        boolean result = false;
        try {
            result = network.send(message);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return result;
    }

}
```

```java
@ExtendWith(MockitoExtension.class)
public class MockitoInjectMocksTest {

    // 1 - a interface Network, que é uma dependência da classe Communication, será simulada
    @Mock
    Network network;

    // 2 - a anotação @InjectMocks permite criar um mock da classe Communication e resolver
    // a dependência Network
    @InjectMocks
    Communication communication;

    @Test
    public void injectMocksTest() {
        when(communication.send("message")).thenReturn(true);
        Assertions.assertEquals(true, communication.send("message"));
    }

}
```

Outra anotação interessante é a [`@Captor`](https://frontbackend.com/java/mockito-captor-annotation), utilizada em conjunto com a classe `ArgumentCaptor`, permite  capturar os argumentos passados para um método que queremos inspecionar. A captura de parâmetros pode ser útil para testar métodos chamados em outros métodos, observe um exemplo:

```java
    @Mock
    private List<String> list;

    @Captor
    private ArgumentCaptor<String> valueCaptor;

    @Test
    public void shouldCaptureListParameters() {

        // 1 - Primeiro, adicionamos dois valores String à nossa lista: "um", "dois".
        // Depois, usando o método Verify(...) com o ArgumentCaptor para capturar essas strings.
        list.add("one");
        list.add("two");
        verify(list, times(2)).add(valueCaptor.capture());

        // 2 - ArgumentCaptor possui dois métodos getValue() e getAllValues():
        //    O getValue() pode ser usado quando capturamos um argumento de uma única chamada de método e
        // retornará o último valor capturado.
        //    O getAllValues() retorna a lista de argumentos que foram passados para o método
        List<String> allValues = valueCaptor.getAllValues();

        Assertions.assertTrue(allValues.contains("one"));
        Assertions.assertTrue(allValues.contains("two"));
        // 3 - retorna o último valor capturado
        Assertions.assertEquals("two", valueCaptor.getValue());
    }
```

## Exemplos

Para se obter o código completo dos exemplos dos Mocks acima, por favor acesse:

    git clone -b dev https://github.com/rodrigoprestesmachado/vvs
    code vvs/exemplos/mockito/

## Referências

SOMMERVILLE, Ian. [Engenharia de software](https://biblioteca.ifrs.edu.br/pergamum_ifrs/biblioteca_s/acesso_login.php?cod_acervo_acessibilidade=5030950&acesso=aHR0cHM6Ly9taWRkbGV3YXJlLWJ2LmFtNC5jb20uYnIvU1NPL2lmcnMvOTc4ODU0MzAyNDk3NA==&label=acesso%20restrito), 10ª ed. Editora Pearson 768 ISBN 9788543024974.

<center>
    <a href="rpmhub.dev" target="blanck"><img src="../imgs/logo.png" alt="Rodrigo Prestes Machado" width="3%" height="3%" border=0 style="border:0; text-decoration:none; outline:none"></a><br/>

    <a rel="license" href="http://creativecommons.org/licenses/by/4.0/">Creative Commons Atribuição 4.0 Internacional</a>
</center>