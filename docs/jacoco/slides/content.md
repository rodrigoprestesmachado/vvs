<!-- .slide:  data-background-opacity="0.35" data-background-image="img/title.jpg"
data-transition="convex"  -->
# JaCoCo 🏠
<!-- .element: style="margin-bottom:100px; font-size: 50px; color:white; font-family: Marker Felt;" -->

Pressione 'F' para tela cheia
<!-- .element: style="font-size: small; color:white;" -->

[versão em pdf](?print-pdf)
<!-- .element: style="font-size: small;" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## A planta da casa
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

- O código é a planta de uma casa. Os testes são quem entra e acende a luz.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- O JaCoCo desenha essa planta: onde alguém passou, a luz fica acesa.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Cômodo verde: percorrido por inteiro. Interruptor âmbar: entrou, mas não abriu todas as portas. Cômodo escuro: nenhum teste chegou lá.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## O que ele mede
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

- Instrução: o passo de bytecode, a menor marca no chão.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Linha: o corredor. Ramo: cada porta de um `if`, `else` ou `switch`.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Método e classe: o cômodo e a ala da casa.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## As cores do relatório
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

- Verde: tudo que está naquela linha foi executado.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Amarelo: a linha rodou, mas alguma porta ficou fechada. O losango marca o ramo.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Vermelho: nenhum teste passou por ali.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Por que a extensão no Quarkus?
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

- `prepare-agent` espera na porta da frente, o classloader do Surefire. `report` só desenha a planta depois.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- O `@QuarkusTest` carrega as classes pela porta de serviço, o `QuarkusClassLoader`. O agente do plugin não acompanha essa carga.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- A extensão `quarkus-jacoco` instrumenta essa porta. Os dois juntos, sem ajuste, marcam a mesma classe duas vezes.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->


<!-- .slide: data-background="white" data-transition="convex"  -->
## Como ligar
<!-- .element: style="margin-bottom:30px; font-size: 40px; font-family: Marker Felt; color:black" -->

```xml
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-jacoco</artifactId>
    <scope>test</scope>
</dependency>
```
<!-- .element: style="font-size: 22px; color:black" -->

Depois: `./mvnw verify`
<!-- .element: style="font-size: 22px; color:black" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Onde fica o relatório
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

- HTML: `target/jacoco-report/index.html`
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Dados brutos: `target/jacoco-quarkus.exec`
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- O caminho do HTML muda com `quarkus.jacoco.report-location`.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->


<!-- .slide: data-background="white" data-transition="convex"  -->
## O losango âmbar
<!-- .element: style="margin-bottom:20px; font-size: 40px; font-family: Marker Felt; color:black" -->

```java
if (author == null || author.isBlank()) {
    throw new InvalidBookException("Author cannot be blank");
}
```
<!-- .element: style="font-size: 18px; color:black" -->

Um teste com autor vazio cobre só um lado do `||`. O ramo fica parcial até o autor ir nulo.
<!-- .element: style="font-size: 22px; color:black" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Exercícios
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

- 1. No `BooksServiceTest`, gere o relatório e ache o autor sem cobertura em `Book.validate`.
<!-- .element: style="margin-bottom:28px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- 2. Teste o autor vazio. 3. Cubra `author == null`. 4. Cubra o ano anterior a 1.
<!-- .element: style="margin-bottom:28px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- 5. Exclua `BookRequest` do relatório. 6. Explique por que linha e ramo não andam juntos.
<!-- .element: style="margin-bottom:28px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->
