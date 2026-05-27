package dev.ifrs.hexagonal;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static io.restassured.RestAssured.given;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Testes E2E (end-to-end) da UI Vue do projeto hexagonal usando Playwright.
 *
 * <p>{@code @QuarkusTest} sobe o Quarkus e o MySQL Dev Services em
 * {@code http://localhost:8080} (porta fixada em {@code application.properties}
 * pela propriedade {@code quarkus.http.test-port=8080}).</p>
 *
 * <p>{@code @UsePlaywright} gerencia o {@code Playwright}, o {@code Browser} e
 * cria um {@code BrowserContext} novo para cada {@code @Test}, injetando o
 * {@code Page} como parâmetro do método.</p>
 *
 * <p>Para depurar visualmente em modo headed, troque a anotação por
 * {@code @UsePlaywright(BooksE2ETest.HeadedOptions.class)} (factory comentada
 * abaixo).</p>
 */
@QuarkusTest
@UsePlaywright
public class BooksE2ETest {

    /** ISBN-10 válido usado nos cenários desta classe. */
    private static final String ISBN = "156881111X";

    private static final String BOOK_JSON = """
            {
              "isbn": "%s",
              "title": "Erdős on Graphs: His Legacy of Unsolved Problems",
              "author": "Fan Chung, Ronald L. Graham",
              "publicationYear": 1999,
              "copiesAvailable": 1
            }
            """.formatted(ISBN);

    // public static class HeadedOptions implements com.microsoft.playwright.junit.OptionsFactory {
    //     @Override
    //     public com.microsoft.playwright.junit.Options getOptions() {
    //         return new com.microsoft.playwright.junit.Options().setHeadless(false);
    //     }
    // }

    /**
     * Remove o livro de teste antes de cada cenário para garantir
     * independência da ordem de execução (404 é ignorado).
     */
    @BeforeEach
    void limparLivroDeTeste() {
        given().delete("/books/{isbn}", ISBN);
    }

    /**
     * Fluxo gravado pelo Codegen: cria um livro pela UI e verifica que ele
     * aparece na tabela.
     */
    @Test
    void cadastraLivro(Page page) {
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
                page.getByRole(AriaRole.ROW).filter(
                        new Locator.FilterOptions().setHasText(ISBN))
        ).isVisible();
    }

    /**
     * Mesmo fluxo da gravação, mas com verificação adicional da mensagem de
     * sucesso exibida no elemento {@code role="status"}.
     */
    @Test
    void cadastraLivroComMensagemDeSucesso(Page page) {
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
                page.getByRole(AriaRole.ROW).filter(
                        new Locator.FilterOptions().setHasText(ISBN))
        ).isVisible();

        assertThat(
                page.getByRole(AriaRole.STATUS)
        ).hasText("Livro criado com sucesso.");
    }

    /**
     * Pré-condição via REST + edição pela UI. O ISBN não pode ser alterado
     * (campo desabilitado em modo edição), por isso só o título é trocado.
     */
    @Test
    void editaLivro(Page page) {
        given()
                .contentType(ContentType.JSON)
                .body(BOOK_JSON)
                .post("/books")
                .then().statusCode(201);

        page.navigate("http://localhost:8080/");

        page.getByRole(AriaRole.ROW)
                .filter(new Locator.FilterOptions().setHasText(ISBN))
                .getByRole(AriaRole.BUTTON,
                        new Locator.GetByRoleOptions().setName("Editar"))
                .click();

        Locator campoTitulo = page.getByRole(AriaRole.TEXTBOX,
                new Page.GetByRoleOptions().setName("Título"));
        campoTitulo.clear();
        campoTitulo.fill("Erdős on Graphs (2ª edição)");

        page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Salvar")).click();

        assertThat(
                page.getByRole(AriaRole.ROW)
                        .filter(new Locator.FilterOptions()
                                .setHasText("Erdős on Graphs (2ª edição)"))
        ).isVisible();
    }

    /**
     * Pré-condição via REST + exclusão pela UI. A confirmação nativa
     * ({@code window.confirm}) é aceita pelo handler de diálogo registrado
     * antes do clique.
     */
    @Test
    void excluiLivro(Page page) {
        given()
                .contentType(ContentType.JSON)
                .body(BOOK_JSON)
                .post("/books")
                .then().statusCode(201);

        page.navigate("http://localhost:8080/");

        page.onDialog(dialog -> dialog.accept());

        page.getByRole(AriaRole.ROW)
                .filter(new Locator.FilterOptions().setHasText(ISBN))
                .getByRole(AriaRole.BUTTON,
                        new Locator.GetByRoleOptions().setName("Excluir"))
                .click();

        assertThat(
                page.getByRole(AriaRole.ROW)
                        .filter(new Locator.FilterOptions().setHasText(ISBN))
        ).not().isVisible();

        assertThat(
                page.getByRole(AriaRole.STATUS)
        ).hasText("Livro removido com sucesso.");
    }
}
