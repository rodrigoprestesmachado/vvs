---
layout: default
title: Playwright
parent: Teste de sistema
grand_parent: Teste de desenvolvimento
nav_order: 19
---

## 1. Visão geral

O [Playwright](https://playwright.dev) é uma ferramenta de teste de sistema
desenvolvida pela Microsoft que permite construir testes end to end com
interface Web. Assim como o [Selenium](https://www.selenium.dev), ele simula a
interação de um usuário com a aplicação no navegador.

Esta documentação usa como exemplo o frontend do projeto hexagonal de cadastro
de livros, disponível em `exemplos/hexagonal/frontend`. A aplicação é uma SPA
Vue 3 servida pelo backend Quarkus em `http://localhost:8080` e oferece as
operações de cadastro, edição, exclusão e listagem de livros.

### 1.1. Por que usar o Playwright?

| Característica | Descrição |
|---|---|
| Multi navegador | Suporta Chromium (Chrome/Edge), Firefox e WebKit (Safari) com a mesma API |
| Multi linguagem | JavaScript, TypeScript, Python, Java e .NET |
| Auto wait | Aguarda automaticamente que elementos estejam prontos antes de agir |
| Execução paralela | Roda múltiplos testes simultaneamente |
| Recursos de depuração | Trace viewer, modo UI, screenshots e gravação de vídeo |
| Codegen | Gera código automaticamente a partir da interação com a página |

### 1.2. Pré-requisitos

- [Java 17](https://openjdk.org) ou superior
- [Maven 3.9](https://maven.apache.org) ou superior
- Backend Quarkus do projeto hexagonal rodando em `http://localhost:8080`

---

## 2. Configuração do projeto Maven

Para usar o Playwright com Java, crie um projeto Maven e adicione as
dependências do Playwright e do JUnit 5 no arquivo `pom.xml`. O Maven Surefire
cuida da execução dos testes e da geração dos relatórios.

### 2.1. Estrutura do projeto

```
books-e2e/
├── src/
│   └── test/
│       └── java/
│           └── BooksTest.java
├── src/
│   └── test/
│       └── resources/
│           └── junit-platform.properties
└── pom.xml
```

### 2.2. Arquivo pom.xml

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
             http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>dev.ifrs</groupId>
  <artifactId>books-e2e</artifactId>
  <version>1.0.0</version>

  <properties>
    <maven.compiler.release>17</maven.compiler.release>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  </properties>

  <dependencies>

    <!-- Playwright Java -->
    <dependency>
      <groupId>com.microsoft.playwright</groupId>
      <artifactId>playwright</artifactId>
      <version>1.51.0</version>
      <scope>test</scope>
    </dependency>

    <!-- JUnit 5 -->
    <dependency>
      <groupId>org.junit.jupiter</groupId>
      <artifactId>junit-jupiter</artifactId>
      <version>5.11.0</version>
      <scope>test</scope>
    </dependency>

  </dependencies>

  <build>
    <plugins>

      <!-- Executa testes JUnit 5 e gera relatório XML (surefire-reports/) -->
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <version>3.2.5</version>
      </plugin>

      <!-- Permite rodar a CLI do Playwright (codegen, install) via mvn exec:java -->
      <plugin>
        <groupId>org.codehaus.mojo</groupId>
        <artifactId>exec-maven-plugin</artifactId>
        <version>3.2.0</version>
      </plugin>

    </plugins>
  </build>

</project>
```

### 2.3. Instalação dos navegadores

Após criar o `pom.xml`, baixe os binários dos navegadores com o comando abaixo.
Esse passo é necessário somente uma vez por máquina:

```sh
mvn exec:java -e \
  -D exec.mainClass=com.microsoft.playwright.CLI \
  -D exec.args="install"
```

---

## 3. Primeiro teste

Um teste no Playwright Java segue o ciclo padrão do JUnit 5: a classe de teste
abre o navegador uma vez (`@BeforeAll`), cria um contexto isolado para cada
teste (`@BeforeEach`) e fecha tudo ao final (`@AfterAll`, `@AfterEach`).

O exemplo abaixo verifica que a página inicial do cadastro de livros exibe o
título correto:

```java
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class BooksTest {

    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    Page page;

    @BeforeAll
    static void abrirNavegador() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
    }

    @AfterAll
    static void fecharNavegador() {
        playwright.close();
    }

    @BeforeEach
    void novoContexto() {
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    void fecharContexto() {
        context.close();
    }

    @Test
    void paginaInicialExibeTitulo() {
        page.navigate("http://localhost:8080");
        Locator titulo = page.getByRole(
            AriaRole.HEADING,
            new Page.GetByRoleOptions().setName("Cadastro de Livros")
        );
        assertTrue(titulo.isVisible());
    }
}
```

### 3.1. Anatomia do teste

| Elemento | Função |
|---|---|
| `Playwright.create()` | Inicia o processo interno do Playwright |
| `playwright.chromium().launch()` | Abre uma instância do navegador Chromium |
| `browser.newContext()` | Cria um perfil isolado: cookies, storage e estado próprios |
| `context.newPage()` | Abre uma aba dentro do contexto |
| `page.navigate(url)` | Navega para a URL informada |
| `page.getByRole(...)` | Localiza um elemento pelo papel ARIA |
| `assertTrue(locator.isVisible())` | Assertiva: verifica que o elemento está visível |
| `playwright.close()` | Fecha o navegador e libera todos os recursos |

---

## 4. Playwright Codegen

A forma mais rápida de criar um teste é **gravar a interação** com a interface.
O Codegen abre o navegador, observa o que você faz e gera o código Java
automaticamente, sem precisar escrever nada do zero.

### 4.1. Iniciando o Codegen

Execute o comando abaixo com o backend Quarkus rodando. A opção `--target java`
instrui o Codegen a gerar código Java em vez de TypeScript:

```sh
mvn exec:java -e \
  -D exec.mainClass=com.microsoft.playwright.CLI \
  -D exec.args="codegen --target java http://localhost:8080"
```

Ao executar o comando, duas janelas aparecem lado a lado:

1. **Navegador** — onde você interage normalmente (clica, digita, navega).
2. **Playwright Inspector** — painel que exibe o código Java gerado em tempo real.

<center>
  <img src="img/codegen.png" alt="Playwright Codegen" width="80%" height="80%" border=0 style="border:0; text-decoration:none; outline:none" /><br/>
  Figura 1 — Codegen: navegador (esquerda) e Inspector (direita)
</center>

### 4.2. Fluxo de uso passo a passo

1. **Inicie a gravação** — assim que o Codegen abre, ele já está gravando
   (ícone vermelho ativo no Inspector).
2. **Interaja com a página** — clique em botões, preencha formulários e navegue
   normalmente. Cada ação vira uma linha de código Java.
3. **Adicione assertivas** — use os botões da barra do Inspector (ver seção 4.3).
4. **Pause se necessário** — clique no botão vermelho para pausar ou retomar.
5. **Copie o código** — clique em **Copy** no Inspector e cole dentro de um
   método `@Test` na sua classe.

### 4.3. Criando assertivas

O Inspector possui três botões para criar assertivas sem escrever código:

| Botão | Método gerado | O que verifica |
|---|---|---|
| Assert visibility | `assertThat(locator).isVisible()` | O elemento está visível na tela |
| Assert text | `assertThat(locator).hasText(...)` | O elemento contém o texto esperado |
| Assert value | `assertThat(locator).hasValue(...)` | Um campo de formulário tem o valor esperado |

**Como usar:** clique no botão da assertiva desejada e, em seguida, clique
sobre o elemento na página. A linha é adicionada automaticamente ao código.

### 4.4. Seletores gerados

O Codegen prioriza seletores **semânticos** (baseados em como o usuário
enxerga a interface), tornando os testes mais robustos a mudanças de layout:

| Prioridade | Método | Exemplo em Java |
|---|---|---|
| 1ª | `getByRole()` | `page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Salvar"))` |
| 2ª | `getByText()` | `page.getByText("Nenhum livro cadastrado")` |
| 3ª | `getByLabel()` | `page.getByLabel("Título")` |
| 4ª | `getByPlaceholder()` | `page.getByPlaceholder("Ex.: 1-56881-111-X")` |
| 5ª | `getByTestId()` | `page.getByTestId("btn-salvar")` |
| Fallback | `locator()` (CSS) | `page.locator("#isbn")` |

### 4.5. Opções úteis da linha de comando

| Opção | Exemplo | Descrição |
|---|---|---|
| `--browser` | `--browser firefox` | Navegador alvo (chromium, firefox, webkit) |
| `--output` | `--output /tmp/Exemplo.java` | Salva direto em arquivo |
| `--target` | `--target java` | Linguagem de saída |
| `--device` | `--device "iPhone 13"` | Emula um dispositivo móvel |
| `--lang` | `--lang "pt-BR"` | Idioma do navegador |

### 4.6. Exemplo de código gerado pelo Codegen

O teste abaixo foi produzido pelo Codegen ao acessar o cadastro de livros,
clicar em **Novo livro**, preencher o formulário e verificar que a linha
aparece na tabela:

```java
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;
import static com.microsoft.playwright.options.AriaRole.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ExemploCodgen {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
            );
            BrowserContext context = browser.newContext();
            Page page = context.newPage();

            page.navigate("http://localhost:8080");

            page.getByRole(BUTTON, new Page.GetByRoleOptions().setName("Novo livro")).click();
            page.getByRole(TEXTBOX, new Page.GetByRoleOptions().setName("ISBN-")).fill("156881111X");
            page.getByRole(TEXTBOX, new Page.GetByRoleOptions().setName("Título")).fill(
                "Erdős on Graphs: His Legacy of Unsolved Problems"
            );
            page.getByRole(TEXTBOX, new Page.GetByRoleOptions().setName("Autor")).fill(
                "Fan Chung, Ronald L. Graham"
            );
            page.getByRole(SPINBUTTON, new Page.GetByRoleOptions().setName("Ano de publicação"))
                .fill("1999");
            page.getByRole(BUTTON, new Page.GetByRoleOptions().setName("Salvar")).click();

            assertThat(
                page.getByRole(ROW, new Page.GetByRoleOptions().setName("156881111X"))
            ).isVisible();
        }
    }
}
```

Pontos a observar:

- **`try-with-resources`** — o bloco garante que o Playwright feche todos os
  recursos automaticamente ao final.
- **`getByRole()`** — seletor semântico baseado no papel ARIA do elemento.
- **`assertThat(...).isVisible()`** — assertiva com auto wait: aguarda até o
  tempo limite configurado antes de falhar.

---

## 5. Exemplos completos: CRUD de livros

Esta seção mostra testes para cada operação disponível no cadastro de livros.
Os testes usam a mesma estrutura de classe apresentada na seção 3.

### 5.1. Cadastrar um livro

```java
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import org.junit.jupiter.api.*;

public class BooksTest {

    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    Page page;

    @BeforeAll
    static void abrirNavegador() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
    }

    @AfterAll
    static void fecharNavegador() {
        playwright.close();
    }

    @BeforeEach
    void novoContexto() {
        context = browser.newContext();
        page = context.newPage();
        page.navigate("http://localhost:8080");
    }

    @AfterEach
    void fecharContexto() {
        context.close();
    }

    @Test
    void cadastraLivro() {
        page.getByRole(AriaRole.BUTTON,
            new Page.GetByRoleOptions().setName("Novo livro")).click();

        page.getByRole(AriaRole.TEXTBOX,
            new Page.GetByRoleOptions().setName("ISBN-")).fill("156881111X");
        page.getByRole(AriaRole.TEXTBOX,
            new Page.GetByRoleOptions().setName("Título")).fill(
                "Erdős on Graphs: His Legacy of Unsolved Problems");
        page.getByRole(AriaRole.TEXTBOX,
            new Page.GetByRoleOptions().setName("Autor")).fill(
                "Fan Chung, Ronald L. Graham");
        page.getByRole(AriaRole.SPINBUTTON,
            new Page.GetByRoleOptions().setName("Ano de publicação")).fill("1999");

        page.getByRole(AriaRole.BUTTON,
            new Page.GetByRoleOptions().setName("Salvar")).click();

        // Verifica que a linha com o ISBN aparece na tabela
        assertThat(
            page.getByRole(AriaRole.ROW).filter(
                new Locator.FilterOptions().setHasText("156881111X"))
        ).isVisible();

        // Verifica a mensagem de sucesso
        assertThat(
            page.getByRole(AriaRole.STATUS)
        ).hasText("Livro criado com sucesso.");
    }
}
```

### 5.2. Editar um livro

O campo ISBN fica desabilitado na edição; somente os demais campos podem ser
alterados. O teste verifica que o título atualizado aparece na tabela após
salvar:

```java
@Test
void editaLivro() {
    // Pré-condição: o livro já deve existir no banco
    page.getByRole(AriaRole.ROW)
        .filter(new Locator.FilterOptions().setHasText("156881111X"))
        .getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Editar"))
        .click();

    Locator campoTitulo = page.getByRole(AriaRole.TEXTBOX,
        new Page.GetByRoleOptions().setName("Título"));
    campoTitulo.clear();
    campoTitulo.fill("Erdős on Graphs (2ª edição)");

    page.getByRole(AriaRole.BUTTON,
        new Page.GetByRoleOptions().setName("Salvar")).click();

    assertThat(
        page.getByRole(AriaRole.ROW)
            .filter(new Locator.FilterOptions().setHasText("Erdős on Graphs (2ª edição)"))
    ).isVisible();
}
```

### 5.3. Excluir um livro

A exclusão exibe um diálogo de confirmação nativo do navegador
(`window.confirm`). O Playwright permite aceitar ou rejeitar esse diálogo
antes de clicar no botão:

```java
@Test
void excluiLivro() {
    // Aceita o diálogo de confirmação automaticamente
    page.onDialog(dialog -> dialog.accept());

    page.getByRole(AriaRole.ROW)
        .filter(new Locator.FilterOptions().setHasText("156881111X"))
        .getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Excluir"))
        .click();

    // Verifica que a linha sumiu da tabela
    assertThat(
        page.getByRole(AriaRole.ROW)
            .filter(new Locator.FilterOptions().setHasText("156881111X"))
    ).not().isVisible();

    assertThat(
        page.getByRole(AriaRole.STATUS)
    ).hasText("Livro removido com sucesso.");
}
```

---

## 6. Integração com Maven e exportação JUnit

O Maven Surefire gera automaticamente relatórios no formato XML do JUnit após
cada execução de `mvn test`. Esses arquivos são o padrão aceito por ferramentas
de integração contínua (Jenkins, GitHub Actions, GitLab CI, etc.).

### 6.1. Executar os testes via Maven

Com o projeto configurado conforme a seção 2, basta rodar:

```sh
mvn test
```

O Surefire executa todos os métodos anotados com `@Test` e grava os relatórios
em `target/surefire-reports/`. Cada classe de teste gera dois arquivos:

```
target/surefire-reports/
├── BooksTest.txt          ← saída em texto puro
└── TEST-BooksTest.xml     ← relatório no formato JUnit XML
```

### 6.2. Formato do relatório XML

O arquivo `TEST-BooksTest.xml` segue o esquema padrão do JUnit e pode ser
importado diretamente por qualquer ferramenta de CI:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<testsuite name="BooksTest" tests="3" failures="0" errors="0" skipped="0" time="8.42">
  <testcase name="cadastraLivro" classname="BooksTest" time="3.15"/>
  <testcase name="editaLivro"    classname="BooksTest" time="2.80"/>
  <testcase name="excluiLivro"   classname="BooksTest" time="2.47"/>
</testsuite>
```

### 6.3. Integração com o ciclo de vida do Maven

Para que os testes E2E façam parte do build sem bloquear o ciclo padrão, crie
um perfil `e2e` no `pom.xml`. Dessa forma o comando `mvn test` normal não
executa os testes E2E; eles rodam apenas quando o perfil é ativado
explicitamente:

```xml
<profiles>
  <profile>
    <id>e2e</id>
    <build>
      <plugins>
        <plugin>
          <groupId>org.apache.maven.plugins</groupId>
          <artifactId>maven-surefire-plugin</artifactId>
          <version>3.2.5</version>
          <configuration>
            <includes>
              <include>**/*Test.java</include>
            </includes>
          </configuration>
        </plugin>
      </plugins>
    </build>
  </profile>
</profiles>
```

Para executar os testes E2E e gerar o relatório XML:

```sh
mvn test -P e2e
```

---

## 7. Execução paralela em múltiplos navegadores

O Playwright Java suporta Chromium, Firefox e WebKit com a mesma API. Usando
`@ParameterizedTest` do JUnit 5 é possível rodar o mesmo teste nos três
navegadores ao mesmo tempo, reduzindo o tempo total de execução.

### 7.1. Teste parametrizado por navegador

A ideia é passar o nome do navegador como parâmetro. Dentro do teste, a
instância correta do `BrowserType` é selecionada antes de abrir o navegador:

```java
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.*;
import java.util.stream.Stream;

public class BooksMultiBrowserTest {

    static Playwright playwright;

    @BeforeAll
    static void iniciar() {
        playwright = Playwright.create();
    }

    @AfterAll
    static void encerrar() {
        playwright.close();
    }

    static Stream<String> navegadores() {
        return Stream.of("chromium", "firefox", "webkit");
    }

    @ParameterizedTest(name = "cadastraLivro [{0}]")
    @MethodSource("navegadores")
    void cadastraLivroEmCadaNavegador(String nomeNavegador) {
        BrowserType tipo = switch (nomeNavegador) {
            case "firefox" -> playwright.firefox();
            case "webkit"  -> playwright.webkit();
            default        -> playwright.chromium();
        };

        try (Browser browser = tipo.launch()) {
            Page page = browser.newContext().newPage();
            page.navigate("http://localhost:8080");

            page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Novo livro")).click();
            page.getByRole(AriaRole.TEXTBOX,
                new Page.GetByRoleOptions().setName("ISBN-")).fill("156881111X");
            page.getByRole(AriaRole.TEXTBOX,
                new Page.GetByRoleOptions().setName("Título")).fill(
                    "Erdős on Graphs: His Legacy of Unsolved Problems");
            page.getByRole(AriaRole.TEXTBOX,
                new Page.GetByRoleOptions().setName("Autor")).fill(
                    "Fan Chung, Ronald L. Graham");
            page.getByRole(AriaRole.SPINBUTTON,
                new Page.GetByRoleOptions().setName("Ano de publicação")).fill("1999");
            page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Salvar")).click();

            assertThat(
                page.getByRole(AriaRole.ROW)
                    .filter(new Locator.FilterOptions().setHasText("156881111X"))
            ).isVisible();
        }
    }
}
```

### 7.2. Habilitando o paralelismo no JUnit 5

Por padrão o JUnit 5 executa os testes de forma sequencial. Para que os três
navegadores abram ao mesmo tempo, crie o arquivo
`src/test/resources/junit-platform.properties` com o conteúdo abaixo:

```properties
junit.jupiter.execution.parallel.enabled=true
junit.jupiter.execution.parallel.mode.default=concurrent
junit.jupiter.execution.parallel.config.strategy=fixed
junit.jupiter.execution.parallel.config.fixed.parallelism=3
```

| Propriedade | Valor | Efeito |
|---|---|---|
| `parallel.enabled` | `true` | Ativa o modo paralelo |
| `mode.default` | `concurrent` | Testes rodam ao mesmo tempo por padrão |
| `config.strategy` | `fixed` | Número fixo de threads paralelas |
| `config.fixed.parallelism` | `3` | Uma thread por navegador |

Após essa configuração, ao rodar `mvn test` os três navegadores abrirão
simultaneamente e o resultado aparecerá no relatório XML com os três casos
de teste separados:

```
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO]   BooksMultiBrowserTest > cadastraLivro [chromium] PASSED
[INFO]   BooksMultiBrowserTest > cadastraLivro [firefox]  PASSED
[INFO]   BooksMultiBrowserTest > cadastraLivro [webkit]   PASSED
```

---

## 8. Gravação de vídeo

A gravação de vídeo é útil para investigar falhas que não se reproduzem
facilmente de forma manual. O Playwright salva um arquivo `.webm` por contexto
dentro do diretório informado.

Para ativar a gravação, passe a opção `setRecordVideoDir` ao criar o contexto:

```java
import java.nio.file.Paths;

BrowserContext context = browser.newContext(
    new Browser.NewContextOptions()
        .setRecordVideoDir(Paths.get("test-videos/"))
);

Page page = context.newPage();
page.navigate("http://localhost:8080");
// ... interações do teste ...

// O vídeo só é gravado no disco após fechar o contexto
context.close();
```

Para gravar somente quando o teste falha, feche o contexto dentro de um bloco
`try/finally` e delete o arquivo em caso de sucesso:

```java
Path videoPath = null;
try {
    context = browser.newContext(
        new Browser.NewContextOptions()
            .setRecordVideoDir(Paths.get("test-videos/"))
    );
    page = context.newPage();
    page.navigate("http://localhost:8080");

    // ... passos do teste ...

    videoPath = page.video().path();
    context.close();
    // Teste passou: remove o vídeo
    Files.deleteIfExists(videoPath);
} catch (AssertionError e) {
    context.close();
    // Teste falhou: o vídeo permanece em test-videos/
    throw e;
}
```

Os vídeos são salvos em `test-videos/` com nomes gerados automaticamente:

```
test-videos/
└── a3f9b2c1d4e5f6a7.webm
```

---

## 9. Referências

- [Playwright Java — documentação oficial](https://playwright.dev/java/docs/intro)
- [Codegen Java — guia de gravação](https://playwright.dev/java/docs/codegen)
- [Locators Java — guia de seletores](https://playwright.dev/java/docs/locators)
- [Assertions Java — referência completa](https://playwright.dev/java/docs/test-assertions)
- [Parallel execution — JUnit Platform](https://junit.org/junit5/docs/current/user-guide/#writing-tests-parallel-execution)
- [Maven Surefire Plugin](https://maven.apache.org/surefire/maven-surefire-plugin/)

<center>
<a href="https://rpmhub.dev" target="blanck"><img src="../imgs/logo.png" alt="Rodrigo Prestes Machado" width="3%" height="3%" border=0 style="border:0; text-decoration:none; outline:none"></a><br/>
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/">CC BY 4.0 DEED</a>
</center>
