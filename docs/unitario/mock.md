---
layout: default
title: Mock
parent: Teste Unitário
grand_parent: Teste de desenvolvimento
nav_order: 10
---

# Mock 🧪

Imagine que você quer testar uma classe que envia e-mails, consulta um banco
de dados ou consome uma API externa. Se essas dependências fizerem parte do
teste, o resultado pode variar conforme o ambiente, a conexão ou o estado do
sistema, tornando o teste lento, imprevisível e difícil de reproduzir.
Objetos *mock* resolvem esse problema: eles simulam o comportamento das
dependências para que você teste apenas o trecho de código que realmente
importa.
{: .fs-3 }

Em Java, o [Mockito](https://site.mockito.org) é o *framework* mais utilizado
para construir objetos *mock*. Também existem alternativas como
[EasyMock](https://easymock.org) e [JMock](https://jmock.org), mas o Mockito
se destaca pela legibilidade e pela integração com o JUnit.
{: .fs-3 }

Um conceito central no Mockito é o *stub*: por meio de
`when(...).thenReturn(...)` você combina com antecedência qual resposta a
dependência simulada deve dar durante o teste, tornando o comportamento
completamente previsível.
{: .fs-3 }

## Anotações do Mockito

O Mockito oferece quatro anotações que aparecem com frequência em testes
unitários: `@Mock`, `@Spy`, `@InjectMocks` e `@Captor`. Cada uma atende a
um cenário diferente, e combiná-las bem é o que torna os testes expressivos
e fáceis de manter.
{: .fs-3 }

### `@Mock`

Pense em um dublê de cinema: ele substitui o ator real e executa exatamente
o que o diretor planejou para aquela cena. A anotação
[`@Mock`](https://frontbackend.com/java/mockito-mock-annotation) faz o mesmo
com dependências: cria uma instância simulada de uma classe ou interface e
permite que você defina, via *stub*, o que cada chamada de método deve
retornar ou lançar. Use-a sempre que sua classe depender de um recurso
externo (repositório, API, gateway de pagamento etc.) e você quiser isolar
esse recurso do teste.
{: .fs-3 }

```java
// Estende o JUnit para suportar injeção de dependências com Mockito
@ExtendWith(MockitoExtension.class)
public class AppTest {

    // Cria um objeto mock da interface DataBase
    @Mock
    DataBase base;

    @Test
    public void create() {
        // Define o comportamento esperado do método createUser (stub)
        when(base.createUser("Rodrigo")).thenReturn("Rodrigo");
        assertEquals("Rodrigo", base.createUser("Rodrigo"));
    }

    @Test
    public void delete() {
        when(base.deleteUser(5L)).thenReturn(false);
        assertEquals(false, base.deleteUser(5L));
    }

    @Test
    public void deleteProblem() {
        // Configura o mock para lançar exceção com argumento inválido
        when(base.deleteUser(-1L)).thenThrow(new IllegalArgumentException());

        // Verifica se a exceção lançada é a esperada
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            base.deleteUser(-1L);
        });
    }
}
```

Sem um *stub* configurado, o Mockito retorna valores padrão (`null` para
objetos, `0` para números, `false` para booleanos), o que pode não
representar seu caso de teste. Além disso, evite criar mocks em excesso:
testes com muitas dependências simuladas tendem a ficar frágeis e difíceis
de entender.
{: .fs-3 }

### `@Spy`

Se `@Mock` substitui completamente a dependência, a anotação
[`@Spy`](https://www.studytonight.com/java-examples/spy-in-mockito) faz algo
diferente: ela envolve um objeto real e registra todas as interações com ele,
como um instrutor de autoescola ao lado do aluno, observando cada manobra
sem interferir. Os métodos continuam executando o código real, mas você pode
verificar quantas vezes foram chamados, com quais argumentos, e ainda
sobrescrever comportamentos pontuais com *stub* quando necessário.
{: .fs-3 }

```java
@ExtendWith(MockitoExtension.class)
public class MockitoSpyTest {

    @Spy
    private final List<String> list = new ArrayList<>();

    @Test
    public void shouldAddItemsToListSuccessfully() {
        // Cada chamada ao objeto espionado é rastreada pelo Mockito
        list.add("one");
        list.add("two");

        // Verifica se o método add foi chamado duas vezes com qualquer String
        verify(list, times(2)).add(anyString());

        // Verifica se add foi chamado com os valores esperados
        verify(list).add("one");
        verify(list).add("two");

        // O objeto real foi modificado, portanto size() retorna 2
        Assert.assertEquals(2, list.size());
    }
}
```

Também é possível sobrescrever comportamentos pontuais no objeto espionado:
{: .fs-3 }

```java
@ExtendWith(MockitoExtension.class)
public class MockitoSpyStubTest {

    @Spy
    private final List<String> list = new ArrayList<>();

    @Test
    public void shouldReturnDifferentSizeWhenStubbed() {

        // Sobrescreve o comportamento real de size() com um stub
        when(list.size()).thenReturn(100);

        list.add("one");
        list.add("two");

        verify(list, times(2)).add(anyString());
        verify(list).add("one");
        verify(list).add("two");

        // size() agora retorna 100, não 2
        Assertions.assertEquals(100, list.size());
    }

}
```

Prefira `@Spy` quando o comportamento real do objeto contribui para o teste
e você só precisa monitorar ou ajustar partes específicas. Misturar muitos
*stubs* com `@Spy` pode gerar confusão entre o que é real e o que é
simulado.
{: .fs-3 }

### `@InjectMocks`

Ao escrever testes, montar manualmente um objeto que possui diversas
dependências pode ser trabalhoso. A anotação
[`@InjectMocks`](https://frontbackend.com/java/mockito-injectmocks-annotation)
automatiza esse processo: ela cria uma instância da classe testada e injeta
nela os mocks declarados no mesmo teste. Funciona como encaixar peças em um
quebra-cabeça, onde o Mockito encontra o lugar certo para cada peça simulada.
{: .fs-3 }

No exemplo abaixo, a interface `Network` é uma dependência da classe
`Communication`:
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

    // A interface Network será simulada
    @Mock
    Network network;

    // O Mockito cria Communication e injeta o mock de Network automaticamente
    @InjectMocks
    Communication communication;

    @Test
    public void injectMocksTest() {
        when(network.send("message")).thenReturn(true);
        Assertions.assertEquals(true, communication.send("message"));
    }

}
```

Lembre-se de que `@InjectMocks` depende dos mocks declarados com `@Mock` no
mesmo arquivo de teste: não declare apenas `@InjectMocks` e espere que as
dependências apareçam sozinhas. Valide sempre o comportamento da classe
testada, não apenas os retornos das dependências simuladas.
{: .fs-3 }

### `@Captor`

Às vezes o método que você quer testar não retorna o objeto de interesse:
ele simplesmente o repassa para outra dependência. Nesses casos, é como
querer verificar o conteúdo de um pacote depois de entregá-lo: você precisa
interceptar o pacote antes do envio para conferir o que está dentro. A
anotação [`@Captor`](https://frontbackend.com/java/mockito-captor-annotation),
usada em conjunto com `ArgumentCaptor`, faz exatamente isso: captura o
argumento passado para um método de uma dependência simulada para que você
possa inspecioná-lo.
{: .fs-3 }

No exemplo abaixo, `EmailService` constrói um objeto `Email` internamente e
o passa para `platform.deliver()`. O teste não tem acesso direto a esse
objeto, mas com `@Captor` é possível recuperá-lo e verificar se foi montado
corretamente:
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

    @Captor
    ArgumentCaptor<Email> emailCaptor;

    @Test
    public void whenDoesSupportHtml_expectHTMLEmailFormat() {
        String to = "info@baeldung.com";
        String subject = "Using ArgumentCaptor";
        String body = "Hey, let's use ArgumentCaptor";

        // Invoca o método que constrói e entrega o e-mail internamente
        emailService.send(to, subject, body, true);

        // Captura o argumento passado para platform.deliver()
        verify(platform).deliver(emailCaptor.capture());

        // Recupera o objeto capturado
        Email value = emailCaptor.getValue();

        // Verifica se o formato foi definido corretamente
        assertEquals(Format.HTML, value.getFormat());
    }
}
```

O `@Captor` é especialmente útil para inspecionar objetos complexos que são
construídos internamente e repassados a dependências. Combine sempre com
`verify(...)` para confirmar que a interação de fato ocorreu antes de
inspecionar o argumento capturado.
{: .fs-3 }

## Código completo e repositório

Para obter o código completo dos exemplos apresentados:
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
