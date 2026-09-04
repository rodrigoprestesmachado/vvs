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
você aprofunda cada tópico com exemplos baseados na classe de domínio
[`Book`](https://github.com/rodrigoprestesmachado/vvs/blob/dev/exemplos/hexagonal/src/main/java/dev/ifrs/hexagonal/domain/model/Book.java)
do projeto hexagonal da disciplina.
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

No exemplo hexagonal, a unidade ideal para começar é a classe `Book`: ela
valida ISBN-10, título, autor, ano e exemplares em `Book.of(...)`, sem
depender de banco, REST ou Quarkus. Isso é Java puro e, portanto, um alvo
natural de teste unitário.
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

Trecho resumido da API (veja o
[código completo](https://github.com/rodrigoprestesmachado/vvs/blob/dev/exemplos/hexagonal/src/main/java/dev/ifrs/hexagonal/domain/model/Book.java)):
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

import dev.ifrs.hexagonal.domain.model.Book;
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

No projeto hexagonal, as dependências do JUnit já vêm pelo Quarkus. Em um
projeto Maven clássico, elas aparecem no `pom.xml` assim:
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

import dev.ifrs.hexagonal.domain.model.Book;
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

import dev.ifrs.hexagonal.domain.exception.InvalidBookException;
import dev.ifrs.hexagonal.domain.model.Book;
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

No módulo hexagonal, execute:
{: .fs-3 }

```bash
cd exemplos/hexagonal
./mvnw test
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
o mesmo fluxo se aplica ao módulo `exemplos/hexagonal` quando você criar a
classe `BookTest`.
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

Depois de assistir, use `./mvnw test` ou a própria extensão para validar
os testes que você escrever na próxima seção.
{: .fs-3 }

## Exercícios práticos: testando `Book`

Agora é a sua vez de escrever testes unitários sobre a classe
[`Book`](https://github.com/rodrigoprestesmachado/vvs/blob/dev/exemplos/hexagonal/src/main/java/dev/ifrs/hexagonal/domain/model/Book.java).
O objetivo é exercitar `@Test`, assertivas e `assertThrows` sem depender de
banco, HTTP ou mocks.
{: .fs-3 }

### Como obter o código

```bash
git clone -b dev https://github.com/rodrigoprestesmachado/vvs
code vvs/exemplos/hexagonal
```

### Preparação

1. Crie a classe `BookTest` em
   `src/test/java/dev/ifrs/hexagonal/domain/model/BookTest.java`.
2. Resolva os exercícios **na ordem**: eles vão do mais simples ao mais
   desafiador e reaproveitam o que você aprendeu no exercício anterior.
3. Depois de cada exercício, execute `./mvnw test` (ou use o VS Code) e só
   avance quando o teste passar.
{: .fs-3 }

### Exercício 1: criação válida

Escreva `shouldCreateBookWhenDataIsValid`, chamando `Book.of` com o ISBN
`0-306-40615-2` e dados coerentes (título, autor, ano e exemplares). Use
`assertEquals` para conferir cada um desses quatro valores com os getters
de `Book`.
{: .fs-3 }

> Este é o mesmo teste do Exemplo 1. Copie-o para começar com confiança e
> depois siga para os próximos.
{: .fs-3 }

### Exercício 2: título em branco

Escreva `shouldRejectBlankTitle`: chame `Book.of` com `title` igual a
`""` (mantendo os demais campos válidos) e use `assertThrows` para
verificar que `InvalidBookException` é lançada. Use o Exemplo 4 como
modelo.
{: .fs-3 }

### Exercício 3: autor em branco

Repita a estrutura do Exercício 2, agora trocando o `author` por `""`.
Nomeie o método `shouldRejectBlankAuthor`.
{: .fs-3 }

### Exercício 4: exemplares negativos

Escreva `shouldRejectNegativeCopies`: use um `copiesAvailable` negativo
(por exemplo, `-1`) e verifique que `InvalidBookException` é lançada.
{: .fs-3 }

### Exercício 5: ano inválido

Escreva `shouldRejectFuturePublicationYear`, usando um ano bem no futuro
(por exemplo, `9999`), e `shouldRejectYearZero`, usando o ano `0`. Ambos
devem lançar `InvalidBookException`.
{: .fs-3 }

### Exercício 6: reduzindo repetição com `@BeforeEach`

Até aqui, cada teste provavelmente repetiu o mesmo ISBN, título e autor
válidos. Crie um campo `VALID_ISBN` (ou similar) e, se achar necessário,
um método auxiliar para montar um `Book` válido, reduzindo a duplicação
entre os exercícios anteriores. Adicione `@DisplayName` a pelo menos dois
testes para deixar o relatório mais legível.
{: .fs-3 }

### Exercício 7 (desafio): validação de ISBN

Agora explore a validação de ISBN-10 lendo o método `validate` na classe
`Book`. Escreva:
{: .fs-3 }

* `shouldRejectIsbnWithWrongLength`: ISBN com comprimento incorreto, como
  `"123"`.
* `shouldRejectIsbnWithInvalidCheckDigit`: ISBN com o dígito verificador
  trocado (por exemplo, troque o último dígito de `0-306-40615-2`).
* `shouldAcceptIsbnWithCheckDigitX`: um ISBN-10 válido cujo dígito
  verificador seja `X`, usando `assertDoesNotThrow`.
{: .fs-3 }

### Dicas

* Não é necessário Mockito nestes exercícios: `Book` não tem dependências
  externas.
* Se travar no Exercício 7, releia a seção **Assertivas** e o método
  `validate` de `Book.java` antes de tentar de novo.
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

Classe `Book` (exemplo hexagonal). Disponível em:
[github.com/rodrigoprestesmachado/vvs/.../Book.java](https://github.com/rodrigoprestesmachado/vvs/blob/dev/exemplos/hexagonal/src/main/java/dev/ifrs/hexagonal/domain/model/Book.java).
{: .fs-3 }

<center>
    <a href="rpmhub.dev" target="blanck"><img src="../imgs/logo.png" alt="Rodrigo Prestes Machado" width="3%" height="3%" border=0 style="border:0; text-decoration:none; outline:none"></a><br/>

    <a rel="license" href="http://creativecommons.org/licenses/by/4.0/">CC BY 4.0 DEED</a>
</center>
