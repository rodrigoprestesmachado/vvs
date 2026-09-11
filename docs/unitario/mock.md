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
importa. Esse é exatamente o argumento do slide **Por que usar mocks?**.
{: .fs-3 }

Em Java, o [Mockito](https://site.mockito.org) é o *framework* mais utilizado
para construir objetos *mock*. Também existem alternativas como
[EasyMock](https://easymock.org) e [JMock](https://jmock.org), mas o Mockito
se destaca pela legibilidade e pela integração com o JUnit (veja o slide
**O que é Mockito?**).
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

Se você já viu o exemplo hexagonal da disciplina, `@Mock` é o que você usa
para simular as **portas de saída** (`domain.ports.out`, como
`BookRepository` ou `EmailNotification`): o teste não fala com um banco ou
serviço de e-mail de verdade, só com um duplo controlado pelo Mockito. Os
slides **`@Mock`** e **Exemplo de `@Mock` com BookService** resumem essa
ideia.
{: .fs-3 }

```java
// Estende o JUnit para suportar injeção de dependências com Mockito
@ExtendWith(MockitoExtension.class)
public class BookRepositoryMockTest {

    // Cria um objeto mock da porta de saída BookRepository
    // (a mesma interface usada nos exercícios práticos, mais adiante)
    @Mock
    BookRepository repository;

    @Test
    public void shouldReturnBookWhenFound() {
        Book book = Book.of(
                "0-306-40615-2", "Clean Code", "Robert Martin", 2008, 5);

        // Define o comportamento esperado do método findByIsbn (stub)
        when(repository.findByIsbn("0-306-40615-2"))
                .thenReturn(Optional.of(book));

        assertEquals(book, repository.findByIsbn("0-306-40615-2").get());
    }

    @Test
    public void shouldReturnEmptyWhenBookNotFound() {
        when(repository.findByIsbn("0-000-00000-0"))
                .thenReturn(Optional.empty());

        assertTrue(repository.findByIsbn("0-000-00000-0").isEmpty());
    }

    @Test
    public void shouldThrowWhenRepositoryFails() {
        // Configura o mock para lançar exceção em vez de retornar um valor
        when(repository.findByIsbn("invalid"))
                .thenThrow(new IllegalArgumentException());

        // Verifica se a exceção lançada é a esperada
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            repository.findByIsbn("invalid");
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

`@Spy` é útil quando você tem uma implementação real e simples de uma
**porta de saída** (por exemplo, um `BookRepository` em memória, sem
banco de verdade) e quer testar o serviço de aplicação usando esse
comportamento real, mas ainda assim confirmar as interações com `verify`.
Veja o slide **`@Spy`**.
{: .fs-3 }

**Exemplo 1: monitorando chamadas sem alterar o comportamento real**
{: .fs-3 }

No exemplo abaixo, `InMemoryBookRepository` é uma implementação **real** de
`BookRepository`, baseada em um `HashMap` (sem banco de dados nenhum). O
`@Spy` não muda nada no funcionamento dela: `save` de fato guarda o livro e
`findByIsbn` de fato o recupera. O que muda é que o Mockito registra cada
chamada, permitindo usar `verify` para confirmar que as interações
aconteceram como esperado:
{: .fs-3 }

```java
public class InMemoryBookRepository implements BookRepository {

    private final Map<String, Book> books = new HashMap<>();

    @Override
    public Optional<Book> findByIsbn(final String isbn) {
        return Optional.ofNullable(books.get(isbn));
    }

    @Override
    public void save(final Book book) {
        books.put(book.getIsbn(), book);
    }
}
```

```java
@ExtendWith(MockitoExtension.class)
public class InMemoryBookRepositorySpyTest {

    // InMemoryBookRepository é real, @Spy apenas a monitora
    @Spy
    private final InMemoryBookRepository repository =
            new InMemoryBookRepository();

    @Test
    public void shouldSaveAndFindBookSuccessfully() {
        Book book = Book.of(
                "0-306-40615-2", "Clean Code", "Robert Martin", 2008, 5);

        // Executa o código real: o livro é de fato salvo no HashMap
        repository.save(book);

        // verify confirma que save() foi chamado com o livro esperado
        verify(repository).save(book);

        // assertEquals confirma o estado real do repositório: findByIsbn
        // encontra o livro porque save() de fato o persistiu (código real
        // executado)
        Assert.assertEquals(book, repository.findByIsbn("0-306-40615-2").get());
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

No exemplo abaixo, `save` continua funcionando de verdade (o livro é de
fato guardado no `HashMap`), mas `findByIsbn` é substituído por um *stub*
que sempre retorna vazio, simulando um repositório que "esqueceu" o livro:
{: .fs-3 }

```java
@ExtendWith(MockitoExtension.class)
public class InMemoryBookRepositoryStubTest {

    @Spy
    private final InMemoryBookRepository repository =
            new InMemoryBookRepository();

    @Test
    public void shouldReturnEmptyWhenStubbed() {

        // Sobrescreve findByIsbn() com um stub; apenas este método é simulado
        when(repository.findByIsbn(anyString())).thenReturn(Optional.empty());

        // save() continua usando o código real; o livro é de fato inserido
        Book book = Book.of(
                "0-306-40615-2", "Clean Code", "Robert Martin", 2008, 5);
        repository.save(book);

        // verify confirma a interação com save(), que executou normalmente
        verify(repository).save(book);

        // findByIsbn() retorna vazio (stub), mesmo com o livro salvo de
        // verdade; isso permite simular cenários sem depender do estado
        // interno do repositório
        Assertions.assertTrue(repository.findByIsbn("0-306-40615-2").isEmpty());
    }
}
```

A diferença fundamental entre os dois exemplos é: no primeiro, **tudo é
real**; no segundo, **apenas `findByIsbn` é simulado**, enquanto o restante
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

No exemplo abaixo, `BookService` depende de duas portas de saída,
`BookRepository` e `NotificationService` (as mesmas interfaces definidas
por completo na seção de exercícios práticos, mais adiante):
{: .fs-3 }

```java
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

}
```

```java
@ExtendWith(MockitoExtension.class)
public class BookServiceInjectMocksTest {

    // As duas portas de saída serão simuladas
    @Mock
    BookRepository repository;
    @Mock
    NotificationService notifier;

    // O Mockito cria BookService e injeta os dois mocks automaticamente
    @InjectMocks
    BookService bookService;

    @Test
    public void shouldSaveBookWhenRegistering() {
        Book book = Book.of(
                "0-306-40615-2", "Clean Code", "Robert Martin", 2008, 5);

        bookService.registerBook(book);

        verify(repository).save(book);
        verify(notifier).notify(anyString(), anyString());
    }

}
```

Lembre-se de que `@InjectMocks` depende dos mocks declarados com `@Mock` no
mesmo arquivo de teste: não declare apenas `@InjectMocks` e espere que as
dependências apareçam sozinhas. Valide sempre o comportamento da classe
testada, não apenas os retornos das dependências simuladas.
{: .fs-3 }

No vocabulário do exemplo hexagonal, o objeto anotado com `@InjectMocks` é
o **serviço de aplicação** (como `BooksService`), a classe concreta que
**implementa um ou mais casos de uso** (as portas de entrada,
`domain.ports.in`, como `AddBookUseCase`). O teste não instancia o serviço
na mão; o Mockito monta o serviço e já injeta as portas de saída mockadas
nele. Veja o slide **`@InjectMocks`**.
{: .fs-3 }

### `@Captor`

Às vezes o método que você quer testar não retorna o objeto de interesse:
ele simplesmente o repassa para outra dependência. Nesses casos, é como
querer verificar o conteúdo de um pacote depois de entregá-lo: você precisa
interceptar o pacote antes do envio para conferir o que está dentro. A
anotação [`@Captor`](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Captor.html),
usada em conjunto com `ArgumentCaptor`, faz exatamente isso: captura o
argumento passado para um método de uma dependência simulada para que você
possa inspecioná-lo. É especialmente comum quando o serviço de aplicação
apenas repassa um objeto de domínio para uma porta de saída (como
`repository.save(book)`) sem devolvê-lo diretamente. Os slides
**`@Captor`** e **Exemplo de `@Captor`** trazem esse mesmo raciocínio.
{: .fs-3 }

Para entender o problema que `@Captor` resolve, considere um `BookService`
com um método `borrowBook` que registra um empréstimo. Ele não devolve a
mensagem enviada ao leitor; ela simplesmente some dentro de
`notifier.notify(...)`:
{: .fs-3 }

```java
public class BookService {

    private final BookRepository repository;
    private final NotificationService notifier;

    public BookService(
            final BookRepository repository,
            final NotificationService notifier) {
        this.repository = repository;
        this.notifier = notifier;
    }

    public Book borrowBook(final String isbn, final String patronEmail) {
        Book book = repository.findByIsbn(isbn).orElseThrow();

        // A mensagem é construída internamente; o teste não tem acesso a ela
        notifier.notify(patronEmail, "You borrowed: " + book.getTitle());

        return book;
    }

}
```

O que queremos testar é se a mensagem enviada ao leitor menciona o título
correto do livro. Sem `@Captor`, não há como acessar essa `String` no
teste. Com `@Captor`, o Mockito intercepta a chamada a `notify()` e guarda
o argumento para que possamos inspecioná-lo:
{: .fs-3 }

```java
@ExtendWith(MockitoExtension.class)
public class BookServiceCaptorTest {

    // Simula o notificador; não queremos enviar notificações de verdade
    @Mock
    NotificationService notifier;
    @Mock
    BookRepository repository;

    // Cria BookService e injeta os mocks automaticamente
    @InjectMocks
    BookService bookService;

    // Declara um captor tipado: vai interceptar a mensagem enviada
    @Captor
    ArgumentCaptor<String> messageCaptor;

    @Test
    public void shouldNotifyPatronWithBookTitle() {
        Book book = Book.of(
                "0-306-40615-2", "Clean Code", "Robert Martin", 2008, 5);
        when(repository.findByIsbn("0-306-40615-2"))
                .thenReturn(Optional.of(book));

        // Passo 1: executa o método que queremos testar
        bookService.borrowBook("0-306-40615-2", "ana@ifrs.edu.br");

        // Passo 2: usa verify para confirmar que notify() foi chamado e,
        // ao mesmo tempo, captura a mensagem passada como argumento
        verify(notifier).notify(eq("ana@ifrs.edu.br"), messageCaptor.capture());

        // Passo 3: recupera a mensagem capturada
        String message = messageCaptor.getValue();

        // Passo 4: inspeciona a mensagem; ela deve citar o título do livro
        assertTrue(message.contains("Clean Code"));
    }
}
```

O fluxo segue três responsabilidades bem separadas: `@InjectMocks` monta o
objeto testado, `verify` + `messageCaptor.capture()` confirmam que a
interação ocorreu e guardam o argumento, e `assertTrue` valida o conteúdo
capturado. Remover qualquer uma dessas etapas enfraquece o teste: sem
`verify`, o captor nunca é acionado; sem a asserção final, você confirma
que `notify` foi chamado mas não verifica se a mensagem estava correta.
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
como planejado?"* Essa distinção é o tema do slide **`verify` vs. `assert`**.
{: .fs-3 }

```java
@ExtendWith(MockitoExtension.class)
public class BookServiceVerifyVsAssertTest {

    @Mock
    BookRepository repository;
    @Mock
    NotificationService notifier;

    @InjectMocks
    BookService bookService;

    @Test
    public void shouldReturnBookAndNotifyPatron() {
        Book book = Book.of(
                "0-306-40615-2", "Clean Code", "Robert Martin", 2008, 5);
        when(repository.findByIsbn("0-306-40615-2"))
                .thenReturn(Optional.of(book));

        Book borrowed = bookService.borrowBook(
                "0-306-40615-2", "ana@ifrs.edu.br");

        // assert: verifica o RESULTADO retornado pelo método testado
        assertEquals("Clean Code", borrowed.getTitle());

        // verify: verifica o COMPORTAMENTO, se o leitor foi notificado
        // exatamente uma vez
        verify(notifier, times(1))
                .notify(eq("ana@ifrs.edu.br"), anyString());
    }
}
```

Use `assert` quando o método testado retorna um valor que você pode comparar
diretamente. Use `verify` quando o método não retorna o dado de interesse, mas
você precisa garantir que a dependência foi acionada corretamente, por
exemplo, que uma notificação foi enviada, que um log foi registrado ou que um
repositório foi chamado para persistir um objeto.
{: .fs-3 }

Evite substituir um pelo outro: um teste que só usa `verify` não checa o
resultado produzido; um teste que só usa `assert` pode passar mesmo que a
dependência nunca tenha sido chamada.
{: .fs-3 }

## Exercícios práticos: testando `BookService`

Agora é a sua vez de escrever testes com Mockito. O cenário reaproveita a
classe `Book` já apresentada na seção de
[Teste Unitário](junit.html#exercícios-práticos-testando-book) e segue a
mesma organização em portas e casos de uso do exemplo hexagonal da
disciplina (`domain.ports.in`, `domain.ports.out`, `domain.service`): duas
portas de entrada (`RegisterBookUseCase`, `BorrowBookUseCase`) descrevem os
casos de uso; duas portas de saída (`BookRepository`,
`NotificationService`) abstraem as dependências externas; e um único
serviço de aplicação, `BookService`, implementa os casos de uso orquestrando
as portas de saída (veja o slide **Cenário: portas e casos de uso**). É
justamente a dependência dessas portas de saída que faz do Mockito uma
ferramenta necessária, como resume o slide **Exercício prático**.
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

Assim como no exemplo hexagonal, as exceções de domínio ficam em
`domain.exception`. Crie
`src/main/java/dev/ifrs/mockito/domain/exception/InvalidBookException.java`:
{: .fs-3 }

```java
package dev.ifrs.mockito.domain.exception;

public class InvalidBookException extends RuntimeException {

    public InvalidBookException(final String message) {
        super(message);
    }
}
```

Crie
`src/main/java/dev/ifrs/mockito/domain/exception/BookNotFoundException.java`:
{: .fs-3 }

```java
package dev.ifrs.mockito.domain.exception;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(final String message) {
        super(message);
    }
}
```

Crie
`src/main/java/dev/ifrs/mockito/domain/exception/NoCopiesAvailableException.java`:
{: .fs-3 }

```java
package dev.ifrs.mockito.domain.exception;

public class NoCopiesAvailableException extends RuntimeException {

    public NoCopiesAvailableException(final String message) {
        super(message);
    }
}
```

Crie `src/main/java/dev/ifrs/mockito/domain/model/Book.java`:
{: .fs-3 }

```java
package dev.ifrs.mockito.domain.model;

import dev.ifrs.mockito.domain.exception.InvalidBookException;

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

As **portas de saída** (`domain.ports.out`) abstraem tudo que é externo ao
domínio: persistência e notificação. Crie
`src/main/java/dev/ifrs/mockito/domain/ports/out/BookRepository.java`:
{: .fs-3 }

```java
package dev.ifrs.mockito.domain.ports.out;

import dev.ifrs.mockito.domain.model.Book;

import java.util.Optional;

public interface BookRepository {

    Optional<Book> findByIsbn(String isbn);

    void save(Book book);
}
```

Crie
`src/main/java/dev/ifrs/mockito/domain/ports/out/NotificationService.java`:
{: .fs-3 }

```java
package dev.ifrs.mockito.domain.ports.out;

public interface NotificationService {

    void notify(String recipient, String message);
}
```

As **portas de entrada** (`domain.ports.in`) descrevem os casos de uso, um
por operação, no mesmo estilo de `AddBookUseCase` no exemplo hexagonal.
Crie `src/main/java/dev/ifrs/mockito/domain/ports/in/RegisterBookUseCase.java`:
{: .fs-3 }

```java
package dev.ifrs.mockito.domain.ports.in;

import dev.ifrs.mockito.domain.model.Book;

public interface RegisterBookUseCase {

    void registerBook(Book book);
}
```

Crie `src/main/java/dev/ifrs/mockito/domain/ports/in/BorrowBookUseCase.java`:
{: .fs-3 }

```java
package dev.ifrs.mockito.domain.ports.in;

import dev.ifrs.mockito.domain.model.Book;

public interface BorrowBookUseCase {

    Book borrowBook(String isbn, String patronEmail);
}
```

Por fim, o **serviço de aplicação** (`domain.service`) implementa as duas
portas de entrada, orquestrando as portas de saída, assim como
`BooksService` faz no exemplo hexagonal. Crie
`src/main/java/dev/ifrs/mockito/domain/service/BookService.java`:
{: .fs-3 }

```java
package dev.ifrs.mockito.domain.service;

import dev.ifrs.mockito.domain.exception.BookNotFoundException;
import dev.ifrs.mockito.domain.exception.NoCopiesAvailableException;
import dev.ifrs.mockito.domain.model.Book;
import dev.ifrs.mockito.domain.ports.in.BorrowBookUseCase;
import dev.ifrs.mockito.domain.ports.in.RegisterBookUseCase;
import dev.ifrs.mockito.domain.ports.out.BookRepository;
import dev.ifrs.mockito.domain.ports.out.NotificationService;

public class BookService implements RegisterBookUseCase, BorrowBookUseCase {

    private final BookRepository repository;
    private final NotificationService notifier;

    public BookService(
            final BookRepository repository,
            final NotificationService notifier) {
        this.repository = repository;
        this.notifier = notifier;
    }

    @Override
    public void registerBook(final Book book) {
        repository.save(book);
        notifier.notify(
                "librarian@library.dev",
                "Book registered: " + book.getTitle());
    }

    @Override
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
   `src/test/java/dev/ifrs/mockito/domain/service/BookServiceTest.java`.
2. Anote a classe com `@ExtendWith(MockitoExtension.class)`, como nos
   exemplos apresentados anteriormente.
3. Resolva os exercícios **na ordem**: eles vão do mais simples ao mais
   desafiador e reaproveitam o que você aprendeu no exercício anterior.
4. Depois de cada exercício, execute `mvn test` (ou use o VS Code) e só
   avance quando o teste passar.
{: .fs-3 }

### Exercício 1: `@Mock` e `@InjectMocks` básicos
{: .fw-500 }

Declare `@Mock BookRepository repository` e `@Mock NotificationService
notifier` (as duas portas de saída) e `@InjectMocks BookService
bookService` (o serviço de aplicação que implementa `RegisterBookUseCase`
e `BorrowBookUseCase`). Escreva `shouldSaveBookWhenRegistering`: crie um
`Book` válido, chame `bookService.registerBook(book)` e use `verify` para
confirmar que `repository.save(book)` foi chamado exatamente uma vez.
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

Os exemplos desta página já trazem o código completo de cada teste, usando
sempre `Book` e suas portas (`BookRepository`, `NotificationService`). Se
quiser ver outras variações clássicas de uso do Mockito (fora do contexto
de `Book`), consulte o repositório de exemplos da disciplina:
{: .fs-3 }

    git clone -b dev https://github.com/rodrigoprestesmachado/vvs
    code vvs/exemplos/mockito/

## Teste seus conhecimentos 🧠

Revise o texto e os slides e responda às questões teóricas abaixo.
{: .fs-3 }

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
