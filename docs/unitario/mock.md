---
layout: default
title: Mock
parent: Teste Unitário
grand_parent: Teste de desenvolvimento
nav_order: 10
---

# Mock 🧪

A ideia por trás dos objetos mock está na possibilidade de simular o
comportamento de uma ou mais dependências (acoplamentos) que por ventura possa
existir em um método. Uma vez que conseguimos simular e, consequentemente,
controlar o comportamento das dependências, podemos então testar de forma
segura um trecho de código do nosso interesse.
{: .fs-3 }

A grande maioria de linguagens de programação possui *frameworks* para construir
objetos mock. Em Java, por exemplo, existe uma série de ferramentas capazes de
realizar essa tarefa, entre elas: [Mockito](https://site.mockito.org),
[EasyMock](https://easymock.org), [JMock](https://jmock.org).
{: .fs-3 }

Possivelmente, o [Mockito](https://site.mockito.org) seja o *framework* em Java
mais utilizado na construção de objetos *mock*. Nesse sentido, observe trecho de
código do exemplo abaixo que ilustra a utilização de objetos _mock_ em um teste
unitário: 😃
{: .fs-3 }

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

Como pode ser visto no item (3) do exemplo acima, utilizamos o comando `when`
para criar um *stub*. Um stub faz com que uma chamada de método sempre retorne
o mesmo valor, ou seja, com essa técnica podemos prever o comportamento das
dependências e testar de forma segura um trecho de código.
{: .fs-3 }

## Principais anotações do Mockito

O Mockito possui algumas anotações úteis que nos auxiliam no momento de
construir objetos mock, não elas: `@Mock`, `@Spy`, `@InjectMocks` e `@Captor`.
{: .fs-3 }

A anotação mais usada no Mockito é a [`@Mock`](https://frontbackend.com/java/mockito-mock-annotation). Por meio desta anotação podemos criar e injetar instâncias de classes/interfaces
simuladas e, por meio da operação de *stub*, podemos definir os valores de
retorno para as chamadas dos métodos. O exemplo acima demostra a utilização da
anotação `@Mock`.
{: .fs-3 }

Já a anotação [`@Spy`](https://www.studytonight.com/java-examples/spy-in-mockito)
é usada para adicionar um mecanismo de rastreamento em um objeto real, por essa
razão, trata-se de um mock "parcial", vejamos um exemplo:
{: .fs-3 }

```java
@ExtendWith(MockitoExtension.class)
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

Podemos configurar os objetos que estamos espionando de forma que os métodos
selecionados retornem um valor específico (*stub*), veja o exemplo abaixo:
{: .fs-3 }

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

A anotação [`@InjectMocks`](https://frontbackend.com/java/mockito-injectmocks-annotation)
permite injetar objetos Mock em um objeto real. Vejamos um exemplo, imagine uma
interface chama `Network` e uma classe `Communication` que utiliza essa
interface:
{: .fs-3 }

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

Outra anotação interessante é a [`@Captor`](https://frontbackend.com/java/mockito-captor-annotation), utilizada em conjunto com a classe `ArgumentCaptor`, permite  capturar os
argumentos passados para um método que queremos inspecionar. A captura de
parâmetros pode ser útil na construção de alguns tipos de testes, por
[exemplo](https://www.baeldung.com/mockito-argumentcaptor):
{: .fs-3 }

```java
public class EmailService {

    private DeliveryPlatform platform;

    public EmailService(DeliveryPlatform platform) {
        this.platform = platform;
    }

    public void send(String to, String subject, String body, boolean html) {
        Format format = Format.TEXT_ONLY;
        if (html) {
            format = Format.HTML;
        }
        Email email = new Email(to, subject, body);
        email.setFormat(format);
        platform.deliver(email);
    }

}
```

```java
@ExtendWith(MockitoExtension.class)
public class EmailServiceUnitTest {

    @Mock
    DeliveryPlatform platform;

    @InjectMocks
    EmailService emailService;

    // 1 - utilizando a anotação @Captor em conjunto da classe ArgumentCaptor
    @Captor
    ArgumentCaptor<Email> emailCaptor;

    @Test
    public void whenDoesSupportHtml_expectHTMLEmailFormat() {
        String to = "info@baeldung.com";
        String subject = "Using ArgumentCaptor";
        String body = "Hey, let'use ArgumentCaptor";

        // 2 - invocando o método send da classe EmailServices
        // note que foi criado um objeto mock chamado platform
        emailService.send(to, subject, body, true);

        // 3 - capturando o argumento do método deliver do objeto platform
        verify(platform).deliver(emailCaptor.capture());

        // 4 - recuperando o último valor capturado por meio do método getValue
        Email value = emailCaptor.getValue();

        // 5 - verificando se o e-mail foi enviado no formato HTML
        assertEquals(Format.HTML, value.getFormat());
    }
}
```

## Exemplos

Para se obter o código completo dos exemplos dos Mocks acima, por favor acesse:
{: .fs-3 }

    git clone -b dev https://github.com/rodrigoprestesmachado/vvs
    code vvs/exemplos/mockito/

## Teste seus conhecimentos 🧠

<center>
    <iframe src="https://vvs.rpmhub.dev/unitario/questionsMock.html"
        title="Questões sobre Mockito"
        width="90%" height="500"
        style="border:none;">
    </iframe>
</center>
{: .fs-3 }

## Referências

* Mockito framework site. Disponível em: [https://site.mockito.org](https://site.mockito.org).
{: .fs-3 }

<center>
    <a href="rpmhub.dev" target="blanck"><img src="../imgs/logo.png" alt="Rodrigo Prestes Machado" width="3%" height="3%" border=0 style="border:0; text-decoration:none; outline:none"></a><br/>

    <a rel="license" href="http://creativecommons.org/licenses/by/4.0/">CC BY 4.0 DEED</a>
</center>
