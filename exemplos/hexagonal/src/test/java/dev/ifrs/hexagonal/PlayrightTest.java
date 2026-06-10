package dev.ifrs.hexagonal;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;

import io.quarkus.test.junit.QuarkusTest;

import java.nio.file.Paths;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Testes E2E com Playwright.
 *
 * <p>O método {@code test} usa a instância injetada pelo {@code @UsePlaywright}
 * (Chromium). O teste parametrizado {@code cadastraLivroEmCadaNavegador} cria
 * sua própria instância de {@link Playwright} dentro de cada invocação porque
 * {@code Playwright} não é thread-safe; dessa forma os três navegadores podem
 * rodar em paralelo sem interferência.</p>
 */
@QuarkusTest
@UsePlaywright(PlayrightTest.WithVideo.class)
public class PlayrightTest {

    static Stream<String> navegadores() {
        return Stream.of("chromium", "firefox", "webkit");
    }

    @Test
    void test(Page page) {
        page.navigate("http://localhost:8080/");
        page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Novo livro")).click();
        page.getByRole(AriaRole.TEXTBOX,
                new Page.GetByRoleOptions().setName("ISBN-")).fill("6587958494");
        page.getByRole(AriaRole.TEXTBOX,
                new Page.GetByRoleOptions().setName("Título")).fill("Estatistica Basica");
        page.getByRole(AriaRole.TEXTBOX,
                new Page.GetByRoleOptions().setName("Autor")).fill("Rodrigo");
        page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Salvar")).click();
        assertThat(page.locator("tbody")).containsText("6587958494");
    }

    @ParameterizedTest(name = "cadastraLivro [{0}]")
    @MethodSource("navegadores")
    @Execution(ExecutionMode.CONCURRENT)
    void cadastraLivroEmCadaNavegador(String nomeNavegador) {
        String isbn = switch (nomeNavegador) {
            case "firefox" -> "0201634554";
            case "webkit"  -> "0596007124";
            default        -> "0306406152";
        };

        try (Playwright pw = Playwright.create()) {
            BrowserType tipo = switch (nomeNavegador) {
                case "firefox" -> pw.firefox();
                case "webkit"  -> pw.webkit();
                default        -> pw.chromium();
            };

            try (Browser browser = tipo.launch(
                        new BrowserType.LaunchOptions().setSlowMo(300));
                 BrowserContext context = browser.newContext(
                        new Browser.NewContextOptions()
                                .setRecordVideoDir(
                                        Paths.get("target/videos/" + nomeNavegador)))) {

                Page page = context.newPage();
                page.navigate("http://localhost:8080/");

                page.getByRole(AriaRole.BUTTON,
                        new Page.GetByRoleOptions().setName("Novo livro")).click();
                page.getByRole(AriaRole.TEXTBOX,
                        new Page.GetByRoleOptions().setName("ISBN-")).fill(isbn);
                page.getByRole(AriaRole.TEXTBOX,
                        new Page.GetByRoleOptions().setName("Título")).fill("Estatistica Basica");
                page.getByRole(AriaRole.TEXTBOX,
                        new Page.GetByRoleOptions().setName("Autor")).fill("Rodrigo");
                page.getByRole(AriaRole.BUTTON,
                        new Page.GetByRoleOptions().setName("Salvar")).click();

                assertThat(page.locator("tbody")).containsText(isbn);
            }
        }
    }

    /** Configura gravação de vídeo e slowMo para o teste {@code test}. */
    public static class WithVideo implements OptionsFactory {
        @Override
        public Options getOptions() {
            return new Options()
                    .setLaunchOptions(new BrowserType.LaunchOptions().setSlowMo(300))
                    .setContextOptions(new Browser.NewContextOptions()
                            .setRecordVideoDir(Paths.get("target/videos/chromium")));
        }
    }
}

