package dev.ifrs.hexagonal;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static io.restassured.RestAssured.given;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;

import io.quarkus.test.junit.QuarkusTest;

import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Executa o mesmo cenário de cadastro em Chromium, Firefox e WebKit.
 *
 * <p>{@code @UsePlaywright} cria um único navegador por classe — para
 * alternar entre três navegadores no mesmo conjunto de testes voltamos ao
 * controle manual com {@code Playwright.create()}. {@code @QuarkusTest}
 * continua subindo o backend automaticamente.</p>
 *
 * <p>Pré-requisito: além do Chromium (já instalado pelo {@code setup:e2e}),
 * baixe Firefox e WebKit uma vez por máquina:</p>
 *
 * <pre>{@code
 * ./mvnw exec:java -e \
 *   -D exec.mainClass=com.microsoft.playwright.CLI \
 *   -D exec.args="install firefox webkit"
 * }</pre>
 */
@QuarkusTest
public class BooksMultiBrowserTest {

    private static final String ISBN = "156881111X";

    static Playwright playwright;

    @BeforeAll
    static void iniciar() {
        playwright = Playwright.create();
    }

    @AfterAll
    static void encerrar() {
        playwright.close();
    }

    @BeforeEach
    void limparLivroDeTeste() {
        given().delete("/books/{isbn}", ISBN);
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
            page.navigate("http://localhost:8080/");

            page.getByRole(AriaRole.BUTTON,
                    new Page.GetByRoleOptions().setName("Novo livro")).click();
            page.getByRole(AriaRole.TEXTBOX,
                    new Page.GetByRoleOptions().setName("ISBN-")).fill(ISBN);
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
                            .filter(new Locator.FilterOptions().setHasText(ISBN))
            ).isVisible();

            given().delete("/books/{isbn}", ISBN);
        }
    }
}
