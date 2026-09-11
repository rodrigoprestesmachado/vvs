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
você aprofunda cada tópico com exemplos baseados em uma classe de domínio
`Book`, uma versão simplificada e autocontida (sem framework, banco ou HTTP)
inspirada no projeto hexagonal da disciplina.
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

A classe `Book` usada a seguir é a unidade ideal para começar: ela valida
ISBN-10, título, autor, ano e exemplares em `Book.of(...)`, sem depender de
banco, REST ou Quarkus. Isso é Java puro e, portanto, um alvo natural de
teste unitário.
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

## O domínio `Book` como unidade sob teste

A fábrica `Book.of` é o único ponto de criação validado. Se os dados forem
válidos, retorna um `Book`; se algum invariante for violado, lança
`InvalidBookException`.
{: .fs-3 }

Trecho resumido da API (o código completo é apresentado na seção
[Classes sob teste](#classes-sob-teste), mais adiante):
{: .fs-3 }

```java
public static Book of(
        final String isbn,
        final String title,
        final String author,
        final int publicationYear,
        final int copiesAvailable) {
    validate(isbn, title, author, publicationYear, copiesAvailable);
    return builder()
            .isbn(isbn)
            .title(title)
            .author(author)
            .publicationYear(publicationYear)
            .copiesAvailable(copiesAvailable)
            .build();
}
```

ISBN válido usado nos exemplos abaixo: `0-306-40615-2` (hífens são aceitos
e removidos na validação).
{: .fs-3 }

## Primeiro teste

O Exemplo 1 mostra um teste JUnit 5 que cria um livro válido e verifica o
título (o mesmo tipo de exemplo apresentado nos slides):
{: .fs-3 }

```java
import static org.junit.jupiter.api.Assertions.assertEquals;

import dev.ifrs.junit.model.Book;
import org.junit.jupiter.api.Test;

class BookTest {

    @Test
    void shouldCreateBookWhenDataIsValid() {
        Book book = Book.of(
                "0-306-40615-2",
                "Clean Code",
                "Robert Martin",
                2008,
                5);

        assertEquals("Clean Code", book.getTitle());
        assertEquals("Robert Martin", book.getAuthor());
        assertEquals(2008, book.getPublicationYear());
    }
}
```

<center>
Exemplo 1: primeiro teste unitário com Book
</center>
{: .fs-3 }

No Exemplo 1, a anotação `@Test` indica que o método é um caso de teste.
A assertiva `assertEquals` compara o valor esperado com o obtido pelos
getters do `Book`.
{: .fs-3 }

Em projetos Quarkus, as dependências do JUnit já vêm por padrão. Em um
projeto Maven clássico como o que você vai criar nos exercícios, elas
precisam ser declaradas no `pom.xml` assim:
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
seção aprofunda o tema com exemplos sobre `Book`. Entre as anotações mais
usadas estão `@BeforeAll`, `@AfterAll`, `@BeforeEach` e `@AfterEach`
([documentação](https://junit.org/junit5/docs/current/user-guide/#writing-tests-annotations)):
{: .fs-3 }

* `@BeforeAll`: método estático executado **antes** de todos os outros métodos de teste.
* `@AfterAll`: método estático executado **depois** de todos os outros métodos de teste.
* `@BeforeEach`: método executado **antes** de cada método anotado com `@Test`, `@RepeatedTest`, `@ParameterizedTest` ou `@TestFactory`.
* `@AfterEach`: método executado **depois** de cada método anotado com `@Test`, `@RepeatedTest`, `@ParameterizedTest` ou `@TestFactory`.
{: .fs-3 }

O Exemplo 2 usa `@BeforeEach` para montar um `Book` válido antes de cada
teste. Assim, cada método começa com o mesmo estado inicial, sem repetir a
chamada a `Book.of` em todos os testes.
{: .fs-3 }

```java
import static org.junit.jupiter.api.Assertions.assertEquals;

import dev.ifrs.junit.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BookLifecycleTest {

    private Book book;

    @BeforeEach
    void setUp() {
        book = Book.of(
                "0-306-40615-2",
                "Clean Code",
                "Robert Martin",
                2008,
                5);
    }

    @Test
    @DisplayName("Deve expor o ISBN informado")
    void shouldExposeIsbn() {
        assertEquals("0-306-40615-2", book.getIsbn());
    }

    @Test
    @DisplayName("Deve expor a quantidade de exemplares")
    void shouldExposeCopies() {
        assertEquals(5, book.getCopiesAvailable());
    }
}
```

<center>
Exemplo 2: @BeforeEach e @DisplayName com Book
</center>
{: .fs-3 }

Observe o uso de `@DisplayName`: a anotação permite um nome mais
significativo nos relatórios de execução.
{: .fs-3 }

Outra situação comum é definir a ordem de execução dos casos de teste. Com
`@Order`, você estabelece uma sequência pré-definida. No Exemplo 3, o método
`second` (ordem 1) roda antes de `first` (ordem 2). Em testes de domínio
como `Book`, prefira testes independentes; `@Order` aparece aqui só para
você conhecer a anotação.
{: .fs-3 }

```java
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookOrderDemoTest {

    @Test
    @Order(2)
    void first() {
        assertEquals(1, 1);
    }

    @Test
    @Order(1)
    void second() {
        assertEquals(1, 1);
    }
}
```

<center>
Exemplo 3: ordem de execução com @Order
</center>
{: .fs-3 }

## Assertivas

Como mostrado nos slides, o JUnit 5 oferece várias assertivas. Entre as mais
comuns estão `assertEquals`, `assertTrue`, `assertNull` e `assertTimeout`.
Vale destacar `assertThrows`, usada para verificar exceções, com uma forma
de escrita um pouco diferente das demais.
{: .fs-3 }

No domínio `Book`, dados inválidos devem produzir `InvalidBookException`.
O Exemplo 4 verifica o caso de título em branco:
{: .fs-3 }

```java
import static org.junit.jupiter.api.Assertions.assertThrows;

import dev.ifrs.junit.exception.InvalidBookException;
import dev.ifrs.junit.model.Book;
import org.junit.jupiter.api.Test;

class BookExceptionTest {

    @Test
    void shouldRejectBlankTitle() {
        assertThrows(InvalidBookException.class, () -> {
            Book.of("0-306-40615-2", "", "Robert Martin", 2008, 5);
        });
    }
}
```

<center>
Exemplo 4: assertThrows com InvalidBookException
</center>
{: .fs-3 }

No Exemplo 4, `assertThrows` recebe a classe da exceção esperada e uma
expressão [lambda](https://www.w3schools.com/java/java_lambda.asp) com o
código que deve falhar. Se a exceção não for lançada (ou for de outro tipo),
o teste falha.
{: .fs-3 }

## Boas práticas

Os slides de JUnit 5 também destacam boas práticas que valem a pena fixar
desde o início:
{: .fs-3 }

* **Independência:** cada teste deve ser independente e não depender do
  estado deixado por outros testes.
* **Nomeação:** use nomes descritivos nos métodos de teste, indicando o
  comportamento que está sendo verificado (por exemplo,
  `shouldRejectBlankTitle`).
* **Organização:** organize os testes em classes separadas, correspondendo
  às classes de produção. Para `Book`, o natural é uma classe `BookTest`.
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
A classe `BookTest` respeita essa convenção. Os padrões de inclusão e
exclusão estão na
[documentação](https://maven.apache.org/surefire/maven-surefire-plugin/examples/inclusion-exclusion.html)
do Surefire.
{: .fs-3 }

No seu projeto Maven, execute:
{: .fs-3 }

```bash
mvn test
```

Muitas vezes é necessário agrupar testes para executá-los de forma separada
(por requisito, componente ou funcionalidade). A anotação `@Tag` rotula
testes em categorias:
{: .fs-3 }

```java
@Test
@Tag("domain")
void shouldRejectBlankTitle() {
    assertThrows(InvalidBookException.class, () -> {
        Book.of("0-306-40615-2", "", "Robert Martin", 2008, 5);
    });
}
```

<center>
Exemplo 5: anotação @Tag em um teste de Book
</center>
{: .fs-3 }

Com a configuração abaixo no Surefire, apenas os testes com a tag `domain`
entram em `mvn test`:
{: .fs-3 }

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>${maven-surefire-plugin.version}</version>
    <configuration>
        <groups>domain</groups>
    </configuration>
</plugin>
```

## Executando testes no VS Code

Antes de partir para os exercícios, vale ver como criar e rodar testes
JUnit no editor. O Vídeo 1 usa a extensão
[Java Test Runner](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-test)
para escrever e executar casos de teste. O cenário do vídeo é introdutório;
o mesmo fluxo se aplica ao projeto simples que você vai criar a seguir,
quando criar a classe `BookTest`.
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
    Vídeo 1: como escrever e executar testes JUnit no VS Code
</center>
{: .fs-3 }

Depois de assistir, use `mvn test` ou a própria extensão para validar
os testes que você escrever na próxima seção.
{: .fs-3 }

## Exercícios práticos: testando `Book`

Agora é a sua vez de escrever testes unitários sobre uma classe `Book`.
O objetivo é exercitar os conceitos básicos de teste unitário e assertivas
(`@Test`, asserções e `assertThrows`) sem depender de banco, HTTP ou mocks.
{: .fs-3 }

A classe `Book` que você vai copiar contém, de propósito, alguns bugs. A
ideia é que você escreva os testes descritos em cada exercício e, quando
um deles falhar de um jeito inesperado (uma exceção que não é lançada
quando deveria, ou que é lançada quando não deveria), você investigue o
código de `Book`, encontre o bug e o corrija. Só avance para o próximo
exercício depois que o teste correspondente passar. Esse é o ciclo
vermelho → verde que você vai usar bastante na disciplina.
{: .fs-3 }

### Preparação do projeto

Crie um projeto Quarkus simples do zero, não é necessário clonar nenhum
repositório. Você pode usar a linha de comando:

```bash
mvn io.quarkus.platform:quarkus-maven-plugin:3.15.1:create \
    -DprojectGroupId=dev.ifrs.junit \
    -DprojectArtifactId=book-junit \
    -DclassName="dev.ifrs.junit.GreetingResource" \
    -Dextensions="resteasy-reactive"
cd book-junit
code .
```

### Classes que serão testadas

Copie as duas classes abaixo para o seu projeto.
{: .fs-3 }

```java
package dev.ifrs.junit.exception;

/**
 * Erro de validação de dados de livro (regras de domínio ou formato).
 */
public class InvalidBookException extends RuntimeException {

    public InvalidBookException(final String message) {
        super(message);
    }
}
```

```java
package dev.ifrs.junit.model;

import dev.ifrs.junit.exception.InvalidBookException;

import java.time.Year;
import java.util.Objects;

/**
 * Representa um livro catalogado pela biblioteca. A criação validada ocorre
 * apenas via {@link #of(String, String, String, int, int)}.
 */
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

    /**
     * Único ponto de criação validado; use em toda lógica de negócio.
     *
     * @param isbn             ISBN-10 (hífens opcionais)
     * @param title            título não vazio
     * @param author           autor não vazio
     * @param publicationYear  ano entre 1 e o ano corrente
     * @param copiesAvailable  exemplares disponíveis (não negativo)
     * @return livro válido
     * @throws InvalidBookException se algum invariante for violado
     */
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
        validateIsbn10(isbn);
        if (title == null || author.isBlank()) {
            throw new InvalidBookException("Title cannot be blank");
        }
        if (author == null || title.isBlank()) {
            throw new InvalidBookException("Author cannot be blank");
        }
        if (title.length() > MAX_TITLE_LENGTH) {
            throw new InvalidBookException(
                    "Title cannot be longer than " + MAX_TITLE_LENGTH
                            + " characters");
        }
        if (author.contains(" ")) {
            throw new InvalidBookException(
                    "Author must include first and last name");
        }
        int currentYear = Year.now().getValue();
        if (publicationYear < 1 || publicationYear > currentYear) {
            throw new InvalidBookException(
                    "Publication year must be between 1 and " + currentYear);
        }
        if (copiesAvailable <= 0) {
            throw new InvalidBookException(
                    "Copies available cannot be negative");
        }
    }

    private static void validateIsbn10(final String isbn) {
        if (isbn == null) {
            throw new InvalidBookException("ISBN cannot be null");
        }

        String clean = isbn.replaceAll("[\\s\\-]", "");

        if (clean.length() != 10) {
            throw new InvalidBookException(
                    "ISBN-10 must have exactly 10 characters after "
                            + "removing hyphens (got: " + clean.length() + ")");
        }

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            char c = clean.charAt(i);
            if (!Character.isDigit(c)) {
                throw new InvalidBookException(
                        "ISBN-10 positions 1–9 must be digits");
            }
            sum += (i + 1) * Character.getNumericValue(c);
        }

        char last = clean.charAt(9);
        if (last == 'X' || last == 'x') {
            sum += 10 * 10;
        } else if (Character.isDigit(last)) {
            sum += 10 * Character.getNumericValue(last);
        } else {
            throw new InvalidBookException(
                    "ISBN-10 check digit must be 0–9 or X");
        }

        if (sum % 11 != 0) {
            throw new InvalidBookException(
                    "Invalid ISBN-10: check digit does not match");
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

### Preparação dos testes

1. Crie a classe `BookTest` em
   `src/test/java/dev/ifrs/junit/model/BookTest.java`.
2. Resolva os exercícios **na ordem**: eles vão do mais simples ao mais
   desafiador e reaproveitam o que você aprendeu no exercício anterior.
3. Depois de cada exercício, execute `mvn test` (ou use o VS Code) e só
   avance quando o teste passar.
{: .fs-3 }

### Exercício 1: criação válida
{: .fw-500 }

Escreva `shouldCreateBookWhenDataIsValid`, chamando `Book.of` com o ISBN
`0-306-40615-2` e dados coerentes (título, autor, ano e exemplares). Use
`assertEquals` para conferir cada um desses quatro valores com os getters
de `Book`.
{: .fs-3 }

### Exercício 2: título em branco
{: .fw-500 }

Escreva `shouldRejectBlankTitle`: chame `Book.of` com `title` igual a
`""` (mantendo os demais campos válidos) e use `assertThrows` para
verificar que `InvalidBookException` é lançada. Use o Exemplo 4 como
modelo. Se o teste falhar (a exceção não é lançada), há um bug em
`validate`; investigue qual campo está sendo verificado no lugar
errado e corrija.
{: .fs-3 }

### Exercício 3: autor em branco
{: .fw-500 }

Repita a estrutura do Exercício 2, agora trocando o `author` por `""`.
Nomeie o método `shouldRejectBlankAuthor`. Assim como no exercício
anterior, se a exceção não for lançada, há um bug para corrigir.
{: .fs-3 }

### Exercício 4: exemplares
{: .fw-500 }

Escreva `shouldRejectNegativeCopies`: use um `copiesAvailable` negativo
(por exemplo, `-1`) e verifique que `InvalidBookException` é lançada.
Escreva também `shouldAcceptZeroCopies`, usando `copiesAvailable` igual a
`0` e `assertDoesNotThrow` (zero exemplares é uma quantidade válida, só
negativos devem ser rejeitados). Se esse segundo teste falhar, há um bug
na condição que valida `copiesAvailable`; corrija-o.
{: .fs-3 }

### Exercício 5: ano inválido
{: .fw-500 }

Escreva `shouldRejectFuturePublicationYear`, usando um ano bem no futuro
(por exemplo, `9999`), e `shouldRejectYearZero`, usando o ano `0`. Ambos
devem lançar `InvalidBookException`.
{: .fs-3 }

### Exercício 6: título muito longo
{: .fw-500 }

Escreva `shouldRejectTitleLongerThan200Characters`, usando um `title`
com mais de 200 caracteres (você pode gerar uma string longa com
`"a".repeat(201)`), e verifique que `InvalidBookException` é lançada.
{: .fs-3 }

### Exercício 7: autor com nome completo
{: .fw-500 }

A regra de negócio diz que `author` deve conter nome e sobrenome (ou
seja, deve haver pelo menos um espaço entre duas palavras). Escreva
`shouldAcceptAuthorWithFullName`, usando um autor como `"Ada Lovelace"`
e `assertDoesNotThrow`, e `shouldRejectAuthorWithoutLastName`, usando um
autor sem espaço, como `"Ada"`, verificando que `InvalidBookException` é
lançada. Se um dos dois testes falhar, há um bug na condição que valida
o nome do autor; corrija-o.
{: .fs-3 }

### Exercício 8: ISBN normalizado
{: .fw-500 }

A regra de negócio diz que `getIsbn()` deve sempre devolver o ISBN
normalizado, ou seja, apenas dígitos (e opcionalmente `X`), sem
separadores ou espaços, mesmo que o ISBN informado em `Book.of` os
contenha. Escreva `shouldReturnNormalizedIsbn`, criando um `Book` com o
ISBN `"0-306-40615-2"` e usando `assertEquals` para verificar que
`getIsbn()` retorna `"0306406152"`. Se o teste falhar, há um bug (ou
uma regra ainda não implementada) em `Book`; corrija-o.
{: .fs-3 }

### Exercício 9: igualdade entre livros
{: .fw-500 }

Dois `Book` com o mesmo ISBN devem ser considerados iguais, mesmo que os
demais campos sejam diferentes. Escreva `shouldConsiderBooksWithSameIsbnEqual`,
criando dois livros com o mesmo ISBN, mas título/autor diferentes, e use
`assertEquals` para comparar os dois objetos. Escreva também
`shouldConsiderBooksWithDifferentIsbnNotEqual`, comparando dois livros
com ISBNs diferentes e usando `assertNotEquals`.
{: .fs-3 }

### Exercício 10: reduzindo repetição com `@BeforeEach`
{: .fw-500 }

Até aqui, cada teste provavelmente repetiu o mesmo ISBN, título e autor
válidos. Crie um campo `VALID_ISBN` (ou similar) e, se achar necessário,
um método auxiliar para montar um `Book` válido, reduzindo a duplicação
entre os exercícios anteriores. Adicione `@DisplayName` a pelo menos dois
testes para deixar o relatório mais legível.
{: .fs-3 }

### Exercício 11 (desafio): validação de ISBN
{: .fw-500 }

Agora explore a validação de ISBN-10 lendo o método `validateIsbn10` na
classe `Book` que você copiou. Se o algoritmo do dígito verificador não
ficar claro, peça a uma IA (por exemplo, o chat do Cursor ou outro
assistente) para explicar passo a passo como o ISBN-10 calcula e confere
seu dígito verificador; isso ajuda a entender por que cada caso de teste
abaixo deve passar ou lançar exceção. Escreva:
{: .fs-3 }

* `shouldRejectIsbnWithWrongLength`: ISBN com comprimento incorreto, como
  `"123"`.
* `shouldRejectIsbnWithInvalidCheckDigit`: ISBN com o dígito verificador
  trocado (por exemplo, troque o último dígito de `0-306-40615-2`).
* `shouldAcceptIsbnWithCheckDigitX`: um ISBN-10 válido cujo dígito
  verificador seja `X`, usando `assertDoesNotThrow`.
{: .fs-3 }

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

Classe `Book` (versão original, usada no projeto hexagonal da disciplina;
a versão simplificada usada nos exercícios está nesta própria página).
Disponível em:
[github.com/rodrigoprestesmachado/vvs/.../Book.java](https://github.com/rodrigoprestesmachado/vvs/blob/dev/exemplos/hexagonal/src/main/java/dev/ifrs/hexagonal/domain/model/Book.java).
{: .fs-3 }

<center>
    <a href="rpmhub.dev" target="blanck"><img src="../imgs/logo.png" alt="Rodrigo Prestes Machado" width="3%" height="3%" border=0 style="border:0; text-decoration:none; outline:none"></a><br/>

    <a rel="license" href="http://creativecommons.org/licenses/by/4.0/">CC BY 4.0 DEED</a>
</center>
