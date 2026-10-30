<!-- .slide:  data-background-opacity="0.25" data-background-image="../img/codegen.png"
data-transition="convex"  -->
# Playwright
<!-- .element: style="margin-bottom:100px; font-size: 50px; color:white; font-family: Marker Felt;" -->

Pressione 'F' para tela cheia
<!-- .element: style="font-size: small; color:white;" -->

[versão em pdf](?print-pdf)
<!-- .element: style="font-size: small;" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## O assistente no navegador
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

Imagine um assistente sentado no seu lugar, na frente do navegador.
<!-- .element: style="margin-bottom:30px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* Ele abre a página, clica, digita e lê o que apareceu na tela.
<!-- .element: style="margin-bottom:25px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* Ele não se cansa. Repete o mesmo roteiro todas as manhãs.
<!-- .element: style="margin-bottom:25px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* Se o botão sumiu ou o texto mudou, ele avisa: o teste falhou.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## O que é o Playwright?
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

Playwright é uma ferramenta da Microsoft para **teste de sistema**.
<!-- .element: style="margin-bottom:30px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* Ela dirige o navegador de verdade: Chromium, Firefox e WebKit.
<!-- .element: style="margin-bottom:25px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* O teste percorre a interface como um usuário: de ponta a ponta.
<!-- .element: style="margin-bottom:25px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* Nesta aula usamos a API **Java**, junto com JUnit e Quarkus.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Em outras palavras
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

Rest Assured pede o prato na cozinha (a API). Playwright senta na mesa e come com os olhos (a tela).
<!-- .element: style="margin-bottom:40px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

Os dois se complementam. Um confere o JSON. O outro confere o botão, o formulário e a mensagem de sucesso.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Por que Playwright?
<!-- .element: style="margin-bottom:40px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

* **Vários navegadores** com a mesma API: Chromium, Firefox e WebKit.
<!-- .element: style="margin-bottom:22px; font-size: 22px; font-family: arial; color:#F5F5F5" -->

* **Espera automática**: o assistente só clica quando o botão já está pronto.
<!-- .element: style="margin-bottom:22px; font-size: 22px; font-family: arial; color:#F5F5F5" -->

* **Codegen**: um escriba anota o que você faz na tela e vira código Java.
<!-- .element: style="margin-bottom:22px; font-size: 22px; font-family: arial; color:#F5F5F5" -->

* **Vídeo e rastreio**: quando falha, você assiste o filme da falha.
<!-- .element: style="margin-bottom:50px; font-size: 22px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## O exemplo desta aula
<!-- .element: style="margin-bottom:40px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

O cadastro de livros em `exemplos/hexagonal`.
<!-- .element: style="margin-bottom:30px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* Interface Vue servida pelo Quarkus em `http://localhost:8080`.
<!-- .element: style="margin-bottom:22px; font-size: 22px; font-family: arial; color:#F5F5F5" -->

* Você cadastra, edita, exclui e lista livros pela tela.
<!-- .element: style="margin-bottom:22px; font-size: 22px; font-family: arial; color:#F5F5F5" -->

* Os testes Java moram em `src/test/java/dev/ifrs/hexagonal/`.
<!-- .element: style="margin-bottom:50px; font-size: 22px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## O que você precisa ter pronto
<!-- .element: style="margin-bottom:40px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

* Java 25 e o wrapper Maven do módulo (`./mvnw`).
<!-- .element: style="margin-bottom:25px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* Docker ligado: o `@QuarkusTest` sobe o MySQL sozinho.
<!-- .element: style="margin-bottom:25px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* Navegadores do Playwright instalados uma vez por máquina.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="white" data-transition="convex"  -->
## Instalar os navegadores
<!-- .element: style="margin-bottom:30px; font-size: 40px; font-family: Marker Felt; color:black" -->

```sh
cd exemplos/hexagonal
./mvnw exec:java -e \
  -D exec.mainClass=com.microsoft.playwright.CLI \
  -D exec.args="install"
```
<!-- .element: style="font-size: 18px; color:black" -->

Só precisa disso uma vez. Depois, os testes já encontram Chromium, Firefox e WebKit.
<!-- .element: style="font-size: 20px; color:black" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Anatomia do assistente
<!-- .element: style="margin-bottom:40px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

Quatro peças, sempre nesta ordem:
<!-- .element: style="margin-bottom:25px; font-size: 22px; font-family: arial; color:#F5F5F5" -->

* **Playwright**: liga o motor.
<!-- .element: style="margin-bottom:20px; font-size: 22px; font-family: arial; color:#F5F5F5" -->

* **Browser**: o navegador em si (Chromium, Firefox ou WebKit).
<!-- .element: style="margin-bottom:20px; font-size: 22px; font-family: arial; color:#F5F5F5" -->

* **BrowserContext**: a gaveta limpa (cookies e sessão só daquele teste).
<!-- .element: style="margin-bottom:20px; font-size: 22px; font-family: arial; color:#F5F5F5" -->

* **Page**: a aba onde o assistente clica e digita.
<!-- .element: style="margin-bottom:50px; font-size: 22px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## A gaveta limpa
<!-- .element: style="margin-bottom:40px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

Cada teste ganha um `BrowserContext` novo.
<!-- .element: style="margin-bottom:30px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

É como esvaziar a gaveta antes de guardar outra coisa: cookies, armazenamento e login do teste anterior não vazam para o próximo.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Espera automática
<!-- .element: style="margin-bottom:40px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

O assistente não aperta o botão enquanto o botão não está pronto.
<!-- .element: style="margin-bottom:30px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* `click()` espera o elemento visível e habilitado.
<!-- .element: style="margin-bottom:22px; font-size: 22px; font-family: arial; color:#F5F5F5" -->

* `assertThat(...).isVisible()` espera o texto aparecer.
<!-- .element: style="margin-bottom:22px; font-size: 22px; font-family: arial; color:#F5F5F5" -->

* Você quase nunca precisa de `Thread.sleep`.
<!-- .element: style="margin-bottom:50px; font-size: 22px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## O escriba: Codegen
<!-- .element: style="margin-bottom:40px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

A forma mais rápida de começar é **gravar**, não escrever.
<!-- .element: style="margin-bottom:30px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

Você usa a tela. O Inspector anota cada clique e cada texto. No fim, você copia o Java.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="white" data-transition="convex"  -->
## Abrir o Codegen
<!-- .element: style="margin-bottom:20px; font-size: 40px; font-family: Marker Felt; color:black" -->

Com o Quarkus em `./mvnw quarkus:dev`:
<!-- .element: style="font-size: 20px; color:black" -->

```sh
./mvnw exec:java -e \
  -D exec.mainClass=com.microsoft.playwright.CLI \
  -D exec.args="codegen --target java http://localhost:8080"
```
<!-- .element: style="font-size: 16px; color:black" -->

Duas janelas: o navegador (você age) e o Inspector (o escriba escreve).
<!-- .element: style="font-size: 20px; color:black" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## O que gravar
<!-- .element: style="margin-bottom:30px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

O cadastro de um livro:
<!-- .element: style="margin-bottom:20px; font-size: 22px; font-family: arial; color:#F5F5F5" -->

* Clique em **Novo livro**.
<!-- .element: style="margin-bottom:16px; font-size: 20px; font-family: arial; color:#F5F5F5" -->

* Preencha ISBN, título, autor e ano.
<!-- .element: style="margin-bottom:16px; font-size: 20px; font-family: arial; color:#F5F5F5" -->

* Clique em **Salvar**.
<!-- .element: style="margin-bottom:16px; font-size: 20px; font-family: arial; color:#F5F5F5" -->

* Use **Assert visibility** na linha da tabela que contém o ISBN.
<!-- .element: style="margin-bottom:40px; font-size: 20px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Assertivas na gravação
<!-- .element: style="margin-bottom:30px; font-size: 38px; font-family: Marker Felt; color:#F5F5F5" -->

Três botões no Inspector, sem escrever código:
<!-- .element: style="margin-bottom:25px; font-size: 22px; font-family: arial; color:#F5F5F5" -->

* **Assert visibility** → `assertThat(locator).isVisible()`
<!-- .element: style="margin-bottom:20px; font-size: 20px; font-family: arial; color:#F5F5F5" -->

* **Assert text** → `assertThat(locator).hasText(...)`
<!-- .element: style="margin-bottom:20px; font-size: 20px; font-family: arial; color:#F5F5F5" -->

* **Assert value** → `assertThat(locator).hasValue(...)`
<!-- .element: style="margin-bottom:50px; font-size: 20px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="white" data-transition="convex"  -->
## O código que o escriba gera
<!-- .element: style="margin-bottom:15px; font-size: 34px; font-family: Marker Felt; color:black" -->

```java
page.navigate("http://localhost:8080");
page.getByRole(BUTTON, new Page.GetByRoleOptions()
    .setName("Novo livro")).click();
page.getByRole(TEXTBOX, new Page.GetByRoleOptions()
    .setName("ISBN-")).fill("156881111X");
page.getByRole(BUTTON, new Page.GetByRoleOptions()
    .setName("Salvar")).click();
assertThat(page.getByRole(ROW).filter(
    new Locator.FilterOptions().setHasText("156881111X")))
    .isVisible();
```
<!-- .element: style="font-size: 15px; color:black" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Lendo o código em voz alta
<!-- .element: style="margin-bottom:30px; font-size: 36px; font-family: Marker Felt; color:#F5F5F5" -->

"Abra a home, clique em Novo livro, preencha o ISBN, clique em Salvar, então a linha com esse ISBN deve estar visível."
<!-- .element: style="margin-bottom:40px; font-size: 22px; font-family: arial; color:#F5F5F5" -->

Se você consegue ler o teste como uma frase, já entendeu a lógica.
<!-- .element: style="margin-bottom:50px; font-size: 22px; font-family: arial; color:#C9E66A" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## O que cada chamada faz
<!-- .element: style="margin-bottom:25px; font-size: 36px; font-family: Marker Felt; color:#F5F5F5" -->

* `Playwright.create()` liga o motor.
<!-- .element: style="margin-bottom:16px; font-size: 20px; font-family: arial; color:#F5F5F5" -->

* `chromium().launch(...)` abre o navegador.
<!-- .element: style="margin-bottom:16px; font-size: 20px; font-family: arial; color:#F5F5F5" -->

* `newContext()` abre a gaveta limpa.
<!-- .element: style="margin-bottom:16px; font-size: 20px; font-family: arial; color:#F5F5F5" -->

* `newPage()` abre a aba.
<!-- .element: style="margin-bottom:16px; font-size: 20px; font-family: arial; color:#F5F5F5" -->

* `getByRole(...).click()` e `fill(...)` agem na tela.
<!-- .element: style="margin-bottom:40px; font-size: 20px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Seletores que falam a língua da tela
<!-- .element: style="margin-bottom:25px; font-size: 34px; font-family: Marker Felt; color:#F5F5F5" -->

O Codegen prefere o que o usuário enxerga, não o CSS escondido:
<!-- .element: style="margin-bottom:20px; font-size: 20px; font-family: arial; color:#F5F5F5" -->

* 1º `getByRole()` → botão **Salvar**, caixa **Título**
<!-- .element: style="margin-bottom:16px; font-size: 20px; font-family: arial; color:#F5F5F5" -->

* 2º `getByText()` → "Nenhum livro cadastrado"
<!-- .element: style="margin-bottom:16px; font-size: 20px; font-family: arial; color:#F5F5F5" -->

* 3º `getByLabel()` → rótulo **Título**
<!-- .element: style="margin-bottom:16px; font-size: 20px; font-family: arial; color:#F5F5F5" -->

* Por último `locator()` com CSS, só se o resto falhar
<!-- .element: style="margin-bottom:40px; font-size: 20px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Do main para o JUnit
<!-- .element: style="margin-bottom:30px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

O Codegen gera um `main()`. Para a disciplina, queremos JUnit e Maven.
<!-- .element: style="margin-bottom:25px; font-size: 22px; font-family: arial; color:#F5F5F5" -->

Duas anotações substituem a abertura e o fechamento manuais:
<!-- .element: style="margin-bottom:25px; font-size: 22px; font-family: arial; color:#F5F5F5" -->

* `@QuarkusTest` sobe a aplicação e o MySQL.
<!-- .element: style="margin-bottom:20px; font-size: 22px; font-family: arial; color:#F5F5F5" -->

* `@UsePlaywright` liga o assistente e entrega um `Page` limpo.
<!-- .element: style="margin-bottom:50px; font-size: 22px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="white" data-transition="convex"  -->
## A classe fica pequena
<!-- .element: style="margin-bottom:15px; font-size: 36px; font-family: Marker Felt; color:black" -->

```java
@QuarkusTest
@UsePlaywright
public class BooksE2ETest {

    @Test
    void cadastraLivro(Page page) {
        page.navigate("http://localhost:8080/");
        page.getByRole(AriaRole.BUTTON,
            new Page.GetByRoleOptions()
                .setName("Novo livro")).click();
        // ... preencher e salvar ...
    }
}
```
<!-- .element: style="font-size: 16px; color:black" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Quem faz o quê
<!-- .element: style="margin-bottom:30px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

* `@QuarkusTest` → sobe o Quarkus em `http://localhost:8080`.
<!-- .element: style="margin-bottom:22px; font-size: 22px; font-family: arial; color:#F5F5F5" -->

* `@UsePlaywright` → abre o Chromium e cria a gaveta limpa.
<!-- .element: style="margin-bottom:22px; font-size: 22px; font-family: arial; color:#F5F5F5" -->

* `Page page` no `@Test` → a aba chega injetada, você só usa.
<!-- .element: style="margin-bottom:22px; font-size: 22px; font-family: arial; color:#F5F5F5" -->

* `@Test` → o JUnit 5 reconhece o método como caso de teste.
<!-- .element: style="margin-bottom:50px; font-size: 22px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Quero ver o navegador
<!-- .element: style="margin-bottom:25px; font-size: 38px; font-family: Marker Felt; color:#F5F5F5" -->

O padrão é invisível (headless). Para acompanhar com os olhos:
<!-- .element: style="margin-bottom:20px; font-size: 20px; font-family: arial; color:#F5F5F5" -->

```java
@UsePlaywright(BooksE2ETest.HeadedOptions.class)
public static class HeadedOptions implements OptionsFactory {
    public Options getOptions() {
        return new Options().setHeadless(false);
    }
}
```
<!-- .element: style="background-color: white; margin-bottom:20px; font-size: 16px; font-family: arial; color:black"  -->

`setHeadless(false)` deixa a janela visível. Útil para depurar na sua máquina.
<!-- .element: style="margin-bottom:40px; font-size: 20px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Independência dos testes
<!-- .element: style="margin-bottom:30px; font-size: 38px; font-family: Marker Felt; color:#F5F5F5" -->

A gaveta da sessão é limpa. O **banco MySQL** não é.
<!-- .element: style="margin-bottom:25px; font-size: 22px; font-family: arial; color:#F5F5F5" -->

Antes de cada caso, apague o ISBN de teste pela API:
<!-- .element: style="margin-bottom:20px; font-size: 22px; font-family: arial; color:#F5F5F5" -->

```java
@BeforeEach
void limparLivroDeTeste() {
    given().delete("/books/156881111X");
}
```
<!-- .element: style="background-color: white; margin-bottom:20px; font-size: 18px; font-family: arial; color:black"  -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Cadastrar um livro
<!-- .element: style="margin-bottom:20px; font-size: 38px; font-family: Marker Felt; color:#F5F5F5" -->

"Dado a home, quando eu criar o livro, então a linha e a mensagem de sucesso aparecem."
<!-- .element: style="margin-bottom:20px; font-size: 18px; font-family: arial; color:#F5F5F5" -->

```java
page.getByRole(AriaRole.BUTTON, ...setName("Salvar")).click();
assertThat(page.getByRole(AriaRole.ROW).filter(
    new Locator.FilterOptions().setHasText("156881111X")))
    .isVisible();
assertThat(page.getByRole(AriaRole.STATUS))
    .hasText("Livro criado com sucesso.");
```
<!-- .element: style="background-color: white; margin-bottom:20px; font-size: 16px; font-family: arial; color:black"  -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Editar um livro
<!-- .element: style="margin-bottom:25px; font-size: 38px; font-family: Marker Felt; color:#F5F5F5" -->

O ISBN fica desabilitado na edição. Só os outros campos mudam.
<!-- .element: style="margin-bottom:20px; font-size: 20px; font-family: arial; color:#F5F5F5" -->

* Crie a condição prévia com `POST /books` (mais rápido que a tela).
<!-- .element: style="margin-bottom:18px; font-size: 20px; font-family: arial; color:#F5F5F5" -->

* Clique em **Editar** na linha daquele ISBN.
<!-- .element: style="margin-bottom:18px; font-size: 20px; font-family: arial; color:#F5F5F5" -->

* Troque o título, salve e confira a linha nova na tabela.
<!-- .element: style="margin-bottom:50px; font-size: 20px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Excluir: o diálogo vem primeiro
<!-- .element: style="margin-bottom:25px; font-size: 36px; font-family: Marker Felt; color:#F5F5F5" -->

A exclusão abre um `confirm` do navegador. O assistente precisa estar escutando **antes** do clique.
<!-- .element: style="margin-bottom:20px; font-size: 20px; font-family: arial; color:#F5F5F5" -->

```java
page.onDialog(dialog -> dialog.accept());
page.getByRole(AriaRole.ROW)
    .filter(...setHasText("156881111X"))
    .getByRole(AriaRole.BUTTON, ...setName("Excluir"))
    .click();
assertThat(...).not().isVisible();
```
<!-- .element: style="background-color: white; margin-bottom:20px; font-size: 16px; font-family: arial; color:black"  -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Aceitar ou recusar
<!-- .element: style="margin-bottom:40px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

* `dialog.accept()` → o livro some. A mensagem diz que foi removido.
<!-- .element: style="margin-bottom:30px; font-size: 22px; font-family: arial; color:#F5F5F5" -->

* `dialog.dismiss()` → o livro permanece na tabela.
<!-- .element: style="margin-bottom:30px; font-size: 22px; font-family: arial; color:#F5F5F5" -->

Os exercícios 5 e 6 pedem os dois caminhos. É o mesmo clique, com respostas diferentes.
<!-- .element: style="margin-bottom:50px; font-size: 22px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Rodar com Maven
<!-- .element: style="margin-bottom:25px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

```sh
cd exemplos/hexagonal
./mvnw test -Dtest=BooksE2ETest
```
<!-- .element: style="background-color: white; margin-bottom:20px; font-size: 20px; font-family: arial; color:black"  -->

O Surefire grava o relatório XML em `target/surefire-reports/`. É o mesmo formato que a integração contínua sabe ler.
<!-- .element: style="margin-bottom:50px; font-size: 20px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Vários navegadores
<!-- .element: style="margin-bottom:30px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

A mesma API serve Chromium, Firefox e WebKit.
<!-- .element: style="margin-bottom:25px; font-size: 22px; font-family: arial; color:#F5F5F5" -->

Com `@ParameterizedTest` você roda o mesmo cenário nos três. Com `@Execution(CONCURRENT)`, os três ao mesmo tempo.
<!-- .element: style="margin-bottom:50px; font-size: 22px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Um Playwright por invocação
<!-- .element: style="margin-bottom:30px; font-size: 38px; font-family: Marker Felt; color:#F5F5F5" -->

A classe `Playwright` **não pode ser compartilhada entre threads**.
<!-- .element: style="margin-bottom:25px; font-size: 22px; font-family: arial; color:#F5F5F5" -->

A solução é simples: cada invocação do teste cria e fecha o seu próprio `Playwright` num `try` with resources.
<!-- .element: style="margin-bottom:50px; font-size: 22px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## ISBN distinto por navegador
<!-- .element: style="margin-bottom:30px; font-size: 38px; font-family: Marker Felt; color:#F5F5F5" -->

O banco é compartilhado. Se os três testes usarem o mesmo ISBN, um pisa no outro.
<!-- .element: style="margin-bottom:30px; font-size: 22px; font-family: arial; color:#F5F5F5" -->

Chromium, Firefox e WebKit recebem ISBNs diferentes. Cada um grava o seu livro sem briga de chave primária.
<!-- .element: style="margin-bottom:50px; font-size: 22px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Gravar vídeo
<!-- .element: style="margin-bottom:25px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

Útil quando a falha não se reproduz na mão. O Playwright salva um `.webm` por contexto.
<!-- .element: style="margin-bottom:20px; font-size: 20px; font-family: arial; color:#F5F5F5" -->

```java
new Options()
    .setLaunchOptions(
        new BrowserType.LaunchOptions().setSlowMo(300))
    .setContextOptions(new Browser.NewContextOptions()
        .setRecordVideoDir(Paths.get("target/videos/chromium")));
```
<!-- .element: style="background-color: white; margin-bottom:20px; font-size: 16px; font-family: arial; color:black"  -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Quando o filme aparece
<!-- .element: style="margin-bottom:40px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

O arquivo `.webm` só é escrito **depois** que o contexto fecha.
<!-- .element: style="margin-bottom:30px; font-size: 22px; font-family: arial; color:#F5F5F5" -->

`slowMo(300)` coloca 300 ms entre cada ação. Sem isso, o vídeo passa rápido demais para você acompanhar.
<!-- .element: style="margin-bottom:50px; font-size: 22px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#C9E66A" data-transition="zoom"  -->
# Exercícios de fixação
<!-- .element: style="margin-bottom:30px; font-size: 45px; font-family: Marker Felt;" -->

Classe nova: `LivrosFixacaoTest`, no módulo `exemplos/hexagonal`.
<!-- .element: style="margin-bottom:25px; font-size: 22px; font-family: arial;" -->

Os dois primeiros são fáceis de propósito. O objetivo é ganhar confiança antes do diálogo e do desafio.
<!-- .element: style="margin-bottom:40px; font-size: 22px; font-family: arial;" -->


<!-- .slide: data-background="#C9E66A" data-transition="convex"  -->
## Preparação
<!-- .element: style="margin-bottom:25px; font-size: 40px; font-family: Marker Felt;" -->

* `@QuarkusTest` e `@UsePlaywright` na classe.
<!-- .element: style="margin-bottom:18px; font-size: 20px; font-family: arial;" -->

* ISBN próprio: `0134685991`. Limpe no `@BeforeEach` com `DELETE /books/{isbn}`.
<!-- .element: style="margin-bottom:18px; font-size: 20px; font-family: arial;" -->

* Rode com `./mvnw test -Dtest=LivrosFixacaoTest`.
<!-- .element: style="margin-bottom:40px; font-size: 20px; font-family: arial;" -->


<!-- .slide: data-background="#C9E66A" data-transition="convex"  -->
## Exercício 1: o botão está lá
<!-- .element: style="margin-bottom:30px; font-size: 36px; font-family: Marker Felt;" -->

*Revise a anatomia e a espera automática.*
<!-- .element: style="margin-bottom:20px; font-size: 20px; font-family: arial;" -->

Abra `/` e confira que o botão **Novo livro** está visível.
<!-- .element: style="margin-bottom:40px; font-size: 22px; font-family: arial;" -->


<!-- .slide: data-background="#C9E66A" data-transition="convex"  -->
## Exercício 2: o formulário abre
<!-- .element: style="margin-bottom:25px; font-size: 36px; font-family: Marker Felt;" -->

*Revise `getByRole` e `click`.*
<!-- .element: style="margin-bottom:20px; font-size: 20px; font-family: arial;" -->

Clique em **Novo livro**. Confira que ISBN, Título, Autor e Ano de publicação estão visíveis.
<!-- .element: style="margin-bottom:40px; font-size: 22px; font-family: arial;" -->


<!-- .slide: data-background="#C9E66A" data-transition="convex"  -->
## Exercício 3: o fluxo feliz
<!-- .element: style="margin-bottom:25px; font-size: 36px; font-family: Marker Felt;" -->

*Revise o cadastro e `assertThat`.*
<!-- .element: style="margin-bottom:20px; font-size: 20px; font-family: arial;" -->

Cadastre o livro com o ISBN `0134685991`. A linha deve aparecer na tabela e o `role=status` deve dizer "Livro criado com sucesso."
<!-- .element: style="margin-bottom:40px; font-size: 20px; font-family: arial;" -->


<!-- .slide: data-background="#C9E66A" data-transition="convex"  -->
## Exercício 4: ISBN travado
<!-- .element: style="margin-bottom:25px; font-size: 36px; font-family: Marker Felt;" -->

*Revise a edição e a condição prévia via REST.*
<!-- .element: style="margin-bottom:20px; font-size: 20px; font-family: arial;" -->

Crie o livro com `POST /books`. Clique em **Editar**. Confira que o campo ISBN está desabilitado.
<!-- .element: style="margin-bottom:40px; font-size: 20px; font-family: arial;" -->


<!-- .slide: data-background="#C9E66A" data-transition="convex"  -->
## Exercício 5: recusar a exclusão
<!-- .element: style="margin-bottom:25px; font-size: 36px; font-family: Marker Felt;" -->

*Revise `onDialog`.*
<!-- .element: style="margin-bottom:20px; font-size: 20px; font-family: arial;" -->

Registre `dialog.dismiss()` **antes** do clique em **Excluir**. O livro deve **permanecer** na tabela.
<!-- .element: style="margin-bottom:40px; font-size: 20px; font-family: arial;" -->


<!-- .slide: data-background="#C9E66A" data-transition="convex"  -->
## Exercício 6: desafio
<!-- .element: style="margin-bottom:25px; font-size: 36px; font-family: Marker Felt;" -->

Aceite a exclusão com `dialog.accept()`. Confira que a linha some e que o `role=status` diz "Livro removido com sucesso."
<!-- .element: style="margin-bottom:40px; font-size: 20px; font-family: arial;" -->


<!-- .slide: data-background="#C9E66A" data-transition="zoom"  -->
# Questões
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt;" -->

<center>
<iframe src="https://vvs.rpmhub.dev/playwirght/questions.html" title="Playwright" width="90%" height="500" style="border:none;"></iframe>
</center>


<!-- .slide: data-background="#185449" data-transition="convex"  -->
# Referências
<!-- .element: style="margin-bottom:40px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

* Playwright Java. Disponível em: https://playwright.dev/java/docs/intro
<!-- .element: style="margin-bottom:18px; font-size: 20px; font-family: arial; color:#F5F5F5" -->

* Codegen Java. Disponível em: https://playwright.dev/java/docs/codegen
<!-- .element: style="margin-bottom:18px; font-size: 20px; font-family: arial; color:#F5F5F5" -->

* Locators Java. Disponível em: https://playwright.dev/java/docs/locators
<!-- .element: style="margin-bottom:18px; font-size: 20px; font-family: arial; color:#F5F5F5" -->

* Assertions Java. Disponível em: https://playwright.dev/java/docs/test-assertions
<!-- .element: style="margin-bottom:40px; font-size: 20px; font-family: arial; color:#F5F5F5" -->

<center>
<a href="https://rpmhub.dev" target="blanck"><img src="../../imgs/logo.png" alt="Rodrigo Prestes Machado" width="3%" height="3%" border=0 style="border:0; text-decoration:none; outline:none"></a><br/>
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/">CC BY 4.0 DEED</a>
</center>
  <!-- .element: style="margin-top:80px; font-size: 15px; font-family: Bradley Hand" -->
