<!-- .slide:  data-background-opacity="0.3" data-background-image="img/title.jpg"
data-transition="convex"  -->
# Mock 🧪
<!-- .element: style="margin-bottom:100px; font-size: 50px; color:white; font-family: Marker Felt;" -->

Pressione 'F' para tela cheia
<!-- .element: style="font-size: small; color:white;" -->

[versão em pdf](?print-pdf)
<!-- .element: style="font-size: small;" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Por que usar mocks?
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

- Testar uma classe que envia e-mails, consulta um banco ou consome uma API externa é lento, imprevisível e difícil de reproduzir.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Objetos *mock* simulam o comportamento das dependências para que você teste apenas o trecho de código que realmente importa.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## O que é Mockito?
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

- Em Java, é o *framework* mais utilizado para construir objetos *mock*.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Existem alternativas como EasyMock e JMock, mas o Mockito se destaca pela legibilidade e pela integração com o JUnit.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## O conceito de *stub*
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

- Um conceito central no Mockito é o *stub*.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Com `when(...).thenReturn(...)` você combina com antecedência qual resposta a dependência simulada deve dar durante o teste.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Isso torna o comportamento completamente previsível.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Anotações do Mockito
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

- O Mockito oferece quatro anotações que aparecem com frequência em testes unitários.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- `@Mock`, `@Spy`, `@InjectMocks` e `@Captor`.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Cada uma atende a um cenário diferente; combiná-las bem torna os testes expressivos e fáceis de manter.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## `@Mock`
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

- Como um dublê de cinema: substitui o ator real e executa exatamente o que o diretor planejou.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Cria uma instância simulada de uma classe ou interface e permite definir, via *stub*, o que cada chamada de método deve retornar ou lançar.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Use sempre que sua classe depender de um recurso externo (repositório, API, gateway de pagamento etc.).
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- No exemplo hexagonal, `@Mock` simula as **portas de saída** (`domain.ports.out`), como `BookRepository` ou `EmailNotification`.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->


<!-- .slide: data-background="white" data-transition="convex"  -->
## Exemplo de `@Mock`
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:black" -->

```java
@ExtendWith(MockitoExtension.class)
public class BookRepositoryMockTest {

    @Mock
    BookRepository repository;

    @Test
    public void shouldReturnBookWhenFound() {
        Book book = Book.of(
                "0-306-40615-2", "Clean Code", "Robert Martin", 2008, 5);

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
        when(repository.findByIsbn("invalid"))
                .thenThrow(new IllegalArgumentException());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            repository.findByIsbn("invalid");
        });
    }
}
```
<!-- .element: style="margin-bottom:50px; font-size: 14px; color:black" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## `@Spy`
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

- Usa o objeto **real**, mas envolve esse objeto com uma camada de monitoramento.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Como uma câmera de segurança: tudo continua funcionando normalmente, mas cada movimento fica registrado.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Permite executar o código real, verificar chamadas com `verify` e, se necessário, sobrescrever métodos pontuais via *stub*.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Útil quando você tem uma implementação real e simples de uma **porta de saída** (por exemplo, um `BookRepository` em memória).
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->


<!-- .slide: data-background="white" data-transition="convex"  -->
## Exemplo de `@Spy`: `InMemoryBookRepository`
<!-- .element: style="margin-bottom:50px; font-size: 32px; font-family: Marker Felt; color:black" -->

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
<!-- .element: style="margin-bottom:50px; font-size: 18px; color:black" -->


<!-- .slide: data-background="white" data-transition="convex"  -->
## Exemplo 1 de `@Spy`: monitorando sem alterar
<!-- .element: style="margin-bottom:50px; font-size: 28px; font-family: Marker Felt; color:black" -->

`@Spy` não muda nada no funcionamento da implementação real: `save` de fato guarda o livro e `findByIsbn` de fato o recupera. O Mockito apenas registra cada chamada.
<!-- .element: style="margin-bottom:20px; font-size: 18px; color:black" -->

```java
@ExtendWith(MockitoExtension.class)
public class InMemoryBookRepositorySpyTest {

    @Spy
    private final InMemoryBookRepository repository =
            new InMemoryBookRepository();

    @Test
    public void shouldSaveAndFindBookSuccessfully() {
        Book book = Book.of(
                "0-306-40615-2", "Clean Code", "Robert Martin", 2008, 5);

        repository.save(book);

        verify(repository).save(book);
        Assert.assertEquals(
                book, repository.findByIsbn("0-306-40615-2").get());
    }
}
```
<!-- .element: style="margin-bottom:50px; font-size: 15px; color:black" -->


<!-- .slide: data-background="white" data-transition="convex"  -->
## Exemplo 2 de `@Spy`: sobrescrevendo um método
<!-- .element: style="margin-bottom:50px; font-size: 26px; font-family: Marker Felt; color:black" -->

`save` continua funcionando de verdade, mas `findByIsbn` é substituído por um *stub* que sempre retorna vazio, simulando um repositório que "esqueceu" o livro.
<!-- .element: style="margin-bottom:20px; font-size: 18px; color:black" -->

```java
@ExtendWith(MockitoExtension.class)
public class InMemoryBookRepositoryStubTest {

    @Spy
    private final InMemoryBookRepository repository =
            new InMemoryBookRepository();

    @Test
    public void shouldReturnEmptyWhenStubbed() {
        when(repository.findByIsbn(anyString()))
                .thenReturn(Optional.empty());

        Book book = Book.of(
                "0-306-40615-2", "Clean Code", "Robert Martin", 2008, 5);
        repository.save(book);

        verify(repository).save(book);
        Assertions.assertTrue(
                repository.findByIsbn("0-306-40615-2").isEmpty());
    }
}
```
<!-- .element: style="margin-bottom:50px; font-size: 15px; color:black" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## `@Mock` direto ou `@Spy`?
<!-- .element: style="margin-bottom:50px; font-size: 36px; font-family: Marker Felt; color:#F5F5F5" -->

- No primeiro exemplo, **tudo é real**; no segundo, **apenas `findByIsbn` é simulado**.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Prefira `@Spy` quando o comportamento real do objeto importa e você só precisa monitorar ou ajustar partes específicas.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Se você se pegar substituindo muitos métodos via *stub*, use `@Mock` diretamente: é sinal de que o objeto real não contribui para o teste.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## `@InjectMocks`
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

- Cria uma instância da classe testada e injeta nela os mocks declarados no mesmo teste.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Como encaixar peças em um quebra-cabeça: o Mockito encontra o lugar certo para cada peça simulada.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- No vocabulário do exemplo hexagonal, é o **serviço de aplicação** (como `BooksService`) que **implementa um ou mais casos de uso** (`domain.ports.in`).
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Depende dos mocks já declarados com `@Mock` no mesmo arquivo de teste.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->


<!-- .slide: data-background="white" data-transition="convex"  -->
## `BookService`: `registerBook`
<!-- .element: style="margin-bottom:50px; font-size: 32px; font-family: Marker Felt; color:black" -->

`BookService` depende de duas portas de saída, `BookRepository` e `NotificationService`:
<!-- .element: style="margin-bottom:20px; font-size: 18px; color:black" -->

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
<!-- .element: style="margin-bottom:50px; font-size: 16px; color:black" -->


<!-- .slide: data-background="white" data-transition="convex"  -->
## Exemplo de `@InjectMocks`
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:black" -->

```java
@ExtendWith(MockitoExtension.class)
public class BookServiceInjectMocksTest {

    @Mock
    BookRepository repository;
    @Mock
    NotificationService notifier;

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
<!-- .element: style="margin-bottom:50px; font-size: 16px; color:black" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## `@Captor`
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

- Útil quando o método testado não retorna o objeto de interesse: ele apenas o repassa para outra dependência.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Como interceptar um pacote antes do envio para conferir o que está dentro.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Usada em conjunto com `ArgumentCaptor`, captura o argumento passado para um método de uma dependência simulada.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Comum quando o serviço de aplicação apenas repassa um objeto de domínio para uma porta de saída, sem devolvê-lo.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->


<!-- .slide: data-background="white" data-transition="convex"  -->
## `BookService`: `borrowBook`
<!-- .element: style="margin-bottom:50px; font-size: 32px; font-family: Marker Felt; color:black" -->

`borrowBook` não devolve a mensagem enviada ao leitor; ela some dentro de `notifier.notify(...)`:
<!-- .element: style="margin-bottom:20px; font-size: 18px; color:black" -->

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

        notifier.notify(patronEmail, "You borrowed: " + book.getTitle());

        return book;
    }
}
```
<!-- .element: style="margin-bottom:50px; font-size: 15px; color:black" -->


<!-- .slide: data-background="white" data-transition="convex"  -->
## Exemplo de `@Captor`
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:black" -->

```java
@ExtendWith(MockitoExtension.class)
public class BookServiceCaptorTest {

    @Mock
    NotificationService notifier;
    @Mock
    BookRepository repository;

    @InjectMocks
    BookService bookService;

    @Captor
    ArgumentCaptor<String> messageCaptor;

    @Test
    public void shouldNotifyPatronWithBookTitle() {
        Book book = Book.of(
                "0-306-40615-2", "Clean Code", "Robert Martin", 2008, 5);
        when(repository.findByIsbn("0-306-40615-2"))
                .thenReturn(Optional.of(book));

        bookService.borrowBook("0-306-40615-2", "ana@ifrs.edu.br");

        verify(notifier).notify(
                eq("ana@ifrs.edu.br"), messageCaptor.capture());

        String message = messageCaptor.getValue();

        assertTrue(message.contains("Clean Code"));
    }
}
```
<!-- .element: style="margin-bottom:50px; font-size: 14px; color:black" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Quando usar cada anotação
<!-- .element: style="margin-bottom:50px; font-size: 36px; font-family: Marker Felt; color:#F5F5F5" -->

| Anotação | Papel | Use quando... |
|---|---|---|
| `@Mock` | Cria uma dependência totalmente simulada | A classe testada depende de um recurso externo que você quer isolar |
| `@Spy` | Envolve um objeto **real** com monitoramento | O comportamento real importa, mas você quer verificar interações |
| `@InjectMocks` | Monta a classe testada e injeta os mocks nela | Você já declarou `@Mock`/`@Spy` e quer evitar montar o objeto na mão |
| `@Captor` | Captura o argumento passado a um método do mock | O método simulado não retorna o dado que você quer inspecionar |
<!-- .element: style="margin-bottom:50px; font-size: 17px; font-family: system-ui; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## `verify` vs. `assert`
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

- `assert` (JUnit) verifica o **resultado**: compara o valor retornado por um método com o valor esperado.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- `verify` (Mockito) verifica o **comportamento**: confirma que um método de um *mock* foi chamado, quantas vezes e com quais argumentos.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Um teste que só usa `verify` não checa o resultado; um teste que só usa `assert` pode passar mesmo sem a dependência ter sido chamada.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->


<!-- .slide: data-background="white" data-transition="convex"  -->
## Exemplo de `verify` vs. `assert`
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:black" -->

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
        verify(notifier, times(1))
                .notify(eq("ana@ifrs.edu.br"), anyString());
    }
}
```
<!-- .element: style="margin-bottom:50px; font-size: 14px; color:black" -->


<!-- .slide: data-background="#C9E66A" data-transition="zoom"  -->
# Questões 📚
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt;" -->

<center>
    <iframe src="https://vvs.rpmhub.dev/unitario/slidesMock/questions.html"
        title="Questões sobre Mockito" width="90%" height="500" style="border:none;">
    </iframe>
</center>


<!-- .slide: data-background="#185449" data-transition="convex"  -->
# Referências 📚
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

* Mockito framework site. Disponível em: https://site.mockito.org
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

<center>
<a href="https://rpmhub.dev" target="blanck"><img src="../../imgs/logo.png" alt="Rodrigo Prestes Machado" width="3%" height="3%" border=0 style="border:0; text-decoration:none; outline:none"></a><br/>
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/">CC BY 4.0 DEED</a>
</center>
  <!-- .element: style="margin-top:150px; font-size: 15px; font-family: Bradley Hand" -->
