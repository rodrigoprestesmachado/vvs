<!-- .slide:  data-background-opacity="0.3" data-background-image="img/title.jpg"
data-transition="convex"  -->
# Introdução ao JUnit 5
<!-- .element: style="margin-bottom:100px; font-size: 50px; color:white; font-family: Marker Felt;" -->

Com exemplos da classe Book (hexagonal)
<!-- .element: style="font-size: small; color:white;" -->

Pressione 'F' para tela cheia
<!-- .element: style="font-size: small; color:white;" -->

[versão em pdf](?print-pdf)
<!-- .element: style="font-size: small;" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## O que é JUnit?
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

- Criado em 1997 por Erich Gamma e Kent Beck, o JUnit é uma das ferramentas mais populares para testes de unidade em Java.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Fornece anotações para identificar métodos de teste, de configuração e de limpeza, além de assertivas para verificar resultados.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Por que usar JUnit?
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

- Testes automatizados garantem a integridade do código.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->
- Facilita a detecção de bugs e erros.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->
- Permite a execução rápida e repetitiva de testes.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Unidade sob teste: Book
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

- No exemplo hexagonal, `Book` é domínio em Java puro.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- `Book.of(...)` valida ISBN-10, título, autor, ano e exemplares.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Dados inválidos lançam `InvalidBookException`.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Ideal para teste unitário: sem banco, REST ou Quarkus.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->


<!-- .slide: data-background="white" data-transition="convex"  -->
## Exemplo de Teste com JUnit 5
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:black" -->

```java
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
    }
}
```
<!-- .element: style="margin-bottom:50px; font-size: 18px; color:black" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Principais Anotações do JUnit 5
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

- `@BeforeAll`: método estático executado antes de todos os testes.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- `@AfterAll`: método estático executado depois de todos os testes.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- `@BeforeEach`: executado antes de cada `@Test` (útil para montar um `Book` válido).
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- `@AfterEach`: executado depois de cada `@Test`.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Principais Assertivas do JUnit 5
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

- `assertEquals`: verifica se dois valores são iguais.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- `assertTrue`: verifica se uma condição é verdadeira.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- `assertNull`: verifica se um valor é nulo.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- `assertThrows`: verifica se uma exceção é lançada (com lambda).
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->


<!-- .slide: data-background="white" data-transition="convex"  -->
## assertThrows com Book
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:black" -->

```java
@Test
void shouldRejectBlankTitle() {
    assertThrows(InvalidBookException.class, () -> {
        Book.of("0-306-40615-2", "", "Robert Martin", 2008, 5);
    });
}
```
<!-- .element: style="margin-bottom:50px; font-size: 18px; color:black" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Boas Práticas
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

- Escrever testes independentes: cada teste não deve depender do estado de outros.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Nomeação descritiva: por exemplo, `shouldRejectBlankTitle`.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Organização: classe `BookTest` para a unidade `Book`.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Exercício prático
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

- Crie `BookTest` no módulo `exemplos/hexagonal`.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Cubra criação válida e rejeições (título, autor, exemplares, ano, ISBN).
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Execute com `./mvnw test`.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Detalhes no texto: docs/unitario/junit.md
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
# Conclusão
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

- JUnit automatiza testes de unidades como `Book`.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Domínio hexagonal em Java puro facilita testes rápidos e isolados.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->


<!-- .slide: data-background="#C9E66A" data-transition="zoom"  -->
# Questões 📚
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt;" -->

<center>
    <iframe src="https://vvs.rpmhub.dev/unitario/slides/questions.html"
        title="Questões sobre JUnit" width="90%" height="500" style="border:none;">
    </iframe>
</center>


<!-- .slide: data-background="#185449" data-transition="convex"  -->
# Referências 📚
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

* JUnit. Disponível em: https://junit.org/junit5/
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

* Book.java (hexagonal). Disponível em: github.com/rodrigoprestesmachado/vvs
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

<center>
<a href="https://rpmhub.dev" target="blanck"><img src="../../imgs/logo.png" alt="Rodrigo Prestes Machado" width="3%" height="3%" border=0 style="border:0; text-decoration:none; outline:none"></a><br/>
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/">CC BY 4.0 DEED</a>
</center>
  <!-- .element: style="margin-top:150px; font-size: 15px; font-family: Bradley Hand" -->
