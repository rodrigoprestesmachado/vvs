<!-- .slide:  data-background-opacity="0.35" data-background-image="img/title.jpg"
data-transition="convex"  -->
# JaCoCo 🪲
<!-- .element: style="margin-bottom:100px; font-size: 50px; color:white; font-family: Marker Felt;" -->

Pressione 'F' para tela cheia
<!-- .element: style="font-size: small; color:white;" -->

[versão em pdf](?print-pdf)
<!-- .element: style="font-size: small;" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## O besouro-lanterna
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

- O código é um museu de vidro. Os testes são os visitantes.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- O JaCoCo é o besouro-lanterna que acende o chão por onde alguém passou.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Sala verde: percorrida por inteiro. Porta âmbar: entrou, mas não abriu todas as portas. Sala escura: nenhum teste chegou lá.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## O que ele mede
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

- Instrução: o passo de bytecode, a menor marca no chão.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Linha: o corredor. Ramo: cada porta de um `if`, `else` ou `switch`.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Método e classe: a sala e a ala do museu.
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

- O Quarkus carrega as classes com o próprio classloader.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- A extensão `quarkus-jacoco` instrumenta esses testes `@QuarkusTest` sem o plugin Maven.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Ligar a extensão e o `jacoco-maven-plugin` ao mesmo tempo instrumenta a classe duas vezes.
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
if (weightKg <= 5) {
    return baseFare();
}
return baseFare() + 1500;
```
<!-- .element: style="font-size: 22px; color:black" -->

Um teste com 2 kg abre só uma porta. O losango fica âmbar até existir um teste com mais de 5 kg.
<!-- .element: style="font-size: 22px; color:black" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Salas que o besouro pode ignorar
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

- DTOs e código gerado não são regra de negócio.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- `quarkus.jacoco.excludes` tira essas classes do relatório.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Exemplo: `**/dto/**/*`
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Luz acesa não é museu sem vazamento
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

- 100% de cobertura diz que os testes passaram por ali.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- Não diz que o resultado está certo. Quem confere o comportamento é o `assert`.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- O modo nativo do Quarkus não gera esse relatório.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Exercícios, um corredor por vez
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

- 1. Gere o relatório e ache a sala escura.
<!-- .element: style="margin-bottom:28px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- 2. Acenda um método inteiro. 3. Feche o losango âmbar. 4. Cubra a exceção.
<!-- .element: style="margin-bottom:28px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->

- 5. Exclua o DTO. 6. Explique por que linha e ramo não andam juntos.
<!-- .element: style="margin-bottom:28px; font-size: 23px; font-family: system-ui; color:#F5F5F5" -->
