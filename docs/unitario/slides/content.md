<!-- .slide:  data-background-opacity="0.3" data-background-image="img/title.jpg"
data-transition="convex"  -->
# Introdução ao Junit 5
<!-- .element: style="margin-bottom:100px; font-size: 50px; color:white; font-family: Marker Felt;" -->

Pressione 'F' para tela cheia
<!-- .element: style="font-size: small; color:white;" -->

[versão em pdf](?print-pdf)
<!-- .element: style="font-size: small;" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## O que é JUnit?
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

- Criado em 1997 por Erich Gamma e Kent Beck, O Junit é uma das ferramentas mais populares para testes de unidade em Java.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- O Junit é uma estrutura de teste de unidade para a linguagem de programação Java. Fornece anotações para identificar métodos de teste, métodos de configuração e métodos de limpeza.
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


<!-- .slide: data-background="white" data-transition="convex"  -->
## Exemplo de Teste com JUnit 5
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:black" -->

```java
class MyFirstJUnitJupiterTests {

    private final Calculator calculator = new Calculator();

    @Test
    void addition() {
        assertEquals(2, calculator.add(1, 1));
    }
}
```
<!-- .element: style="margin-bottom:50px; font-size: 20px; color:black" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Principais Anotações do JUnit 5
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

- `@BeforeAll`: Indica que o método estático que será executado antes dos outros métodos.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- `@AfterAll`: Indica que o método estático que será executado depois dos outros métodos.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- `@BeforeEach`: Indica que o método que será executado antes de cada método anotado com: @Test,  `@RepeatedTest`, @ParameterizedTest ou @TestFactory.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- `@AfterEach`: Indica que o método que será executado depois de cada método anotado com: `@Test`, `@RepeatedTest`, `@ParameterizedTest` ou `@TestFactory`.
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

- `assertThrows`: verifica se uma exceção é lançada. Porém, se faz necessário
destacar o uso da assertiva `assertThrows` que, para verificar exceções, possui
uma forma de escrita um pouco diferente das demais.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->


<!-- .slide: data-background="white" data-transition="convex"  -->
## Principais Assertivas do JUnit 5
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:black" -->

```java
@Test
void exception() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
        Integer.parseInt("One");
    });
}
```
<!-- .element: style="margin-bottom:50px; font-size: 20px; color:black" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Boas Práticas
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

- Escrever testes independentes: cada teste deve ser independente e não depender do estado de outros testes.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Nomeação de métodos de teste: use nomes descritivos para os métodos de teste, indicando o comportamento que está sendo testado.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Organização de testes: organize os testes em classes separadas, correspondendo às classes de código que estão sendo testadas.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
# Conclusão
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

- JUnit é uma ferramenta poderosa para testes automatizados em Java.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Permite escrever testes eficientes e confiáveis para garantir a qualidade do código.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
# Referências 📚
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

* Junit. Disponível em: https://junit.org/junit5/
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

<center>
<a href="https://rpmhub.dev" target="blanck"><img src="../../imgs/logo.png" alt="Rodrigo Prestes Machado" width="3%" height="3%" border=0 style="border:0; text-decoration:none; outline:none"></a><br/>
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/">CC BY 4.0 DEED</a>
</center>
  <!-- .element: style="margin-top:150px; font-size: 15px; font-family: Bradley Hand" -->