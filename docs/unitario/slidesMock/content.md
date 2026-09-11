<!-- .slide:  data-background-opacity="0.3" data-background-image="img/title.jpg"
data-transition="convex"  -->
# Introdução a Mocks e Mockito
<!-- .element: style="margin-bottom:100px; font-size: 50px; color:white; font-family: Marker Felt;" -->

Com exemplos baseados em Book, casos de uso e BookService
<!-- .element: style="font-size: small; color:white;" -->

Pressione 'F' para tela cheia
<!-- .element: style="font-size: small; color:white;" -->

[versão em pdf](?print-pdf)
<!-- .element: style="font-size: small;" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Por que usar mocks?
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

- Classes que dependem de banco, HTTP ou e-mail ficam lentas e imprevisíveis de testar.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Objetos mock simulam essas dependências, isolando o trecho de código sob teste.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## O que é Mockito?
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

- Framework mais usado em Java para criar objetos mock.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Conceito central: o *stub*, feito com `when(...).thenReturn(...)`.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Integra bem com o JUnit via `@ExtendWith(MockitoExtension.class)`.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Cenário: portas e casos de uso
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

- Mesma organização do exemplo hexagonal: `domain.ports.in`, `domain.ports.out`, `domain.service`.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- `RegisterBookUseCase` e `BorrowBookUseCase` são as portas de entrada (casos de uso).
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- `BookRepository` e `NotificationService` são as portas de saída (dependências externas).
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- `BookService` implementa os casos de uso e é quem vamos testar com Mockito.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## `@Mock`
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

- Cria uma instância totalmente simulada de uma classe ou interface.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Você define, via *stub*, o que cada chamada deve retornar ou lançar.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Use para isolar recursos externos: repositório, API, gateway de pagamento.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- No exemplo hexagonal, `@Mock` simula as **portas de saída** (`domain.ports.out`).
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->


<!-- .slide: data-background="white" data-transition="convex"  -->
## Exemplo de `@Mock`
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:black" -->

```java
@ExtendWith(MockitoExtension.class)
class BookRepositoryMockTest {

    @Mock
    BookRepository repository;

    @Test
    void shouldReturnBookWhenFound() {
        Book book = Book.of("123", "Clean Code", "R. Martin", 2008, 5);
        when(repository.findByIsbn("123")).thenReturn(Optional.of(book));

        assertEquals(book, repository.findByIsbn("123").get());
    }
}
```
<!-- .element: style="margin-bottom:50px; font-size: 18px; color:black" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## `@Spy`
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

- Envolve um objeto **real** com uma camada de monitoramento.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- O comportamento real continua executando; o Mockito só registra as chamadas.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Útil para uma implementação em memória de `BookRepository`, por exemplo.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->


<!-- .slide: data-background="white" data-transition="convex"  -->
## Exemplo de `@Spy`
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:black" -->

```java
public class InMemoryBookRepository implements BookRepository {
    private final Map<String, Book> books = new HashMap<>();

    public Optional<Book> findByIsbn(String isbn) {
        return Optional.ofNullable(books.get(isbn));
    }

    public void save(Book book) {
        books.put(book.getIsbn(), book);
    }
}
```
<!-- .element: style="margin-bottom:20px; font-size: 18px; color:black" -->

```java
@ExtendWith(MockitoExtension.class)
class InMemoryBookRepositorySpyTest {

    @Spy
    InMemoryBookRepository repository = new InMemoryBookRepository();

    @Test
    void shouldSaveAndFindBookSuccessfully() {
        Book book = Book.of("123", "Clean Code", "R. Martin", 2008, 5);
        repository.save(book);

        verify(repository).save(book);
        assertEquals(book, repository.findByIsbn("123").get());
    }
}
```
<!-- .element: style="margin-bottom:50px; font-size: 18px; color:black" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## `@InjectMocks`
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

- Cria a instância da classe testada (`BookService`) e injeta os mocks declarados.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Evita montar o objeto manualmente a cada teste.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Depende dos mocks já declarados com `@Mock` na mesma classe de teste.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- É o **serviço de aplicação** que implementa os casos de uso (portas de entrada).
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->


<!-- .slide: data-background="white" data-transition="convex"  -->
## Exemplo de `@InjectMocks`
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:black" -->

```java
@ExtendWith(MockitoExtension.class)
class BookServiceInjectMocksTest {

    @Mock
    BookRepository repository;
    @Mock
    NotificationService notifier;
    @InjectMocks
    BookService bookService;

    @Test
    void shouldSaveBookWhenRegistering() {
        Book book = Book.of("123", "Clean Code", "R. Martin", 2008, 5);
        bookService.registerBook(book);

        verify(repository).save(book);
        verify(notifier).notify(anyString(), anyString());
    }
}
```
<!-- .element: style="margin-bottom:50px; font-size: 18px; color:black" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## `@Captor`
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

- Captura o argumento passado para um método de uma dependência simulada.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Útil quando o método testado não retorna o objeto que você quer inspecionar.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Exemplo: capturar o `Book` salvo em `repository.save(...)` após um empréstimo.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->


<!-- .slide: data-background="white" data-transition="convex"  -->
## Exemplo de `@Captor`
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:black" -->

```java
@ExtendWith(MockitoExtension.class)
class BookServiceCaptorTest {

    @Mock
    NotificationService notifier;
    @Mock
    BookRepository repository;
    @InjectMocks
    BookService bookService;

    @Captor
    ArgumentCaptor<String> messageCaptor;

    @Test
    void shouldNotifyPatronWithBookTitle() {
        Book book = Book.of("123", "Clean Code", "R. Martin", 2008, 5);
        when(repository.findByIsbn("123")).thenReturn(Optional.of(book));

        bookService.borrowBook("123", "ana@ifrs.edu.br");

        verify(notifier).notify(eq("ana@ifrs.edu.br"), messageCaptor.capture());
        assertTrue(messageCaptor.getValue().contains("Clean Code"));
    }
}
```
<!-- .element: style="margin-bottom:50px; font-size: 16px; color:black" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## `verify` vs. `assert`
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

- `assert` (JUnit) verifica o **resultado**: o método devolveu o que eu esperava?
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- `verify` (Mockito) verifica o **comportamento**: a interação com a dependência ocorreu como planejado?
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Um bom teste combina os dois quando faz sentido, como em `borrowBook`.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->


<!-- .slide: data-background="white" data-transition="convex"  -->
## Exemplo de `verify` vs. `assert`
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:black" -->

```java
@Test
void shouldReturnBookAndNotifyPatron() {
    Book book = Book.of("123", "Clean Code", "R. Martin", 2008, 5);
    when(repository.findByIsbn("123")).thenReturn(Optional.of(book));

    Book borrowed = bookService.borrowBook("123", "ana@ifrs.edu.br");

    // assert: verifica o RESULTADO retornado pelo método testado
    assertEquals("Clean Code", borrowed.getTitle());

    // verify: verifica o COMPORTAMENTO, se o leitor foi notificado
    verify(notifier, times(1)).notify(eq("ana@ifrs.edu.br"), anyString());
}
```
<!-- .element: style="margin-bottom:50px; font-size: 18px; color:black" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Exercício prático
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

- Crie `BookServiceTest` em um novo projeto Quarkus com Mockito.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Cubra `@Mock`, `@InjectMocks`, exceções, `verify`, `@Captor` e `@Spy`.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Execute com `mvn test`.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Detalhes no texto: docs/unitario/mock.md
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
# Conclusão
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

- Mocks isolam a unidade sob teste de dependências externas lentas ou imprevisíveis.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- `@Mock`, `@Spy`, `@InjectMocks` e `@Captor` cobrem os cenários mais comuns com Mockito.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->


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

* Book.java (hexagonal). Disponível em: github.com/rodrigoprestesmachado/vvs
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

<center>
<a href="https://rpmhub.dev" target="blanck"><img src="../../imgs/logo.png" alt="Rodrigo Prestes Machado" width="3%" height="3%" border=0 style="border:0; text-decoration:none; outline:none"></a><br/>
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/">CC BY 4.0 DEED</a>
</center>
  <!-- .element: style="margin-top:150px; font-size: 15px; font-family: Bradley Hand" -->
