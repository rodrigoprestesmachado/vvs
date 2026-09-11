---
layout: default
title: Mock
parent: Teste Unitário
grand_parent: Teste de desenvolvimento
nav_order: 10
---

# Mock 🧪

<center>
    <iframe src="https://vvs.rpmhub.dev/unitario/slidesMock/index.html#/"
    title="Mock"
    width="90%" height="500" style="border:none;">
    </iframe>
</center>

Os slides acima apresentam uma visão geral de mocks e do Mockito. Nas seções
a seguir, você aprofunda cada tópico e, nos exercícios práticos, aplica os
conceitos sobre um `BookService` que reaproveita a classe `Book` já
apresentada na seção de [Teste Unitário](junit.html).
{: .fs-3 }

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
[`@Mock`](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mock.html) faz o mesmo
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

Se `@Mock` substitui completamente a dependência por uma versão simulada, a
anotação
[`@Spy`](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Spy.html)
funciona de forma diferente: ela usa o objeto **real**, mas envolve esse
objeto com uma camada de monitoramento. É como colocar uma câmera de
segurança em uma sala: tudo continua funcionando normalmente, mas cada
movimento fica registrado.
{: .fs-3 }

Com `@Spy` você consegue, ao mesmo tempo:
{: .fs-3 }

- executar o código real do objeto (sem simular nada);
- verificar quantas vezes um método foi chamado e com quais argumentos;
- sobrescrever o comportamento de métodos pontuais via *stub*, se necessário.
{: .fs-3 }

**Exemplo 1: monitorando chamadas sem alterar o comportamento real**
{: .fs-3 }

No exemplo abaixo, `list` é uma `ArrayList` real. O `@Spy` não muda nada no
funcionamento dela: `add` de fato adiciona os itens e `size` de fato retorna
o tamanho correto. O que muda é que o Mockito registra cada chamada,
permitindo usar `verify` para confirmar que as interações aconteceram como
esperado:
{: .fs-3 }

```java
@ExtendWith(MockitoExtension.class)
public class MockitoSpyTest {

    // list é uma ArrayList real, @Spy apenas a monitora
    @Spy
    private final List<String> list = new ArrayList<>();

    @Test
    public void shouldAddItemsToListSuccessfully() {

        // Executa o código real: os itens são de fato adicionados à lista
        list.add("one");
        list.add("two");

        // verify confirma que add() foi chamado 2 vezes com qualquer String
        verify(list, times(2)).add(anyString());

        // verify confirma que add() foi chamado com cada valor específico
        verify(list).add("one");
        verify(list).add("two");

        // assertEquals confirma o estado real da lista: size() retorna 2
        // porque os itens foram de fato adicionados (código real executado)
        Assert.assertEquals(2, list.size());
    }
}
```

**Exemplo 2: sobrescrevendo um método pontual com *stub***
{: .fs-3 }

Às vezes o comportamento real de um método específico atrapalha o teste,
por exemplo um método que acessa o banco de dados ou que retorna um valor
difícil de controlar. Com `@Spy` é possível sobrescrever apenas esse método
via `when(...).thenReturn(...)`, mantendo o comportamento real dos demais.
{: .fs-3 }

No exemplo abaixo, `add` continua funcionando de verdade (os itens são
adicionados), mas `size` é substituído por um *stub* que sempre retorna
`100`:
{: .fs-3 }

```java
@ExtendWith(MockitoExtension.class)
public class MockitoSpyStubTest {

    @Spy
    private final List<String> list = new ArrayList<>();

    @Test
    public void shouldReturnDifferentSizeWhenStubbed() {

        // Sobrescreve size() com um stub; apenas este método é simulado
        when(list.size()).thenReturn(100);

        // add() continua usando o código real; os itens são de fato inseridos
        list.add("one");
        list.add("two");

        // verify confirma as interações com add(), que executou normalmente
        verify(list, times(2)).add(anyString());
        verify(list).add("one");
        verify(list).add("two");

        // size() retorna 100 (stub), não 2 (valor real)
        // isso permite simular cenários sem depender do estado interno da lista
        Assertions.assertEquals(100, list.size());
    }
}
```

A diferença fundamental entre os dois exemplos é: no primeiro, **tudo é
real**; no segundo, **apenas `size` é simulado**, enquanto o restante
continua executando código real. Prefira `@Spy` quando o comportamento real
do objeto é importante para o teste e você só precisa monitorar ou ajustar
partes específicas. Se você se pegar substituindo muitos métodos via *stub*,
considere usar `@Mock` diretamente, pois isso é um sinal de que o objeto real
não contribui para o teste.
{: .fs-3 }

### `@InjectMocks`

Ao escrever testes, montar manualmente um objeto que possui diversas
dependências pode ser trabalhoso. A anotação
[`@InjectMocks`](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/InjectMocks.html)
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
anotação [`@Captor`](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Captor.html),
usada em conjunto com `ArgumentCaptor`, faz exatamente isso: captura o
argumento passado para um método de uma dependência simulada para que você
possa inspecioná-lo.
{: .fs-3 }

Para entender o problema que `@Captor` resolve, considere a classe abaixo.
O método `send` recebe dados simples (destinatário, assunto, corpo e um
sinalizador HTML), monta um objeto `Email` internamente e o entrega à
plataforma. O objeto `Email` nunca é retornado; ele simplesmente some para
dentro de `platform.deliver()`:
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
        // Email é construído internamente; o teste não tem acesso a ele
        Email email = new Email(to, subject, body);
        email.setFormat(format);
        platform.deliver(email);
    }

}
```

O que queremos testar é se o `Email` foi montado corretamente antes de ser
entregue, em especial, se o formato foi definido como `HTML` quando o
parâmetro `html` for `true`. Sem `@Captor`, não há como acessar esse objeto
no teste. Com `@Captor`, o Mockito intercepta a chamada a `deliver()` e
guarda o argumento para que possamos inspecioná-lo:
{: .fs-3 }

```java
@ExtendWith(MockitoExtension.class)
public class EmailServiceUnitTest {

    // Simula a plataforma de entrega; não queremos enviar e-mails de verdade
    @Mock
    DeliveryPlatform platform;

    // Cria EmailService e injeta o mock de platform automaticamente
    @InjectMocks
    EmailService emailService;

    // Declara um captor tipado: vai interceptar argumentos do tipo Email
    @Captor
    ArgumentCaptor<Email> emailCaptor;

    @Test
    public void whenDoesSupportHtml_expectHTMLEmailFormat() {

        // Passo 1: executa o método que queremos testar
        emailService.send("info@baeldung.com", "Assunto", "Corpo", true);

        // Passo 2: usa verify para confirmar que deliver() foi chamado e,
        // ao mesmo tempo, captura o Email que foi passado como argumento
        verify(platform).deliver(emailCaptor.capture());

        // Passo 3: recupera o objeto Email capturado
        Email emailEnviado = emailCaptor.getValue();

        // Passo 4: inspeciona o objeto; o formato deve ser HTML
        assertEquals(Format.HTML, emailEnviado.getFormat());
    }
}
```

O fluxo segue três responsabilidades bem separadas: `@InjectMocks` monta o
objeto testado, `verify` + `emailCaptor.capture()` confirmam que a interação
ocorreu e guardam o argumento, e `assertEquals` valida o conteúdo do objeto
capturado. Remover qualquer uma dessas etapas enfraquece o teste: sem
`verify`, o captor nunca é acionado; sem `assertEquals`, você confirma que
`deliver` foi chamado mas não verifica se o e-mail estava correto.
{: .fs-3 }

### Quando usar cada anotação

Antes de seguir para os exercícios práticos, vale fixar rapidamente o
papel de cada anotação:
{: .fs-3 }

| Anotação        | Papel                                            | Use quando...                                                        |
|-----------------|---------------------------------------------------|-----------------------------------------------------------------------|
| `@Mock`         | Cria uma dependência totalmente simulada          | A classe testada depende de um recurso externo que você quer isolar   |
| `@Spy`          | Envolve um objeto **real** com monitoramento      | O comportamento real importa, mas você quer verificar interações      |
| `@InjectMocks`  | Monta a classe testada e injeta os mocks nela     | Você já declarou `@Mock`/`@Spy` e quer evitar montar o objeto na mão   |
| `@Captor`       | Captura o argumento passado a um método do mock   | O método simulado não retorna o dado que você quer inspecionar        |
{: .fs-3 }

## `verify` vs. `assert`

Nos testes com Mockito aparecem dois tipos de verificação que têm propósitos
distintos e complementares: `assert` e `verify`. Confundi-los é um erro comum
que leva a testes que passam sem realmente validar o que deveriam.
{: .fs-3 }

O **`assert`** (JUnit) verifica o **resultado**: ele compara o valor retornado
por um método com o valor esperado. A pergunta que responde é *"o método
devolveu o que eu esperava?"*
{: .fs-3 }

O **`verify`** (Mockito) verifica o **comportamento**: ele confirma que um
determinado método de um *mock* foi chamado, quantas vezes e com quais
argumentos. A pergunta que responde é *"a interação com a dependência ocorreu
como planejado?"*
{: .fs-3 }

```java
@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    PaymentGateway gateway;

    @InjectMocks
    OrderService orderService;

    @Test
    public void shouldChargeAndReturnConfirmation() {
        when(gateway.charge(150.0)).thenReturn("TX-001");

        String confirmation = orderService.placeOrder(150.0);

        // assert: verifica o RESULTADO retornado pelo método testado
        assertEquals("TX-001", confirmation);

        // verify: verifica o COMPORTAMENTO, se o gateway foi chamado
        // com o valor correto, exatamente uma vez
        verify(gateway, times(1)).charge(150.0);
    }
}
```

Use `assert` quando o método testado retorna um valor que você pode comparar
diretamente. Use `verify` quando o método não retorna o dado de interesse, mas
você precisa garantir que a dependência foi acionada corretamente, por
exemplo, que um e-mail foi enviado, que um log foi registrado ou que um
repositório foi chamado para persistir um objeto.
{: .fs-3 }

Evite substituir um pelo outro: um teste que só usa `verify` não checa o
resultado produzido; um teste que só usa `assert` pode passar mesmo que a
dependência nunca tenha sido chamada.
{: .fs-3 }

## Exercícios práticos: testando `BookService`

Agora é a sua vez de escrever testes com Mockito. O cenário reaproveita a
classe `Book` já apresentada na seção de
[Teste Unitário](junit.html#exercícios-práticos-testando-book), mas agora
ela é manipulada por um `BookService` que depende de duas colaborações
externas: um repositório e um serviço de notificação. É justamente esse
tipo de dependência externa que faz do Mockito uma ferramenta necessária.
{: .fs-3 }

### Preparação do projeto

Crie um projeto Quarkus simples do zero, não é necessário clonar nenhum
repositório:
{: .fs-3 }

```bash
mvn io.quarkus.platform:quarkus-maven-plugin:3.15.1:create \
    -DprojectGroupId=dev.ifrs.mockito \
    -DprojectArtifactId=book-mockito \
    -DclassName="dev.ifrs.mockito.GreetingResource" \
    -Dextensions="resteasy-reactive"
cd book-mockito
code .
```

O arquétipo do Quarkus já traz o JUnit 5, mas não o Mockito. Adicione a
dependência abaixo ao `pom.xml` gerado:
{: .fs-3 }

```xml
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-junit-jupiter</artifactId>
    <version>5.14.1</version>
    <scope>test</scope>
</dependency>
```

### Classes que serão testadas

Copie as classes abaixo para o seu projeto. `Book` é uma versão
simplificada da mesma classe usada na seção de Teste Unitário (sem a
validação completa de ISBN-10, para manter o foco no uso de mocks).
{: .fs-3 }

Crie `src/main/java/dev/ifrs/mockito/exception/InvalidBookException.java`:
{: .fs-3 }

```java
package dev.ifrs.mockito.exception;

public class InvalidBookException extends RuntimeException {

    public InvalidBookException(final String message) {
        super(message);
    }
}
```

Crie `src/main/java/dev/ifrs/mockito/exception/BookNotFoundException.java`:
{: .fs-3 }

```java
package dev.ifrs.mockito.exception;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(final String message) {
        super(message);
    }
}
```

Crie `src/main/java/dev/ifrs/mockito/exception/NoCopiesAvailableException.java`:
{: .fs-3 }

```java
package dev.ifrs.mockito.exception;

public class NoCopiesAvailableException extends RuntimeException {

    public NoCopiesAvailableException(final String message) {
        super(message);
    }
}
```

Crie `src/main/java/dev/ifrs/mockito/model/Book.java`:
{: .fs-3 }

```java
package dev.ifrs.mockito.model;

import dev.ifrs.mockito.exception.InvalidBookException;

import java.time.Year;
import java.util.Objects;

public final class Book {

    private static final int MAX_TITLE_LENGTH = 200;

    private final String isbn;
    private final String title;
    private final String author;
    private final int publicationYear;
    private final int copiesAvailable;

    private Book(
            final String isbn,
            final String title,
            final String author,
            final int publicationYear,
            final int copiesAvailable) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.copiesAvailable = copiesAvailable;
    }

    public static Book of(
            final String isbn,
            final String title,
            final String author,
            final int publicationYear,
            final int copiesAvailable) {
        validate(isbn, title, author, publicationYear, copiesAvailable);
        return new Book(isbn, title, author, publicationYear, copiesAvailable);
    }

    private static void validate(
            final String isbn,
            final String title,
            final String author,
            final int publicationYear,
            final int copiesAvailable) {
        if (isbn == null || isbn.isBlank()) {
            throw new InvalidBookException("ISBN cannot be blank");
        }
        if (title == null || title.isBlank()) {
            throw new InvalidBookException("Title cannot be blank");
        }
        if (title.length() > MAX_TITLE_LENGTH) {
            throw new InvalidBookException(
                    "Title cannot be longer than " + MAX_TITLE_LENGTH
                            + " characters");
        }
        if (author == null || author.isBlank()) {
            throw new InvalidBookException("Author cannot be blank");
        }
        int currentYear = Year.now().getValue();
        if (publicationYear < 1 || publicationYear > currentYear) {
            throw new InvalidBookException(
                    "Publication year must be between 1 and " + currentYear);
        }
        if (copiesAvailable < 0) {
            throw new InvalidBookException(
                    "Copies available cannot be negative");
        }
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public int getCopiesAvailable() {
        return copiesAvailable;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Book)) {
            return false;
        }
        Book book = (Book) o;
        return isbn.equals(book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
}
```

Crie `src/main/java/dev/ifrs/mockito/repository/BookRepository.java`:
{: .fs-3 }

```java
package dev.ifrs.mockito.repository;

import dev.ifrs.mockito.model.Book;

import java.util.Optional;

public interface BookRepository {

    Optional<Book> findByIsbn(String isbn);

    void save(Book book);
}
```

Crie
`src/main/java/dev/ifrs/mockito/notification/NotificationService.java`:
{: .fs-3 }

```java
package dev.ifrs.mockito.notification;

public interface NotificationService {

    void notify(String recipient, String message);
}
```

Crie `src/main/java/dev/ifrs/mockito/service/BookService.java`:
{: .fs-3 }

```java
package dev.ifrs.mockito.service;

import dev.ifrs.mockito.exception.BookNotFoundException;
import dev.ifrs.mockito.exception.NoCopiesAvailableException;
import dev.ifrs.mockito.model.Book;
import dev.ifrs.mockito.notification.NotificationService;
import dev.ifrs.mockito.repository.BookRepository;

public class BookService {

    private final BookRepository repository;
    private final NotificationService notifier;

    public BookService(
            final BookRepository repository,
            final NotificationService notifier) {
        this.repository = repository;
        this.notifier = notifier;
    }

    public void registerBook(final Book book) {
        repository.save(book);
        notifier.notify(
                "librarian@library.dev",
                "Book registered: " + book.getTitle());
    }

    public Book borrowBook(final String isbn, final String patronEmail) {
        Book book = repository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(
                        "Book not found for ISBN: " + isbn));

        if (book.getCopiesAvailable() == 0) {
            throw new NoCopiesAvailableException(
                    "No copies available for ISBN: " + isbn);
        }

        Book updated = Book.of(
                book.getIsbn(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublicationYear(),
                book.getCopiesAvailable() - 1);

        repository.save(updated);
        notifier.notify(patronEmail, "You borrowed: " + updated.getTitle());

        return updated;
    }
}
```

### Preparação dos testes

1. Crie a classe `BookServiceTest` em
   `src/test/java/dev/ifrs/mockito/service/BookServiceTest.java`.
2. Anote a classe com `@ExtendWith(MockitoExtension.class)`, como nos
   exemplos apresentados anteriormente.
3. Resolva os exercícios **na ordem**: eles vão do mais simples ao mais
   desafiador e reaproveitam o que você aprendeu no exercício anterior.
4. Depois de cada exercício, execute `mvn test` (ou use o VS Code) e só
   avance quando o teste passar.
{: .fs-3 }

### Exercício 1: `@Mock` e `@InjectMocks` básicos
{: .fw-500 }

Declare `@Mock BookRepository repository`, `@Mock NotificationService
notifier` e `@InjectMocks BookService bookService`. Escreva
`shouldSaveBookWhenRegistering`: crie um `Book` válido, chame
`bookService.registerBook(book)` e use `verify` para confirmar que
`repository.save(book)` foi chamado exatamente uma vez.
{: .fs-3 }

### Exercício 2: livro não encontrado
{: .fw-500 }

Escreva `shouldThrowWhenBookNotFound`: configure
`when(repository.findByIsbn(anyString())).thenReturn(Optional.empty())` e
use `assertThrows` para verificar que `bookService.borrowBook(...)` lança
`BookNotFoundException`.
{: .fs-3 }

### Exercício 3: sem cópias disponíveis
{: .fw-500 }

Escreva `shouldThrowWhenNoCopiesAvailable`: configure o *stub* de
`findByIsbn` para retornar um `Book` com `copiesAvailable` igual a `0` e
use `assertThrows` para verificar que `NoCopiesAvailableException` é
lançada.
{: .fs-3 }

### Exercício 4: `verify` da notificação
{: .fw-500 }

Escreva `shouldNotifyPatronWhenBookIsBorrowed`: configure o *stub* de
`findByIsbn` para retornar um `Book` válido com ao menos uma cópia
disponível, chame `borrowBook`, e use `verify` para confirmar que
`notifier.notify(...)` foi chamado com o e-mail do leitor.
{: .fs-3 }

### Exercício 5: `@Captor` no livro salvo
{: .fw-500 }

Declare `@Captor ArgumentCaptor<Book> bookCaptor`. Escreva
`shouldDecrementCopiesWhenBookIsBorrowed`: repita o cenário do exercício
anterior, use `verify(repository).save(bookCaptor.capture())` e, com
`bookCaptor.getValue()`, use `assertEquals` para confirmar que
`copiesAvailable` foi decrementado em exatamente uma unidade.
{: .fs-3 }

### Exercício 6: `@Spy` em um repositório real
{: .fw-500 }

Crie uma implementação em memória de `BookRepository` (por exemplo,
baseada em `HashMap<String, Book>`), sem usar Mockito. Em um novo teste,
declare `@Spy` sobre essa implementação real (em vez de `@Mock`),
adicione um `Book` a ela previamente, chame `bookService.borrowBook(...)`
e use `verify(repository).save(any(Book.class))` para confirmar a
interação, enquanto `findByIsbn` e `save` continuam executando o código
real da sua implementação em memória.
{: .fs-3 }

### Exercício 7: `assert` e `verify` juntos
{: .fw-500 }

Escreva `shouldReturnUpdatedBookAndNotifyPatron`, combinando as duas
formas de verificação estudadas nesta página: use `assertEquals` para
conferir que o `Book` retornado por `borrowBook` tem uma cópia a menos, e
use `verify` para confirmar que `notifier.notify` foi chamado. Um teste
que só usasse `assert` não garantiria que o leitor foi avisado; um teste
que só usasse `verify` não garantiria que o retorno estava correto.
{: .fs-3 }

## Código completo e repositório

Para obter o código completo dos exemplos apresentados:
{: .fs-3 }

    git clone -b dev https://github.com/rodrigoprestesmachado/vvs
    code vvs/exemplos/mockito/

## Teste seus conhecimentos 🧠

<center>
    <iframe src="https://vvs.rpmhub.dev/unitario/slidesMock/questions.html"
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
